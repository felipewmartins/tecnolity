package org.esmerilprogramming.tecnolity.pedidos.ui

import java.awt.*
import java.awt.event.*

import javax.swing.*
import javax.swing.border.*

import org.esmerilprogramming.tecnolity.ui.Dialogo
import org.esmerilprogramming.tecnolity.ui.modelo.ModeloTabelaDB
import org.esmerilprogramming.tecnolity.administracao.*
import org.esmerilprogramming.tecnolity.administracao.ui.*
import org.esmerilprogramming.tecnolity.aplicacao.Aplicacao
import org.esmerilprogramming.tecnolity.pedidos.*

class DlgDadosCliente extends Dialogo implements ActionListener
{
  final int IDENTIFICADOR = 9

    private Aplicacao aplicacao
    private CardLayout card
    private Container conteudo
    private JPanel pnlAreaDados
    private JButton btNovoPais, btNovoEstado, btAnterior, btProximo, btConfirmar, btCancelar
    private JTextField txtRazaoSocial, txtNomeFantasia, txtCnpj, txtInscricaoEstadual, 
            txtLogradouro, txtBairro, txtComplemento, txtCidade, txtCep, 
            txtTelefone, txtFax, txtContatoComercial, txtContatoTecnico, txtEmail
              private JComboBox cbxEstado, cbxPais
              private Vector estados, paises

              /* Dados de Locais de entrega*/
              private JTextField txtDescricaoLocal, txtLogradouroEntrega, txtComplementoEntrega, 
            txtBairroEntrega, txtCidadeEntrega, txtCepEntrega, txtTelefoneEntrega, txtResponsavelRecebimento
              private JComboBox cbxCliente, cbxEstadoEntrega

              private JTable tblLocaisEntrega
              private ModeloTabelaDB modeloTblLocaisEntrega

              private JButton btNovoEstadoEntrega, btAdicionarLocal, btExcluirLocal, btLimparLocal, btPreencherLocalEntrega
              private Vector clientes, estadosEntrega

              private Cliente cliente

              DlgDadosCliente(Aplicacao aplicacao) {
                super(aplicacao, "Cliente")
                  this.aplicacao = aplicacao
                  montarInterface()
              }

  DlgDadosCliente(Aplicacao aplicacao, Cliente cliente) {
    super(aplicacao, "Cliente")
      this.aplicacao = aplicacao
      try
      {
        this.cliente = cliente
          cliente.carregarCliente(aplicacao.obterConexao())
          montarInterface()
          this.atualizarTabelaLocaisEntrega()
          dimencionar()
      }
    catch(Exception e) {
      JOptionPane.showMessageDialog(aplicacao, "Erro: "  +  e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE)
        e.printStackTrace()
    }
  }

