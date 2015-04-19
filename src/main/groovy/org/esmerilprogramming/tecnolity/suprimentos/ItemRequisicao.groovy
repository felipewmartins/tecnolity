package org.esmerilprogramming.tecnolity.suprimentos


class ItemRequisicao {
  // status do item da requisição interna.
  static final String EMITIDO                 = 'EM' // Quando a requisição de compra é emitida.
  static final String ABASTECIDO_TOTALMENTE   = 'AT' // Quando a quantidade requisitada do item entra de uma vez no estoque.
  static final String ABASTECIDO_PARCIALMENTE = 'AP' // Quando a quantidade requisitada do item entra em parte no estoque.
  static final String CANCELADO               = 'CL' // Quando a requisição de compra é cancelada.

  Item item
  float quantidadeItem, quantidadePendente, quantidadeAbastecida, valorUnitario
  int percentualIPI
  RequisicaoCompra requisicaoCompra
  String status

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
    this.item = item
    this.requisicaoCompra = requisicaoCompra
    this.quantidadeItem = quantidadeItem
    this.quantidadePendente = quantidadePendente
    this.quantidadeAbastecidaq = quantidadeAbastecida
  }

  ItemRequisicao(RequisicaoCompra requisicaoCompra) {
    this.requisicaoCompra = requisicaoCompra
  }

  float getValorTotal() {
    quantidadeItem * valorUnitario
  }

  float getValorTotalComIPI() {
    obterValorTotal()  +  ((obterValorTotal() * percentualIPI)/100)
  }

  float getValorIPI() {
    (obterValorTotal() * percentualIPI)/100
  }

}
