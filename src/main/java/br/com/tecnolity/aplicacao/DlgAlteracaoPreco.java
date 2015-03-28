/**
* Projeto: 001 - Tecnolity
* Autor do Código: Hildeberto Mendonça Filho
* Nome do Arquivo: DlgDadosEstado.java
* Linguagem: Java
* 
* Requerimentos: Requer máquina virtual java versão 1.3 ou superior.
* 
* Objetivo: Diálogo para cadastramento,alteração e visualização de estados.
* 
* Objetivo definido por: Hildeberto Mendonça
* Início da Programação: 14/01/2002
* Fim da Programação:
* Última Versão: 1.0
*/

package br.com.tecnolity.aplicacao;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import br.com.tecnolity.suprimentos.*;
import br.com.tecnolity.util.*;

public class DlgAlteracaoPreco extends JDialog implements ActionListener
{
    public final int IDENTIFICADOR = 14;
    
    private Aplicacao aplicacao;
    private Container conteudo;
    private GridBagLayout gridbag;
    private GridBagConstraints gbc;
    private JTextField txtValorItem;
    private JButton btConfirmar, btCancelar;
    private FornecedorItem fornecedorItem;

    public DlgAlteracaoPreco(Aplicacao aplicacao, FornecedorItem fornecedorItem)
    {
        super(aplicacao,true);

        this.setTitle("Alteração de Preço");

        this.aplicacao = aplicacao;
        this.fornecedorItem = fornecedorItem;
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
        adicionarComponente(pnlDados,new JLabel("Fornecedor:"),0,0,1,1);
        adicionarComponente(pnlDados,new JLabel(fornecedorItem.obterFornecedor().obterRazaoSocial()),0,1,1,1);
        adicionarComponente(pnlDados,new JLabel("Item:"),1,0,1,1);
        adicionarComponente(pnlDados,new JLabel(fornecedorItem.obterItem().obterDescricao()),1,1,1,1);        
        adicionarComponente(pnlDados,new JLabel("Valor:"),2,0,1,1);
        txtValorItem = new JTextField(Numero.formatarValorMoeda(fornecedorItem.obterValorItem(),"").trim(),8);
        adicionarComponente(pnlDados,txtValorItem,2,1,1,1);
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
	    	try
            {
                this.fornecedorItem.definirValorItem(Float.parseFloat(Numero.inverterSeparador(txtValorItem.getText())));
                this.fornecedorItem.alterarValorItem();
                this.setVisible(false);
            }
            catch(Exception e)
            {
             	JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.WARNING_MESSAGE);
                e.printStackTrace();
            }
	    }
	    
	    if(objeto == btCancelar)
	    {
	    	this.setVisible(false);	
	    }
	}
}