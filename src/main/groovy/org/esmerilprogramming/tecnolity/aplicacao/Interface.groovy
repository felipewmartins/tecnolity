package org.esmerilprogramming.tecnolity.aplicacao

import org.esmerilprogramming.tecnolity.util.*
import java.sql.*

class Interface {
  private int identificador
  private String nomeInterface
  private String descricaoInterface

  Interface(int identificador, String nomeInterface, String descricaoInterface) {
    this.identificador = identificador
    this.nomeInterface = nomeInterface
    this.descricaoInterface = descricaoInterface
  }

  Interface(int identificador) {
    this.identificador = identificador
  }

  Interface() {

  }

  Vector carregarInterfaces(Conexao conexao) throws Exception
  {
    Vector interfaces = new Vector()
      ResultSet dadosInterface = conexao.executarConsulta('select * from interface order by interface asc')
      while (dadosInterface.next()) {
        interfaces.addElement(new Interface(dadosInterface.getInt('identificador'), dadosInterface.getString('interface'), dadosInterface.getString('descricao')))
      }
    dadosInterface.close()
      return interfaces
  }

  void excluirInterfacesPadroes() throws Exception
  {
    Conexao conexao = new Conexao('T')

      if (conexao.abrirConexao()) {
        conexao.executarAtualizacao('delete from interface')
          conexao.fecharConexao()
      }
  }
}
