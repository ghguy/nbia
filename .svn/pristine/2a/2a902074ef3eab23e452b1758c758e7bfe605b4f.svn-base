/**
 *
 */
package gov.nih.nci.ncia;

import gov.nih.nci.cagrid.cqlquery.Association;
import gov.nih.nci.cagrid.cqlquery.Attribute;
import gov.nih.nci.cagrid.cqlquery.CQLQuery;
import gov.nih.nci.cagrid.cqlquery.LogicalOperator;
import gov.nih.nci.cagrid.cqlquery.Predicate;
import gov.nih.nci.ncia.domain.TrialDataProvenance;
import gov.nih.nci.ncia.griddao.TrialDataProvenanceDAO;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.Group;
import gov.nih.nci.security.authorization.domainobjects.ProtectionElement;
import gov.nih.nci.security.authorization.domainobjects.ProtectionElementPrivilegeContext;
import gov.nih.nci.security.dao.GroupSearchCriteria;
import gov.nih.nci.security.exceptions.CSConfigurationException;
import gov.nih.nci.security.exceptions.CSException;

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
 * nimpy: The lifecycle of this object is not quite clear.  Need to be clearer
 * about what values should be cached in the singleton instance, and
 * what is/should be recomputed per method invocation
 *
 * @author lethai
 */
public class NCIAQueryFilter {


