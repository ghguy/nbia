/**
 * ServiceContextContextPropertyCollection.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cagrid.metadata.service;

public class ServiceContextContextPropertyCollection  implements java.io.Serializable {
    private gov.nih.nci.cagrid.metadata.service.ContextProperty[] contextProperty;

    public ServiceContextContextPropertyCollection() {
    }

    public ServiceContextContextPropertyCollection(
           gov.nih.nci.cagrid.metadata.service.ContextProperty[] contextProperty) {
           this.contextProperty = contextProperty;
    }


    /**
     * Gets the contextProperty value for this ServiceContextContextPropertyCollection.
     * 
     * @return contextProperty
     */
    public gov.nih.nci.cagrid.metadata.service.ContextProperty[] getContextProperty() {
        return contextProperty;
    }


    /**
     * Sets the contextProperty value for this ServiceContextContextPropertyCollection.
     * 
     * @param contextProperty
     */
    public void setContextProperty(gov.nih.nci.cagrid.metadata.service.ContextProperty[] contextProperty) {
        this.contextProperty = contextProperty;
    }

    public gov.nih.nci.cagrid.metadata.service.ContextProperty getContextProperty(int i) {
        return this.contextProperty[i];
    }

    public void setContextProperty(int i, gov.nih.nci.cagrid.metadata.service.ContextProperty _value) {
        this.contextProperty[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ServiceContextContextPropertyCollection)) return false;
        ServiceContextContextPropertyCollection other = (ServiceContextContextPropertyCollection) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.contextProperty==null && other.getContextProperty()==null) || 
             (this.contextProperty!=null &&
              java.util.Arrays.equals(this.contextProperty, other.getContextProperty())));
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
        if (getContextProperty() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getContextProperty());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getContextProperty(), i);
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
        new org.apache.axis.description.TypeDesc(ServiceContextContextPropertyCollection.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("gme://caGrid.caBIG/1.0/gov.nih.nci.cagrid.metadata.service", ">ServiceContext>contextPropertyCollection"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contextProperty");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://caGrid.caBIG/1.0/gov.nih.nci.cagrid.metadata.service", "ContextProperty"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://caGrid.caBIG/1.0/gov.nih.nci.cagrid.metadata.service", "ContextProperty"));
        elemField.setMinOccurs(0);
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
