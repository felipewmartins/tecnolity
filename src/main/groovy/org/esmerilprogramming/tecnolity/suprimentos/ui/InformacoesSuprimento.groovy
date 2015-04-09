package org.esmerilprogramming.tecnolity.suprimentos.ui

import java.awt.*
import java.sql.*

import java.awt.event.*

import javax.swing.*
import javax.swing.border.*
import org.esmerilprogramming.tecnolity.pedidos.*
import org.esmerilprogramming.tecnolity.suprimentos.*
import org.esmerilprogramming.tecnolity.util.*
import org.esmerilprogramming.tecnolity.aplicacao.Aplicacao
import org.esmerilprogramming.tecnolity.aplicacao.DlgAlteracaoPreco
import org.esmerilprogramming.tecnolity.aplicacao.modelos.*
import org.esmerilprogramming.tecnolity.aplicacao.relatorios.*

class InformacoesSuprimento extends JTabbedPane implements ActionListener
{
  private Aplicacao aplicacao
    private JPanel pnlItem, pnlMovimentacao, pnlFornecedor, pnlRequisicaoInterna, pnlRequisicaoCompra, pnlInventario

    /* Objetos da aba de Itens */
    private JComboBox cbxCategoria
    private JButton btAdicionarItemTodos, btAlterarItemTodos, btExcluirItemTodos, btAtualizarItemTodos, btImprimirItensTodos, btPesquisarItensTodos,
            btAdicionarItemPorCategoria, btAlterarItemPorCategoria, btExcluirItemPorCategoria, btAtualizarItemPorCategoria, btImprimirItensPorCategoria, btPesquisarItensPorCategoria,
            btAlterarItemListaCompras, btExcluirItemListaCompras, btAtualizarItemListaCompras, btImprimirItensListaCompras, btAtualizarItensInativos, btImprimirItensInativos
              private JTextField txtPalavraChaveItemTodos, txtPalavraChaveItemPorCategoria
              private JTable tblItensTodos, tblItensPorCategoria, tblItensListaCompras, tblItensInativos
              private JCheckBox chbMostrarTodos
              private Vector categorias
              private Item item
              private Categoria categoria = new Categoria()
              private ModeloTabelaItens modeloTabelaItensTodos, modeloTabelaItensPorCategoria, modeloTabelaItensListaCompras, modeloTabelaItensInativos

              /* Objetos da aba Movimentacao */
              private JComboBox cbxTipoMovimentacao, cbxItemMovimentacao
              private JButton btMovimentarItem, btPesquisarMovimentacao, btAlterarMovimentacao, btExcluirMovimentacao, btAtualizarMovimentacao, btImprimirMovimentacao
              private JTable tblMovimentacoes
              private JTextField txtDataInicio, txtDataFinal
              private Vector tiposMovimentacao, itensMovimentacao
              private ModeloTabela modeloTabelaMovimentacoes

              /* Objetos da aba Fornecedores */
              private JButton btAdicionarFornecedor, btAlterarFornecedor, btExcluirFornecedor, btAtualizarFornecedor
              private JTable tblFornecedores
              private Fornecedor fornecedor
              private ModeloTabela modeloTabelaFornecedores

              /* Objetos da aba Requisica interna */
              private JButton btRequisitarItem, btCancelarRequisicao, btAtualizarRequisicao, btImprimirRequisicaoInterna, btVisualizarRequisicaoInterna
              private JTable tblRequisicoesInternas
              private ModeloTabela modeloTabelaRequisicoesInternas

              /* Objetos da aba Requisicao de compra */
              private JButton btEmitirRequisicaoCompra, btCancelarRequisicaoCompra, btVisualizarRequisicaoCompra,
            btAtualizarRequisicaoCompra, btImprimirRequisicaoCompra, btVisualizarRecursos, btAlterarValor,
            btAtualizarFornecedores, btImprimirFornecedores
              private JComboBox cbxPedidos, cbxFornecedor, cbxItem
              private Vector fornecedores, itens, fornecedoresItem
              private JTable tblRequisicoesCompras, tblRecursos, tblListaPrecos, tblFornecedoresPendentes
              private JScrollPane scrollRecursos
              private String[][] dadosRecursos
              private ModeloTabela modeloTabelaRequisicoesCompras
              private ModeloTabela modeloTabelaListaPreco
              private ModeloTabela modeloTabelaFornecedoresPendentes


              /* Objetos da aba Inventário*/
              private JTextField txtDataInventario
              private JButton btPesquisarItens, btImprimirInventario
              private JComboBox cbxItens
              private Vector itensInventario
              private JTable tblInventario
              private ModeloTabela modeloTabelaInventario

