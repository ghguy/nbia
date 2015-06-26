/**
* $Id: Hibernate3DataAccess.java 12195 2010-02-20 00:25:48Z panq $
*
* $Log: not supported by cvs2svn $
* Revision 1.3  2007/08/10 19:48:46  bauerd
* *** empty log message ***
*
* Revision 1.1  2007/08/05 21:44:39  bauerd
* Initial Check in of reorganized components
*
* Revision 1.7  2006/12/14 19:38:08  mccrimms
* modified to support "select *" type criteria based queries
*
* Revision 1.6  2006/09/27 20:46:28  panq
* Reformated with Sun Java Code Style and added a header for holding CVS history.
*
*/
package gov.nih.nci.ncia.db;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


public class Hibernate3DataAccess implements IDataAccess {
    final static Logger logger = Logger.getLogger(Hibernate3DataAccess.class);

    /**
     * Location of hibernate.cfg.xml file. NOTICE: Location should be on the
     * classpath as Hibernate uses #resourceAsStream style lookup for its
     * configuration file. That is place the config file in a Java package - the
     * default location is the default Java package.<br>
     * <br>
     * Examples: <br>
     * <code>CONFIG_FILE_LOCATION = "/hibernate.conf.xml".
     * CONFIG_FILE_LOCATION = "/com/foo/bar/myhiberstuff.conf.xml".</code>
     */
    private static String CONFIG_FILE_LOCATION = "/ncia.hibernate.cfg.xml";
    private static Hibernate3DataAccess instance = null;
    
    private SessionFactory sessionFactory;
    private final ThreadLocal<Session> threadSession = new ThreadLocal<Session>();
    private final ThreadLocal<Transaction> threadTransaction = new ThreadLocal<Transaction> ();
    private Transaction tx;

    private Hibernate3DataAccess() {
        sessionFactory = null;

        Configuration cfg = new Configuration();

        try {
            cfg.configure(CONFIG_FILE_LOCATION);
            sessionFactory = cfg.buildSessionFactory();
        } catch (Exception e) {
            logger.error("%%%% Error Creating SessionFactory %%%%", e);
            e.printStackTrace();
        }
    }

    public static Hibernate3DataAccess getInstance() {
        if (instance == null) {
            instance = new Hibernate3DataAccess();
        }

        return instance;
    }

    public void open() throws Exception {
        Session session = threadSession.get();

        if (session == null) {
        	
            session = sessionFactory.openSession();            
            threadSession.set(session);
        }
    }
    
    public void beginTransaction() throws HibernateException{
    	if(tx == null){
    		tx = threadSession.get().beginTransaction();
    		logger.debug("begin transaction tx is " + tx);
    		threadTransaction.set(tx);
    	}
    }
    
    public void commit(){
    	Transaction tx = threadTransaction.get();
    	try {
            if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()) {
                tx.commit();                
                logger.debug(" commit is called for transaction : " + tx);
            }
            threadTransaction.set(null);
            this.tx = null;
        }
        catch (HibernateException ex) {
            rollback();
            throw ex;
        }
    }
    
    public void rollback() {
        Transaction tx = threadTransaction.get();
        threadTransaction.set(null);
        try {
            if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()) {
                tx.rollback();                
                logger.debug("rollback is called for transaction: " + tx.toString());
            }
        }
        catch (Exception e) {
            logger.error("Exception rollback transaction" + e);
        }
    }

    public void close() throws Exception {
    	commit();
    	Session s = threadSession.get();
    	threadSession.set(null);
        
        if (s != null) {
            s.close();
            logger.debug("Closed database connection");
        }
    }

    public List search(String HQL) throws Exception {
    	 logger.debug("HQL Query:" +HQL);
    	 //System.out.println("HQL Query:" +HQL);
        Query query = threadSession.get().createQuery(HQL);

        return query.list();
    }
    
    public int executeUpdate(String HQL) throws Exception {
   	 logger.debug("HQL Query:" +HQL);
   	 //System.out.println("HQL Query:" +HQL);
       Query query = threadSession.get().createQuery(HQL);

       return query.executeUpdate();
   }
    
    /**
     * Uses the Criteria query API to return all instances of a given persistent class. This is a
     * convenient way of running a "select * from..." query that is not dependant on the table name.
     * @param className
     * @throws Exception
     */
    public List search(Class className) throws Exception {
    	Criteria crit = threadSession.get().createCriteria(className);
    	return crit.list();
    }
    
    /**
     * Uses the Criteria query API to return all instances of a given persistent class. This is a
     * convenient way of running a "select * from..." query that is not dependant on the table name.
     * The method is overloaded here to restrict the number of records returned.
     * @param className
     * @throws Exception
     */
    public List search(Class className, int maxResults) throws Exception {
    	Criteria crit = threadSession.get().createCriteria(className);
    	crit.setMaxResults(maxResults);
    	return crit.list();
    }

    public void store(Object o) {
    	threadSession.get().saveOrUpdate(o);
    }

    public void update(Object o) {
    	threadSession.get().update(o);
    }

    public Object load(Class klass, long id) {
        return threadSession.get().get(klass, id);
    }

    public Object load(Class klass, Integer id) {
        return threadSession.get().get(klass, id);
    }

    public Criteria createCriteria(Class klass) {
        return threadSession.get().createCriteria(klass);
    }

    public Criteria createCriteria(Class klass, String alias) {
        return threadSession.get().createCriteria(klass, alias);
    }
    
    public Query createFilter(Object coll, String filter) {
        return threadSession.get().createFilter(coll, filter);
    }

    public void delete(Object o) {
    	threadSession.get().delete(o);
    }

    public Query createNamedQuery(String name) {
        return threadSession.get().getNamedQuery(name);
    }

    public void flush() {
    	threadSession.get().flush();
    }
}
