package org.esmerilprogramming.tecnolity.producao

import java.util.Vector
import java.sql.*
import org.esmerilprogramming.tecnolity.util.*

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

 class TipoProducao
{
    private int codigo
    private String tipoProducao
    
     TipoProducao(int codigo, String tipoProducao)
    {
        this.codigo = codigo
        this.tipoProducao = tipoProducao
    }

     TipoProducao(int codigo)
    {
        this.codigo = codigo
    }
    
     TipoProducao()
    {
        
    }
	
     int obterCodigo()
    {
        return this.codigo
    }

     String obterTipoProducao()
    {
        return this.tipoProducao	
    }
    
     void definirCodigo(int codigo)
    {
        this.codigo = codigo
    }

     void definirTipoProducao(String tipoProducao)
    {
        this.tipoProducao = tipoProducao
    }
    
     Vector carregarTiposProducao(Conexao conexao) throws Exception
    {
        ResultSet dadosTipoProducao
        Vector tiposProducao = new Vector()
        try
        {
            dadosTipoProducao = conexao.executarConsulta("select * from tipo_producao order by tipo_producao asc")
            tiposProducao.addElement(null)
                        
            while(dadosTipoProducao.next())
            {
                tiposProducao.addElement(new TipoProducao(dadosTipoProducao.getInt("codigo"),dadosTipoProducao.getString("tipo_producao")))
            }
            dadosTipoProducao.close()
        }
        catch (SQLException e)
        {
            e.printStackTrace()
        }
        return tiposProducao
    }
    
     int obterUltimoIdentificador(Conexao conexao) throws Exception
    {
        ResultSet dadosTipoProducao
        try
        {
            dadosTipoProducao = conexao.executarConsulta("select max(codigo) as identificador_maior from tipo_producao")
            if(dadosTipoProducao.next())
            {
                return dadosTipoProducao.getInt("identificador_maior") + 1
            }
            dadosTipoProducao.close()
        }
        catch (SQLException e)
        {
            e.printStackTrace()
        }
        return 1
    }
    
     void cadastrarTipoProducao()
    {
        String query = "insert into tipo_producao (codigo,tipo_producao) values ("+ this.codigo +",'"+ this.tipoProducao +"')"
	Conexao conexao = new Conexao('T')
	if (conexao.abrirConexao())
	{
            conexao.executarAtualizacao(query)
            conexao.fecharConexao()
	}
    }
}