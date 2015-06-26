package gov.nih.nci.ncia.domain;


import java.io.Serializable;
	/**
	* Data associated with a trial, patient, study, series, image, or annotation file that is not contained in the image header.   The data is stored as name value pairs where CDEs and their values are used to represent the name and value.	**/
public class CurationData  implements Serializable
{
	/**
	* An attribute to allow serialization of the domain objects
	*/
	private static final long serialVersionUID = 1234567890L;

	
		/**
	* The public ID of the Common Data Element that describes the curation data	**/
	private String cdeId;
	/**
	* Retreives the value of cdeId attribute
	* @return cdeId
	**/

	public String getCdeId(){
		return cdeId;
	}

	/**
	* Sets the value of cdeId attribue
	**/

	public void setCdeId(String cdeId){
		this.cdeId = cdeId;
	}
	
		/**
	* The description of the Common Data Element that describes the curation data	**/
	private String cdeName;
	/**
	* Retreives the value of cdeName attribute
	* @return cdeName
	**/

	public String getCdeName(){
		return cdeName;
	}

	/**
	* Sets the value of cdeName attribue
	**/

	public void setCdeName(String cdeName){
		this.cdeName = cdeName;
	}
	
		/**
	* The Common Data Element value that describes the curation data	**/
	private String cdeValue;
	/**
	* Retreives the value of cdeValue attribute
	* @return cdeValue
	**/

	public String getCdeValue(){
		return cdeValue;
	}

	/**
	* Sets the value of cdeValue attribue
	**/

	public void setCdeValue(String cdeValue){
		this.cdeValue = cdeValue;
	}
	
		/**
	* The version of the Common Data Element that describes the curation data	**/
	private String cdeVersion;
	/**
	* Retreives the value of cdeVersion attribute
	* @return cdeVersion
	**/

	public String getCdeVersion(){
		return cdeVersion;
	}

	/**
	* Sets the value of cdeVersion attribue
	**/

	public void setCdeVersion(String cdeVersion){
		this.cdeVersion = cdeVersion;
	}
	
		/**
	* The date and time at which curation data was submitted	**/
	private java.util.Date curationTimestamp;
	/**
	* Retreives the value of curationTimestamp attribute
	* @return curationTimestamp
	**/

	public java.util.Date getCurationTimestamp(){
		return curationTimestamp;
	}

	/**
	* Sets the value of curationTimestamp attribue
	**/

	public void setCurationTimestamp(java.util.Date curationTimestamp){
		this.curationTimestamp = curationTimestamp;
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
	* The name (as chosen by the image submitter) of the clinical trial to which the curation data is related.	**/
	private String trialName;
	/**
	* Retreives the value of trialName attribute
	* @return trialName
	**/

	public String getTrialName(){
		return trialName;
	}

	/**
	* Sets the value of trialName attribue
	**/

	public void setTrialName(String trialName){
		this.trialName = trialName;
	}
	
		/**
	* Unique identifier of the NCIA user that uploaded the curation data record	**/
	private Integer userId;
	/**
	* Retreives the value of userId attribute
	* @return userId
	**/

	public Integer getUserId(){
		return userId;
	}

	/**
	* Sets the value of userId attribue
	**/

	public void setUserId(Integer userId){
		this.userId = userId;
	}
	
	/**
	* An associated gov.nih.nci.ncia.domain.Study object
	**/
			
	private Study study;
	/**
	* Retreives the value of study attribue
	* @return study
	**/
	
	public Study getStudy(){
		return study;
	}
	/**
	* Sets the value of study attribue
	**/

	public void setStudy(Study study){
		this.study = study;
	}
			
	/**
	* An associated gov.nih.nci.ncia.domain.Image object
	**/
			
	private Image image;
	/**
	* Retreives the value of image attribue
	* @return image
	**/
	
	public Image getImage(){
		return image;
	}
	/**
	* Sets the value of image attribue
	**/

	public void setImage(Image image){
		this.image = image;
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
	* An associated gov.nih.nci.ncia.domain.Annotation object
	**/
			
	private Annotation annotationFile;
	/**
	* Retreives the value of annotationFile attribue
	* @return annotationFile
	**/
	
	public Annotation getAnnotationFile(){
		return annotationFile;
	}
	/**
	* Sets the value of annotationFile attribue
	**/

	public void setAnnotationFile(Annotation annotationFile){
		this.annotationFile = annotationFile;
	}
			
	/**
	* An associated gov.nih.nci.ncia.domain.Patient object
	**/
			
	private Patient patient;
	/**
	* Retreives the value of patient attribue
	* @return patient
	**/
	
	public Patient getPatient(){
		return patient;
	}
	/**
	* Sets the value of patient attribue
	**/

	public void setPatient(Patient patient){
		this.patient = patient;
	}
			
	/**
	* Compares <code>obj</code> to it self and returns true if they both are same
	*
	* @param obj
	**/
	public boolean equals(Object obj)
	{
		if(obj instanceof CurationData) 
		{
			CurationData c =(CurationData)obj; 			 
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