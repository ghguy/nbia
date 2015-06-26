package gov.nih.nci.ncia.domain;

import java.util.Collection;
import java.io.Serializable;
	/**
	* A set of images.  Typically, a series is made up of all of the slice generated from a scan.  Each series is associated with exactly one Study.	**/
public class Series  implements Serializable
{
	/**
	* An attribute to allow serialization of the domain objects
	*/
	private static final long serialVersionUID = 1234567890L;

	
		/**
	* Text description of the anatomic part of the body examined.	**/
	private String bodyPartExamined;
	/**
	* Retreives the value of bodyPartExamined attribute
	* @return bodyPartExamined
	**/

	public String getBodyPartExamined(){
		return bodyPartExamined;
	}

	/**
	* Sets the value of bodyPartExamined attribue
	**/

	public void setBodyPartExamined(String bodyPartExamined){
		this.bodyPartExamined = bodyPartExamined;
	}
	
		/**
	* Unique identifier for the frame of reference for an imaging series. Each series shall have a single Frame of Reference UID, while multiple Series within a Study may share a Frame of Reference UID.	**/
	private String frameOfReferenceUID;
	/**
	* Retreives the value of frameOfReferenceUID attribute
	* @return frameOfReferenceUID
	**/

	public String getFrameOfReferenceUID(){
		return frameOfReferenceUID;
	}

	/**
	* Sets the value of frameOfReferenceUID attribue
	**/

	public void setFrameOfReferenceUID(String frameOfReferenceUID){
		this.frameOfReferenceUID = frameOfReferenceUID;
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
	* Position or laterality of (paired) body part examined.	**/
	private String laterality;
	/**
	* Retreives the value of laterality attribute
	* @return laterality
	**/

	public String getLaterality(){
		return laterality;
	}

	/**
	* Sets the value of laterality attribue
	**/

	public void setLaterality(String laterality){
		this.laterality = laterality;
	}
	
		/**
	* Type of equipment that originally acquired the data used to create the images in this series.	**/
	private String modality;
	/**
	* Retreives the value of modality attribute
	* @return modality
	**/

	public String getModality(){
		return modality;
	}

	/**
	* Sets the value of modality attribue
	**/

	public void setModality(String modality){
		this.modality = modality;
	}
	
		/**
	* User-defined description of the conditions under which the (imaging) Series was performed.	**/
	private String protocolName;
	/**
	* Retreives the value of protocolName attribute
	* @return protocolName
	**/

	public String getProtocolName(){
		return protocolName;
	}

	/**
	* Sets the value of protocolName attribue
	**/

	public void setProtocolName(String protocolName){
		this.protocolName = protocolName;
	}
	
		/**
	* Date the Series started.	**/
	private java.util.Date seriesDate;
	/**
	* Retreives the value of seriesDate attribute
	* @return seriesDate
	**/

	public java.util.Date getSeriesDate(){
		return seriesDate;
	}

	/**
	* Sets the value of seriesDate attribue
	**/

	public void setSeriesDate(java.util.Date seriesDate){
		this.seriesDate = seriesDate;
	}
	
		/**
	* User-provided text description of the imaging series.	**/
	private String seriesDescription;
	/**
	* Retreives the value of seriesDescription attribute
	* @return seriesDescription
	**/

	public String getSeriesDescription(){
		return seriesDescription;
	}

	/**
	* Sets the value of seriesDescription attribue
	**/

	public void setSeriesDescription(String seriesDescription){
		this.seriesDescription = seriesDescription;
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
	* A number that identifies this Series.	**/
	private Integer seriesNumber;
	/**
	* Retreives the value of seriesNumber attribute
	* @return seriesNumber
	**/

	public Integer getSeriesNumber(){
		return seriesNumber;
	}

	/**
	* Sets the value of seriesNumber attribue
	**/

	public void setSeriesNumber(Integer seriesNumber){
		this.seriesNumber = seriesNumber;
	}
	
		/**
	* UID of common synchronization environment.  A set of equipment may share a common acquisition synchronization environment.	**/
	private String synchronizationFrameOfReferenceUID;
	/**
	* Retreives the value of synchronizationFrameOfReferenceUID attribute
	* @return synchronizationFrameOfReferenceUID
	**/

	public String getSynchronizationFrameOfReferenceUID(){
		return synchronizationFrameOfReferenceUID;
	}

	/**
	* Sets the value of synchronizationFrameOfReferenceUID attribue
	**/

	public void setSynchronizationFrameOfReferenceUID(String synchronizationFrameOfReferenceUID){
		this.synchronizationFrameOfReferenceUID = synchronizationFrameOfReferenceUID;
	}
	
	/**
	* An associated gov.nih.nci.ncia.domain.Image object's collection 
	**/
			
	private Collection<Image> imageCollection;
	/**
	* Retreives the value of imageCollection attribue
	* @return imageCollection
	**/

	public Collection<Image> getImageCollection(){
		return imageCollection;
	}

	/**
	* Sets the value of imageCollection attribue
	**/

	public void setImageCollection(Collection<Image> imageCollection){
		this.imageCollection = imageCollection;
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
	* An associated gov.nih.nci.ncia.domain.Annotation object's collection 
	**/
			
	private Collection<Annotation> annotationCollection;
	/**
	* Retreives the value of annotationCollection attribue
	* @return annotationCollection
	**/

	public Collection<Annotation> getAnnotationCollection(){
		return annotationCollection;
	}

	/**
	* Sets the value of annotationCollection attribue
	**/

	public void setAnnotationCollection(Collection<Annotation> annotationCollection){
		this.annotationCollection = annotationCollection;
	}
		
	/**
	* An associated gov.nih.nci.ncia.domain.Equipment object
	**/
			
	private Equipment equipment;
	/**
	* Retreives the value of equipment attribue
	* @return equipment
	**/
	
	public Equipment getEquipment(){
		return equipment;
	}
	/**
	* Sets the value of equipment attribue
	**/

	public void setEquipment(Equipment equipment){
		this.equipment = equipment;
	}
			
	/**
	* Compares <code>obj</code> to it self and returns true if they both are same
	*
	* @param obj
	**/
	public boolean equals(Object obj)
	{
		if(obj instanceof Series) 
		{
			Series c =(Series)obj; 			 
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