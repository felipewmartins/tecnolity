package org.esmerilprogramming.tecnolity.aplicacao.modelos

import javax.swing.JPanel
import org.esmerilprogramming.tecnolity.administracao.*
import org.esmerilprogramming.tecnolity.aplicacao.*
import org.esmerilprogramming.tecnolity.util.*

class ModeloAreaTrabalho extends JPanel {

  protected char verificarPermissaoAcesso(Interface tela, Colaborador colaborador) {
    Permissao permissao = new Permissao(tela, colaborador)
    permissao.verificarPermissaoAcesso()
  }
}
