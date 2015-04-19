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
  interface varchar(60) not null
);
'''

    db.execute '''
create table categoria_item (
    categoria_item_id int(9) not null auto_increment,
    categoria varchar(50) not null,
    primary key (categoria_item_id)
);
'''

    db.execute '''
create table item (
    item_id int(9) not null auto_increment,
    descricao varchar(60) not null,
    categoria_id int(9) not null,
    armazenamento text null,
    unidade int(9) not null,
    temperatura decimal(18, 3) null,
    seguranca text null,
    quantidade decimal(15, 3) not null,
    quantidade_minima decimal(15, 3) not null,
    quantidade_maxima decimal(15, 3) null,
    percentual_ipi int(9) null,
    percentual_perda decimal(3, 2) null,
    ativo bit not null,
    independente bit not null,
    primary key (item_id)
);
'''

    db.execute '''
create table unidade (
  unidade_id int(9) not null auto_increment,
  nome varchar(50) not null,
  primary key (unidade_id)
);
'''

    db.execute '''
create table lote (
  numero int(9) not null auto_increment,
  item int(9) not null,
  movimentacao_item int(9) null ,
  localizacao varchar (100)  null,
  data_validade datetime null ,
  quantidade decimal(18, 3) not null,
  reservado bit null,
  descricao varchar (200) null,
  lote_basico char (1) null
);
'''
    def params = [1, 'e', 'p', 1, 'Esmeril Programming', '999999999', 'ssp', '9999999999',
    1, 'universe', 'orion', 'space', 'gamma', 'ray', '12345', '111', '88888888', '111', '88888888', 'ep@test.com', 0]

    db.execute 'insert into usuario values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)', params

    db.execute 'insert into interface values (1, \'Secao Administração\')'
    db.execute 'insert into interface values (2, \'Seção Gerência\')'
    db.execute 'insert into interface values (3, \'Seção Logística\')'
    db.execute 'insert into interface values (4, \'Seção Pedido\')'
    db.execute 'insert into interface values (5, \'Seção Produção\')'
    db.execute 'insert into interface values (6, \'Seção Suprimentos\')'
    db.execute 'insert into interface values (7, \'Acesso Direto a Base de Dados\')'
    db.execute 'insert into interface values (8, \'Categoria\')'
    db.execute 'insert into interface values (9, \'Cliente\')'
    db.execute 'insert into interface values (10, \'Colaborador\')'
    db.execute 'insert into interface values (11, \'Componente\')'
    db.execute 'insert into interface values (12, \'Departamento\')'
    db.execute 'insert into interface values (13, \'Despesa\')'
    db.execute 'insert into interface values (14, \'Estado\')'
    db.execute 'insert into interface values (15, \'Forma de Pagamento\')'
    db.execute 'insert into interface values (16, \'Fornecedor\')'
    db.execute 'insert into interface values (17, \'Item\')'
    db.execute 'insert into interface values (18, \'Lote\')'
    db.execute 'insert into interface values (19, \'Matriz\')'
    db.execute 'insert into interface values (20, \'Motorista\')'
    db.execute 'insert into interface values (21, \'Movimentação de Itens\')'
    db.execute 'insert into interface values (22, \'Multa\')'
    db.execute 'insert into interface values (23, \'País\')'
    db.execute 'insert into interface values (24, \'Pedido\')'
    db.execute 'insert into interface values (25, \'Atribuição de Permissões\')'
    db.execute 'insert into interface values (26, \'Produto\')'
    db.execute 'insert into interface values (27, \'Requisição de Compra\')'
    db.execute 'insert into interface values (28, \'Requisição Interna\')'
    db.execute 'insert into interface values (29, \'Tipo de Produção\')'
    db.execute 'insert into interface values (30, \'Transportadora\')'
    db.execute 'insert into interface values (31, \'Unidade\')'
    db.execute 'insert into interface values (32, \'Veículo\')'
    db.execute 'insert into interface values (33, \'Recursos do Pedido\')'
    db.execute 'insert into interface values (34, \'Alteração de Preço\')'

    db.execute 'insert into permissao values (6, \'e\', \'E\')'

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
      throw new Exception('N\u00e3o foi poss\u00edvel verificar o estado da conex\u00e3o.')
    }
  }

  void fecharConexao() throws Exception {
    try {
      this.conexao.close()
    } catch (e) {
      throw new Exception('N\u00e3o foi poss\u00edvel fechar a conex\u00e3o com o banco de dados.')
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
