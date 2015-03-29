package org.esmerilprogramming.tecnolity.aplicacao.modelos

import org.esmerilprogramming.tecnolity.util.*

public class ModeloTabelaRequisicoesInternas extends ModeloTabela
{
    public ModeloTabelaRequisicoesInternas () 
    {
        super()
    }
    
    public Object getValueAt(int linha, int coluna)
    {        
        if(coluna == 2 || coluna == 3)   
        {
            if(((String[])cache.elementAt(linha))[coluna] != null)
                return Calendario.ajustarFormatoDataBanco(((String[])cache.elementAt(linha))[coluna])
            else
                return ((String[])cache.elementAt(linha))[coluna]
        }
        else
            return ((String[])cache.elementAt(linha))[coluna]        
    }
}