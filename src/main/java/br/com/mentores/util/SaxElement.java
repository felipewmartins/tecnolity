package br.com.mentores.util;

import org.xml.sax.*;

public class SaxElement
{
    private String namespace;
    private String localName;
    private String qualifiedName;
    private String value;
    private Attributes attributes;
    
    public SaxElement(final String namespace, final String localName, final String qualifiedName, final Attributes attributes) {
        this.namespace = namespace;
        this.localName = localName;
        this.qualifiedName = qualifiedName;
        this.attributes = attributes;
    }
    
    public void setValue(final String value) {
        this.value = value;
    }
    
    public Attributes getAttributes() {
        return this.attributes;
    }
    
    public String getLocalName() {
        return this.localName;
    }
    
    public String getNamespace() {
        return this.namespace;
    }
    
    public String getQualifiedName() {
        return this.qualifiedName;
    }
    
    public String getValue() {
        return this.value;
    }
}
