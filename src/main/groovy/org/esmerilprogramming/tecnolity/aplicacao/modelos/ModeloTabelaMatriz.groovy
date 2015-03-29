package org.esmerilprogramming.tecnolity.aplicacao.modelos

 class ModeloTabelaMatriz extends ModeloTabela
{
     ModeloTabelaMatriz() 
    {
        super()
    }
    
     Object getValueAt(int linha, int coluna)
    {
        return ((String[])cache.elementAt(linha))[coluna]
    }
}