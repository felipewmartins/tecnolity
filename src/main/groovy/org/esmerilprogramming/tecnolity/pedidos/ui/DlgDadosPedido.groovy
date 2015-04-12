package org.esmerilprogramming.tecnolity.pedidos.ui

import java.awt.*
import java.awt.event.*


import javax.swing.*
import javax.swing.border.*

import org.esmerilprogramming.tecnolity.aplicacao.Aplicacao
import org.esmerilprogramming.tecnolity.pedidos.*
import org.esmerilprogramming.tecnolity.producao.*
import org.esmerilprogramming.tecnolity.producao.ui.*
import org.esmerilprogramming.tecnolity.util.*

class DlgDadosPedido extends JDialog implements ActionListener, FocusListener
{
  final int IDENTIFICADOR = 24
    static final int INICIAL = 0
    static final int PEDIDO = 1
    static final int ITENS_PEDIDO = 2

    private Aplicacao aplicacao
    private CardLayout card
    private int telaPedido
    private boolean alteracao

    private Container conteudo
    private JPanel pnlAreaDados
    private JRadioButton rdbImportar, rdbInserir
    private JButton btAbrirArquivo, btNovoCliente, btNovoProduto, btNovaMatriz, btProximo, btAnterior, btImportar, btCancelar, 
            btIncluirProduto, btExcluirProduto, btDesfazerProduto, btConfirmar, btConfirmarAlteracao
              private JTextField txtCaminhoArquivo, txtOrdemCompraCliente, txtDataEmissao, txtEsteiraCliente, 
            txtDataEntrega, txtValorTotalPedido, txtRemessa, txtQuantidade, 
            txtTransferenciaICMS, txtValorNegociado
              private JComboBox cbxTipoOperacao, cbxCliente, cbxMoeda, cbxProduto, cbxMatriz, cbxLocalEntrega
              private File edi
              private JTextArea txaConteudoArquivo, txaObservacao, txaObservacaoProduto, txaResumo
              private JTable tblProduto
              private JProgressBar pgbAndamentoImportacao
              private JLabel lblStatus
              private GridBagLayout gridbag
              private GridBagConstraints gbc
              private int indiceCard, codigoPedido, indiceTabela
              private Vector clientes, produtos, matrizes, locaisEntrega, produtosPedido
              private Pedido pedido

              DlgDadosPedido(Aplicacao aplicacao) {
                super(aplicacao, true)

                  // Define o título da janela
                  this.setTitle('Pedido')

                  this.aplicacao = aplicacao
                  this.produtosPedido = new Vector()
                  this.locaisEntrega = new Vector()

                  montarInterface()
              }

