/**
* Projeto: 001 - Tecnolity
* Autor do Código: Kenia Soares
* Nome do Arquivo: DlgDadosMotorista.java
* Linguagem: Java
* 
* Requerimentos: Requer máquina virtual java versão 1.3 ou superior.
* 
* Objetivo: Diálogo para cadastramento,alteração e visualização de dados de um motorista.
* 
* Objetivo definido por: Kenia Soares
* Início da Programação: 13/02/2002
* Fim da Programação:
* Última Versão: 1.0
*/

package org.esmerilprogramming.tecnolity.logistica.ui

import java.awt.*
import java.util.*
import java.awt.event.*
import javax.swing.*
import javax.swing.border.*
import org.esmerilprogramming.tecnolity.administracao.*
import org.esmerilprogramming.tecnolity.administracao.ui.DlgDadosEstado
import org.esmerilprogramming.tecnolity.aplicacao.Aplicacao
import org.esmerilprogramming.tecnolity.logistica.*
import org.esmerilprogramming.tecnolity.util.*

public class DlgDadosMotorista extends JDialog implements ActionListener, FocusListener
{
    public final int IDENTIFICADOR = 20
    
    private Aplicacao aplicacao
    private Vector veiculos,categorias,estados    
    private GridBagLayout gridbag
    private GridBagConstraints gbc
    private Veiculo veiculo = new Veiculo()
    private Motorista motorista
    private int codigoMotorista
    private String validade

    private JPanel pnlDadosDespesa
    private JComboBox cbxVeiculo,cbxCategoria,cbxEstado
    private JTextField txtMotorista,txtIdentidade,txtOrgaoEmissor,txtCpf,
                       txtHabilitacao,txtValidade,txtLogradouro,txtComplemento,
                       txtBairro,txtCidade,txtCep,txtTelefone,
                       txtCelular
    private JButton btNovoVeiculo,btNovoEstado

    private Container conteudo
    private JPanel pnlAreaDados
    private JButton btConfirmar, btCancelar
		
    public DlgDadosMotorista(Aplicacao aplicacao, char modo) 
    {
        super(aplicacao,true)
        motorista = new Motorista()

        this.setTitle("Motorista")

        this.aplicacao = aplicacao
                
        montarInterface()
    }
    
