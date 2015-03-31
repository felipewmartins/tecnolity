package org.esmerilprogramming.tecnolity.administracao.ui

import java.awt.*
import javax.swing.*
import javax.swing.border.*
import org.esmerilprogramming.tecnolity.administracao.Permissao
import org.esmerilprogramming.tecnolity.aplicacao.Aplicacao
import org.esmerilprogramming.tecnolity.aplicacao.Interface
import org.esmerilprogramming.tecnolity.aplicacao.modelos.ModeloAreaTrabalho
import org.esmerilprogramming.tecnolity.ui.img.ImageLoader

class AreaTrabalhoAdministracao extends ModeloAreaTrabalho {
  final int IDENTIFICADOR = 1
  private JPanel pnlCabecalho, pnlInformacoes
  private InformacoesAdministracao tbpInformacoes

  AreaTrabalhoAdministracao(Aplicacao app) {
    switch(super.verificarPermissaoAcesso(new Interface(IDENTIFICADOR),app.colaborador, app.conexao)) {
      case Permissao.SEM_ACESSO:
        JOptionPane.showMessageDialog(this, 'Acesso Negado.', 'Segurança', JOptionPane.WARNING_MESSAGE)
        break
      default:
        this.setLayout(new BorderLayout())
        pnlCabecalho = new JPanel(new BorderLayout(5,5))
        pnlCabecalho.setBorder(new EmptyBorder(3,3,3,3))
        pnlCabecalho.setBackground(Color.white)
        def img = ImageLoader.instance
        JLabel lblImagemSecao = new JLabel(img.icon('tit_administracao.gif'))
        pnlCabecalho.add(lblImagemSecao,BorderLayout.WEST)
        lblImagemSecao = new JLabel(img.icon('logo_mentores.gif'))
        pnlCabecalho.add(lblImagemSecao, BorderLayout.EAST)
        this.add(pnlCabecalho, BorderLayout.NORTH)

        pnlInformacoes = new JPanel(new BorderLayout())
        tbpInformacoes = new InformacoesAdministracao(aplicacao)
        pnlInformacoes.add(tbpInformacoes, BorderLayout.CENTER)
        this.add(pnlInformacoes, BorderLayout.CENTER)
    }
  }
}
