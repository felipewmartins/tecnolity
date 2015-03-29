package org.esmerilprogramming.tecnolity.pedidos

import org.esmerilprogramming.tecnolity.producao.Matriz
import org.esmerilprogramming.tecnolity.producao.Produto

 class ProdutoPedido 
{
    private Pedido pedido
    private Produto produto
    private Matriz matriz
    private float quantidade
    private String observacao
    private int transferenciaICMS
    private float valorNegociado
    private String moeda
    private String resumo
    
     ProdutoPedido() 
    {
    
    }
    
     ProdutoPedido(Pedido pedido, Produto produto, Matriz matriz, float quantidade, int transferenciaICMS,
                         String moeda, float valorNegociado, String observacao)
    {
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
                         int transferenciaICMS, float valorNegociado, String moeda, String resumo)
    {
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
    
     Pedido obterPedido()
    {
        return this.pedido
    }
    
     Produto obterProduto()
    {
        return this.produto
    }
    
     Matriz obterMatriz()
    {
        return this.matriz
    }
    
     float obterQuantidade()
    {
        return this.quantidade
    }
    
     String obterObservacao()
    {
        return this.observacao
    }
    
     int obterTransferenciaICMS()
    {
        return this.transferenciaICMS
    }
    
     float obterValorNegociado()
    {
        return this.valorNegociado
    }
    
     String obterMoeda()
    {
        return this.moeda
    }
    
     void definirPedido(Pedido pedido)
    {
        this.pedido = pedido
    }
    
     void definirProduto(Produto produto)
    {
        this.produto = produto
    }
    
     void definirMatriz(Matriz matriz)
    {
        this.matriz = matriz
    }
        
     void definirQuantidade(float quantidade)
    {
        this.quantidade = quantidade
    }
    
     void definirObservacao(String observacao)
    {
        this.observacao = observacao
    }
    
     void definirTransferenciaICMS(int transferenciaICMS)
    {
        this.transferenciaICMS = transferenciaICMS
    }
    
     void definirValorNegociado(float valorNegociado)
    {
        this.valorNegociado = valorNegociado
    }
    
     void definirMoeda(String moeda)
    {
        this.moeda = moeda
    }
    
     void definirResumo(String resumo)
    {
        this.resumo = resumo
    }
}
