/**
* Projeto: 001 - Tecnolity
* Autor do C�digo: Hildeberto Mendon�a Filho
* Nome do Arquivo: DlgDadosUnidade.java
* Linguagem: Java
* 
* Requerimentos: Requer m�quina virtual java vers�o 1.3 ou superior.
* 
* Objetivo: Di�logo para cadastramento,altera��o e visualiza��o de dados de uma unidade.
* 
* Objetivo definido por: Hildeberto Mendon�a
* In�cio da Programa��o: 31/12/2001
* Fim da Programa��o:
* �ltima Vers�o: 1.0
*/

package org.esmerilprogramming.tecnolity.suprimentos.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.esmerilprogramming.tecnolity.aplicacao.Aplicacao;
import org.esmerilprogramming.tecnolity.suprimentos.*;

public class DlgDadosUnidade extends JDialog implements ActionListener
{
    public final int IDENTIFICADOR = 31;
    
	private Aplicacao aplicacao;
	private char modo; // I = inserir, A = alterar, V = visualizar
	private Container conteudo;
	private GridBagLayout gridbag;
	private GridBagConstraints gbc;
	private JTextField txtNomeUnidade;
	private JButton btConfirmar, btCancelar;

	public DlgDadosUnidade(Aplicacao aplicacao, char modo)
	{
		super(aplicacao,true);
		
		// Define o t�tulo da janela
	    String tituloJanela = "";
	    if (modo == 'I')
	    	tituloJanela = "Nova Unidade";
	    if (modo == 'A')
	    	tituloJanela = "Unidade";
	    if (modo == 'V')
	    	tituloJanela = "Unidade";
	    this.setTitle(tituloJanela);
		
	    this.aplicacao = aplicacao;
	    this.modo = modo;
	    
	    this.conteudo = this.getContentPane();
	    
	    gridbag = new GridBagLayout();
	    gbc = new GridBagConstraints();
	    gbc.fill = GridBagConstraints.NONE;
	    gbc.anchor = GridBagConstraints.NORTHWEST;
	    gbc.insets.bottom = 2;
	    gbc.insets.left = 2;
	    gbc.insets.right = 2;
	    gbc.insets.top = 2;
	    
	    JPanel pnlDados = new JPanel(gridbag);
	    JLabel label = new JLabel("Nome da Unidade");
	    adicionarComponente(pnlDados,label,0,0,1,1);
	    txtNomeUnidade = new JTextField(20);
	    adicionarComponente(pnlDados,txtNomeUnidade,1,0,1,1);
	    this.conteudo.add(pnlDados, BorderLayout.CENTER);
	    
	    JPanel pnlComandos = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	    btConfirmar = new JButton("Confirmar");
	    btConfirmar.addActionListener(this);
	    pnlComandos.add(btConfirmar);
	    btCancelar = new JButton("Cancelar");
	    btCancelar.addActionListener(this);
	    pnlComandos.add(btCancelar);
	    this.conteudo.add(pnlComandos, BorderLayout.SOUTH);
	    
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
	    
	    if(objeto == btConfirmar)
	    {
	    	Unidade unidade = new Unidade();
	    	if(unidade.cadastrarUnidade(txtNomeUnidade.getText()))
	    		this.setVisible(false);
	    	else
	    		JOptionPane.showMessageDialog(aplicacao,"N�o foi poss�vel cadastrar a Unidade informada!","Erro",JOptionPane.WARNING_MESSAGE);
	    }
	    
	    if(objeto == btCancelar)
	    {
	    	this.setVisible(false);	
	    }
	}
}