package org.esmerilprogramming.tecnolity.aplicacao.relatorios

import java.sql.*
import org.esmerilprogramming.tecnolity.util.*

class RelatorioMovimentacao extends Relatorio
{
  private String tipo, dataInicio, dataFinal
    private int codigoItem
    private Conexao conexao

    RelatorioMovimentacao(Conexao conexao, String tipo, int codigoItem, String dataInicio, String dataFinal) {
      this.conexao = conexao
        this.tipo = tipo
        this.codigoItem = codigoItem
        this.dataInicio = dataInicio
        this.dataFinal = dataFinal
    }

  String gerarRelatorio() {
    Calendario calendario = new Calendario()
      conteudo = new StringBuffer()
      conteudo.append("INVENTÁRIO DE ITENS DO ESTOQUE                                TECNOLITY DO NORDESTE LTDA")
      conteudo.append(QUEBRA)
      conteudo.append("========================================================================================")
      conteudo.append(QUEBRA)
      conteudo.append("                                                 Período de: "+ dataInicio +"  até: " + dataFinal)
      conteudo.append(QUEBRA)
      conteudo.append("Cod.  | Movimento             | Item                           | Quantidade | Data      ")
      conteudo.append(QUEBRA)
      conteudo.append("----------------------------------------------------------------------------------------")
      conteudo.append(QUEBRA)
      try {
        String sql = "select mi.codigo, (case tipo_movimento when 'AB' then 'Abastecimento' when 'CS' then 'Consumo' when 'VD' then 'Vendas' when 'DS' then 'Descarte' when 'DV' then 'Devolução' when 'DE' then 'Devolução Externa' when 'DP' then 'Depósito' when 'RD' then 'Retirada do Depósito' end) as tipo_movimento, i.descricao, mi.quantidade, data_hora from movimentacao_item mi, item i, usuario u where mi.item = i.codigo and mi.responsavel = u.usuario "
          if(tipo != null)
            sql += "and tipo_movimento = '"+ tipo +"' "
              if(!dataInicio.equals(""))
                sql += "and mi.data_hora >= '"+ Calendario.inverterFormato(dataInicio, "/") +" 00:00:00.000' "
                  if(!dataFinal.equals(""))
                    sql += "and mi.data_hora <= '"+ Calendario.inverterFormato(dataFinal, "/") +" 23:59:59.999' "
                      if(codigoItem > 0)
                        sql += "and i.codigo = " + codigoItem
                          sql += " order by tipo_movimento"

                          ResultSet rsMovimentacoes = conexao.executarConsulta(sql)
                          while(rsMovimentacoes.next()) {
                            conteudo.append("" + Texto.obterNumeroTamanhoFixo(rsMovimentacoes.getString("codigo"), 6, "0") + " "+ Texto.obterStringTamanhoFixo(rsMovimentacoes.getString("tipo_movimento"), 23) +" "+ Texto.obterStringTamanhoFixo(rsMovimentacoes.getString("descricao"), 32) +" "+ Texto.obterNumeroTamanhoFixo(rsMovimentacoes.getString("quantidade"), 12, " ") + "  " + Texto.obterStringTamanhoFixo(rsMovimentacoes.getString("data_hora"), 10))
                              conteudo.append(QUEBRA)
                          }
        rsMovimentacoes.close()
      }
    catch(Exception e){}
    conteudo.append(QUEBRA)
      conteudo.append("----------------------------------------------------------------------------------------")
      return conteudo.toString()
  }
}
