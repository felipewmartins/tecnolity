package org.esmerilprogramming.tecnolity.aplicacao.relatorios

import java.sql.*
import org.esmerilprogramming.tecnolity.util.*

public class RelatorioInventario extends Relatorio
{
  private String data
  private Conexao conexao

  public RelatorioInventario(Conexao conexao, String data)
  {
    this.conexao = conexao
    this.data = data
  }

  public String gerarRelatorio()
  {
    Calendario calendario = new Calendario()
    conteudo = new StringBuffer()
    conteudo.append("INVENTÁRIO DE ITENS DO ESTOQUE                                                           TECNOLITY DO NORDESTE LTDA")
    conteudo.append(QUEBRA)
    conteudo.append("===================================================================================================================")
    conteudo.append(QUEBRA)
    conteudo.append("                                                                                                   Data: " + data)
    conteudo.append(QUEBRA)
    conteudo.append('Cod.  | Item                                   | Unid. | Categoria    | Quant.        | Vl. Unit.  | Total (R$)')
    conteudo.append(QUEBRA)
    conteudo.append("-------------------------------------------------------------------------------------------------------------------")
    conteudo.append(QUEBRA)
    try
    {
      String query = "select codigo from item order by descricao"
      ResultSet rsItens = conexao.executarConsulta(query)
      float totalGeral = 0.0f, total
      while(rsItens.next())
      {
        int codigoItem = rsItens.getInt("codigo")
        query = "select i.codigo, i.descricao, u.unidade, c.categoria, hqi.quantidade, hvi.valor_item, (hqi.quantidade * hvi.valor_item) as total " +
          "from historico_quantidade_item hqi, item i, unidade u, categoria_item c, historico_valor_item hvi " +
          "where i.codigo = hqi.codigo and u.codigo = i.unidade and c.codigo = i.categoria and hvi.item = i.codigo and i.codigo = "+ codigoItem +" and "+
          "hqi.data_hora <= '"+ Calendario.inverterFormato(data,"/") +" 23:59:59.999' and " +
          "hqi.data_hora = (select max(data_hora) from historico_quantidade_item where data_hora <= '"+ Calendario.inverterFormato(data,"/") +" 23:59:59.999' and codigo = "+ codigoItem +") and " +
          "hvi.data_atualizacao <= '"+ Calendario.inverterFormato(data,"/") +" 23:59:59.999' and " +
          "hvi.data_atualizacao = (select max(data_atualizacao) from historico_valor_item where data_atualizacao <= '"+ Calendario.inverterFormato(data,"/") +" 23:59:59.999' and item = "+ codigoItem +")"
        ResultSet rsItensInventario = conexao.executarConsulta(query)
        if(rsItensInventario.next())
        {
          conteudo.append("" + Texto.obterNumeroTamanhoFixo(rsItensInventario.getString("codigo"),6,"0") + " "+ Texto.obterStringTamanhoFixo(rsItensInventario.getString("descricao"),40) +" "+ Texto.obterStringTamanhoFixo(rsItensInventario.getString("unidade"),7) +" "+ Texto.obterStringTamanhoFixo(rsItensInventario.getString("categoria"),14) +" "+ Texto.obterNumeroTamanhoFixo(rsItensInventario.getString("quantidade"),15," ") + " " + Texto.obterNumeroTamanhoFixo(rsItensInventario.getString("valor_item"),12," "))
          total = rsItensInventario.getFloat("total")
          totalGeral += total
          conteudo.append(" " + Texto.obterNumeroTamanhoFixo("" + total,15," "))
          conteudo.append(QUEBRA)
        }
        rsItensInventario.close()
      }
      conteudo.append("-------------------------------------------------------------------------------------------------------------------")
      conteudo.append(QUEBRA)
      conteudo.append("                                                                                        Total Geral:"+ Texto.obterNumeroTamanhoFixo("" + totalGeral,15," "))
      rsItens.close()
    }
    catch(Exception e){}
    return conteudo.toString()
  }
}
