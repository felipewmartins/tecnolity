package org.esmerilprogramming.tecnolity.aplicacao.modelos

import org.esmerilprogramming.tecnolity.util.*

public class ModeloTabelaMovimentacoes extends ModeloTabela
{

    public ModeloTabelaMovimentacoes() 
    {
        super()
    }
    
    public Object getValueAt(int linha, int coluna)
    {
        if(coluna == 3)
            return Numero.inverterSeparador(((String[])cache.elementAt(linha))[coluna])
        if(coluna == 4)        
            return Calendario.ajustarFormatoDataBanco(((String[])cache.elementAt(linha))[coluna])
        else
            return ((String[])cache.elementAt(linha))[coluna]
    }
}