  private void montarInterface() {
    conteudo = this.getContentPane()

      card = new CardLayout()
      pnlAreaDados = new JPanel(card)
      JPanel pnlDadosCliente = new JPanel(this.getGridbag())
      JLabel label = new JLabel("Razão Social")
      adicionarComponente(pnlDadosCliente, label, 0, 0, 2, 1)
      label = new JLabel("Nome Fantasia")
      adicionarComponente(pnlDadosCliente, label, 0, 2, 2, 1)
      txtRazaoSocial = new JTextField((cliente != null?cliente.obterRazaoSocial():""), 20)
      adicionarComponente(pnlDadosCliente, txtRazaoSocial, 1, 0, 2, 1)
      txtNomeFantasia = new JTextField((cliente != null?cliente.obterNomeFantasia():""), 20)
      adicionarComponente(pnlDadosCliente, txtNomeFantasia, 1, 2, 2, 1)
      label = new JLabel("CNPJ (Somente números)")
      adicionarComponente(pnlDadosCliente, label, 2, 0, 2, 1)
      label = new JLabel("Inscrição Estadual")
      adicionarComponente(pnlDadosCliente, label, 2, 2, 2, 1)
      txtCnpj = new JTextField((cliente != null?cliente.obterCnpj():""), 20)
      adicionarComponente(pnlDadosCliente, txtCnpj, 3, 0, 2, 1)
      txtInscricaoEstadual = new JTextField((cliente != null?cliente.obterInscricaoEstadual():""), 20)
      adicionarComponente(pnlDadosCliente, txtInscricaoEstadual, 3, 2, 2, 1)
      JPanel pnlEndereco = new JPanel(this.getGridbag())
      pnlEndereco.setBorder(new TitledBorder("Endereço"))
      label = new JLabel("Logradouro")
      adicionarComponente(pnlEndereco, label, 0, 0, 2, 1)
      label = new JLabel("Complemento")
      adicionarComponente(pnlEndereco, label, 0, 2, 1, 1)
      label = new JLabel("Bairro")
      adicionarComponente(pnlEndereco, label, 0, 3, 1, 1)
      txtLogradouro = new JTextField((cliente != null?cliente.obterLogradouro():""), 15)
      adicionarComponente(pnlEndereco, txtLogradouro, 1, 0, 2, 1)
      txtComplemento = new JTextField((cliente != null?cliente.obterComplemento():""), 10)
      adicionarComponente(pnlEndereco, txtComplemento, 1, 2, 1, 1)
      txtBairro = new JTextField((cliente != null?cliente.obterBairro():""), 10)
      adicionarComponente(pnlEndereco, txtBairro, 1, 3, 1, 1)
      label = new JLabel("Cidade")
      adicionarComponente(pnlEndereco, label, 2, 0, 2, 1)
      label = new JLabel("Pais")
      adicionarComponente(pnlEndereco, label, 2, 2, 1, 1)
      label = new JLabel("Estado")
      adicionarComponente(pnlEndereco, label, 2, 3, 1, 1)
      txtCidade = new JTextField((cliente != null?cliente.obterCidade():""), 15)
      adicionarComponente(pnlEndereco, txtCidade, 3, 0, 2, 1)
      cbxPais = new JComboBox()
      try
      {
        paises = Pais.carregarPaises(aplicacao.obterConexao())
          carregarPaises()
      }
    catch(Exception e) {
      JOptionPane.showMessageDialog(aplicacao, "Erro: Não foi possível carregar os Países.", "Erro", JOptionPane.ERROR_MESSAGE)
        e.printStackTrace()
    }
    cbxPais.addActionListener(this)
      JPanel pnlSuporteCombo = new JPanel(new BorderLayout())
      pnlSuporteCombo.add(cbxPais, BorderLayout.CENTER)
      btNovoPais = new JButton(new ImageIcon("imagens/novo.jpg"))
      btNovoPais.addActionListener(this)
      btNovoPais.setToolTipText("Novo Pais")
      btNovoPais.setPreferredSize(new Dimension(22, 20))
      pnlSuporteCombo.add(btNovoPais, BorderLayout.EAST)
      adicionarComponente(pnlEndereco, pnlSuporteCombo, 3, 2, 1, 1)
      cbxEstado = new JComboBox()
      try
      {
        estados = Estado.carregarEstados("BRA", aplicacao.obterConexao())
          carregarEstados()
      }
    catch(Exception e) {
      JOptionPane.showMessageDialog(aplicacao, "Erro: Não foi possível carregar os Estados", "Erro", JOptionPane.ERROR_MESSAGE)
        e.printStackTrace()
    }
    pnlSuporteCombo = new JPanel(new BorderLayout())
      pnlSuporteCombo.add(cbxEstado, BorderLayout.CENTER)
      btNovoEstado = new JButton(new ImageIcon("imagens/novo.jpg"))
      btNovoEstado.addActionListener(this)
      btNovoEstado.setToolTipText("Novo Estado")
      btNovoEstado.setPreferredSize(new Dimension(22, 20))
      pnlSuporteCombo.add(btNovoEstado, BorderLayout.EAST)
      adicionarComponente(pnlEndereco, pnlSuporteCombo, 3, 3, 1, 1)
      label = new JLabel("CEP (Somente números)")
      adicionarComponente(pnlEndereco, label, 4, 0, 1, 1)
      txtCep = new JTextField((cliente != null?cliente.obterCep():""), 8)
      adicionarComponente(pnlEndereco, txtCep, 5, 0, 1, 1)
      adicionarComponente(pnlDadosCliente, pnlEndereco, 4, 0, 4, 1)

      JPanel pnlContato = new JPanel(this.getGridbag())
      pnlContato.setBorder(new TitledBorder("Contato"))
      label = new JLabel("Contato Comercial")
      adicionarComponente(pnlContato, label, 0, 0, 2, 1)
      label = new JLabel("Contato Técnico")
      adicionarComponente(pnlContato, label, 0, 2, 2, 1)
      txtContatoComercial = new JTextField((cliente != null?cliente.obterContatoComercial():""), 19)
      adicionarComponente(pnlContato, txtContatoComercial, 1, 0, 2, 1)
      txtContatoTecnico = new JTextField((cliente != null?cliente.obterContatoTecnico():""), 19)
      adicionarComponente(pnlContato, txtContatoTecnico, 1, 2, 2, 1)
      label = new JLabel("Telefone")
      adicionarComponente(pnlContato, label, 2, 0, 1, 1)
      label = new JLabel("Fax")
      adicionarComponente(pnlContato, label, 2, 1, 1, 1)
      label = new JLabel("E-mail")
      adicionarComponente(pnlContato, label, 2, 2, 2, 1)
      txtTelefone = new JTextField((cliente != null?cliente.obterTelefone():""), 10)
      adicionarComponente(pnlContato, txtTelefone, 3, 0, 1, 1)
      txtFax = new JTextField((cliente != null?cliente.obterFax():""), 10)
      adicionarComponente(pnlContato, txtFax, 3, 1, 1, 1)
      txtEmail = new JTextField((cliente != null?cliente.obterEmail():""), 19)
      adicionarComponente(pnlContato, txtEmail, 3, 2, 2, 1)
      adicionarComponente(pnlDadosCliente, pnlContato, 5, 0, 4, 1)

      pnlAreaDados.add(pnlDadosCliente, "cliente")
      JPanel pnlDadosLocalEntrega = new JPanel(this.getGridbag())
      pnlDadosLocalEntrega.setBorder(new TitledBorder("Local de Entrega"))

      boolean localEntregaClienteExistente = false
      if(cliente != null) {
        for(int i = 0;i < cliente.getLocaisEntrega().size(); i++) {
          if(cliente.obterRazaoSocial().equals((LocalEntrega)cliente.getLocaisEntrega().get(i))) {
            localEntregaClienteExistente = true
              break
          }
        }
      }
    if(localEntregaClienteExistente) {
      label = new JLabel("Descrição")
        adicionarComponente(pnlDadosLocalEntrega, label, 0, 0, 3, 1)
        txtDescricaoLocal = new JTextField(20)
        adicionarComponente(pnlDadosLocalEntrega, txtDescricaoLocal, 1, 0, 3, 1)
    }
    else
    {
      btPreencherLocalEntrega = new JButton("Preencher mesmo endereço")
        btPreencherLocalEntrega.addActionListener(this)
        adicionarComponente(pnlDadosLocalEntrega, btPreencherLocalEntrega, 0, 0, 1, 2)
        label = new JLabel("Descrição")
        adicionarComponente(pnlDadosLocalEntrega, label, 0, 1, 2, 1)
        txtDescricaoLocal = new JTextField(20)
        adicionarComponente(pnlDadosLocalEntrega, txtDescricaoLocal, 1, 1, 2, 1)
    }

    label = new JLabel("Logradouro")
      adicionarComponente(pnlDadosLocalEntrega, label, 2, 0, 1, 1)
      label = new JLabel("Complemento")
      adicionarComponente(pnlDadosLocalEntrega, label, 2, 1, 1, 1)
      label = new JLabel("Bairro")
      adicionarComponente(pnlDadosLocalEntrega, label, 2, 2, 1, 1)
      txtLogradouroEntrega = new JTextField(20)
      adicionarComponente(pnlDadosLocalEntrega, txtLogradouroEntrega, 3, 0, 1, 1)
      txtComplementoEntrega = new JTextField(10)
      adicionarComponente(pnlDadosLocalEntrega, txtComplementoEntrega, 3, 1, 1, 1)
      txtBairroEntrega = new JTextField(10)
      adicionarComponente(pnlDadosLocalEntrega, txtBairroEntrega, 3, 2, 1, 1)
      label = new JLabel("Cidade")
      adicionarComponente(pnlDadosLocalEntrega, label, 4, 0, 1, 1)
      label = new JLabel("Estado")
      adicionarComponente(pnlDadosLocalEntrega, label, 4, 1, 1, 1)
      label = new JLabel("CEP")
      adicionarComponente(pnlDadosLocalEntrega, label, 4, 2, 1, 1)
      txtCidadeEntrega = new JTextField(20)
      adicionarComponente(pnlDadosLocalEntrega, txtCidadeEntrega, 5, 0, 1, 1)
      cbxEstadoEntrega = new JComboBox()
      try
      {
        estadosEntrega = Estado.carregarEstados("BRA", aplicacao.obterConexao())
          carregarEstadosEntrega()
      }
    catch(Exception e) {
      JOptionPane.showMessageDialog(aplicacao, "Erro: Não foi possível carregar os Estados do Local de Entrega", "Erro", JOptionPane.ERROR_MESSAGE)
        e.printStackTrace()
    }
    pnlSuporteCombo = new JPanel(new BorderLayout())
      pnlSuporteCombo.add(cbxEstadoEntrega, BorderLayout.CENTER)
      btNovoEstadoEntrega = new JButton(new ImageIcon("imagens/novo.jpg"))
      btNovoEstadoEntrega.addActionListener(this)
      btNovoEstadoEntrega.setToolTipText("Novo Estado")
      btNovoEstadoEntrega.setPreferredSize(new Dimension(22, 20))
      pnlSuporteCombo.add(btNovoEstadoEntrega, BorderLayout.EAST)
      adicionarComponente(pnlDadosLocalEntrega, pnlSuporteCombo, 5, 1, 1, 1)
      txtCepEntrega = new JTextField(10)
      adicionarComponente(pnlDadosLocalEntrega, txtCepEntrega, 5, 2, 1, 1)
      label = new JLabel("Responsável Recebimento")
      adicionarComponente(pnlDadosLocalEntrega, label, 6, 0, 1, 1)
      label = new JLabel("Telefone")
      adicionarComponente(pnlDadosLocalEntrega, label, 6, 1, 1, 1)
      txtResponsavelRecebimento = new JTextField(20)
      adicionarComponente(pnlDadosLocalEntrega, txtResponsavelRecebimento, 7, 0, 1, 1)
      txtTelefoneEntrega = new JTextField(10)
      adicionarComponente(pnlDadosLocalEntrega, txtTelefoneEntrega, 7, 1, 1, 1)
      btAdicionarLocal = new JButton("Adicionar")
      btAdicionarLocal.addActionListener(this)
      adicionarComponente(pnlDadosLocalEntrega, btAdicionarLocal, 8, 1, 1, 1)
      btLimparLocal = new JButton("Limpar")
      btLimparLocal.addActionListener(this)
      adicionarComponente(pnlDadosLocalEntrega, btLimparLocal, 8, 2, 1, 1)

      modeloTblLocaisEntrega = new ModeloTabelaDB()
      tblLocaisEntrega = new JTable(modeloTblLocaisEntrega)
      tblLocaisEntrega.setPreferredScrollableViewportSize(new Dimension(460, 100))
      JScrollPane scroll = new JScrollPane(tblLocaisEntrega)
      adicionarComponente(pnlDadosLocalEntrega, scroll, 9, 0, 3, 1)

      btExcluirLocal = new JButton("Excluir Selecionado")
      btExcluirLocal.addActionListener(this)
      if(cliente == null)
        btExcluirLocal.setEnabled(false)
          adicionarComponente(pnlDadosLocalEntrega, btExcluirLocal, 10, 0, 1, 1)
          pnlAreaDados.add(pnlDadosLocalEntrega, "localentrega")
          this.setPnlAreaDados(pnlAreaDados)

          JPanel pnlComandos = new JPanel(new FlowLayout(FlowLayout.RIGHT))
          btAnterior = new JButton("<< Anterior")
          btAnterior.setEnabled(false)
          btAnterior.addActionListener(this)
          pnlComandos.add(btAnterior)
          btProximo = new JButton("Próximo >>")
          btProximo.addActionListener(this)
          pnlComandos.add(btProximo)
          btConfirmar = new JButton("Confirmar")
          btConfirmar.addActionListener(this)
          pnlComandos.add(btConfirmar)
          btCancelar = new JButton("Cancelar")
          btCancelar.addActionListener(this)
          pnlComandos.add(btCancelar)
          this.setPnlAreaComandos(pnlComandos)
          dimencionar()
  }

