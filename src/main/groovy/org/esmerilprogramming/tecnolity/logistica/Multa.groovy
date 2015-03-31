package org.esmerilprogramming.tecnolity.logistica

import org.esmerilprogramming.tecnolity.util.*
import java.sql.*

class Multa {

  int codigo
  Veiculo veiculo
  String motivo
  float valor
  int responsabilidade
  String data

  Multa(int codigo) {
    definirCodigo(codigo)
  }

  Multa(int codigo, Conexao conexao)throws Exception
  {
    this.definirCodigo(codigo)

      ResultSet dadosMulta
      dadosMulta = conexao.executarConsulta("select * from multa where codigo = "+ this.codigo +" ")

      if(dadosMulta.next()) {
        try
        {
          this.definirVeiculo(new Veiculo(dadosMulta.getString("placa")))
            this.definirMotivo(dadosMulta.getString("motivo"))
            this.definirValor(dadosMulta.getFloat("valor"))
            this.definirResponsabilidade(dadosMulta.getInt("responsabilidade"))
            this.definirData(dadosMulta.getString("datahora"))
        }
        catch(Exception e) {
          e.printStackTrace()
        }
      }
  }

  Multa(Veiculo veiculo, String motivo, float valor, int responsabilidade, String data) {
    try
    {
      this.definirVeiculo(veiculo)
        this.definirMotivo(motivo)
        this.definirValor(valor)
        this.definirResponsabilidade(responsabilidade)
        this.definirData(data)
    }
    catch(Exception e) {
      e.printStackTrace()
    }
  }

  Multa(int codigo, Veiculo veiculo, String motivo, float valor, int responsabilidade, String data) {
    this.definirCodigo(codigo)
      try
      {
        this.definirVeiculo(veiculo)
          this.definirMotivo(motivo)
          this.definirValor(valor)
          this.definirResponsabilidade(responsabilidade)
          this.definirData(data)
      }
    catch(Exception e) {
      e.printStackTrace()
    }
  }

  void definirVeiculo(Veiculo veiculo) throws Exception
  {
    if(veiculo != null)
      this.veiculo = veiculo
    else
    {
      throw new Exception("A Placa não foi informada.")
    }
  }

  void definirMotivo(String motivo) throws Exception
  {
    if(!motivo.equals("") && motivo.length() <= 100)
      this.motivo = motivo
    else
    {
      Exception e = new Exception("O Motivo não foi informado corretamente.")
        throw e
    }
  }

  void definirValor(float valor) throws Exception
  {
    String erro = ""
      if(Float.isNaN(valor) || valor <= 0.0f)
        erro = "O Valor não foi informado corretamente."

          if(!erro.equals("")) {
            Exception e = new Exception(erro)
              throw e
          }
          else
            this.valor = valor
  }

  void definirResponsabilidade(int responsabilidade) throws Exception
  {
    if(responsabilidade > 0)
      this.responsabilidade = responsabilidade
    else
    {
      Exception e = new Exception("O Motorista não foi informado.")
        throw e
    }
  }

  void definirData(String data) throws Exception
  {
    String erro = ""
      if(data.equals(""))
        erro = "A Data não foi informada."
      else if(data.length() == 10) {
        if(!Calendario.validarData(data,"/"))
          erro = "Data inválida."
      }
      else
        erro = "Data inválida."

          if(!erro.equals("")) {
            Exception e = new Exception(erro)
              throw e
          }
          else
            this.data = data
  }

  void cadastrarMulta() throws Exception
  {
    String query = "insert into multa (placa,motivo,valor,responsabilidade,datahora) values "
      query = query + "('"+ this.veiculo.obterPlaca() +"', '"+ this.motivo +"', "+ this.valor +", "+ this.responsabilidade +", '"+ Calendario.inverterFormato(this.data,"/") +"')"
      Conexao conexao = new Conexao('T')
      if (conexao.abrirConexao()) {
        conexao.executarAtualizacao(query)
          conexao.fecharConexao()
      }
      else
      {
        Exception e = new Exception("Não foi possível realizar uma conexão com o banco de dados.")
          throw e
      }
  }

  void alterarMulta() throws Exception
  {
    String query = "update multa set placa = '"+ this.veiculo.obterPlaca() +"',motivo = '"+ this.motivo +"',valor = "+ this.valor +",responsabilidade = "+ this.responsabilidade +",datahora = '"+ Calendario.inverterFormato(this.data,"/") +"' where codigo = "+ this.codigo +" "
      Conexao conexao = new Conexao('T')
      if(conexao.abrirConexao()) {
        conexao.executarAtualizacao(query)
          conexao.fecharConexao()
      }
      else
      {
        Exception e = new Exception("Não foi possível realizar uma conexão com o banco de dados.")
          throw e
      }
  }

  void excluirMulta() throws Exception
  {
    String query = "delete from multa where codigo = "+ this.codigo +" "
      Conexao conexao = new Conexao('T')
      if(conexao.abrirConexao()) {
        conexao.executarAtualizacao(query)
          conexao.fecharConexao()
      }
      else
      {
        Exception e = new Exception("Não foi possível realizar uma conexão com o banco de dados.")
          throw e
      }
  }
}
