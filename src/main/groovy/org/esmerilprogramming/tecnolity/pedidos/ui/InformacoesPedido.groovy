package org.esmerilprogramming.tecnolity.pedidos.ui

import java.awt.*
import java.util.*
import java.awt.event.*
import javax.swing.*
import javax.swing.border.*
import org.esmerilprogramming.tecnolity.aplicacao.Aplicacao
import org.esmerilprogramming.tecnolity.aplicacao.modelos.*
import org.esmerilprogramming.tecnolity.aplicacao.relatorios.*
import org.esmerilprogramming.tecnolity.pedidos.*

 class InformacoesPedido extends JTabbedPane implements ActionListener
{
  private Aplicacao aplicacao
  private JPanel pnlPedido

  /* Objetos da aba de Pedidos */
  private JComboBox cbxSituacao
  private JButton btAdicionarPedido, btAlterarPedido, btCancelarPedido, btAtualizarPedido, btImprimirPedido, btImprimirPorReferencia, btRecursosNecessarios
  private JTable tblPedido
  private String[] situacoes = ["",Pedido.PENDENTE,Pedido.PRODUZINDO,Pedido.FINALIZADO,Pedido.ATRASADO,Pedido.CANCELADO]
  private Pedido pedido

  /* Objetos da aba de Cliente */
  private JButton btAdicionarCliente, btAlterarCliente, btExcluirCliente, btAtualizarCliente
  private JTable tblCliente
  private ModeloTabelaCliente modeloTabelaCliente
  private ModeloTabelaPedido modeloTabelaPedido
  private JPanel pnlCliente

   InformacoesPedido(Aplicacao aplicacao)
  {
    this.setBorder(new LineBorder(Color.black))
    this.aplicacao = aplicacao
    // Conteúdo da Aba Pedidos
    pnlPedido = new JPanel(new BorderLayout())

    JPanel pnlParametro = new JPanel(new FlowLayout(FlowLayout.LEFT))
    JLabel label = new JLabel("Situação: ")
    pnlParametro.add(label)
    cbxSituacao = new JComboBox()
    cbxSituacao.addItem("Todos")
    cbxSituacao.addItem("Pendente")
    cbxSituacao.addItem("Produzindo")
    cbxSituacao.addItem("Finalizado")
    cbxSituacao.addItem("Atrasado")
    cbxSituacao.addItem("Cancelado")
    cbxSituacao.addActionListener(this)

    pnlParametro.add(cbxSituacao)
    pnlPedido.add(pnlParametro, BorderLayout.NORTH)

    modeloTabelaPedido = new ModeloTabelaPedido()
    modeloTabelaPedido.definirConexao(aplicacao.obterConexao())
    tblPedido = new JTable(modeloTabelaPedido)
    JScrollPane scroll = new JScrollPane(tblPedido)
    pnlPedido.add(scroll,BorderLayout.CENTER)

    JPanel pnlAreaComandos = new JPanel()
    JPanel pnlComandos = new JPanel(new GridLayout(7,1,5,5))
    btAdicionarPedido = new JButton("Adicionar Pedido")
    btAdicionarPedido.addActionListener(this)
    pnlComandos.add(btAdicionarPedido)
    btAlterarPedido = new JButton("Alterar Selecionado")
    btAlterarPedido.addActionListener(this)
    pnlComandos.add(btAlterarPedido)
    btCancelarPedido = new JButton("Cancelar Selecionado")
    btCancelarPedido.addActionListener(this)
    pnlComandos.add(btCancelarPedido)
    btAtualizarPedido = new JButton("Atualizar Tabela")
    btAtualizarPedido.addActionListener(this)
    pnlComandos.add(btAtualizarPedido)
    btImprimirPedido = new JButton("Imprimir Selecionado")
    btImprimirPedido.addActionListener(this)
    pnlComandos.add(btImprimirPedido)
    btImprimirPorReferencia = new JButton("Imprimir p/ Referência")
    btImprimirPorReferencia.addActionListener(this)
    pnlComandos.add(btImprimirPorReferencia)
    btRecursosNecessarios = new JButton("Recursos Necessários")
    btRecursosNecessarios.addActionListener(this)
    pnlComandos.add(btRecursosNecessarios)
    pnlAreaComandos.add(pnlComandos)
    pnlPedido.add(pnlAreaComandos, BorderLayout.EAST)

    this.addTab("Pedidos",pnlPedido)

    pnlCliente = new JPanel(new BorderLayout())

    modeloTabelaCliente = new ModeloTabelaCliente()
    modeloTabelaCliente.definirConexao(aplicacao.obterConexao())
    //modeloTabelaCliente.definirConsulta("select codigo,razao_social as 'razao social',CNPJ,contato_comercial as 'Contato Comercial',email as 'e-mail' from cliente order by razao_social asc")
    tblCliente = new JTable(modeloTabelaCliente)
    scroll = new JScrollPane(tblCliente)
    pnlCliente.add(scroll,BorderLayout.CENTER)

    pnlAreaComandos = new JPanel()
    pnlComandos = new JPanel(new GridLayout(4,1,5,5))
    btAdicionarCliente = new JButton("Adicionar Cliente")
    btAdicionarCliente.addActionListener(this)
    pnlComandos.add(btAdicionarCliente)
    btAlterarCliente = new JButton("Alterar Selecionado")
    btAlterarCliente.addActionListener(this)
    pnlComandos.add(btAlterarCliente)
    btExcluirCliente = new JButton("Excluir Selecionado")
    btExcluirCliente.addActionListener(this)
    pnlComandos.add(btExcluirCliente)
    btAtualizarCliente = new JButton("Atualizar Tabela")
    btAtualizarCliente.addActionListener(this)
    pnlComandos.add(btAtualizarCliente)
    pnlAreaComandos.add(pnlComandos)
    pnlCliente.add(pnlAreaComandos, BorderLayout.EAST)
    this.addTab("Clientes",pnlCliente)
  }

