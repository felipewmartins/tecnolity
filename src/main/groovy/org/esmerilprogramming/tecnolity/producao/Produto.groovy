package org.esmerilprogramming.tecnolity.producao

import java.sql.*
import java.text.DecimalFormat


import org.esmerilprogramming.tecnolity.util.Calendario
import org.esmerilprogramming.tecnolity.pedidos.Cliente
import org.esmerilprogramming.tecnolity.suprimentos.*
import org.esmerilprogramming.tecnolity.util.*

class Produto {
  long codigo
  String referenciaCliente
  Componente componente
  String nomeModelo
  float valor
  String moeda
  float custoFabricacao
  Cliente cliente
  TipoProducao tipoProducao
  String especificacaoInserto
  String acabamento
  String lavagem
  String pintura
  char destino
  Vector materiasPrimas

  Produto(Conexao conexao, long codigo) throws Exception
  {
    definirCodigo(codigo)
      this.carregarProduto(conexao)
  }

  Produto(long codigo, String referenciaCliente, String nomeModelo) throws Exception
  {
    this.definirCodigo(codigo)
      this.definirReferenciaCliente(referenciaCliente)
      this.definirNomeModelo(nomeModelo)
  }

  Produto(long codigo, String referenciaCliente, String nomeModelo, float valor, String moeda) throws Exception
  {
    this.definirCodigo(codigo)
      this.definirReferenciaCliente(referenciaCliente)
      this.definirNomeModelo(nomeModelo)
      this.definirValor(valor)
      this.definirMoeda(moeda)
  }

  Produto(long codigo, String referenciaCliente, Componente componente, String nomeModelo,
      float valor, String moeda, float custoFabricacao, Cliente cliente, TipoProducao tipoProducao,
      String especificacaoInserto, String acabamento, String lavagem, String pintura,
      char destino) throws Exception
  {
    this.definirCodigo(codigo)
      this.definirReferenciaCliente(referenciaCliente)
      this.definirComponente(componente)
      this.definirNomeModelo(nomeModelo)
      this.definirValor(valor)
      this.definirMoeda(moeda)
      this.definirCustoFabricacao(custoFabricacao)
      this.definirCliente(cliente)
      this.definirTipoProducao(tipoProducao)
      this.definirEspecificacaoInserto(especificacaoInserto)
      this.definirAcabamento(acabamento)
      this.definirLavagem(lavagem)
      this.definirPintura(pintura)
      this.definirDestino(destino)
  }

  Produto(String referenciaCliente, Componente componente, String nomeModelo,
      float valor, String moeda, float custoFabricacao, Cliente cliente, TipoProducao tipoProducao,
      String especificacaoInserto, String acabamento, String lavagem, String pintura,
      char destino) throws Exception
  {
    this.definirReferenciaCliente(referenciaCliente)
      this.definirComponente(componente)
      this.definirNomeModelo(nomeModelo)
      this.definirValor(valor)
      this.definirMoeda(moeda)
      this.definirCustoFabricacao(custoFabricacao)
      this.definirCliente(cliente)
      this.definirTipoProducao(tipoProducao)
      this.definirEspecificacaoInserto(especificacaoInserto)
      this.definirAcabamento(acabamento)
      this.definirLavagem(lavagem)
      this.definirPintura(pintura)
      this.definirDestino(destino)
  }

  String getValor(String formato) {
    DecimalFormat df
      if(!formato.equals("")) {
        df = new DecimalFormat(formato)
      }
      else
      {
        df = new DecimalFormat("###.##")
      }
    return df.format(this.valor)
  }

  void definirCodigo(long codigo) throws Exception
  {
    if(codigo < 0) {
      Exception e = new Exception("Código do Produto inválido.")
        throw e
    }
    this.codigo = codigo
  }

  void definirReferenciaCliente(String referencia) throws Exception
  {
    if(referencia.equals("") || referencia == null) {
      Exception e = new Exception("A Referência do Cliente não foi informada.")
        throw e
    }
    this.referenciaCliente = referencia
  }

  void definirComponente(Componente componente) throws Exception
  {
    if(componente == null) {
      Exception e = new Exception("O Tipo de Componente não foi informado.")
        throw e
    }
    this.componente = componente
  }

