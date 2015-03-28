package br.com.tecnolity.administracao.ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import br.com.tecnolity.administracao.Permissao;
import br.com.tecnolity.aplicacao.Aplicacao;
import br.com.tecnolity.aplicacao.Interface;
import br.com.tecnolity.aplicacao.modelos.ModeloAreaTrabalho;

/**
   * Projeto: 001 - Tecnolity
   * Autor do Código: Hildeberto Mendonça Filho
   * Nome do Arquivo: AreaTrabalhoAdministracao.java
   * Linguagem: Java
   * 
   * Requerimentos: Requer máquina virtual java versão 1.3 ou superior.
   * 
   * Objetivo: Seção que manipula dos dados da Administracao.
   * 
   * Objetivo definido por: Hildeberto Mendonça
   * Início da Programação: 22/12/2001
   * Fim da Programação:
   * Última Versão: 1.0
*/

public class AreaTrabalhoAdministracao extends ModeloAreaTrabalho
{
    public final int IDENTIFICADOR = 1;
    
    private JPanel pnlCabecalho, pnlInformacoes;
    private InformacoesAdministracao tbpInformacoes;
        
    public AreaTrabalhoAdministracao(Aplicacao aplicacao) 
    {
        switch(super.verificarPermissaoAcesso(new Interface(IDENTIFICADOR),aplicacao.obterColaborador(),aplicacao.obterConexao()))
        {
            case Permissao.SEM_ACESSO:
                JOptionPane.showMessageDialog(this,"Acesso Negado.","Segurança",JOptionPane.WARNING_MESSAGE);
                break;
            default:
                this.setLayout(new BorderLayout());

                pnlCabecalho = new JPanel(new BorderLayout(5,5));
                pnlCabecalho.setBorder(new EmptyBorder(3,3,3,3));
                pnlCabecalho.setBackground(Color.white);
                JLabel lblImagemSecao = new JLabel(new ImageIcon("imagens/tit_administracao.gif"));
                pnlCabecalho.add(lblImagemSecao,BorderLayout.WEST);
                lblImagemSecao = new JLabel(new ImageIcon("imagens/logo_mentores.gif"));
                pnlCabecalho.add(lblImagemSecao,BorderLayout.EAST);
                this.add(pnlCabecalho,BorderLayout.NORTH);

                pnlInformacoes = new JPanel(new BorderLayout());
                tbpInformacoes = new InformacoesAdministracao(aplicacao);
                pnlInformacoes.add(tbpInformacoes, BorderLayout.CENTER);
                this.add(pnlInformacoes, BorderLayout.CENTER);
        }
    }
}