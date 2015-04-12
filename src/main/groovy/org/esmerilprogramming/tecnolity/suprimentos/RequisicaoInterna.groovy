package org.esmerilprogramming.tecnolity.suprimentos

import org.esmerilprogramming.tecnolity.administracao.Colaborador
import org.esmerilprogramming.tecnolity.administracao.Departamento
import org.esmerilprogramming.tecnolity.pedidos.Pedido
import org.esmerilprogramming.tecnolity.util.*

import java.sql.*

class RequisicaoInterna
{
  static final String STATUS_EMITIDO    = "EM"
    static final String STATUS_CANCELADO  = "CL"
    static final String STATUS_CONFIRMADO = "CO"
    static final String STATUS_PENDENTE   = "PD"

    static final String REQUISICAO_CONSUMO           = "CS"
    static final String REQUISICAO_VENDAS            = "VD"
    static final String REQUISICAO_DESCARTE          = "DS"
    static final String REQUISICAO_DEVOLUCAO         = "DV"
    static final String REQUISICAO_DEVOLUCAO_EXTERNA = "DE"

    private int codigo
    private int ultimoCodigoRequisicao
    private String tipoSolicitacao
    private String dataLimiteEntrega
    private String data
    private String dataEmissao
    private Departamento departamento
    private Colaborador solicitante
    private Pedido pedidoCliente
    private String justificativa
    private Colaborador responsavelAutorizacao
    private String status
    private Vector itensRequisicaoInterna

    RequisicaoInterna() {}

  RequisicaoInterna(int codigo) {
    this.definirCodigo(codigo)
  }

  RequisicaoInterna(int codigo, Departamento departamento) throws Exception
  {
    this.definirCodigo(codigo)
      this.definirDepartamento(departamento)
  }

  RequisicaoInterna(int codigo, Conexao conexao) throws Exception
  {
    this.definirCodigo(codigo)
      this.carregarRequisicaoInterna(conexao)
  }

  RequisicaoInterna(int codigo, String dataLimiteEntrega, Departamento departamento, Colaborador solicitante, Pedido pedidoCliente, String justificativa, String status) throws Exception
  {
    this.definirCodigo(codigo)
      this.definirDataLimiteEntrega(dataLimiteEntrega)
      this.definirDepartamento(departamento)
      this.definirSolicitante(solicitante)
      this.definirPedidoCliente(pedidoCliente)
      this.definirJustificativa(justificativa)
      this.definirStatus(status)
  }

  RequisicaoInterna(int codigo, String dataEmissao, String dataLimiteEntrega, Departamento departamento, Colaborador solicitante, Pedido pedidoCliente, String justificativa, String status) throws Exception
  {
    this.definirCodigo(codigo)
      this.definirDataEmissao(dataEmissao)
      this.definirDataLimiteEntrega(dataLimiteEntrega)
      this.definirDepartamento(departamento)
      this.definirSolicitante(solicitante)
      this.definirPedidoCliente(pedidoCliente)
      this.definirJustificativa(justificativa)
      this.definirStatus(status)
  }

  void definirCodigo(int codigo) {
    this.codigo = codigo
  }

  void definirTipoSolicitacao(String tipoSolicitacao) throws Exception
  {
    if (!tipoSolicitacao.equals("") || tipoSolicitacao.length() <= 2)
      this.tipoSolicitacao = tipoSolicitacao
    else
    {
      Exception e = new Exception("O Tipo de Solicitação não foi informado.")
        throw e
    }
  }

  void definirDataEmissao(String dataEmissao) {
    this.dataEmissao = dataEmissao
  }

  void definirDataLimiteEntrega(String dataLimiteEntrega) throws Exception
  {
    String erro = ""
      if (!dataLimiteEntrega.equals("")) {
        if (Calendario.validarData(dataLimiteEntrega, "/")) {
          this.dataLimiteEntrega = dataLimiteEntrega
        }
        else
          erro = "Data de Limite de Entrega inválida."
      }
      else
        erro = "Data de Limite de Entrega não informada."

          if (!erro.equals("")) {
            Exception e = new Exception(erro)
              throw e
          }
  }

