package br.com.mentores.site.taglib;

import javax.servlet.jsp.tagext.*;
import javax.servlet.*;
import javax.servlet.jsp.*;
import java.util.*;
import java.io.*;

public class TreeViewDataTag extends TagSupport
{
    private TreeViewDataNo treeRoot;
    private String treeTitle;
    private int level;
    private String linkPrefix;
    private String linkNode;
    private String linkTarget;
    private String keyExpand;
    private String[] keysExpand;
    private boolean showData;
    private boolean expandEnabled;
    private boolean expandAll;
    private String headerColor;
    private String contentColor;
    private String headerWidth;
    private String iconXpand;
    private String iconColap;
    private String iconNode;
    private String destinationId;
    private int cellpaddingHeader;
    private int cellpaddingTree;
    private String width;
    
    public TreeViewDataTag() {
        this.treeTitle = "";
        this.linkTarget = "_top";
        this.showData = true;
        this.expandEnabled = true;
        this.headerColor = "dddddd";
        this.contentColor = "eeeeee";
        this.cellpaddingHeader = 2;
        this.cellpaddingTree = 2;
    }
    
    public int doStartTag() throws JspException {
        final JspWriter out = super.pageContext.getOut();
        final ServletRequest req = super.pageContext.getRequest();
        this.keysExpand = new String[this.level];
        for (int i = 0; i < this.level; ++i) {
            if (req.getParameter(String.valueOf(this.keyExpand) + i) != null) {
                this.keysExpand[i] = req.getParameter(String.valueOf(this.keyExpand) + i);
            }
            else {
                this.keysExpand[i] = "";
            }
        }
        try {
            StringBuffer html = new StringBuffer();
            html.append("<tr>");
            html.append("<td valign='top'>");
            html.append("<table border='0' cellspacing='1' cellpadding='" + this.cellpaddingHeader + "' width='100%'>");
            html.append("<tr><td bgcolor='#" + this.headerColor + "'><font face='Arial' size='2'><b>" + ((this.treeRoot.getLabel() != null) ? this.treeRoot.getLabel() : this.treeTitle) + "</b></font>&nbsp;</td>");
            if (this.isShowData() && this.treeRoot.getDataHeader() != null) {
                for (int j = 0; j < this.treeRoot.getDataHeader().length; ++j) {
                    html.append("<td " + ((this.treeRoot.getAlignHeader() != null) ? ("align='" + this.treeRoot.getAlignHeader()[j] + "'") : "") + " bgcolor='#" + this.headerColor + "' " + ((this.headerWidth != null) ? ("width='" + this.headerWidth + "'") : "") + "><font face='Arial' size='2'><b>" + this.treeRoot.getDataHeader()[j] + "</b></font>&nbsp;</td>");
                }
            }
            html.append("</tr>");
            out.print(html.toString());
            if (this.treeRoot.getSubNodes() != null) {
                this.printNode(out, req, this.keysExpand.length - 1, null, this.treeRoot.getSubNodes());
            }
            html = new StringBuffer();
            html.append("</table>");
            html.append("</td>");
            html.append("</tr>");
            out.print(html.toString());
        }
        catch (Exception e) {
            e.printStackTrace(new PrintWriter((Writer)out));
        }
        return 0;
    }
    