    public DlgDadosMotorista(Aplicacao aplicacao, char modo, int codigo)
    {	
        super(aplicacao,true)
        try
        {	
        	motorista = new Motorista(codigo,aplicacao.obterConexao())
        }
        catch(Exception e)
        {
        	JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar o Motorista.","Erro", JOptionPane.ERROR_MESSAGE)	
                e.printStackTrace()
        }
        this.codigoMotorista = codigo

        this.setTitle("Motorista")

        this.aplicacao = aplicacao
        
        validade = Calendario.ajustarFormatoDataBanco(this.motorista.obterValidade())
        
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
        
        JLabel label = new JLabel("Nome Completo")
        adicionarComponente(pnlAreaDados,label,0,0,2,1)
        
        txtMotorista = new JTextField((this.motorista.obterMotorista())==null?"":this.motorista.obterMotorista().trim(),20)
        adicionarComponente(pnlAreaDados,txtMotorista,1,0,2,1)
        
        label = new JLabel("Veículo")
        adicionarComponente(pnlAreaDados,label,0,2,2,1)
        
        JPanel pnlSuporteCombo = new JPanel(new BorderLayout())
        try
        {
        	veiculos = Veiculo.carregarVeiculos(aplicacao.obterConexao())
        	cbxVeiculo = new JComboBox(veiculos)
        }
        catch(Exception e)
        {
        	JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar os Veículos. ","Erro", JOptionPane.ERROR_MESSAGE)	
                e.printStackTrace()
        }

        pnlSuporteCombo.add(cbxVeiculo,BorderLayout.CENTER)
        btNovoVeiculo = new JButton(new ImageIcon("imagens/novo.jpg"))
        btNovoVeiculo.addActionListener(this)
        btNovoVeiculo.setToolTipText("Novo Veículo")
        btNovoVeiculo.setPreferredSize(new Dimension(22,20))
        pnlSuporteCombo.add(btNovoVeiculo,BorderLayout.EAST)
        adicionarComponente(pnlAreaDados,pnlSuporteCombo,1,2,2,1)
        
        label = new JLabel("Identidade")
        adicionarComponente(pnlAreaDados,label,2,0,2,1)
        label = new JLabel("Órgão Emissor")
        adicionarComponente(pnlAreaDados,label,2,2,1,1)
        label = new JLabel("CPF")
        adicionarComponente(pnlAreaDados,label,2,3,1,1)
        txtIdentidade = new JTextField((this.motorista.obterIdentidade())==null?"":this.motorista.obterIdentidade().trim(),15)
        adicionarComponente(pnlAreaDados,txtIdentidade,3,0,2,1)
        txtOrgaoEmissor = new JTextField((this.motorista.obterOrgaoEmissorIdentidade())==null?"":this.motorista.obterOrgaoEmissorIdentidade().trim(),10)
        adicionarComponente(pnlAreaDados,txtOrgaoEmissor,3,2,1,1)
        txtCpf = new JTextField((this.motorista.obterCpf())==null?"":this.motorista.obterCpf().trim(),10)
        adicionarComponente(pnlAreaDados,txtCpf,3,3,1,1)
        
        label = new JLabel("Categoria")
        adicionarComponente(pnlAreaDados,label,4,0,2,1)
        label = new JLabel("Habilitação")
        adicionarComponente(pnlAreaDados,label,4,2,1,1)
        label = new JLabel("Data de Validade")
        adicionarComponente(pnlAreaDados,label,4,3,1,1)
        
        pnlSuporteCombo = new JPanel(new BorderLayout())
        cbxCategoria = new JComboBox()
        cbxCategoria.addItem("Selecione...")
        cbxCategoria.addItem("A")
        cbxCategoria.addItem("B")
        cbxCategoria.addItem("C")
        categorias = new Vector()
        categorias.addElement(null)
        categorias.addElement("A")
        categorias.addElement("B")
        categorias.addElement("C")
                
        int indiceCategoria = 0
        for(int i = 1 i < categorias.size() i++)
        {            
            if((categorias.get(i)).equals((this.motorista.obterCategoria())==null?"":this.motorista.obterCategoria()))
            {
                indiceCategoria = i         
            }
        }        
        cbxCategoria.setSelectedIndex(indiceCategoria)                
        adicionarComponente(pnlAreaDados,cbxCategoria,5,0,2,1)
        
        txtHabilitacao = new JTextField((this.motorista.obterHabilitacao())==null?"":this.motorista.obterHabilitacao().trim(),10)
        adicionarComponente(pnlAreaDados,txtHabilitacao,5,2,1,1)
        txtValidade = new JTextField(this.validade,10)
        adicionarComponente(pnlAreaDados,txtValidade,5,3,1,1)

        JPanel pnlContato = new JPanel(gridbag)
        pnlContato.setBorder(new TitledBorder("Contato"))
        label = new JLabel("Logradouro")
        adicionarComponente(pnlContato,label,0,0,2,1)
        label = new JLabel("Complemento")
        adicionarComponente(pnlContato,label,0,2,1,1)
        label = new JLabel("Bairro")
        adicionarComponente(pnlContato,label,0,3,1,1)
        txtLogradouro = new JTextField((this.motorista.obterLogradouro())==null?"":this.motorista.obterLogradouro().trim(),20)
        adicionarComponente(pnlContato,txtLogradouro,1,0,2,1)
        txtComplemento = new JTextField((this.motorista.obterComplemento())==null?"":this.motorista.obterComplemento().trim(),10)
        adicionarComponente(pnlContato,txtComplemento,1,2,1,1)
        txtBairro = new JTextField((this.motorista.obterBairro())==null?"":this.motorista.obterBairro().trim(),10)
        adicionarComponente(pnlContato,txtBairro,1,3,1,1)
        
        label = new JLabel("Cidade")
        adicionarComponente(pnlContato,label,2,0,1,1)
        label = new JLabel("Estado")
        adicionarComponente(pnlContato,label,2,1,2,1)
        label = new JLabel("CEP")
        adicionarComponente(pnlContato,label,2,3,1,1)
        txtCidade = new JTextField((this.motorista.obterCidade())==null?"":this.motorista.obterCidade().trim(),10)
        adicionarComponente(pnlContato,txtCidade,3,0,1,1)
        pnlSuporteCombo = new JPanel(new BorderLayout())
        cbxEstado = new JComboBox()
        try
        {
            estados = Estado.carregarEstados("BRA",aplicacao.obterConexao())
            carregarEstados()
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar os Estados","Erro", JOptionPane.ERROR_MESSAGE)
            e.printStackTrace()
        }

        pnlSuporteCombo.add(cbxEstado,BorderLayout.CENTER)
        btNovoEstado = new JButton(new ImageIcon("imagens/novo.jpg"))
        btNovoEstado.addActionListener(this)
        btNovoEstado.setToolTipText("Novo Estado")
        btNovoEstado.setPreferredSize(new Dimension(22,20))
        pnlSuporteCombo.add(btNovoEstado,BorderLayout.EAST)
        adicionarComponente(pnlContato,pnlSuporteCombo,3,1,2,1)
        txtCep = new JTextField((this.motorista.obterCep())==null?"":this.motorista.obterCep().trim(),8)
        adicionarComponente(pnlContato,txtCep,3,3,1,1)
        
        label = new JLabel("Telefone")
        adicionarComponente(pnlContato,label,4,0,1,1)
        label = new JLabel("Celular")
        adicionarComponente(pnlContato,label,4,1,1,1)
        txtTelefone = new JTextField((this.motorista.obterTelefone())==null?"":this.motorista.obterTelefone().trim(),8)
        adicionarComponente(pnlContato,txtTelefone,5,0,1,1)
        txtCelular = new JTextField((this.motorista.obterCelular())==null?"":this.motorista.obterCelular().trim(),8)
        adicionarComponente(pnlContato,txtCelular,5,1,1,1)        
        
        adicionarComponente(pnlAreaDados,pnlContato,6,0,4,1)   
        
        conteudo.add(pnlAreaDados, BorderLayout.CENTER)
        
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
    
    private void carregarVeiculos()
    {
        cbxVeiculo.removeAllItems()
        cbxVeiculo.addItem("Selecione...")
        
        for(int i = 1i < veiculos.size()i++)
        {
            cbxVeiculo.addItem(((Veiculo)veiculos.get(i)).obterPlaca())
        }
    }
    
    private void carregarEstados()
    {
        cbxEstado.removeAllItems()
        cbxEstado.addItem("Selecione...")
        
        for(int i = 1i < estados.size()i++)
        {
            cbxEstado.addItem(((Estado)estados.get(i)).getNome())
        }
    }
  
    public void actionPerformed(java.awt.event.ActionEvent actionEvent) 
    {
    	Object objeto = actionEvent.getSource()
        
        if(objeto == btNovoEstado)
        {
            DlgDadosEstado dlgDadosEstado = new DlgDadosEstado(aplicacao,'I')
            dlgDadosEstado.setVisible(true)
            try
            {
                estados = Estado.carregarEstados("BRA",aplicacao.obterConexao())
                carregarEstados()
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar os Estados","Erro", JOptionPane.ERROR_MESSAGE)
                e.printStackTrace()
            }
        }
        
        if(objeto == btNovoVeiculo)
        {
            DlgDadosVeiculo dlgDadosVeiculo = new DlgDadosVeiculo(aplicacao)
            dlgDadosVeiculo.setVisible(true)
            try
            {
                veiculos = Veiculo.carregarVeiculos(aplicacao.obterConexao())
                carregarVeiculos()
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar os Veículos","Erro", JOptionPane.ERROR_MESSAGE)
                e.printStackTrace()
            }
        }
    	
    	if(objeto == btCancelar)
        {
            this.setVisible(false)
        }
	    
        if(objeto == btConfirmar)
        {                                       
            if(motorista == null)
            {                     
                boolean confirmado = true
                try
                {                        
                    motorista.definirMotorista(this.txtMotorista.getText())
                    motorista.definirPlaca(((Veiculo)veiculos.get(this.cbxVeiculo.getSelectedIndex()))==null?"":((Veiculo)veiculos.get(this.cbxVeiculo.getSelectedIndex())).obterPlaca())
                    motorista.definirIdentidade(this.txtIdentidade.getText())
                    motorista.definirOrgaoEmissorIdentidade(this.txtOrgaoEmissor.getText())
                    motorista.definirCpf(this.txtCpf.getText())
                    motorista.definirCategoria(((String)this.categorias.get(this.cbxCategoria.getSelectedIndex()))==null?"":(String)this.categorias.get(this.cbxCategoria.getSelectedIndex()))
                    motorista.definirHabilitacao(this.txtHabilitacao.getText())                        
                    motorista.definirValidade(this.txtValidade.getText())
                    motorista.definirLogradouro(this.txtLogradouro.getText())
                    motorista.definirComplemento(this.txtComplemento.getText())
                    motorista.definirBairro(this.txtBairro.getText())
                    motorista.definirCidade(this.txtCidade.getText())
                    motorista.definirEstado((Estado)this.estados.get(this.cbxEstado.getSelectedIndex()))
                    motorista.definirCep(this.txtCep.getText())
                    motorista.definirTelefone(this.txtTelefone.getText())
                    motorista.definirCelular(this.txtCelular.getText())                        
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
                        this.motorista.cadastrarMotorista()
                    }
                    catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE)	
                        e.printStackTrace()
                    }
                    this.setVisible(false)
                }
            }
            else
            {  
                boolean confirmado = true
                try
                {                        
                    motorista.definirCodigo(this.codigoMotorista)
                    motorista.definirPlaca(((Veiculo)veiculos.get(this.cbxVeiculo.getSelectedIndex())).obterPlaca())
                    motorista.definirMotorista(this.txtMotorista.getText())
                    motorista.definirIdentidade(this.txtIdentidade.getText())
                    motorista.definirOrgaoEmissorIdentidade(this.txtOrgaoEmissor.getText())
                    motorista.definirCpf(this.txtCpf.getText())
                    motorista.definirCategoria((String)this.categorias.get(this.cbxCategoria.getSelectedIndex()))
                    motorista.definirHabilitacao(this.txtHabilitacao.getText())                        
                    motorista.definirValidade(this.txtValidade.getText())
                    motorista.definirLogradouro(this.txtLogradouro.getText())
                    motorista.definirComplemento(this.txtComplemento.getText())
                    motorista.definirBairro(this.txtBairro.getText())
                    motorista.definirCidade(this.txtCidade.getText())
                    motorista.definirEstado((Estado)this.estados.get(this.cbxEstado.getSelectedIndex()))
                    motorista.definirCep(this.txtCep.getText())
                    motorista.definirTelefone(this.txtTelefone.getText())
                    motorista.definirCelular(this.txtCelular.getText())
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
                       this.motorista.alterarMotorista()
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
    }
    
    public void focusLost(java.awt.event.FocusEvent focusEvent) {}
    
    public void focusGained(java.awt.event.FocusEvent focusEvent) {}   
}