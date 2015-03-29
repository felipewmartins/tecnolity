package org.esmerilprogramming.tecnolity.aplicacao

import java.awt.*
import java.util.*
import java.awt.event.*
import javax.swing.*
import javax.swing.border.*
import org.esmerilprogramming.tecnolity.administracao.*
import org.esmerilprogramming.tecnolity.util.*
import org.esmerilprogramming.tecnolity.aplicacao.Aplicacao
class DlgConfiguracoes extends JDialog implements ActionListener
{
  private Aplicacao aplicacao
    private Container conteudo
    private GridBagLayout gridbag
    private GridBagConstraints gbc
    private JTextField txtOrganizacaoRazaoSocial, txtOrganizacaoNomeFantasia, txtOrganizacaoCnpj, txtOrganizacaoLogradouro,
            txtOrganizacaoNumero, txtOrganizacaoComplemento, txtOrganizacaoBairro, txtOrganizacaoCidade, txtOrganizacaoCep,
            txtOrganizacaoTelefone, txtOrganizacaoFax, txtRepositorioConsultas,
            txtRepositorioRelatorios, txtRepositorioLogs,
            txtBancoDadosUsuario, txtBancoDadosDriver,
            txtBancoDadosURL, txtBancoDadosBaseDados
              private JPasswordField pwdBancoDadosSenha
              private JComboBox cbxEstado, cbxPais
              private Pais pais
              private Estado estado
              private Vector paises, estados
              private JButton btConfirmar, btCancelar
              private Configuracao configuracao

              DlgConfiguracoes(Aplicacao aplicacao,Configuracao configuracao) {
                super(aplicacao,true)

                  this.setTitle("Configurações")

                  this.aplicacao = aplicacao
                  this.configuracao = configuracao
                  this.conteudo = this.getContentPane()
                  this.montarInterface()
              }

