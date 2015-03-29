package org.esmerilprogramming.tecnolity.producao.ui

import java.awt.*
import java.awt.event.*
import java.util.*
import javax.swing.*

import org.esmerilprogramming.tecnolity.aplicacao.Aplicacao
import org.esmerilprogramming.tecnolity.producao.*
import org.esmerilprogramming.tecnolity.util.*

class DlgDadosMatriz extends JDialog implements ActionListener {
  final int IDENTIFICADOR = 19
    private Aplicacao aplicacao
    private char modo // I = inserir, A = alterar, V = visualizar
    private Container conteudo
    private GridBagLayout gridbag
    private GridBagConstraints gbc
    private JTextField txtReferencia,txtNumeroSola,txtQuantidade,txtDureza,txtDensidade,txtPeso,txtVolume,txtTempoInjecao,txtTempoForma
    private JTextArea txaModificacoes
    private JButton btNovoProduto, btConfirmar, btCancelar
    private Vector produtos
    private Produto produto
    private Matriz matriz

    DlgDadosMatriz(Aplicacao aplicacao) {
      super(aplicacao,true)
        this.setTitle("Nova Matriz")
        this.aplicacao = aplicacao
        this.matriz = new Matriz()
        carregarInterface()
    }

  DlgDadosMatriz(Aplicacao aplicacao,Produto produto) {
    super(aplicacao,true)
      this.setTitle("Nova Matriz")
      this.aplicacao = aplicacao
      this.produto = produto
      this.matriz = new Matriz()
      carregarInterface()
  }

  DlgDadosMatriz(Aplicacao aplicacao,Matriz matriz) {
    super(aplicacao,true)
      this.setTitle("Matriz")
      this.aplicacao = aplicacao
      this.matriz = matriz
      try
      {
        this.matriz.carregarMatriz(aplicacao.obterConexao())
      }
    catch(Exception e) {
      JOptionPane.showMessageDialog(aplicacao,"Erro:" + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE)
        e.printStackTrace()
    }
    carregarInterface()
  }

  private void carregarInterface() {
    this.conteudo = this.getContentPane()

      gridbag = new GridBagLayout()
      gbc = new GridBagConstraints()
      gbc.fill = GridBagConstraints.NONE
      gbc.anchor = GridBagConstraints.NORTHWEST
      gbc.insets.bottom = 2
      gbc.insets.left = 2
      gbc.insets.right = 2
      gbc.insets.top = 2

      JPanel pnlDados = new JPanel(gridbag)
      JLabel label = new JLabel("Referência")
      adicionarComponente(pnlDados,label,0,0,1,1)
      label = new JLabel("No. Sola")
      adicionarComponente(pnlDados,label,0,1,1,1)
      label = new JLabel("Quantidade")
      adicionarComponente(pnlDados,label,0,2,1,1)
      txtReferencia = new JTextField(matriz.obterReferencia(),10)
      adicionarComponente(pnlDados,txtReferencia,1,0,1,1)
      txtNumeroSola = new JTextField("" + matriz.obterNumeroSola(),3)
      adicionarComponente(pnlDados,txtNumeroSola,1,1,1,1)
      txtQuantidade = new JTextField("" + matriz.obterQuantidade(),5)
      adicionarComponente(pnlDados,txtQuantidade,1,2,1,1)
      label = new JLabel("Dureza")
      adicionarComponente(pnlDados,label,2,0,1,1)
      label = new JLabel("Densidade")
      adicionarComponente(pnlDados,label,2,1,1,1)
      label = new JLabel("Peso (g)")
      adicionarComponente(pnlDados,label,2,2,1,1)
      label = new JLabel("Volume (cm3)")
      adicionarComponente(pnlDados,label,2,3,1,1)
      txtDureza = new JTextField(Numero.inverterSeparador("" + matriz.obterDureza()),5)
      adicionarComponente(pnlDados,txtDureza,3,0,1,1)
      txtDensidade = new JTextField(Numero.inverterSeparador("" + matriz.obterDensidade()),5)
      adicionarComponente(pnlDados,txtDensidade,3,1,1,1)
      txtPeso = new JTextField(Numero.inverterSeparador("" + matriz.obterPeso()),5)
      adicionarComponente(pnlDados,txtPeso,3,2,1,1)
      txtVolume = new JTextField(Numero.inverterSeparador("" + matriz.obterVolume()),5)
      adicionarComponente(pnlDados,txtVolume,3,3,1,1)
      label = new JLabel("Tempo Injeção (seg)")
      adicionarComponente(pnlDados,label,4,0,1,1)
      txtTempoInjecao = new JTextField(Numero.inverterSeparador("" + matriz.obterTempoInjecao()),5)
      adicionarComponente(pnlDados,txtTempoInjecao,5,0,1,1)
      label = new JLabel("Tempo de Forma (seg)")
      adicionarComponente(pnlDados,label,4,1,1,1)
      txtTempoForma = new JTextField("" + matriz.obterTempoForma(),5)
      adicionarComponente(pnlDados,txtTempoForma,5,1,1,1)
      label = new JLabel("Modificações")
      adicionarComponente(pnlDados,label,6,0,1,1)
      txaModificacoes = new JTextArea(matriz.obterModificacoes(),3,30)
      JScrollPane scroll = new JScrollPane(txaModificacoes)
      adicionarComponente(pnlDados,scroll,7,0,4,1)
      this.conteudo.add(pnlDados, BorderLayout.CENTER)

      JPanel pnlComandos = new JPanel(new FlowLayout(FlowLayout.RIGHT))
      btConfirmar = new JButton("Confirmar")
      btConfirmar.addActionListener(this)
      pnlComandos.add(btConfirmar)
      btCancelar = new JButton("Cancelar")
      btCancelar.addActionListener(this)
      pnlComandos.add(btCancelar)
      this.conteudo.add(pnlComandos, BorderLayout.SOUTH)

      redimencionar()
  }

