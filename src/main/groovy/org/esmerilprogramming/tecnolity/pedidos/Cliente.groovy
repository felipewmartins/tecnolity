package org.esmerilprogramming.tecnolity.pedidos

import java.sql.*

import org.esmerilprogramming.tecnolity.administracao.Estado
import org.esmerilprogramming.tecnolity.administracao.Pais
import org.esmerilprogramming.tecnolity.util.*

class Cliente {
  long codigo
  String razaoSocial
  String nomeFantasia
  String cnpj
  String inscricaoEstadual
  String logradouro
  String bairro
  String complemento
  String cidade
  Estado estado
  Pais pais
  String cep
  String telefone
  String fax
  String contatoComercial
  String contatoTecnico
  String email
  Vector locaisEntrega = new Vector()

  Cliente(long codigo) throws Exception
  {
    definirCodigo(codigo)
  }

  Cliente(String razaoSocial, String nomeFantasia, String cnpj, String inscricaoEstadual,
      String logradouro, String bairro, String complemento, String cidade,
      Estado estado, Pais pais, String cep, String telefone, String fax,
      String contatoComercial, String contatoTecnico, String email) throws Exception
  {
    this.definirRazaoSocial(razaoSocial)
      this.definirNomeFantasia(nomeFantasia)
      this.definirCnpj(cnpj)
      this.definirInscricaoEstadual(inscricaoEstadual)
      this.definirLogradouro(logradouro)
      this.definirBairro(bairro)
      this.definirComplemento(complemento)
      this.definirCidade(cidade)
      this.definirEstado(estado)
      this.definirPais(pais)
      this.definirCep(cep)
      this.definirTelefone(telefone)
      this.definirFax(fax)
      this.definirContatoComercial(contatoComercial)
      this.definirContatoTecnico(contatoTecnico)
      this.definirEmail(email)
  }

  Cliente(long codigo, String razaoSocial) throws Exception
  {
    this.definirCodigo(codigo)
      this.definirRazaoSocial(razaoSocial)
  }

  void definirCodigo(long codigo) throws Exception
  {
    if (codigo <= 0) {
      Exception e = new Exception('Código do Cliente inválido.')
        throw e
    }
    this.codigo = codigo
  }

  void definirRazaoSocial(String razaoSocial) throws Exception
  {
    if (razaoSocial.equals('') || razaoSocial == null) {
      Exception e = new Exception('A Razão Social não foi informada.')
        throw e
    }
    this.razaoSocial = razaoSocial.trim().toUpperCase()
  }

  /**
   *  Define o Nome Fantasia do cliente da fábrica.
   *  @param nomeFantasia nome fantasia do cliente.
   */
  void definirNomeFantasia(String nomeFantasia) {
    this.nomeFantasia = nomeFantasia.trim().toUpperCase()
  }

  /**
   *  Define o CNPJ do cliente da fábrica.
   *  @param cnpj números do CNPJ sem pontos e separadores.
   *  @throws Exception caso o CNPJ não tenha sido informado.
   */
  void definirCnpj(String cnpj) throws Exception
  {
    if (!cnpj.equals('') && cnpj.length() <= 15) {
      this.cnpj = cnpj.trim()
    }
    else
    {
      Exception e = new Exception('CNPJ inválido. Informe somente números.')
        throw e
    }
  }

  void definirInscricaoEstadual(String inscricaoEstadual) {
    this.inscricaoEstadual = inscricaoEstadual.trim()
  }

  void definirLogradouro(String logradouro) throws Exception
  {
    if (logradouro.equals('') || logradouro == null) {
      Exception e = new Exception('O Logradouro nao foi informado.')
        throw e
    }
    this.logradouro = logradouro.trim().toUpperCase()
  }

  void definirCidade(String cidade) throws Exception
  {
    if (cidade.equals('') || cidade == null) {
      Exception e = new Exception('A Cidade não foi informada.')
        throw e
    }
    this.cidade = cidade.trim().toUpperCase()
  }

