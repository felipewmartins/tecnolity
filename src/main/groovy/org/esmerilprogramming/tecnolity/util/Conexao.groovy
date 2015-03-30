package org.esmerilprogramming.tecnolity.util

import java.sql.*
import groovy.sql.*

@Singleton
class Conexao implements ConexaoDB {
  Connection conexao
  Statement expressao
  ResultSet resultado

  def db

  void setupDB() {

    db = Sql.newInstance('jdbc:h2:mem:tecnolity', 'sa', '', 'org.h2.Driver')

    db.execute '''
create table pais ( pais_id int(3) not null auto_increment,
  sigla varchar(3) not null, nome varchar(50) not null, primary key (pais_id)
);
'''

    db.execute '''
create table usuario (
  usuario_id int(4) not null auto_increment,
  nome varchar(30) not null,
  senha varchar(30) not null,
  sexo tinyint(1) not null,
  nome_completo varchar(60) not null,
  identidade varchar(15) not null,
  orgao_emissor_rg varchar(10) not null,
  cpf varchar(11) not null,
  departamento int(2) null,
  logradouro varchar(100) not null,
  complemento varchar(50) null,
  bairro varchar(50) not null,
  cidade varchar(50) not null,
  estado varchar(3) null,
  cep varchar(8) null,
  ddd varchar(3) null,
  telefone varchar(8) null,
  ramal varchar(8) null,
  celular varchar(8) null,
  email varchar(60) null,
  senha_alterada tinyint(1) null,
  primary key (usuario_id)
);
'''

    db.execute '''
create table permissao (
  interface int(6) not null,
  usuario varchar(20) not null,
  permissao varchar(2) not null
);
'''

    db.execute '''
create table interface (
  identificador int(6) not null,
  interface varchar(60) not null,
  descricao varchar(120) null
);
'''

    def params = [1, 'e', 'p', 1, 'Esmeril Programming', '999999999', 'ssp', '9999999999',
    1,'universe','orion','space','gamma','ray','12345','111','88888888','111','88888888','ep@test.com', 0]

    db.execute 'insert into usuario values (?, ?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)', params

  }

  void abrirConexao() throws Exception {

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

}
