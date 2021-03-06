package org.esmerilprogramming.tecnolity.pedidos

import java.sql.*

import org.esmerilprogramming.tecnolity.administracao.Estado
import org.esmerilprogramming.tecnolity.util.Conexao

class LocalEntrega {
  long codigo
  Cliente cliente
  String descricaoLocal
  String logradouro
  String complemento
  String bairro
  String cidade
  Estado estado
  String cep
  String telefone
  String responsavelRecebimento
  boolean novoLocalEntrega // Quando um novo local de entrega for informado para inclus�o.
  boolean invalido // Quando o local de entrega for selecionado para exclus�o.

  LocalEntrega() {
    setNovoLocalEntrega(true)
  }

  LocalEntrega(long codigo, Cliente cliente, String descricaoLocal) throws Exception {
    this.codigo = codigo
    this.cliente = cliente
    this.descricaoLocal = descricaoLocal
    this.novoLocalEntrega = false
  }

  LocalEntrega(Cliente cliente, String descricaoLocal, String logradouro, String complemento,
      String bairro, String cidade, Estado estado, String cep, String telefone,
      String responsavelRecebimento) throws Exception
  {
    definirCliente(cliente)
    definirDescricaoLocal(descricaoLocal)
    definirLogradouro(logradouro)
    definirComplemento(complemento)
    definirBairro(bairro)
    definirCidade(cidade)
    definirEstado(estado)
    definirCep(cep)
    definirTelefone(telefone)
    definirResponsavelRecebimento(responsavelRecebimento)
    setNovoLocalEntrega(true)
  }

  LocalEntrega(Cliente cliente, long codigo, String descricaoLocal, String logradouro, String complemento,
      String bairro, String cidade, Estado estado, String cep, String telefone,
      String responsavelRecebimento) throws Exception
  {
    definirCliente(cliente)
    definirCodigo(codigo)
    definirDescricaoLocal(descricaoLocal)
    definirLogradouro(logradouro)
    definirComplemento(complemento)
    definirBairro(bairro)
    definirCidade(cidade)
    definirEstado(estado)
    definirCep(cep)
    definirTelefone(telefone)
    definirResponsavelRecebimento(responsavelRecebimento)
    setNovoLocalEntrega(false)
  }

  void definirCodigo(long codigo) throws Exception {
    if (codigo <= 0) {
      Exception e = new Exception('C�digo do Local de Entrega inv�lido.')
      throw e
    }
    this.codigo = codigo
    setNovoLocalEntrega(false)
  }

  void definirCliente(Cliente cliente) throws Exception
  {
    if (cliente == null) {
      Exception e = new Exception('O Cliente n�o foi informado.')
        throw e
    }
    this.cliente = cliente
  }

  void definirDescricaoLocal(String descricaoLocal) throws Exception
  {
    if (descricaoLocal.equals('') || descricaoLocal == null) {
      Exception e = new Exception('A Descri��o do Local n�o foi informada.')
        throw e
    }
    this.descricaoLocal = descricaoLocal
  }

  void definirLogradouro(String logradouro) throws Exception
  {
    if (!logradouro.equals('')) {
      this.logradouro = logradouro
    }
    else
    {
      Exception e = new Exception('O Logradouro n�o foi informado.')
        throw e
    }
  }

  void definirCidade(String cidade) throws Exception
  {
    if (!cidade.equals('')) {
      this.cidade = cidade
    }
    else
    {
      Exception e = new Exception('A Cidade n�o foi informada.')
        throw e
    }
  }

  void definirEstado(Estado estado) throws Exception
  {
    if (estado != null) {
      this.estado = estado
        this.estado.carregarEstado()
    }
    else
    {
      Exception e = new Exception('O Estado n�o foi informado.')
        throw e
    }
  }

  void addLocalEntrega(Conexao conexao) throws Exception
  {
    String query = ''
      if (isNovoLocalEntrega() && !isInvalido()) {
        query = 'insert into local_entrega (cliente, descricao_local, logradouro, complemento, bairro, cidade, estado, cep, telefone, responsavel_recebimento) '  + 
          'values (' +  this.obterCliente().obterCodigo() + ', ' + this.obterDescricaoLocal() + ', ' + this.obterLogradouro() + ', ' + this.obterComplemento() + ', ' + this.obterBairro() + ', ' + this.obterCidade() + ', ' + this.obterEstado().getSigla() + ', ' + this.obterCep() + ', ' + this.obterTelefone() + ', ' + this.obterResponsavelRecebimento() + ')'
          conexao.executarAtualizacao(query)
          return
      }
      else if (isInvalido()) {
        deleteLocalEntrega(conexao)
      }
  }

  void deleteLocalEntrega(Conexao conexao) throws Exception
  {
    ResultSet rsLocalEntrega = conexao.executarConsulta('select * from pedido_cliente where local_entrega = '  +  this.codigo)
    if (rsLocalEntrega.next()) {
      Exception e = new Exception('N�o foi poss�vel excluir o local de entrega por estar associado a um pedido cadastrado.')
      throw e
    }
    else
        Conexao.instance.db.execute 'delete from local_entrega where codigo_local = ' + codigo
  }

  String toString() {
    descricaoLocal
  }

}
