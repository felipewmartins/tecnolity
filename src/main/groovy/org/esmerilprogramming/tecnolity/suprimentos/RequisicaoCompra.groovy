package org.esmerilprogramming.tecnolity.suprimentos

import org.esmerilprogramming.tecnolity.administracao.Colaborador
import org.esmerilprogramming.tecnolity.administracao.Departamento
import org.esmerilprogramming.tecnolity.logistica.Transportadora
import org.esmerilprogramming.tecnolity.pedidos.FormaPagamento
import org.esmerilprogramming.tecnolity.pedidos.Pedido
import org.esmerilprogramming.tecnolity.util.*

import java.sql.*

class RequisicaoCompra
{
  // tipos de status de requisi��o e dos itens da requisi��o
  static final String STATUS_EMITIDO       = 'EM' // Assim que a requisi��o � cadastrada
    static final String STATUS_CANCELADO     = 'CL' // Caso precise cancelar a requisi��o inteira
    static final String STATUS_CONCLUIDO     = 'CO' // Quando a requisi��o terminar de ser abastecida totalmente.
    static final String STATUS_PENDENTE      = 'PD' // Quando a requisi��o for abastecida parcialmente.

    // tipo de frete
    static final String CIF     = 'C'
    static final String FOB     = 'F'
    static final String PROPRIO = 'P'

    private int codigo
    private int ultimoCodigoRequisicao
    private Fornecedor fornecedor
    private String dataEmissao
    private Colaborador responsavelEmissao
    private Departamento departamento
    private String dataLimiteEntrega
    private String condicaoPagamento
    private FormaPagamento formaPagamento
    private Transportadora transportadora
    private String tipoFrete
    private String observacao
    private String status
    private Vector itensRequisicao
    private Vector pedidos

    private Conexao conexao

    RequisicaoCompra(){}

  RequisicaoCompra(int codigo) {
    this.definirCodigo(codigo)
  }

  RequisicaoCompra(int codigo, Conexao conexao) throws Exception
  {
    this.conexao = conexao
      this.definirCodigo(codigo)
      try {
        this.carregarRequisicaoCompra(conexao)
          this.carregarItensRequisicaoCompleto(conexao)
          this.carregarPedidosRequisicao(conexao)
      }
    catch (e) {
      e.printStackTrace()
    }
  }

  RequisicaoCompra(int codigo, Fornecedor fornecedor) throws Exception
  {
    this.definirCodigo(codigo)
      this.definirFornecedor(fornecedor)
  }

  RequisicaoCompra(int codigo, Fornecedor fornecedor, String dataEmissao, Departamento departamento, String dataLimiteEntrega, Transportadora transportadora, String tipoFrete) throws Exception
  {
    this.definirCodigo(codigo)
      this.definirFornecedor(fornecedor)
      this.definirDataEmissao(dataEmissao)
      this.definirDepartamento(departamento)
      this.definirDataLimiteEntrega(Calendario.ajustarFormatoDataBanco(dataLimiteEntrega))
      this.definirTransportadora(transportadora)
      this.definirTipoFrete(tipoFrete)
  }

  void definirFornecedor(Fornecedor fornecedor) throws Exception
  {
    if (fornecedor != null)
      this.fornecedor = fornecedor
    else
    {
      Exception e = new Exception('O Fornecedor n�o foi informado.')
        throw e
    }
  }

  void definirDataEmissao(String dataEmissao) throws Exception
  {
    String erro = ''
      if (dataEmissao.equals(''))
        erro = 'A Data de Emiss�o n�o foi informada.'
      else if (!Calendario.validarData(dataEmissao, '/'))
        erro = 'Data de Emiss�o inv�lida.'
          if (!erro.equals('')) {
            Exception e = new Exception(erro)
              throw e
          }
          else
            this.dataEmissao = dataEmissao
  }

  void definirDepartamento(Departamento departamento) throws Exception
  {
    if (departamento != null)
      this.departamento = departamento
    else
    {
      Exception e = new Exception('O Departamento Solicitante n�o foi informado.')
        throw e
    }
  }

