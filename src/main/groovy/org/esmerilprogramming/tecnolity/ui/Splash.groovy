package org.esmerilprogramming.ui

import javax.swing.*
import java.awt.*

class Splash extends JWindow
{
  private JLabel figura

  Splash(final String caminhoFigura) {
    final Container conteudo = this.getContentPane()
    conteudo.add(this.figura = new JLabel(new ImageIcon(caminhoFigura)), "Center")
    this.dimencionar(400, 260)
  }

  void dimencionar(final int largura, final int altura) {
    final Dimension tela = Toolkit.getDefaultToolkit().getScreenSize()
    this.setBounds((tela.width - largura) / 2, (tela.height - altura) / 2, largura, altura)
  }
}
