package br.com.mentores.site.taglib;

import javax.servlet.jsp.tagext.*;
import java.io.*;
import javax.servlet.jsp.*;

public class TextFieldTag extends TagSupport
{
    private StringBuffer html;
    private String name;
    private int size;
    private int maxLength;
    private String form;
    private String value;
    private String cssClassName;
    private String help;
    private boolean editable;
    private boolean disabled;
    private boolean mandatory;
    
    public TextFieldTag() {
        this.editable = true;
        this.disabled = false;
        this.mandatory = false;
    }
    
    public int doStartTag() throws JspException {
        final JspWriter out = super.pageContext.getOut();
        this.html = new StringBuffer();
        try {
            if (this.editable) {
                this.html.append("<input type=\"text\" name=\"" + this.getName() + "\" ");
                if (this.cssClassName != null) {
                    this.html.append("class=\"" + this.getCssClassName() + "\" ");
                }
                else if (this.mandatory) {
                    this.html.append("style=\"{background-color: #ffffff; font: 12px verdana, arial, helvetica, sans-serif; color:#003399; border:1px solid #f00;}\" ");
                }
                else {
                    this.html.append("style=\"{background-color: #ffffff; font: 12px verdana, arial, helvetica, sans-serif; color:#003399; border:1px solid #aaa;}\" ");
                }
                if (this.getValue() != null) {
                    this.html.append("value=\"" + this.getValue() + "\" ");
                }
                this.html.append("size=\"" + this.getSize() + "\" maxlength=\"" + this.maxLength + "\"");
                if (this.help != null) {
                    this.html.append("  onFocus=\"window.status='" + this.getHelp() + "'; return true;\" onBlur=\"window.status=''; return true;\"");
                }
                this.html.append(">");
                if (this.disabled && this.getForm() != null) {
                    this.html.append("<script language=\"JavaScript\">");
                    this.html.append("document." + this.getForm() + "." + this.getName() + ".disabled = true;");
                    this.html.append("</script>");
                }
                out.println(this.html.toString());
            }
            else {
                out.println("<font>" + this.value + "</font>");
            }
        }
        catch (IOException ioe) {
            throw new JspTagException("Erro: Ocorreu uma excess\u00e3o enquanto escrevia componente.");
        }
        return 0;
    }
    
    public int getSize() {
        return this.size;
    }
    
    public void setSize(final int size) {
        this.size = size;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public void setValue(final String string) {
        if (string != null && !string.equals("")) {
            this.value = string;
        }
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String string) {
        this.name = string;
    }
    
    public boolean isEditable() {
        return this.editable;
    }
    
    public void setEditable(final boolean editable) {
        this.editable = editable;
    }
    
    public String getCssClassName() {
        return this.cssClassName;
    }
    
    public boolean isMandatory() {
        return this.mandatory;
    }
    
    public int getMaxLength() {
        return this.maxLength;
    }
    
    public void setCssClassName(final String string) {
        if (string != null && !string.equals("")) {
            this.cssClassName = string;
        }
    }
    
    public void setMandatory(final boolean b) {
        this.mandatory = b;
    }
    
    public void setMaxLength(final int maxLength) {
        this.maxLength = maxLength;
    }
    
    public String getHelp() {
        return this.help;
    }
    
    public void setHelp(final String string) {
        if (string != null && !string.equals("")) {
            this.help = string;
        }
    }
    
    public boolean isDisabled() {
        return this.disabled;
    }
    
    public void setDisabled(final boolean b) {
        this.disabled = b;
    }
    
    public String getForm() {
        return this.form;
    }
    
    public void setForm(final String string) {
        if (string != null && !string.equals("")) {
            this.form = string;
        }
    }
}