  void definirResponsavelEmissao(Colaborador responsavelEmissao) throws Exception
  {
    if (responsavelEmissao != null)
      this.responsavelEmissao = responsavelEmissao
    else
    {
      Exception e = new Exception('O Respons�vel da Emiss�o n�o foi informado corretamente.')
        throw e
    }
  }

  void definirDataLimiteEntrega(String dataLimiteEntrega) throws Exception
  {
    String erro = ''
      if (dataLimiteEntrega.equals(''))
        erro = 'A Data de Limite de Entrega n�o foi informada.'
      else if (!Calendario.validarData(dataLimiteEntrega, '/'))
        erro = 'Data de Limite de Entrega inv�lida.'

          if (!erro.equals('')) {
            Exception e = new Exception(erro)
              throw e
          }
          else
            this.dataLimiteEntrega = dataLimiteEntrega
  }

  void definirCondicaoPagamento(String condicaoPagamento) throws Exception
  {
    if (condicaoPagamento.length() <= 60)
      this.condicaoPagamento = condicaoPagamento
    else
    {
      Exception e = new Exception('A Condi��o de Pagamento n�o foi informada corretamente.')
        throw e
    }
  }

  void definirFormaPagamento(FormaPagamento formaPagamento) throws Exception
  {
    if (formaPagamento != null)
      this.formaPagamento = formaPagamento
    else
    {
      Exception e = new Exception('A Forma de Pagamento n�o foi informada corretamente.')
        throw e
    }
  }

  void definirTransportadora(Transportadora transportadora) throws Exception
  {
    if (transportadora != null)
      this.transportadora = transportadora
    else
      throw new Exception('A Transportadora n�o foi informada.')
  }

  void definirTipoFrete(String tipoFrete) throws Exception
  {
    if (!tipoFrete.equals(''))
      this.tipoFrete = tipoFrete
    else
    {
      Exception e = new Exception('O Tipo de Frete n�o foi informado.')
        throw e
    }
  }

  void definirObservacao(String observacao) throws Exception
  {
    if (observacao.length() <= 200)
      this.observacao = observacao
    else
    {
      Exception e = new Exception('A Observa��o n�o foi informada corretamente.')
        throw e
    }
  }

  void definirStatus(String status) throws Exception
  {
    if (!status.equals('') && status.length() <= 2)
      this.status = status
    else
    {
      Exception e = new Exception('O Status n�o foi informado corretamente.')
        throw e
    }
  }

  int obterCodigo() {
    return this.codigo
  }

  Fornecedor obterFornecedor() {
    return this.fornecedor
  }

  String obterDataEmissao() {
    return this.dataEmissao
  }

  Departamento obterDepartamento() {
    return this.departamento
  }

  Colaborador obterResponsavelEmissao() {
    return this.responsavelEmissao
  }

  String obterDataLimiteEntrega() {
    return this.dataLimiteEntrega
  }

  String obterCondicaoPagamento() {
    return this.condicaoPagamento
  }

  FormaPagamento obterFormaPagamento() {
    return this.formaPagamento
  }

  Transportadora obterTransportadora() {
    return this.transportadora
  }

  String obterTipoFrete() {
    return this.tipoFrete
  }

  String obterObservacao() {
    return this.observacao
  }

  String obterStatus() {
    return this.status
  }

  Vector obterItensRequisicao() {
    return this.itensRequisicao
  }

