package gov.nih.nci.ncia.domain;


import java.io.Serializable;
	/**
	* A Clinical Trial Subject identifies the Patient who is enrolled as a Subject in the investigational Protocol.	**/
public class ClinicalTrialSubject  implements Serializable
{
	/**
	* An attribute to allow serialization of the domain objects
	*/
	private static final long serialVersionUID = 1234567890L;

	
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
	* The assigned identifier for the patient as a clinical trial subject.	**/
	private String subjectId;
	/**
	* Retreives the value of subjectId attribute
	* @return subjectId
	**/

	public String getSubjectId(){
		return subjectId;
	}

	/**
	* Sets the value of subjectId attribue
	**/

	public void setSubjectId(String subjectId){
		this.subjectId = subjectId;
	}
	
		/**
	* Identifies the patient as a clinical trial subject for blinded evaluations.	**/
	private String subjectReadingId;
	/**
	* Retreives the value of subjectReadingId attribute
	* @return subjectReadingId
	**/

	public String getSubjectReadingId(){
		return subjectReadingId;
	}

	/**
	* Sets the value of subjectReadingId attribue
	**/

	public void setSubjectReadingId(String subjectReadingId){
		this.subjectReadingId = subjectReadingId;
	}
	
	/**
	* An associated gov.nih.nci.ncia.domain.ClinicalTrialProtocol object
	**/
			
	private ClinicalTrialProtocol protocol;
	/**
	* Retreives the value of protocol attribue
	* @return protocol
	**/
	
	public ClinicalTrialProtocol getProtocol(){
		return protocol;
	}
	/**
	* Sets the value of protocol attribue
	**/

	public void setProtocol(ClinicalTrialProtocol protocol){
		this.protocol = protocol;
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
	* An associated gov.nih.nci.ncia.domain.ClinicalTrialSite object
	**/
			
	private ClinicalTrialSite site;
	/**
	* Retreives the value of site attribue
	* @return site
	**/
	
	public ClinicalTrialSite getSite(){
		return site;
	}
	/**
	* Sets the value of site attribue
	**/

	public void setSite(ClinicalTrialSite site){
		this.site = site;
	}
			
	/**
	* Compares <code>obj</code> to it self and returns true if they both are same
	*
	* @param obj
	**/
	public boolean equals(Object obj)
	{
		if(obj instanceof ClinicalTrialSubject) 
		{
			ClinicalTrialSubject c =(ClinicalTrialSubject)obj; 			 
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