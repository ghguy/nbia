package gov.nih.nci.ncia.beans.customserieslist;

import gov.nih.nci.ncia.beans.BeanManager;
import gov.nih.nci.ncia.beans.basket.BasketBean;
import gov.nih.nci.ncia.beans.security.SecurityBean;
import gov.nih.nci.ncia.customserieslist.CustomSeriesListProcessor;
import gov.nih.nci.ncia.dto.CustomSeriesListDTO;
import gov.nih.nci.ncia.dto.SeriesDTO;
import gov.nih.nci.ncia.security.AuthorizationManager;
import gov.nih.nci.ncia.util.SeriesDTOConverter;

import java.util.List;
import org.apache.log4j.Logger;

public class SearchCustomSeriesListBean {

    private static Logger logger = Logger.getLogger(SearchCustomSeriesListBean.class);
    private List<String> noPermissionSeries;
    private CustomSeriesListProcessor processor;
    private SecurityBean sb;
    private String name="";
    private boolean errorMessage=false;
    private String message;


    public boolean isErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(boolean errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SearchCustomSeriesListBean() throws Exception{
        sb = BeanManager.getSecurityBean();
        AuthorizationManager am = sb.getAuthorizationManager();
        processor = new CustomSeriesListProcessor(sb.getUsername(), am);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

     /**
     * search the system for the series list. Add content to data basket for successful search
     */
    public String search(){
        errorMessage=false;
        message="";
        if(noPermissionSeries != null){
            noPermissionSeries.clear();
        }
        //redirect to data basket page after successfully search
        CustomSeriesListDTO result = processor.searchByName(name);
        
        if(result != null){
            List<String> seriesList = result.getSeriesInstanceUIDs();
            noPermissionSeries = processor.validate(seriesList);

            if(noPermissionSeries.isEmpty()){
                //add to databasket
                List<SeriesDTO> seriesDTO = processor.getSeriesDTO(seriesList);

                //check to see if the list has been compromised.
                if(hasTheListBeenCompromised(seriesList, seriesDTO)){
                    message="The List (" + name + ") has been modified by the system recently. " +
                            "There is a discrepancy between the current search results and the original list. " + 
                            "Please contact the list owner or application support for additional information.";
                    errorMessage=true;
                    return null;
                }
                //add to data basket
                BasketBean dataBasket = (BasketBean) BeanManager.getBasketBean();
                dataBasket.setCustomListSearch(true);
                dataBasket.setCustomListComment(result.getComment());
                dataBasket.setCustomListName(result.getName());
                dataBasket.setCustomListLink(result.getHyperlink());

                try {
                    if (seriesDTO != null && seriesDTO.size() > 0){
                        dataBasket.getBasket().addSeries(SeriesDTOConverter.convert(seriesDTO));
                    }
                } catch (Exception e) {
                    logger.error("Error adding series to databasket " + e);
                    throw new RuntimeException(e);
                }
                return "dataBasket";
             }
        }else{
            //display message not found here
            errorMessage=true;
            message="The list (" + name + ") could not be found.";
        }
        return null;
    }

    public List<String> getNoPermissionSeries() {
        return noPermissionSeries;
    }

    public void setNoPermissionSeries(List<String> noPermissionSeries) {
        this.noPermissionSeries = noPermissionSeries;
    }

    /**
     * Reset 
     */
    public String reset(){
        errorMessage=false;
        name="";
        message="";
        if(noPermissionSeries != null){
            noPermissionSeries.clear();
        }
        return null;
    }
    
    private boolean hasTheListBeenCompromised(List<String> listSeriesUids, List<SeriesDTO> resultFromSearch){
        boolean changed = false;
        if(listSeriesUids.size() != resultFromSearch.size()){
            changed = true;
        }
        for(SeriesDTO series:resultFromSearch){
            if (!listSeriesUids.contains(series.getSeriesUID())){
                changed = true;
            }
        }
        return changed;	
    }
}