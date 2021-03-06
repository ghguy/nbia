package gov.nih.nci.ncia.deletion;

/**
 * This class holds the all deletion information before the deletion occurs.  
 * @author zhoujim
 *
 */
public class DeletionDisplayObject {
	
	private String seriesUID;
	/*
	 * The order of series
	 */
	private int order;
	/*
	 * Trial Data Provenance collection
	 */
	private String project;
	/*
	 * Trial Data Provence site name 
	 */
	private String site;
	
	public String getSeriesUID() {
		return seriesUID;
	}
	public void setSeriesUID(String seriesUID) {
		this.seriesUID = seriesUID;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	

}
