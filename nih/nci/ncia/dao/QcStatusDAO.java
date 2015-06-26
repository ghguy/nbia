package gov.nih.nci.ncia.dao;

import gov.nih.nci.ncia.db.DataAccessProxy;
import gov.nih.nci.ncia.db.Hibernate3DataAccess;
import gov.nih.nci.ncia.db.IDataAccess;
import gov.nih.nci.ncia.dto.QcSearchResultDTO;
import gov.nih.nci.ncia.dto.QcStatusHistoryDTO;
import gov.nih.nci.ncia.internaldomain.GeneralSeries;
import gov.nih.nci.ncia.internaldomain.QCStatusHistory;
import gov.nih.nci.ncia.qctool.VisibilityStatus;

//import java.sql.Date;
import java.util.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;


/**
 * This object encapsulates data access pertaining to the submission_history
 * table. Theoretically would contain all data access CRUD operations, but
 * currently only has "find" operations.
 */
public class QcStatusDAO {
	/**
	 * This should be package constructor called by factory.
	 */
	public QcStatusDAO() {
		try {
			dataAccess = (Hibernate3DataAccess) new DataAccessProxy()
					.getInstance(IDataAccess.HIBERNATE3);
		} catch (Exception e) {
			throw new RuntimeException(
					"Could not initialize Hibernate3DataAccess in VerifySubmissionUtil");
		}
	}

	public List<QcSearchResultDTO> findSeries(String[] qcStatus,
			List<String> collectionSites, String[] patients) {
		String selectStmt = "SELECT sh.project," + "sh.site," + "sh.patientId,"
				+ "sh.studyInstanceUID," + "sh.seriesInstanceUID,"
				+ "gs.visibility," + "max(sh.submissionDate)";
		String fromStmt = "FROM GeneralSeries as gs, SubmissionHistory as sh";
		String whereStmt = " WHERE ";
		List<QcSearchResultDTO> searchResultDtos = new ArrayList<QcSearchResultDTO>();

		whereStmt = whereStmt + setStatusCondition(qcStatus)
				+ " and gs.seriesInstanceUID = sh.seriesInstanceUID"
				+ setCollectionCondition(collectionSites)
				+ setPatientCondition(patients)
				+ " GROUP BY sh.seriesInstanceUID ORDER BY col_6_0_";
		try {
			String hql = selectStmt + fromStmt + whereStmt;

			List<Object[]> searchResults = openConnectionToSearch(dataAccess, hql);

			for (Object[] row : searchResults) {
				String collection = (String) row[0];
				String site = (String) row[1];
				String patient = (String) row[2];
				String study = (String) row[3];
				String series = (String) row[4];
				String visibilitySt = (String) row[5];
				Timestamp submissionDate = (Timestamp) row[6];
				// System.out.println("Series=" + series);

				QcSearchResultDTO qcSrDTO = new QcSearchResultDTO(collection,
						site, patient, study, series, new Date(submissionDate
								.getTime()), visibilitySt);
				searchResultDtos.add(qcSrDTO);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			closeConnection(dataAccess);
		}
		return searchResultDtos;
	}

	public List<QcStatusHistoryDTO> findQcStatusHistoryInfo(
			List<String> seriesList) {
		List<QcStatusHistoryDTO> qcsList = new ArrayList<QcStatusHistoryDTO>();
		String selectStmt = "SELECT qsh.historyTimestamp,"
				+ "qsh.seriesInstanceUid," + "qsh.oldValue," + "qsh.newValue,"
				+ "qsh.comment," + "qsh.userId ";
		String fromStmt = "FROM QCStatusHistory as qsh";
		String whereStmt = " WHERE qsh.seriesInstanceUid in (";
		if (seriesList != null) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < seriesList.size(); ++i) {
				if (i == 0) {
					sb.append("'" + seriesList.get(i) + "'");
				} else {
					sb.append(", '" + seriesList.get(i) + "'");
				}
			}
			whereStmt = whereStmt + sb.toString() + ")"
					+ " ORDER BY qsh.seriesInstanceUid,qsh.historyTimestamp";
		}