  void definirEstado(Estado estado) {
    this.estado = estado
  }

  void definirPais(Pais pais) throws Exception
  {
    if (pais == null) {
      Exception e = new Exception('O País nao foi informado.')
        throw e
    }
    this.pais = pais
  }

  /**
   *  Define o CEP do endereço do cliente da fábrica.
   *  @param cep números do CEP sem pontos e separadores.
   *  @throws Exception caso o CEP tenha tamanho diferente de 8.
   */
  void definirCep(String cep) throws Exception
  {
    if (!cep.equals('')) {
      if (cep.length() == 8)
        this.cep = cep
      else
      {
        Exception e = new Exception('CEP inválido. Informe somente números.')
          throw e
      }
    }
  }

  void carregarCliente(Conexao conexao) throws Exception
  {
    if (codigo > 0) {
      ResultSet dadosCliente = conexao.executarConsulta('select * from cliente where codigo = '  +  this.codigo)
        if (dadosCliente.next()) {
          this.definirRazaoSocial(dadosCliente.getString('razao_social'))
            this.definirNomeFantasia(dadosCliente.getString('nome_fantasia'))
            this.definirCnpj(dadosCliente.getString('cnpj'))
            this.definirInscricaoEstadual(dadosCliente.getString('inscricao_estadual'))
            this.definirLogradouro(dadosCliente.getString('logradouro'))
            this.definirBairro(dadosCliente.getString('bairro'))
            this.definirComplemento(dadosCliente.getString('complemento'))
            this.definirCidade(dadosCliente.getString('cidade'))
            this.definirEstado(new Estado(dadosCliente.getString('estado')))
            this.definirPais(new Pais(dadosCliente.getString('pais')))
            this.definirCep(dadosCliente.getString('cep'))
            this.definirTelefone(dadosCliente.getString('telefone'))
            this.definirFax(dadosCliente.getString('fax'))
            this.definirContatoComercial(dadosCliente.getString('contato_comercial'))
            this.definirContatoTecnico(dadosCliente.getString('contato_tecnico'))
            this.definirEmail(dadosCliente.getString('email'))
            this.setLocaisEntrega(Cliente.carregarLocaisEntrega(conexao, this))
        }
        else
        {
          Exception e = new Exception('Não existe cliente cadastrado com o código informado.')
            throw e
        }
      dadosCliente.close()
    }
    else
    {
      Exception e = new Exception('O Código do Cliente não foi informado.')
        throw e
    }
  }

  Vector carregarClientes(Conexao conexao) throws Exception
  {
    ResultSet dadosCliente
      Vector clientes = new Vector()
      try {
        dadosCliente = conexao.executarConsulta('select codigo, razao_social from cliente order by razao_social asc')
          clientes.addElement(null)

          while (dadosCliente.next()) {
            clientes.addElement(new Cliente(dadosCliente.getLong('codigo'), dadosCliente.getString('razao_social')))
          }
        dadosCliente.close()
      }
    catch (SQLException e) {
      e.printStackTrace()
    }
    return clientes
  }

  static Vector carregarLocaisEntrega(Conexao conexao, Cliente cliente) throws Exception
  {
    ResultSet dadosLocalEntrega
      Vector locaisEntrega = new Vector()
      String query = 'select * from local_entrega where cliente = ' +  cliente.obterCodigo() + ' order by descricao_local asc'
      dadosLocalEntrega = conexao.executarConsulta(query)
      while (dadosLocalEntrega.next()) {
        locaisEntrega.addElement(new LocalEntrega(cliente,
              dadosLocalEntrega.getLong('codigo_local'),
              dadosLocalEntrega.getString('descricao_local'),
              dadosLocalEntrega.getString('logradouro'),
              dadosLocalEntrega.getString('complemento'),
              dadosLocalEntrega.getString('bairro'),
              dadosLocalEntrega.getString('cidade'),
              (new Estado(dadosLocalEntrega.getString('estado'))),
              dadosLocalEntrega.getString('cep'),
              dadosLocalEntrega.getString('telefone'),
              dadosLocalEntrega.getString('responsavel_recebimento')))
      }
    dadosLocalEntrega.close()

      return locaisEntrega
  }