  private void carregarPaises() {
    cbxPais.removeAllItems()
      cbxPais.addItem("Selecione...")
      String siglaPais
      int indicePais = 0
      for(int i = 1;i < paises.size(); i++) {
        if(cliente != null) {
          siglaPais = ((Pais)paises.get(i)).getSigla()
            if(cliente.obterPais().getSigla().equals(siglaPais)) {
              indicePais = i
            }
        }
        cbxPais.addItem(((Pais)paises.get(i)).getNome())
      }
    cbxPais.setSelectedIndex(indicePais)
      dimencionar()
  }

  private void carregarEstados() {
    cbxEstado.removeAllItems()
      cbxEstado.addItem("Selecione...")
      String siglaEstado
      int indiceEstado = 0
      for(int i = 1;i < estados.size(); i++) {
        if(cliente != null) {
          siglaEstado = ((Estado)estados.get(i)).getSigla()
            if(cliente.obterEstado().getSigla().equals(siglaEstado)) {
              indiceEstado = i
            }
        }
        cbxEstado.addItem(((Estado)estados.get(i)).getNome())
      }
    cbxEstado.setSelectedIndex(indiceEstado)
      dimencionar()
  }

  private void carregarEstadosEntrega() {
    cbxEstadoEntrega.removeAllItems()
      cbxEstadoEntrega.addItem("Selecione...")

      for(int i = 1;i < estadosEntrega.size();i++) {
        cbxEstadoEntrega.addItem(((Estado)estadosEntrega.get(i)).getNome())
      }
    dimencionar()
  }