  private void atualizarTabelaPedido()
  {
    String query = ""
    if(cbxSituacao.getSelectedIndex() > 0)
    {
      query = "select pc.codigo,pc.ordem_compra,c.razao_social,le.descricao_local,pc.data_emissao,pc.data_entrega,(case pc.status when '"+ Pedido.PENDENTE +"' then 'Pendente' when '"+ Pedido.PRODUZINDO+"' then 'Produzindo' when '"+ Pedido.FINALIZADO +"' then 'Finalizado' when '"+ Pedido.ATRASADO +"' then 'Atrasado' when '"+ Pedido.CANCELADO +"' then 'Cancelado' end) as status " +
        "from pedido_cliente pc, cliente c, local_entrega le " +
        "where pc.cliente = c.codigo and c.codigo = le.cliente and le.codigo_local = pc.local_entrega and pc.status = '"+ situacoes[cbxSituacao.getSelectedIndex()] +"' " +
        "order by pc.codigo desc"
      modeloTabelaPedido.definirConsulta(query)
    }
    else
    {
      query = "select pc.codigo,pc.ordem_compra,c.razao_social,le.descricao_local,pc.data_emissao,pc.data_entrega,(case pc.status when '"+ Pedido.PENDENTE +"' then 'Pendente' when '"+ Pedido.PRODUZINDO+"' then 'Produzindo' when '"+ Pedido.FINALIZADO +"' then 'Finalizado' when '"+ Pedido.ATRASADO +"' then 'Atrasado' when '"+ Pedido.CANCELADO +"' then 'Cancelado' end) as status " +
        "from pedido_cliente pc, cliente c, local_entrega le " +
        "where pc.cliente = c.codigo and c.codigo = le.cliente and le.codigo_local = pc.local_entrega " +
        "order by pc.codigo desc"
      modeloTabelaPedido.definirConsulta(query)
    }
    tblPedido.setModel(modeloTabelaPedido)
    tblPedido.updateUI()
  }

  private void atualizarTabelaCliente()
  {
    modeloTabelaCliente.definirConsulta("select codigo,razao_social as 'razao social',CNPJ,contato_comercial as 'Contato Comercial',email as 'e-mail' from cliente order by razao_social asc")
    tblCliente.setModel(modeloTabelaCliente)
    tblCliente.updateUI()
  }

