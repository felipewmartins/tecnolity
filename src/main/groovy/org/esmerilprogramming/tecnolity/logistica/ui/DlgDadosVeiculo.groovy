package org.esmerilprogramming.tecnolity.logistica.ui

import java.awt.*
import java.awt.event.*
import javax.swing.*
import javax.swing.border.*
import org.esmerilprogramming.tecnolity.aplicacao.Aplicacao
import org.esmerilprogramming.tecnolity.logistica.*
import org.esmerilprogramming.tecnolity.util.*

class DlgDadosVeiculo  extends JDialog implements ActionListener
{
  final int IDENTIFICADOR = 32

    private Aplicacao aplicacao
    private Veiculo veiculo
    private Transportadora transportadora
    private Vector veiculos, transportadoras
    private GridBagLayout gridbag
    private GridBagConstraints gbc

    // Objetos do painel de dados do veículo
    private JTextField txtPlaca, txtChassi, txtRenavam, txtCor,
            txtAno, txtMarca, txtModelo, txtNumeroEixo, txtCubagem,
            txtTara, txtPesoBruto, txtCombustivel, txtVolumeCombustivel,
            txtMediaConsumo
              private JComboBox cbxTransportadora
              private JButton btNovaTransportadora
              private Container conteudo
              private JButton btConfirmar, btCancelar

              DlgDadosVeiculo(Aplicacao aplicacao) {
                super(aplicacao, true)

                  this.setTitle("Veículo")
                  this.aplicacao = aplicacao
                  montarInterface()
              }

