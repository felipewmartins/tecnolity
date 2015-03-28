package br.com.tecnolity.aplicacao.modelos;

import java.sql.*;
import java.util.Vector;
import javax.swing.table.*;
import br.com.tecnolity.util.*;

public class ModeloTabelaVisualizacao extends AbstractTableModel
{
    protected Vector cache;
    private int numeroColunas;
    private String[] cabecalhos;	
    private ResultSet resultado;
    private Conexao conexao;

    public ModeloTabelaVisualizacao() 
    {
        cache = new Vector();		
    }	

    public String getColumnName(int i)
    {
        return cabecalhos[i];	
    }

    public int getColumnCount()
    {
        return numeroColunas;
    }

    public int getRowCount()
    {
        return cache.size();
    }

    public Object getValueAt(int linha, int coluna)
    {
        return ((String[])cache.elementAt(linha))[coluna];
    }	

    public void definirConexao(Conexao conexao)
    {
        this.conexao = conexao;
    }

    public void definirConsulta(String consulta) throws SQLException
    {
        cache = new Vector();
        if (conexao.abrirConexao())
        {
            resultado = conexao.executarConsulta(consulta);	
            if (resultado.next())
            {			
                ResultSetMetaData meta = resultado.getMetaData();
                numeroColunas = meta.getColumnCount();
                cabecalhos = new String[numeroColunas];
                for(int i = 1; i <= numeroColunas; i++)
                {
                    cabecalhos[i -1] = meta.getColumnName(i);
                }
                do 
                {
                    String[] dados = new String[numeroColunas];
                    for(int i = 0; i < numeroColunas; i++)
                    {
                        dados[i] = resultado.getString(i + 1);
                    }
                    cache.addElement(dados);
                }
                while(resultado.next());
            }
            resultado.close();				
            fireTableChanged(null);//notificar a criacao de uma nova tabela
        }
    }		
}