/**
   * Projeto: 001 - Tecnolity <br>
   * Autor do Código: Kenia Soares <br>
   * Nome do Arquivo: Multa.java <br>
   * Linguagem: Java <br>
   *  <br>
   * Requerimentos: Requer máquina virtual java versão 1.3 ou superior. <br>
   *  <br>
   * Objetivo: Classe que representa uma multa no sistema. <br>
   *  <br>
   * Objetivo definido por: Kenia Soares <br>
   * Início da Programação: 11/02/2001 <br>
   * Fim da Programação: <br>
   * Última Versão: 1.0 <br>
*/

package org.esmerilprogramming.tecnolity.logistica;

import org.esmerilprogramming.tecnolity.util.*;
import java.sql.*;

public class Multa 
{

	private int codigo;
	private Veiculo veiculo;
	private String motivo;
	private float valor;
	private int responsabilidade;
        private String data;
	
	public Multa(){}
	
	public Multa(int codigo)
	{
		definirCodigo(codigo);			
	}
	
	public Multa(int codigo, Conexao conexao)throws Exception
	{
            this.definirCodigo(codigo);

            ResultSet dadosMulta;        
            dadosMulta = conexao.executarConsulta("select * from multa where codigo = "+ this.codigo +" ");
        
            if(dadosMulta.next())
            {                
                try
                {
                    this.definirVeiculo(new Veiculo(dadosMulta.getString("placa")));
                    this.definirMotivo(dadosMulta.getString("motivo"));
                    this.definirValor(dadosMulta.getFloat("valor"));
                    this.definirResponsabilidade(dadosMulta.getInt("responsabilidade"));
                    this.definirData(dadosMulta.getString("datahora"));
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }			
	}
	
	public Multa(Veiculo veiculo, String motivo, float valor, int responsabilidade, String data)
	{		
        try
        {
            this.definirVeiculo(veiculo);
            this.definirMotivo(motivo);
            this.definirValor(valor);
            this.definirResponsabilidade(responsabilidade);
            this.definirData(data);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
	}
        
    public Multa(int codigo, Veiculo veiculo, String motivo, float valor, int responsabilidade, String data)
    {
		this.definirCodigo(codigo);                
        try
        {
            this.definirVeiculo(veiculo);
            this.definirMotivo(motivo);
            this.definirValor(valor);
            this.definirResponsabilidade(responsabilidade);
            this.definirData(data);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
	}
	
	public void definirCodigo(int codigo)
	{
        this.codigo = codigo;
    }
	
	public void definirVeiculo(Veiculo veiculo) throws Exception
	{
        if(veiculo != null)
            this.veiculo = veiculo;
        else
        {
            throw new Exception("A Placa não foi informada.");	
        }
	}
	
	public void definirMotivo(String motivo) throws Exception
	{
        if(!motivo.equals("") && motivo.length() <= 100)           
            this.motivo = motivo;
        else
        {
            Exception e = new Exception("O Motivo não foi informado corretamente.");
            throw e;	
        }
	}
	
	public void definirValor(float valor) throws Exception
	{
        String erro = "";
        if(Float.isNaN(valor) || valor <= 0.0f)
            erro = "O Valor não foi informado corretamente.";

        if(!erro.equals(""))
        {
            Exception e = new Exception(erro);
            throw e;
        }
        else	
            this.valor = valor;
	}
	
	public void definirResponsabilidade(int responsabilidade) throws Exception
	{
        if(responsabilidade > 0)        
            this.responsabilidade = responsabilidade;
        else
        {
            Exception e = new Exception("O Motorista não foi informado.");
            throw e;	
        }	            
	}
        
    public void definirData(String data) throws Exception
	{		
        String erro = "";
        if(data.equals(""))
            erro = "A Data não foi informada.";
        else if(data.length() == 10)
        {
            if(!Calendario.validarData(data,"/"))
                erro = "Data inválida.";
        }
        else
            erro = "Data inválida.";
        
        if(!erro.equals(""))
        {
            Exception e = new Exception(erro);
            throw e;
        }
        else
            this.data = data;
	}
	
	public int obterCodigo()
	{
		return this.codigo;	
	}
	
	public Veiculo obterVeiculo()
	{
		return this.veiculo;
	}
	
	public String obterMotivo()
	{
		return this.motivo;
	}
	
	public float obterValor()
	{
		return this.valor;
	}
	
	public int obterResponsabilidade()
	{
		return this.responsabilidade;
	}
        
        public String obterData()
        {
            return this.data;
        }
	
	public void cadastrarMulta() throws Exception
	{
		String query = "insert into multa (placa,motivo,valor,responsabilidade,datahora) values ";
		query = query + "('"+ this.veiculo.obterPlaca() +"', '"+ this.motivo +"', "+ this.valor +", "+ this.responsabilidade +", '"+ Calendario.inverterFormato(this.data,"/") +"')";
		Conexao conexao = new Conexao('T');
		if (conexao.abrirConexao())
		{
			conexao.executarAtualizacao(query);
			conexao.fecharConexao();
		}
                else
                {
                    Exception e = new Exception("Não foi possível realizar uma conexão com o banco de dados.");
                    throw e;
                }
	}
        
    public void alterarMulta() throws Exception
    {
        String query = "update multa set placa = '"+ this.veiculo.obterPlaca() +"',motivo = '"+ this.motivo +"',valor = "+ this.valor +",responsabilidade = "+ this.responsabilidade +",datahora = '"+ Calendario.inverterFormato(this.data,"/") +"' where codigo = "+ this.codigo +" ";
        Conexao conexao = new Conexao('T');
        if(conexao.abrirConexao())
        {            
            conexao.executarAtualizacao(query);
            conexao.fecharConexao();
        }
        else
        {
            Exception e = new Exception("Não foi possível realizar uma conexão com o banco de dados.");
            throw e;
        }
    }
    
    public void excluirMulta() throws Exception
    {
        String query = "delete from multa where codigo = "+ this.codigo +" ";
        Conexao conexao = new Conexao('T');
        if(conexao.abrirConexao())
        {            
            conexao.executarAtualizacao(query);
            conexao.fecharConexao();
        }
        else
        {
            Exception e = new Exception("Não foi possível realizar uma conexão com o banco de dados.");
            throw e;
        }
    }
}