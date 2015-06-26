/**
 * RetrieveStudyAndSeriesForPatientRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cagrid.ncia.stubs;

public class RetrieveStudyAndSeriesForPatientRequest  implements java.io.Serializable {
    private gov.nih.nci.cagrid.ncia.stubs.RetrieveStudyAndSeriesForPatientRequestPatientSearchResult patientSearchResult;

    public RetrieveStudyAndSeriesForPatientRequest() {
    }

    public RetrieveStudyAndSeriesForPatientRequest(
           gov.nih.nci.cagrid.ncia.stubs.RetrieveStudyAndSeriesForPatientRequestPatientSearchResult patientSearchResult) {
           this.patientSearchResult = patientSearchResult;
    }


    /**
     * Gets the patientSearchResult value for this RetrieveStudyAndSeriesForPatientRequest.
     * 
     * @return patientSearchResult
     */
    public gov.nih.nci.cagrid.ncia.stubs.RetrieveStudyAndSeriesForPatientRequestPatientSearchResult getPatientSearchResult() {
        return patientSearchResult;
    }


    /**
     * Sets the patientSearchResult value for this RetrieveStudyAndSeriesForPatientRequest.
     * 
     * @param patientSearchResult
     */
    public void setPatientSearchResult(gov.nih.nci.cagrid.ncia.stubs.RetrieveStudyAndSeriesForPatientRequestPatientSearchResult patientSearchResult) {
        this.patientSearchResult = patientSearchResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RetrieveStudyAndSeriesForPatientRequest)) return false;
        RetrieveStudyAndSeriesForPatientRequest other = (RetrieveStudyAndSeriesForPatientRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.patientSearchResult==null && other.getPatientSearchResult()==null) || 
             (this.patientSearchResult!=null &&
              this.patientSearchResult.equals(other.getPatientSearchResult())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getPatientSearchResult() != null) {
            _hashCode += getPatientSearchResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RetrieveStudyAndSeriesForPatientRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ncia.cagrid.nci.nih.gov/NCIACoreService", ">RetrieveStudyAndSeriesForPatientRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("patientSearchResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ncia.cagrid.nci.nih.gov/NCIACoreService", "patientSearchResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ncia.cagrid.nci.nih.gov/NCIACoreService", ">>RetrieveStudyAndSeriesForPatientRequest>patientSearchResult"));
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
