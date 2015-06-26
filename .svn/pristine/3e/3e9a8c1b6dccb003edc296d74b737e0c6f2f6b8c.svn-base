package gov.nih.nci.ncia.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import gov.nih.nci.ncia.dto.PatientDTO;
import gov.nih.nci.ncia.internaldomain.Patient;

public class PatientDAO extends AbstractDAO {
	 private static Logger logger = Logger.getLogger(PatientDAO.class);
	 
	/**
	 * Fetch Patient Object through patient PK ID
	 * @param pid patient PK id
	 */
	public PatientDTO getPatientById(Integer pid) throws RuntimeException
	{
		PatientDTO pDto = null;
		try
		{
			dataAccess.open();
			Criteria criteria = dataAccess.createCriteria(Patient.class);
			criteria.add(Restrictions.eq("id", pid));
			
			List<Patient> result = criteria.list();
			if (result != null && result.size() > 0)
			{
				Patient patient = result.get(0);
				pDto = new PatientDTO();
				pDto.setProject(patient.getDataProvenance().getProject());
				pDto.setSiteName(patient.getDataProvenance().getDpSiteName());
				
			}
			
		}catch(Exception e)
		{
			logger.error("Exception in PatientDTO: " + e);
			throw new RuntimeException(e.getMessage());
		}finally{
			closeConnection(dataAccess);
		}
		
		
		return pDto; 
	}

}
