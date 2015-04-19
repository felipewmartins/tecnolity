package org.esmerilprogramming.tecnolity.suprimentos

import java.sql.*

import org.esmerilprogramming.tecnolity.administracao.Estado
import org.esmerilprogramming.tecnolity.administracao.Pais
import org.esmerilprogramming.tecnolity.util.*

class Fornecedor {
  int codigo
  String razaoSocial
  String cnpj
  int percentualICMS
  String logradouro
  String complemento
  String bairro
  String cidade
  Estado estado
  Pais pais
  String cep
  String ddd
  String telefone
  String ramal
  String fax
  String email
  String website

    Fornecedor(int codigo, String razaoSocial, String cnpj,
        int percentualICMS, String logradouro, String complemento,
        String bairro, String cidade, Estado estado,
        Pais pais, String cep, String ddd, String telefone,
        String ramal, String fax, String email, String website) {
      this.codigo = codigo
        this.razaoSocial = razaoSocial
        this.cnpj = cnpj
        this.percentualICMS = percentualICMS
        this.logradouro = logradouro
        this.complemento = complemento
        this.bairro = bairro
        this.cidade = cidade
        this.estado = estado
        this.pais = pais
        this.cep = cep
        this.ddd = ddd
        this.telefone = telefone
        this.ramal = ramal
        this.fax = fax
        this.email = email
        this.website = website
    }

  Fornecedor(String razaoSocial, String cnpj,
      int percentualICMS, String logradouro, String complemento,
      String bairro, String cidade, Estado estado,
      Pais pais, String cep, String ddd, String telefone,
      String ramal, String fax, String email, String website) {
    this.razaoSocial = razaoSocial
      this.cnpj = cnpj
      this.percentualICMS = percentualICMS
      this.logradouro = logradouro
      this.complemento = complemento
      this.bairro = bairro
      this.cidade = cidade
      this.estado = estado
      this.pais = pais
      this.cep = cep
      this.ddd = ddd
      this.telefone = telefone
      this.ramal = ramal
      this.fax = fax
      this.email = email
      this.website = website
  }

  Fornecedor(int codigo, String razaoSocial, String cnpj) {
    this.codigo = codigo
      this.razaoSocial = razaoSocial
      this.cnpj = cnpj
  }

  Fornecedor(int codigo, String razaoSocial) {
    this.codigo = codigo
    this.razaoSocial = razaoSocial
  }

  Fornecedor(int codigo) {
    this.codigo = codigo
    carregarFornecedor()
  }

