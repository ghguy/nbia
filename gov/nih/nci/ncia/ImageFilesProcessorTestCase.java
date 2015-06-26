/**
 *
 */
package gov.nih.nci.ncia;

import gov.nih.nci.ncia.domain.Image;
import gov.nih.nci.ncia.domain.TrialDataProvenance;
import gov.nih.nci.ncia.zip.ZippingDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dbunit.PropertiesBasedJdbcDatabaseTester;

/**
 * @author lethai
 *
 */
public class ImageFilesProcessorTestCase extends AbstractDbTestCaseImpl {


	public void testGetImagesFiles() throws Exception{
		String sopInstanceUIDList = "'1.3.6.1.4.1.9328.50.3.170' OR "+
		                            "SOP_INSTANCE_UID = '1.3.6.1.4.1.9328.50.3.171' OR "+
			                        "SOP_INSTANCE_UID = '1.3.6.1.4.1.9328.50.3.172' OR "+
			                        "SOP_INSTANCE_UID = '1.3.6.1.4.1.9328.50.3.173'";

		ImageFilesProcessor imageFilesProcessor = new ImageFilesProcessor();
		Map<String, String> sopToFilePathMap = imageFilesProcessor.getImagesFiles(new StringBuffer(sopInstanceUIDList));
		System.out.println("Size:"+sopToFilePathMap.size());
//		for(String k : sopToFilePathMap.keySet()) {
//			System.out.println(k+"="+sopToFilePathMap.get(k));
//		}

		assertTrue(sopToFilePathMap.size()==4);

		assertTrue(sopToFilePathMap.get("1.3.6.1.4.1.9328.50.3.170.dcm").equals("/usr/local/tomcat-5.5.9/webapps/NCICBIMAGE/documents/1.3.6.1.4.1.9328.50.3.68/https-57684.dcm"));
		assertTrue(sopToFilePathMap.get("1.3.6.1.4.1.9328.50.3.171.dcm").equals("/usr/local/tomcat-5.5.9/webapps/NCICBIMAGE/documents/1.3.6.1.4.1.9328.50.3.68/https-57685.dcm"));
		assertTrue(sopToFilePathMap.get("1.3.6.1.4.1.9328.50.3.172.dcm").equals("/usr/local/tomcat-5.5.9/webapps/NCICBIMAGE/documents/1.3.6.1.4.1.9328.50.3.68/https-57690.dcm"));
		assertTrue(sopToFilePathMap.get("1.3.6.1.4.1.9328.50.3.173.dcm").equals("/usr/local/tomcat-5.5.9/webapps/NCICBIMAGE/documents/1.3.6.1.4.1.9328.50.3.68/https-57691.dcm"));

	}

	public void testGetImagesFilesEmpty() throws Exception {
		String sopInstanceUIDList = "'fred'";

		ImageFilesProcessor imageFilesProcessor = new ImageFilesProcessor();
		Map<String, String> sopToFilePathMap = imageFilesProcessor.getImagesFiles(new StringBuffer(sopInstanceUIDList));

		assertTrue(sopToFilePathMap.size()==0);
	}

	public void testGetImagesFilesByPatientId() throws Exception {
		ImageFilesProcessor imageFilesProcessor = new ImageFilesProcessor();
		Map<String, String> sopToFilePathMap = imageFilesProcessor.getImagesFilesByPatientId("1.3.6.1.4.1.9328.50.3.0022");

		System.out.println("Size:"+sopToFilePathMap.size());
		assertTrue(sopToFilePathMap.size()==124);

		assertTrue(sopToFilePathMap.get("1.3.6.1.4.1.9328.50.3.170.dcm").equals("/usr/local/tomcat-5.5.9/webapps/NCICBIMAGE/documents/1.3.6.1.4.1.9328.50.3.68/https-57684.dcm"));
		assertTrue(sopToFilePathMap.get("1.3.6.1.4.1.9328.50.3.171.dcm").equals("/usr/local/tomcat-5.5.9/webapps/NCICBIMAGE/documents/1.3.6.1.4.1.9328.50.3.68/https-57685.dcm"));
		assertTrue(sopToFilePathMap.get("1.3.6.1.4.1.9328.50.3.172.dcm").equals("/usr/local/tomcat-5.5.9/webapps/NCICBIMAGE/documents/1.3.6.1.4.1.9328.50.3.68/https-57690.dcm"));
		assertTrue(sopToFilePathMap.get("1.3.6.1.4.1.9328.50.3.173.dcm").equals("/usr/local/tomcat-5.5.9/webapps/NCICBIMAGE/documents/1.3.6.1.4.1.9328.50.3.68/https-57691.dcm"));

	}

