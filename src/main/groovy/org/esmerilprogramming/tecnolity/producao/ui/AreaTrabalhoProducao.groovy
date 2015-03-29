package org.esmerilprogramming.tecnolity.producao.ui

import java.awt.*
import javax.swing.*
import javax.swing.border.*
import org.esmerilprogramming.tecnolity.administracao.Permissao
import org.esmerilprogramming.tecnolity.aplicacao.Aplicacao
import org.esmerilprogramming.tecnolity.aplicacao.Interface
import org.esmerilprogramming.tecnolity.aplicacao.modelos.ModeloAreaTrabalho

/**
   * Projeto: 001 - Tecnolity <br>
   * Autor do Código: Hildeberto Mendonça Filho <br>
   * Nome do Arquivo: AreaTrabalhoProducao.java <br>
   * Linguagem: Java <br>
   * <br>
   * Requerimentos: Requer máquina virtual java versão 1.3 ou superior. <br>
   * <br>
   * Objetivo: Seção que manipula dos dados da Produção. <br>
   * <br>
   * Objetivo definido por: Hildeberto Mendonça <br>
   * Início da Programação: 06/02/2001 <br>
   * Fim da Programação: <br>
   * Última Versão: 1.0 <br>
*/

public class AreaTrabalhoProducao extends ModeloAreaTrabalho
{
    public final int IDENTIFICADOR = 5
    
    private JPanel pnlCabecalho, pnlInformacoes
    private InformacoesProducao tbpInformacoes
    private Aplicacao aplicacao
    
    public AreaTrabalhoProducao(Aplicacao aplicacao) 
    {
        this.aplicacao = aplicacao
        switch(super.verificarPermissaoAcesso(new Interface(IDENTIFICADOR),aplicacao.obterColaborador(),aplicacao.obterConexao()))
        {
            case Permissao.SEM_ACESSO:
                JOptionPane.showMessageDialog(this,"Acesso Negado.","Segurança",JOptionPane.WARNING_MESSAGE)
                break
            default:
                this.setLayout(new BorderLayout())

                pnlCabecalho = new JPanel(new BorderLayout(5,5))
                pnlCabecalho.setBorder(new EmptyBorder(3,3,3,3))
                pnlCabecalho.setBackground(Color.white)
                JLabel lblImagemSecao = new JLabel(new ImageIcon("imagens/tit_producao.gif"))
                pnlCabecalho.add(lblImagemSecao,BorderLayout.WEST)
                lblImagemSecao = new JLabel(new ImageIcon("imagens/logo_mentores.gif"))
                pnlCabecalho.add(lblImagemSecao,BorderLayout.EAST)
                this.add(pnlCabecalho,BorderLayout.NORTH)

                pnlInformacoes = new JPanel(new BorderLayout())
                tbpInformacoes = new InformacoesProducao(aplicacao)
                pnlInformacoes.add(tbpInformacoes, BorderLayout.CENTER)
                this.add(pnlInformacoes, BorderLayout.CENTER)
        }
    }
}