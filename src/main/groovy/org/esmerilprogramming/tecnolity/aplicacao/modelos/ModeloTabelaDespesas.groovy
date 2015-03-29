package org.esmerilprogramming.tecnolity.aplicacao.modelos

import org.esmerilprogramming.tecnolity.util.*

public class ModeloTabelaDespesas extends ModeloTabela
{
    public ModeloTabelaDespesas () 
    {
        super()
    }
    
    public Object getValueAt(int linha, int coluna)
    {
        if(coluna == 3)
            return Numero.inverterSeparador(((String[])cache.elementAt(linha))[coluna])
        if(coluna == 1)        
            return Calendario.ajustarFormatoDataBanco(((String[])cache.elementAt(linha))[coluna])
        else
            return ((String[])cache.elementAt(linha))[coluna]
    }
}