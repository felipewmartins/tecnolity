package org.esmerilprogramming.tecnolity.producao.ui

import java.awt.*
import javax.swing.*
import javax.swing.border.*
import org.esmerilprogramming.tecnolity.administracao.Permissao
import org.esmerilprogramming.tecnolity.aplicacao.Aplicacao
import org.esmerilprogramming.tecnolity.aplicacao.Interface
import org.esmerilprogramming.tecnolity.aplicacao.modelos.ModeloAreaTrabalho

class AreaTrabalhoProducao extends ModeloAreaTrabalho
{
  final int IDENTIFICADOR = 5

    private JPanel pnlCabecalho, pnlInformacoes
    private InformacoesProducao tbpInformacoes
    private Aplicacao aplicacao

    AreaTrabalhoProducao(Aplicacao aplicacao) {
      this.aplicacao = aplicacao
        switch(super.verificarPermissaoAcesso(new Interface(IDENTIFICADOR), aplicacao.obterColaborador(), aplicacao.obterConexao())) {
          case Permissao.SEM_ACESSO:
            JOptionPane.showMessageDialog(this, "Acesso Negado.", "Segurança", JOptionPane.WARNING_MESSAGE)
              break
          default:
              this.setLayout(new BorderLayout())

                pnlCabecalho = new JPanel(new BorderLayout(5, 5))
                pnlCabecalho.setBorder(new EmptyBorder(3, 3, 3, 3))
                pnlCabecalho.setBackground(Color.white)
                JLabel lblImagemSecao = new JLabel(new ImageIcon("imagens/tit_producao.gif"))
                pnlCabecalho.add(lblImagemSecao, BorderLayout.WEST)
                lblImagemSecao = new JLabel(new ImageIcon("imagens/logo_mentores.gif"))
                pnlCabecalho.add(lblImagemSecao, BorderLayout.EAST)
                this.add(pnlCabecalho, BorderLayout.NORTH)

                pnlInformacoes = new JPanel(new BorderLayout())
                tbpInformacoes = new InformacoesProducao(aplicacao)
                pnlInformacoes.add(tbpInformacoes, BorderLayout.CENTER)
                this.add(pnlInformacoes, BorderLayout.CENTER)
        }
    }
}
