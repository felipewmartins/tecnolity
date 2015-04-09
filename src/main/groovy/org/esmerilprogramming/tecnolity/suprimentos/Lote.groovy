package org.esmerilprogramming.tecnolity.suprimentos

import org.esmerilprogramming.tecnolity.util.*
import java.sql.*

class Lote
{
  static final char LOTE_BASICO = 'B'

    private int numero
    private Item item
    private Movimentacao movimentacao
    private Categoria localizacao
    private String dataValidade
    private float quantidade
    private boolean reservado
    private String descricao

    Lote() {}

  Lote(Item item) throws Exception
  {
    this.definirItem(item)
  }

  Lote(Item item, Movimentacao movimentacao, float quantidade) throws Exception
  {
    this.definirItem(item)
      this.definirMovimentacao(movimentacao)
      this.definirQuantidade(quantidade)
  }

  Lote(int numero, Item item, Movimentacao movimentacao, Categoria localizacao, String dataValidade, float quantidade, boolean reservado, String descricao) throws Exception
  {
    this.definirNumero(numero)
      this.definirItem(item)
      this.definirMovimentacao(movimentacao)
      this.definirLocalizacao(localizacao)
      this.definirDataValidade(dataValidade)
      this.definirQuantidade(quantidade)
      this.definirReservado(reservado)
      this.definirDescricao(descricao)
  }

  Lote(Item item, Movimentacao movimentacao, Categoria localizacao, String dataValidade, float quantidade, boolean reservado, String descricao) throws Exception
  {
    this.definirItem(item)
      this.definirMovimentacao(movimentacao)
      this.definirLocalizacao(localizacao)
      this.definirDataValidade(dataValidade)
      this.definirQuantidade(quantidade)
      this.definirReservado(reservado)
      this.definirDescricao(descricao)
  }

  void definirNumero(int numero) {
    this.numero = numero
  }

  void definirItem(Item item) {
    this.item = item
  }

  void definirMovimentacao(Movimentacao movimentacao) {
    this.movimentacao = movimentacao
  }

  void definirLocalizacao(Categoria localizacao) {
    this.localizacao = localizacao
  }

  void definirDataValidade(String dataValidade) throws Exception
  {
    String erro = ""
      if(!dataValidade.equals("")) {
        if(!Calendario.validarData(dataValidade, "/"))
          erro = "Data de Validade inválida."
      }

    if(!erro.equals("")) {
      Exception e = new Exception(erro)
        throw e
    }
    else
      this.dataValidade = dataValidade
  }

  void definirQuantidade(float quantidade) {
    this.quantidade = quantidade
  }

  void definirDescricao(String descricao) {
    this.descricao = descricao
  }

  void definirReservado(boolean reservado) {
    this.reservado = reservado
  }

  String obterLocalizacao() {
    return this.localizacao.obterNomeCategoria()
  }

  String obterDataValidade() {
    return this.dataValidade
  }

