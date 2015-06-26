package gov.nih.nci.ncia.dao;

import gov.nih.nci.ncia.dto.ImageDTO;
import gov.nih.nci.ncia.dto.ImageSecurityDTO;
import gov.nih.nci.ncia.dto.SeriesDTO;
import gov.nih.nci.ncia.internaldomain.CTImage;
import gov.nih.nci.ncia.internaldomain.GeneralImage;
import gov.nih.nci.ncia.util.HqlUtils;
import gov.nih.nci.ncia.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class ImageDAO extends AbstractDAO {
	
	public String findImagePath(Integer imagePkId) {
        try {
            dataAccess.open();
            GeneralImage generalImage = (GeneralImage) dataAccess.load(GeneralImage.class,
                                                                       imagePkId);
            if(generalImage==null){
            	return null;
            }
            return generalImage.getFilename();
        } 
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally {
        	closeConnection(dataAccess);
        }
	}
	
	public ImageSecurityDTO findImageSecurity(String imageSopInstanceUid) {
        try{
        	dataAccess.open();

            Criteria criteria = dataAccess.createCriteria(GeneralImage.class, "i");
            ProjectionList projectionList = Projections.projectionList();
            projectionList.add(Projections.property("i.filename"));
            projectionList.add(Projections.property("gs.visibility"));
            projectionList.add(Projections.property("gs.securityGroup"));
            projectionList.add(Projections.property("tdp.project"));
            projectionList.add(Projections.property("tdp.dpSiteName"));
                                                       
            criteria = criteria.createCriteria("generalSeries","gs");
            criteria = criteria.createCriteria("study","s"); 
            criteria = criteria.createCriteria("patient","p");
            criteria = criteria.createCriteria("dataProvenance","tdp");
            criteria.setProjection(projectionList);

            criteria.add(Restrictions.eq("i.SOPInstanceUID", imageSopInstanceUid));

            Collection<Object[]> imageResults = (Collection<Object[]>)criteria.list();
            assert imageResults.size() <= 1;
            
            if(imageResults.size()==0) {
            	return null;
            }
            else {
            	Object[] imageResult = imageResults.iterator().next();
            	String fileName = (String)imageResult[0];
            	String seriesVisibility = (String)imageResult[1];
            	
            	String securityGroup = (String)imageResult[2];
            	String project = (String)imageResult[3];
            	String dpSiteName = (String)imageResult[4];
            	
    	        ImageSecurityDTO imageSecurityDTO = new ImageSecurityDTO(imageSopInstanceUid,
    	        		                                                 fileName, 
    	        		                                                 project, 
    	        		                                                 dpSiteName, 
    	        		                                                 securityGroup,
    	        		                                                 seriesVisibility.equals("1"));
    	        return imageSecurityDTO;            	
            }
	        

        }
        catch(Exception e) {
        	e.printStackTrace();
        	throw new RuntimeException(e);
        }
        finally {
        	closeConnection(dataAccess);
        }		
	}
	public Collection<String> findDistinctConvolutionKernels() {

        try{
        	dataAccess.open();

            Criteria criteria = dataAccess.createCriteria(CTImage.class, "ct");
            criteria.setProjection(Projections.distinct(Projections.property("convolutionKernel")));
            criteria.add(Restrictions.isNotNull("convolutionKernel"));
            //criteria = criteria.createCriteria("generalImage");
            //criteria = criteria.createCriteria("generalSeries");
            criteria.addOrder(Order.asc("ct.convolutionKernel"));
            //criteria.add(Restrictions.eq("visibility", "1"));

	        return (Collection<String>)criteria.list();
        }
        catch(Exception e) {
        	e.printStackTrace();
        	throw new RuntimeException(e);
        }
        finally {
        	closeConnection(dataAccess);
        }
	}

    public Map<Integer, List<ImageDTO>> findKeyedImagesBySeriesPkId(List<Integer> seriesIds) throws Exception {

        // Build return list
        List<ImageDTO> imageList = findImagesbySeriesPkID(seriesIds);

        // Build results hashmap for Dicom files based on results from database
        Map<Integer, List<ImageDTO>> dicomFilePaths = new HashMap<Integer, List<ImageDTO>>();

        for (ImageDTO biib : imageList) {
            List<ImageDTO> imagesForSeries = dicomFilePaths.get(biib.getSeriesPkId());

            if (imagesForSeries != null) {
                // Series is already in map.  Just add the filename
                imagesForSeries.add(biib);
            } else {
                // Add series and then add the filename to it
                imagesForSeries = new ArrayList<ImageDTO>();
                imagesForSeries.add(biib);
                dicomFilePaths.put(biib.getSeriesPkId(), imagesForSeries);
            }
        }

        return dicomFilePaths;
    }

    /**
     * Retrieve the maximum curation timestamp from the database
     */
    public Date findLastCurationDate() throws Exception {
        try{
        	dataAccess.open();

	        // Submit the search
	        long start = System.currentTimeMillis();
	        logger.info("Issuing query: " + LAST_CURATION_DATE_HQL);

	        List result = dataAccess.search(LAST_CURATION_DATE_HQL);
	        long end = System.currentTimeMillis();
	        logger.debug("total query time: " + (end - start) + " ms");

	        return (Date) result.get(0);
        }
        catch(Exception e) {
        	logger.error("Exception in LatestCurationDateQueryQueryHandler: " + e);
        	e.printStackTrace();
        	throw new RuntimeException(e);
        }
        finally {
        	closeConnection(dataAccess);
        }
    }

	public List<ImageDTO> findImagesbySeriesDTO(SeriesDTO seriesDTO) throws Exception {
		Collection<Integer> ids = new ArrayList<Integer>();
		ids.add(seriesDTO.getSeriesPkId());
		return this.findImagesbySeriesPkID(ids);
	}

	public List<ImageDTO> findImagesbySeriesPkID(Integer seriesPkId) throws Exception {
		Collection<Integer> ids = new ArrayList<Integer>();
		ids.add(seriesPkId);
		return this.findImagesbySeriesPkID(ids);
	}

	public List<ImageDTO> findImagesbySeriesPkID(Collection<Integer> seriesPkIds) throws Exception {
		String whereStmt = HqlUtils.buildInClauseUsingIntegers(" image.seriesPKId in ", seriesPkIds);
        return searchImagesbyHql(whereStmt);		                                        
    }
	public List<ImageDTO> findImagesbySeriesInstandUid(Collection<String> seriesInstanceUids) throws Exception {
		String whereStmt = HqlUtils.buildInClause(" image.seriesInstanceUID in ",seriesInstanceUids);
		return searchImagesbyHql(whereStmt);	
	}
	private List<ImageDTO> searchImagesbyHql(String whereCondi) throws Exception {
        String selectStmt = SQL_QUERY_SELECT;
        String fromStmt = SQL_QUERY_FROM;
        String whereStmt = SQL_QUERY_WHERE;
        List<ImageDTO> imageList = new ArrayList<ImageDTO>();
        whereStmt += whereCondi;
        
        try {
	        dataAccess.open();

	        // Submit the search
	        long start = System.currentTimeMillis();
	        logger.info("Issuing query: " + selectStmt + fromStmt + whereStmt);

	        List seriesQuery = dataAccess.search(selectStmt + fromStmt + whereStmt);
	        long end = System.currentTimeMillis();
	        logger.debug("total query time: " + (end - start) + " ms");	        

	        for (Object item : seriesQuery) {
	            Object[] row = (Object[]) item;


	            String imageFileName = row[3].toString();

	            ImageDTO thumbnailDTO = new ImageDTO();
	            thumbnailDTO.setImagePkId((Integer) row[0]);
	            thumbnailDTO.setContentDate((Date) row[1]);
	            thumbnailDTO.setContentTime(Util.nullSafeString(row[2]));	            
	            thumbnailDTO.setSeriesPkId((Integer) row[4]);
	            thumbnailDTO.setInstanceNumber((Integer) row[6]);
	            thumbnailDTO.setSeriesInstanceUid(row[7].toString());
	            thumbnailDTO.setSopInstanceUid(row[8].toString());
	            thumbnailDTO.setFileURI(imageFileName);
	            thumbnailDTO.setSize((Long) row[5]);
	            imageList.add(thumbnailDTO);
	        }

	        Collections.sort(imageList);
        }
        catch(Exception e) {
        	logger.error(e);
        	e.printStackTrace();
        	throw new RuntimeException(e);
        }
        finally {
        	closeConnection(dataAccess);
        }
        return imageList;
    }

	public ImageDTO getGeneralImageByImagePkId(Integer imagePkId)
	{
		ImageDTO imageDto = null;
		try
		{
			dataAccess.open();
			Criteria criteria = dataAccess.createCriteria(GeneralImage.class);
			criteria.add(Restrictions.eq("id", imagePkId));
			
			List<GeneralImage> result = criteria.list();
			if (result != null && result.size() > 0)
			{
				imageDto = new ImageDTO();
				GeneralImage image = (GeneralImage)result.get(0);
				imageDto.setProject(image.getDataProvenance().getProject());
				imageDto.setSiteName(image.getDataProvenance().getDpSiteName());
			}
		}catch(Exception e)
		{
			logger.error(e);
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}finally{
			closeConnection(dataAccess);
		}
		
		return imageDto;
	}
	

	//////////////////////////////////////PRIVATE///////////////////////////////////////////

    private static Logger logger = Logger.getLogger(ImageDAO.class);

    private static final String LAST_CURATION_DATE_HQL = "select max(gi.curationTimestamp) from GeneralImage gi";

	private String SQL_QUERY_SELECT = "SELECT image.id, image.contentDate, image.contentTime, image.filename, image.seriesPKId, image.dicomSize, image.instanceNumber, image.seriesInstanceUID, image.SOPInstanceUID ";
    private String SQL_QUERY_FROM = "FROM GeneralImage image ";
    private String SQL_QUERY_WHERE = "WHERE ";
}
