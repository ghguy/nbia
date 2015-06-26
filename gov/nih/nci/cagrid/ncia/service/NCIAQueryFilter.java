/**
 * 
 */
package gov.nih.nci.cagrid.ncia.service;

import gov.nih.nci.cagrid.cqlquery.Association;
import gov.nih.nci.cagrid.cqlquery.CQLQuery;
import gov.nih.nci.cagrid.cqlquery.LogicalOperator;
import gov.nih.nci.cagrid.cqlquery.Predicate;
import gov.nih.nci.ncia.ImageFilesProcessor;
import gov.nih.nci.ncia.domain.TrialDataProvenance;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.Group;
import gov.nih.nci.security.authorization.domainobjects.ProtectionElement;
import gov.nih.nci.security.authorization.domainobjects.ProtectionElementPrivilegeContext;
import gov.nih.nci.security.dao.GroupSearchCriteria;
import gov.nih.nci.security.exceptions.CSConfigurationException;
import gov.nih.nci.security.exceptions.CSException;
import gov.nih.nci.cagrid.cqlquery.Attribute;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.globus.wsrf.encoding.ObjectSerializer;
import org.globus.wsrf.encoding.SerializationException;

/**
 * @author lethai
 * 
 */
public class NCIAQueryFilter {
	private String groupName;
	String delimiter = "//";
	private final String NULL = "null";
	private final String NCIA_PROJECT = "NCIA.PROJECT";
	private final String NCIA_DP_SITE_NAME = "DP_SITE_NAME";

	private final String IMAGE_DOMAIN = "gov.nih.nci.ncia.domain.Image";
	private final String SERIES_DOMAIN = "gov.nih.nci.ncia.domain.Series";
	private final String STUDY_DOMAIN = "gov.nih.nci.ncia.domain.Study";
	private final String PATIENT_DOMAIN = "gov.nih.nci.ncia.domain.Patient";
	private final String PROJECT = "project";
	private final String PATIENT_ID = "patientId";
	private final String SERIES_INSTANCE_UID = "seriesInstanceUID";
	private final String STUDY_INSTANCE_UID = "studyInstanceUID";
	private final String SOP_INSTANCE_UID = "sopInstanceUID";
	private final String SITE_NAME = "siteName";
	private static NCIAQueryFilter thisFilter=null;
	

	private static Logger logger = Logger.getLogger(NCIAQueryFilter.class);
	private List<TrialDataProvenance> tdpList=null;
	
	public List<TrialDataProvenance> getTdpList() {
		return tdpList;
	}
	public void setTdpList(List<TrialDataProvenance> tdpList) {
		this.tdpList = tdpList;
	}
	public NCIAQueryFilter() {		
		
	}
	/**
	 * This method uses csmapi to retrieve PUBLIC-GRID group which contains all
	 * the public collections and sites. Return list of TrialDataProvenance for
	 * public project and site
	 * 
	 * @return List<TrialDataProvenance>
	 */
	public List<TrialDataProvenance> getDataFilter()
			throws CSConfigurationException, CSException, Exception {
		List<TrialDataProvenance> tdp = new ArrayList<TrialDataProvenance>();
		try {
			if(this.tdpList == null){
				logger.info("TDPList is null, retrieve it.............");
				UserProvisioningManager upm = (UserProvisioningManager) SecurityServiceProvider
						.getAuthorizationManager("NCIA");
				Group g = new Group();
				g.setGroupName(this.groupName);
				System.out.println("Group name: " + this.groupName);
				logger.debug("group name: " + this.groupName);
				GroupSearchCriteria gsc = new GroupSearchCriteria(g);
				List<Group> pgrslt = upm.getObjects(gsc);
				Set peg;
				if (pgrslt.size() > 0) {
					Long publicGroupId = ((Group) pgrslt.get(0)).getGroupId();
					peg = upm
							.getProtectionElementPrivilegeContextForGroup(publicGroupId
									.toString());
				} else {
					throw new Exception("Couldnot find group name: "
							+ this.groupName);
				}
	
				Iterator itrpeg = peg.iterator();
	
				while (itrpeg.hasNext()) {
					ProtectionElement pe = ((ProtectionElementPrivilegeContext) itrpeg
							.next()).getProtectionElement();
					TrialDataProvenance tdptemp = processProtectionElement(pe);
					
					if (tdptemp != null){
						tdp.add(tdptemp);
					}
				}
				this.tdpList = tdp;
				return this.tdpList;
			}else{
				return this.tdpList;
			}
		} catch (CSConfigurationException cse) {
			logger.error("CSMConfigurationExcepion: Error getting csm data:",
					cse);
			throw new CSConfigurationException("Error getting data to filter: "
					+ cse.getMessage(), cse);
		} catch (CSException cse) {
			logger.error("CSException: Error getting csm data:", cse);
			throw new CSException("Error getting data to filter: "
					+ cse.getMessage(), cse);
		} catch (Exception e) {
			logger.error("Error getting csm data: ", e);
			throw new Exception("Error getting data to filter: "
					+ e.getMessage(), e);
		}
	}

