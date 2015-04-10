package org.esmerilprogramming.tecnolity.ui

import org.esmerilprogramming.tecnolity.util.*
import javax.swing.*
import javax.swing.border.*
import java.awt.*
import java.awt.event.*

class DlgCalendario extends JDialog implements ActionListener
{
  private JButton btMesAnterior
  private JButton btMesPosterior
  private JButton[][] btDias
  private JLabel lblNomeMes
  private JLabel lblDataSelecionada
  private Container conteudo
  private GridBagLayout gridbag
  private GridBagConstraints gbc
  private Calendario calendario
  private JPanel pnlDias

  DlgCalendario(final JFrame aplicacao, final Calendario calendario) {
    super(aplicacao, true)
    this.btDias = new JButton[6][7]
    this.calendario = calendario
    this.conteudo = this.getContentPane()
    this.gridbag = new GridBagLayout()
    this.gbc = new GridBagConstraints()
    this.gbc.fill = 10
    this.gbc.anchor = 10
    this.gbc.insets.bottom = 0
    this.gbc.insets.left = 0
    this.gbc.insets.right = 0
    this.gbc.insets.top = 0
    final JPanel pnlCabecalho = new JPanel(new BorderLayout())
    (this.btMesAnterior = new JButton(new ImageIcon("imagens/seta_contraido_esquerda.gif"))).setToolTipText("M\u00eas Anterior")
    this.btMesAnterior.setPreferredSize(new Dimension(20, 20))
    this.btMesAnterior.addActionListener(this)
    pnlCabecalho.add(this.btMesAnterior, "West")
    final JPanel pnlNomeMes = new JPanel()
    this.lblNomeMes = new JLabel()
    this.imprimirMesAno()
    this.lblNomeMes.setFont(new Font("Arial", 1, 16))
    pnlNomeMes.add(this.lblNomeMes)
    pnlCabecalho.add(pnlNomeMes, "Center")
    (this.btMesPosterior = new JButton(new ImageIcon("imagens/seta_contraido_direita.gif"))).setToolTipText("Pr\u00f3ximo M\u00eas")
    this.btMesPosterior.setPreferredSize(new Dimension(20, 20))
    this.btMesPosterior.addActionListener(this)
    pnlCabecalho.add(this.btMesPosterior, "East")
    this.conteudo.add(pnlCabecalho, "North")
    final JPanel pnlCalendario = new JPanel(this.gridbag)
    this.adicionarComponente(pnlCalendario, new JLabel(" Dom "), 0, 0, 1, 1)
    this.adicionarComponente(pnlCalendario, new JLabel(" Seg "), 0, 1, 1, 1)
    this.adicionarComponente(pnlCalendario, new JLabel("  Ter "), 0, 2, 1, 1)
    this.adicionarComponente(pnlCalendario, new JLabel("  Qua "), 0, 3, 1, 1)
    this.adicionarComponente(pnlCalendario, new JLabel("  Qui "), 0, 4, 1, 1)
    this.adicionarComponente(pnlCalendario, new JLabel("  Sex "), 0, 5, 1, 1)
    this.adicionarComponente(pnlCalendario, new JLabel("  Sab "), 0, 6, 1, 1)
    (this.pnlDias = new JPanel(this.gridbag)).setBorder(new LineBorder(Color.black))
    this.imprimirCalendario()
    this.adicionarComponente(pnlCalendario, this.pnlDias, 1, 0, 7, 1)
    this.conteudo.add(pnlCalendario, "Center")
    final JPanel pnlDataSelecionada = new JPanel(new FlowLayout(0))
    this.lblDataSelecionada = new JLabel()
    this.imprimirDataSelecionada()
    pnlDataSelecionada.add(this.lblDataSelecionada)
    this.conteudo.add(pnlDataSelecionada, "South")
    this.dimencionar()
  }

  private void imprimirMesAno() {
    if (this.calendario != null) {
      this.lblNomeMes.setText(String.valueOf(this.calendario.getMesPorExtenso())  +  ", " + this.calendario.get("yyyy"))
    }
  }

  private void imprimirCalendario() {
    Calendario calendarioAux = new Calendario(this.calendario.get(1), this.calendario.get(2)  +  1, 1)
    int j = calendarioAux.get(7) - 1
    calendarioAux = null
    int indiceDia = 1
    this.pnlDias.removeAll()
    this.pnlDias.updateUI()
    for (int i = 0 ; i < 6 ; ++i) {
      for (j = 0;  j < 7;  ++j) {
        if (indiceDia <= this.calendario.getNumeroDiasMes()) {
          (this.btDias[i][j] = new JButton(""  +  indiceDia)).setMargin(new Insets(0, 0, 0, 0))
          if (indiceDia == this.calendario.get(5)) {
            this.btDias[i][j].setBackground(Color.gray)
          }
          ++indiceDia
          this.btDias[i][j].setBorderPainted(false)
          this.btDias[i][j].setPreferredSize(new Dimension(30, 25))
          this.btDias[i][j].setToolTipText("Selecione a data.")
          this.btDias[i][j].addActionListener(this)
          this.adicionarComponente(this.pnlDias, this.btDias[i][j], i, j, 1, 1)
        }
      }
      j = 0
    }
  }

  private void imprimirDataSelecionada() {
    this.lblDataSelecionada.setText("Data Selecionada: "  +  this.calendario.get("dd/MM/yyyy"))
  }

  protected void dimencionar() {
    this.pack()
    final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize()
    this.setBounds(screenSize.width / 2 - this.getBounds().width / 2, screenSize.height / 2 - this.getBounds().height / 2 - 30, this.getBounds().width, this.getBounds().height)
  }

  protected void adicionarComponente(final JPanel painel, final Component c, final int linha, final int coluna, final int largura, final int altura) {
    this.gbc.gridx = coluna
    this.gbc.gridy = linha
    this.gbc.gridwidth = largura
    this.gbc.gridheight = altura
    this.gridbag.setConstraints(c, this.gbc)
    painel.add(c)
  }

  void actionPerformed(final ActionEvent actionEvent) {
    final Object objeto = actionEvent.getSource()
    if (objeto == this.btMesAnterior) {
      if (this.calendario.get(2) == 0) {
        this.calendario.set(1, this.calendario.get(1) - 1)
        this.calendario.set(2, 11)
      }
      else {
        this.calendario.set(2, this.calendario.get(2) - 1)
      }
    }
    if (objeto == this.btMesPosterior) {
      if (this.calendario.get(2) == 11) {
        this.calendario.set(1, this.calendario.get(1)  +  1)
        this.calendario.set(2, 0)
      }
      else {
        this.calendario.set(2, this.calendario.get(2)  +  1)
      }
    }
    if (objeto != this.btMesAnterior && objeto != this.btMesPosterior && objeto instanceof JButton) {
      final JButton btDia = (JButton)objeto
      this.calendario.set(5, Integer.parseInt(btDia.getText()))
      this.setVisible(false)
    }
    this.imprimirMesAno()
    this.imprimirCalendario()
    this.imprimirDataSelecionada()
    this.dimencionar()
  }
}
