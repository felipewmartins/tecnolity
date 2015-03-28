package br.com.mentores.util;

import java.sql.*;

public class Conexao implements ConexaoDB
{
    private Connection conexao;
    private Statement expressao;
    private ResultSet resultado;
    private String driver;
    private String fonteDados;
    private String usuario;
    private String senha;
    
    public Conexao() {
    }
    
    public Conexao(final String driver, final String fonteDados) {
        this.driver = driver;
        this.fonteDados = fonteDados;
    }
    
    public Conexao(final String driver, final String fonteDados, final String usuario, final String senha) {
        this.driver = driver;
        this.fonteDados = fonteDados;
        this.usuario = usuario;
        this.senha = senha;
    }
    
    public void abrirConexao() throws Exception {
        try {
            Class.forName(this.driver);
            this.conexao = DriverManager.getConnection(this.fonteDados, this.usuario, this.senha);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("N\u00e3o foi poss\u00edvel abrir uma conex\u00e3o com o banco de dados. (" + e.getMessage() + ")");
        }
    }
    
    public void commit() throws SQLException {
    }
    
    public void rollback() throws SQLException {
    }
    
    public boolean conexaoAberta() throws Exception {
        try {
            return !this.conexao.isClosed();
        }
        catch (Exception e) {
            throw new Exception("N\u00e3o foi poss\u00edvel verificar o estado da conex\u00e3o.");
        }
    }
    
    public void fecharConexao() throws Exception {
        try {
            this.conexao.close();
        }
        catch (Exception e) {
            throw new Exception("N\u00e3o foi poss\u00edvel fechar a conex\u00e3o com o banco de dados.");
        }
    }
    
    public ResultSet executarConsulta(final String query) throws SQLException {
        this.expressao = this.conexao.createStatement(1004, 1007);
        return this.resultado = this.expressao.executeQuery(query);
    }
    
    public int executarAtualizacao(final String query) throws SQLException {
        int quantAtualizacoes = 0;
        this.expressao = this.conexao.createStatement();
        quantAtualizacoes = this.expressao.executeUpdate(query);
        return quantAtualizacoes;
    }
    
    public String getDriver() {
        return this.driver;
    }
    
    public String getFonteDados() {
        return this.fonteDados;
    }
    
    public String getSenha() {
        return this.senha;
    }
    
    public String getUsuario() {
        return this.usuario;
    }
    
    public void setDriver(final String driver) {
        this.driver = driver;
    }
    
    public void setFonteDados(final String fonteDados) {
        this.fonteDados = fonteDados;
    }
    
    public void setSenha(final String senha) {
        this.senha = senha;
    }
    
    public void setUsuario(final String usuario) {
        this.usuario = usuario;
    }
}
