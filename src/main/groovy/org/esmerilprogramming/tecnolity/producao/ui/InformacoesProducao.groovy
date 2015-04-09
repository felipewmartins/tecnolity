package org.esmerilprogramming.tecnolity.producao.ui

import java.awt.*

import java.sql.*
import java.awt.event.*
import javax.swing.*
import javax.swing.border.*
import org.esmerilprogramming.tecnolity.aplicacao.Aplicacao
import org.esmerilprogramming.tecnolity.aplicacao.modelos.*
import org.esmerilprogramming.tecnolity.aplicacao.relatorios.*
import org.esmerilprogramming.tecnolity.pedidos.*
import org.esmerilprogramming.tecnolity.pedidos.ui.DlgRecursosPedido
import org.esmerilprogramming.tecnolity.producao.*

class InformacoesProducao extends JTabbedPane implements ActionListener, MouseListener
{
  private Aplicacao aplicacao

    /* Objetos da aba de Produção */
    private JPanel pnlProducao
    private JComboBox cbxSituacao
    private JButton btIniciarProducao, btFinalizarProducao, btCancelarProducao, btAtualizarProducao, btParalizarProducao, btHabilitarProducao, btRecursosNecessarios, btImprimirPedido, btImprimirPedidoPorMatriz
    private JTable tblProducao, tblStatus
    private Vector situacoes
    private String[] statusPedido = [Pedido.PENDENTE, Pedido.PRODUZINDO, Pedido.FINALIZADO, Pedido.ATRASADO, Pedido.CANCELADO, Pedido.PARALIZADO]
    private ModeloTabela modeloTabelaProducao

    /* Objetos da aba de Produtos */
    private JPanel pnlProduto
    private JComboBox cbxCliente
    private JButton btAdicionarProduto, btAlterarProduto, btExcluirProduto, btAtualizarProdutos,
            btImprimirProduto
              private JTable tblProduto
              private Vector clientes
              private ModeloTabelaProduto modeloTabelaProduto

              /* Objetos da aba de Matrizes */
              private JPanel pnlMatriz
              private JComboBox cbxProduto
              private JButton btAdicionarMatriz, btAlterarMatriz, btExcluirMatriz, btAtualizarMatrizes
              private JTable tblMatriz
              private Vector produtos
              private ModeloTabelaMatriz modeloTabelaMatriz