	public void setGroupName(String groupName) {
		this.groupName = groupName;
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

			return tdp;

		} catch (Exception e) {
			logger.error("Error getting csm data: ", e);
			throw new Exception("Error getting data to filter: "
					+ e.getMessage(), e);
		}
	}


	/**
	 * This method converts the TrialDataProvenance list into CQL where clause
	 */
	public static gov.nih.nci.cagrid.cqlquery.Group convertTDPToCQL(List<TrialDataProvenance> authorizedTdpList) {

		List<gov.nih.nci.cagrid.cqlquery.Group> projectAndSiteGroups =
			new ArrayList<gov.nih.nci.cagrid.cqlquery.Group>();

		for (TrialDataProvenance authorizedTdp : authorizedTdpList) {
			if(isMangledTdp(authorizedTdp)) {
				System.err.println("Skipping:"+authorizedTdp.getProject()+","+authorizedTdp.getSiteName());
				continue;
			}

			Attribute projectAtt = new Attribute();
			projectAtt.setName("project");
			projectAtt.setPredicate(Predicate.EQUAL_TO);
			projectAtt.setValue(authorizedTdp.getProject());

			Attribute siteAtt = new Attribute();
			siteAtt.setName("siteName");
			siteAtt.setPredicate(Predicate.EQUAL_TO);
			siteAtt.setValue(authorizedTdp.getSiteName());

			Attribute[] projectAndSiteAttributesArr = new Attribute[2];
			projectAndSiteAttributesArr[0] = projectAtt;
			projectAndSiteAttributesArr[1] = siteAtt;

			gov.nih.nci.cagrid.cqlquery.Group projectAndSiteGroup = new gov.nih.nci.cagrid.cqlquery.Group();
			projectAndSiteGroup.setAttribute(projectAndSiteAttributesArr);
			projectAndSiteGroup.setLogicRelation(LogicalOperator.AND);

			projectAndSiteGroups.add(projectAndSiteGroup);
		}

		gov.nih.nci.cagrid.cqlquery.Group oneOfTheProjectSitePairsGroup = new gov.nih.nci.cagrid.cqlquery.Group();
		oneOfTheProjectSitePairsGroup.setLogicRelation(LogicalOperator.OR);
		oneOfTheProjectSitePairsGroup.setGroup(projectAndSiteGroups.toArray(new gov.nih.nci.cagrid.cqlquery.Group[]{}));
		return oneOfTheProjectSitePairsGroup;
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

	public static CQLQuery applyFilter(CQLQuery cqlQuery,
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

			o = modifyCQLQueryTarget(cqlQuery, seriesAssociation);
		} else if (o.getName().equalsIgnoreCase(SERIES_DOMAIN)) {
			patientAssociation.setAssociation(tdpAssociation);
			studyAssociation.setAssociation(patientAssociation);

			o = modifyCQLQueryTarget(cqlQuery, studyAssociation);
		} else if (o.getName().equalsIgnoreCase(STUDY_DOMAIN)) {
			patientAssociation.setAssociation(tdpAssociation);

			o = modifyCQLQueryTarget(cqlQuery, patientAssociation);
		} else if (o.getName().equalsIgnoreCase(PATIENT_DOMAIN)) {
			o = processPatientDomain(cqlQuery, tdpAssociation);

		} else if (o.getName().equalsIgnoreCase(
				"gov.nih.nci.ncia.domain.Annotation")) {
			patientAssociation.setAssociation(tdpAssociation);
			studyAssociation.setAssociation(patientAssociation);
			seriesAssociation.setAssociation(studyAssociation);

			o = modifyCQLQueryTarget(cqlQuery, seriesAssociation);
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


	/**
	 * The map contain project=,site=,patient=,series=,sop= and
	 * this will figure out if that artifact is public, using
	 * the easiest artifact first.
	 */
	public boolean isPublic(Map<String,String> hm,
			                List<TrialDataProvenance> tdpList) throws Exception{
		boolean inPublicGroup=false;
        TrialDataProvenanceDAO trialDataProvenanceDAO = new TrialDataProvenanceDAO();
		Set<String> keys = hm.keySet();
		long start = System.nanoTime();

		if( keys.contains(PROJECT)){
			String project = hm.get(PROJECT);
			String siteName = hm.get(SITE_NAME);

			return isProjectPublic(project,
					               siteName,
					               tdpList);
		}
		else
		if(keys.contains(PATIENT_ID)){
			String patientId = hm.get(PATIENT_ID);
			// retrieve trial data proveance by patientId
			TrialDataProvenance tdp = trialDataProvenanceDAO.getTDPByPatientId(patientId);
			if(tdp == null){
				return false;
			}
			inPublicGroup = isPublic(tdp,tdpList);
		}
		else
		if(keys.contains(STUDY_INSTANCE_UID)){
			String studyInstanceUID = hm.get(STUDY_INSTANCE_UID);
			// retrieve trial data proveance by patientId
			TrialDataProvenance tdp = trialDataProvenanceDAO.getTDPByStudyInstanceUID(studyInstanceUID);
			if(tdp == null){
				return false;
			}
			inPublicGroup = isPublic(tdp,tdpList);

		}
		else
		if(keys.contains(SERIES_INSTANCE_UID)){
			String seriesInstanceUID = hm.get(SERIES_INSTANCE_UID);
			// retrieve trial data proveance by patientId
			TrialDataProvenance tdp = trialDataProvenanceDAO.getTDPBySeriesInstanceUID(seriesInstanceUID);
			if(tdp == null){
				return false;
			}
			inPublicGroup = isPublic(tdp,tdpList);


		}
		else
		if(keys.contains(SOP_INSTANCE_UID)){
			TrialDataProvenance tdp = trialDataProvenanceDAO.getTDPBySopInstanceUID(hm.get(SOP_INSTANCE_UID));
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

	////////////////////////////////////PRIVATE/////////////////////////////////////////

	private String groupName;

	private final static String delimiter = "//";
	private final static String NULL = "null";
	private final static String NCIA_PROJECT = "NCIA.PROJECT";
	private final static String NCIA_DP_SITE_NAME = "DP_SITE_NAME";
	private final static String IMAGE_DOMAIN = "gov.nih.nci.ncia.domain.Image";
	private final static String SERIES_DOMAIN = "gov.nih.nci.ncia.domain.Series";
	private final static String STUDY_DOMAIN = "gov.nih.nci.ncia.domain.Study";
	private final static String PATIENT_DOMAIN = "gov.nih.nci.ncia.domain.Patient";
	private final static String PROJECT = "project";
	private final static String PATIENT_ID = "patientId";
	private final static String SERIES_INSTANCE_UID = "seriesInstanceUID";
	private final static String STUDY_INSTANCE_UID = "studyInstanceUID";
	private final static String SOP_INSTANCE_UID = "sopInstanceUID";
	private final static String SITE_NAME = "siteName";

	private static NCIAQueryFilter thisFilter=null;


	private static Logger logger = Logger.getLogger(NCIAQueryFilter.class);

	private NCIAQueryFilter() {
	}

	private boolean isProjectPublic(String project,
			                        String siteName,
			                        List<TrialDataProvenance> tdpList) {
		TrialDataProvenance tdp = new TrialDataProvenance();
		tdp.setProject(project);
		if(siteName!=null){
			tdp.setSiteName(siteName);
			return isPublic(tdp, tdpList);
		}else{
			return isPublic(tdp, tdpList);
		}
	}

	private static gov.nih.nci.cagrid.cqlquery.Object processPatientDomain(
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

	private static String stripNciaPrefix(String peName) {
		return peName.substring(5);
	}

	private TrialDataProvenance processProtectionElementNoDelimeter(ProtectionElement pe) {
		String att = pe.getAttribute();
		String pename = pe.getProtectionElementName();
		TrialDataProvenance tdptemp = null;

		if (att.equalsIgnoreCase(NCIA_PROJECT)) {
			String project = stripNciaPrefix(pename);
			if(project==null) {
				throw new RuntimeException("null project name:"+pename);
			}
			tdptemp = new TrialDataProvenance();
			tdptemp.setProject(project);
		}

		return tdptemp;
	}

	private static TrialDataProvenance processProtectionElementWithDelimeter(ProtectionElement pe) {
		String att = pe.getAttribute();
		String pename = pe.getProtectionElementName();
		int pos = att.indexOf(delimiter);
		TrialDataProvenance tdptemp = null;

		final int NCIA_PREFIX_OFFSET = 5;
		String beforeDelimeterinAttribute = att.substring(0, pos);
		String afterDelimeterinAttribute = att.substring(pos + 2);
		int delimeterPositionInPeName = pename.indexOf(delimiter);

		if (beforeDelimeterinAttribute.equalsIgnoreCase(NCIA_PROJECT)) {
			String projectValue = pename.substring(NCIA_PREFIX_OFFSET, delimeterPositionInPeName).trim();
			if(projectValue==null) {
				throw new RuntimeException("null projectname:"+pename);
			}

			tdptemp = new TrialDataProvenance();
			tdptemp.setProject(projectValue);
		}
		else {
			throw new RuntimeException("attribute should start with ncia.project");
		}

		if (afterDelimeterinAttribute.equalsIgnoreCase(NCIA_DP_SITE_NAME)) {
			String siteValue = (pename.substring(delimeterPositionInPeName + 2)).trim();
			if(siteValue==null) {
				throw new RuntimeException("null siteValue:"+pename);
			}
			if (tdptemp == null) {
				tdptemp = new TrialDataProvenance();
			}
			tdptemp.setSiteName(siteValue);
		}
		else {
			throw new RuntimeException("attribute should end with ncia.dp_site_name");
		}
		return tdptemp;
	}

	/**
	 * Given a CSM Protection Element, interpret our screwy smoke signals in the PE
	 * to construct a TrialDataProvenance object that would match up.
	 */
	private static TrialDataProvenance processProtectionElement(ProtectionElement pe) {
		String att = pe.getAttribute();
		int pos = att.indexOf(delimiter);
		if (pos < 0) {
			return null;
		}
		else  {
			return processProtectionElementWithDelimeter(pe);
		}
	}



	/**
	 * This method modify the input CQLQuery to include the where clause created
	 * in the convertTDPToCQL method and return the modified target
	 *
	 * @param cqlQuery
	 * @param association
	 * @return Object
	 */

	private static gov.nih.nci.cagrid.cqlquery.Object modifyCQLQueryTarget(
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

	private static boolean hasProjectAndSite(Attribute projectAtt, Attribute siteAtt) {
		return projectAtt != null && siteAtt != null;
	}

	private static boolean hasProjectAndNoSite(Attribute projectAtt, Attribute siteAtt) {
		return projectAtt != null && siteAtt == null;
	}

	private static boolean hasSiteAndNoProject(Attribute projectAtt, Attribute siteAtt) {
		return projectAtt == null && siteAtt != null;
	}

	/**
	 * nimpy: this comparison to the string 'null' is probably wrong, but
	 * not going to touch this legacy code just right now.
	 */
	private static boolean isNotEmptyOrEqualToActualNullString(String string) {
		return string != null &&
		       !string.equalsIgnoreCase(NULL)
		       && string.length() > 0;
	}

	private static boolean isMangledTdp(TrialDataProvenance tdp) {
		return isEmpty(tdp.getProject()) ||
		       isEmpty(tdp.getSiteName());
	}

	private static boolean isEmpty(String s) {
		return s==null || s.length()==0;
	}
}
