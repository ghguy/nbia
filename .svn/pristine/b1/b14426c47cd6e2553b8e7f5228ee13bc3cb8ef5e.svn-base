/**
 * 
 */
package gov.nih.nci.ncia;

import gov.nih.nci.ncia.domain.TrialDataProvenance;
import gov.nih.nci.ncia.zip.ZippingDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @author lethai
 *
 */
public class ResultSetProcessor {
	private static Logger logger = Logger.getLogger(ResultSetProcessor.class);
	
	/**
	 * This method processes the resultset and return a map that contains 
	 * sopInstanceUid key and corresponding image file path
	 * @param rs
	 * @return Map
	 */
	public Map<String, String> process(ResultSet rs) throws Exception{
		Map<String, String> retrievedFileNames = new HashMap<String, String>();
		try {
			while (rs.next()) {
				String imagePath = rs.getString("DICOM_FILE_URI");
				String sop = rs.getString("SOP_INSTANCE_UID");
				retrievedFileNames.put(sop + ".dcm", imagePath);				
			}
		} catch (SQLException e) {
			logger.error("Error getting image paths: " + e);
			throw new Exception("Error getting image filepath", e);
		}
		return retrievedFileNames;
	}
	 
	//this method should return only one record
	public TrialDataProvenance processTDP(ResultSet rs) throws Exception{
		TrialDataProvenance tdp = null;
		int count = 0;
		try {

			while (rs.next()) {
				String project = rs.getString("PROJECT");
				String siteName = rs.getString("DP_SITE_NAME");
				logger.info("project: " + project + " site: " + siteName);
				tdp = new TrialDataProvenance();
				tdp.setProject(project);
				tdp.setSiteName(siteName);
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Error processing resultset to get TrialDataProvenance data", e);
		}
		if(count > 1){
			logger.error("Multiple TrialDataProvenance returned where one is expected");
			throw new Exception("Error: multiple TrialDataProvenance returned where one is expected");
		}
		return tdp;
	}
	
	public Map<String,TrialDataProvenance> processTDP(ResultSet rs, String columnName) throws Exception{
		Map<String, TrialDataProvenance> tdpList = new HashMap<String, TrialDataProvenance>();
		TrialDataProvenance tdp=null;
		try {
			while (rs.next()) {
				String project = rs.getString("PROJECT");
				String siteName = rs.getString("DP_SITE_NAME");
				String id = rs.getString(columnName);
				logger.info("project: " + project + " site: " + siteName);
				tdp = new TrialDataProvenance();
				tdp.setProject(project);
				tdp.setSiteName(siteName);
				tdpList.put(id, tdp);
			}
		} catch (SQLException e) {
			logger.error("Error processing resultset", e);
			throw new Exception("Error processing resultSet", e);			
		}
		
		return tdpList;
	}

	public List<ZippingDTO> processDTO(ResultSet rs) throws Exception{
		List<ZippingDTO> dtoList = new ArrayList<ZippingDTO>();
		int count = 0;
		try {
			while (rs.next()) {
				String project = rs.getString("PROJECT");
				String patientId = rs.getString("PATIENT_ID");
				String studyInstanceUid = rs.getString("STUDY_INSTANCE_UID");
				String seriesInstanceUid = rs.getString("SERIES_INSTANCE_UID");
				String imagePath = rs.getString("DICOM_FILE_URI");
				String sop = rs.getString("SOP_INSTANCE_UID");
				logger.info("project: " + project + " PATIENT: " + patientId);
				ZippingDTO zdto = new ZippingDTO();
				zdto.setProject(project);
				zdto.setFilePath(imagePath);
				zdto.setPatientId(patientId);
				zdto.setSeriesInstanceUid(seriesInstanceUid);
				zdto.setStudyInstanceUid(studyInstanceUid);
				zdto.setSopInstanceUidAsFileName(sop + ".dcm");
				dtoList.add(zdto);
				count++;
			}
		} catch (SQLException e) {
			logger.error("Error processing resultset", e);
			throw new Exception("Error processing resultSet", e);
		}
		logger.debug("result count: " + count);		
		return dtoList;
	}
}
