package org.esmerilprogramming.tecnolity.producao.ui

import java.awt.*
import java.awt.event.*

import javax.swing.*
import javax.swing.border.*
import org.esmerilprogramming.tecnolity.pedidos.*
import org.esmerilprogramming.tecnolity.pedidos.ui.DlgDadosCliente
import org.esmerilprogramming.tecnolity.producao.*
import org.esmerilprogramming.tecnolity.suprimentos.*
import org.esmerilprogramming.tecnolity.suprimentos.ui.DlgDadosItem
import org.esmerilprogramming.tecnolity.util.*
import org.esmerilprogramming.tecnolity.aplicacao.Aplicacao
import org.esmerilprogramming.tecnolity.aplicacao.relatorios.*

class DlgDadosProduto extends JDialog implements ActionListener
{
  final int IDENTIFICADOR = 26

    private Aplicacao aplicacao

    private Container conteudo
    private JPanel pnlAreaDados
    private CardLayout card
    private int indiceCard
    private JButton btAdicionarMateriaPrima, btRemoverMateriaPrima, btLimparMateriaPrima,
            btNovaMatriz, btNovoItem, btProximo, btAnterior, btConfirmarParcial, btImprimir,
            btConfirmar, btCancelar, btNovoComponente, btNovoCliente, btNovoTipoProducao,
            btReassociarItens, btAlterarSelecionado, btAlterarMateriaPrima
              private JTable tblMateriaPrima
              private int idMatPrimSelecionada
              private int indiceTabela
              private GridBagLayout gridbag
              private GridBagConstraints gbc

              private JTextField txtModelo, txtReferenciaCliente, txtValor, txtCustoFabricacao, txtQuantidade
              private JComboBox cbxComponente, cbxCliente, cbxTipoProducao, cbxMatriz, cbxItem
              private JTextArea txaEspecificacaoInserto, txaAcabamentos, txaLavagem, txaPintura
              private JRadioButton rdbExportacao, rdbMercadoInterno
              private Vector tiposProducao, clientes, componentes, materiasPrimas, matrizes, itens,
            materiasPrimasIniciais
              private Cliente cliente
              private Produto produto

              DlgDadosProduto(Aplicacao aplicacao) {
                super(aplicacao,true)
                  this.setTitle("Novo Produto")

                  this.aplicacao = aplicacao

                  montarInterface()
              }

  DlgDadosProduto(Aplicacao aplicacao, Cliente cliente) {
    super(aplicacao,true)

      this.setTitle("Novo Produto")

      this.aplicacao = aplicacao
      this.cliente = cliente

      montarInterface()
  }

