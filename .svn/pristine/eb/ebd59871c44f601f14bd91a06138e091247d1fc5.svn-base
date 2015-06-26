package gov.nih.nci.ncia.deletion.dao;

import java.util.Date;

import gov.nih.nci.ncia.internaldomain.DeletionAuditTrail;
import gov.nih.nci.ncia.internaldomain.GeneralSeries;
import gov.nih.nci.ncia.internaldomain.Patient;
import gov.nih.nci.ncia.internaldomain.Study;
import gov.nih.nci.ncia.exception.DataAccessException;
import gov.nih.nci.ncia.util.NCIAConstants;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class DeletionAuditTrailDAOImpl extends HibernateDaoSupport implements
		DeletionAuditTrailDAO
{

	public void recordSeries(GeneralSeries series, String userName) throws DataAccessException {
		Session session = getExistingSession();
		DeletionAuditTrail deletionAuditTrail = getDeletionAuditTrail(series, userName);
		session.save(deletionAuditTrail);
	}
	public void recordStudy(Study study, String userName) throws DataAccessException {
		Session session = getExistingSession();
		DeletionAuditTrail deletionAuditTrail = getDeletionAuditTrail(study, userName);
		session.save(deletionAuditTrail);
	}
	public void recordPatient(Patient patient, String userName) throws DataAccessException {
		Session session = getExistingSession();
		DeletionAuditTrail deletionAuditTrail = getDeletionAuditTrail(patient, userName);
		session.save(deletionAuditTrail);
	}

	private Session getExistingSession() throws DataAccessException
	{
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		if (session == null)
		{
			throw new DataAccessException("Cannot obtain the session in DeletionAuditTrailDAOImpl class");
		}

		return session;
	}

	private DeletionAuditTrail getDeletionAuditTrail(Object o, String userName)
	{
		DeletionAuditTrail dat = new DeletionAuditTrail();
		if (o instanceof GeneralSeries)
		{
			GeneralSeries series = (GeneralSeries)o;
			dat.setDataType(NCIAConstants.GENERAL_SERIES_TYPE);
			dat.setDataId(series.getSeriesInstanceUID());
			dat.setTotalImages(series.getGeneralImageCollection().size());
		}
		if(o instanceof Study)
		{
			Study study = (Study)o;
			dat.setDataType(NCIAConstants.STUDY_TYPE);
			dat.setDataId(study.getStudyInstanceUID());
			dat.setTotalImages(null);
		}
		if (o instanceof Patient)
		{
			Patient patient = (Patient)o;
			dat.setDataType(NCIAConstants.PATIENT_TYPE);
			dat.setDataId(patient.getPatientId());
			dat.setTotalImages(null);
		}
		dat.setTimeStamp(new Date());
		if (userName == null)
		{
			dat.setUserName("System");
		}
		else
		{
			dat.setUserName(userName);
		}

		return dat;
	}
}