  void carregarRequisicaoCompra(Conexao conexao) throws Exception
  {
    ResultSet dadosRequisicaoCompra
      String query = 'select rc.codigo, rc.fornecedor as codigo_fornecedor, f.razao_social, rc.departamento_solicitante, rc.data_emissao, rc.responsavel_emissao, rc.data_despacho, rc.data_limite_entrega, rc.data_recebimento, rc.responsavel_recebimento, rc.data_pagamento, rc.condicao_pagamento, rc.forma_pagamento as sigla_forma_pagamento, fp.forma_pagamento, rc.transportadora, rc.tipo_frete, rc.valor_conhecimento, rc.numero_conhecimento, rc.observacao, rc.nota_fiscal, rc.status, rc.observacao_status '  +
      'from requisicao_compra rc, fornecedor f, forma_pagamento fp '  +
      'where rc.fornecedor = f.codigo and rc.forma_pagamento = fp.sigla and rc.codigo = '  +  this.codigo
      dadosRequisicaoCompra = conexao.executarConsulta(query)
      if (dadosRequisicaoCompra.next()) {
        try {
          this.definirFornecedor(new Fornecedor(dadosRequisicaoCompra.getInt('codigo_fornecedor'), dadosRequisicaoCompra.getString('razao_social')))
            this.definirDepartamento(new Departamento(dadosRequisicaoCompra.getInt('departamento_solicitante')))
            this.definirDataEmissao(dadosRequisicaoCompra.getString('data_emissao'))
            this.definirResponsavelEmissao(new Colaborador(dadosRequisicaoCompra.getString('responsavel_emissao')))
            this.definirDataLimiteEntrega(dadosRequisicaoCompra.getString('data_limite_entrega'))
            this.definirCondicaoPagamento(dadosRequisicaoCompra.getString('condicao_pagamento'))
            this.definirFormaPagamento(new FormaPagamento(dadosRequisicaoCompra.getString('sigla_forma_pagamento'), dadosRequisicaoCompra.getString('forma_pagamento')))
            this.definirTransportadora(new Transportadora(dadosRequisicaoCompra.getInt('transportadora'), this.conexao))
            this.definirTipoFrete(dadosRequisicaoCompra.getString('tipo_frete'))
            this.definirObservacao(dadosRequisicaoCompra.getString('observacao'))
            this.definirStatus(dadosRequisicaoCompra.getString('status'))
        }
        catch (e) {
          e.printStackTrace()
        }
      }
  }

  void carregarItensRequisicaoAbastecidos(Conexao conexao) throws Exception
  {
    itensRequisicao = new Vector()
      String query = 'select i.codigo as codigo_item, i.descricao as descricao_item, ir.requisicao_compra, ir.status, ir.quantidade, ir.quantidade_pendente, ir.quantidade_abastecida, ir.valor_item, i.percentual_ipi ' +
      'from item_requisicao ir, item i ' +
      'where ir.item = i.codigo and (ir.status = ' +  ItemRequisicao.ABASTECIDO_TOTALMENTE + ' or ir.status = ' + ItemRequisicao.ABASTECIDO_PARCIALMENTE + ') and requisicao_compra = ' + this.codigo +
      ' order by ir.item asc'
      try {
        ResultSet dadosItemRequisicao = conexao.executarConsulta(query)
          while (dadosItemRequisicao.next()) {
            try {
              itensRequisicao.addElement(new ItemRequisicao(new Item(dadosItemRequisicao.getInt('item'), dadosItemRequisicao.getString('descricao')), this,
                    dadosItemRequisicao.getString('status'),
                    dadosItemRequisicao.getFloat('quantidade'),
                    dadosItemRequisicao.getFloat('quantidade_pendente'),
                    dadosItemRequisicao.getFloat('quantidade_abastecida'),
                    dadosItemRequisicao.getFloat('valor_item'),
                    dadosItemRequisicao.getInt('percentual_ipi')))
            }
            catch (e) {
              e.printStackTrace()
            }
          }
        dadosItemRequisicao.close()
      }
    catch (SQLException e) {
      e.printStackTrace()
    }
  }