  DlgDadosProduto(Aplicacao aplicacao, Produto produto) {
    super(aplicacao,true)
      this.setTitle("Produto")

      this.aplicacao = aplicacao

      this.produto = produto
      try
      {
        this.produto.carregarProduto(aplicacao.obterConexao())
      }
    catch(Exception e) {
      JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar os dados do Produto.\n\n" + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE)
        e.printStackTrace()
    }
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
      JPanel pnlDadosProduto = new JPanel(gridbag)
      JLabel label = new JLabel("Modelo")
      adicionarComponente(pnlDadosProduto,label,0,0,3,1)
      label = new JLabel("Referência do Cliente")
      adicionarComponente(pnlDadosProduto,label,0,3,1,1)
      txtModelo = new JTextField((this.produto == null)?"":this.produto.obterNomeModelo(),28) 
      adicionarComponente(pnlDadosProduto,txtModelo,1,0,3,1)
      txtReferenciaCliente = new JTextField((this.produto == null)?"":this.produto.obterReferenciaCliente(),10)
      adicionarComponente(pnlDadosProduto,txtReferenciaCliente,1,3,1,1)
      label = new JLabel("Cliente")
      adicionarComponente(pnlDadosProduto,label,2,0,2,1)
      label = new JLabel("Tipo de Componente")
      adicionarComponente(pnlDadosProduto,label,2,2,1,1)
      label = new JLabel("Tipo de Produção")
      adicionarComponente(pnlDadosProduto,label,2,3,1,1)

      JPanel pnlSuporteCombo = new JPanel(new BorderLayout())
      cbxCliente = new JComboBox()
      try
      {
        Cliente cliente = new Cliente()

          clientes = cliente.carregarClientes(aplicacao.obterConexao())
          carregarClientes()
      }
    catch(Exception e) {
      JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar os Clientes","Erro", JOptionPane.ERROR_MESSAGE)
        e.printStackTrace()
    }
    pnlSuporteCombo.add(cbxCliente,BorderLayout.CENTER)
      btNovoCliente = new JButton(new ImageIcon("imagens/novo.jpg"))
      btNovoCliente.addActionListener(this)
      btNovoCliente.setToolTipText("Novo Cliente")
      btNovoCliente.setPreferredSize(new Dimension(22,20))
      pnlSuporteCombo.add(btNovoCliente,BorderLayout.EAST)
      adicionarComponente(pnlDadosProduto,pnlSuporteCombo,3,0,2,1)

      pnlSuporteCombo = new JPanel(new BorderLayout())
      cbxComponente = new JComboBox()
      try
      {
        Componente componente = new Componente()
          componentes = componente.carregarComponentes(aplicacao.obterConexao())
          carregarComponentes()
      }
    catch(Exception e) {
      JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar os Componentes.","Erro",JOptionPane.ERROR_MESSAGE)
        e.printStackTrace()
    }
    pnlSuporteCombo.add(cbxComponente,BorderLayout.CENTER)
      btNovoComponente = new JButton(new ImageIcon("imagens/novo.jpg"))
      btNovoComponente.addActionListener(this)
      btNovoComponente.setToolTipText("Novo Componente")
      btNovoComponente.setPreferredSize(new Dimension(22,20))
      pnlSuporteCombo.add(btNovoComponente,BorderLayout.EAST)
      adicionarComponente(pnlDadosProduto,pnlSuporteCombo,3,2,1,1)

      cbxTipoProducao = new JComboBox()
      try
      {
        TipoProducao tipoProducao = new TipoProducao()
          tiposProducao = tipoProducao.carregarTiposProducao(aplicacao.obterConexao())
          carregarTiposProducao()
      }
    catch(Exception e) {
      JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar os tipos de produção.","Erro",JOptionPane.ERROR_MESSAGE)
        e.printStackTrace()
    }
    pnlSuporteCombo = new JPanel(new BorderLayout())
      pnlSuporteCombo.add(cbxTipoProducao,BorderLayout.CENTER)
      btNovoTipoProducao = new JButton(new ImageIcon("imagens/novo.jpg"))
      btNovoTipoProducao.addActionListener(this)
      btNovoTipoProducao.setToolTipText("Novo Tipo de Produção")
      btNovoTipoProducao.setPreferredSize(new Dimension(22,20))
      pnlSuporteCombo.add(btNovoTipoProducao,BorderLayout.EAST)
      adicionarComponente(pnlDadosProduto,pnlSuporteCombo,3,3,1,1)
      boolean destino = true
      if(this.produto != null) {
        if(this.produto.obterDestino() == 'E')
          destino = false
      }
    JPanel pnlExportacao = new JPanel(new GridLayout(2,1))
      pnlExportacao.setBorder(new TitledBorder("Destino"))
      ButtonGroup grupo = new ButtonGroup()
      rdbMercadoInterno = new JRadioButton("Mercado Interno")
      rdbMercadoInterno.setSelected(destino)
      grupo.add(rdbMercadoInterno)
      pnlExportacao.add(rdbMercadoInterno)
      rdbExportacao = new JRadioButton("Exportação")
      rdbExportacao.setSelected(!destino)
      grupo.add(rdbExportacao)
      pnlExportacao.add(rdbExportacao)
      adicionarComponente(pnlDadosProduto,pnlExportacao,4,0,1,3)
      label = new JLabel('Valor (R$)')
      adicionarComponente(pnlDadosProduto,label,4,2,1,1)
      label = new JLabel('Fabricação (R$)')
      adicionarComponente(pnlDadosProduto,label,4,3,1,1)
      txtValor = new JTextField(((this.produto != null)?Numero.inverterSeparador(this.produto.obterValor()):"0,00"),8)
      adicionarComponente(pnlDadosProduto,txtValor,5,2,1,1)
      txtCustoFabricacao = new JTextField(((this.produto != null)?Numero.inverterSeparador(this.produto.obterCustoFabricacao()):"0,00"),8)
      adicionarComponente(pnlDadosProduto,txtCustoFabricacao,5,3,1,1)
      if(this.produto != null) {
        try
        {
          label = new JLabel("Valor atualizado em: " + produto.getDataUltimaAlteracaoValor(this.aplicacao.obterConexao()))
            label.setFont(new Font("Arial",Font.PLAIN,10))
            adicionarComponente(pnlDadosProduto,label,6,2,2,1)
        }
        catch(Exception e) {

        }
      }

    JPanel pnlInformacoes = new JPanel(gridbag)
      label = new JLabel("Insertos")
      adicionarComponente(pnlInformacoes,label,0,0,2,1)
      label = new JLabel("Acabamentos")
      adicionarComponente(pnlInformacoes,label,0,2,2,1)
      txaEspecificacaoInserto = new JTextArea(((this.produto != null)?this.produto.obterEspecificacaoInserto():""),4,20)
      txaEspecificacaoInserto.setLineWrap(true)
      txaEspecificacaoInserto.setWrapStyleWord(true)
      JScrollPane scroll = new JScrollPane(txaEspecificacaoInserto)
      adicionarComponente(pnlInformacoes,scroll,1,0,2,1)
      txaAcabamentos = new JTextArea(((this.produto != null)?this.produto.obterAcabamento():""),4,20)
      txaAcabamentos.setLineWrap(true)
      txaAcabamentos.setWrapStyleWord(true)
      scroll = new JScrollPane(txaAcabamentos)
      adicionarComponente(pnlInformacoes,scroll,1,2,2,1)
      label = new JLabel("Lavagem")
      adicionarComponente(pnlInformacoes,label,2,0,2,1)
      label = new JLabel("Pintura")
      adicionarComponente(pnlInformacoes,label,2,2,2,1)
      txaLavagem = new JTextArea(((this.produto != null)?this.produto.obterLavagem():""),4,20)
      txaLavagem.setLineWrap(true)
      txaLavagem.setWrapStyleWord(true)
      scroll = new JScrollPane(txaLavagem)
      adicionarComponente(pnlInformacoes,scroll,3,0,2,1)
      txaPintura = new JTextArea(((this.produto != null)?this.produto.obterPintura():""),4,20)
      txaPintura.setLineWrap(true)
      txaPintura.setWrapStyleWord(true)
      scroll = new JScrollPane(txaPintura)
      adicionarComponente(pnlInformacoes,scroll,3,2,2,1)
      adicionarComponente(pnlDadosProduto,pnlInformacoes,7,0,4,1)

      pnlAreaDados.add(pnlDadosProduto,"produto")

      JPanel pnlMateriaPrima = new JPanel(gridbag)
      materiasPrimas = new Vector()
      label = new JLabel("Matriz")
      adicionarComponente(pnlMateriaPrima,label,0,0,1,1)
      cbxMatriz = new JComboBox()
      if(this.produto != null) {
        try
        {
          Matriz matriz = new Matriz()
            this.matrizes = matriz.carregarMatrizes(aplicacao.obterConexao())
            carregarMatrizes()
        }
        catch(Exception e) {
          JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar as Matrizes.","Erro",JOptionPane.ERROR_MESSAGE)
            e.printStackTrace()
        }
      }
    cbxMatriz.addActionListener(this)
      pnlSuporteCombo = new JPanel(new BorderLayout())
      pnlSuporteCombo.add(cbxMatriz,BorderLayout.CENTER)
      btNovaMatriz = new JButton(new ImageIcon("imagens/novo.jpg"))
      btNovaMatriz.addActionListener(this)
      btNovaMatriz.setToolTipText("Nova Matriz")
      btNovaMatriz.setPreferredSize(new Dimension(22,20))
      pnlSuporteCombo.add(btNovaMatriz,BorderLayout.EAST)
      adicionarComponente(pnlMateriaPrima,pnlSuporteCombo,1,0,1,1)
      btReassociarItens = new JButton("Reassociar Itens")
      btReassociarItens.addActionListener(this)
      btReassociarItens.setVisible(false)
      adicionarComponente(pnlMateriaPrima,btReassociarItens,1,1,1,1)
      label = new JLabel("Materia Prima")
      adicionarComponente(pnlMateriaPrima,label,2,0,1,1)
      cbxItem = new JComboBox()
      try
      {
        itens = Item.carregarItens(aplicacao.obterConexao())
          carregarItens()
      }
    catch(Exception e) {
      JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar as Matérias Primas.","Erro",JOptionPane.ERROR_MESSAGE)
        e.printStackTrace()
    }
    pnlSuporteCombo = new JPanel(new BorderLayout())
      pnlSuporteCombo.add(cbxItem,BorderLayout.CENTER)
      btNovoItem = new JButton(new ImageIcon("imagens/novo.jpg"))
      btNovoItem.addActionListener(this)
      btNovoItem.setToolTipText("Nova Matéria Prima")
      btNovoItem.setPreferredSize(new Dimension(22,20))
      pnlSuporteCombo.add(btNovoItem,BorderLayout.EAST)
      adicionarComponente(pnlMateriaPrima,pnlSuporteCombo,3,0,1,1)
      label = new JLabel("Quantidade")
      adicionarComponente(pnlMateriaPrima,label,2,1,1,1)
      txtQuantidade = new JTextField("0,000",10)
      adicionarComponente(pnlMateriaPrima,txtQuantidade,3,1,1,1)
      JPanel pnlComandosTabela = new JPanel()
      btAdicionarMateriaPrima = new JButton("Adicionar")
      btAdicionarMateriaPrima.addActionListener(this)
      pnlComandosTabela.add(btAdicionarMateriaPrima)
      btAlterarMateriaPrima = new JButton("Alterar")
      btAlterarMateriaPrima.addActionListener(this)
      btAlterarMateriaPrima.setEnabled(false)
      pnlComandosTabela.add(btAlterarMateriaPrima)
      btLimparMateriaPrima = new JButton("Limpar")
      btLimparMateriaPrima.addActionListener(this)
      pnlComandosTabela.add(btLimparMateriaPrima)
      adicionarComponente(pnlMateriaPrima,pnlComandosTabela,4,0,3,1)
      Object[][] dados = new Object[1000][5]
      if(this.produto != null) {
        try
        {
          dados = this.produto.carregarMateriasPrimas(aplicacao.obterConexao(),dados)
            this.materiasPrimas = this.produto.obterMateriasPrimas()
            indiceTabela = materiasPrimas.size()
        }
        catch(Exception e) {
          JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar as Matérias Primas","Erro",JOptionPane.ERROR_MESSAGE)
            e.printStackTrace()
        }
      }
    String[] nomeColunas = ["Matriz","Matéria Prima","Quantidade"]
      tblMateriaPrima = new JTable(dados,nomeColunas)
      tblMateriaPrima.setPreferredScrollableViewportSize(new Dimension(500, 200))
      tblMateriaPrima.addRowSelectionInterval(0,0)
      scroll = new JScrollPane(tblMateriaPrima)
      adicionarComponente(pnlMateriaPrima,scroll,5,0,3,1)
      pnlComandosTabela = new JPanel()
      btAlterarSelecionado = new JButton("Alterar Selecionado")
      btAlterarSelecionado.addActionListener(this)
      pnlComandosTabela.add(btAlterarSelecionado)
      btRemoverMateriaPrima = new JButton("Remover Selecionado")
      btRemoverMateriaPrima.addActionListener(this)
      pnlComandosTabela.add(btRemoverMateriaPrima)
      adicionarComponente(pnlMateriaPrima,pnlComandosTabela,6,0,2,1)
      pnlAreaDados.add(pnlMateriaPrima,"materiaprima")

      conteudo.add(pnlAreaDados, BorderLayout.CENTER)
      JPanel pnlComandos = new JPanel(new FlowLayout(FlowLayout.RIGHT))
      if(this.produto != null) {
        btImprimir = new JButton("Imprimir")
          btImprimir.addActionListener(this)
          btImprimir.setEnabled(true)
          pnlComandos.add(btImprimir)
          btAnterior = new JButton("<< Anterior")
          btAnterior.addActionListener(this)
          btAnterior.setEnabled(false)
          pnlComandos.add(btAnterior)

          btProximo = new JButton("Próximo >>")
          btProximo.addActionListener(this)
          pnlComandos.add(btProximo)

          btConfirmar = new JButton("Confirmar")
          btConfirmar.addActionListener(this)
          pnlComandos.add(btConfirmar)
      }
      else
      {
        btConfirmarParcial = new JButton("Confirmar")
          btConfirmarParcial.addActionListener(this)
          pnlComandos.add(btConfirmarParcial)
      }
    btCancelar = new JButton("Cancelar")
      btCancelar.addActionListener(this)
      pnlComandos.add(btCancelar)
      conteudo.add(pnlComandos, BorderLayout.SOUTH)

      redimencionar()
  }

