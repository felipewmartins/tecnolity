package org.esmerilprogramming.tecnolity.administracao.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import org.esmerilprogramming.tecnolity.administracao.*;
import org.esmerilprogramming.tecnolity.aplicacao.Aplicacao;

/**
   * Projeto: 001 - Tecnolity <br>
   * Autor do Código: Hildeberto Mendonça Filho <br>
   * Nome do Arquivo: DlgDadosColaborador.java <br>
   * Linguagem: Java <br>
   *  <br>
   * Requerimentos: Requer máquina virtual java versão 1.3 ou superior. <br>
   *  <br>
   * Objetivo: Diálogo para cadastramento,alteração e visualização de dados de um colaborador. <br>
   * <br> 
   * Objetivo definido por: Hildeberto Mendonça <br>
   * Início da Programação: 25/12/2001 <br>
   * Fim da Programação: <br>
   * Última Versão: 1.0 <br>
*/

public class DlgDadosColaborador extends JDialog implements ActionListener, FocusListener
{
    public final int IDENTIFICADOR = 10;
    
    private Aplicacao aplicacao;
    private char modo; // I = inhserir, A = alterar, V = visualizar
    private Vector estados, paises;
    private Vector departamentos;
    private Pais pais = new Pais();
    private Estado estado = new Estado();
    private Colaborador colaborador;
    
    private Container conteudo;
    private JPanel pnlAreaDados;
    private JButton btConfirmar, btCancelar, btNovoEstado, btNovoDepartamento;
    
    private GridBagLayout gridbag;
    private GridBagConstraints gbc;
    
    private JTextField  txtNomeCompleto, txtIdentidade, txtOrgaoEmissor, txtCpf,
                        txtLogradouro, txtMatricula, txtComplemento,
                        txtBairro, txtCidade, txtCep, txtDdd, txtTelefone,
                        txtRamal, txtCelular, txtEmail;
    private JPasswordField txpSenha, txpConfirmacaoSenha;
    private JRadioButton rdbSexoMasculino, rdbSexoFeminino;
    private JComboBox cbxDepartamento, cbxEstado, cbxPais;
    
    public DlgDadosColaborador(Aplicacao aplicacao, char modo)
    {
        super(aplicacao,true);
        colaborador = new Colaborador();
        
        // Define o título da janela
        String tituloJanela = "";
        if (modo == 'I')
            tituloJanela = "Novo Colaborador";	   
        this.setTitle(tituloJanela);
        
        this.aplicacao = aplicacao;
        this.modo = modo;
        this.departamentos = new Vector();
        montarInterface();
    }
    