   void actionPerformed(java.awt.event.ActionEvent actionEvent)
  {
    Object objeto = actionEvent.getSource()

    if(objeto == btAdicionarPedido)
    {
      DlgDadosPedido dlgDadosPedido = new DlgDadosPedido(aplicacao)
      dlgDadosPedido.setVisible(true)
    }

    if(objeto == btAlterarPedido)
    {
      if(tblPedido.getSelectedRow() >=0)
      {
        try
        {
          int linha = tblPedido.getSelectedRow()
          int codigoPedido = Integer.parseInt((String)tblPedido.getValueAt(linha,0))
          Pedido pedido = new Pedido(codigoPedido)
          DlgDadosPedido dlgDadosPedido = new DlgDadosPedido(aplicacao,pedido)
          dlgDadosPedido.setVisible(true)
        }
        catch(Exception e)
        {
          JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar o pedido selecionado.","Erro",JOptionPane.ERROR_MESSAGE)
        }
      }
      else
      {
        JOptionPane.showMessageDialog(aplicacao,"Atenção: Selecione um pedido para alterar os seus dados.","Atenção",JOptionPane.WARNING_MESSAGE)
      }
    }

    if(objeto == btCancelarPedido)
    {
      if(tblPedido.getSelectedRow() >=0)
      {
        if(JOptionPane.showConfirmDialog(aplicacao,"Atenção: Tem certeza que deseja excluir o pedido selecionado?","Atenção",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE) == 0)
        {
          int linha = tblPedido.getSelectedRow()
          int codigoPedido = Integer.parseInt((String)tblPedido.getValueAt(linha,0))
          try
          {
            Pedido pedido = new Pedido(codigoPedido)
            pedido.cancelarPedido()
          }
          catch(Exception e)
          {
            JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível cancelar o pedido.","Erro",JOptionPane.ERROR_MESSAGE)
            e.printStackTrace()
          }
        }
      }
      else
      {
        JOptionPane.showMessageDialog(aplicacao,"Atenção: Selecione o pedido a ser excluído.","Atenção",JOptionPane.WARNING_MESSAGE)
      }
      atualizarTabelaPedido()
    }

    if(objeto == btAtualizarPedido)
    {
      atualizarTabelaPedido()
    }

    if(objeto == btImprimirPedido)
    {
      int linha = tblPedido.getSelectedRow()
      int codigoPedido = Integer.parseInt((String)tblPedido.getValueAt(linha,0))
      try
      {
        Pedido pedido = new Pedido(codigoPedido)
        pedido.carregarPedido(aplicacao.obterConexao())
        RelatorioPedido relPedido = new RelatorioPedido(pedido)
        Vector paginas = relPedido.paginar(aplicacao.obterFormatoPagina())
        Impressora impressora = new Impressora()
        impressora.addPaginas(paginas,aplicacao.obterFormatoPagina())
        impressora.imprimir()
      }
      catch(Exception e)
      {
        JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível imprimir o pedido.","Erro",JOptionPane.ERROR_MESSAGE)
        e.printStackTrace()
      }
    }

    if(objeto == btImprimirPorReferencia)
    {
      int linha = tblPedido.getSelectedRow()
      int codigoPedido = Integer.parseInt((String)tblPedido.getValueAt(linha,0))
      try
      {
        Pedido pedido = new Pedido(codigoPedido)
        pedido.carregarPedido(aplicacao.obterConexao())
        RelatorioPedidoReferencia relPedido = new RelatorioPedidoReferencia(pedido)
        Vector paginas = relPedido.paginar(aplicacao.obterFormatoPagina())
        Impressora impressora = new Impressora()
        impressora.addPaginas(paginas,aplicacao.obterFormatoPagina())
        impressora.imprimir()
      }
      catch(Exception e)
      {
        JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível imprimir o pedido.","Erro",JOptionPane.ERROR_MESSAGE)
        e.printStackTrace()
      }
    }

    if(objeto == btRecursosNecessarios)
    {
      int[] linha = tblPedido.getSelectedRows()
      int[] codigoPedido = new int[linha.length]
      Pedido[] pedidos = new Pedido[linha.length]
      try
      {
        for(int i = 0i < linha.lengthi++)
        {
          codigoPedido[i] = Integer.parseInt((String)tblPedido.getValueAt(linha[i],0))
          pedidos[i] = new Pedido(codigoPedido[i])
          pedidos[i].carregarPedido(aplicacao.obterConexao())
        }
        DlgRecursosPedido dlgRecursosPedido = new DlgRecursosPedido(aplicacao,pedidos)
        dlgRecursosPedido.setVisible(true)
      }
      catch(Exception e)
      {
        JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível exibir os Recursos Necessários.","Erro",JOptionPane.ERROR_MESSAGE)
        e.printStackTrace()
      }
    }

    if(objeto == btAdicionarCliente)
    {
      DlgDadosCliente dlgDadosCliente = new DlgDadosCliente(aplicacao)
      dlgDadosCliente.setVisible(true)
    }

    if(objeto == btAlterarCliente)
    {
      try
      {
        int linha = tblCliente.getSelectedRow()
        DlgDadosCliente dlgDadosCliente = new DlgDadosCliente(aplicacao,new Cliente(Long.parseLong(tblCliente.getValueAt(linha,0).toString())))
        dlgDadosCliente.setVisible(true)
      }
      catch(Exception e)
      {
        JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar o Cliente selecionado.","Erro",JOptionPane.ERROR_MESSAGE)
        e.printStackTrace()
      }
    }

    if(objeto == btExcluirCliente)
    {
      if(JOptionPane.showConfirmDialog(aplicacao,"Atenção: A exclusão de um Cliente implicará na Exclusão de todos os dados \nRelacionados ao mesmo.\nTem certeza que deseja continuar a operação de exclusão?","Atenção",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE) == 0)
      {
        int codigoCliente = Integer.parseInt((String)tblCliente.getValueAt(tblCliente.getSelectedRow(),0))
        Cliente cliente = new Cliente()
        try
        {
          cliente.excluirCliente(aplicacao.obterConexao())
          atualizarTabelaCliente()
        }
        catch(Exception e)
        {
          JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível excluir o Cliente selecionado.","Erro",JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
        }
      }
    }

    if(objeto == btAtualizarCliente)
    {
      atualizarTabelaCliente()
    }

    if (objeto == cbxSituacao)
    {
      this.atualizarTabelaPedido()
    }
  }
}
