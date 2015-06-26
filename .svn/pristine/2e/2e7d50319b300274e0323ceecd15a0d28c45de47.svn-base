package gov.nih.nci.ncia.dbadapter;

import gov.nih.nci.ncia.db.IDataAccess;
import gov.nih.nci.ncia.internaldomain.CTImage;
import gov.nih.nci.ncia.internaldomain.ClinicalTrial;
import gov.nih.nci.ncia.internaldomain.GeneralEquipment;
import gov.nih.nci.ncia.internaldomain.GeneralImage;
import gov.nih.nci.ncia.internaldomain.GeneralSeries;
import gov.nih.nci.ncia.internaldomain.Patient;
import gov.nih.nci.ncia.internaldomain.Study;
import gov.nih.nci.ncia.internaldomain.SubmissionHistory;
import gov.nih.nci.ncia.internaldomain.TrialDataProvenance;
import gov.nih.nci.ncia.internaldomain.TrialSite;
import gov.nih.nci.ncia.domain.operation.AnnotationOperation;
import gov.nih.nci.ncia.domain.operation.CTImageOperation;
import gov.nih.nci.ncia.domain.operation.ClinicalTrialOperation;
import gov.nih.nci.ncia.domain.operation.GeneralEquipmentOperation;
import gov.nih.nci.ncia.domain.operation.GeneralImageOperation;
import gov.nih.nci.ncia.domain.operation.ImageSubmissionHistoryOperation;
import gov.nih.nci.ncia.domain.operation.PatientOperation;
import gov.nih.nci.ncia.domain.operation.SeriesOperation;
import gov.nih.nci.ncia.domain.operation.StudyOperation;
import gov.nih.nci.ncia.domain.operation.TrialDataProvenanceOperation;
import gov.nih.nci.ncia.domain.operation.TrialSiteOperation;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.rsna.ctp.pipeline.Status;

public class ImageStorage {
	public Status storeDicomObject(IDataAccess ida,
			                       Map numbers,
			                       String fileName,
			                       long fileSize,
			                       boolean visibility) {
        TrialDataProvenance tdp=null;
		try {
			TrialDataProvenanceOperation tdpo = new TrialDataProvenanceOperation(ida);
			tdp = (TrialDataProvenance)tdpo.validate(numbers);
			ida.store(tdp);
        }catch(Exception e) {
            log.error("Exception in TrialDataProvenanceOperation " + e);
            errors.put("TrialDataProvenance", e.getMessage());
            return Status.FAIL;
        }

		try {
			ClinicalTrialOperation cto = new ClinicalTrialOperation(ida);
			ClinicalTrial trial = (ClinicalTrial)cto.validate(numbers);
			ida.store(trial);
        }catch(Exception e) {
            log.error("Exception in ClinicalTrialOperation " + e);
            errors.put("ClinicalTrial", e.getMessage());
            return Status.FAIL;
        }
        TrialSite site=null;
        try {
			TrialSiteOperation tso = new TrialSiteOperation(ida);
			site = (TrialSite)tso.validate(numbers);
			ida.store(site);
        }catch(Exception e) {
            log.error("Exception in TrialSiteOperation " + e);
            errors.put("TrialSite", e.getMessage());
            return Status.FAIL;
        }
        Patient patient=null;
        try {
			PatientOperation po = new PatientOperation(ida);
			//po.setVisibility(visibility);
			po.setTdp(tdp);
			po.setSite(site);
			patient = (Patient)po.validate(numbers);
			ida.store(patient);
        }catch(Exception e) {
            log.error("Exception in PatientOperation " + e);
            return Status.FAIL;
        }
        Study study=null;
        try {
			StudyOperation so = new StudyOperation(ida);
			so.setPatient(patient);
			//so.setVisibility(visibility);
			study = (Study)so.validate(numbers);
			ida.store(study);
        }catch(Exception e) {
            log.error("Exception in StudyOperation " + e);
            return Status.FAIL;
        }
        GeneralEquipment equip=null;
        try {
			GeneralEquipmentOperation geo = new GeneralEquipmentOperation(ida);
			equip = (GeneralEquipment)geo.validate(numbers);
			ida.store(equip);
        }catch(Exception e) {
            log.error("Exception in GeneralEquipmentOperation " + e);
            return Status.FAIL;
        }
        GeneralSeries series=null;
        try {
			SeriesOperation serieso = new SeriesOperation(ida);
			serieso.setEquip(equip);
			serieso.setPatient(patient);
			serieso.setStudy(study);
			//serieso.setVisibility(visibility);
			series = (GeneralSeries)serieso.validate(numbers);
			ida.store(series);
			AnnotationOperation ao = new AnnotationOperation(ida);
			ao.updateAnnotation(series);
        }catch(Exception e) {
            log.error("Exception in SeriesOperation " + e);
            return Status.FAIL;
        }
        GeneralImage gi=null;
        GeneralImageOperation gio = null;
        try {
			gio = new GeneralImageOperation(ida);
			gio.setDataProvenance(tdp);
			gio.setPatient(patient);
			gio.setSeries(series);
			gio.setStudy(study);
			//still need visibility to determine curationTimestamp
			//gio.setVisibility(visibility);
			gi = (GeneralImage)gio.validate(numbers);

		    if (fileName != null) {
		        gi.setFilename(fileName);
		    }
		    gi.setDicomSize(Long.valueOf(fileSize));
		    //calculate insertion time, need to remove it later.
	       // String currentTime = "" + Calendar.getInstance().getTimeInMillis();
	        //gi.setContentTime(currentTime);
	        ida.store(gi);
        }catch(Exception e) {
            log.error("File " + gi.getFilename()+ " " + e);
            e.printStackTrace();
            return Status.FAIL;
        }

        try {
			CTImageOperation ctio = new CTImageOperation(ida);
			ctio.setGeneralImage(gi);
			//ctio.setVisibility(visibility);
			CTImage ct = (CTImage)ctio.validate(numbers);
			ida.store(ct);

		}catch(Exception e) {
			log.error("Exception in CTImageOperation " + e);
            errors.put("CTImage", e.getMessage());
            return Status.FAIL;
		}

		try {
			ImageSubmissionHistoryOperation imageSubmissionHistoryOperation
				= new ImageSubmissionHistoryOperation(gio.isReplacement(),
						                              tdp,
						                              patient,
					                                  study,
					                                  series);
			SubmissionHistory imageSubmissionHistory =
				(SubmissionHistory)imageSubmissionHistoryOperation.validate(numbers);
			ida.store(imageSubmissionHistory);
		}
		catch(Exception e) {
			log.error("Exception in ImageSubmissionHistoryOperation " + e);
            //it is my belief that this is totally useless but will follow pattern till overall analysis is done
            errors.put("ImageSubmissionHistory", e.getMessage());
            return Status.FAIL;
        }

        if(errors.size()> 0) {
            System.out.println("Total numbers of errors: " + errors.size());
            return Status.FAIL;
        }
        return Status.OK;
    }

    private Map<String,String> errors = new HashMap<String,String>();

    private Logger log = Logger.getLogger(ImageStorage.class);
}
