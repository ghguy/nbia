package gov.nih.nci.cagrid.ncia.service;

import static java.lang.System.out;
import gov.nih.nci.cagrid.cqlquery.CQLQuery;
import gov.nih.nci.ncia.ImageFilesProcessor;
import gov.nih.nci.ncia.ServiceImplHelper;
import gov.nih.nci.ncia.domain.Image;
import gov.nih.nci.ncia.domain.TrialDataProvenance;
import gov.nih.nci.ncia.zip.ZippingDTO;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.cagrid.transfer.context.service.helper.TransferServiceHelper;
import org.cagrid.transfer.context.stubs.types.TransferServiceContextReference;

/**
 * TODO:I am the service side implementation class. IMPLEMENT AND DOCUMENT ME
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 * @author lethai
 *
 */

public class NCIACoreServiceImpl extends NCIACoreServiceImplBase {

    private static Logger logger = Logger.getLogger(NCIACoreServiceImpl.class);

    private static final String ZIP_EXT = ".zip";
    private static String NOT_PUBLIC_GROUP= " is not in public access group.";
    private static String ERROR_SERIES = "Error getting data for seriesInstanceUID: ";
    private static String ERROR_STUDY = "Error getting data for studyInstanceUID: ";
    
    public NCIACoreServiceImpl() throws RemoteException {
        super();
    }

    public TransferServiceContextReference retrieveDicomData(CQLQuery cQLQuery) throws RemoteException {
        // Step 1: get the file path
        final Map<String, String> fileNames = ServiceImplHelper.retrieveImageFiles(cQLQuery);

        // return empty output stream if there is data found for the given
        // cqlquery
        if (fileNames == null) {
            return ServiceImplHelper.getEmptyOutputStream();
        }

        // Step 2: We transfer the data
        // set up the piped streams
        PipedOutputStream pos = new PipedOutputStream();
        PipedInputStream pis = new PipedInputStream();
        try {
            pis.connect(pos);
        } catch (IOException e) {
            throw new RemoteException("Unable to make a pipe", e);
        }

        // The part below needs to be threaded, since the transfer service
        // creation reads from the stream completely.
        Thread t = new Thread(new ZipWorker(pos, fileNames));
        t.start();

        // set up the transfer context
        TransferServiceContextReference tscr = TransferServiceHelper
                .createTransferContext(pis, null);
        return tscr;
    }

    /**
     * This method retrieve dicom data for patientId
     * 
     * @param patientId
     * @return
     * @throws RemoteException
     */
    public TransferServiceContextReference retrieveDicomDataByPatientId(String patientId) throws RemoteException {
        TransferServiceContextReference tscr = null;
        ImageFilesProcessor ifp = null;
        try {
            // find out project and site of the patientId
            ifp = new ImageFilesProcessor();
            TrialDataProvenance tdp = ifp.getTDPByPatientId(patientId);
            if (tdp == null) {
                logger
                        .info("Couldnot find TrialDataProvenance(project, site) information for the PatientId "
                                + patientId);
                return ServiceImplHelper.getEmptyOutputStream();
            }

            boolean isPublic = ServiceImplHelper.getPublicGroupAndCheckForPublicAccess(tdp);
            if (isPublic) {
                Map<String, String> filePaths = ifp
                        .getImagesFilesByPatientId(patientId);
                tscr = getDicomData(filePaths);
                
            } else {
                logger.info("The PatientId " + patientId
                        + NOT_PUBLIC_GROUP);
                return ServiceImplHelper.getEmptyOutputStream();
            }

        } catch (Exception e) {
            logger.error("Error getting data for patientId: " + patientId, e);
            throw new RemoteException("Error getting data for patientId: "
                    + patientId + e.getMessage(), e);
        }
        ifp = null;
        return tscr;
    }

