package org.esmerilprogramming.tecnolity.organizacao

import org.esmerilprogramming.tecnolity.util.ConexaoDB

interface Cliente {
  void addCliente(ConexaoDB p0) throws Exception
  void removeCliente(ConexaoDB p0) throws Exception
  Cliente getCliente(ConexaoDB p0) throws Exception
  void setCliente(ConexaoDB p0) throws Exception
}
