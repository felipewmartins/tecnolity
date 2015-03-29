package org.esmerilprogramming.tecnolity.aplicacao.modelos

import org.esmerilprogramming.tecnolity.util.*

 class ModeloTabelaMultas extends ModeloTabela
{
     ModeloTabelaMultas () 
    {
        super()
    }
    
     Object getValueAt(int linha, int coluna)
    {
        if(coluna == 3)
            return Numero.inverterSeparador(((String[])cache.elementAt(linha))[coluna])
        else
            return ((String[])cache.elementAt(linha))[coluna]
    }
}