  private void carregarClientes() {
    cbxCliente.removeAllItems()
      cbxCliente.addItem("Selecione...")
      int posicao = 0
      for(int i = 1;i < clientes.size();i++) {
        if(cliente != null) {
          if(cliente.obterCodigo() == ((Cliente)clientes.get(i)).obterCodigo())
            posicao = i
        }else if(produto != null) {
          if(produto.obterCliente().obterCodigo() == ((Cliente)clientes.get(i)).obterCodigo())
            posicao = i
        }
        cbxCliente.addItem(((Cliente)clientes.get(i)).obterRazaoSocial())
      }
    cbxCliente.setSelectedIndex(posicao)
      redimencionar()
  }

  private void carregarMatrizes() {
    cbxMatriz.removeAllItems()
      cbxMatriz.addItem("Selecione...")
      Matriz matriz
      for(int i = 1;i < matrizes.size();i++) {
        matriz = (Matriz)matrizes.get(i)
          cbxMatriz.addItem(matriz.obterDescricao())
      }
    redimencionar()
  }

  private void carregarItens() {
    cbxItem.removeAllItems()
      cbxItem.addItem("Selecione...")
      for(int i = 1;i < itens.size();i++) {
        cbxItem.addItem(((Item)itens.get(i)).obterDescricao())
      }
    redimencionar()
  }

