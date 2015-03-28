package br.com.tecnolity.suprimentos.ui;

import java.sql.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import br.com.tecnolity.administracao.*;
import br.com.tecnolity.aplicacao.Aplicacao;
import br.com.tecnolity.pedidos.*;
import br.com.tecnolity.suprimentos.*;
import br.com.tecnolity.util.*;

/**
* Projeto: 001 - Tecnolity
* Autor do Código: Kenia Sousa
* Nome do Arquivo: DlgDadosMovimentacao.java
* Linguagem: Java
* 
* Requerimentos: Requer máquina virtual java versão 1.3 ou superior.
* 
* Objetivo: Diálogo para cadastramento,alteração e visualização de dados de uma Movimentação de Items.
* 
* Objetivo definido por: Hildeberto Mendonça
* Início da Programação: 24/12/2001
* Fim da Programação:
* Última Versão: 1.0
*/

public class DlgDadosMovimentacao extends JDialog implements ActionListener
{
    public final int IDENTIFICADOR = 21;
    
    private Aplicacao aplicacao;

    private GridBagLayout gridbag;
    private GridBagConstraints gbc;

    private Container conteudo;
    private JPanel pnlAreaDados, pnlAreaMovimentacao;
    private CardLayout card;
    private int indiceCard;
    private JButton btConfirmar, btCancelar;
    private JLabel lblUsuario, lblInstanteAtual;
    private JComboBox cbxTipoMovimentacao;
    private Calendario calendario;
    private Colaborador responsavelMovimentacao;
    private Conexao conexao;
    private String tipoMovimentacao;

    // Objetos do painel de dados da movimentação
    private JPanel  pnlAbastecimento, pnlAbastecimentoConfirmacao, pnlConsumo, pnlVendas, 
                    pnlDescarte, pnlDevolucao, pnlDevolucaoExterna,pnlDeposito, pnlRetiradaDeposito;
    
    // Objetos do painel de Abastecimento
    private JComboBox cbxRequisicaoAbastecimento;
    private JLabel  lblFornecedor, lblDataEmissaoAbastecimento, lblDepartamentoSolicitante,
                    lblLimiteEntregaAbastecimento, lblTransportadora, lblTipoFrete,
                    lblRequisicaoAbastecimento, lblTotalGeral;
    private JTable tblItensAbastecimento, tblItensAbastecimentoConfirmacao;
    private JTextField txtNumeroConhecimento, txtDataDespacho, txtDataRecebimento, 
                       txtNotaFiscal, txtValorConhecimento;
    private JTextArea txaObservacao;
    private JButton btDefinirLote,
                    btAnterior, btProximo;
    private Vector requisicoesCompra;
    private Vector tiposMovimentacao = new Vector();
    private Vector itensRequisicaoCompra = new Vector();    
    private Vector codigosMovimentacoes = new Vector();
    private Vector movimentacoes;
    private RequisicaoCompra requisicaoCompra;
    private String razaoSocial, departamentoSolicitante,transportadora,dataEmissao,
                   dataLimiteEntrega, tipoFrete, numeroConhecimento, observacao;
    private float valorConhecimento;
    private Item item;
    private int numeroItens = 0;
    private int codigoMovimentacao;
    private RequisicaoCompra requisicaoCompraSelecionada;
                        
    // Objetos do painel de Consumo
    private JComboBox cbxRequisicaoConsumo;
    private JLabel lblDataLimiteEntregaConsumo, lblPedidoClienteConsumo, 
    			   lblDepartSolicitanteConsumo, lblSolicitanteConsumo;
    private JTextArea txaJustificativaConsumo;
    private JTable tblItensConsumo;
    private Vector requisicoesInternasConsumo, itensRequisicaoInterna;
    private RequisicaoInterna requisicaoInterna;
    private int ordemCompra;
    private Pedido pedidoCliente;
    
    // Objetos do painel de Vendas
    private JComboBox cbxRequisicaoVenda;
    private JTable tblItensVenda;
    private Vector requisicoesInternasVendas;
    private JLabel lblDataEmissao, lblDataLimiteEntregaVendas, lblDepartSolicitanteVendas, 
                   lblSolicitanteVendas, lblDataEmissaoVendas, lblHoraLimiteEntregaVendas;
    private String dataEmissaoVenda, dataLimiteEntregaVenda, departSolicitanteVenda, 
                   solicitanteVenda;

    // Objetos do painel de Descarte
    private JComboBox cbxRequisicaoDescarte;
    private JTable tblItensDescarte;    
    private Vector requisicoesInternasDescarte;
    private JLabel lblDataEmissaoDescarte, lblDataLimiteEntregaDescarte, 
                   lblDepartSolicitanteDescarte, lblSolicitanteDescarte;
    private JTextArea txaJustificativaDescarte;
    private String dataEmissaoDescarte, dataLimiteEntregaDescarte, 
                   departSolicitanteDescarte, solicitanteDescarte,justificativaDescarte;
                   
    // Objetos do painel de Devolução
    private JComboBox cbxRequisicaoDevolucao;
    private JTable tblItensDevolucao;
    private JLabel lblDataEmissaoDevolucao, lblDataLimiteEntregaDevolucao,
                   lblHoraLimiteEntregaDevolucao, lblDepartSolicitanteDevolucao,
                   lblSolicitanteDevolucao, lblPedidoClienteDevolucao;
    private JTextArea txaJustificativaDevolucao;
    private Vector requisicoesDevolucao;
	
    // Objetos do painel de Devolução Externa
    private JComboBox cbxRequisicaoDevolucaoExterna;
    private JTable tblItensDevolucaoExterna;
    private JLabel lblDataEmissaoDevolucaoExterna, lblDataLimiteEntregaDevolucaoExterna,
                   lblDepartSolicitanteDevolucaoExterna,lblSolicitanteDevolucaoExterna;
    private JTextArea txaJustificativaDevolucaoExterna;
    private Vector requisicoesInternasDevolucaoExterna;
    private String dataEmissaoDevolucaoExterna, dataLimiteEntregaDevolucaoExterna,
                   departSolicitanteDevolucaoExterna, solicitanteDevolucaoExterna,
                   justificativaDevolucaoExterna;

    // Objetos do painel Depósito
    private JComboBox cbxRequisicaoDeposito;
    private JLabel lblDescricaoDeposito;
    private JLabel lblDataEmissaoDeposito, lblDataLimiteEntregaDeposito, 
                   lblHoraLimiteEntregaDeposito, lblDepartSolicitanteDeposito,
                   lblSolicitanteDeposito;
    private JTextArea txaJustificativaDeposito;

    // Objetos do painel Retirada do Depósito
    private JComboBox cbxRequisicaoRetiradaDeposito;
    private JLabel lblDataEmissaoRetiradaDeposito, lblDataLimiteEntregaRetiradaDeposito,
                   lblHoraLimiteEntregaRetiradaDeposito, lblDepartSolicitanteRetiradaDeposito,
                   lblSolicitanteRetiradaDeposito, lblDescricaoRetiradaDeposito;
    private JTextArea txaJustificativaRetiradaDeposito;
    	
