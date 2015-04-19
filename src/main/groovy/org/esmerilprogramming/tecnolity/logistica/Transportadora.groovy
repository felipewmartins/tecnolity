package org.esmerilprogramming.tecnolity.logistica

import org.esmerilprogramming.tecnolity.util.*
import java.sql.*

class Transportadora {
  int codigo
  String transportadora

  Transportadora(int codigo) {
    this.definirCodigo(codigo)
  }

  Transportadora(int codigo, String transportadora) {
    this.codigo = codigo
    this.transportadora = transportadora
  }

  Transportadora(String transportadora) {
    this.transportadora = transportadora
  }

  Transportadora(int codigo, Conexao conexao)throws Exception
  {
    this.definirCodigo(codigo)
      ResultSet dadosTransportadora
      dadosTransportadora = conexao.executarConsulta('select * from transportadora where codigo = ' +  this.codigo)
      if (dadosTransportadora.next()) {
        try {
          this.definirTransportadora(dadosTransportadora.getString('transportadora'))
        }
        catch (e) {
          e.printStackTrace()
        }
      }
  }

  static Vector carregarTransportadoras(Conexao conexao) throws Exception
  {
    ResultSet dadosTransportadora
      Vector transportadoras = null
      dadosTransportadora = conexao.executarConsulta('select * from transportadora order by transportadora asc')
      transportadoras = new Vector()
      transportadoras.addElement('Selecione...')
      while (dadosTransportadora.next()) {
        transportadoras.addElement(new Transportadora(dadosTransportadora.getInt('codigo'), dadosTransportadora.getString('transportadora')))
      }
    dadosTransportadora.close()
      return transportadoras
  }

  void cadastrarTransportadora() {
    String query = 'insert into transportadora (transportadora) values '
    query = query  +  '(' + transportadora + ')'
    Conexao.instance.db.execute query
  }

  void alterarTransportadora() {
    Conexao.instance.db.execute 'update transportadora set transportadora = ' + transportadora + ' where codigo = ' + codigo
  }

  void excluirTransportadora() {
    Conexao.instance.db.execute 'delete from transportadora where codigo = ' + codigo
  }

}
