package org.esmerilprogramming.tecnolity.aplicacao

import org.esmerilprogramming.tecnolity.util.Conexao

class Interface {
  private int identificador
  private String nomeInterface

  Interface(int identificador, String nomeInterface) {
    this.identificador = identificador
    this.nomeInterface = nomeInterface
  }

  Interface(int identificador) {
    this.identificador = identificador
  }

  Interface() {

  }

  Vector carregarInterfaces() {
    Vector interfaces = new Vector()
    def query = 'select identificador, interface as inter rom interface order by interface asc'
    def db = Conexao.instance.db
    db.eachRow(query) {
      interfaces.addElement(new Interface(it.identificador, it.inter))
    }
    interfaces
  }

  void excluirInterfacesPadroes() {
    Conexao.instance.db.execute 'delete from interface'
  }
}
