package org.esmerilprogramming.tecnolity.suprimentos;

import org.esmerilprogramming.tecnolity.administracao.Departamento;
import org.esmerilprogramming.tecnolity.util.*;

public class ItemRequisicaoInterna
{
    public static final String STATUS_EMITIDO    = "EM";
    public static final String STATUS_CANCELADO  = "CL";
    public static final String STATUS_CONFIRMADO = "CO";
    public static final String STATUS_PENDENTE   = "PD";
    
    private Item item;
    private RequisicaoInterna requisicaoInterna;
    private String status;
    private float quantidadeItem;
    private Departamento destino;
    
    public ItemRequisicaoInterna(Item item, RequisicaoInterna requisicaoInterna, float quantidadeItem,Departamento destino, String status) throws Exception
    {
        this.definirItem(item);
        this.definirRequisicaoInterna(requisicaoInterna);
        this.definirQuantidadeItem(quantidadeItem);
        this.definirDestino(destino);
        this.definirStatus(status);
    }
        
    /** Construtor para carregar itens de uma requisicao interna. */
    public ItemRequisicaoInterna(RequisicaoInterna requisicaoInterna)
    {
        this.definirRequisicaoInterna(requisicaoInterna);
    }
    
    public ItemRequisicaoInterna(Item item, float quantidade) throws Exception
    {
        this.definirItem(item);
        this.definirQuantidadeItem(quantidade);
    }
    
    public void definirItem(Item item)
    {
        this.item = item;
    }
    
    public void definirQuantidadeItem(float quantidadeItem) throws Exception
    {
        if(quantidadeItem >= 0.0f)
        {
        	this.quantidadeItem = quantidadeItem;
        }
        else
        {
        	throw new Exception("A quantidade deve ser maior ou igual a zero zero.");
        }
    }
        
    public void definirRequisicaoInterna(RequisicaoInterna requisicaoInterna)
    {
        this.requisicaoInterna = requisicaoInterna;
    }
    
    public void definirStatus(String status)
    {
        this.status = status;
    }
    
    public void definirDestino(Departamento destino)
    {
        this.destino = destino;
    }
        
    public Item obterItem()
    {
        return this.item;
    }
    
    public float obterQuantidadeItem()
    {
        return this.quantidadeItem;
    }
        
    public RequisicaoInterna obterRequisicaoInterna()
    {
        return this.requisicaoInterna;
    }
    
    public String obterStatus()
    {
        return this.status;
    }
    
    public String obterStatusLiteral()
    {
        if(status.equals(STATUS_CANCELADO))
            return "Cancelado";
        if(status.equals(STATUS_CONFIRMADO))
            return "Confirmado";    
        if(status.equals(STATUS_EMITIDO))
            return "Emitido";
        else
            return "Pendente";
    }
    
    public Departamento obterDestino()
    {
        return this.destino;
    }
    
    public void registrarItemRequisicaoInterna() throws Exception
    {
        Conexao conexao = new Conexao('T');
        if(conexao.abrirConexao())
        {
            String query = "insert into item_requisicao_interna (item,requisicao_interna,status,quantidade,destino) values ("+ this.item.obterCodigo() +","+ this.requisicaoInterna.obterCodigo() +",'EM',"+ this.quantidadeItem +","+ this.destino.obterCodigo() +")";
            conexao.executarAtualizacao(query);
            conexao.fecharConexao();
        }
        conexao = null;
    }
    
	/**
	 * Method atualizarItemRequisicaoInterna. Atualiza o status do item de uma
     * requisição interna.
	 * @param status Status para o qual se quer alterar o item da requisição interna.
	 * @throws Exception Em caso de erros com a transação com o banco de dados.
	 */
    public void atualizarItemRequisicaoInterna() throws Exception
    {
        Conexao conexao = new Conexao('T');
        if(conexao.abrirConexao())
        {
            String query = "update item_requisicao_interna set status = '"+ status +"', quantidade = '"+ this.quantidadeItem +"' where item = " + this.obterItem().obterCodigo() + " and requisicao_interna = " + this.obterRequisicaoInterna().obterCodigo();
            conexao.executarAtualizacao(query);
            conexao.fecharConexao();
        }
        conexao = null;
    }
}       