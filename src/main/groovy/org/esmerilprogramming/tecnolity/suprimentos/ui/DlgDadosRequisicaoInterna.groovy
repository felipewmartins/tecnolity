package org.esmerilprogramming.tecnolity.suprimentos.ui

import java.sql.*
import java.awt.*

import java.awt.event.*
import javax.swing.*
import javax.swing.border.*
import org.esmerilprogramming.tecnolity.administracao.*
import org.esmerilprogramming.tecnolity.administracao.ui.DlgDadosDepartamento
import org.esmerilprogramming.tecnolity.aplicacao.Aplicacao
import org.esmerilprogramming.tecnolity.pedidos.*
import org.esmerilprogramming.tecnolity.suprimentos.*
import org.esmerilprogramming.tecnolity.util.*

class DlgDadosRequisicaoInterna extends JDialog implements ActionListener, FocusListener
{
  final int IDENTIFICADOR = 28

    private Aplicacao aplicacao
    private RequisicaoInterna requisicaoInterna
    private Departamento departamento = new Departamento()
    private Pedido pedidoCliente = new Pedido()
    private Item item = new Item()
    private Vector tiposSolicitacoes, departamentos,pedidosClientes,
            itensRequisicaoInterna,itens
              private GridBagLayout gridbag
              private GridBagConstraints gbc
              private Colaborador solicitante

              // Objetos do painel de dados da requisicao interna
              private JPanel pnlDadosRequisicaoInterna
              private JTextField txtDataLimiteEntrega
              private JComboBox cbxTipoSolicitacao,cbxDepartamento
              private JTextArea txaJustificativa
              private JButton btNovoDepartamento

              // Objetos do painel pedidos do cliente
              private JPanel pnlPedidosClientes
              private JList lstPedidos, lstPedidosSelecionados
              private Vector pedidos, pedidosSelecionados
              private DefaultListModel modeloListaPedidos, modeloListaPedidosSelecionados
              private JButton btAdicionar, btRemover

              //Objetos do painel de itens da requisição interna
              private JPanel pnlDadosItensRequisicaoInterna
              private JComboBox cbxItem
              private JTextField txtQuantidade
              private JButton btNovoItem,btAdicionarItem,btCancelarItem,btExcluirItem
              private JTable tblItens

              private Container conteudo
              private JPanel pnlAreaDados
              private CardLayout card
              private int indiceCard
              private JButton btAnterior, btProximo, btProximoLeitura, btConfirmar, btCancelar

              DlgDadosRequisicaoInterna(Aplicacao aplicacao) {
                super(aplicacao,true)

                  this.setTitle("Requisição Interna")
                  this.aplicacao = aplicacao
                  this.solicitante = aplicacao.obterColaborador()
                  this.pedidosSelecionados = new Vector()
                  montarInterface()
                  requisicaoInterna = new RequisicaoInterna()
              }

  DlgDadosRequisicaoInterna(Aplicacao aplicacao, Pedido pedido, Vector itensRequisicaoInterna) {
    super(aplicacao,true)
      this.setTitle("Requisição Interna")
      this.aplicacao = aplicacao
      this.solicitante = aplicacao.obterColaborador()
      this.pedidoCliente = pedido
      this.itensRequisicaoInterna = itensRequisicaoInterna
      this.pedidosSelecionados = new Vector()
      this.pedidosSelecionados.addElement(pedido)
      montarInterface()
      requisicaoInterna = new RequisicaoInterna()
      cbxTipoSolicitacao.setSelectedIndex(1)
      cbxTipoSolicitacao.setEnabled(false)
      btNovoDepartamento.setEnabled(false)
      for(int i = 0;i < departamentos.size();i++) {
        if(departamentos.get(i) instanceof Departamento) {
          if(((Departamento)departamentos.get(i)).obterNomeDepartamento().equals(this.solicitante.obterDepartamento().obterNomeDepartamento())) {
            cbxDepartamento.setSelectedIndex(i)
              break
          }
        }
      }
    cbxDepartamento.setEnabled(false)

      for(int i = 0;i < this.itensRequisicaoInterna.size();i++) {
        ItemRequisicaoInterna itemRequisicaoInterna = ((ItemRequisicaoInterna)itensRequisicaoInterna.get(i))
          itemRequisicaoInterna.definirRequisicaoInterna(this.requisicaoInterna)
          this.tblItens.setValueAt(itemRequisicaoInterna.obterItem(),i,0)
          this.tblItens.setValueAt("" + itemRequisicaoInterna.obterQuantidadeItem(),i,1)
          itemRequisicaoInterna.definirDestino(this.solicitante.obterDepartamento())
      }

    this.btExcluirItem.setEnabled(true)
      this.btConfirmar.setEnabled(true)
  }