  void cadastrarLote() throws Exception
  {
    String query = "insert into lote (item, movimentacao_item, localizacao, data_validade, quantidade, reservado, descricao) values ("+ this.item.obterCodigo()

      if(this.movimentacao != null)
        query = query + ", "+ this.movimentacao.obterCodigo()
      else
        query = query + ", null"

          if(this.localizacao != null)
            query = query + ", '"+ this.localizacao.obterNomeCategoria() + "' "
          else
            query = query + ", null"

              if(this.dataValidade != null)
                query = query + ", '"+ Calendario.inverterFormato(this.dataValidade, "/") + "' "
              else
                query = query + ", null"

                  query = query + ", "+ this.quantidade +", "+ ((this.reservado)?1:0)

                  if(this.descricao != null)
                    query = query + ", '"+ this.descricao +"')"
                  else
                    query = query + ", null)"

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

  /**
   * Method retirarItem. Subtrai as quantidades dos lotes para atender a
   * quantidade de uma requisição interna. Se a quantidade solicitada for
   * maior que a quantidade disponível, a disponível é liberada e o item
   * continua pendente para ser atendido.
   * @param movimentacao Movimentação criada para o item.
   * @param itemRequisicaoInterna item, cuja quantidade será retirada do
   * estoque.
   * @throws Exception
   */
  static void retirarItem(Movimentacao movimentacao, ItemRequisicaoInterna itemRequisicaoInterna) throws Exception
  {
    String erro = ""
      Conexao conexao = new Conexao('T')
      float quantidadeSolicitada = itemRequisicaoInterna.obterQuantidadeItem()
      float quantSolicitadaAux = quantidadeSolicitada
      if(conexao.abrirConexao()) {
        // Consulta a quantidade de determinado item em lotes distribuídos no estoque.
        String query = "select isnull(sum(quantidade), -1) as quantidade_total from lote where item = " + itemRequisicaoInterna.obterItem().obterCodigo()
          ResultSet rsQuantidadeTotal = conexao.executarConsulta(query)
          float quantidadeTotal = 0.0f
          if(rsQuantidadeTotal.next())
            quantidadeTotal = rsQuantidadeTotal.getFloat("quantidade_total")
              // Continua se houver algum lote do item no estoque
              if(quantidadeTotal >= 0) {
                boolean quantidadeSuficiente = false
                  if(quantidadeSolicitada <= quantidadeTotal) {
                    quantidadeSuficiente = true
                  }
                query = "select quantidade, numero from lote where item = " + itemRequisicaoInterna.obterItem().obterCodigo() + " and quantidade > 0"
                  ResultSet rsLote = conexao.executarConsulta(query)
                  // Percorre os lotes existentes retirando os itens dos mesmos
                  // até que a quantidade necessária seja atendida.
                  while(rsLote.next() && quantidadeSolicitada > 0) {
                    float quantidadeLote = rsLote.getFloat("quantidade")
                      // Neste caso a solicitação foi atendida completamente.
                      if(quantidadeSolicitada <= quantidadeLote) {
                        query = "update lote set quantidade = (quantidade - "+ quantidadeSolicitada +") where numero = " + rsLote.getInt("numero")
                          quantidadeSolicitada = 0
                      }
                    // Neste caso a quantidade que havia no Lote não foi suficiente
                    // para atender a solicitação completamente, sendo necessário
                    // extrair itens de outros lotes.
                      else
                      {
                        query = "update lote set quantidade = 0 where numero = " + rsLote.getInt("numero")
                          quantidadeSolicitada -= quantidadeLote
                      }
                    conexao.executarAtualizacao(query)
                  }
                rsLote.close()
                  if(quantidadeSuficiente) {
                    movimentacao.cadastrarMovimentacao()
                      itemRequisicaoInterna.definirStatus(ItemRequisicaoInterna.STATUS_CONFIRMADO)
                      itemRequisicaoInterna.atualizarItemRequisicaoInterna()

                      if(!itemRequisicaoInterna.obterRequisicaoInterna().obterStatus().equals(RequisicaoInterna.STATUS_PENDENTE)) {
                        itemRequisicaoInterna.obterRequisicaoInterna().definirStatus(RequisicaoInterna.STATUS_EMITIDO)
                          itemRequisicaoInterna.obterRequisicaoInterna().atualizarRequisicaoInterna(RequisicaoInterna.STATUS_EMITIDO)
                      }
                  }
                  else
                  {
                    //A movimentação só é gerada se alguma quantidade tiver sido movimentada.
                    if((quantSolicitadaAux - quantidadeSolicitada) > 0) {
                      itemRequisicaoInterna.definirQuantidadeItem(quantSolicitadaAux - quantidadeSolicitada)
                        movimentacao.cadastrarMovimentacao()
                    }
                    itemRequisicaoInterna.definirStatus(ItemRequisicaoInterna.STATUS_PENDENTE)
                      itemRequisicaoInterna.definirQuantidadeItem(quantidadeSolicitada)
                      itemRequisicaoInterna.atualizarItemRequisicaoInterna()
                      if(!itemRequisicaoInterna.obterRequisicaoInterna().obterStatus().equals(RequisicaoInterna.STATUS_PENDENTE)) {
                        itemRequisicaoInterna.obterRequisicaoInterna().definirStatus(RequisicaoInterna.STATUS_PENDENTE)
                          itemRequisicaoInterna.obterRequisicaoInterna().atualizarRequisicaoInterna(RequisicaoInterna.STATUS_PENDENTE)
                      }
                  }
                rsQuantidadeTotal.close()
              }
              else
              {
                erro = "Não há nenhum lote registrado para o item selecionado."
              }
        conexao.fecharConexao()
      }
  }

  /** Exclui os lotes que não mais serão usados por estarem vazios. */
  static void excluirLotesVazios() throws Exception
  {
    Conexao conexao = new Conexao('T')
      if(conexao.abrirConexao()) {
        conexao.executarAtualizacao("delete from lote where quantidade <= 0")
          conexao.fecharConexao()
      }
  }
}