  void definirNomeModelo(String nomeModelo) throws Exception
  {
    if(nomeModelo.equals("") || nomeModelo == null) {
      Exception e = new Exception("O Nome do Modelo não foi informado.")
        throw e
    }
    this.nomeModelo = nomeModelo
  }

  void definirCliente(Cliente cliente) throws Exception
  {
    if(cliente == null) {
      Exception e = new Exception("O Cliente não foi informado.")
        throw e
    }
    this.cliente = cliente
  }

  void definirTipoProducao(TipoProducao tipoProducao) throws Exception
  {
    if(tipoProducao == null) {
      Exception e = new Exception("O Tipo de Produção não foi informado.")
        throw e
    }
    this.tipoProducao = tipoProducao
  }

  void definirEspecificacaoInserto(String especificacaoInserto) {
    if(especificacaoInserto != null)
      this.especificacaoInserto = especificacaoInserto
    else
      this.especificacaoInserto = ""
  }

  void definirAcabamento(String acabamento) {
    if(acabamento != null)
      this.acabamento = acabamento
    else
      this.acabamento = ""
  }

  void definirLavagem(String lavagem) {
    if(lavagem != null)
      this.lavagem = lavagem
    else
      this.lavagem = ""
  }

  void definirPintura(String pintura) {
    if(pintura != null)
      this.pintura = pintura
    else
      this.pintura = ""
  }

  void cadastrarProduto() throws Exception
  {
    Conexao conexao = new Conexao('T')
      String query

      if(conexao.abrirConexao()) {
        query = "insert into modelo (referencia_cliente, componente, modelo, valor, custo_fabricacao, cliente, tipo_producao, especificacao_inserto, acabamentos, lavagem, pintura, exportacao) values "  + 
          "('" +  this.referenciaCliente + "', " + this.componente.obterCodigo() + ", '" + this.nomeModelo + "', " + this.valor + ", " + this.custoFabricacao + ", " + this.cliente.obterCodigo() + ", " + this.tipoProducao.obterCodigo() + ", '" + this.especificacaoInserto + "', '" + this.acabamento + "', '" + this.lavagem + "', '" + this.pintura + "', '" + this.destino + "')"
          ResultSet modelo = conexao.executarConsulta("select codigo from modelo where modelo = '" +  this.nomeModelo + "' and referencia_cliente = '" + this.referenciaCliente + "' and componente = " + this.componente.obterCodigo() + " and cliente = " + this.cliente.obterCodigo() + "")
          if(modelo.next()) {
            Exception e = new Exception("Já existe um produto cadastrado com esta referência")
              throw e
          }
        conexao.executarAtualizacao(query)
          modelo = conexao.executarConsulta("select codigo from modelo where referencia_cliente = '" +  this.referenciaCliente + "' and componente = " + this.componente.obterCodigo() + " and cliente = " + this.cliente.obterCodigo() + "")
          if(modelo.next())
            this.codigo = modelo.getInt("codigo")
              modelo.close()
              conexao.fecharConexao()
      }
  }

  void alterarProduto() throws Exception
  {
    Conexao conexao = new Conexao('T')
      String query

      if(conexao.abrirConexao()) {
        query = "update modelo set referencia_cliente = '" +  this.referenciaCliente + "', componente = " + this.componente.obterCodigo() + ", modelo = '" + this.nomeModelo + "', valor = " + this.valor + ", custo_fabricacao = " + this.custoFabricacao + ", cliente = " + this.cliente.obterCodigo() + ", tipo_producao = " + this.tipoProducao.obterCodigo() + ", especificacao_inserto = '" + this.especificacaoInserto + "', acabamentos = '" + this.acabamento + "', lavagem = '" + this.lavagem + "', pintura = '" + this.pintura + "', exportacao = '" + this.destino + "' " +
          "where codigo = "  +  this.obterCodigo()
          conexao.executarAtualizacao(query)
          conexao.fecharConexao()
      }
  }

