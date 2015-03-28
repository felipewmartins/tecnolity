package br.com.mentores.site.taglib;

import javax.servlet.jsp.tagext.*;
import java.io.*;
import javax.servlet.jsp.*;

public class ViewColumnKeyTag extends TagSupport
{
    private String query;
    private String column;
    static /* synthetic */ Class class$0;
    
    public int doStartTag() throws JspException {
        try {
            ViewLineDataTag viewLineData = null;
            Class class$0;
            if ((class$0 = ViewColumnKeyTag.class$0) == null) {
                try {
                    class$0 = (ViewColumnKeyTag.class$0 = Class.forName("br.com.mentores.site.taglib.ViewLineDataTag"));
                }
                catch (ClassNotFoundException ex) {
                    throw new NoClassDefFoundError(ex.getMessage());
                }
            }
            viewLineData = (ViewLineDataTag)TagSupport.findAncestorWithClass((Tag)this, class$0);
            viewLineData.addColumnsKey(this.query, this.column);
        }
        catch (Exception e) {
            e.printStackTrace(new PrintWriter((Writer)super.pageContext.getOut()));
        }
        return 0;
    }
    
    public String getColumn() {
        return this.column;
    }
    
    public String getQuery() {
        return this.query;
    }
    
    public void setColumn(final String string) {
        this.column = string;
    }
    
    public void setQuery(final String string) {
        this.query = string;
    }
}
