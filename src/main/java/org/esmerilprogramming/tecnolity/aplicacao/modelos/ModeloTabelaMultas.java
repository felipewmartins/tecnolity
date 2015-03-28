package br.com.tecnolity.aplicacao.modelos;

import br.com.tecnolity.util.*;

public class ModeloTabelaMultas extends ModeloTabela
{
    public ModeloTabelaMultas () 
    {
        super();
    }
    
    public Object getValueAt(int linha, int coluna)
    {
        if(coluna == 3)
            return Numero.inverterSeparador(((String[])cache.elementAt(linha))[coluna]);
        else
            return ((String[])cache.elementAt(linha))[coluna];
    }
}