/**
 * 
 */
package gov.nih.nci.cagrid.ncia.client;

import gov.nih.nci.cagrid.cqlquery.CQLQuery;
import gov.nih.nci.cagrid.cqlresultset.CQLQueryResults;
import gov.nih.nci.cagrid.data.faults.MalformedQueryExceptionType;
import gov.nih.nci.cagrid.data.faults.QueryProcessingExceptionType;
import gov.nih.nci.cagrid.data.utilities.CQLQueryResultsIterator;
import gov.nih.nci.ivi.utils.ZipEntryInputStream;
import gov.nih.nci.ncia.domain.Annotation;
import gov.nih.nci.ncia.domain.Image;
import gov.nih.nci.ncia.domain.Patient;
import gov.nih.nci.ncia.domain.Series;
import gov.nih.nci.ncia.domain.Study;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.util.zip.ZipInputStream;

import javax.xml.namespace.QName;

import junit.framework.TestCase;

import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
import org.cagrid.transfer.context.client.TransferServiceContextClient;
import org.cagrid.transfer.context.client.helper.TransferClientHelper;
import org.cagrid.transfer.descriptor.DataTransferDescriptor;
import org.globus.wsrf.encoding.DeserializationException;
import org.globus.wsrf.encoding.ObjectDeserializer;
import org.globus.wsrf.encoding.ObjectSerializer;
import org.globus.wsrf.encoding.SerializationException;
import org.xml.sax.InputSource;

/**
 * @author lethai
 *
 */
public class NCIACoreServiceClientTestCaseFunctional_ extends TestCase {

	//String gridServiceUrl = "http://imaging-dev.nci.nih.gov/wsrf/services/cagrid/NCIACoreService";
	//String gridServiceUrl = "http://nciavgriddev5004.nci.nih.gov:18080/wsrf/services/cagrid/NCIACoreService";
	//String gridServiceUrl = "http://imaging4-qa.nci.nih.gov/wsrf/services/cagrid/NCIACoreService";
	//String localDownloadLocation = System.getProperty("java.io.tmpdir") + File.separator + "NCIAGridClientDownload";
	//String gridServiceUrl = "http://imaging-stage.nci.nih.gov/wsrf/services/cagrid/NCIACoreService";
	String gridServiceUrl = "http://imaging.nci.nih.gov/wsrf/services/cagrid/NCIACoreService";
//	String gridServiceUrl = "http://imaging4-qa.nci.nih.gov/wsrf/services/cagrid/NCIACoreService";
//String gridServiceUrl = "http://localhost:21080/wsrf/services/cagrid/NCIACoreService";

	String clientDownLoadLocation ="NBIAGridClientDownLoad";
	
	public void testAnnotation(){
		System.out.println("Retrieving Annotation");
		String filename = "test/resources/testCase11.xml";
		final CQLQuery fcqlq = this.loadXMLFile(filename);
		long start = System.currentTimeMillis();
		// Execute Query
		CQLQueryResults result = this.connectAndExecuteQuery(fcqlq);
		long end = System.currentTimeMillis();
		System.out.println("Total time to get meta data and print out result for testcase 1: " + (end - start) + " milli seconds");
		start = System.currentTimeMillis();
		if(result != null)	{
			CQLQueryResultsIterator iter2 = new CQLQueryResultsIterator(result);

			System.out.println("printing out result for testcase 1......................");
			// Print Results
			int ii = 1;
			while (iter2.hasNext()) {
				Annotation obj = (Annotation)iter2.next();
				if (obj == null) {
					System.out.println("something not right.  obj is null" );
					continue;
				}else{
					System.out.println("Result " + ii++ + ". series instance id is " + obj.getSeriesInstanceUID()+ " file path: " + obj.getFilePath());
				}
			}
		}else{
			System.out.println("No result found for " + filename);
		}
		end = System.currentTimeMillis();
		System.out.println("Total time to print out result for testcase 1: " + (end - start) + " milli seconds");

	}

	/**
	 * Test method for {@link gov.nih.nci.cagrid.ncia.client.NCIACoreServiceClient#retrieveDicomData(gov.nih.nci.cagrid.cqlquery.CQLQuery)}.
	 */
	public void testRetrieveDicomData() {
		NCIACoreServiceClient client = null;
		try {
			client = new NCIACoreServiceClient(gridServiceUrl);
		} catch (MalformedURIException e) {
			fail("Malformed URI Exception Thrown");
			System.out.println("MalformURIException " + e);
		} catch (RemoteException e) {
			fail("Remote Exception Thrown");
			System.out.println("RemoteException " + e);
		}
		assertNotNull("Connection with remote grid service could not be opened", client);

		String filename = "test/resources/testCase8.xml";
		gov.nih.nci.cagrid.cqlquery.CQLQuery newQuery = null;
		long start = System.currentTimeMillis();
		try {
			InputSource queryInput = new InputSource(new FileReader(filename));
			newQuery = (CQLQuery) ObjectDeserializer.deserialize(queryInput, CQLQuery.class);
			System.err.println(ObjectSerializer.toString(newQuery,
					new QName("http://CQL.caBIG/1/gov.nih.nci.cagrid.CQLQuery", "CQLQuery")));
		} catch (FileNotFoundException e) {
			System.out.println("File not Found " + e);
			fail("test Query XML file not found");
		} catch (DeserializationException e) {
			fail("test Query XML file could not be deserialized");
			System.out.println("DeserializationException " + e);
		} catch (SerializationException e) {
			fail("test Query XML file could not be serialized");
			System.out.println("SerializationException " + e);
		}
		assertNotNull(newQuery);

		InputStream istream = null;
		TransferServiceContextClient tclient = null;
		try {
			System.out.println("............using transferService to retrieve data.................");
			org.cagrid.transfer.context.stubs.types.TransferServiceContextReference tscr = client.retrieveDicomData(newQuery);
			
			tclient = new TransferServiceContextClient(tscr.getEndpointReference());

			istream = TransferClientHelper.getData(tclient.getDataTransferDescriptor());
			long end = System.currentTimeMillis();
			System.out.println("Total to get dicom data : " + (end - start) + " milli seconds.");
		} catch (MalformedURIException e) {
			System.out.println("MalformedURIException " +e);

			fail("Malformed URI Exception Thrown");
		} catch (RemoteException e) {

			System.out.println("RemoteException ,,,,,,,,,,,,,,,,,,, " +e);
			fail("Remote Exception Thrown");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}


		System.out.println("getting zip input stream.............." + istream);
		assertNotNull("Input stream recieved from transfer service is null", istream);

		if(istream == null){
			System.out.println("istrea is null");
			return;
		}
		start = System.currentTimeMillis();
        ZipInputStream zis = new ZipInputStream(istream);
        ZipEntryInputStream zeis = null;
        BufferedInputStream bis = null;
        int ii = 1;
        while(true) {
              try {
                    zeis = new ZipEntryInputStream(zis);
              } catch (EOFException e) {
                    break;
              } catch (IOException e) {
				fail("IOException thrown when recieving the zip stream" + e);
            	  System.out.println("IOException " + e);
			}
              String unzzipedFile = downloadLocation();
              System.out.println(ii++ + " filename: " + zeis.getName());

              bis = new BufferedInputStream(zeis);

              byte[] data = new byte[8192];
              int bytesRead = 0;
              try {
            	  BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(unzzipedFile + File.separator + zeis.getName()));

				while ((bytesRead = (bis.read(data, 0, data.length))) > 0)  {
						bos.write(data, 0, bytesRead);
				  }
					bos.flush();
		           bos.close();
			} catch (IOException e) {
				fail("IOException thrown when reading the zip stream " + e);
				System.out.println("IOException " + e);
			}
        }

