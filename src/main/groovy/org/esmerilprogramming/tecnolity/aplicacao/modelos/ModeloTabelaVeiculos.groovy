package org.esmerilprogramming.tecnolity.aplicacao.modelos

import org.esmerilprogramming.tecnolity.util.*

 class ModeloTabelaVeiculos extends ModeloTabela
{
     ModeloTabelaVeiculos() 
    {
        super()
    }
    
     Object getValueAt(int linha, int coluna)
    {
        if(coluna == 5 || coluna == 6)
            return Numero.inverterSeparador(((String[])cache.elementAt(linha))[coluna])
        else
            return ((String[])cache.elementAt(linha))[coluna]
    }
}