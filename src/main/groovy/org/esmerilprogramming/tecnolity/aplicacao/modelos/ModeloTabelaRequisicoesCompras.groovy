package org.esmerilprogramming.tecnolity.aplicacao.modelos

import org.esmerilprogramming.tecnolity.util.*

class ModeloTabelaRequisicoesCompras extends ModeloTabela
{
  ModeloTabelaRequisicoesCompras () {
    super()
  }

  Object getValueAt(int linha, int coluna) {
    if(((String[])cache.elementAt(linha))[coluna] != null) {
      if(coluna == 3 || coluna == 4 || coluna == 5)
        return Calendario.ajustarFormatoDataBanco(((String[])cache.elementAt(linha))[coluna])
      else
        return ((String[])cache.elementAt(linha))[coluna]
    }
    else
      return ((String[])cache.elementAt(linha))[coluna]
  }
}
