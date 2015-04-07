package org.esmerilprogramming.ui

import javax.swing.*
import java.awt.*

class Painel extends JPanel {
  private GridBagLayout gridbag
  private GridBagConstraints gbc

  Painel() {
    gridbag = new GridBagLayout()
    gbc = new GridBagConstraints()
    gbc.fill = 0
    gbc.anchor = 17
    gbc.insets.bottom = 2
    gbc.insets.left = 2
    gbc.insets.right = 2
    gbc.insets.top = 2
  }

  protected void adicionarComponente(final JPanel painel, final Component c, final int linha, final int coluna, final int largura, final int altura) {
    gbc.gridx = coluna
    gbc.gridy = linha
    gbc.gridwidth = largura
    gbc.gridheight = altura
    gridbag.setConstraints(c, this.gbc)
    painel.add(c)
  }

  protected GridBagLayout getGridbag() {
    gridbag
  }

  protected GridBagConstraints getGbc() {
    gbc
  }
}
