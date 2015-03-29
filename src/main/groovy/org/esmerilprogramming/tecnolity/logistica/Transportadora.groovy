/**
   * Projeto: 001 - Tecnolity <br>
   * Autor do Código: Kenia Soares <br>
   * Nome do Arquivo: Transportadora.java <br>
   * Linguagem: Java <br>
   *  <br>
   * Requerimentos: Requer máquina virtual java versão 1.3 ou superior. <br>
   *  <br>
   * Objetivo: Classe que representa uma transportadora no sistema. <br>
   *  <br>
   * Objetivo definido por: Kenia Soares <br>
   * Início da Programação: 10/02/2001 <br>
   * Fim da Programação: <br>
   * Última Versão: 1.0 <br>
*/

package org.esmerilprogramming.tecnolity.logistica

import org.esmerilprogramming.tecnolity.util.*
import java.util.Vector
import java.sql.*

 class Transportadora 
{
    private int codigo
    private String transportadora
    
     Transportadora(){}
    
     Transportadora(int codigo)
    {
        this.definirCodigo(codigo)
    }
    
     Transportadora(int codigo, String transportadora)
    {
        this.definirCodigo(codigo)
        try
        {
            this.definirTransportadora(transportadora)
        }
        catch(Exception e)
        {
            e.printStackTrace()
        }
    }
    
     Transportadora(String transportadora)
    {
        try
        {
            this.definirTransportadora(transportadora)
        }
        catch(Exception e)
        {
            e.printStackTrace()
        }
    }
    
     Transportadora(int codigo, Conexao conexao)throws Exception
    {
        this.definirCodigo(codigo)

        ResultSet dadosTransportadora        
        dadosTransportadora = conexao.executarConsulta("select * from transportadora where codigo = "+ this.codigo +" ")

        if(dadosTransportadora.next())
        {                
            try
            {
                this.definirTransportadora(dadosTransportadora.getString("transportadora"))                
            }
            catch(Exception e)
            {
                e.printStackTrace()
            }
        }			
    }
    
     void definirCodigo(int codigo)
    {
        this.codigo = codigo
    }
    
     void definirTransportadora(String transportadora) throws Exception
    {
        if(!transportadora.equals("") && transportadora.length() <= 60)           
            this.transportadora = transportadora
        else
        {
            Exception e = new Exception("A Transportadora não foi informada corretamente.")
            throw e	
        }
    }
    
     int obterCodigo()
    {
        return this.codigo
    }
     String obterNome()
    {
        return this.transportadora
    }        
    
     static Vector carregarTransportadoras(Conexao conexao) throws Exception
    {
    	ResultSet dadosTransportadora
        Vector transportadoras = null
        dadosTransportadora = conexao.executarConsulta("select * from transportadora order by transportadora asc")
        transportadoras = new Vector()
        transportadoras.addElement("Selecione...")
        while(dadosTransportadora.next())
        {
            transportadoras.addElement(new Transportadora(dadosTransportadora.getInt("codigo"),dadosTransportadora.getString("transportadora")))
        }
        dadosTransportadora.close()
        return transportadoras
    }  
    
     void cadastrarTransportadora() throws Exception
    {
        String query = "insert into transportadora (transportadora) values "
        query = query + "('"+ this.transportadora + "')"
        Conexao conexao = new Conexao('T')
        if (conexao.abrirConexao())
        {
                conexao.executarAtualizacao(query)
                conexao.fecharConexao()
        }
        else
        {
            Exception e = new Exception("Não foi possível realizar uma conexão com o banco de dados.")
            throw e
        }
    }
    
     void alterarTransportadora() throws Exception
    {
        String query = "update transportadora set transportadora = '"+ this.transportadora +"' where codigo = "+ this.codigo +" "
        Conexao conexao = new Conexao('T')
        if(conexao.abrirConexao())
        {            
            conexao.executarAtualizacao(query)
            conexao.fecharConexao()
        }
        else
        {
            Exception e = new Exception("Não foi possível realizar uma conexão com o banco de dados.")
            throw e
        }
    }
    
     void excluirTransportadora() throws Exception
    {
        String query = "delete from transportadora where codigo = "+ this.codigo +" "
        Conexao conexao = new Conexao('T')
        if(conexao.abrirConexao())
        {            
            conexao.executarAtualizacao(query)
            conexao.fecharConexao()
        }
        else
        {
            Exception e = new Exception("Não foi possível realizar uma conexão com o banco de dados.")
            throw e
        }
    }
    
     String toString()
    {
    	return this.transportadora
    }
}