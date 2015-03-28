package br.com.mentores.site.taglib;

import javax.servlet.jsp.*;
import java.net.*;
import javax.xml.transform.stream.*;
import javax.servlet.*;
import javax.xml.transform.*;
import java.io.*;

public class XSLTProcessorBean implements Serializable
{
    public void process(final String xml, final String xsl, final JspWriter writer) throws IOException, ServletException {
        final TransformerFactory tf = TransformerFactory.newInstance();
        try {
            final URL xmlUrl = new URL(xml);
            final URL xslUrl = new URL(xsl);
            final Transformer t = tf.newTransformer(new StreamSource(xslUrl.toString()));
            t.transform(new StreamSource(xmlUrl.toString()), new StreamResult((Writer)writer));
        }
        catch (Exception e) {
            throw new ServletException("<b>Erro no XSLTProcessorBean:</b> " + e);
        }
    }
}