              InformacoesSuprimento(Aplicacao aplicacao) {
                this.setBorder(new LineBorder(Color.black))
                  this.aplicacao = aplicacao

                  // Conteúdo da Aba Itens
                  JTabbedPane tbpVisoesItens = new JTabbedPane()

                  pnlItem = new JPanel(new BorderLayout())

                  JPanel pnlParametro = new JPanel(new FlowLayout(FlowLayout.LEFT))
                  JLabel label = new JLabel('Pesquisa:')
                  pnlParametro.add(label)
                  txtPalavraChaveItemTodos = new JTextField(20)
                  txtPalavraChaveItemTodos.addActionListener(this)
                  pnlParametro.add(txtPalavraChaveItemTodos)
                  btPesquisarItensTodos = new JButton('Pesquisar')
                  btPesquisarItensTodos.addActionListener(this)
                  pnlParametro.add(btPesquisarItensTodos)
                  chbMostrarTodos = new JCheckBox('Mostrar Todos')
                  chbMostrarTodos.setToolTipText('Mostrar itens ativos e inativos')
                  pnlParametro.add(chbMostrarTodos)
                  pnlItem.add(pnlParametro, BorderLayout.NORTH)

                  modeloTabelaItensTodos = new ModeloTabelaItens()
                  modeloTabelaItensTodos.definirConexao(aplicacao.obterConexao())
                  tblItensTodos = new JTable(modeloTabelaItensTodos)
                  JScrollPane scroll = new JScrollPane(tblItensTodos)
                  pnlItem.add(scroll, BorderLayout.CENTER)

                  JPanel pnlAreaComandos = new JPanel()
                  JPanel pnlComandos = new JPanel(new GridLayout(5, 1, 5, 5))
                  btAdicionarItemTodos = new JButton('Adicionar Item')
                  btAdicionarItemTodos.addActionListener(this)
                  pnlComandos.add(btAdicionarItemTodos)
                  btAlterarItemTodos = new JButton('Alterar Selecionado')
                  btAlterarItemTodos.addActionListener(this)
                  pnlComandos.add(btAlterarItemTodos)
                  btExcluirItemTodos = new JButton('Excluir Selecionado')
                  btExcluirItemTodos.addActionListener(this)
                  pnlComandos.add(btExcluirItemTodos)
                  btAtualizarItemTodos = new JButton('Atualizar Tabela')
                  btAtualizarItemTodos.addActionListener(this)
                  pnlComandos.add(btAtualizarItemTodos)
                  btImprimirItensTodos = new JButton('Imprimir Itens')
                  btImprimirItensTodos.addActionListener(this)
                  pnlComandos.add(btImprimirItensTodos)
                  pnlAreaComandos.add(pnlComandos)
                  pnlItem.add(pnlAreaComandos, BorderLayout.EAST)
                  tbpVisoesItens.addTab('Todos', pnlItem)

                  JPanel pnlItemPorCategoria = new JPanel(new BorderLayout())

                  pnlParametro = new JPanel(new FlowLayout(FlowLayout.LEFT))
                  label = new JLabel('Categorias: ')
                  pnlParametro.add(label)
                  cbxCategoria = new JComboBox()
                  categorias = categoria.carregarCategorias(cbxCategoria, aplicacao)
                  cbxCategoria.addActionListener(this)
                  pnlParametro.add(cbxCategoria)
                  label = new JLabel('   Pesquisa:')
                  pnlParametro.add(label)
                  txtPalavraChaveItemPorCategoria = new JTextField(20)
                  txtPalavraChaveItemPorCategoria.addActionListener(this)
                  pnlParametro.add(txtPalavraChaveItemPorCategoria)
                  btPesquisarItensPorCategoria = new JButton('Pesquisar')
                  btPesquisarItensPorCategoria.addActionListener(this)
                  pnlParametro.add(btPesquisarItensPorCategoria)
                  pnlItemPorCategoria.add(pnlParametro, BorderLayout.NORTH)

                  modeloTabelaItensPorCategoria = new ModeloTabelaItens()
                  modeloTabelaItensPorCategoria.definirConexao(aplicacao.obterConexao())
                  tblItensPorCategoria = new JTable(modeloTabelaItensPorCategoria)
                  scroll = new JScrollPane(tblItensPorCategoria)
                  pnlItemPorCategoria.add(scroll, BorderLayout.CENTER)

                  pnlAreaComandos = new JPanel()
                  pnlComandos = new JPanel(new GridLayout(5, 1, 5, 5))
                  btAdicionarItemPorCategoria = new JButton('Adicionar Item')
                  btAdicionarItemPorCategoria.addActionListener(this)
                  pnlComandos.add(btAdicionarItemPorCategoria)
                  btAlterarItemPorCategoria = new JButton('Alterar Selecionado')
                  btAlterarItemPorCategoria.addActionListener(this)
                  pnlComandos.add(btAlterarItemPorCategoria)
                  btExcluirItemPorCategoria = new JButton('Excluir Selecionado')
                  btExcluirItemPorCategoria.addActionListener(this)
                  pnlComandos.add(btExcluirItemPorCategoria)
                  btAtualizarItemPorCategoria = new JButton('Atualizar Tabela')
                  btAtualizarItemPorCategoria.addActionListener(this)
                  pnlComandos.add(btAtualizarItemPorCategoria)
                  btImprimirItensPorCategoria = new JButton('Imprimir Itens')
                  btImprimirItensPorCategoria.addActionListener(this)
                  pnlComandos.add(btImprimirItensPorCategoria)
                  pnlAreaComandos.add(pnlComandos)
                  pnlItemPorCategoria.add(pnlAreaComandos, BorderLayout.EAST)
                  tbpVisoesItens.addTab('Por Categoria', pnlItemPorCategoria)

                  JPanel pnlItensInativos = new JPanel(new BorderLayout())

                  modeloTabelaItensInativos = new ModeloTabelaItens()
                  modeloTabelaItensInativos.definirConexao(aplicacao.obterConexao())
                  tblItensInativos = new JTable(modeloTabelaItensInativos)
                  scroll = new JScrollPane(tblItensInativos)
                  pnlItensInativos.add(scroll, BorderLayout.CENTER)

                  pnlAreaComandos = new JPanel()
                  pnlComandos = new JPanel(new GridLayout(5, 1, 5, 5))
                  btAtualizarItensInativos = new JButton('Atualizar Tabela')
                  btAtualizarItensInativos.addActionListener(this)
                  pnlComandos.add(btAtualizarItensInativos)
                  btImprimirItensInativos = new JButton('Imprimir Itens')
                  btImprimirItensInativos.addActionListener(this)
                  pnlComandos.add(btImprimirItensInativos)
                  pnlAreaComandos.add(pnlComandos)
                  pnlItensInativos.add(pnlAreaComandos, BorderLayout.EAST)
                  tbpVisoesItens.addTab('Inativos', pnlItensInativos)

                  JPanel pnlItemListaCompras = new JPanel(new BorderLayout())

                  modeloTabelaItensListaCompras = new ModeloTabelaItens()
                  modeloTabelaItensListaCompras.definirConexao(aplicacao.obterConexao())
                  tblItensListaCompras = new JTable(modeloTabelaItensListaCompras)
                  scroll = new JScrollPane(tblItensListaCompras)
                  pnlItemListaCompras.add(scroll, BorderLayout.CENTER)

                  pnlAreaComandos = new JPanel()
                  pnlComandos = new JPanel(new GridLayout(5, 1, 5, 5))
                  btAlterarItemListaCompras = new JButton('Alterar Selecionado')
                  btAlterarItemListaCompras.addActionListener(this)
                  pnlComandos.add(btAlterarItemListaCompras)
                  btExcluirItemListaCompras = new JButton('Excluir Selecionado')
                  btExcluirItemListaCompras.addActionListener(this)
                  pnlComandos.add(btExcluirItemListaCompras)
                  btAtualizarItemListaCompras = new JButton('Atualizar Tabela')
                  btAtualizarItemListaCompras.addActionListener(this)
                  pnlComandos.add(btAtualizarItemListaCompras)
                  btImprimirItensListaCompras = new JButton('Imprimir Itens')
                  btImprimirItensListaCompras.addActionListener(this)
                  pnlComandos.add(btImprimirItensListaCompras)
                  pnlAreaComandos.add(pnlComandos)
                  pnlItemListaCompras.add(pnlAreaComandos, BorderLayout.EAST)
                  tbpVisoesItens.addTab('Lista de Compras', pnlItemListaCompras)

                  this.addTab('Insumos', tbpVisoesItens)

                  // Conteúdo da Aba Movimentação
                  pnlMovimentacao = new JPanel(new BorderLayout())
                  JPanel pnlLinhasParametro = new JPanel(new GridLayout(2, 1, 0, 0))
                  pnlParametro = new JPanel(new FlowLayout(FlowLayout.LEFT))
                  label = new JLabel('Tipo de Movimentação:')
                  pnlParametro.add(label)
                  cbxTipoMovimentacao = new JComboBox()
                  cbxTipoMovimentacao.addItem('Selecione...')
                  cbxTipoMovimentacao.addItem('Abastecimento')
                  cbxTipoMovimentacao.addItem('Consumo')
                  cbxTipoMovimentacao.addItem('Vendas')
                  cbxTipoMovimentacao.addItem('Descarte')
                  cbxTipoMovimentacao.addItem('Devolução')
                  cbxTipoMovimentacao.addItem('Devolução Externa')
                  cbxTipoMovimentacao.addItem('Depósito')
                  cbxTipoMovimentacao.addItem('Retirada do Depósito')
                  cbxTipoMovimentacao.addActionListener(this)
                  tiposMovimentacao = new Vector()
                  tiposMovimentacao.addElement(null)
                  tiposMovimentacao.addElement(Movimentacao.ABASTECIMENTO)
                  tiposMovimentacao.addElement(Movimentacao.CONSUMO)
                  tiposMovimentacao.addElement(Movimentacao.VENDAS)
                  tiposMovimentacao.addElement(Movimentacao.DESCARTE)
                  tiposMovimentacao.addElement(Movimentacao.DEVOLUCAO)
                  tiposMovimentacao.addElement(Movimentacao.DEVOLUCAO_EXTERNA)
                  tiposMovimentacao.addElement(Movimentacao.DEPOSITO)
                  tiposMovimentacao.addElement(Movimentacao.RETIRADA_DEPOSITO)
                  pnlParametro.add(cbxTipoMovimentacao)
                  pnlParametro.add(new JLabel(' de '))
                  txtDataInicio = new JTextField(8)
                  txtDataInicio.addActionListener(this)
                  pnlParametro.add(txtDataInicio)
                  pnlParametro.add(new JLabel(' até '))
                  txtDataFinal = new JTextField(8)
                  txtDataFinal.addActionListener(this)
                  pnlParametro.add(txtDataFinal)
                  pnlLinhasParametro.add(pnlParametro)
                  JPanel pnlParametroItem = new JPanel(new FlowLayout(FlowLayout.LEFT))
                  try {
                    itensMovimentacao = new Vector()
                      itensMovimentacao = Item.carregarItens(aplicacao.obterConexao())
                      cbxItemMovimentacao = new JComboBox()
                      cbxItemMovimentacao.addActionListener(this)
                      cbxItemMovimentacao.addItem('Todos')
                      for(int i = 1;i < itensMovimentacao.size();i++) {
                        cbxItemMovimentacao.addItem((Item)itensMovimentacao.get(i))
                      }
                  }
                catch(Exception e) {
                  JOptionPane.showMessageDialog(aplicacao, 'Erro: Não foi possível carregar os itens da movimentação.', 'Erro', JOptionPane.ERROR_MESSAGE)
                    e.printStackTrace()
                }
                finally
                {
                  if(cbxItemMovimentacao == null) {
                    cbxItemMovimentacao = new JComboBox()
                      cbxItemMovimentacao.addItem('Todos')
                  }
                  cbxItemMovimentacao.addActionListener(this)
                }
                pnlParametroItem.add(new JLabel('Itens do Estoque:'))
                  pnlParametroItem.add(cbxItemMovimentacao)
                  btPesquisarMovimentacao = new JButton('Pesquisar')
                  btPesquisarMovimentacao.addActionListener(this)
                  pnlParametroItem.add(btPesquisarMovimentacao)
                  pnlLinhasParametro.add(pnlParametroItem)
                  pnlMovimentacao.add(pnlLinhasParametro, BorderLayout.NORTH)

                  pnlAreaComandos = new JPanel()
                  pnlComandos = new JPanel(new GridLayout(4, 1, 5, 5))
                  btMovimentarItem = new JButton('Movimentar Itens')
                  btMovimentarItem.addActionListener(this)
                  pnlComandos.add(btMovimentarItem)
                  btAtualizarMovimentacao = new JButton('Atualizar Tabela')
                  btAtualizarMovimentacao.addActionListener(this)
                  pnlComandos.add(btAtualizarMovimentacao)
                  btImprimirMovimentacao = new JButton('Imprimir Movimentações')
                  btImprimirMovimentacao.addActionListener(this)
                  pnlComandos.add(btImprimirMovimentacao)
                  pnlAreaComandos.add(pnlComandos)
                  pnlMovimentacao.add(pnlAreaComandos, BorderLayout.EAST)

                  modeloTabelaMovimentacoes = new ModeloTabela()
                  modeloTabelaMovimentacoes.definirConexao(aplicacao.obterConexao())
                  tblMovimentacoes = new JTable(modeloTabelaMovimentacoes)
                  JScrollPane scrollMovimentacoes = new JScrollPane(tblMovimentacoes)
                  pnlMovimentacao.add(scrollMovimentacoes, BorderLayout.CENTER)
                  this.addTab('Movimentação de Itens', pnlMovimentacao)

                  // Conteúdo da Aba Fornecedores
                  pnlFornecedor = new JPanel(new BorderLayout())
                  pnlAreaComandos = new JPanel()
                  pnlComandos = new JPanel(new GridLayout(4, 1, 5, 5))
                  btAdicionarFornecedor = new JButton('Adicionar Fornecedor')
                  btAdicionarFornecedor.addActionListener(this)
                  pnlComandos.add(btAdicionarFornecedor)
                  btAlterarFornecedor = new JButton('Alterar Selecionado')
                  btAlterarFornecedor.addActionListener(this)
                  pnlComandos.add(btAlterarFornecedor)
                  btExcluirFornecedor = new JButton('Excluir Selecionado')
                  btExcluirFornecedor.addActionListener(this)
                  pnlComandos.add(btExcluirFornecedor)
                  btAtualizarFornecedor = new JButton('Atualizar Tabela')
                  btAtualizarFornecedor.addActionListener(this)
                  pnlComandos.add(btAtualizarFornecedor)
                  pnlAreaComandos.add(pnlComandos)
                  pnlFornecedor.add(pnlAreaComandos, BorderLayout.EAST)

                  modeloTabelaFornecedores = new ModeloTabela()
                  modeloTabelaFornecedores.definirConexao(aplicacao.obterConexao())
                  tblFornecedores = new JTable(modeloTabelaFornecedores)
                  JScrollPane scrollFornecedores = new JScrollPane(tblFornecedores)
                  pnlFornecedor.add(scrollFornecedores, BorderLayout.CENTER)
                  this.addTab('Fornecedores', pnlFornecedor)

                  // Conteúdo da Aba Requisição Interna
                  pnlRequisicaoInterna = new JPanel(new BorderLayout())
                  pnlAreaComandos = new JPanel()
                  pnlComandos = new JPanel(new GridLayout(5, 1, 5, 5))
                  btRequisitarItem = new JButton('Requisitar Itens')
                  btRequisitarItem.addActionListener(this)
                  pnlComandos.add(btRequisitarItem)
                  btCancelarRequisicao = new JButton('Cancelar Selecionado')
                  btCancelarRequisicao.addActionListener(this)
                  pnlComandos.add(btCancelarRequisicao)
                  btVisualizarRequisicaoInterna = new JButton('Visualizar Requisição')
                  btVisualizarRequisicaoInterna.addActionListener(this)
                  pnlComandos.add(btVisualizarRequisicaoInterna)
                  btAtualizarRequisicao = new JButton('Atualizar Tabela')
                  btAtualizarRequisicao.addActionListener(this)
                  pnlComandos.add(btAtualizarRequisicao)
                  btImprimirRequisicaoInterna = new JButton('Imprimir Requisição')
                  btImprimirRequisicaoInterna.addActionListener(this)
                  pnlComandos.add(btImprimirRequisicaoInterna)
                  pnlAreaComandos.add(pnlComandos)
                  pnlRequisicaoInterna.add(pnlAreaComandos, BorderLayout.EAST)

                  modeloTabelaRequisicoesInternas = new ModeloTabela()
                  modeloTabelaRequisicoesInternas.definirConexao(aplicacao.obterConexao())
                  tblRequisicoesInternas = new JTable(modeloTabelaRequisicoesInternas)
                  JScrollPane scrollRequisicoesInternas = new JScrollPane(tblRequisicoesInternas)
                  pnlRequisicaoInterna.add(scrollRequisicoesInternas, BorderLayout.CENTER)
                  this.addTab('Requisições Internas', pnlRequisicaoInterna)

                  // Conteúdo da Aba Requisição de Compra
                  JTabbedPane tbpRequisicaoCompra = new JTabbedPane()

                  pnlRequisicaoCompra = new JPanel(new BorderLayout())
                  JPanel pnlAreaParametros = new JPanel(new FlowLayout(FlowLayout.LEFT))
                  try {
                    cbxPedidos = new JComboBox(Pedido.carregarPedidosClientes(aplicacao.obterConexao()))
                      cbxPedidos.addItem('Sem Pedido')
                  }
                catch(Exception e) {
                  JOptionPane.showMessageDialog(aplicacao, 'Erro: Não foi possível carregar os pedidos dos clientes.', 'Erro', JOptionPane.ERROR_MESSAGE)
                    e.printStackTrace()
                }
                finally
                {
                  if(cbxPedidos == null) {
                    cbxPedidos = new JComboBox()
                      cbxPedidos.addItem('Selecione...')
                  }
                  cbxPedidos.addActionListener(this)
                }
                pnlAreaParametros.add(new JLabel('Pedidos: '))
                  pnlAreaParametros.add(cbxPedidos)
                  pnlRequisicaoCompra.add(pnlAreaParametros, BorderLayout.NORTH)

                  pnlAreaComandos = new JPanel()
                  pnlComandos = new JPanel(new GridLayout(5, 1, 5, 5))
                  btEmitirRequisicaoCompra = new JButton('Emitir Requisição')
                  btEmitirRequisicaoCompra.addActionListener(this)
                  pnlComandos.add(btEmitirRequisicaoCompra)
                  btCancelarRequisicaoCompra = new JButton('Cancelar Selecionado')
                  btCancelarRequisicaoCompra.addActionListener(this)
                  pnlComandos.add(btCancelarRequisicaoCompra)
                  btVisualizarRequisicaoCompra = new JButton('Visualizar Requisição')
                  btVisualizarRequisicaoCompra.addActionListener(this)
                  pnlComandos.add(btVisualizarRequisicaoCompra)
                  btAtualizarRequisicaoCompra = new JButton('Atualizar Tabela')
                  btAtualizarRequisicaoCompra.addActionListener(this)
                  pnlComandos.add(btAtualizarRequisicaoCompra)
                  btImprimirRequisicaoCompra = new JButton('Imprimir Requisição')
                  btImprimirRequisicaoCompra.addActionListener(this)
                  pnlComandos.add(btImprimirRequisicaoCompra)
                  pnlAreaComandos.add(pnlComandos)
                  pnlRequisicaoCompra.add(pnlAreaComandos, BorderLayout.EAST)

                  modeloTabelaRequisicoesCompras = new ModeloTabelaRequisicoesCompras()
                  modeloTabelaRequisicoesCompras.definirConexao(aplicacao.obterConexao())
                  tblRequisicoesCompras = new JTable(modeloTabelaRequisicoesCompras)
                  JScrollPane scrollRequisicoesCompras = new JScrollPane(tblRequisicoesCompras)
                  pnlRequisicaoCompra.add(scrollRequisicoesCompras, BorderLayout.CENTER)

                  JPanel pnlAreaRecursos = new JPanel(new BorderLayout())
                  btVisualizarRecursos = new JButton('Visualizar Recursos do Pedido')
                  btVisualizarRecursos.addActionListener(this)
                  pnlAreaRecursos.add(btVisualizarRecursos, BorderLayout.NORTH)
                  dadosRecursos = new String[40][6]
                  String[] titulos = ['Item', 'Necessario', 'Requisitado', '%', 'Disponivel', '%']
                  tblRecursos = new JTable(dadosRecursos, titulos)
                  tblRecursos.setPreferredScrollableViewportSize(new Dimension(460, 100))
                  tblRecursos.addRowSelectionInterval(0, 0)
                  scrollRecursos = new JScrollPane(tblRecursos)
                  scrollRecursos.setVisible(false)
                  pnlAreaRecursos.add(scrollRecursos, BorderLayout.CENTER)
                  pnlRequisicaoCompra.add(pnlAreaRecursos, BorderLayout.SOUTH)

                  tbpRequisicaoCompra.add('Requisições', pnlRequisicaoCompra)

                  JPanel pnlListaPreco = new JPanel(new BorderLayout())
                  pnlAreaParametros = new JPanel(new FlowLayout(FlowLayout.LEFT))
                  pnlAreaParametros.add(new JLabel('Fornecedor:'))
                  cbxFornecedor = new JComboBox()
                  cbxFornecedor.addItem('Todos')
                  try {
                    fornecedores = Fornecedor.carregarFornecedores(aplicacao.obterConexao())
                      for(int i = 1;i < fornecedores.size();i++) {
                        cbxFornecedor.addItem((Fornecedor)fornecedores.get(i))
                      }
                    cbxFornecedor.addActionListener(this)
                  }
                catch(Exception e) {
                  JOptionPane.showMessageDialog(aplicacao, 'Erro: Não foi possível carregar os fornecedores', 'Erro', JOptionPane.ERROR_MESSAGE)
                    e.printStackTrace()
                }
                pnlAreaParametros.add(cbxFornecedor)
                  pnlListaPreco.add(pnlAreaParametros, BorderLayout.NORTH)

                  modeloTabelaListaPreco = new ModeloTabela()
                  modeloTabelaListaPreco.definirConexao(aplicacao.obterConexao())
                  tblListaPrecos = new JTable(modeloTabelaListaPreco)
                  scroll = new JScrollPane(tblListaPrecos)
                  pnlListaPreco.add(scroll, BorderLayout.CENTER)

                  pnlAreaComandos = new JPanel()
                  pnlComandos = new JPanel(new GridLayout(4, 1, 5, 5))
                  btAlterarValor = new JButton('Alterar Valor')
                  btAlterarValor.addActionListener(this)
                  pnlComandos.add(btAlterarValor)
                  pnlAreaComandos.add(pnlComandos)
                  pnlListaPreco.add(pnlAreaComandos, BorderLayout.EAST)

                  tbpRequisicaoCompra.add('Lista de Preços', pnlListaPreco)

                  JPanel pnlFornecedoresPendentes = new JPanel(new BorderLayout())
                  modeloTabelaFornecedoresPendentes = new ModeloTabela()
                  modeloTabelaFornecedoresPendentes.definirConexao(aplicacao.obterConexao())
                  tblFornecedoresPendentes = new JTable(modeloTabelaFornecedoresPendentes)
                  scroll = new JScrollPane(tblFornecedoresPendentes)
                  pnlFornecedoresPendentes.add(scroll, BorderLayout.CENTER)

                  pnlAreaComandos = new JPanel()
                  pnlComandos = new JPanel(new GridLayout(4, 1, 5, 5))
                  btAtualizarFornecedores = new JButton('Atualizar Fornecedores')
                  btAtualizarFornecedores.addActionListener(this)
                  pnlComandos.add(btAtualizarFornecedores)
                  btImprimirFornecedores = new JButton('Imprimir')
                  btImprimirFornecedores.addActionListener(this)
                  pnlComandos.add(btImprimirFornecedores)
                  pnlAreaComandos.add(pnlComandos)
                  pnlFornecedoresPendentes.add(pnlAreaComandos, BorderLayout.EAST)

                  tbpRequisicaoCompra.add('Fornecedores Pendentes', pnlFornecedoresPendentes)

                  this.addTab('Requisições de Compra', tbpRequisicaoCompra)

                  // Conteúdo da Aba Inventário
                  pnlInventario = new JPanel(new BorderLayout())
                  pnlAreaParametros = new JPanel(new FlowLayout(FlowLayout.LEFT))
                  try {
                    itensInventario = Item.carregarItens(aplicacao.obterConexao())
                      cbxItens = new JComboBox()
                      cbxItens.addItem('Todos')
                      for(int i = 1;i < itensInventario.size();i++) {
                        cbxItens.addItem((Item)itensInventario.get(i))
                      }
                  }
                catch(Exception e) {
                  JOptionPane.showMessageDialog(aplicacao, 'Erro: Não foi possível carregar os itens.', 'Erro', JOptionPane.ERROR_MESSAGE)
                    e.printStackTrace()
                }
                finally
                {
                  if(cbxItens == null) {
                    cbxItens = new JComboBox()
                      cbxItens.addItem('Todos')
                  }
                  cbxItens.addActionListener(this)
                }
                pnlAreaParametros.add(new JLabel('Itens: '))
                  pnlAreaParametros.add(cbxItens)
                  pnlAreaParametros.add(new JLabel(' Data: '))
                  txtDataInventario = new JTextField(8)
                  txtDataInventario.addActionListener(this)
                  pnlAreaParametros.add(txtDataInventario)
                  btPesquisarItens = new JButton('Pesquisar')
                  btPesquisarItens.addActionListener(this)
                  pnlAreaParametros.add(btPesquisarItens)
                  pnlInventario.add(pnlAreaParametros, BorderLayout.NORTH)

                  pnlAreaComandos = new JPanel()
                  pnlComandos = new JPanel(new GridLayout(4, 1, 5, 5))
                  btImprimirInventario = new JButton('Imprimir Inventário')
                  btImprimirInventario.addActionListener(this)
                  pnlComandos.add(btImprimirInventario)
                  pnlAreaComandos.add(pnlComandos)
                  pnlInventario.add(pnlAreaComandos, BorderLayout.EAST)

                  int numeroItens = 100
                  String[] titulosTabelaInventario = ['Codigo', 'Descrição', 'Quantidade', 'Valor Unitário', 'Total']
                  try {
                    numeroItens = Item.obterNumeroItensCadastrados(aplicacao.obterConexao())
                  }
                catch(Exception e){}
                String[][] dados = new String[numeroItens][5]
                  tblInventario = new JTable(dados, titulosTabelaInventario)
                  JScrollPane scrollInventario = new JScrollPane(tblInventario)
                  pnlInventario.add(scrollInventario, BorderLayout.CENTER)

                  this.addTab('Inventário', pnlInventario)
              }

