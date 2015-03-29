package org.esmerilprogramming.tecnolity.logistica.ui

import java.awt.*
import java.util.*
import java.awt.event.*
import javax.swing.*

import org.esmerilprogramming.tecnolity.aplicacao.Aplicacao
import org.esmerilprogramming.tecnolity.logistica.*
import org.esmerilprogramming.tecnolity.util.*

public class DlgDadosMulta extends JDialog implements ActionListener, FocusListener
{
  public final int IDENTIFICADOR = 22

  private Aplicacao aplicacao
  private Vector veiculos, motoristas
  private GridBagLayout gridbag
  private GridBagConstraints gbc
  private Veiculo veiculo
  private Motorista motorista
  private Multa multa
  private Calendario calendario

  private JPanel pnlDadosMulta
  private JComboBox cbxPlaca, cbxMotorista
  private JTextField txtValor,txtData
  private JTextArea txaMotivo
  private JButton btNovoVeiculo, btNovoMotorista

  private Container conteudo
  private JPanel pnlAreaDados
  private JButton btConfirmar, btCancelar

  public DlgDadosMulta(Aplicacao aplicacao)
  {
    super(aplicacao,true)
    this.setTitle("Multa")

    this.aplicacao = aplicacao

    montarInterface()
  }

  public DlgDadosMulta(Aplicacao aplicacao, Multa multa)
  {
    super(aplicacao,true)
    this.multa = multa

    // Define o título da janela
    this.setTitle("Multa")

    this.aplicacao = aplicacao

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

    try
    {
      veiculos = Veiculo.carregarVeiculos(aplicacao.obterConexao())
      cbxPlaca = new JComboBox(veiculos)
      if(multa != null)
        cbxPlaca.setSelectedItem(multa.obterVeiculo())
    }
    catch(Exception e)
    {
      JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar os Veículos. ","Erro", JOptionPane.ERROR_MESSAGE)
      e.printStackTrace()
    }

    pnlSuporteCombo.add(cbxPlaca,BorderLayout.CENTER)
    btNovoVeiculo = new JButton(new ImageIcon("imagens/novo.jpg"))
    btNovoVeiculo.addActionListener(this)
    btNovoVeiculo.setToolTipText("Novo Veículo")
    btNovoVeiculo.setPreferredSize(new Dimension(22,20))
    pnlSuporteCombo.add(btNovoVeiculo,BorderLayout.EAST)
    adicionarComponente(pnlAreaDados,pnlSuporteCombo,1,0,1,1)

    txtValor = new JTextField(Numero.inverterSeparador(""+this.multa.obterValor()),8)
    txtValor.addFocusListener(this)
    adicionarComponente(pnlAreaDados,txtValor,1,1,1,1)

    label = new JLabel("Motorista")
    adicionarComponente(pnlAreaDados,label,2,0,1,1)
    label = new JLabel("Data")
    adicionarComponente(pnlAreaDados,label,2,1,1,1)

    pnlSuporteCombo = new JPanel(new BorderLayout())
    cbxMotorista = new JComboBox()
    try
    {
      motoristas = motorista.carregarMotoristas(aplicacao.obterConexao())
      carregarMotoristas()
    }
    catch(Exception e)
    {
      JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar os Motoristas.","Erro", JOptionPane.ERROR_MESSAGE)
      e.printStackTrace()
    }

    int indiceMotorista = 0
    for(int i = 1 i < motoristas.size() i++)
    {
      if((((Motorista)motoristas.get(i)).obterCodigo()) == ((this.multa)==null?0:this.multa.obterResponsabilidade()))
      {
        indiceMotorista = i
      }
    }
    cbxMotorista.setSelectedIndex(indiceMotorista)

    pnlSuporteCombo.add(cbxMotorista,BorderLayout.CENTER)
    btNovoMotorista = new JButton(new ImageIcon("imagens/novo.jpg"))
    btNovoMotorista.addActionListener(this)
    btNovoMotorista.setToolTipText("Novo Motorista")
    btNovoMotorista.setPreferredSize(new Dimension(22,20))
    pnlSuporteCombo.add(btNovoMotorista,BorderLayout.EAST)
    adicionarComponente(pnlAreaDados,pnlSuporteCombo,3,0,1,1)

    Calendario calendario = new Calendario()
    txtData = new JTextField(calendario.dataHoje("dd/MM/aaaa"),10)
    txtData.addFocusListener(this)
    adicionarComponente(pnlAreaDados,txtData,3,1,1,1)

    label = new JLabel("Motivo")
    adicionarComponente(pnlAreaDados,label,4,0,2,1)

    txaMotivo = new JTextArea(this.multa.obterMotivo(),4,20)
    txaMotivo.setLineWrap(true)
    txaMotivo.setWrapStyleWord(true)
    JScrollPane scroll = new JScrollPane(txaMotivo)
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

  private void carregarMotoristas()
  {
    cbxMotorista.removeAllItems()
    cbxMotorista.addItem("Selecione...")

    for(int i = 1i < motoristas.size()i++)
    {
      cbxMotorista.addItem(((Motorista)motoristas.get(i)).obterMotorista())
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

    if(objeto == btNovoMotorista)
    {
      DlgDadosMotorista dlgDadosMotorista = new DlgDadosMotorista(aplicacao,'I')
      dlgDadosMotorista.setVisible(true)
      try
      {
        Motorista motorista = new Motorista()
        motoristas = motorista.carregarMotoristas(aplicacao.obterConexao())
        carregarMotoristas()
      }
      catch(Exception e)
      {
        JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar os Motoristas","Erro", JOptionPane.ERROR_MESSAGE)
        e.printStackTrace()
      }
    }

    if(objeto == btCancelar)
    {
      this.setVisible(false)
    }

    if(objeto == btConfirmar)
    {
      if(multa == null)
      {
        try
        {
          multa = new Multa()
          multa.definirVeiculo((Veiculo)veiculos.get(this.cbxPlaca.getSelectedIndex()))
          multa.definirValor(Float.parseFloat(Numero.inverterSeparador(this.txtValor.getText())))
          multa.definirResponsabilidade(((Motorista)motoristas.get(this.cbxMotorista.getSelectedIndex()))==null?0:((Motorista)motoristas.get(this.cbxMotorista.getSelectedIndex())).obterCodigo())
          multa.definirData(this.txtData.getText())
          multa.definirMotivo(this.txaMotivo.getText())
          this.multa.cadastrarMulta()
        }
        catch(Exception e)
        {
          JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
        }
        this.setVisible(false)
      }
      else
      {
        try
        {
          multa.definirVeiculo(((Veiculo)veiculos.get(this.cbxPlaca.getSelectedIndex())))
          multa.definirValor(Float.parseFloat(Numero.inverterSeparador(this.txtValor.getText())))
          multa.definirResponsabilidade(((Motorista)motoristas.get(this.cbxMotorista.getSelectedIndex()))==null?0:((Motorista)motoristas.get(this.cbxMotorista.getSelectedIndex())).obterCodigo())
          multa.definirData(this.txtData.getText())
          multa.definirMotivo(this.txaMotivo.getText())
          this.multa.alterarMulta()
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

  public void focusLost(java.awt.event.FocusEvent focusEvent) {}

  public void focusGained(FocusEvent e)
  {
    Component componente = e.getComponent()

    if(componente == txtValor)
      txtValor.selectAll()
  }
}