  DlgDadosRequisicaoInterna(Aplicacao aplicacao, RequisicaoInterna requisicaoInterna) {
    super(aplicacao,true)
      this.setTitle("Requisição Interna")
      this.aplicacao = aplicacao
      this.requisicaoInterna = requisicaoInterna

      if(requisicaoInterna != null) {
        try
        {
          this.solicitante = requisicaoInterna.obterSolicitante()
            this.pedidosSelecionados = requisicaoInterna.obterPedidosCliente(aplicacao.obterConexao())
            montarInterface()
            cbxTipoSolicitacao.setSelectedItem(requisicaoInterna.obterTipoSolicitacaoLiteral())
            txtDataLimiteEntrega.setText(Calendario.ajustarFormatoDataBanco(requisicaoInterna.obterDataLimiteEntrega()))
            for(int i = 1;i < departamentos.size();i++) {
              if(((Departamento)departamentos.get(i)).obterNomeDepartamento().equals(requisicaoInterna.obterDepartamento().obterNomeDepartamento()))
                cbxDepartamento.setSelectedIndex(i)
            }
          txaJustificativa.setText(requisicaoInterna.obterJustificativa())
            String query = "select i.descricao,iri.quantidade as necessaria from item i, item_requisicao_interna iri " +
            "where i.codigo = iri.item and iri.requisicao_interna = " + requisicaoInterna.obterCodigo()
            ResultSet rsItensPedidos = this.aplicacao.obterConexao().executarConsulta(query)
            int numeroItens = 0
            while(rsItensPedidos.next()) {
              this.tblItens.setValueAt(rsItensPedidos.getString("descricao"),numeroItens,0)
                this.tblItens.setValueAt("" + rsItensPedidos.getFloat("necessaria"),numeroItens,1)
                numeroItens++
            }
          rsItensPedidos.close()
        }
        catch(Exception e) {
          JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar a requisição interna. ","Erro", JOptionPane.ERROR_MESSAGE)
            e.printStackTrace()
        }
      }

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

      pnlAreaDados = new JPanel()
      card = new CardLayout()
      pnlAreaDados.setLayout(card)
      pnlDadosRequisicaoInterna = new JPanel(gridbag)
      pnlDadosRequisicaoInterna.setBorder(new TitledBorder("Dados da Requisição Interna"))

      JLabel label = new JLabel("Tipo de Solicitação")
      adicionarComponente(pnlDadosRequisicaoInterna,label,0,0,2,1)
      label = new JLabel("Dt. Limite Entrega (dd/mm/aaaa)")
      adicionarComponente(pnlDadosRequisicaoInterna,label,0,2,2,1)

      cbxTipoSolicitacao = new JComboBox()
      cbxTipoSolicitacao.addActionListener(this)
      cbxTipoSolicitacao.addItem("Selecione...")
      cbxTipoSolicitacao.addItem("Consumo")
      cbxTipoSolicitacao.addItem("Vendas")
      cbxTipoSolicitacao.addItem("Descarte")
      cbxTipoSolicitacao.addItem("Devolução")
      cbxTipoSolicitacao.addItem("Devolução Externa")
      tiposSolicitacoes = new Vector()
      tiposSolicitacoes.addElement(null)
      tiposSolicitacoes.addElement(RequisicaoInterna.REQUISICAO_CONSUMO)
      tiposSolicitacoes.addElement(RequisicaoInterna.REQUISICAO_VENDAS)
      tiposSolicitacoes.addElement(RequisicaoInterna.REQUISICAO_DESCARTE)
      tiposSolicitacoes.addElement(RequisicaoInterna.REQUISICAO_DEVOLUCAO)
      tiposSolicitacoes.addElement(RequisicaoInterna.REQUISICAO_DEVOLUCAO_EXTERNA)

      adicionarComponente(pnlDadosRequisicaoInterna,cbxTipoSolicitacao,1,0,2,1)

      txtDataLimiteEntrega = new JTextField(10)
      txtDataLimiteEntrega.addFocusListener(this)
      adicionarComponente(pnlDadosRequisicaoInterna,txtDataLimiteEntrega,1,2,2,1)

      label = new JLabel("Departamento")
      adicionarComponente(pnlDadosRequisicaoInterna,label,2,0,2,1)

      JPanel pnlSuporteCombo = new JPanel(new BorderLayout())
      try
      {
        departamentos = departamento.carregarNomesDepartamentos(aplicacao.obterConexao())
          cbxDepartamento = new JComboBox(departamentos)
      }
    catch(Exception e) {
      JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar os Departamentos. ","Erro", JOptionPane.ERROR_MESSAGE)
        e.printStackTrace()
    }
    finally
    {
      if(cbxDepartamento == null) {
        cbxDepartamento = new JComboBox()
          cbxDepartamento.addItem("Selecione...")
      }
      cbxDepartamento.addActionListener(this)
    }

    pnlSuporteCombo.add(cbxDepartamento,BorderLayout.CENTER)
      btNovoDepartamento = new JButton(new ImageIcon("imagens/novo.jpg"))
      btNovoDepartamento.addActionListener(this)
      btNovoDepartamento.setToolTipText("Novo Departamento")
      btNovoDepartamento.setPreferredSize(new Dimension(22,20))
      pnlSuporteCombo.add(btNovoDepartamento,BorderLayout.EAST)
      adicionarComponente(pnlDadosRequisicaoInterna,pnlSuporteCombo,3,0,2,1)

      label = new JLabel("Justificativa")
      adicionarComponente(pnlDadosRequisicaoInterna,label,4,0,4,1)

      txaJustificativa = new JTextArea(4,30)
      txaJustificativa.setLineWrap(true)
      txaJustificativa.setWrapStyleWord(true)
      JScrollPane scroll = new JScrollPane(txaJustificativa)
      adicionarComponente(pnlDadosRequisicaoInterna,scroll,5,0,4,1)

      //Painel Pedidos do Cliente
      pnlPedidosClientes = new JPanel(new BorderLayout())

      JPanel pnlPedidos = new JPanel(new BorderLayout())
      pnlPedidos.add(new JLabel("Pedidos"), BorderLayout.NORTH)
      try
      {
        this.pedidos = Pedido.carregarPedidosPendentes(aplicacao.obterConexao())
          modeloListaPedidos = new DefaultListModel()
          lstPedidos = new JList(modeloListaPedidos)
          for(int i = 0;i < this.pedidos.size();i++) {
            modeloListaPedidos.addElement(this.pedidos.get(i).toString())
          }
        lstPedidos.setFixedCellWidth(200)
          scroll = new JScrollPane(lstPedidos)
          pnlPedidos.add(scroll, BorderLayout.CENTER)
          pnlPedidosClientes.add(pnlPedidos, BorderLayout.WEST)
      }
    catch(Exception e) {
      JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar os pedidos.","Erro",JOptionPane.ERROR_MESSAGE)
        e.printStackTrace()
    }
    JPanel pnlSuporteComandos = new JPanel()
      JPanel pnlComandosPedidos = new JPanel(new GridLayout(2,1,5,5))
      btAdicionar = new JButton("Adicionar >>")
      btAdicionar.addActionListener(this)
      pnlComandosPedidos.add(btAdicionar)
      btRemover = new JButton("<< Remover")
      btRemover.addActionListener(this)
      pnlComandosPedidos.add(btRemover)
      pnlSuporteComandos.add(pnlComandosPedidos)
      pnlPedidosClientes.add(pnlSuporteComandos, BorderLayout.CENTER)

      JPanel pnlPedidosSelecionados = new JPanel(new BorderLayout())
      pnlPedidosSelecionados.add(new JLabel("Pedidos Selecionados"), BorderLayout.NORTH)
      modeloListaPedidosSelecionados = new DefaultListModel()
      lstPedidosSelecionados = new JList(modeloListaPedidosSelecionados)
      if(this.pedidosSelecionados != null) {
        for(int i = 0;i < this.pedidosSelecionados.size();i++) {
          modeloListaPedidosSelecionados.addElement(this.pedidosSelecionados.get(i).toString())
            modeloListaPedidos.removeElement(this.pedidosSelecionados.get(i).toString())
        }
      }
    lstPedidosSelecionados.setFixedCellWidth(200)
      scroll = new JScrollPane(lstPedidosSelecionados)
      pnlPedidosSelecionados.add(scroll, BorderLayout.CENTER)
      pnlPedidosClientes.add(pnlPedidosSelecionados, BorderLayout.EAST)

      //Painel Itens da Requisição Interna
      if(itensRequisicaoInterna == null)
        itensRequisicaoInterna = new Vector()
          pnlDadosItensRequisicaoInterna = new JPanel(gridbag)
          pnlDadosItensRequisicaoInterna.setBorder(new TitledBorder("Item da Requisição Interna"))

          label = new JLabel("Item")
          adicionarComponente(pnlDadosItensRequisicaoInterna,label,0,0,1,1)
          label = new JLabel("Quantidade")
          adicionarComponente(pnlDadosItensRequisicaoInterna,label,0,1,1,1)

          pnlSuporteCombo = new JPanel(new BorderLayout())
          cbxItem = new JComboBox()
          cbxItem.addActionListener(this)

          pnlSuporteCombo.add(cbxItem,BorderLayout.CENTER)
          btNovoItem = new JButton(new ImageIcon("imagens/novo.jpg"))
          btNovoItem.addActionListener(this)
          btNovoItem.setToolTipText("Novo Item")
          btNovoItem.setPreferredSize(new Dimension(22,20))
          pnlSuporteCombo.add(btNovoItem,BorderLayout.EAST)
          adicionarComponente(pnlDadosItensRequisicaoInterna,pnlSuporteCombo,1,0,1,1)

          txtQuantidade = new JTextField(5)
          txtQuantidade.addFocusListener(this)
          adicionarComponente(pnlDadosItensRequisicaoInterna,txtQuantidade,1,1,1,1)

          JPanel pnlItens = new JPanel(new BorderLayout())
          JPanel pnlComandoItem = new JPanel(new FlowLayout(FlowLayout.RIGHT))
          btAdicionarItem = new JButton("Adicionar")
          btAdicionarItem.addActionListener(this)
          pnlComandoItem.add(btAdicionarItem)
          btCancelarItem = new JButton("Cancelar")
          btCancelarItem.addActionListener(this)
          pnlComandoItem.add(btCancelarItem)
          pnlItens.add(pnlComandoItem, BorderLayout.NORTH)
          Object[][] dados = new Object[100][2]
          String[] nomeColunas = ["Item","Quantidade"]
          tblItens = new JTable(dados, nomeColunas)
          tblItens.setPreferredScrollableViewportSize(new Dimension(460, 100))
          tblItens.addRowSelectionInterval(0,0)
          scroll = new JScrollPane(tblItens)

          pnlItens.add(scroll, BorderLayout.CENTER)
          pnlComandoItem = new JPanel(new FlowLayout(FlowLayout.RIGHT))
          btExcluirItem = new JButton("Excluir Selecionado")
          btExcluirItem.addActionListener(this)
          pnlComandoItem.add(btExcluirItem)
          pnlItens.add(pnlComandoItem, BorderLayout.SOUTH)
          adicionarComponente(pnlDadosItensRequisicaoInterna,pnlItens,4,0,4,1)

          pnlAreaDados.add(pnlDadosRequisicaoInterna,"Requisição Interna")
          pnlAreaDados.add(pnlPedidosClientes,"Pedidos Clientes")
          pnlAreaDados.add(pnlDadosItensRequisicaoInterna,"Itens da Requisição Interna")
          conteudo.add(pnlAreaDados,BorderLayout.CENTER)

          JPanel pnlComandos = new JPanel(new FlowLayout(FlowLayout.RIGHT))
          btAnterior = new JButton("<< Anterior")
          btAnterior.addActionListener(this)
          btAnterior.setEnabled(false)
          pnlComandos.add(btAnterior)

          btProximo = new JButton("Próximo >>")
          btProximoLeitura = new JButton("Próximo >>")
          if(requisicaoInterna == null) {
            btProximo.addActionListener(this)
              pnlComandos.add(btProximo)
          }
          else
          {
            btProximoLeitura.addActionListener(this)
              pnlComandos.add(btProximoLeitura)
          }

    btConfirmar = new JButton("Confirmar")
      if(requisicaoInterna == null) {
        btConfirmar.addActionListener(this)
          pnlComandos.add(btConfirmar)
      }

    btCancelar = new JButton("Cancelar")
      btCancelar.addActionListener(this)
      pnlComandos.add(btCancelar)

      conteudo.add(pnlComandos, BorderLayout.SOUTH)
      redimencionar()
  }