  private void carregarComponentes() {
    cbxComponente.removeAllItems()
      cbxComponente.addItem("Selecione...")
      int posicao = 0
      Componente componente
      for(int i = 1;i < componentes.size();i++) {
        componente = (Componente)componentes.get(i)
          if(this.produto != null) {
            try
            {
              if(this.produto.obterComponente().obterNomeComponente(aplicacao.obterConexao()).equals(componente.obterNomeComponente()))
                posicao = i
            }
            catch(Exception e) {
              e.printStackTrace()
            }
          }
        cbxComponente.addItem(componente.obterNomeComponente())
      }
    cbxComponente.setSelectedIndex(posicao)
      redimencionar()
  }

  private void carregarTiposProducao() {
    cbxTipoProducao.removeAllItems()
      cbxTipoProducao.addItem("Selecione...")
      int posicao = 0
      TipoProducao tipoProducao
      for(int i = 1;i < tiposProducao.size();i++) {
        tipoProducao = (TipoProducao)tiposProducao.get(i)
          if(this.produto != null) {
            if(this.produto.obterTipoProducao().obterCodigo() == tipoProducao.obterCodigo())
              posicao = i
          }
        cbxTipoProducao.addItem(tipoProducao.obterTipoProducao())
      }
    cbxTipoProducao.setSelectedIndex(posicao)
      redimencionar()
  }

