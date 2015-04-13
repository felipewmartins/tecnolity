package org.esmerilprogramming.tecnolity.producao


import java.sql.*
import org.esmerilprogramming.tecnolity.util.*

class TipoProducao {
  int codigo
  String tipoProducao

  TipoProducao(int codigo, String tipoProducao) {
    this.codigo = codigo
    this.tipoProducao = tipoProducao
  }

  TipoProducao(int codigo) {
    this.codigo = codigo
  }

  Vector carregarTiposProducao(Conexao conexao) throws Exception
  {
    ResultSet dadosTipoProducao
      Vector tiposProducao = new Vector()
      try {
        dadosTipoProducao = conexao.executarConsulta('select * from tipo_producao order by tipo_producao asc')
          tiposProducao.addElement(null)

          while (dadosTipoProducao.next()) {
            tiposProducao.addElement(new TipoProducao(dadosTipoProducao.getInt('codigo'), dadosTipoProducao.getString('tipo_producao')))
          }
        dadosTipoProducao.close()
      }
    catch (SQLException e) {
      e.printStackTrace()
    }
    return tiposProducao
  }

  int obterUltimoIdentificador(Conexao conexao) throws Exception
  {
    ResultSet dadosTipoProducao
      try {
        dadosTipoProducao = conexao.executarConsulta('select max(codigo) as identificador_maior from tipo_producao')
          if (dadosTipoProducao.next()) {
            return dadosTipoProducao.getInt('identificador_maior')  +  1
          }
        dadosTipoProducao.close()
      }
    catch (SQLException e) {
      e.printStackTrace()
    }
    return 1
  }

  void cadastrarTipoProducao() {
    String query = 'insert into tipo_producao (codigo, tipo_producao) values (' +  this.codigo + ', ' + this.tipoProducao + ')'
      Conexao conexao = new Conexao('T')
      if (conexao.abrirConexao()) {
        conexao.executarAtualizacao(query)
          conexao.fecharConexao()
      }
  }
}
