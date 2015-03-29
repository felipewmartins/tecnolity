package org.esmerilprogramming.tecnolity.aplicacao.modelos

 class ModeloTabelaPedido extends ModeloTabela
{
     ModeloTabelaPedido() 
    {
        super()
    }
    
     Object getValueAt(int linha, int coluna)
    {
        return ((String[])cache.elementAt(linha))[coluna]
    }
}