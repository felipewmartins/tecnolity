package br.com.mentores.site;

import java.io.*;
import br.com.mentores.util.*;
import java.sql.*;

public class Frase implements Serializable
{
    private String frase;
    private int mes;
    private int dia;
    private String autor;
    private String data;
    
    public Frase() {
        final Conexao conexao = new Conexao(Controller.DRIVER, Controller.FONTE_DADOS, Controller.USUARIO, Controller.SENHA);
        try {
            conexao.abrirConexao();
            final Frase frase = new Frase(4, 7, "frase", "hamilton");
            frase.getFrase(conexao);
            this.frase = frase.getFrase();
            conexao.fecharConexao();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Frase(final int mes, final int dia, final String frase, final String autor) {
        this.setMes(mes);
        this.setDia(dia);
        this.setFrase(frase);
        this.setAutor(autor);
        Calendario.validarData(this.data = String.valueOf(this.getDia()) + "/" + this.getMes() + "/" + "2003", "/");
    }
    
    public Frase(final int mes, final int dia) {
        this.setMes(mes);
        this.setDia(dia);
        Calendario.validarData(this.data = String.valueOf(this.getDia()) + "/" + this.getMes() + "/" + "2003", "/");
    }
    
    public void addFrase(final ConexaoDB conexao) throws Exception {
        final String sql = "INSERT INTO FRASE (MES,DIA,FRASE,AUTOR) VALUES ('" + this.getMes() + "','" + this.getDia() + "','" + this.getFrase() + "','" + this.getAutor() + "')";
        conexao.executarAtualizacao(sql);
    }
    
    public void removeFrase(final ConexaoDB conexao) throws Exception {
        final String sql = "DELETE FROM FRASE WHERE MES = " + this.getMes() + " AND DIA = " + this.getDia() + "";
        conexao.executarAtualizacao(sql);
    }
    
    public void setFrase(final ConexaoDB conexao) throws Exception {
        if (this.getDia() > 0 && this.getMes() > 0) {
            final String query = "UPDATE FRASE SET FRASE = '" + this.getFrase() + "',AUTOR ='" + this.getAutor() + "' WHERE MES = " + this.getMes() + " AND DIA = " + this.getDia();
            conexao.executarAtualizacao(query);
            return;
        }
        throw new Exception("C\u00f3digo inv\u00e1lido.");
    }
    
    public Frase getFrase(final ConexaoDB conexao) throws Exception {
        final String sql = "SELECT MES,DIA,FRASE,AUTOR FROM FRASE WHERE MES = " + this.getMes() + " AND DIA = " + this.getDia();
        final ResultSet rsFrase = conexao.executarConsulta(sql);
        if (rsFrase.next()) {
            this.setMes(rsFrase.getInt("MES"));
            this.setDia(rsFrase.getInt("DIA"));
            this.setFrase(rsFrase.getString("FRASE"));
            this.setAutor(rsFrase.getString("AUTOR"));
        }
        rsFrase.close();
        return this;
    }
    
    public String getAutor() {
        return this.autor;
    }
    
    public int getDia() {
        return this.dia;
    }
    
    public String getFrase() {
        return this.frase;
    }
    
    public int getMes() {
        return this.mes;
    }
    
    public void setAutor(final String autor) {
        this.autor = autor;
    }
    
    public void setDia(final int dia) {
        this.dia = dia;
    }
    
    public void setFrase(final String frase) {
        this.frase = frase;
    }
    
    public void setMes(final int mes) {
        this.mes = mes;
    }
}