  private void redimencionar() {
    this.pack()
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize()
      this.setBounds((screenSize.width/2) - (this.getBounds().width/2),
          (screenSize.height/2) - (this.getBounds().height/2) - 30,
          this.getBounds().width,
          this.getBounds().height)
  }

  private void adicionarComponente(JPanel painel, Component c, int linha, int coluna, int largura, int altura) {
    gbc.gridx = coluna
      gbc.gridy = linha

      gbc.gridwidth = largura
      gbc.gridheight = altura

      gridbag.setConstraints(c,gbc)
      painel.add(c)
  }

  private void carregarDepartamentos() {
    try
    {
      cbxDepartamento.removeAllItems()
        departamentos = departamento.carregarNomesDepartamentos(aplicacao.obterConexao())
        for(int i = 0;i < departamentos.size();i++) {
          cbxDepartamento.addItem(departamentos.get(i))
        }
    }
    catch(Exception e) {
      JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar os Departamentos.","Erro",JOptionPane.ERROR_MESSAGE)
        e.printStackTrace()
    }
  }

  private void carregarItens() {
    cbxItem.removeAllItems()
      cbxItem.addItem("Selecione...")
      for(int i = 1;i < itens.size();i++) {
        cbxItem.addItem(((Item)itens.get(i)).obterDescricao())
      }
  }

