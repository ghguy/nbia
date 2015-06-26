/**
 * SearchForPatientsResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cagrid.ncia.stubs;

public class SearchForPatientsResponse  implements java.io.Serializable {
    private gov.nih.nci.ncia.search.PatientSearchResult[] patientSearchResult;

    public SearchForPatientsResponse() {
    }

    public SearchForPatientsResponse(
           gov.nih.nci.ncia.search.PatientSearchResult[] patientSearchResult) {
           this.patientSearchResult = patientSearchResult;
    }


    /**
     * Gets the patientSearchResult value for this SearchForPatientsResponse.
     * 
     * @return patientSearchResult
     */
    public gov.nih.nci.ncia.search.PatientSearchResult[] getPatientSearchResult() {
        return patientSearchResult;
    }


    /**
     * Sets the patientSearchResult value for this SearchForPatientsResponse.
     * 
     * @param patientSearchResult
     */
    public void setPatientSearchResult(gov.nih.nci.ncia.search.PatientSearchResult[] patientSearchResult) {
        this.patientSearchResult = patientSearchResult;
    }

    public gov.nih.nci.ncia.search.PatientSearchResult getPatientSearchResult(int i) {
        return this.patientSearchResult[i];
    }

    public void setPatientSearchResult(int i, gov.nih.nci.ncia.search.PatientSearchResult _value) {
        this.patientSearchResult[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SearchForPatientsResponse)) return false;
        SearchForPatientsResponse other = (SearchForPatientsResponse) obj;
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
              java.util.Arrays.equals(this.patientSearchResult, other.getPatientSearchResult())));
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
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPatientSearchResult());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPatientSearchResult(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SearchForPatientsResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ncia.cagrid.nci.nih.gov/NCIACoreService", ">SearchForPatientsResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("patientSearchResult");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://NBIA.caBIG/4.4/gov.nih.nci.nbia.remotesearch", "PatientSearchResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://NBIA.caBIG/4.4/gov.nih.nci.nbia.remotesearch", "PatientSearchResult"));
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
