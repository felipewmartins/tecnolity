package org.esmerilprogramming.tecnolity.producao

import java.text.*

import org.esmerilprogramming.tecnolity.suprimentos.*

/**
   * Projeto: 001 - Tecnolity <br>
   * Autor do Código: Hildeberto Mendonça Filho <br>
   * Nome do Arquivo: MateriaPrima.java <br>
   * Linguagem: Java <br>
   *  <br>
   * Requerimentos: Requer máquina virtual java versão 1.3 ou superior. <br>
   *  <br>
   * Objetivo: Classe que representa a Matéria Prima de um Produto. <br>
   *  <br>
   * Objetivo definido por: Hildeberto Mendonça <br>
   * Início da Programação: 20/02/2002 <br>
   * Fim da Programação: <br>
   * Última Versão: 1.0 <br>
*/

 class MateriaPrima implements Cloneable
{
    private Item item
    private Matriz matriz
    private float quantidade

     MateriaPrima() {}
    
     MateriaPrima(Item item, Matriz matriz, float quantidade) throws Exception
    {
        definirItem(item)
        definirMatriz(matriz)
        definirQuantidade(quantidade)
    }

     Item obterItem() 
    {
        return this.item
    }
    
     Matriz obterMatriz() 
    {
        return this.matriz
    }
    
     float obterQuantidade() 
    {
        return this.quantidade
    }
    
     String obterQuantidade(String formato)
    {
        DecimalFormat df
        if(!formato.equals(""))
        {
            df = new DecimalFormat(formato)
        }
        else
        {
            df = new DecimalFormat("###.#####")
        }
        return df.format(this.quantidade)
    }
        
     void definirItem(Item item) throws Exception
    {
        if(item == null)
        {
            Exception e = new Exception("O Item não foi informado.")
            throw e
        }
        this.item = item
    }
    
     void definirMatriz(Matriz matriz) throws Exception
    {
        if(matriz == null)
        {
            Exception e = new Exception("A Matriz nao foi informada.")
            throw e
        }
        this.matriz = matriz
    }
    
     void definirQuantidade(float quantidade) throws Exception
    {
        if(quantidade < 0.0f)
        {
            Exception e = new Exception("Quantidade inválida.")
            throw e
        }
        this.quantidade = quantidade
    }
    
     MateriaPrima clonar()
    {
        MateriaPrima materiaPrima = null
        try
        {
            materiaPrima = (MateriaPrima)super.clone()
        } 
        catch (CloneNotSupportedException e) 
        {
            
        }
        return materiaPrima
    }
}