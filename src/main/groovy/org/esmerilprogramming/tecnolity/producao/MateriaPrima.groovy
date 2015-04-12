package org.esmerilprogramming.tecnolity.producao

import java.text.*

import org.esmerilprogramming.tecnolity.suprimentos.*

class MateriaPrima implements Cloneable {
  Item item
  Matriz matriz
  float quantidade

  MateriaPrima(Item item, Matriz matriz, float quantidade) throws Exception
  {
    definirItem(item)
      definirMatriz(matriz)
      definirQuantidade(quantidade)
  }

  String obterQuantidade(String formato) {
    DecimalFormat df
      if (!formato.equals('')) {
        df = new DecimalFormat(formato)
      }
      else
      {
        df = new DecimalFormat('###.#####')
      }
    return df.format(this.quantidade)
  }

  void definirItem(Item item) throws Exception
  {
    if (item == null) {
      Exception e = new Exception('O Item não foi informado.')
        throw e
    }
    this.item = item
  }

  void definirMatriz(Matriz matriz) throws Exception
  {
    if (matriz == null) {
      Exception e = new Exception('A Matriz nao foi informada.')
        throw e
    }
    this.matriz = matriz
  }

  void definirQuantidade(float quantidade) throws Exception
  {
    if (quantidade < 0.0f) {
      Exception e = new Exception('Quantidade inválida.')
        throw e
    }
    this.quantidade = quantidade
  }

  MateriaPrima clonar() {
    MateriaPrima materiaPrima = null
      try {
        materiaPrima = (MateriaPrima)super.clone()
      } 
    catch (CloneNotSupportedException e) {

    }
    return materiaPrima
  }
}
