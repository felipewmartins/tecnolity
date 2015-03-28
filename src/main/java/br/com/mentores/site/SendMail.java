package br.com.mentores.site;

import javax.servlet.jsp.tagext.*;
import javax.mail.internet.*;
import javax.mail.*;
import java.util.*;

public class SendMail extends TagSupport
{
    private String smtpServer;
    private String[][] to;
    private String[][] cc;
    private String[][] bcc;
    private String fromMail;
    private String fromName;
    private String subject;
    private String body;
    
    public SendMail(final String smtpServer, final String[][] to, final String fromName, final String fromMail, final String subject, final String message) {
        this.fromMail = "";
        this.fromName = "";
        this.subject = "Sem Assunto";
        this.body = "Mensagem ausente.";
        this.setSmtpServer(smtpServer);
        this.setTo(to);
        this.setFromName(fromName);
        this.setFromMail(fromMail);
        this.setSubject(subject);
        this.setBody(message);
    }
    
    public SendMail(final String smtpServer, final String toName, final String toAddress, final String fromName, final String fromAddress, final String subject, final String message) {
        this.fromMail = "";
        this.fromName = "";
        this.subject = "Sem Assunto";
        this.body = "Mensagem ausente.";
        this.setSmtpServer(smtpServer);
        this.setTo(toName, toAddress);
        this.setFromName(fromName);
        this.setFromMail(fromAddress);
        this.setSubject(subject);
        this.setBody(message);
    }
    
    public void send() throws Exception {
        final Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        if (this.smtpServer == null) {
            throw new Exception("O servidor SMTP n\u00e3o foi informado.");
        }
        ((Hashtable<String, String>)props).put("mail.host", this.smtpServer);
        props.setProperty("mail.user", "");
        props.setProperty("mail.password", "");
        final Session mailSession = Session.getInstance(props, (Authenticator)null);
        final Message msg = (Message)new MimeMessage(mailSession);
        msg.setFrom((Address)new InternetAddress(this.fromMail, this.fromName));
        if (this.to == null) {
            throw new Exception("Os destinat\u00e1rios n\u00e3o foram informados.");
        }
        final InternetAddress[] addressTo = new InternetAddress[this.to.length];
        for (int i = 0; i < this.to.length; ++i) {
            addressTo[i] = new InternetAddress(this.to[i][0], this.to[i][1]);
        }
        if (addressTo.length > 0) {
            msg.setRecipients(Message$RecipientType.TO, (Address[])addressTo);
        }
        if (this.cc != null) {
            final InternetAddress[] addressCc = new InternetAddress[this.cc.length];
            for (int j = 0; j < this.cc.length; ++j) {
                addressCc[j] = new InternetAddress(this.cc[j][0], this.cc[j][1]);
            }
            if (addressCc.length > 0) {
                msg.setRecipients(Message$RecipientType.CC, (Address[])addressCc);
            }
        }
        if (this.bcc != null) {
            final InternetAddress[] addressBcc = new InternetAddress[this.bcc.length];
            for (int j = 0; j < this.bcc.length; ++j) {
                addressBcc[j] = new InternetAddress(this.bcc[j][0], this.bcc[j][1]);
            }
            if (addressBcc.length > 0) {
                msg.setRecipients(Message$RecipientType.BCC, (Address[])addressBcc);
            }
        }
        msg.setSubject(this.subject);
        final MimeBodyPart textPart = new MimeBodyPart();
        textPart.setContent((Object)this.body, "text/plain");
        final Multipart mp = (Multipart)new MimeMultipart();
        mp.addBodyPart((BodyPart)textPart);
        msg.setContent(mp);
        Transport.send(msg);
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
    
    public void setTo(final String toName, final String toAddress) {
        this.to = new String[1][2];
        this.getTo()[0][0] = toAddress;
        this.getTo()[0][1] = toName;
    }
}
