package br.com.mentores.site.taglib;

import java.util.*;

public interface TreeViewDataNo
{
    String getLabel();
    
    String getKey();
    
    String getKeyId();
    
    void setKeyId(String p0);
    
    String getDestinationKey();
    
    void setDestinationKey(String p0);
    
    TreeViewDataNo getSuperNode();
    
    Vector getSubNodes();
    
    String[] getDataHeader();
    
    String[] getAlignHeader();
    
    String[] getDataNode();
    
    String[] getAlignData();
    
    String getIconXpand();
    
    String getIconColap();
    
    String getIconNode();
    
    void setIconXpand(String p0);
    
    void setIconColap(String p0);
    
    void setIconNode(String p0);
}
