package org.esmerilprogramming.tecnolity.logistica.ui

import java.awt.*
import java.util.*
import java.awt.event.*
import javax.swing.*

import org.esmerilprogramming.tecnolity.aplicacao.Aplicacao
import org.esmerilprogramming.tecnolity.logistica.*
import org.esmerilprogramming.tecnolity.util.*

public class DlgDadosDespesa extends JDialog implements ActionListener, FocusListener
{
  public final int IDENTIFICADOR = 13

  private Aplicacao aplicacao
  private char modo // I = inserir, A = alterar, V = visualizar
  private Vector veiculos, datasDespesas
  private GridBagLayout gridbag
  private GridBagConstraints gbc
  private Veiculo veiculo = new Veiculo()
  private Despesa despesa
  private Calendario calendario
  private int codigoDespesa
  private String dataDespesa

  private JPanel pnlDadosDespesa
  private JComboBox cbxPlaca
  private JTextField txtValor, txtData
  private JTextArea txaDescricao
  private JButton btNovoVeiculo

  private Container conteudo
  private JPanel pnlAreaDados
  private JButton btConfirmar, btCancelar

  public DlgDadosDespesa(Aplicacao aplicacao, char modo)
  {
    super(aplicacao,true)
    despesa = new Despesa()

    // Define o título da janela
    String tituloJanela = ""
    if (modo == 'I')
      tituloJanela = "Nova Despesa"
    this.setTitle(tituloJanela)

    this.aplicacao = aplicacao
    this.modo = modo

    calendario = new Calendario()
    dataDespesa = calendario.dataHoje("dd/MM/yyyy")

    montarInterface()
  }

  public DlgDadosDespesa(Aplicacao aplicacao, char modo, String placa, String data)
  {
    super(aplicacao,true)
    try
    {
      despesa = new Despesa(placa,data,aplicacao.obterConexao())
    }
    catch(Exception e)
    {
      JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar a Despesa.","Erro", JOptionPane.ERROR_MESSAGE)
      e.printStackTrace()
    }

    // Define o título da janela
    String tituloJanela = ""
    if (modo == 'A')
      tituloJanela = "Despesa"
    if (modo == 'V')
      tituloJanela = "Despesa"
    this.setTitle(tituloJanela)

    this.aplicacao = aplicacao
    this.modo = modo

    dataDespesa = this.despesa.obterData()

    montarInterface()
  }

