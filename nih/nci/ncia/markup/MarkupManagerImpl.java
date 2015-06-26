/**
* $Id: MarkupManagerImpl.java 10966 2009-11-18 12:43:48Z kascice $
*
* $Log: not supported by cvs2svn $
* Revision 1.2  2007/10/12 16:21:57  panq
* Removed unused code.
*
*/
package gov.nih.nci.ncia.markup;

import gov.nih.nci.ncia.internaldomain.ImageMarkup;
import gov.nih.nci.ncia.dto.MarkupDTO;
import gov.nih.nci.ncia.internaldomain.GeneralSeries;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class MarkupManagerImpl implements MarkupManager {
    private static Logger logger = Logger.getLogger(MarkupManagerImpl.class);
    private static String CONFIG_FILE_LOCATION = "/ncia.hibernate.cfg.xml";
    SessionFactory sessionFactory = null;

    /**
     * Constructor
     */
    public MarkupManagerImpl() {
        sessionFactory = new Configuration().configure(CONFIG_FILE_LOCATION).buildSessionFactory();
    }


    /* (non-Javadoc)
	 * @see gov.nih.nci.ncia.markup.MarkupManager#getMarkups(gov.nih.nci.ncia.dto.MarkupDTO)
	 */


    public String getMarkups(MarkupDTO dto) {
        List<String> results = null;
        String hql = "select imgmkup.markupContent from ImageMarkup imgmkup where ";
        hql = hql + "imgmkup.seriesInstanceUID = '" + dto.getSeriesUID() + "'";
        String data = null;
        Session session = null;
        session =sessionFactory.openSession();
        try {
            Query query = session.createQuery(hql);
            results = query.list();

            if (results != null && results.size() > 0) {
                data = results.get(0);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        finally {
            session.flush();
            session.close();
        }

        return data;
    }

    /* (non-Javadoc)
	 * @see gov.nih.nci.ncia.markup.MarkupManager#saveMarkup(gov.nih.nci.ncia.dto.MarkupDTO)
	 */
    public void saveMarkup(MarkupDTO dto){
        if (isDuplicate(dto)) {
            updateMarkup(dto);
        }
        else {
            insertMarkup(dto);
        }
     }

    /* (non-Javadoc)
	 * @see gov.nih.nci.ncia.markup.MarkupManager#updateMarkup(gov.nih.nci.ncia.dto.MarkupDTO)
	 */
    public void updateMarkup(MarkupDTO dto){
        Session session =sessionFactory.openSession();
        String hql = "update ImageMarkup set markupContent = :data, submissionDate = :date where seriesInstanceUID = :uid";
        Query query = session.createQuery(hql);
        query.setString("data",dto.getMarkupData());
        query.setString("uid",dto.getSeriesUID());
        query.setDate("date", new java.util.Date());
        int rowCount = query.executeUpdate();
        System.out.println("Rows affected: " + rowCount);
        session.flush();
        session.close();

    }

    /* (non-Javadoc)
	 * @see gov.nih.nci.ncia.markup.MarkupManager#insertMarkup(gov.nih.nci.ncia.dto.MarkupDTO)
	 */
    public void insertMarkup(MarkupDTO dto){
        Session session = null;
        ImageMarkup im = new ImageMarkup();
        GeneralSeries series = new GeneralSeries();
        String hql = "from GeneralSeries as series where ";
        hql += (" series.seriesInstanceUID = '" +
        dto.getSeriesUID().trim() + "' ");
        session =sessionFactory.openSession();
        List retList = session.createQuery(hql).list();
        if (retList != null && retList.size() >0) {
            series = (GeneralSeries) retList.get(0);
        }

        if (series == null) {
            logger.error("!!!!can not get series pk id for series uid:"+dto.getSeriesUID());
        }

        im.setSeries(series);
        im.setLoginName(dto.getUsrLoginName());
        im.setMarkupContent(dto.getMarkupData());
        im.setSeriesInstanceUID(dto.getSeriesUID());
        im.setSubmissionDate(new java.util.Date());

        try {
            session.save(im);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            session.flush();
            session.close();
        }
    }


    /* (non-Javadoc)
	 * @see gov.nih.nci.ncia.markup.MarkupManager#markupExist(gov.nih.nci.ncia.dto.MarkupDTO)
	 */
    public boolean markupExist(MarkupDTO dto) {
        List results = null;
        Session session = null;
        String hql= "select distinct imgmkup.id from ImageMarkup imgmkup where ";
        hql = hql + "imgmkup.seriesInstanceUID = '" + dto.getSeriesUID() + "'";

        session =sessionFactory.openSession();
        try {
            Query query = session.createQuery(hql);
            results = query.list();
            System.out.println("Done get markup ids");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        finally {
            if (session.isOpen()){
                session.flush();
                session.close();
            }
        }

        if (results != null && results.size() > 0) {
            return true;
        }
        else {
        	return false;
        }
    }

    /* (non-Javadoc)
	 * @see gov.nih.nci.ncia.markup.MarkupManager#isDuplicate(gov.nih.nci.ncia.dto.MarkupDTO)
	 */
    public boolean isDuplicate(MarkupDTO dto) {
        List results = null;
        Session session = null;
        String hql= "select distinct imgmkup.id from ImageMarkup imgmkup where ";
        hql = hql + "imgmkup.seriesInstanceUID = '" + dto.getSeriesUID() + "'";
        //hql = hql + " and imgmkup.loginName = '" + dto.getUsrLoginName() + "'";

        session =sessionFactory.openSession();
        try {
            Query query = session.createQuery(hql);
            results = query.list();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        finally {
            if (session.isOpen()){
                session.flush();
                session.close();
            }
        }

        if (results != null && results.size() > 0) {
            return true;
        }
        else {
        	return false;
        }
    }
}
