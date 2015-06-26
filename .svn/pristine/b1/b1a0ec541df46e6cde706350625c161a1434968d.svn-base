package gov.nih.nci.ncia.deletion.dao;

import gov.nih.nci.ncia.deletion.ImageDeletionService;
import gov.nih.nci.ncia.internaldomain.CTImage;
import gov.nih.nci.ncia.internaldomain.GeneralImage;
import gov.nih.nci.ncia.exception.DataAccessException;

import java.util.List;

public interface ImageDAO {
	public void removeImages(List<Integer> seriesIds, ImageDeletionService ids)  throws DataAccessException;
	public List<CTImage> getCTImageObject(List<Integer> imageIDs);
	public List<GeneralImage> getImageObject(List<Integer> imageIDs);
	public List<Integer> getImageIDs(List<Integer> seriesIds);
	public void deleteImage(List<GeneralImage> imageObjects);
	public void deleteCTImage(List<CTImage> ctImageObjects);
}
