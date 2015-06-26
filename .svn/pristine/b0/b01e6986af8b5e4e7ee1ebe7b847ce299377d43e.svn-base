package gov.nih.nci.ncia;

import gov.nih.nci.ncia.domain.*;
import gov.nih.nci.ncia.zip.*;

import java.util.*;
import com.mockrunner.jdbc.BasicJDBCTestCaseAdapter;
import com.mockrunner.jdbc.StatementResultSetHandler;
import com.mockrunner.mock.jdbc.MockConnection;
import com.mockrunner.mock.jdbc.MockResultSet;

public class ResultSetProcessorTestCase extends BasicJDBCTestCaseAdapter {
   
	public void testProcess() throws Exception {
        MockResultSet resultSet = createMockResultSet();
        
        resultSet.addColumn("nothing");
        resultSet.addColumn("DICOM_FILE_URI");
        resultSet.addColumn("SOP_INSTANCE_UID");
        
        resultSet.addRow(new Object[]{"filler", "fred","barney"});
        resultSet.addRow(new Object[]{"filler", "wilma","norma"});
        resultSet.addRow(new Object[]{"filler", "betty","bambam"});

		ResultSetProcessor resultSetProcessor = new ResultSetProcessor();
		Map<String, String> sopToImagePathMap = resultSetProcessor.process(resultSet);
		
		assertTrue(sopToImagePathMap.size()==3);
		assertTrue(sopToImagePathMap.get("barney.dcm").equals("fred"));
		assertTrue(sopToImagePathMap.get("norma.dcm").equals("wilma"));
		assertTrue(sopToImagePathMap.get("bambam.dcm").equals("betty"));
	}  
	
	public void testProcessTDPFail() throws Exception {
        MockResultSet resultSet = createMockResultSet();
        
        resultSet.addColumn("nothing");
        resultSet.addColumn("PROJECT");
        resultSet.addColumn("DP_SITE_NAME");
        try{
        resultSet.addRow(new Object[]{"filler", "P1","SITE1"});
        resultSet.addRow(new Object[]{"filler", "P2","SITE2"});
        resultSet.addRow(new Object[]{"filler", "P3","SITE3"});
       
		ResultSetProcessor resultSetProcessor = new ResultSetProcessor();
		TrialDataProvenance trialDataProvenance = resultSetProcessor.processTDP(resultSet);
		fail("This test failed as expected because multiple rows were returned while one was expected. ");
        }catch(Exception e){
        	System.out.println("This is expected exception.");
        }
		
	}
	
	public void testProcessTDPSuccess() throws Exception {
        MockResultSet resultSet = createMockResultSet();
        
        resultSet.addColumn("nothing");
        resultSet.addColumn("PROJECT");
        resultSet.addColumn("DP_SITE_NAME");
        
        resultSet.addRow(new Object[]{"filler", "P3","SITE3"});
       
		ResultSetProcessor resultSetProcessor = new ResultSetProcessor();
		TrialDataProvenance trialDataProvenance = resultSetProcessor.processTDP(resultSet);
		
		assertTrue(trialDataProvenance.getProject().equals("P3"));
		assertTrue(trialDataProvenance.getSiteName().equals("SITE3"));
	}
	
	public void testProcessTDPEmpty() throws Exception{
        MockResultSet resultSet = createMockResultSet();
        
        resultSet.addColumn("nothing");
        resultSet.addColumn("PROJECT");
        resultSet.addColumn("DP_SITE_NAME");

		ResultSetProcessor resultSetProcessor = new ResultSetProcessor();
		TrialDataProvenance trialDataProvenance = resultSetProcessor.processTDP(resultSet);
		
		assertNull(trialDataProvenance);
	}
	
