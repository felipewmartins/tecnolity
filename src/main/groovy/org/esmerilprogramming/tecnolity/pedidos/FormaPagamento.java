/**
   * Projeto: 001 - Tecnolity <br>
   * Autor do Código: Kenia Soares <br>
   * Nome do Arquivo: FormaPagamento.java <br>
   * Linguagem: Java <br>
   *  <br>
   * Requerimentos: Requer máquina virtual java versão 1.3 ou superior. <br>
   *  <br>
   * Objetivo: Classe que representa uma forma de pagamento no sistema. <br>
   *  <br>
   * Objetivo definido por: Kenia Soares <br>
   * Início da Programação: 21/02/2001 <br>
   * Fim da Programação: <br>
   * Última Versão: 1.0 <br>
*/

package br.com.tecnolity.pedidos;

import br.com.tecnolity.util.*;
import java.sql.*;
import java.util.*;

public class FormaPagamento 
{
    private String sigla;
    private String siglaAntesAlteracao;
    private String formaPagamento;
    
    public FormaPagamento() 
    {
    
    }
    
    public FormaPagamento(String sigla, String formaPagamento)
    {
        try
        {
            this.definirSigla(sigla);
            this.definirFormaPagamento(formaPagamento);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public FormaPagamento(String sigla)
    {
        try
        {
            this.definirSigla(sigla);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public FormaPagamento(String sigla, Conexao conexao)throws Exception
    {
        try
        {
            this.definirSigla(sigla);
            this.definirSiglaAntesAlteracao(sigla);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        ResultSet dadosFormaPagamento;        
        dadosFormaPagamento = conexao.executarConsulta("select * from forma_pagamento where sigla = '"+ this.sigla +"' ");

        if(dadosFormaPagamento.next())
        {                
            try
            {
                this.definirFormaPagamento(dadosFormaPagamento.getString("forma_pagamento"));                
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }			
    }
    
    public void definirSigla(String sigla) throws Exception
    {
        if(!sigla.equals("") && sigla.length() <= 2)           
            this.sigla = sigla;
        else
        {
            Exception e = new Exception("A Sigla não foi informada corretamente.");
            throw e;	
        } 
    }
    
    public void definirSiglaAntesAlteracao(String siglaAntesAlteracao) throws Exception
    {
        if(!siglaAntesAlteracao.equals("") && siglaAntesAlteracao.length() <= 2)           
            this.siglaAntesAlteracao = siglaAntesAlteracao;
        else
        {
            Exception e = new Exception("A Sigla não foi informada corretamente.");
            throw e;	
        } 
    }
    
    public void definirFormaPagamento(String formaPagamento) throws Exception
    {
        if(!formaPagamento.equals("") && formaPagamento.length() <= 50)           
            this.formaPagamento = formaPagamento;
        else
        {
            Exception e = new Exception("A Forma de Pagamento não foi informada corretamente.");
            throw e;	
        } 
    }
    
    public String obterSigla()
    {
        return this.sigla;
    }
    
    public String obterSiglaAntesAlteracao()
    {
        return this.siglaAntesAlteracao;
    }
    
    public String obterFormaPagamento()
    {
        return this.formaPagamento;
    }
    
    public Vector carregarFormasPagamento(Conexao conexao) throws Exception
    {
    	ResultSet formasPagamento;
        Vector formas = null;
        formasPagamento = conexao.executarConsulta("select * from forma_pagamento order by forma_pagamento asc");
        formas = new Vector();
        formas.addElement(null);
        while(formasPagamento.next())
        {
            formas.addElement(new FormaPagamento(formasPagamento.getString("sigla"),formasPagamento.getString("forma_pagamento")));
        }
        formasPagamento.close();        
        return formas;
    }
    
    public void cadastrarFormaPagamento() throws Exception
    {
        String query = "insert into forma_pagamento (sigla,forma_pagamento) values ";
        query = query + "('"+ this.sigla+ "', '"+ this.formaPagamento +"')";
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
    
    public void alterarFormaPagamento() throws Exception
    {
        String query = "update forma_pagamento set sigla = '"+ this.sigla +"', forma_pagamento = '"+ this.formaPagamento +"' where sigla = '"+ this.siglaAntesAlteracao +"' ";
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
    
    public void excluirFormaPagamento() throws Exception
    {
        String query = "delete from forma_pagamento where sigla = '"+ this.sigla +"' ";
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
    
    public String toString()
    {
        return this.formaPagamento;
    }
}