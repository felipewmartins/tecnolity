package br.com.mentores.site.taglib;

import java.io.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class ViewActionTag extends BodyTagSupport
{
    private String columnsNumber;
    private String bgColor;
    private String align;
    
    public ViewActionTag() {
        this.bgColor = "cccccc";
        this.align = "left";
    }
    
    public int doStartTag() throws JspException {
        final JspWriter out = ((TagSupport)this).pageContext.getOut();
        try {
            out.print("<tr><td align='" + this.align + "' colspan='" + this.columnsNumber + "' bgcolor='#" + this.bgColor + "'>");
        }
        catch (IOException ioe) {
            ioe.printStackTrace(new PrintWriter((Writer)out));
        }
        return 2;
    }
    
    public int doAfterBody() throws JspException {
        try {
            this.getBodyContent().writeOut((Writer)this.getPreviousOut());
        }
        catch (IOException e) {
            e.printStackTrace(new PrintWriter((Writer)((TagSupport)this).pageContext.getOut()));
        }
        return 0;
    }
    
    public int doEndTag() throws JspException {
        final JspWriter out = ((TagSupport)this).pageContext.getOut();
        try {
            out.print("</td></tr>");
        }
        catch (IOException ioe) {
            ioe.printStackTrace(new PrintWriter((Writer)((TagSupport)this).pageContext.getOut()));
        }
        return 6;
    }
    
    public String getColumnsNumber() {
        return this.columnsNumber;
    }
    
    public void setColumnsNumber(final String string) {
        this.columnsNumber = string;
    }
    
    public String getBgColor() {
        return this.bgColor;
    }
    
    public void setBgColor(final String string) {
        this.bgColor = string;
    }
    
    public String getAlign() {
        return this.align;
    }
    
    public void setAlign(final String string) {
        this.align = string;
    }
}
