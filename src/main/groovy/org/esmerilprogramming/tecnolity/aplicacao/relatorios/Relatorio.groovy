package org.esmerilprogramming.tecnolity.aplicacao.relatorios

import java.awt.print.*

class Relatorio
{
  protected int numeroLinhas
    protected StringBuffer conteudo
    protected static String QUEBRA = "\n"

    Vector paginar(PageFormat formato) {
      Vector paginas = new Vector()
        String conteudoAImprimir = gerarRelatorio()
        int totalLinhasPagina = (int)(formato.getImageableHeight() / Pagina.ENTRELINHA) - 3
        int posicaoFimLinha
        String linha
        while(!conteudoAImprimir.equals("")) {
          StringBuffer conteudoPagina = new StringBuffer()
            for(int i = 0;i < totalLinhasPagina;i++) {
              posicaoFimLinha = conteudoAImprimir.indexOf("\n")
                if(posicaoFimLinha >= 0) {
                  linha = conteudoAImprimir.substring(0, posicaoFimLinha + 1)
                    conteudoAImprimir = (conteudoAImprimir.length() > 1?conteudoAImprimir.substring(posicaoFimLinha + 1):"")
                }
                else
                {
                  linha = conteudoAImprimir
                    conteudoAImprimir = ""
                }
              conteudoPagina.append(linha)
                if(conteudoAImprimir.equals(""))
                  break
            }
          Pagina pagina = new Pagina(conteudoPagina.toString())
            paginas.addElement(pagina)
        }
      return paginas
    }

  String gerarRelatorio() {
    return "Relatório Vazio"
  }
}
