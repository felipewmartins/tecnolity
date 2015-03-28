package br.com.mentores.site.taglib;

import java.util.*;
import java.io.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class ViewLineDataTag extends BodyTagSupport
{
    private Vector lines;
    private Object[] line;
    private Vector columnsKey;
    private Vector querysKey;
    private boolean columnKeyReaded;
    private Iterator iterator;
    
    public int doStartTag() throws JspException {
        this.columnsKey = new Vector();
        this.querysKey = new Vector();
        return 2;
    }
    
    public void doInitBody() throws JspException {
        final JspWriter out = ((TagSupport)this).pageContext.getOut();
        try {
            this.iterator = this.lines.iterator();
            out.println("<tr>");
        }
        catch (IOException ioe) {
            ioe.printStackTrace(new PrintWriter((Writer)out));
        }
    }
    
    public int doAfterBody() throws JspException {
        final JspWriter out = ((TagSupport)this).pageContext.getOut();
        try {
            if (!this.columnKeyReaded) {
                this.columnKeyReaded = true;
            }
            out.println("</tr>");
            if (this.iterator.hasNext()) {
                this.line = this.iterator.next();
                for (int i = 0; i < this.line.length; ++i) {
                    ((TagSupport)this).setValue("cel" + i, this.line[i]);
                }
                out.println("<tr>");
                return 2;
            }
            this.getBodyContent().writeOut((Writer)this.getPreviousOut());
        }
        catch (IOException e) {
            e.printStackTrace(new PrintWriter((Writer)((TagSupport)this).pageContext.getOut()));
        }
        return 0;
    }
    
    public int doEndTag() throws JspException {
        this.columnKeyReaded = false;
        this.columnsKey = null;
        this.querysKey = null;
        for (int i = 0; i < this.line.length; ++i) {
            ((TagSupport)this).removeValue("title" + i);
        }
        return 6;
    }
    
    public Vector getLines() {
        return this.lines;
    }
    
    public void setLines(final Vector vector) {
        this.lines = vector;
    }
    
    public void addColumnsKey(final String queryKey, final String columnKey) {
        if (!this.columnKeyReaded) {
            this.querysKey.addElement(queryKey);
            this.columnsKey.addElement(columnKey);
        }
    }
    
    public Vector getColumnsKey() {
        return this.columnsKey;
    }
    
    public Vector getQuerysKey() {
        return this.querysKey;
    }
}