  void excluirProduto(int codigoProduto) throws Exception
  {
    Conexao conexao = new Conexao('T')
      if(conexao.abrirConexao()) {
        if(codigoProduto > 0) {
          conexao.executarAtualizacao("delete from modelo where codigo = "  +  codigoProduto)
        }
        else
        {
          Exception e = new Exception("Selecione um Produto antes de continuar.")
            throw e
        }
        conexao.fecharConexao()
      }
  }

  void associarMateriasPrimas(Vector materiasPrimas) throws Exception
  {
    Conexao conexao = new Conexao('T')
      String query
      MateriaPrima materiaPrima
      boolean exclusao = false

      if(conexao.abrirConexao()) {
        /*  Exclui toda a matéria prima para evitar duplicação duranta a inserção.*/
        conexao.executarAtualizacao("delete from quantidade_materia_prima where produto = " +  this.obterCodigo())
          for(int i = 0;i < materiasPrimas.size();i++) {
            materiaPrima = (MateriaPrima)materiasPrimas.get(i)
              query = "insert into quantidade_materia_prima (item, referencia, numero_sola, quantidade, produto) "
              query  += "values (" + materiaPrima.obterItem().obterCodigo()
              query  += ", '" + materiaPrima.obterMatriz().obterReferencia() + "', "
              query  += materiaPrima.obterMatriz().obterNumeroSola()
              query  += ", " + materiaPrima.obterQuantidade() + ", "
              query  += this.obterCodigo() + ")"
              conexao.executarAtualizacao(query)
          }
        conexao.fecharConexao()
      }
  }

  static Vector carregarProdutos(Cliente cliente, Conexao conexao) {
    ResultSet dadosProduto
      Vector produtos = new Vector()
      try {
        dadosProduto = conexao.executarConsulta("select codigo, referencia_cliente, modelo, valor, moeda from modelo where cliente = " +  cliente.obterCodigo() + " order by modelo asc")
          produtos.addElement(null)

          while(dadosProduto.next()) {
            produtos.addElement(new Produto(dadosProduto.getLong("codigo"), dadosProduto.getString("referencia_cliente"), dadosProduto.getString("modelo"), dadosProduto.getFloat("valor"), dadosProduto.getString("moeda")))
          }
        dadosProduto.close()
      }
    catch (SQLException e) {
      e.printStackTrace()
    }
    catch (e){}
    return produtos
  }

  Vector carregarProdutos(Conexao conexao) {
    ResultSet dadosProduto
      Vector produtos = new Vector()
      try {
        dadosProduto = conexao.executarConsulta("select codigo, referencia_cliente, modelo, valor, moeda from modelo order by modelo asc")
          produtos.addElement(null)

          while(dadosProduto.next()) {
            produtos.addElement(new Produto(dadosProduto.getLong("codigo"), dadosProduto.getString("referencia_cliente"), dadosProduto.getString("modelo"), dadosProduto.getFloat("valor"), dadosProduto.getString("moeda")))
          }
        dadosProduto.close()
      }
    catch(SQLException e){}
    catch (e){}
    return produtos
  }

