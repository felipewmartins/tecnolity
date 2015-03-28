package br.com.mentores.site.taglib;

import java.io.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class ActionTag extends BodyTagSupport
{
    private String id;
    private String link;
    private String message;
    private String help;
    
    public int doStartTag() throws JspException {
        return 2;
    }
    
    public void doInitBody() throws JspException {
        final JspWriter out = ((TagSupport)this).pageContext.getOut();
        try {
            out.print("<script>");
            out.print("function confirmar" + this.id + "(){");
            if (this.message != null && !this.message.equals("")) {
                out.print("   if(confirm(\"Aten\u00e7\u00e3o: " + this.message + "\"))");
            }
            out.print("document.location = \"" + this.link + "\";");
            out.print("}</script>");
            out.print("<a href=\"javascript:confirmar" + this.id + "();\" onMouseOver=\"self.status='" + this.help + "'; return true;\" onMouseOut=\"self.status=''; return true;\">");
        }
        catch (IOException ioe) {
            ioe.printStackTrace(new PrintWriter((Writer)out));
        }
    }
    
    public int doAfterBody() throws JspException {
        final JspWriter out = ((TagSupport)this).pageContext.getOut();
        try {
            out.print("</a>");
            this.getBodyContent().writeOut((Writer)this.getPreviousOut());
        }
        catch (IOException e) {
            e.printStackTrace(new PrintWriter((Writer)out));
        }
        return 0;
    }
    
    public String getLink() {
        return this.link;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public void setLink(final String string) {
        this.link = string;
    }
    
    public void setMessage(final String string) {
        this.message = string;
    }
    
    public String getHelp() {
        return this.help;
    }
    
    public void setHelp(final String string) {
        this.help = string;
    }
    
    public String getId() {
        return this.id;
    }
    
    public void setId(final String string) {
        this.id = string;
    }
}
