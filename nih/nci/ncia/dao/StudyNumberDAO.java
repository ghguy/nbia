package gov.nih.nci.ncia.dao;

import gov.nih.nci.ncia.dto.StudyNumberDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudyNumberDAO extends AbstractDAO {


	public Map<Integer, StudyNumberDTO> findAllStudyNumbers() {
		String hql =
	    	"select id, patientId, project, seriesNumber, studyNumber from StudyNumber";

        try {
	        dataAccess.open();

	        List<Object[]> resultSetList = (List<Object[]>)dataAccess.search(hql);

	        Map<Integer, StudyNumberDTO> studyNumberMap = new HashMap<Integer, StudyNumberDTO>();


            for (Object[] result : resultSetList) {


                StudyNumberDTO studyNumber = new StudyNumberDTO((Integer) result[0],
                                                                (String) result[1],
                                                                (String) result[2],
                                                                (Integer) result[4],
                                                                (Integer) result[3]);
                studyNumberMap.put(studyNumber.getPatientPkId(), studyNumber);
            }

	        return studyNumberMap;
        }
        catch(Exception e) {
        	e.printStackTrace();
        	throw new RuntimeException(e);
        }
        finally {
        	closeConnection(dataAccess);
        }
	}

	//////////////////////////////////////PRIVATE///////////////////////////////////////////

    /**
     * This should be package constructor called by factory.
     */
    public StudyNumberDAO() {
    }


}