	private TrialDataProvenance processProtectionElement(ProtectionElement pe) {
		String att = pe.getAttribute();
		String pename = pe.getProtectionElementName();
		int pos = att.indexOf(delimiter);
		TrialDataProvenance tdptemp = null;
		if (pos < 0) {
			if (att.equalsIgnoreCase(NCIA_PROJECT)) {
				if (pename.substring(5) != null
						&& !pename.substring(5).equalsIgnoreCase(NULL)) {
					tdptemp = new TrialDataProvenance();
					tdptemp.setProject(pename.substring(5));
				}
			} else if ((att.equalsIgnoreCase("NCIA.DP_SITE_NAME")
					|| (att.equalsIgnoreCase(NCIA_DP_SITE_NAME))) && (pename.substring(5) != null
							&& !pename.substring(5).equalsIgnoreCase(NULL))) {				
					tdptemp = new TrialDataProvenance();
					tdptemp.setSiteName(pename.substring(5));				
			}
		} else if (pos > 0) {
			String temp = att.substring(0, pos);
			String temp2 = att.substring(pos + 2);
			int p = pename.indexOf(delimiter);
			if (temp.equalsIgnoreCase(NCIA_PROJECT)) {
				String projectValue = (pename.substring(5, p)).trim();
				if (projectValue != null
						&& !projectValue.equalsIgnoreCase(NULL)) {
					tdptemp = new TrialDataProvenance();
					tdptemp.setProject(projectValue);
				}
			} else if (temp.equalsIgnoreCase(NCIA_DP_SITE_NAME)) {
				String siteValue = (pename.substring(5, p)).trim();
				if (siteValue != null && !siteValue.equalsIgnoreCase(NULL)) {
					if (tdptemp == null) {
						tdptemp = new TrialDataProvenance();
					}
					tdptemp.setSiteName(siteValue);
				}
			}
			if (temp2.equalsIgnoreCase(NCIA_PROJECT)) {
				String projectValue = (pename.substring(p + 2)).trim();
				if (projectValue != null
						&& !projectValue.equalsIgnoreCase(NULL)) {
					if (tdptemp == null) {
						tdptemp = new TrialDataProvenance();
					}
					tdptemp.setProject(projectValue);
				}
			} else if (temp2.equalsIgnoreCase(NCIA_DP_SITE_NAME)) {
				String siteValue = (pename.substring(p + 2)).trim();
				if (siteValue != null && !siteValue.equalsIgnoreCase(NULL)) {
					if (tdptemp == null) {
						tdptemp = new TrialDataProvenance();
					}
					tdptemp.setSiteName(siteValue);
				}
			}
		}
		return tdptemp;
	}

