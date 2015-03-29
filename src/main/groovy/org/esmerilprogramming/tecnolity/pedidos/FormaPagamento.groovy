package org.esmerilprogramming.tecnolity.pedidos

import org.esmerilprogramming.tecnolity.util.*
import java.sql.*
import java.util.*

 class FormaPagamento 
{
    private String sigla
    private String siglaAntesAlteracao
    private String formaPagamento
    
     FormaPagamento() 
    {
    
    }
    
     FormaPagamento(String sigla, String formaPagamento)
    {
        try
        {
            this.definirSigla(sigla)
            this.definirFormaPagamento(formaPagamento)
        }
        catch(Exception e)
        {
            e.printStackTrace()
        }
    }
    
     FormaPagamento(String sigla)
    {
        try
        {
            this.definirSigla(sigla)
        }
        catch(Exception e)
        {
            e.printStackTrace()
        }
    }
    
     FormaPagamento(String sigla, Conexao conexao)throws Exception
    {
        try
        {
            this.definirSigla(sigla)
            this.definirSiglaAntesAlteracao(sigla)
        }
        catch(Exception e)
        {
            e.printStackTrace()
        }

        ResultSet dadosFormaPagamento        
        dadosFormaPagamento = conexao.executarConsulta("select * from forma_pagamento where sigla = '"+ this.sigla +"' ")

        if(dadosFormaPagamento.next())
        {                
            try
            {
                this.definirFormaPagamento(dadosFormaPagamento.getString("forma_pagamento"))                
            }
            catch(Exception e)
            {
                e.printStackTrace()
            }
        }			
    }
    
     void definirSigla(String sigla) throws Exception
    {
        if(!sigla.equals("") && sigla.length() <= 2)           
            this.sigla = sigla
        else
        {
            Exception e = new Exception("A Sigla n�o foi informada corretamente.")
            throw e	
        } 
    }
    
     void definirSiglaAntesAlteracao(String siglaAntesAlteracao) throws Exception
    {
        if(!siglaAntesAlteracao.equals("") && siglaAntesAlteracao.length() <= 2)           
            this.siglaAntesAlteracao = siglaAntesAlteracao
        else
        {
            Exception e = new Exception("A Sigla n�o foi informada corretamente.")
            throw e	
        } 
    }
    
     void definirFormaPagamento(String formaPagamento) throws Exception
    {
        if(!formaPagamento.equals("") && formaPagamento.length() <= 50)           
            this.formaPagamento = formaPagamento
        else
        {
            Exception e = new Exception("A Forma de Pagamento n�o foi informada corretamente.")
            throw e	
        } 
    }
    
     String obterSigla()
    {
        return this.sigla
    }
    
     String obterSiglaAntesAlteracao()
    {
        return this.siglaAntesAlteracao
    }
    
     String obterFormaPagamento()
    {
        return this.formaPagamento
    }
    
     Vector carregarFormasPagamento(Conexao conexao) throws Exception
    {
    	ResultSet formasPagamento
        Vector formas = null
        formasPagamento = conexao.executarConsulta("select * from forma_pagamento order by forma_pagamento asc")
        formas = new Vector()
        formas.addElement(null)
        while(formasPagamento.next())
        {
            formas.addElement(new FormaPagamento(formasPagamento.getString("sigla"),formasPagamento.getString("forma_pagamento")))
        }
        formasPagamento.close()        
        return formas
    }
    
     void cadastrarFormaPagamento() throws Exception
    {
        String query = "insert into forma_pagamento (sigla,forma_pagamento) values "
        query = query + "('"+ this.sigla+ "', '"+ this.formaPagamento +"')"
        Conexao conexao = new Conexao('T')
        if (conexao.abrirConexao())
        {
                conexao.executarAtualizacao(query)
                conexao.fecharConexao()
        }
        else
        {
            Exception e = new Exception("N�o foi poss�vel realizar uma conex�o com o banco de dados.")
            throw e
        }
    }
    
     void alterarFormaPagamento() throws Exception
    {
        String query = "update forma_pagamento set sigla = '"+ this.sigla +"', forma_pagamento = '"+ this.formaPagamento +"' where sigla = '"+ this.siglaAntesAlteracao +"' "
        Conexao conexao = new Conexao('T')
        if(conexao.abrirConexao())
        {            
            conexao.executarAtualizacao(query)
            conexao.fecharConexao()
        }
        else
        {
            Exception e = new Exception("N�o foi poss�vel realizar uma conex�o com o banco de dados.")
            throw e
        }
    }
    
     void excluirFormaPagamento() throws Exception
    {
        String query = "delete from forma_pagamento where sigla = '"+ this.sigla +"' "
        Conexao conexao = new Conexao('T')
        if(conexao.abrirConexao())
        {            
            conexao.executarAtualizacao(query)
            conexao.fecharConexao()
        }
        else
        {
            Exception e = new Exception("N�o foi poss�vel realizar uma conex�o com o banco de dados.")
            throw e
        }
    }
    
     String toString()
    {
        return this.formaPagamento
    }
}