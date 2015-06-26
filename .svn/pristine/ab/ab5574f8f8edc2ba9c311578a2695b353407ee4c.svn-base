package gov.nih.nci.ncia.beans.searchresults;

import gov.nih.nci.ncia.basket.BasketSeriesItemBean;
import gov.nih.nci.ncia.beans.BeanManager;
import gov.nih.nci.ncia.beans.basket.BasketBean;
import gov.nih.nci.ncia.beans.security.AnonymousLoginBean;
import gov.nih.nci.ncia.beans.security.SecurityBean;
import gov.nih.nci.ncia.search.PatientSearchResult;
import gov.nih.nci.ncia.util.CedaraUtil;
import gov.nih.nci.ncia.util.MessageUtil;
import gov.nih.nci.ncia.util.NCIAConfig;

import java.util.Collection;

public class CedaraBean {


    public String getIrwVersion() {
        return NCIAConfig.getMappedIRWVersion();
    }


    public String getIrwLink() {
        return NCIAConfig.getMappedIRWLink();
    }


	/**
	 * Check whether the Cedara I-Response Workstation is running or not.
	 */
	public Boolean getIsIRWAvailable() {
		return this.isIRWAvailable;
	}


	/**
	 * Get the Cedara IGS information from property file
	 */
	public String getHost() {
		String hostAddress = NCIAConfig.getCedaraIGSAddress();
		String hostPort = NCIAConfig.getCedaraIGSPort();
		return "http://" + hostAddress + ":" + hostPort;
	}


	/**
	 * Get current user name
	 */
	public String getUser() {
		SecurityBean secure = BeanManager.getSecurityBean();
		return secure.getUsername();
	}


    public String getUid() {
        return uid;
    }


    public void setUid(String uid) {
    	this.uid = uid;
    }


    /**
     * Get the select uid and redirect to check IRW page.
     */
    public String visualizeSelectedSeries() {
    	fromDataBasket = false;

        //if guest user, give them warning that they need to login to use this feature
        AnonymousLoginBean anonymousLoginBean = BeanManager.getAnonymousLoginBean();
		if(anonymousLoginBean.getGuestLoggedIn()){
			//set message here
			String warningText = "MAINbody:dataForm:visualizeImages";
			MessageUtil.addErrorMessage(warningText,
			                            "visulizeImagesForGuest");
			return null;
		}

		uid = CedaraUtil.constructUidParameterString(studiesSearchResultBean.getStudyResults());

        return "checkIRW";
    }

    public boolean isLocal() {
    	PatientSearchResult patientSearchResult = BeanManager.getSearchResultBean().getPatient();
    	if(patientSearchResult!=null) { //went to basket page before search
    		return patientSearchResult.associatedLocation().isLocal();
    	}
    	else {
    		return true;
    	}
    }


    /**
     * This property returns a URL to the "portal" markup servlet.
     * Cedara I-Response is passed this in checkIRW.xhtml.  This
     * enables I-Response to talk directly to our markup servlets
     * to read and save markup
     **/
    public String getArchiveValue() {
        // get JBoss url from external properties
        String url = NCIAConfig.getJBossPublicUrl()+"MarkupQuery";
        System.out.println(url);
        return url;
    }


    /**
     * Was Cedara initiated from the data basket (true) or from the study
     * results page (false).
     */
    public boolean isFromBasket() {
    	return fromDataBasket;
    }

    /**
     * Get the select uid and redirect to check IRW page.
     */
    public String initiateCedaraAgainstSelectedBasketItems() throws Exception {
    	fromDataBasket = true;

        AnonymousLoginBean anonymousLoginBean = BeanManager.getAnonymousLoginBean();
        if(anonymousLoginBean.getGuestLoggedIn()){
        	//set message here
            String warningText = "MAINbody:basketForm:visualizeImages";
            MessageUtil.addErrorMessage(warningText,
                                        "visulizeImagesForGuest");
            return null;
        }

        Collection<BasketSeriesItemBean> basketItems = basketBean.getSeriesItems();
        if(CedaraUtil.containsRemoteSeries(basketItems)) {
            String warningText = "MAINbody:basketForm:visualizeImages";
            MessageUtil.addErrorMessage(warningText,
                                        "remoteNode");
            return null;
        }
        if(CedaraUtil.containsMultiplePatients(basketItems)) {
            String warningText = "MAINbody:basketForm:visualizeImages";
            MessageUtil.addErrorMessage(warningText,
                                          "multiplePatient");
            return null;
        }

        uid = CedaraUtil.constructUidParameterString(basketItems);


        setUid(uid);

        return "checkIRW";
    }


    /**
     * For dependency injection
     */
	public StudiesSearchResultBean getStudiesSearchResultBean() {
		return studiesSearchResultBean;
	}


    /**
     * For dependency injection
     */
	public void setStudiesSearchResultBean(StudiesSearchResultBean studiesSearchResultBean) {
		this.studiesSearchResultBean = studiesSearchResultBean;
	}


    /**
     * For dependency injection
     */
	public BasketBean getBasketBean() {
		return basketBean;
	}


    /**
     * For dependency injection
     */
	public void setBasketBean(BasketBean basketBean) {
		this.basketBean = basketBean;
	}

	/////////////////////////////////////PRIVATE//////////////////////////////////

	private boolean fromDataBasket;

    private StudiesSearchResultBean studiesSearchResultBean;

    private BasketBean basketBean;

    private Boolean isIRWAvailable = true;

    private String uid;
}