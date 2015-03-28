package br.com.tecnolity.administracao;

import java.util.Vector;
import java.sql.*;

import br.com.tecnolity.util.*;

/**
   * Projeto: 001 - Tecnolity <br>
   * Autor do Código: Hildeberto Mendonça Filho <br>
   * Nome do Arquivo: Departamento.java <br>
   * Linguagem: Java <br>
   *  <br>
   * Requerimentos: Requer máquina virtual java versão 1.3 ou superior. <br>
   *  <br>
   * Objetivo: Classe que representa um departamento da organização. <br>
   *  <br>
   * Objetivo definido por: Hildeberto Mendonça <br>
   * Início da Programação: 25/12/2001 <br>
   * Fim da Programação: <br>
   * Última Versão: 1.0 <br>
*/

public class Departamento
{
    private int codigo;
    private String nomeDepartamento;
    private Colaborador responsavel;

    public Departamento(int codigo, String nomeDepartamento, Colaborador responsavel)
    {
        this.codigo = codigo;
        this.nomeDepartamento = nomeDepartamento;
        this.responsavel = responsavel;
    }

    public Departamento(int codigo, String nomeDepartamento)
    {
        this.codigo = codigo;
        this.nomeDepartamento = nomeDepartamento;
    }

    public Departamento(int codigo)
    {
        this.codigo = codigo;
    }
    
    public Departamento()
    {
        
    }
	
    public int obterCodigo()
    {
        return this.codigo;
    }

    public String obterNomeDepartamento()
    {
        return this.nomeDepartamento;	
    }

    public Colaborador obterResponsavel()
    {
        return this.responsavel;
    }
    
    public void definirCodigo(int codigo)
    {
        this.codigo = codigo;
    }

    public void definirNomeDepartamento(String nomeDepartamento)
    {
        this.nomeDepartamento = nomeDepartamento;
    }

    public void definirResponsavel(Colaborador responsavel)
    {
        this.responsavel = responsavel;
    }
    
    public void carregarDepartamento(Conexao conexao) throws Exception
    {
        ResultSet dadosDepartamento = conexao.executarConsulta("select * from departamento where codigo = " + this.codigo);
        if(dadosDepartamento.next())
        {
            this.definirNomeDepartamento(dadosDepartamento.getString("departamento"));
            String responsavel = dadosDepartamento.getString("responsavel");
            this.definirResponsavel((responsavel != null)?new Colaborador(responsavel):null);
        }
    }
    
    public Vector carregarDepartamentos(Conexao conexao) throws Exception
    {
        ResultSet dadosDepartamento;
        Vector departamentos = new Vector();
        try
        {
            dadosDepartamento = conexao.executarConsulta("select codigo, d.departamento, nome_completo as responsavel from departamento d, usuario u where responsavel *= usuario order by d.departamento asc");
            departamentos.addElement(null);
            String responsavel, nomeDepartamento;
            int codigo;
            
            while(dadosDepartamento.next())
            {
                codigo = dadosDepartamento.getInt("codigo");
                nomeDepartamento = dadosDepartamento.getString("departamento");
                responsavel = dadosDepartamento.getString("responsavel");
                if(responsavel == null)
                    departamentos.addElement(new Departamento(codigo,nomeDepartamento,null));
                else
                    departamentos.addElement(new Departamento(codigo,nomeDepartamento,new Colaborador(responsavel)));
            }
            dadosDepartamento.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return departamentos;
    }
    
    public Vector carregarNomesDepartamentos(Conexao conexao) throws Exception
    {
    	ResultSet dadosDepartamento;
        Vector departamentos = new Vector();
        dadosDepartamento = conexao.executarConsulta("select * from departamento order by departamento asc");
        departamentos.addElement("Selecione...");
        while(dadosDepartamento.next())
        {
            departamentos.addElement(new Departamento(dadosDepartamento.getInt("codigo"),dadosDepartamento.getString("departamento")));
        }
        dadosDepartamento.close();        
        return departamentos;
    }
        
    public void cadastrarDepartamento(String nomeDepartamento, Colaborador responsavel) throws Exception
    {
        String query = "insert into departamento (departamento, responsavel) values ('"+ nomeDepartamento +"',"+ ((responsavel == null)?"NULL":"'" + responsavel.obterMatricula() + "'") +")";
		Conexao conexao = new Conexao('T');
		if (conexao.abrirConexao())
		{
			conexao.executarAtualizacao(query);
			conexao.fecharConexao();
		}
        else
        {
            Exception e = new Exception("Não foi possível cadastrar o Departamento Informado.");
            throw e;
        }
    }
    
    public void alterarDepartamento(String nomeDepartamento, Colaborador responsavel) throws Exception
    {
        String query = "update departamento set departamento = '"+ nomeDepartamento +"', responsavel = "+ ((responsavel == null)?"NULL":"'" + responsavel.obterMatricula() +"'") + " where codigo = " + this.codigo;
		Conexao conexao = new Conexao('T');
		if (conexao.abrirConexao())
		{
			conexao.executarAtualizacao(query);
			conexao.fecharConexao();
		}
        else
        {
            Exception e = new Exception("Não foi possível alterar o Departamento Informado.");
            throw e;
        }
    }
    
    public void excluirDepartamento() throws Exception
    {
        String query = "delete from departamento where codigo = " + this.codigo;
		Conexao conexao = new Conexao('T');
		if (conexao.abrirConexao())
		{
			conexao.executarAtualizacao(query);
			conexao.fecharConexao();
		}
        else
        {
            Exception e = new Exception("Não foi possível excluir o Departamento Informado.");
            throw e;
        }
    }
    
    public String toString()
    {
        return this.nomeDepartamento;
    }
}