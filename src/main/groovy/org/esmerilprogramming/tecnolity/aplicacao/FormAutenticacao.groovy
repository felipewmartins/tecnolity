/**
   * Projeto: 001 - Tecnolity
   * Autor do Código: Hildeberto Mendonça Filho
   * Nome do Arquivo: FormAutenticacao.java
   * Linguagem: Java
   * 
   * Requerimentos: Requer máquina virtual java versão 1.3 ou superior.
   * 
   * Objetivo: Exige no momento do acesso ao sistema o nome do usuario e senha 
   * para verificação de permissões.
   * 
   * Objetivo definido por: Hildeberto Mendonça
   * Início da Programação: 22/12/2001
   * Fim da Programação:
   * Última Versão: 1.0
*/

package org.esmerilprogramming.tecnolity.aplicacao;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.esmerilprogramming.tecnolity.administracao.*;
import org.esmerilprogramming.tecnolity.administracao.ui.*;

public class FormAutenticacao extends JDialog implements ActionListener
{
    private JButton btAcessar, btCancelar;
    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    
    private GridBagLayout gridbag;
    private GridBagConstraints gbc;
    
    private Aplicacao aplicacao;
    
    public FormAutenticacao(Aplicacao aplicacao)
    {
        super(aplicacao,true);
        this.aplicacao = aplicacao;
        Container conteudo = this.getContentPane();
        conteudo.setLayout(new BorderLayout());
        JLabel lblImagem = new JLabel(new ImageIcon("imagens/splash.gif"));
        lblImagem.setSize(400,122);
        conteudo.add(lblImagem, BorderLayout.NORTH);
                     
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds((screenSize.width/2) - 200,(screenSize.height/2) - 150, 410, 300);
        
        gridbag = new GridBagLayout();
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets.bottom = 2;
        gbc.insets.left = 2;
        gbc.insets.right = 2;
        gbc.insets.top = 2;
        
        JPanel pnlAcesso = new JPanel(gridbag);
        JLabel lblTexto = new JLabel("Usuário: ");
        adicionarComponente(pnlAcesso, lblTexto, 0, 0, 1, 1);
        txtUsuario = new JTextField(10);
        txtUsuario.addActionListener(this);
        adicionarComponente(pnlAcesso, txtUsuario, 0, 1, 1, 1);
        
        lblTexto = new JLabel("Senha: ");
        adicionarComponente(pnlAcesso, lblTexto, 1, 0, 1, 1);
        txtSenha = new JPasswordField(10);
        txtSenha.addActionListener(this);
        adicionarComponente(pnlAcesso, txtSenha, 1, 1, 1, 1);
                      
        conteudo.add(pnlAcesso, BorderLayout.CENTER);
        
        JPanel pnlComandos = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btAcessar = new JButton("Acessar");
        btAcessar.addActionListener(this);
        pnlComandos.add(btAcessar);
        btCancelar = new JButton("Cancelar");
        btCancelar.addActionListener(this);
        pnlComandos.add(btCancelar);
        conteudo.add(pnlComandos, BorderLayout.SOUTH);
    }
    
    private void adicionarComponente(JPanel painel, Component c, int linha, int coluna, int largura, int altura)
    {
        gbc.gridx = coluna;
        gbc.gridy = linha;
        
        gbc.gridwidth = largura;
        gbc.gridheight = altura;
        
        gridbag.setConstraints(c,gbc);
        painel.add(c);
    }
    
    private void verificarAutenticacao()
    {
        try
        {
            Colaborador colaborador = new Colaborador(txtUsuario.getText(), String.valueOf(txtSenha.getPassword()));
            if(colaborador.autenticarColaborador())
            {
                aplicacao.definirColaborador(colaborador);
                this.setVisible(false);
            }
            else
            {
                if(colaborador.colaboradorExiste())
                {
                    JOptionPane.showMessageDialog(aplicacao,"Usuário ou senha inválida!","Problema de Autenticação",JOptionPane.WARNING_MESSAGE);
                    this.txtUsuario.transferFocus();
                    this.txtUsuario.selectAll();
                }
                else
                {
                    DlgDadosColaborador dlgDadosColaborador = new DlgDadosColaborador(aplicacao,'I');
                    dlgDadosColaborador.setVisible(true);
                }
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    public void actionPerformed(java.awt.event.ActionEvent actionEvent) 
    {
        Object objeto = actionEvent.getSource();
        
        if(objeto == txtUsuario)
        {
            verificarAutenticacao();
        }
        
        if(objeto == txtSenha)
        {
            verificarAutenticacao();
        }
        if(objeto == btAcessar)
        {
            verificarAutenticacao();
        }
        
        if(objeto == btCancelar)
        {
            aplicacao.finalizarAplicacao();
        }
    }    
}