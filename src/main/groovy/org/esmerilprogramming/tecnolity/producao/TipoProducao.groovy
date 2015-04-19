package org.esmerilprogramming.tecnolity.producao

import org.esmerilprogramming.tecnolity.util.Conexao

class TipoProducao {
  int codigo
  String tipoProducao

  TipoProducao(int codigo, String tipoProducao) {
    this.codigo = codigo
    this.tipoProducao = tipoProducao
  }

  TipoProducao(int codigo) {
    this.codigo = codigo
  }

  Vector carregarTiposProducao() {
    Vector tiposProducao = new Vector()
    tiposProducao.addElement(null)
    def query = 'select codigo, tipo_producao from tipo_producao order by tipo_producao asc'
    def db = Conexao.instance.db
    db.eachRow(query) {
      tiposProducao.addElement(new TipoProducao(it.codigo, it.tipo_producao))
    }
    tiposProducao
  }

  int obterUltimoIdentificador() {
    def retorno = 1
    def query 'select max(codigo) as identificador_maior from tipo_producao'
    def db = Conexao.instance.db
    db.firstRow(query) {
      retorno += it.identificador_maior
    }
    retorno
  }

  void cadastrarTipoProducao() {
    Conexao.instance.db.execute 'insert into tipo_producao (codigo, tipo_producao) values (?,?)' ,codigo,tipoProducao
  }
}
