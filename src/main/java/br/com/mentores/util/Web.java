package br.com.mentores.util;

import java.net.*;
import java.io.*;

public class Web
{
    public static void cacheURLContent(final String url, final String destination) throws Exception {
        final Calendario calendario = new Calendario();
        final BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(String.valueOf(destination) + "/" + calendario.get("yyyy-MM-dd") + ".xml"));
        final URL urlXml = new URL(url);
        final InputStream is = (InputStream)urlXml.getContent();
        int ch;
        while ((ch = is.read()) >= 0) {
            output.write(ch);
        }
        output.close();
    }
}
