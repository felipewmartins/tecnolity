package org.esmerilprogramming.tecnolity.logistica

import org.esmerilprogramming.tecnolity.util.*
import java.sql.*

class Transportadora {
  int codigo
  String transportadora

  Transportadora(int codigo) {
    this.definirCodigo(codigo)
  }

  Transportadora(int codigo, String transportadora) {
    this.definirCodigo(codigo)
      try {
        this.definirTransportadora(transportadora)
      }
    catch (e) {
      e.printStackTrace()
    }
  }

  Transportadora(String transportadora) {
    try {
      this.definirTransportadora(transportadora)
    }
    catch (e) {
      e.printStackTrace()
    }
  }

  Transportadora(int codigo, Conexao conexao)throws Exception
  {
    this.definirCodigo(codigo)

      ResultSet dadosTransportadora
      dadosTransportadora = conexao.executarConsulta('select * from transportadora where codigo = ' +  this.codigo + ' ')

      if (dadosTransportadora.next()) {
        try {
          this.definirTransportadora(dadosTransportadora.getString('transportadora'))
        }
        catch (e) {
          e.printStackTrace()
        }
      }
  }

  void definirTransportadora(String transportadora) throws Exception
  {
    if (!transportadora.equals('') && transportadora.length() <= 60)
      this.transportadora = transportadora
    else
    {
      Exception e = new Exception('A Transportadora não foi informada corretamente.')
        throw e
    }
  }

  static Vector carregarTransportadoras(Conexao conexao) throws Exception
  {
    ResultSet dadosTransportadora
      Vector transportadoras = null
      dadosTransportadora = conexao.executarConsulta('select * from transportadora order by transportadora asc')
      transportadoras = new Vector()
      transportadoras.addElement('Selecione...')
      while (dadosTransportadora.next()) {
        transportadoras.addElement(new Transportadora(dadosTransportadora.getInt('codigo'), dadosTransportadora.getString('transportadora')))
      }
    dadosTransportadora.close()
      return transportadoras
  }

  void cadastrarTransportadora() throws Exception
  {
    String query = 'insert into transportadora (transportadora) values '
      query = query  +  '('' + this.transportadora + '')'
      Conexao conexao = new Conexao('T')
      if (conexao.abrirConexao()) {
        conexao.executarAtualizacao(query)
          conexao.fecharConexao()
      }
      else
      {
        Exception e = new Exception('Não foi possível realizar uma conexão com o banco de dados.')
          throw e
      }
  }

  void alterarTransportadora() throws Exception
  {
    String query = 'update transportadora set transportadora = '' +  this.transportadora + '' where codigo = ' + this.codigo + ' '
      Conexao conexao = new Conexao('T')
      if (conexao.abrirConexao()) {
        conexao.executarAtualizacao(query)
          conexao.fecharConexao()
      }
      else
      {
        Exception e = new Exception('Não foi possível realizar uma conexão com o banco de dados.')
          throw e
      }
  }

  void excluirTransportadora() throws Exception
  {
    String query = 'delete from transportadora where codigo = ' +  this.codigo + ' '
      Conexao conexao = new Conexao('T')
      if (conexao.abrirConexao()) {
        conexao.executarAtualizacao(query)
          conexao.fecharConexao()
      }
      else
      {
        Exception e = new Exception('Não foi possível realizar uma conexão com o banco de dados.')
          throw e
      }
  }

}
