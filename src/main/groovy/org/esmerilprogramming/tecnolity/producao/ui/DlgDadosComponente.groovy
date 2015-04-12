package org.esmerilprogramming.tecnolity.producao.ui

import java.awt.*
import java.awt.event.*
import javax.swing.*

import org.esmerilprogramming.tecnolity.aplicacao.Aplicacao
import org.esmerilprogramming.tecnolity.producao.*

class DlgDadosComponente extends JDialog implements ActionListener, FocusListener
{
  final int IDENTIFICADOR = 11

    private Aplicacao aplicacao
    private char modo // I = inhserir, A = alterar, V = visualizar

    private Container conteudo
    private JPanel pnlAreaDados
    private JButton btConfirmar, btCancelar
    private JTextField txtComponente

    private GridBagLayout gridbag
    private GridBagConstraints gbc

    DlgDadosComponente(Aplicacao aplicacao, char modo) {
      super(aplicacao, true)

        // Define o título da janela
        String tituloJanela = ""
        if (modo == 'I')
          tituloJanela = "Novo Componente"
            if (modo == 'A')
              tituloJanela = "Componente"
                if (modo == 'V')
                  tituloJanela = "Componente"
                    this.setTitle(tituloJanela)

                    this.aplicacao = aplicacao
                    this.modo = modo

                    montarInterface()
    }

  private void montarInterface() {
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
      JLabel label = new JLabel("Nome do Componente")
      adicionarComponente(pnlAreaDados, label, 0, 0, 1, 1)
      txtComponente = new JTextField(20)
      adicionarComponente(pnlAreaDados, txtComponente, 1, 0, 1, 1)
      conteudo.add(pnlAreaDados, BorderLayout.CENTER)

      JPanel pnlComandos = new JPanel(new FlowLayout(FlowLayout.RIGHT))
      btConfirmar = new JButton("Confirmar")
      btConfirmar.addActionListener(this)
      pnlComandos.add(btConfirmar)
      btCancelar = new JButton("Cancelar")
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

  private void adicionarComponente(JPanel painel, Component c, int linha, int coluna, int largura, int altura) {
    gbc.gridx = coluna
      gbc.gridy = linha

      gbc.gridwidth = largura
      gbc.gridheight = altura

      gridbag.setConstraints(c, gbc)
      painel.add(c)
  }

  void actionPerformed(java.awt.event.ActionEvent actionEvent) {
    Object objeto = actionEvent.getSource()

      if (objeto == btConfirmar) {
        Componente componente = new Componente(txtComponente.getText())
          componente.cadastrarComponente()
          this.setVisible(false)
      }

    if (objeto == btCancelar) {
      this.setVisible(false)
    }
  }

  void focusLost(java.awt.event.FocusEvent focusEvent) {

  }

  void focusGained(java.awt.event.FocusEvent e) {

  }
}
