/**
 *
 */
package gov.nih.nci.ncia;

import gov.nih.nci.cagrid.cqlquery.CQLQuery;
import gov.nih.nci.cagrid.cqlresultset.CQLQueryResults;
import gov.nih.nci.cagrid.data.MalformedQueryException;
import gov.nih.nci.cagrid.data.QueryProcessingException;
import gov.nih.nci.cagrid.sdkquery4.processor.SDK4QueryProcessor;
import gov.nih.nci.cagrid.ncia.service.NCIACoreServiceConfiguration;
import gov.nih.nci.ncia.domain.TrialDataProvenance;

import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.log4j.Logger;
import org.globus.wsrf.encoding.ObjectSerializer;
import org.globus.wsrf.encoding.SerializationException;

/**
 * @author lethai
 *
 */
public class NCIAQueryProcessor extends SDK4QueryProcessor {
	private static Logger logger = Logger.getLogger(NCIAQueryProcessor.class);
	/*
	 * This method modifies the CQLQuery to apply the filtering to return only public data.
	 * @see gov.nih.nci.cagrid.sdkquery4.processor.SDK4QueryProcessor#processQuery(gov.nih.nci.cagrid.cqlquery.CQLQuery)
	 */

	public CQLQueryResults processQuery(CQLQuery cqlQuery) throws MalformedQueryException, QueryProcessingException {
		CQLQueryResults results = null;
		System.out.println("NCIAQueryProcessor is to use to return public data - no filter at this time");
		CustomizedCQLQuery c = new CustomizedCQLQuery();
		NCIAQueryFilter filter=null;
		try{
			 filter = NCIAQueryFilter.getInstance();
			String publicGroupName = NCIACoreServiceConfiguration.getConfiguration().getNciaPublicGroup();
			filter.setGroupName(publicGroupName);
			List<TrialDataProvenance> tdp = filter.getDataFilter();



	  		long start = System.nanoTime();
	  		//Map<String,String> hm = c.parseCQLQuery(cqlQuery);
	  		long end = System.nanoTime();
	  		System.out.println("\n=================== total time to parseCQLQuery is " + (end - start)/1000000 + " milli seconds");

	  		//using jdbc to retrieve tdp
	  		if(tdp.size() == 0){
  				return new CQLQueryResults();
	  		}
	  		else
  			if(tdp.size() > 0){
				gov.nih.nci.cagrid.cqlquery.Group tdpGroup = filter.convertTDPToCQL(tdp);
				cqlQuery = filter.applyFilter(cqlQuery, tdpGroup);
	  		}
	  		start = System.currentTimeMillis();

			logger.info("cqlQuery : ..... " +ObjectSerializer.toString(cqlQuery,
						new QName("http://CQL.caBIG/1/gov.nih.nci.cagrid.CQLQuery", "CQLQuery")));

			results = super.processQuery(cqlQuery);
			end = System.currentTimeMillis();
			logger.info("\n=================== total time to get metadata is " + (end - start) + " milli seconds");

		}catch (SerializationException e) {
			logger.error("SerializationException: ", e);
		} 	catch(Exception e){
			throw new QueryProcessingException(e.getMessage(), e);
		}
		c = null;
		filter = null;

		return results;
	}
}