  private void atualizarTabelaRequisicaoCompra() {
    String sql = 'select requisicao_compra.codigo as 'código', fornecedor.razao_social as 'razão social', departamento.departamento as 'departamento solicitante', requisicao_compra.data_emissao as 'data de emissão', requisicao_compra.data_limite_entrega as 'limite de entrega', requisicao_compra.data_recebimento as 'data de recebimento', (case requisicao_compra.status when 'EM' then 'Emitido' when 'CL' then 'Cancelado' when 'CO' then 'Confirmado' when 'PD' then 'Pendente' end) as 'status' from requisicao_compra, fornecedor, departamento where requisicao_compra.fornecedor = fornecedor.codigo and requisicao_compra.departamento_solicitante = departamento.codigo order by requisicao_compra.data_emissao desc'
      modeloTabelaRequisicoesCompras.definirConsulta(sql)
      tblRequisicoesCompras.setModel(modeloTabelaRequisicoesCompras)
      tblRequisicoesCompras.updateUI()
  }

  private void atualizarTabelaItem(char tabela, String palavraChave) {
    String query = ''
      String restricao = ''
      if(!chbMostrarTodos.isSelected()) {
        restricao = 'and i.ativo = 1'
      }
    if(''.equals(palavraChave)) {
      if(cbxCategoria.getSelectedIndex() > 0) {
        Categoria categoriaSelecionada = (Categoria)categorias.get(cbxCategoria.getSelectedIndex())
          query = 'select i.codigo, i.descricao, c.categoria as categoria, u.unidade as unidade, i.quantidade, i.quantidade_minima, i.quantidade_maxima from item i, categoria_item c, unidade u where i.categoria = c.codigo and i.unidade = u.codigo '+ restricao +' and i.categoria = ''+ ((categoriaSelecionada == null)?0:categoriaSelecionada.obterCodigo()) +'' order by i.descricao, ci.categoria'
      }
      else
      {
        query = 'select i.codigo, i.descricao, c.categoria as categoria, u.unidade as unidade, i.quantidade, i.quantidade_minima, i.quantidade_maxima from item i, categoria_item c, unidade u where i.categoria = c.codigo and i.unidade = u.codigo '+ restricao +' order by i.descricao, ci.categoria'
      }
    }
    else
    {
      query = 'select i.codigo, i.descricao, c.categoria as categoria, u.unidade as unidade, i.quantidade, i.quantidade_minima, i.quantidade_maxima from item i, categoria_item c, unidade u where i.categoria = c.codigo and i.unidade = u.codigo '+ restricao +' and i.descricao like '%'+ palavraChave +'%' order by i.descricao, ci.categoria'
    }
    switch(tabela) {
      case 'T':
        modeloTabelaItensTodos.definirConsulta(query)
          tblItensTodos.setModel(modeloTabelaItensTodos)
          tblItensTodos.updateUI()
          break
      case 'C':
          modeloTabelaItensPorCategoria.definirConsulta(query)
            tblItensPorCategoria.setModel(modeloTabelaItensPorCategoria)
            tblItensPorCategoria.updateUI()
            break
      case 'L':
            modeloTabelaItensListaCompras.definirConsulta('select distinct i.codigo, i.descricao, ci.codigo as codigo_categoria, ci.categoria as categoria, u.codigo as codigo_unidade, u.unidade as unidade, i.quantidade, i.quantidade_minima, i.quantidade_maxima, i.percentual_ipi, i.independente from item i, categoria_item ci, unidade u, fornecedor_item fi, fornecedor f where i.independente = 1 and i.quantidade_minima > i.quantidade and i.unidade = u.codigo and ci.codigo = i.categoria and fi.item = i.codigo and fi.fornecedor = f.codigo order by i.descricao, ci.categoria')
              tblItensListaCompras.setModel(modeloTabelaItensListaCompras)
              tblItensListaCompras.updateUI()
              break
      case 'I':
              modeloTabelaItensInativos.definirConsulta('select i.codigo, i.descricao, c.categoria as categoria, u.unidade as unidade, i.quantidade, i.quantidade_minima, i.quantidade_maxima from item i, categoria_item c, unidade u where i.categoria = c.codigo and i.unidade = u.codigo and i.ativo = 0 order by i.descricao, ci.categoria')
                tblItensInativos.setModel(modeloTabelaItensInativos)
                tblItensInativos.updateUI()
                break
    }
  }

