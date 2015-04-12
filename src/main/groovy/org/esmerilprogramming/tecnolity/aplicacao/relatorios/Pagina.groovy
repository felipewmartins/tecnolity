package org.esmerilprogramming.tecnolity.aplicacao.relatorios

import java.awt.*
import java.awt.print.*

class Pagina implements Printable
{
  private String conteudo
    private int numeroLinhas
    static int ENTRELINHA = 12
    private int totalLinhasPagina

    Pagina(String conteudo) {
      this.conteudo = conteudo
    }

  void desenharPagina(Graphics2D g2) {
    int posicaoFimLinha = 0
      String conteudoAImprimir = conteudo
      String linha = ''
      while ((numeroLinhas <= totalLinhasPagina) && !conteudoAImprimir.equals('')) {
        posicaoFimLinha = conteudoAImprimir.indexOf('\n')
          if (posicaoFimLinha >= 0) {
            linha = conteudoAImprimir.substring(0, posicaoFimLinha)
              conteudoAImprimir = conteudoAImprimir.substring(posicaoFimLinha  +  1)
          }
          else
          {
            linha = conteudoAImprimir
              conteudoAImprimir = ''
          }
        g2.drawString(linha, 0, numeroLinhas++ * ENTRELINHA)
      }
  }

  int print(Graphics g, PageFormat formato, int pagina) throws PrinterException
  {
    Graphics2D g2 = (Graphics2D)g
      g2.translate(formato.getImageableX(), formato.getImageableY())
      totalLinhasPagina = (int)(formato.getImageableHeight() / ENTRELINHA) - 3
      g2.setPaint(Color.black)
      g2.setFont(new Font('Courier New', Font.PLAIN, 10))
      numeroLinhas = 1
      desenharPagina(g2)
      return Printable.PAGE_EXISTS
  }
}
