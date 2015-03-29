package org.esmerilprogramming.tecnolity.ui.modelo

import java.util.*
import org.esmerilprogramming.tecnolity.util.*
import javax.swing.event.*
import java.sql.*

class ModeloTabelaDB extends ModeloTabela {
  protected Vector cache
    private int numeroColunas
    private String[] cabecalhos
    private ResultSet resultado
    private ConexaoDB conexao

     ModeloTabelaDB() {
      this.cache = new Vector()
    }

   String getColumnName(final int i) {
    return this.cabecalhos[i]
  }

   int getColumnCount() {
    return this.numeroColunas
  }

   int getRowCount() {
    return this.cache.size()
  }

   Object getValueAt(final int linha, final int coluna) {
    return ((String[])this.cache.elementAt(linha))[coluna]
  }

   void setConexao(final ConexaoDB conexao) {
    this.conexao = conexao
  }

   void setConsulta(final String consulta) {
    this.cache = new Vector()
      try {
        this.resultado = this.conexao.executarConsulta(consulta)
          if (this.resultado.next()) {
            final ResultSetMetaData meta = this.resultado.getMetaData()
              this.numeroColunas = meta.getColumnCount()
              this.cabecalhos = new String[this.numeroColunas]
              for (int i = 1 ; i <= this.numeroColunas;  ++i) {
                this.cabecalhos[i - 1] = meta.getColumnName(i)
              }
            /*do {
              final String[] dados = new String[this.numeroColunas]
                for (int j = 0  j < this.numeroColunas  ++j) {
                  dados[j] = this.resultado.getString(j + 1)
                }
              this.cache.addElement(dados)
            } while (this.resultado.next())*/
          }
        this.resultado.close()
          this.fireTableChanged(null)
      }
    catch (Exception e) {
      this.cache = new Vector()
        e.printStackTrace()
    }
  }

   void carregar(final String[] cabecalhos, final String[][] dados) {
    this.cache = new Vector()
      this.numeroColunas = cabecalhos.length
      this.cabecalhos = cabecalhos
      for (int i = 0;  i < dados.length ; ++i) {
        final String[] celulas = new String[this.numeroColunas]
          for (int j = 0 ; j < this.numeroColunas; ++j) {
            celulas[j] = dados[i][j]
          }
        this.cache.addElement(celulas)
      }
    this.fireTableChanged(null)
  }
}
