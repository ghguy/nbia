package gov.nih.nci.ncia.domain;


import java.io.Serializable;
	/**
	* A non-image file associated with a series that provides additional information about the images in the series.   Examples of annotation files are image markup and radiologist reports.	**/
public class Annotation  implements Serializable
{
	/**
	* An attribute to allow serialization of the domain objects
	*/
	private static final long serialVersionUID = 1234567890L;

	
		/**
	* The location (full path and file name) of the annotation file on the file system.	**/
	private String filePath;
	/**
	* Retreives the value of filePath attribute
	* @return filePath
	**/

	public String getFilePath(){
		return filePath;
	}

	/**
	* Sets the value of filePath attribue
	**/

	public void setFilePath(String filePath){
		this.filePath = filePath;
	}
	
		/**
	* The size (in bytes) of the annotation file	**/
	private Integer fileSize;
	/**
	* Retreives the value of fileSize attribute
	* @return fileSize
	**/

	public Integer getFileSize(){
		return fileSize;
	}

	/**
	* Sets the value of fileSize attribue
	**/

	public void setFileSize(Integer fileSize){
		this.fileSize = fileSize;
	}
	
		/**
	* One or more characters used to identify, name, or characterize the nature, properties, or contents of a thing.	**/
	private Integer id;
	/**
	* Retreives the value of id attribute
	* @return id
	**/

	public Integer getId(){
		return id;
	}

	/**
	* Sets the value of id attribue
	**/

	public void setId(Integer id){
		this.id = id;
	}
	
		/**
	* Unique identifer of a study series.	**/
	private String seriesInstanceUID;
	/**
	* Retreives the value of seriesInstanceUID attribute
	* @return seriesInstanceUID
	**/

	public String getSeriesInstanceUID(){
		return seriesInstanceUID;
	}

	/**
	* Sets the value of seriesInstanceUID attribue
	**/

	public void setSeriesInstanceUID(String seriesInstanceUID){
		this.seriesInstanceUID = seriesInstanceUID;
	}
	
		/**
	* The date on which the annotation file was submitted.	**/
	private java.util.Date submissionDate;
	/**
	* Retreives the value of submissionDate attribute
	* @return submissionDate
	**/

	public java.util.Date getSubmissionDate(){
		return submissionDate;
	}

	/**
	* Sets the value of submissionDate attribue
	**/

	public void setSubmissionDate(java.util.Date submissionDate){
		this.submissionDate = submissionDate;
	}
	
		/**
	* The file extension of the annotation file.  For example, xml or zip.	**/
	private String type;
	/**
	* Retreives the value of type attribute
	* @return type
	**/

	public String getType(){
		return type;
	}

	/**
	* Sets the value of type attribue
	**/

	public void setType(String type){
		this.type = type;
	}
	
	/**
	* An associated gov.nih.nci.ncia.domain.Series object
	**/
			
	private Series series;
	/**
	* Retreives the value of series attribue
	* @return series
	**/
	
	public Series getSeries(){
		return series;
	}
	/**
	* Sets the value of series attribue
	**/

	public void setSeries(Series series){
		this.series = series;
	}
			
	/**
	* Compares <code>obj</code> to it self and returns true if they both are same
	*
	* @param obj
	**/
	public boolean equals(Object obj)
	{
		if(obj instanceof Annotation) 
		{
			Annotation c =(Annotation)obj; 			 
			if(getId() != null && getId().equals(c.getId()))
				return true;
		}
		return false;
	}
		
	/**
	* Returns hash code for the primary key of the object
	**/
	public int hashCode()
	{
		if(getId() != null)
			return getId().hashCode();
		return 0;
	}
	
}