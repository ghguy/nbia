package gov.nih.nci.ncia.dynamicsearch.criteria;

import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;

import org.hibernate.criterion.Restrictions;

import gov.nih.nci.ncia.util.SiteData;

import java.util.List;

public class CriteriaForAuthorizedSiteData {

	public void setAuthorizedSiteData(String tableAlias, Criteria criteria, List<SiteData> sites)
	{
		Disjunction disjunction = Restrictions.disjunction();

		if(tableAlias != null)
		{
			for (SiteData sd : sites)
			{
				Conjunction con = new Conjunction();
				con.add(Restrictions.eq(tableAlias+".dpSiteName",sd.getSiteName()));
				con.add(Restrictions.eq(tableAlias+".project", sd.getCollection()));
				disjunction.add(con);
			}
			criteria.add(disjunction);
		}
		else
		{
			for (SiteData sd : sites)
			{
				Conjunction con = new Conjunction();
				con.add(Restrictions.eq("dpSiteName",sd.getSiteName()));
				con.add(Restrictions.eq("project", sd.getCollection()));
				disjunction.add(con);
			}
			criteria.add(disjunction);
		}
	}
	
	public void setSeriesSecurityGroups(String tableAlias, Criteria criteria, List<String> securityGroups)
	{
		Conjunction con = new Conjunction();
		if(tableAlias != null)
		{
			if (securityGroups != null && securityGroups.size() != 0)
			{	
				Disjunction disjunction = Restrictions.disjunction();
				disjunction.add(Restrictions.isNull(tableAlias+".securityGroup"));
				disjunction.add(Restrictions.in(tableAlias+".securityGroup", securityGroups));
				con.add(disjunction);
				criteria.add(con);
			}
			else
			{
				criteria.add(Restrictions.isNull(tableAlias+".securityGroup"));
			}
			
		}
		else
		{
			if (securityGroups != null && securityGroups.size() != 0)
			{	
				Disjunction disjunction = Restrictions.disjunction();
				disjunction.add(Restrictions.isNull("securityGroup"));
				disjunction.add(Restrictions.in("securityGroup", securityGroups));
				con.add(disjunction);
				criteria.add(con);
			}
			else
			{
				criteria.add(Restrictions.isNull("securityGroup"));
			}
		}
	}
}