              InformacoesProducao(Aplicacao aplicacao) {
                this.setBorder(new LineBorder(Color.black))
                  this.aplicacao = aplicacao

                  // Conteúdo da Aba Produção
                  pnlProducao = new JPanel(new BorderLayout())

                  JPanel pnlParametro = new JPanel(new FlowLayout(FlowLayout.LEFT))
                  JLabel label = new JLabel("Situação: ")
                  pnlParametro.add(label)
                  cbxSituacao = new JComboBox()
                  cbxSituacao.addItem("Todos")
                  cbxSituacao.addItem("Pendentes")
                  cbxSituacao.addItem("Produzindo")
                  cbxSituacao.addItem("Finalizados")
                  cbxSituacao.addItem("Atrasados")
                  cbxSituacao.addItem("Cancelados")
                  cbxSituacao.addItem("Paralizado")
                  cbxSituacao.addActionListener(this)

                  pnlParametro.add(cbxSituacao)
                  pnlProducao.add(pnlParametro, BorderLayout.NORTH)

                  JPanel pnlAreaComandos = new JPanel()
                  JPanel pnlComandos = new JPanel(new GridLayout(9, 1, 5, 5))
                  btHabilitarProducao = new JButton("Habilitar")
                  btHabilitarProducao.addActionListener(this)
                  pnlComandos.add(btHabilitarProducao)
                  btIniciarProducao = new JButton("Iniciar")
                  btIniciarProducao.addActionListener(this)
                  pnlComandos.add(btIniciarProducao)
                  btParalizarProducao = new JButton("Paralizar")
                  btParalizarProducao.addActionListener(this)
                  pnlComandos.add(btParalizarProducao)
                  btFinalizarProducao = new JButton("Finalizar")
                  btFinalizarProducao.addActionListener(this)
                  pnlComandos.add(btFinalizarProducao)
                  btCancelarProducao = new JButton("Cancelar")
                  btCancelarProducao.addActionListener(this)
                  pnlComandos.add(btCancelarProducao)
                  btAtualizarProducao = new JButton("Atualizar Tabela")
                  btAtualizarProducao.addActionListener(this)
                  pnlComandos.add(btAtualizarProducao)
                  btImprimirPedido = new JButton("Imprimir Pedido")
                  btImprimirPedido.addActionListener(this)
                  pnlComandos.add(btImprimirPedido)
                  btImprimirPedidoPorMatriz = new JButton("Impr. por Referência")
                  btImprimirPedidoPorMatriz.addActionListener(this)
                  pnlComandos.add(btImprimirPedidoPorMatriz)
                  btRecursosNecessarios = new JButton("Recursos Necessários")
                  btRecursosNecessarios.addActionListener(this)
                  pnlComandos.add(btRecursosNecessarios)
                  pnlAreaComandos.add(pnlComandos)
                  pnlProducao.add(pnlAreaComandos, BorderLayout.EAST)

                  JPanel pnlDadosPedido = new JPanel(new BorderLayout())
                  modeloTabelaProducao = new ModeloTabela()
                  modeloTabelaProducao.definirConexao(aplicacao.obterConexao())
                  /*String query =  "select pc.codigo, pc.ordem_compra, c.razao_social, le.descricao_local as local_entrega, pc.data_emissao, pc.data_entrega, (case pc.status when '"+ Pedido.PENDENTE +"' then 'Pendente' when '"+ Pedido.PRODUZINDO+"' then 'Produzindo' when '"+ Pedido.FINALIZADO +"' then 'Finalizado' when '"+ Pedido.ATRASADO +"' then 'Atrasado' when '"+ Pedido.CANCELADO +"' then 'Cancelado' when '"+ Pedido.PARALIZADO +"' then 'Paralizado' end) as status " +
                    "from pedido_cliente pc, cliente c, local_entrega le " +
                    "where pc.cliente = c.codigo and c.codigo = le.cliente and le.codigo_local = pc.local_entrega " +
                    "order by pc.codigo desc"
                    modeloTabelaProducao.definirConsulta(query)*/
                  tblProducao = new JTable(modeloTabelaProducao)
                  tblProducao.addMouseListener(this)
                  JScrollPane scroll = new JScrollPane(tblProducao)
                  pnlDadosPedido.add(scroll, BorderLayout.CENTER)

                  JPanel pnlStatus = new JPanel(new BorderLayout())

                  JPanel pnlTituloHistorico = new JPanel()
                  pnlTituloHistorico.add(new JLabel("Histórico do Pedido Selecionado"))
                  pnlStatus.add(pnlTituloHistorico, BorderLayout.NORTH)

                  Object[][] dados = new Object[1][5]
                  String[] nomeColunas = ["Pendente", "Iniciado", "Finalizado", "Paralizado", "Cancelado"]
                  tblStatus = new JTable(dados, nomeColunas)
                  tblStatus.setPreferredScrollableViewportSize(new Dimension(460, 20))
                  tblStatus.addRowSelectionInterval(0, 0)
                  scroll = new JScrollPane(tblStatus)
                  pnlStatus.add(scroll, BorderLayout.CENTER)

                  pnlDadosPedido.add(pnlStatus, BorderLayout.SOUTH)
                  pnlProducao.add(pnlDadosPedido, BorderLayout.CENTER)

                  this.addTab("Produção", pnlProducao)

                  // Conteúdo da Aba de Produtos
                  pnlProduto = new JPanel(new BorderLayout())

                  pnlParametro = new JPanel(new FlowLayout(FlowLayout.LEFT))
                  label = new JLabel("Cliente: ")
                  pnlParametro.add(label)
                  this.cbxCliente = new JComboBox()
                  try {
                    Cliente cliente = new Cliente()
                      clientes = cliente.carregarClientes(aplicacao.obterConexao())
                      carregarClientes()
                  }
                catch(Exception e) {
                  JOptionPane.showMessageDialog(aplicacao, "Erro: Não foi possível carregar os Clientes.", "Erro", JOptionPane.ERROR_MESSAGE)
                    e.printStackTrace()
                }
                cbxCliente.addActionListener(this)
                  pnlParametro.add(cbxCliente)
                  pnlProduto.add(pnlParametro, BorderLayout.NORTH)

                  modeloTabelaProduto = new ModeloTabelaProduto()
                  modeloTabelaProduto.definirConexao(aplicacao.obterConexao())
                  //modeloTabelaProduto.definirConsulta("select m.codigo as codigo, m.modelo as produto, cl.razao_social as cliente, c.componente as 'tipo de componente', tp.tipo_producao as 'tipo de produção', m.referencia_cliente as 'referência do cliente' from modelo m, componente c, tipo_producao tp, cliente cl where m.cliente = cl.codigo and m.componente = c.codigo and m.tipo_producao = tp.codigo order by m.modelo asc")
                  tblProduto = new JTable(modeloTabelaProduto)
                  scroll = new JScrollPane(tblProduto)
                  pnlProduto.add(scroll, BorderLayout.CENTER)

                  pnlAreaComandos = new JPanel()
                  pnlComandos = new JPanel(new GridLayout(5, 1, 5, 5))
                  btAdicionarProduto = new JButton("Adicionar Produto")
                  btAdicionarProduto.addActionListener(this)
                  pnlComandos.add(btAdicionarProduto)
                  btAlterarProduto = new JButton("Alterar Selecionado")
                  btAlterarProduto.addActionListener(this)
                  pnlComandos.add(btAlterarProduto)
                  btExcluirProduto = new JButton("Excluir Selecionado")
                  btExcluirProduto.addActionListener(this)
                  pnlComandos.add(btExcluirProduto)
                  btAtualizarProdutos = new JButton("Atualizar")
                  btAtualizarProdutos.addActionListener(this)
                  pnlComandos.add(btAtualizarProdutos)
                  btImprimirProduto = new JButton("Imprimir Selecionado")
                  btImprimirProduto.addActionListener(this)
                  pnlComandos.add(btImprimirProduto)
                  pnlAreaComandos.add(pnlComandos)
                  pnlProduto.add(pnlAreaComandos, BorderLayout.EAST)

                  this.addTab("Produtos", pnlProduto)

                  // Conteúdo da Aba de Matrizes
                  pnlMatriz = new JPanel(new BorderLayout())

                  pnlParametro = new JPanel(new FlowLayout(FlowLayout.LEFT))
                  label = new JLabel("Produto: ")
                  pnlParametro.add(label)
                  this.cbxProduto = new JComboBox()
                  try {
                    Produto produto = new Produto()
                      produtos = produto.carregarProdutos(aplicacao.obterConexao())
                      carregarProdutos()
                  }
                catch(Exception e) {
                  JOptionPane.showMessageDialog(aplicacao, "Erro: Não foi possível carregar os Produtos.", "Erro", JOptionPane.ERROR_MESSAGE)
                    e.printStackTrace()
                }
                cbxProduto.addActionListener(this)
                  pnlParametro.add(cbxProduto)
                  pnlMatriz.add(pnlParametro, BorderLayout.NORTH)

                  modeloTabelaMatriz = new ModeloTabelaMatriz()
                  modeloTabelaMatriz.definirConexao(aplicacao.obterConexao())
                  //modeloTabelaMatriz.definirConsulta("select referencia, numero_sola as 'No. Sola', quantidade, dureza, densidade, peso, volume, tempo_injecao as 'Temp. Injeção' from matriz_modelo")
                  tblMatriz = new JTable(modeloTabelaMatriz)
                  scroll = new JScrollPane(tblMatriz)
                  pnlMatriz.add(scroll, BorderLayout.CENTER)

                  pnlAreaComandos = new JPanel()
                  pnlComandos = new JPanel(new GridLayout(4, 1, 5, 5))
                  btAdicionarMatriz = new JButton("Adicionar Matriz")
                  btAdicionarMatriz.addActionListener(this)
                  pnlComandos.add(btAdicionarMatriz)
                  btAlterarMatriz = new JButton("Alterar Selecionado")
                  btAlterarMatriz.addActionListener(this)
                  pnlComandos.add(btAlterarMatriz)
                  btExcluirMatriz = new JButton("Excluir Selecionado")
                  btExcluirMatriz.addActionListener(this)
                  pnlComandos.add(btExcluirMatriz)
                  btAtualizarMatrizes = new JButton("Atualizar")
                  btAtualizarMatrizes.addActionListener(this)
                  pnlComandos.add(btAtualizarMatrizes)
                  pnlAreaComandos.add(pnlComandos)
                  pnlMatriz.add(pnlAreaComandos, BorderLayout.EAST)

                  this.addTab("Matrizes", pnlMatriz)
              }