  void carregarProduto(Conexao conexao) throws Exception
  {
    String query = "select  m.codigo as codigo_modelo, m.referencia_cliente, c.codigo as codigo_componente, "  + 
      "c.componente, m.modelo, m.valor, m.custo_fabricacao, m.moeda, cl.codigo as codigo_cliente, "  + 
      "cl.razao_social, tp.codigo as codigo_tipo_producao, tp.tipo_producao, m.especificacao_inserto, "  + 
      "m.acabamentos, m.lavagem, m.pintura, m.exportacao "  + 
      "from modelo m, componente c, cliente cl, tipo_producao tp "  + 
      "where m.componente = c.codigo and m.cliente = cl.codigo and m.tipo_producao = tp.codigo and "  + 
      "m.codigo = "  +  this.codigo
      ResultSet dadosProduto = conexao.executarConsulta(query)
      if(dadosProduto.next()) {
        this.definirCodigo(dadosProduto.getLong("codigo_modelo"))
          this.definirReferenciaCliente(dadosProduto.getString("referencia_cliente"))
          this.definirComponente(new Componente(dadosProduto.getInt("codigo_componente"), dadosProduto.getString("componente")))
          this.definirNomeModelo(dadosProduto.getString("modelo"))
          this.definirValor(dadosProduto.getFloat("valor"))
          this.definirCustoFabricacao(dadosProduto.getFloat("custo_fabricacao"))
          this.definirMoeda(dadosProduto.getString("moeda"))
          this.definirCliente(new Cliente(dadosProduto.getLong("codigo_cliente"), dadosProduto.getString("razao_social")))
          this.definirTipoProducao(new TipoProducao(dadosProduto.getInt("codigo_tipo_producao"), dadosProduto.getString("tipo_producao")))
          this.definirEspecificacaoInserto(dadosProduto.getString("especificacao_inserto"))
          this.definirAcabamento(dadosProduto.getString("acabamentos"))
          this.definirLavagem(dadosProduto.getString("lavagem"))
          this.definirPintura(dadosProduto.getString("pintura"))
          this.definirDestino(dadosProduto.getString("exportacao").charAt(0))
      }
    dadosProduto.close()
      query = "select i.codigo as codigo_item, i.descricao as descricao_item, mm.referencia, mm.numero_sola, mm.quantidade as quantidade_matriz, mm.tempo_forma, mm.tempo_injecao, mm.dureza, mm.densidade, mm.peso, mm.volume, qmp.quantidade "  + 
      "from matriz_modelo mm, quantidade_materia_prima qmp, item i "  + 
      "where mm.referencia = qmp.referencia and mm.numero_sola = qmp.numero_sola and qmp.item = i.codigo and qmp.produto = "  +  this.codigo +
      " order by mm.numero_sola, i.descricao"
      ResultSet dadosMateriaPrima = conexao.executarConsulta(query)
      materiasPrimas = new Vector()
      while(dadosMateriaPrima.next()) {
        MateriaPrima materiaPrima = new MateriaPrima(new Item(dadosMateriaPrima.getInt("codigo_item"), dadosMateriaPrima.getString("descricao_item")),
            new Matriz(dadosMateriaPrima.getString("referencia"), dadosMateriaPrima.getInt("numero_sola"), dadosMateriaPrima.getInt("quantidade_matriz"), dadosMateriaPrima.getInt("tempo_forma"), dadosMateriaPrima.getFloat("tempo_injecao"), dadosMateriaPrima.getFloat("dureza"), dadosMateriaPrima.getFloat("densidade"), dadosMateriaPrima.getFloat("peso"), dadosMateriaPrima.getFloat("volume")),
            dadosMateriaPrima.getFloat("quantidade"))
          materiasPrimas.addElement(materiaPrima)
      }
    dadosMateriaPrima.close()
  }

  Object[][] carregarMateriasPrimas(Conexao conexao, Object[][] dados) throws Exception
  {
    MateriaPrima materiaPrima
      for(int i = 0;i < materiasPrimas.size();i++) {
        materiaPrima = (MateriaPrima)materiasPrimas.get(i)
          dados[i][0] = materiaPrima.obterMatriz().obterDescricao()
          dados[i][1] = materiaPrima.obterItem().obterDescricao()
          dados[i][2] = materiaPrima.obterQuantidade("###.#####")
      }
    return dados
  }

  String getDataUltimaAlteracaoValor(Conexao conexao) throws Exception
  {
    String dataUltimaAlteracao = ""
      if(conexao == null) {
        conexao = new Conexao('T')
          conexao.abrirConexao()
          ResultSet rsData = conexao.executarConsulta("select max(data_atualizacao) as data_atualizacao from historico_valor_modelo where modelo ="  +  this.codigo)
          if(rsData.next()) {
            dataUltimaAlteracao = (new Calendario(rsData.getTimestamp("data_atualizacao"))).getDataAtual()
          }
        conexao.fecharConexao()
      }
      else
      {
        ResultSet rsData = conexao.executarConsulta("select max(data_atualizacao) as data_atualizacao from historico_valor_modelo where modelo ="  +  this.codigo)
          if(rsData.next()) {
            dataUltimaAlteracao = (new Calendario(rsData.getTimestamp("data_atualizacao"))).getDataAtual()
          }
      }
    return dataUltimaAlteracao
  }
}
