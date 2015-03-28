/**
   * Projeto: 001 - Tecnolity
   * Autor do Código: Hildeberto Mendonça Filho
   * Nome do Arquivo: ProdutoPedido.java
   * Linguagem: Java
   * 
   * Requerimentos: Requer máquina virtual java versão 1.3 ou superior.
   * 
   * Objetivo: Classe que representa o produto do pedido do cliente.
   * 
   * Objetivo definido por: Hildeberto Mendonça
   * Início da Programação: 17/02/2002
   * Fim da Programação:
   * Última Versão: 1.0
*/

package org.esmerilprogramming.tecnolity.pedidos;

import org.esmerilprogramming.tecnolity.producao.Matriz;
import org.esmerilprogramming.tecnolity.producao.Produto;

public class ProdutoPedido 
{
    private Pedido pedido;
    private Produto produto;
    private Matriz matriz;
    private float quantidade;
    private String observacao;
    private int transferenciaICMS;
    private float valorNegociado;
    private String moeda;
    private String resumo;
    
    public ProdutoPedido() 
    {
    
    }
    
    public ProdutoPedido(Pedido pedido, Produto produto, Matriz matriz, float quantidade, int transferenciaICMS,
                         String moeda, float valorNegociado, String observacao)
    {
        this.definirPedido(pedido);
        this.definirProduto(produto);
        this.definirMatriz(matriz);
        this.definirQuantidade(quantidade);
        this.definirTransferenciaICMS(transferenciaICMS);
        this.definirMoeda(moeda);        
        this.definirValorNegociado(valorNegociado);
        this.definirObservacao(observacao);
    }
    
    public ProdutoPedido(Pedido pedido, Produto produto, Matriz matriz, float quantidade, String observacao,
                         int transferenciaICMS, float valorNegociado, String moeda, String resumo)
    {
        this.definirPedido(pedido);
        this.definirProduto(produto);
        this.definirMatriz(matriz);
        this.definirQuantidade(quantidade);
        this.definirObservacao(observacao);
        this.definirTransferenciaICMS(transferenciaICMS);
        this.definirValorNegociado(valorNegociado);
        this.definirMoeda(moeda);
        this.definirResumo(resumo);
    }
    
    public Pedido obterPedido()
    {
        return this.pedido;
    }
    
    public Produto obterProduto()
    {
        return this.produto;
    }
    
    public Matriz obterMatriz()
    {
        return this.matriz;
    }
    
    public float obterQuantidade()
    {
        return this.quantidade;
    }
    
    public String obterObservacao()
    {
        return this.observacao;
    }
    
    public int obterTransferenciaICMS()
    {
        return this.transferenciaICMS;
    }
    
    public float obterValorNegociado()
    {
        return this.valorNegociado;
    }
    
    public String obterMoeda()
    {
        return this.moeda;
    }
    
    public void definirPedido(Pedido pedido)
    {
        this.pedido = pedido;
    }
    
    public void definirProduto(Produto produto)
    {
        this.produto = produto;
    }
    
    public void definirMatriz(Matriz matriz)
    {
        this.matriz = matriz;
    }
        
    public void definirQuantidade(float quantidade)
    {
        this.quantidade = quantidade;
    }
    
    public void definirObservacao(String observacao)
    {
        this.observacao = observacao;
    }
    
    public void definirTransferenciaICMS(int transferenciaICMS)
    {
        this.transferenciaICMS = transferenciaICMS;
    }
    
    public void definirValorNegociado(float valorNegociado)
    {
        this.valorNegociado = valorNegociado;
    }
    
    public void definirMoeda(String moeda)
    {
        this.moeda = moeda;
    }
    
    public void definirResumo(String resumo)
    {
        this.resumo = resumo;
    }
}