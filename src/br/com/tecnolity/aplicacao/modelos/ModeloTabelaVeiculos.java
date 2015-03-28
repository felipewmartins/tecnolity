package br.com.tecnolity.aplicacao.modelos;

import br.com.tecnolity.util.*;

public class ModeloTabelaVeiculos extends ModeloTabela
{
    public ModeloTabelaVeiculos() 
    {
        super();
    }
    
    public Object getValueAt(int linha, int coluna)
    {
        if(coluna == 5 || coluna == 6)
            return Numero.inverterSeparador(((String[])cache.elementAt(linha))[coluna]);
        else
            return ((String[])cache.elementAt(linha))[coluna];
    }
}