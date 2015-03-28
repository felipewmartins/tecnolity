package br.com.mentores.site.taglib;

import javax.servlet.jsp.tagext.*;
import java.io.*;
import javax.servlet.jsp.*;

public class MultiSelectTag extends TagSupport
{
    private int size;
    private String titleSelect;
    private String titleSelected;
    private String actionAddElement;
    private String actionAddElements;
    private String actionRemoveElement;
    private String actionRemoveElements;
    private String formName;
    private String fieldName;
    private String[][] select;
    private String[][] selected;
    private boolean editable;
    
    public MultiSelectTag() {
        this.actionAddElement = "";
        this.actionAddElements = "";
        this.actionRemoveElement = "";
        this.actionRemoveElements = "";
    }
    
    public int doStartTag() throws JspException {
        final JspWriter out = super.pageContext.getOut();
        try {
            if (this.editable) {
                this.removeSelectFromSelected();
                if (this.actionAddElement.equals("") || this.actionAddElements.equals("") || this.actionRemoveElement.equals("") || this.actionRemoveElements.equals("")) {
                    this.actionAddElement = "<input type='button' value=' > ' onClick='addElement" + this.fieldName + "(document." + this.formName + ".origem" + this.fieldName + ",document." + this.formName + ".destino" + this.fieldName + ")'>";
                    this.actionAddElements = "<input type='button' value='>>' onClick='addElements" + this.fieldName + "(document." + this.formName + ".origem" + this.fieldName + ",document." + this.formName + ".destino" + this.fieldName + ")'>";
                    this.actionRemoveElement = "<input type='button' value=' < ' onClick='addElement" + this.fieldName + "(document." + this.formName + ".destino" + this.fieldName + ",document." + this.formName + ".origem" + this.fieldName + ")'>";
                    this.actionRemoveElements = "<input type='button' value='<<' onClick='addElements" + this.fieldName + "(document." + this.formName + ".destino" + this.fieldName + ",document." + this.formName + ".origem" + this.fieldName + ")'>";
                }
                else {
                    this.actionAddElement = "<a href='javascript:addElement" + this.fieldName + "(document." + this.formName + ".origem" + this.fieldName + ", document." + this.formName + ".destino" + this.fieldName + ");'>" + this.actionAddElement + "</a>";
                    this.actionAddElements = "<a href='javascript:addElements" + this.fieldName + "(document." + this.formName + ".origem" + this.fieldName + ", document." + this.formName + ".destino" + this.fieldName + ");'>" + this.actionAddElements + "</a>";
                    this.actionRemoveElement = "<a href='javascript:addElement" + this.fieldName + "(document." + this.formName + ".destino" + this.fieldName + ", document." + this.formName + ".origem" + this.fieldName + ");'>" + this.actionRemoveElement + "</a>";
                    this.actionRemoveElements = "<a href='javascript:addElements" + this.fieldName + "(document." + this.formName + ".destino" + this.fieldName + ", document." + this.formName + ".origem" + this.fieldName + ");'>" + this.actionRemoveElements + "</a>";
                }
                out.print("<script>");
                out.print("function addElement" + this.fieldName + "(origem, destino){");
                out.print("if(origem.selectedIndex >= 0){");
                out.print("destino.options[destino.length] = new Option(origem.options[origem.selectedIndex].text, origem.options[origem.selectedIndex].value);");
                out.print("origem.options[origem.selectedIndex] = null;}");
                out.print("setValue" + this.fieldName + "();}");
                out.print("function addElements" + this.fieldName + "(origem, destino){");
                out.print("if(origem.length > 0){");
                out.print("for(i = 0;i < origem.length;i++){");
                out.print("destino.options[destino.length] = new Option(origem.options[i].text, origem.options[i].value);}");
                out.print("for(i = origem.length;i >= 0;i--){origem.options[i] = null;}");
                out.print("}setValue" + this.fieldName + "();}");
                out.print("function setValue" + this.fieldName + "(){");
                out.print("document." + this.formName + "." + this.fieldName + ".value = '';");
                out.print("for(i = 0;i < document." + this.formName + ".destino" + this.fieldName + ".length;i++){");
                out.print("document." + this.formName + "." + this.fieldName + ".value += document." + this.formName + ".destino" + this.fieldName + ".options[i].value;");
                out.print("if(i < (document." + this.formName + ".destino" + this.fieldName + ".length - 1))");
                out.print("document." + this.formName + "." + this.fieldName + ".value += ';';}}");
                out.print("</script>");
                out.print("<table><tr>");
                out.print("<td><font face='Arial' size='2'><b>" + this.titleSelect + "</b></font></td><td>&nbsp;</td><td><font face='Arial' size='2'><b>" + this.titleSelected + "</b></font></td>");
                out.print("</tr><tr>");
                out.print("<td valign='top'><select name='origem" + this.fieldName + "' multiple size='" + this.size + "'>");
                for (int i = 0; i < this.select.length; ++i) {
                    out.print("<option value='" + this.select[i][0] + "'>" + this.select[i][1] + "</option>");
                }
                out.print("</select></td>");
                out.print("<td valign='top'><table>");
                out.print("<tr><td>" + this.actionAddElement + "</td></tr>");
                out.print("<tr><td>" + this.actionAddElements + "</td></tr>");
                out.print("<tr><td>" + this.actionRemoveElement + "</td></tr>");
                out.print("<tr><td>" + this.actionRemoveElements + "</td></tr>");
                out.print("</table></td>");
                out.print("<td valign='top'><select name='destino" + this.fieldName + "' multiple size='" + this.size + "'>");
                String valueSelected = "";
                if (this.selected != null) {
                    for (int j = 0; j < this.selected.length; ++j) {
                        out.print("<option value='" + this.selected[j][0] + "'>" + this.selected[j][1] + "</option>");
                        valueSelected = String.valueOf(valueSelected) + this.selected[j][0];
                        if (j < this.selected.length - 1) {
                            valueSelected = String.valueOf(valueSelected) + ";";
                        }
                    }
                }
                out.print("</select><input type='hidden' name='" + this.fieldName + "' value='" + valueSelected + "'></td></tr></table>");
            }
            else if (!this.editable && this.selected != null) {
                out.print("<ul type='cycle'>");
                for (int i = 0; i < this.selected.length; ++i) {
                    out.print("<li> <font face='Arial' size='2'>" + this.selected[i][1] + "</font>");
                }
                out.print("</ul>");
            }
        }
        catch (IOException ioe) {
            ioe.printStackTrace(new PrintWriter((Writer)out));
        }
        return 0;
    }
    
