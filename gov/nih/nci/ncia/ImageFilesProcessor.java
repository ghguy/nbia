/**
 *
 */
package gov.nih.nci.ncia;

import gov.nih.nci.cagrid.cqlquery.CQLQuery;
import gov.nih.nci.cagrid.cqlresultset.CQLQueryResults;
import gov.nih.nci.cagrid.data.utilities.CQLQueryResultsIterator;
import gov.nih.nci.cagrid.ncia.client.NCIACoreServiceClient;
import gov.nih.nci.cagrid.ncia.service.NCIACoreServiceConfiguration;
import gov.nih.nci.ncia.domain.Image;
import gov.nih.nci.ncia.domain.TrialDataProvenance;
import gov.nih.nci.ncia.zip.ZippingDTO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @author lethai
 *
 */
public class ImageFilesProcessor{
	private static Logger logger = Logger.getLogger(ImageFilesProcessor.class);

	private final String SELECT_STATEMENT = "SELECT DICOM_FILE_URI, SOP_INSTANCE_UID FROM GENERAL_IMAGE";
	private final String SELECT_FILE_STATEMENT = "SELECT PROJECT, PATIENT_ID, STUDY_INSTANCE_UID, SERIES_INSTANCE_UID, DICOM_FILE_URI, SOP_INSTANCE_UID FROM GENERAL_IMAGE";
	private final String SELECT_TDP = "SELECT TDP.PROJECT, TDP.DP_SITE_NAME FROM TRIAL_DATA_PROVENANCE TDP ";
	private String gridServiceURL = null;
	private ResultSetProcessor rsp = null;

	public ImageFilesProcessor() throws Exception{
		if (gridServiceURL == null) {
			try {
				gridServiceURL = NCIACoreServiceConfiguration
						.getConfiguration().getGridServiceUrl();
			} catch (Exception e) {
				logger.error("Error getting the grid service url: " + e);
				throw new Exception("Error getting the grid Service url: ", e);
			}
		}
		if(rsp == null){
			rsp = new ResultSetProcessor();
		}

	}


	/**
	 * This method queries the service to get list of Image SOPInstanceUID
	 *
	 * @param cqlQuery
	 * @return StringBuffer
	 */
	public StringBuffer getSOPInstanceUIDList(CQLQuery cqlQuery) throws Exception{
		StringBuffer sbSOPInstanceUIDList = null;
		try {

			// Open a connection and execute our query
			if(this.gridServiceURL == null){
				gridServiceURL = NCIACoreServiceConfiguration
					.getConfiguration().getGridServiceUrl();
			}
			logger.info("gridServiceURL: " + gridServiceURL);

			NCIACoreServiceClient client = new NCIACoreServiceClient(
					gridServiceURL);
			long start = System.currentTimeMillis();
			CQLQueryResults results = client.query(cqlQuery);
			CQLQueryResultsIterator iter = new CQLQueryResultsIterator(results,
					NCIACoreServiceClient.class
							.getResourceAsStream("client-config.wsdd"));
			sbSOPInstanceUIDList = new StringBuffer();
			sbSOPInstanceUIDList.append("'");

			while (iter.hasNext()) {
				Image image = (Image) iter.next();
				sbSOPInstanceUIDList.append(image.getSopInstanceUID());
				if (iter.hasNext()) {
					sbSOPInstanceUIDList.append("' OR SOP_INSTANCE_UID = '");
				}
			}
			sbSOPInstanceUIDList.append("'");
			long end = System.currentTimeMillis();
			logger.info("Total time to get metadata and build the SOPInstanceUIDList is "
							+ (end - start) + " milli seconds.");

			logger.info("sopInstanceUIDList = " + sbSOPInstanceUIDList);

		} catch (Exception e) {
			logger.error("Could not execute CQL against caGRID Data service: ",	e);
			throw new Exception("Couldn't execute CQL against data service", e);
			//return new StringBuffer();
		}
		return sbSOPInstanceUIDList;
	}