  private void adicionarComponente(JPanel painel, Component c, int linha, int coluna, int largura, int altura) {
    gbc.gridx = coluna
      gbc.gridy = linha

      gbc.gridwidth = largura
      gbc.gridheight = altura

      gridbag.setConstraints(c,gbc)
      painel.add(c)
  }

  private void redimencionar() {
    this.pack()

      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize()
      this.setBounds((screenSize.width/2) - (this.getBounds().width/2),
          (screenSize.height/2) - (this.getBounds().height/2) - 30,
          this.getBounds().width,
          this.getBounds().height)
  }

  private void inicializarFormMateriaPrima() {
    this.cbxMatriz.setSelectedIndex(0)
      this.cbxItem.setSelectedIndex(0)
      this.txtQuantidade.setText("0,000")
  }

  private void carregarFormMateriaPrima(MateriaPrima materiaPrima) {
    for(int i = 1;i < matrizes.size();i++) {
      if(materiaPrima.obterMatriz().obterDescricao().equals(((Matriz)matrizes.get(i)).obterDescricao())) {
        this.cbxMatriz.setSelectedIndex(i)
          break
      }
    }

    for(int i = 1;i < itens.size();i++) {
      if(materiaPrima.obterItem().obterCodigo() == ((Item)itens.get(i)).obterCodigo()) {
        this.cbxItem.setSelectedIndex(i)
          break
      }
    }
    this.txtQuantidade.setText(Numero.inverterSeparador(materiaPrima.obterQuantidade("###.#####")))
  }

