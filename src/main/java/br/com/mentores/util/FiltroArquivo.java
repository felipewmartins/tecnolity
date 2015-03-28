package br.com.mentores.util;

import javax.swing.filechooser.*;
import java.io.*;
import java.util.*;

public class FiltroArquivo extends FileFilter
{
    protected String description;
    protected ArrayList exts;
    
    public FiltroArquivo() {
        this.exts = new ArrayList();
    }
    
    public void addType(final String type) {
        this.exts.add(type);
    }
    
    public boolean accept(final File f) {
        if (f.isDirectory()) {
            return true;
        }
        if (f.isFile()) {
            final Iterator it = this.exts.iterator();
            while (it.hasNext()) {
                if (f.getName().endsWith(it.next())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(final String string) {
        this.description = string;
    }
}