  private void montarInterface() {
    gridbag = new GridBagLayout()
      gbc = new GridBagConstraints()
      gbc.fill = GridBagConstraints.NONE
      gbc.anchor = GridBagConstraints.NORTHWEST
      gbc.insets.bottom = 2
      gbc.insets.left = 2
      gbc.insets.right = 2
      gbc.insets.top = 2

      JTabbedPane tbnAreaDados = new JTabbedPane()

      JPanel pnlOrganizacao = new JPanel(gridbag)
      JLabel label = new JLabel("Razão Social")
      adicionarComponente(pnlOrganizacao,label,0,0,1,1)
      label = new JLabel("Nome Fantasia")
      adicionarComponente(pnlOrganizacao,label,0,1,1,1)
      txtOrganizacaoRazaoSocial = new JTextField(Configuracao.getOrganizacaoRazaoSocial(),23)
      adicionarComponente(pnlOrganizacao,txtOrganizacaoRazaoSocial,1,0,1,1)
      txtOrganizacaoNomeFantasia = new JTextField(Configuracao.getOrganizacaoNomeFantasia(),23)
      adicionarComponente(pnlOrganizacao,txtOrganizacaoNomeFantasia,1,1,1,1)
      label = new JLabel("CNPJ")
      adicionarComponente(pnlOrganizacao,label,2,0,1,1)
      txtOrganizacaoCnpj = new JTextField(Configuracao.getOrganizacaoCnpj(),15)
      adicionarComponente(pnlOrganizacao,txtOrganizacaoCnpj,3,0,1,1)

      JPanel pnlEndereco = new JPanel(gridbag)
      pnlEndereco.setBorder(new TitledBorder(new LineBorder(Color.black),"Endereço"))
      label = new JLabel("Logradouro")
      adicionarComponente(pnlEndereco,label,0,0,1,1)
      label = new JLabel("Número")
      adicionarComponente(pnlEndereco,label,0,1,1,1)
      label = new JLabel("Complemento")
      adicionarComponente(pnlEndereco,label,0,2,1,1)
      txtOrganizacaoLogradouro = new JTextField(Configuracao.getOrganizacaoLogradouro(),20)
      adicionarComponente(pnlEndereco,txtOrganizacaoLogradouro,1,0,1,1)
      txtOrganizacaoNumero = new JTextField(Configuracao.getOrganizacaoNumero(),10)
      adicionarComponente(pnlEndereco,txtOrganizacaoNumero,1,1,1,1)
      txtOrganizacaoComplemento = new JTextField(Configuracao.getOrganizacaoComplemento(),15)
      adicionarComponente(pnlEndereco,txtOrganizacaoComplemento,1,2,1,1)
      label = new JLabel("Bairro")
      adicionarComponente(pnlEndereco,label,2,0,1,1)
      label = new JLabel("Cidade")
      adicionarComponente(pnlEndereco,label,2,1,1,1)
      txtOrganizacaoBairro = new JTextField(Configuracao.getOrganizacaoBairro(),20)
      adicionarComponente(pnlEndereco,txtOrganizacaoBairro,3,0,1,1)
      txtOrganizacaoCidade = new JTextField(Configuracao.getOrganizacaoCidade(),20)
      adicionarComponente(pnlEndereco,txtOrganizacaoCidade,3,1,2,1)
      label = new JLabel("Estado")
      adicionarComponente(pnlEndereco,label,4,0,1,1)
      label = new JLabel("Pais")
      adicionarComponente(pnlEndereco,label,4,1,1,1)
      cbxEstado = new JComboBox()
      if(configuracao.isCarregada()) {
        try
        {
          estados = Estado.carregarEstados("BRA",aplicacao.obterConexao())
        }
        catch(Exception e){}
        carregarEstados(Configuracao.getOrganizacaoEstado())
      }
    adicionarComponente(pnlEndereco,cbxEstado,5,0,1,1)
      cbxPais = new JComboBox()
      if(configuracao.isCarregada()) {
        try
        {
          paises = Pais.carregarPaises(aplicacao.obterConexao())
        }
        catch(Exception e){}
        carregarPaises(Configuracao.getOrganizacaoPais())
      }
    adicionarComponente(pnlEndereco,cbxPais,5,1,2,1)
      label = new JLabel("CEP")
      adicionarComponente(pnlEndereco,label,6,0,1,1)
      label = new JLabel("Telefone")
      adicionarComponente(pnlEndereco,label,6,1,1,1)
      label = new JLabel("Fax")
      adicionarComponente(pnlEndereco,label,6,2,1,1)
      txtOrganizacaoCep = new JTextField(Configuracao.getOrganizacaoCep(),10)
      adicionarComponente(pnlEndereco,txtOrganizacaoCep,7,0,1,1)
      txtOrganizacaoTelefone = new JTextField(Configuracao.getOrganizacaoTelefone(),10)
      adicionarComponente(pnlEndereco,txtOrganizacaoTelefone,7,1,1,1)
      txtOrganizacaoFax = new JTextField(Configuracao.getOrganizacaoFax(),10)
      adicionarComponente(pnlEndereco,txtOrganizacaoFax,7,2,1,1)

      adicionarComponente(pnlOrganizacao,pnlEndereco,4,0,3,1)
      tbnAreaDados.addTab("Organização",pnlOrganizacao)

      JPanel pnlRepositorio = new JPanel(gridbag)
      label = new JLabel("Consultas")
      adicionarComponente(pnlRepositorio,label,0,0,1,1)
      txtRepositorioConsultas = new JTextField(Configuracao.getRepositorioConsultas(),45)
      adicionarComponente(pnlRepositorio,txtRepositorioConsultas,1,0,1,1)
      label = new JLabel("Relatórios")
      adicionarComponente(pnlRepositorio,label,2,0,1,1)
      txtRepositorioRelatorios = new JTextField(Configuracao.getRepositorioRelatorios(),45)
      adicionarComponente(pnlRepositorio,txtRepositorioRelatorios,3,0,1,1)
      label = new JLabel("Logs")
      adicionarComponente(pnlRepositorio,label,4,0,1,1)
      txtRepositorioLogs = new JTextField(Configuracao.getRepositorioLogs(),45)
      adicionarComponente(pnlRepositorio,txtRepositorioLogs,5,0,1,1)
      tbnAreaDados.add("Repositórios",pnlRepositorio)

      JPanel pnlBancoDados = new JPanel(gridbag)
      label = new JLabel("Usuário")
      adicionarComponente(pnlBancoDados,label,0,0,1,1)
      label = new JLabel("Senha")
      adicionarComponente(pnlBancoDados,label,0,1,1,1)
      txtBancoDadosUsuario = new JTextField(Configuracao.getBancoDadosUsuario(),15)
      adicionarComponente(pnlBancoDados,txtBancoDadosUsuario,1,0,1,1)
      pwdBancoDadosSenha = new JPasswordField(Configuracao.getBancoDadosSenha(),15)
      adicionarComponente(pnlBancoDados,pwdBancoDadosSenha,1,1,1,1)
      label = new JLabel("Driver")
      adicionarComponente(pnlBancoDados,label,2,0,1,1)
      txtBancoDadosDriver = new JTextField(Configuracao.getBancoDadosDriver(),45)
      adicionarComponente(pnlBancoDados,txtBancoDadosDriver,3,0,2,1)
      label = new JLabel("Fonte de Dados")
      adicionarComponente(pnlBancoDados,label,4,0,1,1)
      txtBancoDadosURL = new JTextField(Configuracao.getBancoDadosURL(),45)
      adicionarComponente(pnlBancoDados,txtBancoDadosURL,5,0,2,1)
      label = new JLabel("Nome da Base de Dados")
      adicionarComponente(pnlBancoDados,label,6,0,1,1)
      txtBancoDadosBaseDados = new JTextField(Configuracao.getBancoDadosBaseDados(),45)
      adicionarComponente(pnlBancoDados,txtBancoDadosBaseDados,7,0,2,1)
      tbnAreaDados.add("Banco de Dados",pnlBancoDados)

      this.conteudo.add(tbnAreaDados, BorderLayout.CENTER)

      JPanel pnlComandos = new JPanel(new FlowLayout(FlowLayout.RIGHT))
      btConfirmar = new JButton("Confirmar")
      btConfirmar.addActionListener(this)
      pnlComandos.add(btConfirmar)
      btCancelar = new JButton("Fechar")
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

  private void carregarPaises(String pais) {
    cbxPais.removeAllItems()
      cbxPais.addItem("Selecione...")

      for(int i = 1;i < paises.size();i++) {
        cbxPais.addItem(((Pais)paises.get(i)).getNome())
      }
    if(!"".equals(pais))
      cbxPais.setSelectedItem(pais)
  }

  private void carregarEstados(String estado) {
    cbxEstado.removeAllItems()
      cbxEstado.addItem("Selecione...")

      for(int i = 1;i < estados.size();i++) {
        cbxEstado.addItem(((Estado)estados.get(i)).getNome())
      }
    if(!"".equals(estado))
      cbxEstado.setSelectedItem(estado)
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

      if(objeto == btConfirmar) {
        configuracao.setOrganizacaoRazaoSocial(txtOrganizacaoRazaoSocial.getText())
          configuracao.setOrganizacaoNomeFantasia(txtOrganizacaoNomeFantasia.getText())
          configuracao.setOrganizacaoCnpj(txtOrganizacaoCnpj.getText())
          configuracao.setOrganizacaoLogradouro(txtOrganizacaoLogradouro.getText())
          configuracao.setOrganizacaoNumero(txtOrganizacaoNumero.getText())
          configuracao.setOrganizacaoComplemento(txtOrganizacaoComplemento.getText())
          configuracao.setOrganizacaoBairro(txtOrganizacaoBairro.getText())
          configuracao.setOrganizacaoCidade(txtOrganizacaoCidade.getText())
          if(estados != null && paises != null) {
            if(estados.size() <= 1)
              configuracao.setOrganizacaoEstado("")
            else
              configuracao.setOrganizacaoEstado((String)cbxEstado.getSelectedItem())
                if(paises.size() <= 1)
                  configuracao.setOrganizacaoPais("")
                else
                  configuracao.setOrganizacaoPais((String)cbxPais.getSelectedItem())
          }
          else
          {
            configuracao.setOrganizacaoEstado("")
              configuracao.setOrganizacaoPais("")
          }
        configuracao.setOrganizacaoCep(txtOrganizacaoCep.getText())
          configuracao.setOrganizacaoTelefone(txtOrganizacaoTelefone.getText())
          configuracao.setOrganizacaoFax(txtOrganizacaoFax.getText())
          configuracao.setRepositorioConsultas(txtRepositorioConsultas.getText())
          configuracao.setRepositorioRelatorios(txtRepositorioRelatorios.getText())
          configuracao.setRepositorioLogs(txtRepositorioLogs.getText())
          configuracao.setBancoDadosUsuario(txtBancoDadosUsuario.getText())
          configuracao.setBancoDadosSenha(String.valueOf(pwdBancoDadosSenha.getPassword()))
          configuracao.setBancoDadosDriver(txtBancoDadosDriver.getText())
          configuracao.setBancoDadosURL(txtBancoDadosURL.getText())
          configuracao.setBancoDadosBaseDados(txtBancoDadosBaseDados.getText())
          configuracao.salvarConfiguracao()
          this.setVisible(false)
      }

    if(objeto == btCancelar) {
      this.setVisible(false)
    }
  }
}
