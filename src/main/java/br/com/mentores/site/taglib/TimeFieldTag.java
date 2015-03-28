package br.com.mentores.site.taglib;

import javax.servlet.jsp.tagext.*;
import java.io.*;
import javax.servlet.jsp.*;

public class TimeFieldTag extends TagSupport
{
    private String fieldName;
    private String formName;
    private String fieldSize;
    private String timeValue;
    private boolean editable;
    
    public int doStartTag() throws JspException {
        final JspWriter out = super.pageContext.getOut();
        try {
            if (this.editable) {
                out.println("<script>");
                out.println("function format" + this.fieldName + "(timeField)");
                out.println("{");
                out.println("    if(window.event.keyCode < 48 || window.event.keyCode > 57)");
                out.println("        return false;");
                out.println("    var time = timeField.value;");
                out.println("    if(time.length == 2)");
                out.println("        timeField.value = time + ':';");
                out.println("    if(time.length >= 5)");
                out.println("        return false;");
                out.println("}");
                out.println("</script>");
                out.println("<input type=\"text\" name=\"" + this.fieldName + "\" value=\"" + this.timeValue + "\" size=\"" + this.fieldSize + "\" onkeypress=\"return format" + this.fieldName + "(document." + this.formName + "." + this.fieldName + ")\">");
            }
            else {
                out.println(this.timeValue);
            }
        }
        catch (IOException ioe) {
            throw new JspTagException("Erro: Ocorreu uma excess\u00e3o enquanto escrevia componente.");
        }
        return 0;
    }
    
    public String getFormName() {
        return this.formName;
    }
    
    public String getFieldSize() {
        return this.fieldSize;
    }
    
    public void setFormName(final String string) {
        this.formName = string;
    }
    
    public void setFieldSize(final String string) {
        this.fieldSize = string;
    }
    
    public String getTimeValue() {
        return this.timeValue;
    }
    
    public void setTimeValue(final String string) {
        this.timeValue = string;
    }
    
    public String getFieldName() {
        return this.fieldName;
    }
    
    public void setFieldName(final String string) {
        this.fieldName = string;
    }
    
    public boolean isEditable() {
        return this.editable;
    }
    
    public void setEditable(final boolean editable) {
        this.editable = editable;
    }
}
