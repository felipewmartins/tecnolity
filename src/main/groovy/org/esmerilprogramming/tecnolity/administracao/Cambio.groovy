package org.esmerilprogramming.tecnolity.administracao

import org.esmerilprogramming.tecnolity.util.*
import java.sql.*


class Cambio
{
  private float dolar
    private String data

    Cambio() {}

  Cambio(float dolar, String data) {
    this.dolar = dolar
      this.data = data
  }

  float obterDolar() {
    return this.dolar
  }

  String obterData() {
    return this.data
  }

  void definirDolar(float dolar) throws Exception
  {
    if(dolar == 0.0f) {
      Exception e = new Exception("O valor do dólar não foi informado.")
        throw e
    }
    else
      this.dolar = dolar
  }

  void definirData(String data) {
    this.data = data
  }

  void carregarCambio(Conexao conexao) throws Exception
  {
    ResultSet dadosCambio
      dadosCambio = conexao.executarConsulta("select distinct valor_dolar from cotacao_dolar where data = (select max(data) from cotacao_dolar)")
      if(dadosCambio.next()) {
        definirDolar(dadosCambio.getFloat("valor_dolar"))
      }
    dadosCambio.close()
  }

  void cadastrarCambio() throws Exception
  {
    Conexao conexao = new Conexao('T')
      if(conexao.abrirConexao()) {
        String query = "insert into cotacao_dolar (valor_dolar) "
          query += "values ("+ this.dolar +")"
          conexao.executarAtualizacao(query)
          conexao.fecharConexao()
      }
  }
}
