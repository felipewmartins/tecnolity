package br.com.mentores.site;

import java.util.*;
import br.com.mentores.util.*;
import java.sql.*;

public class Secao
{
    private int codigo;
    private String titulo;
    private String descricao;
    private String tituloLogico;
    private String caminhoFisico;
    private boolean homepage;
    private int numeroVisitas;
    private Vector subsecoes;
    private Secao superSecao;
    private int ordem;
    private Vector fluxos;
    
    public Secao() {
        this.numeroVisitas = 0;
    }
    
    public Secao(final String titulo, final String tituloLogico, final String descricao, final Secao superSecao, final String caminhoFisico, final boolean homepage, final int numeroVisitas, final int ordem) {
        this.numeroVisitas = 0;
        this.setTitulo(titulo);
        this.setTituloLogico(tituloLogico);
        this.setDescricao(descricao);
        this.setSuperSecao(superSecao);
        this.setCaminhoFisico(caminhoFisico);
        this.setHomepage(homepage);
        this.setNumeroVisitas(numeroVisitas);
        this.setOrdem(ordem);
    }
    
    public Secao(final int codigo, final String titulo, final String tituloLogico, final String descricao, final Secao superSecao, final String caminhoFisico, final boolean homepage, final int numeroVisitas, final int ordem) {
        this.numeroVisitas = 0;
        this.setCodigo(codigo);
        this.setTitulo(titulo);
        this.setTituloLogico(tituloLogico);
        this.setDescricao(descricao);
        this.setSuperSecao(superSecao);
        this.setCaminhoFisico(caminhoFisico);
        this.setHomepage(homepage);
        this.setNumeroVisitas(numeroVisitas);
        this.setOrdem(ordem);
    }
    
    public Secao(final int codigo) {
        this.numeroVisitas = 0;
        this.setCodigo(codigo);
    }
    
    public Secao(final int codigo, final String titulo, final Secao superSecao) {
        this.numeroVisitas = 0;
        this.setCodigo(codigo);
        this.setTitulo(titulo);
        this.setSuperSecao(superSecao);
    }
    
    public Secao(final int codigo, final String titulo, final String tituloLogico, final Secao superSecao) {
        this.numeroVisitas = 0;
        this.setCodigo(codigo);
        this.setTitulo(titulo);
        this.setTituloLogico(tituloLogico);
        this.setSuperSecao(superSecao);
    }
    
    public void incrementarVisitas(final ConexaoDB conexao) throws Exception {
        if (this.codigo > 0) {
            final String sql = "UPDATE SECAO SET NUMERO_VISITAS = NUMERO_VISITAS + 1 WHERE CODIGO_SECAO = " + this.getCodigo();
            conexao.executarAtualizacao(sql);
            this.setNumeroVisitas(this.numeroVisitas + 1);
        }
    }
    
    private void criarCaminhoFisico() {
    }
    
    public void addSecao(final ConexaoDB conexao) throws Exception {
        final String sql = "INSERT INTO SECAO (TITULO,TITULO_LOGICO,DESCRICAO,SUPER_SECAO,CAMINHO_FISICO,HOMEPAGE,NUMERO_VISITAS,ORDEM) VALUES ('" + this.getTitulo() + "','" + this.getTituloLogico() + "','" + this.getDescricao() + "','" + this.getSuperSecao() + "','" + this.getCaminhoFisico() + "','" + (this.isHomepage() ? 1 : 0) + "','" + this.getNumeroVisitas() + "','" + this.getOrdem() + "')";
        conexao.executarAtualizacao(sql);
    }
    
    public void removeSecao(final ConexaoDB conexao) throws Exception {
        final String sql = "DELETE FROM SECAO WHERE CODIGO_SECAO = '" + this.getCodigo() + "'";
        conexao.executarAtualizacao(sql);
    }
    
