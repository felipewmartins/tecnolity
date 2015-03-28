package br.com.mentores.site;

import br.com.mentores.util.*;
import java.sql.*;

public class Fluxo
{
    private int codigo;
    private String nome;
    private Secao secao;
    
    public Fluxo(final int codigo) {
        this.setCodigo(codigo);
    }
    
    public Fluxo(final Secao secao, final String nome) {
        this.setSecao(secao);
        this.setNome(nome);
    }
    
    public Fluxo(final int codigo, final Secao secao, final String nome) {
        this.setCodigo(codigo);
        this.setSecao(secao);
        this.setNome(nome);
    }
    
    public void addFluxo(final ConexaoDB conexao) throws Exception {
        final String sql = "INSERT INTO FLUXO (SECAO,NOME_FLUXO) VALUES ('" + this.getSecao().getCodigo() + "','" + this.getNome() + "')";
        conexao.executarAtualizacao(sql);
    }
    
    public void removeFluxo(final ConexaoDB conexao) throws Exception {
        final String sql = "DELETE FROM FLUXO WHERE CODIGO_FLUXO = '" + this.getCodigo() + "'";
        conexao.executarAtualizacao(sql);
    }
    
    public void setFluxo(final ConexaoDB conexao) throws Exception {
        if (this.getCodigo() > 0) {
            final String query = "UPDATE FLUXO SET SECAO ='" + this.getSecao().getCodigo() + "',NOME_FLUXO ='" + this.getNome() + "' WHERE CODIGO_FLUXO = " + this.getCodigo();
            conexao.executarAtualizacao(query);
            return;
        }
        throw new Exception("C\u00f3digo inv\u00e1lido.");
    }
    
    public Fluxo getFluxo(final ConexaoDB conexao) throws Exception {
        if (this.codigo > 0) {
            final ResultSet rsFluxo = conexao.executarConsulta("SELECT CODIGO_FLUXO,SECAO,NOME_FLUXO FROM FLUXO WHERE CODIGO_FLUXO = " + this.getCodigo());
            if (rsFluxo.next()) {
                this.setCodigo(rsFluxo.getInt("CODIGO_FLUXO"));
                this.setSecao(new Secao(rsFluxo.getInt("SECAO")));
                this.setNome(rsFluxo.getString("NOME_FLUXO"));
            }
            rsFluxo.close();
        }
        return this;
    }
    
    public int getCodigo() {
        return this.codigo;
    }
    
    public String getNome() {
        return this.nome;
    }
    
    public Secao getSecao() {
        return this.secao;
    }
    
    public void setCodigo(final int codigo) {
        this.codigo = codigo;
    }
    
    public void setNome(final String nome) {
        this.nome = nome;
    }
    
    public void setSecao(final Secao secao) {
        this.secao = secao;
    }
}
