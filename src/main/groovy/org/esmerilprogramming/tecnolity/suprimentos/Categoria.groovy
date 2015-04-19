package org.esmerilprogramming.tecnolity.suprimentos

import javax.swing.JComboBox
import org.esmerilprogramming.tecnolity.util.Conexao
import org.esmerilprogramming.tecnolity.aplicacao.Aplicacao

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
    Conexao.instance.db.execute 'insert into categoria_item (categoria) values (?)' ,nomeCategoria
    false
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

  Vector carregarCategorias() {
    Vector categorias = new Vector()
    categorias.addElement(null)
    def query = 'select codigo, categoria from categoria_item order by categoria asc'
    def db = Conexao.instance.db
    db.eachRow(query) {
      categorias.addElement(new Categoria(it.codigo, it.categoria))
    }
    categorias
  }
}
