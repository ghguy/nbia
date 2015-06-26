package gov.nih.nci.ncia.query;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import gov.nih.nci.ncia.db.IDataAccess;
import gov.nih.nci.ncia.internaldomain.GeneralImage;
import org.rsna.ctp.stdstages.database.UIDResult;

public class DicomSOPInstanceUIDQuery {

	IDataAccess ida = null;
	Set<String> sopUIDSet;

	public DicomSOPInstanceUIDQuery(IDataAccess ida, Set<String> sopUid)
	{
		this.ida = ida;
		this.sopUIDSet = sopUid;
	}

	public Map<String, UIDResult> getUIDResult()
	{
		Map<String, UIDResult> resultMap = new HashMap<String, UIDResult>();
		UIDResult result = null;
		String[] uidArray = sopUIDSet.toArray(new String[0]);
		for (int i=0; i < uidArray.length; i++)
		{
			result = getindividualUIDResult(uidArray[i]);
			resultMap.put(uidArray[i], result);
		}

		return resultMap;
	}

	private UIDResult getindividualUIDResult(String sopInstanceUID)
	{
		UIDResult uidResult = null;
		Criteria criteria = ida.createCriteria(GeneralImage.class);
    	criteria.setProjection(Projections.property("submissionDate"));
    	criteria.add(Restrictions.eq("SOPInstanceUID",sopInstanceUID));
    	List<Date> result = criteria.list();
    	if (result != null && result.size() > 0)
    	{
    		Date submissionDate = (Date)result.get(0);
    		long datetime = submissionDate.getTime();
    		uidResult = UIDResult.PRESENT(datetime);
    	}
    	if (result == null || result.size() == 0)
    	{
    		uidResult = UIDResult.MISSING();
    	}

		return uidResult;
	}
}
