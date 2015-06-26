/**
* $Id: NCIADatabase.java 4417 2008-04-18 20:43:12Z saksass $
*
* $Log: not supported by cvs2svn $
* Revision 1.29  2007/12/04 17:28:48  lethai
* comment debug code
*
* Revision 1.28  2007/11/05 22:23:33  lethai
* Modified error messages
*
* Revision 1.27  2007/10/17 15:38:45  lethai
* For release 3.0
*
* Revision 1.26  2007/10/01 17:36:33  lethai
* Changes for release 3.0
*
* Revision 1.25  2007/09/24 15:01:13  lethai
* Changes for release 3.0
*
* Revision 1.24  2007/09/10 18:26:14  lethai
* 3.0 release
*
* Revision 1.23  2007/01/11 20:14:43  zhouro
* added inserting AcquisitionMatrix and DxDataCollectionDiameter in GeneralImage table
*
* Revision 1.22  2006/12/15 20:21:57  zhouro
* changed data type from Integer to Double
*
* Revision 1.21  2006/09/28 19:29:00  panq
* Reformated with Sun Java Code Style and added a header for holding CVS history.
*
*/
package gov.nih.nci.ncia.dbadapter;

import gov.nih.nci.ncia.db.DataAccessProxy;
import gov.nih.nci.ncia.db.IDataAccess;
import gov.nih.nci.ncia.internaldomain.GeneralSeries;
import gov.nih.nci.ncia.query.DicomSOPInstanceUIDQuery;
import gov.nih.nci.ncia.util.AnnotationUtil;
import gov.nih.nci.ncia.util.DicomConstants;

import java.io.File;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.dcm4che.data.Dataset;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.rsna.ctp.objects.DicomObject;
import org.rsna.ctp.objects.FileObject;
import org.rsna.ctp.objects.XmlObject;
import org.rsna.ctp.objects.ZipObject;
import org.rsna.ctp.pipeline.Status;
import org.rsna.ctp.stdstages.database.DatabaseAdapter;
import org.rsna.ctp.stdstages.database.UIDResult;
import org.w3c.dom.Document;

/**
 * Word of warning - if you configure multiple pipelines, any
 * static data here is shared among the pipelines.  This means
 * you need to be very careful of what you make static.
 */
public class NCIADatabase extends DatabaseAdapter {
    Logger log = Logger.getLogger(NCIADatabase.class);
    Map numbers = new HashMap();
    IDataAccess ida = null;
    Properties dicomProp = new Properties();
    Properties databaseProp = new Properties();
    static final String DATABASE_MAPPING = "database.properties";
    static final String DICOM_PROPERITIES = "dicom.properties";

    public Status connect() {

    	if (ida == null) {
    		try {

                InputStream in = Thread.currentThread().getContextClassLoader()
                                       .getResourceAsStream(DATABASE_MAPPING);
                databaseProp.load(in);
                ida = (new DataAccessProxy()).getInstance(Integer.parseInt(
                            databaseProp.getProperty("database_mapping")));
            } catch (Exception e) {
                log.error("Error in create DataAccess at connect() in NCIADatabase: ",
                    e);
                return Status.FAIL;
            }
        }
        try {
            ida.open();
        } catch (Exception e) {
            log.error("Error in open at connect() in NCIADatabase: " + e);
            return Status.FAIL;
        }
        return Status.OK;
    }

    public Status disconnect() {

        try {
            if (ida != null) {
                ida.close();
            }

            ida = null;
        } catch (Exception e) {
            log.error("Error in close at close() in NCIADatabase: " + e);

            return Status.FAIL;
        }

        return Status.OK;
    }

    public Status shutdown() {
        try {
            if (ida != null) {
                ida.close();
            }

            ida = null;
        } catch (Exception e) {
            ida = null;
            log.error("Error in close at shutdown() in NCIADatabase: " + e);

            return Status.FAIL;
        }

        return Status.OK;
    }

