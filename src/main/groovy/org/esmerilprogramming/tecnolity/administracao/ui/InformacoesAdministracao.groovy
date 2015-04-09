package org.esmerilprogramming.tecnolity.administracao.ui

import java.sql.*
import java.awt.*
import java.awt.event.*
import javax.swing.*
import javax.swing.border.*
import org.esmerilprogramming.tecnolity.util.*
import org.esmerilprogramming.tecnolity.administracao.*
import org.esmerilprogramming.tecnolity.aplicacao.Aplicacao
import org.esmerilprogramming.tecnolity.aplicacao.modelos.*

class InformacoesAdministracao extends JTabbedPane implements ActionListener, KeyListener
{
  private Aplicacao aplicacao
  private JPanel pnlUsuarios, pnlDepartamentos, pnlPermissoes, pnlPropriedades, pnlGeracaoInformacoes
  private GridBagLayout gridbag
  private GridBagConstraints gbc

  /* Objetos da aba Empresa */
  /* Objetos da subaba Usuários */
  private JButton btAdicionarUsuario, btAlterarUsuario, btExcluirUsuario, btAtualizarUsuario
  private JTable tblColaboradores
  private ModeloTabela modeloTabelaColaboradores

  /* Objetos da subaba Departamentos */
  private JButton btAdicionarDepartamento, btAlterarDepartamento, btExcluirDepartamento, btAtualizarDepartamento
  private JTable tblDepartamentos
  private ModeloTabela modeloTabelaDepartamentos

  /* Objetos da aba Segurança */
  private JComboBox cbxColaboradores
  private Vector colaboradores
  private Colaborador colaborador = new Colaborador()
  private JTable tblPermissoes
  private ModeloTabela modeloTabelaPermissoes
  private JButton btAtribuirPermissao, btAtualizarPermissao

  /* Objetos da aba Propriedades */
  private JButton btConfirmar, btDesfazer, btEditar
  private JTextField txtCotacaoDolar
  private Cambio cambio

  /* Objetos da aba Geração de Informações */
  private JTextField txtArquivoComando
  private JButton btAbrirComando, btExecutarComando, btLimparComando, btSalvar
  private JTextArea txaComandos
  private JLabel lblStatusResultado
  private JTable tblResultado
  private ModeloTabelaVisualizacao modeloTabelaVisualizacao
  private File arqComandos

