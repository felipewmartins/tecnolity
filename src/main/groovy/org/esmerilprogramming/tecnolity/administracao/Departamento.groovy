package org.esmerilprogramming.tecnolity.administracao

import org.esmerilprogramming.tecnolity.util.Conexao

class Departamento {
  int codigo
  String nomeDepartamento
  Colaborador responsavel

  Departamento(int codigo, String nomeDepartamento, Colaborador responsavel) {
    this.codigo = codigo
    this.nomeDepartamento = nomeDepartamento
    this.responsavel = responsavel
  }

  Departamento(int codigo, String nomeDepartamento) {
    this.codigo = codigo
    this.nomeDepartamento = nomeDepartamento
  }

  Departamento(int codigo) {
    this.codigo = codigo
  }

  void carregarDepartamento() {
    def db = Conexao.instance.db
    def query = 'select departamento, responsavel from departamento where codigo = ' + codigo
    db.firstRow(query) {
      nomeDepartamento = it.departamento
      responsavel = it.responsavel ?: new Colaborador(it.responsavel)
    }
  }

  Vector carregarDepartamentos() {
    Vector departamentos = new Vector()
    departamentos.addElement(null)
    def db = Conexao.instance.db
    def query = 'select codigo, d.departamento, nome_completo as responsavel from departamento d, usuario u where responsavel += usuario order by d.departamento asc'
    db.eachRow(query) {
      def cod = it.codigo
      def nomeDep = it.departamento
      def resp = it.responsavel
      if (!resp) {
        departamentos.addElement(new Departamento(cod, nomeDep, null))
      } else {
        departamentos.addElement(new Departamento(cod, nomeDep, new Colaborador(resp)))
      }
    }
    departamentos
  }

  Vector carregarNomesDepartamentos() {
    def db = Conexao.instance.db
    def query = 'select codigo, departamento from departamento order by departamento asc'
    Vector departamentos = new Vector()
    departamentos.addElement('Selecione...')
    db.eachRow(query) {
      departamentos.addElement(new Departamento(it.codigo, it.departamento))
    }
    departamentos
  }

  void cadastrarDepartamento(String nomeDepartamento, Colaborador responsavel) throws Exception {
    String query = 'insert into departamento (departamento, responsavel) values ('' +  nomeDepartamento + '', ' + ((responsavel == null)?'NULL':''' + responsavel.obterMatricula() + ''') + ')'
    Conexao.instance.db.execute query
  }

  void alterarDepartamento(String nomeDepartamento, Colaborador responsavel) {
    String query = 'update departamento set departamento = '' +  nomeDepartamento + '', responsavel = ' + ((responsavel == null)?'NULL':''' + responsavel.obterMatricula() + ''') + ' where codigo = ' + this.codigo
    Conexao.instance.db.execute query
  }

  void excluirDepartamento() {
    Conexao.instance.db.execute 'delete from departamento where codigo = ' + codigo
  }

  String toString() {
    nomeDepartamento
  }
}
