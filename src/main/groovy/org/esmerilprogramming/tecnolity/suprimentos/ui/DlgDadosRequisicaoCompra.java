/**
* Projeto: 001 - Tecnolity
* Autor do Código: Kenia Soares
* Nome do Arquivo: DlgDadosRequisicaoCompra.java
* Linguagem: Java
* 
* Requerimentos: Requer máquina virtual java versão 1.3 ou superior.
* 
* Objetivo: Diálogo para cadastramento,alteração e visualização de dados de uma requisição de compra.
* 
* Objetivo definido por: Kenia Soares
* Início da Programação: 21/02/2002
* Fim da Programação:
* Última Versão: 1.0
*/

package org.esmerilprogramming.tecnolity.suprimentos.ui;

import java.sql.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import org.esmerilprogramming.tecnolity.logistica.*;
import org.esmerilprogramming.tecnolity.logistica.ui.*;
import org.esmerilprogramming.tecnolity.pedidos.*;
import org.esmerilprogramming.tecnolity.pedidos.ui.DlgDadosFormaPagamento;
import org.esmerilprogramming.tecnolity.suprimentos.*;
import org.esmerilprogramming.tecnolity.util.*;
import org.esmerilprogramming.tecnolity.administracao.*;
import org.esmerilprogramming.tecnolity.aplicacao.Aplicacao;
import org.esmerilprogramming.tecnolity.aplicacao.relatorios.*;

public class DlgDadosRequisicaoCompra  extends JDialog implements ActionListener, FocusListener
{
    public final int IDENTIFICADOR = 27;
    
    private Aplicacao aplicacao;
    private RequisicaoCompra requisicaoCompra;
    private Fornecedor fornecedor = new Fornecedor();
    private FormaPagamento formaPagamento = new FormaPagamento();
    private Transportadora transportadora = new Transportadora();    
    private Item item;
    private Vector fornecedores, pedidos, pedidosSelecionados, formasPagamento, transportadoras,
                   itens, itensRequisicao;
    private GridBagLayout gridbag;
    private GridBagConstraints gbc;
    private String responsavelEmissao;

    // Objetos do painel de dados da requisicao de compra
    private JPanel pnlDadosRequisicaoCompra;
    private JTextField txtDataLimiteEntrega,txtCondicoesPagamento,txtDataPagamento;
    private JComboBox cbxFornecedor, cbxFormaPagamento, cbxTransportadora, cbxFrete;
    private JTextArea txaObservacoes;
    private JButton btNovoFornecedor,btNovoPedidoCliente,btNovaFormaPagamento,
                    btNovaTransportadora;
    // Objetos do painel pedidos do cliente
    private JPanel pnlPedidosClientes;
    private JRadioButton rdbProducaoIniciada, rdbProducaoNaoIniciada;
    private JList lstPedidos, lstPedidosSelecionados;
    private DefaultListModel modeloListaPedidos, modeloListaPedidosSelecionados;
    private JButton btAdicionar, btRemover;
    // Objetos do painel dos itens da requisicao de compra
    private JPanel pnlDadosItensRequisicao;
    private JComboBox cbxItem;
    private JTextField txtQuantidade,txtValorUnitario,txtTotal,txtPercentualIpi,txtIpi; 
    private JButton btNovoItem,btAdicionarItem,btCancelarItem,btAlterarItem,btExcluirItem,btConfirmarAlteracao;
    private JTable tblItens;
    private int numeroItens, linhaAAlterar;
    private Container conteudo;
    private JPanel pnlAreaDados;
    private CardLayout card;
    private int indiceCard;
    private JButton btAnterior, btProximo, btProximoLeitura, btConfirmar, btCancelar;
    
    public DlgDadosRequisicaoCompra(Aplicacao aplicacao)
    {
        super(aplicacao,true);
        this.setTitle("Nova Requisição de Compra");
        this.aplicacao = aplicacao;
        this.responsavelEmissao = (aplicacao.obterColaborador()).obterMatricula();
        this.pedidosSelecionados = new Vector();
        montarInterface();
        requisicaoCompra = new RequisicaoCompra();
    }
    
