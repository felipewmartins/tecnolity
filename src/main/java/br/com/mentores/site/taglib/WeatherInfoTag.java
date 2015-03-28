package br.com.mentores.site.taglib;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import br.com.mentores.util.*;
import java.net.*;
import java.io.*;

public class WeatherInfoTag extends TagSupport
{
    private String locName;
    private String locId;
    private String locTime;
    private String locLatitud;
    private String locLongetud;
    private String locSunEarly;
    private String locSunSchine;
    private String locZone;
    private String temperatureUnit;
    private String distanceUnit;
    private String velocityUnit;
    private int daysNumber;
    private String partnerId;
    private String licenseKey;
    private String productCode;
    private String unit;
    private String iconLocation;
    private String iconSize;
    
    public WeatherInfoTag() {
        this.temperatureUnit = "C";
        this.distanceUnit = "km";
        this.velocityUnit = "km/h";
        this.productCode = "xoap";
        this.unit = "m";
        this.iconSize = "64x64";
    }
    
    public int doStartTag() throws JspException {
        final JspWriter out = super.pageContext.getOut();
        try {
            Web.cacheURLContent("http://xoap.weather.com/weather/local/BRXX0290?dayf=7&par=1003172358&key=173e436909f4d525&prod=xoap&unit=m", "c:/mentores");
        }
        catch (Exception e) {
            e.printStackTrace(new PrintWriter((Writer)out));
        }
        return 0;
    }
    
    private void cacheWheatherInformation(final String url) throws Exception {
        final Calendario calendario = new Calendario();
        final BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(String.valueOf(calendario.get("yyyy-MM-dd")) + ".xml"));
        final URL urlXml = new URL(url);
        final InputStream is = (InputStream)urlXml.getContent();
        int ch;
        while ((ch = is.read()) >= 0) {
            output.write(ch);
        }
        output.close();
    }
    
    public int getDaysNumber() {
        return this.daysNumber;
    }
    
    public String getIconLocation() {
        return this.iconLocation;
    }
    
    public String getIconSize() {
        return this.iconSize;
    }
    
    public String getLicenseKey() {
        return this.licenseKey;
    }
    
    public String getLocId() {
        return this.locId;
    }
    
    public String getLocName() {
        return this.locName;
    }
    
    public String getPartnerId() {
        return this.partnerId;
    }
    
    public String getUnit() {
        return this.unit;
    }
    
    public void setDaysNumber(final int i) {
        this.daysNumber = i;
    }
    
    public void setIconLocation(final String string) {
        this.iconLocation = string;
    }
    
    public void setIconSize(final String string) {
        this.iconSize = string;
    }
    
    public void setLicenseKey(final String string) {
        this.licenseKey = string;
    }
    
    public void setLocId(final String string) {
        this.locId = string;
    }
    
    public void setLocName(final String string) {
        this.locName = string;
    }
    
    public void setPartnerId(final String string) {
        this.partnerId = string;
    }
    
    public void setUnit(final String string) {
        this.unit = string;
    }
}