  private void atualizarTabelaLocaisEntrega() {
    try
    {
      Vector locaisEntrega = cliente.getLocaisEntrega()
        String[][] dados = new String[locaisEntrega.size()][2]
        String[] cabecalhos = ['Local', 'Endereço']
        LocalEntrega localEntrega
        int linha = 0
        for(int i = 0;i < locaisEntrega.size();i++) {
          localEntrega = (LocalEntrega)locaisEntrega.get(i)
            if(!localEntrega.isInvalido()) {
              dados[linha][0] = localEntrega.obterDescricaoLocal()
                dados[linha++][1] = Endereco.formatarEndereco(localEntrega.obterLogradouro(), localEntrega.obterComplemento(), localEntrega.obterBairro(), localEntrega.obterCidade(), localEntrega.obterEstado().getNome(), "", localEntrega.obterCep())
            }
        }
      modeloTblLocaisEntrega.carregar(cabecalhos, dados)
        int[] tamanhoColunas = [25, 75]
        modeloTblLocaisEntrega.setTamanhoColunas(tblLocaisEntrega, tamanhoColunas)
    }
    catch(Exception ex) {
      JOptionPane.showMessageDialog(this.aplicacao, "Não foi possível carregar os locais de entrega. "  +  ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE)
        ex.printStackTrace()
    }
  }

