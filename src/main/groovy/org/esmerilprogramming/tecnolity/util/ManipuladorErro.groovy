package org.esmerilprogramming.tecnolity.util

import java.util.*

interface ManipuladorErro {
  Vector getErrosFatais()

  Vector getAlertas()

  Vector getErros()

  void addAlerta(String p0)

  void addErro(String p0)

  void addErroFatal(String p0)

  void alerta(String p0) throws Exception

  boolean existeErro()
}
