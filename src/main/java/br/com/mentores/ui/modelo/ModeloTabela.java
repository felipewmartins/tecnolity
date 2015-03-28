package br.com.mentores.ui.modelo;

import javax.swing.*;
import javax.swing.table.*;

public class ModeloTabela extends AbstractTableModel
{
    private String[] nomesColunas;
    private Object[][] dados;
    private int numeroRegistros;
    
    public ModeloTabela() {
    }
    
    public ModeloTabela(final String[] nomesColunas, final Object[][] dados) {
        this.nomesColunas = nomesColunas;
        this.dados = dados;
    }
    
    public String getColumnName(final int col) {
        return this.nomesColunas[col].toString();
    }
    
    public int getRowCount() {
        return this.dados.length;
    }
    
    public int getColumnCount() {
        return this.nomesColunas.length;
    }
    
    public Object getValueAt(final int row, final int col) {
        return this.dados[row][col];
    }
    
    public void setValueAt(final Object value, final int row, final int col) {
        this.dados[row][col] = value;
        this.fireTableCellUpdated(row, col);
    }
    
    public void addRow(final Object[] values) {
        for (int i = 0; i < values.length; ++i) {
            this.setValueAt(values[i], this.numeroRegistros, i);
        }
        ++this.numeroRegistros;
    }
    
    public void setRow(final Object[] values, final int posLinha) {
        for (int i = 0; i < values.length; ++i) {
            this.setValueAt(values[i], posLinha, i);
        }
    }
    
    public Object[] getRow(final int posLinha) {
        final Object[] linha = new Object[this.getColumnCount()];
        for (int i = 0; i < linha.length; ++i) {
            linha[i] = this.getValueAt(posLinha, i);
        }
        return linha;
    }
    
    public void removeRow(final int posLinha) {
        if (posLinha < this.numeroRegistros) {
            for (int i = 0; i < this.getColumnCount(); ++i) {
                this.setValueAt("", posLinha, i);
            }
            for (int i = posLinha; i < this.numeroRegistros - 1; ++i) {
                for (int j = 0; j < this.getColumnCount(); ++j) {
                    this.setValueAt(this.getValueAt(i + 1, j), i, j);
                    this.setValueAt("", i + 1, j);
                }
            }
            --this.numeroRegistros;
        }
    }
    
    public void setTamanhoColunas(final JTable tabela, final int[] tamanhoColunas) {
        TableColumn column = null;
        int larguraTabela = 0;
        for (int i = 0; i < this.getColumnCount(); ++i) {
            column = tabela.getColumnModel().getColumn(i);
            larguraTabela = tabela.getPreferredScrollableViewportSize().width;
            column.setPreferredWidth(tamanhoColunas[i] * larguraTabela / 100);
        }
    }
    
    public int getNumeroRegistros() {
        return this.numeroRegistros;
    }
    
    protected void setNumeroRegistros(final int numero) {
        this.numeroRegistros = numero;
    }
}
