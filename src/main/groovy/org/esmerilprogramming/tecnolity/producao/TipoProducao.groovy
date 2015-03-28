package org.esmerilprogramming.tecnolity.producao;

import java.util.Vector;
import java.sql.*;
import org.esmerilprogramming.tecnolity.util.*;

/**
   * Projeto: 001 - Tecnolity <br>
   * Autor do Código: Hildeberto Mendonça Filho <br>
   * Nome do Arquivo: TipoProducao.java <br>
   * Linguagem: Java <br>
   *  <br>
   * Requerimentos: Requer máquina virtual java versão 1.3 ou superior. <br>
   *  <br>
   * Objetivo: Classe que representa um tipo de produção da fábrica. Cada tipo
   * representa uma linha de produção. <br>
   *  <br>
   * Objetivo definido por: Hildeberto Mendonça <br>
   * Início da Programação: 12/02/2002 <br>
   * Fim da Programação: <br>
   * Última Versão: 1.0 <br>
*/

public class TipoProducao
{
    private int codigo;
    private String tipoProducao;
    
    public TipoProducao(int codigo, String tipoProducao)
    {
        this.codigo = codigo;
        this.tipoProducao = tipoProducao;
    }

    public TipoProducao(int codigo)
    {
        this.codigo = codigo;
    }
    
    public TipoProducao()
    {
        
    }
	
    public int obterCodigo()
    {
        return this.codigo;
    }

    public String obterTipoProducao()
    {
        return this.tipoProducao;	
    }
    
    public void definirCodigo(int codigo)
    {
        this.codigo = codigo;
    }

    public void definirTipoProducao(String tipoProducao)
    {
        this.tipoProducao = tipoProducao;
    }
    
    public Vector carregarTiposProducao(Conexao conexao) throws Exception
    {
        ResultSet dadosTipoProducao;
        Vector tiposProducao = new Vector();
        try
        {
            dadosTipoProducao = conexao.executarConsulta("select * from tipo_producao order by tipo_producao asc");
            tiposProducao.addElement(null);
                        
            while(dadosTipoProducao.next())
            {
                tiposProducao.addElement(new TipoProducao(dadosTipoProducao.getInt("codigo"),dadosTipoProducao.getString("tipo_producao")));
            }
            dadosTipoProducao.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return tiposProducao;
    }
    
    public int obterUltimoIdentificador(Conexao conexao) throws Exception
    {
        ResultSet dadosTipoProducao;
        try
        {
            dadosTipoProducao = conexao.executarConsulta("select max(codigo) as identificador_maior from tipo_producao");
            if(dadosTipoProducao.next())
            {
                return dadosTipoProducao.getInt("identificador_maior") + 1;
            }
            dadosTipoProducao.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return 1;
    }
    
    public void cadastrarTipoProducao()
    {
        String query = "insert into tipo_producao (codigo,tipo_producao) values ("+ this.codigo +",'"+ this.tipoProducao +"')";
	Conexao conexao = new Conexao('T');
	if (conexao.abrirConexao())
	{
            conexao.executarAtualizacao(query);
            conexao.fecharConexao();
	}
    }
}