    public void setSecao(final ConexaoDB conexao) throws Exception {
        if (this.getCodigo() > 0) {
            final String query = "UPDATE SECAO SET TITULO ='" + this.getTitulo() + "',TITULO_LOGICO ='" + this.getTituloLogico() + "',DESCRICAO ='" + this.getDescricao() + "',SUPER_SECAO ='" + this.getSuperSecao() + "',CAMINHO_FISICO ='" + this.getCaminhoFisico() + "',HOMEPAGE ='" + (this.isHomepage() ? 1 : 0) + "',NUMERO_VISITAS ='" + this.getNumeroVisitas() + "',ORDEM ='" + this.getOrdem() + "' WHERE CODIGO_SECAO = " + this.getCodigo();
            conexao.executarAtualizacao(query);
            return;
        }
        throw new Exception("C\u00f3digo inv\u00e1lido.");
    }
    
    public Secao getSecao(final ConexaoDB conexao) throws Exception {
        if (this.codigo > 0) {
            final ResultSet rsSecao = conexao.executarConsulta("SELECT CODIGO_SECAO,TITULO,TITULO_LOGICO,DESCRICAO,SUPER_SECAO,CAMINHO_FISICO FROM SECAO WHERE CODIGO_SECAO = " + this.getCodigo());
            if (rsSecao.next()) {
                this.setCodigo(rsSecao.getInt("CODIGO_SECAO"));
                this.setTitulo(rsSecao.getString("TITULO"));
                this.setTituloLogico(rsSecao.getString("TITULO_LOGICO"));
                this.setDescricao(rsSecao.getString("DESCRICAO"));
                this.setSuperSecao(new Secao(rsSecao.getInt("SUPER_SECAO")));
                this.setCaminhoFisico(rsSecao.getString("CAMINHO_FISICO"));
            }
            rsSecao.close();
        }
        return this;
    }
    
    public Vector getSecoesPrincipais(final ConexaoDB conexao) throws Exception {
        final Vector secoes = new Vector();
        final ResultSet rsSecoes = conexao.executarConsulta("SELECT CODIGO_SECAO, TITULO, TITULO_LOGICO FROM SECAO WHERE SUPER_SECAO = 0 ORDER BY ORDEM");
        while (rsSecoes.next()) {
            secoes.addElement(new Secao(rsSecoes.getInt("CODIGO_SECAO"), rsSecoes.getString("TITULO"), rsSecoes.getString("TITULO_LOGICO"), new Secao(0)));
        }
        rsSecoes.close();
        return secoes;
    }
    
    public Vector getSecoesHomePage(final ConexaoDB conexao) throws Exception {
        final Vector secoes = new Vector();
        final ResultSet rsSecoes = conexao.executarConsulta("SELECT CODIGO_SECAO, TITULO, TITULO_LOGICO, SUPER_SECAO FROM SECAO WHERE HOMEPAGE > 0 ORDER BY HOMEPAGE");
        while (rsSecoes.next()) {
            secoes.addElement(new Secao(rsSecoes.getInt("CODIGO_SECAO"), rsSecoes.getString("TITULO"), rsSecoes.getString("TITULO_LOGICO"), new Secao(rsSecoes.getInt("SUPER_SECAO"))));
        }
        rsSecoes.close();
        return secoes;
    }
    
    public Vector getHierarquiaSecao(final ConexaoDB conexao) throws Exception {
        final Vector secoes = new Vector();
        int codigoSuperSecao = this.getSuperSecao().getCodigo();
        secoes.addElement(this);
        while (codigoSuperSecao != 0) {
            final ResultSet rsSuperSecao = conexao.executarConsulta("SELECT CODIGO_SECAO, TITULO, SUPER_SECAO FROM SECAO WHERE CODIGO_SECAO = " + codigoSuperSecao);
            if (rsSuperSecao.next()) {
                secoes.addElement(new Secao(rsSuperSecao.getInt("CODIGO_SECAO"), rsSuperSecao.getString("TITULO"), new Secao(codigoSuperSecao = rsSuperSecao.getInt("SUPER_SECAO"))));
            }
            rsSuperSecao.close();
        }
        return secoes;
    }
    