  DlgDadosVeiculo(Aplicacao aplicacao, Veiculo veiculo) {
    super(aplicacao, true)
      try {
        this.veiculo = veiculo
      }
    catch(Exception e) {
      JOptionPane.showMessageDialog(aplicacao, "Erro: Não foi possível carregar o Veículo.", "Erro", JOptionPane.ERROR_MESSAGE)
        e.printStackTrace()
    }

    this.setTitle("Veículo")
      this.aplicacao = aplicacao
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

      JPanel pnlDadosVeiculo = new JPanel(gridbag)
      pnlDadosVeiculo.setBorder(new TitledBorder("Dados do Veículo"))

      JLabel label = new JLabel("Transportadora")
      adicionarComponente(pnlDadosVeiculo, label, 0, 0, 1, 1)
      label = new JLabel("Placa")
      adicionarComponente(pnlDadosVeiculo, label, 0, 1, 1, 1)
      label = new JLabel("Ano")
      adicionarComponente(pnlDadosVeiculo, label, 0, 2, 2, 1)

      JPanel pnlSuporteCombo = new JPanel(new BorderLayout())
      try {
        this.transportadoras = Transportadora.carregarTransportadoras(aplicacao.obterConexao())
          cbxTransportadora = new JComboBox(this.transportadoras)
          if(veiculo != null) {
            for(int i = 1;i < transportadoras.size();i++) {
              if(((Transportadora)transportadoras.get(i)).obterNome().equals(veiculo.obterTransportadora().obterNome())) {
                cbxTransportadora.setSelectedIndex(i)
                  break
              }
            }
          }
      }
    catch(Exception e) {
      JOptionPane.showMessageDialog(aplicacao, "Erro: Não foi possível carregar as Transportadoras. ", "Erro", JOptionPane.ERROR_MESSAGE)
        e.printStackTrace()
    }

    pnlSuporteCombo.add(cbxTransportadora, BorderLayout.CENTER)
      btNovaTransportadora = new JButton(new ImageIcon("imagens/novo.jpg"))
      btNovaTransportadora.addActionListener(this)
      btNovaTransportadora.setToolTipText("Nova Transportadora")
      btNovaTransportadora.setPreferredSize(new Dimension(22, 20))
      pnlSuporteCombo.add(btNovaTransportadora, BorderLayout.EAST)
      adicionarComponente(pnlDadosVeiculo, pnlSuporteCombo, 1, 0, 1, 1)

      txtPlaca = new JTextField((this.veiculo != null?this.veiculo.obterPlaca():""), 8)
      adicionarComponente(pnlDadosVeiculo, txtPlaca, 1, 1, 1, 1)
      txtAno = new JTextField((this.veiculo != null?this.veiculo.obterAno():""), 4)
      adicionarComponente(pnlDadosVeiculo, txtAno, 1, 2, 1, 1)

      label = new JLabel("Chassi")
      adicionarComponente(pnlDadosVeiculo, label, 2, 0, 1, 1)
      label = new JLabel("Renavam")
      adicionarComponente(pnlDadosVeiculo, label, 2, 1, 1, 1)
      label = new JLabel("Cor")
      adicionarComponente(pnlDadosVeiculo, label, 2, 2, 2, 1)

      txtChassi = new JTextField((this.veiculo==null?"":this.veiculo.obterChassi().trim()), 10)
      adicionarComponente(pnlDadosVeiculo, txtChassi, 3, 0, 1, 1)
      txtRenavam = new JTextField((this.veiculo == null?"":this.veiculo.obterRenavam().trim()), 10)
      adicionarComponente(pnlDadosVeiculo, txtRenavam, 3, 1, 1, 1)
      txtCor = new JTextField((this.veiculo == null?"":this.veiculo.obterCor().trim()), 10)
      adicionarComponente(pnlDadosVeiculo, txtCor, 3, 2, 2, 1)

      label = new JLabel("Marca")
      adicionarComponente(pnlDadosVeiculo, label, 4, 0, 1, 1)
      label = new JLabel("Modelo")
      adicionarComponente(pnlDadosVeiculo, label, 4, 1, 2, 1)

      txtMarca = new JTextField((this.veiculo == null?"":this.veiculo.obterMarca().trim()), 20)
      adicionarComponente(pnlDadosVeiculo, txtMarca, 5, 0, 1, 1)
      txtModelo = new JTextField((this.veiculo == null?"":this.veiculo.obterModelo().trim()), 19)
      adicionarComponente(pnlDadosVeiculo, txtModelo, 5, 1, 2, 1)

      label = new JLabel("Número do Eixo")
      adicionarComponente(pnlDadosVeiculo, label, 6, 0, 1, 1)
      label = new JLabel("Cubagem")
      adicionarComponente(pnlDadosVeiculo, label, 6, 1, 1, 1)
      label = new JLabel("Tara")
      adicionarComponente(pnlDadosVeiculo, label, 6, 2, 1, 1)
      label = new JLabel("Peso Bruto")
      adicionarComponente(pnlDadosVeiculo, label, 6, 3, 1, 1)

      txtNumeroEixo = new JTextField((this.veiculo != null?""  +  this.veiculo.obterNumeroEixo():""), 10)
      adicionarComponente(pnlDadosVeiculo, txtNumeroEixo, 7, 0, 1, 1)
      txtCubagem = new JTextField((this.veiculo != null?Numero.inverterSeparador("" + this.veiculo.obterCubagem()):""), 8)
      adicionarComponente(pnlDadosVeiculo, txtCubagem, 7, 1, 1, 1)
      txtTara = new JTextField((this.veiculo != null?Numero.inverterSeparador("" + this.veiculo.obterTara()):""), 8)
      adicionarComponente(pnlDadosVeiculo, txtTara, 7, 2, 1, 1)
      txtPesoBruto = new JTextField((this.veiculo != null?Numero.inverterSeparador("" + this.veiculo.obterPesoBruto()):""), 8)
      adicionarComponente(pnlDadosVeiculo, txtPesoBruto, 7, 3, 1, 1)

      label = new JLabel("Combustível")
      adicionarComponente(pnlDadosVeiculo, label, 8, 0, 2, 1)
      label = new JLabel("Volume Combustível")
      adicionarComponente(pnlDadosVeiculo, label, 8, 2, 1, 1)
      label = new JLabel("Média de Consumo")
      adicionarComponente(pnlDadosVeiculo, label, 8, 3, 1, 1)

      txtCombustivel = new JTextField((this.veiculo == null?"":this.veiculo.obterCombustivel().trim()), 20)
      adicionarComponente(pnlDadosVeiculo, txtCombustivel, 9, 0, 2, 1)
      txtVolumeCombustivel = new JTextField((this.veiculo != null?Numero.inverterSeparador("" + this.veiculo.obterVolumeCombustivel()):""), 8)
      adicionarComponente(pnlDadosVeiculo, txtVolumeCombustivel, 9, 2, 1, 1)
      txtMediaConsumo= new JTextField((this.veiculo != null?Numero.inverterSeparador("" + this.veiculo.obterMediaConsumo()):""), 8)
      adicionarComponente(pnlDadosVeiculo, txtMediaConsumo, 9, 3, 1, 1)
      conteudo.add(pnlDadosVeiculo, BorderLayout.CENTER)

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

  private void carregarTransportadoras() {
    cbxTransportadora.removeAllItems()
      cbxTransportadora.addItem("Selecione...")

      for(int i = 1;i < transportadoras.size();i++) {
        cbxTransportadora.addItem(((Transportadora)transportadoras.get(i)).obterNome())
      }
    this.pack()
  }


  void actionPerformed(java.awt.event.ActionEvent actionEvent) {
    Object objeto = actionEvent.getSource()

      if(objeto == btNovaTransportadora) {
        DlgDadosTransportadora dlgDadosTransportadora = new DlgDadosTransportadora(aplicacao, 'I')
          dlgDadosTransportadora.setVisible(true)
          try {
            Transportadora transportadora = new Transportadora()
              transportadoras = Transportadora.carregarTransportadoras(aplicacao.obterConexao())
              carregarTransportadoras()
          }
        catch(Exception e) {
          JOptionPane.showMessageDialog(aplicacao, "Erro: Não foi possível carregar as Transportadoras", "Erro", JOptionPane.ERROR_MESSAGE)
            e.printStackTrace()
        }
      }

    if(objeto == btConfirmar) {
      try {
        if(veiculo == null) {
          veiculo = new Veiculo(this.txtPlaca.getText(), (Transportadora)this.cbxTransportadora.getSelectedItem(), this.txtChassi.getText(), this.txtRenavam.getText(),
              this.txtMarca.getText(), this.txtModelo.getText(), this.txtAno.getText(), this.txtCor.getText(),
              Integer.parseInt(this.txtNumeroEixo.getText()), Float.parseFloat(Numero.inverterSeparador(this.txtCubagem.getText())),
              Float.parseFloat(Numero.inverterSeparador(this.txtTara.getText())), Float.parseFloat(Numero.inverterSeparador(this.txtPesoBruto.getText())),
              this.txtCombustivel.getText(), Float.parseFloat(Numero.inverterSeparador(this.txtVolumeCombustivel.getText())), Float.parseFloat(Numero.inverterSeparador(this.txtMediaConsumo.getText())))
            this.veiculo.cadastrarDadosVeiculo()
        }
        else
        {
          this.veiculo.definirTransportadora((Transportadora)this.cbxTransportadora.getSelectedItem())
            this.veiculo.definirPlaca(this.txtPlaca.getText())
            this.veiculo.definirAno(this.txtAno.getText())
            this.veiculo.definirChassi(this.txtChassi.getText())
            this.veiculo.definirRenavam(this.txtRenavam.getText())
            this.veiculo.definirCor(this.txtCor.getText())
            this.veiculo.definirMarca(this.txtMarca.getText())
            this.veiculo.definirModelo(this.txtModelo.getText())
            this.veiculo.definirNumeroEixo(Integer.parseInt(this.txtNumeroEixo.getText()))
            this.veiculo.definirCubagem(Float.parseFloat(Numero.inverterSeparador(this.txtCubagem.getText())))
            this.veiculo.definirTara(Float.parseFloat(Numero.inverterSeparador(this.txtTara.getText())))
            this.veiculo.definirPesoBruto(Float.parseFloat(Numero.inverterSeparador(this.txtPesoBruto.getText())))
            this.veiculo.definirCombustivel(this.txtCombustivel.getText())
            this.veiculo.definirVolumeCombustivel(Float.parseFloat(Numero.inverterSeparador(this.txtVolumeCombustivel.getText())))
            this.veiculo.definirMediaConsumo(Float.parseFloat(Numero.inverterSeparador(this.txtMediaConsumo.getText())))
            this.veiculo.alterarDadosVeiculo()
        }
        this.setVisible(false)
      }
      catch(NumberFormatException n) {
        JOptionPane.showMessageDialog(aplicacao, "Erro: Valor incorreto.", "Erro", JOptionPane.ERROR_MESSAGE)
          n.printStackTrace()
      }
      catch(Exception e) {
        JOptionPane.showMessageDialog(aplicacao, "Erro: "  +  e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
    }

    if(objeto == btCancelar) {
      this.setVisible(false)
    }
  }
}
