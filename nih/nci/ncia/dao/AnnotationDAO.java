package gov.nih.nci.ncia.dao;

import gov.nih.nci.ncia.internaldomain.Annotation;
import gov.nih.nci.ncia.dto.AnnotationDTO;
import gov.nih.nci.ncia.dto.AnnotationFileDTO;
import gov.nih.nci.ncia.util.HqlUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class AnnotationDAO extends AbstractDAO {


    public Map<Integer, List<AnnotationFileDTO>> findKeyedAnnotationBySeriesPkId(List<Integer> seriesIds) throws Exception {
        Map<Integer, List<AnnotationFileDTO>> returnMap = new HashMap<Integer, List<AnnotationFileDTO>>();

        if (seriesIds.isEmpty()) {
            return returnMap;
        }

        List<AnnotationFileDTO> annotations = findAnnotationBySeriesPkId(seriesIds);

        for(AnnotationFileDTO dbars : annotations) {

            List<AnnotationFileDTO> annotsForSeries = returnMap.get(dbars.getSeriesPkId());

            if (annotsForSeries != null) {
                annotsForSeries.add(dbars);
            } else {
                annotsForSeries = new ArrayList<AnnotationFileDTO>();
                annotsForSeries.add(dbars);
                returnMap.put(dbars.getSeriesPkId(), annotsForSeries);
            }
        }

        return returnMap;
    }

	public List<AnnotationFileDTO> findAnnotationBySeriesPkId(Collection<Integer> seriesPkIds){
        String selectStmt = SQL_QUERY_SELECT;
        String fromStmt = SQL_QUERY_FROM;

        // Build the where clause based on the critiera
        String whereStmt = HqlUtils.buildInClauseUsingIntegers(SQL_QUERY_WHERE +"annot.generalSeriesPkId IN ",
                                                               seriesPkIds);

        // Get handle to IDataAccess
        List<AnnotationFileDTO> returnValue = new ArrayList<AnnotationFileDTO>();
        try {
	        dataAccess.open();

	        // Run the query
	        long start = System.currentTimeMillis();
	        List resultsData = dataAccess.search(selectStmt + fromStmt + whereStmt);
	        logger.info("Ran this query to retrieve annotations for series: " +
	            selectStmt + fromStmt + whereStmt);

	        long end = System.currentTimeMillis();
	        logger.debug("total query time: " + (end - start) + " ms");

	        // Map the rows retrieved from hibernate to the DataBasketResultSet objects.
	        for (Object item : resultsData) {
	            Object[] row = (Object[]) item;
	            AnnotationFileDTO dbars = new AnnotationFileDTO((Integer)row[0],
	            		                                        row[1].toString(),
          	            		                                (Integer) row[2]);
	            // Add to the list to return
	            returnValue.add(dbars);
	        }
	        return returnValue;
        }
        catch(Exception e) {
        	e.printStackTrace();
        	logger.error("Exception in DataBasketAnnotationQueryHandler: " + e);
        	throw new RuntimeException(e);
        }
        finally {
        	closeConnection(dataAccess);
        }
    }



	public List<AnnotationDTO> findAnnotationBySeriesUid(String seriesInstanceUid){

		List<AnnotationDTO> annotationList = new ArrayList<AnnotationDTO>();

		 String query = "select distinct ann from Annotation ann  where ann.seriesInstanceUID = '"+
	        seriesInstanceUid + "'";
	        try {
	            dataAccess.open();

	            List<Annotation> results = dataAccess.search(query);
	            for(Annotation a:results){
	            	AnnotationDTO dto = new AnnotationDTO(a.getFilePath(),
	            			                              a.getFileName(),
	            			                              a.getAnnotationType(),
	            			                              a.getFileSize());
	            	annotationList.add(dto);

	            }
	            return annotationList;

	        } catch (Exception e) {
	           logger.error("Error getting annotation for Series: " + seriesInstanceUid + " " + e.getMessage());
	        }finally{
	        	closeConnection(dataAccess);
	        }
	        return null;

	}

	////////////////////////////////////////////PRIVATE///////////////////////////////////////////////
    private String SQL_QUERY_SELECT = "SELECT annot.generalSeriesPkId, annot.filePath, annot.fileSize ";
    private String SQL_QUERY_FROM = "FROM Annotation annot ";
    private String SQL_QUERY_WHERE = "WHERE ";

	private static Logger logger = Logger.getLogger(AnnotationDAO.class);
}
