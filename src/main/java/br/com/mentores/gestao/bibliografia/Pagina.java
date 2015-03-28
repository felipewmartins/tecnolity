package br.com.mentores.gestao.bibliografia;

import br.com.mentores.util.*;
import java.sql.*;

public class Pagina
{
    private int numeroPagina;
    private String conteudo;
    private int numeroVisitas;
    private Artigo artigo;
    
    public Pagina() {
        this.numeroVisitas = 0;
    }
    
    public Pagina(final int numeroPagina, final Artigo artigo, final String conteudo, final int numeroVisitas) {
        this.numeroVisitas = 0;
        this.setNumeroPagina(numeroPagina);
        this.setArtigo(artigo);
        this.setConteudo(conteudo);
        this.setNumeroVisitas(numeroVisitas);
    }
    
    public Pagina(final int numeroPagina, final Artigo artigo) {
        this.numeroVisitas = 0;
        this.setNumeroPagina(numeroPagina);
        this.setArtigo(artigo);
    }
    
    public Pagina(final int numeroPagina, final String conteudo, final int numeroVisitas, final Artigo artigo) {
        this.numeroVisitas = 0;
        this.setNumeroPagina(numeroPagina);
        this.setConteudo(conteudo);
        this.setNumeroVisitas(numeroVisitas);
        this.setArtigo(artigo);
    }
    
    public void addPagina(final ConexaoDB conexao) throws Exception {
        String query = "INSERT INTO PAGINA_ARTIGO (NUMERO_PAGINA,ARTIGO,CONTEUDO,NUMERO_VISITAS) VALUES ('" + this.getNumeroPagina() + "','" + this.getArtigo().getCodigoArtigo() + "','" + this.getConteudo() + "','" + this.getNumeroVisitas() + "')";
        conexao.executarAtualizacao(query);
        query = " UPDATE ARTIGO SET NUMERO_PAGINAS = NUMERO_PAGINAS + 1 WHERE CODIGO_ARTIGO = " + this.getArtigo().getCodigoArtigo();
        conexao.executarAtualizacao(query);
    }
    
    public void removePagina(final ConexaoDB conexao) throws Exception {
        String sql = "DELETE FROM PAGINA_ARTIGO WHERE NUMERO_PAGINA = " + this.getNumeroPagina() + " AND ARTIGO = " + this.getArtigo().getCodigoArtigo() + "";
        conexao.executarAtualizacao(sql);
        sql = " UPDATE ARTIGO SET NUMERO_PAGINAS = NUMERO_PAGINAS - 1 WHERE CODIGO_ARTIGO = " + this.getArtigo().getCodigoArtigo() + "";
        conexao.executarAtualizacao(sql);
    }
    
    public void setPagina(final ConexaoDB conexao) throws Exception {
        if (this.getNumeroPagina() > 0) {
            final String query = "UPDATE PAGINA_ARTIGO SET CONTEUDO ='" + this.getConteudo() + ",NUMERO_VISITAS =" + this.getNumeroVisitas() + " WHERE NUMERO_PAGINA = " + this.getNumeroPagina() + " AND ARTIGO = " + this.getArtigo().getCodigoArtigo();
            conexao.executarAtualizacao(query);
            return;
        }
        throw new Exception("N\u00famero de p\u00e1gina inv\u00e1lido.");
    }
    
    public Pagina getPagina(final ConexaoDB conexao) throws Exception {
        if (this.numeroPagina > 0 && this.artigo != null) {
            final String sql = "SELECT CONTEUDO FROM PAGINA_ARTIGO WHERE ARTIGO = " + this.artigo.getCodigoArtigo() + " AND NUMERO_PAGINA = " + this.getNumeroPagina();
            final ResultSet rsPagina = conexao.executarConsulta(sql);
            if (rsPagina.next()) {
                this.setConteudo(rsPagina.getString("CONTEUDO"));
            }
            rsPagina.close();
        }
        return this;
    }
    
    public void incrementarVisitas(final ConexaoDB conexao) throws Exception {
        if (this.numeroPagina > 0 && this.artigo != null) {
            final String sql = "UPDATE PAGINA_ARTIGO SET NUMERO_VISITAS = NUMERO_VISITAS + 1 WHERE ARTIGO = " + this.artigo.getCodigoArtigo() + " AND NUMERO_PAGINA = " + this.getNumeroPagina();
            conexao.executarAtualizacao(sql);
            this.setNumeroVisitas(this.numeroVisitas + 1);
        }
    }
    
    public void incrementarNumeroPagina() {
    }
    
    public Artigo getArtigo() {
        return this.artigo;
    }
    
    public String getConteudo() {
        return this.conteudo;
    }
    
    public int getNumeroVisitas() {
        return this.numeroVisitas;
    }
    
    public int getNumeroPagina() {
        return this.numeroPagina;
    }
    
    public void setArtigo(final Artigo artigo) {
        this.artigo = artigo;
    }
    
    public void setConteudo(final String conteudo) {
        this.conteudo = conteudo;
    }
    
    public void setNumeroVisitas(final int numeroVisitas) {
        this.numeroVisitas = numeroVisitas;
    }
    
    public void setNumeroPagina(final int numeroPagina) {
        this.numeroPagina = numeroPagina;
    }
}
