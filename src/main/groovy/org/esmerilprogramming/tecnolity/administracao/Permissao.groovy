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
    this.tela = tela
    this.colaborador = colaborador
    this.tipoAcesso = tipoAcesso
  }

  Permissao(Interface tela, char tipoAcesso) {
    this.tela = tela
    this.tipoAcesso = tipoAcesso
  }

  Permissao(Interface tela, Colaborador colaborador) {
    this.tela = tela
    this.colaborador = colaborador
  }

  char verificarPermissaoAcesso() {
    def db = Conexao.instance.db
    def params = [tela.identificador, colaborador.matricula]
    def query = 'select permissao from permissao where interface = ? and usuario = ?'
    def row = db.firstRow(query, params)
    char retorno = SEM_ACESSO
    if (row.permissao == ESCRITA) {
      retorno = ESCRITA
    }
    retorno
  }
}
