package org.esmerilprogramming.tecnolity.suprimentos.ui

import java.awt.*
import java.awt.event.*

import javax.swing.*
import javax.swing.border.*
import org.esmerilprogramming.tecnolity.administracao.*
import org.esmerilprogramming.tecnolity.administracao.ui.*
import org.esmerilprogramming.tecnolity.aplicacao.Aplicacao
import org.esmerilprogramming.tecnolity.suprimentos.*

class DlgDadosFornecedor extends JDialog implements ActionListener, FocusListener {
  final int IDENTIFICADOR = 16

  private Aplicacao aplicacao
  private char modo // I = inhserir, A = alterar, V = visualizar
  private Fornecedor fornecedor
  private Vector estados, paises
  private Container conteudo
  private JPanel pnlAreaDados
  private JButton btConfirmar, btCancelar, btNovoPais, btNovoEstado
  private GridBagLayout gridbag
  private GridBagConstraints gbc
  private JTextField  txtRazaoSocial, txtCnpj, txtPercentualICMS , txtLogradouro,
  txtComplemento, txtBairro, txtCidade,
  txtCep, txtDdd, txtTelefone, txtRamal,
  txtFax, txtEmail, txtWebSite
  private JComboBox   cbxEstado, cbxPais

  DlgDadosFornecedor(Aplicacao aplicacao, char modo) {
    super(aplicacao,true)
    fornecedor = new Fornecedor()
    String tituloJanela = ''
    if (modo == 'I') {
      tituloJanela = 'Novo Fornecedor'
    }
    if (modo == 'A') {
      tituloJanela = 'Fornecedor'
    }
    if (modo == 'V') {
      tituloJanela = 'Fornecedor'
    }
    this.setTitle(tituloJanela)
    this.aplicacao = aplicacao
    this.modo = modo
    montarInterface()
    }

  DlgDadosFornecedor(Aplicacao aplicacao, char modo, int codigoFornecedor) {
    super(aplicacao,true)
    try {
      fornecedor = new Fornecedor(codigoFornecedor)
    } catch(e) {
      JOptionPane.showMessageDialog(aplicacao,'Erro: '+e.getMessage(),'Erro',JOptionPane.ERROR_MESSAGE)
      e.printStackTrace()
    }

    String tituloJanela = ''
    if (modo == 'I') {
      tituloJanela = 'Novo Fornecedor'
    }
    if (modo == 'A') {
      tituloJanela = 'Fornecedor'
    }
    if (modo == 'V') {
      tituloJanela = 'Fornecedor'
    }
    this.setTitle(tituloJanela)
    this.aplicacao = aplicacao
    this.modo = modo
    montarInterface()
  }