  void definirData(String data) throws Exception
  {
    String erro = ""
      if (data.equals(""))
        erro = "A Data não foi informada."
      else if (!Calendario.validarData(data, "/"))
        erro = "Data inválida."
          if (!erro.equals("")) {
            Exception e = new Exception(erro)
              throw e
          }
          else
            this.data = data
  }

  void definirDepartamento(Departamento departamento) throws Exception
  {
    if (departamento != null)
      this.departamento = departamento
    else
    {
      Exception e = new Exception("O Departamento não foi informado.")
        throw e
    }
  }

  void definirSolicitante(Colaborador solicitante) throws Exception
  {
    if (solicitante != null)
      this.solicitante = solicitante
    else
    {
      Exception e = new Exception("O Solicitante não foi informado corretamente.")
        throw e
    }
  }

  void definirPedidoCliente(Pedido pedidoCliente) {
    this.pedidoCliente = pedidoCliente
  }

  void definirJustificativa(String justificativa) throws Exception
  {
    if (justificativa.length() <= 200)
      this.justificativa = justificativa
    else
    {
      Exception e = new Exception("A Justificativa não foi informada corretamente.")
        throw e
    }
  }

  void definirResponsavelAutorizacao(Colaborador responsavelAutorizacao) throws Exception
  {
    if (responsavelAutorizacao != null)
      this.responsavelAutorizacao = responsavelAutorizacao
    else
    {
      Exception e = new Exception("O Responsável da Autorização não foi informado corretamente.")
        throw e
    }
  }

  void definirStatus(String status) throws Exception
  {
    if (!status.equals("") && status.length() <= 2)
      this.status = status
    else
    {
      Exception e = new Exception("O Status não foi informado corretamente.")
        throw e
    }
  }

  int obterCodigo() {
    return this.codigo
  }

  String obterTipoSolicitacao() {
    return this.tipoSolicitacao
  }

  String obterTipoSolicitacaoLiteral() {
    String tipo
      if (tipoSolicitacao.equals(REQUISICAO_CONSUMO))
        return "Consumo"
      else if (tipoSolicitacao.equals(REQUISICAO_VENDAS))
        return "Vendas"
      else if (tipoSolicitacao.equals(REQUISICAO_DESCARTE))
        return "Descarte"
      else if (tipoSolicitacao.equals(REQUISICAO_DEVOLUCAO))
        return "Devolução"
      else
        return "Devolução Externa"
  }

  String obterDataEmissao() {
    return this.dataEmissao
  }

  String obterDataLimiteEntrega() {
    return this.dataLimiteEntrega
  }

  String obterData() {
    return this.data
  }

  Departamento obterDepartamento() {
    return this.departamento
  }

  Colaborador obterSolicitante() {
    return this.solicitante
  }

  Pedido obterPedidoCliente() {
    return this.pedidoCliente
  }

  Vector obterPedidosCliente(Conexao conexao) throws Exception
  {
    Vector pedidosRequisicao = new Vector()
      String query = "select distinct p.codigo, p.ordem_compra "  + 
      "from pedido_cliente p, pedido_requisicao_interna pri "  + 
      "where p.codigo = pri.pedido and pri.requisicao_interna = "  +  this.codigo
      ResultSet rsPedidosRequisicao = conexao.executarConsulta(query)
      while (rsPedidosRequisicao.next()) {
        pedidosRequisicao.addElement(new Pedido(rsPedidosRequisicao.getInt("codigo"), rsPedidosRequisicao.getString("ordem_compra")))
      }
    rsPedidosRequisicao.close()
      return pedidosRequisicao
  }

  String obterJustificativa() {
    return this.justificativa
  }

  Colaborador obterResponsavelAutorizacao() {
    return this.responsavelAutorizacao
  }

  String obterStatus() {
    return this.status
  }

  String obterStatusLiteral() {
    if (this.status.equals(RequisicaoInterna.STATUS_EMITIDO))
      return "Emitido"
    else if (this.status.equals(RequisicaoInterna.STATUS_CANCELADO))
      return "Cancelado"
    else if (this.status.equals(RequisicaoInterna.STATUS_CONFIRMADO))
      return "Confirmado"
    else
      return "Pendente"
  }

  Vector obterItensRequisicaoInterna() {
    return itensRequisicaoInterna
  }