        try {
			zis.close();
		} catch (IOException e) {
			fail("IOException thrown when closing the zip stream " + e);
			System.out.println("IOException " +e);
		}

        try {
			tclient.destroy();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Remote exception thrown when closing the transer context " + e);
		}
		long end = System.currentTimeMillis();
		System.out.println("Total time download images is " + (end - start) + " milli seconds");
	
	}
	
	/**
	 * Test method for {@link gov.nih.nci.cagrid.ncia.client.NCIACoreServiceClient#retrieveDicomDataByNthStudyTimepointForPatient(java.lang.String, int)}.
	 */
	public void testRetrieveDicomDataByNthStudyTimepointForPatient() throws Exception {
		String patientId = "1.3.6.1.4.1.9328.50.14.0001";
		System.out.println("PatientId : " + patientId); 
		int studyNumber = 2;

		NCIACoreServiceClient client = new NCIACoreServiceClient(gridServiceUrl);
		assertNotNull("Connection with remote grid service could not be opened", client);
		long start = System.currentTimeMillis();
		
		InputStream istream = null;
		TransferServiceContextClient tclient = null;
		System.out.println("............using transferService to retrieve data.................");
		org.cagrid.transfer.context.stubs.types.TransferServiceContextReference tscr = client.retrieveDicomDataByNthStudyTimePointForPatient(patientId, studyNumber);
			
		System.out.println("tscr............ "  + tscr);
    	tclient = new TransferServiceContextClient(tscr.getEndpointReference());

	    istream = TransferClientHelper.getData(tclient.getDataTransferDescriptor());
		long end = System.currentTimeMillis();
		System.out.println("Total time get images is " + (end - start) + " milli seconds");

		System.out.println("getting zip input stream.............." + istream);
		assertNotNull("Input stream recieved from transfer service is null", istream);

		if(istream == null){
			System.out.println("istream is null");
			return;
		}
		start = System.currentTimeMillis();
        ZipInputStream zis = new ZipInputStream(istream);
        ZipEntryInputStream zeis = null;
        BufferedInputStream bis = null;
        int ii = 1;
        while(true) {
        	try {
                zeis = new ZipEntryInputStream(zis);
                //System.out.println("zipentryInputstream " + zeis);
        	} 
        	catch (EOFException e) {
        		break;
        	}            
  
            String unzzipedFile = downloadLocation();
            System.out.println(ii++ + " filename: " + zeis.getName());

            bis = new BufferedInputStream(zeis);
            byte[] data = new byte[8192];
            int bytesRead = 0;
            
            File outputFile = new File(unzzipedFile, zeis.getName());
            outputFile.getParentFile().mkdirs();
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputFile));
          	  
            while ((bytesRead = (bis.read(data, 0, data.length))) > 0)  {
                bos.write(data, 0, bytesRead);
            }
            bos.flush();
		    bos.close();
        }

        zis.close();
		tclient.destroy();

		end = System.currentTimeMillis();
		System.out.println("Total time get download images is " + (end - start) + " milli seconds");
	}

	/**
	 * Test method for {@link gov.nih.nci.cagrid.ncia.client.NCIACoreServiceClient#retrieveDicomDataByPatientId(java.lang.String)}.
	 */
	public void testRetrieveDicomDataByPatientId() {		
		String patientId = "1.3.6.1.4.1.9328.50.14.0007";
		//patientId="SNMCB04";
		//patientId = "1.3.6.1.4.1.9328.50.4.0042";	
		System.out.println("PatientId : " + patientId);

		NCIACoreServiceClient client = null;
		try {
			client = new NCIACoreServiceClient(gridServiceUrl);
		} catch (MalformedURIException e) {
			fail("Malformed URI Exception Thrown");
			System.out.println("MalformURIException " + e);
		} catch (RemoteException e) {
			fail("Remote Exception Thrown");
			System.out.println("RemoteException " + e);
		}
		assertNotNull("Connection with remote grid service could not be opened", client);
		long start = System.currentTimeMillis();
		
		InputStream istream = null;
		TransferServiceContextClient tclient = null;
		try {
			System.out.println("............using transferService to retrieve data.................");
			org.cagrid.transfer.context.stubs.types.TransferServiceContextReference tscr = client.retrieveDicomDataByPatientId(patientId);
			
			tclient = new TransferServiceContextClient(tscr.getEndpointReference());
	       
			istream = TransferClientHelper.getData(tclient.getDataTransferDescriptor());
			long end = System.currentTimeMillis();
			System.out.println("Total time get images is " + (end - start) + " milli seconds");
		} catch (MalformedURIException e) {
			System.out.println("MalformedURIException " +e);

			fail("Malformed URI Exception Thrown");
		} catch (RemoteException e) {

			System.out.println("RemoteException ,,,,,,,,,,,,,,,,,,, " +e);
			fail("Remote Exception Thrown");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}


		//System.out.println("getting zip input stream.............." + istream);
		assertNotNull("Input stream recieved from transfer service is null", istream);

		if(istream == null){
			System.out.println("istream is null");
			return;
		}
		start = System.currentTimeMillis();
        ZipInputStream zis = new ZipInputStream(istream);
        ZipEntryInputStream zeis = null;
        BufferedInputStream bis = null;
        int ii = 1;
        while(true) {
              try {
                    zeis = new ZipEntryInputStream(zis);
              } catch (EOFException e) {
                    break;
              } catch (IOException e) {
				fail("IOException thrown when recieving the zip stream" + e);
            	  System.out.println("IOException " + e);
			}
              String unzzipedFile = downloadLocation();
              System.out.println(ii++ + " filename: " + zeis.getName());
              try {
              bis = new BufferedInputStream(zeis);
              byte[] data = new byte[8192];
                         
            	  BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(unzzipedFile + File.separator + zeis.getName()));
            	  int bytesRead = 0;   
            	  while ((bytesRead = (bis.read(data, 0, data.length))) > 0)  {
						bos.write(data, 0, bytesRead);
				  }
				
					bos.flush();
		           bos.close();
			} catch (IOException e) {
				fail("IOException thrown when reading the zip stream " + e);
				System.out.println("IOException " + e);
			}
        }

        try {
			zis.close();
		} catch (IOException e) {
			fail("IOException thrown when closing the zip stream " + e);
			System.out.println("IOException " +e);
		}

        try {
			tclient.destroy();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Remote exception thrown when closing the transer context " + e);
		}
		long end = System.currentTimeMillis();
		System.out.println("Total time get download images is " + (end - start) + " milli seconds");

	}

	/**
	 * Test method for {@link gov.nih.nci.cagrid.ncia.client.NCIACoreServiceClient#retrieveDicomDataBySeriesUID(java.lang.String)}.
	 */
	public void testRetrieveDicomDataBySeriesUID() {
		String seriesInstanceUID = "1.3.6.1.4.1.9328.50.1.8862";
		//seriesInstanceUID = "1.2.840.113704.1.111.4076.1187279953.16";
		//seriesInstanceUID = "1.3.6.1.4.1.9328.50.14.1344";
		//seriesInstanceUID = "1.3.6.1.4.1.9328.50.14.868";
		//seriesInstanceUID = "1.3.6.1.4.1.9328.50.14.3132";
		//seriesInstanceUID ="1.3.6.1.4.1.9328.50.6.555";
		//seriesInstanceUID = "1.3.6.1.4.1.9328.50.14.4052";
		//seriesInstanceUID = "1.3.6.1.4.1.9328.50.4.2";
		seriesInstanceUID = "1.3.6.1.4.1.9328.50.4.44589";
		seriesInstanceUID = "1.2.840.113704.1.111.3372.1187274264.6";
		System.out.println("seriesInstanceUID : " + seriesInstanceUID);
		NCIACoreServiceClient client = null;
		try {
			client = new NCIACoreServiceClient(gridServiceUrl);
		} catch (MalformedURIException e) {
			fail("Malformed URI Exception Thrown");
			System.out.println("MalformURIException " + e);
		} catch (RemoteException e) {
			fail("Remote Exception Thrown");
			System.out.println("RemoteException " + e);
		}
		assertNotNull("Connection with remote grid service could not be opened", client);
		long start = System.currentTimeMillis();
		
		InputStream istream = null;
		TransferServiceContextClient tclient = null;
		try {
			System.out.println("............using transferService to retrieve data.................");
			org.cagrid.transfer.context.stubs.types.TransferServiceContextReference tscr = client.retrieveDicomDataBySeriesUID(seriesInstanceUID);
			
			tclient = new TransferServiceContextClient(tscr.getEndpointReference());

			istream = TransferClientHelper.getData(tclient.getDataTransferDescriptor());
			long end = System.currentTimeMillis();
			System.out.println("Total time get images is " + (end - start) + " milli seconds");
		} catch (MalformedURIException e) {
			System.out.println("MalformedURIException " +e);

			fail("Malformed URI Exception Thrown");
		} catch (RemoteException e) {

			System.out.println("RemoteException ,,,,,,,,,,,,,,,,,,, " +e);
			fail("Remote Exception Thrown");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		assertNotNull("Input stream recieved from transfer service is null", istream);

		if(istream == null){
			System.out.println("istrea is null");
			return;
		}
		 start = System.currentTimeMillis();
        ZipInputStream zis = new ZipInputStream(istream);
        ZipEntryInputStream zeis = null;
        BufferedInputStream bis = null;
        int ii = 1;
        while(true) {
              try {
                    zeis = new ZipEntryInputStream(zis);
              } catch (EOFException e) {
                    break;
              } catch (IOException e) {
				fail("IOException thrown when recieving the zip stream" + e);
            	  System.out.println("IOException " + e);
			}
              String unzzipedFile = downloadLocation();
              System.out.println(ii++ + " filename: " + zeis.getName());

              bis = new BufferedInputStream(zeis);

              byte[] data = new byte[8192];
              int bytesRead = 0;
              try {
            	  BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(unzzipedFile + File.separator + zeis.getName()));

				while ((bytesRead = (bis.read(data, 0, data.length))) > 0)  {
						bos.write(data, 0, bytesRead);
				  }
					bos.flush();
		           bos.close();
			} catch (IOException e) {
				fail("IOException thrown when reading the zip stream " + e);
				System.out.println("IOException " + e);
			}
        }

        try {
			zis.close();
		} catch (IOException e) {
			fail("IOException thrown when closing the zip stream " + e);
			System.out.println("IOException " +e);
		}

        try {
			tclient.destroy();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Remote exception thrown when closing the transer context " + e);
		}
		long end = System.currentTimeMillis();
		System.out.println("Total time get download images is " + (end - start) + " milli seconds");
	
	}

	/**
	 * Test method for {@link gov.nih.nci.cagrid.ncia.client.NCIACoreServiceClient#retrieveDicomDataByStudyUID(java.lang.String)}.
	 */
	public void testRetrieveDicomDataByStudyUID() {		;
		String studyInstanceUID = "1.3.6.1.4.1.9328.50.1.4434"; 
		//studyInstanceUID ="1.3.6.1.4.1.9328.50.1.12324";
		//studyInstanceUID = "1.3.6.1.4.1.9328.50.1.8858";
		//studyInstanceUID = "1.3.6.1.4.1.9328.50.1.12324";
		//studyInstanceUID = "1.3.6.1.4.1.9328.50.1.139";
		//studyInstanceUID = "1.3.6.1.4.1.9328.50.6.3294";
		//studyInstanceUID = "1.2.840.113704.1.111.2408.1187279444.3";
		//studyInstanceUID = "1.3.6.1.4.1.9328.50.45.326662079066250663678557696078244480878";
		studyInstanceUID = "1.3.6.1.4.1.9328.50.45.275881025454183713545354420382217269222";
		
		System.out.println("studyInstanceUID : "+ studyInstanceUID);

		NCIACoreServiceClient client = null;
		try {
			client = new NCIACoreServiceClient(gridServiceUrl);
		} catch (MalformedURIException e) {
			fail("Malformed URI Exception Thrown");
			System.out.println("MalformURIException " + e);
		} catch (RemoteException e) {
			fail("Remote Exception Thrown");
			System.out.println("RemoteException " + e);
		}
		assertNotNull("Connection with remote grid service could not be opened", client);
		long start = System.currentTimeMillis();
		
		InputStream istream = null;
		TransferServiceContextClient tclient = null;
		try {
			System.out.println("............using transferService to retrieve data.................");
			org.cagrid.transfer.context.stubs.types.TransferServiceContextReference tscr = client.retrieveDicomDataByStudyUID(studyInstanceUID);
			
			tclient = new TransferServiceContextClient(tscr.getEndpointReference());

			istream = TransferClientHelper.getData(tclient.getDataTransferDescriptor());
			long end = System.currentTimeMillis();
			System.out.println("Total time get images is " + (end - start) + " milli seconds");
		} catch (MalformedURIException e) {
			System.out.println("MalformedURIException " +e);

			fail("Malformed URI Exception Thrown");
		} catch (RemoteException e) {

			System.out.println("RemoteException ,,,,,,,,,,,,,,,,,,, " +e);
			fail("Remote Exception Thrown");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		assertNotNull("Input stream recieved from transfer service is null", istream);

		if(istream == null){
			System.out.println("istrea is null");
			return;
		}
		start = System.currentTimeMillis();
        ZipInputStream zis = new ZipInputStream(istream);
        ZipEntryInputStream zeis = null;
        BufferedInputStream bis = null;
        int ii = 1;
        while(true) {
              try {
                    zeis = new ZipEntryInputStream(zis);
              } catch (EOFException e) {
                    break;
              } catch (IOException e) {
				fail("IOException thrown when recieving the zip stream" + e);
            	  System.out.println("IOException " + e);
			}
              String unzzipedFile = downloadLocation();
              System.out.println(ii++ + " filename: " + zeis.getName());

              bis = new BufferedInputStream(zeis);

              byte[] data = new byte[8192];
              int bytesRead = 0;
              try {
            	  BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(unzzipedFile + File.separator + zeis.getName()));

				while ((bytesRead = (bis.read(data, 0, data.length))) > 0)  {
						bos.write(data, 0, bytesRead);
				  }
					bos.flush();
		           bos.close();
			} catch (IOException e) {
				fail("IOException thrown when reading the zip stream " + e);
				System.out.println("IOException " + e);
			}
        }

        try {
			zis.close();
		} catch (IOException e) {
			fail("IOException thrown when closing the zip stream " + e);
			System.out.println("IOException " +e);
		}

        try {
			tclient.destroy();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Remote exception thrown when closing the transer context " + e);
		}
		long end = System.currentTimeMillis();
		System.out.println("Total time download images is " + (end - start) + " milli seconds");

	}

	/**
	 * Test method for {@link gov.nih.nci.cagrid.ncia.client.NCIACoreServiceClient#retrieveDicomDataByPatientIds(java.lang.String[])}.
	 */
	public void testRetrieveDicomDataByPatientIds() {
		String fileName = "1.3.6.1.4.1.9328.50.1.0042.zip";
		
		//private patient: 1.3.6.1.4.1.9328.50.3.0015, E09932
		String [] patientArray = new String[1];
		/*patientArray[0] = "1.3.6.1.4.1.9328.50.14.0007";
		patientArray[1] = "SNMCB04";*/
		patientArray[0] = "1.3.6.1.4.1.9328.50.4.0042";		 
		/*patientArray[1] = "1.3.6.1.4.1.9328.50.4.0045";		 
		patientArray[2] = "1.3.6.1.4.1.9328.50.4.0049";		 
		patientArray[3] = "1.3.6.1.4.1.9328.50.4.0052";		 
		patientArray[4] = "1.3.6.1.4.1.9328.50.4.0019";		 
		patientArray[5] = "1.3.6.1.4.1.9328.50.4.0043";		 
		patientArray[6] = "1.3.6.1.4.1.9328.50.4.0080";		 
		patientArray[7] = "1.3.6.1.4.1.9328.50.4.0132";		 
		patientArray[8] = "1.3.6.1.4.1.9328.50.4.0104";		 
		patientArray[9] = "1.3.6.1.4.1.9328.50.4.0123";		 
		patientArray[10] = "1.3.6.1.4.1.9328.50.4.0127";		 
		patientArray[11] = "1.3.6.1.4.1.9328.50.4.0136";	*/	 

		
		/*patientArray[1] = "TCGA-06-0137";	 
		patientArray[1] = "TCGA-06-0147";	 
		patientArray[1] = "TCGA-06-0148"; 
		patientArray[1] = "TCGA-06-0164"; 
		patientArray[1] = "TCGA-06-0173";	 
		patientArray[1] = "TCGA-06-0176";	 
		patientArray[1] = "TCGA-06-0178";
		 */

		NCIACoreServiceClient client = null;
		try {
			client = new NCIACoreServiceClient(gridServiceUrl);
		} catch (MalformedURIException e) {
			fail("Malformed URI Exception Thrown");
			System.out.println("MalformURIException " + e);
		} catch (RemoteException e) {
			fail("Remote Exception Thrown");
			System.out.println("RemoteException " + e);
		}
		assertNotNull("Connection with remote grid service could not be opened", client);
		long start = System.currentTimeMillis();
		
		
		InputStream istream = null;
		TransferServiceContextClient tclient = null;
		try {
			System.out.println("............using transferService to retrieve data.................");
			org.cagrid.transfer.context.stubs.types.TransferServiceContextReference tscr = client.retrieveDicomDataByPatientIds(patientArray);
			tclient = new TransferServiceContextClient(tscr.getEndpointReference());

	        istream = TransferClientHelper.getData(tclient.getDataTransferDescriptor());
			long end = System.currentTimeMillis();
			System.out.println("Total time get images is " + (end - start) + " milli seconds");
		} catch (MalformedURIException e) {
			System.out.println("MalformedURIException " +e);

			fail("Malformed URI Exception Thrown");
		} catch (RemoteException e) {

			System.out.println("RemoteException ,,,,,,,,,,,,,,,,,,, " +e);
			fail("Remote Exception Thrown");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		System.out.println("getting zip input stream.............." + istream);
		assertNotNull("Input stream recieved from transfer service is null", istream);

		if(istream == null){
			System.out.println("istrea is null");
			return;
		}
		start = System.currentTimeMillis();
				
		File f=new File(downloadLocation() + File.separator + fileName); 
		try {
		    OutputStream out=new FileOutputStream(f);
		    byte buf[]=new byte[1024];
		    int len;
		    while((len=istream.read(buf))>0){
		    out.write(buf,0,len);
		    }
		    out.flush();
		    out.close();
		    istream.close();
		} catch (EOFException e) {
			System.out.println("end of file");
            //break;
      } catch (IOException e) {
		fail("IOException thrown when recieving the zip stream" + e);
    	  System.out.println("IOException " + e);
	}		

        try {
			tclient.destroy();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Remote exception thrown when closing the transer context " + e);
		}
		long end = System.currentTimeMillis();
		System.out.println("Total time download images is " + (end - start) + " milli seconds");

	}

	/**
	 * Test method for {@link gov.nih.nci.cagrid.ncia.client.NCIACoreServiceClient#retrieveDicomDataBySeriesUIDs(java.lang.String[])}.
	 */
	public void testRetrieveDicomDataBySeriesUIDs() {
		String localClient = downloadLocation();
		String fileName ="1.2.840.113704.1.111.4076.1187279953.16.zip";
		//String seriesInstanceUID = "1.3.6.1.4.1.9328.50.1.8862";
		//String seriesInstanceUID = "1.2.840.113704.1.111.4076.1187279953.16";
		//seriesInstanceUID = "1.3.6.1.4.1.9328.50.14.1344";
		//seriesInstanceUID = "1.3.6.1.4.1.9328.50.14.868";
		//seriesInstanceUID = "1.3.6.1.4.1.9328.50.14.3132";
		//seriesInstanceUID ="1.3.6.1.4.1.9328.50.6.555";
		//seriesInstanceUID = "1.3.6.1.4.1.9328.50.14.4052";
		//private series: 1.2.840.113619.2.134.1762388187.1967.1102076182.901; 1.2.840.113619.2.134.1762388187.1967.1102076183.45
		String [] seriesArray = new String[4];
		seriesArray[0] = "1.2.840.113704.1.111.4076.1187279953.16";
		seriesArray[1] = "1.3.6.1.4.1.9328.50.14.1344";
		seriesArray[2] = "1.3.6.1.4.1.9328.50.1.8862"; 
		seriesArray[3] = "1.2.840.113619.2.134.1762388187.1967.1102076182.901";

		NCIACoreServiceClient client = null;
		try {
			client = new NCIACoreServiceClient(gridServiceUrl);
		} catch (MalformedURIException e) {
			fail("Malformed URI Exception Thrown");
			System.out.println("MalformURIException " + e);
		} catch (RemoteException e) {
			fail("Remote Exception Thrown");
			System.out.println("RemoteException " + e);
		}
		assertNotNull("Connection with remote grid service could not be opened", client);
		long start = System.currentTimeMillis();
		
		

		
		InputStream istream = null;
		TransferServiceContextClient tclient = null;
		try {
			System.out.println("............using transferService to retrieve data.................");
			org.cagrid.transfer.context.stubs.types.TransferServiceContextReference tscr = client.retrieveDicomDataBySeriesUIDs(seriesArray);
			
			tclient = new TransferServiceContextClient(tscr.getEndpointReference());

			istream = TransferClientHelper.getData(tclient.getDataTransferDescriptor());
			long end = System.currentTimeMillis();
			System.out.println("Total time get images is " + (end - start) + " milli seconds");
		} catch (MalformedURIException e) {
			System.out.println("MalformedURIException " +e);

			fail("Malformed URI Exception Thrown");
		} catch (RemoteException e) {

			System.out.println("RemoteException ,,,,,,,,,,,,,,,,,,, " +e);
			fail("Remote Exception Thrown");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		assertNotNull("Input stream recieved from transfer service is null", istream);

		if(istream == null){
			System.out.println("istrea is null");
			return;
		}
		start = System.currentTimeMillis();
		
		
		File f=new File(localClient + File.separator + fileName); 
		try {
		    OutputStream out=new FileOutputStream(f);
		    byte buf[]=new byte[1024];
		    int len;
		    while((len=istream.read(buf))>0){
		    out.write(buf,0,len);
		    }
		    out.flush();
		    out.close();
		    istream.close();
		} catch (EOFException e) {
			System.out.println("end of file");
            //break;
      } catch (IOException e) {
		fail("IOException thrown when recieving the zip stream" + e);
    	  System.out.println("IOException " + e);
	}
		

        try {
			tclient.destroy();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Remote exception thrown when closing the transer context " + e);
		}
		long end = System.currentTimeMillis();
		System.out.println("Total time download images is " + (end - start) + " milli seconds");
	}

	/**
	 * Test method for {@link gov.nih.nci.cagrid.ncia.client.NCIACoreServiceClient#retrieveDicomDataByStudyUIDs(java.lang.String[])}.
	 */
	public void testRetrieveDicomDataByStudyUIDs() {
		String fileName = "1.3.6.1.4.1.9328.50.1.8858.zip";
		//private study 1.2.124.113532.128.231.96.56.20041202.111945.1331778; 
		//1.2.124.113532.128.231.96.56.20050317.110736.1403277 		 

		String [] studyArray = new String[1];
		//studyArray[0] = "1.3.6.1.4.1.9328.50.45.326662079066250663678557696078244480878";
		//studyArray[0] = "1.3.6.1.4.1.9328.50.45.275881025454183713545354420382217269222";
		//studyArray[0] = "1.3.6.1.4.1.9328.50.45.244356231255766995137159318570729147227";
		//studyArray[0] = "1.3.6.1.4.1.9328.50.45.278844795194298910084884570122397123437";
		//studyArray[0] = "1.3.6.1.4.1.9328.50.45.277258831415090891309462723056931217706";
		//studyArray[0] = "1.3.6.1.4.1.9328.50.45.198999303901323755527019586055500288037";
		studyArray[0] = "1.3.6.1.4.1.9328.50.45.240457512892149075774394662651059696506"; 
		//studyArray[0] = "1.3.6.1.4.1.9328.50.45.326662079066250663678557696078244480878";
		//studyArray[0] = "1.3.6.1.4.1.9328.50.1.8858";
		//studyArray[1] = "1.3.6.1.4.1.9328.50.6.3294";
		//studyArray[2] = "1.3.6.1.4.1.9328.50.1.4434"; 
		//studyArray[3] = "1.3.6.1.4.1.9328.50.6.3294";
		
		NCIACoreServiceClient client = null;
		try {
			client = new NCIACoreServiceClient(gridServiceUrl);
		} catch (MalformedURIException e) {
			fail("Malformed URI Exception Thrown");
			System.out.println("MalformURIException " + e);
		} catch (RemoteException e) {
			fail("Remote Exception Thrown");
			System.out.println("RemoteException " + e);
		}
		assertNotNull("Connection with remote grid service could not be opened", client);
		long start = System.currentTimeMillis();
		
		
		InputStream istream = null;
		TransferServiceContextClient tclient = null;
		try {
			System.out.println("............using transferService to retrieve data.................");
			org.cagrid.transfer.context.stubs.types.TransferServiceContextReference tscr = client.retrieveDicomDataByStudyUIDs(studyArray);
			

			tclient = new TransferServiceContextClient(tscr.getEndpointReference());

			istream = TransferClientHelper.getData(tclient.getDataTransferDescriptor());
			long end = System.currentTimeMillis();
			System.out.println("Total time get images is " + (end - start) + " milli seconds");
		} catch (MalformedURIException e) {
			System.out.println("MalformedURIException " +e);

			fail("Malformed URI Exception Thrown");
		} catch (RemoteException e) {

			System.out.println("RemoteException ,,,,,,,,,,,,,,,,,,, " +e);
			fail("Remote Exception Thrown");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}


		System.out.println("getting zip input stream.............." + istream);
		assertNotNull("Input stream recieved from transfer service is null", istream);

		if(istream == null){
			System.out.println("istrea is null");
			return;
		}
		start = System.currentTimeMillis();
		
		
		File f=new File(downloadLocation() + File.separator + fileName); 
		try {
		    OutputStream out=new FileOutputStream(f);
		    byte buf[]=new byte[1024];
		    int len;
		    while((len=istream.read(buf))>0){
		    out.write(buf,0,len);
		    }
		    out.flush();
		    out.close();
		    istream.close();
		} catch (EOFException e) {
			System.out.println("end of file");
            //break;
      } catch (IOException e) {
		fail("IOException thrown when recieving the zip stream" + e);
    	  System.out.println("IOException " + e);
	}
        try {
			tclient.destroy();
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Remote exception thrown when closing the transer context " + e);
		}
		long end = System.currentTimeMillis();
		System.out.println("Total time download images is " + (end - start) + " milli seconds");
	}
	
	public void testStudy(){
		System.out.println("Test case - Retrieve Study for patientID 1.3.6.1.4.1.9328.50.1.0019");
		String filename = "test/resources/testCase2.xml";
		final CQLQuery fcqlq = this.loadXMLFile(filename);
		long start = System.currentTimeMillis();
		// Execute Query
		CQLQueryResults result = this.connectAndExecuteQuery(fcqlq);

		long end = System.currentTimeMillis();
		System.out.println("Total time to get meta data for testcase 2: " + (end - start) + " milli seconds");
		 start = System.currentTimeMillis();
		if(result != null)	{
			CQLQueryResultsIterator iter2 = new CQLQueryResultsIterator(result);

			System.out.println("printing out result for testcase 1......................");
			// Print Results
			int ii = 1;
			while (iter2.hasNext()) {
				Study obj = (Study)iter2.next();
				if (obj == null) {
					System.out.println("something not right.  obj is null" );
					continue;
				}else{
					System.out.println("Result " + ii++ + ". study instance uid is " + obj.getStudyInstanceUID() + " study description: " + obj.getStudyDescription());
				}
			}
		}else{
			System.out.println("No result found for " + filename);
		}

		 end = System.currentTimeMillis();
		System.out.println("Total time to  print out result for testcase 2: " + (end - start) + " milli seconds");


	}
	
	public void testStudy1(){
		System.out.println("Test case - Retrieve Study for patientid 1.3.6.1.4.1.9328.50.1.0025");
		String filename = "test/resources/testCase2a.xml";
		final CQLQuery fcqlq = this.loadXMLFile(filename);
		long start = System.currentTimeMillis();
		// Execute Query
		CQLQueryResults result = this.connectAndExecuteQuery(fcqlq);
		long end = System.currentTimeMillis();
		
		System.out.println("Total time to get meta data for testcase 2a: " + (end - start) + " milli seconds");
		 start = System.currentTimeMillis();
		if(result != null)	{
			CQLQueryResultsIterator iter2 = new CQLQueryResultsIterator(result);

			System.out.println("printing out result for testcase ......................");
			// Print Results
			int ii = 1;
			while (iter2.hasNext()) {
				Study obj = (Study)iter2.next();
				if (obj == null) {
					System.out.println("something not right.  obj is null" );
					continue;
				}else{
					System.out.println("Result " + ii++ + ". study instance uid is " + obj.getStudyInstanceUID() + " study description: " + obj.getStudyDescription());
				}
			}
		}else{
			System.out.println("No result found for " + filename);
		}

		 end = System.currentTimeMillis();
		System.out.println("Total time to print out result for testcase 2: " + (end - start) + " milli seconds");


	}
	
	public void testStudy2(){
		System.out.println("Test case  - Retrieve Study for study InstanceUID 1.3.6.1.4.1.9328.50.1.12324");
		String filename = "test/resources/testCase2b.xml";
		final CQLQuery fcqlq = this.loadXMLFile(filename);
		long start = System.currentTimeMillis();
		// Execute Query
		CQLQueryResults result = this.connectAndExecuteQuery(fcqlq);

		long end = System.currentTimeMillis();
		System.out.println("Total time to get meta data  for testcase 2b: " + (end - start) + " milli seconds");
		 start = System.currentTimeMillis();
		if(result != null)	{
			CQLQueryResultsIterator iter2 = new CQLQueryResultsIterator(result);

			System.out.println("printing out result for testcase 1......................");
			// Print Results
			int ii = 1;
			while (iter2.hasNext()) {
				Study obj = (Study)iter2.next();
				if (obj == null) {
					System.out.println("something not right.  obj is null" );
					continue;
				}else{
					System.out.println("Result " + ii++ + ". study instance uid is " + obj.getStudyInstanceUID() + " study description: " + obj.getStudyDescription());
				}
			}
		}else{
			System.out.println("No result found for " + filename);
		}

		 end = System.currentTimeMillis();
		System.out.println("Total time to print out result for testcase 2: " + (end - start) + " milli seconds");


	}
	public void testStudyWithORGroup(){
		System.out.println("Test case - Retrieve Study ");
		String filename = "test/resources/testStudy.xml";
		final CQLQuery fcqlq = this.loadXMLFile(filename);
		long start = System.currentTimeMillis();
		// Execute Query
		CQLQueryResults result = this.connectAndExecuteQuery(fcqlq);
		long end = System.currentTimeMillis();
		System.out.println("Total time to get meta data for testcase testStudy: " + (end - start) + " milli seconds");
		 start = System.currentTimeMillis();
		if(result != null)	{
			CQLQueryResultsIterator iter2 = new CQLQueryResultsIterator(result);

			System.out.println("printing out result for testcase 1......................");
			// Print Results
			int ii = 1;
			while (iter2.hasNext()) {
				Study obj = (Study)iter2.next();
				if (obj == null) {
					System.out.println("something not right.  obj is null" );
					continue;
				}else{
					System.out.println("Result " + ii++ + ". study instance uid is " + obj.getStudyInstanceUID() + " study description: " + obj.getStudyDescription());
				}
			}
		}else{
			System.out.println("No result found for " + filename);
		}

		 end = System.currentTimeMillis();
		System.out.println("Total time to print out result for testcase 2b: " + (end - start) + " milli seconds");


	}

	public void testSeries(){
		System.out.println("Test case 3 - retrieving series");
		String filename = "test/resources/testCase3.xml";
		final CQLQuery fcqlq = this.loadXMLFile(filename);
		long start = System.currentTimeMillis();
		// Execute Query
		CQLQueryResults result = this.connectAndExecuteQuery(fcqlq);
		
		long end = System.currentTimeMillis();
		System.out.println("Total time to get meta data result for testcase 3: " + (end - start) + " milli seconds");
		start = System.currentTimeMillis();
		if(result != null)	{
			CQLQueryResultsIterator iter2 = new CQLQueryResultsIterator(result);

			System.out.println("printing out result for testcase 3......................");
			// Print Results
			int ii = 1;
			while (iter2.hasNext()) {
				Series obj = (Series)iter2.next();
				if (obj == null) {
					System.out.println("something not right.  obj is null" );
					continue;
				}else{
					System.out.println("Result " + ii++ + ". series instance uid is " + obj.getSeriesInstanceUID() + " modality: " + obj.getModality());
				}
			}
		}else{
			System.out.println("No result found for " + filename);
		}
		 end = System.currentTimeMillis();
		System.out.println("Total time to  and print out result for testcase 3: " + (end - start) + " milli seconds");

	}

	public void testSeries1(){
		System.out.println("Test case  - retrieve Series given patientid, studyInstanceUID and series InstanceUID");
		String filename = "test/resources/testCase3a.xml";
		final CQLQuery fcqlq = this.loadXMLFile(filename);
		long start = System.currentTimeMillis();
		// Execute Query
		CQLQueryResults result = this.connectAndExecuteQuery(fcqlq);
		long end = System.currentTimeMillis();
		System.out.println("Total time to get meta data for testcase 7: " + (end - start) + " milli seconds");
		start = System.currentTimeMillis();
		if(result != null)	{
			CQLQueryResultsIterator iter2 = new CQLQueryResultsIterator(result);

			System.out.println("printing out result for testcase 7......................");
			// Print Results
			int ii = 1;
			while (iter2.hasNext()) {
				Series obj = (Series)iter2.next();
				if (obj == null) {
					System.out.println("something not right.  obj is null" );
					continue;
				}else{
					System.out.println("Result " + ii++ + ". series instance id is " + obj.getSeriesInstanceUID()+ " modality name: " + obj.getModality());
				}
			}
		}else{
			System.out.println("No result found for " + filename);
		}
		 end = System.currentTimeMillis();
		System.out.println("Total time to print out result for testcase 7: " + (end - start) + " milli seconds");

	}

	public void testSeries2(){
		System.out.println("Test case 10. Trying to retrieve private series");
		String filename = "test/resources/testCase10.xml";
		final CQLQuery fcqlq = this.loadXMLFile(filename);
		long start = System.currentTimeMillis();
		// Execute Query
		CQLQueryResults result = this.connectAndExecuteQuery(fcqlq);
		
		long end = System.currentTimeMillis();
		 start = System.currentTimeMillis();
		System.out.println("Total time to get meta data for testcase 10: " + (end - start) + " milli seconds");
		if(result != null)	{
			CQLQueryResultsIterator iter2 = new CQLQueryResultsIterator(result);

			System.out.println("printing out result for testcase ......................");
			// Print Results
			int ii = 1;
			while (iter2.hasNext()) {
				Series obj = (Series)iter2.next();
				if (obj == null) {
					System.out.println("something not right.  obj is null" );
					continue;
				}else{
					System.out.println("Result " + ii++ + ". series instance id is " + obj.getSeriesInstanceUID()+ " modality name: " + obj.getModality());
				}
			}
		}else{
			System.out.println("No result found for " + filename);
		}
		 end = System.currentTimeMillis();
		System.out.println("Total time to print out result for testcase 1: " + (end - start) + " milli seconds");

	}
	public void testImage(){
		System.out.println("Test case 4 - retrieve Images");
		//String filename = "test/resources/testImage.xml";
		String filename = "test/resources/testCase4.xml";
		final CQLQuery fcqlq = this.loadXMLFile(filename);
		long start = System.currentTimeMillis();
		// Execute Query
		CQLQueryResults result = this.connectAndExecuteQuery(fcqlq);
		long end = System.currentTimeMillis();
		
		System.out.println("Total time to get meta data  for testcase 4: " + (end - start) + " milli seconds");
		start = System.currentTimeMillis();
		if(result != null)	{
			CQLQueryResultsIterator iter2 = new CQLQueryResultsIterator(result);

			System.out.println("printing out result for testcase 4----......................");
			// Print Results
			int ii = 1;
			while (iter2.hasNext()) {
				Image obj = (Image)iter2.next();
				if (obj == null) {
					System.out.println("something not right.  obj is null" );
					continue;
				}else{
					System.out.println("Result " + ii++ + ". Image instance uid is " + obj.getSopInstanceUID() + "\tImage sop class uid " + obj.getSopClassUID());
				}
			}
		}else{
			System.out.println("No result found for " + filename);
		}
		 end = System.currentTimeMillis();
		System.out.println("Total time to print out result for testcase 4: " + (end - start) + " milli seconds");


	}

	public void testImage1(){
		System.out.println("Test case 9 - Retrieve Images for series 1.3.6.1.4.1.9328.50.3.33747. This series is private. We should not get anything back.");
		String filename = "test/resources/testCase9.xml";
		final CQLQuery fcqlq = this.loadXMLFile(filename);
		long start = System.currentTimeMillis();
		// Execute Query
		CQLQueryResults result = this.connectAndExecuteQuery(fcqlq);
		long end = System.currentTimeMillis();
		System.out.println("Total time to get meta data for testcase 1: " + (end - start) + " milli seconds");

		start = System.currentTimeMillis();
		if(result != null)	{
			CQLQueryResultsIterator iter2 = new CQLQueryResultsIterator(result);

			System.out.println("printing out result for testcase 1......................");
			// Print Results
			int ii = 1;
			while (iter2.hasNext()) {
				Image obj = (Image)iter2.next();
				if (obj == null) {
					System.out.println("something not right.  obj is null" );
					continue;
				}else{
					System.out.println("Result " + ii++ + ". Image instance uid is " + obj.getSopInstanceUID() + "\tImage sop class uid " + obj.getSopClassUID());
				}
			}
		}else{
			System.out.println("No result found for " + filename);
		}
		end = System.currentTimeMillis();
		System.out.println("Total time to  print out result for testcase 1: " + (end - start) + " milli seconds");

	}
	
	
	public void testPatient(){
		System.out.println("Test case 1 - Retrieve Patient for project RIDER");
		String filename = "test/resources/testCase1.xml";
		final CQLQuery fcqlq = this.loadXMLFile(filename);
		long start = System.currentTimeMillis();
		// Execute Query
		CQLQueryResults result = this.connectAndExecuteQuery(fcqlq);
		long end = System.currentTimeMillis();
		System.out.println("Total time to get meta data for testcase 1: " + (end - start) + " milli seconds");
		start = System.currentTimeMillis();
		
		if(result != null)	{
			CQLQueryResultsIterator iter2 = new CQLQueryResultsIterator(result);

			System.out.println("printing out result for testcase 1......................");
			// Print Results
			int ii = 1;
			while (iter2.hasNext()) {
				Patient obj = (Patient)iter2.next();
				if (obj == null) {
					System.out.println("something not right.  obj is null" );
					continue;
				}else{
					//System.out.println("patient object is " + obj.toString());
					System.out.println("Result " + ii++ + ". patient id is " + obj.getPatientId() + " patient name: " + obj.getPatientName());
				}
			}
		}else{
			System.out.println("No result found for " + filename);
		}
		end = System.currentTimeMillis();
		System.out.println("Total time to print out result for testcase 1: " + (end - start) + " milli seconds");

	}

	public void testPatient1(){
		System.out.println("Test case 5 - retrieve Patient where patientID is 1.3.6.1.4.1.9328.50.1.0025");
		String filename = "test/resources/testCase5.xml";
		final CQLQuery fcqlq = this.loadXMLFile(filename);
		long start = System.currentTimeMillis();
		// Execute Query
		CQLQueryResults result = this.connectAndExecuteQuery(fcqlq);

		long end = System.currentTimeMillis();
		System.out.println("Total time to get meta data for testcase 5: " + (end - start) + " milli seconds");
		long t = end - start;
		start = System.currentTimeMillis();
		if(result != null)	{
			CQLQueryResultsIterator iter2 = new CQLQueryResultsIterator(result);

			System.out.println("printing out result for testcase 5......................");
			// Print Results
			int ii = 1;
			while (iter2.hasNext()) {
				Patient obj = (Patient)iter2.next();
				if (obj == null) {
					System.out.println("something not right.  obj is null" );
					continue;
				}else{
					System.out.println("Result " + ii++ + ". patient id is " + obj.getPatientId() + " patient name: " + obj.getPatientName());
				}
			}
		}else{
			System.out.println("No result found for " + filename);
		}
		end = System.currentTimeMillis();
		System.out.println("total time to get data is " + t + " ms.");
		System.out.println("Total time to print out result for testcase 5: " + (end - start) + " milli seconds");

	}

	public void testPatient2(){
		System.out.println("Test case 6 - retrieve Patient where project is Phantom and modalities are CT and PT");
		String filename = "test/resources/testCase6.xml";
		final CQLQuery fcqlq = this.loadXMLFile(filename);
		long start = System.currentTimeMillis();
		// Execute Query
		CQLQueryResults result = this.connectAndExecuteQuery(fcqlq);
		long end = System.currentTimeMillis();
		System.out.println("Total time to get meta data result for testcase 6: " + (end - start) + " milli seconds");
		long t = end - start;
		start = System.currentTimeMillis();
		if(result != null)	{
			CQLQueryResultsIterator iter2 = new CQLQueryResultsIterator(result);

			System.out.println("printing out result for testcase 6......................");
			// Print Results
			int ii = 1;
			while (iter2.hasNext()) {
				Patient obj = (Patient)iter2.next();
				if (obj == null) {
					System.out.println("something not right.  obj is null" );
					continue;
				}else{
					System.out.println("Result " + ii++ + ". patient id is " + obj.getPatientId() + " patient name: " + obj.getPatientName());
				}
			}
		}else{
			System.out.println("No result found for " + filename);
		}
		end = System.currentTimeMillis();
		System.out.println("total time to get data is " + t + " ms.");
		System.out.println("Total time to print out result for testcase 6: " + (end - start) + " milli seconds");
		
	}
	
	public void testPatientCaseInSensitive(){
		System.out.println("Test case 1 - Retrieve Patient");
		String filename = "test/resources/testPatientCaseInSensitive.xml";
		final CQLQuery fcqlq = this.loadXMLFile(filename);
		long start = System.currentTimeMillis();
		// Execute Query
		CQLQueryResults result = this.connectAndExecuteQuery(fcqlq);
		long end = System.currentTimeMillis();
		System.out.println("Total time to get meta data  for testcase 1: " + (end - start) + " milli seconds");
		start = System.currentTimeMillis();
		if(result != null)	{
			CQLQueryResultsIterator iter2 = new CQLQueryResultsIterator(result);

			System.out.println("printing out result for testcase 1......................");
			// Print Results
			int ii = 1;
			while (iter2.hasNext()) {
				Patient obj = (Patient)iter2.next();
				if (obj == null) {
					System.out.println("something not right.  obj is null" );
					continue;
				}else{
					//System.out.println("patient object is " + obj.toString());
					System.out.println("Result " + ii++ + ". patient id is " + obj.getPatientId() + " patient name: " + obj.getPatientName());
				}
			}
		}else{
			System.out.println("No result found for " + filename);
		}
		 end = System.currentTimeMillis();
		System.out.println("Total time to print out result for testcase 1: " + (end - start) + " milli seconds");

	}
	public void testPatientWithORGroup(){
		System.out.println("Test case 1 - Retrieve Patient ");
		String filename = "test/resources/testPatient.xml";
		final CQLQuery fcqlq = this.loadXMLFile(filename);
		long start = System.currentTimeMillis();
		// Execute Query
		CQLQueryResults result = this.connectAndExecuteQuery(fcqlq);

		long end = System.currentTimeMillis();
		System.out.println("Total time to get meta data  result for testcase 1: " + (end - start) + " milli seconds");
		 start = System.currentTimeMillis();
		if(result != null)	{
			CQLQueryResultsIterator iter2 = new CQLQueryResultsIterator(result);

			System.out.println("printing out result for testcase 1......................");
			// Print Results
			int ii = 1;
			while (iter2.hasNext()) {
				Patient obj = (Patient)iter2.next();
				if (obj == null) {
					System.out.println("something not right.  obj is null" );
					continue;
				}else{
					//System.out.println("patient object is " + obj.toString());
					System.out.println("Result " + ii++ + ". patient id is " + obj.getPatientId() + " patient name: " + obj.getPatientName());
				}
			}
		}else{
			System.out.println("No result found for " + filename);
		}
		 end = System.currentTimeMillis();
		System.out.println("Total time to print out result for testcase 1: " + (end - start) + " milli seconds");

	}

	public void testEPRname(){
		TransferServiceContextClient tclient = null;
		NCIACoreServiceClient client = null;
		
		String subTarget = gridServiceUrl.substring(gridServiceUrl.indexOf("http://"), gridServiceUrl.indexOf("wsrf"));
		try {
			client = new NCIACoreServiceClient(gridServiceUrl);
		} catch (MalformedURIException e) {
			fail("Malformed URI Exception Thrown");
			System.out.println("MalformURIException " + e);
		} catch (RemoteException e) {
			fail("Remote Exception Thrown");
			System.out.println("RemoteException " + e);
		}
		String filename = "test/resources/testCase8.xml";
		gov.nih.nci.cagrid.cqlquery.CQLQuery newQuery = null;
		try {
			InputSource queryInput = new InputSource(new FileReader(filename));
			newQuery = (CQLQuery) ObjectDeserializer.deserialize(queryInput, CQLQuery.class);
			System.err.println(ObjectSerializer.toString(newQuery,
					new QName("http://CQL.caBIG/1/gov.nih.nci.cagrid.CQLQuery", "CQLQuery")));
		} catch (FileNotFoundException e) {
			System.out.println("File not Found " + e);
			fail("test Query XML file not found");
		} catch (DeserializationException e) {
			fail("test Query XML file could not be deserialized");
			System.out.println("DeserializationException " + e);
		} catch (SerializationException e) {
			fail("test Query XML file could not be serialized");
			System.out.println("SerializationException " + e);
		}
		assertNotNull(newQuery);

		
		try {
			System.out.println("............using transferService to retrieve data.................");
			org.cagrid.transfer.context.stubs.types.TransferServiceContextReference tscr = client.retrieveDicomData(newQuery);
			EndpointReferenceType oldEpr = tscr.getEndpointReference();
			System.out.println("This should point to imaging/wsrf");
			System.out.println(oldEpr.toString());
			String strOldEpr = oldEpr.toString();
			String subOldEpr = strOldEpr.substring(strOldEpr.indexOf("http://"), strOldEpr.indexOf("wsrf"));
			System.out.println("subTarget = " + subTarget + " | subOldEpr = " + subOldEpr);
			if (!subOldEpr.equals(subTarget)){
				fail("the End Point Reference is not the same.  external user cannot access the DICOM data.");
			}
			tclient = new TransferServiceContextClient(oldEpr);

			DataTransferDescriptor tempDD = tclient.getDataTransferDescriptor();
			System.out.println("This should point to imaging/caGridTransfer");
			System.out.println(tempDD.getUrl());		

		} catch (MalformedURIException e) {
			System.out.println("MalformedURIException " +e);
		} catch (RemoteException e) {
			System.out.println("RemoteException ,,,,,,,,,,,,,,,,,,, " +e);
			fail("Remote Exception Thrown");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}	
	
	public void testGetNumberOfStudyTimepointForPatient(){
		NCIACoreServiceClient client = null;
		String patientId = "1.3.6.1.4.1.9328.50.1.0002";
		//patientId = "SNMCB04";
		//patientId = "1.3.6.1.4.1.9328.50.3.0015";
		
		try {
			client = new NCIACoreServiceClient(gridServiceUrl);
			int numberOfStudies = client.getNumberOfStudyTimePointForPatient(patientId);
			System.out.println("number of study time point for  patientid: " + patientId + " is " +numberOfStudies);
		} catch (MalformedURIException e) {
			fail("Malformed URI Exception Thrown");
			System.out.println("MalformURIException " + e);
		} catch (RemoteException e) {
			//fail("Remote Exception Thrown");
			System.out.println("RemoteException " + e);
		}
	}	
	
	/**
	 * Test method for {@link gov.nih.nci.cagrid.ncia.client.NCIACoreServiceClient#getRepresentativeImageBySeries(java.lang.String)}.
	 */
	public void testGetRepresentativeImageBySeries() {
		String seriesInstanceUID = "1.3.6.1.4.1.9328.50.1.8862";
		//seriesInstanceUID = "1.2.840.113704.1.111.4076.1187279953.16";
		//seriesInstanceUID = "1.3.6.1.4.1.9328.50.14.1344";
		//seriesInstanceUID = "1.3.6.1.4.1.9328.50.14.868";
		//seriesInstanceUID = "1.3.6.1.4.1.9328.50.14.3132";
		//seriesInstanceUID ="1.3.6.1.4.1.9328.50.6.555";
		//seriesInstanceUID = "1.3.6.1.4.1.9328.50.14.4052";
		//seriesInstanceUID = "1.3.6.1.4.1.9328.50.4.2";
		//seriesInstanceUID = "1.3.6.1.4.1.9328.50.4.44589";
		//seriesInstanceUID = "1.2.840.113704.1.111.3372.1187274264.6";
		//seriesInstanceUID = "1.2.840.113619.2.99.26.1046702390.711051";
		System.out.println("seriesInstanceUID : " + seriesInstanceUID);
		NCIACoreServiceClient client = null;
		long start = System.currentTimeMillis();
		try {
			client = new NCIACoreServiceClient(gridServiceUrl);
			Image image = client.getRepresentativeImageBySeries(seriesInstanceUID);
			System.out.println("printing result: ");
			System.out.println("sopInstanceUID: " + image.getSopInstanceUID() + "\tinstanceNumber: " + image.getInstanceNumber() + 
					"\t" + image.getAcquisitionDatetime() + image.getAcquisitionTime() + image.getAnatomicRegionSequence() );
		} catch (MalformedURIException e) {
			fail("Malformed URI Exception Thrown");
			System.out.println("MalformURIException " + e);
		} catch (RemoteException e) {
			fail("Remote Exception Thrown" + e);
			System.out.println("RemoteException " + e);
		}
		//assertNotNull("Connection with remote grid service could not be opened", client);
		
		
		
		long end = System.currentTimeMillis();
		System.out.println("Total time get image is " + (end - start) + " milli seconds");
	
	}

	 /////////////////////////////////////////PRIVATE////////////////////////////
	private CQLQuery loadXMLFile(String filename){
		CQLQuery newQuery = null;
		try {
			InputSource queryInput = new InputSource(new FileReader(filename));
			newQuery = (CQLQuery) ObjectDeserializer.deserialize(queryInput, CQLQuery.class);
			System.err.println(ObjectSerializer.toString(newQuery,
					new QName("http://CQL.caBIG/1/gov.nih.nci.cagrid.CQLQuery", "CQLQuery")));
		} catch (FileNotFoundException e) {
			fail("test Query XML file not found " + e);
		} catch (DeserializationException e) {
			fail("test Query XML file could not be deserialized " + e);
		} catch (SerializationException e) {
			fail("test Query XML file could not be serialized " + e);
		}
		assertNotNull(newQuery);
		return newQuery;
	}

	
	private CQLQueryResults connectAndExecuteQuery(CQLQuery cqlQuery) {

		NCIACoreServiceClient nciaClient = null;
		CQLQueryResults result = null;
		try {
			nciaClient = new NCIACoreServiceClient(gridServiceUrl);
		} catch (MalformedURIException e) {
			fail("Malformed URI Exception Thrown" + e);
			e.printStackTrace();
		} catch (RemoteException e) {
			fail("Remote Exception Thrown " + e);
			e.printStackTrace();
		}
		assertNotNull("Connection with remote grid service could not be opened", nciaClient);
		try{
			result = nciaClient.query(cqlQuery);

		} catch (QueryProcessingExceptionType e) {
			System.out.println("exception happened....");
			e.printStackTrace();
			fail("Query could not be processed " + e);
		} catch (MalformedQueryExceptionType e) {
			System.out.println("exception happened============");
			e.printStackTrace();
			fail("Query was malformed " + e);
		} catch (RemoteException e) {
			System.out.println("exception....................");
			e.printStackTrace();
			fail("Remote Exception Thrown when executing query " + e);
		}
			return result;
	}

	private String downloadLocation(){
		String localClient= System.getProperty("java.io.tmpdir") + File.separator + clientDownLoadLocation;
		if(!new File(localClient).exists()){
			new File(localClient).mkdir();
		}
		System.out.println("Local download location: "+localClient);
		return localClient;
	}
}