  private void montarInterface() {
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
    JLabel label = new JLabel('Razão Social')
    adicionarComponente(pnlAreaDados,label,0,0,3,1)
    label = new JLabel('CNPJ (Somente Números)')
    adicionarComponente(pnlAreaDados,label,0,3,1,1)
    txtRazaoSocial = new JTextField((this.fornecedor.obterRazaoSocial())==null?'':this.fornecedor.obterRazaoSocial().trim(),28)
    adicionarComponente(pnlAreaDados,txtRazaoSocial,1,0,3,1)
    txtCnpj = new JTextField((this.fornecedor.obterCnpj())==null?'':this.fornecedor.obterCnpj().trim(),10)
    adicionarComponente(pnlAreaDados,txtCnpj,1,3,1,1)
    conteudo.add(pnlAreaDados,BorderLayout.CENTER)
    label = new JLabel('ICMS (%)')
    adicionarComponente(pnlAreaDados,label,2,0,1,1)
    txtPercentualICMS = new JTextField(''+this.fornecedor.obterPercentualICMS(),3)
    txtPercentualICMS.addFocusListener(this)
    adicionarComponente(pnlAreaDados,txtPercentualICMS,3,0,1,1)
    JPanel pnlEndereco = new JPanel(gridbag)
    pnlEndereco.setBorder(new TitledBorder('Endereço'))
    label = new JLabel('Logradouro')
    adicionarComponente(pnlEndereco,label,0,0,2,1)
    label = new JLabel('Complemento')
    adicionarComponente(pnlEndereco,label,0,2,1,1)
    label = new JLabel('Bairro')
    adicionarComponente(pnlEndereco,label,0,3,1,1)
    txtLogradouro = new JTextField((this.fornecedor.obterLogradouro())==null?'':this.fornecedor.obterLogradouro().trim(),20)
    adicionarComponente(pnlEndereco,txtLogradouro,1,0,2,1)
    txtComplemento = new JTextField((this.fornecedor.obterComplemento())==null?'':this.fornecedor.obterComplemento().trim(),10)
    adicionarComponente(pnlEndereco,txtComplemento,1,2,1,1)
    txtBairro = new JTextField((this.fornecedor.obterBairro())==null?'':this.fornecedor.obterBairro().trim(),10)
    adicionarComponente(pnlEndereco,txtBairro,1,3,1,1)
    label = new JLabel('Cidade')
    adicionarComponente(pnlEndereco,label,2,0,2,1)
    label = new JLabel('País')
    adicionarComponente(pnlEndereco,label,2,2,2,1)
    txtCidade = new JTextField((this.fornecedor.obterCidade())==null?'':this.fornecedor.obterCidade().trim(),20)
    adicionarComponente(pnlEndereco,txtCidade,3,0,2,1)
    JPanel pnlSuporteCombo = new JPanel(new BorderLayout())
    cbxPais = new JComboBox()
    cbxPais.addActionListener(this)
    try {
      paises = Pais.carregarPaises(aplicacao.obterConexao())
      carregarPaises()
    }
      catch(e) {
        JOptionPane.showMessageDialog(aplicacao,'Erro: Não foi possível carregar os Países.','Erro',JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }

        int indicePais = 0
        for(int i = 1; i < paises.size(); i++) {
          if((((Pais)paises.get(i)).getSigla()).equals((this.fornecedor.obterPais())==null?'':(this.fornecedor.obterPais()).getSigla())) {
            indicePais = i
          }
        }
      cbxPais.setSelectedIndex(indicePais)
        
        pnlSuporteCombo.add(cbxPais,BorderLayout.CENTER)
        btNovoPais = new JButton(new ImageIcon('imagens/novo.jpg'))
        btNovoPais.addActionListener(this)
        btNovoPais.setToolTipText('Novo Pais')
        btNovoPais.setPreferredSize(new Dimension(22,20))
        pnlSuporteCombo.add(btNovoPais,BorderLayout.EAST)
       
        adicionarComponente(pnlEndereco,pnlSuporteCombo,3,2,2,1)
        label = new JLabel('Estado')
        adicionarComponente(pnlEndereco,label,4,0,2,1)
        label = new JLabel('CEP (Somente Números)')
        adicionarComponente(pnlEndereco,label,4,2,2,1)
        
        pnlSuporteCombo = new JPanel(new BorderLayout())
        cbxEstado = new JComboBox()
        try
        {
          estados = Estado.carregarEstados((this.fornecedor.obterPais())==null?'BRA':(String)(this.fornecedor.obterPais()).getSigla(),aplicacao.obterConexao())
            carregarEstados()
        }
      catch(Exception e) {
        JOptionPane.showMessageDialog(aplicacao,'Erro: Não foi possível carregar os Estados','Erro', JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
      
        pnlSuporteCombo.add(cbxEstado,BorderLayout.CENTER)
        btNovoEstado = new JButton(new ImageIcon('imagens/novo.jpg'))
        btNovoEstado.addActionListener(this)
        btNovoEstado.setToolTipText('Novo Estado')
        btNovoEstado.setPreferredSize(new Dimension(22,20))
        pnlSuporteCombo.add(btNovoEstado,BorderLayout.EAST)
        
        adicionarComponente(pnlEndereco,pnlSuporteCombo,5,0,2,1)
        txtCep = new JTextField((this.fornecedor.obterCep())==null?'':this.fornecedor.obterCep().trim(),10)
        adicionarComponente(pnlEndereco,txtCep,5,2,2,1)
        
        adicionarComponente(pnlAreaDados,pnlEndereco,4,0,4,1)
        
        JPanel pnlContato = new JPanel(gridbag)
        pnlContato.setBorder(new TitledBorder('Contato'))
        label = new JLabel('DDD')
        adicionarComponente(pnlContato,label,0,0,1,1)
        label = new JLabel('Telefone')
        adicionarComponente(pnlContato,label,0,1,1,1)
        label = new JLabel('Ramal')
        adicionarComponente(pnlContato,label,0,2,1,1)
        label = new JLabel('Fax')
        adicionarComponente(pnlContato,label,0,3,1,1)
        txtDdd = new JTextField((this.fornecedor.obterDdd())==null?'':this.fornecedor.obterDdd().trim(),9)
        adicionarComponente(pnlContato,txtDdd,1,0,1,1)
        txtTelefone = new JTextField((this.fornecedor.obterTelefone())==null?'':this.fornecedor.obterTelefone().trim(),10)
        adicionarComponente(pnlContato,txtTelefone,1,1,1,1)
        txtRamal = new JTextField((this.fornecedor.obterRamal())==null?'':this.fornecedor.obterRamal().trim(),10)
        adicionarComponente(pnlContato,txtRamal,1,2,1,1)
        txtFax = new JTextField((this.fornecedor.obterFax())==null?'':this.fornecedor.obterFax().trim(),10)
        adicionarComponente(pnlContato,txtFax,1,3,1,1)
        label = new JLabel('E-mail')
        adicionarComponente(pnlContato,label,2,0,2,1)
        label = new JLabel('Web Site')
        adicionarComponente(pnlContato,label,2,2,2,1)
        txtEmail = new JTextField((this.fornecedor.obterEmail())==null?'':this.fornecedor.obterEmail().trim(),20)
        adicionarComponente(pnlContato,txtEmail,3,0,2,1)
        txtWebSite = new JTextField((this.fornecedor.obterWebsite())==null?'':this.fornecedor.obterWebsite().trim(),20)
        adicionarComponente(pnlContato,txtWebSite,3,2,2,1)
        
        adicionarComponente(pnlAreaDados,pnlContato,5,0,4,1)
        
        JPanel pnlComandos = new JPanel(new FlowLayout(FlowLayout.RIGHT))
        
        btConfirmar = new JButton('Confirmar')
        btConfirmar.addActionListener(this)
        pnlComandos.add(btConfirmar)
        
        btCancelar = new JButton('Cancelar')
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
  
    private void carregarPaises() {
      cbxPais.removeAllItems()
        cbxPais.addItem('Selecione...')
        
        for(int i = 1;i < paises.size();i++) {
          cbxPais.addItem(((Pais)paises.get(i)).getNome())
        }
    }
  
    private void carregarEstados() {
      cbxEstado.removeAllItems()
        cbxEstado.addItem('Selecione...')
        
        for(int i = 1;i < estados.size();i++) {
          cbxEstado.addItem(((Estado)estados.get(i)).getNome())
        }
    }
  
    private void adicionarComponente(JPanel painel, Component c, int linha, int coluna, int largura, int altura) {
      gbc.gridx = coluna
        gbc.gridy = linha
        
        gbc.gridwidth = largura
        gbc.gridheight = altura
        
        gridbag.setConstraints(c,gbc)
        painel.add(c)
    }
  
    void actionPerformed(java.awt.event.ActionEvent actionEvent) {
      Object objeto = actionEvent.getSource()
        
        if(objeto == cbxPais) {
          if(cbxEstado != null) {
            try
            {
              estados = Estado.carregarEstados(((Pais)paises.get(cbxPais.getSelectedIndex())).getSigla(),aplicacao.obterConexao())
                carregarEstados()
            }
            catch(Exception e) {
              JOptionPane.showMessageDialog(aplicacao,'Erro: Não foi possível carregar os Estados','Erro', JOptionPane.ERROR_MESSAGE)
                e.printStackTrace()
            }
          }
        }
      
        if(objeto == btNovoPais) {
          DlgDadosPais dlgDadosPais = new DlgDadosPais(aplicacao,'I')
            dlgDadosPais.setVisible(true)
            try
            {
              Pais pais = new Pais()
                paises = Pais.carregarPaises(aplicacao.obterConexao())
                carregarPaises()
            }
          catch(Exception e) {
            JOptionPane.showMessageDialog(aplicacao,'Erro: Não foi possível carregar os Países.','Erro',JOptionPane.ERROR_MESSAGE)
              e.printStackTrace()
          }
        }
      
        if(objeto == btNovoEstado) {
          DlgDadosEstado dlgDadosEstado = new DlgDadosEstado(aplicacao,'I')
            dlgDadosEstado.setVisible(true)
            try
            {
              Estado estado = new Estado()
                estados = Estado.carregarEstados((this.fornecedor.obterPais())==null?'BRA':(String)(this.fornecedor.obterPais()).getSigla(),aplicacao.obterConexao())
                carregarEstados()
            }
          catch(Exception e) {
            JOptionPane.showMessageDialog(aplicacao,'Erro: Não foi possível carregar os Estados','Erro', JOptionPane.ERROR_MESSAGE)
              e.printStackTrace()
          }
        }
      
        if(objeto == btConfirmar) {
          try
          {
            if(modo == 'I') {
              boolean confirmado = true
                try
                {
                  fornecedor.definirRazaoSocial(txtRazaoSocial.getText())
                    fornecedor.definirCnpj(txtCnpj.getText())
                    fornecedor.definirPercentualIcms(Integer.parseInt(txtPercentualICMS.getText()))
                    fornecedor.definirLogradouro(txtLogradouro.getText())
                    fornecedor.definirComplemento(txtComplemento.getText())
                    fornecedor.definirBairro(txtBairro.getText())
                    fornecedor.definirCidade(txtCidade.getText())
                    fornecedor.definirPais((Pais)paises.get(cbxPais.getSelectedIndex()))
                    fornecedor.definirEstado((Estado)estados.get(cbxEstado.getSelectedIndex()))
                    fornecedor.definirCep(txtCep.getText())
                    fornecedor.definirDdd(txtDdd.getText())
                    fornecedor.definirTelefone(txtTelefone.getText())
                    fornecedor.definirRamal(txtRamal.getText())
                    fornecedor.definirFax(txtFax.getText())
                    fornecedor.definirEmail(txtEmail.getText())
                    fornecedor.definirWebsite(txtWebSite.getText())
                }
              catch(Exception e) {
                JOptionPane.showMessageDialog(aplicacao,'Erro: ' + e.getMessage(),'Erro',JOptionPane.ERROR_MESSAGE)
                  e.printStackTrace()
                  confirmado = false
              }
              if(confirmado) {
                try
                {
                  fornecedor.cadastrarFornecedor()
                }
                catch(Exception e) {
                  JOptionPane.showMessageDialog(aplicacao,'Erro: ' + e.getMessage(),'Erro',JOptionPane.ERROR_MESSAGE)
                    e.printStackTrace()
                }
                this.setVisible(false)
              }
            }
            else if(modo == 'A') {
              boolean confirmado = true
                try
                {
                  fornecedor.definirCodigo(this.fornecedor.obterCodigo())
                    fornecedor.definirRazaoSocial(txtRazaoSocial.getText())
                    fornecedor.definirCnpj(txtCnpj.getText())
                    fornecedor.definirPercentualIcms(Integer.parseInt(txtPercentualICMS.getText()))
                    fornecedor.definirLogradouro(txtLogradouro.getText())
                    fornecedor.definirComplemento(txtComplemento.getText())
                    fornecedor.definirBairro(txtBairro.getText())
                    fornecedor.definirCidade(txtCidade.getText())
                    fornecedor.definirPais((Pais)paises.get(cbxPais.getSelectedIndex()))
                    fornecedor.definirEstado((Estado)estados.get(cbxEstado.getSelectedIndex()))
                    fornecedor.definirCep(txtCep.getText())
                    fornecedor.definirDdd(txtDdd.getText())
                    fornecedor.definirTelefone(txtTelefone.getText())
                    fornecedor.definirRamal(txtRamal.getText())
                    fornecedor.definirFax(txtFax.getText())
                    fornecedor.definirEmail(txtEmail.getText())
                    fornecedor.definirWebsite(txtWebSite.getText())
                }
              catch(e) {
                JOptionPane.showMessageDialog(aplicacao,'Erro: ' + e.getMessage(),'Erro',JOptionPane.ERROR_MESSAGE)
                e.printStackTrace()
                confirmado = false
              }
              if(confirmado) {
                try {
                  fornecedor.alterarFornecedor()
                }
                catch(Exception e) {
                  JOptionPane.showMessageDialog(aplicacao,'Erro: ' + e.getMessage(),'Erro',JOptionPane.ERROR_MESSAGE)
                    e.printStackTrace()
                }
                this.setVisible(false)
              }
            }
          }
          catch(NumberFormatException n) {
            JOptionPane.showMessageDialog(aplicacao,'Erro: Valor incorreto.','Erro',JOptionPane.ERROR_MESSAGE)
              n.printStackTrace()
          } catch(e) {
            JOptionPane.showMessageDialog(aplicacao,'Erro: ' + e.getMessage(),'Erro',JOptionPane.ERROR_MESSAGE)
            e.printStackTrace()
          }
        }
      
        if(objeto == btCancelar) {
          this.setVisible(false)
        }
    }
  
  void focusLost(java.awt.event.FocusEvent focusEvent) {
  }

  void focusGained(java.awt.event.FocusEvent e) {
    Component componente = e.getComponent()
    if(componente == txtPercentualICMS)
      txtPercentualICMS.selectAll()
  }
}
