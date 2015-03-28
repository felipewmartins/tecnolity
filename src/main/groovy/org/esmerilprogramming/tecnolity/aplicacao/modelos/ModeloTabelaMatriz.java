package org.esmerilprogramming.tecnolity.aplicacao.modelos;

public class ModeloTabelaMatriz extends ModeloTabela
{
    public ModeloTabelaMatriz() 
    {
        super();
    }
    
    public Object getValueAt(int linha, int coluna)
    {
        return ((String[])cache.elementAt(linha))[coluna];
    }
}