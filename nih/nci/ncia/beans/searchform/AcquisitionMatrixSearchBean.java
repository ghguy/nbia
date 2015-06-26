package gov.nih.nci.ncia.beans.searchform;

import gov.nih.nci.ncia.criteria.AcquisitionMatrixCriteria;
import gov.nih.nci.ncia.query.DICOMQuery;
import org.apache.log4j.Logger;

/**
 * This is the Session scope bean that provides the search functionality on the
 * search page. It is maintained in the session in order to provide the
 * functionality of being able to go back and edit a search from the
 * breadcrumbs.
 *
 * @author zhouro
 */
public class AcquisitionMatrixSearchBean extends RangeCriteriaSearchBean {

	public AcquisitionMatrixSearchBean() {
    	logger = Logger.getLogger(AcquisitionMatrixSearchBean.class);
        logger.debug("constructing AcquisitionMatrixSearchBean");
    }
    /**
     * Populates the DicomQuery
     *
     */
    protected void setQuery(DICOMQuery query) {
        AcquisitionMatrixCriteria criteria = new AcquisitionMatrixCriteria(getCriteriaLeftCompare(), getCriteriaLeft(), getCriteriaRightCompare(), getCriteriaRight());
        query.setCriteria(criteria);
    }
}
