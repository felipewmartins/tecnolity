package br.com.mentores.gestao.bibliografia;

import br.com.mentores.util.*;
import java.sql.*;

public class AutorArtigo
{
    private Autor autor;
    private Artigo artigo;
    private boolean principal;
    
    public AutorArtigo(final Autor autor, final Artigo artigo, final boolean principal) {
        this.setAutor(autor);
        this.setArtigo(artigo);
        this.setPrincipal(principal);
    }
    
    public void addAutorArtigo(final ConexaoDB conexao) throws Exception {
        final String query = " INSERT INTO AUTOR_ARTIGO (AUTOR,ARTIGO,PRINCIPAL) VALUES (" + this.getAutor().getCodigo() + "," + this.getArtigo().getCodigoArtigo() + "," + (this.isPrincipal() ? 1 : 0) + ")";
        conexao.executarAtualizacao(query);
    }
    
    public void setAutorArtigo(final ConexaoDB conexao) throws Exception {
        if (this.getArtigo().getCodigoArtigo() > 0) {
            final String query = "UPDATE AUTOR_ARTIGO SET PRINCIPAL =" + (this.isPrincipal() ? 1 : 0) + " WHERE AUTOR =" + this.getAutor().getCodigo() + " AND ARTIGO = " + this.getArtigo().getCodigoArtigo();
            conexao.executarAtualizacao(query);
            return;
        }
        throw new Exception("Artigo Inv\u00e1lido.");
    }
    
    public void removeAutorArtigo(final ConexaoDB conexao) throws Exception {
        final String sql = "DELETE FROM AUTOR_ARTIGO WHERE AUTOR =" + this.getAutor().getCodigo() + " AND ARTIGO = " + this.getArtigo().getCodigoArtigo() + "";
        conexao.executarAtualizacao(sql);
    }
    
    public AutorArtigo getAutorArtigo(final ConexaoDB conexao) throws Exception {
        final String sql = "SELECT AUTOR,ARTIGO,PRINCIPAL FROM AUTOR_ARTIGO WHERE AUTOR =" + this.getAutor().getCodigo() + " AND ARTIGO = " + this.getArtigo().getCodigoArtigo();
        final ResultSet rsAutorArtigo = conexao.executarConsulta(sql);
        if (rsAutorArtigo.next()) {
            this.setAutor(new Autor(rsAutorArtigo.getInt("AUTOR")));
            this.setArtigo(new Artigo(rsAutorArtigo.getInt("ARTIGO")));
            this.setPrincipal(rsAutorArtigo.getBoolean("PRINCIPAL"));
        }
        rsAutorArtigo.close();
        return this;
    }
    
    public Artigo getArtigo() {
        return this.artigo;
    }
    
    public Autor getAutor() {
        return this.autor;
    }
    
    public boolean isPrincipal() {
        return this.principal;
    }
    
    public void setArtigo(final Artigo artigo) {
        this.artigo = artigo;
    }
    
    public void setAutor(final Autor autor) {
        this.autor = autor;
    }
    
    public void setPrincipal(final boolean principal) {
        this.principal = principal;
    }
}
