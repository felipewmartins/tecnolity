package org.esmerilprogramming.tecnolity.suprimentos

import org.esmerilprogramming.tecnolity.util.Conexao

class Unidade {
  int codigo
  String nomeUnidade

  Unidade(int codigo, String nomeUnidade) {
    this.codigo = codigo
    this.nomeUnidade = nomeUnidade
  }

  Unidade(int codigo) {
    this.codigo = codigo
  }

  Unidade() {

  }

  boolean cadastrarUnidade(String nomeUnidade) {
    Conexao.instance.db.execute 'insert into unidade (unidade) values (?)' , nomeUnidade
    false
  }
}
