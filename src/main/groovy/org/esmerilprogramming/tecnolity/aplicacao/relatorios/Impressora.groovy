package org.esmerilprogramming.tecnolity.aplicacao.relatorios

import java.awt.print.*
import java.util.*

class Impressora
{
  private PrinterJob trabalhoImpressao
    private Book livro
    private int numeroPaginas

    Impressora() {
      trabalhoImpressao = PrinterJob.getPrinterJob()
        livro = new Book()
    }

  void addPaginas(Vector paginas, PageFormat formato) {
    numeroPaginas = paginas.size()
      for(int i = 0;i < numeroPaginas;i++) {
        livro.append((Pagina)paginas.get(i),formato)
      }
  }

  void imprimir() throws PrinterException
  {
    if(numeroPaginas > 0) {
      trabalhoImpressao.setPageable(livro)
        if(trabalhoImpressao.printDialog()) {
          trabalhoImpressao.print()
        }
    }
    else
      throw new PrinterException("Nenhuma página foi adicionada a impressora.")
  }
}
