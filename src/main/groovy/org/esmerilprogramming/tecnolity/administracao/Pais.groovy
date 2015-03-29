package org.esmerilprogramming.tecnolity.administracao

import java.sql.*
import java.util.*
import org.esmerilprogramming.tecnolity.util.*

public class Pais extends org.esmerilprogramming.tecnolity.util.Pais
{
  public Pais(String siglaPais, String nomePais) throws Exception
  {
    super(siglaPais, nomePais)
  }

  public Pais(String siglaPais) throws Exception
  {
    super(siglaPais)
  }

  public Pais(){}

  public static Vector carregarPaises(Conexao conexao) throws Exception
  {
    ResultSet dadosPais
    Vector paises = null
    dadosPais = conexao.executarConsulta("select sigla_pais, pais from pais order by pais asc")
    paises = new Vector()
    paises.addElement(null)
    while(dadosPais.next())
    {
      paises.addElement(new Pais(dadosPais.getString("sigla_pais"),dadosPais.getString("pais")))
    }
    dadosPais.close()
    return paises
  }

  public void cadastrarPais() throws Exception
  {
    Conexao conexao = new Conexao('T')
    String erro = ""
    if (conexao.abrirConexao())
    {
      String query = "Select sigla_pais from pais where sigla_pais = '"+ getSigla() +"'"
      try
      {
        ResultSet dadosPais = conexao.executarConsulta(query)
        if(!dadosPais.next())
        {
          query = "insert into pais (sigla_pais,pais) values ('"+ getSigla() +"','"+ getNome() +"')"
          conexao.executarAtualizacao(query)
          conexao.fecharConexao()
        }
        else
        {
          erro = "Não foi possível cadastrar o País Informado.\nVerifique se o mesmo já foi cadastrado."
          dadosPais.close()
        }
      }
      catch(Exception ex)
      {
        ex.printStackTrace()
      }
      conexao.fecharConexao()
    }
    else
    {
      erro = "Não foi possível realizar uma conexão com o banco de dados."
    }

    if (!erro.equals(""))
    {
      Exception e = new Exception(erro)
      throw e
    }
  }
}
