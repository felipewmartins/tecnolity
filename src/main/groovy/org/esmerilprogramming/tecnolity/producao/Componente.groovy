package org.esmerilprogramming.tecnolity.producao

import java.util.Vector
import java.sql.*
import org.esmerilprogramming.tecnolity.util.*

 class Componente
{
    private int codigo
    private String nomeComponente
    
     Componente(int codigo, String nomeComponente)
    {
        this.codigo = codigo
        this.nomeComponente = nomeComponente
    }

     Componente(int codigo)
    {
        this.codigo = codigo
    }
    
     Componente(String nomeComponente)
    {
        this.nomeComponente = nomeComponente
    }
    
     Componente()
    {
        
    }
	
     int obterCodigo()
    {
        return this.codigo
    }

     String obterNomeComponente()
    {
        return this.nomeComponente	
    }
    
     String obterNomeComponente(Conexao conexao) throws Exception
    {
        ResultSet dadosComponente = conexao.executarConsulta("select componente from componente where codigo = " + this.codigo)
        if(dadosComponente.next())
        {
            this.nomeComponente = dadosComponente.getString("componente")
        }
        dadosComponente.close()
        return this.nomeComponente
    }
    
     void definirCodigo(int codigo)
    {
        this.codigo = codigo
    }

     void definirNomeComponente(String nomeComponente)
    {
        this.nomeComponente = nomeComponente
    }
    
     Vector carregarComponentes(Conexao conexao) throws Exception
    {
        ResultSet dadosComponente
        Vector componentes = new Vector()
        dadosComponente = conexao.executarConsulta("select * from componente order by componente asc")
        componentes.addElement(null)
                        
        while(dadosComponente.next())
        {
            componentes.addElement(new Componente(dadosComponente.getInt("codigo"),dadosComponente.getString("componente")))
        }
        dadosComponente.close()
        return componentes
    }
    
     void cadastrarComponente()
    {
        String query = "insert into componente (componente) values ('"+ this.nomeComponente +"')"
	Conexao conexao = new Conexao('T')
	if (conexao.abrirConexao())
	{
            conexao.executarAtualizacao(query)
            conexao.fecharConexao()
	}
    }
}