	/**
	 * This method converts the TrialDataProvenance list into CQL where clause
	 * 
	 * @param List
	 *            <TrialDataProvenance>
	 * @return Group
	 */
	public gov.nih.nci.cagrid.cqlquery.Group convertTDPToCQL(
			List<TrialDataProvenance> tdp) {

		gov.nih.nci.cagrid.cqlquery.Group tdpGroup = new gov.nih.nci.cagrid.cqlquery.Group();
		tdpGroup.setLogicRelation(LogicalOperator.AND);
		gov.nih.nci.cagrid.cqlquery.Group attGroupArray[] = new gov.nih.nci.cagrid.cqlquery.Group[tdp
				.size()];

		gov.nih.nci.cagrid.cqlquery.Group attGroup = new gov.nih.nci.cagrid.cqlquery.Group();
		attGroup.setLogicRelation(LogicalOperator.OR);

		for (int i = 0; i < tdp.size(); i++) {
			gov.nih.nci.cagrid.cqlquery.Group g = null;
			Attribute tdpA[] = null;
			Attribute projectAtt = null;
			Attribute siteAtt = null;

			String project = tdp.get(i).getProject();
			String site = tdp.get(i).getSiteName();

			logger.debug("i: " + i + " project: " + project + "===== site: "
					+ site + "=======");
			if (project != null && (!project.equalsIgnoreCase(NULL))
					&& project.length() > 0) {
				projectAtt = new Attribute();
				projectAtt.setName("project");
				projectAtt.setPredicate(Predicate.EQUAL_TO);
				projectAtt.setValue(project);
			}

			if (site != null && (!site.equalsIgnoreCase(NULL))
					&& site.length() > 0) {
				siteAtt = new Attribute();
				siteAtt.setName("siteName");
				siteAtt.setPredicate(Predicate.EQUAL_TO);
				siteAtt.setValue(site);
			}

			if (projectAtt != null && siteAtt != null) {
				tdpA = new Attribute[2];
				tdpA[0] = projectAtt;
				tdpA[1] = siteAtt;
				g = new gov.nih.nci.cagrid.cqlquery.Group();
				g.setAttribute(tdpA);
			} else if (projectAtt != null && siteAtt == null) {
				tdpA = new Attribute[1];
				tdpA[0] = projectAtt;
				g = new gov.nih.nci.cagrid.cqlquery.Group();
				g.setAttribute(tdpA);
			} else if (projectAtt == null && siteAtt != null) {
				tdpA = new Attribute[1];
				tdpA[0] = siteAtt;
				g = new gov.nih.nci.cagrid.cqlquery.Group();
				g.setAttribute(tdpA);
			}

			if (g != null) {
				g.setLogicRelation(LogicalOperator.AND);
				attGroupArray[i] = g;
			}

		}
		attGroup.setGroup(attGroupArray);
		return attGroup;

	}

	/**
	 * This method goes through the hierarchy chain of the object to apply the
	 * right association and group for each one and modify it to include the
	 * filter where clause. The modified CQLQuery is returned because calling
	 * the super class processQuery method
	 * 
	 * @param cqlQuery
	 * @param tdpGroup
	 * @return CQLQuery
	 */