  void carregarRequisicaoInterna(Conexao conexao) throws Exception
  {
    ResultSet dadosRequisicaoInterna
      String query = "select ri.tipo_solicitacao, ri.datahora_limite_entrega, ri.datahora, d.codigo as codigo_departamento, d.departamento, u.usuario, u.nome_completo, ri.pedido_cliente, ri.justificativa, ri.responsavel_autorizacao, ri.status "  + 
      "from requisicao_interna ri, departamento d, usuario u "  + 
      "where ri.departamento = d.codigo and ri.solicitante = u.usuario and ri.codigo = "  +  this.codigo
      dadosRequisicaoInterna = conexao.executarConsulta(query)
      if (dadosRequisicaoInterna.next()) {
        this.definirTipoSolicitacao(dadosRequisicaoInterna.getString("tipo_solicitacao"))
          this.definirDataLimiteEntrega(dadosRequisicaoInterna.getString("datahora_limite_entrega"))
          this.definirData(dadosRequisicaoInterna.getString("datahora"))
          this.definirDepartamento(new Departamento(dadosRequisicaoInterna.getInt("codigo_departamento"), dadosRequisicaoInterna.getString("departamento")))
          this.definirSolicitante(new Colaborador(dadosRequisicaoInterna.getString("usuario"), dadosRequisicaoInterna.getString("nome_completo"), "-"))
          this.definirPedidoCliente(new Pedido(dadosRequisicaoInterna.getInt("pedido_cliente")))
          this.definirJustificativa(dadosRequisicaoInterna.getString("justificativa"))
          this.definirResponsavelAutorizacao(new Colaborador(dadosRequisicaoInterna.getString("responsavel_autorizacao")))
          this.definirStatus(dadosRequisicaoInterna.getString("status"))
      }
    carregarItensRequisicaoInternaCompleta(conexao)
  }

  /**
   * Method carregarRequisicoesInternas. Consulta no banco de dados as
   * requisições internas ainda pendentes de um determinado tipo.
   * @param conexao Conexao conexão aberta ao banco de dados para realização
   * da consulta.
   * @param tipoSolicitacao String tipo de solicitação interna.
   * @return Vector Lista de Requisições Internas encontradas no banco de dados.
   * @throws Exception
   */
  static Vector carregarRequisicoesInternas(Conexao conexao, String tipoSolicitacao) throws Exception
  {
    Vector requisicoesInternas = new Vector()
      ResultSet dadosRequisicaoInterna
      requisicoesInternas.addElement("Selecione...")
      String query = "select ri.codigo, ri.datahora, ri.datahora_limite_entrega, d.codigo as codigo_departamento, d.departamento, u.usuario, u.nome_completo, ri.pedido_cliente, pc.ordem_compra, ri.justificativa, ri.status from requisicao_interna ri, departamento d, usuario u, pedido_cliente pc where ri.departamento = d.codigo and ri.solicitante = u.usuario and ri.tipo_solicitacao = '" +  tipoSolicitacao + "' and (ri.status = '" + STATUS_EMITIDO + "' or ri.status = '" + STATUS_PENDENTE + "') and ri.pedido_cliente *= pc.codigo order by datahora_limite_entrega desc"
      dadosRequisicaoInterna = conexao.executarConsulta(query)
      while (dadosRequisicaoInterna.next()) {
        requisicoesInternas.addElement(new RequisicaoInterna(dadosRequisicaoInterna.getInt("codigo"),
              Calendario.ajustarFormatoDataBanco(dadosRequisicaoInterna.getString("datahora")),
              Calendario.ajustarFormatoDataBanco(dadosRequisicaoInterna.getString("datahora_limite_entrega")),
              new Departamento(dadosRequisicaoInterna.getInt("codigo_departamento"), dadosRequisicaoInterna.getString("departamento")),
              new Colaborador(dadosRequisicaoInterna.getString("usuario"), dadosRequisicaoInterna.getString("nome_completo"), ""),
              new Pedido(dadosRequisicaoInterna.getInt("pedido_cliente"), dadosRequisicaoInterna.getString("ordem_compra")),
              dadosRequisicaoInterna.getString("justificativa"),
              dadosRequisicaoInterna.getString("status")))
      }
    dadosRequisicaoInterna.close()
      return requisicoesInternas
  }

