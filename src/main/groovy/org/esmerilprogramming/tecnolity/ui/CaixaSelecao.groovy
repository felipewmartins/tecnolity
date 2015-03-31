package org.esmerilprogramming.tecnolity.ui

import java.awt.*

import java.awt.event.*
import javax.swing.*

class CaixaSelecao extends JPanel implements ActionListener
{
  private JComboBox cbxSelecao
    private JButton btAdicionarItem
    private Vector itens
    private DialogoCaixaSelecao dialogo
    private String strDialogo
    private Aplicacao aplicacao
    private boolean editavel

    CaixaSelecao(final Aplicacao aplicacao) {
      this.aplicacao = aplicacao
        this.itens = new Vector()
        this.montarInterface()
    }

  CaixaSelecao(final Aplicacao aplicacao, final boolean editavel) {
    this.aplicacao = aplicacao
      this.itens = new Vector()
      this.editavel = editavel
      this.montarInterface()
  }

  CaixaSelecao(final Aplicacao aplicacao, final String strDialogo) {
    this.aplicacao = aplicacao
      this.strDialogo = strDialogo
      this.itens = new Vector()
      this.montarInterface()
  }

  CaixaSelecao(final Aplicacao aplicacao, final Vector itens) {
    this.aplicacao = aplicacao
      this.itens = itens
      this.montarInterface()
  }

  CaixaSelecao(final Aplicacao aplicacao, final Collection itens) {
    this.aplicacao = aplicacao
      if (itens != null) {
        this.itens = new Vector()
          final Object[] itensAux = itens.toArray()
          for (int i = 0;  i < itensAux.length ; ++i) {
            this.itens.add(itensAux[i])
          }
      }
    this.montarInterface()
  }

  void montarInterface() {
    this.setLayout(new FlowLayout(0, 0, 0))
      (this.cbxSelecao = new JComboBox()).setEditable(this.editavel)
      if (!this.editavel) {
        this.cbxSelecao.addItem("Selecione...")
      }
      else {
        this.cbxSelecao.addItem("")
      }
    if (this.itens.size() > 0) {
      this.addItens()
    }
    this.add(this.cbxSelecao)
      if (this.strDialogo != null) {
        (this.btAdicionarItem = new JButton(new ImageIcon("imagens/novo_item.png"))).setBorderPainted(false)
          this.btAdicionarItem.setPreferredSize(new Dimension(20, 20))
          this.btAdicionarItem.setToolTipText("Adicionar um novo Item")
          this.btAdicionarItem.addActionListener(this)
          this.add(this.btAdicionarItem)
      }
  }

  void addItem(final Object item) {
    this.itens.add(item)
      try {
        Collections.sort((List<Comparable>)this.itens)
      }
    catch (ClassCastException ex) {}
    this.cbxSelecao.removeAllItems()
      if (!this.editavel) {
        this.cbxSelecao.addItem("Selecione...")
      }
      else {
        this.cbxSelecao.addItem("")
      }
    for (int i = 0 ; i < this.itens.size();  ++i) {
      this.cbxSelecao.addItem(this.itens.get(i))
    }
  }

  void addItens() {
    this.cbxSelecao.removeAllItems()
      if (!this.editavel) {
        this.cbxSelecao.addItem("Selecione...")
      }
      else {
        this.cbxSelecao.addItem("")
      }
    for (int i = 0;  i < this.itens.size() ; ++i) {
      this.cbxSelecao.addItem(this.itens.get(i))
    }
  }

  void addItens(final Vector itens) {
    this.itens.addAll(itens)
      this.cbxSelecao.removeAllItems()
      if (!this.editavel) {
        this.cbxSelecao.addItem("Selecione...")
      }
      else {
        this.cbxSelecao.addItem("")
      }
    for (int i = 0;  i < itens.size();  ++i) {
      this.cbxSelecao.addItem(itens.get(i))
    }
  }

  void removeAllItens() {
    this.itens.removeAllElements()
      this.cbxSelecao.removeAllItems()
  }

  void addActionListener(final ActionListener action) {
    this.cbxSelecao.addActionListener(action)
  }

  int getSelectedIndex() {
    return this.cbxSelecao.getSelectedIndex()
  }

  Object getSelectedItem() {
    final int selecao = this.cbxSelecao.getSelectedIndex()
      if (selecao > 0) {
        return this.itens.get(selecao - 1)
      }
    if (selecao < 0 && this.editavel) {
      return this.cbxSelecao.getSelectedItem()
    }
    return null
  }

  void setSelectedItem(final Object objeto) {
    if (objeto != null) {
      for (int i = 0;  i < this.itens.size();  ++i) {
        if (this.itens.get(i).toString().equals(objeto.toString())) {
          this.cbxSelecao.setSelectedIndex(i + 1)
            break
        }
      }
    }
  }

  void setSelectedIndex(final int index) {
    if (index >= 0) {
      this.cbxSelecao.setSelectedIndex(index)
    }
  }

  void setEditable(final boolean editable) {
    this.cbxSelecao.setEditable(editable)
      this.editavel = editable
  }

  void setEnabled(final boolean habilitado) {
    this.cbxSelecao.setEnabled(habilitado)
  }

  void actionPerformed(final ActionEvent evento) {
    final Object objeto = evento.getSource()
      if (objeto == this.btAdicionarItem) {
        try {
          final Class classe = Class.forName(this.strDialogo)
            (this.dialogo = classe.newInstance()).setAplicacao(this.aplicacao)
            this.dialogo.montarInterface()
            this.dialogo.setVisible(true)
            if (this.dialogo.getObjeto() != null) {
              this.addItem(this.dialogo.getObjeto())
                this.cbxSelecao.setSelectedItem(this.dialogo.getObjeto())
            }
        }
        catch (ClassNotFoundException e) {
          JOptionPane.showMessageDialog(this.aplicacao, "Di\u00e1logo informado n\u00e3o encontrado.", "Erro", 0)
        }
        catch (InstantiationException ex) {
          JOptionPane.showMessageDialog(this.aplicacao, "N\u00e3o foi poss\u00edvel instanciar o di\u00e1logo informado.", "Erro", 0)
        }
        catch (IllegalAccessException ea) {
          JOptionPane.showMessageDialog(this.aplicacao, "Acesso negado ao di\u00e1logo informado.", "Erro", 0)
        }
      }
  }

  JComboBox getCbxSelecao() {
    return this.cbxSelecao
  }
}
