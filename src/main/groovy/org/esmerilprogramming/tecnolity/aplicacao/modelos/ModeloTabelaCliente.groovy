package org.esmerilprogramming.tecnolity.aplicacao.modelos

 class ModeloTabelaCliente extends ModeloTabela
{
     ModeloTabelaCliente() 
    {
        super()
    }
    
     Object getValueAt(int linha, int coluna)
    {
        return ((String[])cache.elementAt(linha))[coluna]
    }
}