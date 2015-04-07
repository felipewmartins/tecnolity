package org.esmerilprogramming.tecnolity.aplicacao

import java.awt.*
import java.awt.event.*
import javax.swing.*
import org.esmerilprogramming.tecnolity.administracao.*
import org.esmerilprogramming.tecnolity.administracao.ui.*

class FormAutenticacao extends JDialog implements ActionListener {

  private JButton btAcessar, btCancelar
  private JTextField txtUsuario
  private JPasswordField txtSenha

  private GridBagLayout gridbag
  private GridBagConstraints gbc

  private Aplicacao aplicacao

  FormAutenticacao(Aplicacao aplicacao) {
    super(aplicacao,true)
    this.aplicacao = aplicacao
    Container conteudo = this.getContentPane()
    conteudo.setLayout(new BorderLayout())
    JLabel lblImagem = new JLabel(new ImageIcon('imagens/splash.gif'))
    lblImagem.setSize(400,122)
    conteudo.add(lblImagem, BorderLayout.NORTH)

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize()
    setBounds(screenSize.width/2 - 200 as int, screenSize.height/2 - 150 as int, 410, 300)

    gridbag = new GridBagLayout()
    gbc = new GridBagConstraints()
    gbc.fill = GridBagConstraints.NONE
    gbc.anchor = GridBagConstraints.NORTHWEST
    gbc.insets.bottom = 2
    gbc.insets.left = 2
    gbc.insets.right = 2
    gbc.insets.top = 2

    JPanel pnlAcesso = new JPanel(gridbag)
    JLabel lblTexto = new JLabel('Usuário: ')
    adicionarComponente(pnlAcesso, lblTexto, 0, 0, 1, 1)
    txtUsuario = new JTextField(10)
    txtUsuario.addActionListener(this)
    adicionarComponente(pnlAcesso, txtUsuario, 0, 1, 1, 1)

    lblTexto = new JLabel('Senha: ')
    adicionarComponente(pnlAcesso, lblTexto, 1, 0, 1, 1)
    txtSenha = new JPasswordField(10)
    txtSenha.addActionListener(this)
    adicionarComponente(pnlAcesso, txtSenha, 1, 1, 1, 1)

    conteudo.add(pnlAcesso, BorderLayout.CENTER)

    JPanel pnlComandos = new JPanel(new FlowLayout(FlowLayout.RIGHT))
    btAcessar = new JButton('Acessar')
    btAcessar.addActionListener(this)
    pnlComandos.add(btAcessar)
    btCancelar = new JButton('Cancelar')
    btCancelar.addActionListener(this)
    pnlComandos.add(btCancelar)
    conteudo.add(pnlComandos, BorderLayout.SOUTH)
  }

  private void adicionarComponente(JPanel painel, Component c, int linha, int coluna, int largura, int altura) {
    gbc.gridx = coluna
    gbc.gridy = linha
    gbc.gridwidth = largura
    gbc.gridheight = altura
    gridbag.setConstraints(c,gbc)
    painel.add(c)
  }

  private void verificarAutenticacao() {
    try {
      Colaborador colaborador = new Colaborador(txtUsuario.getText(), txtSenha.getPassword() as String)
      if (colaborador.autenticarColaborador()) {
        aplicacao.definirColaborador(colaborador)
        this.setVisible(false)
      }
        else
        {
          if(colaborador.colaboradorExiste()) {
            JOptionPane.showMessageDialog(aplicacao,'Usuário ou senha inválida!','Problema de Autenticação',JOptionPane.WARNING_MESSAGE)
            this.txtUsuario.transferFocus()
            this.txtUsuario.selectAll()
          }
          else
          {
            DlgDadosColaborador dlgDadosColaborador = new DlgDadosColaborador(aplicacao,'I')
              dlgDadosColaborador.setVisible(true)
          }
        }
    } catch(e) {
      JOptionPane.showMessageDialog(aplicacao,'Erro: ' + e.getMessage(),'Erro',JOptionPane.ERROR_MESSAGE)
      e.printStackTrace()
    }
  }

  void actionPerformed(java.awt.event.ActionEvent actionEvent) {
    Object objeto = actionEvent.getSource()

      if(objeto == txtUsuario) {
        verificarAutenticacao()
      }

    if(objeto == txtSenha) {
      verificarAutenticacao()
    }
    if(objeto == btAcessar) {
      verificarAutenticacao()
    }

    if(objeto == btCancelar) {
      aplicacao.finalizarAplicacao()
    }
  }
}
