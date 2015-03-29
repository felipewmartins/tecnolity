package org.esmerilprogramming.tecnolity.suprimentos.ui;

import java.sql.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import org.esmerilprogramming.tecnolity.administracao.*;
import org.esmerilprogramming.tecnolity.aplicacao.Aplicacao;
import org.esmerilprogramming.tecnolity.suprimentos.*;
import org.esmerilprogramming.tecnolity.util.*;

class DlgDadosItem extends JDialog implements ActionListener, FocusListener
{
  public final int IDENTIFICADOR = 17;

  private Aplicacao aplicacao;
  private Vector unidades,categorias,fornecedores, departamentos, departamentosSelecionados, fornecedoresItem;
  private Item item;
  private Categoria categoria = new Categoria();
  private GridBagLayout gridbag;
  private GridBagConstraints gbc;
  private Calendario calendario;

  // Objetos do painel de dados do item
  private JPanel pnlDadosItem;
  private JTextField txtDescricao, txtTemperatura, txtQuantidade, 
          txtQuantMinima, txtQuantMaxima, txtPercentualIPI, txtPercentualPerda;
  private JCheckBox chbReservavel;
  private JRadioButton rdbSituacaoAtiva, rdbSituacaoDesativa;
  private JComboBox cbxUnidadeTecnolity, cbxCategoria;
  private JTextArea txaArmazenamento, txaSeguranca;
  private JButton btNovaUnidadeTecnolity, btNovaCategoria, btNovoFornecedor, btNovaUnidadeFornecedor;

  // Objetos do painel de fornecedores do item
  private JPanel pnlDadosFornecedor;
  private JComboBox cbxFornecedor, cbxUnidadeFornecedor, cbxMoeda;
  private JTextField txtValorUnitario, txtDataAtualizacao, txtReferenciaFornecedor;
  private JButton btAdicionarFornecedor, btCancelarFornecedor, btExcluirFornecedor, btAlterarFornecedor, btConfirmarAlteracao;
  private JTable tblFornecedores;
  private int numeroFornecedores, linhaSelecionada;

  // Objetos do painel de Departamentos Associados
  private JPanel pnlAssociacaoDepartamento;
  private JButton btAdicionarDepartamento, btExcluirDepartamento;
  private JList lstDepartamento, lstDepartamentoSelecionado;

  private Container conteudo;
  private JPanel pnlAreaDados;
  private CardLayout card;
  private int indiceCard;
  private JButton btAnterior, btProximo, btConfirmar, btCancelar;

  public DlgDadosItem(Aplicacao aplicacao)
  {
    super(aplicacao,true);	    
    item = new Item();

    this.setTitle("Item");

    this.aplicacao = aplicacao;

    montarInterface();
  }