   InformacoesAdministracao(Aplicacao aplicacao) {
    this.setBorder(new LineBorder(Color.black))
    this.aplicacao = aplicacao

    gridbag = new GridBagLayout()
    gbc = new GridBagConstraints()
    gbc.fill = GridBagConstraints.NONE
    gbc.anchor = GridBagConstraints.NORTHWEST
    gbc.insets.bottom = 2
    gbc.insets.left = 2
    gbc.insets.right = 2
    gbc.insets.top = 2

    JTabbedPane tbpEmpresa = new JTabbedPane()
    // Conteúdo da SubAba Usuários
    pnlUsuarios = new JPanel(new BorderLayout())

    JPanel pnlAreaComandos = new JPanel()
    JPanel pnlComandos = new JPanel(new GridLayout(4, 1, 5, 5))
    btAdicionarUsuario = new JButton("Adicionar Colaborador")
    btAdicionarUsuario.addActionListener(this)
    pnlComandos.add(btAdicionarUsuario)
    btAlterarUsuario = new JButton("Alterar Selecionado")
    btAlterarUsuario.addActionListener(this)
    pnlComandos.add(btAlterarUsuario)
    btExcluirUsuario = new JButton("Excluir Selecionado")
    btExcluirUsuario.addActionListener(this)
    pnlComandos.add(btExcluirUsuario)
    btAtualizarUsuario = new JButton("Atualizar Colaboradores")
    btAtualizarUsuario.addActionListener(this)
    pnlComandos.add(btAtualizarUsuario)
    pnlAreaComandos.add(pnlComandos)
    pnlUsuarios.add(pnlAreaComandos, BorderLayout.EAST)

    modeloTabelaColaboradores = new ModeloTabela()
    modeloTabelaColaboradores.definirConexao(aplicacao.obterConexao())
    modeloTabelaColaboradores.definirConsulta("select usuario as 'usuário', nome_completo as 'nome completo', telefone, ramal, email as 'e-mail' from usuario order by usuario asc")
    tblColaboradores = new JTable(modeloTabelaColaboradores)
    JScrollPane scrollColaboradores = new JScrollPane(tblColaboradores)
    pnlUsuarios.add(scrollColaboradores, BorderLayout.CENTER)
    tbpEmpresa.addTab("Colaboradores", pnlUsuarios)

    // Conteúdo da SubAba Departamentos
    pnlDepartamentos = new JPanel(new BorderLayout())

    pnlAreaComandos = new JPanel()
    pnlComandos = new JPanel(new GridLayout(4, 1, 5, 5))
    btAdicionarDepartamento = new JButton("Adicionar Departamento")
    btAdicionarDepartamento.addActionListener(this)
    pnlComandos.add(btAdicionarDepartamento)
    btAlterarDepartamento = new JButton("Alterar Selecionado")
    btAlterarDepartamento.addActionListener(this)
    pnlComandos.add(btAlterarDepartamento)
    btExcluirDepartamento = new JButton("Excluir Selecionado")
    btExcluirDepartamento.addActionListener(this)
    pnlComandos.add(btExcluirDepartamento)
    btAtualizarDepartamento = new JButton("Atualizar Departamentos")
    btAtualizarDepartamento.addActionListener(this)
    pnlComandos.add(btAtualizarDepartamento)
    pnlAreaComandos.add(pnlComandos)
    pnlDepartamentos.add(pnlAreaComandos, BorderLayout.EAST)

    modeloTabelaDepartamentos = new ModeloTabela()
    modeloTabelaDepartamentos.definirConexao(aplicacao.obterConexao())
    modeloTabelaDepartamentos.definirConsulta("select codigo, d.departamento, nome_completo as responsavel from departamento d, usuario u where responsavel *= usuario order by d.departamento asc")
    tblDepartamentos = new JTable(modeloTabelaDepartamentos)
    JScrollPane scrollDepartamentos = new JScrollPane(tblDepartamentos)
    pnlDepartamentos.add(scrollDepartamentos, BorderLayout.CENTER)
    tbpEmpresa.addTab("Departamentos", pnlDepartamentos)
    this.addTab("Empresa", tbpEmpresa)

    // Conteúdo da Aba Permissoes
    pnlPermissoes = new JPanel(new BorderLayout())

    JPanel pnlParametro = new JPanel(new FlowLayout(FlowLayout.LEFT))
    JLabel label
    try {
      label = new JLabel("Colaborador:")
      pnlParametro.add(label)
      cbxColaboradores = new JComboBox()
      cbxColaboradores.addActionListener(this)
      colaboradores = colaborador.carregarColaboradores(aplicacao.obterConexao())
      this.carregarColaboradores()
      pnlParametro.add(cbxColaboradores)
      pnlPermissoes.add(pnlParametro, BorderLayout.NORTH)
    }
    catch (Exception e) {
      JOptionPane.showMessageDialog(aplicacao, "Erro: " + e.getMessage(), "Erro", JOptionPane.WARNING_MESSAGE)
      e.printStackTrace()
    }
    pnlPermissoes.add(pnlParametro, BorderLayout.NORTH)

    pnlAreaComandos = new JPanel()
    pnlComandos = new JPanel(new GridLayout(4, 1, 5, 5))
    btAtribuirPermissao = new JButton("Atribuir Permissão")
    btAtribuirPermissao.addActionListener(this)
    pnlComandos.add(btAtribuirPermissao)
    btAtualizarPermissao = new JButton("Atualizar Permissões")
    btAtualizarPermissao.addActionListener(this)
    pnlComandos.add(btAtualizarPermissao)
    pnlAreaComandos.add(pnlComandos)
    pnlPermissoes.add(pnlAreaComandos, BorderLayout.EAST)

    modeloTabelaPermissoes = new ModeloTabela()
    modeloTabelaPermissoes.definirConexao(aplicacao.obterConexao())
    tblPermissoes = new JTable()
    atualizarTabelaPermissoes(null)

    JScrollPane scrollPermissoes = new JScrollPane(tblPermissoes)
    pnlPermissoes.add(scrollPermissoes, BorderLayout.CENTER)
    this.addTab("Segurança", pnlPermissoes)

    // Conteúdo da Aba Propriedades
    this.cambio = new Cambio()
    try {
      this.cambio.carregarCambio(aplicacao.obterConexao())
    }
    catch(Exception e) {
      JOptionPane.showMessageDialog(aplicacao, "Erro: Não foi possível carregar a cotação do dólar.", "Erro", JOptionPane.ERROR_MESSAGE)
      e.printStackTrace()
    }
    pnlPropriedades = new JPanel(new BorderLayout())
    JPanel pnlValoresPropriedades = new JPanel(gridbag)
    label = new JLabel('Cotação do Dólar (R$):')
    adicionarComponente(pnlValoresPropriedades, label, 0, 0, 1, 1)
    txtCotacaoDolar = new JTextField(Numero.inverterSeparador(""+this.cambio.obterDolar()), 5)
    txtCotacaoDolar.setEnabled(false)
    adicionarComponente(pnlValoresPropriedades, txtCotacaoDolar, 0, 1, 1, 1)
    pnlPropriedades.add(pnlValoresPropriedades, BorderLayout.CENTER)
    JPanel pnlComandosPropriedades = new JPanel(new FlowLayout(FlowLayout.RIGHT))
    btEditar = new JButton("Editar")
    btEditar.addActionListener(this)
    pnlComandosPropriedades.add(btEditar)
    btConfirmar = new JButton("Confirmar")
    btConfirmar.setEnabled(false)
    btConfirmar.addActionListener(this)
    pnlComandosPropriedades.add(btConfirmar)
    btDesfazer = new JButton("Desfazer")
    btDesfazer.setEnabled(false)
    btDesfazer.addActionListener(this)
    pnlComandosPropriedades.add(btDesfazer)
    pnlPropriedades.add(pnlComandosPropriedades, BorderLayout.SOUTH)
    this.addTab("Propriedades", pnlPropriedades)

    pnlGeracaoInformacoes = new JPanel(new BorderLayout())
    JPanel pnlAreaComando = new JPanel(new BorderLayout())
    JPanel pnlConteudoComando = new JPanel(new BorderLayout())

    pnlConteudoComando.add(new JLabel("Comando/Consulta"), BorderLayout.NORTH)
    txaComandos = new JTextArea(4, 40)
    txaComandos.setFont(new Font("Courier New", Font.PLAIN, 12))
    txaComandos.setForeground(Color.DARK_GRAY)
    txaComandos.addKeyListener(this)
    txaComandos.setLineWrap(true)
    txaComandos.setWrapStyleWord(true)
    JScrollPane scroll = new JScrollPane(txaComandos)
    pnlConteudoComando.add(scroll, BorderLayout.CENTER)

    pnlAreaComando.add(pnlConteudoComando, BorderLayout.CENTER)
    JPanel pnlAcaoComando = new JPanel(new BorderLayout())
    JPanel pnlTituloAbrirComando = new JPanel(new FlowLayout(FlowLayout.LEFT))

    pnlTituloAbrirComando.add(new JLabel("Abrir Arquivo de Comando"))

    pnlAcaoComando.add(pnlTituloAbrirComando, BorderLayout.NORTH)
    JPanel pnlAbrirComando = new JPanel(new FlowLayout(FlowLayout.LEFT))

    txtArquivoComando = new JTextField(20)
    txtArquivoComando.setEditable(false)
    pnlAbrirComando.add(txtArquivoComando)
    btAbrirComando = new JButton("Abrir...")
    btAbrirComando.addActionListener(this)
    pnlAbrirComando.add(btAbrirComando)

    pnlAcaoComando.add(pnlAbrirComando, BorderLayout.CENTER)
    JPanel pnlExecucaoComando = new JPanel(new FlowLayout(FlowLayout.LEFT))

    btExecutarComando = new JButton("Executar")
    btExecutarComando.addActionListener(this)
    pnlExecucaoComando.add(btExecutarComando)
    btLimparComando = new JButton("Limpar")
    btLimparComando.addActionListener(this)
    pnlExecucaoComando.add(btLimparComando)

    pnlAcaoComando.add(pnlExecucaoComando, BorderLayout.SOUTH)
    pnlAreaComando.add(pnlAcaoComando, BorderLayout.EAST)
    JPanel pnlStatusComando = new JPanel(new FlowLayout(FlowLayout.LEFT))
    lblStatusResultado = new JLabel("Status: ")
    pnlStatusComando.add(lblStatusResultado)
    pnlAreaComando.add(pnlStatusComando, BorderLayout.SOUTH)

    pnlGeracaoInformacoes.add(pnlAreaComando, BorderLayout.NORTH)

    modeloTabelaVisualizacao = new ModeloTabelaVisualizacao()
    modeloTabelaVisualizacao.definirConexao(aplicacao.obterConexao())
    tblResultado = new JTable(modeloTabelaVisualizacao)
    tblResultado.setPreferredScrollableViewportSize(new Dimension(700, 300))
    scroll = new JScrollPane(tblResultado)
    pnlGeracaoInformacoes.add(scroll, BorderLayout.CENTER)

    JPanel pnlComandoInfo = new JPanel(new FlowLayout(FlowLayout.RIGHT))
    btSalvar = new JButton("Salvar Consulta", new ImageIcon("org.esmerilprogramming.tecnolity/aplicacao/recursos/imagens/salvar.gif"))
    btSalvar.addActionListener(this)
    btSalvar.setEnabled(false)
    pnlComandoInfo.add(btSalvar)
    pnlGeracaoInformacoes.add(pnlComandoInfo, BorderLayout.SOUTH)

    this.addTab("Geração de Informações", pnlGeracaoInformacoes)
  }

