package br.com.tecnolity.aplicacao.relatorios;

import java.awt.print.*;
import java.util.*;

/**
 * @author hildeberto
 */

public class Impressora
{
    private PrinterJob trabalhoImpressao;
    private Book livro;
    private int numeroPaginas;
   
	public Impressora()
	{
        trabalhoImpressao = PrinterJob.getPrinterJob();
        livro = new Book();
	}
    
    public void addPaginas(Vector paginas, PageFormat formato)
    {
        numeroPaginas = paginas.size();
        for(int i = 0;i < numeroPaginas;i++)
        {
            livro.append((Pagina)paginas.get(i),formato);
        }
    }
        
    public void imprimir() throws PrinterException
    {
        if(numeroPaginas > 0)
        {
            trabalhoImpressao.setPageable(livro);
            if(trabalhoImpressao.printDialog())
            {
                trabalhoImpressao.print();
            }
        }
        else
            throw new PrinterException("Nenhuma página foi adicionada a impressora.");
    }
}