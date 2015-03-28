package br.com.mentores.gestao.bibliografia;

import java.util.*;
import br.com.mentores.util.*;
import java.sql.*;

public class Artigo
{
    private int codigoArtigo;
    private String titulo;
    private String comentario;
    private String resumo;
    private int numeroPaginas;
    private int numeroVisitas;
    private Vector paginas;
    private Vector autores;
    private Vector comentarios;
    
    public Artigo() {
        this.numeroPaginas = 1;
    }
    
    public Artigo(final String titulo, final String comentario, final String resumo, final int numeroPaginas, final int numeroVisitas) {
        this.numeroPaginas = 1;
        this.setTitulo(titulo);
        this.setComentario(comentario);
        this.setResumo(resumo);
        this.setNumeroPaginas(numeroPaginas);
        this.setNumeroVisitas(numeroVisitas);
    }
    
    public Artigo(final int codigoArtigo, final String titulo, final String comentario, final String resumo, final int numeroPaginas, final int numeroVisitas) {
        this.numeroPaginas = 1;
        this.setCodigoArtigo(codigoArtigo);
        this.setTitulo(titulo);
        this.setComentario(comentario);
        this.setResumo(resumo);
        this.setNumeroPaginas(numeroPaginas);
        this.setNumeroVisitas(numeroVisitas);
    }
    
    public Artigo(final int codigoArtigo) {
        this.numeroPaginas = 1;
        this.setCodigoArtigo(codigoArtigo);
    }
    
    public Artigo(final int codigo, final String titulo, final String comentario) {
        this.numeroPaginas = 1;
        this.setCodigoArtigo(codigo);
        this.setTitulo(titulo);
        this.setComentario(comentario);
    }
    
    public Artigo(final int codigo, final String titulo, final String comentario, final String resumo) {
        this.numeroPaginas = 1;
        this.setCodigoArtigo(codigo);
        this.setTitulo(titulo);
        this.setComentario(comentario);
        this.setResumo(resumo);
    }
    
    public void addArtigo(final ConexaoDB conexao) throws Exception {
        final String sql = "INSERT INTO ARTIGO (TITULO,COMENTARIO,RESUMO,NUMERO_PAGINAS,NUMERO_VISITAS) VALUES ('" + this.getTitulo() + "','" + this.getComentario() + "','" + this.getResumo() + "','" + this.getNumeroPaginas() + "','" + this.getNumeroVisitas() + "')";
        conexao.executarAtualizacao(sql);
    }
    
    public void removeArtigo(final ConexaoDB conexao) throws Exception {
        final String sql = "DELETE FROM ARTIGO WHERE CODIGO_ARTIGO = '" + this.getCodigoArtigo() + "'";
        conexao.executarAtualizacao(sql);
    }
    
    public void setArtigo(final ConexaoDB conexao) throws Exception {
        if (this.getCodigoArtigo() > 0) {
            final String query = "UPDATE ARTIGO SET TITULO ='" + this.getTitulo() + "',COMENTARIO ='" + this.getComentario() + "',RESUMO ='" + this.getResumo() + "',NUMERO_PAGINAS ='" + this.getNumeroPaginas() + "',NUMERO_VISITAS ='" + this.getNumeroVisitas() + "' WHERE CODIGO_ARTIGO = " + this.getCodigoArtigo();
            conexao.executarAtualizacao(query);
            return;
        }
        throw new Exception("C\u00f3digo inv\u00e1lido.");
    }
    
    public Artigo getArtigo(final ConexaoDB conexao) throws Exception {
        if (this.codigoArtigo > 0) {
            final String sql = "SELECT CODIGO_ARTIGO, TITULO, COMENTARIO, RESUMO, NUMERO_PAGINAS, NUMERO_VISITAS FROM ARTIGO WHERE CODIGO_ARTIGO = " + this.getCodigoArtigo();
            final ResultSet rsArtigo = conexao.executarConsulta(sql);
            if (rsArtigo.next()) {
                this.setCodigoArtigo(rsArtigo.getInt("CODIGO_ARTIGO"));
                this.setTitulo(rsArtigo.getString("TITULO"));
                this.setComentario(rsArtigo.getString("COMENTARIO"));
                this.setResumo(rsArtigo.getString("RESUMO"));
                this.setNumeroPaginas(rsArtigo.getInt("NUMERO_PAGINAS"));
                this.setNumeroVisitas(rsArtigo.getInt("NUMERO_VISITAS"));
            }
            rsArtigo.close();
        }
        return this;
    }
    
