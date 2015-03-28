package br.com.mentores.site.taglib;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;

public class XSLTApplyTag extends BodyTagSupport
{
    private String xsl;
    
    public int doAfterBody() throws JspException {
        final XSLTProcessorBean xslBean = new XSLTProcessorBean();
        final String xml = super.bodyContent.getString().trim();
        try {
            if (xml.equals("")) {
                throw new JspException("O corpo da tag deve conter XML.");
            }
            xslBean.process(xml, this.xsl, this.getPreviousOut());
        }
        catch (Exception e) {
            throw new JspException(e.getMessage());
        }
        return 0;
    }
    
    public String getXsl() {
        return this.xsl;
    }
    
    public void setXsl(final String string) {
        this.xsl = string;
    }
}
