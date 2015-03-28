package br.com.mentores.site;

import br.com.mentores.gestao.bibliografia.*;
import br.com.mentores.util.*;
import java.sql.*;
import java.util.*;

public class ArtigoSecao
{
    private Calendario dataPublicacao;
    private Calendario dataExpiracao;
    private Secao secao;
    private Artigo artigo;
    
    public ArtigoSecao(final Artigo artigo, final Secao secao, final Calendario dataPublicacao, final Calendario dataExpiracao) {
        this.setArtigo(artigo);
        this.setSecao(secao);
        this.setDataPublicacao(dataPublicacao);
        this.setDataExpiracao(dataExpiracao);
    }
    
    public ArtigoSecao(final Secao secao) {
        this.setSecao(secao);
    }
    
    public ArtigoSecao(final Artigo artigo) {
        this.setArtigo(artigo);
    }
    
    public ArtigoSecao(final Secao secao, final Artigo artigo) {
        this.setSecao(secao);
        this.setArtigo(artigo);
    }
    
    public ArtigoSecao(final Secao secao, final Artigo artigo, final Calendario dataPublicacao, final Calendario dataExpiracao) {
        this.setSecao(secao);
        this.setArtigo(artigo);
        this.setDataPublicacao(dataPublicacao);
        this.setDataExpiracao(dataExpiracao);
    }
    
    public void addArtigoSecao(final ConexaoDB conexao) throws Exception {
        if (this.getDataExpiracao().compararCom(this.getDataPublicacao()) >= 0) {
            final String sql = "INSERT INTO ARTIGO_SECAO (ARTIGO,SECAO,DATA_PUBLICACAO,DATA_EXPIRACAO) VALUES (" + this.getArtigo().getCodigoArtigo() + "," + this.getSecao().getCodigo() + ",'" + this.getDataPublicacao() + "','" + this.getDataExpiracao() + "')";
            conexao.executarAtualizacao(sql);
            return;
        }
        throw new Exception("A Data de Publica\u00e7\u00e3o est\u00e1 incorreta.");
    }
    
    public void setArtigoSecao(final ConexaoDB conexao) throws Exception {
        if (this.getArtigo().getCodigoArtigo() <= 0) {
            throw new Exception("Artigo Inv\u00e1lido.");
        }
        if (this.getDataExpiracao().compararCom(this.getDataPublicacao()) >= 0) {
            final String query = "UPDATE ARTIGO_SECAO SET DATA_PUBLICACAO ='" + this.getDataPublicacao() + "',DATA_EXPIRACAO ='" + this.getDataExpiracao() + "' WHERE ARTIGO = " + this.getArtigo().getCodigoArtigo() + " AND SECAO =" + this.getSecao().getCodigo();
            conexao.executarAtualizacao(query);
            return;
        }
        throw new Exception("A Data de Publica\u00e7\u00e3o est\u00e1 incorreta.");
    }
    
    public void removeArtigoSecao(final ConexaoDB conexao) throws Exception {
        final String sql = "DELETE FROM ARTIGO_SECAO WHERE ARTIGO = " + this.getArtigo().getCodigoArtigo() + " AND SECAO = " + this.getSecao().getCodigo() + "";
        conexao.executarAtualizacao(sql);
    }
    