  void actionPerformed(java.awt.event.ActionEvent actionEvent) {
    Object objeto = actionEvent.getSource()

      if(objeto == btNovoCliente) {
        DlgDadosCliente dlgDadosCliente = new DlgDadosCliente(aplicacao)
          dlgDadosCliente.setVisible(true)
          try
          {
            Cliente cliente = new Cliente()
              clientes = cliente.carregarClientes(aplicacao.obterConexao())
              carregarClientes()
          }
        catch(Exception e) {
          JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar os Clientes","Erro", JOptionPane.ERROR_MESSAGE)
            e.printStackTrace()
        }
        redimencionar()
      }

    if(objeto == btNovoComponente) {
      DlgDadosComponente dlgDadosComponente = new DlgDadosComponente(aplicacao,'I')
        dlgDadosComponente.setVisible(true)
        try
        {
          Componente componente = new Componente()
            componentes = componente.carregarComponentes(aplicacao.obterConexao())
            carregarComponentes()
        }
      catch(Exception e) {
        JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar os Componentes.","Erro",JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
    }

    if(objeto == btNovoTipoProducao) {
      DlgDadosTipoProducao dlgDadosTipoProducao = new DlgDadosTipoProducao(aplicacao,'I')
        dlgDadosTipoProducao.setVisible(true)
        try
        {
          TipoProducao tipoProducao = new TipoProducao()
            tiposProducao = tipoProducao.carregarTiposProducao(aplicacao.obterConexao())
            carregarTiposProducao()
        }
      catch(Exception e) {
        JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar os tipos de produção.\n\n Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
    }

    if(objeto == cbxMatriz) {
      if(this.materiasPrimas.size() > 0) {
        this.btReassociarItens.setVisible(true)
          this.pack()
      }
      if(cbxMatriz.getSelectedIndex() == 0) {
        this.btReassociarItens.setVisible(false)
          this.pack()
      }
    }

    if(objeto == btNovaMatriz) {
      DlgDadosMatriz dlgDadosMatriz = new DlgDadosMatriz(aplicacao,produto)
        dlgDadosMatriz.setVisible(true)
        try
        {
          Matriz matriz = new Matriz()
            this.matrizes = matriz.carregarMatrizes(aplicacao.obterConexao())
            carregarMatrizes()
        }
      catch(Exception e) {
        JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar as Matrizes.","Erro",JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
    }

    if(objeto == btReassociarItens) {
      if(materiasPrimasIniciais == null) {
        materiasPrimasIniciais = new Vector()
          for(int i = 0;i < materiasPrimas.size();i++) {
            materiasPrimasIniciais.addElement(materiasPrimas.get(i))
          }
        for(int i = 0;i < materiasPrimasIniciais.size();i++) {
          try
          {
            MateriaPrima mp = ((MateriaPrima)materiasPrimasIniciais.get(i)).clonar()
              mp.definirMatriz((Matriz)matrizes.get(cbxMatriz.getSelectedIndex()))
              tblMateriaPrima.setValueAt(mp.obterMatriz().obterDescricao(),indiceTabela,0)
              tblMateriaPrima.setValueAt(mp.obterItem().obterDescricao(),indiceTabela,1)
              mp.definirQuantidade(0.0f)
              tblMateriaPrima.setValueAt(Numero.inverterSeparador(mp.obterQuantidade()),indiceTabela,2)
              materiasPrimas.addElement(mp)
              indiceTabela++
          }
          catch(Exception e) {
            JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE)
              e.printStackTrace()
          }
        }
      }
      else
      {
        for(int i = 0;i < materiasPrimasIniciais.size();i++) {
          try
          {
            MateriaPrima mp = ((MateriaPrima)materiasPrimasIniciais.get(i)).clonar()
              mp.definirMatriz((Matriz)matrizes.get(cbxMatriz.getSelectedIndex()))
              tblMateriaPrima.setValueAt(mp.obterMatriz().obterDescricao(),indiceTabela,0)
              tblMateriaPrima.setValueAt(mp.obterItem().obterDescricao(),indiceTabela,1)
              tblMateriaPrima.setValueAt("0,000",indiceTabela,2)
              materiasPrimas.addElement(mp)
              indiceTabela++
          }
          catch(Exception e) {
            JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE)
              e.printStackTrace()
          }
        }
      }
    }

    if(objeto == btNovoItem) {
      DlgDadosItem dlgDadosItem = new DlgDadosItem(aplicacao,'I')
        dlgDadosItem.setVisible(true)
        try
        {
          itens = Item.carregarItens(aplicacao.obterConexao())
            carregarItens()
        }
      catch(Exception e) {
        JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar as Matérias Primas.","Erro",JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
    }

    if(objeto == btAdicionarMateriaPrima) {
      try
      {
        materiasPrimas.addElement(new MateriaPrima(((Item)itens.get(cbxItem.getSelectedIndex())),((Matriz)matrizes.get(cbxMatriz.getSelectedIndex())),Float.parseFloat(Numero.inverterSeparador(txtQuantidade.getText()))))
          tblMateriaPrima.setValueAt(this.cbxMatriz.getSelectedItem(),indiceTabela,0)
          tblMateriaPrima.setValueAt(this.cbxItem.getSelectedItem(),indiceTabela,1)
          tblMateriaPrima.setValueAt(this.txtQuantidade.getText(),indiceTabela,2)
          indiceTabela++
          inicializarFormMateriaPrima()
      }
      catch(Exception e) {
        JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
    }

    if(objeto == btAlterarMateriaPrima) {
      try
      {
        materiasPrimas.setElementAt(new MateriaPrima(((Item)itens.get(cbxItem.getSelectedIndex())),((Matriz)matrizes.get(cbxMatriz.getSelectedIndex())),Float.parseFloat(Numero.inverterSeparador(txtQuantidade.getText()))),idMatPrimSelecionada)
          tblMateriaPrima.setValueAt(this.cbxMatriz.getSelectedItem(),idMatPrimSelecionada,0)
          tblMateriaPrima.setValueAt(this.cbxItem.getSelectedItem(),idMatPrimSelecionada,1)
          tblMateriaPrima.setValueAt(this.txtQuantidade.getText(),idMatPrimSelecionada,2)
          inicializarFormMateriaPrima()
          btAlterarMateriaPrima.setEnabled(false)
      }
      catch(Exception e) {
        JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
    }

    if(objeto == btLimparMateriaPrima) {
      inicializarFormMateriaPrima()
    }

    if(objeto == btAlterarSelecionado) {
      int linhaSelecionada = tblMateriaPrima.getSelectedRow()
        if(linhaSelecionada >= 0) {
          this.idMatPrimSelecionada = linhaSelecionada
            carregarFormMateriaPrima((MateriaPrima)materiasPrimas.get(linhaSelecionada))
            btAlterarMateriaPrima.setEnabled(true)
        }
        else
        {
          JOptionPane.showMessageDialog(aplicacao,"Atenção: Selecione uma Matéria Prima antes de continuar.","Atenção",JOptionPane.WARNING_MESSAGE)
        }
    }

    if(objeto == btRemoverMateriaPrima) {
      int linhaSelecionada = tblMateriaPrima.getSelectedRow()
        if(linhaSelecionada >= 0) {
          this.materiasPrimas.removeElementAt(linhaSelecionada)

            tblMateriaPrima.setValueAt("",linhaSelecionada,0)
            tblMateriaPrima.setValueAt("",linhaSelecionada,1)
            tblMateriaPrima.setValueAt("",linhaSelecionada,2)

            if(linhaSelecionada < (indiceTabela - 1)) {
              for(int i = linhaSelecionada;i < (indiceTabela - 1);i++) {
                tblMateriaPrima.setValueAt(tblMateriaPrima.getValueAt(i+1,0),i,0)
                  tblMateriaPrima.setValueAt(tblMateriaPrima.getValueAt(i+1,1),i,1)
                  tblMateriaPrima.setValueAt(tblMateriaPrima.getValueAt(i+1,2),i,2)
              }
              tblMateriaPrima.setValueAt("",indiceTabela -1,0)
                tblMateriaPrima.setValueAt("",indiceTabela -1,1)
                tblMateriaPrima.setValueAt("",indiceTabela -1,2)
                tblMateriaPrima.setValueAt("",indiceTabela -1,3)
            }
          indiceTabela--

            if (indiceTabela == 0) {
              btRemoverMateriaPrima.setEnabled(false)
            }
        }
        else
        {
          JOptionPane.showMessageDialog(aplicacao,"Atenção: Selecione uma Matéria Prima antes de continuar.","Atenção",JOptionPane.WARNING_MESSAGE)
        }
    }

    if(objeto == btAnterior) {
      card.show(pnlAreaDados,"produto")
        btAnterior.setEnabled(false)
        btProximo.setEnabled(true)
    }

    if(objeto == btProximo) {
      card.show(pnlAreaDados,"materiaprima")
        btAnterior.setEnabled(true)
        btProximo.setEnabled(false)
    }

    if(objeto == btConfirmar) {
      try
      {
        this.produto.definirReferenciaCliente(txtReferenciaCliente.getText())
          this.produto.definirComponente((Componente)componentes.get(cbxComponente.getSelectedIndex()))
          this.produto.definirNomeModelo(txtModelo.getText())
          this.produto.definirValor(Float.parseFloat(Numero.inverterSeparador(txtValor.getText())))
          this.produto.definirMoeda('R$')
          this.produto.definirCustoFabricacao(Float.parseFloat(Numero.inverterSeparador(txtCustoFabricacao.getText())))
          this.produto.definirCliente((Cliente)clientes.get(cbxCliente.getSelectedIndex()))
          this.produto.definirTipoProducao((TipoProducao)tiposProducao.get(cbxTipoProducao.getSelectedIndex()))
          this.produto.definirEspecificacaoInserto(txaEspecificacaoInserto.getText())
          this.produto.definirAcabamento(txaAcabamentos.getText())
          this.produto.definirLavagem(txaLavagem.getText())
          this.produto.definirPintura(txaPintura.getText())
          this.produto.definirDestino((rdbMercadoInterno.isSelected()?'I':'E'))
          this.produto.alterarProduto()
          this.produto.associarMateriasPrimas(this.materiasPrimas)
          this.setVisible(false)
      }
      catch(Exception e) {
        JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
    }

    if(objeto == btConfirmarParcial) {
      switch (indiceCard) {
        /* Salva o produto e segue para o cadastro de matrizes.*/
        case 0:
          try
          {
            this.produto = new Produto(txtReferenciaCliente.getText(),
                ((Componente)componentes.get(cbxComponente.getSelectedIndex())),
                txtModelo.getText(),
                Float.parseFloat(Numero.inverterSeparador(txtValor.getText())),'R$',
                Float.parseFloat(Numero.inverterSeparador(txtCustoFabricacao.getText())),
                ((Cliente)clientes.get(cbxCliente.getSelectedIndex())),
                ((TipoProducao)tiposProducao.get(cbxTipoProducao.getSelectedIndex())),
                txaEspecificacaoInserto.getText(),
                txaAcabamentos.getText(),
                txaLavagem.getText(),
                txaPintura.getText(),
                (rdbMercadoInterno.isSelected()?'I':'E'))
              this.produto.cadastrarProduto()
              card.show(pnlAreaDados,"materiaprima")
              indiceCard++
              try
              {
                Matriz matriz = new Matriz()
                  this.matrizes = matriz.carregarMatrizes(aplicacao.obterConexao())
                  carregarMatrizes()
              }
            catch(Exception e) {
              JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar as Matrizes.","Erro",JOptionPane.ERROR_MESSAGE)
                e.printStackTrace()
            }
          }
          catch(Exception e) {
            JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível cadastrar o Produto.\n\n Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE)
              e.printStackTrace()
          }
          break
        case 1:
            try
            {
              this.produto.associarMateriasPrimas(materiasPrimas)
                this.setVisible(false)
            }
            catch(Exception e) {
              JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE)
                e.printStackTrace()
            }
            break
      }
    }

    if(objeto == btImprimir) {
      try
      {
        RelatorioProduto relProduto = new RelatorioProduto(produto)
          Vector paginas = relProduto.paginar(aplicacao.obterFormatoPagina())
          Impressora impressora = new Impressora()
          impressora.addPaginas(paginas,aplicacao.obterFormatoPagina())
          impressora.imprimir()
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
}
