/**
   * Projeto: 001 - Tecnolity
   * Autor do Código: Hildeberto Mendonça Filho
   * Nome do Arquivo: Unidade.java
   * Linguagem: Java
   * 
   * Requerimentos: Requer máquina virtual java versão 1.3 ou superior.
   * 
   * Objetivo: Classe que representa a unidade que quantifica os itens
   * de estoque e os produtos fabricados pela organização.
   * 
   * Objetivo definido por: Hildeberto Mendonça
   * Início da Programação: 31/01/2002
   * Fim da Programação:
   * Última Versão: 1.0
*/

package org.esmerilprogramming.tecnolity.suprimentos;

import org.esmerilprogramming.tecnolity.util.*;

public class Unidade
{
	private int codigo;
	private String nomeUnidade;
	
	public Unidade(int codigo, String nomeUnidade)
	{
		this.codigo = codigo;
		this.nomeUnidade = nomeUnidade;
	}	
	
	public Unidade(int codigo)
	{
		this.codigo = codigo;
	}
	
	public Unidade()
	{
		
	}
	
	public int obterCodigo()
	{
		return codigo;
	}
	
	public String obterNomeUnidade()
	{
		return this.nomeUnidade;
	}
	
	public boolean cadastrarUnidade(String nomeUnidade)
	{
		String query = "insert into unidade (unidade) values ('"+ nomeUnidade +"')";
		Conexao conexao = new Conexao('T');
		if (conexao.abrirConexao())
		{
			conexao.executarAtualizacao(query);
			conexao.fecharConexao();
			return true;
		}
		return false;
	}
}