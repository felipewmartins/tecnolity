package org.esmerilprogramming.tecnolity.administracao

import java.util.*
import java.sql.*

import org.esmerilprogramming.tecnolity.util.*

class Estado extends org.esmerilprogramming.tecnolity.util.Estado
{
  Estado(String sigla, String nome, Pais pais) throws Exception
  {
    super(sigla,nome,pais)
  }

  Estado(String siglaEstado, String nomeEstado) throws Exception
  {
    super(siglaEstado,nomeEstado)
  }

  Estado(String siglaEstado) throws Exception
  {
    super(siglaEstado)
  }

  Estado(){}

  void carregarEstado() throws Exception
  {
    Conexao conexao = new Conexao('C')
      if(conexao.abrirConexao()) {
        ResultSet dadosEstado = conexao.executarConsulta("select * from estado where sigla_estado = '"+ this.getSigla() +"'")
          if(dadosEstado.next()) {
            setNome(dadosEstado.getString("estado"))
              setPais(new Pais(dadosEstado.getString("pais")))
          }
        dadosEstado.close()
      }
    conexao.fecharConexao()
  }

  static Vector carregarEstados(String pais,Conexao conexao) throws Exception
  {
    ResultSet dadosEstado
      Vector estados = new Vector()
      try
      {
        dadosEstado = conexao.executarConsulta("select sigla_estado, estado from estado where pais = '"+ pais +"' order by estado asc")
          estados.addElement(null)
          while(dadosEstado.next()) {
            estados.addElement(new Estado(dadosEstado.getString("sigla_estado"),dadosEstado.getString("estado")))
          }
        dadosEstado.close()
      }
    catch (SQLException e) {
      e.printStackTrace()
    }
    return estados
  }

  void cadastrarEstado() throws Exception
  {
    Conexao conexao = new Conexao('T')
      String erro = ""
      if (conexao.abrirConexao()) {
        String query = "Select sigla_estado from estado where sigla_estado = '"+ this.getSigla() +"'"
          try
          {
            ResultSet dadosEstado = conexao.executarConsulta(query)
              if(!dadosEstado.next()) {
                query = "insert into estado (sigla_estado,estado,pais) values ('"+ this.getSigla() +"','"+ this.getNome() +"','"+ this.getPais().getSigla() +"')"
                  conexao.executarAtualizacao(query)
                  conexao.fecharConexao()
              }
              else
              {
                erro = "Não foi possível cadastrar o Estado Informado.\nVerifique se o mesmo já foi cadastrado."
                  dadosEstado.close()
              }
          }
        catch(Exception ex) {
          ex.printStackTrace()
        }
      }
      else
      {
        erro = "Não foi possível realizar uma conexão com o banco de dados."
      }

    if (!erro.equals("")) {
      Exception e = new Exception(erro)
        throw e
    }
  }
}