  private void atualizarTabelaMovimentacao(String tipo) {
    if(modeloTabelaMovimentacoes != null) {
      String sql = 'select mi.codigo as 'código', (case tipo_movimento when 'AB' then 'Abastecimento' when 'CS' then 'Consumo' when 'VD' then 'Vendas' when 'DS' then 'Descarte' when 'DV' then 'Devolução' when 'DE' then 'Devolução Externa' when 'DP' then 'Depósito' when 'RD' then 'Retirada do Depósito' end) as 'tipo de movimento', i.descricao as 'item', mi.quantidade, data_hora as 'data', data_recebimento as 'recebimento', u.nome_completo as 'responsável', mi.nota_fiscal as 'Nota Fiscal' from movimentacao_item mi, item i, usuario u where mi.item = i.codigo and mi.responsavel = u.usuario '
        if(tipo != null)
          sql += 'and tipo_movimento = ''+ tipo +'' '
            if(!txtDataInicio.getText().equals(''))
              sql += 'and mi.data_hora >= ''+ Calendario.inverterFormato(txtDataInicio.getText(), '/') +' 00:00:00.000' '
                if(!txtDataFinal.getText().equals(''))
                  sql += 'and mi.data_hora <= ''+ Calendario.inverterFormato(txtDataFinal.getText(), '/') +' 23:59:59.999' '
                    if(cbxItemMovimentacao.getSelectedIndex() > 0)
                      sql += 'and i.codigo = ' + ((Item)itensMovimentacao.get(cbxItemMovimentacao.getSelectedIndex())).obterCodigo()
                        sql += ' order by tipo_movimento'
                        modeloTabelaMovimentacoes.definirConsulta(sql)
                        tblMovimentacoes.setModel(modeloTabelaMovimentacoes)
                        tblMovimentacoes.updateUI()
    }
  }

