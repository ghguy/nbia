/**
 * 
 */
package gov.nih.nci.ncia.domain.operation;

import gov.nih.nci.ncia.db.IDataAccess;
import gov.nih.nci.ncia.internaldomain.Annotation;
import gov.nih.nci.ncia.internaldomain.GeneralSeries;
import gov.nih.nci.ncia.internaldomain.Study;

import java.util.Map;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author lethai
 *
 */
public class AnnotationOperation extends DomainOperation {
	
	private static Logger log = Logger.getLogger(AnnotationOperation.class);
	
	public AnnotationOperation(IDataAccess ida) {
		this.ida = ida;
	}
	
	/* (non-Javadoc)
	 * @see gov.nih.nci.ncia.domain.operation.DomainOperation#validate(java.util.Hashtable)
	 */
	public Object validate(Map numbers) throws Exception {
		return null;
	}
	
	/**
	 * This method sets the "has annotations" flag on the series
	 * if there is indeed an annotation for a given series.
	 */
	public void updateAnnotation(GeneralSeries series) throws Exception{
		String hql = "from Annotation as annotation where ";
        hql += (" annotation.seriesInstanceUID = '" +
        series.getSeriesInstanceUID() + "' ");
        
        try {
            List annotationList = (List)ida.search(hql);
    
            if ((annotationList != null) && (annotationList.size() != 0)) {
                Iterator aIter = annotationList.iterator();
    
                while (aIter.hasNext()) {
                	Annotation annotation = (Annotation) aIter.next();
                    annotation.setGeneralSeries(series);
                    ida.store(annotation);
                    series.setAnnotationsFlag(Boolean.TRUE);
                    ida.store(series);
                }
            }
        }catch(Exception e) {        	
        	throw new Exception("Exception in AnnotationOperation " + e);
        }		
	}
	

	/**
	 * This method is used when annotation file is submitted. 
	 */
	public void insertOrReplaceAnnotation(Annotation annotation,
			                              String filename, 
			                              String studyInstanceUID) throws Exception {
		
        String hql = "from Annotation as annotation where ";
        hql += (" annotation.filePath = '" + filename + "' ");
        
        List res = (List)ida.search(hql);
        if(res != null && res.size() > 0) {
        	annotation = (Annotation)res.get(0);
        }
        //if we found it, why do we store it again?????
        ida.store(annotation);

        linkAnnotationToSeries(annotation, studyInstanceUID);                            
	}
	
	private void linkAnnotationToSeries(Annotation annotation,
			                            String studyInstanceUID) throws Exception {
		
		GeneralSeries series = new GeneralSeries();
        
        String hql = "from GeneralSeries as series where "+
                     " series.seriesInstanceUID = '" +
                     annotation.getSeriesInstanceUID().trim() +
                     "' ";
        
        List ret = (List)ida.search(hql);
        if(ret != null && ret.size() > 0) {
        	if(ret.size() == 1) {
        		series = (GeneralSeries)ret.get(0);
        	}
	    	else 
    		if (ret.size() > 1) {
    			throw new Exception("series table has duplicate records, please contact Data Team to fix data, then upload data again");
	    	}
        }
        else {
        	throw new Exception("Annotation cannot be submitted before an image from its series (anymore).  Series Instance UID:"+annotation.getSeriesInstanceUID());
        }
        
        //make sure the series link to right study
        Study study =(Study)series.getStudy();
        if(study != null) {
            if(studyInstanceUID.equals(study.getStudyInstanceUID())){
            	if (series.getId() != null) {
                    series.setAnnotationsFlag(Boolean.TRUE);
                    ida.store(series);
                    annotation.setGeneralSeries(series);
                    ida.store(annotation);
                }        	
            }
            else {
            	log.error("Annotation file submitted: studyInstance UID (" + studyInstanceUID + ") is different with studyInstance UID in database (" + study.getStudyInstanceUID()+")");
            	throw new Exception ("Annotation file submitted: studyInstance UID (" + studyInstanceUID + ") is different with studyInstance UID in database (" + study.getStudyInstanceUID()+")");
            }
        }
        //first one should probably take care of things
        else {
        	throw new Exception("Annotation cannot be submitted before an image from its series (anymore).  Series Instance UID:"+annotation.getSeriesInstanceUID());
        }  
	}	
}
