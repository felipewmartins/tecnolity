package org.esmerilprogramming.tecnolity.administracao

import java.sql.*

import org.esmerilprogramming.tecnolity.aplicacao.Interface
import org.esmerilprogramming.tecnolity.util.*

/**
* Projeto: 001 - Tecnolity <br>
* Autor do C�digo: Hildeberto Mendon�a Filho <br>
* Nome do Arquivo: Permissao.java <br>
* Linguagem: Java <br>
*  <br>
* Requerimentos: Requer m�quina virtual java vers�o 1.3 ou superior. <br>
*  <br>
* Objetivo: Di�logo que representa a permiss�o atribuida a um usu�rio. <br>
*  <br>
* Objetivo definido por: Hildeberto Mendon�a <br>
* In�cio da Programa��o: 08/02/2002 <br>
* Fim da Programa��o: <br>
* �ltima Vers�o: 1.0  <br>
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
    
    /** Verifica de o usu�rio tem permiss�o de leitura ou escrita na tela. Caso
        contr�rio um caractere de SEM_ACESSO � retornado. */
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