  void carregarItensRequisicao(Conexao conexao) throws Exception
  {
    itensRequisicao = new Vector()
      String query = 'select distinct i.codigo as codigo_item, i.descricao as descricao_item, ci.codigo as codigo_categoria, ci.categoria, fi.referencia_fornecedor, ir.status, ir.quantidade, ir.quantidade_pendente, ir.quantidade_abastecida, ir.valor_item, i.percentual_ipi ' +
      'from item_requisicao ir, item i, categoria_item ci, fornecedor f, fornecedor_item fi, requisicao_compra rc ' +
      'where ir.item = i.codigo and i.categoria = ci.codigo and ir.requisicao_compra = rc.codigo and rc.fornecedor = f.codigo and f.codigo = fi.fornecedor and fi.item = i.codigo and f.codigo = ' + this.obterFornecedor().obterCodigo() + ' and (ir.status = ' + ItemRequisicao.EMITIDO + ' or ir.status = ' + ItemRequisicao.ABASTECIDO_PARCIALMENTE + ') and ir.requisicao_compra = ' + this.codigo
      try {
        ResultSet dadosItemRequisicao = conexao.executarConsulta(query)
          Item item = null
          while (dadosItemRequisicao.next()) {
            try {
              item = new Item(dadosItemRequisicao.getInt('codigo_item'), dadosItemRequisicao.getString('descricao_item'), new Categoria(dadosItemRequisicao.getInt('codigo_categoria'), dadosItemRequisicao.getString('categoria')), new FornecedorItem(this.obterFornecedor(), dadosItemRequisicao.getString('referencia_fornecedor')))
                itensRequisicao.addElement(new ItemRequisicao(item, this,
                      dadosItemRequisicao.getString('status'),
                      dadosItemRequisicao.getFloat('quantidade'),
                      dadosItemRequisicao.getFloat('quantidade_pendente'),
                      dadosItemRequisicao.getFloat('quantidade_abastecida'),
                      dadosItemRequisicao.getFloat('valor_item'),
                      dadosItemRequisicao.getInt('percentual_ipi')))
            }
            catch (e) {
              e.printStackTrace()
            }
          }
        dadosItemRequisicao.close()
      }
    catch (SQLException e) {
      e.printStackTrace()
    }
  }

  void carregarItensRequisicaoCompleto(Conexao conexao) throws Exception
  {
    itensRequisicao = new Vector()
      String query = 'select distinct i.codigo as codigo_item, i.descricao as descricao_item, ci.codigo as codigo_categoria, ci.categoria, fi.referencia_fornecedor, ir.status, ir.quantidade, ir.quantidade_pendente, ir.quantidade_abastecida, ir.valor_item, i.percentual_ipi ' +
      'from item_requisicao ir, item i, categoria_item ci, fornecedor f, fornecedor_item fi, requisicao_compra rc ' +
      'where ir.item = i.codigo and i.categoria = ci.codigo and ir.requisicao_compra = rc.codigo and rc.fornecedor = f.codigo and f.codigo = fi.fornecedor and fi.item = i.codigo and f.codigo = ' +  this.obterFornecedor().obterCodigo() + ' and (ir.status = ' + ItemRequisicao.EMITIDO + ' or ir.status = ' + ItemRequisicao.ABASTECIDO_PARCIALMENTE + ' or ir.status = ' + ItemRequisicao.ABASTECIDO_TOTALMENTE + ' or ir.status = ' + ItemRequisicao.CANCELADO + ') and ir.requisicao_compra = ' + this.codigo
      try {
        ResultSet dadosItemRequisicao = conexao.executarConsulta(query)
          Item item = null
          while (dadosItemRequisicao.next()) {
            try {
              item = new Item(dadosItemRequisicao.getInt('codigo_item'), dadosItemRequisicao.getString('descricao_item'), new Categoria(dadosItemRequisicao.getInt('codigo_categoria'), dadosItemRequisicao.getString('categoria')), new FornecedorItem(this.obterFornecedor(), dadosItemRequisicao.getString('referencia_fornecedor')))
                itensRequisicao.addElement(new ItemRequisicao(item, this,
                      dadosItemRequisicao.getString('status'),
                      dadosItemRequisicao.getFloat('quantidade'),
                      dadosItemRequisicao.getFloat('quantidade_pendente'),
                      dadosItemRequisicao.getFloat('quantidade_abastecida'),
                      dadosItemRequisicao.getFloat('valor_item'),
                      dadosItemRequisicao.getInt('percentual_ipi')))
            }
            catch (e) {
              e.printStackTrace()
            }
          }
        dadosItemRequisicao.close()
      }
    catch (SQLException e) {
      e.printStackTrace()
    }
  }

