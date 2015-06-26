/**
 * 
 */
package gov.nih.nci.ncia;

import gov.nih.nci.cagrid.cqlquery.CQLQuery;
import gov.nih.nci.cagrid.ncia.service.NCIACoreServiceConfiguration;
import gov.nih.nci.cagrid.ncia.service.NCIAQueryFilter;
import gov.nih.nci.ncia.domain.TrialDataProvenance;
import gov.nih.nci.ncia.zip.ZipManager;
import gov.nih.nci.ncia.zip.ZippingDTO;

import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.cagrid.transfer.context.service.helper.TransferServiceHelper;
import org.cagrid.transfer.context.stubs.types.TransferServiceContextReference;
import org.globus.wsrf.encoding.ObjectSerializer;
import org.globus.wsrf.encoding.SerializationException;

/**
 * @author lethai
 *
 */
public class ServiceImplHelper {
	private static Logger logger = Logger.getLogger(ServiceImplHelper.class);
	
	/**
	 * retrieve image files path from the file system
	 */
	// STEP 1: run your CQLQuery --> get the images filepath
	// return HashMap that contains sopInstanceUID as key and filepath
	/* This can be improved by using interface Map as return type */
	public static Map<String, String> retrieveImageFiles(CQLQuery cQLQuery)
			throws RemoteException {

		CustomizedCQLQuery c = new CustomizedCQLQuery();
		StringBuffer sbSOPInstanceUIDList = null;

		/***********************************************************************
		 * STEP 1.0 Modify the CQLQuery so that it always returns Image Object
		 * in order to get list of SOPInstanceUIDs
		 **********************************************************************/
		long start = System.nanoTime();

		cQLQuery = c.modifyCQLQuery(cQLQuery);
		long end = System.nanoTime();
		logger.info("Total time to modify the CQL is " + (end - start)
				+ " nano seconds");
		try {
			logger.debug("cqlQuery after modified: ..... "
					+ ObjectSerializer.toString(cQLQuery, new QName(
							"http://CQL.caBIG/1/gov.nih.nci.cagrid.CQLQuery",
							"CQLQuery")));
		} catch (SerializationException e) {
			logger.error("SerializationException: ", e);
		}
		/***********************************************************************
		 * STEP 1.1 We will first run the CQL query as a regular query and get a
		 * list of SOPInstanceUIDs
		 **********************************************************************/
		Map<String, String> retrievedFileNames=null;
		ImageFilesProcessor ifp = null;
		try{
		ifp = new ImageFilesProcessor();
		sbSOPInstanceUIDList = ifp.getSOPInstanceUIDList(cQLQuery);
		String sopString = sbSOPInstanceUIDList.toString();
		logger.debug("sopString list: " + sopString.trim());
		if (sopString == null|| sopString.trim().equals("''")) {
			return null;
		}

		/***********************************************************************
		 * STEP 1.2 Using this list of SOPInstanceUIDs get the actual files
		 **********************************************************************/

		retrievedFileNames = ifp
				.getImagesFiles(sbSOPInstanceUIDList);
		}catch(Exception e){
			logger.error("Error getting image files" , e);
		}
		c = null;
		ifp = null;

		return retrievedFileNames;

	}

	/**
	 * Empty output stream
	 * @return
	 * @throws RemoteException
	 */

	public static TransferServiceContextReference getEmptyOutputStream()
			throws RemoteException {
		TransferServiceContextReference tscr = null;
		PipedOutputStream pos = new PipedOutputStream();
		PipedInputStream pis = new PipedInputStream();
		try {
			pis.connect(pos);
			pos.flush();
			pos.close();
		} catch (IOException e) {
			logger.error("Error in getEmptyOutputStream ", e);
		}

		tscr = TransferServiceHelper.createTransferContext(pis, null);
		return tscr;
	}
	/**
	 * check whether the given trial data provenance is publicly available
	 * @param tdp
	 * @return
	 * @throws RemoteException
	 */

