package br.com.mentores.gestao.bibliografia;

import br.com.mentores.util.*;

public class EnvioArtigo
{
    private int codigo;
    private Artigo artigo;
    private String remetente;
    private String emailRemetente;
    private String destinatario;
    private String emailDestinatario;
    private String comentario;
    private Calendario dataEnvio;
    
    public EnvioArtigo() {
        this.dataEnvio = new Calendario();
    }
    
    public EnvioArtigo(final Artigo artigo, final String remetente, final String emailRemetente, final String destinatario, final String emailDestinatario) {
        this.setArtigo(artigo);
        this.setRemetente(remetente);
        this.setEmailRemetente(emailRemetente);
        this.setDestinatario(destinatario);
        this.setEmailDestinatario(emailDestinatario);
    }
    
    public void addEnvioArtigo(final ConexaoDB conexao) throws Exception {
        final String sql = "INSERT INTO ENVIO_ARTIGO (ARTIGO, REMETENTE, EMAIL_REMETENTE, DESTINATARIO, EMAIL_DESTINATARIO, COMENTARIO, DATA_ENVIO) VALUES (" + this.getArtigo().getCodigoArtigo() + ",'" + this.getRemetente() + "','" + this.getEmailRemetente() + "','" + this.getDestinatario() + "','" + this.getEmailDestinatario() + "','" + this.getComentario() + "','" + this.dataEnvio.get("aaaa-MM-dd hh:mm:ss") + "')";
        conexao.executarAtualizacao(sql);
    }
    
    public Artigo getArtigo() {
        return this.artigo;
    }
    
    public int getCodigo() {
        return this.codigo;
    }
    
    public Calendario getDataEnvio() {
        return this.dataEnvio;
    }
    
    public String getDestinatario() {
        return this.destinatario;
    }
    
    public String getEmailDestinatario() {
        return this.emailDestinatario;
    }
    
    public String getEmailRemetente() {
        return this.emailRemetente;
    }
    
    public String getRemetente() {
        return this.remetente;
    }
    
    public void setArtigo(final Artigo artigo) {
        this.artigo = artigo;
    }
    
    public void setCodigo(final int i) {
        this.codigo = i;
    }
    
    public void setDataEnvio(final Calendario calendario) {
        this.dataEnvio = calendario;
    }
    
    public void setDestinatario(final String string) {
        this.destinatario = string;
    }
    
    public void setEmailDestinatario(final String string) {
        this.emailDestinatario = string;
    }
    
    public void setEmailRemetente(final String string) {
        this.emailRemetente = string;
    }
    
    public void setRemetente(final String string) {
        this.remetente = string;
    }
    
    public String getComentario() {
        return this.comentario;
    }
    
    public void setComentario(final String string) {
        this.comentario = string;
    }
}
