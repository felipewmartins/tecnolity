package org.esmerilprogramming.tecnolity.aplicacao.modelos

import org.esmerilprogramming.tecnolity.util.*

class ModeloTabelaMovimentacoes extends ModeloTabela
{

  ModeloTabelaMovimentacoes() {
    super()
  }

  Object getValueAt(int linha, int coluna) {
    if (coluna == 3)
      return Numero.inverterSeparador(((String[])cache.elementAt(linha))[coluna])
        if (coluna == 4)
          return Calendario.ajustarFormatoDataBanco(((String[])cache.elementAt(linha))[coluna])
        else
          return ((String[])cache.elementAt(linha))[coluna]
  }
}
