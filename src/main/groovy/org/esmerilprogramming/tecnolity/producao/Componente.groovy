package org.esmerilprogramming.tecnolity.producao

import java.util.Vector
import java.sql.*
import org.esmerilprogramming.tecnolity.util.*

/**
   * Projeto: 001 - Tecnolity <br>
   * Autor do C�digo: Hildeberto Mendon�a Filho <br>
   * Nome do Arquivo: Componente.java <br>
   * Linguagem: Java <br>
   *  <br>
   * Requerimentos: Requer m�quina virtual java vers�o 1.3 ou superior. <br>
   *  <br>
   * Objetivo: Classe que representa um componente fabricado pela empresa. <br>
   *  <br>
   * Objetivo definido por: Hildeberto Mendon�a <br>
   * In�cio da Programa��o: 12/02/2002 <br>
   * Fim da Programa��o: <br>
   * �ltima Vers�o: 1.0 <br>
*/

public class Componente
{
    private int codigo
    private String nomeComponente
    
    public Componente(int codigo, String nomeComponente)
    {
        this.codigo = codigo
        this.nomeComponente = nomeComponente
    }

    public Componente(int codigo)
    {
        this.codigo = codigo
    }
    
    public Componente(String nomeComponente)
    {
        this.nomeComponente = nomeComponente
    }
    
    public Componente()
    {
        
    }
	
    public int obterCodigo()
    {
        return this.codigo
    }

    public String obterNomeComponente()
    {
        return this.nomeComponente	
    }
    
    public String obterNomeComponente(Conexao conexao) throws Exception
    {
        ResultSet dadosComponente = conexao.executarConsulta("select componente from componente where codigo = " + this.codigo)
        if(dadosComponente.next())
        {
            this.nomeComponente = dadosComponente.getString("componente")
        }
        dadosComponente.close()
        return this.nomeComponente
    }
    
    public void definirCodigo(int codigo)
    {
        this.codigo = codigo
    }

    public void definirNomeComponente(String nomeComponente)
    {
        this.nomeComponente = nomeComponente
    }
    
    public Vector carregarComponentes(Conexao conexao) throws Exception
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
    
    public void cadastrarComponente()
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