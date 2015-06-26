package gov.nih.nci.ncia.deletion.dao;

import gov.nih.nci.ncia.internaldomain.GeneralSeries;
import gov.nih.nci.ncia.internaldomain.Study;
import gov.nih.nci.ncia.exception.DataAccessException;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class StudyDAOImpl extends HibernateDaoSupport implements StudyDAO {
	@Autowired
	private DeletionAuditTrailDAO datd;

	public void removeStudy(Study study, String userName) throws DataAccessException
	{
		if (study != null)
		{
			Session session = getExistingSession();
			session.delete(study);
			recordStudyDeletion(study, userName);
		}

	}

	private void recordStudyDeletion(Study study, String userName)
	{
		datd.recordStudy(study, userName);
	}


	public boolean checkStudyNeedToBeRemoved(Integer studyId, Integer count) throws DataAccessException
	{
		boolean needToRemove = false;
		if (studyId == null || count == 0)
		{
			return false;
		}

		//get total studies by a given patient
		int size = getTotalSeriesNumber(studyId) ;
		needToRemove  = (size > 0 ? false : true);

		return needToRemove;
	}

	public Study getStudyObject(Integer studyId)throws DataAccessException
	{
		Study study = null;

		Session session = getExistingSession();
		Criteria criteria = session.createCriteria(Study.class);
		criteria.add(Restrictions.eq("id", studyId));

		List<Study> result = criteria.list();
		if (result != null && result.size() > 0)
		{
			study = result.get(0);
		}
		return study;
	}
	
	public int getTotalSeriesNumber(Integer studyId)throws DataAccessException
	{
		int total = 0;

		Session session = getExistingSession();
		Criteria criteria = session.createCriteria(GeneralSeries.class);
		criteria.add(Restrictions.eq("study.id", studyId));

		List<GeneralSeries> result = criteria.list();
		if (result != null && result.size() > 0)
		{
			total = result.size();
		}
		
		return total;
	}
	private Session getExistingSession()
	{
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		if (session == null)
		{
			throw new DataAccessException("Cannot fetch Session from StudyDAOImpl");
		}

		return session;
	}

}
