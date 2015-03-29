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

package org.esmerilprogramming.tecnolity.administracao.ui

import java.awt.*
import java.util.*
import java.awt.event.*
import javax.swing.*
import org.esmerilprogramming.tecnolity.administracao.*
import org.esmerilprogramming.tecnolity.aplicacao.Aplicacao

public class DlgDadosEstado extends JDialog implements ActionListener
{
    public final int IDENTIFICADOR = 14
    
    private Aplicacao aplicacao
    private char modo // I = inserir, A = alterar, V = visualizar, E = Excluir
    private Container conteudo
    private GridBagLayout gridbag
    private GridBagConstraints gbc
    private JTextField txtNomeEstado, txtSiglaEstado
    private JComboBox cbxPais
    private Pais pais = new Pais()
    private Vector paises
    private JButton btNovoPais, btConfirmar, btCancelar

    public DlgDadosEstado(Aplicacao aplicacao, char modo)
    {
        super(aplicacao,true)

        // Define o título da janela
        String tituloJanela = ""
        if (modo == 'I')
            tituloJanela = "Novo Estado"
        if (modo == 'A')
            tituloJanela = "Estado"
        if (modo == 'V')
            tituloJanela = "Estado"
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
        JLabel label = new JLabel("Nome do Estado")
        adicionarComponente(pnlDados,label,0,0,1,1)
        txtNomeEstado = new JTextField(20)
        adicionarComponente(pnlDados,txtNomeEstado,1,0,1,1)
        label = new JLabel("Sigla")
        adicionarComponente(pnlDados,label,2,0,1,1)
        txtSiglaEstado = new JTextField(3)
	    adicionarComponente(pnlDados,txtSiglaEstado,3,0,1,1)
        label = new JLabel("País")
        adicionarComponente(pnlDados,label,4,0,1,1)
        
        JPanel pnlSuporteCombo = new JPanel(new BorderLayout())
        cbxPais = new JComboBox()
        try
        {
            paises = Pais.carregarPaises(aplicacao.obterConexao())
            carregarPaises()
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar os Países.","Erro",JOptionPane.ERROR_MESSAGE)
            e.printStackTrace()
        }
        pnlSuporteCombo.add(cbxPais,BorderLayout.CENTER)
        btNovoPais = new JButton(new ImageIcon("imagens/novo.jpg"))
        btNovoPais.addActionListener(this)
        btNovoPais.setToolTipText("Novo Pais")
        btNovoPais.setPreferredSize(new Dimension(22,20))
        pnlSuporteCombo.add(btNovoPais,BorderLayout.EAST)
        
        adicionarComponente(pnlDados,pnlSuporteCombo,5,0,1,1)
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
        
        private void carregarPaises()
        {
            cbxPais.removeAllItems()
            cbxPais.addItem("Selecione...")

            for(int i = 1i < paises.size()i++)
            {
                cbxPais.addItem(((Pais)paises.get(i)).getNome())
            }
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
	    
        if(objeto == btNovoPais)
        {
            DlgDadosPais dlgDadosPais = new DlgDadosPais(aplicacao,'I')
            dlgDadosPais.setVisible(true)
            try
            {
                paises = Pais.carregarPaises(aplicacao.obterConexao())
                carregarPaises()
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar os Países.","Erro",JOptionPane.ERROR_MESSAGE)
                e.printStackTrace()
            }
        }
        
	    if(objeto == btConfirmar)
	    {
	    	try
            {
                Estado estado = new Estado(txtSiglaEstado.getText(),txtNomeEstado.getText(),(Pais)paises.get(cbxPais.getSelectedIndex()))
                estado.cadastrarEstado()
                this.setVisible(false)
            }
            catch(Exception e)
            {
             	JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.WARNING_MESSAGE)
                e.printStackTrace()
            }
	    }
	    
	    if(objeto == btCancelar)
	    {
	    	this.setVisible(false)	
	    }
	}
}