    /**
     * This method retrieve dicom data for a given seriesInstanceUID
     * 
     * @param seriesInstanceUID
     * @return
     * @throws RemoteException
     */
    public TransferServiceContextReference retrieveDicomDataBySeriesUID(String seriesInstanceUID) throws RemoteException {
        TransferServiceContextReference tscr = null;
        ImageFilesProcessor ifp = null; 
        try {
            // find out what project and site this seriesInstanceUID belongs to
            ifp = new ImageFilesProcessor();
            TrialDataProvenance tdp = ifp
                    .getTDPBySeriesInstanceUID(seriesInstanceUID);
            if (tdp == null) {
                logger
                        .info("Couldn't find trial data provenance information for seriesInstanceUID "
                                + seriesInstanceUID);
                return ServiceImplHelper.getEmptyOutputStream();
            }
            // get public access group and check whether this seriesInstanceUID
            // is in this group
            boolean isPublic = ServiceImplHelper.getPublicGroupAndCheckForPublicAccess(tdp);
            if (isPublic) {
                Map<String, String> filePaths = ifp
                        .getImagesFilesBySeriesInstanceUID(seriesInstanceUID);
                tscr = getDicomData(filePaths);
                
            } else {
                logger.info("The SeriesInstanceUID " + seriesInstanceUID
                        + NOT_PUBLIC_GROUP);
                return ServiceImplHelper.getEmptyOutputStream();
            }

        } catch (Exception e) {
            logger.error(ERROR_SERIES
                    + seriesInstanceUID, e);
            throw new RemoteException(
                    ERROR_SERIES
                            + seriesInstanceUID + e.getMessage(), e);
        }
        ifp = null;
        return tscr;
    }

    /**
     * This method retrieve dicom data for a given studyInstanceUID
     * 
     * @param studyInstanceUID
     * @return
     * @throws RemoteException
     */
    public TransferServiceContextReference retrieveDicomDataByStudyUID(String studyInstanceUID) throws RemoteException {
        TransferServiceContextReference tscr = null;
        ImageFilesProcessor ifp = null; 
        try {
            // find out project and site of this studyInstanceUID
            ifp = new ImageFilesProcessor();
            TrialDataProvenance tdp = ifp
                    .getTDPByStudyInstanceUID(studyInstanceUID);
            if (tdp == null) {
                logger
                        .info("Couldnot find TrialDataProvenance information for the studyInstanceUID "
                                + studyInstanceUID);
                return ServiceImplHelper.getEmptyOutputStream();
            }
            boolean isPublic = ServiceImplHelper.getPublicGroupAndCheckForPublicAccess(tdp);
            if (isPublic) {
                Map<String, String> filePaths = ifp
                        .getImagesFilesByStudyInstanceUID(studyInstanceUID);
                tscr = getDicomData(filePaths);
                
            } else {
                logger.info("The studyInstanceUID " + studyInstanceUID
                        + NOT_PUBLIC_GROUP);
                return ServiceImplHelper.getEmptyOutputStream();
            }
        } catch (Exception e) {
            logger.error(ERROR_STUDY
                    + studyInstanceUID, e);
            throw new RemoteException(
                    ERROR_STUDY
                            + studyInstanceUID + e.getMessage(), e);
        }

        ifp = null;
        return tscr;
    }

    public TransferServiceContextReference retrieveDicomDataByPatientIds(String[] patientIds) throws RemoteException {
        TransferServiceContextReference tscr = null;
        ImageFilesProcessor ifp = null;
        List<String> patientIdsList = new ArrayList<String>();
        String filename = "";
        
        try {
            String filepath = ServiceImplHelper.getTempZipLocation();
            ifp = new ImageFilesProcessor();
            
            for (int i = 0, n = patientIds.length; i < n; i++) {
                patientIdsList.add(patientIds[i]);
                
            }
            Map<String, TrialDataProvenance> patientTDPs = ifp.getTDPByListIds(patientIdsList, "PATIENT_ID");            
            
            //check for public access
            List<String> publicPatientIds = ServiceImplHelper.getPublicGroupAndCheckForPublicAccess(patientTDPs);

            /* choose the first study in the list to be the zip file name*/
            if (publicPatientIds.size() > 0) {
                filename = publicPatientIds.get(0);
            }
            List<ZippingDTO> list = ifp
                    .getImageFilesByPatientIds(publicPatientIds);
            if (list == null || list.isEmpty()) {
                logger
                        .info("Couldnot find image information for the patientIdsList "
                                + patientIdsList.toString());
                return ServiceImplHelper.getEmptyOutputStream();
            } else {
                tscr = ServiceImplHelper.getDicomData(list, filepath, filename + ZIP_EXT);
                
            }
        
        } catch (Exception e) {
            logger.error("Error getting data for patientIds: "
                    + patientIdsList.toString(), e);
            throw new RemoteException(
                    "Error getting data for patientIds: "
                            + patientIdsList.toString() + e.getMessage(), e);
        }
        return tscr;
    }