  /** Reinicia o formulário para inserção de um novo item. */
  private void cancelarItem() {
    this.cbxItem.setSelectedIndex(0)
      this.txtQuantidade.setText("0,000")
  }

  private void carregarPedidosPendentes() {
    try
    {
      modeloListaPedidos.removeAllElements()
        this.pedidos = Pedido.carregarPedidosPendentes(aplicacao.obterConexao())
        for(int i = 0;i < this.pedidos.size();i++) {
          modeloListaPedidos.addElement(this.pedidos.get(i))
        }
    }
    catch(Exception e) {
      JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar pedidos em produção.","Erro", JOptionPane.ERROR_MESSAGE)
        e.printStackTrace()
    }
  }

  private void carregarPedidosProduzindo() {
    try
    {
      modeloListaPedidos.removeAllElements()
        this.pedidos = Pedido.carregarPedidosProduzindo(aplicacao.obterConexao())
        for(int i = 0;i < this.pedidos.size();i++) {
          modeloListaPedidos.addElement(this.pedidos.get(i))
        }
    }
    catch(Exception e) {
      JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar pedidos em produção.","Erro", JOptionPane.ERROR_MESSAGE)
        e.printStackTrace()
    }
  }

  void actionPerformed(java.awt.event.ActionEvent actionEvent) {
    Object objeto = actionEvent.getSource()

      if(objeto == this.btNovoDepartamento) {
        DlgDadosDepartamento dlgDadosDepartamento = new DlgDadosDepartamento(aplicacao)
          dlgDadosDepartamento.setVisible(true)
          this.carregarDepartamentos()
      }

    if(objeto == this.btNovoItem) {
      DlgDadosItem dlgDadosItem = new DlgDadosItem(aplicacao,'I')
        dlgDadosItem.setVisible(true)
        try
        {
          if(tiposSolicitacoes.get(cbxTipoSolicitacao.getSelectedIndex()) == "CS")
            this.itens = Item.carregarItensIndependentes(aplicacao.obterConexao())
          else
            this.itens = Item.carregarItens(aplicacao.obterConexao())
              this.carregarItens()
        }
      catch(Exception e) {
        JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar os Ítens.","Erro", JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
    }

    if(objeto == cbxTipoSolicitacao) {
      if(cbxTipoSolicitacao.getSelectedIndex() > 0) {
        try
        {
          if(tiposSolicitacoes.get(cbxTipoSolicitacao.getSelectedIndex()) == RequisicaoInterna.REQUISICAO_CONSUMO)
            this.itens = Item.carregarItensIndependentes(aplicacao.obterConexao())
          else
            this.itens = Item.carregarItens(aplicacao.obterConexao())
              this.carregarItens()
        }
        catch(Exception e) {
          JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar os Ítens.","Erro", JOptionPane.ERROR_MESSAGE)
            e.printStackTrace()
        }
      }
    }

    if(objeto == cbxDepartamento) {
      if(cbxDepartamento.getSelectedIndex() > 0) {
        try
        {
          if(tiposSolicitacoes.get(cbxTipoSolicitacao.getSelectedIndex()) == "CS")
            itens = Item.carregarItensIndependentes((Departamento)departamentos.get(cbxDepartamento.getSelectedIndex()),aplicacao.obterConexao())
          else
            this.itens = Item.carregarItens((Departamento)departamentos.get(cbxDepartamento.getSelectedIndex()),aplicacao.obterConexao())
              carregarItens()
              redimencionar()
        }
        catch(Exception e) {
          JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possivel carregar os itens.","Erro",JOptionPane.ERROR_MESSAGE)
        }
      }
    }

    if(objeto == btAdicionar) {
      if(!lstPedidos.isSelectionEmpty()) {
        pedidosSelecionados.addElement(pedidos.get(lstPedidos.getSelectedIndex()))
          pedidos.removeElementAt(lstPedidos.getSelectedIndex())
          DefaultListModel modelo = (DefaultListModel)lstPedidos.getModel()
          DefaultListModel modeloSelecionado = (DefaultListModel)lstPedidosSelecionados.getModel()
          modeloSelecionado.addElement(((Pedido)pedidosSelecionados.get(pedidosSelecionados.size() - 1)))
          modelo.removeElementAt(lstPedidos.getSelectedIndex())
      }
      else
      {
        JOptionPane.showMessageDialog(aplicacao,"Selecione um pedido.","Atenção",JOptionPane.WARNING_MESSAGE)
      }
    }

    if(objeto == btRemover) {
      if(!lstPedidosSelecionados.isSelectionEmpty()) {
        pedidos.addElement(pedidosSelecionados.get(lstPedidosSelecionados.getSelectedIndex()))
          pedidosSelecionados.removeElementAt(lstPedidosSelecionados.getSelectedIndex())
          DefaultListModel modelo = (DefaultListModel)lstPedidos.getModel()
          DefaultListModel modeloSelecionado = (DefaultListModel)lstPedidosSelecionados.getModel()
          modelo.addElement(((Pedido)pedidos.get(pedidos.size() - 1)))
          modeloSelecionado.removeElementAt(lstPedidosSelecionados.getSelectedIndex())
      }
      else
      {
        JOptionPane.showMessageDialog(aplicacao,"Selecione um Pedido.","Atenção",JOptionPane.WARNING_MESSAGE)
      }
    }

    if(objeto == btAdicionarItem) {
      if(itensRequisicaoInterna.size() < 20) {
        try
        {
          boolean ItemInserido = false
            for(int i=0;i < itensRequisicaoInterna.size();i++) {
              if(cbxItem.getSelectedItem().equals((String)tblItens.getValueAt(i,0))) {
                ItemInserido = true
              }
            }
          if(!ItemInserido) {
            itensRequisicaoInterna.addElement(new ItemRequisicaoInterna((Item)itens.get(cbxItem.getSelectedIndex()),
                  this.requisicaoInterna,
                  Float.parseFloat(Numero.inverterSeparador(this.txtQuantidade.getText())),
                  (Departamento)this.departamentos.get(cbxDepartamento.getSelectedIndex()),
                  RequisicaoInterna.STATUS_EMITIDO))
              this.tblItens.setValueAt(cbxItem.getSelectedItem(),itensRequisicaoInterna.size() - 1,0)
              this.tblItens.setValueAt(txtQuantidade.getText(),itensRequisicaoInterna.size() - 1,1)
              cancelarItem()
          }
          else
          {
            JOptionPane.showMessageDialog(aplicacao,"Erro: O Item informado já foi associado a Requisição Interna.","Erro",JOptionPane.ERROR_MESSAGE)
          }
        }
        catch(NumberFormatException n) {
          JOptionPane.showMessageDialog(aplicacao,"Erro: Valor incorreto.","Erro",JOptionPane.ERROR_MESSAGE)
            n.printStackTrace()
        }
        catch(Exception e) {
          JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE)
            e.printStackTrace()
        }
        if (itensRequisicaoInterna.size() > 0) {
          this.btExcluirItem.setEnabled(true)
            this.btConfirmar.setEnabled(true)
        }
        if (itensRequisicaoInterna.size() == 20) {
          this.btAdicionarItem.setEnabled(false)
        }
      }
      else
      {
        this.btAdicionarItem.setEnabled(false)
      }
    }

    if(objeto == this.btExcluirItem) {
      int linhaSelecionada = this.tblItens.getSelectedRow()
        this.itensRequisicaoInterna.removeElementAt(linhaSelecionada)

        this.tblItens.setValueAt("",linhaSelecionada,0)
        this.tblItens.setValueAt("",linhaSelecionada,1)

        if(linhaSelecionada < itensRequisicaoInterna.size()) {
          for(int i = linhaSelecionada;i < itensRequisicaoInterna.size();i++) {
            this.tblItens.setValueAt(this.tblItens.getValueAt(i+1,0),i,0)
              this.tblItens.setValueAt(this.tblItens.getValueAt(i+1,1),i,1)
          }
          this.tblItens.setValueAt("",this.itensRequisicaoInterna.size(),0)
            this.tblItens.setValueAt("",this.itensRequisicaoInterna.size(),1)
        }

      if (itensRequisicaoInterna.size() == 0) {
        this.btExcluirItem.setEnabled(false)
          this.btConfirmar.setEnabled(false)
      }
      if (itensRequisicaoInterna.size() < 20) {
        this.btAdicionarItem.setEnabled(true)
      }
    }

    if(objeto == this.btCancelarItem) {
      cancelarItem()
    }

    if(objeto == btProximoLeitura) {
      card.next(pnlAreaDados)
        indiceCard++
        if(indiceCard > 0)
          btAnterior.setEnabled(true)
            if(indiceCard == 2) {
              btProximo.setEnabled(false)
            }
    }

    if(objeto == btProximo) {
      boolean confirmado = true // Se os dados forem válidos autoriza a mudança de painel.
        if(indiceCard == 0) {
          try
          {
            this.requisicaoInterna.definirTipoSolicitacao(((String)this.tiposSolicitacoes.get(this.cbxTipoSolicitacao.getSelectedIndex()))==null?"":(String)this.tiposSolicitacoes.get(this.cbxTipoSolicitacao.getSelectedIndex()))
              this.requisicaoInterna.definirDataLimiteEntrega(txtDataLimiteEntrega.getText())
              this.requisicaoInterna.definirDepartamento(this.departamentos.get(this.cbxDepartamento.getSelectedIndex()) instanceof Departamento?(Departamento)this.departamentos.get(this.cbxDepartamento.getSelectedIndex()):null)
              this.requisicaoInterna.definirSolicitante(this.solicitante)
              this.requisicaoInterna.definirJustificativa(txaJustificativa.getText())
          }
          catch(NumberFormatException n) {
            JOptionPane.showMessageDialog(aplicacao,"Erro: Valor incorreto.","Erro",JOptionPane.ERROR_MESSAGE)
              n.printStackTrace()
              confirmado = false
          }
          catch(Exception e) {
            JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE)
              e.printStackTrace()
              confirmado = false
          }
        }

      // Seleção dos pedidos dos clientes se o tipo de requisição for de consumo.
      if(((String)tiposSolicitacoes.get(cbxTipoSolicitacao.getSelectedIndex())).equals(RequisicaoInterna.REQUISICAO_CONSUMO)) {
        if(indiceCard == 1) {
          if(pedidosSelecionados.size() > 0) {
            boolean proceguir = false
              boolean excluirPedidosRequisitados = false
              String query
              /*do
                {
                if(excluirPedidosRequisitados) {
                query = "delete from pedido_requisicao_interna "
                for(int i = 0i < pedidosSelecionados.size()i++) {
                if(i == 0)
                query += "where "
                query += "pedido = " + ((Pedido)pedidosSelecionados.get(i)).obterCodigo()
                if((i+1) < pedidosSelecionados.size())
                query += " or "
                }
                if(pedidosSelecionados.size() > 0) {
                this.aplicacao.obterConexao().executarAtualizacao(query)
                }
                }
                query = "select i.codigo,i.descricao,(sum(qmp.quantidade * mp.quantidade) + ((i.percentual_perda * sum(qmp.quantidade * mp.quantidade))/100)) as necessaria " +
                "from item i, modelo_pedido mp, quantidade_materia_prima qmp " +
                "where mp.referencia = qmp.referencia and qmp.produto = mp.modelo and mp.numero_sola = qmp.numero_sola and qmp.item = i.codigo "

                for(int i = 0i < pedidosSelecionados.size()i++) {
                if(i == 0)
                query += "and ("
                query += "mp.pedido = "+ ((Pedido)pedidosSelecionados.get(i)).obterCodigo()
                if((i+1) < pedidosSelecionados.size())
                query += " or "
                if((i + 1) == pedidosSelecionados.size())
                query += ") "
                }
                query += "and i.codigo not in (select distinct item from pedido_requisicao_interna "
                for(int i = 0i < pedidosSelecionados.size()i++) {
                if(i == 0)
                query += "where "
                query += "pedido = "+ ((Pedido)pedidosSelecionados.get(i)).obterCodigo()
                if((i+1) < pedidosSelecionados.size())
                query += " or "
                }
                query += ") group by i.codigo,i.descricao,i.percentual_perda,i.percentual_ipi"

                for(int i = 0i < 100i++) {
                this.tblItens.setValueAt("",i,0)
                this.tblItens.setValueAt("",i,1)
                }

                try
                {
                ResultSet rsItensPedidos = this.aplicacao.obterConexao().executarConsulta(query)

                int numeroItens = 0
                itensRequisicaoInterna.removeAllElements()
                while(rsItensPedidos.next()) {
                Item item = new Item(rsItensPedidos.getInt("codigo"),rsItensPedidos.getString("descricao"))
                float quantidadeNecessaria = rsItensPedidos.getFloat("necessaria")
                ItemRequisicaoInterna itemRequisicaoInterna = new ItemRequisicaoInterna(item,this.requisicaoInterna,quantidadeNecessaria,(Departamento)departamentos.get(cbxDepartamento.getSelectedIndex()),ItemRequisicaoInterna.STATUS_EMITIDO)
                itensRequisicaoInterna.addElement(itemRequisicaoInterna)

                this.tblItens.setValueAt(item.obterDescricao(),numeroItens,0)
                this.tblItens.setValueAt("" + itemRequisicaoInterna.obterQuantidadeItem(),numeroItens,1)
                numeroItens++
                }
                rsItensPedidos.close()
                proceguir = true
                }
                catch(SQLException e) {
              if(JOptionPane.showConfirmDialog(aplicacao,"Os ítens para este pedido já foram requisitados anteriormente.\n Tem certeza que deseja requisitar novamente?\n Aviso: Isso pode comprometer a produção de outros pedidos.","Atenção",JOptionPane.YES_NO_OPTION) == 0) {
                confirmado = true
                  proceguir = false
                  excluirPedidosRequisitados = true
              }
              else
              {
                excluirPedidosRequisitados = true
                  confirmado = false
                  proceguir = true
              }
            e.printStackTrace()
          }
          catch(Exception ex) {
            JOptionPane.showMessageDialog(aplicacao,"Erro: " + ex.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE)
              confirmado = false
              ex.printStackTrace()
          }
          }while(!proceguir)*/
          }
        }
      }
      else
      {
        card.next(pnlAreaDados)
          indiceCard++
          confirmado = true
      }

      if(confirmado) {
        card.next(pnlAreaDados)
          indiceCard++
          if(indiceCard > 0)
            btAnterior.setEnabled(true)
              if(indiceCard == 2) {
                btProximo.setEnabled(false)
              }
      }
    }

    if(objeto == btAnterior) {
      card.previous(pnlAreaDados)
        indiceCard--
        if(indiceCard < 2)
          btProximo.setEnabled(true)
            if(indiceCard == 0)
              btAnterior.setEnabled(false)
    }

    if(objeto == btConfirmar) {
      try
      {
        try
        {
          this.requisicaoInterna.cadastrarRequisicaoInterna()
            this.requisicaoInterna.associarItens(itensRequisicaoInterna,pedidosSelecionados)
        }
        catch(Exception e) {
          JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE)
            e.printStackTrace()
        }
        this.setVisible(false)
      }
      catch(Exception e) {
        JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
    }

    if(objeto == btCancelar) {
      this.setVisible(false)
    }
  }

  void focusGained(FocusEvent e) {
    Component componente = e.getComponent()

      if(componente == txtQuantidade)
        txtQuantidade.selectAll()
  }

  void focusLost(FocusEvent e) {}
}
