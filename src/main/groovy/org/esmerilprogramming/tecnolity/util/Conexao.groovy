package org.esmerilprogramming.tecnolity.util

import java.sql.*

public class Conexao implements ConexaoDB {
  Connection conexao
  Statement expressao
  ResultSet resultado
  String driver
  String fonteDados
  String usuario
  String senha

  void abrirConexao() throws Exception {
    try {
      Class.forName(this.driver)
      this.conexao = DriverManager.getConnection(this.fonteDados, this.usuario, this.senha)
    }
    catch (Exception e) {
      e.printStackTrace()
      throw new Exception("N\u00e3o foi poss\u00edvel abrir uma conex\u00e3o com o banco de dados. (" + e.getMessage() + ")")
    }
  }

  void commit() throws SQLException {
  }

  void rollback() throws SQLException {
  }

  boolean conexaoAberta() throws Exception {
    try {
      return !this.conexao.isClosed()
    }
    catch (Exception e) {
      throw new Exception("N\u00e3o foi poss\u00edvel verificar o estado da conex\u00e3o.")
    }
  }

  void fecharConexao() throws Exception {
    try {
      this.conexao.close()
    }
    catch (Exception e) {
      throw new Exception("N\u00e3o foi poss\u00edvel fechar a conex\u00e3o com o banco de dados.")
    }
  }

  ResultSet executarConsulta(final String query) throws SQLException {
    this.expressao = this.conexao.createStatement(1004, 1007)
    return this.resultado = this.expressao.executeQuery(query)
  }

  int executarAtualizacao(final String query) throws SQLException {
    int quantAtualizacoes = 0
    this.expressao = this.conexao.createStatement()
    quantAtualizacoes = this.expressao.executeUpdate(query)
    return quantAtualizacoes
  }

}
