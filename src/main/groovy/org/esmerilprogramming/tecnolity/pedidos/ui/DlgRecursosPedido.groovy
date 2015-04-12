package org.esmerilprogramming.tecnolity.pedidos.ui

import java.awt.*

import java.awt.event.*
import javax.swing.*
import org.esmerilprogramming.tecnolity.aplicacao.Aplicacao
import org.esmerilprogramming.tecnolity.aplicacao.modelos.*
import org.esmerilprogramming.tecnolity.aplicacao.relatorios.*
import org.esmerilprogramming.tecnolity.pedidos.*
import org.esmerilprogramming.tecnolity.suprimentos.*
import org.esmerilprogramming.tecnolity.suprimentos.ui.DlgDadosRequisicaoInterna

class DlgRecursosPedido extends JDialog implements ActionListener
{
  final int IDENTIFICADOR = 33

    private Aplicacao aplicacao
    private Pedido[] pedidos
    private Container conteudo
    private JButton btRequisitarSelecionado, btRequisitarTudo, btImprimir, btFechar
    private JTable tblRecursos
    private ModeloTabela modeloTabelaRecursos

    DlgRecursosPedido(Aplicacao aplicacao, Pedido pedido) {
      super(aplicacao, true)
        this.setTitle("Recursos Necessários para produção do Pedido")

        this.aplicacao = aplicacao
        this.pedidos = new Pedido[1]
        this.pedidos[0] = pedido
        montarInterface()
    }

  DlgRecursosPedido(Aplicacao aplicacao, Pedido[] pedidos) {
    super(aplicacao, true)
      this.setTitle("Recursos Necessários para produção do Pedido")

      this.aplicacao = aplicacao
      this.pedidos = pedidos
      montarInterface()
  }

  void montarInterface() {
    conteudo = this.getContentPane()

      String strLabel = "Recursos Necessários para a produção do pedido No. "
      for(int i = 0;i < pedidos.length;i++) {
        strLabel  += "" + pedidos[i].obterCodigo()
          if((i + 1) < pedidos.length)
            strLabel  += ", "
      }

    conteudo.add(new JLabel(strLabel), BorderLayout.NORTH)

      modeloTabelaRecursos = new ModeloTabela()
      modeloTabelaRecursos.definirConexao(aplicacao.obterConexao())
      String query =  "select i.codigo, i.descricao, i.quantidade as disponivel, (sum(qmp.quantidade * mp.quantidade)  +  ((i.percentual_perda * sum(qmp.quantidade * mp.quantidade))/100)) as necessaria, (i.quantidade - (sum(qmp.quantidade * mp.quantidade) + ((i.percentual_perda * sum(qmp.quantidade * mp.quantidade))/100))) as saldo " +
      "from item i, modelo_pedido mp, quantidade_materia_prima qmp "  + 
      "where mp.referencia = qmp.referencia and qmp.produto = mp.modelo and mp.numero_sola = qmp.numero_sola and qmp.item = i.codigo and ("
      for(int i = 0;i < pedidos.length;i++) {
        query  += "mp.pedido = " + pedidos[i].obterCodigo()
          if((i + 1) < pedidos.length)
            query  += " or "
      }
    query  += ") group by i.codigo, i.descricao, i.quantidade, i.percentual_perda"
      modeloTabelaRecursos.definirConsulta(query)
      tblRecursos = new JTable(modeloTabelaRecursos)
      tblRecursos.setPreferredScrollableViewportSize(new Dimension(500, 250))
      tblRecursos.addRowSelectionInterval(0, 0)
      JScrollPane scroll = new JScrollPane(tblRecursos)
      conteudo.add(scroll, BorderLayout.CENTER)

      JPanel pnlComandos = new JPanel(new FlowLayout(FlowLayout.RIGHT))

      if(pedidos.length == 1) {
        btRequisitarSelecionado = new JButton("Requisitar Selecionado")
          btRequisitarSelecionado.addActionListener(this)
          pnlComandos.add(btRequisitarSelecionado)

          btRequisitarTudo = new JButton("Requisitar Tudo")
          btRequisitarTudo.addActionListener(this)
          pnlComandos.add(btRequisitarTudo)
      }

    btImprimir = new JButton("Imprimir")
      btImprimir.addActionListener(this)
      pnlComandos.add(btImprimir)

      btFechar = new JButton("Fechar")
      btFechar.addActionListener(this)
      pnlComandos.add(btFechar)

      conteudo.add(pnlComandos, BorderLayout.SOUTH)
      this.pack()

      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize()
      this.setBounds((screenSize.width/2) - (this.getBounds().width/2), 
          (screenSize.height/2) - (this.getBounds().height/2) - 30, 
          this.getBounds().width, 
          this.getBounds().height)
  }

  void actionPerformed(java.awt.event.ActionEvent actionEvent) {
    Object objeto = actionEvent.getSource()

      if(objeto == btRequisitarSelecionado) {
        if(tblRecursos.getSelectedRows().length == 1) {
          Vector itensRequisicaoInterna = new Vector()
            int linha = tblRecursos.getSelectedRow()
            try
            {
              itensRequisicaoInterna.addElement(new ItemRequisicaoInterna(new Item(Integer.parseInt((String)tblRecursos.getValueAt(linha, 0)), 
                      (String)tblRecursos.getValueAt(linha, 1)), 
                    Float.parseFloat((String)tblRecursos.getValueAt(linha, 3))))
                DlgDadosRequisicaoInterna dlgDadosRequisicaoInterna = new DlgDadosRequisicaoInterna(aplicacao, this.pedidos[0], itensRequisicaoInterna)
                dlgDadosRequisicaoInterna.setVisible(true)
            }
          catch (e) {
            JOptionPane.showMessageDialog(aplicacao, "Erro: "  +  e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE)
              e.printStackTrace()
          }
        }
        else
        {
          JOptionPane.showMessageDialog(aplicacao, "Atenção: Selecione apenas um item da lista.", "Atenção", JOptionPane.WARNING_MESSAGE)
        }
      }

    if(objeto == btRequisitarTudo) {
      Vector itensRequisicaoInterna = new Vector()
        for(int i = 0;i < tblRecursos.getRowCount();i++) {
          try
          {
            itensRequisicaoInterna.addElement(new ItemRequisicaoInterna(new Item(Integer.parseInt((String)tblRecursos.getValueAt(i, 0)), 
                    (String)tblRecursos.getValueAt(i, 1)), 
                  Float.parseFloat((String)tblRecursos.getValueAt(i, 3))))

          }
          catch (e) {
            JOptionPane.showMessageDialog(aplicacao, "Erro: "  +  e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE)
              e.printStackTrace()
          }
        }
      DlgDadosRequisicaoInterna dlgDadosRequisicaoInterna = new DlgDadosRequisicaoInterna(aplicacao, this.pedidos[0], itensRequisicaoInterna)
        dlgDadosRequisicaoInterna.setVisible(true)
    }

    if(objeto == btImprimir) {
      RelRecursosPedido relRecursosPedido = new RelRecursosPedido(aplicacao, pedidos)
        relRecursosPedido.imprimir()
    }

    if(objeto == btFechar) {
      this.setVisible(false)
    }
  }
}
