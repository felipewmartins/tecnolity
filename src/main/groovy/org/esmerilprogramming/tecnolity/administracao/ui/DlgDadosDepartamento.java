package org.esmerilprogramming.tecnolity.administracao.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Vector;
import org.esmerilprogramming.tecnolity.administracao.*;
import org.esmerilprogramming.tecnolity.aplicacao.Aplicacao;

/**
* Projeto: 001 - Tecnolity <br>
* Autor do Código: Hildeberto Mendonça Filho <br>
* Nome do Arquivo: DlgDadosDepartamento.java <br>
* Linguagem: Java <br>
*  <br>
* Requerimentos: Requer máquina virtual java versão 1.3 ou superior. <br>
*  <br>
* Objetivo: Diálogo para cadastramento,alteração e visualização de departamentos. <br>
*  <br>
* Objetivo definido por: Hildeberto Mendonça <br>
* Início da Programação: 06/02/2002 <br>
* Fim da Programação: <br>
* Última Versão: 1.0  <br>
*/

public class DlgDadosDepartamento extends JDialog implements ActionListener
{
    public final int IDENTIFICADOR = 12;
    
	private Aplicacao aplicacao;
	private Container conteudo;
	private GridBagLayout gridbag;
	private GridBagConstraints gbc;
	private JTextField txtNomeDepartamento;
    private JComboBox cbxResponsavel;
	private JButton btConfirmar, btCancelar;
    private Vector responsaveis;
    private Departamento departamento;

	public DlgDadosDepartamento(Aplicacao aplicacao)
	{
		super(aplicacao,true);
		
		this.setTitle("Novo Departamento");
		
	    this.aplicacao = aplicacao;
        this.departamento = new Departamento();
        montarInterface();
	}
    
    public DlgDadosDepartamento(Aplicacao aplicacao, Departamento departamento)
	{
		super(aplicacao,true);
		
		this.setTitle("Novo Departamento");
		
	    this.aplicacao = aplicacao;
        this.departamento = departamento;
        
        montarInterface();
	}
    
    private void montarInterface()
    {
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
	    JLabel label = new JLabel("Nome do Departamento");
	    adicionarComponente(pnlDados,label,0,0,1,1);
	    txtNomeDepartamento = new JTextField(departamento.obterNomeDepartamento(),20);
	    adicionarComponente(pnlDados,txtNomeDepartamento,1,0,1,1);
        label = new JLabel("Responsável");
        adicionarComponente(pnlDados,label,2,0,1,1);
        cbxResponsavel = new JComboBox();
        Colaborador colaborador = new Colaborador();
        try
        {
            responsaveis = colaborador.carregarColaboradores(aplicacao.obterConexao());
            carregarResponsaveis();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        adicionarComponente(pnlDados,cbxResponsavel,3,0,1,1);
        
	    this.conteudo.add(pnlDados, BorderLayout.CENTER);
	    
	    JPanel pnlComandos = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	    btConfirmar = new JButton("Confirmar");
	    btConfirmar.addActionListener(this);
	    pnlComandos.add(btConfirmar);
	    btCancelar = new JButton("Cancelar");
	    btCancelar.addActionListener(this);
	    pnlComandos.add(btCancelar);
	    this.conteudo.add(pnlComandos, BorderLayout.SOUTH);
	    
	    redimencionar();
    }
    
    private void redimencionar()
    {
        this.pack();
	    
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds((screenSize.width/2) - (this.getBounds().width/2),
        			   (screenSize.height/2) - (this.getBounds().height/2) - 30,
        			   this.getBounds().width,
        			   this.getBounds().height);
    }
    
    private void carregarResponsaveis()
    {
        cbxResponsavel.removeAllItems();
        cbxResponsavel.addItem("Selecione...");
        int selecionado = 0;
        for(int i = 1;i < responsaveis.size();i++)
        {
            Colaborador colaborador = (Colaborador)responsaveis.get(i);
            if(departamento.obterResponsavel() != null)
                if(colaborador.obterMatricula().equals(departamento.obterResponsavel().obterMatricula()))
                   selecionado = i;
            cbxResponsavel.addItem(colaborador.getNome());
        }
        cbxResponsavel.setSelectedIndex(selecionado);
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
            if(departamento.obterCodigo() > 0)
            {
                try
                {
                    departamento.alterarDepartamento(txtNomeDepartamento.getText(),(Colaborador)responsaveis.get(cbxResponsavel.getSelectedIndex()));
                    this.setVisible(false);
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
            else
            {
	    	   	try
                {
                    departamento.cadastrarDepartamento(txtNomeDepartamento.getText(),(Colaborador)responsaveis.get(cbxResponsavel.getSelectedIndex()));
                    this.setVisible(false);
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
	    }
	    
	    if(objeto == btCancelar)
	    {
	    	this.setVisible(false);	
	    }
	}
}