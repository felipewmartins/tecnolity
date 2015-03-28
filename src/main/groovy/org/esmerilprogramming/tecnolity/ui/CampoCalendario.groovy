package org.esmerilprogramming.ui

import org.esmerilprogramming.util.*
import javax.swing.*
import java.awt.*
import java.awt.event.*

class CampoCalendario extends JPanel implements ActionListener, KeyListener, FocusListener
{
  private JButton btAbrirCalendario
    private CampoTexto txtData
    private Calendario calendario
    private String formato
    private int tamanho
    private int tamanhoMaximo
    private int numeroCaracteres
    private JFrame aplicacao
    private boolean digitado

    CampoCalendario(final JFrame aplicacao, final String formato, final int tamanho) {
      this.tamanhoMaximo = 10
        this.aplicacao = aplicacao
        this.formato = formato
        this.tamanho = tamanho
        this.calendario = new Calendario()
        this.montarInterface()
        this.digitado = true
    }

  CampoCalendario(final JFrame aplicacao, final Calendario calendario, final String formato, final int tamanho) {
    this.tamanhoMaximo = 10
      this.aplicacao = aplicacao
      this.formato = formato
      this.tamanho = tamanho
      this.calendario = calendario
      this.montarInterface()
      this.txtData.setText(this.calendario.get(formato))
      this.digitado = true
  }

  Calendario getData() {
    if (this.digitado) {
      if (!this.txtData.getText().equals("dd/mm/aaaa")) {
        this.calendario = Calendario.stringParaCalendario(String.valueOf(this.txtData.getText()) + " 0:0")
      }
      else {
        this.calendario = null
      }
    }
    return this.calendario
  }

  void setData(final Calendario calendario) {
    this.calendario = calendario
      if (calendario != null) {
        this.txtData.setText(calendario.get("dd/MM/yyyy"))
      }
      else {
        this.txtData.setText("dd/mm/aaaa")
      }
  }

  void montarInterface() {
    this.setLayout(new FlowLayout(0))
      (this.txtData = new CampoTexto("dd/mm/aaaa", this.tamanho, this.tamanhoMaximo)).addKeyListener(this)
      this.txtData.addFocusListener(this)
      this.add(this.txtData)
      (this.btAbrirCalendario = new JButton(new ImageIcon("imagens/calendario_icone.gif"))).setPreferredSize(new Dimension(20, 20))
      this.btAbrirCalendario.addActionListener(this)
      this.add(this.btAbrirCalendario)
  }

  void actionPerformed(final ActionEvent evento) {
    final DlgCalendario dlgCalendario = new DlgCalendario(this.aplicacao, this.calendario)
      dlgCalendario.setVisible(true)
      this.digitado = false
      this.txtData.setText(this.calendario.get(this.formato))
  }

  void keyPressed(final KeyEvent evento) {
    if (evento.getKeyCode() == 8) {
      if (this.numeroCaracteres > 0) {
        --this.numeroCaracteres
      }
    }
    else if (this.numeroCaracteres < this.tamanhoMaximo) {
      ++this.numeroCaracteres
    }
  }

  void keyReleased(final KeyEvent evento) {
    this.digitado = true
      if (evento.getKeyCode() == 8) {
        return
      }
    if (evento.getKeyChar() == '/') {
      this.txtData.setText(this.txtData.getText().substring(0, this.txtData.getText().length() - 1))
        --this.numeroCaracteres
    }
    if (this.txtData.getText().length() == 2 || this.txtData.getText().length() == 5) {
      this.txtData.setText(String.valueOf(this.txtData.getText()) + "/")
        ++this.numeroCaracteres
        this.txtData.setCaretPosition(this.txtData.getText().length())
    }
  }

  void keyTyped(final KeyEvent evento) {
  }

  void focusGained(final FocusEvent focusEvent) {
    if (this.txtData.getText().equals("dd/mm/aaaa")) {
      this.txtData.setText("")
    }
    else {
      this.txtData.setSelectionStart(0)
        this.txtData.setSelectionEnd(this.txtData.getText().length())
    }
  }

  void focusLost(final FocusEvent focusEvent) {
    if (this.txtData.getText().trim().equals("")) {
      this.txtData.setText("dd/mm/aaaa")
    }
  }

  void setEnabled(final boolean enabled) {
    this.txtData.setEnabled(enabled)
      this.btAbrirCalendario.setEnabled(enabled)
  }
}
