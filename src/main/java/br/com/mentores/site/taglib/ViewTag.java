package br.com.mentores.site.taglib;

import java.io.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class ViewTag extends BodyTagSupport
{
    private int border;
    private String width;
    private int cellspacing;
    private int cellpadding;
    private String bgColor;
    
    public ViewTag() {
        this.border = 0;
        this.width = "";
        this.cellspacing = 0;
        this.cellpadding = 0;
        this.bgColor = "white";
    }
    
    public int doStartTag() throws JspException {
        final JspWriter out = ((TagSupport)this).pageContext.getOut();
        try {
            out.print("<table border='" + this.border + "' bgcolor='" + this.bgColor + "' " + (this.width.equals("") ? "" : ("width='" + this.width + "'")) + " cellspacing='" + this.cellspacing + "' cellpadding='" + this.cellpadding + "'>");
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
        final JspWriter saida = ((TagSupport)this).pageContext.getOut();
        try {
            saida.print("</table>");
        }
        catch (IOException ioe) {
            ioe.printStackTrace(new PrintWriter((Writer)((TagSupport)this).pageContext.getOut()));
        }
        return 6;
    }
    
    public int getBorder() {
        return this.border;
    }
    
    public String getWidth() {
        return this.width;
    }
    
    public void setBorder(final int border) {
        this.border = border;
    }
    
    public void setWidth(final String width) {
        this.width = width;
    }
    
    public int getCellpadding() {
        return this.cellpadding;
    }
    
    public int getCellspacing() {
        return this.cellspacing;
    }
    
    public void setCellpadding(final int i) {
        this.cellpadding = i;
    }
    
    public void setCellspacing(final int i) {
        this.cellspacing = i;
    }
    
    public String getBgColor() {
        return this.bgColor;
    }
    
    public void setBgColor(final String string) {
        this.bgColor = string;
    }
}