  private void carregarClientes() {
    cbxCliente.removeAllItems()
      cbxCliente.addItem("Todos")
      for(int i=1;i < clientes.size();i++) {
        cbxCliente.addItem(((Cliente)clientes.get(i)).obterRazaoSocial())
      }
  }

  private void carregarProdutos() {
    cbxProduto.removeAllItems()
      cbxProduto.addItem("Todos")
      for(int i=1;i < produtos.size();i++) {
        cbxProduto.addItem(((Produto)produtos.get(i)).obterNomeModelo())
      }
  }

  private void atualizarTabelaProducao() {
    String query =  "select pc.codigo, pc.ordem_compra, c.razao_social, le.descricao_local as local_entrega, pc.data_emissao, pc.data_entrega, (case pc.status when '"+ Pedido.PENDENTE +"' then 'Pendente' when '"+ Pedido.PRODUZINDO+"' then 'Produzindo' when '"+ Pedido.FINALIZADO +"' then 'Finalizado' when '"+ Pedido.ATRASADO +"' then 'Atrasado' when '"+ Pedido.CANCELADO +"' then 'Cancelado' when '"+ Pedido.PARALIZADO +"' then 'Paralizado' end) as status " +
      "from pedido_cliente pc, cliente c, local_entrega le " +
      "where pc.cliente = c.codigo and c.codigo = le.cliente and le.codigo_local = pc.local_entrega "
      if(cbxSituacao.getSelectedIndex() > 0) {
        query += "and pc.status = '" + statusPedido[cbxSituacao.getSelectedIndex() - 1] + "'"
      }
    query += " order by pc.codigo desc"
      modeloTabelaProducao.definirConsulta(query)
      tblProducao.setModel(modeloTabelaProducao)
      tblProducao.updateUI()
  }