  public void montarInterface()
  {
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
    JLabel label = new JLabel("Placa")
    adicionarComponente(pnlAreaDados,label,0,0,1,1)
    label = new JLabel('Valor (R$)')
    adicionarComponente(pnlAreaDados,label,0,1,1,1)

    JPanel pnlSuporteCombo = new JPanel(new BorderLayout())
    cbxPlaca = new JComboBox()
    try
    {
      veiculos = Veiculo.carregarVeiculos(aplicacao.obterConexao())
      carregarVeiculos()
    }
    catch(Exception e)
    {
      JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar os Veículos. ","Erro", JOptionPane.ERROR_MESSAGE)
      e.printStackTrace()
    }

    int indicePlaca = 0
    for(int i = 1 i < veiculos.size() i++)
    {
      if((((Veiculo)veiculos.get(i)).obterPlaca()).equals((this.despesa)==null?"":this.despesa.obterPlaca()))
      {
        indicePlaca = i
      }
    }
    cbxPlaca.setSelectedIndex(indicePlaca)

    pnlSuporteCombo.add(cbxPlaca,BorderLayout.CENTER)
    btNovoVeiculo = new JButton(new ImageIcon("imagens/novo.jpg"))
    btNovoVeiculo.addActionListener(this)
    btNovoVeiculo.setToolTipText("Novo Veículo")
    btNovoVeiculo.setPreferredSize(new Dimension(22,20))
    pnlSuporteCombo.add(btNovoVeiculo,BorderLayout.EAST)
    adicionarComponente(pnlAreaDados,pnlSuporteCombo,1,0,1,1)

    txtValor = new JTextField(Numero.inverterSeparador(""+this.despesa.obterValor()),8)
    txtValor.addFocusListener(this)
    adicionarComponente(pnlAreaDados,txtValor,1,1,1,1)

    label = new JLabel("Data")
    adicionarComponente(pnlAreaDados,label,2,0,2,1)

    txtData = new JTextField(this.dataDespesa,10)
    txtData.addFocusListener(this)
    adicionarComponente(pnlAreaDados,txtData,3,0,2,1)

    label = new JLabel("Descrição")
    adicionarComponente(pnlAreaDados,label,4,0,2,1)

    txaDescricao = new JTextArea(this.despesa.obterDescricao(),4,20)
    txaDescricao.setLineWrap(true)
    txaDescricao.setWrapStyleWord(true)
    JScrollPane scroll = new JScrollPane(txaDescricao)
    adicionarComponente(pnlAreaDados,scroll,5,0,2,1)

    conteudo.add(pnlAreaDados,BorderLayout.CENTER)

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

  private void adicionarComponente(JPanel painel, Component c, int linha, int coluna, int largura, int altura)
  {
    gbc.gridx = coluna
    gbc.gridy = linha

    gbc.gridwidth = largura
    gbc.gridheight = altura

    gridbag.setConstraints(c,gbc)
    painel.add(c)
  }

  private void carregarVeiculos()
  {
    cbxPlaca.removeAllItems()
    cbxPlaca.addItem("Selecione...")

    for(int i = 1i < veiculos.size()i++)
    {
      cbxPlaca.addItem(((Veiculo)veiculos.get(i)).obterPlaca())
    }
  }

  public void actionPerformed(java.awt.event.ActionEvent actionEvent)
  {
    Object objeto = actionEvent.getSource()

    if(objeto == btNovoVeiculo)
    {
      DlgDadosVeiculo dlgDadosVeiculo = new DlgDadosVeiculo(aplicacao)
      dlgDadosVeiculo.setVisible(true)
      try
      {
        veiculos = Veiculo.carregarVeiculos(aplicacao.obterConexao())
        carregarVeiculos()
      }
      catch(Exception e)
      {
        JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar os Veículos","Erro", JOptionPane.ERROR_MESSAGE)
        e.printStackTrace()
      }
    }

    if(objeto == btCancelar)
    {
      this.setVisible(false)
    }

    if(objeto == btConfirmar)
    {
      try
      {
        if(modo == 'I')
        {
          boolean confirmado = true
          try
          {
            despesa.definirPlaca(((Veiculo)veiculos.get(this.cbxPlaca.getSelectedIndex()))==null?"":((Veiculo)veiculos.get(this.cbxPlaca.getSelectedIndex())).obterPlaca())
            despesa.definirValor(Float.parseFloat(Numero.inverterSeparador(this.txtValor.getText())))
            despesa.definirData(this.txtData.getText())
            despesa.definirDescricao(this.txaDescricao.getText())
          }
          catch(Exception e)
          {
            JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE)
            e.printStackTrace()
            confirmado = false
          }
          if(confirmado)
          {
            try
            {
              this.despesa.cadastrarDespesa()
            }
            catch(Exception e)
            {
              JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE)
              e.printStackTrace()
            }
            this.setVisible(false)
          }
        }
        else if(modo == 'A')
        {
          boolean confirmado = true
          try
          {
            despesa.definirPlaca(((Veiculo)veiculos.get(this.cbxPlaca.getSelectedIndex()))==null?"":((Veiculo)veiculos.get(this.cbxPlaca.getSelectedIndex())).obterPlaca())
            despesa.definirValor(Float.parseFloat(Numero.inverterSeparador(this.txtValor.getText())))
            despesa.definirData(this.txtData.getText())
            despesa.definirDescricao(this.txaDescricao.getText())
          }
          catch(Exception e)
          {
            JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE)
            e.printStackTrace()
            confirmado = false
          }
          if(confirmado)
          {
            try
            {
              this.despesa.alterarDespesa()
            }
            catch(Exception e)
            {
              JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE)
              e.printStackTrace()
            }
            this.setVisible(false)
          }
        }

      }
      catch(Exception e)
      {
        JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE)
        e.printStackTrace()
      }
    }
  }

  public void focusLost(java.awt.event.FocusEvent focusEvent) {}

  public void focusGained(FocusEvent e)
  {
    Component componente = e.getComponent()

    if(componente == txtValor)
      txtValor.selectAll()
  }
}
