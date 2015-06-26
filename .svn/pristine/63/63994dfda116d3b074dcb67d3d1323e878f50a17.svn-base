package gov.nih.nci.ncia.deletion.dao;

import gov.nih.nci.ncia.internaldomain.GeneralSeries;
import gov.nih.nci.ncia.internaldomain.Patient;
import gov.nih.nci.ncia.internaldomain.Study;
import gov.nih.nci.ncia.exception.DataAccessException;

public interface DeletionAuditTrailDAO {

	public void recordSeries(GeneralSeries series, String userName) throws DataAccessException;
	public void recordStudy(Study study, String userName) throws DataAccessException;
	public void recordPatient(Patient patient, String userName) throws DataAccessException;

}
