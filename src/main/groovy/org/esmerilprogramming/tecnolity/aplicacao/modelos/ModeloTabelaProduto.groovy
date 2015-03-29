package org.esmerilprogramming.tecnolity.aplicacao.modelos

public class ModeloTabelaProduto extends ModeloTabela
{
    public ModeloTabelaProduto() 
    {
        super()
    }
    
    public Object getValueAt(int linha, int coluna)
    {
        return ((String[])cache.elementAt(linha))[coluna]
    }
}