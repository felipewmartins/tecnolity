/**
* Projeto: 001 - Tecnolity
* Autor do Código: Kenia Soares
* Nome do Arquivo: DlgDadosFormaPagamento.java
* Linguagem: Java
* 
* Requerimentos: Requer máquina virtual java versão 1.3 ou superior.
* 
* Objetivo: Diálogo para cadastramento,alteração e visualização de dados de uma forma de pagamento.
* 
* Objetivo definido por: Kenia Soares
* Início da Programação: 25/02/2002
* Fim da Programação:
* Última Versão: 1.0
*/

package org.esmerilprogramming.tecnolity.pedidos.ui

import java.awt.*
import java.awt.event.*
import javax.swing.*

import org.esmerilprogramming.tecnolity.aplicacao.Aplicacao
import org.esmerilprogramming.tecnolity.pedidos.*

public class DlgDadosFormaPagamento extends JDialog implements ActionListener, FocusListener
{
    public final int IDENTIFICADOR = 15
    
    private Aplicacao aplicacao
    private char modo // I = inserir, A = alterar, V = visualizar 
    private FormaPagamento formaPagamento
    private GridBagLayout gridbag
    private GridBagConstraints gbc
    private String sigla  

    private JPanel pnlDadosFormaPagamento
    private JTextField txtSigla, txtFormaPagamento
    
    private Container conteudo
    private JPanel pnlAreaDados
    private JButton btConfirmar, btCancelar
		
    public DlgDadosFormaPagamento(Aplicacao aplicacao, char modo) 
    {
        super(aplicacao,true)
        formaPagamento = new FormaPagamento()

        // Define o título da janela
        String tituloJanela = ""
        if (modo == 'I')
            tituloJanela = "Nova Forma de Pagamento"	   
        this.setTitle(tituloJanela)

        this.aplicacao = aplicacao
        this.modo = modo
        
        montarInterface()
    }
    
    public DlgDadosFormaPagamento(Aplicacao aplicacao, char modo, String sigla)
    {	
        super(aplicacao,true)
        try
        {	
        	formaPagamento = new FormaPagamento(sigla,aplicacao.obterConexao())
        }
        catch(Exception e)
        {
        	JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar a Forma de Pagamento.","Erro", JOptionPane.ERROR_MESSAGE)	
                e.printStackTrace()
        }
        this.sigla = sigla
        
        // Define o título da janela
        String tituloJanela = ""
        if (modo == 'A')            
            tituloJanela = "Forma de Pagamento"                            
        if (modo == 'V')
            tituloJanela = "Forma de Pagamento"
        this.setTitle(tituloJanela)

        this.aplicacao = aplicacao
        this.modo = modo
        
        montarInterface()        
    }
    
    public void montarInterface()
    {
       conteudo = this.getContentPane()
	    
        gridbag = new GridBagLayout()
        gbc = new GridBagConstraints()
        gbc.fill = GridBagConstraints.NONE
        gbc.anchor = GridBagConstraints.NORTHWEST
        gbc.insets.bottom = 2
        gbc.insets.left = 2
        gbc.insets.right = 2
        gbc.insets.top = 2

        pnlAreaDados = new JPanel(gridbag)         
        JLabel label = new JLabel("Sigla")
        adicionarComponente(pnlAreaDados,label,0,0,1,1)
        label = new JLabel("Forma de Pagamento")
        adicionarComponente(pnlAreaDados,label,0,1,2,1)
        
        txtSigla = new JTextField(this.formaPagamento.obterSigla(),2)
        txtSigla.addFocusListener(this)
        adicionarComponente(pnlAreaDados,txtSigla,1,0,1,1)
        
        txtFormaPagamento = new JTextField(this.formaPagamento.obterFormaPagamento(),30)
        txtFormaPagamento.addFocusListener(this)
        adicionarComponente(pnlAreaDados,txtFormaPagamento,1,1,2,1)
                
        conteudo.add(pnlAreaDados,BorderLayout.CENTER)
        
        JPanel pnlComandos = new JPanel(new FlowLayout(FlowLayout.RIGHT))        
        btConfirmar = new JButton("Confirmar")
        btConfirmar.addActionListener(this)
        pnlComandos.add(btConfirmar)

        btCancelar = new JButton("Cancelar")
        btCancelar.addActionListener(this)
        pnlComandos.add(btCancelar)

        conteudo.add(pnlComandos, BorderLayout.SOUTH)
        this.pack()

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize()
        this.setBounds((screenSize.width/2) - (this.getBounds().width/2),
                               (screenSize.height/2) - (this.getBounds().height/2) - 30,
                               this.getBounds().width,
                               this.getBounds().height)
    }
    
    private void adicionarComponente(JPanel painel, Component c, int linha, int coluna, int largura, int altura)
    {
        gbc.gridx = coluna
        gbc.gridy = linha

        gbc.gridwidth = largura
        gbc.gridheight = altura

        gridbag.setConstraints(c,gbc)
        painel.add(c)
    }   
       
    public void actionPerformed(java.awt.event.ActionEvent actionEvent) 
    {
    	Object objeto = actionEvent.getSource()
            	
    	if(objeto == btCancelar)
        {
            this.setVisible(false)
        }
	    
        if(objeto == btConfirmar)
        {
            try
            {                
                if(modo == 'I')
                {                    
                    boolean confirmado = true
                    try
                    {
                        this.formaPagamento.definirSigla(this.txtSigla.getText())
                        this.formaPagamento.definirFormaPagamento(this.txtFormaPagamento.getText())
                    }
                    catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE)
                        e.printStackTrace()
                        confirmado = false
                    }
                    if(confirmado)
                    {
                        try
                        {
                            this.formaPagamento.cadastrarFormaPagamento()
                        }
                        catch(Exception e)
                        {
                            JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE)
                            e.printStackTrace()
                        }
                        this.setVisible(false)
                    }
                }
                else if(modo == 'A')
                {
                    boolean confirmado = true
                    try
                    {
                        this.formaPagamento.definirSigla(this.txtSigla.getText())
                        this.formaPagamento.definirFormaPagamento(this.txtFormaPagamento.getText())
                    }
                    catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE)
                        e.printStackTrace()
                        confirmado = false
                    }
                    if(confirmado)
                    {                    
                        try
                        {
                            this.formaPagamento.alterarFormaPagamento()
                        }
                        catch(Exception e)
                        {
                            JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE)
                            e.printStackTrace()
                        }
                        this.setVisible(false)
                    }
                }                
            }
            catch(Exception e)	
            {
                    JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE)
                    e.printStackTrace()
            }
        }
    }
    
    public void focusLost(java.awt.event.FocusEvent focusEvent) {}
    
    public void focusGained(java.awt.event.FocusEvent focusEvent) {}    
}