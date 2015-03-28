package br.com.tecnolity.aplicacao.modelos;

public class ModeloTabelaCliente extends ModeloTabela
{
    public ModeloTabelaCliente() 
    {
        super();
    }
    
    public Object getValueAt(int linha, int coluna)
    {
        return ((String[])cache.elementAt(linha))[coluna];
    }
}