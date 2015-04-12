package org.esmerilprogramming.tecnolity.aplicacao.modelos

import org.esmerilprogramming.tecnolity.util.*

class ModeloTabelaMotoristas extends ModeloTabela
{
  ModeloTabelaMotoristas() {
    super()
  }

  Object getValueAt(int linha, int coluna) {
    if (coluna == 5)
      return Calendario.ajustarFormatoDataBanco(((String[])cache.elementAt(linha))[coluna])
    else
      return ((String[])cache.elementAt(linha))[coluna]
  }
}
