package org.esmerilprogramming.tecnolity.administracao

import org.esmerilprogramming.tecnolity.util.Conexao

class Cambio {
  float dolar
  String data

  Cambio(float dolar, String data) {
    this.dolar = dolar
    this.data = data
  }

  void definirDolar(float dolar) throws Exception {
    if (dolar == 0.0f) {
      Exception e = new Exception('O valor do dólar não foi informado.')
      throw e
    } else {
      this.dolar = dolar
    }
  }

  void carregarCambio() {
    def db = Conexao.instance.db
    def query = 'select distinct valor_dolar from cotacao_dolar where data = '
    query += '(select max(data) from cotacao_dolar)'
    db.firstRow(query) {
      definirDolar(it.valor_dolar)
    }
  }

  void cadastrarCambio() throws Exception {
    def db = Conexao.instance.db
    db.execute 'insert into cotacao_dolar (valor_dolar) values (?)', dolar
  }
}