    public int doEndTag() throws JspException {
        return 6;
    }
    
    private void removeSelectFromSelected() {
        if (this.selected != null) {
            final String[][] newSelect = new String[this.select.length - this.selected.length][2];
            int indiceNewSelect = 0;
            for (int i = 0; i < this.select.length; ++i) {
                boolean isEquals = false;
                for (int j = 0; j < this.selected.length; ++j) {
                    if (this.selected[j][0].equals(this.select[i][0]) && this.selected[j][1].equals(this.select[i][1])) {
                        isEquals = true;
                    }
                }
                if (!isEquals) {
                    newSelect[indiceNewSelect] = this.select[i];
                    ++indiceNewSelect;
                }
            }
            this.select = newSelect;
        }
    }
    
    public boolean isEditable() {
        return this.editable;
    }
    
    public String getActionAddElement() {
        return this.actionAddElement;
    }
    
    public String getActionAddElements() {
        return this.actionAddElements;
    }
    
    public String getActionRemoveElement() {
        return this.actionRemoveElement;
    }
    
    public String getActionRemoveElements() {
        return this.actionRemoveElements;
    }
    
    public String getFormName() {
        return this.formName;
    }
    
    public String getFieldName() {
        return this.fieldName;
    }
    
    public String[][] getSelect() {
        return this.select;
    }
    
    public int getSize() {
        return this.size;
    }
    
    public String getTitleSelect() {
        return this.titleSelect;
    }
    
    public String getTitleSelected() {
        return this.titleSelected;
    }
    
    public void setEditable(final boolean editable) {
        this.editable = editable;
    }
    
    public void setActionAddElement(final String string) {
        this.actionAddElement = string;
    }
    
    public void setActionAddElements(final String string) {
        this.actionAddElements = string;
    }
    
    public void setActionRemoveElement(final String string) {
        this.actionRemoveElement = string;
    }
    
    public void setActionRemoveElements(final String string) {
        this.actionRemoveElements = string;
    }
    
    public void setFormName(final String string) {
        this.formName = string;
    }
    
    public void setFieldName(final String string) {
        this.fieldName = string;
    }
    
    public void setSelect(final String[][] strings) {
        this.select = strings;
    }
    
    public void setSize(final int i) {
        this.size = i;
    }
    
    public void setTitleSelect(final String string) {
        this.titleSelect = string;
    }
    
    public void setTitleSelected(final String string) {
        this.titleSelected = string;
    }
    
    public String[][] getSelected() {
        return this.selected;
    }
    
    public void setSelected(final String[][] strings) {
        this.selected = strings;
    }
}
