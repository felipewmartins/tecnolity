package org.esmerilprogramming.tecnolity.aplicacao.modelos

public class ModeloTabelaPedido extends ModeloTabela
{
    public ModeloTabelaPedido() 
    {
        super()
    }
    
    public Object getValueAt(int linha, int coluna)
    {
        return ((String[])cache.elementAt(linha))[coluna]
    }
}