    public Vector getArtigosSecao(final boolean mostrarResumo, final ConexaoDB conexao) throws Exception {
        final Vector artigos = new Vector();
        final Calendario hoje = new Calendario();
        String sql = "";
        if (mostrarResumo) {
            sql = "SELECT A.CODIGO_ARTIGO, A.TITULO, A.COMENTARIO, A.RESUMO FROM ARTIGO A, ARTIGO_SECAO ARS, SECAO S WHERE A.CODIGO_ARTIGO = ARS.ARTIGO AND ARS.SECAO = S.CODIGO_SECAO AND S.CODIGO_SECAO = " + this.secao.getCodigo() + " AND ARS.DATA_PUBLICACAO <= '" + hoje.get("yyyy-MM-dd HH:mm:ss") + "' AND ARS.DATA_EXPIRACAO > '" + hoje.get("yyyy-MM-dd HH:mm:ss") + "' ORDER BY ARS.DATA_PUBLICACAO DESC";
        }
        else {
            sql = "SELECT A.CODIGO_ARTIGO, A.TITULO, A.COMENTARIO FROM ARTIGO A, ARTIGO_SECAO ARS, SECAO S WHERE A.CODIGO_ARTIGO = ARS.ARTIGO AND ARS.SECAO = S.CODIGO_SECAO AND S.CODIGO_SECAO = " + this.secao.getCodigo() + " AND ARS.DATA_PUBLICACAO <= '" + hoje.get("yyyy-MM-dd HH:mm:ss") + "' AND ARS.DATA_EXPIRACAO > '" + hoje.get("yyyy-MM-dd HH:mm:ss") + "' ORDER BY ARS.DATA_PUBLICACAO DESC";
        }
        System.out.println(sql);
        final ResultSet rsArtigos = conexao.executarConsulta(sql);
        while (rsArtigos.next()) {
            ArtigoSecao art;
            if (mostrarResumo) {
                art = new ArtigoSecao(this.secao, new Artigo(rsArtigos.getInt("CODIGO_ARTIGO"), rsArtigos.getString("TITULO"), rsArtigos.getString("COMENTARIO"), rsArtigos.getString("RESUMO")));
            }
            else {
                art = new ArtigoSecao(this.secao, new Artigo(rsArtigos.getInt("CODIGO_ARTIGO"), rsArtigos.getString("TITULO"), rsArtigos.getString("COMENTARIO")));
            }
            artigos.addElement(art);
        }
        rsArtigos.close();
        return artigos;
    }
    
    public Vector getArtigosSecao(final boolean mostrarResumo, final int numeroArtigos, final ConexaoDB conexao) throws Exception {
        final Vector artigos = new Vector();
        final Calendario hoje = new Calendario();
        String sql = "";
        if (mostrarResumo) {
            sql = "SELECT A.CODIGO_ARTIGO, A.TITULO, A.COMENTARIO, A.RESUMO FROM ARTIGO A, ARTIGO_SECAO ARS, SECAO S WHERE A.CODIGO_ARTIGO = ARS.ARTIGO AND ARS.SECAO = S.CODIGO_SECAO AND S.CODIGO_SECAO = " + this.secao.getCodigo() + " AND ARS.DATA_PUBLICACAO <= '" + hoje.get("yyyy-MM-dd HH:mm:ss") + "' AND ARS.DATA_EXPIRACAO > '" + hoje.get("yyyy-MM-dd HH:mm:ss") + "' ORDER BY DATA_PUBLICACAO DESC LIMIT 0, " + numeroArtigos;
        }
        else {
            sql = "SELECT A.CODIGO_ARTIGO, A.TITULO, A.COMENTARIO FROM ARTIGO A, ARTIGO_SECAO ARS, SECAO S WHERE A.CODIGO_ARTIGO = ARS.ARTIGO AND ARS.SECAO = S.CODIGO_SECAO AND S.CODIGO_SECAO = " + this.secao.getCodigo() + " AND ARS.DATA_PUBLICACAO <= '" + hoje.get("yyyy-MM-dd HH:mm:ss") + "' AND ARS.DATA_EXPIRACAO > '" + hoje.get("yyyy-MM-dd HH:mm:ss") + "' ORDER BY DATA_PUBLICACAO DESC LIMIT 0, " + numeroArtigos;
        }
        System.out.println(sql);
        final ResultSet rsArtigos = conexao.executarConsulta(sql);
        while (rsArtigos.next()) {
            ArtigoSecao art;
            if (mostrarResumo) {
                art = new ArtigoSecao(this.secao, new Artigo(rsArtigos.getInt("CODIGO_ARTIGO"), rsArtigos.getString("TITULO"), rsArtigos.getString("COMENTARIO"), rsArtigos.getString("RESUMO")));
            }
            else {
                art = new ArtigoSecao(this.secao, new Artigo(rsArtigos.getInt("CODIGO_ARTIGO"), rsArtigos.getString("TITULO"), rsArtigos.getString("COMENTARIO")));
            }
            artigos.addElement(art);
        }
        rsArtigos.close();
        return artigos;
    }
    