  void carregarPedidosRequisicao(Conexao conexao) throws Exception
  {
    pedidos = new Vector()
      String query = 'select distinct prc.pedido, pc.ordem_compra '  +
      'from pedido_requisicao_compra prc, pedido_cliente pc '  +
      'where prc.pedido = pc.codigo and prc.requisicao_compra = '  +  this.codigo
      try {
        ResultSet rsPedidos = conexao.executarConsulta(query)
          while (rsPedidos.next()) {
            try {
              pedidos.addElement(new Pedido(rsPedidos.getInt('pedido'), rsPedidos.getString('ordem_compra')))
            }
            catch (e) {
              e.printStackTrace()
            }
          }
        rsPedidos.close()
      }
    catch (SQLException e) {
      e.printStackTrace()
    }
  }

  /**
   * Method carregarRequisicoesCompra Carrega todas as requisi��es de compra
   * emitidas ou pendentes.
   * @param conexao Conex�o ativa com o banco de dados.
   * @return Vector Requisi��es de compra obtidas do banco de dados.
   * @throws Exception Caso n�o seja poss�vel acessar o banco de dados para
   * obter as requisi��es de compra.
   */
  Vector carregarRequisicoesCompra(Conexao conexao) throws Exception
  {
    Vector requisicoesCompra = new Vector()
      requisicoesCompra.addElement(null)
      String query = 'select rc.codigo as codigo_requisicao, f.codigo as codigo_fornecedor, f.razao_social as fornecedor, rc.data_emissao, d. codigo as codigo_departamento, d.departamento, rc.data_limite_entrega, t.codigo as codigo_transportadora, t.transportadora, rc.tipo_frete from requisicao_compra rc, fornecedor f, departamento d, transportadora t where rc.fornecedor = f.codigo and rc.departamento_solicitante = d.codigo and rc.transportadora = t.codigo and (rc.status = ' +  STATUS_EMITIDO + ' or rc.status = ' + STATUS_PENDENTE + ') order by rc.codigo'
      ResultSet rsRequisicao = conexao.executarConsulta(query)
      while (rsRequisicao.next()) {
        requisicoesCompra.addElement(new RequisicaoCompra(rsRequisicao.getInt('codigo_requisicao'),
              new Fornecedor(rsRequisicao.getInt('codigo_fornecedor'), rsRequisicao.getString('fornecedor')),
              rsRequisicao.getString('data_emissao'),
              new Departamento(rsRequisicao.getInt('codigo_departamento'), rsRequisicao.getString('departamento')),
              rsRequisicao.getString('data_limite_entrega'),
              new Transportadora(rsRequisicao.getInt('codigo_transportadora'), rsRequisicao.getString('transportadora')),
              rsRequisicao.getString('tipo_frete')))
      }
    rsRequisicao.close()
      return requisicoesCompra
  }

  void cadastrarRequisicaoCompra() throws Exception
  {
    String query = ''
      Conexao conexao = new Conexao('T')
      if (conexao.abrirConexao()) {
        query = 'insert into requisicao_compra (fornecedor, departamento_solicitante, responsavel_emissao, data_limite_entrega, condicao_pagamento, forma_pagamento, transportadora, tipo_frete, observacao, status) values  (' +
          this.fornecedor.obterCodigo()  +  ', ' + this.departamento.obterCodigo() + ', ' + this.responsavelEmissao.obterMatricula() + ', ' + Calendario.inverterFormato(this.dataLimiteEntrega, '/') + ', ' + this.condicaoPagamento + ', ' + this.formaPagamento.obterSigla() + ', ' + this.transportadora.obterCodigo() + ', ' + this.tipoFrete + ', ' + this.observacao + ', ' + STATUS_EMITIDO + ') '
          conexao.executarAtualizacao(query)
          ResultSet codigoRequisicao = conexao.executarConsulta('select max(codigo) as codigo from requisicao_compra ')
          if (codigoRequisicao.next())
            definirCodigo(codigoRequisicao.getInt('codigo'))
              conexao.fecharConexao()
      }
      else
      {
        Exception e = new Exception('N�o foi poss�vel cadastrar a Requisi��o de Compras.')
          throw e
      }
  }

