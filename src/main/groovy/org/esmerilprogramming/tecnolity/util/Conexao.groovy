package org.esmerilprogramming.tecnolity.util

import java.sql.*
import groovy.sql.*

class Conexao implements ConexaoDB {
  Connection conexao
  Statement expressao
  ResultSet resultado
  String driver
  String fonteDados
  String usuario
  String senha

  Conexao() {
  }

  Conexao(String driver, String fonteDados) {
    this.driver = driver
    this.fonteDados = fonteDados
  }

  Conexao(String driver, String fonteDados, String usuario, String senha) {
    this.driver = driver
    this.fonteDados = fonteDados
    this.usuario = usuario
    this.senha = senha
  }

  void abrirConexao() throws Exception {
    try {
      Class.forName(driver)
      conexao = DriverManager.getConnection(fonteDados, usuario, senha)
    } catch (e) {
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
      return !conexao.isClosed()
    } catch (e) {
      throw new Exception("N\u00e3o foi poss\u00edvel verificar o estado da conex\u00e3o.")
    }
  }

  void fecharConexao() throws Exception {
    try {
      this.conexao.close()
    } catch (e) {
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
    quantAtualizacoes
  }

  static main(args) {

    def db = Sql.newInstance('jdbc:h2:mem:tecnolity', 'sa', '', 'org.h2.Driver')

    db.execute '''
create table pais (
  pais_id int(3) not null auto_increment,
  sigla varchar(3) not null,
  nome varchar(50) not null,
  primary key (pais_id)
);
'''
    def paises = db.rows('select * from pais')
    println paises

  }
}