	public void testGetImagesFilesByStudyInstanceUID() throws Exception{
		ImageFilesProcessor imageFilesProcessor = new ImageFilesProcessor();
		Map<String, String> sopToFilePathMap = imageFilesProcessor.getImagesFilesByStudyInstanceUID("1.3.6.1.4.1.9328.50.3.68");

		System.out.println("Size:"+sopToFilePathMap.size());
		assertTrue(sopToFilePathMap.size()==124);

		assertTrue(sopToFilePathMap.get("1.3.6.1.4.1.9328.50.3.170.dcm").equals("/usr/local/tomcat-5.5.9/webapps/NCICBIMAGE/documents/1.3.6.1.4.1.9328.50.3.68/https-57684.dcm"));
		assertTrue(sopToFilePathMap.get("1.3.6.1.4.1.9328.50.3.171.dcm").equals("/usr/local/tomcat-5.5.9/webapps/NCICBIMAGE/documents/1.3.6.1.4.1.9328.50.3.68/https-57685.dcm"));
		assertTrue(sopToFilePathMap.get("1.3.6.1.4.1.9328.50.3.172.dcm").equals("/usr/local/tomcat-5.5.9/webapps/NCICBIMAGE/documents/1.3.6.1.4.1.9328.50.3.68/https-57690.dcm"));
		assertTrue(sopToFilePathMap.get("1.3.6.1.4.1.9328.50.3.173.dcm").equals("/usr/local/tomcat-5.5.9/webapps/NCICBIMAGE/documents/1.3.6.1.4.1.9328.50.3.68/https-57691.dcm"));

	}

	public void testGetImagesFilesBySeriesInstanceUID() throws Exception{
		ImageFilesProcessor imageFilesProcessor = new ImageFilesProcessor();
		Map<String, String> sopToFilePathMap = imageFilesProcessor.getImagesFilesBySeriesInstanceUID("1.3.6.1.4.1.9328.50.3.195");

		System.out.println("Size:"+sopToFilePathMap.size());
		assertTrue(sopToFilePathMap.size()==139);

		assertTrue(sopToFilePathMap.get("1.3.6.1.4.1.9328.50.3.330.dcm").equals("/usr/local/tomcat-5.5.9/webapps/NCICBIMAGE/documents/1.3.6.1.4.1.9328.50.3.194/https-58000.dcm"));
		assertTrue(sopToFilePathMap.get("1.3.6.1.4.1.9328.50.3.331.dcm").equals("/usr/local/tomcat-5.5.9/webapps/NCICBIMAGE/documents/1.3.6.1.4.1.9328.50.3.194/https-58001.dcm"));
		assertTrue(sopToFilePathMap.get("1.3.6.1.4.1.9328.50.3.332.dcm").equals("/usr/local/tomcat-5.5.9/webapps/NCICBIMAGE/documents/1.3.6.1.4.1.9328.50.3.194/https-58002.dcm"));
		assertTrue(sopToFilePathMap.get("1.3.6.1.4.1.9328.50.3.333.dcm").equals("/usr/local/tomcat-5.5.9/webapps/NCICBIMAGE/documents/1.3.6.1.4.1.9328.50.3.194/https-58003.dcm"));
	}


	public void testGetTDPByPatientId() throws Exception{
		ImageFilesProcessor imageFilesProcessor = new ImageFilesProcessor();
		TrialDataProvenance trialDataProvenance = imageFilesProcessor.getTDPByPatientId("1.3.6.1.4.1.9328.50.3.0022");


		assertTrue(trialDataProvenance.getProject().equals("LIDC"));
		assertTrue(trialDataProvenance.getSiteName().equals("LIDC"));
	}


	public void testGetTDPByStudyInstanceUID() throws Exception{
		ImageFilesProcessor imageFilesProcessor = new ImageFilesProcessor();
		TrialDataProvenance trialDataProvenance = imageFilesProcessor.getTDPByStudyInstanceUID("1.3.6.1.4.1.9328.50.3.68");


		assertTrue(trialDataProvenance.getProject().equals("LIDC"));
		assertTrue(trialDataProvenance.getSiteName().equals("LIDC"));
	}


	public void testGetTDPBySeriesInstanceUID() throws Exception {
		ImageFilesProcessor imageFilesProcessor = new ImageFilesProcessor();
		TrialDataProvenance trialDataProvenance = imageFilesProcessor.getTDPBySeriesInstanceUID("1.3.6.1.4.1.9328.50.3.69");

		assertTrue(trialDataProvenance.getProject().equals("LIDC"));
		assertTrue(trialDataProvenance.getSiteName().equals("LIDC"));
	}



