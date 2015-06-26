package gov.nih.nci.ncia.dao;

import gov.nih.nci.ncia.internaldomain.LoginHistory;

import java.util.Date;

public class LoginHistoryDAO extends AbstractDAO {
    /**
     * Records a user login in the database
     *
     * @throws Exception
     */
    public void recordUserLogin(String ipAddress) throws Exception {

    	try {
    		dataAccess.open();

    		LoginHistory loginHistory = new LoginHistory();
    		loginHistory.setLoginTimestamp(new Date());
    		// Pull the IP address off of the HTTP request
    		loginHistory.setIPAddress(ipAddress);

    		dataAccess.store(loginHistory);
    	}
    	finally {
    		closeConnection(dataAccess);
    	}
    }
}
