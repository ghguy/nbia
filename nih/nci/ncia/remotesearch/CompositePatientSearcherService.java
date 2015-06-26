package gov.nih.nci.ncia.remotesearch;

import gov.nih.nci.ncia.query.DICOMQuery;
import gov.nih.nci.ncia.remotesearch.requests.RemoteSearchForPatientsRequest;
import gov.nih.nci.ncia.search.AbstractPatientSearcherService;
import gov.nih.nci.ncia.search.LocalSearchForPatientsRequest;
import gov.nih.nci.ncia.search.NBIANode;
import gov.nih.nci.ncia.search.PatientSearchResults;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.Future;

/**
 * This implementation is smart enough to deal with a mix of local and remote
 * nodes to search, creating the proper task to deal with local/remote.
 */
public class CompositePatientSearcherService extends AbstractPatientSearcherService  {
		
	/**
	 * {@inheritDoc}
	 * 
	 * <P>Per node this service will execute a local search or a remote search
	 * depending on the node.
	 */
	protected List<Future<PatientSearchResults>> 
        searchNodes(CompletionService<PatientSearchResults> completionService,
	                List<NBIANode> nodesToSearch,
	                DICOMQuery dicomQuery) {
		
		
		List<Future<PatientSearchResults>> futures =  
		    new ArrayList<Future<PatientSearchResults>>();
		
		for(NBIANode nodeToSearch : nodesToSearch) {
			Future<PatientSearchResults> future = null;
			if(nodeToSearch.isLocal()) {
				future=
					completionService.submit(new LocalSearchForPatientsRequest(nodeToSearch, 
					     	                                                   dicomQuery));
			}
			else {
				RemoteNode remoteNode = (RemoteNode)nodeToSearch;
				future = completionService.submit(new RemoteSearchForPatientsRequest(remoteNode,
						                                                             dicomQuery));
			}
			futures.add(future);

		}
		return futures;
	}
}