  private void carregarFornecedor() {
    def query = 'select * from fornecedor where codigo = ' + this.codigo
      try {
        ResultSet resultado = conexao.executarConsulta(query)
          if (resultado.next()) {
            this.razaoSocial = resultado.getString('razao_social')
              this.cnpj = resultado.getString('cnpj')
              this.percentualICMS = resultado.getInt('percentual_icms')
              this.logradouro = resultado.getString('logradouro')
              this.complemento = resultado.getString('complemento')
              this.bairro = resultado.getString('bairro')
              this.cidade = resultado.getString('cidade')
              this.estado = new Estado(resultado.getString('estado'))
                this.pais = new Pais(resultado.getString('pais'))
            this.cep = resultado.getString('cep')
              this.ddd = resultado.getString('ddd')
              this.telefone = resultado.getString('telefone')
              this.ramal = resultado.getString('ramal')
              this.fax = resultado.getString('fax')
              this.email = resultado.getString('email')
              this.website = resultado.getString('website')
          }
  }

  static Vector carregarFornecedores(Conexao conexao) throws Exception
  {
    ResultSet dadosFornecedor
      Vector fornecedores = new Vector()
      dadosFornecedor = conexao.executarConsulta('select codigo, razao_social, cnpj from fornecedor order by razao_social asc')
      fornecedores.addElement(null)
      while (dadosFornecedor.next()) {
        fornecedores.addElement(new Fornecedor(dadosFornecedor.getInt('codigo'), dadosFornecedor.getString('razao_social'), dadosFornecedor.getString('cnpj')))
      }
    dadosFornecedor.close()
      return fornecedores
  }

  void cadastrarFornecedor() throws Exception
  {
    String query = 'insert into fornecedor (razao_social, cnpj, percentual_icms, logradouro, complemento, bairro, cidade, estado, pais, cep, ddd, telefone, ramal, fax, email, website) values '
      query = query  +  '(' + razaoSocial + ', ' + cnpj + ', '+ percentualICMS + ', ' + logradouro + ', ' + complemento + ', ' + bairro + ', ' + cidade + ', ' + estado.getSigla() + ', ' + pais.getSigla() + ', ' + cep + ', ' + ddd + ', ' + telefone + ', ' + ramal + ', ' + fax + ', ' + email + ', ' + website + ')'
      Conexao conexao = new Conexao('T')
      if (conexao.abrirConexao()) {
        ResultSet dadosFornecedor = conexao.executarConsulta('select cnpj from fornecedor where cnpj = ' +  this.cnpj)
          if (dadosFornecedor.next()) {
            Exception e = new Exception('Já existe um Fornecedor com o CNPJ informado.')
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

  void alterarFornecedor() {
    String query = 'update fornecedor set razao_social = ' +  razaoSocial + ', cnpj = ' + cnpj + ', percentual_icms = ' + percentualICMS + ', logradouro = ' + logradouro + ', complemento = ' + complemento + ', bairro = ' + bairro + ', cidade = ' + cidade + ', estado = ' + estado.getSigla() + ', pais = ' + pais.getSigla() + ', cep = ' + cep + ', ddd = ' + ddd + ', telefone = ' + telefone + ', ramal = ' + ramal + ', fax = ' + fax + ', email = ' + email + ', website = ' + website
    query = query  +  ' where codigo = ' + codigo
    Conexao.instance.db.execute query
  }

  void excluirFornecedor(int codigo) throws Exception
  {
    String query = 'delete from fornecedor where codigo = ' +  codigo + ' '
      Conexao conexao = new Conexao('T')
      boolean existente = false

      if (conexao.abrirConexao()) {
        ResultSet fornecedor = conexao.executarConsulta('select * from fornecedor_item where fornecedor = ' +  codigo + ' ')
          if (fornecedor.next()) {
            existente = true
              Exception e = new Exception('Existe um item associado a este fornecedor. Não foi possível realizar a exclusão.')
              throw e
          }
          else
          {
            conexao.executarAtualizacao(query)
              conexao.fecharConexao()
          }
      }
      else
      {
        Exception e = new Exception('Não foi possível realizar uma conexão com o banco de dados.')
          throw e
      }
  }

  /**
   * Method possuiApenasItensIndependentes.
   * @param conexao Conexão ativa com o banco de dados.
   * @return boolean Verdadeiro se fornecedor possui apenas itens independentes,
   * Falso se o fornecedor possui pelo menos 1 item dependente de pedido.
   * @throws SQLException Problemas de acesso ao banco de dados.
   */
  boolean possuiApenasItensIndependentes(Conexao conexao) throws Exception
  {
    String query = 'select count(i.independente) '  +
      'from fornecedor f, item i, fornecedor_item fi ' +
      'where f.codigo = fi.fornecedor and i.codigo = fi.item and f.codigo = ' +  this.obterCodigo() + ' and i.independente = 0 '
      ResultSet rsItensIndependentes = conexao.executarConsulta(query)
      if (rsItensIndependentes.next()) {
        if (rsItensIndependentes.getInt(1) > 0)
          return false // possui algum item dependente de pedido.
      }
    return true // não possui nenhum item dependente de pedido.
  }

  String toString() {
    return this.obterRazaoSocial()
  }
}
