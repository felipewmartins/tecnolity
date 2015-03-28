package org.esmerilprogramming.tecnolity.util

import java.util.*
import org.xml.sax.helpers.*
import java.net.*
import java.io.*
import org.xml.sax.*

class SaxParser implements ContentHandler {
  private Vector elementos
  private SaxElement currentElement
  private String elementText

  SaxParser() {
    this.elementos = new Vector()
  }

  Vector parse(final String url) throws SAXException, IOException {
    final XMLReader xmlReader = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser")
      final URL xmlUrl = new URL(url)
      xmlReader.setContentHandler(this)
      final InputSource inputSource = new InputSource(xmlUrl.toString())
      xmlReader.parse(inputSource)
      return this.elementos
  }

  void startElement(final String uri, final String localName, final String qName, final Attributes attributes) {
    this.currentElement = new SaxElement(uri, localName, qName, attributes)
      this.elementos.addElement(this.currentElement)
      this.elementText = new String()
  }

  void endElement(final String uri, final String localName, final String qName) {
    if (this.currentElement != null && this.elementText != null) {
      this.currentElement.setValue(this.elementText.trim())
    }
    this.currentElement = null
  }

  void characters(final char[] chars, final int start, final int length) throws SAXException {
    if (this.currentElement != null && this.elementText != null) {
      final String value = new String(chars, start, length)
        this.elementText = String.valueOf(this.elementText) + value
    }
  }

  void endDocument() throws SAXException {
  }

  void endPrefixMapping(final String arg0) throws SAXException {
  }

  void ignorableWhitespace(final char[] arg0, final int arg1, final int arg2) throws SAXException {
  }

  void processingInstruction(final String arg0, final String arg1) throws SAXException {
  }

  void setDocumentLocator(final Locator arg0) {
  }

  void skippedEntity(final String arg0) throws SAXException {
  }

  void startDocument() throws SAXException {
  }

  void startPrefixMapping(final String arg0, final String arg1) throws SAXException {
  }
}