  void cadastrarCliente(Conexao conexao) throws Exception
  {
    String query
      ResultSet dadosCliente = conexao.executarConsulta('select cnpj from cliente where cnpj = '' +  this.cnpj + ''')
      if (dadosCliente.next()) {
        Exception e = new Exception('Já existe um cliente com este CNPJ.')
          throw e
      }
    dadosCliente.close()
      query = 'insert into cliente (razao_social, nome_fantasia, cnpj, inscricao_estadual, logradouro, bairro, complemento, cidade, estado, pais, cep, telefone, fax, contato_comercial, contato_tecnico, email) values '  + 
      '('' +  this.razaoSocial + '', '' + this.nomeFantasia + '', '' + this.cnpj + '', '' + this.inscricaoEstadual + '', '' + this.logradouro + '', '' + this.bairro + '', '' + this.complemento + '', '' + this.cidade + '', '' + ((this.estado == null)?'':this.estado.getSigla()) + '', '' + this.pais.getSigla() + '', '' + this.cep + '', '' + this.telefone + '', '' + this.fax + '', '' + this.contatoComercial + '', '' + this.contatoTecnico + '', '' + this.email + '')'
      conexao.executarAtualizacao(query)
      LocalEntrega localEntrega
      for (int i = 0;i < locaisEntrega.size(); i++) {
        localEntrega = (LocalEntrega)locaisEntrega.get(i)
          localEntrega.addLocalEntrega(conexao)
      }
    dadosCliente = conexao.executarConsulta('select codigo from cliente where razao_social = '' +  this.razaoSocial + '' and cnpj = '' + this.cnpj + ''')
      if (dadosCliente.next()) {
        this.codigo = dadosCliente.getInt('codigo')
      }
  }

  void alterarCliente(Conexao conexao) throws Exception
  {
    String query
      query = 'update cliente set razao_social = '' +  this.razaoSocial + '', nome_fantasia = '' + this.nomeFantasia + '', cnpj = '' + this.cnpj + '', inscricao_estadual = '' + this.inscricaoEstadual + '', logradouro = '' + this.logradouro + '', bairro = '' + this.bairro + '', complemento = '' + this.complemento + '', cidade = '' + this.cidade + '', estado = '' + this.estado.getSigla() + '', pais = '' + this.pais.getSigla() + '', cep = '' + this.obterCep() + '', telefone = '' + this.telefone + '', fax = '' + this.fax + '', contato_comercial = '' + this.contatoComercial + '', contato_tecnico = '' + this.contatoTecnico + '', email = '' + this.email + '' where codigo = ' + this.codigo
      conexao.executarAtualizacao(query)
      LocalEntrega localEntrega
      for (int i = 0;i < locaisEntrega.size(); i++) {
        localEntrega = (LocalEntrega)locaisEntrega.get(i)
          localEntrega.addLocalEntrega(conexao)
      }
  }

  void excluirCliente(Conexao conexao) throws Exception
  {
    ResultSet dadosModelo = conexao.executarConsulta('select codigo from modelo where cliente = ' +  this.obterCodigo())
      while (dadosModelo.next()) {
        conexao.executarAtualizacao('delete from materia_prima where modelo ='  +  dadosModelo.getInt('codigo'))
      }
    dadosModelo.close()
      conexao.executarAtualizacao('delete from modelo where cliente = '  +  this.obterCodigo())
      conexao.executarAtualizacao('delete from local_entrega where cliente ='  +  this.obterCodigo())
      conexao.executarAtualizacao('delete from cliente where codigo = '  +  this.obterCodigo())
  }

}
