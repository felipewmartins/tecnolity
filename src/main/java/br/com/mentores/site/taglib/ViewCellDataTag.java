package br.com.mentores.site.taglib;

import javax.servlet.jsp.tagext.*;
import java.io.*;
import java.util.*;
import javax.servlet.jsp.*;

public class ViewCellDataTag extends TagSupport
{
    private int column;
    private String align;
    private String valign;
    private String bgcolor;
    private String bgHeaderColor;
    private String linkPrefix;
    private String target;
    private String font;
    private String fontSize;
    private String fontColor;
    private String title;
    private ViewLineDataTag viewLineData;
    static /* synthetic */ Class class$0;
    
    public ViewCellDataTag() {
        this.align = "left";
        this.valign = "top";
        this.bgcolor = "white";
        this.target = "_top";
        this.font = "Arial";
        this.fontSize = "2";
        this.fontColor = "black";
    }
    
    public int doStartTag() throws JspException {
        final JspWriter out = super.pageContext.getOut();
        try {
            if (this.bgHeaderColor == null) {
                this.bgHeaderColor = this.bgcolor;
            }
            Class class$0;
            if ((class$0 = ViewCellDataTag.class$0) == null) {
                try {
                    class$0 = (ViewCellDataTag.class$0 = Class.forName("br.com.mentores.site.taglib.ViewLineDataTag"));
                }
                catch (ClassNotFoundException ex) {
                    throw new NoClassDefFoundError(ex.getMessage());
                }
            }
            this.viewLineData = (ViewLineDataTag)TagSupport.findAncestorWithClass((Tag)this, class$0);
            if (((TagSupport)this.viewLineData).getValue("title" + this.column) == null) {
                out.print("<td align='left' bgColor='" + this.bgHeaderColor + "'><font face='" + this.font + "' size='" + this.fontSize + "'><b>" + this.title + "</b></font></td>");
                ((TagSupport)this.viewLineData).setValue("title" + this.column, (Object)this.title);
            }
            else {
                out.print("<td align='" + this.align + "' valign='" + this.valign + "' bgColor='" + this.bgcolor + "'>");
                if (this.linkPrefix == null) {
                    out.print("<font face='" + this.font + "' size='" + this.fontSize + "' color='" + this.fontColor + "'>" + ((TagSupport)this.viewLineData).getValue("cel" + this.column) + "</font>");
                }
                else {
                    String link = this.linkPrefix;
                    final Vector columns = this.viewLineData.getColumnsKey();
                    final Vector querys = this.viewLineData.getQuerysKey();
                    if (querys.size() > 0 && columns.size() > 0) {
                        for (int i = 0; i < columns.size(); ++i) {
                            link = String.valueOf(link) + "&" + querys.get(i) + "=" + ((TagSupport)this.viewLineData).getValue("cel" + columns.get(i));
                        }
                    }
                    out.print("<a href='" + link + "' target='" + this.target + "'><font face='" + this.font + "' size='" + this.fontSize + "'>" + ((TagSupport)this.viewLineData).getValue("cel" + this.column) + "</font></a>");
                }
                out.print("</td>");
            }
        }
        catch (IOException ioe) {
            ioe.printStackTrace(new PrintWriter((Writer)out));
        }
        return 0;
    }
    
    public int doEndTag() throws JspException {
        this.linkPrefix = null;
        return 6;
    }
    
    public String getAlign() {
        return this.align;
    }
    
    public String getBgcolor() {
        return this.bgcolor;
    }
    
    public int getColumn() {
        return this.column;
    }
    
    public void setAlign(final String string) {
        this.align = string;
    }
    
    public void setBgcolor(final String string) {
        this.bgcolor = string;
    }
    
    public void setColumn(final int i) {
        this.column = i;
    }
    
    public String getLinkPrefix() {
        return this.linkPrefix;
    }
    
    public String getTarget() {
        return this.target;
    }
    
    public void setLinkPrefix(final String string) {
        this.linkPrefix = string;
    }
    
    public void setTarget(final String string) {
        this.target = string;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(final String string) {
        this.title = string;
    }
    
    public String getValign() {
        return this.valign;
    }
    
    public void setValign(final String string) {
        this.valign = string;
    }
    
    public String getFont() {
        return this.font;
    }
    
    public String getFontColor() {
        return this.fontColor;
    }
    
    public String getFontSize() {
        return this.fontSize;
    }
    
    public void setFont(final String string) {
        this.font = string;
    }
    
    public void setFontColor(final String string) {
        this.fontColor = string;
    }
    
    public void setFontSize(final String string) {
        this.fontSize = string;
    }
    
    public String getBgHeaderColor() {
        return this.bgHeaderColor;
    }
    
    public void setBgHeaderColor(final String string) {
        this.bgHeaderColor = string;
    }
}
