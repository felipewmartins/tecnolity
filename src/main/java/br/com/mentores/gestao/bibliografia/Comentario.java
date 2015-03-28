package br.com.mentores.gestao.bibliografia;

import br.com.mentores.util.*;
import java.util.*;
import java.sql.*;

public class Comentario
{
    private int codigo;
    private String nome;
    private String email;
    private int nota;
    private String comentario;
    private Calendario dataEnvio;
    private Artigo artigo;
    
    public Comentario() {
        this.nota = -1;
    }
    
    public Comentario(final String nome, final String email, final int nota, final String comentario, final Calendario dataEnvio, final Artigo artigo) {
        this.nota = -1;
        this.setNome(nome);
        this.setEmail(email);
        this.setNota(nota);
        this.setComentario(comentario);
        this.setDataEnvio(dataEnvio);
        this.setArtigo(artigo);
    }
    
    public Comentario(final int codigo, final String nome, final String email, final String comentario, final Calendario dataEnvio, final Artigo artigo) {
        this.nota = -1;
        this.setCodigo(codigo);
        this.setNome(nome);
        this.setEmail(email);
        this.setComentario(comentario);
        this.setDataEnvio(dataEnvio);
        this.setArtigo(artigo);
    }
    
    public void addComentario(final ConexaoDB conexao) throws Exception {
        if (!this.getNome().equals("") || !this.getEmail().equals("") || !this.getComentario().equals("")) {
            final String sql = "INSERT INTO COMENTARIO (NOME,EMAIL,NOTA,COMENTARIO,DATA_ENVIO,ARTIGO) values ('" + this.getNome() + "','" + this.getEmail() + "'," + ((this.getNota() >= 0) ? String.valueOf(this.getNota()) : "null") + ",'" + this.getComentario() + "','" + this.getDataEnvio().get("yyyy-MM-dd hh:mm") + "'," + this.getArtigo().getCodigoArtigo() + ")";
            conexao.executarAtualizacao(sql);
        }
    }
    
    public void removeComentario(final ConexaoDB conexao) throws Exception {
        final String sql = "DELETE FROM COMENTARIO WHERE CODIGO_COMENTARIO = '" + this.getCodigo() + "'";
        conexao.executarAtualizacao(sql);
    }
    
    public void setComentario(final ConexaoDB conexao) throws Exception {
        if (this.getCodigo() > 0) {
            final String query = "UPDATE COMENTARIO SET NOME ='" + this.getNome() + "',EMAIL ='" + this.getEmail() + "',COMENTARIO ='" + this.getComentario() + "',DATA_ENVIO ='" + this.getDataEnvio() + "',ARTIGO ='" + this.getArtigo().getCodigoArtigo() + "' WHERE CODIGO_COMENTARIO = " + this.getCodigo();
            conexao.executarAtualizacao(query);
            return;
        }
        throw new Exception("C\u00f3digo inv\u00e1lido.");
    }
    
    public Comentario getComentario(final ConexaoDB conexao) throws Exception {
        if (this.codigo > 0) {
            final String sql = "SELECT CODIGO_COMENTARIO, NOME, EMAIL, COMENTARIO, DATA_ENVIO, ARTIGO FROM COMENTARIO WHERE CODIGO_COMENTARIO = " + this.getCodigo();
            final ResultSet rsComentario = conexao.executarConsulta(sql);
            if (rsComentario.next()) {
                this.setCodigo(rsComentario.getInt("CODIGO_COMENTARIO"));
                this.setNome(rsComentario.getString("NOME"));
                this.setEmail(rsComentario.getString("EMAIL"));
                this.setComentario(rsComentario.getString("COMENTARIO"));
                this.setDataEnvio(new Calendario(rsComentario.getTimestamp("DATA_ENVIO")));
                this.setArtigo(new Artigo(rsComentario.getInt("ARTIGO")));
            }
            rsComentario.close();
        }
        return this;
    }
    
    public Artigo getArtigo() {
        return this.artigo;
    }
    
    public int getCodigo() {
        return this.codigo;
    }
    
    public String getComentario() {
        return this.comentario;
    }
    
    public Calendario getDataEnvio() {
        return this.dataEnvio;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public String getNome() {
        return this.nome;
    }
    
    public void setArtigo(final Artigo artigo) {
        this.artigo = artigo;
    }
    
    public void setCodigo(final int codigo) {
        this.codigo = codigo;
    }
    
    public void setComentario(final String comentario) {
        this.comentario = comentario;
    }
    
    public void setDataEnvio(final Calendario dataEnvio) {
        this.dataEnvio = dataEnvio;
    }
    
    public void setEmail(final String email) {
        this.email = email;
    }
    
    public void setNome(final String nome) {
        this.nome = nome;
    }
    
    public int getNota() {
        return this.nota;
    }
    
    public void setNota(final int i) {
        this.nota = i;
    }
}