	/*
	 * This methods calls to csm tables to get public access trial data
	 * provenance and compare with the trial data provenance of the query.
	 * Return true if the query trial data provenance is in the public group,
	 * false otherwise.
	 */
	public static boolean getPublicGroupAndCheckForPublicAccess(
			TrialDataProvenance tdp) throws RemoteException {

		boolean isPublic = false;
		try {
			NCIAQueryFilter filter = NCIAQueryFilter.getInstance();
			filter.setGroupName(NCIACoreServiceConfiguration.getConfiguration()
					.getNciaPublicGroup());
			List<TrialDataProvenance> tdpList = filter.getDataFilter();
			isPublic = filter.isPublic(tdp, tdpList);

		} catch (Exception e) {
			logger.error("Error getting csm data: ", e);
			throw new RemoteException("Error getting data to filter and check for public access: "
					+ e.getMessage(), e);
		}
		return isPublic;
	}

	/**
	 * check whether given trial data provenance map is publicly available.
	 * @param tdps
	 * @return
	 * @throws RemoteException
	 */
	
	public static List<String> getPublicGroupAndCheckForPublicAccess(
			Map<String, TrialDataProvenance> tdps) throws RemoteException {

		boolean isPublic = false;
		List<String> publicIds = new ArrayList<String>();
		try {
			NCIAQueryFilter filter = NCIAQueryFilter.getInstance();
			filter.setGroupName(NCIACoreServiceConfiguration.getConfiguration()
					.getNciaPublicGroup());
			List<TrialDataProvenance> tdpList = filter.getDataFilter();
			//for each id and tdp, check whether it's in public group
			Set keys = tdps.keySet();
			for(Iterator i = keys.iterator(); i.hasNext();){
				String id = (String)i.next();
				logger.info("key: " + id );
				TrialDataProvenance tdp = tdps.get(id);
				isPublic = filter.isPublic(tdp, tdpList);
				if(isPublic){
					publicIds.add(id);
					logger.info("\n++++++++++++ public key: " + id);
				}
			}
			

		} catch (Exception e) {
			logger.error("Error getting csm data: ", e);
			throw new RemoteException("Error getting data to filter: "
					+ e.getMessage(), e);
		}
		return publicIds;
	}
	/**
	 * This method zips all the files in zdto into zip file (fileName) and store at filePath location
	 * @param zdto
	 * @param filePath
	 * @param fileName
	 * @return
	 * @throws RemoteException
	 */
	public static org.cagrid.transfer.context.stubs.types.TransferServiceContextReference getDicomData(
			List<ZippingDTO> zdto, String filePath, String fileName)
			throws RemoteException {

		long start = System.currentTimeMillis();
		ZipManager zipManager = null;
		try {
			zipManager = new ZipManager();
			zipManager.setZdto(zdto);
			zipManager.setDestinationFileName(fileName);
			zipManager.setDestinationFilePath(filePath);
			logger.info("\n\nstart zipping.....");
			logger.info("filepath: " + filePath + " \tfileName: " + fileName); 
			zipManager.zip();
			/*zipManager.start();
			 Make sure the zipManager thread is done before sending the zip file to client 
			while (true) {
				Thread.State ts = zipManager.getState();
				//logger.info("thread state:\n " + ts.name());
				if (ts == Thread.State.TERMINATED) {
					break;
				}
			}*/

			long end = System.currentTimeMillis();
			logger.info("\nTotal time to read and zip " + filePath + fileName
					+ ": " + (end - start) + " milli seconds");
		} catch (Exception e) {
			logger.error("Error zipping file " + e.getMessage());
			throw new RemoteException(e.getMessage(), e);
		} finally {
			zipManager = null;
		}
		start = System.currentTimeMillis();
		File zipFile = new File(filePath + fileName);
		logger.info("file name: " + zipFile.getName() + " path: " + zipFile.getPath());
		TransferServiceContextReference tscr = TransferServiceHelper
				.createTransferContext(zipFile, null, true);
		long end = System.currentTimeMillis();

		logger.info("\nTotal time to read and transfer " + filePath + fileName
				+ ": " + (end - start) + " milli seconds");

		return tscr;
	}	
	/**
	 * get the temp location to store the zip file
	 * @return
	 * @throws Exception
	 */
	public static String getTempZipLocation() throws Exception{
		
		String filepath="";
		try {
			filepath = NCIACoreServiceConfiguration.getConfiguration().getTempZipLocation();
			logger.info("filepath: " + filepath);
			//check for the forward slash at the end
			if(!filepath.endsWith("/")){
				filepath += "/";
			}
			logger.info("filepath: " + filepath);
		}catch(Exception e){
			throw new Exception("Error getting tempZipLocation: " + e.getMessage(), e);
		}
		return filepath;
	}
}

