package org.esmerilprogramming.tecnolity.administracao

import java.util.Vector
import java.sql.*

import org.esmerilprogramming.tecnolity.util.*


class Departamento
{
  private int codigo
    private String nomeDepartamento
    private Colaborador responsavel

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

  Departamento() {

  }

  int obterCodigo() {
    return this.codigo
  }

  String obterNomeDepartamento() {
    return this.nomeDepartamento
  }

  Colaborador obterResponsavel() {
    return this.responsavel
  }

  void definirCodigo(int codigo) {
    this.codigo = codigo
  }

  void definirNomeDepartamento(String nomeDepartamento) {
    this.nomeDepartamento = nomeDepartamento
  }

  void definirResponsavel(Colaborador responsavel) {
    this.responsavel = responsavel
  }

  void carregarDepartamento(Conexao conexao) throws Exception
  {
    ResultSet dadosDepartamento = conexao.executarConsulta("select * from departamento where codigo = " + this.codigo)
      if(dadosDepartamento.next()) {
        this.definirNomeDepartamento(dadosDepartamento.getString("departamento"))
          String responsavel = dadosDepartamento.getString("responsavel")
          this.definirResponsavel((responsavel != null)?new Colaborador(responsavel):null)
      }
  }

  Vector carregarDepartamentos(Conexao conexao) throws Exception
  {
    ResultSet dadosDepartamento
      Vector departamentos = new Vector()
      try
      {
        dadosDepartamento = conexao.executarConsulta("select codigo, d.departamento, nome_completo as responsavel from departamento d, usuario u where responsavel *= usuario order by d.departamento asc")
          departamentos.addElement(null)
          String responsavel, nomeDepartamento
          int codigo

          while(dadosDepartamento.next()) {
            codigo = dadosDepartamento.getInt("codigo")
              nomeDepartamento = dadosDepartamento.getString("departamento")
              responsavel = dadosDepartamento.getString("responsavel")
              if(responsavel == null)
                departamentos.addElement(new Departamento(codigo,nomeDepartamento,null))
              else
                departamentos.addElement(new Departamento(codigo,nomeDepartamento,new Colaborador(responsavel)))
          }
        dadosDepartamento.close()
      }
    catch (SQLException e) {
      e.printStackTrace()
    }
    return departamentos
  }

  Vector carregarNomesDepartamentos(Conexao conexao) throws Exception
  {
    ResultSet dadosDepartamento
      Vector departamentos = new Vector()
      dadosDepartamento = conexao.executarConsulta("select * from departamento order by departamento asc")
      departamentos.addElement("Selecione...")
      while(dadosDepartamento.next()) {
        departamentos.addElement(new Departamento(dadosDepartamento.getInt("codigo"),dadosDepartamento.getString("departamento")))
      }
    dadosDepartamento.close()
      return departamentos
  }

  void cadastrarDepartamento(String nomeDepartamento, Colaborador responsavel) throws Exception
  {
    String query = "insert into departamento (departamento, responsavel) values ('"+ nomeDepartamento +"',"+ ((responsavel == null)?"NULL":"'" + responsavel.obterMatricula() + "'") +")"
      Conexao conexao = new Conexao('T')
      if (conexao.abrirConexao()) {
        conexao.executarAtualizacao(query)
          conexao.fecharConexao()
      }
      else
      {
        Exception e = new Exception("Não foi possível cadastrar o Departamento Informado.")
          throw e
      }
  }

  void alterarDepartamento(String nomeDepartamento, Colaborador responsavel) throws Exception
  {
    String query = "update departamento set departamento = '"+ nomeDepartamento +"', responsavel = "+ ((responsavel == null)?"NULL":"'" + responsavel.obterMatricula() +"'") + " where codigo = " + this.codigo
      Conexao conexao = new Conexao('T')
      if (conexao.abrirConexao()) {
        conexao.executarAtualizacao(query)
          conexao.fecharConexao()
      }
      else
      {
        Exception e = new Exception("Não foi possível alterar o Departamento Informado.")
          throw e
      }
  }

  void excluirDepartamento() throws Exception
  {
    String query = "delete from departamento where codigo = " + this.codigo
      Conexao conexao = new Conexao('T')
      if (conexao.abrirConexao()) {
        conexao.executarAtualizacao(query)
          conexao.fecharConexao()
      }
      else
      {
        Exception e = new Exception("Não foi possível excluir o Departamento Informado.")
          throw e
      }
  }

  String toString() {
    return this.nomeDepartamento
  }
}
