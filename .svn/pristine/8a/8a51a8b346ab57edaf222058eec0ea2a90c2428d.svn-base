/**
 * $Id: HibernateDataAccess.java 5544 2008-07-15 16:48:16Z zengje $
 * 
 * $Log: not supported by cvs2svn $
 * Revision 1.13  2007/10/15 13:56:29  lethai
 * clean up
 *
 * Revision 1.12  2007/10/01 17:36:03  lethai
 * Changes for release 3.0
 *
 * Revision 1.11  2007/09/24 14:57:55  lethai
 * Changes for release 3.0
 * Revision 1.10 2007/09/10 18:37:30 lethai
 * 3.0 release
 * 
 * Revision 1.9 2007/09/10 18:26:54 lethai 3.0 release
 * 
 * Revision 1.8 2006/09/28 19:29:00 panq Reformated with Sun Java Code Style and
 * added a header for holding CVS history.
 * 
 */
/*
 * Created on Jul 21, 2005
 * 
 * TODO To change the template for this generated file go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.ncia.db;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


/**
 * @author Jin Chen
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class HibernateDataAccess implements IDataAccess {
    private static Logger logger = Logger.getLogger(HibernateDataAccess.class);
    public static SessionFactory sessionFactory;
    private final ThreadLocal<Session> threadSession = new ThreadLocal();
    private final ThreadLocal<Transaction> threadTransaction = new ThreadLocal();
    private Session session;
    private Transaction tx;

    public HibernateDataAccess()
        throws Exception {
        try {
        	if (sessionFactory == null)
        	{	
        		sessionFactory = new Configuration().configure().buildSessionFactory();
        	}
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            logger.error("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public void open() throws HibernateException {
        session = (Session) threadSession.get();

        if (session == null) {
            session = sessionFactory.openSession();
            logger.info("opensession in open " + session.toString());
            threadSession.set(session);
        }       
    }

    public void beginTransaction() throws HibernateException {

        if (tx == null) {
            tx = session.beginTransaction();            
            threadTransaction.set(tx);
        }        
    }

    
    public void close() throws HibernateException {
        commit();
      
        Session s = threadSession.get();        
        threadSession.set(null);
        if (s != null) {
            s.close();
        }
        
    }

    public Object search(String hql) throws Exception {
        List retList = session.createQuery(hql).list();

        return retList;
    }

    public void store(Object o) throws Exception {        
        session.saveOrUpdate(o);        
    }

    public void commit() {
        Transaction tx = threadTransaction.get();
        
        try {
            if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()) {
                tx.commit();                
                logger.info(" commit is called for transaction : " + tx);
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
                logger.info("rollback is called for transaction: " + tx.toString());
            }
        }
        catch (Exception e) {
            logger.error("Exception rollback transaction" + e);
        }
    }
    
    public Criteria createCriteria(Class klass) {
        return session.createCriteria(klass);
    }    
}
