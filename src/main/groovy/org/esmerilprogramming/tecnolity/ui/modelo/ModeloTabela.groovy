package org.esmerilprogramming.tecnolity.ui.modelo

import javax.swing.*
import javax.swing.table.*

class ModeloTabela extends AbstractTableModel {
  String[] nomesColunas
  Object[][] dados
  int numeroRegistros

  ModeloTabela(final String[] nomesColunas, final Object[][] dados) {
    this.nomesColunas = nomesColunas
      this.dados = dados
  }

  String getColumnName(final int col) {
    return this.nomesColunas[col].toString()
  }

  int getRowCount() {
    return this.dados.length
  }

  int getColumnCount() {
    return this.nomesColunas.length
  }

  Object getValueAt(final int row, final int col) {
    return this.dados[row][col]
  }

  void setValueAt(final Object value, final int row, final int col) {
    this.dados[row][col] = value
      this.fireTableCellUpdated(row, col)
  }

  void addRow(final Object[] values) {
    for (int i = 0 i < values.length ++i) {
      this.setValueAt(values[i], this.numeroRegistros, i)
    }
    ++this.numeroRegistros
  }

  void setRow(final Object[] values, final int posLinha) {
    for (int i = 0 i < values.length ++i) {
      this.setValueAt(values[i], posLinha, i)
    }
  }

  Object[] getRow(final int posLinha) {
    final Object[] linha = new Object[this.getColumnCount()]
      for (int i = 0 i < linha.length ++i) {
        linha[i] = this.getValueAt(posLinha, i)
      }
    return linha
  }

  void removeRow(final int posLinha) {
    if (posLinha < this.numeroRegistros) {
      for (int i = 0 i < this.getColumnCount() ++i) {
        this.setValueAt("", posLinha, i)
      }
      for (int i = posLinha i < this.numeroRegistros - 1 ++i) {
        for (int j = 0 j < this.getColumnCount() ++j) {
          this.setValueAt(this.getValueAt(i + 1, j), i, j)
            this.setValueAt("", i + 1, j)
        }
      }
      --this.numeroRegistros
    }
  }

  void setTamanhoColunas(final JTable tabela, final int[] tamanhoColunas) {
    TableColumn column = null
      int larguraTabela = 0
      for (int i = 0 i < this.getColumnCount() ++i) {
        column = tabela.getColumnModel().getColumn(i)
          larguraTabela = tabela.getPreferredScrollableViewportSize().width
          column.setPreferredWidth(tamanhoColunas[i] * larguraTabela / 100)
      }
  }

}
