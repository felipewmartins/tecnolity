package org.esmerilprogramming.tecnolity.suprimentos

import java.sql.*
import javax.swing.*
import org.esmerilprogramming.tecnolity.util.*
import org.esmerilprogramming.tecnolity.aplicacao.*

class Categoria {
  int codigo
  String nomeCategoria

  Categoria() {

  }

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
    String query = 'insert into categoria_item (categoria) values ('' +  nomeCategoria +'')'
      Conexao conexao = new Conexao('T')
      if (conexao.abrirConexao()) {
        conexao.executarAtualizacao(query)
          conexao.fecharConexao()
          return true
      }
    return false
  }

  Vector carregarCategorias(JComboBox comboBox, Aplicacao aplicacao) {
    Vector categorias = new Vector()
    categorias.addElement(null)
    comboBox.removeAllItems()
    def db = Conexao.instance.db
    def query = 'select categoria_item_id as id, categoria from categoria_item order by categoria asc'
    comboBox.addItem('Selecione...')
    int i = 1
    db.eachRow(query) {
      categorias.addElement(new Categoria(it.id, it.categoria))
      comboBox.addItem(((Categoria)categorias.get(i)).nomeCategoria)
      i++
    }
    categorias
  }

  Vector carregarCategorias(Conexao conexao) {
    ResultSet dadosCategoria
    Vector categorias = new Vector()
    try {
      dadosCategoria = conexao.executarConsulta('select * from categoria_item order by categoria asc')
      categorias.addElement(null)
      while (dadosCategoria.next()) {
        categorias.addElement(new Categoria(dadosCategoria.getInt('codigo'), dadosCategoria.getString('categoria')))
      }
      dadosCategoria.close()
    } catch (e) {
      e.printStackTrace()
    }
    categorias
  }
}
