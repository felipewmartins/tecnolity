package org.esmerilprogramming.tecnolity.aplicacao

import java.awt.*
import java.awt.event.*
import java.awt.print.PageFormat
import javax.swing.*
import javax.swing.border.*
import org.esmerilprogramming.tecnolity.aplicacao.Aplicacao
import org.esmerilprogramming.tecnolity.util.Configuracao

 class DlgConfiguracaoImpressora extends JDialog implements ActionListener, FocusListener
{
  private Aplicacao aplicacao
  private Container conteudo
  private GridBagLayout gridbag
  private GridBagConstraints gbc
  private JTextField txtMargemEsquerda, txtMargemDireita, txtMargemSuperior, txtMargemInferior
  private JComboBox cbxTamanhoPapel
  private JRadioButton rdbOrientacaoRetrato, rdbOrientacaoPaisagem
  private PainelDesenho pnlAmostra
  private JButton btConfirmar, btCancelar

   DlgConfiguracaoImpressora(Aplicacao aplicacao)
  {
    super(aplicacao,true)

    this.setTitle("Configurações de Impressão")

    this.aplicacao = aplicacao
    this.conteudo = this.getContentPane()
    this.montarInterface()
  }

  private void montarInterface()
  {
    gridbag = new GridBagLayout()
    gbc = new GridBagConstraints()
    gbc.fill = GridBagConstraints.NONE
    gbc.anchor = GridBagConstraints.NORTHWEST
    gbc.insets.bottom = 2
    gbc.insets.left = 2
    gbc.insets.right = 2
    gbc.insets.top = 2

    JPanel pnlDados = new JPanel(gridbag)
    JLabel label = new JLabel("Tamanho do Papel")
    adicionarComponente(pnlDados,label,0,0,1,1)
    cbxTamanhoPapel = new JComboBox()
    cbxTamanhoPapel.setFont(new Font("Arial",Font.PLAIN,12))
    cbxTamanhoPapel.addItem("A4 (210 x 297mm)")
    cbxTamanhoPapel.addItem("Carta (216 x 280 mm)")
    try
    {
      if(Configuracao.getLarguraPapel() == 210 &&
          Configuracao.getAlturaPapel() == 297)
      {
        cbxTamanhoPapel.setSelectedIndex(0)
      }
      else if(Configuracao.getLarguraPapel() == 216 &&
          Configuracao.getAlturaPapel() == 280)
      {
        cbxTamanhoPapel.setSelectedIndex(1)
      }
    }
    catch(NumberFormatException e)
    {
      cbxTamanhoPapel.setSelectedIndex(0)
    }
    cbxTamanhoPapel.addActionListener(this)
    adicionarComponente(pnlDados,cbxTamanhoPapel,1,0,1,1)
    label = new JLabel("Orientação")
    adicionarComponente(pnlDados,label,2,0,1,1)
    JPanel pnlOrientacao = new JPanel()
    ButtonGroup grupo = new ButtonGroup()
    rdbOrientacaoRetrato = new JRadioButton("Retrato")
    rdbOrientacaoRetrato.setFont(new Font("Arial",Font.PLAIN,12))
    grupo.add(rdbOrientacaoRetrato)
    pnlOrientacao.add(rdbOrientacaoRetrato)
    rdbOrientacaoPaisagem = new JRadioButton("Paisagem")
    rdbOrientacaoPaisagem.setFont(new Font("Arial",Font.PLAIN,12))
    try
    {
      if(Configuracao.getOrientacao() == PageFormat.PORTRAIT)
      {
        rdbOrientacaoRetrato.setSelected(true)
      }
      else if(Configuracao.getOrientacao() == PageFormat.LANDSCAPE)
      {
        rdbOrientacaoPaisagem.setSelected(true)
      }
    }
    catch(NumberFormatException e)
    {
      rdbOrientacaoRetrato.setSelected(true)
    }

    rdbOrientacaoRetrato.addActionListener(this)
    rdbOrientacaoPaisagem.addActionListener(this)
    grupo.add(rdbOrientacaoPaisagem)
    pnlOrientacao.add(rdbOrientacaoPaisagem)
    adicionarComponente(pnlDados,pnlOrientacao,3,0,1,1)
    label = new JLabel("Margens")
    adicionarComponente(pnlDados,label,4,0,1,1)
    JPanel pnlMargens = new JPanel(new GridLayout(2,4,5,5))
    label = new JLabel("Esquerda")
    label.setFont(new Font("Arial",Font.PLAIN,12))
    pnlMargens.add(label)
    txtMargemEsquerda = new JTextField("" + Configuracao.getMargemEsquerda(),2)
    txtMargemEsquerda.addFocusListener(this)
    pnlMargens.add(txtMargemEsquerda)
    label = new JLabel("Direita")
    label.setFont(new Font("Arial",Font.PLAIN,12))
    pnlMargens.add(label)
    txtMargemDireita = new JTextField("" + Configuracao.getMargemDireita(),2)
    txtMargemDireita.addFocusListener(this)
    pnlMargens.add(txtMargemDireita)
    label = new JLabel("Superior")
    label.setFont(new Font("Arial",Font.PLAIN,12))
    pnlMargens.add(label)
    txtMargemSuperior = new JTextField("" + Configuracao.getMargemSuperior(),2)
    txtMargemSuperior.addFocusListener(this)
    pnlMargens.add(txtMargemSuperior)
    label = new JLabel("Inferior")
    label.setFont(new Font("Arial",Font.PLAIN,12))
    pnlMargens.add(label)
    txtMargemInferior = new JTextField("" + Configuracao.getMargemInferior(),2)
    txtMargemInferior.addFocusListener(this)
    pnlMargens.add(txtMargemInferior)
    adicionarComponente(pnlDados,pnlMargens,5,0,1,1)
    pnlAmostra = new PainelDesenho(111,157,0,0,0,0,1)
    pnlAmostra.setPreferredSize(new Dimension(180,180))
    pnlAmostra.setBorder(new TitledBorder(" Amostra "))
    adicionarComponente(pnlDados,pnlAmostra,0,1,1,6)
    this.conteudo.add(pnlDados, BorderLayout.CENTER)

    JPanel pnlComandos = new JPanel(new FlowLayout(FlowLayout.RIGHT))
    btConfirmar = new JButton("Confirmar")
    btConfirmar.addActionListener(this)
    pnlComandos.add(btConfirmar)
    btCancelar = new JButton("Fechar")
    btCancelar.addActionListener(this)
    pnlComandos.add(btCancelar)
    this.conteudo.add(pnlComandos, BorderLayout.SOUTH)

    this.pack()

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize()
    this.setBounds((screenSize.width/2) - (this.getBounds().width/2),
        (screenSize.height/2) - (this.getBounds().height/2) - 30,
        this.getBounds().width,
        this.getBounds().height)
    desenharPapel()
  }