    public DlgDadosColaborador(Aplicacao aplicacao, char modo, String usuario)
    {
        super(aplicacao,true);
        try
        {
            colaborador = new Colaborador(usuario,aplicacao.obterConexao());
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(aplicacao,"Erro: "+e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }        
        // Define o título da janela
        String tituloJanela = "";
        if (modo == 'A')
            tituloJanela = "Colaborador";
        if (modo == 'V')
            tituloJanela = "Colaborador";
        this.setTitle(tituloJanela);

        this.aplicacao = aplicacao;
        this.modo = modo;
        
        montarInterface();
    }
    
    private void montarInterface()
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
        
        JLabel label = new JLabel("Nome Completo");
        adicionarComponente(pnlAreaDados,label,0,0,3,1);
        
        JPanel pnlSexo = new JPanel();
        pnlSexo.setBorder(new TitledBorder("Sexo"));
        ButtonGroup grupo = new ButtonGroup();
        rdbSexoMasculino = new JRadioButton("Masculino");
        if(colaborador != null)
            if(this.colaborador.obterSexo() == 'M' || this.colaborador.obterSexo() == '\u0000')
                rdbSexoMasculino.setSelected(true);        
        grupo.add(rdbSexoMasculino);
        pnlSexo.add(rdbSexoMasculino);
        rdbSexoFeminino = new JRadioButton("Feminino");
        if(colaborador != null)
            if(this.colaborador.obterSexo() == 'F')
                rdbSexoFeminino.setSelected(true);
        grupo.add(rdbSexoFeminino);
        pnlSexo.add(rdbSexoFeminino);            
        adicionarComponente(pnlAreaDados,pnlSexo,0,2,2,2);
        
        
        txtNomeCompleto = new JTextField(this.colaborador.getNome(),20);
        adicionarComponente(pnlAreaDados,txtNomeCompleto,1,0,2,1);
        
        label = new JLabel("Identidade");
        adicionarComponente(pnlAreaDados,label,2,0,2,1);
        label = new JLabel("Órgão Emissor");
        adicionarComponente(pnlAreaDados,label,2,2,1,1);
        label = new JLabel("CPF");
        adicionarComponente(pnlAreaDados,label,2,3,1,1);
        txtIdentidade = new JTextField(this.colaborador.getIdentidade(),20);
        adicionarComponente(pnlAreaDados,txtIdentidade,3,0,2,1);
        txtOrgaoEmissor = new JTextField(this.colaborador.getOrgaoIdentidade(),10);
        adicionarComponente(pnlAreaDados,txtOrgaoEmissor,3,2,1,1);
        txtCpf = new JTextField(this.colaborador.getCPF(),10);
        adicionarComponente(pnlAreaDados,txtCpf,3,3,1,1);
        
        label = new JLabel("Departamento");
        adicionarComponente(pnlAreaDados,label,4,0,2,1);
        label = new JLabel("E-mail");
        adicionarComponente(pnlAreaDados,label,4,2,2,1);
        
        JPanel pnlSuporteCombo = new JPanel(new BorderLayout());
        cbxDepartamento = new JComboBox();
        this.carregarDepartamentos(cbxDepartamento);
        
        int indiceDepartamento = 0;
        for(int i = 1; i < departamentos.size(); i++)
        {            
            if((((Departamento)departamentos.get(i)).obterNomeDepartamento()).equals((this.colaborador.obterDepartamento())==null?"":(this.colaborador.obterDepartamento()).obterNomeDepartamento()))
            {
                indiceDepartamento = i;         
            }
        }        
        cbxDepartamento.setSelectedIndex(indiceDepartamento);

        pnlSuporteCombo.add(cbxDepartamento,BorderLayout.CENTER);
        btNovoDepartamento = new JButton(new ImageIcon("imagens/novo.jpg"));
        btNovoDepartamento.addActionListener(this);
        btNovoDepartamento.setToolTipText("Novo Departamento");
        btNovoDepartamento.setPreferredSize(new Dimension(22,20));
        pnlSuporteCombo.add(btNovoDepartamento,BorderLayout.EAST);
        adicionarComponente(pnlAreaDados,pnlSuporteCombo,5,0,2,1);
        
        txtEmail = new JTextField(this.colaborador.getEmail(),20);
        adicionarComponente(pnlAreaDados,txtEmail,5,2,2,1);

        JPanel pnlContato = new JPanel(gridbag);
        pnlContato.setBorder(new TitledBorder("Contato"));
        label = new JLabel("Logradouro");
        adicionarComponente(pnlContato,label,0,0,2,1);
        label = new JLabel("Complemento");
        adicionarComponente(pnlContato,label,0,2,1,1);
        label = new JLabel("Bairro");
        adicionarComponente(pnlContato,label,0,3,1,1);
        txtLogradouro = new JTextField(this.colaborador.getLogradouro(),19);
        adicionarComponente(pnlContato,txtLogradouro,1,0,2,1);
        txtComplemento = new JTextField(this.colaborador.getComplemento(),10);
        adicionarComponente(pnlContato,txtComplemento,1,2,1,1);
        txtBairro = new JTextField(this.colaborador.getBairro(),10);
        adicionarComponente(pnlContato,txtBairro,1,3,1,1);
        
        label = new JLabel("Cidade");
        adicionarComponente(pnlContato,label,2,0,1,1);
        label = new JLabel("Estado");
        adicionarComponente(pnlContato,label,2,1,2,1);
        label = new JLabel("CEP");
        adicionarComponente(pnlContato,label,2,3,1,1);
        txtCidade = new JTextField(this.colaborador.getCidade(),10);
        adicionarComponente(pnlContato,txtCidade,3,0,1,1);
        pnlSuporteCombo = new JPanel(new BorderLayout());
        cbxEstado = new JComboBox();
        try
        {
            estados = Estado.carregarEstados("BRA",aplicacao.obterConexao());
            carregarEstados();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar os Estados","Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        
        int indiceEstado = 0;
        for(int i = 1; i < estados.size(); i++)
        {            
            if((((Estado)estados.get(i)).getSigla()).equals((this.colaborador.obterEstado())==null?"":(this.colaborador.obterEstado()).getSigla()))
            {
                indiceEstado = i;         
            }
        }        
        cbxEstado.setSelectedIndex(indiceEstado);
        
        pnlSuporteCombo.add(cbxEstado,BorderLayout.CENTER);
        btNovoEstado = new JButton(new ImageIcon("imagens/novo.jpg"));
        btNovoEstado.addActionListener(this);
        btNovoEstado.setToolTipText("Novo Estado");
        btNovoEstado.setPreferredSize(new Dimension(22,20));
        pnlSuporteCombo.add(btNovoEstado,BorderLayout.EAST);
        adicionarComponente(pnlContato,pnlSuporteCombo,3,1,2,1);
        txtCep = new JTextField(this.colaborador.getCEP(),10);
        adicionarComponente(pnlContato,txtCep,3,3,1,1);
        
        label = new JLabel("DDD");
        adicionarComponente(pnlContato,label,4,0,1,1);
        label = new JLabel("Telefone");
        adicionarComponente(pnlContato,label,4,1,1,1);
        label = new JLabel("Ramal");
        adicionarComponente(pnlContato,label,4,2,1,1);
        label = new JLabel("Celular");
        adicionarComponente(pnlContato,label,4,3,1,1);
        txtDdd = new JTextField(this.colaborador.getDDD(),4);
        adicionarComponente(pnlContato,txtDdd,5,0,1,1);
        txtTelefone = new JTextField(this.colaborador.getTelefone(),8);
        adicionarComponente(pnlContato,txtTelefone,5,1,1,1);
        txtRamal = new JTextField(this.colaborador.obterRamal(),6);
        adicionarComponente(pnlContato,txtRamal,5,2,1,1);
        txtCelular = new JTextField(this.colaborador.getCelular(),8);
        adicionarComponente(pnlContato,txtCelular,5,3,1,1);        
        
        adicionarComponente(pnlAreaDados,pnlContato,6,0,4,1);
        
        JPanel pnlAutenticacao = new JPanel(gridbag);
        pnlAutenticacao.setBorder(new TitledBorder("Identificação"));
        label = new JLabel("Usuário");
        adicionarComponente(pnlAutenticacao,label,0,0,1,1);
        label = new JLabel("Senha");
        adicionarComponente(pnlAutenticacao,label,0,1,1,1);
        label = new JLabel("Confirmação de Senha");
        adicionarComponente(pnlAutenticacao,label,0,2,1,1);        
        txtMatricula = new JTextField(this.colaborador.obterMatricula(),10);
        if(modo == 'A')
            txtMatricula.setEditable(false);
        adicionarComponente(pnlAutenticacao,txtMatricula,1,0,1,1);
        txpSenha = new JPasswordField(this.colaborador.obterSenha(),10);
        adicionarComponente(pnlAutenticacao,txpSenha,1,1,1,1);
        txpConfirmacaoSenha = new JPasswordField(this.colaborador.obterSenha(),10);
        adicionarComponente(pnlAutenticacao,txpConfirmacaoSenha,1,2,1,1);
        
        adicionarComponente(pnlAreaDados,pnlAutenticacao,7,0,4,1);
        
        conteudo.add(pnlAreaDados, BorderLayout.CENTER);
        
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
    
    private void carregarEstados()
    {
        cbxEstado.removeAllItems();
        cbxEstado.addItem("Selecione...");
        
        for(int i = 1;i < estados.size();i++)
        {
            cbxEstado.addItem(((Estado)estados.get(i)).getNome());
        }
    }
    
    private void carregarDepartamentos(JComboBox comboBox)
    {
        Departamento departamento = new Departamento();
        comboBox.removeAllItems();
        try
        {
            departamentos = departamento.carregarDepartamentos(aplicacao.obterConexao());
            comboBox.addItem("Selecione...");
            for(int i=1;i < this.departamentos.size();i++)
            {
                comboBox.addItem(((Departamento)this.departamentos.get(i)).obterNomeDepartamento());
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
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
        
        if(objeto == btNovoEstado)
        {
            DlgDadosEstado dlgDadosEstado = new DlgDadosEstado(aplicacao,'I');
            dlgDadosEstado.setVisible(true);
            try
            {
                estados = Estado.carregarEstados("BRA",aplicacao.obterConexao());
                carregarEstados();
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar os Estados","Erro", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
        
        if(objeto == btNovoDepartamento)
        {
            DlgDadosDepartamento dlgDadosDepartamento = new DlgDadosDepartamento(aplicacao);
            dlgDadosDepartamento.setVisible(true);
            try
            {
                departamentos = new Departamento().carregarDepartamentos(aplicacao.obterConexao());
                this.carregarDepartamentos(cbxDepartamento);
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
        
        if(objeto == btConfirmar)
        {
            try
            {
                colaborador.definirMatricula(this.txtMatricula.getText());
                colaborador.definirSenha(String.valueOf(this.txpSenha.getPassword()));
                colaborador.definirSexo((this.rdbSexoMasculino.isSelected()?'M':'F'));
                colaborador.setNome(this.txtNomeCompleto.getText());
                colaborador.definirIdentidade(this.txtIdentidade.getText());
                colaborador.definirOrgaoEmissorIdentidade(this.txtOrgaoEmissor.getText());
                colaborador.definirCpf(this.txtCpf.getText());
                colaborador.definirDepartamento((Departamento)this.departamentos.get(this.cbxDepartamento.getSelectedIndex()));
                colaborador.definirLogradouro(this.txtLogradouro.getText());
                colaborador.definirComplemento(this.txtComplemento.getText());
                colaborador.definirBairro(this.txtBairro.getText());
                colaborador.definirCidade(this.txtCidade.getText());
                colaborador.setEstado((Estado)this.estados.get(this.cbxEstado.getSelectedIndex()));
                colaborador.definirCep(this.txtCep.getText());
                colaborador.setDDD(this.txtDdd.getText());
                colaborador.definirTelefone(this.txtTelefone.getText());
                colaborador.definirRamal(this.txtRamal.getText());
                colaborador.definirCelular(this.txtCelular.getText());
                colaborador.definirEmail(this.txtEmail.getText());
                colaborador.senhaAlterada(true);
                
                if(modo == 'I')                
                    colaborador.cadastrarColaborador();
                else if(modo == 'A')
                    colaborador.alterarColaborador();
                this.setVisible(false);
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
        
        if(objeto == btCancelar)
        {
        	this.setVisible(false);
        }
    }
    
    public void focusLost(java.awt.event.FocusEvent focusEvent) 
    {
    }
    
    public void focusGained(java.awt.event.FocusEvent e) 
    {
        Component componente = e.getComponent();
    }
}