package org.esmerilprogramming.tecnolity.suprimentos.ui

import java.awt.*
import java.awt.event.*
import javax.swing.*

import org.esmerilprogramming.tecnolity.aplicacao.Aplicacao
import org.esmerilprogramming.tecnolity.suprimentos.*

/**
* Projeto: 001 - Tecnolity <br>
* Autor do Código: Hildeberto Mendonça Filho <br>
* Nome do Arquivo: DlgDadosCategoria.java <br>
* Linguagem: Java <br>
*  <br>
* Requerimentos: Requer máquina virtual java versão 1.3 ou superior. <br>
*  <br>
* Objetivo: Diálogo para cadastramento,alteração e visualização de categorias de um item. <br>
*  <br>
* Objetivo definido por: Hildeberto Mendonça <br>
* Início da Programação: 14/01/2002 <br>
* Fim da Programação: <br>
* Última Versão: 1.0 <br>
*/

 class DlgDadosCategoria extends JDialog implements ActionListener
{
     final int IDENTIFICADOR = 8
    
	private Aplicacao aplicacao
	private char modo // I = inserir, A = alterar, V = visualizar
	private Container conteudo
	private GridBagLayout gridbag
	private GridBagConstraints gbc
	private JTextField txtNomeCategoria
	private JButton btConfirmar, btCancelar

	 DlgDadosCategoria(Aplicacao aplicacao, char modo)
	{
		super(aplicacao,true)
		
		// Define o título da janela
	    String tituloJanela = ""
	    if (modo == 'I')
	    	tituloJanela = "Nova Categoria"
	    if (modo == 'A')
	    	tituloJanela = "Categoria"
	    if (modo == 'V')
	    	tituloJanela = "Categoria"
	    this.setTitle(tituloJanela)
		
	    this.aplicacao = aplicacao
	    this.modo = modo
	    
	    this.conteudo = this.getContentPane()
	    
	    gridbag = new GridBagLayout()
	    gbc = new GridBagConstraints()
	    gbc.fill = GridBagConstraints.NONE
	    gbc.anchor = GridBagConstraints.NORTHWEST
	    gbc.insets.bottom = 2
	    gbc.insets.left = 2
	    gbc.insets.right = 2
	    gbc.insets.top = 2
	    
	    JPanel pnlDados = new JPanel(gridbag)
	    JLabel label = new JLabel("Nome da Categoria")
	    adicionarComponente(pnlDados,label,0,0,1,1)
	    txtNomeCategoria = new JTextField(20)
	    adicionarComponente(pnlDados,txtNomeCategoria,1,0,1,1)
	    this.conteudo.add(pnlDados, BorderLayout.CENTER)
	    
	    JPanel pnlComandos = new JPanel(new FlowLayout(FlowLayout.RIGHT))
	    btConfirmar = new JButton("Confirmar")
	    btConfirmar.addActionListener(this)
	    pnlComandos.add(btConfirmar)
	    btCancelar = new JButton("Cancelar")
	    btCancelar.addActionListener(this)
	    pnlComandos.add(btCancelar)
	    this.conteudo.add(pnlComandos, BorderLayout.SOUTH)
	    
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
	
	 void actionPerformed(java.awt.event.ActionEvent actionEvent) 
	{
	    Object objeto = actionEvent.getSource()
	    
	    if(objeto == btConfirmar)
	    {
	    	Categoria categoria = new Categoria()
	    	if(categoria.cadastrarCategoria(txtNomeCategoria.getText()))
	    		this.setVisible(false)
	    	else
	    		JOptionPane.showMessageDialog(aplicacao,"Não foi possível cadastrar a Categoria informada!","Erro",JOptionPane.WARNING_MESSAGE)
	    }
	    
	    if(objeto == btCancelar)
	    {
	    	this.setVisible(false)	
	    }
	}
}