  private void adicionarComponente(JPanel painel, Component c, int linha, int coluna, int largura, int altura)
  {
    gbc.gridx = coluna
    gbc.gridy = linha

    gbc.gridwidth = largura
    gbc.gridheight = altura

    gridbag.setConstraints(c,gbc)
    painel.add(c)
  }

  private void desenharPapel()
  {
    switch(cbxTamanhoPapel.getSelectedIndex())
    {
      case 0:
        pnlAmostra.setLargura(111)
        pnlAmostra.setAltura(157)
        break
      case 1:
        pnlAmostra.setLargura(114)
        pnlAmostra.setAltura(148)
        break
    }

    if(rdbOrientacaoRetrato.isSelected())
    {
      pnlAmostra.setOrientacao(PageFormat.PORTRAIT)
    }else if(rdbOrientacaoPaisagem.isSelected())
    {
      pnlAmostra.setOrientacao(PageFormat.LANDSCAPE)
    }

    if(!txtMargemEsquerda.getText().equals(""))
      pnlAmostra.setMargemEsquerda(Integer.parseInt(txtMargemEsquerda.getText()))

    if(!txtMargemDireita.getText().equals(""))
      pnlAmostra.setMargemDireita(Integer.parseInt(txtMargemDireita.getText()))

    if(!txtMargemSuperior.getText().equals(""))
      pnlAmostra.setMargemSuperior(Integer.parseInt(txtMargemSuperior.getText()))

    if(!txtMargemInferior.getText().equals(""))
      pnlAmostra.setMargemInferior(Integer.parseInt(txtMargemInferior.getText()))

    pnlAmostra.repaint()
  }

