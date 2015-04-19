package org.esmerilprogramming.tecnolity.administracao

import org.esmerilprogramming.tecnolity.util.Conexao

class Pais extends org.esmerilprogramming.tecnolity.util.Pais {
  Pais(String siglaPais, String nomePais) {
    super(siglaPais, nomePais)
  }

  Pais(String siglaPais) {
    super(siglaPais)
  }

  static Vector carregarPaises() {
    Vector paises = new Vector()
    paises.addElement(null)
    def db = Conexao.instance.db
    def query = 'select sigla_pais, pais from pais order by pais asc'
    db.eachRow(query) {
      paises.addElement(new Pais(it.sigla_pais, it.pais))
    }
    paises
  }

  void cadastrarPais() {
    def db = Conexao.instance.db
    def query = 'select sigla_pais from pais where sigla_pais = ' + sigla
    if (!db.firstRow(query)) {
      db.execute 'insert into pais (sigla_pais, pais) values (?, ?)', sigla, nome
    }
  }

}
