package org.esmerilprogramming.tecnolity.administracao

import org.esmerilprogramming.tecnolity.util.Conexao

class Estado extends org.esmerilprogramming.tecnolity.util.Estado {
  Estado(String sigla, String nome, Pais pais) {
    super(sigla, nome, pais)
  }

  Estado(String siglaEstado, String nomeEstado) {
    super(siglaEstado, nomeEstado)
  }

  Estado(String siglaEstado) {
    super(siglaEstado)
  }

  void carregarEstado() {
    def db = Conexao.instance.db
    def query = 'select estado, pais from estado where sigla_estado = ' + sigla
    db.firstRow(query) {
      nome = it.estado
      pais = new Pais(it.pais)
    }
  }

  static List<Estado> carregarEstados(String pais) {
    def estados = []
    estados.add(null)
    def query = 'select sigla_estado, estado from estado where pais = ' + pais + ' order by estado asc'
    def db = Conexao.instance.db
    db.eachRow(query) {
      estados.add(new Estado(it.sigla_estado, it.estado))
    }
    estados
  }

  void cadastrarEstado() {
    def query = 'select sigla_estado from estado where sigla_estado = ' + sigla
    def db = Conexao.instance.db
    if (!db.firstRow(query)) {
      db.execute 'insert into estado (sigla_estado, estado, pais) values (?,?,?)' , sigla, nome, pais.sigla
    }
 }
}