   void actionPerformed(java.awt.event.ActionEvent actionEvent)
  {
    Object objeto = actionEvent.getSource()

    if(objeto == cbxTamanhoPapel)
    {
      desenharPapel()
    }

    if(objeto == rdbOrientacaoRetrato)
    {
      desenharPapel()
    }

    if(objeto == rdbOrientacaoPaisagem)
    {
      desenharPapel()
    }

    if(objeto == btConfirmar)
    {
      if(cbxTamanhoPapel.getSelectedIndex() == 0)
      {
        this.aplicacao.obterConfiguracao().setLarguraPapel(210)
        this.aplicacao.obterConfiguracao().setAlturaPapel(297)
      }
      else if(cbxTamanhoPapel.getSelectedIndex() == 1)
      {
        this.aplicacao.obterConfiguracao().setLarguraPapel(216)
        this.aplicacao.obterConfiguracao().setAlturaPapel(280)
      }
      try
      {
        this.aplicacao.obterConfiguracao().setOrientacao(rdbOrientacaoRetrato.isSelected()?1:0)
        this.aplicacao.obterConfiguracao().setMargemEsquerda(Integer.parseInt(txtMargemEsquerda.getText()))
        this.aplicacao.obterConfiguracao().setMargemDireita(Integer.parseInt(txtMargemDireita.getText()))
        this.aplicacao.obterConfiguracao().setMargemSuperior(Integer.parseInt(txtMargemSuperior.getText()))
        this.aplicacao.obterConfiguracao().setMargemInferior(Integer.parseInt(txtMargemInferior.getText()))
        this.aplicacao.obterConfiguracao().salvarConfiguracao()
      }
      catch(NumberFormatException e)
      {
        JOptionPane.showMessageDialog(aplicacao,"Erro: Informe somente números inteiros nas margens da página.","Erro",JOptionPane.ERROR_MESSAGE)
        e.printStackTrace()
      }
      this.setVisible(false)
    }

    if(objeto == btCancelar)
    {
      this.setVisible(false)
    }
  }

   void focusLost(FocusEvent evento)
  {
    desenharPapel()
  }

   void focusGained(FocusEvent evento)
  {

  }
}

class PainelDesenho extends JPanel
{
  int altura, largura, margemEsquerda, margemDireita, margemSuperior, margemInferior, orientacao

   PainelDesenho(int largura,int altura,int margemEsquerda,int margemDireita,int margemSuperior,int margemInferior,int orientacao)
  {
    this.setLargura(largura)
    this.setAltura(altura)
    this.setMargemEsquerda(margemEsquerda)
    this.setMargemDireita(margemDireita)
    this.setMargemSuperior(margemSuperior)
    this.setMargemInferior(margemInferior)
    this.setOrientacao(orientacao)
  }

   void paintComponent(Graphics g)
  {
    super.paintComponent(g)
    if(orientacao == 1)
    {
      g.setColor(Color.white)
      g.fillRect(((this.getWidth()/2) - (largura/2)),16,largura,altura)
      g.setColor(Color.BLACK)
      g.drawRect(((this.getWidth()/2) - (largura/2)) + margemEsquerda,
          16 + margemSuperior,
          largura - margemDireita - margemEsquerda,
          altura - margemInferior - margemSuperior)
    }
    else if(orientacao == 0)
    {
      g.setColor(Color.WHITE)
      g.fillRect(((this.getWidth()/2) - (altura/2)),16,altura,largura)
      g.setColor(Color.BLACK)
      g.drawRect(((this.getWidth()/2) - (altura/2)) + margemEsquerda,
          16 + margemSuperior,
          altura - margemDireita - margemEsquerda,
          largura - margemInferior - margemSuperior)
    }
  }

   void setAltura(int altura)
  {
    this.altura = altura
  }

   void setLargura(int largura)
  {
    this.largura = largura
  }

   void setMargemEsquerda(int margemEsquerda)
  {
    this.margemEsquerda = margemEsquerda
  }

   void setMargemDireita(int margemDireita)
  {
    this.margemDireita = margemDireita
  }

   void setMargemSuperior(int margemSuperior)
  {
    this.margemSuperior = margemSuperior
  }

   void setMargemInferior(int margemInferior)
  {
    this.margemInferior = margemInferior
  }

   void setOrientacao(int orientacao)
  {
    this.orientacao = orientacao
  }
}
