package org.esmerilprogramming.tecnolity.aplicacao.modelos

import javax.swing.event.TableModelEvent
import javax.swing.event.TableModelListener

class ModeloTabelaListaPreco extends ModeloTabela implements TableModelListener
{
  ModeloTabelaListaPreco() {
    this.addTableModelListener(this)
  }
  boolean isCellEditable(int row, int col) {
    if (col < 3) {
      return false
    }
    else
    {
      return true
    }
  }

  void tableChanged(TableModelEvent arg0) {

  }
}
