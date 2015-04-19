package org.esmerilprogramming.tecnolity.producao

import org.esmerilprogramming.tecnolity.util.Conexao

class Componente {
  int codigo
  String nomeComponente

  Componente(int codigo, String nomeComponente) {
    this.codigo = codigo
    this.nomeComponente = nomeComponente
  }

  Componente(int codigo) {
    this.codigo = codigo
  }

  Componente(String nomeComponente) {
    this.nomeComponente = nomeComponente
  }

  String obterNomeComponente(Conexao conexao) throws Exception {
    def query 'select componente from componente where codigo = ' + codigo
    def db = Conexao.instance.db
    db.eachRow(query) {
      nomeComponente = it.componente
    }
    nomeComponente
  }

  Vector carregarComponentes() {
    Vector componentes = new Vector()
    componentes.addElement(null)
    def query = 'select codigo, componente from componente order by componente asc'
    def db = Conexao.instance.db
    db.eachRow(query) {
      componentes.addElement(new Componente(it.codigo, it.componente))
    }
    componentes
  }

  void cadastrarComponente() {
    Conexao.instance.db.execute 'insert into componente (componente) values (?)' , nomeComponente
  }
}
