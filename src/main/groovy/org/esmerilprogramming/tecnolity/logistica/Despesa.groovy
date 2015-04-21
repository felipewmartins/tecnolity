package org.esmerilprogramming.tecnolity.logistica

import org.esmerilprogramming.tecnolity.util.*
import java.sql.*

class Despesa {

  String placa, descricao, data, placaAntesAlteracao, dataAntesAlteracao
  float valor

  Despesa(String data) {
    this.data = data
  }

  Despesa(String placa, String data) {
    this.placa = placa
    this.data = data
  }

  Despesa(String placa, String data) {
    this.placa = placa
    this.data = data
    this.placaAntesAlteracao = placa
    this.dataAntesAlteracao = data

    def query = 'select descricao, valor from despesa_veiculo where veiculo = ' +  placa + ' and datahora = ' + Calendario.inverterFormato(data, '/')
    def db = Conexao.instance.db
    if (db.firstRow(query)) {
      descricao = it.descricao
      valor = it.valor
    }
  }

  Despesa(String placa, String descricao, float valor, String data) {
    this.placa = placa
    this.descricao = descricao
    this.valor = valor
    this.data = data
  }

  Despesa(String placa, String descricao, float valor, String data, String placaAntesAlteracao, String dataAntesAlteracao) {
    this.definirPlaca(placa)
    this.definirPlacaAntesAlteracao(placaAntesAlteracao)
    this.definirDescricao(descricao)
    this.definirValor(valor)
    this.definirData(data)
    this.definirDataAntesAlteracao(dataAntesAlteracao)
  }

  void definirDataAntesAlteracao(String dataAntesAlteracao) throws Exception
  {

    String erro = ''
      if (dataAntesAlteracao.equals(''))
        erro = 'A Data não foi informada.'
      else if (dataAntesAlteracao.length() == 10) {
        if (!Calendario.validarData(dataAntesAlteracao, '/'))
          erro = 'Data inválida.'
      }
      else if (dataAntesAlteracao.length() < 10)
        erro = 'Data inválida.'

          if (!erro.equals('')) {
            Exception e = new Exception(erro)
              throw e
          }
          else
            this.dataAntesAlteracao = dataAntesAlteracao
  }

  void definirData(String data) throws Exception
  {

    String erro = ''
      if (data.equals(''))
        erro = 'A Data não foi informada.'
      else if (data.length() == 10) {
        if (!Calendario.validarData(data, '/'))
          erro = 'Data inválida.'
      }
      else
        erro = 'Data inválida.'

          if (!erro.equals('')) {
            Exception e = new Exception(erro)
              throw e
          }
          else
            this.data = data
  }

  Vector carregarDatasDespesas() {
    Vector datasDespesas = new Vector()
    def query = 'select datahora from despesa_veiculo where veiculo = ' + placa + ' order by datahora desc'
    def db = Conexao.instance.db
    db.eachRow(query) {
      datasDespesas.addElement(new Despesa(it.datahora))
    }
    datasDespesas
  }

  void cadastrarDespesa() throws Exception
  {
    String query = 'insert into despesa_veiculo (veiculo, datahora, descricao, valor) values '
      query = query  +  '(' + this.placa + ', ' + Calendario.inverterFormato(this.data, '/') + ', ' + this.descricao + ', ' + this.valor + ')'
      Conexao conexao = new Conexao('T')
      boolean existente = false
      if (conexao.abrirConexao()) {
        ResultSet despesa = conexao.executarConsulta('select * from despesa_veiculo where veiculo = ' +  this.placa + ' and datahora = ' + Calendario.inverterFormato(this.data, '/'))
          if (despesa.next()) {
            existente = true
              Exception e = new Exception('Já existe uma despesa associada a este veículo na data informada. Não foi possível realizar o cadastro.')
              throw e
          }
          else
          {
            conexao.executarAtualizacao(query)
          }
        conexao.fecharConexao()
      }
      else
      {
        Exception e = new Exception('Não foi possível realizar uma conexão com o banco de dados.')
          throw e
      }
  }

  void alterarDespesa() {
    def query = 'update despesa_veiculo set veiculo = ' + placa + ', datahora = ' + Calendario.inverterFormato(data, '/') + ', descricao = ' + descricao + ', valor = ' + valor + ' where veiculo = ' + placaAntesAlteracao + ' and datahora = ' + Calendario.inverterFormato(dataAntesAlteracao, '/')
    Conexao.instance.db.execute query
  }

  void excluirDespesa() {
    def query = 'delete from despesa_veiculo where veiculo = ' + placa + ' and datahora = ' + Calendario.inverterFormato(data, '/')
    Conexao.instance.db.execute query
  }
}
