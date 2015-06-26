package gov.nih.nci.ncia.deletion.dao;


import gov.nih.nci.ncia.deletion.ImageDeletionService;
import gov.nih.nci.ncia.internaldomain.CTImage;
import gov.nih.nci.ncia.internaldomain.GeneralImage;
import gov.nih.nci.ncia.exception.DataAccessException;

import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class ImageDAOImpl extends HibernateDaoSupport implements ImageDAO {
	private static Logger logger = Logger.getLogger(ImageDAOImpl.class);
	private Session session = null;

	public void removeImages(List<Integer> seriesIds, ImageDeletionService ids) throws DataAccessException{
		session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		if (session == null)
		{
			throw new DataAccessException("Cannot get session from session Factory in ImageDAOImpl");
		}
		try
		{
			List<Integer> imageIds = getImageIDs(seriesIds);
			if (imageIds != null && imageIds.size() > 0)
			{
				List<GeneralImage> deletedImageObject = getImageObject(imageIds);
				List<CTImage> deletedCTImageObject = getCTImageObject(imageIds);

				List<String> imageFiles = new ArrayList<String>();

				for (GeneralImage image : deletedImageObject)
				{
					imageFiles.add(image.getFilename());
				}
				ids.setImageFileNames(imageFiles);

				if (deletedCTImageObject != null && deletedCTImageObject.size() > 0)
				{
					deleteCTImage(deletedCTImageObject);
				}
				if (deletedImageObject != null && deletedImageObject.size() > 0)
				{
					deleteImage(deletedImageObject);
				}
			}
		}catch(org.springframework.dao.DataAccessException e)
		{
			logger.error("Failed to remove Images or CT image in ImageDAOImpl!!");
			throw new DataAccessException("Failed to remove Images or CT image in ImageDAOImpl!!");
		}
	}

	public void deleteImage(List<GeneralImage> imageObjects)
	{
		for (GeneralImage image : imageObjects)
		{
			session.delete(image);
		}
	}

	public void deleteCTImage(List<CTImage> ctImageObjects)
	{
		for (CTImage ct : ctImageObjects)
		{
			session.delete(ct);
		}
	}

	public List<Integer> getImageIDs(List<Integer> seriesIds)
	{
		Criteria criteria = session.createCriteria(GeneralImage.class);
		criteria.setProjection(Projections.property("id"));

		criteria.add(Restrictions.in("seriesPKId", seriesIds));

		List<Integer> imageIds = criteria.list();

		return imageIds;
	}

	public List<GeneralImage> getImageObject(List<Integer> imageIDs)
	{
		Criteria criteria = session.createCriteria(GeneralImage.class);
		criteria.add(Restrictions.in("id", imageIDs));

		List<GeneralImage> result = criteria.list();

		return result;
	}

	public List<CTImage> getCTImageObject(List<Integer> imageIDs)
	{
		Criteria criteria = session.createCriteria(CTImage.class);
		criteria.add(Restrictions.in("generalImage.id", imageIDs));

		List<CTImage> result = criteria.list();

		return result;
	}
}
