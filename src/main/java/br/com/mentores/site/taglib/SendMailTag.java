package br.com.mentores.site.taglib;

import javax.servlet.jsp.tagext.*;
import java.util.*;
import javax.mail.internet.*;
import javax.mail.*;
import javax.servlet.jsp.*;

public class SendMailTag extends TagSupport
{
    private String smtpServer;
    private String[][] to;
    private String[][] cc;
    private String[][] bcc;
    private String fromMail;
    private String fromName;
    private String subject;
    private String body;
    
    public int doStartTag() throws JspException {
        try {
            final Properties props = new Properties();
            props.setProperty("mail.transport.protocol", "smtp");
            props.setProperty("mail.host", this.smtpServer);
            props.setProperty("mail.user", this.fromMail);
            props.setProperty("mail.password", "");
            final Session mailSession = Session.getDefaultInstance(props, (Authenticator)null);
            final Message msg = (Message)new MimeMessage(mailSession);
            msg.setFrom((Address)new InternetAddress(this.fromMail, this.fromName));
            final InternetAddress[] addressTo = new InternetAddress[this.to.length];
            for (int i = 0; i < this.to.length; ++i) {
                addressTo[i] = new InternetAddress(this.to[i][0], this.to[i][1]);
            }
            if (addressTo.length > 0) {
                msg.setRecipients(Message$RecipientType.TO, (Address[])addressTo);
            }
            final InternetAddress[] addressCc = new InternetAddress[this.cc.length];
            for (int j = 0; j < this.cc.length; ++j) {
                addressCc[j] = new InternetAddress(this.cc[j][0], this.cc[j][1]);
            }
            if (addressCc.length > 0) {
                msg.setRecipients(Message$RecipientType.CC, (Address[])addressCc);
            }
            final InternetAddress[] addressBcc = new InternetAddress[this.bcc.length];
            for (int k = 0; k < this.bcc.length; ++k) {
                addressBcc[k] = new InternetAddress(this.bcc[k][0], this.bcc[k][1]);
            }
            if (addressBcc.length > 0) {
                msg.setRecipients(Message$RecipientType.BCC, (Address[])addressBcc);
            }
            msg.setSubject(this.subject);
            final MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent((Object)this.body, "text/plain");
            final Multipart mp = (Multipart)new MimeMultipart();
            mp.addBodyPart((BodyPart)textPart);
            msg.setContent(mp);
            Transport.send(msg);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public String[][] getBcc() {
        return this.bcc;
    }
    
    public String getBody() {
        return this.body;
    }
    
    public String[][] getCc() {
        return this.cc;
    }
    
    public String getFromMail() {
        return this.fromMail;
    }
    
    public String getFromName() {
        return this.fromName;
    }
    
    public String getSmtpServer() {
        return this.smtpServer;
    }
    
    public String getSubject() {
        return this.subject;
    }
    
    public String[][] getTo() {
        return this.to;
    }
    
    public void setBcc(final String[][] strings) {
        this.bcc = strings;
    }
    
    public void setBody(final String string) {
        this.body = string;
    }
    
    public void setCc(final String[][] strings) {
        this.cc = strings;
    }
    
    public void setFromMail(final String string) {
        this.fromMail = string;
    }
    
    public void setFromName(final String string) {
        this.fromName = string;
    }
    
    public void setSmtpServer(final String string) {
        this.smtpServer = string;
    }
    
    public void setSubject(final String string) {
        this.subject = string;
    }
    
    public void setTo(final String[][] strings) {
        this.to = strings;
    }
}
