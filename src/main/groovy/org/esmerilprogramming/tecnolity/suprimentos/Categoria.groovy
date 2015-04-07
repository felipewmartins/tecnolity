package org.esmerilprogramming.tecnolity.suprimentos

import java.sql.*
import javax.swing.*
import org.esmerilprogramming.tecnolity.util.*
import org.esmerilprogramming.tecnolity.aplicacao.*

class Categoria {
  int codigo
  String nomeCategoria

  Categoria(int codigo, String nomeCategoria) {
    this.codigo = codigo
    this.nomeCategoria = nomeCategoria
  }

  Categoria(String nomeCategoria) {
    this.nomeCategoria = nomeCategoria
  }

  Categoria(int codigo) {
    this.codigo = codigo
  }

  boolean cadastrarCategoria(String nomeCategoria) {
    String query = 'insert into categoria_item (categoria) values (''+ nomeCategoria +'')'
      Conexao conexao = new Conexao('T')
      if (conexao.abrirConexao()) {
        conexao.executarAtualizacao(query)
          conexao.fecharConexao()
          return true
      }
    return false
  }

  Vector carregarCategorias(JComboBox comboBox, Aplicacao aplicacao) {
    ResultSet dadosCategoria
      Vector categorias = new Vector()
      Conexao conexao = aplicacao.obterConexao()
      comboBox.removeAllItems()
      try {
        dadosCategoria = conexao.executarConsulta('select * from categoria_item order by categoria asc')
          comboBox.addItem('Selecione...')
          categorias = new Vector()
          categorias.addElement(null)
          int i = 1
          while(dadosCategoria.next()) {
            categorias.addElement(new Categoria(dadosCategoria.getInt('codigo'),dadosCategoria.getString('categoria')))
              comboBox.addItem(((Categoria)categorias.get(i)).obterNomeCategoria())
              i++
          }
        dadosCategoria.close()
      }
    catch (e) {
      e.printStackTrace()
    }
    categorias
  }

  Vector carregarCategorias(Conexao conexao) {
    ResultSet dadosCategoria
    Vector categorias = new Vector()
    try {
      dadosCategoria = conexao.executarConsulta('select * from categoria_item order by categoria asc')
      categorias.addElement(null)
      while(dadosCategoria.next()) {
        categorias.addElement(new Categoria(dadosCategoria.getInt('codigo'),dadosCategoria.getString('categoria')))
      }
      dadosCategoria.close()
    } catch (e) {
      e.printStackTrace()
    }
    categorias
  }
}
