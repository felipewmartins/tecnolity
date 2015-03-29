package org.esmerilprogramming.tecnolity.administracao

import java.sql.*

import org.esmerilprogramming.tecnolity.aplicacao.Interface
import org.esmerilprogramming.tecnolity.util.*

/**
* Projeto: 001 - Tecnolity <br>
* Autor do Código: Hildeberto Mendonça Filho <br>
* Nome do Arquivo: Permissao.java <br>
* Linguagem: Java <br>
*  <br>
* Requerimentos: Requer máquina virtual java versão 1.3 ou superior. <br>
*  <br>
* Objetivo: Diálogo que representa a permissão atribuida a um usuário. <br>
*  <br>
* Objetivo definido por: Hildeberto Mendonça <br>
* Início da Programação: 08/02/2002 <br>
* Fim da Programação: <br>
* Última Versão: 1.0  <br>
*/

 class Permissao 
{
     static final char LEITURA = 'L'
     static final char ESCRITA = 'E'
     static final char SEM_ACESSO = 'S'
    
    private Interface tela
    private Colaborador colaborador
    private char tipoAcesso
    
     Permissao(Interface tela, Colaborador colaborador, char tipoAcesso) 
    {
        definirTela(tela)
        definirColaborador(colaborador)
        definirTipoAcesso(tipoAcesso)
    }
    
     Permissao(Interface tela, char tipoAcesso) 
    {
        definirTela(tela)
        definirTipoAcesso(tipoAcesso)
    }
    
     Permissao(Interface tela, Colaborador colaborador) 
    {
        definirTela(tela)
        definirColaborador(colaborador)
    }
    
     Interface obterTela()
    {
        return this.tela
    }
    
     Colaborador obterColaborador()
    {
        return this.colaborador
    }
    
     char obterTipoAcesso()
    {
        return this.tipoAcesso
    }

     void definirTela(Interface tela)
    {
        this.tela = tela
    }
    
     void definirColaborador(Colaborador colaborador)
    {
        this.colaborador = colaborador
    }
    
     void definirTipoAcesso(char tipoAcesso)
    {
        this.tipoAcesso = tipoAcesso
    }
    
    /** Verifica de o usuário tem permissão de leitura ou escrita na tela. Caso
        contrário um caractere de SEM_ACESSO é retornado. */
     char verificarPermissaoAcesso(Conexao conexao)
    {
        try
        {
            String query = "select permissao from permissao where interface = "+ tela.obterIdentificador() +" and usuario = '"+ colaborador.obterMatricula() +"'"
            ResultSet permissoes = conexao.executarConsulta(query)
            if(permissoes.next())
            {
                return permissoes.getString("permissao").charAt(0)
            }
        }
        catch(SQLException e)
        {
            return SEM_ACESSO
        }
        return SEM_ACESSO
    }
}