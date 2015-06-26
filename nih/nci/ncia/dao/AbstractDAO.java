package gov.nih.nci.ncia.dao;

import org.apache.log4j.Logger;

import gov.nih.nci.ncia.db.DataAccessProxy;
import gov.nih.nci.ncia.db.Hibernate3DataAccess;
import gov.nih.nci.ncia.db.IDataAccess;

public class AbstractDAO {
    protected Hibernate3DataAccess dataAccess;

    /**
     * This should be package constructor called by factory.
     */
    protected AbstractDAO() {
        try {
            dataAccess = (Hibernate3DataAccess) new DataAccessProxy().getInstance(IDataAccess.HIBERNATE3);
        }
        catch (Exception e) {
            throw new RuntimeException("Could not initialize Hibernate3DataAccess in AbstractDAO");
        }
    }	

    /**
     * Close out the connection to the db.  Move this into super class if we
     * ever set one up so all DAO have this. (and make it protected)
     */
    protected final static void closeConnection(Hibernate3DataAccess dataAccess) {
       if (dataAccess != null) {
           try {
               dataAccess.close();
           }
           catch(Exception e) {
               logger.error("Unable to close database connection ", e);
           }
       }
    } 
    
    private static Logger logger = Logger.getLogger(AbstractDAO.class);
    
}
