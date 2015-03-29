package org.esmerilprogramming.tecnolity.suprimentos


class ItemRequisicao
{
  // status do item da requisição interna.
  static final String EMITIDO                 = "EM" // Quando a requisição de compra é emitida.
    static final String ABASTECIDO_TOTALMENTE   = "AT" // Quando a quantidade requisitada do item entra de uma vez no estoque.
    static final String ABASTECIDO_PARCIALMENTE = "AP" // Quando a quantidade requisitada do item entra em parte no estoque.
    static final String CANCELADO               = "CL" // Quando a requisição de compra é cancelada.

    private Item item
    private float quantidadeItem, quantidadePendente, quantidadeAbastecida, valorUnitario
    private int percentualIPI
    private RequisicaoCompra requisicaoCompra
    private String status

    ItemRequisicao(Item item, RequisicaoCompra requisicaoCompra, String status, float quantidadeItem, float quantidadePendente, float quantidadeAbastecida, float valorUnitario, int percentualIPI) {
      this.definirItem(item)
        this.definirRequisicaoCompra(requisicaoCompra)
        this.definirStatus(status)
        this.setQuantidadeItem(quantidadeItem)
        this.setQuantidadePendente(quantidadePendente)
        this.setQuantidadeAbastecida(quantidadeAbastecida)
        this.definirValorUnitario(valorUnitario)
        this.definirPercentualIPI(percentualIPI)
    }

  ItemRequisicao(Item item, RequisicaoCompra requisicaoCompra, float quantidadeItem, float quantidadePendente, float quantidadeAbastecida, float valorUnitario, int percentualIPI) {
    this.definirItem(item)
      this.definirRequisicaoCompra(requisicaoCompra)
      this.setQuantidadeItem(quantidadeItem)
      this.setQuantidadePendente(quantidadePendente)
      this.setQuantidadeAbastecida(quantidadeAbastecida)
      this.definirValorUnitario(valorUnitario)
      this.definirPercentualIPI(percentualIPI)
  }

  ItemRequisicao(Item item, RequisicaoCompra requisicaoCompra, float quantidadeItem, float quantidadePendente, float quantidadeAbastecida) {
    this.definirItem(item)
      this.definirRequisicaoCompra(requisicaoCompra)
      this.setQuantidadeItem(quantidadeItem)
      this.setQuantidadePendente(quantidadePendente)
      this.setQuantidadeAbastecida(quantidadeAbastecida)
  }

  /** Construtor para carregar itens de uma requisicao de compras. */
  ItemRequisicao(RequisicaoCompra requisicaoCompra) {
    this.definirRequisicaoCompra(requisicaoCompra)
  }

  void definirItem(Item item) {
    this.item = item
  }

  void setQuantidadeItem(float quantidadeItem) {
    this.quantidadeItem = quantidadeItem
  }

  void definirStatus(String status) {
    this.status = status
  }

  void setQuantidadeAbastecida(float quantidadeAbastecida) {
    // Atualiza a quantidade pendente com base no último abastecimento.
    this.quantidadeAbastecida = quantidadeAbastecida
  }

  void definirValorUnitario(float valorUnitario) {
    this.valorUnitario = valorUnitario
  }

  void definirRequisicaoCompra(RequisicaoCompra requisicaoCompra) {
    this.requisicaoCompra = requisicaoCompra
  }

  void definirPercentualIPI(int percentualIPI) {
    this.percentualIPI = percentualIPI
  }

  Item obterItem() {
    return this.item
  }

  float getQuantidadeItem() {
    return this.quantidadeItem
  }

  String obterStatus() {
    return this.status
  }

  float getQuantidadeAbastecida() {
    return this.quantidadeAbastecida
  }

  float obterValorUnitario() {
    return this.valorUnitario
  }

  float obterValorTotal() {
    return this.quantidadeItem * this.valorUnitario
  }

  float obterValorTotalComIPI() {
    return obterValorTotal() + ((obterValorTotal() * percentualIPI)/100)
  }

  float obterValorIPI() {
    return (obterValorTotal() * percentualIPI)/100
  }

  RequisicaoCompra obterRequisicaoCompra() {
    return this.requisicaoCompra
  }

  int obterPercentualIPI() {
    return this.percentualIPI
  }

  /**
   * Quantidade do item requisitado que ainda não foi movimentado.
   * @return float A quantidade pendente.
   */
  float getQuantidadePendente() {
    return quantidadePendente
  }

  /**
   * Define a quantidade do item requisitado que ainda não foi movimentado.
   * @param quantidadePendente A quantidade a ser definida.
   */
  void setQuantidadePendente(float quantidadePendente) {
    this.quantidadePendente = quantidadePendente
  }
}
