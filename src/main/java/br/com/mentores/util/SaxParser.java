package br.com.mentores.util;

import java.util.*;
import org.xml.sax.helpers.*;
import java.net.*;
import java.io.*;
import org.xml.sax.*;

public class SaxParser implements ContentHandler
{
    private Vector elementos;
    private SaxElement currentElement;
    private String elementText;
    
    public SaxParser() {
        this.elementos = new Vector();
    }
    
    public Vector parse(final String url) throws SAXException, IOException {
        final XMLReader xmlReader = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
        final URL xmlUrl = new URL(url);
        xmlReader.setContentHandler(this);
        final InputSource inputSource = new InputSource(xmlUrl.toString());
        xmlReader.parse(inputSource);
        return this.elementos;
    }
    
    public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) {
        this.currentElement = new SaxElement(uri, localName, qName, attributes);
        this.elementos.addElement(this.currentElement);
        this.elementText = new String();
    }
    
    public void endElement(final String uri, final String localName, final String qName) {
        if (this.currentElement != null && this.elementText != null) {
            this.currentElement.setValue(this.elementText.trim());
        }
        this.currentElement = null;
    }
    
    public void characters(final char[] chars, final int start, final int length) throws SAXException {
        if (this.currentElement != null && this.elementText != null) {
            final String value = new String(chars, start, length);
            this.elementText = String.valueOf(this.elementText) + value;
        }
    }
    
    public void endDocument() throws SAXException {
    }
    
    public void endPrefixMapping(final String arg0) throws SAXException {
    }
    
    public void ignorableWhitespace(final char[] arg0, final int arg1, final int arg2) throws SAXException {
    }
    
    public void processingInstruction(final String arg0, final String arg1) throws SAXException {
    }
    
    public void setDocumentLocator(final Locator arg0) {
    }
    
    public void skippedEntity(final String arg0) throws SAXException {
    }
    
    public void startDocument() throws SAXException {
    }
    
    public void startPrefixMapping(final String arg0, final String arg1) throws SAXException {
    }
}