    /**
     * If ALL of the group 13 tags aren't found in the number map,
     * then reject this submission.
     *
     * <p>Historically this method tried to piece together the missing provenance
     * information if some of the provenance information wasn't found.
     * Too complicated for to little gain... so submitters must include
     * all provenance information.
     */
    private boolean preProcess() throws Exception {
        boolean ok = false;
    	// Pass the check if none of the project name, site id, site name and trial name is null.
    	if ((numbers.get(DicomConstants.PROJECT_NAME) != null) &&
            (numbers.get(DicomConstants.SITE_ID) != null) &&
            (numbers.get(DicomConstants.SITE_NAME) != null) &&
            (numbers.get(DicomConstants.TRIAL_NAME) != null)) {

          	ok = checkSeriesStatus();
        }
    	return ok;
    }

    private boolean checkSeriesStatus()
    {
    	boolean status = false;
    	Criteria criteria = ida.createCriteria(GeneralSeries.class);
    	criteria.setProjection(Projections.property("visibility"));
    	criteria.add(Restrictions.eq("seriesInstanceUID", numbers.get(DicomConstants.SERIES_INSTANCE_UID)));

    	 List<String> rs =(List<String>)criteria.list();

    	 if(rs == null || rs.size() <= 0)
    	 {
    		 status = true;
    	 }
    	 else
    	 {
	    	 String visibility = (String)rs.get(0);
	    	 if (visibility.equalsIgnoreCase("4"))
	    	 {
	    		 log.error("Series Instance UID - " + numbers.get(DicomConstants.SERIES_INSTANCE_UID)
	    				 + " marked as deleted. Cannot update this series");
	    		 status = false;
	    	 }
	    	 else
	    	 {
	    		 status = true;
	    	 }
    	 }

    	 return status;
    }

    public Status process(DicomObject file, File storedFile,String url) {
    	if (storedFile == null)
    	{
    		log.error("Unable to obtain the stored DICOM file");
    		return Status.FAIL;
    	}
        String filename = storedFile.getAbsolutePath();

        long filesize = storedFile.length();
        boolean visibility=false;
        try {
            numbers = new HashMap();

            //Dataset set = DcmUtil.parse(storedFile, 0x5FFFffff);
            //Based on what John Perry's request
            Dataset set = file.getDataset();
            parseDICOMPropertiesFile(set);

            //enhancement of storage service
            if (!preProcess()) {
            	log.error("Storage Service - Preprocess: Error occurs when trying to find project, site in preprocess() for file " + file.getFile().getAbsolutePath());
                return Status.FAIL;
            }

            String temp = (String) numbers.get(DicomConstants.TRIAL_VISIBILITY);

            if ((temp != null) &&
                    temp.equals(DicomConstants.SPECIFIC_CHARACTER_SET)) {
                visibility = true;
            } else {
                visibility = false;
            }
            ida.beginTransaction();
			ImageStorage imageStorage = new ImageStorage();
            Status status = imageStorage.storeDicomObject(ida,numbers,filename, filesize,visibility);
            if(status.equals(Status.OK)) {
	            ida.commit();
	            return Status.OK;
            }else {
            	ida.rollback();
                log.error("Rollback in process(DicomObject,String) for file " + file.getFile().getAbsolutePath());
            	return Status.FAIL;
            }

        } catch (Exception e) {
            if(e.getMessage() != null) {
                log.error("Error in process(DicomObject,String) for file " + file.getFile().getAbsolutePath()+  " " + e.getMessage());
            }
            else {
                log.error("Error in process(DicomObject,String) for file " + file.getFile().getAbsolutePath());
            }
            try {
            	ida.rollback();
                log.error("Rollback in process(DicomObject,String) for file " + file.getFile().getAbsolutePath());
            }catch(Exception ex) {
            	log.error("Error in rollback transaction " + ex);
            	return Status.FAIL;
            }

            return Status.FAIL;
        }
    }


