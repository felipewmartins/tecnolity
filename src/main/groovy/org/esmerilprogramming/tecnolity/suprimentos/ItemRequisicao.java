package org.esmerilprogramming.tecnolity.suprimentos;


public class ItemRequisicao
{
    // status do item da requisição interna.
    public static final String EMITIDO                 = "EM"; // Quando a requisição de compra é emitida.
    public static final String ABASTECIDO_TOTALMENTE   = "AT"; // Quando a quantidade requisitada do item entra de uma vez no estoque.
    public static final String ABASTECIDO_PARCIALMENTE = "AP"; // Quando a quantidade requisitada do item entra em parte no estoque.
    public static final String CANCELADO               = "CL"; // Quando a requisição de compra é cancelada.
    
    private Item item;
    private float quantidadeItem, quantidadePendente, quantidadeAbastecida, valorUnitario;
    private int percentualIPI;
    private RequisicaoCompra requisicaoCompra;
    private String status;
    
    public ItemRequisicao(Item item, RequisicaoCompra requisicaoCompra, String status, float quantidadeItem, float quantidadePendente, float quantidadeAbastecida, float valorUnitario, int percentualIPI)
    {
        this.definirItem(item);
        this.definirRequisicaoCompra(requisicaoCompra);
        this.definirStatus(status);
        this.setQuantidadeItem(quantidadeItem);
        this.setQuantidadePendente(quantidadePendente);
        this.setQuantidadeAbastecida(quantidadeAbastecida);
        this.definirValorUnitario(valorUnitario);
        this.definirPercentualIPI(percentualIPI);
    }
    
    public ItemRequisicao(Item item, RequisicaoCompra requisicaoCompra, float quantidadeItem, float quantidadePendente, float quantidadeAbastecida, float valorUnitario, int percentualIPI)
    {
        this.definirItem(item);
        this.definirRequisicaoCompra(requisicaoCompra);
        this.setQuantidadeItem(quantidadeItem);
        this.setQuantidadePendente(quantidadePendente);
        this.setQuantidadeAbastecida(quantidadeAbastecida);
        this.definirValorUnitario(valorUnitario);
        this.definirPercentualIPI(percentualIPI);
    }
    
    public ItemRequisicao(Item item, RequisicaoCompra requisicaoCompra, float quantidadeItem, float quantidadePendente, float quantidadeAbastecida)
    {
        this.definirItem(item);
        this.definirRequisicaoCompra(requisicaoCompra);
        this.setQuantidadeItem(quantidadeItem);
        this.setQuantidadePendente(quantidadePendente);
        this.setQuantidadeAbastecida(quantidadeAbastecida);
    }
    
    /** Construtor para carregar itens de uma requisicao de compras. */
    public ItemRequisicao(RequisicaoCompra requisicaoCompra)
    {
        this.definirRequisicaoCompra(requisicaoCompra);
    }
    
    public void definirItem(Item item)
    {
        this.item = item;
    }
    
    public void setQuantidadeItem(float quantidadeItem)
    {
        this.quantidadeItem = quantidadeItem;
    }
    
    public void definirStatus(String status)
    {
        this.status = status;
    }
    
    public void setQuantidadeAbastecida(float quantidadeAbastecida)
    {
        // Atualiza a quantidade pendente com base no último abastecimento.
        this.quantidadeAbastecida = quantidadeAbastecida;
    }
    
    public void definirValorUnitario(float valorUnitario)
    {
        this.valorUnitario = valorUnitario;
    }
    
    public void definirRequisicaoCompra(RequisicaoCompra requisicaoCompra)
    {
        this.requisicaoCompra = requisicaoCompra;
    }
    
    public void definirPercentualIPI(int percentualIPI)
    {
        this.percentualIPI = percentualIPI;
    }
    
    public Item obterItem()
    {
        return this.item;
    }
    
    public float getQuantidadeItem()
    {
        return this.quantidadeItem;
    }
    
    public String obterStatus()
    {
        return this.status;
    }
    
    public float getQuantidadeAbastecida()
    {
        return this.quantidadeAbastecida;
    }
    
    public float obterValorUnitario()
    {
        return this.valorUnitario;
    }
    
    public float obterValorTotal()
    {
        return this.quantidadeItem * this.valorUnitario;
    }
    
    public float obterValorTotalComIPI()
    {
        return obterValorTotal() + ((obterValorTotal() * percentualIPI)/100);
    }
    
    public float obterValorIPI()
    {
        return (obterValorTotal() * percentualIPI)/100;
    }
    
    public RequisicaoCompra obterRequisicaoCompra()
    {
        return this.requisicaoCompra;
    }
    
    public int obterPercentualIPI()
    {
        return this.percentualIPI;
    }
    
	/**
	 * Quantidade do item requisitado que ainda não foi movimentado.
	 * @return float A quantidade pendente.
	 */
	public float getQuantidadePendente()
	{
		return quantidadePendente;
	}

	/**
	 * Define a quantidade do item requisitado que ainda não foi movimentado.
	 * @param quantidadePendente A quantidade a ser definida.
	 */
	public void setQuantidadePendente(float quantidadePendente)
	{
		this.quantidadePendente = quantidadePendente;
	}
}       