  private void carregarColaboradores() {
    this.cbxColaboradores.removeAllItems()
    this.cbxColaboradores.addItem("Selecione...")
    for(int i = 1;i < colaboradores.size();i++) {
      this.cbxColaboradores.addItem(((Colaborador)colaboradores.get(i)).getNome())
    }
  }

  private void adicionarComponente(JPanel painel, Component c, int linha, int coluna, int largura, int altura) {
    gbc.gridx = coluna
    gbc.gridy = linha

    gbc.gridwidth = largura
    gbc.gridheight = altura

    gridbag.setConstraints(c, gbc)
    painel.add(c)
  }

  private void atualizarTabelaColaborador() {
    modeloTabelaColaboradores.definirConsulta("select usuario as 'usuário', nome_completo as 'nome completo', telefone, ramal, email as 'e-mail' from usuario order by usuario asc")
    tblColaboradores.setModel(modeloTabelaColaboradores)
    tblColaboradores.updateUI()
  }

  private void atualizarTabelaDepartamento() {
    modeloTabelaDepartamentos.definirConsulta("select codigo, d.departamento, nome_completo as responsavel from departamento d, usuario u where responsavel *= usuario order by d.departamento asc")
    tblDepartamentos.setModel(modeloTabelaDepartamentos)
    tblDepartamentos.updateUI()
  }

