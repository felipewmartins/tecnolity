package org.esmerilprogramming.tecnolity.aplicacao.relatorios

import java.util.*
import org.esmerilprogramming.tecnolity.suprimentos.*
import org.esmerilprogramming.tecnolity.util.*

class RelatorioItens extends Relatorio
{
  private Vector itens
    private String titulo

    RelatorioItens(Vector itens, String titulo)
    {
      this.itens = itens
        this.titulo = titulo
    }

  String gerarRelatorio()
  {
    Calendario calendario = new Calendario()
      conteudo = new StringBuffer()
      conteudo.append(Texto.obterStringTamanhoFixo(titulo,29) + "                                 TECNOLITY DO NORDESTE LTDA")
      conteudo.append(QUEBRA)
      conteudo.append("========================================================================================")
      conteudo.append(QUEBRA)
      conteudo.append("                                                                        Data: " + calendario.dataHoje("dd/MM/yyyy"))
      conteudo.append(QUEBRA)
      conteudo.append("Cod.  | Item                             | Unid. | Categoria    | Quant.    | Q. Mínima ")
      conteudo.append(QUEBRA)
      conteudo.append("----------------------------------------------------------------------------------------")
      conteudo.append(QUEBRA)
      int linha = 60, alturaItens = 0
      String quantMinima = ""
      for(int i = 1;i < itens.size();i++)
      {
        Item item = (Item)itens.get(i)
          if(item.obterQuantidade() <= item.obterQuantidadeMinima())
            quantMinima = "*"
          else
            quantMinima = ""
              conteudo.append("" + Texto.obterNumeroTamanhoFixo("" + item.obterCodigo(),6,"0") +"|"+ Texto.obterStringTamanhoFixo(item.obterDescricao(),34) +"|"+ Texto.obterStringTamanhoFixo(item.obterUnidade().obterNomeUnidade(),7) +"|"+ Texto.obterStringTamanhoFixo(item.obterCategoria().obterNomeCategoria(),14) + "|" + Texto.obterNumeroTamanhoFixo("" + Numero.formatarValorNumerico(item.obterQuantidade(),3,","),11," ") + "|" + Texto.obterNumeroTamanhoFixo("" + Numero.formatarValorNumerico(item.obterQuantidadeMinima(),3,","),11," ") + quantMinima)
              conteudo.append(QUEBRA)
              alturaItens = linha + (i * 10)
      }
    conteudo.append("----------------------------------------------------------------------------------------")
      return conteudo.toString()
  }
}
