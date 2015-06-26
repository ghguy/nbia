package gov.nih.nci.ncia.deletion.dao;

import gov.nih.nci.ncia.deletion.ImageDeletionService;
import gov.nih.nci.ncia.internaldomain.Annotation;
import gov.nih.nci.ncia.exception.DataAccessException;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;

import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class AnnotationDAOImpl extends HibernateDaoSupport implements AnnotationDAO{
	private static Logger logger = Logger.getLogger(AnnotationDAOImpl.class);
	private Session session = null;
	private List<String> annotationFilePath = new ArrayList<String>();

	public void deleteAnnotation(List<Integer> seriesIDs, ImageDeletionService ids)
	{
		this.session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		List<Annotation> annotations = getAnnotations(seriesIDs);
		if (annotations != null && annotations.size() > 0)
		{
			processDeletion(annotations);
		}
		ids.setAnnotationFile(annotationFilePath);
	}

	public void processDeletion(List<Annotation> annotations)
	throws DataAccessException
	{
		try
		{
			for (Annotation ann : annotations)
			{
				annotationFilePath.add(ann.getFilePath());
				session.delete(ann);
			}
		}catch (org.springframework.dao.DataAccessException e)
		{
			logger.error("In AnnotationDAOImpl class (processDeletion method)," +
					" data access exception: " + e.getMessage());
			throw new DataAccessException(e.getMessage(), e);
		}
	}

	public List<Annotation> getAnnotations(List<Integer> seriesIds)
	throws DataAccessException
	{
		Criteria criteria = null;
		List<Annotation> result;

		try
		{
			criteria = session.createCriteria(Annotation.class);
			criteria.add(Restrictions.in("generalSeriesPkId", seriesIds));

			result = criteria.list();
		}catch(org.springframework.dao.DataAccessException e)
		{
			logger.error("In annotationDAOImpl class (getAnnotations method)," +
					" data access exception: " + e.getMessage());
			throw new DataAccessException(e.getMessage());
		}

		return result;
	}
}