  private void exibirHistoricoPedido() {
    int linhaSelecionada = tblProducao.getSelectedRow()
      if(linhaSelecionada >= 0) {
        try {
          Pedido pedido = new Pedido(Long.parseLong((String)tblProducao.getValueAt(linhaSelecionada, 0)))
            Vector historico = pedido.carregarHistoricoPedido(aplicacao.obterConexao())
            tblStatus.setValueAt("", 0, 0)
            tblStatus.setValueAt("", 0, 1)
            tblStatus.setValueAt("", 0, 2)
            tblStatus.setValueAt("", 0, 3)
            tblStatus.setValueAt("", 0, 4)
            for(int i = 0; i < historico.size();i++) {
              if(((RegistroHistoricoStatusPedido)historico.get(i)).getStatus().equals(Pedido.PENDENTE))
                tblStatus.setValueAt(((RegistroHistoricoStatusPedido)historico.get(i)).getData(), 0, 0)
              else if(((RegistroHistoricoStatusPedido)historico.get(i)).getStatus().equals(Pedido.PRODUZINDO))
                tblStatus.setValueAt(((RegistroHistoricoStatusPedido)historico.get(i)).getData(), 0, 1)
              else if(((RegistroHistoricoStatusPedido)historico.get(i)).getStatus().equals(Pedido.FINALIZADO))
                tblStatus.setValueAt(((RegistroHistoricoStatusPedido)historico.get(i)).getData(), 0, 2)
              else if(((RegistroHistoricoStatusPedido)historico.get(i)).getStatus().equals(Pedido.PARALIZADO))
                tblStatus.setValueAt(((RegistroHistoricoStatusPedido)historico.get(i)).getData(), 0, 3)
              else if(((RegistroHistoricoStatusPedido)historico.get(i)).getStatus().equals(Pedido.CANCELADO))
                tblStatus.setValueAt(((RegistroHistoricoStatusPedido)historico.get(i)).getData(), 0, 4)
            }
        }
        catch(Exception e) {

        }
      }
  }

