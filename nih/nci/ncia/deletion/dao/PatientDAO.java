package gov.nih.nci.ncia.deletion.dao;

import gov.nih.nci.ncia.internaldomain.Patient;
import gov.nih.nci.ncia.exception.DataAccessException;

public interface PatientDAO {

	public void removePatient(Patient patient, String str) throws DataAccessException;
	public int getTotalStudiesInPatient(Integer patientId) throws DataAccessException;
	public boolean checkPatientNeedToBeRemoved(Integer patientId, Integer count) throws DataAccessException;
	public Patient getPatientObject(Integer patientId) throws DataAccessException;

}
