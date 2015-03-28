package br.com.tecnolity.administracao;

import java.sql.*;
import java.util.*;
import br.com.tecnolity.util.*;

/**
   * Projeto: 001 - Tecnolity <br>
   * Autor do C�digo: Hildeberto Mendon�a Filho <br>
   * Nome do Arquivo: Pais.java <br>
   * Linguagem: Java <br>
   *  <br>
   * Requerimentos: Requer m�quina virtual java vers�o 1.3 ou superior. <br>
   *  <br>
   * Objetivo: Classe que representa um pais do mundo. <br>
   *  <br>
   * Objetivo definido por: Hildeberto Mendon�a <br>
   * In�cio da Programa��o: 25/12/2001 <br>
   * Fim da Programa��o: <br>
   * �ltima Vers�o: 1.0 <br>
*/

public class Pais extends br.com.mentores.Pais
{
    public Pais(String siglaPais, String nomePais) throws Exception
    {
        super(siglaPais, nomePais);
    }	

    public Pais(String siglaPais) throws Exception
    {
        super(siglaPais);		
    }
    
    public Pais(){}
	
    public static Vector carregarPaises(Conexao conexao) throws Exception
    {
    	ResultSet dadosPais;
        Vector paises = null;
        dadosPais = conexao.executarConsulta("select sigla_pais, pais from pais order by pais asc");
        paises = new Vector();
        paises.addElement(null);
        while(dadosPais.next())
        {
            paises.addElement(new Pais(dadosPais.getString("sigla_pais"),dadosPais.getString("pais")));
        }
        dadosPais.close();
        return paises;
    }
    
    public void cadastrarPais() throws Exception
    {
        Conexao conexao = new Conexao('T');
        String erro = "";
        if (conexao.abrirConexao())
        {
            String query = "Select sigla_pais from pais where sigla_pais = '"+ getSigla() +"'";
            try
            {
                ResultSet dadosPais = conexao.executarConsulta(query);
                if(!dadosPais.next())
                {
                    query = "insert into pais (sigla_pais,pais) values ('"+ getSigla() +"','"+ getNome() +"')";
                    conexao.executarAtualizacao(query);
                    conexao.fecharConexao();
                }
                else
                {
                    erro = "N�o foi poss�vel cadastrar o Pa�s Informado.\nVerifique se o mesmo j� foi cadastrado.";
                    dadosPais.close();
                }
            }
            catch(Exception ex) 
            {
                ex.printStackTrace();
            }
            conexao.fecharConexao();
        }
        else
        {
            erro = "N�o foi poss�vel realizar uma conex�o com o banco de dados.";
        }
        
        if (!erro.equals(""))
        {
            Exception e = new Exception(erro);
            throw e;
        }
    }
}