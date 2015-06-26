package gov.nih.nci.ncia.dao;

import gov.nih.nci.ncia.internaldomain.DownloadDataHistory;

import java.util.Date;

import org.apache.log4j.Logger;

public class DownloadDataDAO extends AbstractDAO {
    private static Logger logger = Logger.getLogger(DownloadDataDAO.class);


    public void record(String seriesInstanceUid,
    		           String loginName,
    		           String type,
    		           Long size)throws Exception{
    	try{
			dataAccess.open();
			//System.out.println("DownloadDataDAO creating DownloadDataHistory....");
			DownloadDataHistory downloadHistory = new DownloadDataHistory();
	    	downloadHistory.setLoginName(loginName);
	    	downloadHistory.setSeriesInstanceUid(seriesInstanceUid);
	    	downloadHistory.setSize(size);
	    	downloadHistory.setDownloadTimestamp(new Date());
	    	downloadHistory.setType(type);
	        dataAccess.store(downloadHistory);

		}catch(Exception e)	{
			logger.error("Exception recording download history: " + e.getMessage());
			throw e;
		}finally{
			closeConnection(dataAccess);
		}
    }

}
