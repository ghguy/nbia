package gov.nih.nci.ncia.domain;

import java.util.Collection;
import java.io.Serializable;
	/**
	* A Clinical Trial Protocol identifies the investigational Protocol in which the Subject has been enrolled.The Protocol has a Protocol Identifier and Protocol Name.	**/
public class ClinicalTrialProtocol  implements Serializable
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
	* Identifier for the noted protocol, used by the Clinical Trial Sponsor to uniquely identify the investigational protocol.	**/
	private String protocolId;
	/**
	* Retreives the value of protocolId attribute
	* @return protocolId
	**/

	public String getProtocolId(){
		return protocolId;
	}

	/**
	* Sets the value of protocolId attribue
	**/

	public void setProtocolId(String protocolId){
		this.protocolId = protocolId;
	}
	
		/**
	* The name or title of the clinical trial protocol.	**/
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
	* An associated gov.nih.nci.ncia.domain.ClinicalTrialSubject object's collection 
	**/
			
	private Collection<ClinicalTrialSubject> subjectCollection;
	/**
	* Retreives the value of subjectCollection attribue
	* @return subjectCollection
	**/

	public Collection<ClinicalTrialSubject> getSubjectCollection(){
		return subjectCollection;
	}

	/**
	* Sets the value of subjectCollection attribue
	**/

	public void setSubjectCollection(Collection<ClinicalTrialSubject> subjectCollection){
		this.subjectCollection = subjectCollection;
	}
		
	/**
	* An associated gov.nih.nci.ncia.domain.ClinicalTrialSponsor object
	**/
			
	private ClinicalTrialSponsor trial;
	/**
	* Retreives the value of trial attribue
	* @return trial
	**/
	
	public ClinicalTrialSponsor getTrial(){
		return trial;
	}
	/**
	* Sets the value of trial attribue
	**/

	public void setTrial(ClinicalTrialSponsor trial){
		this.trial = trial;
	}
			
	/**
	* Compares <code>obj</code> to it self and returns true if they both are same
	*
	* @param obj
	**/
	public boolean equals(Object obj)
	{
		if(obj instanceof ClinicalTrialProtocol) 
		{
			ClinicalTrialProtocol c =(ClinicalTrialProtocol)obj; 			 
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