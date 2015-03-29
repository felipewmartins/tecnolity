package org.esmerilprogramming.tecnolity.administracao

import org.esmerilprogramming.tecnolity.util.*
import java.sql.*

/**
   * Projeto: 001 - Tecnolity <br>
   * Autor do Código: Kenia Soares <br>
   * Nome do Arquivo: Cambio.java <br>
   * Linguagem: Java <br>
   *  <br>
   * Requerimentos: Requer máquina virtual java versão 1.3 ou superior. <br>
   *  <br>
   * Objetivo: Classe que representa um câmbio de moeda no sistema. <br>
   *  <br>
   * Objetivo definido por: Kenia Soares <br>
   * Início da Programação: 11/02/2001 <br>
   * Fim da Programação: <br>
   * Última Versão: 1.0 <br>
*/

public class Cambio 
{
    private float dolar
    private String data
    
    public Cambio() {}
    
    public Cambio(float dolar, String data)
    {
        this.dolar = dolar
        this.data = data
    }
    
    public float obterDolar()
    {
        return this.dolar
    }
    
    public String obterData()
    {
        return this.data
    }
    
    public void definirDolar(float dolar) throws Exception
    {
        if(dolar == 0.0f)
        {
            Exception e = new Exception("O valor do dólar não foi informado.")
            throw e
        }
        else
            this.dolar = dolar
     }
     
     public void definirData(String data)
     {
         this.data = data
     }
     
     public void carregarCambio(Conexao conexao) throws Exception
     {
        ResultSet dadosCambio        
        dadosCambio = conexao.executarConsulta("select distinct valor_dolar from cotacao_dolar where data = (select max(data) from cotacao_dolar)")
        if(dadosCambio.next())
        {
            definirDolar(dadosCambio.getFloat("valor_dolar"))
        }
        dadosCambio.close()
      }
     
     public void cadastrarCambio() throws Exception
     {
        Conexao conexao = new Conexao('T')        
        if(conexao.abrirConexao())
        {                
            String query = "insert into cotacao_dolar (valor_dolar) "
            query += "values ("+ this.dolar +")"
            conexao.executarAtualizacao(query)                
            conexao.fecharConexao()
        }
      }
}