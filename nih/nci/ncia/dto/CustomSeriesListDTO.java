package gov.nih.nci.ncia.dto;

import gov.nih.nci.ncia.util.NCIAConfig;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Represents customized series list for data transfer purposes
 * @author lethai
 *
 */
public class CustomSeriesListDTO {
	private String name;
	private String comment;
	private String hyperlink;
	private Date date;
	private Integer id;
	private String userName;
	private List<String> seriesInstanceUIDs;
	private List<CustomSeriesListAttributeDTO> seriesInstanceUidsList;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<CustomSeriesListAttributeDTO> getSeriesInstanceUidsList() {
		return seriesInstanceUidsList;
	}
	public void setSeriesInstanceUidsList(
			List<CustomSeriesListAttributeDTO> seriesInstanceUidsList) {
		this.seriesInstanceUidsList = seriesInstanceUidsList;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getHyperlink() {
		return hyperlink;
	}
	public void setHyperlink(String hyperlink) {
		this.hyperlink = hyperlink;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public List<String> getSeriesInstanceUIDs() {
		return seriesInstanceUIDs;
	}
	public void setSeriesInstanceUIDs(List<String> seriesInstanceUIDs) {
		this.seriesInstanceUIDs = seriesInstanceUIDs;
	}
	 public String getDateString() {
	        if (date == null) {
	            return "";
	        }
	        SimpleDateFormat sdf =  NCIAConfig.getDateFormat();//new SimpleDateFormat("MM/dd/yyyy");
	        return sdf.format(date);
	    }

}