	public CQLQuery applyFilter(CQLQuery cqlQuery,
			gov.nih.nci.cagrid.cqlquery.Group tdpGroup) {
		gov.nih.nci.cagrid.cqlquery.Object o = (gov.nih.nci.cagrid.cqlquery.Object) cqlQuery
				.getTarget();

		Association tdpAssociation = new Association();
		tdpAssociation.setName("gov.nih.nci.ncia.domain.TrialDataProvenance");
		tdpAssociation.setRoleName("dataProvenance");
		tdpAssociation.setGroup(tdpGroup);

		Association seriesAssociation = new Association();
		seriesAssociation.setName(SERIES_DOMAIN);
		seriesAssociation.setRoleName("series");
		Association studyAssociation = new Association();
		studyAssociation.setName(STUDY_DOMAIN);
		studyAssociation.setRoleName("study");

		Association patientAssociation = new Association();
		patientAssociation.setName(PATIENT_DOMAIN);
		patientAssociation.setRoleName("patient");
		if (o.getName().equalsIgnoreCase(IMAGE_DOMAIN)) {
			patientAssociation.setAssociation(tdpAssociation);
			studyAssociation.setAssociation(patientAssociation);
			seriesAssociation.setAssociation(studyAssociation);

			o = this.modifyCQLQueryTarget(cqlQuery, seriesAssociation);
		} else if (o.getName().equalsIgnoreCase(SERIES_DOMAIN)) {
			patientAssociation.setAssociation(tdpAssociation);
			studyAssociation.setAssociation(patientAssociation);

			o = this.modifyCQLQueryTarget(cqlQuery, studyAssociation);
		} else if (o.getName().equalsIgnoreCase(STUDY_DOMAIN)) {
			patientAssociation.setAssociation(tdpAssociation);

			o = this.modifyCQLQueryTarget(cqlQuery, patientAssociation);
		} else if (o.getName().equalsIgnoreCase(PATIENT_DOMAIN)) {
			o = processPatientDomain(cqlQuery, tdpAssociation);

		} else if (o.getName().equalsIgnoreCase(
				"gov.nih.nci.ncia.domain.Annotation")) {
			patientAssociation.setAssociation(tdpAssociation);
			studyAssociation.setAssociation(patientAssociation);
			seriesAssociation.setAssociation(studyAssociation);

			o = this.modifyCQLQueryTarget(cqlQuery, seriesAssociation);
		}

		cqlQuery.setTarget(o);

		try {
			logger.debug(ObjectSerializer.toString(cqlQuery, new QName(
							"http://CQL.caBIG/1/gov.nih.nci.cagrid.CQLQuery",
							"CQLQuery")));
		} catch (SerializationException e) {
			logger.error(e);
		}
		return cqlQuery;
	}

	private gov.nih.nci.cagrid.cqlquery.Object processPatientDomain(
			CQLQuery cqlQuery, Association tdpAssociation) {
		gov.nih.nci.cagrid.cqlquery.Object o = (gov.nih.nci.cagrid.cqlquery.Object) cqlQuery
				.getTarget();
		Association assoc = o.getAssociation();
		Attribute attr = o.getAttribute();
		gov.nih.nci.cagrid.cqlquery.Group group = o.getGroup();

		if (group == null && assoc != null) {
			gov.nih.nci.cagrid.cqlquery.Group patientGroup = new gov.nih.nci.cagrid.cqlquery.Group();
			patientGroup.setLogicRelation(LogicalOperator.AND);
			Association aAA[] = new Association[2];
			aAA[0] = assoc;
			aAA[1] = tdpAssociation;
			patientGroup.setAssociation(aAA);
			o.setAssociation(null);
			o.setGroup(patientGroup);
		} else if (group != null) {
			Association aA[] = group
					.getAssociation();
			LogicalOperator lg = group.getLogicRelation();
			logger.info("Logic value: " + lg.getValue());
			Association aAA[] = null;
			if (lg.getValue().equalsIgnoreCase("OR")) {
				gov.nih.nci.cagrid.cqlquery.Group targetGroup = new gov.nih.nci.cagrid.cqlquery.Group();
				targetGroup.setLogicRelation(LogicalOperator.AND);
				gov.nih.nci.cagrid.cqlquery.Group nestedGroup[] = new gov.nih.nci.cagrid.cqlquery.Group[1];
				nestedGroup[0] = group;
				aAA = new Association[1];
				aAA[0] = tdpAssociation;
				targetGroup.setGroup(nestedGroup);
				targetGroup.setAssociation(aAA);
				o.setGroup(targetGroup);
			} else {
				if (aA != null) {
					int i = aA.length;
					aAA = new Association[i + 1];
					for (int j = 0; j < i; j++) {
						aAA[j] = aA[j];
					}
					aAA[i] = tdpAssociation;
				} else {
					aAA = new Association[1];
					aAA[0] = tdpAssociation;
				}
				group.setAssociation(aAA);
				o.setGroup(group);
			}
		} else if (group == null && assoc == null) {
			if (attr == null) {
				o.setAssociation(tdpAssociation);
			} else {
				Attribute attA[] = new Attribute[1];
				attA[0] = attr;
				gov.nih.nci.cagrid.cqlquery.Group ptGroup = new gov.nih.nci.cagrid.cqlquery.Group();
				ptGroup.setLogicRelation(LogicalOperator.AND);
				Association tdp[] = new Association[1];
				tdp[0] = tdpAssociation;
				ptGroup.setAssociation(tdp);
				ptGroup.setAttribute(attA);
				o.setGroup(ptGroup);
				o.setAttribute(null);
			}
		}
		return o;
	}