  DlgDadosPedido(Aplicacao aplicacao, Pedido pedido) {
    super(aplicacao, true)
      this.setTitle('Pedido')
      this.alteracao = true
      this.aplicacao = aplicacao
      this.pedido = pedido
      this.pedido.carregarPedido(aplicacao.obterConexao())
      this.produtosPedido = pedido.getProdutosPedido()
      this.locaisEntrega = new Vector()
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

      card = new CardLayout()
      pnlAreaDados = new JPanel(card)
      JLabel label = null
      JScrollPane scroll = null
      if (pedido == null) {
        JPanel pnlOpcoes = new JPanel(gridbag)
          ButtonGroup grupo = new ButtonGroup()
          rdbImportar = new JRadioButton('Importar Arquivo (EDI)')
          grupo.add(rdbImportar)
          adicionarComponente(pnlOpcoes, rdbImportar, 0, 0, 1, 1)
          rdbInserir = new JRadioButton('Inserir Manualmente')
          grupo.add(rdbInserir)
          adicionarComponente(pnlOpcoes, rdbInserir, 1, 0, 1, 1)
          pnlAreaDados.add(pnlOpcoes, 'opcoes')

          JPanel pnlImportacao = new JPanel(gridbag)
          label = new JLabel('Informe o caminho do Arquivo EDI a ser importado.')
          adicionarComponente(pnlImportacao, label, 0, 0, 1, 1)
          JPanel pnlSuporteTexto = new JPanel(new BorderLayout())
          txtCaminhoArquivo = new JTextField(20)
          pnlSuporteTexto.add(txtCaminhoArquivo, BorderLayout.CENTER)
          btAbrirArquivo = new JButton(new ImageIcon('imagens/abrir.gif'))
          btAbrirArquivo.addActionListener(this)
          btAbrirArquivo.setToolTipText('Abrir arquivo EDI')
          btAbrirArquivo.setPreferredSize(new Dimension(22, 20))
          pnlSuporteTexto.add(btAbrirArquivo, BorderLayout.EAST)
          adicionarComponente(pnlImportacao, pnlSuporteTexto, 1, 0, 1, 1)
          btImportar = new JButton('Importar')
          btImportar.addActionListener(this)
          adicionarComponente(pnlImportacao, btImportar, 2, 0, 1, 1)
          label = new JLabel('Andamento do Processo')
          adicionarComponente(pnlImportacao, label, 3, 0, 1, 1)
          pgbAndamentoImportacao = new JProgressBar()
          adicionarComponente(pnlImportacao, pgbAndamentoImportacao, 4, 0, 1, 1)
          label = new JLabel('Conteúdo do EDI')
          adicionarComponente(pnlImportacao, label, 5, 0, 1, 1)
          txaConteudoArquivo = new JTextArea(6, 30)
          scroll = new JScrollPane(txaConteudoArquivo)
          adicionarComponente(pnlImportacao, scroll, 6, 0, 1, 1)
          lblStatus = new JLabel('Status: Não Importado')
          adicionarComponente(pnlImportacao, lblStatus, 7, 0, 1, 1)
          pnlAreaDados.add(pnlImportacao, 'importacao')
      }
    JPanel pnlInsercaoPedido = new JPanel(gridbag)
      label = new JLabel('Cliente')
      adicionarComponente(pnlInsercaoPedido, label, 0, 0, 2, 1)
      label = new JLabel('Tipo de Operação')
      adicionarComponente(pnlInsercaoPedido, label, 0, 2, 1, 1)
      label = new JLabel('Ordem de Compra')
      adicionarComponente(pnlInsercaoPedido, label, 0, 3, 1, 1)
      cbxCliente = new JComboBox()
      try
      {
        Cliente cliente = new Cliente()
          clientes = cliente.carregarClientes(aplicacao.obterConexao())
          carregarClientes()
      }
    catch (e) {
      JOptionPane.showMessageDialog(aplicacao, 'Erro: Não foi possível carregar os Clientes.\n\n'  +  e.getMessage())
        e.printStackTrace()
    }
    cbxCliente.addActionListener(this)
      JPanel pnlSuporteCombo = new JPanel(new BorderLayout())
      pnlSuporteCombo.add(cbxCliente, BorderLayout.CENTER)
      btNovoCliente = new JButton(new ImageIcon('imagens/novo.jpg'))
      btNovoCliente.addActionListener(this)
      btNovoCliente.setToolTipText('Novo Cliente')
      btNovoCliente.setPreferredSize(new Dimension(22, 20))
      pnlSuporteCombo.add(btNovoCliente, BorderLayout.EAST)
      adicionarComponente(pnlInsercaoPedido, pnlSuporteCombo, 1, 0, 2, 1)
      cbxTipoOperacao = new JComboBox()
      cbxTipoOperacao.addItem('Selecione...')
      cbxTipoOperacao.addItem('Venda')
      cbxTipoOperacao.addItem('Beneficiamento')
      if (pedido != null) {
        cbxTipoOperacao.setSelectedItem(pedido.obterTipoOperacao() == 'V'?'Venda':'Beneficiamento')
      }
    adicionarComponente(pnlInsercaoPedido, cbxTipoOperacao, 1, 2, 1, 1)
      txtOrdemCompraCliente = new JTextField(pedido != null?pedido.obterOrdemCompra():'', 10)
      adicionarComponente(pnlInsercaoPedido, txtOrdemCompraCliente, 1, 3, 1, 1)
      label = new JLabel('Local de Entrega')
      adicionarComponente(pnlInsercaoPedido, label, 2, 0, 2, 1)
      label = new JLabel('Esteira')
      adicionarComponente(pnlInsercaoPedido, label, 2, 2, 1, 1)
      cbxLocalEntrega = new JComboBox()
      carregarLocaisEntrega()
      adicionarComponente(pnlInsercaoPedido, cbxLocalEntrega, 3, 0, 2, 1)
      txtEsteiraCliente = new JTextField(pedido != null?pedido.obterEsteira():'', 8)
      adicionarComponente(pnlInsercaoPedido, txtEsteiraCliente, 3, 2, 1, 1)
      label = new JLabel('Dt. Emissão')
      adicionarComponente(pnlInsercaoPedido, label, 4, 0, 1, 1)
      label = new JLabel('Dt. Entrega')
      adicionarComponente(pnlInsercaoPedido, label, 4, 1, 1, 1)

      txtDataEmissao = new JTextField(pedido != null?pedido.obterDataEmissao():'', 8)
      adicionarComponente(pnlInsercaoPedido, txtDataEmissao, 5, 0, 1, 1)
      txtDataEntrega = new JTextField(pedido != null?pedido.obterDataEntrega():'', 8)
      adicionarComponente(pnlInsercaoPedido, txtDataEntrega, 5, 1, 1, 1)

      label = new JLabel('Observação')
      adicionarComponente(pnlInsercaoPedido, label, 6, 0, 4, 1)
      txaObservacao = new JTextArea(pedido != null?pedido.obterObservacao():'', 6, 40)
      txaObservacao.setLineWrap(true)
      txaObservacao.setWrapStyleWord(true)
      scroll = new JScrollPane(txaObservacao)
      adicionarComponente(pnlInsercaoPedido, scroll, 7, 0, 4, 1)
      pnlAreaDados.add(pnlInsercaoPedido, 'pedido')

      JPanel pnlProdutosPedido = new JPanel(new BorderLayout())
      JPanel pnlProduto = new JPanel(gridbag)
      pnlProduto.setBorder(new TitledBorder('Informações do Produto'))
      label = new JLabel('Produto')
      adicionarComponente(pnlProduto, label, 0, 0, 1, 1)
      label = new JLabel('Matriz')
      adicionarComponente(pnlProduto, label, 2, 0, 1, 1)
      label = new JLabel('Quantidade')
      adicionarComponente(pnlProduto, label, 2, 1, 1, 1)
      cbxProduto = new JComboBox()
      produtos = new Vector()
      cbxProduto.addActionListener(this)
      pnlSuporteCombo = new JPanel(new BorderLayout())
      pnlSuporteCombo.add(cbxProduto, BorderLayout.CENTER)
      btNovoProduto = new JButton(new ImageIcon('imagens/novo.jpg'))
      btNovoProduto.addActionListener(this)
      btNovoProduto.setToolTipText('Novo Produto')
      btNovoProduto.setPreferredSize(new Dimension(22, 20))
      pnlSuporteCombo.add(btNovoProduto, BorderLayout.EAST)
      adicionarComponente(pnlProduto, pnlSuporteCombo, 1, 0, 1, 1)
      if (pedido != null) {
        produtos = Produto.carregarProdutos(((Cliente)clientes.get(cbxCliente.getSelectedIndex())), aplicacao.obterConexao())
          carregarProdutos()
      }
    cbxMatriz = new JComboBox()
      matrizes = new Vector()
      cbxMatriz.addActionListener(this)
      pnlSuporteCombo = new JPanel(new BorderLayout())
      pnlSuporteCombo.add(cbxMatriz, BorderLayout.CENTER)
      btNovaMatriz = new JButton(new ImageIcon('imagens/novo.jpg'))
      btNovaMatriz.addActionListener(this)
      btNovaMatriz.setToolTipText('Nova Matriz')
      btNovaMatriz.setPreferredSize(new Dimension(22, 20))
      pnlSuporteCombo.add(btNovaMatriz, BorderLayout.EAST)
      adicionarComponente(pnlProduto, pnlSuporteCombo, 3, 0, 1, 1)
      txtQuantidade = new JTextField(8)
      adicionarComponente(pnlProduto, txtQuantidade, 3, 1, 1, 1)
      label = new JLabel('Transferência ICMS (%)   ')
      adicionarComponente(pnlProduto, label, 4, 0, 1, 1)
      label = new JLabel('Valor Negociado')
      adicionarComponente(pnlProduto, label, 4, 1, 1, 1)
      txtTransferenciaICMS = new JTextField(3)
      adicionarComponente(pnlProduto, txtTransferenciaICMS, 5, 0, 1, 1)
      txtValorNegociado = new JTextField(8)
      adicionarComponente(pnlProduto, txtValorNegociado, 5, 1, 1, 1)
      label = new JLabel('Observação')
      adicionarComponente(pnlProduto, label, 6, 0, 2, 1)
      txaObservacaoProduto = new JTextArea(3, 40)
      scroll = new JScrollPane(txaObservacaoProduto)
      adicionarComponente(pnlProduto, scroll, 7, 0, 3, 1)
      pnlProdutosPedido.add(pnlProduto, BorderLayout.NORTH)

      JPanel pnlProdutosSelecionados = new JPanel(gridbag)
      btIncluirProduto = new JButton('Incluir Produto')
      btIncluirProduto.addActionListener(this)
      adicionarComponente(pnlProdutosSelecionados, btIncluirProduto, 0, 1, 1, 1)
      btDesfazerProduto = new JButton('Limpar')
      btDesfazerProduto.addActionListener(this)
      adicionarComponente(pnlProdutosSelecionados, btDesfazerProduto, 0, 2, 1, 1)
      Object[][] dados = new Object[1000][3]
      String[] nomeColunas = ['Produto', 'Matriz', 'Quantidade']
      tblProduto = new JTable(dados, nomeColunas)
      tblProduto.setPreferredScrollableViewportSize(new Dimension(460, 50))
      tblProduto.addRowSelectionInterval(0, 0)
      scroll = new JScrollPane(tblProduto)
      adicionarComponente(pnlProdutosSelecionados, scroll, 1, 0, 3, 1)
      if (pedido != null) {
        carregarProdutosPedido()
      }
    btExcluirProduto = new JButton('Excluir Selecionado')
      btExcluirProduto.addActionListener(this)
      adicionarComponente(pnlProdutosSelecionados, btExcluirProduto, 2, 0, 1, 1)
      pnlProdutosPedido.add(pnlProdutosSelecionados, BorderLayout.CENTER)

      pnlAreaDados.add(pnlProdutosPedido, 'produto')

      conteudo.add(pnlAreaDados, BorderLayout.CENTER)

      JPanel pnlComandos = new JPanel(new FlowLayout(FlowLayout.RIGHT))
      btAnterior = new JButton('<< Anterior')
      btAnterior.addActionListener(this)
      btAnterior.setEnabled(false)
      pnlComandos.add(btAnterior)
      btProximo = new JButton('Proximo >>')
      btProximo.addActionListener(this)
      pnlComandos.add(btProximo)
      btConfirmar = new JButton('Confirmar Pedido')
      btConfirmar.addActionListener(this)
      btConfirmar.setVisible(false)
      if (pedido == null) {
        pnlComandos.add(btConfirmar)
      }
      else
      {
        btConfirmarAlteracao = new JButton('Confirmar Pedido')
          btConfirmarAlteracao.addActionListener(this)
          btConfirmarAlteracao.setEnabled(false)
          pnlComandos.add(btConfirmarAlteracao)
      }
    btCancelar = new JButton('Cancelar')
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

  private void carregarClientes() {
    cbxCliente.removeAllItems()
      cbxCliente.addItem('Selecione...')
      for (int i = 1;i < clientes.size();i++) {
        cbxCliente.addItem(((Cliente)clientes.get(i)).obterRazaoSocial())
      }
    if (pedido != null) {
      cbxCliente.setSelectedItem(pedido.obterCliente().obterRazaoSocial())
        try
        {
          locaisEntrega = Cliente.carregarLocaisEntrega(aplicacao.obterConexao(), (Cliente)clientes.get(cbxCliente.getSelectedIndex()))
        }
      catch (e) {
        JOptionPane.showMessageDialog(aplicacao, 'Erro: Não foi possível carregar os locais de entrega.', 'Erro', JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
      Produto produto = new Produto()
        produtos = Produto.carregarProdutos(((Cliente)clientes.get(cbxCliente.getSelectedIndex())), aplicacao.obterConexao())
    }
    redimencionar()
  }

  private void carregarProdutos() {
    cbxProduto.removeAllItems()
      cbxProduto.addItem('Selecione...')
      for (int i = 1;i < produtos.size();i++) {
        cbxProduto.addItem(((Produto)produtos.get(i)).obterNomeModelo())
      }
    redimencionar()
  }

  private void carregarProdutosPedido() {
    for (int i = 0;i < produtosPedido.size();i++) {
      ProdutoPedido produtoPedido = (ProdutoPedido)produtosPedido.get(i)
        tblProduto.setValueAt(produtoPedido.obterProduto().obterNomeModelo(), indiceTabela, 0)
        tblProduto.setValueAt(''  +  produtoPedido.obterMatriz().obterNumeroSola(), indiceTabela, 1)
        tblProduto.setValueAt(''  +  produtoPedido.obterQuantidade(), indiceTabela, 2)
        indiceTabela++
    }
  }

  private void carregarMatrizes() {
    cbxMatriz.removeAllItems()
      cbxMatriz.addItem('Selecione...')
      Matriz matriz
      for (int i = 1;i < matrizes.size();i++) {
        matriz = (Matriz)matrizes.get(i)
          cbxMatriz.addItem(matriz.obterDescricao())
      }
    redimencionar()
  }

  private void carregarLocaisEntrega() {
    cbxLocalEntrega.removeAllItems()
      cbxLocalEntrega.addItem('Selecione...')
      for (int i = 1;i < locaisEntrega.size();i++) {
        cbxLocalEntrega.addItem(((LocalEntrega)locaisEntrega.get(i)).obterDescricaoLocal())
      }
    if (pedido != null)
      cbxLocalEntrega.setSelectedItem(pedido.obterLocalEntrega().obterDescricaoLocal())
        redimencionar()
  }

  private void adicionarComponente(JPanel painel, Component c, int linha, int coluna, int largura, int altura) {
    gbc.gridx = coluna
      gbc.gridy = linha

      gbc.gridwidth = largura
      gbc.gridheight = altura

      gridbag.setConstraints(c, gbc)
      painel.add(c)
  }

  private void inicializarFormularioProduto() {
    txtQuantidade.setText('')
      txtTransferenciaICMS.setText('')
      txaObservacaoProduto.setText('')
  }

  void actionPerformed(java.awt.event.ActionEvent actionEvent) {
    Object objeto = actionEvent.getSource()

      if (objeto == btAbrirArquivo) {
        JFileChooser fchEDI = new JFileChooser('edi')
          int status = fchEDI.showOpenDialog(this)

          if (status == JFileChooser.APPROVE_OPTION) {
            edi = fchEDI.getSelectedFile()
              txtCaminhoArquivo.setText(edi.getAbsolutePath())
          }
      }

    if (objeto == cbxCliente) {
      try
      {
        if (cbxCliente.getSelectedIndex() > 0) {
          locaisEntrega = Cliente.carregarLocaisEntrega(aplicacao.obterConexao(), (Cliente)clientes.get(cbxCliente.getSelectedIndex()))
            carregarLocaisEntrega()
        }
      }
      catch (e) {
        JOptionPane.showMessageDialog(aplicacao, 'Erro: Não foi possível carregar os locais de entrega.\n\n'  +  e.getMessage(), 'Erro', JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }

      try
      {
        if (cbxCliente.getSelectedIndex() > 0) {
          produtos = Produto.carregarProdutos(((Cliente)clientes.get(cbxCliente.getSelectedIndex())), aplicacao.obterConexao())
        }
        carregarProdutos()
      }
      catch (e) {
        JOptionPane.showMessageDialog(aplicacao, 'Erro: Não foi possível carregar os produtos.\n\n'  +  e.getMessage(), 'Erro', JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
    }

    if (objeto == cbxProduto) {
      try
      {
        if (cbxProduto.getSelectedIndex() > 0) {
          Matriz matriz = new Matriz()
            matrizes = matriz.carregarMatrizes(((Produto)produtos.get(cbxProduto.getSelectedIndex())), aplicacao.obterConexao())
            carregarMatrizes()
        }
      }
      catch (e) {
        JOptionPane.showMessageDialog(aplicacao, 'Erro: Não foi possível carregar as Matrizes dos produtos.\n\n'  +  e.getMessage(), 'Erro', JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
      if (txtValorNegociado != null && cbxProduto.getSelectedIndex() > 0) {
        txtValorNegociado.setText(Numero.inverterSeparador(''  +  ((Produto)produtos.get(cbxProduto.getSelectedIndex())).obterValor()))
      }
    }

    if (objeto == btIncluirProduto) {
      tblProduto.setValueAt(this.cbxProduto.getSelectedItem(), indiceTabela, 0)
        tblProduto.setValueAt(this.cbxMatriz.getSelectedItem(), indiceTabela, 1)
        tblProduto.setValueAt(this.txtQuantidade.getText(), indiceTabela, 2)
        ProdutoPedido produtoPedido = new ProdutoPedido(this.pedido, 
            ((Produto)produtos.get(cbxProduto.getSelectedIndex())), 
            ((Matriz)matrizes.get(cbxMatriz.getSelectedIndex())), 
            Float.parseFloat(Numero.inverterSeparador(txtQuantidade.getText())), 
            txaObservacaoProduto.getText(), 
            Integer.parseInt((txtTransferenciaICMS.getText().equals('')?'0':txtTransferenciaICMS.getText())), 
            Float.parseFloat(Numero.inverterSeparador(txtValorNegociado.getText())), 'R$', '')
        produtosPedido.addElement(produtoPedido)
        indiceTabela++
        if (indiceTabela > 0) {
          btExcluirProduto.setEnabled(true)
        }
      inicializarFormularioProduto()
    }

    if (objeto == btDesfazerProduto) {
      inicializarFormularioProduto()
    }

    if (objeto == btExcluirProduto) {
      int linhaSelecionada = tblProduto.getSelectedRow()
        produtosPedido.removeElementAt(linhaSelecionada)

        tblProduto.setValueAt('', linhaSelecionada, 0)
        tblProduto.setValueAt('', linhaSelecionada, 1)
        tblProduto.setValueAt('', linhaSelecionada, 2)

        if (linhaSelecionada < (indiceTabela - 1)) {
          for (int i = linhaSelecionada;i < (indiceTabela - 1);i++) {
            tblProduto.setValueAt(tblProduto.getValueAt(i + 1, 0), i, 0)
              tblProduto.setValueAt(tblProduto.getValueAt(i + 1, 1), i, 1)
              tblProduto.setValueAt(tblProduto.getValueAt(i + 1, 2), i, 2)
          }
          tblProduto.setValueAt('', indiceTabela -1, 0)
            tblProduto.setValueAt('', indiceTabela -1, 1)
            tblProduto.setValueAt('', indiceTabela -1, 2)
        }
      indiceTabela--

        if (indiceTabela == 0) {
          btExcluirProduto.setEnabled(false)
        }
    }

    if (objeto == btNovoCliente) {
      DlgDadosCliente dlgDadosCliente = new DlgDadosCliente(aplicacao)
        dlgDadosCliente.setVisible(true)
        try
        {
          Cliente cliente = new Cliente()
            clientes = cliente.carregarClientes(aplicacao.obterConexao())
            carregarClientes()
        }
      catch (e) {
        JOptionPane.showMessageDialog(aplicacao, 'Erro: Não foi possível carregar os Clientes.\n\n'  +  e.getMessage())
          e.printStackTrace()
      }
    }

    if (objeto == btNovoProduto) {
      DlgDadosProduto dlgDadosProduto = new DlgDadosProduto(aplicacao, ((Cliente)clientes.get(cbxCliente.getSelectedIndex())))
        dlgDadosProduto.setVisible(true)
        try
        {
          if (cbxCliente.getSelectedIndex() > 0) {
            produtos = Produto.carregarProdutos(((Cliente)clientes.get(cbxCliente.getSelectedIndex())), aplicacao.obterConexao())
          }
          carregarProdutos()
        }
      catch (e) {
        JOptionPane.showMessageDialog(aplicacao, 'Erro: Não foi possível carregar os produtos.\n\n'  +  e.getMessage(), 'Erro', JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
    }

    if (objeto == btNovaMatriz) {
      if (cbxProduto.getSelectedIndex() > 0) {
        DlgDadosMatriz dlgDadosMatriz = new DlgDadosMatriz(aplicacao, ((Produto)produtos.get(cbxProduto.getSelectedIndex())))
          dlgDadosMatriz.setVisible(true)
          try
          {
            Matriz matriz = new Matriz()
              matrizes = matriz.carregarMatrizes(((Produto)produtos.get(cbxProduto.getSelectedIndex())), aplicacao.obterConexao())
              carregarMatrizes()
          }
        catch (e) {
          JOptionPane.showMessageDialog(aplicacao, 'Erro: Não foi possível carregar as Matrizes do produto.\n\n'  +  e.getMessage(), 'Erro', JOptionPane.ERROR_MESSAGE)
            e.printStackTrace()
        }
      }
    }

    if (objeto == btImportar) {
      try
      {
        txaConteudoArquivo.setText(pedido.importarArquivoEDI(edi))
          lblStatus.setText('Status: Importado. '  +  pedido.obterNumeroRegistros() + ' registros.')
          redimencionar()
      }
      catch (e) {
        JOptionPane.showMessageDialog(aplicacao, 'Erro: Não foi possível importar o arquivo.\n\n'  +  e.getMessage(), 'Erro', JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
    }

    if (objeto == btAnterior) {
      if (rdbImportar != null) {
        if (rdbImportar.isSelected()) {
          card.show(pnlAreaDados, 'opcoes')
            btProximo.setEnabled(true)
            btAnterior.setEnabled(false)
        }
        else if (rdbInserir.isSelected()) {
          switch(telaPedido) {
            case ITENS_PEDIDO:
              card.show(pnlAreaDados, 'pedido')
                btProximo.setEnabled(true)
                btConfirmar.setVisible(false)
                telaPedido = PEDIDO
                break
            case PEDIDO:
                card.show(pnlAreaDados, 'opcoes')
                  btAnterior.setEnabled(false)
                  telaPedido = INICIAL
                  break
          }
        }
      }
      else
      {
        card.show(pnlAreaDados, 'pedido')
          btProximo.setEnabled(true)
          btAnterior.setEnabled(false)
          telaPedido = PEDIDO
      }
    }

    if (objeto == btProximo) {
      if (pedido != null?false:rdbImportar.isSelected()) {
        card.show(pnlAreaDados, 'importacao')
          btProximo.setEnabled(false)
          btAnterior.setEnabled(true)
      }
      else
      {
        if (pedido != null)
          telaPedido = PEDIDO
            switch(telaPedido) {
              case INICIAL: // Na inclusão de um pedido mostra o formulário de pedido em branco
                card.show(pnlAreaDados, 'pedido')
                  btAnterior.setEnabled(true)
                  telaPedido = PEDIDO
                  break
              case PEDIDO:
                  try
                  {
                    if (pedido == null)
                      pedido = new Pedido()
                    else if (btConfirmarAlteracao != null)
                      btConfirmarAlteracao.setEnabled(true)
                        this.pedido.definirCliente((Cliente)clientes.get(cbxCliente.getSelectedIndex()))
                        this.pedido.definirTipoOperacao(((String)cbxTipoOperacao.getSelectedItem()).charAt(0))
                        this.pedido.definirOrdemCompra(txtOrdemCompraCliente.getText())
                        this.pedido.definirDataEmissao(txtDataEmissao.getText())
                        this.pedido.definirDataEntrega(txtDataEntrega.getText())
                        this.pedido.definirLocalEntrega(((LocalEntrega)locaisEntrega.get(cbxLocalEntrega.getSelectedIndex())))
                        this.pedido.definirEsteira(txtEsteiraCliente.getText())
                        this.pedido.definirObservacao(txaObservacao.getText())
                        card.show(pnlAreaDados, 'produto')
                        btAnterior.setEnabled(true)
                        btProximo.setEnabled(false)
                        btConfirmar.setVisible(true)
                        telaPedido = ITENS_PEDIDO
                  }
                  catch (e) {
                    JOptionPane.showMessageDialog(aplicacao, 'Erro: '  +  e.getMessage(), 'Erro', JOptionPane.ERROR_MESSAGE)
                      e.printStackTrace()
                  }
                  break
            }
      }
    }

    if (objeto == btConfirmar) {
      try
      {
        this.pedido.cadastrarPedido()
          this.pedido.associarProdutos(produtosPedido)

          this.setVisible(false)
      }
      catch (e) {
        JOptionPane.showMessageDialog(aplicacao, 'Erro: Não foi possível cadastrar o pedido.'  +  e.getMessage(), 'Erro', JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
    }

    if (objeto == btConfirmarAlteracao) {
      try
      {
        this.pedido.alterarPedido()
          this.pedido.associarProdutos(produtosPedido)

          this.setVisible(false)
      }
      catch (e) {
        JOptionPane.showMessageDialog(aplicacao, 'Erro: Não foi possivel alterar o pedido.'  +  e.getMessage(), 'Erro', JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
    }

    if (objeto == btCancelar) {
      this.setVisible(false)
    }
  }

  void focusLost(java.awt.event.FocusEvent focusEvent) {

  }

  void focusGained(java.awt.event.FocusEvent e) {

  }
}
