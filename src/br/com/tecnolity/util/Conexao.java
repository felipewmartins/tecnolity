package br.com.tecnolity.util;

import java.sql.*;

/**
   * Projeto: 001 - Tecnolity <br>
   * Autor do C�digo: Hildeberto Mendon�a Filho <br>
   * Nome do Arquivo: Conexao.java <br>
   * Linguagem: Java <br>
   *  <br>
   * Requerimentos: Requer m�quina virtual java vers�o 1.3 ou superior. <br>
   *  <br>
   * Objetivo: Conecta-se ao banco de dados e permite realizar <br>
   * transa��es e consultar dados. <br>
   *  <br>
   * Objetivo definido por: Hildeberto Mendon�a <br>
   * In�cio da Programa��o: 25/12/2001 <br>
   * Fim da Programa��o: <br>
   * �ltima Vers�o: 1.0 <br>
*/

public class Conexao
{
    //Vari�veis de conex�o ao banco de dados
    private Connection conexao;
    private Statement  expressao;
    private ResultSet  resultado;
    private String driver, fonteDados, usuario, senha;

    public Conexao(String driver,String fonteDados,String usuario, String senha)
    {
        this.driver = driver;
        this.fonteDados = fonteDados;
        this.usuario = usuario;
        this.senha = senha;
    }
	
    public Conexao(char tipo)
    {
        this.driver = Configuracao.getBancoDadosDriver();
        this.fonteDados = Configuracao.getBancoDadosURL();
		this.usuario = Configuracao.getBancoDadosUsuario();
        this.senha = Configuracao.getBancoDadosSenha();
    }

    public boolean abrirConexao()
    {
        try
        {
            Class.forName (driver);
            conexao = DriverManager.getConnection(fonteDados,usuario,senha);
            return true;
        }
        catch (Exception e)
        {
            handleException(e);
            return false;
        }
    }
        
    public boolean conexaoAberta()
    {
        try
        {
            return !conexao.isClosed();
        }
        catch(Exception e)
        {
            handleException(e);
            return false;
        }
    }

    public void fecharConexao()
    {
        try
        {
            conexao.close();
        }
        catch (Exception e)
        {
            handleException(e);
        }
    }

    public ResultSet executarConsulta(String query)
    {
        try
        {
            expressao = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            resultado = expressao.executeQuery(query);
        }
        catch (Exception e)
        {
            abrirConexao();
            handleException(e);
        }
        return resultado;
    }

    public int executarAtualizacao(String query)
    {
        int quantAtualizacoes = 0;
        try
        {
            expressao = conexao.createStatement();
            quantAtualizacoes = expressao.executeUpdate(query);
        }
        catch (Exception e)
        {
            handleException(e);
        }
        return quantAtualizacoes;
    }

    public void handleException(Exception e)
    {
        e.printStackTrace( );
        if (e instanceof SQLException)
        {
            while ((e = ((SQLException) e).getNextException( )) != null)
            {
                System.out.println("Erro durante a execu��o de um comando SQL\n" + e);
            }
        }
    }

    public void commit() throws SQLException
    {
    }

    public void rollback() throws SQLException
    {
    }
}