package org.esmerilprogramming.tecnolity.aplicacao

import java.awt.*
import javax.swing.*
import org.esmerilprogramming.tecnolity.util.*

class BarraStatus extends JPanel {
  private JLabel lblUsuario, lblMensagem, lblDataHoraAcesso
  private Aplicacao aplicacao

  BarraStatus(Aplicacao app) {
    setLayout(new BorderLayout())
    aplicacao = app
    JPanel pnlUsuario = new JPanel()
    lblUsuario =  new JLabel('Usuário: '  +  app.colaborador.matricula)
    pnlUsuario.add(lblUsuario)
    add(pnlUsuario, BorderLayout.WEST)

    JPanel pnlMensagem = new JPanel()
    lblMensagem = new JLabel('Seja bem vindo ao sistema!!!')
    pnlMensagem.add(lblMensagem)
    add(pnlMensagem, BorderLayout.CENTER)

    JPanel pnlDataHoraAcesso = new JPanel()
    lblDataHoraAcesso = new JLabel('Acesso em: '  +  new Date().format("dd/MM/yyyy 'as' HH:mm:ss"))
    pnlDataHoraAcesso.add(lblDataHoraAcesso)
    add(pnlDataHoraAcesso, BorderLayout.EAST)
  }
}