    private void printNode(final JspWriter out, final ServletRequest req, final int level, final String lnk, final Vector subNodes) throws IOException {
        if (subNodes != null) {
            for (int j = 0; j < subNodes.size(); ++j) {
                final StringBuffer html = new StringBuffer();
                final TreeViewDataNo node = subNodes.get(j);
                if (node.getIconColap() != null) {
                    this.iconColap = node.getIconColap();
                }
                if (node.getIconNode() != null) {
                    this.iconNode = node.getIconNode();
                }
                if (node.getIconXpand() != null) {
                    this.iconXpand = node.getIconXpand();
                }
                final String lastLink = (lnk != null) ? lnk : "";
                final String link = String.valueOf((lnk != null) ? lnk : "") + "&" + this.keyExpand + level + "=" + node.getKey();
                html.append("<tr>");
                html.append("<td bgcolor='#" + this.contentColor + "'><table border='0' cellspacing='0' cellpadding='" + this.cellpaddingTree + "'><tr>");
                for (int i = level; i < this.keysExpand.length; ++i) {
                    if (i == this.keysExpand.length - 1) {
                        if (node.getSubNodes() != null && node.getSubNodes().size() > 0) {
                            if (this.expandEnabled) {
                                if (!this.keysExpand[level].equals(node.getKey())) {
                                    html.append("<td align='center' valign='top' bgcolor='#" + this.contentColor + "' width='22'><a href='" + this.linkPrefix + link + "'><img src='" + this.iconXpand + "' border='0'></a></td>");
                                }
                                else {
                                    html.append("<td align='center' valign='top' bgcolor='#" + this.contentColor + "' width='22'><a href='" + this.linkPrefix + lastLink + "'><img src='" + this.iconColap + "' border='0'></a></td>");
                                }
                            }
                            else if (!this.keysExpand[level].equals(node.getKey())) {
                                html.append("<td align='center' valign='top' bgcolor='#" + this.contentColor + "' width='22'><img src='" + this.iconXpand + "' border='0'></td>");
                            }
                            else {
                                html.append("<td align='center' valign='top' bgcolor='#" + this.contentColor + "' width='22'><img src='" + this.iconColap + "' border='0'></td>");
                            }
                        }
                        else {
                            html.append("<td align='center' valign='top' bgcolor='#" + this.contentColor + "' width='22'><img src='" + this.iconNode + "' border='0'></td>");
                        }
                    }
                    else {
                        html.append("<td bgcolor='#" + this.contentColor + "' width='22'>&nbsp;</td>");
                    }
                }
                if (this.linkNode != null) {
                    if (this.destinationId != null) {
                        html.append("<td valign='top' align='left' bgcolor='#" + this.contentColor + "'><font face='Arial' size='2'><a href='" + this.linkNode + "&" + this.destinationId + "=" + node.getDestinationKey() + "&" + node.getKeyId() + "=" + node.getKey() + "' target='" + this.linkTarget + "'>" + node.getLabel() + "</a></font></td>");
                    }
                    else {
                        html.append("<td valign='top' align='left' bgcolor='#" + this.contentColor + "'><font face='Arial' size='2'><a href='" + this.linkNode + "&" + node.getKeyId() + "=" + node.getKey() + "' target='" + this.linkTarget + "'>" + node.getLabel() + "</a></font></td>");
                    }
                }
                else {
                    html.append("<td valign='top' align='left' bgcolor='#" + this.contentColor + "'><font face='Arial' size='2'>" + node.getLabel() + "</font></td>");
                }
                html.append("</tr></table></td>");
                final String[] dataNode = node.getDataNode();
                if (this.isShowData()) {
                    for (int k = 0; k < dataNode.length; ++k) {
                        html.append("<td " + ((this.treeRoot.getAlignData() != null) ? ("align='" + this.treeRoot.getAlignData()[k] + "'") : "") + " bgcolor='#" + this.contentColor + "'><font face='Arial' size='2'>" + dataNode[k] + "</font></td>");
                    }
                }
                html.append("</tr>");
                out.print(html.toString());
                if (!this.expandAll) {
                    if (level > 1 && this.keysExpand[level].equals(node.getKey())) {
                        this.printNode(out, req, level - 1, link, node.getSubNodes());
                    }
                }
                else if (level > 1) {
                    this.printNode(out, req, level - 1, link, node.getSubNodes());
                }
            }
        }
    }
    
    public TreeViewDataNo getTreeRoot() {
        return this.treeRoot;
    }
    
    public void setTreeRoot(final TreeViewDataNo no) {
        this.treeRoot = no;
    }
    
    public int getLevel() {
        return this.level;
    }
    
    public void setLevel(final int i) {
        this.level = i;
    }
    
    public String getLinkPrefix() {
        return this.linkPrefix;
    }
    
    public void setLinkPrefix(final String string) {
        this.linkPrefix = string;
    }
    
    public String getKeyExpand() {
        return this.keyExpand;
    }
    
    public void setKeyExpand(final String string) {
        this.keyExpand = string;
    }
    
    public String[] getKeysExpand() {
        return this.keysExpand;
    }
    
    public void setKeysExpand(final String[] is) {
        this.keysExpand = is;
    }
    
    public String getContentColor() {
        return this.contentColor;
    }
    
    public String getHeaderColor() {
        return this.headerColor;
    }
    
    public void setContentColor(final String string) {
        this.contentColor = string;
    }
    
    public void setHeaderColor(final String string) {
        this.headerColor = string;
    }
    
    public String getIconColap() {
        return this.iconColap;
    }
    
    public String getIconXpand() {
        return this.iconXpand;
    }
    
    public void setIconColap(final String string) {
        this.iconColap = string;
    }
    
    public void setIconXpand(final String string) {
        this.iconXpand = string;
    }
    
    public String getIconNode() {
        return this.iconNode;
    }
    
    public void setIconNode(final String string) {
        this.iconNode = string;
    }
    
    public String getLinkNode() {
        return this.linkNode;
    }
    
    public void setLinkNode(final String string) {
        this.linkNode = string;
    }
    
    public String getHeaderWidth() {
        return this.headerWidth;
    }
    
    public void setHeaderWidth(final String string) {
        this.headerWidth = string;
    }
    
    public String getTreeTitle() {
        return this.treeTitle;
    }
    
    public void setTreeTitle(final String string) {
        this.treeTitle = string;
    }
    
    public boolean isShowData() {
        return this.showData;
    }
    
    public void setShowData(final boolean show) {
        this.showData = show;
    }
    
    public String getLinkTarget() {
        return this.linkTarget;
    }
    
    public void setLinkTarget(final String string) {
        this.linkTarget = string;
    }
    
    public String getDestinationId() {
        return this.destinationId;
    }
    
    public void setDestinationId(final String string) {
        this.destinationId = string;
    }
    
    public boolean isExpandAll() {
        return this.expandAll;
    }
    
    public void setExpandAll(final boolean expand) {
        this.expandAll = expand;
    }
    
    public int getCellpaddingHeader() {
        return this.cellpaddingHeader;
    }
    
    public int getCellpaddingTree() {
        return this.cellpaddingTree;
    }
    
    public void setCellpaddingHeader(final int i) {
        this.cellpaddingHeader = i;
    }
    
    public void setCellpaddingTree(final int i) {
        this.cellpaddingTree = i;
    }
    
    public void setExpandEnabled(final boolean b) {
        this.expandEnabled = b;
    }
}