  private void redimencionar() {
    this.pack()

      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize()
      this.setBounds((screenSize.width/2) - (this.getBounds().width/2),
          (screenSize.height/2) - (this.getBounds().height/2) - 30,
          this.getBounds().width,
          this.getBounds().height)
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

      if(objeto == btConfirmar) {
        try
        {
          if(matriz.obterReferencia() == null) {
            matriz = new Matriz(txtReferencia.getText(),
                Integer.parseInt("0" + txtNumeroSola.getText()),
                Integer.parseInt("0" + txtQuantidade.getText()),
                Float.parseFloat(Numero.inverterSeparador(txtDureza.getText())),
                Float.parseFloat(Numero.inverterSeparador(txtDensidade.getText())),
                Float.parseFloat(Numero.inverterSeparador(txtPeso.getText())),
                Float.parseFloat(Numero.inverterSeparador(txtVolume.getText())),
                Float.parseFloat(Numero.inverterSeparador(txtTempoInjecao.getText())),
                Integer.parseInt("0" + txtTempoForma.getText()),
                txaModificacoes.getText())
              matriz.cadastrarMatriz()
          }
          else
          {
            matriz.definirReferencia(txtReferencia.getText())
              matriz.definirNumeroSola(Integer.parseInt("0" + txtNumeroSola.getText()))
              matriz.definirQuantidade(Integer.parseInt("0" + txtQuantidade.getText()))
              matriz.definirDureza(Float.parseFloat(Numero.inverterSeparador(txtDureza.getText())))
              matriz.definirDensidade(Float.parseFloat(Numero.inverterSeparador(txtDensidade.getText())))
              matriz.definirPeso(Float.parseFloat(Numero.inverterSeparador(txtPeso.getText())))
              matriz.definirVolume(Float.parseFloat(Numero.inverterSeparador(txtVolume.getText())))
              matriz.definirTempoInjecao(Float.parseFloat(Numero.inverterSeparador(txtTempoInjecao.getText())))
              matriz.definirTempoForma(Integer.parseInt("0" + txtTempoForma.getText()))
              matriz.alterarMatriz()
          }
          this.setVisible(false)
        }
        catch(Exception e) {
          JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE)
            e.printStackTrace()
        }
      }

    if(objeto == btCancelar) {
      this.setVisible(false)
    }
  }
}