  private void atualizarTabelaProduto() {
    if(cbxCliente.getSelectedIndex() > 0)
      modeloTabelaProduto.definirConsulta("select m.codigo, m.modelo, c.componente, tp.tipo_producao, m.referencia_cliente from modelo m, componente c, tipo_producao tp where m.componente = c.codigo and m.tipo_producao = tp.codigo and m.cliente = "+ ((Cliente)clientes.get(cbxCliente.getSelectedIndex())).obterCodigo() +" order by m.modelo asc")
    else
      modeloTabelaProduto.definirConsulta("select m.codigo, m.modelo, cl.razao_social, c.componente, tp.tipo_producao, m.referencia_cliente from modelo m, componente c, tipo_producao tp, cliente cl where m.cliente = cl.codigo and m.componente = c.codigo and m.tipo_producao = tp.codigo order by m.modelo asc")
        tblProduto.setModel(modeloTabelaProduto)
        tblProduto.updateUI()
  }

  private void atualizarTabelaMatriz() {
    if(cbxProduto.getSelectedIndex() > 0)
      modeloTabelaMatriz.definirConsulta("select referencia, numero_sola as 'No. Sola', quantidade, dureza, densidade, peso, volume, tempo_injecao as 'Temp. Injeção' from matriz_modelo where referencia in (select distinct referencia from quantidade_materia_prima where produto = "+ ((Produto)produtos.get(cbxProduto.getSelectedIndex())).obterCodigo() +")")
    else
      modeloTabelaMatriz.definirConsulta("select referencia, numero_sola as 'No. Sola', quantidade, dureza, densidade, peso, volume, tempo_injecao as 'Temp. Injeção' from matriz_modelo")
        tblMatriz.setModel(modeloTabelaMatriz)
        tblMatriz.updateUI()
  }