  private void atualizarTabelaFornecedor() {
    modeloTabelaFornecedores.definirConsulta('select codigo as código, razao_social as 'razão social', cnpj, ('(' + rtrim(ddd) + ') ' + telefone) as telefone, fax, email as 'e-mail' from fornecedor order by razao_social asc')
      tblFornecedores.setModel(modeloTabelaFornecedores)
      tblFornecedores.updateUI()
  }

  private void atualizarTabelaRequisicaoInterna() {
    String sql = 'select ri.codigo as 'código', (case tipo_solicitacao when 'CS' then 'Consumo' when 'VD' then 'Vendas' when 'DS' then 'Descarte' when 'DV' then 'Devolução' when 'DE' then 'Devolução Externa' end) as 'tipo de solicitação', datahora as 'data da requisição', datahora_limite_entrega as 'limite de entrega', d.departamento, (case status when 'EM' then 'Emitido' when 'CL' then 'Cancelado' when 'PD' then 'Pendente' when 'CO' then 'Confirmado' end) as 'status' from requisicao_interna ri, departamento d where ri.departamento = d.codigo order by datahora desc'
      modeloTabelaRequisicoesInternas.definirConsulta(sql)
      tblRequisicoesInternas.setModel(modeloTabelaRequisicoesInternas)
      tblRequisicoesInternas.updateUI()
  }

  private void atualizarTabelaPrecos() {
    String query = 'select f.codigo as codigo_fornecedor, f.razao_social, i.codigo as codigo_item, i.descricao, fi.valor_item from fornecedor f, fornecedor_item fi, item i where f.codigo = fi.fornecedor and i.codigo = fi.item'
      if(cbxFornecedor.getSelectedIndex() > 0)
        query += ' and fi.fornecedor = '+ ((Fornecedor)fornecedores.get(cbxFornecedor.getSelectedIndex())).obterCodigo()
          try {
            ResultSet rsFornecedoresItem = aplicacao.obterConexao().executarConsulta(query)
              fornecedoresItem = null
              fornecedoresItem = new Vector()
              while(rsFornecedoresItem.next()) {
                fornecedoresItem.addElement(new FornecedorItem(new Fornecedor(rsFornecedoresItem.getInt('codigo_fornecedor'), rsFornecedoresItem.getString('razao_social')),
                      new Item(rsFornecedoresItem.getInt('codigo_item'), rsFornecedoresItem.getString('descricao')), rsFornecedoresItem.getFloat('valor_item')))
              }
            rsFornecedoresItem.close()
          }
    catch(Exception e) {
      JOptionPane.showMessageDialog(aplicacao, 'Erro: Não foi possível carregar os preços dos insumos.', 'Erro', JOptionPane.ERROR_MESSAGE)
        e.printStackTrace()
    }
    modeloTabelaListaPreco.definirConsulta(query)
      tblListaPrecos.setModel(modeloTabelaListaPreco)
      tblListaPrecos.updateUI()
  }

