package org.esmerilprogramming.tecnolity.aplicacao.modelos

class ModeloTabelaProduto extends ModeloTabela
{
  ModeloTabelaProduto() {
    super()
  }

  Object getValueAt(int linha, int coluna) {
    return ((String[])cache.elementAt(linha))[coluna]
  }
}