	public void testGetTDPBySopInstanceUID() throws Exception{
		ImageFilesProcessor imageFilesProcessor = new ImageFilesProcessor();
		TrialDataProvenance trialDataProvenance = imageFilesProcessor.getTDPBySopInstanceUID("1.3.6.1.4.1.9328.50.3.193");

		assertTrue(trialDataProvenance.getProject().equals("LIDC"));
		assertTrue(trialDataProvenance.getSiteName().equals("LIDC"));
	}



//	public void testGetTDPByListIds() {
//		ImageFilesProcessor imageFilesProcessor = new ImageFilesProcessor(
//		        System.getProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS),
//		        System.getProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL),
//		        System.getProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME),
//		        System.getProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD));
//
//		List<String> ids = new ArrayList<String>();
//
//		Map<String, TrialDataProvenance> xxxx = imageFilesProcessor.getTDPByListIds(ids,
//				                                                                   "PATIENT_ID");
//
//		System.out.println("Size:"+sopToFilePathMap.size());
//		assertTrue(sopToFilePathMap.size()==139);
//
//		assertTrue(sopToFilePathMap.get("1.3.6.1.4.1.9328.50.3.330.dcm").equals("/usr/local/tomcat-5.5.9/webapps/NCICBIMAGE/documents/1.3.6.1.4.1.9328.50.3.194/https-58000.dcm"));
//		assertTrue(sopToFilePathMap.get("1.3.6.1.4.1.9328.50.3.331.dcm").equals("/usr/local/tomcat-5.5.9/webapps/NCICBIMAGE/documents/1.3.6.1.4.1.9328.50.3.194/https-58001.dcm"));
//		assertTrue(sopToFilePathMap.get("1.3.6.1.4.1.9328.50.3.332.dcm").equals("/usr/local/tomcat-5.5.9/webapps/NCICBIMAGE/documents/1.3.6.1.4.1.9328.50.3.194/https-58002.dcm"));
//		assertTrue(sopToFilePathMap.get("1.3.6.1.4.1.9328.50.3.333.dcm").equals("/usr/local/tomcat-5.5.9/webapps/NCICBIMAGE/documents/1.3.6.1.4.1.9328.50.3.194/https-58003.dcm"));
//	}


	public void testGetImageFilesByPatientIds() throws Exception {
		ImageFilesProcessor imageFilesProcessor = new ImageFilesProcessor();

		List<String> patientIdList = new ArrayList<String>();
		patientIdList.add("1.3.6.1.4.1.9328.50.3.0022");
		patientIdList.add("1.3.6.1.4.1.9328.50.3.0023");
		List<ZippingDTO> zippingDtoList = imageFilesProcessor.getImageFilesByPatientIds(patientIdList);

		assertTrue(zippingDtoList.size()==(139+124));
	}



	public void testGetImageFilesByStudiesUids() throws Exception {
		ImageFilesProcessor imageFilesProcessor = new ImageFilesProcessor();

		List<String> studyIdList = new ArrayList<String>();
		studyIdList.add("1.3.6.1.4.1.9328.50.3.68");
		studyIdList.add("1.3.6.1.4.1.9328.50.3.194");
		List<ZippingDTO> zippingDtoList = imageFilesProcessor.getImageFilesByStudiesUids(studyIdList);

		assertTrue(zippingDtoList.size()==(139+124));
	}

	public void testGetImageFilesBySeriesUids() throws Exception{
		ImageFilesProcessor imageFilesProcessor = new ImageFilesProcessor();

		List<String> seriesIdList = new ArrayList<String>();
		seriesIdList.add("1.3.6.1.4.1.9328.50.3.69");
		seriesIdList.add("1.3.6.1.4.1.9328.50.3.195");
		List<ZippingDTO> zippingDtoList = imageFilesProcessor.getImageFilesBySeriesUids(seriesIdList);

		assertTrue(zippingDtoList.size()==(139+124));
	}

	public void testGetImagesByNthStudyForPatient() throws Exception{
		ImageFilesProcessor imageFilesProcessor = new ImageFilesProcessor();

		List<ZippingDTO> zippingDtoList = imageFilesProcessor.getImagesByNthStudyTimePointForPatient("1.3.6.1.4.1.9328.50.3.0022",
				                                                                            1);

		assertTrue(zippingDtoList.size()==124);

	    zippingDtoList = imageFilesProcessor.getImagesByNthStudyTimePointForPatient("1.3.6.1.4.1.9328.50.3.0022",
                                                                           0);
        //assertNull(zippingDtoList);
	    assertTrue(zippingDtoList.size()==0);

	    zippingDtoList = imageFilesProcessor.getImagesByNthStudyTimePointForPatient("1.3.6.1.4.1.9328.50.3.0022",
                                                                           2);
        //assertNull(zippingDtoList);
	    assertTrue(zippingDtoList.size()==0);
	}

	public void testGetRepresentativeImageBySeries() throws Exception{
		ImageFilesProcessor imageFilesProcessor = new ImageFilesProcessor();

		Image image = imageFilesProcessor.getRepresentativeImageBySeries("1.3.6.1.4.1.9328.50.3.69");
		assertTrue(image != null);
		assertTrue(image.getSopInstanceUID() != null);
	}

    //////////////////////////////PROTECTED/////////////////////////////////

    protected String getDataSetResourceSpec() {
    	return TEST_DB_FLAT_FILE;
    }


    ////////////////////////////////////PRIVATE/////////////////////////////////

    private static final String TEST_DB_FLAT_FILE = "dbunitscripts/patient_1044.xml";
}
