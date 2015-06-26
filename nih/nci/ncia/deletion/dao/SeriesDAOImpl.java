package gov.nih.nci.ncia.deletion.dao;

import gov.nih.nci.ncia.internaldomain.GeneralSeries;
import gov.nih.nci.ncia.exception.DataAccessException;
import gov.nih.nci.ncia.util.NCIAConstants;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


public class SeriesDAOImpl extends HibernateDaoSupport implements SeriesDAO{
	private static Logger logger = Logger.getLogger(SeriesDAOImpl.class);

	@Autowired
	private DeletionAuditTrailDAO datd;

	public List<Integer> listAllDeletedSeries()
	throws DataAccessException
	{
		List<Integer> result = null;

		try
		{
			Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
			if (session == null)
			{
				throw new DataAccessException("Cannot fetch Session from Session Facotry in SesiesDAOImpl");
			}
			Criteria criteria = session.createCriteria(GeneralSeries.class);
			criteria.setProjection(Projections.property("id"));

			criteria.add(Restrictions.eq("visibility", NCIAConstants.DELETED_STATUS));

			result = criteria.list();

		}catch (org.springframework.dao.DataAccessException e)
		{
			logger.error("In SeriesDAOImpl, data Access Exception: " + e.getMessage() );
			throw new DataAccessException(e.getMessage());
		}

		return result;
	}

	public void removeSeries(List<Integer> seriesIds, String userName)
	throws DataAccessException
	{
		List<GeneralSeries> seriesList = getSeriesObject(seriesIds);

		Session session = getExistingSession();
		for (GeneralSeries series : seriesList)
		{
			session.delete(series);
			recordSeriesDeletion(series, userName);
		}

	}

	private void recordSeriesDeletion(GeneralSeries series, String userName)
	{
		datd.recordSeries(series, userName);
	}

	public List<GeneralSeries> getSeriesObject(List<Integer> seriesIds)
	throws DataAccessException
	{
		Session session = getExistingSession();

		Criteria criteria = session.createCriteria(GeneralSeries.class);
		criteria.add(Restrictions.in("id", seriesIds));

		List<GeneralSeries> result = criteria.list();

		return result;
	}

	private Session getExistingSession() throws DataAccessException
	{
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		if (session == null)
		{
			throw new DataAccessException("Cannot fetch Session from Session Facotry in SesiesDAOImpl");
		}
		return session;
	}
}