    public Secao getSecaoPrincipal(final ConexaoDB conexao) throws Exception {
        Secao secao = this;
        int codigoSuperSecao = this.getSuperSecao().getCodigo();
        while (codigoSuperSecao != 0) {
            final ResultSet rsSuperSecao = conexao.executarConsulta("SELECT SUPER_SECAO, CODIGO_SECAO, TITULO, TITULO_LOGICO FROM SECAO WHERE CODIGO_SECAO = " + codigoSuperSecao);
            if (rsSuperSecao.next()) {
                codigoSuperSecao = rsSuperSecao.getInt("SUPER_SECAO");
                if (codigoSuperSecao == 0) {
                    secao = new Secao(rsSuperSecao.getInt("CODIGO_SECAO"), rsSuperSecao.getString("TITULO"), rsSuperSecao.getString("TITULO_LOGICO"), new Secao(codigoSuperSecao));
                }
            }
            rsSuperSecao.close();
        }
        return secao;
    }
    
    public Vector getSubsecoes(final ConexaoDB conexao) throws Exception {
        this.subsecoes = new Vector();
        final ResultSet rsSubsecoes = conexao.executarConsulta("SELECT CODIGO_SECAO, TITULO, TITULO_LOGICO FROM SECAO WHERE SUPER_SECAO = " + this.getCodigo() + " ORDER BY ORDEM");
        while (rsSubsecoes.next()) {
            this.subsecoes.addElement(new Secao(rsSubsecoes.getInt("CODIGO_SECAO"), rsSubsecoes.getString("TITULO"), rsSubsecoes.getString("TITULO_LOGICO"), new Secao(this.getCodigo())));
        }
        rsSubsecoes.close();
        return this.subsecoes;
    }
    
    public Vector getFluxos(final ConexaoDB conexao) throws Exception {
        final Vector fluxos = new Vector();
        final ResultSet rsFluxos = conexao.executarConsulta("SELECT CODIGO_FLUXO, NOME_FLUXO FROM FLUXO WHERE SECAO = " + this.getCodigo());
        while (rsFluxos.next()) {
            fluxos.addElement(new Fluxo(rsFluxos.getInt("CODIGO_FLUXO"), new Secao(this.getCodigo()), rsFluxos.getString("NOME_FLUXO")));
        }
        rsFluxos.close();
        return fluxos;
    }
    
    public String getCaminhoFisico() {
        return this.caminhoFisico;
    }
    
    public int getCodigo() {
        return this.codigo;
    }
    
    public String getDescricao() {
        return this.descricao;
    }
    
    public boolean isHomepage() {
        return this.homepage;
    }
    
    public int getNumeroVisitas() {
        return this.numeroVisitas;
    }
    
    public Vector getSubsecoes() {
        return this.subsecoes;
    }
    
    public Secao getSuperSecao() {
        return this.superSecao;
    }
    
    public String getTitulo() {
        if (this.titulo != null) {
            return this.titulo;
        }
        return "";
    }
    
    public String getTituloLogico() {
        return this.tituloLogico;
    }
    
    public void setCaminhoFisico(final String caminhoFisico) {
        this.caminhoFisico = caminhoFisico;
    }
    
    public void setCodigo(final int codigo) {
        this.codigo = codigo;
    }
    
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }
    
    public void setHomepage(final boolean homepage) {
        this.homepage = homepage;
    }
    
    public void setNumeroVisitas(final int numeroVisitas) {
        this.numeroVisitas = numeroVisitas;
    }
    
    public void setSubsecoes(final Vector subsecoes) {
        this.subsecoes = subsecoes;
    }
    
    public void setSuperSecao(final Secao superSecao) {
        this.superSecao = superSecao;
    }
    
    public void setTitulo(final String titulo) {
        this.titulo = titulo;
    }
    
    public void setTituloLogico(final String tituloLogico) {
        this.tituloLogico = tituloLogico;
    }
    
    public int getOrdem() {
        return this.ordem;
    }
    
    public void setOrdem(final int ordem) {
        this.ordem = ordem;
    }
    
    public Vector getFluxos() {
        return this.fluxos;
    }
    
    public void setFluxos(final Vector fluxos) {
        this.fluxos = fluxos;
    }
}
