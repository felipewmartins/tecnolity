package org.esmerilprogramming.tecnolity.aplicacao.modelos

import javax.swing.*
import org.esmerilprogramming.tecnolity.administracao.*
import org.esmerilprogramming.tecnolity.aplicacao.*
import org.esmerilprogramming.tecnolity.util.*

class ModeloAreaTrabalho extends JPanel
{
  ModeloAreaTrabalho() {}

  protected char verificarPermissaoAcesso(Interface tela, Colaborador colaborador, Conexao conexao) {
    Permissao permissao = new Permissao(tela, colaborador)
      return permissao.verificarPermissaoAcesso(conexao)
  }
}
