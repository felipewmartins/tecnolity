package org.esmerilprogramming.tecnolity.administracao

import java.sql.*

import org.esmerilprogramming.tecnolity.aplicacao.Interface
import org.esmerilprogramming.tecnolity.util.*

class Permissao {
  static final char LEITURA = 'L'
    static final char ESCRITA = 'E'
    static final char SEM_ACESSO = 'S'

    private Interface tela
    private Colaborador colaborador
    private char tipoAcesso

    Permissao(Interface tela, Colaborador colaborador, char tipoAcesso) {
      definirTela(tela)
        definirColaborador(colaborador)
        definirTipoAcesso(tipoAcesso)
    }

  Permissao(Interface tela, char tipoAcesso) {
    definirTela(tela)
      definirTipoAcesso(tipoAcesso)
  }

  Permissao(Interface tela, Colaborador colaborador) {
    definirTela(tela)
      definirColaborador(colaborador)
  }

  /** Verifica de o usuário tem permissão de leitura ou escrita na tela. Caso
    contrário um caractere de SEM_ACESSO é retornado. */
  char verificarPermissaoAcesso(Conexao conexao) {
    try
    {
      String query = "select permissao from permissao where interface = "+ tela.obterIdentificador() +" and usuario = '"+ colaborador.obterMatricula() +"'"
        ResultSet permissoes = conexao.executarConsulta(query)
        if(permissoes.next()) {
          return permissoes.getString("permissao").charAt(0)
        }
    }
    catch(SQLException e) {
      return SEM_ACESSO
    }
    return SEM_ACESSO
  }
}
