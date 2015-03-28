package br.com.mentores.site;

import java.util.*;
import br.com.mentores.util.*;
import java.sql.*;

public class Atividade
{
    private int codigo;
    private Fluxo fluxo;
    private String nome;
    private String tela;
    private String ajuda;
    private Vector relacoesAtividade;
    
    public Atividade(final int codigo) {
        this.setCodigo(codigo);
    }
    
    public Atividade(final Fluxo fluxo, final String nome, final String tela, final String ajuda) {
        this.setFluxo(fluxo);
        this.setNome(nome);
        this.setTela(tela);
        this.setAjuda(ajuda);
    }
    
    public Atividade(final int codigo, final Fluxo fluxo, final String nome, final String tela, final String ajuda) {
        this.setCodigo(codigo);
        this.setFluxo(fluxo);
        this.setNome(nome);
        this.setTela(tela);
        this.setAjuda(ajuda);
    }
    
    public Atividade(final int codigo, final String nome, final String tela) {
        this.setCodigo(codigo);
        this.setNome(nome);
        this.setTela(tela);
    }
    
    public void addAtividade(final ConexaoDB conexao) throws Exception {
        final String sql = "INSERT INTO ATIVIDADE (FLUXO,NOME_ATIVIDADE,INTERFACE,AJUDA) VALUES ('" + this.getFluxo().getCodigo() + "','" + this.getNome() + "','" + this.getTela() + "','" + this.getAjuda() + "')";
        conexao.executarAtualizacao(sql);
    }
    
    public void removeAtividade(final ConexaoDB conexao) throws Exception {
        final String sql = "DELETE FROM ATIVIDADE WHERE CODIGO_ATIVIDADE = '" + this.getCodigo() + "'";
        conexao.executarAtualizacao(sql);
    }
    
    public void setAtividade(final ConexaoDB conexao) throws Exception {
        if (this.getCodigo() > 0) {
            final String query = "UPDATE ATIVIDADE SET FLUXO ='" + this.getFluxo().getCodigo() + "',NOME_ATIVIDADE ='" + this.getNome() + "',INTERFACE ='" + this.getTela() + "',AJUDA ='" + this.getAjuda() + "' WHERE CODIGO_ATIVIDADE = " + this.getCodigo();
            conexao.executarAtualizacao(query);
            return;
        }
        throw new Exception("C\u00f3digo inv\u00e1lido.");
    }
    
    public Atividade getAtividade(final ConexaoDB conexao) throws Exception {
        if (this.codigo > 0) {
            final ResultSet rsAtividade = conexao.executarConsulta("SELECT CODIGO_ATIVIDADE,FLUXO,NOME_ATIVIDADE,INTERFACE,AJUDA FROM ATIVIDADE WHERE CODIGO_ATIVIDADE = " + this.getCodigo());
            if (rsAtividade.next()) {
                this.setCodigo(rsAtividade.getInt("CODIGO_ATIVIDADE"));
                this.setFluxo(new Fluxo(rsAtividade.getInt("FLUXO")));
                this.setNome(rsAtividade.getString("NOME_ATIVIDADE"));
                this.setTela(rsAtividade.getString("INTERFACE"));
                this.setAjuda(rsAtividade.getString("AJUDA"));
            }
            rsAtividade.close();
        }
        return this;
    }
    
    public Vector getRelacoesAtividade(final ConexaoDB conexao) throws Exception {
        this.relacoesAtividade = new Vector();
        final ResultSet rsRelacoesAtividade = conexao.executarConsulta("SELECT CODIGO_ATIVIDADE, NOME_ATIVIDADE, INTERFACE FROM ATIVIDADE WHERE CODIGO_ATIVIDADE = " + this.getCodigo());
        while (rsRelacoesAtividade.next()) {
            this.relacoesAtividade.addElement(new Atividade(rsRelacoesAtividade.getInt("CODIGO_ATIVIDADE"), rsRelacoesAtividade.getString("NOME_ATIVIDADE"), rsRelacoesAtividade.getString("INTERFACE")));
        }
        rsRelacoesAtividade.close();
        return this.relacoesAtividade;
    }
    
    public String getAjuda() {
        return this.ajuda;
    }
    
    public int getCodigo() {
        return this.codigo;
    }
    
    public Fluxo getFluxo() {
        return this.fluxo;
    }
    
    public String getNome() {
        return this.nome;
    }
    
    public String getTela() {
        return this.tela;
    }
    
    public void setAjuda(final String ajuda) {
        this.ajuda = ajuda;
    }
    
    public void setCodigo(final int codigo) {
        this.codigo = codigo;
    }
    
    public void setFluxo(final Fluxo fluxo) {
        this.fluxo = fluxo;
    }
    
    public void setNome(final String nome) {
        this.nome = nome;
    }
    
    public void setTela(final String tela) {
        this.tela = tela;
    }
    
    public void addRelacaoAtividade(final RelacaoAtividade relacaoAtividade) {
        this.relacoesAtividade.addElement(relacaoAtividade);
    }
    
    public Vector getRelacoesAtividade() {
        return this.relacoesAtividade;
    }
    
    public void setRelacoesAtividade(final Vector relacoesAtividade) {
        this.relacoesAtividade = relacoesAtividade;
    }
}