    public void incrementarVisitas(final ConexaoDB conexao) throws Exception {
        if (this.codigoArtigo > 0) {
            final String sql = "UPDATE ARTIGO SET NUMERO_VISITAS = NUMERO_VISITAS + 1 WHERE CODIGO_ARTIGO = " + this.getCodigoArtigo();
            conexao.executarAtualizacao(sql);
            this.setNumeroVisitas(this.numeroVisitas + 1);
        }
    }
    
    public Vector getAutores() {
        return this.autores;
    }
    
    public Vector getAutores(final ConexaoDB conexao) throws Exception {
        this.autores = new Vector();
        if (this.codigoArtigo > 0) {
            final String sql = "SELECT A.CODIGO_AUTOR, A.NOME_COMPLETO, A.EMAIL, A.FOTO, AA.PRINCIPAL FROM AUTOR A, AUTOR_ARTIGO AA WHERE A.CODIGO_AUTOR = AA.AUTOR AND AA.ARTIGO = " + this.getCodigoArtigo() + " ORDER BY PRINCIPAL DESC";
            final ResultSet rsAutores = conexao.executarConsulta(sql);
            while (rsAutores.next()) {
                this.autores.addElement(new Autor(rsAutores.getInt("CODIGO_AUTOR"), rsAutores.getString("NOME_COMPLETO"), rsAutores.getString("EMAIL"), rsAutores.getString("FOTO")));
            }
            rsAutores.close();
        }
        return this.autores;
    }
    
    public String getTodoConteudo(final ConexaoDB conexao) throws Exception {
        final StringBuffer conteudo = new StringBuffer();
        final String sql = "SELECT CONTEUDO FROM PAGINA_ARTIGO WHERE ARTIGO = " + this.getCodigoArtigo() + " ORDER BY NUMERO_PAGINA ASC";
        final ResultSet rsConteudo = conexao.executarConsulta(sql);
        while (rsConteudo.next()) {
            conteudo.append(rsConteudo.getString(1));
            conteudo.append("<br><br>");
        }
        rsConteudo.close();
        return conteudo.toString();
    }
    
    public int getCodigoArtigo() {
        return this.codigoArtigo;
    }
    
    public String getComentario() {
        return this.comentario;
    }
    
    public Vector getComentarios() {
        return this.comentarios;
    }
    
    public int getNumeroPaginas() {
        return this.numeroPaginas;
    }
    
    public int getNumeroVisitas() {
        return this.numeroVisitas;
    }
    
    public Vector getPaginas() {
        return this.paginas;
    }
    
    public String getResumo() {
        return this.resumo;
    }
    
    public String getTitulo() {
        return this.titulo;
    }
    
    public void setAutores(final Vector autores) {
        this.autores = autores;
    }
    
    public void setCodigoArtigo(final int codigoArtigo) {
        this.codigoArtigo = codigoArtigo;
    }
    
    public void setComentario(final String comentario) {
        this.comentario = comentario;
    }
    
    public void setComentarios(final Vector comentarios) {
        this.comentarios = comentarios;
    }
    
    public void setNumeroPaginas(final int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }
    
    public void setNumeroVisitas(final int numeroVisitas) {
        this.numeroVisitas = numeroVisitas;
    }
    
    public void setPaginas(final Vector paginas) {
        this.paginas = paginas;
    }
    
    public void setResumo(final String resumo) {
        this.resumo = resumo;
    }
    
    public void setTitulo(final String titulo) {
        this.titulo = titulo;
    }
}
