package gov.nih.nci.ncia.beans.qctool;

import gov.nih.nci.ncia.dao.QcStatusDAO;
import gov.nih.nci.ncia.dto.QcSearchResultDTO;
import gov.nih.nci.ncia.dto.QcStatusHistoryDTO;
import gov.nih.nci.ncia.util.MessageUtil;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIData;
import javax.faces.event.ValueChangeEvent;
import com.icesoft.faces.async.render.SessionRenderer;


/**
 *
 * <p>This bean relies upon VerifySubmissionBean to manage the
 * selection criteria for the report (date range, collection//site).
 */
public class QcToolSearchBean {
	
	public UIData getDataTable() {
		return dataTable;
	}
	public void setDataTable(UIData dataTable) {
		this.dataTable = dataTable;
	}
	public QcToolSearchBean() {
		SessionRenderer.addCurrentSession(ALL);
	}
	public void updateStatus() {
        // render the page
       	SessionRenderer.render(ALL);
    }

	public void pageNumberChangeListener(ValueChangeEvent event)
	{
		this.getDataTable().setFirst(0);
	}

    /**
     * This is the overall QC Tool Search Result for the total
     * time frame (as opposed to the per day report).
     */
    public List<QcSearchResultDTO> getQsrDTOList() {
        return qsrDTOList;
    }
    public void setQsrDTOList(List<QcSearchResultDTO> qsrDTOList) {
		this.qsrDTOList = qsrDTOList;
	}
	public List<QcStatusHistoryDTO>  getQshDTOList() {
        return qshDTOList;

    }

	/**
	 * @return the selectedDispItemNum
	 */
	public String getSelectedDispItemNum() {
		return selectedDispItemNum;
	}
	/**
	 * @param selectedDispItemNum the selectedDispItemNum to set
	 */
	public void setSelectedDispItemNum(String selectedDispItemNum) {
		this.selectedDispItemNum = selectedDispItemNum;
	}

	/**
     * This action is called when the submit button is clicked.
     */
    public String submit() throws Exception {
    	//for list box only
 //   	List<String> collectionSites = qcToolBean.getSelectedCollectionNames();
    	List<String> collectionSites = new ArrayList<String>();
    	collectionSites.add(qcToolBean.getSelectedCollectionSite());
        String [] qcStatus = {"To Be Deleted"};
        String patientIds = qcToolBean.getSelectedPatients();
        String [] patients = null;
        if (! qcToolBean.isSuperRole()) {
        	qcStatus = qcToolBean.getSelectedQcStatus();
        }
        if (qcStatus == null || qcStatus.length==0){
        	MessageUtil.addErrorMessage("MAINbody:qcToolSearchCritForm:slctQcStatus",
        			REQUIRED_FIELD);
        	return null;
        }

         if (qsrDTOList!=null) {
        	 qsrDTOList.clear();
        }

        if (collectionSites.size()==0) {
        	 collectionSites=qcToolBean.getAuthCollectionList();
        }
        if ((patientIds != null) &&  (patientIds.length() >=1)) {
        	patientIds=patientIds.replaceAll(" ","");
        	patients=patientIds.split(",");
        }

        QcStatusDAO qcStatusDAO = new QcStatusDAO();
        qsrDTOList = qcStatusDAO.findSeries(qcStatus, collectionSites, patients);
        
        if (this.getDataTable() != null)
        {
        	this.getDataTable().setFirst(0);
        }
        
        return "qcToolMain";
    }

    public String  requestRpt(){
    	List<String> seriesList= new ArrayList<String>();
    	if (qsrDTOList !=null){
    	for (int i=0; i<qsrDTOList.size();++i){
    		if (qsrDTOList.get(i).isSelected()){
    			seriesList.add(qsrDTOList.get(i).getSeries());
    		}
    	}
    	}
    	 if (seriesList.size() == 0){
         	MessageUtil.addErrorMessage("MAINbody:qcToolForm:SlctRec",
         			ERRORMSG_RPT);
         	return null;
         }
    	QcStatusDAO qcStatusDAO = new QcStatusDAO();
       	qshDTOList =qcStatusDAO.findQcStatusHistoryInfo(seriesList);
    	return "qcTooStatusRpt";
    }

    public void requestRpt(String seriesId){
    	List<String> seriesList= new ArrayList<String>();
    	seriesList.add(seriesId);
    	QcStatusDAO qcStatusDAO = new QcStatusDAO();
       	qshDTOList =qcStatusDAO.findQcStatusHistoryInfo(seriesList);
    }


    public String  backToQcTool(){
    	 return "qcToolMain";
    }

    public QcToolBean getQcToolBean() {
		return qcToolBean;
	}

    /**
     * Method to wire in the other bean that contains the input controls/values
     */
    public void setQcToolBean(QcToolBean qcToolBean) {
        this.qcToolBean = qcToolBean;

    }
    /**
     * Gets the options for number of displaying items for QC Result.
     *
     * @return array of predefined numbers for displaying search result
     */
 /*   public SelectItem[] getDispItemNums() {
        SelectItem[] dispItemNums = new SelectItem[4];
        dispItemNums[0] = new SelectItem("10");
        dispItemNums[1] = new SelectItem("25");
        dispItemNums[2] = new SelectItem("50");
        dispItemNums[3] = new SelectItem("100");
        return dispItemNums;
    }
    */
    /**
     * Method called when the number of display item are changed.
     *
     *@param vce event of the change
     */
    public void numberChangeListener(ValueChangeEvent vce) {
        System.out.println("numberChangeListener called");
    }

   /**
	 * Any page that wants to force an update can call this notification
	 * hack method.... side effect will make IceFaces think that section of
	 * page has changed and will refresh.
	 */
	public int getNotificationHack() {
		return (notificationHack+=1);
	}

	public String getSelectedHRptDispItemNum() {
		return selectedHRptDispItemNum;
	}
	public void setSelectedHRptDispItemNum(String selectedHRptDispItemNum) {
		this.selectedHRptDispItemNum = selectedHRptDispItemNum;
	}
	
	
    ////////////////////////////PRIVATE///////////////////////////////////////
    private static final String REQUIRED_FIELD = "qcTool_requiedField_Search";
    private static final String ERRORMSG_RPT="qcTool_requiedSeries";
    private QcToolBean qcToolBean;
    private String selectedDispItemNum="25";
    private String selectedHRptDispItemNum="25";
    private List<QcSearchResultDTO> qsrDTOList = null;
    private List<QcStatusHistoryDTO> qshDTOList;
    private static final String ALL = "all";
    private int notificationHack = 0;
    private UIData dataTable;

}