    public DlgDadosRequisicaoCompra(Aplicacao aplicacao, RequisicaoCompra requisicaoCompra)
    {
        super(aplicacao,true);
        this.setTitle("Requisição de Compra");
        this.aplicacao = aplicacao;
        this.requisicaoCompra = requisicaoCompra;
        this.responsavelEmissao = (aplicacao.obterColaborador()).obterMatricula();
        this.pedidosSelecionados = new Vector();
        montarInterface();
        try
        {
            // Seleciona o fornecedor da requisição de compras.
            for(int i = 1; i < fornecedores.size(); i++)
            {
                if( (((Fornecedor)fornecedores.get(i)).obterCodigo()) == ((this.requisicaoCompra.obterFornecedor())==null?0:this.requisicaoCompra.obterFornecedor().obterCodigo()))
                {
                    cbxFornecedor.setSelectedIndex(i);
                    break;
                }
            }
            // Seleciona a forma de pagamento da requisição de compras.
            for(int i = 1; i < formasPagamento.size(); i++)
            {
                if(((FormaPagamento)formasPagamento.get(i)).obterFormaPagamento().equals(requisicaoCompra.obterFormaPagamento().obterFormaPagamento()))
                {
                    cbxFormaPagamento.setSelectedIndex(i);
                    break;
                }
            }
            // Carrega os pedidos atendidos pela requisição de compra.
            String query = "select distinct pc.codigo, pc.ordem_compra from pedido_requisicao_compra prc, pedido_cliente pc " +
                           "where prc.pedido = pc.codigo and prc.requisicao_compra = " + requisicaoCompra.obterCodigo();
            ResultSet rsPedidos = aplicacao.obterConexao().executarConsulta(query);
        
            while(rsPedidos.next())
            {
                modeloListaPedidosSelecionados.addElement(new Pedido(rsPedidos.getInt("codigo"),rsPedidos.getString("ordem_compra")));
            }
            rsPedidos.close();
            // Carrega os itens solicitados na requisição de compra.
            query = "select i.descricao,ir.quantidade,ir.valor_item,(ir.quantidade * ir.valor_item) as valor_total, i.percentual_ipi, (((ir.quantidade * ir.valor_item) * i.percentual_ipi)/100) as ipi " +
                    "from item i, item_requisicao ir " +
                    "where i.codigo = ir.item and ir.requisicao_compra = " + requisicaoCompra.obterCodigo();
            ResultSet rsItensRequisicao = aplicacao.obterConexao().executarConsulta(query);
            int posItem = 0;
            while(rsItensRequisicao.next())
            {
                this.tblItens.setValueAt(rsItensRequisicao.getString("descricao"),posItem,0);
                this.tblItens.setValueAt(rsItensRequisicao.getString("quantidade"),posItem,1);
                this.tblItens.setValueAt(rsItensRequisicao.getString("valor_item"),posItem,2);
                this.tblItens.setValueAt(rsItensRequisicao.getString("valor_total"),posItem,3);
                this.tblItens.setValueAt(rsItensRequisicao.getString("percentual_ipi"),posItem,4);
                this.tblItens.setValueAt(rsItensRequisicao.getString("ipi"),posItem,5);
                posItem++;
            }
            rsItensRequisicao.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
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
        pnlDadosRequisicaoCompra = new JPanel(gridbag);
        pnlDadosRequisicaoCompra.setBorder(new TitledBorder("Dados da Requisição de Compra"));
        
        JLabel label = new JLabel("Fornecedor");
        adicionarComponente(pnlDadosRequisicaoCompra,label,0,0,2,1);
        
        JPanel pnlSuporteCombo = new JPanel(new BorderLayout());
        cbxFornecedor = new JComboBox();
        cbxFornecedor.addActionListener(this);
        try
        {
        	fornecedores = Fornecedor.carregarFornecedores(aplicacao.obterConexao());        	
            carregarFornecedores();
        }
        catch(Exception e)
        {
        	JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar os Fornecedores. ","Erro", JOptionPane.ERROR_MESSAGE);	
            e.printStackTrace();
        }

        pnlSuporteCombo.add(cbxFornecedor,BorderLayout.CENTER);
        btNovoFornecedor = new JButton(new ImageIcon("imagens/novo.jpg"));
        btNovoFornecedor.addActionListener(this);
        btNovoFornecedor.setToolTipText("Novo Fornecedor");
        btNovoFornecedor.setPreferredSize(new Dimension(22,20));
        pnlSuporteCombo.add(btNovoFornecedor,BorderLayout.EAST);
        adicionarComponente(pnlDadosRequisicaoCompra,pnlSuporteCombo,1,0,3,1);
                
        label = new JLabel("Condição de Pagamento");
        adicionarComponente(pnlDadosRequisicaoCompra,label,2,0,1,1);
        label = new JLabel("Forma de Pagamento");
        adicionarComponente(pnlDadosRequisicaoCompra,label,2,1,1,1);
             
        txtCondicoesPagamento = new JTextField((this.requisicaoCompra==null?"":this.requisicaoCompra.obterCondicaoPagamento().trim()),15);
        txtCondicoesPagamento.addFocusListener(this);
        adicionarComponente(pnlDadosRequisicaoCompra,txtCondicoesPagamento,3,0,1,1);
        
        pnlSuporteCombo = new JPanel(new BorderLayout());
        cbxFormaPagamento = new JComboBox();
        try
        {
        	formasPagamento = formaPagamento.carregarFormasPagamento(aplicacao.obterConexao());
        	carregarFormasPagamento();
        }
        catch(Exception e)
        {
        	JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar as Formas de Pagamento. ","Erro", JOptionPane.ERROR_MESSAGE);	
            e.printStackTrace();
        }

        pnlSuporteCombo.add(cbxFormaPagamento,BorderLayout.CENTER);
        btNovaFormaPagamento = new JButton(new ImageIcon("imagens/novo.jpg"));
        btNovaFormaPagamento.addActionListener(this);
        btNovaFormaPagamento.setToolTipText("Nova Forma de Pagamento");
        btNovaFormaPagamento.setPreferredSize(new Dimension(22,20));
        pnlSuporteCombo.add(btNovaFormaPagamento,BorderLayout.EAST);
        adicionarComponente(pnlDadosRequisicaoCompra,pnlSuporteCombo,3,1,1,1);
        
        label = new JLabel("Transportadora");
        adicionarComponente(pnlDadosRequisicaoCompra,label,4,0,1,1);
        label = new JLabel("Limite Entrega");
        adicionarComponente(pnlDadosRequisicaoCompra,label,4,1,1,1);
        label = new JLabel("Frete");
        adicionarComponente(pnlDadosRequisicaoCompra,label,4,2,1,1);
        
        pnlSuporteCombo = new JPanel(new BorderLayout());
        cbxTransportadora = new JComboBox();
        try
        {
        	transportadoras = Transportadora.carregarTransportadoras(aplicacao.obterConexao());
        	carregarTransportadoras();
        }
        catch(Exception e)
        {
        	JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar as Transportadoras. ","Erro", JOptionPane.ERROR_MESSAGE);	
                e.printStackTrace();
        }

        int indiceTransportadora = 0;
        for(int i = 1; i < transportadoras.size(); i++)
        {                
            if((((Transportadora)transportadoras.get(i)).obterCodigo()) == (this.requisicaoCompra==null?0:this.requisicaoCompra.obterTransportadora().obterCodigo()))
            {
                indiceTransportadora = i;         
            }
        }        
        cbxTransportadora.setSelectedIndex(indiceTransportadora);

        pnlSuporteCombo.add(cbxTransportadora,BorderLayout.CENTER);
        btNovaTransportadora = new JButton(new ImageIcon("imagens/novo.jpg"));
        btNovaTransportadora.addActionListener(this);
        btNovaTransportadora.setToolTipText("Nova Transportadora");
        btNovaTransportadora.setPreferredSize(new Dimension(22,20));
        pnlSuporteCombo.add(btNovaTransportadora,BorderLayout.EAST);
        adicionarComponente(pnlDadosRequisicaoCompra,pnlSuporteCombo,5,0,1,1);
                       
        txtDataLimiteEntrega = new JTextField(this.requisicaoCompra==null?"":Calendario.ajustarFormatoDataBanco(this.requisicaoCompra.obterDataLimiteEntrega().trim()),8);
        txtDataLimiteEntrega.addFocusListener(this);
        adicionarComponente(pnlDadosRequisicaoCompra,txtDataLimiteEntrega,5,1,1,1);
        
        cbxFrete = new JComboBox();
        cbxFrete.addItem("CIF");
        cbxFrete.addItem("FOB");
        cbxFrete.addItem("Próprio");
        adicionarComponente(pnlDadosRequisicaoCompra,cbxFrete,5,2,1,1);
                
        label = new JLabel("Observações");
        adicionarComponente(pnlDadosRequisicaoCompra,label,6,0,4,1);
        txaObservacoes = new JTextArea(this.requisicaoCompra==null?"":this.requisicaoCompra.obterObservacao(),4,30);
        txaObservacoes.setLineWrap(true);
        txaObservacoes.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(txaObservacoes);
        adicionarComponente(pnlDadosRequisicaoCompra,scroll,7,0,4,1);

        //Painel Pedidos do Cliente
        pnlPedidosClientes = new JPanel(new BorderLayout());
        JPanel pnlTipoPedido = new JPanel();
        ButtonGroup grupo = new ButtonGroup();
        rdbProducaoNaoIniciada = new JRadioButton("Pedidos Pendentes");
        rdbProducaoNaoIniciada.setSelected(true);
        rdbProducaoNaoIniciada.addActionListener(this);
        grupo.add(rdbProducaoNaoIniciada);
        pnlTipoPedido.add(rdbProducaoNaoIniciada);
        rdbProducaoIniciada = new JRadioButton("Pedidos Iniciados");
        rdbProducaoIniciada.addActionListener(this);
        grupo.add(rdbProducaoIniciada);
        pnlTipoPedido.add(rdbProducaoIniciada);
        pnlPedidosClientes.add(pnlTipoPedido, BorderLayout.NORTH);
        
        JPanel pnlPedidos = new JPanel(new BorderLayout());
        pnlPedidos.add(new JLabel("Pedidos"), BorderLayout.NORTH);
        try
        {
            this.pedidos = Pedido.carregarPedidosPendentes(aplicacao.obterConexao());
            modeloListaPedidos = new DefaultListModel();
            lstPedidos = new JList(modeloListaPedidos);
            for(int i = 0;i < this.pedidos.size();i++)
            {
                modeloListaPedidos.addElement(this.pedidos.get(i));
            }
            lstPedidos.setFixedCellWidth(200);
            scroll = new JScrollPane(lstPedidos);
            pnlPedidos.add(scroll, BorderLayout.CENTER);
            pnlPedidosClientes.add(pnlPedidos, BorderLayout.WEST);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar os pedidos.","Erro",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        JPanel pnlSuporteComandos = new JPanel();
        JPanel pnlComandosPedidos = new JPanel(new GridLayout(2,1,5,5));
        btAdicionar = new JButton("Adicionar >>");
        btAdicionar.addActionListener(this);
        pnlComandosPedidos.add(btAdicionar);
        btRemover = new JButton("<< Remover");
        btRemover.addActionListener(this);
        pnlComandosPedidos.add(btRemover);
        pnlSuporteComandos.add(pnlComandosPedidos);
        pnlPedidosClientes.add(pnlSuporteComandos, BorderLayout.CENTER);
        
        JPanel pnlPedidosSelecionados = new JPanel(new BorderLayout());
        pnlPedidosSelecionados.add(new JLabel("Pedidos Selecionados"), BorderLayout.NORTH);
        modeloListaPedidosSelecionados = new DefaultListModel();
        lstPedidosSelecionados = new JList(modeloListaPedidosSelecionados);
        lstPedidosSelecionados.setFixedCellWidth(200);
        scroll = new JScrollPane(lstPedidosSelecionados);
        pnlPedidosSelecionados.add(scroll, BorderLayout.CENTER);
        pnlPedidosClientes.add(pnlPedidosSelecionados, BorderLayout.EAST);

        //Painel Itens da Requisição
        itensRequisicao = new Vector();
        pnlDadosItensRequisicao = new JPanel(gridbag);
        pnlDadosItensRequisicao.setBorder(new TitledBorder("Item da Requisição"));
        
        label = new JLabel("Item");
        adicionarComponente(pnlDadosItensRequisicao,label,0,0,1,1);
        label = new JLabel("Quantidade");
        adicionarComponente(pnlDadosItensRequisicao,label,0,1,1,1);
        label = new JLabel("Valor Unitário (R$)");
        adicionarComponente(pnlDadosItensRequisicao,label,0,2,1,1);
        
        pnlSuporteCombo = new JPanel(new BorderLayout());
        cbxItem = new JComboBox();
        cbxItem.addActionListener(this);

        pnlSuporteCombo.add(cbxItem,BorderLayout.CENTER);
        btNovoItem = new JButton(new ImageIcon("imagens/novo.jpg"));
        btNovoItem.addActionListener(this);
        btNovoItem.setToolTipText("Novo Item");
        btNovoItem.setPreferredSize(new Dimension(22,20));
        pnlSuporteCombo.add(btNovoItem,BorderLayout.EAST);
        adicionarComponente(pnlDadosItensRequisicao,pnlSuporteCombo,1,0,1,1);   
        
        txtQuantidade = new JTextField("",5);
        txtQuantidade.addFocusListener(this);
        adicionarComponente(pnlDadosItensRequisicao,txtQuantidade,1,1,1,1);
        
        txtValorUnitario = new JTextField("",5);
        txtValorUnitario.addFocusListener(this);
        adicionarComponente(pnlDadosItensRequisicao,txtValorUnitario,1,2,1,1);
        
        label = new JLabel("Total (R$)");
        adicionarComponente(pnlDadosItensRequisicao,label,2,0,1,1);
        label = new JLabel("IPI (%)");
        adicionarComponente(pnlDadosItensRequisicao,label,2,1,1,1);
        label = new JLabel("IPI");
        adicionarComponente(pnlDadosItensRequisicao,label,2,2,1,1);
        
        txtTotal = new JTextField("",10);
        txtTotal.addFocusListener(this);
        txtTotal.setEditable(false);
        adicionarComponente(pnlDadosItensRequisicao,txtTotal,3,0,1,1);
        txtPercentualIpi = new JTextField("",5);
        txtPercentualIpi.addFocusListener(this);
        txtPercentualIpi.setEditable(false);
        adicionarComponente(pnlDadosItensRequisicao,txtPercentualIpi,3,1,1,1);
        txtIpi = new JTextField(5);
        txtIpi.addFocusListener(this);
        txtIpi.setEditable(false);
        adicionarComponente(pnlDadosItensRequisicao,txtIpi,3,2,1,1);
        
        JPanel pnlItens = new JPanel(new BorderLayout());
        JPanel pnlComandoItem = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btConfirmarAlteracao = new JButton("Confirmar Alteração");
        btConfirmarAlteracao.addActionListener(this);
        btConfirmarAlteracao.setEnabled(false);
        pnlComandoItem.add(btConfirmarAlteracao);
        btAdicionarItem = new JButton("Adicionar");
        btAdicionarItem.addActionListener(this);
        pnlComandoItem.add(btAdicionarItem);
        btCancelarItem = new JButton("Cancelar");
        btCancelarItem.addActionListener(this);
        pnlComandoItem.add(btCancelarItem);
        pnlItens.add(pnlComandoItem, BorderLayout.NORTH);
        Object[][] dados = new Object[100][6];
        String[] nomeColunas = {"Item","Quant.","Vl. Unit.","Vl. Total","IPI %","IPI"};
        tblItens = new JTable(dados, nomeColunas);
        tblItens.setPreferredScrollableViewportSize(new Dimension(460, 100));
        tblItens.addRowSelectionInterval(0,0);
        scroll = new JScrollPane(tblItens); 

        pnlItens.add(scroll, BorderLayout.CENTER);
        pnlComandoItem = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btAlterarItem = new JButton("Alterar Selecionado");
        btAlterarItem.addActionListener(this);
        pnlComandoItem.add(btAlterarItem);
        btExcluirItem = new JButton("Excluir Selecionado");
        btExcluirItem.addActionListener(this);
        pnlComandoItem.add(btExcluirItem);
        pnlItens.add(pnlComandoItem, BorderLayout.SOUTH);
        adicionarComponente(pnlDadosItensRequisicao,pnlItens,4,0,4,1);
                
        pnlAreaDados.add(this.pnlDadosRequisicaoCompra,"Requisição de Compra");
        pnlAreaDados.add(this.pnlPedidosClientes,"Pedidos dos Clientes");
        pnlAreaDados.add(this.pnlDadosItensRequisicao,"Itens da Requisição");        
        conteudo.add(pnlAreaDados,BorderLayout.CENTER);
	    
        JPanel pnlComandos = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btAnterior = new JButton("<< Anterior");
        btAnterior.addActionListener(this);
        btAnterior.setEnabled(false);
        pnlComandos.add(btAnterior);

        btProximoLeitura = new JButton("Próximo >>");
        btProximo = new JButton("Próximo >>");
        if(requisicaoCompra == null)
        {
            btProximo.addActionListener(this);
            pnlComandos.add(btProximo);
        }
        else
        {
            btProximoLeitura.addActionListener(this);
            pnlComandos.add(btProximoLeitura);
        }

        
        btConfirmar = new JButton("Confirmar");
        if(requisicaoCompra == null)
        {
            btConfirmar.addActionListener(this);
            btConfirmar.setEnabled(false);
            pnlComandos.add(btConfirmar);
        }
	    
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
        
    private void carregarTransportadoras()
    {
        cbxTransportadora.removeAllItems();
        cbxTransportadora.addItem("Selecione...");

        for(int i = 1;i < transportadoras.size();i++)
        {
            cbxTransportadora.addItem(((Transportadora)transportadoras.get(i)).obterNome());
        }
    }
        
    private void carregarFornecedores()
    {
        cbxFornecedor.removeAllItems();
        cbxFornecedor.addItem("Selecione...");
        String cnpj,razaoSocial;

        for(int i = 1;i < fornecedores.size();i++)
        {                
            cnpj = (((Fornecedor)fornecedores.get(i)).obterCnpj()).trim();
            razaoSocial = (((Fornecedor)fornecedores.get(i)).obterRazaoSocial()).trim();
            cbxFornecedor.addItem(cnpj + " - " + razaoSocial.substring(0,((razaoSocial.length() < 40)?razaoSocial.length():40)) + ((razaoSocial.length() < 40)?"":" . . ."));
        }
    }
        
    private void carregarFormasPagamento()
    {
        cbxFormaPagamento.removeAllItems();
        cbxFormaPagamento.addItem("Selecione...");

        for(int i = 1;i < formasPagamento.size();i++)
        {
            cbxFormaPagamento.addItem(((FormaPagamento)formasPagamento.get(i)).obterFormaPagamento());
        }
    }
    
    private void carregarPedidosPendentes()
    {
        try
        {
            modeloListaPedidos.removeAllElements();
            this.pedidos = Pedido.carregarPedidosPendentes(aplicacao.obterConexao());
            for(int i = 0;i < this.pedidos.size();i++)
            {
                modeloListaPedidos.addElement(this.pedidos.get(i));
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar pedidos em produção.","Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void carregarPedidosProduzindo()
    {
        try
        {
            modeloListaPedidos.removeAllElements();
            this.pedidos = Pedido.carregarPedidosProduzindo(aplicacao.obterConexao());
            for(int i = 0;i < this.pedidos.size();i++)
            {
                modeloListaPedidos.addElement(this.pedidos.get(i));
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar pedidos em produção.","Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void carregarItens()
    {
        cbxItem.removeAllItems();
        cbxItem.addItem("Selecione...");

        for(int i = 1;i < itens.size();i++)
        {
            cbxItem.addItem(((Item)itens.get(i)).obterDescricao());
        }
    }
        
    private void cancelarItem()
    {
        this.cbxItem.setSelectedIndex(0);            
        this.txtQuantidade.setText("0");
        this.txtValorUnitario.setText("0,0");
        this.txtTotal.setText("0,0");
        this.txtPercentualIpi.setText("0");
        this.txtIpi.setText("0");
    }
    
    private int obterDepartamentoSolicitante() throws Exception
    {            
        Conexao conexao = aplicacao.obterConexao();
        ResultSet departamentoSolicitante = conexao.executarConsulta("select departamento from usuario where usuario = '"+ this.responsavelEmissao +"' ");
        if(departamentoSolicitante.next())
            return departamentoSolicitante.getInt("departamento");
        else
            return 0;           
    }
    
    private void calcularPreco()
    {
        float quantidadeItem = Float.parseFloat(Numero.inverterSeparador(this.txtQuantidade.getText()));
        float valorItem = Float.parseFloat(Numero.inverterSeparador(this.txtValorUnitario.getText()));
        float percentualIpi = Float.parseFloat(Numero.inverterSeparador(this.txtPercentualIpi.getText()));
        if(quantidadeItem != 0.0f && valorItem != 0.0f)
        {
            float totalItem = quantidadeItem * valorItem;
            this.txtTotal.setText(""+Numero.inverterSeparador(""+totalItem));
            float ipi = ((percentualIpi)/100) * totalItem;
            this.txtIpi.setText(""+Numero.inverterSeparador(""+ipi));
        }
    }

    public void actionPerformed(java.awt.event.ActionEvent actionEvent) 
    {
        Object objeto = actionEvent.getSource();
        
        if(objeto == this.cbxFornecedor)
        {
            if(cbxFornecedor.getSelectedIndex() > 0)
            {
                try
                {
                    itens = Item.carregarItensIndependentesEmFalta((Fornecedor)fornecedores.get(cbxFornecedor.getSelectedIndex()),this.aplicacao.obterConexao());
                    carregarItens();
                    btAdicionar.setEnabled(true);
                    btRemover.setEnabled(true);
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar os itens do fornecedor","Erro", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        }
        
        if(objeto == this.btNovaTransportadora)
        {
            DlgDadosTransportadora dlgDadosTransportadora = new DlgDadosTransportadora(aplicacao,'I');
            dlgDadosTransportadora.setVisible(true);
            try
            {
                Transportadora transportadora = new Transportadora();
                this.transportadoras = Transportadora.carregarTransportadoras(aplicacao.obterConexao());
                carregarTransportadoras();
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar as Transportadoras","Erro", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
        
        if(objeto == this.btNovoItem)
        {
            DlgDadosItem dlgDadosItem = new DlgDadosItem(aplicacao,'I');
            dlgDadosItem.setVisible(true);
            try
            {
                this.itens = Item.carregarItens(this.requisicaoCompra.obterFornecedor(), pedidosSelecionados,aplicacao.obterConexao());
                this.carregarItens();
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar os Ítens.","Erro", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
        
        if(objeto == this.btNovoFornecedor)
        {
            DlgDadosFornecedor dlgDadosFornecedor = new DlgDadosFornecedor(aplicacao,'I');
            dlgDadosFornecedor.setVisible(true);
            try
            {
                this.fornecedores = Fornecedor.carregarFornecedores(aplicacao.obterConexao());
                this.carregarFornecedores();
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar os Fornecedores.","Erro", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
        
        if(objeto == this.btNovaFormaPagamento)
        {
            DlgDadosFormaPagamento dlgDadosFormaPagamento = new DlgDadosFormaPagamento(aplicacao,'I');
            dlgDadosFormaPagamento.setVisible(true);
            try
            {
                FormaPagamento formaPagamento = new FormaPagamento();
                this.formasPagamento = formaPagamento.carregarFormasPagamento(aplicacao.obterConexao());
                this.carregarFormasPagamento();
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar as Formas de Pagamento.","Erro", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
        
        if(objeto == cbxItem)
        {            
            if(cbxItem.getSelectedIndex() > 0)
            {
                this.item = (Item)itens.get(cbxItem.getSelectedIndex());
                String query = "select fi.valor_item,i.percentual_ipi from item i,fornecedor_item fi where fi.item = i.codigo and i.codigo = "+ this.item.obterCodigo();
                try
                {
                    Conexao conexao = aplicacao.obterConexao();
                    ResultSet dadosItem = conexao.executarConsulta(query);
                    if(dadosItem.next())
                    {
                        txtQuantidade.setText(""+Numero.inverterSeparador(item.obterQuantidadeMinima()));
                        txtValorUnitario.setText(""+Numero.inverterSeparador(dadosItem.getFloat("valor_item")));
                        txtPercentualIpi.setText(""+dadosItem.getInt("percentual_ipi")); 
                    }
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }            
            }
        }
        
        if(objeto == rdbProducaoIniciada)
        {
            carregarPedidosProduzindo();
        }
        
        if(objeto == rdbProducaoNaoIniciada)
        {
            carregarPedidosPendentes();
        }
        
        if(objeto == btAdicionar)
        {
            if(!lstPedidos.isSelectionEmpty())
            {
                pedidosSelecionados.addElement(pedidos.get(lstPedidos.getSelectedIndex()));
                pedidos.removeElementAt(lstPedidos.getSelectedIndex());
                DefaultListModel modelo = (DefaultListModel)lstPedidos.getModel();
                DefaultListModel modeloSelecionado = (DefaultListModel)lstPedidosSelecionados.getModel();
                modeloSelecionado.addElement(((Pedido)pedidosSelecionados.get(pedidosSelecionados.size() - 1)));
                modelo.removeElementAt(lstPedidos.getSelectedIndex());
            }
            else
            {
                JOptionPane.showMessageDialog(aplicacao,"Selecione um pedido.","Atenção",JOptionPane.WARNING_MESSAGE);
            }
        }
        
        if(objeto == btRemover)
        {
            if(!lstPedidosSelecionados.isSelectionEmpty())
            {
                pedidos.addElement(pedidosSelecionados.get(lstPedidosSelecionados.getSelectedIndex()));
                pedidosSelecionados.removeElementAt(lstPedidosSelecionados.getSelectedIndex());
                DefaultListModel modelo = (DefaultListModel)lstPedidos.getModel();
                DefaultListModel modeloSelecionado = (DefaultListModel)lstPedidosSelecionados.getModel();
                modelo.addElement(((Pedido)pedidos.get(pedidos.size() - 1)));
                modeloSelecionado.removeElementAt(lstPedidosSelecionados.getSelectedIndex());
            }
            else
            {
                JOptionPane.showMessageDialog(aplicacao,"Selecione um Pedido.","Atenção",JOptionPane.WARNING_MESSAGE);
            }
        }
        
        if(objeto == btAdicionarItem)
        {
            // Insere no máximo 20 itens
            if(numeroItens < 100)
            {
                try
                {
                    boolean ItemInserido = false;
                    for(int i=0;i < 20;i++)
                    {
                        if(cbxItem.getSelectedItem().equals((String)tblItens.getValueAt(i,0)))
                        {
                            ItemInserido = true;
                        }
                    }
                    if(!ItemInserido)
                    {
                        calcularPreco();
                        float quantidadeItem = Float.parseFloat(Numero.inverterSeparador(this.txtQuantidade.getText()));
                        float valorItem = Float.parseFloat(Numero.inverterSeparador(this.txtValorUnitario.getText()));
                        int percentualIpi = Integer.parseInt(this.txtPercentualIpi.getText());
                        if(quantidadeItem != 0 && valorItem != 0.0f && percentualIpi != 0.0f )
                        {
                            float totalItem = quantidadeItem * valorItem;
                            this.txtTotal.setText(""+Numero.inverterSeparador(""+totalItem));
                            float ipi = ((percentualIpi)/100) * totalItem;
                            this.txtIpi.setText(""+Numero.inverterSeparador(""+ipi));
                        }
                        
                        itensRequisicao.addElement(new ItemRequisicao((Item)itens.get(cbxItem.getSelectedIndex()), this.requisicaoCompra,Float.parseFloat(Numero.inverterSeparador(this.txtQuantidade.getText())),Float.parseFloat(Numero.inverterSeparador(this.txtQuantidade.getText())),0.0f,Float.parseFloat(Numero.inverterSeparador(this.txtValorUnitario.getText())),percentualIpi));
                        this.tblItens.setValueAt(cbxItem.getSelectedItem(),numeroItens,0);
                        this.tblItens.setValueAt(txtQuantidade.getText(),numeroItens,1);
                        this.tblItens.setValueAt(txtValorUnitario.getText(),numeroItens,2);
                        this.tblItens.setValueAt(txtTotal.getText(),numeroItens,3);
                        this.tblItens.setValueAt(txtPercentualIpi.getText(),numeroItens,4);
                        this.tblItens.setValueAt(txtIpi.getText(),numeroItens,5);
                        numeroItens++;
                        cancelarItem();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(aplicacao,"Erro: O Item informado já foi associado a Requisição de Compra.","Erro",JOptionPane.ERROR_MESSAGE);
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
                if (numeroItens > 0)
                {
                    this.btExcluirItem.setEnabled(true);
                    this.btConfirmar.setEnabled(true);
                }
                if (numeroItens == 20)
                {
                    this.btAdicionarItem.setEnabled(false);
                }
            }
            else
            {
                this.btAdicionarItem.setEnabled(false);
            }
        }
        
        if(objeto == btConfirmarAlteracao)
        {
            float quantidadeItem = Float.parseFloat(Numero.inverterSeparador(this.txtQuantidade.getText()));
            float valorItem = Float.parseFloat(Numero.inverterSeparador(this.txtValorUnitario.getText()));
            int percentualIpi = Integer.parseInt(this.txtPercentualIpi.getText());
            if(quantidadeItem != 0 && valorItem != 0.0f && percentualIpi != 0.0f )
            {
                float totalItem = quantidadeItem * valorItem;
                this.txtTotal.setText(""+Numero.inverterSeparador(""+totalItem));
                float ipi = ((percentualIpi)/100) * totalItem;
                this.txtIpi.setText(""+Numero.inverterSeparador(""+ipi));
            }
            itensRequisicao.setElementAt(new ItemRequisicao((Item)itens.get(cbxItem.getSelectedIndex()), this.requisicaoCompra,Float.parseFloat(Numero.inverterSeparador(this.txtQuantidade.getText())),Float.parseFloat(Numero.inverterSeparador(this.txtQuantidade.getText())),0.0f,Float.parseFloat(Numero.inverterSeparador(this.txtValorUnitario.getText())),percentualIpi),linhaAAlterar);
            this.tblItens.setValueAt(cbxItem.getSelectedItem(),linhaAAlterar,0);
            this.tblItens.setValueAt(txtQuantidade.getText(),linhaAAlterar,1);
            this.tblItens.setValueAt(txtValorUnitario.getText(),linhaAAlterar,2);
            this.tblItens.setValueAt(txtTotal.getText(),linhaAAlterar,3);
            this.tblItens.setValueAt(txtPercentualIpi.getText(),linhaAAlterar,4);
            this.tblItens.setValueAt(txtIpi.getText(),linhaAAlterar,5);
            cancelarItem();
            btConfirmarAlteracao.setEnabled(false);
            btAdicionarItem.setEnabled(true);
            btAlterarItem.setEnabled(true);
        }
        
        if(objeto == this.btAlterarItem)
        {
            int linhaSelecionada = this.tblItens.getSelectedRow();
            linhaAAlterar = linhaSelecionada;
            cbxItem.setSelectedItem(this.tblItens.getValueAt(linhaSelecionada,0));
            txtQuantidade.setText((String)tblItens.getValueAt(linhaSelecionada,1));
            txtTotal.setText((String)tblItens.getValueAt(linhaSelecionada,3));
            txtPercentualIpi.setText((String)tblItens.getValueAt(linhaSelecionada,4));
            txtIpi.setText((String)tblItens.getValueAt(linhaSelecionada,5));
            btConfirmarAlteracao.setEnabled(true);
            btAdicionarItem.setEnabled(false);
            btAlterarItem.setEnabled(false);
        }
        
        if(objeto == this.btExcluirItem)
        {
            int linhaSelecionada = this.tblItens.getSelectedRow();
            this.itensRequisicao.removeElementAt(linhaSelecionada);
            
            this.tblItens.setValueAt("",linhaSelecionada,0);
            this.tblItens.setValueAt("",linhaSelecionada,1);
            this.tblItens.setValueAt("",linhaSelecionada,2);
            this.tblItens.setValueAt("",linhaSelecionada,3);
            this.tblItens.setValueAt("",linhaSelecionada,4);
            this.tblItens.setValueAt("",linhaSelecionada,5);            
            
            if(linhaSelecionada < (numeroItens -1))
            {
                for(int i = linhaSelecionada;i < (numeroItens -1);i++)
                {
                    this.tblItens.setValueAt(this.tblItens.getValueAt(i+1,0),i,0);
                    this.tblItens.setValueAt(this.tblItens.getValueAt(i+1,1),i,1);
                    this.tblItens.setValueAt(this.tblItens.getValueAt(i+1,2),i,2);
                    this.tblItens.setValueAt(this.tblItens.getValueAt(i+1,3),i,3);
                    this.tblItens.setValueAt(this.tblItens.getValueAt(i+1,4),i,4);
                    this.tblItens.setValueAt(this.tblItens.getValueAt(i+1,5),i,5);
                }
                this.tblItens.setValueAt("",numeroItens -1,0);
                this.tblItens.setValueAt("",numeroItens -1,1);
                this.tblItens.setValueAt("",numeroItens -1,2);
                this.tblItens.setValueAt("",numeroItens -1,3);
                this.tblItens.setValueAt("",numeroItens -1,4);
                this.tblItens.setValueAt("",numeroItens -1,5);
            }
            numeroItens--;
            
            if (numeroItens == 0)
            {
                this.btExcluirItem.setEnabled(false);
                this.btConfirmar.setEnabled(false);
            }
            if (numeroItens < 20)
            {
                this.btAdicionarItem.setEnabled(true);
            }
        }
        
        if(objeto == this.btCancelarItem)
        {
            cancelarItem();
            btConfirmarAlteracao.setEnabled(false);
            btAdicionarItem.setEnabled(true);
            btAlterarItem.setEnabled(true);
        }
        
        if(objeto == btProximoLeitura)
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
        
        if(objeto == btProximo)
        {
            boolean confirmado = true; // Se os dados forem válidos autoriza a mudança de painel.
            // Preenchimento dos dados essenciais da requisição.
            if(indiceCard == 0)
            {
                try
                {
                    String freteSelecionado = ((String)this.cbxFrete.getSelectedItem()).substring(0,1);
                    
                    this.requisicaoCompra.definirFornecedor((Fornecedor)this.fornecedores.get(this.cbxFornecedor.getSelectedIndex()));
                    this.requisicaoCompra.definirCondicaoPagamento(this.txtCondicoesPagamento.getText());
                    this.requisicaoCompra.definirFormaPagamento((FormaPagamento)formasPagamento.get(cbxFormaPagamento.getSelectedIndex()));
                    this.requisicaoCompra.definirTransportadora((this.transportadoras.get(this.cbxTransportadora.getSelectedIndex()) instanceof Transportadora?(Transportadora)this.transportadoras.get(this.cbxTransportadora.getSelectedIndex()):null));
                    this.requisicaoCompra.definirResponsavelEmissao(new Colaborador(this.responsavelEmissao));
                    this.requisicaoCompra.definirDepartamento(new Departamento(this.obterDepartamentoSolicitante()));
                    this.requisicaoCompra.definirDataLimiteEntrega(this.txtDataLimiteEntrega.getText());
                    this.requisicaoCompra.definirTipoFrete(freteSelecionado);
                    Calendario calendario = new Calendario();
                    this.requisicaoCompra.definirDataEmissao(calendario.dataHoje("dd/MM/yyyy"));
                    this.requisicaoCompra.definirObservacao(this.txaObservacoes.getText());
                    
                    if(this.requisicaoCompra.obterFornecedor().possuiApenasItensIndependentes(this.aplicacao.obterConexao()))
                    {
                        JOptionPane.showMessageDialog(aplicacao,"Atenção: O fornecedor selecionado só oferece itens independentes de pedidos do cliente.\n A seleção de pedidos será desabilitada.","Atenção",JOptionPane.WARNING_MESSAGE);
                        btAdicionar.setEnabled(false);
                        btRemover.setEnabled(false);
                    }
                }
                catch(NumberFormatException n)
                {
                        JOptionPane.showMessageDialog(aplicacao,"Erro: Valor incorreto.","Erro",JOptionPane.ERROR_MESSAGE);
                        confirmado = false;
                        n.printStackTrace();
                }
                catch(Exception e)
                {
                        JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
                        confirmado = false;
                        e.printStackTrace();
                }
            }
            // Seleção dos pedidos dos clientes.
            if(indiceCard == 1)
            {
                // Seleciona a necessidade dos itens a serem requisitados.
                String query =  "select i.codigo,i.descricao,fi.valor_item,fi.referencia_fornecedor,i.percentual_ipi,(sum(qmp.quantidade * mp.quantidade) + ((i.percentual_perda * sum(qmp.quantidade * mp.quantidade))/100)) as necessaria " +
                        "from item i, modelo_pedido mp, quantidade_materia_prima qmp, fornecedor_item fi " +
                        "where mp.referencia = qmp.referencia and qmp.produto = mp.modelo and mp.numero_sola = qmp.numero_sola and qmp.item = i.codigo ";
                
                for(int i = 0;i < pedidosSelecionados.size();i++)
                {
                    if(i == 0)
                        query += "and (";
                    query += "mp.pedido = "+ ((Pedido)pedidosSelecionados.get(i)).obterCodigo();
                    if((i+1) < pedidosSelecionados.size())
                        query += " or ";
                    if((i + 1) == pedidosSelecionados.size())
                        query += ") ";
                } 
                query += "and i.codigo = fi.item and fi.fornecedor = "+ ((Fornecedor)this.fornecedores.get(cbxFornecedor.getSelectedIndex())).obterCodigo() + " and i.codigo not in (select distinct item_requisicao from pedido_requisicao_compra ";
                for(int i = 0;i < pedidosSelecionados.size();i++)
                {
                    if(i == 0)
                        query += "where ";
                    query += "pedido = "+ ((Pedido)pedidosSelecionados.get(i)).obterCodigo();
                    if((i+1) < pedidosSelecionados.size())
                        query += " or ";
                }
                query += ") group by i.codigo,i.descricao,i.percentual_perda,fi.valor_item,fi.referencia_fornecedor,i.percentual_ipi";
                numeroItens = 0;
                for(int i = 0;i < 100;i++)
                {
                    this.tblItens.setValueAt("",i,0);
                    this.tblItens.setValueAt("",i,1);
                    this.tblItens.setValueAt("",i,2);
                    this.tblItens.setValueAt("",i,3);
                    this.tblItens.setValueAt("",i,4);
                    this.tblItens.setValueAt("",i,5);
                }
                if(pedidosSelecionados.size() > 0)
                {
                    try
                    {
                        ResultSet rsItensPedidos = this.aplicacao.obterConexao().executarConsulta(query);
                        itensRequisicao.removeAllElements();
                        // Associa cada item a requisição de compra
                        while(rsItensPedidos.next())
                        {
                            Item item = new Item(rsItensPedidos.getInt("codigo"),rsItensPedidos.getString("descricao"),new FornecedorItem(rsItensPedidos.getFloat("valor_item"),rsItensPedidos.getString("referencia_fornecedor")));
                            float valorItem = item.obterFornecedorItem().obterValorItem();
                            int percentualIPI = rsItensPedidos.getInt("percentual_ipi");
                            float quantidadeNecessaria = rsItensPedidos.getFloat("necessaria");
                            ItemRequisicao itemRequisicao = new ItemRequisicao(item,this.requisicaoCompra,quantidadeNecessaria,quantidadeNecessaria,0.0f,valorItem,percentualIPI);
                            itensRequisicao.addElement(itemRequisicao);
                            
                            this.tblItens.setValueAt(item.obterDescricao(),numeroItens,0);
                            this.tblItens.setValueAt("" + itemRequisicao.getQuantidadeItem(),numeroItens,1);
                            this.tblItens.setValueAt("" + itemRequisicao.obterValorUnitario(),numeroItens,2);
                            this.tblItens.setValueAt("" + itemRequisicao.obterValorTotal(),numeroItens,3);
                            this.tblItens.setValueAt("" + itemRequisicao.obterPercentualIPI(),numeroItens,4);
                            this.tblItens.setValueAt("" + itemRequisicao.obterValorTotalComIPI(),numeroItens,5);
                            numeroItens++;
                        }
                        rsItensPedidos.close();
                    }
                    catch(SQLException e)
                    {
                        JOptionPane.showMessageDialog(aplicacao,"Erro: As quantidades necessárias para os pedidos selecionados \n já foram atendidas em uma requisicao anterior.","Erro",JOptionPane.ERROR_MESSAGE);
                        confirmado = false;
                        e.printStackTrace();
                    }
                    catch(Exception ex)
                    {
                        JOptionPane.showMessageDialog(aplicacao,"Erro: " + ex.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
                        //confirmado = false;
                        ex.printStackTrace();
                    }
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
            redimencionar();
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
                this.requisicaoCompra.cadastrarRequisicaoCompra();
                this.requisicaoCompra.associarItens(itensRequisicao,pedidosSelecionados);
                this.setVisible(false);
                
                RelatorioRequisicaoCompra relRequisicaoCompra = new RelatorioRequisicaoCompra(this.requisicaoCompra,this.pedidosSelecionados,"ESTOQUE");
                Vector paginas = relRequisicaoCompra.paginar(aplicacao.obterFormatoPagina());
                Impressora impressora = new Impressora();
                impressora.addPaginas(paginas,aplicacao.obterFormatoPagina());
                impressora.imprimir();
                relRequisicaoCompra = new RelatorioRequisicaoCompra(this.requisicaoCompra,this.pedidosSelecionados,"COMPRAS");
                paginas = relRequisicaoCompra.paginar(aplicacao.obterFormatoPagina());
                impressora = new Impressora();
                impressora.addPaginas(paginas,aplicacao.obterFormatoPagina());
                impressora.imprimir();
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
	
        if(componente == txtQuantidade)
            txtQuantidade.selectAll();
        if(componente == txtValorUnitario)
            txtValorUnitario.selectAll();
        if(componente == txtTotal)
            txtTotal.selectAll();        
        if(componente == txtPercentualIpi)
            txtPercentualIpi.selectAll();        
        if(componente == txtIpi)
            txtIpi.selectAll();
    }
    
    public void focusLost(FocusEvent e) 
    {
        Component componente = e.getComponent();
	
        if(componente == this.txtQuantidade)
        {
            this.calcularPreco();
        }
    }    
}