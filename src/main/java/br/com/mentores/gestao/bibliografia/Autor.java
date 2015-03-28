package br.com.mentores.gestao.bibliografia;

import java.util.*;
import br.com.mentores.util.*;
import java.sql.*;

public class Autor
{
    private int codigo;
    private String nome;
    private String email;
    private String foto;
    private String bibliografia;
    private Vector artigos;
    private String biografia;
    
    public Autor(final int codigo) {
        this.setCodigo(codigo);
    }
    
    public Autor(final String nomeCompleto, final String email, final String foto, final String biografia) {
        this.setNome(nomeCompleto);
        this.setEmail(email);
        this.setFoto(foto);
        this.setBiografia(biografia);
    }
    
    public Autor(final int codigo, final String nome, final String email, final String foto, final String biografia) {
        this.setCodigo(codigo);
        this.setNome(nome);
        this.setEmail(email);
        this.setFoto(foto);
        this.setBiografia(biografia);
    }
    
    public Autor(final int codigo, final String nome, final String email, final String foto) {
        this.setCodigo(codigo);
        this.setNome(nome);
        this.setEmail(email);
        this.setFoto(foto);
    }
    
    public void addAutor(final ConexaoDB conexao) throws Exception {
        final String sql = "INSERT INTO AUTOR (NOME_COMPLETO,EMAIL,FOTO,BIOGRAFIA) VALUES ('" + this.getNome() + "','" + this.getEmail() + "','" + this.getFoto() + "','" + this.getBiografia() + "')";
        conexao.executarAtualizacao(sql);
    }
    
    public void removeAutor(final ConexaoDB conexao) throws Exception {
        final String sql = "DELETE FROM AUTOR WHERE CODIGO_AUTOR = '" + this.getCodigo() + "'";
        conexao.executarAtualizacao(sql);
    }
    
    public void setAutor(final ConexaoDB conexao) throws Exception {
        if (this.getCodigo() > 0) {
            final String query = "UPDATE AUTOR SET NOME_COMPLETO ='" + this.getNome() + "',EMAIL ='" + this.getEmail() + "',FOTO ='" + this.getFoto() + "',BIOGRAFIA ='" + this.getBiografia() + "' WHERE CODIGO_AUTOR = " + this.getCodigo();
            conexao.executarAtualizacao(query);
            return;
        }
        throw new Exception("C\u00f3digo inv\u00e1lido.");
    }
    
    public Autor getAutor(final ConexaoDB conexao) throws Exception {
        if (this.codigo > 0) {
            final String sql = "SELECT CODIGO_AUTOR, NOME_COMPLETO, EMAIL, FOTO, BIOGRAFIA FROM AUTOR WHERE CODIGO_AUTOR = " + this.getCodigo();
            final ResultSet rsAutor = conexao.executarConsulta(sql);
            if (rsAutor.next()) {
                this.setCodigo(rsAutor.getInt("CODIGO_AUTOR"));
                this.setNome(rsAutor.getString("NOME_COMPLETO"));
                this.setEmail(rsAutor.getString("EMAIL"));
                this.setFoto(rsAutor.getString("FOTO"));
                this.setBiografia(rsAutor.getString("BIOGRAFIA"));
            }
            rsAutor.close();
        }
        return this;
    }
    
    public Vector getArtigos() {
        return this.artigos;
    }
    
    public String getBibliografia() {
        return this.bibliografia;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public String getFoto() {
        return this.foto;
    }
    
    public String getNome() {
        return this.nome;
    }
    
    public void setArtigos(final Vector artigos) {
        this.artigos = artigos;
    }
    
    public void setBibliografia(final String bibliografia) {
        this.bibliografia = bibliografia;
    }
    
    public void setEmail(final String email) {
        this.email = email;
    }
    
    public void setFoto(final String foto) {
        this.foto = foto;
    }
    
    public void setNome(final String nome) {
        this.nome = nome;
    }
    
    public int getCodigo() {
        return this.codigo;
    }
    
    public void setCodigo(final int codigo) {
        this.codigo = codigo;
    }
    
    public String getBiografia() {
        return this.biografia;
    }
    
    public void setBiografia(final String biografia) {
        this.biografia = biografia;
    }
}