    /* (non-Javadoc)
     * @see org.rsna.mircsite.util.DatabaseAdapter#process(org.rsna.mircsite.util.XmlObject, java.lang.String)
     */
    public Status process(XmlObject file,File storedFile, String url) {
        Document document = file.getDocument();
        String seriesInstanceUID = AnnotationUtil.getSeriesInstanceUID(document);
        String studyInstanceUID = AnnotationUtil.getStudyInstanceUID(document);

        log.info("Processing XmlObject with study:"+studyInstanceUID+" and series:"+seriesInstanceUID);

        return processAnnotation(studyInstanceUID,
                                 seriesInstanceUID,
                                 storedFile,
                                 file.getFile(),
                                 AnnotationStorage.AnnotationType.XML);
    }

    /* (non-Javadoc)
     * @see org.rsna.mircsite.util.DatabaseAdapter#process(org.rsna.mircsite.util.ZipObject, java.lang.String)
     */
    public Status process(ZipObject file, File storedFile, String url) {
        if (file.getManifestText().equals("")) {
            log.error("No valid manifest.xml test found");

            return Status.FAIL;
        }

        Document document = file.getManifestDocument();
        String seriesInstanceUID = AnnotationUtil.getSeriesInstanceUID(document);
        String studyInstanceUID = AnnotationUtil.getStudyInstanceUID(document);

        log.info("Processing ZipObject with study:"+studyInstanceUID+" and series:"+seriesInstanceUID);

        return processAnnotation(studyInstanceUID,
        		                 seriesInstanceUID,
        		                 storedFile,
        		                 file.getFile(),
        		                 AnnotationStorage.AnnotationType.ZIP);
    }

    public Status process(FileObject file, File storedFile, String url) {
		String fileExtension = file.getExtension();
    	log.info("file extension: " + fileExtension);
        log.error("FileObject is not supported yet" + storedFile.getAbsolutePath() + "\tfile extension is " + fileExtension);

        return Status.FAIL;
    }

    private void parseDICOMPropertiesFile(Dataset dicomSet)
        throws Exception {
        InputStream in = Thread.currentThread().getContextClassLoader()
                               .getResourceAsStream(DICOM_PROPERITIES);
        dicomProp.load(in);

        Enumeration enum1 = dicomProp.propertyNames();

        while (enum1.hasMoreElements()) {
            String propname = enum1.nextElement().toString();
            int pName = Integer.parseInt(propname, 16);
            String elementheader = null;

            if (propname.equals("00200037") || propname.equals("00200032")) {
                String[] temp = dicomSet.getStrings(pName);

                if ((temp != null) && (temp.length > 0)) {
                    elementheader = temp[0];

                    for (int i = 1; i < temp.length; i++) {
                        elementheader += ("\\" + temp[i]);
                    }
                }
            }
            else {
                try {
                    elementheader = getElementValue(pName, dicomSet);
                } catch (UnsupportedOperationException uoe) {
                    elementheader = null;
                }
            }

            if (elementheader != null) {
                elementheader = elementheader.replaceAll("'", "");

                String[] temp = dicomProp.getProperty(propname).split("\t");
                numbers.put(temp[0], elementheader);

                if(log.isDebugEnabled()) {
                	log.debug("Parsing:"+propname+"/"+temp[0]+" as "+elementheader);
                }
            }
        } //while
    }