  void actionPerformed(java.awt.event.ActionEvent actionEvent) {
    Object objeto = actionEvent.getSource()

      if(objeto == txtPalavraChaveItemTodos || objeto == btPesquisarItensTodos) {
        atualizarTabelaItem('T', this.txtPalavraChaveItemTodos.getText())
      }

    if(objeto == txtPalavraChaveItemPorCategoria || objeto == btPesquisarItensPorCategoria) {
      cbxCategoria.setSelectedIndex(0)
        atualizarTabelaItem('C', this.txtPalavraChaveItemPorCategoria.getText())
    }

    if(objeto == txtDataInventario || objeto == btPesquisarItens) {
      String query
        for(int i = 0;i < tblInventario.getRowCount();i++) {
          tblInventario.setValueAt('', i, 0)
            tblInventario.setValueAt('', i, 1)
            tblInventario.setValueAt('', i, 2)
        }
      try {
        if(cbxItens.getSelectedIndex() == 0) {
          if(!txtDataInventario.getText().equals('')) {
            query = 'select codigo from item order by descricao'
              ResultSet rsItens = aplicacao.obterConexao().executarConsulta(query)
              int linha = 0
              while(rsItens.next()) {
                int codigoItem = rsItens.getInt('codigo')
                  query = 'select i.codigo, i.descricao, hqi.quantidade , hvi.valor_item, (hqi.quantidade * hvi.valor_item) as total from historico_quantidade_item hqi, item i, historico_valor_item hvi ' +
                  'where i.codigo = hqi.codigo and hvi.item = i.codigo and i.codigo = '+ codigoItem +' and hqi.data_hora <= ''+ Calendario.inverterFormato(this.txtDataInventario.getText(), '/') +' 23:59:59.999' and ' +
                  'hqi.data_hora = (select max(data_hora) from historico_quantidade_item where data_hora <= ''+ Calendario.inverterFormato(this.txtDataInventario.getText(), '/') +' 23:59:59.999' and codigo = '+ codigoItem +') and hvi.data_atualizacao <= ''+ Calendario.inverterFormato(this.txtDataInventario.getText(), '/') +' 23:59:59.999' and ' +
                  'hvi.data_atualizacao = (select max(data_atualizacao) from historico_valor_item where data_atualizacao <= ''+ Calendario.inverterFormato(this.txtDataInventario.getText(), '/') +' 23:59:59.999' and item = '+ codigoItem +')'
                  ResultSet rsItensInventario = aplicacao.obterConexao().executarConsulta(query)
                  if(rsItensInventario.next()) {
                    tblInventario.setValueAt(rsItensInventario.getString('codigo'), linha, 0)
                      tblInventario.setValueAt(rsItensInventario.getString('descricao'), linha, 1)
                      tblInventario.setValueAt(rsItensInventario.getString('quantidade'), linha, 2)
                      tblInventario.setValueAt(rsItensInventario.getString('valor_item'), linha, 3)
                      tblInventario.setValueAt(rsItensInventario.getString('total'), linha, 4)
                      linha++
                  }
                rsItensInventario.close()
              }
            rsItens.close()
          }
        }
        else
        {
          if(!txtDataInventario.getText().equals('')) {
            query = 'select i.codigo, i.descricao, hqi.quantidade from historico_quantidade_item hqi, item i ' +
              'where i.codigo = hqi.codigo and i.codigo = '+ ((Item)itensInventario.get(cbxItens.getSelectedIndex())).obterCodigo() +' and hqi.data_hora <= ''+ Calendario.inverterFormato(this.txtDataInventario.getText(), '/') +' 23:59:59.999' and ' +
              'hqi.data_hora = (select max(data_hora) from historico_quantidade_item where data_hora <= ''+ Calendario.inverterFormato(this.txtDataInventario.getText(), '/') +' 23:59:59.999' and codigo = '+ ((Item)itensInventario.get(cbxItens.getSelectedIndex())).obterCodigo() +')'
              ResultSet rsItensInventario = aplicacao.obterConexao().executarConsulta(query)
              int linha = 0
              if(rsItensInventario.next()) {
                tblInventario.setValueAt(rsItensInventario.getString('codigo'), linha, 0)
                  tblInventario.setValueAt(rsItensInventario.getString('descricao'), linha, 1)
                  tblInventario.setValueAt(rsItensInventario.getString('quantidade'), linha, 2)
                  linha++
              }
            rsItensInventario.close()
          }
        }
      }
      catch(Exception e){}
    }

    if(objeto == btImprimirInventario) {
      try {
        RelatorioInventario relInventario = new RelatorioInventario(aplicacao.obterConexao(), txtDataInventario.getText())
          Vector paginas = relInventario.paginar(aplicacao.obterFormatoPagina())
          Impressora impressora = new Impressora()
          impressora.addPaginas(paginas, aplicacao.obterFormatoPagina())
          impressora.imprimir()
      }
      catch(Exception e) {
        e.printStackTrace()
      }
    }

    if(objeto == btImprimirMovimentacao) {
      try {
        RelatorioMovimentacao relMovimentacao = new RelatorioMovimentacao(aplicacao.obterConexao(), (String)tiposMovimentacao.get(cbxTipoMovimentacao.getSelectedIndex()), ((cbxItemMovimentacao.getSelectedIndex() > 0)?((Item)itensMovimentacao.get(cbxItemMovimentacao.getSelectedIndex())).obterCodigo():0), txtDataInicio.getText(), txtDataFinal.getText())
          Vector paginas = relMovimentacao.paginar(aplicacao.obterFormatoPagina())
          Impressora impressora = new Impressora()
          impressora.addPaginas(paginas, aplicacao.obterFormatoPagina())
          impressora.imprimir()
      }
      catch(Exception e) {
        e.printStackTrace()
      }
    }

    if(objeto == btAdicionarItemTodos || objeto == btAdicionarItemPorCategoria) {
      DlgDadosItem dlgDadosItem = new DlgDadosItem(aplicacao)
        dlgDadosItem.setVisible(true)
    }

    if(objeto == btAlterarItemTodos) {
      if(this.tblItensTodos.getSelectedRow() >= 0) {
        int linha = this.tblItensTodos.getSelectedRow()
          int codigoItem = Integer.parseInt((String)this.tblItensTodos.getValueAt(linha, 0))
          DlgDadosItem dlgDadosItem = new DlgDadosItem(aplicacao, codigoItem)
          dlgDadosItem.setVisible(true)
      }
      else
      {
        JOptionPane.showMessageDialog(aplicacao, 'Atenção: Selecione uma linha da visão para alterar os dados do item.', 'Atenção', JOptionPane.WARNING_MESSAGE)
      }
    }

    if(objeto == btAlterarItemPorCategoria) {
      if(this.tblItensPorCategoria.getSelectedRow() >= 0) {
        int linha = this.tblItensPorCategoria.getSelectedRow()
          int codigoItem = Integer.parseInt((String)this.tblItensPorCategoria.getValueAt(linha, 0))
          DlgDadosItem dlgDadosItem = new DlgDadosItem(aplicacao, codigoItem)
          dlgDadosItem.setVisible(true)
      }
      else
      {
        JOptionPane.showMessageDialog(aplicacao, 'Atenção: Selecione uma linha da visão para alterar os dados do item.', 'Atenção', JOptionPane.WARNING_MESSAGE)
      }
    }

    if(objeto == btAlterarItemListaCompras) {
      if(this.tblItensListaCompras.getSelectedRow() >= 0) {
        int linha = this.tblItensListaCompras.getSelectedRow()
          int codigoItem = Integer.parseInt((String)this.tblItensListaCompras.getValueAt(linha, 0))
          DlgDadosItem dlgDadosItem = new DlgDadosItem(aplicacao, codigoItem)
          dlgDadosItem.setVisible(true)
      }
      else
      {
        JOptionPane.showMessageDialog(aplicacao, 'Atenção: Selecione uma linha da visão para alterar os dados do item.', 'Atenção', JOptionPane.WARNING_MESSAGE)
      }
    }

    if(objeto == btExcluirItemTodos) {
      if(tblItensTodos.getSelectedRow() >=0) {
        if(JOptionPane.showConfirmDialog(aplicacao, 'Atenção: Tem certeza que deseja excluir o item selecionado?', 'Atenção', JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
          int linha = tblItensTodos.getSelectedRow()
            int codigoItem = Integer.parseInt((String)tblItensTodos.getValueAt(linha, 0))
            item = new Item()
            try {
              item.excluirFornecedorItem(codigoItem)
                item.excluirDepartamentoItem(codigoItem)
                item.excluirItem(codigoItem)
                atualizarTabelaItem('T', '')
            }
          catch(Exception e) {
            e.printStackTrace()
          }
        }
      }
      else
      {
        JOptionPane.showMessageDialog(aplicacao, 'Atenção: Selecione uma linha da visão para excluir um item.', 'Atenção', JOptionPane.WARNING_MESSAGE)
      }
    }

    if(objeto == btExcluirItemPorCategoria) {
      if(tblItensPorCategoria.getSelectedRow() >=0) {
        if(JOptionPane.showConfirmDialog(aplicacao, 'Atenção: Tem certeza que deseja excluir o item selecionado?', 'Atenção', JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
          int linha = tblItensPorCategoria.getSelectedRow()
            int codigoItem = Integer.parseInt((String)tblItensPorCategoria.getValueAt(linha, 0))
            item = new Item()
            try {
              item.excluirFornecedorItem(codigoItem)
                item.excluirDepartamentoItem(codigoItem)
                item.excluirItem(codigoItem)
                atualizarTabelaItem('C', '')
            }
          catch(Exception e) {
            e.printStackTrace()
          }
        }
      }
      else
      {
        JOptionPane.showMessageDialog(aplicacao, 'Atenção: Selecione uma linha da visão para excluir um item.', 'Atenção', JOptionPane.WARNING_MESSAGE)
      }
    }

    if(objeto == btExcluirItemListaCompras) {
      if(tblItensListaCompras.getSelectedRow() >=0) {
        if(JOptionPane.showConfirmDialog(aplicacao, 'Atenção: Tem certeza que deseja excluir o item selecionado?', 'Atenção', JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
          int linha = tblItensListaCompras.getSelectedRow()
            int codigoItem = Integer.parseInt((String)tblItensListaCompras.getValueAt(linha, 0))
            item = new Item()
            try {
              item.excluirFornecedorItem(codigoItem)
                item.excluirDepartamentoItem(codigoItem)
                item.excluirItem(codigoItem)
                atualizarTabelaItem('L', '')
            }
          catch(Exception e) {
            e.printStackTrace()
          }
        }
      }
      else
      {
        JOptionPane.showMessageDialog(aplicacao, 'Atenção: Selecione uma linha da visão para excluir um item.', 'Atenção', JOptionPane.WARNING_MESSAGE)
      }
    }

    if(objeto == btAtualizarItemTodos) {
      atualizarTabelaItem('T', '')
    }

    if(objeto == btAtualizarItemPorCategoria) {
      atualizarTabelaItem('C', '')
    }

    if(objeto == btAtualizarItensInativos) {
      atualizarTabelaItem('I', '')
    }

    if(objeto == btAtualizarItemListaCompras) {
      atualizarTabelaItem('L', '')
    }

    if(objeto == btImprimirItensTodos) {
      if(tblItensTodos.getSelectedRow() >=0) {
        try {
          int[] linhas = tblItensTodos.getSelectedRows()
            int[] codigosItem = new int[linhas.length]
            for(int i = 0;i < linhas.length;i++) {
              codigosItem[i] = Integer.parseInt((String)tblItensTodos.getValueAt(linhas[i], 0))
            }
          Vector itens = Item.carregarItensSelecionados(codigosItem, aplicacao.obterConexao())
            RelatorioItens relItens = new RelatorioItens(itens, 'RELATÓRIO DE ITENS DO ESTOQUE')
            Vector paginas = relItens.paginar(aplicacao.obterFormatoPagina())
            Impressora impressora = new Impressora()
            impressora.addPaginas(paginas, aplicacao.obterFormatoPagina())
            impressora.imprimir()
        }
        catch(Exception e) {
          JOptionPane.showMessageDialog(aplicacao, 'Erro: Não foi possível carregar os itens para impressão.', 'Erro', JOptionPane.ERROR_MESSAGE)
            e.printStackTrace()
        }
      }
      else
      {
        try {
          Vector itens = Item.carregarItensExistentes(aplicacao.obterConexao())
            RelatorioItens relItens = new RelatorioItens(itens, 'RELATÓRIO DE ITENS DO ESTOQUE')
            Vector paginas = relItens.paginar(aplicacao.obterFormatoPagina())
            Impressora impressora = new Impressora()
            impressora.addPaginas(paginas, aplicacao.obterFormatoPagina())
            impressora.imprimir()
        }
        catch(Exception e) {
          JOptionPane.showMessageDialog(aplicacao, 'Erro: Não foi possível carregar os itens para impressão.', 'Erro', JOptionPane.ERROR_MESSAGE)
            e.printStackTrace()
        }
      }
    }

    if(objeto == btImprimirItensPorCategoria) {
      if(tblItensPorCategoria.getSelectedRow() >=0) {
        try {
          int[] linhas = tblItensPorCategoria.getSelectedRows()
            int[] codigosItem = new int[linhas.length]
            for(int i = 0;i < linhas.length;i++) {
              codigosItem[i] = Integer.parseInt((String)tblItensPorCategoria.getValueAt(linhas[i], 0))
            }
          Vector itens = Item.carregarItensSelecionados(codigosItem, aplicacao.obterConexao())
            RelatorioItens relItens = new RelatorioItens(itens, 'RELATÓRIO DE ITENS DO ESTOQUE')
            Vector paginas = relItens.paginar(aplicacao.obterFormatoPagina())
            Impressora impressora = new Impressora()
            impressora.addPaginas(paginas, aplicacao.obterFormatoPagina())
            impressora.imprimir()
        }
        catch(Exception e) {
          JOptionPane.showMessageDialog(aplicacao, 'Erro: Não foi possível carregar os itens para impressão.', 'Erro', JOptionPane.ERROR_MESSAGE)
            e.printStackTrace()
        }
      }
      else
      {
        try {
          Vector itens = Item.carregarItensExistentes(aplicacao.obterConexao())
            RelatorioItens relItens = new RelatorioItens(itens, 'RELATÓRIO DE ITENS DO ESTOQUE')
            Vector paginas = relItens.paginar(aplicacao.obterFormatoPagina())
            Impressora impressora = new Impressora()
            impressora.addPaginas(paginas, aplicacao.obterFormatoPagina())
            impressora.imprimir()
        }
        catch(Exception e) {
          JOptionPane.showMessageDialog(aplicacao, 'Erro: Não foi possível carregar os itens para impressão.', 'Erro', JOptionPane.ERROR_MESSAGE)
            e.printStackTrace()
        }
      }
    }

    if(objeto == btImprimirItensInativos) {
      try {
        Vector itens = Item.carregarItensInativos(aplicacao.obterConexao())
          RelatorioItens relItens = new RelatorioItens(itens, 'ITENS INATIVOS DO ESTOQUE')
          Vector paginas = relItens.paginar(aplicacao.obterFormatoPagina())
          Impressora impressora = new Impressora()
          impressora.addPaginas(paginas, aplicacao.obterFormatoPagina())
          impressora.imprimir()
      }
      catch(Exception e) {
        JOptionPane.showMessageDialog(aplicacao, 'Erro: Não foi possível carregar os itens para impressão.', 'Erro', JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
    }

    if(objeto == btImprimirItensListaCompras) {
      if(tblItensListaCompras.getSelectedRow() >=0) {
        try {
          int[] linhas = tblItensListaCompras.getSelectedRows()
            int[] codigosItem = new int[linhas.length]
            for(int i = 0;i < linhas.length;i++) {
              codigosItem[i] = Integer.parseInt((String)tblItensListaCompras.getValueAt(linhas[i], 0))
            }
          Vector itens = Item.carregarItensSelecionados(codigosItem, aplicacao.obterConexao())
            RelatorioItens relItens = new RelatorioItens(itens, 'LISTA DE COMPRAS')
            Vector paginas = relItens.paginar(aplicacao.obterFormatoPagina())
            Impressora impressora = new Impressora()
            impressora.addPaginas(paginas, aplicacao.obterFormatoPagina())
            impressora.imprimir()
        }
        catch(Exception e) {
          JOptionPane.showMessageDialog(aplicacao, 'Erro: Não foi possível carregar os itens para impressão.', 'Erro', JOptionPane.ERROR_MESSAGE)
            e.printStackTrace()
        }
      }
      else
      {
        try {
          Vector itens = Item.carregarItensIndependentesEmFalta(aplicacao.obterConexao())
            RelatorioItens relItens = new RelatorioItens(itens, 'LISTA DE COMPRAS')
            Vector paginas = relItens.paginar(aplicacao.obterFormatoPagina())
            Impressora impressora = new Impressora()
            impressora.addPaginas(paginas, aplicacao.obterFormatoPagina())
            impressora.imprimir()
        }
        catch(Exception e) {
          JOptionPane.showMessageDialog(aplicacao, 'Erro: Não foi possível carregar os itens para impressão.', 'Erro', JOptionPane.ERROR_MESSAGE)
            e.printStackTrace()
        }
      }
    }

    if(objeto == btMovimentarItem) {
      DlgDadosMovimentacao dlgDadosMovimentacao = new DlgDadosMovimentacao(aplicacao)
        dlgDadosMovimentacao.setVisible(true)
    }

    if(objeto == btAtualizarMovimentacao || objeto == cbxTipoMovimentacao ||
        objeto == btPesquisarMovimentacao || objeto == txtDataInicio ||
        objeto == txtDataFinal || objeto == cbxItemMovimentacao) {
      atualizarTabelaMovimentacao((String)tiposMovimentacao.get(cbxTipoMovimentacao.getSelectedIndex()))
    }

    if(objeto == btAdicionarFornecedor) {
      DlgDadosFornecedor dlgDadosFornecedor = new DlgDadosFornecedor(aplicacao, 'I')
        dlgDadosFornecedor.setVisible(true)
    }

    if(objeto == btAlterarFornecedor) {
      if(tblFornecedores.getSelectedRow() >=0) {
        int linha = tblFornecedores.getSelectedRow()
          int codigoFornecedor = Integer.parseInt((String)tblFornecedores.getValueAt(linha, 0))
          DlgDadosFornecedor dlgDadosFornecedor = new DlgDadosFornecedor(aplicacao, 'A', codigoFornecedor)
          dlgDadosFornecedor.setVisible(true)
      }
      else
      {
        JOptionPane.showMessageDialog(aplicacao, 'Atenção: Selecione uma linha da visão para alterar os dados do fornecedor.', 'Atenção', JOptionPane.WARNING_MESSAGE)
      }
    }

    if(objeto == btExcluirFornecedor) {
      if(tblFornecedores.getSelectedRow() >=0) {
        if(JOptionPane.showConfirmDialog(aplicacao, 'Atenção: Tem certeza que deseja excluir o fornecedor selecionado?', 'Atenção', JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
          int linha = tblFornecedores.getSelectedRow()
            int codigoFornecedor = Integer.parseInt((String)tblFornecedores.getValueAt(linha, 0))
            fornecedor = new Fornecedor()
            try {
              fornecedor.excluirFornecedor(codigoFornecedor)
                atualizarTabelaFornecedor()
            }
          catch(Exception e) {
            JOptionPane.showMessageDialog(aplicacao, 'Erro: '+e.getMessage(), 'Erro', JOptionPane.ERROR_MESSAGE)
              e.printStackTrace()
          }
        }
      }
      else
      {
        JOptionPane.showMessageDialog(aplicacao, 'Atenção: Selecione uma linha da visão para excluir um fornecedor.', 'Atenção', JOptionPane.WARNING_MESSAGE)
      }
    }

    if(objeto == btAtualizarFornecedor) {
      atualizarTabelaFornecedor()
    }

    if(objeto == cbxPedidos) {
      if(btVisualizarRecursos.getText().equals('Ocultar Recursos do Pedido')) {
        // Limpa a tabela.
        for(int i = 0;i < 40;i++) {
          for(int j = 0;j < 6;j++) {
            tblRecursos.setValueAt('', i, j)
          }
        }
        if(cbxPedidos.getSelectedIndex() > 0 && cbxPedidos.getSelectedItem() instanceof Pedido) {
          try {
            dadosRecursos = Pedido.carregarRecursosPedido(aplicacao.obterConexao(), (Pedido)cbxPedidos.getSelectedItem())
              for(int i = 0;i < 40;i++) {
                for(int j = 0;j < 6;j++) {
                  tblRecursos.setValueAt('' + (dadosRecursos[i][j] == null?'':dadosRecursos[i][j]), i, j)
                }
              }
          }
          catch(Exception e) {
            JOptionPane.showMessageDialog(aplicacao, 'Erro: ' + e.getMessage(), 'Erro', JOptionPane.ERROR_MESSAGE)
              e.printStackTrace()
          }
        }
      }
    }

    if(objeto == btEmitirRequisicaoCompra) {
      DlgDadosRequisicaoCompra dlgDadosRequisicaoCompra = new DlgDadosRequisicaoCompra(aplicacao)
        dlgDadosRequisicaoCompra.setVisible(true)
    }

    if(objeto == this.btCancelarRequisicaoCompra) {
      if(this.tblRequisicoesCompras.getSelectedRow() >=0) {
        if(JOptionPane.showConfirmDialog(aplicacao, 'Atenção: Tem certeza que deseja cancelar a requisição de compra selecionada?', 'Atenção', JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
          int linha = this.tblRequisicoesCompras.getSelectedRow()
            int codigo = Integer.parseInt((String)this.tblRequisicoesCompras.getValueAt(linha, 0))
            RequisicaoCompra requisicaoCompra = new RequisicaoCompra(codigo)
            try {
              requisicaoCompra.cancelarRequisicaoCompra()
            }
          catch(Exception e) {
            e.printStackTrace()
          }
        }
      }
      else
      {
        JOptionPane.showMessageDialog(aplicacao, 'Atenção: Selecione uma linha da visão para cancelar uma requisição de compra.', 'Atenção', JOptionPane.WARNING_MESSAGE)
      }
    }

    if(objeto == this.btAtualizarRequisicaoCompra) {
      atualizarTabelaRequisicaoCompra()
    }

    if(objeto == this.btImprimirRequisicaoInterna) {
      if(tblRequisicoesInternas.getSelectedRow() >= 0) {
        try {
          int codigo = Integer.parseInt((String)tblRequisicoesInternas.getValueAt(tblRequisicoesInternas.getSelectedRow(), 0))
            RequisicaoInterna requisicaoInterna = new RequisicaoInterna(codigo, aplicacao.obterConexao())
            RelatorioRequisicaoInterna relRequisicaoInterna = new RelatorioRequisicaoInterna(requisicaoInterna)
            Vector paginas = relRequisicaoInterna.paginar(aplicacao.obterFormatoPagina())
            Impressora impressora = new Impressora()
            impressora.addPaginas(paginas, aplicacao.obterFormatoPagina())
            impressora.imprimir()
        }
        catch(Exception e) {
          JOptionPane.showMessageDialog(aplicacao, 'Erro: Não foi possível imprimir a requisição interna.', 'Erro', JOptionPane.ERROR_MESSAGE)
            e.printStackTrace()
        }
      }
      else
      {
        JOptionPane.showMessageDialog(aplicacao, 'Atenção: Selecione uma linha da visão para imprimir uma requisição interna.', 'Atenção', JOptionPane.WARNING_MESSAGE)
      }
    }

    if(objeto == this.btVisualizarRequisicaoInterna) {
      if(tblRequisicoesInternas.getSelectedRow() >= 0) {
        try {
          int codigo = Integer.parseInt((String)tblRequisicoesInternas.getValueAt(tblRequisicoesInternas.getSelectedRow(), 0))
            RequisicaoInterna requisicaoInterna = new RequisicaoInterna(codigo, aplicacao.obterConexao())
            DlgDadosRequisicaoInterna dlgDadosRequisicaoInterna = new DlgDadosRequisicaoInterna(aplicacao, requisicaoInterna)
            dlgDadosRequisicaoInterna.setVisible(true)
        }
        catch(Exception e) {
          JOptionPane.showMessageDialog(aplicacao, 'Erro: Não foi possível abrir a requisição interna.', 'Erro', JOptionPane.ERROR_MESSAGE)
            e.printStackTrace()
        }
      }
      else
      {
        JOptionPane.showMessageDialog(aplicacao, 'Atenção: Selecione uma linha da visão para imprimir uma requisição interna.', 'Atenção', JOptionPane.WARNING_MESSAGE)
      }
    }

    if(objeto == this.btImprimirRequisicaoCompra) {
      if(tblRequisicoesCompras.getSelectedRow() >= 0) {
        try {
          int codigo = Integer.parseInt((String)tblRequisicoesCompras.getValueAt(tblRequisicoesCompras.getSelectedRow(), 0))
            RequisicaoCompra requisicaoCompra = new RequisicaoCompra(codigo, aplicacao.obterConexao())
            RelatorioRequisicaoCompra relRequisicaoCompra = new RelatorioRequisicaoCompra(requisicaoCompra, 'CÓPIA')
            Vector paginas = relRequisicaoCompra.paginar(aplicacao.obterFormatoPagina())
            Impressora impressora = new Impressora()
            impressora.addPaginas(paginas, aplicacao.obterFormatoPagina())
            impressora.imprimir()
        }
        catch(Exception e) {
          JOptionPane.showMessageDialog(aplicacao, 'Erro: Não foi possível imprimir a requisição de compra.', 'Erro', JOptionPane.ERROR_MESSAGE)
            e.printStackTrace()
        }
      }
      else
      {
        JOptionPane.showMessageDialog(aplicacao, 'Atenção: Selecione uma linha da visão para imprimir uma requisição interna.', 'Atenção', JOptionPane.WARNING_MESSAGE)
      }
    }

    if(objeto == this.btVisualizarRequisicaoCompra) {
      if(tblRequisicoesCompras.getSelectedRow() >= 0) {
        try {
          int codigo = Integer.parseInt((String)tblRequisicoesCompras.getValueAt(tblRequisicoesCompras.getSelectedRow(), 0))
            RequisicaoCompra requisicaoCompra = new RequisicaoCompra(codigo, aplicacao.obterConexao())
            DlgDadosRequisicaoCompra dlgDadosRequisicaoCompra = new DlgDadosRequisicaoCompra(aplicacao, requisicaoCompra)
            dlgDadosRequisicaoCompra.setVisible(true)
        }
        catch(Exception e) {
          JOptionPane.showMessageDialog(aplicacao, 'Erro: Não foi possível abrir a requisição de compra.', 'Erro', JOptionPane.ERROR_MESSAGE)
            e.printStackTrace()
        }
      }
      else
      {
        JOptionPane.showMessageDialog(aplicacao, 'Atenção: Selecione uma linha da visão para imprimir uma requisição interna.', 'Atenção', JOptionPane.WARNING_MESSAGE)
      }
    }

    if(objeto == this.btRequisitarItem) {
      DlgDadosRequisicaoInterna dlgDadosRequisicaoInterna = new DlgDadosRequisicaoInterna(aplicacao)
        dlgDadosRequisicaoInterna.setVisible(true)
    }

    if(objeto == this.btCancelarRequisicao) {
      if(this.tblRequisicoesInternas.getSelectedRow() >=0) {
        if(JOptionPane.showConfirmDialog(aplicacao, 'Atenção: Tem certeza que deseja cancelar a requisição interna selecionada?', 'Atenção', JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
          int linha = this.tblRequisicoesInternas.getSelectedRow()
            int codigo = Integer.parseInt((String)this.tblRequisicoesInternas.getValueAt(linha, 0))
            RequisicaoInterna requisicaoInterna = new RequisicaoInterna(codigo)
            try {
              requisicaoInterna.cancelarRequisicaoInterna()
            }
          catch(Exception e) {
            e.printStackTrace()
          }
        }
      }
      else
      {
        JOptionPane.showMessageDialog(aplicacao, 'Atenção: Selecione uma linha da visão para cancelar uma requisição interna.', 'Atenção', JOptionPane.WARNING_MESSAGE)
      }
    }

    if(objeto == this.btAtualizarRequisicao) {
      atualizarTabelaRequisicaoInterna()
    }

    if(objeto == cbxCategoria) {
      atualizarTabelaItem('C', '')
    }

    if(objeto == btVisualizarRecursos) {
      if(btVisualizarRecursos.getText().equals('Visualizar Recursos do Pedido')) {
        scrollRecursos.setVisible(true)
          btVisualizarRecursos.setText('Ocultar Recursos do Pedido')
      }
      else
      {
        scrollRecursos.setVisible(false)
          btVisualizarRecursos.setText('Visualizar Recursos do Pedido')
      }
    }

    if(objeto == cbxFornecedor || objeto == cbxItem) {
      atualizarTabelaPrecos()
    }

    if(objeto == btAlterarValor) {
      if(tblListaPrecos.getSelectedRow() >= 0) {
        DlgAlteracaoPreco dlgAlteracaoPreco = new DlgAlteracaoPreco(aplicacao, (FornecedorItem)fornecedoresItem.get(tblListaPrecos.getSelectedRow()))
          dlgAlteracaoPreco.setVisible(true)
          atualizarTabelaPrecos()
      }
    }

    if(objeto == btAtualizarFornecedores) {
      modeloTabelaFornecedoresPendentes.definirConsulta('select distinct f.codigo as codigo_fornecedor, f.razao_social ' +
          'from fornecedor f, fornecedor_item fi, item i, quantidade_materia_prima qmp, modelo_pedido mp, pedido_cliente pc ' +
          'where f.codigo = fi.fornecedor and i.codigo = fi.item and qmp.item = i.codigo and mp.referencia = qmp.referencia and qmp.produto = mp.modelo and mp.numero_sola = qmp.numero_sola and mp.pedido = pc.codigo and pc.status = '1P' and mp.pedido not in (select distinct pedido from pedido_requisicao_compra)')
        tblFornecedoresPendentes.setModel(modeloTabelaFornecedoresPendentes)
        tblFornecedoresPendentes.updateUI()
    }

    if(objeto == btImprimirFornecedores) {
      try {
        Vector fornecedores = new Vector()
          ResultSet rsFornecedoresPendentes = aplicacao.obterConexao().executarConsulta('select distinct f.codigo as codigo_fornecedor, f.razao_social ' +
              'from fornecedor f, fornecedor_item fi, item i, quantidade_materia_prima qmp, modelo_pedido mp, pedido_cliente pc ' +
              'where f.codigo = fi.fornecedor and i.codigo = fi.item and qmp.item = i.codigo and mp.referencia = qmp.referencia and qmp.produto = mp.modelo and mp.numero_sola = qmp.numero_sola and mp.pedido = pc.codigo and pc.status = '1P' and mp.pedido not in (select distinct pedido from pedido_requisicao_compra)')
          while(rsFornecedoresPendentes.next()) {
            fornecedores.addElement(new Fornecedor(rsFornecedoresPendentes.getInt('codigo_fornecedor'), rsFornecedoresPendentes.getString('razao_social')))
          }
        rsFornecedoresPendentes.close()
          RelatorioFornecedoresPendentes relFornecedoresPendentes = new RelatorioFornecedoresPendentes(fornecedores)
          Vector paginas = relFornecedoresPendentes.paginar(aplicacao.obterFormatoPagina())
          Impressora impressora = new Impressora()
          impressora.addPaginas(paginas, aplicacao.obterFormatoPagina())
          impressora.imprimir()
      }
      catch(Exception e) {
        JOptionPane.showMessageDialog(aplicacao, 'Erro: Não foi possível carregar os itens para impressão.', 'Erro', JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
    }
  }
}
