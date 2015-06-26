/**
* $Id: CriteriaHandlerFactory.java 10617 2009-10-23 20:12:11Z kascice $
*
* $Log: not supported by cvs2svn $
* Revision 1.1  2007/08/05 21:44:39  bauerd
* Initial Check in of reorganized components
*
* Revision 1.11  2007/01/06 06:36:29  zhouro
* added new search criteria AcquisitionMatrix, ReconstructionDiameter, DataCollectionDiameter(CT) and DataCollectionDiameter(DX)
*
* Revision 1.10  2006/09/27 20:46:27  panq
* Reformated with Sun Java Code Style and added a header for holding CVS history.
*
*/
package gov.nih.nci.ncia.criteriahandler;


/**
 * @author Jin Chen
 *
 *
 *
 */
public class CriteriaHandlerFactory {
    private static CriteriaHandlerFactory myInstance = new CriteriaHandlerFactory();

    public static CriteriaHandlerFactory getInstance() {
        return myInstance;
    }

    public CriteriaHandler createImageSliceThickness() {
        return new RangeCriteriaHandler();
    }

    public CriteriaHandler createKilovoltagePeakDistribution() {
        return new RangeCriteriaHandler();
    }

    public CriteriaHandler createReconstructionDiameter() {
        return new RangeCriteriaHandler();
    }
    
    public CriteriaHandler createAcquisitionMatrix() {
        return new RangeCriteriaHandler();
    }
    
    public CriteriaHandler createCtDataCollectionDiameter() {
        return new RangeCriteriaHandler();
    }
    
    public CriteriaHandler createDxDataCollectionDiameter() {
        return new RangeCriteriaHandler();
    }

    public CriteriaHandler createCriteriaCollection() {
        return new CriteriaCollectionHandler();
    }
    
    
    public CriteriaHandler createNumOfMonths() {
    	return new RangeCriteriaHandler();
    }

    
    public CriteriaHandler createModelSoftwareCriteria() {
        return new ModelSoftwareCriteriaHandler();
    }
    

    public CriteriaHandler createUrlParamCriteria() {
        return new UrlParamCriteriaHandler();
    }
}