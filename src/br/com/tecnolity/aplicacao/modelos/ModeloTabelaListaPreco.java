package br.com.tecnolity.aplicacao.modelos;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;


/**
 * @author Hildeberto Mendon�a Filho
 */
public class ModeloTabelaListaPreco extends ModeloTabela implements TableModelListener
{
    public ModeloTabelaListaPreco()
    {
        this.addTableModelListener(this);
    }
    public boolean isCellEditable(int row, int col) 
    {
        if (col < 3)
        { 
            return false;
        }
        else
        {
            return true;
        }
    }
        
	public void tableChanged(TableModelEvent arg0)
	{

	}
}