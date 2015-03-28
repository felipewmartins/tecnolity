package org.esmerilprogramming.ui

import javax.swing.*
import java.awt.*

class Painel extends JPanel
{
  private GridBagLayout gridbag
  private GridBagConstraints gbc

  Painel() {
    this.gridbag = new GridBagLayout()
    this.gbc = new GridBagConstraints()
    this.gbc.fill = 0
    this.gbc.anchor = 17
    this.gbc.insets.bottom = 2
    this.gbc.insets.left = 2
    this.gbc.insets.right = 2
    this.gbc.insets.top = 2
  }

  protected void adicionarComponente(final JPanel painel, final Component c, final int linha, final int coluna, final int largura, final int altura) {
    this.gbc.gridx = coluna
    this.gbc.gridy = linha
    this.gbc.gridwidth = largura
    this.gbc.gridheight = altura
    this.gridbag.setConstraints(c, this.gbc)
    painel.add(c)
  }

  protected GridBagLayout getGridbag() {
    return this.gridbag
  }

  protected GridBagConstraints getGbc() {
    return this.gbc
  }
}
