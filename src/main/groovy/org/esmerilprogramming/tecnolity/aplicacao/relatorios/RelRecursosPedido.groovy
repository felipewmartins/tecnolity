package org.esmerilprogramming.tecnolity.aplicacao.relatorios

import java.awt.*
import java.awt.print.*
import java.sql.*
import javax.swing.*
import org.esmerilprogramming.tecnolity.aplicacao.*
import org.esmerilprogramming.tecnolity.pedidos.*
import org.esmerilprogramming.tecnolity.util.*

 class RelRecursosPedido extends JPanel implements Printable
{
  private Book book
  private Aplicacao aplicacao
  private PrinterJob tarefaImpressao
  private PageFormat formatoPagina
  private Pedido[] pedidos

   RelRecursosPedido(Aplicacao aplicacao, Pedido[] pedidos)
  {
    this.pedidos = pedidos
    this.aplicacao = aplicacao
    tarefaImpressao = this.aplicacao.obterTarefaImpressao()
    formatoPagina = this.aplicacao.obterFormatoPagina()

    book = new Book()
    book.append(this,formatoPagina)

    tarefaImpressao.setPageable(book)
  }

   void paintComponent(Graphics g)
  {
    super.paintComponent(g)
    desenharPagina((Graphics2D)g)
  }

   void desenharPagina(Graphics2D g2)
  {
    Calendario calendario = new Calendario()
    g2.setPaint(Color.black)
    g2.setFont(new Font("Courier New",Font.BOLD,10))
    g2.drawString("RECURSOS NECESSÁRIOS PARA PRODUÇÃO DE PEDIDOS     TECNOLITY DO NORDESTE LTDA",10,10)
    g2.setFont(new Font("Courier New",Font.PLAIN,10))
    g2.drawString("============================================================================",10,20)
    int alturaLinha
    if(pedidos.length == 1)
    {
      g2.drawString("    Cliente: "+ Texto.obterStringTamanhoFixo(pedidos[0].obterCliente().obterRazaoSocial(),37) +" Data: " + calendario.dataHoje("dd/MM/yyyy 'as' HH:mm"),10,30)
      g2.drawString("    Destino: "+ Texto.obterStringTamanhoFixo(pedidos[0].obterLocalEntrega().obterDescricaoLocal(),20) +" Num. Pedido: " + pedidos[0].obterCodigo() + "      Ordem Compra:" + pedidos[0].obterOrdemCompra(),10,45)
      alturaLinha = 45
    }
    else
    {
      g2.drawString("No. Pedidos: "+ pedidos.length +" Data: " + calendario.dataHoje("dd/MM/yyyy 'as' HH:mm"),10,30)
      g2.drawString("Pedidos",10,45)
      String strPedidos = ""
      for(int i = 0 ; i < pedidos.length ; i++)
      {
        strPedidos += pedidos[i].obterOrdemCompra()
        if((i + 1) < pedidos.length)
          strPedidos += ", "
      }
      String[] linhas = Texto.obterTextoAlinhado(strPedidos,63)
      alturaLinha = 45
      for(int i = 0 ; i < linhas.length ; i++)
      {
        g2.drawString(linhas[i],10,alturaLinha += 10)
      }
    }
    g2.setFont(new Font("Courier New",Font.BOLD,10))
    g2.drawString("Descrição                           | Disponível | Necessário | Saldo",10,alturaLinha += 15)
    g2.setFont(new Font("Courier New",Font.PLAIN,10))
    g2.drawString("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ",10,alturaLinha += 10)
    String query =  "select i.descricao,i.quantidade as disponivel,(sum(qmp.quantidade * mp.quantidade) + ((i.percentual_perda * sum(qmp.quantidade * mp.quantidade))/100)) as necessaria,(i.quantidade - (sum(qmp.quantidade * mp.quantidade) + ((i.percentual_perda * sum(qmp.quantidade * mp.quantidade))/100))) as saldo " +
      "from item i, modelo_pedido mp, quantidade_materia_prima qmp " +
      "where mp.referencia = qmp.referencia and qmp.produto = mp.modelo and mp.numero_sola = qmp.numero_sola and qmp.item = i.codigo and ("
    for(int i = 0 ; i < pedidos.length ; i++)
    {
      query += "mp.pedido = "+ pedidos[i].obterCodigo()
      if((i+1) < pedidos.length)
        query += " or "
    }
    query += ") group by i.descricao,i.quantidade,i.percentual_perda"
    try
    {
      ResultSet rsRecursosPedido = aplicacao.obterConexao().executarConsulta(query)
      String modelo,modeloAnterior = ""
      while(rsRecursosPedido.next())
      {
        g2.drawString(Texto.obterStringTamanhoFixo(rsRecursosPedido.getString("descricao"),35) + " | "+ Texto.obterNumeroTamanhoFixo("" + Numero.inverterSeparador(rsRecursosPedido.getFloat("disponivel")),10," ") +" | "+ Texto.obterNumeroTamanhoFixo("" + Numero.inverterSeparador(rsRecursosPedido.getFloat("necessaria")),10," ") +" | " + Texto.obterNumeroTamanhoFixo("" + Numero.inverterSeparador(rsRecursosPedido.getFloat("saldo")),11," "),10,alturaLinha += 10)
      }
      rsRecursosPedido.close()
    }
    catch(SQLException e)
    {
      e.printStackTrace()
    }
  }

   void imprimir()
  {
    if(tarefaImpressao.printDialog())
    {
      try
      {
        tarefaImpressao.print()
      }
      catch (PrinterException e)
      {
        JOptionPane.showMessageDialog(aplicacao,"Erro de Impressão: " + e.getMessage(),"Erro de Impressão",JOptionPane.ERROR_MESSAGE)
      }
    }
  }

   int print(Graphics graphics, PageFormat formato, int param) throws PrinterException
  {
    Graphics2D g2 = (Graphics2D)graphics
    g2.translate(formato.getImageableX(),formato.getImageableY())
    desenharPagina(g2)
    int i = 0
    return i
  }

}
