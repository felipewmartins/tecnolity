package br.com.tecnolity.producao;

import java.text.*;

import br.com.tecnolity.suprimentos.*;

/**
   * Projeto: 001 - Tecnolity <br>
   * Autor do C�digo: Hildeberto Mendon�a Filho <br>
   * Nome do Arquivo: MateriaPrima.java <br>
   * Linguagem: Java <br>
   *  <br>
   * Requerimentos: Requer m�quina virtual java vers�o 1.3 ou superior. <br>
   *  <br>
   * Objetivo: Classe que representa a Mat�ria Prima de um Produto. <br>
   *  <br>
   * Objetivo definido por: Hildeberto Mendon�a <br>
   * In�cio da Programa��o: 20/02/2002 <br>
   * Fim da Programa��o: <br>
   * �ltima Vers�o: 1.0 <br>
*/

public class MateriaPrima implements Cloneable
{
    private Item item;
    private Matriz matriz;
    private float quantidade;

    public MateriaPrima() {}
    
    public MateriaPrima(Item item, Matriz matriz, float quantidade) throws Exception
    {
        definirItem(item);
        definirMatriz(matriz);
        definirQuantidade(quantidade);
    }

    public Item obterItem() 
    {
        return this.item;
    }
    
    public Matriz obterMatriz() 
    {
        return this.matriz;
    }
    
    public float obterQuantidade() 
    {
        return this.quantidade;
    }
    
    public String obterQuantidade(String formato)
    {
        DecimalFormat df;
        if(!formato.equals(""))
        {
            df = new DecimalFormat(formato);
        }
        else
        {
            df = new DecimalFormat("###.#####");
        }
        return df.format(this.quantidade);
    }
        
    public void definirItem(Item item) throws Exception
    {
        if(item == null)
        {
            Exception e = new Exception("O Item n�o foi informado.");
            throw e;
        }
        this.item = item;
    }
    
    public void definirMatriz(Matriz matriz) throws Exception
    {
        if(matriz == null)
        {
            Exception e = new Exception("A Matriz nao foi informada.");
            throw e;
        }
        this.matriz = matriz;
    }
    
    public void definirQuantidade(float quantidade) throws Exception
    {
        if(quantidade < 0.0f)
        {
            Exception e = new Exception("Quantidade inv�lida.");
            throw e;
        }
        this.quantidade = quantidade;
    }
    
    public MateriaPrima clonar()
    {
        MateriaPrima materiaPrima = null;
        try
        {
            materiaPrima = (MateriaPrima)super.clone();
        } 
        catch (CloneNotSupportedException e) 
        {
            
        }
        return materiaPrima;
    }
}