		try {
			String hql = selectStmt + fromStmt + whereStmt;
			List<Object[]> searchResults = openConnectionToSearch(dataAccess,
					hql);

			for (Object[] row : searchResults) {
				Timestamp historyTimestamp = (Timestamp) row[0];
				String series = (String) row[1];
				String oldValue = (String) row[2];
				String newValue = (String) row[3];
				String comment = (String) row[4];
				String userId = (String) row[5];
				QcStatusHistoryDTO qcshDTO = new QcStatusHistoryDTO(new Date(
						historyTimestamp.getTime()), series, newValue,
						oldValue, comment, userId);
				qcsList.add(qcshDTO);
			}

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			closeConnection(dataAccess);
		}
		return qcsList;

	}

	public void updateQcStatus(List<String> seriesList,
			List<String> statusList, String newStatus, String userName,
			String comment) {
		String seriesId;
		for (int i = 0; i < seriesList.size(); ++i) {
			seriesId = seriesList.get(i);
			updateDb(seriesId, statusList.get(i), newStatus, userName, comment);
		}
	}

	// /////////////////////////////////////PRIVATE//////////////////////////////////////////
	private static Logger logger = Logger.getLogger(QcStatusDAO.class);
	private Hibernate3DataAccess dataAccess;

	/**
	 * Close out the connection to the db. Move this into super class if we ever
	 * set one up so all DAO have this. (and make it protected)
	 */
	private static void closeConnection(Hibernate3DataAccess dataAccess) {
		if (dataAccess != null) {
			try {
				dataAccess.close();
			} catch (Exception e) {
				logger.error("Unable to close database connection ", e);
			}
		}
	}

	/**
	 * Close out the connection to the db. Move this into super class if we ever
	 * set one up so all DAO have this. (and make it protected)
	 */
	private List openConnectionToSearch(Hibernate3DataAccess dataAccess,
			String hql) throws Exception {
		dataAccess.open();
		return dataAccess.search(hql);
	}

	private String setPatientCondition(String[] patients) {
		StringBuffer sb = new StringBuffer();
		if (patients != null) {
			for (int i = 0; i < patients.length; ++i) {
				if (i == 0) {
					sb.append(" and (sh.patientId in ('" + patients[i] + "'");
				} else {
					sb.append(",'" + patients[i] + "'");
				}
			}
			sb.append("))");
		}
		return sb.toString();
	}

	private String setCollectionCondition(List<String> collectionSites) {
		StringBuffer sb = new StringBuffer();
		if ((collectionSites != null) && (collectionSites.size() >= 1)) {
			sb.append(" and (");
			for (int i = 0; i < collectionSites.size(); ++i) {
				String item = (String) collectionSites.get(i);
				String[] collectionSiteNames = item.split("//");

				if (i == 0) {
					sb
							.append(" (sh.project='" + collectionSiteNames[0]
									+ "' and sh.site='"
									+ collectionSiteNames[1] + "')");
				} else {
					sb
							.append(" or (sh.project='"
									+ collectionSiteNames[0]
									+ "' and sh.site='"
									+ collectionSiteNames[1] + "')");
				}
			}
			sb.append(')');
		}
		return sb.toString();
	}

	private String setStatusCondition(String[] qcStatus) {
		StringBuffer sb = new StringBuffer();
		if (qcStatus != null && qcStatus.length > 0) {
			for (int j = 0; j < qcStatus.length; ++j) {
				if (j == 0) {
					sb.append("(gs.visibility='"
							+ VisibilityStatus.stringStatusFactory(qcStatus[j])
									.getNumberValue().intValue() + "'");
				} else {
					sb.append(" or gs.visibility='"
							+ VisibilityStatus.stringStatusFactory(qcStatus[j])
									.getNumberValue().intValue() + "'");
				}
			}
			sb.append(')');
		}
		return sb.toString();
	}

	private void updateDb(String seriesId, String oldStatus, String newStatus,
			String userName, String comment) {
		QCStatusHistory qsh = new QCStatusHistory();
		qsh.setNewValue(newStatus);
		qsh.setHistoryTimestamp(new Date());
		qsh.setOldValue(oldStatus);
		qsh.setSeriesInstanceUid(seriesId);
		qsh.setUserId(userName);
		qsh.setComment(comment);
		try {
			String hql = "select distinct gs from GeneralSeries gs where gs.seriesInstanceUID ='"
					+ seriesId + "'";
			String updateHql=createUpdateCurationTStatement(seriesId);
			List searchResults = openConnectionToSearch(dataAccess, hql);
			if (searchResults != null) {
				GeneralSeries gs = (GeneralSeries) (searchResults.get(0));
				gs.setVisibility(newStatus);
				dataAccess.beginTransaction();
				dataAccess.update(gs);
				dataAccess.executeUpdate(updateHql);
				dataAccess.store(qsh);
			}
		} catch (Exception ex) {
			dataAccess.rollback();
			throw new RuntimeException(ex);
		} finally {
			closeConnection(dataAccess);
		}
	}
	
	private String createUpdateCurationTStatement(String seriesId){
	    String hql = "update GeneralImage set curationTimestamp = current_timestamp() where seriesInstanceUID = '"+seriesId+"'";
	    return hql;
	}
}