  Vector carregarItensRequisicaoInterna(Conexao conexao) throws Exception
  {
    itensRequisicaoInterna = new Vector()
      ResultSet rsItensRequisicaoInterna
      String query = "select i.codigo as codigo_item, i.descricao as descricao_item, iri.requisicao_interna, iri.quantidade, d.codigo as codigo_departamento, d.departamento, iri.status "  + 
      "from item_requisicao_interna iri, item i, departamento d "  + 
      "where i.codigo = iri.item and "  + 
      "iri.destino = d.codigo and "  + 
      "(iri.status = '" +  STATUS_EMITIDO + "' or iri.status = '" + STATUS_PENDENTE + "') and " +
      "iri.requisicao_interna = "  +  this.obterCodigo()
      rsItensRequisicaoInterna = conexao.executarConsulta(query)
      while (rsItensRequisicaoInterna.next()) {
        itensRequisicaoInterna.addElement(new ItemRequisicaoInterna(new Item(rsItensRequisicaoInterna.getInt("codigo_item"), rsItensRequisicaoInterna.getString("descricao_item")),
              this,
              rsItensRequisicaoInterna.getFloat("quantidade"),
              new Departamento(rsItensRequisicaoInterna.getInt("codigo_departamento"), rsItensRequisicaoInterna.getString("departamento")),
              rsItensRequisicaoInterna.getString("status")))
      }
    return itensRequisicaoInterna
  }

  /** Carrega todos os itens da requisição interna
   * */
  Vector carregarItensRequisicaoInternaCompleta(Conexao conexao) throws Exception
  {
    itensRequisicaoInterna = new Vector()
      ResultSet rsItensRequisicaoInterna
      String query = "select i.codigo as codigo_item, i.descricao as descricao_item, iri.requisicao_interna, iri.quantidade, d.codigo as codigo_departamento, d.departamento, iri.status "  + 
      "from item_requisicao_interna iri, item i, departamento d "  + 
      "where i.codigo = iri.item and "  + 
      "iri.destino = d.codigo and "  + 
      "(iri.status = '" +  STATUS_EMITIDO + "' or iri.status = '" + STATUS_PENDENTE + "' or iri.status = '" + STATUS_CONFIRMADO + "' or iri.status = '" + STATUS_CANCELADO + "') and " +
      "iri.requisicao_interna = "  +  this.obterCodigo()

      rsItensRequisicaoInterna = conexao.executarConsulta(query)
      while (rsItensRequisicaoInterna.next()) {
        itensRequisicaoInterna.addElement(new ItemRequisicaoInterna(new Item(rsItensRequisicaoInterna.getInt("codigo_item"), rsItensRequisicaoInterna.getString("descricao_item")),
              this,
              rsItensRequisicaoInterna.getFloat("quantidade"),
              new Departamento(rsItensRequisicaoInterna.getInt("codigo_departamento"), rsItensRequisicaoInterna.getString("departamento")),
              rsItensRequisicaoInterna.getString("status")))
      }
    return itensRequisicaoInterna
  }

  void cadastrarRequisicaoInterna() throws Exception
  {
    String query = "insert into requisicao_interna (tipo_solicitacao, datahora_limite_entrega, departamento, solicitante, pedido_cliente, justificativa, status) values ('" +  this.tipoSolicitacao + "', "
      if (!this.dataLimiteEntrega.equals(""))
        query += "'" + Calendario.inverterFormato(this.dataLimiteEntrega, "/") + "', "
      else
        query += "'', "
          query += this.departamento.obterCodigo() + ", '" + this.solicitante.obterMatricula() + "', "
          if (this.pedidoCliente != null)
            query += this.pedidoCliente.obterCodigo() + ", "
          else
            query += "null, "
              query += "'" + this.justificativa + "', '" + STATUS_EMITIDO + "') "

              Conexao conexao = new Conexao('T')
              if (conexao.abrirConexao()) {
                conexao.executarAtualizacao(query)
                  ResultSet codigoRequisicao = conexao.executarConsulta("select max(codigo) as codigo from requisicao_interna where solicitante = '" +  this.solicitante.obterMatricula() + "'")
                  if (codigoRequisicao.next())
                    definirCodigo(codigoRequisicao.getInt("codigo"))
                      conexao.fecharConexao()
              }
              else
              {
                Exception e = new Exception("Não foi possível realizar uma conexão com o banco de dados.")
                  throw e
              }
  }

