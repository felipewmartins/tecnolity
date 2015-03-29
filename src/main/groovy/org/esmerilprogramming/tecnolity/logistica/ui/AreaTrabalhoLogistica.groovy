/**
   * Projeto: 001 - Tecnolity
   * Autor do Código: Hildeberto Mendonça Filho
   * Nome do Arquivo: AreaTrabalhoSuprimentos.java
   * Linguagem: Java
   * 
   * Requerimentos: Requer máquina virtual java versão 1.3 ou superior.
   * 
   * Objetivo: Seção que manipula dos dados do Suprimento.
   * 
   * Objetivo definido por: Hildeberto Mendonça
   * Início da Programação: 22/12/2001
   * Fim da Programação:
   * Última Versão: 1.0
*/

package org.esmerilprogramming.tecnolity.logistica.ui

import java.awt.*
import javax.swing.*
import javax.swing.border.*
import org.esmerilprogramming.tecnolity.administracao.Permissao
import org.esmerilprogramming.tecnolity.aplicacao.Aplicacao
import org.esmerilprogramming.tecnolity.aplicacao.Interface
import org.esmerilprogramming.tecnolity.aplicacao.modelos.ModeloAreaTrabalho

public class AreaTrabalhoLogistica extends ModeloAreaTrabalho
{
    public final int IDENTIFICADOR = 3
    
    private JPanel pnlCabecalho, pnlInformacoes
    private InformacoesLogistica tbpInformacoes
    private Aplicacao aplicacao
    
    public AreaTrabalhoLogistica(Aplicacao aplicacao) 
    {
        this.aplicacao = aplicacao
        switch(super.verificarPermissaoAcesso(new Interface(IDENTIFICADOR),aplicacao.obterColaborador(),aplicacao.obterConexao()))
        {
            case Permissao.SEM_ACESSO:
                JOptionPane.showMessageDialog(this,"Acesso Negado","Segurança",JOptionPane.WARNING_MESSAGE)
                break
            default:
                this.setLayout(new BorderLayout())
        
                pnlCabecalho = new JPanel(new BorderLayout(5,5))
                pnlCabecalho.setBorder(new EmptyBorder(3,3,3,3))
                pnlCabecalho.setBackground(Color.white)
                JLabel lblImagemSecao = new JLabel(new ImageIcon("imagens/tit_logistica.gif"))
                pnlCabecalho.add(lblImagemSecao,BorderLayout.WEST)
                lblImagemSecao = new JLabel(new ImageIcon("imagens/logo_mentores.gif"))
                pnlCabecalho.add(lblImagemSecao,BorderLayout.EAST)
                this.add(pnlCabecalho,BorderLayout.NORTH)

                pnlInformacoes = new JPanel(new BorderLayout())
                tbpInformacoes = new InformacoesLogistica(aplicacao)
                pnlInformacoes.add(tbpInformacoes, BorderLayout.CENTER)
                this.add(pnlInformacoes, BorderLayout.CENTER)
                break
        }
    }
}