    public DlgDadosMovimentacao(Aplicacao aplicacao)
    {
        super(aplicacao,true);
        this.setTitle("Movimentação");
        
        this.aplicacao = aplicacao;
        this.conexao = aplicacao.obterConexao();
        responsavelMovimentacao = aplicacao.obterColaborador();

        conteudo = this.getContentPane();

        gridbag = new GridBagLayout();
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets.bottom = 2;
        gbc.insets.left = 2;
        gbc.insets.right = 2;
        gbc.insets.top = 2;

        pnlAreaDados = new JPanel(new BorderLayout());
        
        //usuário logado no sistema
        String nomeResponsavel = "";
        try
        {            
            ResultSet responsavel = conexao.executarConsulta("select nome_completo from usuario where usuario = '"+ (aplicacao.obterColaborador()).obterMatricula() +"' ");
            if(responsavel.next())
                    nomeResponsavel = responsavel.getString("nome_completo");
            responsavel.close();
            responsavel = null;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        //data atual
        String data = "";
        calendario = new Calendario();
        data = calendario.dataHoje("dd/MM/yyyy");
        
        // Cabeçalho do formulário
        JPanel pnlCabecalho = new JPanel(gridbag);
        JLabel label = new JLabel("Responsável");
        adicionarComponente(pnlCabecalho,label,0,0,1,1);
        label = new JLabel("Data");
        adicionarComponente(pnlCabecalho,label,0,1,1,1);
        lblUsuario = new JLabel(nomeResponsavel);
        lblUsuario.setFont(new Font("Arial",Font.PLAIN,12));
        adicionarComponente(pnlCabecalho,lblUsuario,1,0,1,1);
        lblInstanteAtual = new JLabel(data);
        lblInstanteAtual.setFont(new Font("Arial",Font.PLAIN,12));
        adicionarComponente(pnlCabecalho,lblInstanteAtual,1,1,1,1);
        label = new JLabel("Tipo de Movimentação: ");
        adicionarComponente(pnlCabecalho,label,2,0,1,1);
        cbxTipoMovimentacao = new JComboBox();
        cbxTipoMovimentacao.addItem("Selecione...");
        cbxTipoMovimentacao.addItem("Abastecimento");
        cbxTipoMovimentacao.addItem("Consumo");
        cbxTipoMovimentacao.addItem("Vendas");
        cbxTipoMovimentacao.addItem("Descarte");
        cbxTipoMovimentacao.addItem("Devolução");
        cbxTipoMovimentacao.addItem("Devolução Externa");
        //cbxTipoMovimentacao.addItem("Depósito");
        //cbxTipoMovimentacao.addItem("Retirada do Depósito");
        cbxTipoMovimentacao.addActionListener(this);
        tiposMovimentacao.addElement(null);
        tiposMovimentacao.addElement(Movimentacao.ABASTECIMENTO);
        tiposMovimentacao.addElement(Movimentacao.CONSUMO);
        tiposMovimentacao.addElement(Movimentacao.VENDAS);
        tiposMovimentacao.addElement(Movimentacao.DESCARTE);
        tiposMovimentacao.addElement(Movimentacao.DEVOLUCAO);
        tiposMovimentacao.addElement(Movimentacao.DEVOLUCAO_EXTERNA);
        tiposMovimentacao.addElement(Movimentacao.DEPOSITO);
        tiposMovimentacao.addElement(Movimentacao.RETIRADA_DEPOSITO);
        adicionarComponente(pnlCabecalho,cbxTipoMovimentacao,2,1,1,1);
        pnlAreaDados.add(pnlCabecalho, BorderLayout.NORTH);

        card = new CardLayout();
        pnlAreaMovimentacao = new JPanel();
        pnlAreaMovimentacao.setLayout(card);

        // Formulário da Movimentação de Abastecimento
        pnlAbastecimento = new JPanel(gridbag);
        pnlAbastecimento.setBorder(new TitledBorder("Abastecimento"));
        label = new JLabel("Requisição de Compra:");
        adicionarComponente(pnlAbastecimento,label,0,0,1,1);
        cbxRequisicaoAbastecimento = new JComboBox();
        cbxRequisicaoAbastecimento.addActionListener(this);
        try
        {
            requisicaoCompra = new RequisicaoCompra();
            requisicoesCompra = requisicaoCompra.carregarRequisicoesCompra(conexao);
            carregarRequisicoesCompra();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar as Requisições de Compra. ","Erro", JOptionPane.ERROR_MESSAGE);	
            e.printStackTrace();
        }
                        
        adicionarComponente(pnlAbastecimento,cbxRequisicaoAbastecimento,0,1,3,1);
        label = new JLabel("Fornecedor:");
        adicionarComponente(pnlAbastecimento,label,1,0,1,1);
        lblFornecedor = new JLabel();
        lblFornecedor.setFont(new Font("Arial",Font.PLAIN,12));
        adicionarComponente(pnlAbastecimento,lblFornecedor,1,1,1,1);
        label = new JLabel("Departamento Solicitante:");
        adicionarComponente(pnlAbastecimento,label,2,0,1,1);
        lblDepartamentoSolicitante = new JLabel();
        lblDepartamentoSolicitante.setFont(new Font("Arial",Font.PLAIN,12));
        adicionarComponente(pnlAbastecimento,lblDepartamentoSolicitante,2,1,1,1);
        label = new JLabel("Transportadora:");
        adicionarComponente(pnlAbastecimento,label,3,0,1,1);
        lblTransportadora = new JLabel();
        lblTransportadora.setFont(new Font("Arial",Font.PLAIN,12));
        adicionarComponente(pnlAbastecimento,lblTransportadora,3,1,1,1);
        label = new JLabel("Data de Emissão:");
        adicionarComponente(pnlAbastecimento,label,1,2,1,1);
        lblDataEmissaoAbastecimento = new JLabel();
        lblDataEmissaoAbastecimento.setFont(new Font("Arial",Font.PLAIN,12));
        adicionarComponente(pnlAbastecimento,lblDataEmissaoAbastecimento,1,3,1,1);
        label = new JLabel("Limite de Entrega:");
        adicionarComponente(pnlAbastecimento,label,2,2,1,1);
        lblLimiteEntregaAbastecimento = new JLabel();
        lblLimiteEntregaAbastecimento.setFont(new Font("Arial",Font.PLAIN,12));
        adicionarComponente(pnlAbastecimento,lblLimiteEntregaAbastecimento,2,3,1,1);
        label = new JLabel("Tipo de Frete:");
        adicionarComponente(pnlAbastecimento,label,3,2,1,1);
        lblTipoFrete = new JLabel();
        lblTipoFrete.setFont(new Font("Arial",Font.PLAIN,12));
        adicionarComponente(pnlAbastecimento,lblTipoFrete,3,3,1,1);
        
        JPanel pnlItens = new JPanel(new BorderLayout());
        Object[][] dadosItensAbastecimento = new Object[20][6];
        String[] colunaItensAbastecimento = {"Item","Quantidade","Valor Unit.","Total","IPI","Total + IPI"};
        tblItensAbastecimento = new JTable(dadosItensAbastecimento, colunaItensAbastecimento);
        tblItensAbastecimento.setPreferredScrollableViewportSize(new Dimension(460, 100));
        tblItensAbastecimento.addRowSelectionInterval(0,0);
        JScrollPane scroll = new JScrollPane(tblItensAbastecimento);
        pnlItens.add(scroll, BorderLayout.CENTER);
                
        JPanel pnlComandosItem = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        label = new JLabel("Total Geral: ");
        pnlComandosItem.add(label);
        lblTotalGeral = new JLabel("" + Numero.formatarValorMoeda(0.0f,"R$"));
        pnlComandosItem.add(lblTotalGeral);
        pnlItens.add(pnlComandosItem, BorderLayout.SOUTH);
        
        adicionarComponente(pnlAbastecimento,pnlItens,4,0,4,1);
        
        pnlComandosItem = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btProximo = new JButton("Próximo >>");
        btProximo.addActionListener(this);
        pnlComandosItem.add(btProximo);
        adicionarComponente(pnlAbastecimento,pnlComandosItem,5,0,4,1);        
        
        // Continuação do Formulário da Movimentação de Abastecimento
        pnlAbastecimentoConfirmacao = new JPanel(gridbag);
        pnlAbastecimentoConfirmacao.setBorder(new TitledBorder("Abastecimento"));
        label = new JLabel("Número da Requisição:");
        adicionarComponente(pnlAbastecimentoConfirmacao,label,0,0,1,1);
        lblRequisicaoAbastecimento = new JLabel();
        lblRequisicaoAbastecimento.setFont(new Font("Arial",Font.PLAIN,12));
        adicionarComponente(pnlAbastecimentoConfirmacao,lblRequisicaoAbastecimento,0,1,1,1);
        label = new JLabel("Número do Conhecimento:");
        adicionarComponente(pnlAbastecimentoConfirmacao,label,1,0,1,1);
        txtNumeroConhecimento = new JTextField(8);
        adicionarComponente(pnlAbastecimentoConfirmacao,txtNumeroConhecimento,1,1,1,1);
        label = new JLabel("Nota Fiscal:");
        adicionarComponente(pnlAbastecimentoConfirmacao,label,2,0,1,1);
        txtNotaFiscal = new JTextField(8);
        adicionarComponente(pnlAbastecimentoConfirmacao,txtNotaFiscal,2,1,1,1);
        label = new JLabel("Data de Despacho:");
        adicionarComponente(pnlAbastecimentoConfirmacao,label,0,2,1,1);
        txtDataDespacho = new JTextField(8);
        adicionarComponente(pnlAbastecimentoConfirmacao,txtDataDespacho,0,3,1,1);
        label = new JLabel("Data de Recebimento:");
        adicionarComponente(pnlAbastecimentoConfirmacao,label,1,2,1,1);
        txtDataRecebimento = new JTextField(8);
        adicionarComponente(pnlAbastecimentoConfirmacao,txtDataRecebimento,1,3,1,1);
        label = new JLabel("Valor do Conhecimento:");
        adicionarComponente(pnlAbastecimentoConfirmacao,label,2,2,1,1);
        txtValorConhecimento = new JTextField(8);
        adicionarComponente(pnlAbastecimentoConfirmacao,txtValorConhecimento,2,3,1,1);
        label = new JLabel("Observação");
        adicionarComponente(pnlAbastecimentoConfirmacao,label,3,0,4,1);
        txaObservacao = new JTextArea(3,43);
        txaObservacao.setLineWrap(true);
        txaObservacao.setWrapStyleWord(true);
        scroll = new JScrollPane(txaObservacao);
        adicionarComponente(pnlAbastecimentoConfirmacao,scroll,4,0,4,1);
        
        pnlItens = new JPanel(new BorderLayout());
        Object[][] dadosItensAbastecimentoConfirmacao = new Object[20][6];
        String[] colunasItensAbastecimentoConfirmacao = {"Código","Item","Localização","Validade","Requisitado","Abastecido"};
        
        tblItensAbastecimentoConfirmacao = new JTable(dadosItensAbastecimentoConfirmacao, colunasItensAbastecimentoConfirmacao);
        tblItensAbastecimentoConfirmacao.setPreferredScrollableViewportSize(new Dimension(550, 100));
        tblItensAbastecimentoConfirmacao.addRowSelectionInterval(0,0);
        scroll = new JScrollPane(tblItensAbastecimentoConfirmacao);
        pnlItens.add(scroll, BorderLayout.CENTER);
        
        pnlComandosItem = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btAnterior = new JButton("<< Anterior");
        btAnterior.addActionListener(this);
        pnlComandosItem.add(btAnterior);
        btDefinirLote = new JButton("Definir Lote");
        btDefinirLote.addActionListener(this);
        pnlComandosItem.add(btDefinirLote);
        pnlItens.add(pnlComandosItem, BorderLayout.SOUTH);
        
        adicionarComponente(pnlAbastecimentoConfirmacao,pnlItens,5,0,4,1);
                
        // Formulário da Movimentação de Consumo
        pnlConsumo = new JPanel(gridbag);
        label = new JLabel("Requisição Interna:");
        adicionarComponente(pnlConsumo,label,0,0,1,1);
        try
        {
            requisicoesInternasConsumo = RequisicaoInterna.carregarRequisicoesInternas(conexao,RequisicaoInterna.REQUISICAO_CONSUMO);
            cbxRequisicaoConsumo = new JComboBox(requisicoesInternasConsumo);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar as Requisições Internas. ","Erro", JOptionPane.ERROR_MESSAGE);	
            e.printStackTrace();
        }
        finally
        {
        	if(cbxRequisicaoConsumo == null)
        		cbxRequisicaoConsumo = new JComboBox();
        }
        cbxRequisicaoConsumo.addActionListener(this);
        adicionarComponente(pnlConsumo,cbxRequisicaoConsumo,0,1,3,1);
        label = new JLabel("Pedido do Cliente:");
        adicionarComponente(pnlConsumo,label,1,0,1,1);
        lblPedidoClienteConsumo = new JLabel();
        lblPedidoClienteConsumo.setFont(new Font("Arial",Font.PLAIN,12));
        adicionarComponente(pnlConsumo,lblPedidoClienteConsumo,1,1,1,1);
        label = new JLabel("Data Limite Entrega:");
        adicionarComponente(pnlConsumo,label,1,2,1,1);
        lblDataLimiteEntregaConsumo = new JLabel();
        lblDataLimiteEntregaConsumo.setFont(new Font("Arial",Font.PLAIN,12));
        adicionarComponente(pnlConsumo,lblDataLimiteEntregaConsumo,1,3,1,1);        
        label = new JLabel("Departamento Solicitante:");
        adicionarComponente(pnlConsumo,label,2,0,1,1);
        lblDepartSolicitanteConsumo = new JLabel();
        lblDepartSolicitanteConsumo.setFont(new Font("Arial",Font.PLAIN,12));
        adicionarComponente(pnlConsumo,lblDepartSolicitanteConsumo,2,1,1,1);
        label = new JLabel("Solicitante:");
        adicionarComponente(pnlConsumo,label,2,2,1,1);
        lblSolicitanteConsumo = new JLabel();
        lblSolicitanteConsumo.setFont(new Font("Arial",Font.PLAIN,12));
        adicionarComponente(pnlConsumo,lblSolicitanteConsumo,2,3,1,1);
        label = new JLabel("Justificativa");
        adicionarComponente(pnlConsumo,label,3,0,4,1);
        txaJustificativaConsumo = new JTextArea(3,43);
        txaJustificativaConsumo.setLineWrap(true);
        txaJustificativaConsumo.setWrapStyleWord(true);
        scroll = new JScrollPane(txaJustificativaConsumo);
        adicionarComponente(pnlConsumo,scroll,4,0,4,1);
        
        pnlItens = new JPanel(new BorderLayout());
        Object[][] dadosItensConsumo = new Object[100][3];
        String[] colunasItensConsumo = {"Código","Item","Quantidade"};
        
        tblItensConsumo = new JTable(dadosItensConsumo, colunasItensConsumo);
        tblItensConsumo.setPreferredScrollableViewportSize(new Dimension(460, 100));
        tblItensConsumo.addRowSelectionInterval(0,0);
        scroll = new JScrollPane(tblItensConsumo);
        pnlItens.add(scroll, BorderLayout.CENTER);
        adicionarComponente(pnlConsumo,pnlItens,5,0,4,1);
        pnlConsumo.setBorder(new TitledBorder("Consumo"));
        
        // Formulário da Movimentação de Vendas
        pnlVendas = new JPanel(gridbag);
        label = new JLabel("Requisição Interna:");
        adicionarComponente(pnlVendas,label,0,0,1,1);
        try
        {
            requisicoesInternasVendas = RequisicaoInterna.carregarRequisicoesInternas(conexao,RequisicaoInterna.REQUISICAO_VENDAS);
        	cbxRequisicaoVenda = new JComboBox(requisicoesInternasVendas);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar as Requisições Internas. ","Erro", JOptionPane.ERROR_MESSAGE);	
            e.printStackTrace();
        }
        finally
        {
        	if(cbxRequisicaoVenda == null)
        		cbxRequisicaoVenda = new JComboBox();
        }
        cbxRequisicaoVenda.addActionListener(this);
        adicionarComponente(pnlVendas,cbxRequisicaoVenda,0,1,3,1);
        label = new JLabel("Data Limite Entrega:");
        adicionarComponente(pnlVendas,label,1,0,1,1);
        lblDataLimiteEntregaVendas = new JLabel();
        lblDataLimiteEntregaVendas.setFont(new Font("Arial",Font.PLAIN,12));
        adicionarComponente(pnlVendas,lblDataLimiteEntregaVendas,1,1,1,1);
        label = new JLabel("Data de Emissão:");
        adicionarComponente(pnlVendas,label,1,2,1,1);
        lblDataEmissaoVendas = new JLabel();
        lblDataEmissaoVendas.setFont(new Font("Arial",Font.PLAIN,12));
        adicionarComponente(pnlVendas,lblDataEmissaoVendas,1,3,1,1);
        label = new JLabel("Departamento Solicitante:");
        adicionarComponente(pnlVendas,label,2,0,1,1);
        lblDepartSolicitanteVendas = new JLabel();
        lblDepartSolicitanteVendas.setFont(new Font("Arial",Font.PLAIN,12));
        adicionarComponente(pnlVendas,lblDepartSolicitanteVendas,2,1,1,1);
        label = new JLabel("Solicitante:");
        adicionarComponente(pnlVendas,label,2,2,1,1);
        lblSolicitanteVendas = new JLabel();
        lblSolicitanteVendas.setFont(new Font("Arial",Font.PLAIN,12));
        adicionarComponente(pnlVendas,lblSolicitanteVendas,2,3,1,1);
                
        pnlItens = new JPanel(new BorderLayout());
        Object[][] dadosItensVenda = new Object[20][3];
        String[] colunasItensVenda = {"Código","Item","Quantidade"};
        
        tblItensVenda = new JTable(dadosItensVenda, colunasItensVenda);
        tblItensVenda.setPreferredScrollableViewportSize(new Dimension(460, 100));
        tblItensVenda.addRowSelectionInterval(0,0);
        scroll = new JScrollPane(tblItensVenda);
        pnlItens.add(scroll, BorderLayout.CENTER);
        adicionarComponente(pnlVendas,pnlItens,3,0,4,1);
        
    	pnlVendas.setBorder(new TitledBorder("Vendas"));
        
        // Formulário da Movimentação de Descarte
        pnlDescarte = new JPanel(gridbag);
        label = new JLabel("Requisição Interna:");
        adicionarComponente(pnlDescarte,label,0,0,1,1);
        
        try
        {
            requisicoesInternasDescarte = RequisicaoInterna.carregarRequisicoesInternas(conexao,RequisicaoInterna.REQUISICAO_DESCARTE);
            cbxRequisicaoDescarte = new JComboBox(requisicoesInternasDescarte);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar as Requisições Internas. ","Erro", JOptionPane.ERROR_MESSAGE);	
            e.printStackTrace();
        }
        finally
        {
        	if(cbxRequisicaoDescarte == null)
        		cbxRequisicaoDescarte = new JComboBox();
        }
        cbxRequisicaoDescarte.addActionListener(this);
        adicionarComponente(pnlDescarte,cbxRequisicaoDescarte,0,1,3,1);        
        label = new JLabel("Data Limite Entrega:");
        adicionarComponente(pnlDescarte,label,1,0,1,1);
        lblDataLimiteEntregaDescarte = new JLabel();
        lblDataLimiteEntregaDescarte.setFont(new Font("Arial",Font.PLAIN,12));
        adicionarComponente(pnlDescarte,lblDataLimiteEntregaDescarte,1,1,1,1);
        label = new JLabel("Data de Emissão:");
        adicionarComponente(pnlDescarte,label,1,2,1,1);
        lblDataEmissaoDescarte = new JLabel();
        lblDataEmissaoDescarte.setFont(new Font("Arial",Font.PLAIN,12));
        adicionarComponente(pnlDescarte,lblDataEmissaoDescarte,1,3,1,1);
        label = new JLabel("Departamento Solicitante:");
        adicionarComponente(pnlDescarte,label,2,0,1,1);
        lblDepartSolicitanteDescarte = new JLabel();
        lblDepartSolicitanteDescarte.setFont(new Font("Arial",Font.PLAIN,12));
        adicionarComponente(pnlDescarte,lblDepartSolicitanteDescarte,2,1,1,1);
        label = new JLabel("Solicitante:");
        adicionarComponente(pnlDescarte,label,2,2,1,1);
        lblSolicitanteDescarte = new JLabel();
        lblSolicitanteDescarte.setFont(new Font("Arial",Font.PLAIN,12));
        adicionarComponente(pnlDescarte,lblSolicitanteDescarte,2,3,1,1);
        label = new JLabel("Justificativa");
        adicionarComponente(pnlDescarte,label,3,0,4,1);
        txaJustificativaDescarte = new JTextArea(3,43);
        txaJustificativaDescarte.setLineWrap(true);
        txaJustificativaDescarte.setWrapStyleWord(true);
        scroll = new JScrollPane(txaJustificativaDescarte);
        adicionarComponente(pnlDescarte,scroll,4,0,4,1);
        
        pnlItens = new JPanel(new BorderLayout());
        Object[][] dadosItensDescarte = new Object[20][3];
        String[] colunasItensDescarte = {"Código","Item","Quantidade"};
        
        tblItensDescarte = new JTable(dadosItensDescarte, colunasItensDescarte);
        tblItensDescarte.setPreferredScrollableViewportSize(new Dimension(460, 100));
        tblItensDescarte.addRowSelectionInterval(0,0);
        scroll = new JScrollPane(tblItensDescarte);
        pnlItens.add(scroll, BorderLayout.CENTER);
        adicionarComponente(pnlDescarte,pnlItens,6,0,4,1);
        
        pnlDescarte.setBorder(new TitledBorder("Descarte"));
        
        // Formulário da Movimentação de Devolução
        pnlDevolucao = new JPanel(gridbag);
        
        label = new JLabel("Requisição Interna:");
        adicionarComponente(pnlDevolucao,label,0,0,1,1);
        try
        {
        	requisicoesDevolucao = RequisicaoInterna.carregarRequisicoesInternas(aplicacao.obterConexao(),RequisicaoInterna.REQUISICAO_DEVOLUCAO);
        	cbxRequisicaoDevolucao = new JComboBox(requisicoesDevolucao);
        }
        catch(Exception e)
        {
        	JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar as Requisições de Devolução.","Erro",JOptionPane.ERROR_MESSAGE);
        	e.printStackTrace();
        }
        finally
        {
        	if(	cbxRequisicaoDevolucao == null)
        		cbxRequisicaoDevolucao = new JComboBox();
        }
        cbxRequisicaoDevolucao.addActionListener(this);
        adicionarComponente(pnlDevolucao,cbxRequisicaoDevolucao,0,1,1,1);
        label = new JLabel("Data de Emissão:");
        adicionarComponente(pnlDevolucao,label,0,2,1,1);
        lblDataEmissaoDevolucao = new JLabel("");
        lblDataEmissaoDevolucao.setFont(new Font("Arial",Font.PLAIN,12));
        adicionarComponente(pnlDevolucao,lblDataEmissaoDevolucao,0,3,1,1);
        label = new JLabel("Data Limite Entrega:");
        adicionarComponente(pnlDevolucao,label,1,0,1,1);
        lblDataLimiteEntregaDevolucao = new JLabel("");
        lblDataLimiteEntregaDevolucao.setFont(new Font("Arial",Font.PLAIN,12));
        adicionarComponente(pnlDevolucao,lblDataLimiteEntregaDevolucao,1,1,1,1);
        label = new JLabel("Departamento Solicitante:");
        adicionarComponente(pnlDevolucao,label,2,0,1,1);
        lblDepartSolicitanteDevolucao = new JLabel("");
        lblDepartSolicitanteDevolucao.setFont(new Font("Arial",Font.PLAIN,12));
        adicionarComponente(pnlDevolucao,lblDepartSolicitanteDevolucao,2,1,1,1);
        label = new JLabel("Solicitante:");
        adicionarComponente(pnlDevolucao,label,2,2,1,1);
        lblSolicitanteDevolucao = new JLabel("");
        lblSolicitanteDevolucao.setFont(new Font("Arial",Font.PLAIN,12));
        adicionarComponente(pnlDevolucao,lblSolicitanteDevolucao,2,3,1,1);
        label = new JLabel("Justificativa");
        adicionarComponente(pnlDevolucao,label,3,0,4,1);
        txaJustificativaDevolucao = new JTextArea(3,43);
        txaJustificativaDevolucao.setLineWrap(true);
        txaJustificativaDevolucao.setWrapStyleWord(true);
        scroll = new JScrollPane(txaJustificativaDevolucao);
        adicionarComponente(pnlDevolucao,scroll,4,0,4,1);
        
        pnlItens = new JPanel(new BorderLayout());
        Object[][] dadosItensDevolucao = new Object[20][3];
        String[] colunasItensDevolucao = {"Código","Item","Quantidade"};
        
        tblItensDevolucao = new JTable(dadosItensDevolucao, colunasItensDevolucao);
        tblItensDevolucao.setPreferredScrollableViewportSize(new Dimension(460, 100));
        tblItensDevolucao.addRowSelectionInterval(0,0);
        scroll = new JScrollPane(tblItensDevolucao);
        pnlItens.add(scroll, BorderLayout.CENTER);
        adicionarComponente(pnlDevolucao,pnlItens,5,0,4,1);
        
        pnlDevolucao.setBorder(new TitledBorder("Devolução"));
        
        // Formulário da Movimentação de Devolução Externa
        pnlDevolucaoExterna = new JPanel(gridbag);
        label = new JLabel("Requisição Interna:");
        adicionarComponente(pnlDevolucaoExterna,label,0,0,4,1);
        try
        {
            requisicoesInternasDevolucaoExterna = RequisicaoInterna.carregarRequisicoesInternas(conexao,RequisicaoInterna.REQUISICAO_DEVOLUCAO_EXTERNA);
            cbxRequisicaoDevolucaoExterna = new JComboBox(requisicoesInternasDevolucaoExterna);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar as Requisições Internas. ","Erro", JOptionPane.ERROR_MESSAGE);	
            e.printStackTrace();
        }
        finally
        {
        	if(cbxRequisicaoDevolucaoExterna == null)
	        	cbxRequisicaoDevolucaoExterna = new JComboBox();
        }
        cbxRequisicaoDevolucaoExterna.addActionListener(this);
        adicionarComponente(pnlDevolucaoExterna,cbxRequisicaoDevolucaoExterna,0,1,4,1);
        label = new JLabel("Data Limite Entrega:");
        adicionarComponente(pnlDevolucaoExterna,label,1,0,1,1);
        lblDataLimiteEntregaDevolucaoExterna = new JLabel();
        lblDataLimiteEntregaDevolucaoExterna.setFont(new Font("Arial",Font.PLAIN,12));
        adicionarComponente(pnlDevolucaoExterna,lblDataLimiteEntregaDevolucaoExterna,1,1,1,1);
        label = new JLabel("Data de Emissão:");
        adicionarComponente(pnlDevolucaoExterna,label,1,2,1,1);
        lblDataEmissaoDevolucaoExterna = new JLabel();
        lblDataEmissaoDevolucaoExterna.setFont(new Font("Arial",Font.PLAIN,12));
        adicionarComponente(pnlDevolucaoExterna,lblDataEmissaoDevolucaoExterna,1,3,1,1);
        label = new JLabel("Departamento Solicitante:");
        adicionarComponente(pnlDevolucaoExterna,label,2,0,1,1);
        lblDepartSolicitanteDevolucaoExterna = new JLabel();
        lblDepartSolicitanteDevolucaoExterna.setFont(new Font("Arial",Font.PLAIN,12));
        adicionarComponente(pnlDevolucaoExterna,lblDepartSolicitanteDevolucaoExterna,2,1,1,1);
        label = new JLabel("Solicitante:");
        adicionarComponente(pnlDevolucaoExterna,label,2,2,1,1);
        lblSolicitanteDevolucaoExterna = new JLabel();
        lblSolicitanteDevolucaoExterna.setFont(new Font("Arial",Font.PLAIN,12));
        adicionarComponente(pnlDevolucaoExterna,lblSolicitanteDevolucaoExterna,2,3,1,1);
        label = new JLabel("Justificativa");
        adicionarComponente(pnlDevolucaoExterna,label,3,0,4,1);
        txaJustificativaDevolucaoExterna = new JTextArea(3,43);
        txaJustificativaDevolucaoExterna.setLineWrap(true);
        txaJustificativaDevolucaoExterna.setWrapStyleWord(true);
        scroll = new JScrollPane(txaJustificativaDevolucaoExterna);
        adicionarComponente(pnlDevolucaoExterna,scroll,4,0,4,1);
        
        pnlItens = new JPanel(new BorderLayout());
        Object[][] dadosItensDevolucaoExterna = new Object[20][3];
        String[] colunasItensDevolucaoExterna = {"Código","Item","Quantidade"};
        
        tblItensDevolucaoExterna = new JTable(dadosItensDevolucaoExterna, colunasItensDevolucaoExterna);
        tblItensDevolucaoExterna.setPreferredScrollableViewportSize(new Dimension(460, 100));
        tblItensDevolucaoExterna.addRowSelectionInterval(0,0);
        scroll = new JScrollPane(tblItensDevolucaoExterna);
        pnlItens.add(scroll, BorderLayout.CENTER);
        adicionarComponente(pnlDevolucaoExterna,pnlItens,6,0,4,1);
        pnlDevolucaoExterna.setBorder(new TitledBorder("Devolução Externa"));
        
        // Formulário da Movimentação de Depósito
        pnlDeposito = new JPanel(gridbag);
        label = new JLabel("Requisição Interna:");
        adicionarComponente(pnlDeposito,label,0,0,1,1);
        cbxRequisicaoDeposito = new JComboBox();
        cbxRequisicaoDeposito.addItem("Selecione...");
        adicionarComponente(pnlDeposito,cbxRequisicaoDeposito,0,1,1,1);
        label = new JLabel("Data de Emissão:");
        adicionarComponente(pnlDeposito,label,0,2,1,1);
        lblDataEmissaoDeposito = new JLabel("");
        adicionarComponente(pnlDeposito,lblDataEmissaoDeposito,0,3,1,1);
        label = new JLabel("Data Limite Entrega:");
        adicionarComponente(pnlDeposito,label,1,0,1,1);
        lblDataLimiteEntregaDeposito = new JLabel("");
        adicionarComponente(pnlDeposito,lblDataLimiteEntregaDeposito,1,1,1,1);
        label = new JLabel("Hora Limite Entrega:");
        adicionarComponente(pnlDeposito,label,1,2,1,1);
        lblHoraLimiteEntregaDeposito = new JLabel("");
        adicionarComponente(pnlDeposito,lblHoraLimiteEntregaDeposito,1,3,1,1);
        label = new JLabel("Departamento Solicitante:");
        adicionarComponente(pnlDeposito,label,2,0,1,1);
        lblDepartSolicitanteDeposito = new JLabel("");
        adicionarComponente(pnlDeposito,lblDepartSolicitanteDeposito,2,1,1,1);
        label = new JLabel("Solicitante:");
        adicionarComponente(pnlDeposito,label,2,2,1,1);
        lblSolicitanteDeposito = new JLabel("");
        adicionarComponente(pnlDeposito,lblSolicitanteDeposito,2,3,1,1);
        label = new JLabel("Descrição:");
        adicionarComponente(pnlDeposito,label,3,0,1,1);
        lblDescricaoDeposito = new JLabel("");
        adicionarComponente(pnlDeposito,lblDescricaoDeposito,3,1,1,1);
        label = new JLabel("Justificativa");
        adicionarComponente(pnlDeposito,label,4,0,4,1);
        txaJustificativaDeposito = new JTextArea(3,43);
        txaJustificativaDeposito.setLineWrap(true);
        txaJustificativaDeposito.setWrapStyleWord(true);
        scroll = new JScrollPane(txaJustificativaDeposito);
        adicionarComponente(pnlDeposito,scroll,5,0,4,1);
        pnlDeposito.setBorder(new TitledBorder("Depósito"));
        
        // Formulário da Movimentação de Retirada do Depósito
        pnlRetiradaDeposito = new JPanel(gridbag);
        label = new JLabel("Requisição Interna:");
        adicionarComponente(pnlRetiradaDeposito,label,0,0,1,1);
        cbxRequisicaoRetiradaDeposito = new JComboBox();
        cbxRequisicaoRetiradaDeposito.addItem("Selecione...");
        adicionarComponente(pnlRetiradaDeposito,cbxRequisicaoRetiradaDeposito,0,1,1,1);
        label = new JLabel("Data de Emissão:");
        adicionarComponente(pnlRetiradaDeposito,label,0,2,1,1);
        lblDataEmissaoRetiradaDeposito = new JLabel("");
        adicionarComponente(pnlRetiradaDeposito,lblDataEmissaoRetiradaDeposito,0,3,1,1);
        label = new JLabel("Data Limite Entrega:");
        adicionarComponente(pnlRetiradaDeposito,label,1,0,1,1);
        lblDataLimiteEntregaRetiradaDeposito = new JLabel("");
        adicionarComponente(pnlRetiradaDeposito,lblDataLimiteEntregaRetiradaDeposito,1,1,1,1);
        label = new JLabel("Hora Limite Entrega:");
        adicionarComponente(pnlRetiradaDeposito,label,1,2,1,1);
        lblHoraLimiteEntregaRetiradaDeposito = new JLabel("");
        adicionarComponente(pnlRetiradaDeposito,lblHoraLimiteEntregaRetiradaDeposito,1,3,1,1);
        label = new JLabel("Departamento Solicitante:");
        adicionarComponente(pnlRetiradaDeposito,label,2,0,1,1);
        lblDepartSolicitanteRetiradaDeposito = new JLabel("");
        adicionarComponente(pnlRetiradaDeposito,lblDepartSolicitanteRetiradaDeposito,2,1,1,1);
        label = new JLabel("Solicitante:");
        adicionarComponente(pnlRetiradaDeposito,label,2,2,1,1);
        lblSolicitanteRetiradaDeposito = new JLabel("");
        adicionarComponente(pnlRetiradaDeposito,lblSolicitanteRetiradaDeposito,2,3,1,1);
        label = new JLabel("Descrição:");
        adicionarComponente(pnlRetiradaDeposito,label,3,0,1,1);
        lblDescricaoRetiradaDeposito = new JLabel("");
        adicionarComponente(pnlRetiradaDeposito,lblDescricaoRetiradaDeposito,3,1,1,1);
        label = new JLabel("Justificativa");
        adicionarComponente(pnlRetiradaDeposito,label,4,0,4,1);
        txaJustificativaRetiradaDeposito = new JTextArea(3,43);
        txaJustificativaRetiradaDeposito.setLineWrap(true);
        txaJustificativaRetiradaDeposito.setWrapStyleWord(true);
        scroll = new JScrollPane(txaJustificativaRetiradaDeposito);
        adicionarComponente(pnlRetiradaDeposito,scroll,5,0,4,1);
        pnlRetiradaDeposito.setBorder(new TitledBorder("Retirada do Depósito"));

        JPanel pnlBranco = new JPanel();
        pnlAreaMovimentacao.add(pnlBranco,"Selecione...");
        pnlAreaMovimentacao.add(pnlAbastecimento,"Abastecimento");
        pnlAreaMovimentacao.add(pnlAbastecimentoConfirmacao,"Abastecimento Confirmação");
        pnlAreaMovimentacao.add(pnlConsumo,"Consumo");
        pnlAreaMovimentacao.add(pnlVendas,"Vendas"); 
        pnlAreaMovimentacao.add(pnlDescarte,"Descarte");
        pnlAreaMovimentacao.add(pnlDevolucao,"Devolução");
        pnlAreaMovimentacao.add(pnlDevolucaoExterna,"Devolução Externa");
        pnlAreaMovimentacao.add(pnlDeposito,"Depósito");
        pnlAreaMovimentacao.add(pnlRetiradaDeposito,"Retirada do Depósito");

        pnlAreaDados.add(pnlAreaMovimentacao, BorderLayout.CENTER);

        conteudo.add(pnlAreaDados,BorderLayout.CENTER);

        JPanel pnlComandos = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        btConfirmar = new JButton("Confirmar");
        btConfirmar.addActionListener(this);
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

    private void carregarRequisicoesCompra()
    {
        cbxRequisicaoAbastecimento.removeAllItems();
        cbxRequisicaoAbastecimento.addItem("Selecione...");
        int codigoRequisicaoCompra;        
        int fornecedor;

        for(int i = 1;i < this.requisicoesCompra.size();i++)
        {                
            cbxRequisicaoAbastecimento.addItem(""+ ((RequisicaoCompra)requisicoesCompra.get(i)).obterCodigo() + " - "+ 
                                                   ((RequisicaoCompra)requisicoesCompra.get(i)).obterFornecedor().obterRazaoSocial());
        }        
    }
    
    private void carregarItensRequisicaoCompra()
    {                    
        for(int i = 0; i < itensRequisicaoCompra.size();i++)
        {
            tblItensAbastecimento.setValueAt("",i,0);
            tblItensAbastecimento.setValueAt("",i,1);
            tblItensAbastecimento.setValueAt("",i,2);
            tblItensAbastecimento.setValueAt("",i,3);
            tblItensAbastecimento.setValueAt("",i,4);
            tblItensAbastecimento.setValueAt("",i,5);
            tblItensAbastecimentoConfirmacao.setValueAt("",i,0);
            tblItensAbastecimentoConfirmacao.setValueAt("",i,1);
            tblItensAbastecimentoConfirmacao.setValueAt("",i,2);
            tblItensAbastecimentoConfirmacao.setValueAt("",i,3);
            tblItensAbastecimentoConfirmacao.setValueAt("",i,4);
            tblItensAbastecimentoConfirmacao.setValueAt("",i,5);
        }
         
        try
        {
            requisicaoCompraSelecionada = (RequisicaoCompra)requisicoesCompra.get(cbxRequisicaoAbastecimento.getSelectedIndex());
            requisicaoCompraSelecionada.carregarItensRequisicao(aplicacao.obterConexao());
            itensRequisicaoCompra = requisicaoCompraSelecionada.obterItensRequisicao();
            ItemRequisicao itemRequisicao;
            float totalGeral = 0;
            for(int i = 0;i < itensRequisicaoCompra.size();i++)
            {
                itemRequisicao = (ItemRequisicao)itensRequisicaoCompra.get(i);
                tblItensAbastecimento.setValueAt(itemRequisicao.obterItem().obterDescricao(),i,0);
                tblItensAbastecimento.setValueAt(Numero.inverterSeparador(""+ itemRequisicao.getQuantidadeItem()),i,1);
                tblItensAbastecimento.setValueAt(Numero.inverterSeparador(""+ itemRequisicao.obterValorUnitario()),i,2);
                tblItensAbastecimento.setValueAt(Numero.inverterSeparador(""+ itemRequisicao.obterValorTotal()),i,3);
                tblItensAbastecimento.setValueAt(""+ itemRequisicao.obterPercentualIPI(),i,4);
                tblItensAbastecimento.setValueAt(Numero.inverterSeparador(""+ itemRequisicao.obterValorTotalComIPI()),i,5);
                totalGeral += itemRequisicao.obterValorTotalComIPI();
                lblTotalGeral.setText(Numero.inverterSeparador("" + totalGeral));
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarItensRequisicaoInternaVendas()
    {                    
        for(int i = 0; i < numeroItens;i++)
        {
            tblItensVenda.setValueAt("",i,0);   
            tblItensVenda.setValueAt("",i,1);
            tblItensVenda.setValueAt("",i,2);
        }
                
        numeroItens = 0;
        requisicaoInterna = (RequisicaoInterna)requisicoesInternasVendas.get(cbxRequisicaoVenda.getSelectedIndex());
        String query = "select iri.item,i.descricao,iri.quantidade, d.codigo, d.departamento, iri.status " +
                       "from item_requisicao_interna iri,item i, departamento d " +
                       "where iri.item = i.codigo and d.codigo = iri.destino and iri.requisicao_interna = "+ requisicaoInterna.obterCodigo() + " " + 
                       "order by iri.item asc";
        try
        {
            ResultSet resultadoItensRequisicaoInterna = conexao.executarConsulta(query);           
            try
            {
                while(resultadoItensRequisicaoInterna.next())
                {
                    int codigoItem = resultadoItensRequisicaoInterna.getInt("item");
                    String item = resultadoItensRequisicaoInterna.getString("descricao");
                    float quantidadeItem = resultadoItensRequisicaoInterna.getFloat("quantidade");
                    itensRequisicaoInterna.addElement(new ItemRequisicaoInterna(new Item(codigoItem,item),this.requisicaoInterna,quantidadeItem,new Departamento(resultadoItensRequisicaoInterna.getInt("codigo"),resultadoItensRequisicaoInterna.getString("departamento")),resultadoItensRequisicaoInterna.getString("status")));
                    
                    tblItensVenda.setValueAt(""+codigoItem,numeroItens,0);
                    tblItensVenda.setValueAt(item,numeroItens,1);
                    tblItensVenda.setValueAt(Numero.inverterSeparador(""+quantidadeItem),numeroItens,2);
                    numeroItens++;
                }
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);           
                e.printStackTrace();
            }
            resultadoItensRequisicaoInterna.close();
            resultadoItensRequisicaoInterna = null;
        }
        catch(SQLException e){e.printStackTrace();}
    }
    
    public void carregarItensRequisicaoInternaDevolucaoExterna()
    {                    
        for(int i = 0; i < numeroItens;i++)
        {
            tblItensDevolucaoExterna.setValueAt("",i,0);   
            tblItensDevolucaoExterna.setValueAt("",i,1);
            tblItensDevolucaoExterna.setValueAt("",i,2);
        }
                
        numeroItens = 0;
        requisicaoInterna = (RequisicaoInterna)requisicoesInternasDevolucaoExterna.get(cbxRequisicaoDevolucaoExterna.getSelectedIndex());
        String query = "select iri.item,i.descricao,iri.quantidade, d.codigo, d.departamento " +
                       "from item_requisicao_interna iri,item i, departamento d " +
                       "where iri.item = i.codigo and d.codigo = iri.destino and iri.requisicao_interna = "+ requisicaoInterna.obterCodigo() + " " + 
                       "order by iri.item asc";
        try
        {
            ResultSet resultadoItensRequisicaoInterna = conexao.executarConsulta(query);           
            try
            {
                while(resultadoItensRequisicaoInterna.next())
                {
                    int codigoItem = resultadoItensRequisicaoInterna.getInt("item");
                    String item = resultadoItensRequisicaoInterna.getString("descricao");
                    float quantidadeItem = resultadoItensRequisicaoInterna.getFloat("quantidade");
                    itensRequisicaoInterna.addElement(new ItemRequisicaoInterna(new Item(codigoItem,item),this.requisicaoInterna,quantidadeItem,new Departamento(resultadoItensRequisicaoInterna.getInt("codigo"),resultadoItensRequisicaoInterna.getString("departamento")),resultadoItensRequisicaoInterna.getString("status")));
                    
                    tblItensDevolucaoExterna.setValueAt(""+codigoItem,numeroItens,0);
                    tblItensDevolucaoExterna.setValueAt(item,numeroItens,1);
                    tblItensDevolucaoExterna.setValueAt(Numero.inverterSeparador(""+quantidadeItem),numeroItens,2);
                    numeroItens++;
                }
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);           
                e.printStackTrace();
            }
            resultadoItensRequisicaoInterna.close();
            resultadoItensRequisicaoInterna = null;
        }
        catch(SQLException e)
        {            
            e.printStackTrace();
        }
    }
    
    public void carregarDadosRequisicaoCompra()
    {
        requisicaoCompraSelecionada = (RequisicaoCompra)requisicoesCompra.get(cbxRequisicaoAbastecimento.getSelectedIndex());
        String query = "select observacao, fornecedor.razao_social as razao_social from requisicao_compra, fornecedor where requisicao_compra.codigo = "+ requisicaoCompraSelecionada.obterCodigo() +" and fornecedor.codigo = requisicao_compra.fornecedor ";
        try
        {            
            ResultSet dadosRequisicaoCompra = conexao.executarConsulta(query);
            if(dadosRequisicaoCompra.next())
            {
                observacao = dadosRequisicaoCompra.getString("observacao");
                txaObservacao.setText(observacao);
                lblRequisicaoAbastecimento.setText(""+ requisicaoCompraSelecionada.obterCodigo() + " - "+ dadosRequisicaoCompra.getString("razao_social"));
                for(int i = 0; i < itensRequisicaoCompra.size(); i++)
                {
                    tblItensAbastecimentoConfirmacao.setValueAt(""+((ItemRequisicao)itensRequisicaoCompra.get(i)).obterItem().obterCodigo(),i,0);
                    tblItensAbastecimentoConfirmacao.setValueAt(""+((ItemRequisicao)itensRequisicaoCompra.get(i)).obterItem().obterDescricao(),i,1);
                    tblItensAbastecimentoConfirmacao.setValueAt(""+((ItemRequisicao)itensRequisicaoCompra.get(i)).getQuantidadeItem(),i,4);
                    tblItensAbastecimentoConfirmacao.setValueAt(""+((ItemRequisicao)itensRequisicaoCompra.get(i)).getQuantidadeAbastecida(),i,5);
                }
            }
            dadosRequisicaoCompra.close();
            dadosRequisicaoCompra = null;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public void atualizarTabelaItensAbastecimentoConfirmacao()
    {        
        try
        {            
            ItemRequisicao itemRequisicao = null;
            for(int i = 0;i < itensRequisicaoCompra.size();i++)
            {
                itemRequisicao = (ItemRequisicao)itensRequisicaoCompra.get(i);
                if(itemRequisicao.obterItem().obterLote() != null)
                {
                    tblItensAbastecimentoConfirmacao.setValueAt(""+ itemRequisicao.obterItem().obterLote().obterLocalizacao(),i,2);
                    tblItensAbastecimentoConfirmacao.setValueAt(""+ itemRequisicao.obterItem().obterLote().obterDataValidade(),i,3);
                    tblItensAbastecimentoConfirmacao.setValueAt(""+ itemRequisicao.getQuantidadeItem(),i,4);
                    tblItensAbastecimentoConfirmacao.setValueAt(""+ itemRequisicao.getQuantidadeAbastecida(),i,5);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }        
    }
    
    public void actionPerformed(java.awt.event.ActionEvent actionEvent) 
    {
        Object objeto = actionEvent.getSource();
        
        // Abastecimento do estoque com base nas requisições de compra.
        if(objeto == cbxRequisicaoAbastecimento)
        {
            if(cbxRequisicaoAbastecimento.getSelectedIndex() > 0)
            {
                txtDataDespacho.setText("");
                txtNumeroConhecimento.setText("");
                txtDataRecebimento.setText("");
                txtNotaFiscal.setText("");
                txtValorConhecimento.setText("");
                txaObservacao.setText("");
                requisicaoCompraSelecionada = (RequisicaoCompra)requisicoesCompra.get(cbxRequisicaoAbastecimento.getSelectedIndex());
                String query = "select fornecedor.razao_social, departamento.departamento, transportadora.transportadora, data_emissao, data_limite_entrega, (case tipo_frete when 'C' then 'CIF' when 'F' then 'FOB' when 'P' then 'PRÓPRIO' end) as tipo_frete from requisicao_compra, fornecedor,departamento, transportadora where requisicao_compra.fornecedor = fornecedor.codigo and requisicao_compra.departamento_solicitante = departamento.codigo and requisicao_compra.transportadora *= transportadora.codigo and requisicao_compra.codigo = "+ requisicaoCompraSelecionada.obterCodigo() +" ";
                try
                {
                    ResultSet dadosRequisicaoCompra = conexao.executarConsulta(query);
                    if(dadosRequisicaoCompra.next())
                    {
                        razaoSocial = dadosRequisicaoCompra.getString("razao_social");
                        lblFornecedor.setText(razaoSocial);
                        departamentoSolicitante = dadosRequisicaoCompra.getString("departamento");
                        lblDepartamentoSolicitante.setText(departamentoSolicitante);
                        transportadora = dadosRequisicaoCompra.getString("transportadora");
                        lblTransportadora.setText(transportadora);
                        dataEmissao = Calendario.ajustarFormatoDataBanco(dadosRequisicaoCompra.getString("data_emissao")); 
                        lblDataEmissaoAbastecimento.setText(dataEmissao);
                        dataLimiteEntrega = Calendario.ajustarFormatoDataBanco(dadosRequisicaoCompra.getString("data_limite_entrega"));
                        lblLimiteEntregaAbastecimento.setText(dataLimiteEntrega);
                        tipoFrete = dadosRequisicaoCompra.getString("tipo_frete");
                        lblTipoFrete.setText(tipoFrete);
                    }
                    dadosRequisicaoCompra.close();
                    dadosRequisicaoCompra = null;
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }    
                this.pack();
                carregarItensRequisicaoCompra();
                carregarDadosRequisicaoCompra();
            }
        }
        
        if(objeto == cbxRequisicaoConsumo)
        {            
            if(cbxRequisicaoConsumo.getSelectedIndex() > 0)
            {
                if(itensRequisicaoInterna != null)
                    itensRequisicaoInterna.removeAllElements();
                requisicaoInterna = (RequisicaoInterna)requisicoesInternasConsumo.get(cbxRequisicaoConsumo.getSelectedIndex());
        
		        lblPedidoClienteConsumo.setText((requisicaoInterna.obterPedidoCliente() != null)?"" + requisicaoInterna.obterPedidoCliente().obterOrdemCompra():"");
		        lblDataLimiteEntregaConsumo.setText(requisicaoInterna.obterDataLimiteEntrega());
		        lblDepartSolicitanteConsumo.setText(requisicaoInterna.obterDepartamento().obterNomeDepartamento());
		        lblSolicitanteConsumo.setText(requisicaoInterna.obterSolicitante().getNome());
		        txaJustificativaConsumo.setText(requisicaoInterna.obterJustificativa());
		        
		        for(int i = 0; i < numeroItens;i++)
		        {
		            tblItensConsumo.setValueAt("",i,0);
		            tblItensConsumo.setValueAt("",i,1);
		            tblItensConsumo.setValueAt("",i,2);
		        }
		        try
		        {
			        itensRequisicaoInterna = requisicaoInterna.carregarItensRequisicaoInterna(aplicacao.obterConexao());
			        numeroItens = 0;
			        ItemRequisicaoInterna itemRequisicaoInterna;
			        for(int i = 0;i < itensRequisicaoInterna.size();i++)
			        {
			            itemRequisicaoInterna = (ItemRequisicaoInterna)itensRequisicaoInterna.get(i);
			            
			            tblItensConsumo.setValueAt(""+ itemRequisicaoInterna.obterItem().obterCodigo(),numeroItens,0);
			            tblItensConsumo.setValueAt(itemRequisicaoInterna.obterItem().obterDescricao(),numeroItens,1);
			            tblItensConsumo.setValueAt(Numero.inverterSeparador(""+ itemRequisicaoInterna.obterQuantidadeItem()),numeroItens,2);
			            numeroItens++;
			        }
		        }
		        catch(Exception e)
		        {
		        	JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar os itens da Requisição Interna.","Erro",JOptionPane.ERROR_MESSAGE);
		        	e.printStackTrace();
		        }
                redimencionar();
            }
        }
        
        if(objeto == cbxRequisicaoVenda)
        {
            if(cbxRequisicaoVenda.getSelectedIndex() > 0)
            {
                if(itensRequisicaoInterna != null)
                    itensRequisicaoInterna.removeAllElements();
                requisicaoInterna = (RequisicaoInterna)requisicoesInternasVendas.get(cbxRequisicaoVenda.getSelectedIndex());
                
                lblDataEmissaoVendas.setText(requisicaoInterna.obterDataEmissao());
                lblDataLimiteEntregaVendas.setText(requisicaoInterna.obterDataLimiteEntrega());
                lblDepartSolicitanteVendas.setText(requisicaoInterna.obterDepartamento().obterNomeDepartamento());
                lblSolicitanteVendas.setText(requisicaoInterna.obterSolicitante().getNome());
                
                for(int i = 0; i < numeroItens;i++)
                {
                    tblItensVenda.setValueAt("",i,0);
                    tblItensVenda.setValueAt("",i,1);
                    tblItensVenda.setValueAt("",i,2);
                }
                try
                {
                    itensRequisicaoInterna = requisicaoInterna.carregarItensRequisicaoInterna(aplicacao.obterConexao());
                    numeroItens = 0;
                    ItemRequisicaoInterna itemRequisicaoInterna;
                    for(int i = 0;i < itensRequisicaoInterna.size();i++)
                    {
                        itemRequisicaoInterna = (ItemRequisicaoInterna)itensRequisicaoInterna.get(i);
                        
                        tblItensVenda.setValueAt(""+ itemRequisicaoInterna.obterItem().obterCodigo(),numeroItens,0);
                        tblItensVenda.setValueAt(itemRequisicaoInterna.obterItem().obterDescricao(),numeroItens,1);
                        tblItensVenda.setValueAt(Numero.inverterSeparador(""+ itemRequisicaoInterna.obterQuantidadeItem()),numeroItens,2);
                        numeroItens++;
                    }
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar os itens da Requisição Interna.","Erro",JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
                redimencionar();
            }
        }
        
        if(objeto == cbxRequisicaoDescarte)
        {
            if(cbxRequisicaoDescarte.getSelectedIndex() > 0)
            {
                if(itensRequisicaoInterna != null)
                    itensRequisicaoInterna.removeAllElements();
                requisicaoInterna = (RequisicaoInterna)requisicoesInternasDescarte.get(cbxRequisicaoDescarte.getSelectedIndex());
                
                lblDataEmissaoDescarte.setText(requisicaoInterna.obterDataEmissao());
                lblDataLimiteEntregaDescarte.setText(requisicaoInterna.obterDataLimiteEntrega());
                lblDepartSolicitanteDescarte.setText(requisicaoInterna.obterDepartamento().obterNomeDepartamento());
                lblSolicitanteDescarte.setText(requisicaoInterna.obterSolicitante().getNome());
                txaJustificativaDescarte.setText(requisicaoInterna.obterJustificativa());
                for(int i = 0; i < numeroItens;i++)
                {
                    tblItensDescarte.setValueAt("",i,0);
                    tblItensDescarte.setValueAt("",i,1);
                    tblItensDescarte.setValueAt("",i,2);
                }
                try
                {
                    itensRequisicaoInterna = requisicaoInterna.carregarItensRequisicaoInterna(aplicacao.obterConexao());
                    numeroItens = 0;
                    ItemRequisicaoInterna itemRequisicaoInterna;
                    for(int i = 0;i < itensRequisicaoInterna.size();i++)
                    {
                        itemRequisicaoInterna = (ItemRequisicaoInterna)itensRequisicaoInterna.get(i);
                        
                        tblItensDescarte.setValueAt(""+ itemRequisicaoInterna.obterItem().obterCodigo(),numeroItens,0);
                        tblItensDescarte.setValueAt(itemRequisicaoInterna.obterItem().obterDescricao(),numeroItens,1);
                        tblItensDescarte.setValueAt(Numero.inverterSeparador(""+ itemRequisicaoInterna.obterQuantidadeItem()),numeroItens,2);
                        numeroItens++;
                    }
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar os itens da Requisição Interna.","Erro",JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
                redimencionar();
            }
        }
        
        /* Ao selecionar uma requisição de devolução preenche a tela com 
         * informações sobre esta requisição. */
        if(objeto == cbxRequisicaoDevolucao)
        {
        	if(cbxRequisicaoDevolucao.getSelectedIndex() > 0)
        	{
	        	if(itensRequisicaoInterna != null)
                    itensRequisicaoInterna.removeAllElements();
                requisicaoInterna = (RequisicaoInterna)cbxRequisicaoDevolucao.getSelectedItem();
	        	
	        	lblDataEmissaoDevolucao.setText(requisicaoInterna.obterDataEmissao());
		        lblDataLimiteEntregaDevolucao.setText(requisicaoInterna.obterDataLimiteEntrega());
		        lblDepartSolicitanteDevolucao.setText(requisicaoInterna.obterDepartamento().obterNomeDepartamento());
		        lblSolicitanteDevolucao.setText(requisicaoInterna.obterSolicitante().getNome());
		        txaJustificativaDevolucao.setText(requisicaoInterna.obterJustificativa());
		        
                for(int i = 0; i < numeroItens;i++)
                {
                    tblItensDevolucao.setValueAt("",i,0);
                    tblItensDevolucao.setValueAt("",i,1);
                    tblItensDevolucao.setValueAt("",i,2);
                }
		        try
		        {
			        itensRequisicaoInterna = requisicaoInterna.carregarItensRequisicaoInterna(aplicacao.obterConexao());
    		        numeroItens = 0;
			        ItemRequisicaoInterna itemRequisicaoInterna;
			        for(int i = 0;i < itensRequisicaoInterna.size();i++)
			        {
			            itemRequisicaoInterna = (ItemRequisicaoInterna)itensRequisicaoInterna.get(i);
			            tblItensDevolucao.setValueAt(""+ itemRequisicaoInterna.obterItem().obterCodigo(),numeroItens,0);
			            tblItensDevolucao.setValueAt(itemRequisicaoInterna.obterItem().obterDescricao(),numeroItens,1);
			            tblItensDevolucao.setValueAt(Numero.inverterSeparador(""+ itemRequisicaoInterna.obterQuantidadeItem()),numeroItens,2);
			            numeroItens++;
			        }
		        }
		        catch(Exception e)
		        {
		        	JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar os itens da Requisição Interna.","Erro",JOptionPane.ERROR_MESSAGE);
		        	e.printStackTrace();
		        }
		        redimencionar();
        	}
        	else
        	{
        		lblDataEmissaoDevolucao.setText("");
		        lblDataLimiteEntregaDevolucao.setText("");
		        lblDepartSolicitanteDevolucao.setText("");
		        lblSolicitanteDevolucao.setText("");
		        txaJustificativaDevolucao.setText("");
		        
		        for(int i = 0; i < numeroItens;i++)
		        {
		            tblItensDevolucao.setValueAt("",i,0);
		            tblItensDevolucao.setValueAt("",i,1);
		            tblItensDevolucao.setValueAt("",i,2);
		        }
        	}
        }
        
        if(objeto == cbxRequisicaoDevolucaoExterna)
        {
            if(cbxRequisicaoDevolucaoExterna.getSelectedIndex() > 0)
            {
                if(itensRequisicaoInterna != null)
                    itensRequisicaoInterna.removeAllElements();
                requisicaoInterna = (RequisicaoInterna)requisicoesInternasDevolucaoExterna.get(cbxRequisicaoDevolucaoExterna.getSelectedIndex());
                
                lblDataEmissaoDevolucaoExterna.setText(requisicaoInterna.obterDataEmissao());
                lblDataLimiteEntregaDevolucaoExterna.setText(requisicaoInterna.obterDataLimiteEntrega());
                lblDepartSolicitanteDevolucaoExterna.setText(requisicaoInterna.obterDepartamento().obterNomeDepartamento());
                lblSolicitanteDevolucaoExterna.setText(requisicaoInterna.obterSolicitante().getNome());
                txaJustificativaDevolucaoExterna.setText(requisicaoInterna.obterJustificativa());
                for(int i = 0; i < numeroItens;i++)
                {
                    tblItensDevolucaoExterna.setValueAt("",i,0);
                    tblItensDevolucaoExterna.setValueAt("",i,1);
                    tblItensDevolucaoExterna.setValueAt("",i,2);
                }
                try
                {
                    itensRequisicaoInterna = requisicaoInterna.carregarItensRequisicaoInterna(aplicacao.obterConexao());
                    numeroItens = 0;
                    ItemRequisicaoInterna itemRequisicaoInterna;
                    for(int i = 0;i < itensRequisicaoInterna.size();i++)
                    {
                        itemRequisicaoInterna = (ItemRequisicaoInterna)itensRequisicaoInterna.get(i);
                        
                        tblItensDevolucaoExterna.setValueAt(""+ itemRequisicaoInterna.obterItem().obterCodigo(),numeroItens,0);
                        tblItensDevolucaoExterna.setValueAt(itemRequisicaoInterna.obterItem().obterDescricao(),numeroItens,1);
                        tblItensDevolucaoExterna.setValueAt(Numero.inverterSeparador(""+ itemRequisicaoInterna.obterQuantidadeItem()),numeroItens,2);
                        numeroItens++;
                    }
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar os itens da Requisição Interna.","Erro",JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
                redimencionar();
            }
        }
        
        if(objeto == cbxTipoMovimentacao)
        {
            card.show(pnlAreaMovimentacao,(String)cbxTipoMovimentacao.getSelectedItem());
            this.pack();   
            tipoMovimentacao = (String)tiposMovimentacao.get(cbxTipoMovimentacao.getSelectedIndex());                        
        }
        
        if(objeto == btProximo)
        {
            if(cbxRequisicaoAbastecimento.getSelectedIndex() > 0)
            {
                card.show(pnlAreaMovimentacao,"Abastecimento Confirmação");
                try
                {
                    // Carrega os itens da movimentacao pelos itens da requisicao de compras.
                    movimentacoes = new Vector();
                    for(int i = 0; i < itensRequisicaoCompra.size(); i++)
                    {
                        movimentacoes.addElement(new Movimentacao((String)tiposMovimentacao.get(cbxTipoMovimentacao.getSelectedIndex()),
                                                                       ((ItemRequisicao)itensRequisicaoCompra.get(i)),
                                                                       responsavelMovimentacao));
                    }
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro", JOptionPane.ERROR_MESSAGE);
                }
                this.redimencionar();
            }
            else
            {
                JOptionPane.showMessageDialog(aplicacao,"Erro: A Requisição de Compra não foi informada.","Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        if(objeto == btAnterior)
        {
            card.show(pnlAreaMovimentacao,"Abastecimento");
        }
        
        if(objeto == btConfirmar)
        {
            if(tipoMovimentacao == Movimentacao.ABASTECIMENTO)
            {
                try
                {   
                    //Definir o status da requisição de compra com base nos status dos itens da requisição.
                    ItemRequisicao itemRequisicao;
                    for(int i = 0;i < itensRequisicaoCompra.size();i++)
                    {
                        itemRequisicao = (ItemRequisicao)itensRequisicaoCompra.get(i);
                        if(itemRequisicao.obterStatus().equals(ItemRequisicao.ABASTECIDO_PARCIALMENTE))
                        {
                            requisicaoCompraSelecionada.definirStatus(RequisicaoCompra.STATUS_PENDENTE);
                            break;
                        }
                        else
                            requisicaoCompraSelecionada.definirStatus(RequisicaoCompra.STATUS_CONCLUIDO);
                    }
                    requisicaoCompraSelecionada.registrarStatusRequisicaoCompra();
                    //Alterar as quantidades abastecidas dos itens da requisição de compras.
                    requisicaoCompraSelecionada.registrarQuantidadesAbastecidas();
                    //Registrar movimentações para cada item da requisição.
                    
                    Movimentacao movimentacao = null;
                    for(int i = 0;i < movimentacoes.size();i++)
                    {
                        if(((ItemRequisicao)itensRequisicaoCompra.get(i)).obterItem().obterLote() != null)
                        {
                            movimentacao = (Movimentacao)movimentacoes.get(i);
                            movimentacao.definirDataDespacho(txtDataDespacho.getText());
                            movimentacao.definirNumeroConhecimento(txtNumeroConhecimento.getText());
                            movimentacao.definirDataRecebimento(txtDataRecebimento.getText());
                            movimentacao.definirResponsavel(responsavelMovimentacao);
                            movimentacao.definirNotaFiscal(txtNotaFiscal.getText());
                            movimentacao.definirValorConhecimento(Float.parseFloat(Numero.inverterSeparador(txtValorConhecimento.getText())));
                            movimentacao.cadastrarMovimentacao();
                            ((ItemRequisicao)itensRequisicaoCompra.get(i)).obterItem().obterLote().cadastrarLote();
                        }
                    }
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
            
            /**
             * Status da Requisicao Interna é Pendente quando não existe quantidade suficiente em 
             * lote para nenhum item da requisição.
             * Status da Requisicao Interna é  Confirmado quando existe quantidade suficiente em 
             * lote em pelo menos um item.
             */
            else if(tipoMovimentacao == Movimentacao.CONSUMO || tipoMovimentacao == Movimentacao.VENDAS || tipoMovimentacao == Movimentacao.DESCARTE || tipoMovimentacao == Movimentacao.DEVOLUCAO_EXTERNA)
            {
                String statusDefinido = "";
                for(int i = 0; i < itensRequisicaoInterna.size(); i++)
                {
                    try
                    {
                        Movimentacao movimentacao = new Movimentacao((String)tiposMovimentacao.get(cbxTipoMovimentacao.getSelectedIndex()),
                                                                 (ItemRequisicaoInterna)itensRequisicaoInterna.get(i),
                                                                 requisicaoInterna,
                                                                 responsavelMovimentacao);
                        Lote.retirarItem(movimentacao, (ItemRequisicaoInterna)itensRequisicaoInterna.get(i));
                    }
                    catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                    }
                }
                // Atualiza o status da Movimentação Interna
                try
                {
                    for(int i = 0;i < itensRequisicaoInterna.size();i++)
                    {
                        if(((ItemRequisicaoInterna)itensRequisicaoInterna.get(i)).obterStatus().equals(ItemRequisicaoInterna.STATUS_PENDENTE))
                        {
                            requisicaoInterna.definirStatus(RequisicaoInterna.STATUS_PENDENTE);
                            break;
                        }
                        else
                        {
                            requisicaoInterna.definirStatus(RequisicaoInterna.STATUS_CONFIRMADO);
                        }
                    }
                    if(requisicaoInterna.obterStatus().equals(RequisicaoInterna.STATUS_EMITIDO) ||
                       requisicaoInterna.obterStatus().equals(RequisicaoInterna.STATUS_CONFIRMADO))
                    {
                        requisicaoInterna.atualizarRequisicaoInterna(RequisicaoInterna.STATUS_CONFIRMADO);
                    }
                    else if(requisicaoInterna.obterStatus().equals(RequisicaoInterna.STATUS_PENDENTE))
                    {
                        String mensagem = "Atenção: Os seguintes itens possuem quantidades insuficientes no estoque: \n";
                        for(int i = 0; i < itensRequisicaoInterna.size(); i++)
                        {
                            ItemRequisicaoInterna item = (ItemRequisicaoInterna)itensRequisicaoInterna.get(i);
                            if(item.obterStatus().equals(ItemRequisicaoInterna.STATUS_PENDENTE))
                                mensagem += "" + (i + 1) + ". " + item.obterItem().obterDescricao() + " ("+ item.obterQuantidadeItem() +")\n";
                        }
                        JOptionPane.showMessageDialog(aplicacao,mensagem,"Atenção",JOptionPane.WARNING_MESSAGE);
                    }
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível finalizar a Requisição Interna. \n" +
                                                  "Mesmo assim as transações foram efetuadas com sucesso, no entanto \n" +
                                                  "a requisição poderá ser processada novamente sem necessidade.","Erro",
                                                  JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
            /* A devolução de um item implica no acréscimo do estoque. através
             * da entrada de um novo lote. */
            else if(tipoMovimentacao == Movimentacao.DEVOLUCAO)
            {
                for(int i = 0; i < itensRequisicaoInterna.size(); i++)
                {
                    try
                    {
                        ItemRequisicaoInterna itemRequisicaoInterna = (ItemRequisicaoInterna)itensRequisicaoInterna.get(i);
                        Movimentacao movimentacao = new Movimentacao((String)tiposMovimentacao.get(cbxTipoMovimentacao.getSelectedIndex()),
                                                                 itemRequisicaoInterna,
                                                                 requisicaoInterna,
                                                                 responsavelMovimentacao);
                        Lote lote = new Lote(itemRequisicaoInterna.obterItem(),
                                             movimentacao,
                                             itemRequisicaoInterna.obterQuantidadeItem());
                        movimentacao.cadastrarMovimentacao();
                        lote.cadastrarLote();
                    }
                    catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível registrar a movimentação. \n" +
                                                  "O banco de dados pode estar inconsistente. Entre em contato com o administrador.","Erro",
                                                  JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                    }
                }
                // Atualiza o status da Movimentação Interna
                try
                {
                    requisicaoInterna.atualizarRequisicaoInterna(RequisicaoInterna.STATUS_CONFIRMADO);
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível finalizar a Requisição Interna. \n" +
                                                  "Mesmo assim as transações foram efetuadas com sucesso, no entanto \n" +
                                                  "a requisição poderá ser processada novamente sem necessidade.","Erro",
                                                  JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
            this.setVisible(false);
        }
        
        if(objeto == btCancelar)
        {
            this.setVisible(false);
        }
                
        if(objeto == btDefinirLote)
        {
            if(tblItensAbastecimentoConfirmacao.getSelectedRow() >=0)
            {
                ItemRequisicao itemSelecionado = (ItemRequisicao)itensRequisicaoCompra.get(tblItensAbastecimentoConfirmacao.getSelectedRow());
                Movimentacao movimentacaoItem = (Movimentacao)movimentacoes.get(tblItensAbastecimentoConfirmacao.getSelectedRow());
                DlgDadosLote dlgDadosLote = new DlgDadosLote(aplicacao,itemSelecionado,movimentacaoItem);
                dlgDadosLote.setVisible(true);
                atualizarTabelaItensAbastecimentoConfirmacao();
            }
            else
            {
                JOptionPane.showMessageDialog(aplicacao,"Atenção: Selecione um Item da tabela para definir um lote.","Atenção", JOptionPane.WARNING_MESSAGE);
            }
            btConfirmar.setEnabled(true);
         }
    }
}