  private void limparDadosLocalEntrega() {
    txtDescricaoLocal.setText("")
      txtLogradouroEntrega.setText("")
      txtComplementoEntrega.setText("")
      txtBairroEntrega.setText("")
      txtCidadeEntrega.setText("")
      cbxEstadoEntrega.setSelectedIndex(0)
      txtCepEntrega.setText("")
      txtResponsavelRecebimento.setText("")
      txtTelefoneEntrega.setText("")
  }

  void actionPerformed(java.awt.event.ActionEvent actionEvent) {
    Object objeto = actionEvent.getSource()

      if(objeto == btNovoPais) {
        DlgDadosPais dlgDadosPais = new DlgDadosPais(aplicacao, 'I')
          dlgDadosPais.setVisible(true)
          try
          {
            paises = Pais.carregarPaises(aplicacao.obterConexao())
              carregarPaises()
          }
        catch(Exception e) {
          JOptionPane.showMessageDialog(aplicacao, "Erro: Não foi possível carregar os Países.", "Erro", JOptionPane.ERROR_MESSAGE)
            e.printStackTrace()
        }
      }

    if(objeto == btNovoEstado) {
      DlgDadosEstado dlgDadosEstado = new DlgDadosEstado(aplicacao, 'I')
        dlgDadosEstado.setVisible(true)
        try
        {
          estados = Estado.carregarEstados("BRA", aplicacao.obterConexao())
            carregarEstados()
        }
      catch(Exception e) {
        JOptionPane.showMessageDialog(aplicacao, "Erro: Não foi possível carregar os Estados", "Erro", JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
    }

    if(objeto == btNovoEstadoEntrega) {
      DlgDadosEstado dlgDadosEstado = new DlgDadosEstado(aplicacao, 'I')
        dlgDadosEstado.setVisible(true)
        try
        {
          estadosEntrega = Estado.carregarEstados("BRA", aplicacao.obterConexao())
            carregarEstadosEntrega()
        }
      catch(Exception e) {
        JOptionPane.showMessageDialog(aplicacao, "Erro: Não foi possível carregar os Estados do Local de Entrega.", "Erro", JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
    }

    if(objeto == btPreencherLocalEntrega) {
      txtDescricaoLocal.setText(txtRazaoSocial.getText())
        txtLogradouroEntrega.setText(txtLogradouro.getText())
        txtComplementoEntrega.setText(txtComplemento.getText())
        txtBairroEntrega.setText(txtBairro.getText())
        txtCidadeEntrega.setText(txtCidade.getText())
        cbxEstadoEntrega.setSelectedItem(cbxEstado.getSelectedItem())
        txtCepEntrega.setText(txtCep.getText())
        txtResponsavelRecebimento.setText(txtContatoTecnico.getText())
        txtTelefoneEntrega.setText(txtTelefone.getText())
        btPreencherLocalEntrega.setEnabled(false)
    }

    if(objeto == btAdicionarLocal) {
      try
      {
        if(cliente == null) {
          cliente = new Cliente()
        }
        cliente.getLocaisEntrega().addElement(new LocalEntrega(cliente, 
              txtDescricaoLocal.getText(), 
              txtLogradouroEntrega.getText(), 
              txtComplementoEntrega.getText(), 
              txtBairro.getText(), 
              txtCidadeEntrega.getText(), 
              ((Estado)estados.get(cbxEstadoEntrega.getSelectedIndex())), 
              txtCepEntrega.getText(), 
              txtTelefoneEntrega.getText(), 
              txtResponsavelRecebimento.getText()))
          atualizarTabelaLocaisEntrega()
          btExcluirLocal.setEnabled(true)
      }
      catch(Exception e) {
        JOptionPane.showMessageDialog(aplicacao, "Erro: "  +  e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
    }

    if(objeto == btLimparLocal) {
      limparDadosLocalEntrega()
    }

    if(objeto == btExcluirLocal) {
      if(tblLocaisEntrega.getSelectedRow() >= 0) {
        LocalEntrega locEntrega = (LocalEntrega)cliente.getLocaisEntrega().get(tblLocaisEntrega.getSelectedRow())
          locEntrega.setInvalido(true)
          atualizarTabelaLocaisEntrega()
      }
    }

    if(objeto == btAnterior) {
      card.previous(pnlAreaDados)
        btAnterior.setEnabled(false)
        btProximo.setEnabled(true)
    }

    if(objeto == btProximo) {
      try
      {
        if(cliente == null)
          cliente = new Cliente()
            cliente.definirRazaoSocial(txtRazaoSocial.getText())
            cliente.definirNomeFantasia(txtNomeFantasia.getText())
            cliente.definirCnpj(txtCnpj.getText())
            cliente.definirInscricaoEstadual(txtInscricaoEstadual.getText())
            cliente.definirLogradouro(txtLogradouro.getText())
            cliente.definirComplemento(txtComplemento.getText())
            cliente.definirBairro(txtBairro.getText())
            cliente.definirCidade(txtCidade.getText())
            cliente.definirPais((Pais)paises.get(cbxPais.getSelectedIndex()))
            cliente.definirEstado((Estado)estados.get(cbxEstado.getSelectedIndex()))
            cliente.definirCep(txtCep.getText())
            cliente.definirContatoComercial(txtContatoComercial.getText())
            cliente.definirContatoTecnico(txtContatoTecnico.getText())
            cliente.definirTelefone(txtTelefone.getText())
            cliente.definirFax(txtFax.getText())
            cliente.definirEmail(txtEmail.getText())
            card.next(pnlAreaDados)
            btAnterior.setEnabled(true)
            btProximo.setEnabled(false)
      }
      catch(Exception e) {
        JOptionPane.showMessageDialog(aplicacao, "Erro: " +  e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
    }

    if(objeto == btConfirmar) {
      try
      {
        cliente.definirRazaoSocial(txtRazaoSocial.getText())
          cliente.definirNomeFantasia(txtNomeFantasia.getText())
          cliente.definirCnpj(txtCnpj.getText())
          cliente.definirInscricaoEstadual(txtInscricaoEstadual.getText())
          cliente.definirLogradouro(txtLogradouro.getText())
          cliente.definirComplemento(txtComplemento.getText())
          cliente.definirBairro(txtBairro.getText())
          cliente.definirCidade(txtCidade.getText())
          cliente.definirPais((Pais)paises.get(cbxPais.getSelectedIndex()))
          cliente.definirEstado((Estado)estados.get(cbxEstado.getSelectedIndex()))
          cliente.definirCep(txtCep.getText())
          cliente.definirContatoComercial(txtContatoComercial.getText())
          cliente.definirContatoTecnico(txtContatoTecnico.getText())
          cliente.definirTelefone(txtTelefone.getText())
          cliente.definirFax(txtFax.getText())
          cliente.definirEmail(txtEmail.getText())
          if (cliente.obterCodigo() > 0)
            cliente.alterarCliente(aplicacao.obterConexao())
          else
            cliente.cadastrarCliente(aplicacao.obterConexao())
              this.setVisible(false)
      }
      catch(Exception e) {
        JOptionPane.showMessageDialog(aplicacao, "Erro: "  +  e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE)
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

  }
}