    public TransferServiceContextReference retrieveDicomDataBySeriesUIDs(String[] seriesInstanceUids) throws RemoteException {
        TransferServiceContextReference tscr = null;
        ImageFilesProcessor ifp = null;
        List<String> seriesIdsList = new ArrayList<String>();
        String filename = "";
        
        try {
            String filepath = ServiceImplHelper.getTempZipLocation();
            ifp = new ImageFilesProcessor();
            
            for (int i = 0, n = seriesInstanceUids.length; i < n; i++) {
                seriesIdsList.add(seriesInstanceUids[i]);                
            }
            Map<String, TrialDataProvenance> seriesTDPs = ifp.getTDPByListIds(seriesIdsList, "SERIES_INSTANCE_UID");
            
            //check for public access
            List<String> publicSeriesIds = ServiceImplHelper.getPublicGroupAndCheckForPublicAccess(seriesTDPs);

            /* choose the first study in the list to be the zip file name*/
            if (publicSeriesIds.size() > 0) {
                filename = publicSeriesIds.get(0);
            }
            List<ZippingDTO> list = ifp
                    .getImageFilesBySeriesUids(publicSeriesIds);
            if (list == null || list.isEmpty()) {
                logger
                        .info("Couldnot find image information for the seriesIdsList "
                                + seriesIdsList.toString());
                return ServiceImplHelper.getEmptyOutputStream();
            } else {
                tscr = ServiceImplHelper.getDicomData(list, filepath, filename + ZIP_EXT);
                
            }
        } catch (Exception e) {
            logger.error(ERROR_STUDY
                    + seriesIdsList.toString(), e);
            throw new RemoteException(
                    ERROR_STUDY
                            + seriesIdsList.toString() + e.getMessage(), e);
        }
        return tscr;
    }