    public Vector getArtigosSubsecoes(final Secao secao, final ConexaoDB conexao) throws Exception {
        final Vector artigos = new Vector();
        final Calendario hoje = new Calendario();
        String sql = "SELECT CODIGO_SECAO FROM SECAO WHERE SUPER_SECAO = " + secao.getCodigo();
        final ResultSet rsSubSecoes = conexao.executarConsulta(sql);
        while (rsSubSecoes.next()) {
            final Secao sec = new Secao(rsSubSecoes.getInt(1));
            sql = "SELECT A.CODIGO_ARTIGO, A.TITULO, A.COMENTARIO, A.RESUMO FROM ARTIGO A, ARTIGO_SECAO ARS, SECAO S WHERE A.CODIGO_ARTIGO = ARS.ARTIGO AND ARS.SECAO = S.CODIGO_SECAO AND S.CODIGO_SECAO = " + sec.getCodigo() + " AND ARS.DATA_PUBLICACAO <= '" + hoje.get("yyyy-MM-dd HH:mm:ss") + "' AND ARS.DATA_EXPIRACAO > '" + hoje.get("yyyy-MM-dd HH:mm:ss") + "'";
            final ResultSet rsArtigos = conexao.executarConsulta(sql);
            while (rsArtigos.next()) {
                artigos.addElement(new ArtigoSecao(sec, new Artigo(rsArtigos.getInt("CODIGO_ARTIGO"), rsArtigos.getString("TITULO"), rsArtigos.getString("COMENTARIO"), rsArtigos.getString("RESUMO"))));
            }
            rsArtigos.close();
            artigos.addAll(this.getArtigosSubsecoes(sec, conexao));
        }
        rsSubSecoes.close();
        return artigos;
    }
    
    public static Vector getUltimosArtigos(final int quantidade, final ConexaoDB conexao) throws Exception {
        final Vector artigos = new Vector();
        final Calendario hoje = new Calendario();
        final String sql = "SELECT S.CODIGO_SECAO, A.CODIGO_ARTIGO, A.TITULO, A.COMENTARIO FROM ARTIGO A, ARTIGO_SECAO ARS, SECAO S WHERE A.CODIGO_ARTIGO = ARS.ARTIGO AND S.CODIGO_SECAO = ARS.SECAO AND ARS.DATA_PUBLICACAO <= '" + hoje.get("yyyy-MM-dd HH:mm:ss") + "' AND ARS.DATA_EXPIRACAO > '" + hoje.get("yyyy-MM-dd HH:mm:ss") + "' ORDER BY ARS.DATA_PUBLICACAO DESC LIMIT 0, " + quantidade;
        final ResultSet rsArtigos = conexao.executarConsulta(sql);
        while (rsArtigos.next()) {
            artigos.addElement(new ArtigoSecao(new Secao(rsArtigos.getInt("CODIGO_SECAO")), new Artigo(rsArtigos.getInt("CODIGO_ARTIGO"), rsArtigos.getString("TITULO"), rsArtigos.getString("COMENTARIO"))));
        }
        rsArtigos.close();
        return artigos;
    }
    
    public Secao getSecaoArtigo(final ConexaoDB conexao) throws Exception {
        Secao secao = null;
        if (this.artigo != null) {
            final String sql = "SELECT SECAO FROM ARTIGO_SECAO WHERE ARTIGO = " + this.artigo.getCodigoArtigo();
            final ResultSet rsSecao = conexao.executarConsulta(sql);
            if (rsSecao.next()) {
                secao = new Secao(rsSecao.getInt("SECAO"));
            }
            rsSecao.close();
        }
        return secao;
    }
    
    public static Calendario getDataPublicacao(final Artigo artigo, final Secao secao, final ConexaoDB conexao) throws Exception {
        Calendario calendario = null;
        final String sql = "SELECT DATA_PUBLICACAO FROM ARTIGO_SECAO WHERE ARTIGO = " + artigo.getCodigoArtigo() + " AND SECAO = " + secao.getCodigo();
        final ResultSet rsDataPublicacao = conexao.executarConsulta(sql);
        if (rsDataPublicacao.next()) {
            calendario = new Calendario(rsDataPublicacao.getTimestamp(1));
        }
        rsDataPublicacao.close();
        return calendario;
    }
    
    public Artigo getArtigo() {
        return this.artigo;
    }
    
    public Calendario getDataExpiracao() {
        return this.dataExpiracao;
    }
    
    public Calendario getDataPublicacao() {
        return this.dataPublicacao;
    }
    
    public Secao getSecao() {
        return this.secao;
    }
    
    public void setArtigo(final Artigo artigo) {
        this.artigo = artigo;
    }
    
    public void setDataExpiracao(final Calendario dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }
    
    public void setDataPublicacao(final Calendario dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }
    
    public void setSecao(final Secao secao) {
        this.secao = secao;
    }
}