    /**
     * Get the contents of a DICOM element in the DicomObject's dataset.
     * If the element is part of a private group owned by CTP, it returns the
     * value as text. This method returns the defaultString argument if the
     * element does not exist.
     * @param tag the tag specifying the element (in the form 0xggggeeee).
     * @param dataset is Dicom dataset.
     * @return the text of the element, or defaultString if the element does not exist.
     * Notes: Make sure defaultString initial value must be null
     */
    private String getElementValue(int tag, Dataset dataset) {
        boolean ctp = false;

        //if group is odd (private) and element is > 1000 (VR=UN or content v. header)
        if (((tag & 0x00010000) != 0) && ((tag & 0x0000ff00) != 0)) {

			//each block of 1000hex in the tag address space maps
			//back to a slot in the first 1000hex.  from the tag address
			//compute the header address.  so 0013,1010 -> 0013, 0010
			//
			//at a bit level - strip the element/keep the group and
			//then compute the element by stripping of the bottom two
			//
            int blk = (tag & 0xffff0000) | ((tag & 0x0000ff00) >> 8);
            try {
                ctp = dataset.getString(blk).equals("CTP");
            }
            catch (Exception notCTP) {
				log.warn("Is [0013,0010] missing, or it doesnt equal CTP?");
            	notCTP.printStackTrace();
            }
        }
        String value = null;
        try {
            if (ctp) {
               value = new String(dataset.getByteBuffer(tag).array());
            }
            else {
               value = dataset.getString(tag);
            }
        }
        catch (Exception notAvailable) {
    	    log.warn("in NICADatabase class, cannot get element value");
        }
        if (value != null) {
			value = value.trim();
     	}
        return value;
    }

    /**
     * This is a transaction boundary for adding a database row
     * to the annotation table for a submission XML or Zip annotation
     *
     * @param studyInstanceUID Parsed from annotation file or manifest of zip
     * @param seriesInstanceUID Parsed from annotation file or manifest of zip
     * @param storedFile The file on disk
     * @param objectFile This may be the same as storedFile?  But legacy code
     *                   ask the XmlObject or ZipObject for getFile instead of
     *                   using storedFile... so that's what this is
     * @param annotationType XML or ZIP
     */
    private Status processAnnotation(String studyInstanceUID,
    		                         String seriesInstanceUID,
    		                         File storedFile,
    		                         File objectFile,
    		                         AnnotationStorage.AnnotationType annotationType) {
        // 1)get serial instance uid from xml file
        // 2) insert a new record to anotation table and get a new annotation pk id
        // 3) check if general_series table has it, make sure it links to right study
    	// 4)update its annotation column
        if (seriesInstanceUID.equals("") || studyInstanceUID.equals("")) {
            log.error("No seriesInstanceUID or studyInstanceUID found for file " +
            		objectFile.getAbsolutePath());

            return Status.FAIL;
        }

        String filename = storedFile.getAbsolutePath();
        long filesize = storedFile.length();

        try {
            ida.beginTransaction();
            AnnotationStorage AnnotationStorage = new AnnotationStorage();
            Status status = AnnotationStorage.storeAnnotation(ida,
            		                                          studyInstanceUID,
            		                                          seriesInstanceUID,
            		                                          filename,
            		                                          filesize,
            		                                          annotationType);
            if(status.equals(Status.OK)) {
                ida.commit();
            }
            else {
                ida.rollback();
                log.error("Rollback in processAnnotation() for file " + objectFile.getAbsolutePath());
                return Status.FAIL;
            }
        }
        catch(Exception e) {
        	final String ERROR_MSG = "Error in processAnnotation() for file ";
            if(e.getMessage() != null) {
                log.error(ERROR_MSG + objectFile.getAbsolutePath()+  " " +e.getMessage());
            }
            else {
                log.error(ERROR_MSG + objectFile.getAbsolutePath());
            }
            try {
                ida.rollback();
                log.error("Rollback in processAnnotation() for file " + objectFile.getAbsolutePath());
            }
            catch(Exception ex) {
                log.error("Error in rollback transaction " + ex);
                return Status.FAIL;
            }
            return Status.FAIL;
        }
        return Status.OK;
    }

    public Map<String, UIDResult> uidQuery(Set<String> uidSet)
    {
    	Map<String, UIDResult> result = null;
    	try
    	{
	    	DicomSOPInstanceUIDQuery sopQuery = new DicomSOPInstanceUIDQuery(ida, uidSet);
	    	result = sopQuery.getUIDResult();
	    	ida.close();
    	}catch(Exception e)
    	{
    		log.error("In NCIA database uidQuery method, " + e.getMessage());
    	}

	    return result;
    }
}
