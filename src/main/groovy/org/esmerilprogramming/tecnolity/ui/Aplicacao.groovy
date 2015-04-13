package org.esmerilprogramming.tecnolity.ui

import javax.swing.*
import java.awt.*
import java.awt.event.*

class Aplicacao extends JFrame implements WindowListener {

  Aplicacao(final String tituloJanela) {
    super(tituloJanela)
    this.addWindowListener(this)
  }

  void maximizar() {
    Dimension tela = Toolkit.getDefaultToolkit().getScreenSize()
    this.setBounds(0, 0, tela.width, tela.height - 28)
  }

  void windowOpened(final WindowEvent evento) {
  }

  void windowClosing(final WindowEvent evento) {
    this.finalizarAplicacao()
  }

  void windowClosed(final WindowEvent evento) {
  }

  void windowIconified(final WindowEvent evento) {
  }

  void windowDeiconified(final WindowEvent evento) {
  }

  void windowActivated(final WindowEvent evento) {
  }

  void windowDeactivated(final WindowEvent evento) {
  }
}