    public TransferServiceContextReference retrieveDicomDataByStudyUIDs(String[] studyInstanceUids) throws RemoteException {
        TransferServiceContextReference tscr = null;
        ImageFilesProcessor ifp = null;
        List<String> studyIdsList = new ArrayList<String>();
        String filename = "";

        try {
            String filepath = ServiceImplHelper.getTempZipLocation();
            ifp = new ImageFilesProcessor();
            
            for (int i = 0, n = studyInstanceUids.length; i < n; i++) {
                studyIdsList.add(studyInstanceUids[i]);                
            }
            Map<String, TrialDataProvenance> studyTDPs = ifp.getTDPByListIds(studyIdsList, "STUDY_INSTANCE_UID");
                
            //check for public access
            List<String> publicStudyIds = ServiceImplHelper.getPublicGroupAndCheckForPublicAccess(studyTDPs);

            /* choose the first study in the list to be the zip file name*/
            if (publicStudyIds.size() > 0) {
                filename = publicStudyIds.get(0);
            }
            List<ZippingDTO> list = ifp
                    .getImageFilesByStudiesUids(publicStudyIds);
            if (list == null || list.isEmpty()) {
                logger
                        .info("Couldnot find image information for the studyIdsList "
                                + studyIdsList.toString());
                return ServiceImplHelper.getEmptyOutputStream();
            } else {
                tscr = ServiceImplHelper.getDicomData(list, filepath, filename + ZIP_EXT);
                
            }
        } catch (Exception e) {
            logger.error(ERROR_STUDY
                    + studyIdsList.toString(), e);
            throw new RemoteException(
                    ERROR_STUDY
                            + studyIdsList.toString() + e.getMessage(), e);
        }
        return tscr;
    }

    
    public int getNumberOfStudyTimePointForPatient(String patientId) throws RemoteException {
        ImageFilesProcessor ifp = null;

        int numberOfTimepoint =0;

        try {
            ifp = new ImageFilesProcessor();          
          
            TrialDataProvenance patientTDP = ifp.getTDPByPatientId(patientId);          
          
            //check for public access
            boolean isPublic = ServiceImplHelper.getPublicGroupAndCheckForPublicAccess(patientTDP);

            if (isPublic) {
                List<java.sql.Date> studyTimepoint = ifp.getTimepointStudyForPatient(patientId);
                if(!studyTimepoint.isEmpty()){
                    numberOfTimepoint = studyTimepoint.size();
                }
            }
            else{
              logger
              .error("Patient " +patientId + " is not in public group.");
              throw new RemoteException("Permission denied for patient: " + patientId + ". This patient is not in public group.");
            }  
        }catch(Exception e){
            logger.error("Error getting study time point: " ,e);
            throw new RemoteException("Error getting study timepoint: " + e);
        }
        
        return numberOfTimepoint;
    }

  
    /**
     * Retrieve and zip all dicom files for nth study timepoint of a patient. Return empty output stream if no images found. 
     * @param patientId
     * @param studyNumber
     * @return
     * @throws RemoteException
     */
    public TransferServiceContextReference retrieveDicomDataByNthStudyTimePointForPatient(String patientId,int studyTimepoint) throws RemoteException {
       
        TransferServiceContextReference tscr = null;
        ImageFilesProcessor ifp = null;

        String filename = "";

        try {
            String filepath = ServiceImplHelper.getTempZipLocation();
            ifp = new ImageFilesProcessor();
          
          
            TrialDataProvenance patientTDP = ifp.getTDPByPatientId(patientId);
          
          
            //check for public access
            boolean isPublic = ServiceImplHelper.getPublicGroupAndCheckForPublicAccess(patientTDP);

            /* choose the patientId to be the zip file name*/
            if (isPublic) {
                filename = patientId;        
            }else{
                logger.info("Patient " +patientId + " is not in public group.");
                return ServiceImplHelper.getEmptyOutputStream();
            }
            List<ZippingDTO> list = ifp.getImagesByNthStudyTimePointForPatient(patientId, studyTimepoint);
            logger.info("Result......................................");        
          
            if (list == null || list.isEmpty()) {
                logger.info("Couldnot find image information for the patient "
                              + patientId + " study number : " + studyTimepoint);
                return ServiceImplHelper.getEmptyOutputStream();
            } else {
                //debugging codes
                for(int i=0;i<list.size(); i++){
                    out.println( i + ": " + list.get(i).getProject()+ " " + list.get(i).getStudyInstanceUid() + " "+ list.get(i).getSeriesInstanceUid()+ " "  + list.get(i).getSopInstanceUidAsFileName() +
                          " " + list.get(i).getFilePath() + " " + list.get(i).getProject());
                }
                //////
                tscr = ServiceImplHelper.getDicomData(list, filepath, filename + ZIP_EXT);
              
            }
        } 
        catch (Exception e) {
            logger.error("Error getting data for patient: "
                  + patientId, e);
            throw new RemoteException(
                  "Error getting data for patientId: "
                          + patientId + e.getMessage(), e);
        }
        return tscr;
     }
    
    
    /**
     *     
     */
    public Image getRepresentativeImageBySeries(String seriesInstanceUID) throws RemoteException {
     //TODO: check whether this series is in public group
       //build sql to get image attributes for this series
       
       ImageFilesProcessor ifp = null; 
       Image image=null;
       try {
           // find out what project and site this seriesInstanceUID belongs to
           ifp = new ImageFilesProcessor();
           TrialDataProvenance tdp = ifp
                    .getTDPBySeriesInstanceUID(seriesInstanceUID);
           if (tdp == null) {
               logger
                        .error("Couldn't find trial data provenance information for seriesInstanceUID "
                                + seriesInstanceUID);
               throw new RemoteException(
                        "Couldn't find trial data provenance information for seriesInstanceUID: "
                                + seriesInstanceUID );
           }
           // get public access group and check whether this seriesInstanceUID
           // is in this group
           boolean isPublic = ServiceImplHelper.getPublicGroupAndCheckForPublicAccess(tdp);
           if (isPublic) {
               image = ifp.getRepresentativeImageBySeries(seriesInstanceUID);
                
           } else {
               logger.info("The SeriesInstanceUID " + seriesInstanceUID
                        + NOT_PUBLIC_GROUP);
               throw new RemoteException(
                        "The SeriesInstanceUID " + seriesInstanceUID
                        + NOT_PUBLIC_GROUP );
           }

        } 
        catch (Exception e) {
            logger.error(ERROR_SERIES
                    + seriesInstanceUID, e);
            throw new RemoteException(
                    ERROR_SERIES
                            + seriesInstanceUID + e.getMessage(), e);
        }
        ifp = null;
        return image;
    }
 
    private TransferServiceContextReference getDicomData(Map<String, String> filePaths) throws RemoteException {
        final Map<String, String> fileNames = filePaths;

        if (fileNames == null) {
            return ServiceImplHelper.getEmptyOutputStream();
        }
        // Step 2: We transfer the data
        // set up the piped streams
        PipedOutputStream pos = new PipedOutputStream();
        PipedInputStream pis = new PipedInputStream();
        try {
            pis.connect(pos);
        } catch (IOException e) {
            throw new RemoteException("Unable to make a pipe", e);
        }

        // The part below needs to be threaded, since the transfer service
        // creation reads from the stream completely.
        Thread t = new Thread(new ZipWorker(pos, fileNames));
        t.start();

        // set up the transfer context
        TransferServiceContextReference tscr = TransferServiceHelper
                .createTransferContext(pis, null);
        return tscr;
    }
    

}