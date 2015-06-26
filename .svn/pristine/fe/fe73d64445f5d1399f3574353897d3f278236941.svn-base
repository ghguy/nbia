/**
 * RetrieveDicomDataByNthStudyTimePointForPatientRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cagrid.ncia.stubs;

public class RetrieveDicomDataByNthStudyTimePointForPatientRequest  implements java.io.Serializable {
    private java.lang.String patientId;
    private int studyTimepoint;

    public RetrieveDicomDataByNthStudyTimePointForPatientRequest() {
    }

    public RetrieveDicomDataByNthStudyTimePointForPatientRequest(
           java.lang.String patientId,
           int studyTimepoint) {
           this.patientId = patientId;
           this.studyTimepoint = studyTimepoint;
    }


    /**
     * Gets the patientId value for this RetrieveDicomDataByNthStudyTimePointForPatientRequest.
     * 
     * @return patientId
     */
    public java.lang.String getPatientId() {
        return patientId;
    }


    /**
     * Sets the patientId value for this RetrieveDicomDataByNthStudyTimePointForPatientRequest.
     * 
     * @param patientId
     */
    public void setPatientId(java.lang.String patientId) {
        this.patientId = patientId;
    }


    /**
     * Gets the studyTimepoint value for this RetrieveDicomDataByNthStudyTimePointForPatientRequest.
     * 
     * @return studyTimepoint
     */
    public int getStudyTimepoint() {
        return studyTimepoint;
    }


    /**
     * Sets the studyTimepoint value for this RetrieveDicomDataByNthStudyTimePointForPatientRequest.
     * 
     * @param studyTimepoint
     */
    public void setStudyTimepoint(int studyTimepoint) {
        this.studyTimepoint = studyTimepoint;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RetrieveDicomDataByNthStudyTimePointForPatientRequest)) return false;
        RetrieveDicomDataByNthStudyTimePointForPatientRequest other = (RetrieveDicomDataByNthStudyTimePointForPatientRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.patientId==null && other.getPatientId()==null) || 
             (this.patientId!=null &&
              this.patientId.equals(other.getPatientId()))) &&
            this.studyTimepoint == other.getStudyTimepoint();
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
        if (getPatientId() != null) {
            _hashCode += getPatientId().hashCode();
        }
        _hashCode += getStudyTimepoint();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RetrieveDicomDataByNthStudyTimePointForPatientRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ncia.cagrid.nci.nih.gov/NCIACoreService", ">RetrieveDicomDataByNthStudyTimePointForPatientRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("patientId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ncia.cagrid.nci.nih.gov/NCIACoreService", "patientId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("studyTimepoint");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ncia.cagrid.nci.nih.gov/NCIACoreService", "studyTimepoint"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