	/**
	 * This method modify the input CQLQuery to include the where clause created
	 * in the convertTDPToCQL method and return the modified target
	 * 
	 * @param cqlQuery
	 * @param association
	 * @return Object
	 */

	protected gov.nih.nci.cagrid.cqlquery.Object modifyCQLQueryTarget(
			CQLQuery cqlQuery, Association association) {
		gov.nih.nci.cagrid.cqlquery.Object o = (gov.nih.nci.cagrid.cqlquery.Object) cqlQuery
				.getTarget();
		logger.debug("modifyCQLQuery--- target class name " + o.getName());
		Association assoc = o.getAssociation();
		Attribute attr = o.getAttribute();
		gov.nih.nci.cagrid.cqlquery.Group group = o.getGroup();

		if (group == null && assoc != null) {
			gov.nih.nci.cagrid.cqlquery.Group targetGroup = new gov.nih.nci.cagrid.cqlquery.Group();
			targetGroup.setLogicRelation(LogicalOperator.AND);
			Association assocA[] = new Association[2];
			assocA[0] = assoc;
			assocA[1] = association;
			targetGroup.setAssociation(assocA);
			o.setGroup(targetGroup);
			o.setAssociation(null);
		} else if (group != null) {
			Association aA[] = group
					.getAssociation();
			LogicalOperator lg = group.getLogicRelation();
			logger.debug("Logic value: " + lg.getValue());

			Association aAA[] = null;

			if (lg.getValue().equalsIgnoreCase("OR")) {
				gov.nih.nci.cagrid.cqlquery.Group targetGroup = new gov.nih.nci.cagrid.cqlquery.Group();
				targetGroup.setLogicRelation(LogicalOperator.AND);
				gov.nih.nci.cagrid.cqlquery.Group nestedGroup[] = new gov.nih.nci.cagrid.cqlquery.Group[1];
				nestedGroup[0] = group;
				aAA = new Association[1];
				aAA[0] = association;
				targetGroup.setGroup(nestedGroup);
				targetGroup.setAssociation(aAA);
				o.setGroup(targetGroup);
			} else {
				if (aA != null) {
					int i = aA.length;
					aAA = new Association[i + 1];
					for (int j = 0; j < i; j++) {
						aAA[j] = aA[j];
					}
					aAA[i] = association;
				} else {
					aAA = new Association[1];
					aAA[0] = association;
				}
				group.setAssociation(aAA);
				o.setGroup(group);
			}
		} else if (group == null && assoc == null) {
			if (attr == null) {
				o.setAssociation(association);
			} else {
				gov.nih.nci.cagrid.cqlquery.Group targetGroup = new gov.nih.nci.cagrid.cqlquery.Group();
				targetGroup.setLogicRelation(LogicalOperator.AND);
				Association assocA[] = new Association[1];
				assocA[0] = association;
				Attribute attA[] = new Attribute[1];
				attA[0] = attr;
				targetGroup.setAssociation(assocA);
				targetGroup.setAttribute(attA);
				o.setGroup(targetGroup);
				o.setAttribute(null);
			}
		}
		return o;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public boolean isPublic (Map<String,String> hm, List<TrialDataProvenance> tdpList) throws Exception{
		boolean inPublicGroup=false;
		ImageFilesProcessor ifp = new ImageFilesProcessor();
		Set<String> keys = hm.keySet();
		long start = System.nanoTime();  		
  		
		if( keys.contains(PROJECT)){
			String project = hm.get(PROJECT);
			TrialDataProvenance tdp = new TrialDataProvenance();
			tdp.setProject(project);
			if(keys.contains(SITE_NAME)){
				String siteName = hm.get(SITE_NAME);
				tdp.setSiteName(siteName);
				inPublicGroup = isPublic(tdp, tdpList);
			}else{
				inPublicGroup = isPublic(tdp, tdpList);
			}
		}else if(keys.contains(PATIENT_ID)){
			String patientId = hm.get(PATIENT_ID);
			// retrieve trial data proveance by patientId
			TrialDataProvenance tdp = ifp.getTDPByPatientId(patientId);
			if(tdp == null){
				return false;
			}
			inPublicGroup = isPublic(tdp,tdpList);
		}else if(keys.contains(STUDY_INSTANCE_UID)){
			String studyInstanceUID = hm.get(STUDY_INSTANCE_UID);
			// retrieve trial data proveance by patientId
			TrialDataProvenance tdp = ifp.getTDPByStudyInstanceUID(studyInstanceUID);
			if(tdp == null){
				return false;
			}
			inPublicGroup = isPublic(tdp,tdpList);
			
		}else if(keys.contains(SERIES_INSTANCE_UID)){
			String seriesInstanceUID = hm.get(SERIES_INSTANCE_UID);
			// retrieve trial data proveance by patientId
			TrialDataProvenance tdp = ifp.getTDPBySeriesInstanceUID(seriesInstanceUID);
			if(tdp == null){
				return false;
			}
			inPublicGroup = isPublic(tdp,tdpList);
			
			
		}else if(keys.contains(SOP_INSTANCE_UID)){
			TrialDataProvenance tdp = ifp.getTDPBySopInstanceUID(hm.get(SOP_INSTANCE_UID));
			if(tdp == null){
				return false;
			}
			inPublicGroup = isPublic(tdp,tdpList);
		}
		long end = System.nanoTime();	
  		logger.info("\n=================== total time in isPublic " + (end - start)/100000 + " milli seconds");
		logger.info("is public " + inPublicGroup);
			
		return inPublicGroup;
	}
	
	
	public boolean isPublic(TrialDataProvenance tdp, List<TrialDataProvenance> tdpList){
		
		String queryProject = tdp.getProject();
		String querySiteName = tdp.getSiteName();
		logger.info("query project and site: " + queryProject + " " + querySiteName);
		  boolean isProject = false;
		  boolean isSite = false;
		  logger.info("public list: size = " + tdpList.size());
		  for(int i=0, n=tdpList.size(); i<n; i++){
			  TrialDataProvenance t = tdpList.get(i);
			  logger.info("public trialdataprovenance; project: " + t.getProject() + " site name: " + t.getSiteName());
			  
			  String site = t.getSiteName();
			  String project = t.getProject();
			  if(project != null && project.equals(queryProject)){
				  isProject = true;
				  if(site == null ){		
					  if(querySiteName == null){
						  System.out.println("sites are null");
						  isSite=true;
						  break;
					  }
					  
				  }
				  else {
					  if(querySiteName == null){
						  System.out.println("project same and site is not the same");
						  isSite = false;					  
					  }else if(site.equals(querySiteName)){
						  isSite = true;
						  break;
					  }
				  }
			  }
		  }
		  return isProject && isSite;
	}
	public static NCIAQueryFilter getInstance(){
		if(thisFilter == null){
			thisFilter = new NCIAQueryFilter();
		}
		return thisFilter;
	}
}