  void associarItens(Vector itensRequisicaoInterna, Vector pedidos) throws Exception
  {
    if (itensRequisicaoInterna != null) {
      this.itensRequisicaoInterna = itensRequisicaoInterna
        int numItens = this.itensRequisicaoInterna.size()
        Conexao conexao = new Conexao('T')
        String query = ""
        ItemRequisicaoInterna irAtual = null
        if (numItens > 0 && conexao.abrirConexao()) {
          for (int i = 0;i < itensRequisicaoInterna.size();i++) {
            irAtual = (ItemRequisicaoInterna)this.itensRequisicaoInterna.get(i)
              ((ItemRequisicaoInterna)itensRequisicaoInterna.get(i)).registrarItemRequisicaoInterna()
              for (int j = 0;j < pedidos.size();j++) {
                query = "insert into pedido_requisicao_interna (pedido, item, requisicao_interna) values (" +  ((Pedido)pedidos.get(j)).obterCodigo() + ", " + irAtual.obterItem().obterCodigo() + ", " + this.obterCodigo() + ")"
                  conexao.executarAtualizacao(query)
              }
          }
          conexao.fecharConexao()
        }
    }
  }

  void cancelarRequisicaoInterna() throws Exception
  {
    String queryCancelarRequisicaoInterna = "update requisicao_interna set status = 'CL' where codigo = " +  this.codigo + " "
      Conexao conexao = new Conexao('T')
      if (conexao.abrirConexao()) {
        conexao.executarAtualizacao(queryCancelarRequisicaoInterna)
          conexao.fecharConexao()
      }
      else
      {
        Exception e = new Exception("Não foi possível realizar uma conexão com o banco de dados.")
          throw e
      }
  }

  void mudarStatusRequisicaoInterna(String status) throws Exception
  {
    String queryCancelarRequisicaoInterna = "update requisicao_interna set status = '" +  status + "' where codigo = " + this.codigo + " "
      Conexao conexao = new Conexao('T')
      if (conexao.abrirConexao()) {
        conexao.executarAtualizacao(queryCancelarRequisicaoInterna)
          conexao.fecharConexao()
      }
      else
      {
        Exception e = new Exception("Não foi possível realizar uma conexão com o banco de dados.")
          throw e
      }
  }

  void adicionarItem(Item item) {
    this.itensRequisicaoInterna.addElement(item)
  }

  /**
   * Method atualizarRequisicaoInterna. Atualiza a requisição interna com informações
   * provenientes da movimentação.
   * @param status Status para o qual se quer alterar o item da requisição interna.
   * @throws Exception Em caso de erros com a transação com o banco de dados.
   */
  void atualizarRequisicaoInterna(String status) throws Exception
  {
    Conexao conexao = new Conexao('T')
      this.status = status

      // Se o status for CONFIRMADO informações adicionais são inseridas na requisição interna.
      if (this.status.equals(STATUS_CONFIRMADO)) {
        Calendario calendario = new Calendario()
          if (conexao.abrirConexao()) {
            String query = "update requisicao_interna set data_entrega = '" +  calendario.dataHoje("MM/dd/yyyy HH:mm:ss") + "', status = '" + STATUS_CONFIRMADO + "' where codigo = " + this.obterCodigo()
              conexao.executarAtualizacao(query)
              conexao.fecharConexao()
          }
          else
          {
            Exception e = new Exception("Não foi possível realizar uma conexão com o banco de dados.")
              throw e
          }
      }
      else
      {
        if (conexao.abrirConexao()) {
          String query = "update requisicao_interna set status = '" +  this.status + "' where codigo = " + this.obterCodigo()
            conexao.executarAtualizacao(query)
            conexao.fecharConexao()
        }
        else
        {
          Exception e = new Exception("Não foi possível realizar uma conexão com o banco de dados.")
            throw e
        }
      }
    conexao = null
  }

  String toString() {
    return ""  +  this.codigo + " - " + this.departamento.obterNomeDepartamento()
  }
}