  private void atualizarTabelaPermissoes(Colaborador colaborador) {
    String query = "select nome_completo, i.interface, case permissao when 'E' then 'Escrita' when 'L' then 'Leitura' end as permissao from permissao p, usuario u, interface i where	p.usuario = u.usuario and "
    if(colaborador != null)
      query += "u.usuario = '"+ colaborador.obterMatricula() +"' and "
    query += "p.interface = i.identificador order by nome_completo asc"

    modeloTabelaPermissoes.definirConsulta(query)
    tblPermissoes.setModel(modeloTabelaPermissoes)
    tblPermissoes.updateUI()
  }

   void importarArquivoComandoSQL() {
    StringBuffer conteudoArquivo = new StringBuffer()
    try {
      FileReader entrada = new FileReader(arqComandos)
      int indiceRegistro = 0
      while((indiceRegistro = entrada.read()) != -1) {
        conteudoArquivo.append((char)indiceRegistro)
      }
      txaComandos.setText(conteudoArquivo.toString())
    }
    catch(FileNotFoundException e) {
      JOptionPane.showMessageDialog(aplicacao, "Erro: Arquivo informado não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE)
      e.printStackTrace()
    }
    catch(IOException ex) {
      JOptionPane.showMessageDialog(aplicacao, "Erro: Não foi possível ler o arquivo informado.", "Erro", JOptionPane.ERROR_MESSAGE)
      ex.printStackTrace()
    }
  }

  private char reconhecerTipoComando(String comando) {
    // Verifica se o comando é uma consulta. Caso contrário, retorna uma transação.
    comando = comando.toUpperCase()
    if(comando.indexOf("SELECT") >= 0 && comando.indexOf("INSERT") == -1 && comando.indexOf("UPDATE") == -1 && comando.indexOf("DELETE") == -1 && comando.indexOf("CREATE") == -1 && comando.indexOf("ALTER") == -1)
      return 'C'
    else
      return 'T'
  }

  private void executarComandoSQL() {
    try {
      if(txaComandos.getSelectedText() == null) {
        txaComandos.setSelectionStart(0)
        txaComandos.setSelectionEnd(txaComandos.getText().length())
      }
      char tipoComando = reconhecerTipoComando(txaComandos.getSelectedText())
      if(tipoComando == 'C') {
        modeloTabelaVisualizacao.definirConsulta(txaComandos.getSelectedText())
        tblResultado.setModel(modeloTabelaVisualizacao)
        tblResultado.updateUI()
        lblStatusResultado.setForeground(Color.BLUE)
        lblStatusResultado.setText("Status: Consulta Realizada com Sucesso!")
        btSalvar.setEnabled(true)
      }
      else if(tipoComando == 'T') {
        Conexao conexao = new Conexao(tipoComando)
        if(conexao.abrirConexao()) {
          conexao.executarAtualizacao(txaComandos.getSelectedText())
          lblStatusResultado.setForeground(Color.BLUE)
          lblStatusResultado.setText("Status: Alteração Realizada com Sucesso!")
        }
        conexao.fecharConexao()
        btSalvar.setEnabled(false)
      }
    }
    catch(SQLException e) {
      lblStatusResultado.setForeground(Color.RED)
      lblStatusResultado.setText("Erro Nº:" + e.getErrorCode() + " - " + e.getMessage())
      e.printStackTrace()
    }
  }

   void actionPerformed(java.awt.event.ActionEvent actionEvent) {
    Object objeto = actionEvent.getSource()

    if(objeto == btAdicionarUsuario) {
      DlgDadosColaborador dlgDadosColaborador = new DlgDadosColaborador(aplicacao, 'I')
      dlgDadosColaborador.setVisible(true)
      atualizarTabelaColaborador()
    }

    if(objeto == cbxColaboradores) {
      if(colaboradores.get(cbxColaboradores.getSelectedIndex()) != null) {
        atualizarTabelaPermissoes((Colaborador)colaboradores.get(cbxColaboradores.getSelectedIndex()))
      }
    }

    if(objeto == btAtribuirPermissao) {
      DlgDadosPermissao dlgDadosPermissao = new DlgDadosPermissao(aplicacao, 'I')
      dlgDadosPermissao.setVisible(true)
      if(colaboradores.get(cbxColaboradores.getSelectedIndex()) != null) {
        atualizarTabelaPermissoes((Colaborador)colaboradores.get(cbxColaboradores.getSelectedIndex()))
      }
    }

    if(objeto == btAtualizarPermissao) {
      if(colaboradores.get(cbxColaboradores.getSelectedIndex()) != null) {
        atualizarTabelaPermissoes((Colaborador)colaboradores.get(cbxColaboradores.getSelectedIndex()))
      }
    }

    if(objeto == btEditar) {
      txtCotacaoDolar.setEnabled(true)
      btConfirmar.setEnabled(true)
      btDesfazer.setEnabled(true)
      btEditar.setEnabled(false)
    }

    if(objeto == btConfirmar) {
      try {
        this.cambio.definirDolar(Float.parseFloat(Numero.inverterSeparador(txtCotacaoDolar.getText())))
      }
      catch(Exception e) {
        JOptionPane.showMessageDialog(aplicacao, "Erro: "+e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE)
        e.printStackTrace()
      }
      try {
        this.cambio.cadastrarCambio()
      }
      catch(Exception e) {
        JOptionPane.showMessageDialog(aplicacao, "Erro: Não foi possível registrar o câmbio atual.", "Erro", JOptionPane.ERROR_MESSAGE)
        e.printStackTrace()
      }
      txtCotacaoDolar.setEnabled(false)
      btConfirmar.setEnabled(false)
      btDesfazer.setEnabled(false)
      btEditar.setEnabled(true)
    }

    if(objeto == btDesfazer) {
      txtCotacaoDolar.setText(Numero.inverterSeparador(""+this.cambio.obterDolar()))
    }

    if(objeto == btAlterarUsuario) {
      if(this.tblColaboradores.getSelectedRow() >= 0) {
        int linha = this.tblColaboradores.getSelectedRow()
        String usuarioColaborador = (String)this.tblColaboradores.getValueAt(linha, 0)
        DlgDadosColaborador dlgDadosColaborador = new DlgDadosColaborador(aplicacao, 'A', usuarioColaborador)
        dlgDadosColaborador.setVisible(true)
        atualizarTabelaColaborador()
      }
      else
      {
        JOptionPane.showMessageDialog(aplicacao, "Atenção: Selecione uma linha da visão para alterar os dados do item.", "Atenção", JOptionPane.WARNING_MESSAGE)
      }
    }

    if(objeto == btExcluirUsuario) {
      if(tblColaboradores.getSelectedRow() >=0) {
        if(JOptionPane.showConfirmDialog(aplicacao, "Atenção: Tem certeza que deseja excluir o colaborador selecionado?", "Atenção", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
          int linha = tblColaboradores.getSelectedRow()
          String usuarioColaborador = (String)this.tblColaboradores.getValueAt(linha, 0)
          try {
            colaborador = new Colaborador(usuarioColaborador)
          }
          catch(Exception e) {
            JOptionPane.showMessageDialog(aplicacao, "Erro: "+e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE)
            e.printStackTrace()
          }
          colaborador.excluirColaborador()
          atualizarTabelaColaborador()
        }
      }
      else
      {
        JOptionPane.showMessageDialog(aplicacao, "Atenção: Selecione uma linha da visão para excluir um colaborador.", "Atenção", JOptionPane.WARNING_MESSAGE)
      }
    }

    if(objeto == btAtualizarUsuario) {
      atualizarTabelaColaborador()
    }

    if(objeto == btAdicionarDepartamento) {
      DlgDadosDepartamento dlgDadosDepartamento = new DlgDadosDepartamento(aplicacao)
      dlgDadosDepartamento.setVisible(true)
      dlgDadosDepartamento = null
      atualizarTabelaDepartamento()
    }

    if(objeto == btAlterarDepartamento) {
      int linhaSelecionada = tblDepartamentos.getSelectedRow()
      Departamento departamento = new Departamento(Integer.parseInt((String)tblDepartamentos.getValueAt(linhaSelecionada, 0)))
      try {
        departamento.carregarDepartamento(aplicacao.obterConexao())
      }
      catch(Exception e) {
        JOptionPane.showMessageDialog(aplicacao, "Erro: não foi possível carregar o Departamento.", "Erro", JOptionPane.ERROR_MESSAGE)
        e.printStackTrace()
      }
      DlgDadosDepartamento dlgDadosDepartamento = new DlgDadosDepartamento(aplicacao, departamento)
      dlgDadosDepartamento.setVisible(true)
      dlgDadosDepartamento = null
      atualizarTabelaDepartamento()
    }

    if(objeto == btExcluirDepartamento) {
      int linhaSelecionada = tblDepartamentos.getSelectedRow()
      Departamento departamento = new Departamento(Integer.parseInt((String)tblDepartamentos.getValueAt(linhaSelecionada, 0)))
      try {
        departamento.excluirDepartamento()
        atualizarTabelaDepartamento()
      }
      catch(Exception e) {
        JOptionPane.showMessageDialog(aplicacao, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE)
        e.printStackTrace()
      }
    }

    if(objeto == btAtualizarDepartamento) {
      atualizarTabelaDepartamento()
    }

    if(objeto == btAbrirComando) {
      JFileChooser fchComando = new JFileChooser(Configuracao.getRepositorioConsultas())
      int status = fchComando.showOpenDialog(this)

      if(status == JFileChooser.APPROVE_OPTION) {
        arqComandos = fchComando.getSelectedFile()
        txtArquivoComando.setText(arqComandos.getAbsolutePath())
      }

      importarArquivoComandoSQL()
    }

    if(objeto == btExecutarComando) {
      executarComandoSQL()
    }

    if(objeto == btLimparComando) {
      this.txaComandos.setText("")
      this.txtArquivoComando.setText("")
    }

    if(objeto == btSalvar) {
      JFileChooser dlgSalvamento = new JFileChooser(Configuracao.getRepositorioRelatorios())
      int status = dlgSalvamento.showSaveDialog(aplicacao)
      if(status == JFileChooser.APPROVE_OPTION) {
        try {
          File arquivo = dlgSalvamento.getSelectedFile()
          FileWriter saida = new FileWriter(arquivo)
          saida.write("---- CONSULTA -----\n\n" + txaComandos.getText())
          saida.write("\n\n---- RESULTADO ----")
          int[] tabs = new int[tblResultado.getColumnCount()]
          for(int i = 0;i < tblResultado.getColumnCount();i++) {
            tabs[i] = 1
            for(int j = 0;j < tblResultado.getRowCount();j++) {
              if(tabs[i] <= (((String)(tblResultado.getValueAt(j, i) != null?tblResultado.getValueAt(j, i):"")).length()/6.0f))
                tabs[i]++
            }
          }
          String linha = ""
          for(int i = 0;i < tblResultado.getRowCount();i++) {
            for(int j = 0;j < tblResultado.getColumnCount();j++) {
              linha += (tblResultado.getValueAt(i, j) != null?tblResultado.getValueAt(i, j):"")
              for(int l = 0;l < (tabs[j] - ((String)(tblResultado.getValueAt(i, j) != null?tblResultado.getValueAt(i, j):"")).length()/6);l++) {
                linha += "\t"
              }
            }
            saida.write("\n" + linha)
            linha = ""
          }
          saida.close()
        }
        catch(IOException e) {
          JOptionPane.showMessageDialog(aplicacao, "Erro: Não foi possível salvar o arquivo.\n" + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
        }
      }
    }
  }

   void keyTyped(KeyEvent e) {

  }

   void keyPressed(KeyEvent e) {
    if(e.getKeyCode() == KeyEvent.VK_F5) {
      executarComandoSQL()
    }
  }

   void keyReleased(KeyEvent e) {

  }
}
