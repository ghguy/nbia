package gov.nih.nci.cagrid.ncia.service;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;



public class NCIAQueryFilterTestCase extends TestCase {
	public void testSetGroupName() {
		NCIAQueryFilter nciaQueryFilter = new NCIAQueryFilter();
		nciaQueryFilter.setGroupName("foo");
		//just a place holder to get 1% coverage to make sure cobertura is working
	}
	
	public void testConvertTDPToCQL(){
		NCIAQueryFilter nciaQueryFilter = new NCIAQueryFilter();
		List<gov.nih.nci.ncia.domain.TrialDataProvenance> tdpList = new ArrayList<gov.nih.nci.ncia.domain.TrialDataProvenance>();
		gov.nih.nci.ncia.domain.TrialDataProvenance tdp = null;
		
		tdp = new gov.nih.nci.ncia.domain.TrialDataProvenance();
		tdp.setProject("RIDER");
		tdp.setSiteName("MSKCC");
		tdpList.add(tdp);
		
		tdp = new gov.nih.nci.ncia.domain.TrialDataProvenance();
		tdp.setProject("RIDER");
		tdp.setSiteName("MDACC");
		tdpList.add(tdp);
		tdp = new gov.nih.nci.ncia.domain.TrialDataProvenance();
		tdp.setProject("LIDC");
		tdp.setSiteName("LIDC");
		tdpList.add(tdp);
		tdp = new gov.nih.nci.ncia.domain.TrialDataProvenance();
		tdp.setProject("RIDER");
		tdp.setSiteName("");
		tdpList.add(tdp);
		
		gov.nih.nci.cagrid.cqlquery.Group g = nciaQueryFilter.convertTDPToCQL(tdpList);
		
		assertNotNull("results is not null", g);
		assertTrue("returned expected number of results: " + g.getGroup().length, g.getGroup().length == 4);
		assertTrue("contains RIDER:  " + g.getGroup(0).getAttribute(0).getValue(), g.getGroup(0).getAttribute(0).getValue() == "RIDER");
		
		
	}
}