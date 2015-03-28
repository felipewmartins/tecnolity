/**
* Projeto: 001 - Tecnolity
* Autor do Código: Kenia Soares
* Nome do Arquivo: DlgDadosTransportadora.java
* Linguagem: Java
* 
* Requerimentos: Requer máquina virtual java versão 1.3 ou superior.
* 
* Objetivo: Diálogo para cadastramento,alteração e visualização de dados de uma transportadora.
* 
* Objetivo definido por: Kenia Soares
* Início da Programação: 16/02/2002
* Fim da Programação:
* Última Versão: 1.0
*/

package br.com.tecnolity.logistica.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import br.com.tecnolity.aplicacao.Aplicacao;
import br.com.tecnolity.logistica.*;

public class DlgDadosTransportadora extends JDialog implements ActionListener, FocusListener
{
    public final int IDENTIFICADOR = 30;
    
    private Aplicacao aplicacao;
    private char modo; // I = inserir, A = alterar, V = visualizar 
    private Transportadora transportadora;
    private GridBagLayout gridbag;
    private GridBagConstraints gbc;
    private int codigoTransportadora;  

    private JPanel pnlDadosTransportadora;
    private JTextField txtTransportadora;
    
    private Container conteudo;
    private JPanel pnlAreaDados;
    private JButton btConfirmar, btCancelar;
		
    public DlgDadosTransportadora(Aplicacao aplicacao, char modo) 
    {
        super(aplicacao,true);
        transportadora = new Transportadora();

        // Define o título da janela
        String tituloJanela = "";
        if (modo == 'I')
            tituloJanela = "Nova Transportadora";	   
        this.setTitle(tituloJanela);

        this.aplicacao = aplicacao;
        this.modo = modo;
        
        montarInterface();
    }
    
    public DlgDadosTransportadora(Aplicacao aplicacao, char modo, int codigoTransportadora)
    {	
        super(aplicacao,true);
        try
        {	
        	transportadora = new Transportadora(codigoTransportadora,aplicacao.obterConexao());
        }
        catch(Exception e)
        {
        	JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar a Transportadora.","Erro", JOptionPane.ERROR_MESSAGE);	
                e.printStackTrace();
        }
        this.codigoTransportadora = codigoTransportadora;
        
        // Define o título da janela
        String tituloJanela = "";
        if (modo == 'A')            
            tituloJanela = "Transportadora";                            
        if (modo == 'V')
            tituloJanela = "Transportadora";
        this.setTitle(tituloJanela);

        this.aplicacao = aplicacao;
        this.modo = modo;
        
        montarInterface();        
    }
    
    public void montarInterface()
    {
       conteudo = this.getContentPane();
	    
        gridbag = new GridBagLayout();
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets.bottom = 2;
        gbc.insets.left = 2;
        gbc.insets.right = 2;
        gbc.insets.top = 2;

        pnlAreaDados = new JPanel(gridbag);         
        JLabel label = new JLabel("Transportadora");
        adicionarComponente(pnlAreaDados,label,0,0,2,1);
        
        txtTransportadora = new JTextField(this.transportadora.obterNome(),30);
        txtTransportadora.addFocusListener(this);
        adicionarComponente(pnlAreaDados,txtTransportadora,1,0,2,1);
                
        conteudo.add(pnlAreaDados,BorderLayout.CENTER);
        
        JPanel pnlComandos = new JPanel(new FlowLayout(FlowLayout.RIGHT));        
        btConfirmar = new JButton("Confirmar");
        btConfirmar.addActionListener(this);
        pnlComandos.add(btConfirmar);

        btCancelar = new JButton("Cancelar");
        btCancelar.addActionListener(this);
        pnlComandos.add(btCancelar);

        conteudo.add(pnlComandos, BorderLayout.SOUTH);
        this.pack();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds((screenSize.width/2) - (this.getBounds().width/2),
                               (screenSize.height/2) - (this.getBounds().height/2) - 30,
                               this.getBounds().width,
                               this.getBounds().height);
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
       
    public void actionPerformed(java.awt.event.ActionEvent actionEvent) 
    {
    	Object objeto = actionEvent.getSource();
            	
    	if(objeto == btCancelar)
        {
            this.setVisible(false);
        }
	    
        if(objeto == btConfirmar)
        {
            try
            {                
                if(modo == 'I')
                {                    
                    boolean confirmado = true;
                    try
                    {
                        transportadora.definirTransportadora(this.txtTransportadora.getText());
                    }
                    catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                        confirmado = false;
                    }
                    if(confirmado)
                    {
                        try
                        {
                            this.transportadora.cadastrarTransportadora();
                        }
                        catch(Exception e)
                        {
                            JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);	
                            e.printStackTrace();
                        }
                        this.setVisible(false);
                    }
                }
                else if(modo == 'A')
                {
                    boolean confirmado = true;
                    try
                    {
                        transportadora.definirCodigo(this.codigoTransportadora);
                        transportadora.definirTransportadora(this.txtTransportadora.getText());
                    }
                    catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                        confirmado = false;
                    }
                    if(confirmado)
                    {                    
                        try
                        {
                            this.transportadora.alterarTransportadora();
                        }
                        catch(Exception e)
                        {
                            JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);	
                            e.printStackTrace();
                        }
                        this.setVisible(false);
                    }
                }                
            }
            catch(Exception e)	
            {
                    JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
            }
        }
    }
    
    public void focusLost(java.awt.event.FocusEvent focusEvent) {}
    
    public void focusGained(java.awt.event.FocusEvent focusEvent) {}    
}