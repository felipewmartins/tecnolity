package org.esmerilprogramming.tecnolity.suprimentos.ui

import java.awt.*

import java.awt.event.*
import javax.swing.*

import org.esmerilprogramming.tecnolity.aplicacao.Aplicacao
import org.esmerilprogramming.tecnolity.suprimentos.*
import org.esmerilprogramming.tecnolity.util.*

class DlgDadosLote extends JDialog implements ActionListener, FocusListener
{
  final int IDENTIFICADOR = 18

    private Aplicacao aplicacao
    private GridBagLayout gridbag
    private GridBagConstraints gbc
    private Lote lote
    private ItemRequisicao itemRequisicao
    private Movimentacao movimentacaoItem
    private Vector localizacoes
    private JPanel pnlDadosLote
    private JLabel lblItem
    private JTextField txtDataValidade, txtQuantidade, txtQuantidadeRecebida, txtDescricao
    private JCheckBox chbReservado
    private JComboBox cbxLocalizacao

    private Container conteudo
    private JPanel pnlAreaDados
    private JButton btConfirmar, btCancelar

    DlgDadosLote(Aplicacao aplicacao,ItemRequisicao itemRequisicao, Movimentacao movimentacaoItem) {
      super(aplicacao,true)
        try
        {
          lote = new Lote()
        }
      catch(Exception e) {
        JOptionPane.showMessageDialog(aplicacao,'Erro: Não foi possível carregar o Lote.','Erro', JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
      this.itemRequisicao = itemRequisicao
        this.movimentacaoItem = movimentacaoItem

        this.setTitle('Lote')

        this.aplicacao = aplicacao

        montarInterface()
    }

  void montarInterface() {
    conteudo = this.getContentPane()

      gridbag = new GridBagLayout()
      gbc = new GridBagConstraints()
      gbc.fill = GridBagConstraints.NONE
      gbc.anchor = GridBagConstraints.NORTHWEST
      gbc.insets.bottom = 2
      gbc.insets.left = 2
      gbc.insets.right = 2
      gbc.insets.top = 2

      pnlAreaDados = new JPanel(gridbag)
      JLabel label = new JLabel('Item')
      adicionarComponente(pnlAreaDados,label,0,0,2,1)
      label = new JLabel('Data Validade')
      adicionarComponente(pnlAreaDados,label,0,2,1,1)

      lblItem = new JLabel(itemRequisicao.obterItem().obterDescricao())
      adicionarComponente(pnlAreaDados,lblItem,1,0,2,1)

      txtDataValidade = new JTextField(8)
      txtDataValidade.addFocusListener(this)
      adicionarComponente(pnlAreaDados,txtDataValidade,1,2,1,1)

      label = new JLabel('Localização')
      adicionarComponente(pnlAreaDados,label,2,0,1,1)
      label = new JLabel('Quant. Requisitada')
      adicionarComponente(pnlAreaDados,label,2,1,1,1)
      label = new JLabel('Quant. a Abastecer')
      adicionarComponente(pnlAreaDados,label,2,2,1,1)

      localizacoes = (new Categoria()).carregarCategorias(aplicacao.obterConexao())
      cbxLocalizacao = new JComboBox()
      carregarLocalizacoes()
      adicionarComponente(pnlAreaDados,cbxLocalizacao,3,0,1,1)

      txtQuantidade = new JTextField('' + Numero.inverterSeparador(itemRequisicao.getQuantidadeItem()),8)
      txtQuantidade.setEditable(false)
      adicionarComponente(pnlAreaDados,txtQuantidade,3,1,1,1)

      txtQuantidadeRecebida = new JTextField('' + Numero.inverterSeparador(itemRequisicao.getQuantidadePendente()),8)
      adicionarComponente(pnlAreaDados,txtQuantidadeRecebida,3,2,1,1)

      label = new JLabel('Descrição')
      adicionarComponente(pnlAreaDados,label,4,0,2,1)

      txtDescricao = new JTextField(30)
      txtDescricao.addFocusListener(this)
      adicionarComponente(pnlAreaDados,txtDescricao,5,0,3,1)

      conteudo.add(pnlAreaDados,BorderLayout.CENTER)

      JPanel pnlComandos = new JPanel(new FlowLayout(FlowLayout.RIGHT))
      btConfirmar = new JButton('Confirmar')
      btConfirmar.addActionListener(this)
      pnlComandos.add(btConfirmar)

      btCancelar = new JButton('Cancelar')
      btCancelar.addActionListener(this)
      pnlComandos.add(btCancelar)

      conteudo.add(pnlComandos, BorderLayout.SOUTH)
      this.pack()

      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize()
      this.setBounds((screenSize.width/2) - (this.getBounds().width/2),
          (screenSize.height/2) - (this.getBounds().height/2) - 30,
          this.getBounds().width,
          this.getBounds().height)
  }

  private void carregarLocalizacoes() {
    cbxLocalizacao.addItem('Selecione...')
      Categoria categoria
      for(int i = 1;i < localizacoes.size();i++) {
        categoria = (Categoria)localizacoes.get(i)
          cbxLocalizacao.addItem(categoria.obterNomeCategoria())
          if(categoria.obterNomeCategoria().equals(itemRequisicao.obterItem().obterCategoria().obterNomeCategoria()))
            cbxLocalizacao.setSelectedIndex(i)
      }
  }

  private void adicionarComponente(JPanel painel, Component c, int linha, int coluna, int largura, int altura) {
    gbc.gridx = coluna
      gbc.gridy = linha

      gbc.gridwidth = largura
      gbc.gridheight = altura

      gridbag.setConstraints(c,gbc)
      painel.add(c)
  }


  void actionPerformed(java.awt.event.ActionEvent actionEvent) {
    Object objeto = actionEvent.getSource()

      if(objeto == btCancelar) {
        this.setVisible(false)
      }

    if(objeto == btConfirmar) {
      if(!txtQuantidadeRecebida.getText().equals('')) {
        //Verifica se o item foi atendido completamente ou parcialmente.
        if(itemRequisicao.getQuantidadePendente() > Float.parseFloat(Numero.inverterSeparador(txtQuantidadeRecebida.getText())))
          itemRequisicao.definirStatus(ItemRequisicao.ABASTECIDO_PARCIALMENTE)
        else if(itemRequisicao.getQuantidadePendente() == Float.parseFloat(Numero.inverterSeparador(txtQuantidadeRecebida.getText())))
          itemRequisicao.definirStatus(ItemRequisicao.ABASTECIDO_TOTALMENTE)
            // Define o lote para o item requisitado.
            if(itemRequisicao.getQuantidadePendente() >= Float.parseFloat(Numero.inverterSeparador(txtQuantidadeRecebida.getText()))) {
              try
              {
                lote.definirItem(itemRequisicao.obterItem())
                  lote.definirMovimentacao(movimentacaoItem)
                  lote.definirDataValidade(txtDataValidade.getText())
                  lote.definirLocalizacao((Categoria)localizacoes.get(cbxLocalizacao.getSelectedIndex()))
                  lote.definirQuantidade(Float.parseFloat(Numero.inverterSeparador(txtQuantidadeRecebida.getText())))
                  itemRequisicao.setQuantidadeAbastecida(itemRequisicao.getQuantidadeAbastecida() + Float.parseFloat(Numero.inverterSeparador(txtQuantidadeRecebida.getText())))
                  movimentacaoItem.definirQuantidadeItem(Float.parseFloat(Numero.inverterSeparador(txtQuantidadeRecebida.getText())))
                  lote.definirDescricao(txtDescricao.getText())
                  this.itemRequisicao.obterItem().definirLote(this.lote)
              }
              catch(Exception e) {
                JOptionPane.showMessageDialog(aplicacao,'Erro: ' + e.getMessage(),'Erro',JOptionPane.ERROR_MESSAGE)
                  e.printStackTrace()
              }
              this.setVisible(false)
            }
            else
            {
              JOptionPane.showMessageDialog(aplicacao,'Erro: A quantidade a abastecer é maior que a requisitada.','Erro',JOptionPane.ERROR_MESSAGE)
            }
      }
      else
      {
        JOptionPane.showMessageDialog(aplicacao,'Erro: A quantidade a Abastecer não foi informada.','Erro',JOptionPane.ERROR_MESSAGE)
      }
    }
  }

  void focusLost(java.awt.event.FocusEvent focusEvent) {}

  void focusGained(FocusEvent e) {}
}