	/**
	 * This method queries the database with a list of SOPInstanceUIDs to get
	 * the file path of the Image.
	 *
	 * @param sbSOPInstanceUIDList
	 * @return Map
	 */
	public Map<String, String> getImagesFiles(
			StringBuffer sbSOPInstanceUIDList) throws Exception{
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		Map<String, String> retrievedFileNames = new HashMap<String, String>();
		
		DataAccess da = new DataAccess();
		Connection conn = null;
		try {
			if (sbSOPInstanceUIDList != null
					&& sbSOPInstanceUIDList.length() > 0) {
				long start = System.currentTimeMillis();
				conn = da.getConnection();
				
				stmt = conn.createStatement();

				sql.append(SELECT_STATEMENT + " WHERE SOP_INSTANCE_UID = ");
				sql.append(sbSOPInstanceUIDList);
				logger.info("sql: " + sql.toString());
				rs = stmt.executeQuery(sql.toString());
				retrievedFileNames = rsp.process(rs);
				long end = System.currentTimeMillis();
				logger.info("Total time to get filepath (JDBC) and add to the Hashmap given SOPInstanceUIDList:  "
								+ (end - start) + " milli seconds.");
			} else {
				logger.info("SOPInstanceUIDList is empty");
			}
		} catch (Exception e) {
			logger.error(
					"Could not get file paths for sop instance uid list. SQL statement was: "
							+ sql.toString(), e);
			throw new Exception("Error getting file paths for sop instance uid list", e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				da.closeConnection();
			} catch (SQLException sqle) {
				logger.error("SQLException: ", sqle);

			}
		}
		return retrievedFileNames;
	}

