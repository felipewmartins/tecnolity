package org.esmerilprogramming.tecnolity.suprimentos

import org.esmerilprogramming.tecnolity.util.*

 class Unidade
{
	private int codigo
	private String nomeUnidade
	
	 Unidade(int codigo, String nomeUnidade)
	{
		this.codigo = codigo
		this.nomeUnidade = nomeUnidade
	}	
	
	 Unidade(int codigo)
	{
		this.codigo = codigo
	}
	
	 Unidade()
	{
		
	}
	
	 int obterCodigo()
	{
		return codigo
	}
	
	 String obterNomeUnidade()
	{
		return this.nomeUnidade
	}
	
	 boolean cadastrarUnidade(String nomeUnidade)
	{
		String query = "insert into unidade (unidade) values ('"+ nomeUnidade +"')"
		Conexao conexao = new Conexao('T')
		if (conexao.abrirConexao())
		{
			conexao.executarAtualizacao(query)
			conexao.fecharConexao()
			return true
		}
		return false
	}
}
