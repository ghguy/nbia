/**
 * NCIACoreServicePortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cagrid.ncia.stubs;

public interface NCIACoreServicePortType extends java.rmi.Remote {
    public gov.nih.nci.cagrid.ncia.stubs.RetrieveDicomDataResponse retrieveDicomData(gov.nih.nci.cagrid.ncia.stubs.RetrieveDicomDataRequest parameters) throws java.rmi.RemoteException;
    public gov.nih.nci.cagrid.ncia.stubs.RetrieveDicomDataByPatientIdResponse retrieveDicomDataByPatientId(gov.nih.nci.cagrid.ncia.stubs.RetrieveDicomDataByPatientIdRequest parameters) throws java.rmi.RemoteException;
    public gov.nih.nci.cagrid.ncia.stubs.RetrieveDicomDataBySeriesUIDResponse retrieveDicomDataBySeriesUID(gov.nih.nci.cagrid.ncia.stubs.RetrieveDicomDataBySeriesUIDRequest parameters) throws java.rmi.RemoteException;
    public gov.nih.nci.cagrid.ncia.stubs.RetrieveDicomDataByStudyUIDResponse retrieveDicomDataByStudyUID(gov.nih.nci.cagrid.ncia.stubs.RetrieveDicomDataByStudyUIDRequest parameters) throws java.rmi.RemoteException;
    public gov.nih.nci.cagrid.ncia.stubs.RetrieveDicomDataByPatientIdsResponse retrieveDicomDataByPatientIds(gov.nih.nci.cagrid.ncia.stubs.RetrieveDicomDataByPatientIdsRequest parameters) throws java.rmi.RemoteException;
    public gov.nih.nci.cagrid.ncia.stubs.RetrieveDicomDataBySeriesUIDsResponse retrieveDicomDataBySeriesUIDs(gov.nih.nci.cagrid.ncia.stubs.RetrieveDicomDataBySeriesUIDsRequest parameters) throws java.rmi.RemoteException;
    public gov.nih.nci.cagrid.ncia.stubs.RetrieveDicomDataByStudyUIDsResponse retrieveDicomDataByStudyUIDs(gov.nih.nci.cagrid.ncia.stubs.RetrieveDicomDataByStudyUIDsRequest parameters) throws java.rmi.RemoteException;
    public gov.nih.nci.cagrid.ncia.stubs.GetNumberOfStudyTimePointForPatientResponse getNumberOfStudyTimePointForPatient(gov.nih.nci.cagrid.ncia.stubs.GetNumberOfStudyTimePointForPatientRequest parameters) throws java.rmi.RemoteException;
    public gov.nih.nci.cagrid.ncia.stubs.RetrieveDicomDataByNthStudyTimePointForPatientResponse retrieveDicomDataByNthStudyTimePointForPatient(gov.nih.nci.cagrid.ncia.stubs.RetrieveDicomDataByNthStudyTimePointForPatientRequest parameters) throws java.rmi.RemoteException;
    public gov.nih.nci.cagrid.ncia.stubs.GetRepresentativeImageBySeriesResponse getRepresentativeImageBySeries(gov.nih.nci.cagrid.ncia.stubs.GetRepresentativeImageBySeriesRequest parameters) throws java.rmi.RemoteException;
    public org.oasis.wsrf.properties.GetMultipleResourcePropertiesResponse getMultipleResourceProperties(org.oasis.wsrf.properties.GetMultipleResourceProperties_Element getMultipleResourcePropertiesRequest) throws java.rmi.RemoteException, org.oasis.wsrf.properties.InvalidResourcePropertyQNameFaultType, org.oasis.wsrf.properties.ResourceUnknownFaultType;
    public org.oasis.wsrf.properties.GetResourcePropertyResponse getResourceProperty(javax.xml.namespace.QName getResourcePropertyRequest) throws java.rmi.RemoteException, org.oasis.wsrf.properties.InvalidResourcePropertyQNameFaultType, org.oasis.wsrf.properties.ResourceUnknownFaultType;
    public org.oasis.wsrf.properties.QueryResourcePropertiesResponse queryResourceProperties(org.oasis.wsrf.properties.QueryResourceProperties_Element queryResourcePropertiesRequest) throws java.rmi.RemoteException, org.oasis.wsrf.properties.InvalidResourcePropertyQNameFaultType, org.oasis.wsrf.properties.InvalidQueryExpressionFaultType, org.oasis.wsrf.properties.QueryEvaluationErrorFaultType, org.oasis.wsrf.properties.ResourceUnknownFaultType, org.oasis.wsrf.properties.UnknownQueryExpressionDialectFaultType;
    public gov.nih.nci.cagrid.introduce.security.stubs.GetServiceSecurityMetadataResponse getServiceSecurityMetadata(gov.nih.nci.cagrid.introduce.security.stubs.GetServiceSecurityMetadataRequest parameters) throws java.rmi.RemoteException;
    public gov.nih.nci.cagrid.data.QueryResponse query(gov.nih.nci.cagrid.data.QueryRequest parameters) throws java.rmi.RemoteException, gov.nih.nci.cagrid.data.faults.MalformedQueryExceptionType, gov.nih.nci.cagrid.data.faults.QueryProcessingExceptionType;
}
