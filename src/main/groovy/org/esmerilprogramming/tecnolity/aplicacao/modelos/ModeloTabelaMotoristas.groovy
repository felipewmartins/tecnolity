package org.esmerilprogramming.tecnolity.aplicacao.modelos

import org.esmerilprogramming.tecnolity.util.*


public class ModeloTabelaMotoristas extends ModeloTabela
{
    public ModeloTabelaMotoristas() 
    {
        super()
    }
    
    public Object getValueAt(int linha, int coluna)
    {
        if(coluna == 5)
            return Calendario.ajustarFormatoDataBanco(((String[])cache.elementAt(linha))[coluna])
        else
            return ((String[])cache.elementAt(linha))[coluna]
    }
}