package org.esmerilprogramming.tecnolity.administracao.ui

import java.awt.*
import java.awt.event.*
import javax.swing.*
import org.esmerilprogramming.tecnolity.administracao.*
import org.esmerilprogramming.tecnolity.aplicacao.Aplicacao

class DlgDadosPais extends JDialog implements ActionListener {
  final int IDENTIFICADOR = 23

    private Aplicacao aplicacao
    private char modo // I = inserir, A = alterar, V = visualizar, E = Excluir
    private Container conteudo
    private GridBagLayout gridbag
    private GridBagConstraints gbc
    private JTextField txtNomePais, txtSiglaPais
    private JButton btConfirmar, btCancelar

    DlgDadosPais(Aplicacao aplicacao, char modo) {
      super(aplicacao,true)

        // Define o título da janela
        String tituloJanela = ''
        if (modo == 'I')
          tituloJanela = 'Nova País'
            if (modo == 'A')
              tituloJanela = 'País'
                if (modo == 'V')
                  tituloJanela = 'País'
                    this.setTitle(tituloJanela)

                    this.aplicacao = aplicacao
                    this.modo = modo

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
                    JLabel label = new JLabel('Nome do Pais')
                    adicionarComponente(pnlDados,label,0,0,1,1)
                    txtNomePais = new JTextField(20)
                    adicionarComponente(pnlDados,txtNomePais,1,0,1,1)
                    label = new JLabel('Sigla')
                    adicionarComponente(pnlDados,label,2,0,1,1)
                    txtSiglaPais = new JTextField(3)
                    adicionarComponente(pnlDados,txtSiglaPais,3,0,1,1)
                    this.conteudo.add(pnlDados, BorderLayout.CENTER)

                    JPanel pnlComandos = new JPanel(new FlowLayout(FlowLayout.RIGHT))
                    btConfirmar = new JButton('Confirmar')
                    btConfirmar.addActionListener(this)
                    pnlComandos.add(btConfirmar)
                    btCancelar = new JButton('Cancelar')
                    btCancelar.addActionListener(this)
                    pnlComandos.add(btCancelar)
                    this.conteudo.add(pnlComandos, BorderLayout.SOUTH)

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
          Pais pais = new Pais(txtSiglaPais.getText(),txtNomePais.getText())
            pais.cadastrarPais()
            this.setVisible(false)
        }
        catch(Exception e) {
          JOptionPane.showMessageDialog(aplicacao,'Erro: ' + e.getMessage(),'Erro',JOptionPane.WARNING_MESSAGE)
            e.printStackTrace()
        }
      }

    if(objeto == btCancelar) {
      this.setVisible(false)
    }
  }
}
