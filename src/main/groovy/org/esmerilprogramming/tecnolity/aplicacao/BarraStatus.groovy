package org.esmerilprogramming.tecnolity.aplicacao

import java.awt.*
import javax.swing.*
import org.esmerilprogramming.tecnolity.util.*

class BarraStatus extends JPanel
{
  private JLabel lblUsuario, lblMensagem, lblDataHoraAcesso
    private Aplicacao aplicacao
    private Calendario calendario = new Calendario()

    BarraStatus(Aplicacao aplicacao) {
      setLayout(new BorderLayout())
        this.aplicacao = aplicacao
        JPanel pnlUsuario = new JPanel()
        lblUsuario =  new JLabel("Usuário: " + aplicacao.obterColaborador().obterMatricula())
        pnlUsuario.add(lblUsuario)
        this.add(pnlUsuario,BorderLayout.WEST)

        JPanel pnlMensagem = new JPanel()
        lblMensagem = new JLabel("Seja bem vindo ao sistema!!!")
        pnlMensagem.add(lblMensagem)
        this.add(pnlMensagem, BorderLayout.CENTER)

        JPanel pnlDataHoraAcesso = new JPanel()
        lblDataHoraAcesso = new JLabel("Acesso em: " + calendario.dataHoje("dd/MM/yyyy 'as' HH:mm:ss"))
        pnlDataHoraAcesso.add(lblDataHoraAcesso)
        this.add(pnlDataHoraAcesso,BorderLayout.EAST)
    }
}