	/**
	 * This method queries the database with a list of SOPInstanceUIDs to get
	 * the file path of the Image.
	 *
	 * @param sbSOPInstanceUIDList
	 * @return HashMap
	 */
	public Map<String, String> getImagesFilesByPatientId(String patientId) throws Exception{
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		Map<String, String> retrievedFileNames = null;
		
		DataAccess da = new DataAccess();
		Connection conn = null;
		try {
			if (patientId != null) {
				long start = System.currentTimeMillis();
				conn = da.getConnection();
				
				stmt = conn.createStatement();

				sql = SELECT_STATEMENT + " WHERE PATIENT_ID = '" + patientId + "'";

				logger.info("sql: " + sql);
				rs = stmt.executeQuery(sql);
				retrievedFileNames = rsp.process(rs);
				long end = System.currentTimeMillis();
				logger.info("Total time to get filepath (JDBC) and add to the Hashmap given patientId: "
								+ (end - start) + " milli seconds.");
			} else {
				logger.info("SOPInstanceUIDList is empty");
			}
		} catch (Exception e) {
			logger.error("Could not get file paths for patientId: " + patientId
					+ " SQL statement: " + sql.toString(), e);
			throw new Exception("Error getting file path for patient: " +patientId , e);
		} finally {
			da.closeConnection();
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				
			} catch (SQLException sqle) {
				logger.error("SQLException: ", sqle);
			}
		}
		return retrievedFileNames;
	}

	/**
	 * Retrieve image file path by studyInstanceUID
	 * @param studyInstanceUID
	 * @return
	 */
	public Map<String, String> getImagesFilesByStudyInstanceUID(
			String studyInstanceUID) throws Exception{
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		Map<String, String> retrievedFileNames = null;
		
		DataAccess da = new DataAccess();
		Connection conn = null;		
		try {
			if (studyInstanceUID != null) {
				long start = System.currentTimeMillis();
				conn = da.getConnection();
			
				stmt = conn.createStatement();
				sql = SELECT_STATEMENT + " WHERE STUDY_INSTANCE_UID = '"
						+ studyInstanceUID + "'";

				logger.info("sql: " + sql.toString());
				rs = stmt.executeQuery(sql.toString());
				retrievedFileNames = rsp.process(rs); //this.processResultSet(rs);
				long end = System.currentTimeMillis();
				logger
						.info("Total time to get filepath (JDBC) and add to the Hashmap given studyInstanceUID: "
								+ (end - start) + " milli seconds.");
			} else {
				logger.info("SOPInstanceUIDList is empty");
			}
		} catch (Exception e) {
			logger
					.error("Could not get file paths for studyInstanaceUID: "
							+ studyInstanceUID + " SQL statement: "
							+ sql.toString(), e);
			throw new Exception("Error getting file path for studyInstanceUID: " + studyInstanceUID, e);
		} finally {
			da.closeConnection();
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException sqle) {
				logger.error("SQLException: ", sqle);
			}
		}
		return retrievedFileNames;
	}

	public Map<String, String> getImagesFilesBySeriesInstanceUID(
			String seriesInstanceUID) throws Exception{
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		Map<String, String> retrievedFileNames = null;
		
		DataAccess da = new DataAccess();
		Connection conn = null;		
		try {
			if (seriesInstanceUID != null) {
				long start = System.currentTimeMillis();
				conn = da.getConnection();
			
				stmt = conn.createStatement();

				sql = SELECT_STATEMENT + " WHERE SERIES_INSTANCE_UID = '"
						+ seriesInstanceUID + "'";

				logger.info("sql: " + sql.toString());
				rs = stmt.executeQuery(sql.toString());
				retrievedFileNames = rsp.process(rs); //this.processResultSet(rs);
				long end = System.currentTimeMillis();
				logger
						.info("Total time to get filepath (JDBC) and add to the Hashmap given seriesInstanceUID: "
								+ (end - start) + " milli seconds.");
			} else {
				logger.info("SeriesInstanceUID is empty");
			}
		} catch (Exception e) {
			logger.error("Could not get file paths for seriesInstanceUID: "
					+ seriesInstanceUID + " SQL statement: " + sql.toString(),
					e);
			throw new Exception("Error getting filepath for seriesInstanceUID: " + seriesInstanceUID, e);
		} finally {
			da.closeConnection();
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException sqle) {
				logger.error("SQLException: ", sqle);
			}
		}
		return retrievedFileNames;
	}

	public TrialDataProvenance getTDPByPatientId(String patientId) throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		TrialDataProvenance tdp = null;
		String sql = "";
		
		DataAccess da = new DataAccess();
		Connection conn = null;		
		try {
			if (patientId != null) {
				long start = System.currentTimeMillis();
				conn = da.getConnection();
				
				stmt = conn.createStatement();

				sql = SELECT_TDP
						+ ", PATIENT P WHERE P.TRIAL_DP_PK_ID = TDP.TRIAL_DP_PK_ID AND P.PATIENT_ID = '"
						+ patientId + "' AND P.VISIBILITY = '1'";

				logger.info("sql: " + sql.toString());
				rs = stmt.executeQuery(sql.toString());
				tdp = rsp.processTDP(rs);
				long end = System.currentTimeMillis();
				logger.info("Total time to get TDP for patientId "
						+ (end - start) + " milli seconds.");
			} else {
				logger.info("patientId is empty");
			}
		} catch (Exception e) {
			logger.error("Could not get TDP for patientId: " + patientId
					+ " SQL statement: " + sql.toString(), e);
			throw new Exception("Error getting TrialDataProvenance info for patient: " + patientId, e);
		} finally {
			da.closeConnection();
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException sqle) {
				logger.error("SQLException: ", sqle);
			}
		}

		return tdp;
	}

	public TrialDataProvenance getTDPByStudyInstanceUID(String studyInstanceUID) throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		TrialDataProvenance tdp = null;
		String sql = "";
		
		DataAccess da = new DataAccess();
		Connection conn = null;		
		try {
			if (studyInstanceUID != null) {
				long start = System.currentTimeMillis();
				conn = da.getConnection();
				
				stmt = conn.createStatement();

				sql = SELECT_TDP
						+ ", PATIENT P, STUDY S WHERE P.TRIAL_DP_PK_ID = TDP.TRIAL_DP_PK_ID AND "
						+ "P.PATIENT_PK_ID = S.PATIENT_PK_ID AND S.STUDY_INSTANCE_UID = '"
						+ studyInstanceUID + "' AND S.VISIBILITY = '1'";

				logger.info("sql: " + sql.toString());
				rs = stmt.executeQuery(sql.toString());
				tdp = rsp.processTDP(rs);
				long end = System.currentTimeMillis();
				logger
						.info("Total time to get TrialDataProvenance given studyInstanceUID: "
								+ (end - start) + " milli seconds.");
			} else {
				logger.info("StudyInstanceUID is empty");
			}
		} catch (Exception e) {
			logger.error(
					"Could not get TrialDataProvenance for studyInstanceUID: "
							+ studyInstanceUID + " SQL statement: "
							+ sql.toString(), e);
			throw new Exception("Error getting TrialDataProvenance for studyInstanceUID: " + studyInstanceUID, e);
		} finally {
			da.closeConnection();
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				//da.closeConnection();
			} catch (SQLException sqle) {
				logger.error("SQLException: ", sqle);
			}
		}

		return tdp;
	}

	public TrialDataProvenance getTDPBySeriesInstanceUID(
			String seriesInstanceUID) throws Exception{
		Statement stmt = null;
		ResultSet rs = null;
		TrialDataProvenance tdp = null;
		String sql = "";
		
		DataAccess da = new DataAccess();
		Connection conn = null;		
		try {
			if (seriesInstanceUID != null) {
				long start = System.currentTimeMillis();
				conn = da.getConnection();
				
				stmt = conn.createStatement();

				sql = SELECT_TDP
						+ ", PATIENT P, STUDY S, GENERAL_SERIES GS WHERE P.TRIAL_DP_PK_ID = TDP.TRIAL_DP_PK_ID AND "
						+ "P.PATIENT_PK_ID = S.PATIENT_PK_ID AND S.STUDY_PK_ID = GS.STUDY_PK_ID AND GS.SERIES_INSTANCE_UID = '"
						+ seriesInstanceUID + "' AND GS.VISIBILITY = '1'";


				logger.debug("sql: " + sql.toString());
				rs = stmt.executeQuery(sql.toString());
				tdp = rsp.processTDP(rs);
				long end = System.currentTimeMillis();
				logger
						.info("Total time to get TrialDataProvenance given studyInstanceUID: "
								+ (end - start) + " milli seconds.");
			} else {
				logger.info("StudyInstanceUID is empty");
			}
		} catch (Exception e) {
			logger.error(
					"Could not get TrialDataProvenance for seriesInstanceUID: "
							+ seriesInstanceUID + " SQL statement: "
							+ sql.toString(), e);
			throw new Exception("Error getting TrialDataProvenance for seriesInstanceUID: " + seriesInstanceUID, e);
		} finally {
			da.closeConnection();
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException sqle) {
				logger.error("SQLException: ", sqle);
			}
		}

		return tdp;
	}

	public TrialDataProvenance getTDPBySopInstanceUID(String sopInstanceUID) throws Exception{
		Statement stmt = null;
		ResultSet rs = null;
		TrialDataProvenance tdp = null;
		String sql = "";
		
		DataAccess da = new DataAccess();
		Connection conn = null;		
		try {
			if (sopInstanceUID != null) {
				long start = System.currentTimeMillis();
				conn = da.getConnection();
				
				stmt = conn.createStatement();

				sql = SELECT_TDP
						+ ", GENERAL_IMAGE GI WHERE GI.TRIAL_DP_PK_ID = TDP.TRIAL_DP_PK_ID AND "
						+ "GI.SOP_INSTANCE_UID = '" + sopInstanceUID + "' AND GI.VISIBILITY = '1'" ;

				logger.info("sql: " + sql.toString());
				rs = stmt.executeQuery(sql.toString());
				tdp = rsp.processTDP(rs);
				long end = System.currentTimeMillis();
				logger
						.info("Total time to get TrialDataProvenance given sopInstanceUID: "
								+ (end - start) + " milli seconds.");
			} else {
				logger.info("StudyInstanceUID is empty");
			}
		} catch (Exception e) {
			logger.error(
					"Could not get TrialDataProvenance for sopInstanceUID: "
							+ sopInstanceUID + " SQL statement: "
							+ sql.toString(), e);
			throw new Exception("Error getting TrialDataProvenance for sopInstanceUID: " + sopInstanceUID);
		} finally {
			da.closeConnection();
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException sqle) {
				logger.error("SQLException: ", sqle);
			}
		}

		return tdp;
	}



	/**
	 * @param ids
	 * @param columnName
	 * @return
	 */
	public Map<String, TrialDataProvenance> getTDPByListIds(List<String> ids, String columnName) throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		Map<String,TrialDataProvenance> tdpList = new HashMap<String, TrialDataProvenance>();
		String sql = "";

		String sb = this.buildWhereClause(ids, columnName).toString();


		if(columnName.equalsIgnoreCase("PATIENT_ID")){
			sql= "SELECT TDP.PROJECT, TDP.DP_SITE_NAME, P.PATIENT_ID FROM TRIAL_DATA_PROVENANCE TDP "
			+ ", PATIENT P WHERE P.TRIAL_DP_PK_ID = TDP.TRIAL_DP_PK_ID AND (P.PATIENT_ID = "
			+ sb + ") AND P.VISIBILITY = '1'";
		}else if(columnName.equalsIgnoreCase("STUDY_INSTANCE_UID")){
			sql = "SELECT TDP.PROJECT, TDP.DP_SITE_NAME, S.STUDY_INSTANCE_UID FROM TRIAL_DATA_PROVENANCE TDP"
			+ ", PATIENT P, STUDY S WHERE P.TRIAL_DP_PK_ID = TDP.TRIAL_DP_PK_ID AND "
			+ "P.PATIENT_PK_ID = S.PATIENT_PK_ID AND ( S.STUDY_INSTANCE_UID = "
			+ sb + " ) AND S.VISIBILITY = '1'";

		}else if(columnName.equalsIgnoreCase("SERIES_INSTANCE_UID")){
			sql ="SELECT TDP.PROJECT, TDP.DP_SITE_NAME, GS.SERIES_INSTANCE_UID FROM TRIAL_DATA_PROVENANCE TDP "
			+ ", PATIENT P, STUDY S, GENERAL_SERIES GS WHERE P.TRIAL_DP_PK_ID = TDP.TRIAL_DP_PK_ID AND "
			+ " P.PATIENT_PK_ID = S.PATIENT_PK_ID AND S.STUDY_PK_ID = GS.STUDY_PK_ID AND (GS.SERIES_INSTANCE_UID = "
			+ sb + ") AND GS.VISIBILITY = '1'";

		}
		
		DataAccess da = new DataAccess();
		Connection conn = null;		
		try {

				long start = System.currentTimeMillis();
				conn = da.getConnection();
				
				stmt = conn.createStatement();


				logger.info("sql: " + sql.toString());
				rs = stmt.executeQuery(sql.toString());
				tdpList = rsp.processTDP(rs, columnName);
				long end = System.currentTimeMillis();
				logger.info("Total time to get TDP for patientId "
						+ (end - start) + " milli seconds.");

		} catch (Exception e) {
			logger.error("Could not get TDP for : " + sql
					+ " SQL statement: " + sql.toString(), e);
			throw new Exception("Error getting TrialDataProvenance for list of UIDs", e);
			//return null;
		} finally {
			da.closeConnection();
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException sqle) {
				logger.error("SQLException: ", sqle);
			}
		}

		return tdpList;
	}

	public List<ZippingDTO> getImageFilesByPatientIds(List<String> patientIds)throws Exception{
		Statement stmt = null;
		ResultSet rs = null;
		List<ZippingDTO> dtoList = null;
		String sql = "";
		String patientList="";
		
		DataAccess da = new DataAccess();
		Connection conn = null;		
		try {
			if (patientIds != null && patientIds.size() >0) {
				long start = System.currentTimeMillis();
				conn = da.getConnection();
				
				stmt = conn.createStatement();
				patientList = this.buildWhereClause(patientIds, "PATIENT_ID").toString();
				sql = SELECT_FILE_STATEMENT
						+ " WHERE PATIENT_ID = "
						+ patientList + " AND VISIBILITY = '1'";

				logger.info("sql: " + sql.toString());
				rs = stmt.executeQuery(sql.toString());
				dtoList = rsp.processDTO(rs); //this.processResultSetDTO(rs);
				long end = System.currentTimeMillis();
				logger.info("Total time to get image filepath for patientId "
						+ (end - start) + " milli seconds.");
			} else {
				logger.info("patientId is empty");
			}
		} catch (Exception e) {
			logger.error("Could not get image filepath for patientId: " + patientList
					+ " SQL statement: " + sql.toString(), e);
			throw new Exception("Error getting image filepath for list of patient ", e);
			//return null;
		} finally {
			da.closeConnection();
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				da.closeConnection();
			} catch (SQLException sqle) {
				logger.error("SQLException: ", sqle);
			}
		}

		return dtoList;
	}

	public List<ZippingDTO> getImageFilesByStudiesUids(List<String> studyInstanceUids) throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		List<ZippingDTO> dtoList = null;
		String sql = "";
		String studyList="";
		
		DataAccess da = new DataAccess();
		Connection conn = null;		
		try {
			if (studyInstanceUids != null && studyInstanceUids.size() >0) {
				long start = System.currentTimeMillis();
				conn = da.getConnection();
				
				stmt = conn.createStatement();
				studyList = this.buildWhereClause(studyInstanceUids, "STUDY_INSTANCE_UID").toString();
				logger.debug("studyList: " + studyList);
				sql = SELECT_FILE_STATEMENT
						+ " WHERE STUDY_INSTANCE_UID = "
						+ studyList + " AND VISIBILITY = '1' ";

				logger.info("sql: " + sql);
				rs = stmt.executeQuery(sql);
				dtoList = rsp.processDTO(rs);
				long end = System.currentTimeMillis();
				logger.info("Total time to get image files for studyUIds "
						+ (end - start) + " milli seconds.");
			} else {
				logger.info("studyUids is empty");
			}
		} catch (Exception e) {
			logger.error("Could not get image file path for list of studyuids: " + studyList
					+ " SQL statement: " + sql.toString(), e);
			throw new Exception("Error getting image filepath for lis tof studyUids", e);
		} finally {
			da.closeConnection();
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				da.closeConnection();
			} catch (SQLException sqle) {
				logger.error("SQLException: ", sqle);
			}
		}

		return dtoList;
	}

	public List<ZippingDTO> getImageFilesBySeriesUids(List<String> seriesInstanceUids) throws Exception{

		Statement stmt = null;
		ResultSet rs = null;
		List<ZippingDTO> dtoList = null;
		String sql = "";
		String seriesList="";
		
		DataAccess da = new DataAccess();
		Connection conn = null;		
		try {
			if (seriesInstanceUids != null && seriesInstanceUids.size() >0) {
				long start = System.currentTimeMillis();
				conn = da.getConnection();
				
				stmt = conn.createStatement();
				seriesList = this.buildWhereClause(seriesInstanceUids, "SERIES_INSTANCE_UID").toString();
				logger.info("seriesList: " + seriesList);
				sql = SELECT_FILE_STATEMENT
						+ " WHERE SERIES_INSTANCE_UID = "
						+ seriesList + " AND VISIBILITY = '1' ";

				logger.info("sql: " + sql);
				rs = stmt.executeQuery(sql);
				dtoList = rsp.processDTO(rs);
				long end = System.currentTimeMillis();
				logger.info("Total time to get image filepath for list of seriesUids "
						+ (end - start) + " milli seconds.");
			} else {
				logger.info("seriesUids is empty");
			}
		} catch (Exception e) {
			logger.error("Could not get image filepath for seriesUids: " + seriesList
					+ " SQL statement: " + sql.toString(), e);
			throw new Exception("Error getting image filepath for list of series Uids",e);
		} finally {
			da.closeConnection();
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				da.closeConnection();
			} catch (SQLException sqle) {
				logger.error("SQLException: ", sqle);
			}
		}
		return dtoList;
	}

	public List<ZippingDTO> getImagesByNthStudyTimePointForPatient(String patientId, int studyTimepoint) throws Exception{
		Statement stmt = null;
		ResultSet rs = null;
		List<ZippingDTO> dtoList = null;
		String sql = "";
		String studyList="";

		//firststep - find out the date for the the studyTimepoint
		List<Date> dateBucket = this.getTimepointStudyForPatient(patientId);
		Date dateForTimepoint = this.getDateForNthTimePoint(dateBucket, studyTimepoint);
		DataAccess da = new DataAccess();
		Connection conn = null;
		try {
			//when timepoint is  out of range or not found, date is null
			if(dateForTimepoint == null){
				return new ArrayList<ZippingDTO>();
			}
			if (patientId != null ) {
				long start = System.currentTimeMillis();
				conn = da.getConnection();
				
				stmt = conn.createStatement();
				sql = "SELECT PROJECT, PATIENT_ID, general_image.STUDY_INSTANCE_UID, SERIES_INSTANCE_UID, DICOM_FILE_URI, SOP_INSTANCE_UID " +
					" FROM GENERAL_IMAGE, study where general_image.study_pk_id = study.study_pk_id and general_image.patient_pk_id = study.patient_pk_id " +
					" and general_image.patient_id = '" + patientId +"' and study.study_date ='" + dateForTimepoint + "' and general_image.visibility = '1' "; //'1.3.6.1.4.1.9328.50.1.0001' and study.study_id='1'


				logger.info("sql: " + sql);
				rs = stmt.executeQuery(sql);
				dtoList = rsp.processDTO(rs);
				long end = System.currentTimeMillis();
				logger.info("Total time to get image filepath for nthstudy timepoint for patientId  " + patientId + " and studyTimepoint" +  ": "
						+ (end - start) + " milli seconds.");
			} else {
				logger.info("patientId is empty");
				return new ArrayList<ZippingDTO>();
			}
		} catch (Exception e) {
			logger.error("Could not get image by nth timepoint for patientId: " + studyList
					+ " SQL statement: " + sql.toString(), e);
			throw new Exception("Error getting image by nth timepoint for patient: " + patientId, e);
		} finally {
			da.closeConnection();
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				//da.closeConnection();
			} catch (SQLException sqle) {
				logger.error("SQLException: ", sqle);
			}
		}

		return dtoList;
	}


	/**
	 * This method return list of study instance uid for given patientId and studyNumber
	 * @param patientId
	 * @param studyNumber
	 * @return
	 */
	public List<Date> getTimepointStudyForPatient(String patientId) throws Exception{

		String sql = "select  study_date " +
		"from study, patient " +
		"where patient.patient_pk_id=study.patient_pk_id and patient.visibility='1'  and patient.patient_id='"+ patientId +
		"' and study_date is not null " +
		"group by study_date " +
		"order by  study_date";

		List<Date> studyDates = new ArrayList<Date>();
		Statement stmt = null;
		ResultSet rs = null;
		
		DataAccess da = new DataAccess();
		Connection conn = null;		
		try {
			if (patientId != null ) {
				long start = System.currentTimeMillis();
				conn = da.getConnection();
				
				stmt = conn.createStatement();

				logger.info("sql: " + sql);
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					Date sd = rs.getDate("study_date");
					logger.info("study_date: " + sd);
					if(sd != null){
						if(!studyDates.contains(sd)){
							studyDates.add(sd);
						}
					}
				}
				if(studyDates.isEmpty()){
					return null;
				}

				long end = System.currentTimeMillis();
				logger.info("Total time to get number of timepoint for patientId:  " + patientId +" : "
						+ (end - start) + " milli seconds.");
			} else {
				logger.info("patientId is empty");
			}
		} catch (Exception e) {
			logger.error("Could not get number of timepoint for patient: " + patientId
					+ " SQL statement: " + sql.toString(), e);
			throw new Exception("Error getting number of timepoint for patient: " + patientId, e);
		} finally {
			da.closeConnection();
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				da.closeConnection();
			} catch (SQLException sqle) {
				logger.error("SQLException: ", sqle);
			}
		}
		return studyDates;
	}

	public Image getRepresentativeImageBySeries(String seriesInstanceUID)throws Exception{

		String sql = "select image_pk_id from general_image where series_instance_uid = '" + seriesInstanceUID + "' order by instance_number, image_pk_id";

		int imagePkId=0;
		List<Integer> imagePkIds = new ArrayList<Integer>();

		Image image = new Image();
		Statement stmt = null;
		ResultSet rs = null;
		
		DataAccess da = new DataAccess();
		Connection conn = null;		
		try {

				long start = System.currentTimeMillis();
				conn = da.getConnection();
				
				stmt = conn.createStatement();

				logger.info("sql: " + sql);
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					imagePkIds.add(rs.getInt("image_pk_id"));
				}
				int totalImages = imagePkIds.size();
				if( totalImages > 0 ){
					int middleValue = totalImages/2;
					imagePkId = imagePkIds.get(middleValue);
				}

				sql = "select gi.acquisition_date, gi.acquisition_datetime, gi.acquisition_matrix, " +
				"gi.acquisition_number,gi.acquisition_time, gi.i_columns, gi.content_date, gi.content_time, " +
				"gi.contrast_bolus_agent,  gi.contrast_bolus_route, gi.focal_spot_size, " +
				"gi.image_orientation_patient, gi.image_position_patient, gi.instance_number, gi.image_type, " +
				"gi.image_comments, gi.image_laterality, gi.lossy_image_compression, gi.patient_position, " +
				"gi.pixel_spacing, gi.i_rows, gi.slice_thickness, gi.slice_location,  gi.sop_class_uid, " +
				"gi.sop_instance_uid,  gi.source_to_detector_distance, gi.source_subject_distance, " +
				"gi.storage_media_file_set_uid, ct.anatomic_region_seq, ct.ct_pitch_factor, ct.convolution_kernel, " +
				"ct.data_collection_diameter,  ct.exposure, ct.exposure_in_microas, ct.exposure_time, ct.kvp, " +
				"ct.gantry_detector_tilt, ct.reconstruction_diameter, ct.revolution_time, ct.scan_options, " +
				"ct.single_collimation_width, ct.table_feed_per_rotation, ct.table_speed, ct.total_collimation_width, " +
				"ct.x_ray_tube_current " +
				"from general_image gi, ct_image ct where gi.image_pk_id= ct.image_pk_id and gi.image_pk_id= " + imagePkId;

				rs = stmt.executeQuery(sql);
				logger.info("sql: " + sql);
				while(rs.next()){
					image.setAcquisitionDate(rs.getDate("acquisition_date"));
					image.setAcquisitionDatetime(rs.getString("acquisition_datetime"));
					image.setAcquisitionMatrix(rs.getDouble("acquisition_matrix"));
					image.setAcquisitionNumber(rs.getInt("acquisition_number"));
					image.setAcquisitionTime(rs.getString("acquisition_time"));
					image.setColumns(rs.getInt("i_columns"));
					image.setContentDate(rs.getDate("content_date"));
					image.setContentTime(rs.getString("content_time"));
					image.setContrastBolusAgent(rs.getString("contrast_bolus_agent"));
					image.setContrastBolusRoute(rs.getString("contrast_bolus_route"));
					image.setFocalSpotSize(rs.getDouble("focal_spot_size"));
					image.setImageOrientationPatient(rs.getString("image_orientation_patient"));
					image.setImagePositionPatient(rs.getString("image_position_patient"));
					image.setInstanceNumber(rs.getInt("instance_number"));
					image.setImageType(rs.getString("image_type"));
					image.setImageComments(rs.getString("image_comments"));
					image.setImageLaterality(rs.getString("image_laterality"));
					image.setLossyImageCompression(rs.getString("lossy_image_compression"));
					image.setPatientPosition(rs.getString("patient_position"));
					image.setPixelSpacing(rs.getDouble("pixel_spacing"));
					image.setRows(rs.getInt("i_rows"));
					image.setSliceThickness(rs.getDouble("slice_thickness"));
					image.setSliceLocation(rs.getDouble("slice_location"));
					image.setSopClassUID(rs.getString("sop_class_uid"));
					image.setSopInstanceUID(rs.getString("sop_instance_uid"));
					image.setSourceToDetectorDistance(rs.getDouble("source_to_detector_distance"));
					image.setSourceSubjectDistance(rs.getDouble("source_subject_distance"));
					image.setStorageMediaFileSetUID(rs.getString("storage_media_file_set_uid"));
					image.setAnatomicRegionSequence(rs.getString("anatomic_region_seq"));
					image.setCtPitchFactor(rs.getInt("ct_pitch_factor"));
					image.setConvolutionKernel(rs.getString("convolution_kernel"));
					image.setDataCollectionDiameter(rs.getDouble("data_collection_diameter"));
					image.setExposure(rs.getInt("exposure"));
					image.setExposureInMicroAs(rs.getInt("exposure_in_microas"));
					image.setExposureTime(rs.getInt("exposure_time"));
					image.setKvp(rs.getDouble("kvp"));
					image.setGantryDetectorTilt(rs.getDouble("gantry_detector_tilt"));
					image.setReconstructionDiameter(rs.getDouble("reconstruction_diameter"));
					image.setRevolutionTime(rs.getInt("revolution_time"));
					image.setScanOptions(rs.getString("scan_options"));
					image.setSingleCollimationWidth(rs.getInt("single_collimation_width"));
					image.setTableFeedPerRotation(rs.getInt("table_feed_per_rotation"));
					image.setTableSpeed(rs.getInt("table_speed"));
					image.setTotalCollimationWidth(rs.getInt("total_collimation_width"));
					image.setXrayTubeCurrent(rs.getInt("x_ray_tube_current"));
				}
				long end = System.currentTimeMillis();
				logger.info("Total time to get Image for  " + seriesInstanceUID +" : "
						+ (end - start) + " milli seconds.");

		} catch (Exception e) {
			logger.error("Could not get Image for seriesInstanceUID " + seriesInstanceUID
					+ " SQL statement: " + sql.toString(), e);
			throw new Exception("Error getting Image for seriesInstanceUID " + seriesInstanceUID, e);
		} finally {
			da.closeConnection();
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				da.closeConnection();
			} catch (SQLException sqle) {
				logger.error("SQLException: ", sqle);
				throw new Exception("Error getting Image for seriesInstanceUID " + seriesInstanceUID, sqle);
			}
		}
		return image;
	}
	/* go through loop of dateTimepoint, find the value for the nthTimepoint*/
	private Date getDateForNthTimePoint(List<Date> dateTimepoint, int nthTimepoint){
		Date d= null;
		try{

			logger.info("before sorting....");
			for(int i=0; i<dateTimepoint.size(); i++){
				logger.info("i: " + i + " date: " + dateTimepoint.get(i));
			}
			Collections.sort(dateTimepoint);

			logger.info("after sorting....");
			for(int i=0; i<dateTimepoint.size(); i++){
				logger.info("i: " + i + " date: " + dateTimepoint.get(i));
			}
			if(nthTimepoint > 0 && nthTimepoint <= dateTimepoint.size()){
				d = dateTimepoint.get(nthTimepoint - 1);
			}
		}catch(Exception e){
			logger.error("Error getting Date the nth timepoint", e);

		}
		logger.info("date for " + nthTimepoint + " is " + d);
		return d;
	}
	private static StringBuffer buildWhereClause(List<String> ids, String attribute){
		StringBuffer idList = new StringBuffer();
		idList.append("'");

		Iterator<String> iter = ids.iterator();

		while (iter.hasNext()) {
			String id = iter.next();
			idList.append(id);
			if (iter.hasNext()) {
				idList.append("' OR " + attribute + " = '");
			}
		}
		idList.append("'");

		logger.info("build where clause: " + idList.toString());
		return idList;
	}

}
