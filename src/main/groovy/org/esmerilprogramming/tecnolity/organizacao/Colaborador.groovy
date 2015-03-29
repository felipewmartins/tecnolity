package org.esmerilprogramming.tecnolity.organizaca

import org.esmerilprogramming.tecnolity.util.ConexaoDB
import org.esmerilprogramming.tecnolity.util.PessoaFisica

class Colaborador extends PessoaFisica {
  abstract void addColaborador(final ConexaoDB p0) throws Exception
  abstract void removeColaborador(final ConexaoDB p0) throws Exception
  abstract void setColaborador(final ConexaoDB p0) throws Exception
  abstract Colaborador getColaborador(final ConexaoDB p0) throws Exception
}