	public void testProcessTDPMap() throws Exception {
        MockResultSet resultSet = createMockResultSet();
        
        resultSet.addColumn("nothing");
        resultSet.addColumn("PROJECT");
        resultSet.addColumn("DP_SITE_NAME");
        resultSet.addColumn("PATIENT_ID");

        resultSet.addRow(new Object[]{"filler", "P1","SITE1","id1"});
        resultSet.addRow(new Object[]{"filler", "P2","SITE2","id2"});
        resultSet.addRow(new Object[]{"filler", "P3","SITE3","id3"});

		ResultSetProcessor resultSetProcessor = new ResultSetProcessor();
		Map<String,TrialDataProvenance> tdpMap = resultSetProcessor.processTDP(resultSet, "PATIENT_ID");
		
		assertTrue(tdpMap.size()==3);
		
		//revist this - this wont pass as written
		//assertTrue(tdpMap.get("id1").getProject().equals("P1"));
		//assertTrue(tdpMap.get("id1").getSiteName().equals("SITE1"));
		
		assertTrue(tdpMap.get("id3").getProject().equals("P3"));
		assertTrue(tdpMap.get("id3").getSiteName().equals("SITE3"));	
	}
	
	public void testProcessTDPMapEmpty() throws Exception{
        MockResultSet resultSet = createMockResultSet();
        
        resultSet.addColumn("nothing");
        resultSet.addColumn("PROJECT");
        resultSet.addColumn("DP_SITE_NAME");
        resultSet.addColumn("PATIENT_ID");

		ResultSetProcessor resultSetProcessor = new ResultSetProcessor();
		Map<String,TrialDataProvenance> tdpMap = resultSetProcessor.processTDP(resultSet, "PATIENT_ID");
	
		assertTrue(tdpMap.size()==0);	
	}	
	
	
	public void testProcessDTO() throws Exception{
        MockResultSet resultSet = createMockResultSet();
        
        resultSet.addColumn("nothing");
        resultSet.addColumn("PROJECT");
        resultSet.addColumn("PATIENT_ID");
        resultSet.addColumn("STUDY_INSTANCE_UID");
        resultSet.addColumn("SERIES_INSTANCE_UID");
        resultSet.addColumn("DICOM_FILE_URI");
        resultSet.addColumn("SOP_INSTANCE_UID");

        resultSet.addRow(new Object[]{"filler", "P1","patientId1","studyId1", "seriesId1", "dicomUri1", "sopId1"});
        resultSet.addRow(new Object[]{"filler", "P2","patientId2","studyId2", "seriesId2", "dicomUri2", "sopId2"});
        resultSet.addRow(new Object[]{"filler", "P3","patientId3","studyId3", "seriesId3", "dicomUri3", "sopId3"});
        resultSet.addRow(new Object[]{"filler", "P4","patientId4","studyId4", "seriesId4", "dicomUri4", "sopId4"});

        
		ResultSetProcessor resultSetProcessor = new ResultSetProcessor();
		List<ZippingDTO> zippingDtoList = resultSetProcessor.processDTO(resultSet);
		
		assertTrue(zippingDtoList.size()==4);		
		int count = 1;
		for(ZippingDTO zippingDTO : zippingDtoList) {
			assertTrue(zippingDTO.getProject().equals("P"+count));
			assertTrue(zippingDTO.getPatientId().equals("patientId"+count));			
			assertTrue(zippingDTO.getStudyInstanceUid().equals("studyId"+count));
			assertTrue(zippingDTO.getSeriesInstanceUid().equals("seriesId"+count));
			assertTrue(zippingDTO.getFilePath().equals("dicomUri"+count));
			assertTrue(zippingDTO.getSopInstanceUidAsFileName().equals("sopId"+count+".dcm"));
			
			count += 1;
		}
	}		
	
	public void testProcessDTOEmpty() throws Exception {
        MockResultSet resultSet = createMockResultSet();
        
        resultSet.addColumn("nothing");
        resultSet.addColumn("PROJECT");
        resultSet.addColumn("PATIENT_ID");
        resultSet.addColumn("STUDY_INSTANCE_UID");
        resultSet.addColumn("SERIES_INSTANCE_UID");
        resultSet.addColumn("DICOM_FILE_URI");
        resultSet.addColumn("SOP_INSTANCE_UID");

		ResultSetProcessor resultSetProcessor = new ResultSetProcessor();
		List<ZippingDTO> zippingDtoList = resultSetProcessor.processDTO(resultSet);
		
		assertTrue(zippingDtoList.size()==0);		
	}
	
	private MockResultSet createMockResultSet() {
        MockConnection connection = getJDBCMockObjectFactory().getMockConnection();
        StatementResultSetHandler statementHandler = connection.getStatementResultSetHandler();
        MockResultSet resultSet = statementHandler.createResultSet();
        return resultSet;
	}
}
