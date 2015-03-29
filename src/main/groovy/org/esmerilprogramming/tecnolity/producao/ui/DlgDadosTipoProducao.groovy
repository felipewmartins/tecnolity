package org.esmerilprogramming.tecnolity.producao.ui

import java.awt.*
import java.awt.event.*
import javax.swing.*
import org.esmerilprogramming.tecnolity.aplicacao.Aplicacao
import org.esmerilprogramming.tecnolity.producao.*

class DlgDadosTipoProducao extends JDialog implements ActionListener, FocusListener
{
  final int IDENTIFICADOR = 29

    private Aplicacao aplicacao
    private char modo // I = inhserir, A = alterar, V = visualizar

    private Container conteudo
    private JPanel pnlAreaDados
    private JButton btConfirmar, btCancelar
    private JTextField txtIdentificador, txtTipoProducao
    private TipoProducao tipoProducao

    private GridBagLayout gridbag
    private GridBagConstraints gbc

    DlgDadosTipoProducao(Aplicacao aplicacao, char modo) {
      super(aplicacao,true)

        // Define o título da janela
        String tituloJanela = ""
        if (modo == 'I')
          tituloJanela = "Novo Tipo da Produção"
            if (modo == 'A')
              tituloJanela = "Tipo da Produção"
                if (modo == 'V')
                  tituloJanela = "Tipo da Produção"
                    this.setTitle(tituloJanela)

                    this.aplicacao = aplicacao
                    this.modo = modo

                    tipoProducao = new TipoProducao()
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
      JLabel label = new JLabel("Tipo da Produção")
      adicionarComponente(pnlAreaDados,label,0,0,1,1)
      txtTipoProducao = new JTextField(20)
      adicionarComponente(pnlAreaDados,txtTipoProducao,1,0,1,1)
      label = new JLabel("Identificador")
      adicionarComponente(pnlAreaDados,label,2,0,1,1)
      try
      {
        txtIdentificador = new JTextField("" + tipoProducao.obterUltimoIdentificador(aplicacao.obterConexao()),10)
      }
    catch(Exception e) {
      e.printStackTrace()
    }
    adicionarComponente(pnlAreaDados,txtIdentificador,3,0,1,1)
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

      gridbag.setConstraints(c,gbc)
      painel.add(c)
  }

  void actionPerformed(java.awt.event.ActionEvent actionEvent) {
    Object objeto = actionEvent.getSource()

      if(objeto == btConfirmar) {
        tipoProducao.definirCodigo(Integer.parseInt(txtIdentificador.getText()))
          tipoProducao.definirTipoProducao(txtTipoProducao.getText())
          tipoProducao.cadastrarTipoProducao()
          this.setVisible(false)
      }

    if(objeto == btCancelar) {
      this.setVisible(false)
    }
  }

  void focusLost(java.awt.event.FocusEvent focusEvent) {

  }

  void focusGained(java.awt.event.FocusEvent e) {

  }
}
