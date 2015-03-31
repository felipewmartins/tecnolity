package org.esmerilprogramming.tecnolity.pedidos

import org.esmerilprogramming.tecnolity.producao.Matriz
import org.esmerilprogramming.tecnolity.producao.Produto

class ProdutoPedido {
  Pedido pedido
  Produto produto
  Matriz matriz
  float quantidade
  String observacao
  int transferenciaICMS
  float valorNegociado
  String moeda
  String resumo

  ProdutoPedido(Pedido pedido, Produto produto, Matriz matriz, float quantidade, int transferenciaICMS,
      String moeda, float valorNegociado, String observacao) {
    this.definirPedido(pedido)
      this.definirProduto(produto)
      this.definirMatriz(matriz)
      this.definirQuantidade(quantidade)
      this.definirTransferenciaICMS(transferenciaICMS)
      this.definirMoeda(moeda)
      this.definirValorNegociado(valorNegociado)
      this.definirObservacao(observacao)
  }

  ProdutoPedido(Pedido pedido, Produto produto, Matriz matriz, float quantidade, String observacao,
      int transferenciaICMS, float valorNegociado, String moeda, String resumo) {
    this.definirPedido(pedido)
      this.definirProduto(produto)
      this.definirMatriz(matriz)
      this.definirQuantidade(quantidade)
      this.definirObservacao(observacao)
      this.definirTransferenciaICMS(transferenciaICMS)
      this.definirValorNegociado(valorNegociado)
      this.definirMoeda(moeda)
      this.definirResumo(resumo)
  }

}