  void associarItens(Vector itensRequisicao, Vector pedidos) throws Exception
  {
    if (itensRequisicao != null) {
      this.itensRequisicao = itensRequisicao
        int numItens = this.itensRequisicao.size()
        Conexao conexao = new Conexao('T')
        String query = ''
        ItemRequisicao irAtual = null
        if (numItens > 0 && conexao.abrirConexao()) {
          for (int i = 0;i < numItens;i++) {
            irAtual = (ItemRequisicao)this.itensRequisicao.get(i)
              query = 'insert into item_requisicao (item, requisicao_compra, quantidade, quantidade_pendente, valor_item) values '
              query = query  +  '(' + irAtual.obterItem().obterCodigo() + ', ' + this.obterCodigo() + ', ' + irAtual.getQuantidadeItem() + ', ' + irAtual.getQuantidadeItem() + ', ' + irAtual.obterValorUnitario() + ') '
              conexao.executarAtualizacao(query)
              for (int j = 0;j < pedidos.size();j++) {
                query = 'insert into pedido_requisicao_compra (pedido, requisicao_compra, item_requisicao) values (' +  ((Pedido)pedidos.get(j)).obterCodigo() + ', ' + this.obterCodigo() + ', ' + irAtual.obterItem().obterCodigo() + ')'
                conexao.executarAtualizacao(query)
              }
          }
          conexao.fecharConexao()
        }
    }
    else
    {
      Exception e = new Exception('N�o havia itens para associar a requisi��o.')
        throw e
    }
  }

  void alterarRequisicaoCompra() {
    Conexao.instance.db.execute 'update requisicao_compra set fornecedor = ' +  this.fornecedor + ', departamento_solicitante = ' + this.departamento.obterCodigo() + ', responsavel_emissao = ' + this.responsavelEmissao + ', data_limite_entrega = ' + Calendario.inverterFormato(this.dataLimiteEntrega, '/') + ', condicao_pagamento = ' + this.condicaoPagamento + ', forma_pagamento = ' + this.formaPagamento + ', transportadora = ' + this.transportadora + ', tipo_frete = ' + this.tipoFrete + ', observacao = ' + this.observacao + ', status = ' + STATUS_EMITIDO + ' where codigo =  ' + this.codigo
  }

  void excluirRequisicaoCompra() {
    Conexao.instance.db.execute 'delete from requisicao_compra where codigo = ' + codigo
    Conexao.instance.db.execute 'delete from item_requisicao where requisicao_compra = ' + codigo
  }

  void cancelarRequisicaoCompra() {
    Conexao.instance.db.execute 'update requisicao_compra set status = ' + STATUS_CANCELADO + ' where codigo = ' + codigo
    Conexao.instance.db.execute 'delete from pedido_requisicao_compra where requisicao_compra = ' + codigo
  }

  void registrarStatusRequisicaoCompra() {
    Conexao.instance.db.execute 'update requisicao_compra set status = ' + status + ' where codigo = ' + codigo
  }

  void registrarQuantidadesAbastecidas() {
    ItemRequisicao itemRequisicao
    for (int i = 0;i < itensRequisicao.size();i++) {
      itemRequisicao = (ItemRequisicao)itensRequisicao.get(i)
      if (itemRequisicao.obterItem().obterLote() != null) {
        Conexao.instance.db.execute 'update item_requisicao set status = ' +  itemRequisicao.obterStatus() + ', quantidade_pendente = ' + (itemRequisicao.getQuantidadeItem() - itemRequisicao.getQuantidadeAbastecida()) + ', quantidade_abastecida = ' + itemRequisicao.getQuantidadeAbastecida() + ' where item = ' + itemRequisicao.obterItem().obterCodigo() + ' and requisicao_compra = ' + this.codigo
      }
    }
  }

}