  public DlgDadosItem(Aplicacao aplicacao, int codigo)
  {	
    super(aplicacao,true);
    try
    {
      item = new Item(codigo, aplicacao.obterConexao());
    }
    catch(Exception e)
    {
      JOptionPane.showMessageDialog(aplicacao,"Erro: "+e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
      e.printStackTrace();
    }

    this.setTitle("Item");

    this.aplicacao = aplicacao;

    montarInterface();
    carregarFornecedorItem();             
    carregarDepartamentosSelecionados(lstDepartamentoSelecionado);
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

    pnlAreaDados = new JPanel();
    card = new CardLayout();
    pnlAreaDados.setLayout(card);
    pnlDadosItem = new JPanel(gridbag);
    pnlDadosItem.setBorder(new TitledBorder("Dados do Item"));
    JLabel label = new JLabel("Descrição");
    adicionarComponente(pnlDadosItem,label,0,0,3,1);
    label = new JLabel("Unidade Tecnolity");
    adicionarComponente(pnlDadosItem,label,0,3,1,1);
    txtDescricao = new JTextField(this.item.obterDescricao(),30);            
    adicionarComponente(pnlDadosItem,txtDescricao,1,0,3,1);

    JPanel pnlSuporteCombo = new JPanel(new BorderLayout());
    cbxUnidadeTecnolity = new JComboBox();
    carregarUnidades(cbxUnidadeTecnolity);

    int indiceUnidade = 0;
    for(int i = 1; i < unidades.size(); i++)
    {                
      if((((Unidade)unidades.get(i)).obterNomeUnidade()).equals((this.item.obterUnidade())==null?"":(this.item.obterUnidade()).obterNomeUnidade()))
      {
        indiceUnidade = i;         
      }
    }        
    cbxUnidadeTecnolity.setSelectedIndex(indiceUnidade);

    pnlSuporteCombo.add(cbxUnidadeTecnolity,BorderLayout.CENTER);
    btNovaUnidadeTecnolity = new JButton(new ImageIcon("imagens/novo.jpg"));
    btNovaUnidadeTecnolity.addActionListener(this);
    btNovaUnidadeTecnolity.setToolTipText("Nova Unidade");
    btNovaUnidadeTecnolity.setPreferredSize(new Dimension(22,20));
    pnlSuporteCombo.add(btNovaUnidadeTecnolity,BorderLayout.EAST);
    adicionarComponente(pnlDadosItem,pnlSuporteCombo,1,3,1,1);

    label = new JLabel("Categoria");
    adicionarComponente(pnlDadosItem,label,2,0,2,1);
    label = new JLabel("Temperatura (C°)");
    adicionarComponente(pnlDadosItem,label,2,2,1,1);

    pnlSuporteCombo = new JPanel(new BorderLayout());
    cbxCategoria = new JComboBox();
    categorias = categoria.carregarCategorias(cbxCategoria,aplicacao);

    int indiceCategoria = 0;
    for(int i = 1; i < categorias.size(); i++)
    {            
      if((((Categoria)categorias.get(i)).obterNomeCategoria()).equals((this.item.obterCategoria())==null?"":(this.item.obterCategoria()).obterNomeCategoria()))
      {
        indiceCategoria = i;         
      }
    }        
    cbxCategoria.setSelectedIndex(indiceCategoria);

    pnlSuporteCombo.add(cbxCategoria,BorderLayout.CENTER);
    btNovaCategoria = new JButton(new ImageIcon("imagens/novo.jpg"));
    btNovaCategoria.addActionListener(this);
    btNovaCategoria.setToolTipText("Nova Categoria");
    btNovaCategoria.setPreferredSize(new Dimension(22,20));
    pnlSuporteCombo.add(btNovaCategoria,BorderLayout.EAST);
    adicionarComponente(pnlDadosItem,pnlSuporteCombo,3,0,2,1);

    txtTemperatura = new JTextField(""+Numero.inverterSeparador(""+this.item.obterTemperatura()),8);
    txtTemperatura.addFocusListener(this);
    adicionarComponente(pnlDadosItem,txtTemperatura,3,2,1,1);
    chbReservavel = new JCheckBox("Independente");            
    adicionarComponente(pnlDadosItem,chbReservavel,3,3,1,1);
    if(this.item.obterIndependente())
      chbReservavel.setSelected(true);
    else
      chbReservavel.setSelected(false);
    label = new JLabel("Quantidade");
    adicionarComponente(pnlDadosItem,label,4,0,1,1);
    label = new JLabel("Quant.Mínima");
    adicionarComponente(pnlDadosItem,label,4,1,1,1);
    label = new JLabel("Quant.Máxima");
    adicionarComponente(pnlDadosItem,label,4,2,1,1);
    JPanel pnlSituacao = new JPanel();
    pnlSituacao.setBorder(new TitledBorder("Situação"));
    ButtonGroup grupo = new ButtonGroup();
    rdbSituacaoAtiva = new JRadioButton("Ativo");
    if(this.item.obterAtivo())
      rdbSituacaoAtiva.setSelected(true);
    grupo.add(rdbSituacaoAtiva);
    pnlSituacao.add(rdbSituacaoAtiva);
    rdbSituacaoDesativa = new JRadioButton("Inativo");
    if(!this.item.obterAtivo())
      rdbSituacaoDesativa.setSelected(true);
    grupo.add(rdbSituacaoDesativa);
    pnlSituacao.add(rdbSituacaoDesativa);
    adicionarComponente(pnlDadosItem,pnlSituacao,4,3,1,2);
    txtQuantidade = new JTextField(""+Numero.inverterSeparador(""+this.item.obterQuantidade()),10);
    txtQuantidade.addFocusListener(this);
    adicionarComponente(pnlDadosItem,txtQuantidade,5,0,1,1);
    txtQuantMinima = new JTextField(""+Numero.inverterSeparador(""+this.item.obterQuantidadeMinima()),10);
    txtQuantMinima.addFocusListener(this);
    adicionarComponente(pnlDadosItem,txtQuantMinima,5,1,1,1);
    txtQuantMaxima = new JTextField(""+Numero.inverterSeparador(""+this.item.obterQuantidadeMaxima()),10);
    txtQuantMaxima.addFocusListener(this);
    adicionarComponente(pnlDadosItem,txtQuantMaxima,5,2,1,1);
    label = new JLabel("IPI (%)");
    adicionarComponente(pnlDadosItem,label,6,0,1,1);
    label = new JLabel("Perda (%)");
    adicionarComponente(pnlDadosItem,label,6,1,1,1);
    txtPercentualIPI = new JTextField(""+Numero.inverterSeparador(""+this.item.obterPercentualIPI()),3);
    txtPercentualIPI.addFocusListener(this);
    adicionarComponente(pnlDadosItem,txtPercentualIPI,7,0,1,1);
    txtPercentualPerda = new JTextField(""+ this.item.obterPercentualPerda(),3);
    adicionarComponente(pnlDadosItem,txtPercentualPerda,7,1,1,1);
    label = new JLabel("Armazenamento");
    adicionarComponente(pnlDadosItem,label,8,0,2,1);
    label = new JLabel("Segurança");
    adicionarComponente(pnlDadosItem,label,8,2,2,1);
    txaArmazenamento = new JTextArea(this.item.obterArmazenamento(),4,20);
    txaArmazenamento.setLineWrap(true);
    txaArmazenamento.setWrapStyleWord(true);
    JScrollPane scroll = new JScrollPane(txaArmazenamento);
    adicionarComponente(pnlDadosItem,scroll,9,0,2,1);
    txaSeguranca = new JTextArea(this.item.obterSeguranca(),4,20);
    txaSeguranca.setLineWrap(true);
    txaSeguranca.setWrapStyleWord(true);
    scroll = new JScrollPane(txaSeguranca);
    adicionarComponente(pnlDadosItem,scroll,9,2,2,1);

    fornecedoresItem = new Vector();
    pnlDadosFornecedor = new JPanel(gridbag);
    pnlDadosFornecedor.setBorder(new TitledBorder("Dados do Fornecedor"));
    label = new JLabel("Fornecedores");
    adicionarComponente(pnlDadosFornecedor,label,0,0,3,1);
    label = new JLabel("Unidade do Fornecedor");
    adicionarComponente(pnlDadosFornecedor,label,0,3,1,1);

    pnlSuporteCombo = new JPanel(new BorderLayout());
    cbxFornecedor = new JComboBox();
    carregarFornecedores(cbxFornecedor);
    pnlSuporteCombo.add(cbxFornecedor,BorderLayout.CENTER);
    btNovoFornecedor = new JButton(new ImageIcon("imagens/novo.jpg"));
    btNovoFornecedor.addActionListener(this);
    btNovoFornecedor.setToolTipText("Novo Fornecedor");
    btNovoFornecedor.setPreferredSize(new Dimension(22,20));
    pnlSuporteCombo.add(btNovoFornecedor,BorderLayout.EAST);

    adicionarComponente(pnlDadosFornecedor,pnlSuporteCombo,1,0,3,1);

    pnlSuporteCombo = new JPanel(new BorderLayout());
    cbxUnidadeFornecedor = new JComboBox();
    carregarUnidades(cbxUnidadeFornecedor);
    pnlSuporteCombo.add(cbxUnidadeFornecedor,BorderLayout.CENTER);
    btNovaUnidadeFornecedor = new JButton(new ImageIcon("imagens/novo.jpg"));
    btNovaUnidadeFornecedor.addActionListener(this);
    btNovaUnidadeFornecedor.setToolTipText("Nova Unidade");
    btNovaUnidadeFornecedor.setPreferredSize(new Dimension(22,20));
    pnlSuporteCombo.add(btNovaUnidadeFornecedor,BorderLayout.EAST);

    adicionarComponente(pnlDadosFornecedor,pnlSuporteCombo,1,3,1,1);
    label = new JLabel("Moeda");
    adicionarComponente(pnlDadosFornecedor,label,2,0,1,1);
    label = new JLabel("Valor Unitário");
    adicionarComponente(pnlDadosFornecedor,label,2,1,1,1);
    label = new JLabel("Dt. Atualização (dd/mm/aaaa)");
    adicionarComponente(pnlDadosFornecedor,label,2,2,1,1);
    label = new JLabel("Referência do Fornecedor");
    adicionarComponente(pnlDadosFornecedor,label,2,3,1,1);
    cbxMoeda = new JComboBox();
    cbxMoeda.addItem('R$');
    cbxMoeda.addItem('US$');
    adicionarComponente(pnlDadosFornecedor,cbxMoeda,3,0,1,1);
    txtValorUnitario = new JTextField("000,00",10);
    txtValorUnitario.addFocusListener(this);
    adicionarComponente(pnlDadosFornecedor,txtValorUnitario,3,1,1,1);
    calendario = new Calendario();
    txtDataAtualizacao = new JTextField(calendario.dataHoje("dd/MM/yyyy"),10);
    txtDataAtualizacao.addFocusListener(this);
    adicionarComponente(pnlDadosFornecedor,txtDataAtualizacao,3,2,1,1);
    txtReferenciaFornecedor = new JTextField(10);
    adicionarComponente(pnlDadosFornecedor,txtReferenciaFornecedor,3,3,1,1);
    JPanel pnlFornecedores = new JPanel(new BorderLayout());
    JPanel pnlComandoFornecedor = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    btConfirmarAlteracao = new JButton("Confirmar Alteração");
    btConfirmarAlteracao.addActionListener(this);
    btConfirmarAlteracao.setEnabled(false);
    pnlComandoFornecedor.add(btConfirmarAlteracao);
    btAdicionarFornecedor = new JButton("Adicionar");
    btAdicionarFornecedor.addActionListener(this);
    pnlComandoFornecedor.add(btAdicionarFornecedor);
    btCancelarFornecedor = new JButton("Cancelar");
    btCancelarFornecedor.addActionListener(this);
    pnlComandoFornecedor.add(btCancelarFornecedor);
    pnlFornecedores.add(pnlComandoFornecedor, BorderLayout.NORTH);
    Object[][] dados = new Object[20][5];
    String[] nomeColunas = ["Fornecedor","Unidade","Valor Unitário","Atualização"]
    tblFornecedores = new JTable(dados, nomeColunas);
    tblFornecedores.setPreferredScrollableViewportSize(new Dimension(460, 100));
    tblFornecedores.addRowSelectionInterval(0,0);
    scroll = new JScrollPane(tblFornecedores); 

    pnlFornecedores.add(scroll, BorderLayout.CENTER);
    pnlComandoFornecedor = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    btAlterarFornecedor = new JButton("Alterar Selecionado");
    btAlterarFornecedor.addActionListener(this);
    btAlterarFornecedor.setEnabled(false);
    pnlComandoFornecedor.add(btAlterarFornecedor);
    btExcluirFornecedor = new JButton("Excluir Selecionado");
    btExcluirFornecedor.addActionListener(this);
    btExcluirFornecedor.setEnabled(false);
    pnlComandoFornecedor.add(btExcluirFornecedor);
    pnlFornecedores.add(pnlComandoFornecedor, BorderLayout.SOUTH);
    adicionarComponente(pnlDadosFornecedor,pnlFornecedores,4,0,4,1);            

    departamentosSelecionados = new Vector();
    pnlAssociacaoDepartamento = new JPanel(gridbag);
    pnlAssociacaoDepartamento.setBorder(new TitledBorder("Departamentos"));
    label = new JLabel("Departamentos");
    adicionarComponente(pnlAssociacaoDepartamento,label,0,0,1,1);
    label = new JLabel("Departamentos Selecionados");
    adicionarComponente(pnlAssociacaoDepartamento,label,0,2,1,1);
    lstDepartamento = new JList();
    lstDepartamento.setFixedCellWidth(180);
    carregarDepartamentos(lstDepartamento);             
    scroll = new JScrollPane(lstDepartamento);
    adicionarComponente(pnlAssociacaoDepartamento,scroll,1,0,1,4);
    DefaultListModel modeloListaSelecionado = new DefaultListModel();
    lstDepartamentoSelecionado = new JList(modeloListaSelecionado);
    lstDepartamentoSelecionado.setFixedCellWidth(180);

    scroll = new JScrollPane(lstDepartamentoSelecionado);
    adicionarComponente(pnlAssociacaoDepartamento,scroll,1,2,1,4);
    btAdicionarDepartamento = new JButton("Adicionar >>");
    btAdicionarDepartamento.addActionListener(this);
    adicionarComponente(pnlAssociacaoDepartamento,btAdicionarDepartamento,1,1,1,1);
    btExcluirDepartamento = new JButton("  << Excluir  ");
    btExcluirDepartamento.addActionListener(this);
    adicionarComponente(pnlAssociacaoDepartamento,btExcluirDepartamento,2,1,1,1);

    pnlAreaDados.add(pnlDadosItem,"Itens");
    pnlAreaDados.add(pnlDadosFornecedor,"Fornecedor");
    pnlAreaDados.add(pnlAssociacaoDepartamento,"Departamento");
    conteudo.add(pnlAreaDados,BorderLayout.CENTER);

    JPanel pnlComandos = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    btAnterior = new JButton("<< Anterior");
    btAnterior.addActionListener(this);
    btAnterior.setEnabled(false);
    pnlComandos.add(btAnterior);

    btProximo = new JButton("Próximo >>");
    btProximo.addActionListener(this);
    pnlComandos.add(btProximo);

    btConfirmar = new JButton("Confirmar");
    btConfirmar.addActionListener(this);
    btConfirmar.setEnabled(false);
    pnlComandos.add(btConfirmar);

    btCancelar = new JButton("Cancelar");
    btCancelar.addActionListener(this);
    pnlComandos.add(btCancelar);

    conteudo.add(pnlComandos, BorderLayout.SOUTH);
    redimencionar();
  }

  private void redimencionar()
  {
    this.pack();

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    this.setBounds((screenSize.width/2) - (this.getBounds().width/2),
        (screenSize.height/2) - (this.getBounds().height/2) - 30,
        this.getBounds().width,
        this.getBounds().height);
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

  private void carregarUnidades(JComboBox comboBox)
  {
    ResultSet dadosUnidade;
    Conexao conexao = aplicacao.obterConexao();
    comboBox.removeAllItems();
    try
    {
      dadosUnidade = conexao.executarConsulta("select * from unidade order by unidade asc");
      comboBox.addItem("Selecione...");
      unidades = new Vector();
      unidades.addElement(null);
      int i = 1;
      while(dadosUnidade.next())
      {
        unidades.addElement(new Unidade(dadosUnidade.getInt("codigo"),dadosUnidade.getString("unidade")));
        comboBox.addItem(((Unidade)unidades.get(i)).obterNomeUnidade());
        i++;
      }
      dadosUnidade.close();
      redimencionar();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  private void carregarFornecedores(JComboBox comboBox)
  {
    ResultSet dadosFornecedor;
    Conexao conexao = aplicacao.obterConexao();
    comboBox.removeAllItems();
    try
    {
      dadosFornecedor = conexao.executarConsulta("select codigo, razao_social, cnpj from fornecedor order by razao_social asc");
      comboBox.addItem("Selecione...");
      fornecedores = new Vector();
      fornecedores.addElement(null);
      int i = 1;
      String cnpj, razaoSocial;
      while(dadosFornecedor.next())
      {
        fornecedores.addElement(new Fornecedor(dadosFornecedor.getInt("codigo"),dadosFornecedor.getString("razao_social"),dadosFornecedor.getString("cnpj")));
        cnpj = (((Fornecedor)fornecedores.get(i)).obterCnpj()).trim();
        razaoSocial = (((Fornecedor)fornecedores.get(i)).obterRazaoSocial()).trim();
        comboBox.addItem(cnpj + " - " + razaoSocial.substring(0,((razaoSocial.length() < 40)?razaoSocial.length():40)) + ((razaoSocial.length() < 40)?"":" . . ."));
        i++;
      }
      dadosFornecedor.close();
      redimencionar();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  private void carregarDepartamentos(JList list)
  {
    ResultSet dadosDepartamento;
    Conexao conexao = aplicacao.obterConexao();
    DefaultListModel modeloLista = new DefaultListModel();
    try
    {
      if(item.obterCodigo() == 0)
        dadosDepartamento = conexao.executarConsulta("select codigo, departamento from departamento order by departamento asc");
      else
        dadosDepartamento = conexao.executarConsulta("select distinct departamento.codigo, departamento.departamento from departamento, departamento_item where departamento.codigo not in (select departamento from departamento_item where item = "+ this.item.obterCodigo() +") and departamento_item.item = "+ this.item.obterCodigo() +" order by departamento.departamento asc");
      departamentos = new Vector();
      int i = 0;
      list.setModel(modeloLista);
      while(dadosDepartamento.next())
      {
        departamentos.addElement(new Departamento(dadosDepartamento.getInt("codigo"),dadosDepartamento.getString("departamento")));
        modeloLista.addElement(((Departamento)departamentos.get(i)).obterNomeDepartamento());
        i++;
      }
      dadosDepartamento.close();
      redimencionar();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  private void carregarDepartamentosSelecionados(JList list)
  {
    ResultSet dadosDepartamento;
    Conexao conexao = aplicacao.obterConexao();
    DefaultListModel modeloLista = new DefaultListModel();
    try
    {
      dadosDepartamento = conexao.executarConsulta("select departamento.codigo,departamento.departamento from departamento, departamento_item where departamento.codigo = departamento_item.departamento and departamento_item.item = "+ this.item.obterCodigo() +" order by departamento.departamento asc");
      departamentosSelecionados = new Vector();
      int i = 0;
      list.setModel(modeloLista);
      while(dadosDepartamento.next())
      {
        departamentosSelecionados.addElement(new Departamento(dadosDepartamento.getInt("codigo"),dadosDepartamento.getString("departamento")));
        modeloLista.addElement(((Departamento)departamentosSelecionados.get(i)).obterNomeDepartamento());
        i++;
      }
      dadosDepartamento.close();
      redimencionar();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  private void carregarFornecedorItem()
  {            
    numeroFornecedores = 0;
    Conexao conexao = aplicacao.obterConexao();
    String query = "select f.codigo as fornecedor_codigo, f.cnpj as cnpj_fornecedor,razao_social,data_atualizacao_valor,fi.unidade as unidade_codigo,u.unidade as unidade,valor_item,moeda,referencia_fornecedor from fornecedor_item fi, fornecedor f, unidade u where fi.fornecedor = f.codigo and fi.unidade = u.codigo and fi.item = " + this.item.obterCodigo() + " order by razao_social ";
    try
    {
      ResultSet resultado = conexao.executarConsulta(query);
      try
      {
        int codigoFornecedor;
        String razaoSocialFornecedor;
        String cnpj;
        String dataAtualizacaoValor;
        int codigoUnidade;
        String nomeUnidade;
        float valorItem;
        String moeda;
        String referenciaFornecedor;

        while(resultado.next())
        {
          codigoFornecedor = resultado.getInt("fornecedor_codigo");
          cnpj = resultado.getString("cnpj_fornecedor");
          razaoSocialFornecedor = resultado.getString("razao_social");
          dataAtualizacaoValor = Calendario.ajustarFormatoDataBanco(resultado.getString("data_atualizacao_valor"));
          codigoUnidade = resultado.getInt("unidade_codigo");
          nomeUnidade = resultado.getString("unidade");
          valorItem = resultado.getFloat("valor_item");
          moeda = resultado.getString("moeda");
          referenciaFornecedor = resultado.getString("referencia_fornecedor");

          fornecedoresItem.addElement(new FornecedorItem(new Fornecedor(codigoFornecedor,razaoSocialFornecedor,cnpj),(Item)this.item,dataAtualizacaoValor,new Unidade(codigoUnidade,nomeUnidade),valorItem,moeda,referenciaFornecedor));

          tblFornecedores.setValueAt(razaoSocialFornecedor,numeroFornecedores,0);
          tblFornecedores.setValueAt(nomeUnidade,numeroFornecedores,1);
          tblFornecedores.setValueAt(moeda + " " + Numero.inverterSeparador(""+valorItem),numeroFornecedores,2);
          tblFornecedores.setValueAt(dataAtualizacaoValor,numeroFornecedores,3);
          numeroFornecedores++;                    
        }
      }
      catch(Exception ex)
      {
        JOptionPane.showMessageDialog(aplicacao,"Erro: " + ex.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
      }
      resultado.close();
      if (numeroFornecedores > 0)
      {
        btAlterarFornecedor.setEnabled(true);
        btExcluirFornecedor.setEnabled(true);
      }
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
  }

  private void cancelarFornecedor()
  {
    cbxFornecedor.setSelectedIndex(0);
    cbxUnidadeFornecedor.setSelectedIndex(0);
    txtValorUnitario.setText("000,00");
    txtDataAtualizacao.setText(calendario.dataHoje("dd/MM/yyyy"));
    txtReferenciaFornecedor.setText("");
  }

  public void actionPerformed(java.awt.event.ActionEvent actionEvent) 
  {
    Object objeto = actionEvent.getSource();

    if(objeto == btNovaUnidadeTecnolity)
    {
      DlgDadosUnidade dlgDadosUnidade = new DlgDadosUnidade(aplicacao,'I');
      dlgDadosUnidade.setVisible(true);
      carregarUnidades(this.cbxUnidadeTecnolity);
      carregarUnidades(this.cbxUnidadeFornecedor);
    }

    if(objeto == btNovaCategoria)
    {
      DlgDadosCategoria dlgDadosCategoria = new DlgDadosCategoria(aplicacao,'I');
      dlgDadosCategoria.setVisible(true);
      categorias = categoria.carregarCategorias(this.cbxCategoria,aplicacao);
    }

    if(objeto == btNovoFornecedor)
    {
      DlgDadosFornecedor dlgDadosFornecedor = new DlgDadosFornecedor(aplicacao,'I');
      dlgDadosFornecedor.setVisible(true);
      carregarFornecedores(this.cbxFornecedor);
    }

    if(objeto == btNovaUnidadeFornecedor)
    {
      DlgDadosUnidade dlgDadosUnidade = new DlgDadosUnidade(aplicacao,'I');
      dlgDadosUnidade.setVisible(true);
      carregarUnidades(this.cbxUnidadeTecnolity);
      carregarUnidades(this.cbxUnidadeFornecedor);
    }

    if(objeto == btConfirmarAlteracao)
    {
      try
      {
        boolean fornecedorInserido = false;

        if(!fornecedorInserido)
        {
          fornecedoresItem.setElementAt(new FornecedorItem((Fornecedor)fornecedores.get(cbxFornecedor.getSelectedIndex()),(Item)this.item,this.txtDataAtualizacao.getText(),(Unidade)unidades.get(cbxUnidadeFornecedor.getSelectedIndex()),Float.parseFloat(Numero.inverterSeparador(txtValorUnitario.getText())),(String)cbxMoeda.getSelectedItem(),txtReferenciaFornecedor.getText()),linhaSelecionada);
          tblFornecedores.setValueAt(cbxFornecedor.getSelectedItem(),linhaSelecionada,0);
          tblFornecedores.setValueAt(cbxUnidadeFornecedor.getSelectedItem(),linhaSelecionada,1);
          tblFornecedores.setValueAt(txtValorUnitario.getText(),linhaSelecionada,2);
          tblFornecedores.setValueAt(txtDataAtualizacao.getText(),linhaSelecionada,3);
          cancelarFornecedor();
        }
        else
        {
          JOptionPane.showMessageDialog(aplicacao,"Erro: O Fornecedor informado já foi associado ao Item.","Erro",JOptionPane.ERROR_MESSAGE);
        }
      }
      catch(NumberFormatException n)
      {
        JOptionPane.showMessageDialog(aplicacao,"Erro: Valor incorreto.","Erro",JOptionPane.ERROR_MESSAGE);
        n.printStackTrace();
      }
      catch(Exception e)
      {
        JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
      }
      btConfirmarAlteracao.setEnabled(false);
    }

    if(objeto == btAdicionarFornecedor)
    {
      if(numeroFornecedores < 20)
      {
        try
        {
          boolean fornecedorInserido = false;
          for(int i=0;i < 20;i++)
          {
            if(cbxFornecedor.getSelectedItem().equals((String)tblFornecedores.getValueAt(i,0)))
            {
              fornecedorInserido = true;
            }
          }
          if(!fornecedorInserido)
          {
            fornecedoresItem.addElement(new FornecedorItem((Fornecedor)fornecedores.get(cbxFornecedor.getSelectedIndex()),(Item)this.item,this.txtDataAtualizacao.getText(),(Unidade)unidades.get(cbxUnidadeFornecedor.getSelectedIndex()),Float.parseFloat(Numero.inverterSeparador(txtValorUnitario.getText())),(String)cbxMoeda.getSelectedItem(),txtReferenciaFornecedor.getText()));
            tblFornecedores.setValueAt(cbxFornecedor.getSelectedItem(),numeroFornecedores,0);
            tblFornecedores.setValueAt(cbxUnidadeFornecedor.getSelectedItem(),numeroFornecedores,1);
            tblFornecedores.setValueAt(txtValorUnitario.getText(),numeroFornecedores,2);
            tblFornecedores.setValueAt(txtDataAtualizacao.getText(),numeroFornecedores,3);
            numeroFornecedores++;
            cancelarFornecedor();
          }
          else
          {
            JOptionPane.showMessageDialog(aplicacao,"Erro: O Fornecedor informado já foi associado ao Item.","Erro",JOptionPane.ERROR_MESSAGE);
          }
        }
        catch(NumberFormatException n)
        {
          JOptionPane.showMessageDialog(aplicacao,"Erro: Valor incorreto.","Erro",JOptionPane.ERROR_MESSAGE);
          n.printStackTrace();
        }
        catch(Exception e)
        {
          JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
          e.printStackTrace();
        }
        if (numeroFornecedores > 0)
        {
          btAlterarFornecedor.setEnabled(true);
          btExcluirFornecedor.setEnabled(true);
        }
        if (numeroFornecedores == 20)
        {
          btAlterarFornecedor.setEnabled(false);
          btAdicionarFornecedor.setEnabled(false);
        }
      }
      else
      {
        btAdicionarFornecedor.setEnabled(false);
      }
    }

    if(objeto == btAlterarFornecedor)
    {
      linhaSelecionada = tblFornecedores.getSelectedRow();
      FornecedorItem fornecedorSelecionado = (FornecedorItem)fornecedoresItem.get(linhaSelecionada);
      for(int i = 1;i < fornecedores.size();i++)
      {
        if(fornecedorSelecionado.obterFornecedor().obterCodigo() == ((Fornecedor)fornecedores.get(i)).obterCodigo())
        {
          cbxFornecedor.setSelectedIndex(i);
          break;
        }
      }

      for(int i = 1;i < unidades.size();i++)
      {
        if(fornecedorSelecionado.obterUnidade().obterCodigo() == ((Unidade)unidades.get(i)).obterCodigo())
        {
          cbxUnidadeFornecedor.setSelectedIndex(i);
          break;
        }
      }

      cbxMoeda.setSelectedItem(fornecedorSelecionado.obterMoeda());
      txtValorUnitario.setText(Numero.inverterSeparador(fornecedorSelecionado.obterValorItem()));
      txtDataAtualizacao.setText(fornecedorSelecionado.obterDataAtualizacaoValor());
      txtReferenciaFornecedor.setText(fornecedorSelecionado.obterReferenciaFornecedor());
      btConfirmarAlteracao.setEnabled(true);
    }

    if(objeto == btExcluirFornecedor)
    {
      int linhaSelecionada = tblFornecedores.getSelectedRow();
      fornecedoresItem.removeElementAt(linhaSelecionada);
      tblFornecedores.setValueAt("",linhaSelecionada,0);
      tblFornecedores.setValueAt("",linhaSelecionada,1);
      tblFornecedores.setValueAt("",linhaSelecionada,2);
      tblFornecedores.setValueAt("",linhaSelecionada,3);

      if(linhaSelecionada < (numeroFornecedores -1))
      {
        for(int i = linhaSelecionada;i < (numeroFornecedores -1);i++)
        {
          tblFornecedores.setValueAt(tblFornecedores.getValueAt(i+1,0),i,0);
          tblFornecedores.setValueAt(tblFornecedores.getValueAt(i+1,1),i,1);
          tblFornecedores.setValueAt(tblFornecedores.getValueAt(i+1,2),i,2);
          tblFornecedores.setValueAt(tblFornecedores.getValueAt(i+1,3),i,3);                
        }
        tblFornecedores.setValueAt("",numeroFornecedores -1,0);
        tblFornecedores.setValueAt("",numeroFornecedores -1,1);
        tblFornecedores.setValueAt("",numeroFornecedores -1,2);
        tblFornecedores.setValueAt("",numeroFornecedores -1,3);                
      }
      numeroFornecedores--;

      if (numeroFornecedores == 0)
      {
        btAlterarFornecedor.setEnabled(false);
        btExcluirFornecedor.setEnabled(false);
      }
      if (numeroFornecedores < 20)
      {
        btAdicionarFornecedor.setEnabled(true);
      }
    }

    if(objeto == btAdicionarDepartamento)
    {
      if(!lstDepartamento.isSelectionEmpty())
      {
        departamentosSelecionados.addElement(departamentos.get(lstDepartamento.getSelectedIndex()));
        DefaultListModel modelo = (DefaultListModel)lstDepartamento.getModel();
        DefaultListModel modeloSelecionado = (DefaultListModel)lstDepartamentoSelecionado.getModel();
        modeloSelecionado.addElement(lstDepartamento.getSelectedValue());
        modelo.removeElementAt(lstDepartamento.getSelectedIndex());
      }
      else
      {
        JOptionPane.showMessageDialog(aplicacao,"Selecione um departamento.","Erro",JOptionPane.WARNING_MESSAGE);
      }
    }

    if(objeto == btCancelarFornecedor)
    {
      cancelarFornecedor();
    }

    if(objeto == btExcluirDepartamento)
    {
      if(!lstDepartamentoSelecionado.isSelectionEmpty())
      {
        departamentosSelecionados.removeElementAt(lstDepartamentoSelecionado.getSelectedIndex());
        DefaultListModel modelo = (DefaultListModel)lstDepartamento.getModel();
        DefaultListModel modeloSelecionado = (DefaultListModel)lstDepartamentoSelecionado.getModel();
        modelo.addElement(lstDepartamentoSelecionado.getSelectedValue());
        modeloSelecionado.removeElementAt(lstDepartamentoSelecionado.getSelectedIndex());
      }
      else
      {
        JOptionPane.showMessageDialog(aplicacao,"Selecione um departamento.","Erro",JOptionPane.WARNING_MESSAGE);
      }	
    }

    if(objeto == btProximo)
    {
      boolean confirmado = true; // Se os dados forem válidos autoriza a mudança de painel.
      if(indiceCard == 0)
      {
        try
        {
          this.item.definirDescricao(this.txtDescricao.getText());
          this.item.definirUnidade((Unidade)this.unidades.get(this.cbxUnidadeTecnolity.getSelectedIndex()));
          this.item.definirCategoria((Categoria)this.categorias.get(this.cbxCategoria.getSelectedIndex()));
          this.item.definirTemperatura(Float.parseFloat(Numero.inverterSeparador(txtTemperatura.getText())));
          this.item.definirIndependente(this.chbReservavel.isSelected()?true:false);
          this.item.definirQuantidade(Float.parseFloat(Numero.inverterSeparador(txtQuantidade.getText())));
          this.item.definirQuantidadeMinima(Float.parseFloat(Numero.inverterSeparador(txtQuantMinima.getText())));
          this.item.definirQuantidadeMaxima(Float.parseFloat(Numero.inverterSeparador(txtQuantMaxima.getText())));
          this.item.definirAtivo(this.rdbSituacaoAtiva.isSelected()?true:false);
          this.item.definirPercentualIPI(Integer.parseInt(this.txtPercentualIPI.getText()));
          this.item.definirPercentualPerda(Integer.parseInt(this.txtPercentualPerda.getText()));
          this.item.definirArmazenamento(this.txaArmazenamento.getText());
          this.item.definirSeguranca(this.txaSeguranca.getText());
        }
        catch(NumberFormatException n)
        {
          JOptionPane.showMessageDialog(aplicacao,"Erro: Valor incorreto.","Erro",JOptionPane.ERROR_MESSAGE);
          n.printStackTrace();
          confirmado = false;
        }
        catch(Exception e)
        {
          JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
          e.printStackTrace();
          confirmado = false;
        }
      }

      if(confirmado)
      {
        card.next(pnlAreaDados);
        indiceCard++;
        if(indiceCard > 0)
          btAnterior.setEnabled(true);
        if(indiceCard == 2)
        {
          btProximo.setEnabled(false);
          btConfirmar.setEnabled(true);
        }
      }
    }

    if(objeto == btAnterior)
    {
      card.previous(pnlAreaDados);
      indiceCard--;
      if(indiceCard < 2)
        btProximo.setEnabled(true);
      if(indiceCard == 0)
        btAnterior.setEnabled(false);
    }

    if(objeto == btConfirmar)
    {
      try
      {
        if(this.item.obterCodigo() == 0)
        {
          this.item.cadastrarItem();
          this.item.associarFornecedores(fornecedoresItem);
          this.item.associarDepartamentos(departamentosSelecionados);
          this.setVisible(false);
        }
        else
        {
          this.item.alterarItem();
          this.item.alterarFornecedoresItem(fornecedoresItem);
          this.item.alterarDepartamentosItem(departamentosSelecionados);
          this.setVisible(false);
        }
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

  public void focusGained(FocusEvent e)
  {
    Component componente = e.getComponent();
    if(componente == txtTemperatura)
      txtTemperatura.selectAll();

    if(componente == txtQuantidade)
      txtQuantidade.selectAll();	

    if(componente == txtQuantMaxima)
      txtQuantMaxima.selectAll();	

    if(componente == txtQuantMinima)
      txtQuantMinima.selectAll();

    if(componente == txtPercentualIPI)
      txtPercentualIPI.selectAll();

    if(componente == txtValorUnitario)
      txtValorUnitario.selectAll();

    if(componente == txtDataAtualizacao)
      txtDataAtualizacao.selectAll();
  }
  public void focusLost(FocusEvent e){}
}