  void actionPerformed(java.awt.event.ActionEvent actionEvent) {
    Object objeto = actionEvent.getSource()

      if(objeto == cbxCliente) {
        atualizarTabelaProduto()
      }

    if(objeto == cbxProduto) {
      atualizarTabelaMatriz()
    }

    if(objeto == btHabilitarProducao) {
      try {
        int linhaSelecionada = tblProducao.getSelectedRow()
          if(linhaSelecionada >= 0) {
            Pedido pedido = new Pedido(Pedido.PENDENTE, Long.parseLong(tblProducao.getValueAt(linhaSelecionada, 0).toString()))
              pedido.alterarStatus()
          }
          else
          {
            JOptionPane.showMessageDialog(aplicacao, "Atenção: Selecione um pedido antes de continar.", "Atenção", JOptionPane.WARNING_MESSAGE)
          }
      }
      catch(Exception e) {
        JOptionPane.showMessageDialog(aplicacao, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
      atualizarTabelaProducao()
    }

    if(objeto == btIniciarProducao) {
      try {
        int linhaSelecionada = tblProducao.getSelectedRow()
          if(linhaSelecionada >= 0) {
            Pedido pedido = new Pedido(Pedido.PRODUZINDO, Long.parseLong(tblProducao.getValueAt(linhaSelecionada, 0).toString()))
              pedido.alterarStatus()
          }
          else
          {
            JOptionPane.showMessageDialog(aplicacao, "Atenção: Selecione um pedido antes de continar.", "Atenção", JOptionPane.WARNING_MESSAGE)
          }
      }
      catch(Exception e) {
        JOptionPane.showMessageDialog(aplicacao, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
      atualizarTabelaProducao()
    }

    if(objeto == btParalizarProducao) {
      try {
        int linhaSelecionada = tblProducao.getSelectedRow()
          if(linhaSelecionada >= 0) {
            Pedido pedido = new Pedido(Pedido.PARALIZADO, Long.parseLong(tblProducao.getValueAt(linhaSelecionada, 0).toString()))
              pedido.alterarStatus()
          }
          else
          {
            JOptionPane.showMessageDialog(aplicacao, "Atenção: Selecione um pedido antes de continar.", "Atenção", JOptionPane.WARNING_MESSAGE)
          }
      }
      catch(Exception e) {
        JOptionPane.showMessageDialog(aplicacao, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
      atualizarTabelaProducao()
    }

    if(objeto == btFinalizarProducao) {
      try {
        int linhaSelecionada = tblProducao.getSelectedRow()
          if(linhaSelecionada >= 0) {
            Pedido pedido = new Pedido(Pedido.FINALIZADO, Long.parseLong(tblProducao.getValueAt(linhaSelecionada, 0).toString()))
              pedido.alterarStatus()
          }
          else
          {
            JOptionPane.showMessageDialog(aplicacao, "Atenção: Selecione um pedido antes de continar.", "Atenção", JOptionPane.WARNING_MESSAGE)
          }
      }
      catch(Exception e) {
        JOptionPane.showMessageDialog(aplicacao, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
      atualizarTabelaProducao()
    }

    if(objeto == btCancelarProducao) {
      try {
        int linhaSelecionada = tblProducao.getSelectedRow()
          if(linhaSelecionada >= 0) {
            Pedido pedido = new Pedido(Pedido.CANCELADO, Long.parseLong(tblProducao.getValueAt(linhaSelecionada, 0).toString()))
              pedido.alterarStatus()
          }
          else
          {
            JOptionPane.showMessageDialog(aplicacao, "Atenção: Selecione um pedido antes de continar.", "Atenção", JOptionPane.WARNING_MESSAGE)
          }
      }
      catch(Exception e) {
        JOptionPane.showMessageDialog(aplicacao, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
      atualizarTabelaProducao()
    }

    if(objeto == btAtualizarProducao) {
      atualizarTabelaProducao()
    }

    if(objeto == btImprimirPedido) {
      int linha = tblProducao.getSelectedRow()
        int codigoPedido = Integer.parseInt((String)tblProducao.getValueAt(linha, 0))
        try {
          Pedido pedido = new Pedido(codigoPedido)
            pedido.carregarPedido(aplicacao.obterConexao())
            RelatorioPedido relPedido = new RelatorioPedido(pedido)
            Vector paginas = relPedido.paginar(aplicacao.obterFormatoPagina())
            Impressora impressora = new Impressora()
            impressora.addPaginas(paginas, aplicacao.obterFormatoPagina())
            impressora.imprimir()
        }
      catch(Exception e) {
        JOptionPane.showMessageDialog(aplicacao, "Erro: Não foi possível imprimir o pedido.", "Erro", JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
    }

    if(objeto == btImprimirPedidoPorMatriz) {
      int linha = tblProducao.getSelectedRow()
        int codigoPedido = Integer.parseInt((String)tblProducao.getValueAt(linha, 0))
        try {
          Pedido pedido = new Pedido(codigoPedido)
            pedido.carregarPedido(aplicacao.obterConexao())
            RelatorioPedidoReferencia relPedidoReferencia = new RelatorioPedidoReferencia(pedido)
            Vector paginas = relPedidoReferencia.paginar(aplicacao.obterFormatoPagina())
            Impressora impressora = new Impressora()
            impressora.addPaginas(paginas, aplicacao.obterFormatoPagina())
            impressora.imprimir()
        }
      catch(Exception e) {
        JOptionPane.showMessageDialog(aplicacao, "Erro: Não foi possível imprimir o pedido.", "Erro", JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
    }

    if(objeto == btRecursosNecessarios) {
      int[] linha = tblProducao.getSelectedRows()
        int[] codigoPedido = new int[linha.length]
        Pedido[] pedidos = new Pedido[linha.length]
        try {
          for(int i = 0;i < linha.length;i++) {
            codigoPedido[i] = Integer.parseInt((String)tblProducao.getValueAt(linha[i], 0))
              pedidos[i] = new Pedido(codigoPedido[i])
              pedidos[i].carregarPedido(aplicacao.obterConexao())
          }
          DlgRecursosPedido dlgRecursosPedido = new DlgRecursosPedido(aplicacao, pedidos)
            dlgRecursosPedido.setVisible(true)
        }
      catch(Exception e) {
        JOptionPane.showMessageDialog(aplicacao, "Erro: Não foi possível exibir os Recursos Necessários.", "Erro", JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
    }

    if (objeto == cbxSituacao) {
      atualizarTabelaProducao()
    }

    if(objeto == btAdicionarProduto) {
      DlgDadosProduto dlgDadosProduto = new DlgDadosProduto(aplicacao)
        dlgDadosProduto.setVisible(true)
    }

    if(objeto == btAlterarProduto) {
      int colunaSelecionada = tblProduto.getSelectedRow()
        if(colunaSelecionada >= 0) {
          try {
            Produto produto = new Produto(aplicacao.obterConexao(), Long.parseLong((String)tblProduto.getValueAt(colunaSelecionada, 0)))
              DlgDadosProduto dlgDadosProduto = new DlgDadosProduto(aplicacao, produto)
              dlgDadosProduto.setVisible(true)
          }
          catch(Exception e) {
            JOptionPane.showMessageDialog(aplicacao, "Erro:" + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE)
              e.printStackTrace()
          }
        }
        else
        {
          JOptionPane.showMessageDialog(aplicacao, "Atenção: Selecione o produto a ser alterado.", "Atenção", JOptionPane.WARNING_MESSAGE)
        }
    }

    if(objeto == btExcluirProduto) {
      int codigoProduto = Integer.parseInt((String)tblProduto.getValueAt(tblProduto.getSelectedRow(), 0))
        Produto produto = new Produto()
        try {
          if(JOptionPane.showConfirmDialog(aplicacao, "Você tem certeza que deseja excluir\n TODAS as Informações deste Produto?", "Confirmação", JOptionPane.OK_CANCEL_OPTION) == 0) {
            produto.excluirProduto(codigoProduto)
          }
        }
      catch(Exception e) {
        JOptionPane.showMessageDialog(aplicacao, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
      atualizarTabelaProduto()
    }

    if(objeto == btAtualizarProdutos) {
      atualizarTabelaProduto()
    }

    if(objeto == btImprimirProduto) {
      int codigoProduto = Integer.parseInt((String)tblProduto.getValueAt(tblProduto.getSelectedRow(), 0))
        try {
          Produto produto = new Produto(aplicacao.obterConexao(), codigoProduto)
            produto.carregarProduto(aplicacao.obterConexao())
            RelatorioProduto relProduto = new RelatorioProduto(produto)
            Vector paginas = relProduto.paginar(aplicacao.obterFormatoPagina())
            Impressora impressora = new Impressora()
            impressora.addPaginas(paginas, aplicacao.obterFormatoPagina())
            impressora.imprimir()
        }
      catch(Exception e) {
        JOptionPane.showMessageDialog(aplicacao, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
    }

    if(objeto == btAdicionarMatriz) {
      DlgDadosMatriz dlgDadosMatriz = new DlgDadosMatriz(aplicacao)
        dlgDadosMatriz.setVisible(true)
    }

    if(objeto == btAlterarMatriz) {
      int linhaSelecionada = tblMatriz.getSelectedRow()
        if(linhaSelecionada >= 0) {
          String query = "select referencia, numero_sola " +
            "from matriz_modelo " +
            "where referencia = '"+ ((String)tblMatriz.getValueAt(linhaSelecionada, 0)).trim() +"' and " +
            "numero_sola = " + ((String)tblMatriz.getValueAt(linhaSelecionada, 1)).trim()
            try {
              ResultSet dadosMatriz = aplicacao.obterConexao().executarConsulta(query)
                if(dadosMatriz.next()) {
                  Matriz matriz = new Matriz(dadosMatriz.getString("referencia"), dadosMatriz.getInt("numero_sola"))
                    DlgDadosMatriz dlgDadosMatriz = new DlgDadosMatriz(aplicacao, matriz)
                    dlgDadosMatriz.setVisible(true)
                }
            }
          catch(Exception e) {
            JOptionPane.showMessageDialog(aplicacao, "Erro: Não foi possível carregar a Matriz Selecionada. \nEla pode ter sido Excluida por outro usuário. \n Clique no botão Atualizar para obter dados mais recentes.", "Erro", JOptionPane.ERROR_MESSAGE)
              e.printStackTrace()
          }
        }
        else
        {
          JOptionPane.showMessageDialog(aplicacao, "Atenção: Selecione a Matriz que deseja alterar.", "Atenção", JOptionPane.WARNING_MESSAGE)
        }
    }

    if(objeto == btExcluirMatriz) {
      int linhaSelecionada = tblMatriz.getSelectedRow()
        if(linhaSelecionada >= 0) {

          String query = "select referencia, numero_sola " +
            "from matriz_modelo " +
            "where referencia = '"+ ((String)tblMatriz.getValueAt(linhaSelecionada, 0)).trim() +"' and " +
            "numero_sola = " + ((String)tblMatriz.getValueAt(linhaSelecionada, 1)).trim()
            ResultSet dadosMatriz = aplicacao.obterConexao().executarConsulta(query)
            try {
              if(dadosMatriz.next()) {
                Matriz matriz = new Matriz(dadosMatriz.getString("referencia"), dadosMatriz.getInt("numero_sola"))
                  if(JOptionPane.showConfirmDialog(aplicacao, "Você tem certeza que deseja excluir esta Matriz?", "Confirmação", JOptionPane.OK_CANCEL_OPTION) == 0) {
                    matriz.excluirMatriz()
                  }
              }
              atualizarTabelaMatriz()
            }
          catch(SQLException eSql) {
            JOptionPane.showMessageDialog(aplicacao, "Não possível excluir a Matriz. Ela pode estar associada a algum produto.", "Erro", JOptionPane.ERROR_MESSAGE)
          }
          catch(Exception e) {
            JOptionPane.showMessageDialog(aplicacao, "Não possível excluir a Matriz. Ela pode estar associada a algum produto.", "Erro", JOptionPane.ERROR_MESSAGE)
          }
        }
        else
        {
          JOptionPane.showMessageDialog(aplicacao, "Atenção: Selecione a Matriz que deseja alterar.", "Atenção", JOptionPane.WARNING_MESSAGE)
        }
    }

    if(objeto == btAtualizarMatrizes) {
      atualizarTabelaMatriz()
    }
  }

  void mouseClicked(MouseEvent e) {

  }

  void mouseEntered(MouseEvent e) {

  }

  void mouseExited(MouseEvent e) {

  }

  void mousePressed(MouseEvent e) {

  }

  void mouseReleased(MouseEvent e) {
    exibirHistoricoPedido()
  }
}
