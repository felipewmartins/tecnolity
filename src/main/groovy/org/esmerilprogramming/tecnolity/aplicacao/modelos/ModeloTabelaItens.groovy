package org.esmerilprogramming.tecnolity.aplicacao.modelos

import org.esmerilprogramming.tecnolity.util.*

class ModeloTabelaItens extends ModeloTabela
{
  ModeloTabelaItens() {
    super()
  }

  Object getValueAt(int linha, int coluna) {
    if(coluna == 4 || coluna == 5 || coluna == 6)
      return Numero.inverterSeparador(((String[])cache.elementAt(linha))[coluna])
    else
      return ((String[])cache.elementAt(linha))[coluna]
  }
}
