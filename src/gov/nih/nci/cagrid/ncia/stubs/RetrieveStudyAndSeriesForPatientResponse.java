/**
 * RetrieveStudyAndSeriesForPatientResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cagrid.ncia.stubs;

public class RetrieveStudyAndSeriesForPatientResponse  implements java.io.Serializable {
    private gov.nih.nci.ncia.search.StudySearchResult[] studySearchResult;

    public RetrieveStudyAndSeriesForPatientResponse() {
    }

    public RetrieveStudyAndSeriesForPatientResponse(
           gov.nih.nci.ncia.search.StudySearchResult[] studySearchResult) {
           this.studySearchResult = studySearchResult;
    }


    /**
     * Gets the studySearchResult value for this RetrieveStudyAndSeriesForPatientResponse.
     * 
     * @return studySearchResult
     */
    public gov.nih.nci.ncia.search.StudySearchResult[] getStudySearchResult() {
        return studySearchResult;
    }


    /**
     * Sets the studySearchResult value for this RetrieveStudyAndSeriesForPatientResponse.
     * 
     * @param studySearchResult
     */
    public void setStudySearchResult(gov.nih.nci.ncia.search.StudySearchResult[] studySearchResult) {
        this.studySearchResult = studySearchResult;
    }

    public gov.nih.nci.ncia.search.StudySearchResult getStudySearchResult(int i) {
        return this.studySearchResult[i];
    }

    public void setStudySearchResult(int i, gov.nih.nci.ncia.search.StudySearchResult _value) {
        this.studySearchResult[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RetrieveStudyAndSeriesForPatientResponse)) return false;
        RetrieveStudyAndSeriesForPatientResponse other = (RetrieveStudyAndSeriesForPatientResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.studySearchResult==null && other.getStudySearchResult()==null) || 
             (this.studySearchResult!=null &&
              java.util.Arrays.equals(this.studySearchResult, other.getStudySearchResult())));
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
        if (getStudySearchResult() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getStudySearchResult());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getStudySearchResult(), i);
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
        new org.apache.axis.description.TypeDesc(RetrieveStudyAndSeriesForPatientResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ncia.cagrid.nci.nih.gov/NCIACoreService", ">RetrieveStudyAndSeriesForPatientResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("studySearchResult");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://NBIA.caBIG/4.4/gov.nih.nci.nbia.remotesearch", "StudySearchResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://NBIA.caBIG/4.4/gov.nih.nci.nbia.remotesearch", "StudySearchResult"));
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
