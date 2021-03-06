package org.esmerilprogramming.tecnolity.producao

import org.esmerilprogramming.tecnolity.util.*
import java.sql.*

class Matriz {
  String referencia, referenciaOriginal
  int numeroSola, numeroSolaOriginal
  int quantidade
  float dureza
  float densidade
  float peso
  float volume
  float tempoInjecao
  int tempoForma
  String modificacoes
  boolean mudouChave = false

  Matriz(String referencia, int numeroSola) throws Exception
  {
    this.definirReferencia(referencia)
      this.definirNumeroSola(numeroSola)
  }

  Matriz(String referencia, int numeroSola, int quantidade, float dureza, float densidade, float peso, float volume, float tempoInjecao, int tempoForma, String modificacoes) throws Exception
  {
    this.definirReferencia(referencia)
      this.definirNumeroSola(numeroSola)
      this.definirQuantidade(quantidade)
      this.definirDureza(dureza)
      this.definirDensidade(densidade)
      this.definirPeso(peso)
      this.definirVolume(volume)
      this.definirTempoInjecao(tempoInjecao)
      this.definirTempoForma(tempoForma)
      this.definirModificacoes(modificacoes)
  }

  Matriz(String referencia, int numeroSola, int quantidade, int tempoForma, float tempoInjecao, float dureza, float densidade, float peso, float volume) throws Exception
  {
    this.definirReferencia(referencia)
      this.definirNumeroSola(numeroSola)
      this.definirQuantidade(quantidade)
      this.definirTempoForma(tempoForma)
      this.definirTempoInjecao(tempoInjecao)
      this.definirDureza(dureza)
      this.definirDensidade(densidade)
      this.definirPeso(peso)
      this.definirVolume(volume)
  }

  Matriz(String referencia, int numeroSola, float dureza, float densidade, float peso, float volume) throws Exception
  {
    this.definirReferencia(referencia)
      this.definirNumeroSola(numeroSola)
      this.definirDureza(dureza)
      this.definirDensidade(densidade)
      this.definirPeso(peso)
      this.definirVolume(volume)
  }

  String obterDescricao() {
    String descricao = ''
      descricao = this.referencia  +  ' - ' + this.numeroSola
      if (this.dureza > 0.0f || this.densidade > 0.0f || this.peso > 0.0f || this.volume > 0.0f) {
        descricao += '('
      }
    if (this.dureza > 0.0f) {
      descricao += 'Du:' + this.dureza
    }
    if (this.densidade > 0.0f) {
      descricao += '/De:' + this.densidade
    }
    if (this.peso > 0.0f) {
      descricao += '/Pe:' + this.peso
    }
    if (this.volume > 0.0f) {
      descricao += '/Vo:' + this.volume
    }
    if (this.dureza > 0.0f || this.densidade > 0.0f || this.peso > 0.0f || this.volume > 0.0f) {
      descricao += ')'
    }
    return descricao
  }

  Vector carregarMatrizes(Conexao conexao) throws Exception
  {
    Vector matrizes = new Vector()
      matrizes.addElement(null)
      ResultSet dadosMatriz

      dadosMatriz = conexao.executarConsulta('select referencia, numero_sola, dureza, densidade, peso, volume from matriz_modelo order by mm.referencia asc')
      while (dadosMatriz.next()) {
        matrizes.addElement(new Matriz(dadosMatriz.getString('referencia'), dadosMatriz.getInt('numero_sola'), dadosMatriz.getFloat('dureza'), dadosMatriz.getFloat('densidade'), dadosMatriz.getFloat('peso'), dadosMatriz.getFloat('volume')))
      }

    dadosMatriz.close()
      return matrizes
  }

  Vector carregarMatrizes(Produto produto, Conexao conexao) throws Exception
  {
    Vector matrizes = new Vector()
      matrizes.addElement(null)
      ResultSet dadosMatriz

      dadosMatriz = conexao.executarConsulta('select distinct mm.referencia, mm.numero_sola, dureza, densidade, peso, volume from matriz_modelo mm, quantidade_materia_prima qmp, modelo m where mm.referencia = qmp.referencia and mm.numero_sola = qmp.numero_sola and qmp.produto in (select codigo from modelo where codigo = ' +  produto.obterCodigo() + ')')
      while (dadosMatriz.next()) {
        matrizes.addElement(new Matriz(dadosMatriz.getString('referencia'), dadosMatriz.getInt('numero_sola'), dadosMatriz.getFloat('dureza'), dadosMatriz.getFloat('densidade'), dadosMatriz.getFloat('peso'), dadosMatriz.getFloat('volume')))
      }

    dadosMatriz.close()
      return matrizes
  }

  void cadastrarMatriz() throws Exception
  {
    Conexao conexao = new Conexao('T')
      if (conexao.abrirConexao()) {
        ResultSet dadosMatriz = conexao.executarConsulta('select * from matriz_modelo where referencia = ' +  this.referencia + ' and numero_sola = ' + this.numeroSola)
          if (dadosMatriz.next()) {
            Exception e = new Exception('J� existe uma matriz cadastrada com a refer�ncia e o n�mero de sola informados.')
              throw e
          }
        dadosMatriz.close()
          conexao.executarAtualizacao('insert into matriz_modelo (referencia, numero_sola, quantidade, dureza, densidade, peso, volume, tempo_injecao, modificacoes, tempo_forma) '  + 
              'values (' +  this.referencia + ', ' + this.numeroSola + ', ' + this.quantidade + ', ' + this.dureza + ', ' + this.densidade + ', ' + this.peso + ', ' + this.volume + ', ' + this.tempoInjecao + ', ' + this.modificacoes + ', ' + this.tempoForma + ')')
          conexao.fecharConexao()
      }
      else
      {
        Exception e = new Exception('N�o poss�vel cadastrar a Matriz.')
          throw e
      }
  }

  void alterarMatriz() throws Exception
  {
    Conexao conexao = new Conexao('T')
      String query = ''
      boolean mudouChave = true
      if (conexao.abrirConexao()) {
        query = 'select referencia, numero_sola from matriz_modelo where referencia = '' +  this.obterReferencia() + '' and numero_sola = ' + this.obterNumeroSola()
          ResultSet dadosMatriz = conexao.executarConsulta(query)
          if (this.referencia.equals(this.referenciaOriginal) && (this.numeroSola == this.numeroSolaOriginal)) {
            mudouChave = false
          }

        if (dadosMatriz.next() && mudouChave) {
          Exception e = new Exception('J� existe uma matriz com a refer�ncia e n�mero de sola informados.')
            throw e
        }
        else
        {
          query = 'update matriz_modelo set referencia = '' +  this.obterReferencia() + '', numero_sola = ' + this.obterNumeroSola() + ', quantidade = ' + this.obterQuantidade() + ', dureza = ' + this.obterDureza() + ', densidade = ' + this.obterDensidade() + ', peso = ' + this.obterPeso() + ', volume = ' + this.obterVolume() + ', tempo_injecao = ' + this.obterTempoInjecao() + ', modificacoes = '' + this.obterModificacoes() + '', tempo_forma = ' + this.obterTempoForma() + ' where referencia = '' + this.obterReferenciaOriginal() + '' and numero_sola = ' + this.obterNumeroSolaOriginal()
            conexao.executarAtualizacao(query)
        }
        dadosMatriz.close()
          conexao.fecharConexao()
      }
      else
      {
        Exception e = new Exception('N�o foi poss�vel realizar uma conex�o com o banco de dados.')
          throw e
      }
  }

  void carregarMatriz(Conexao conexao) throws Exception
  {
    ResultSet dadosMatriz = conexao.executarConsulta('select * from matriz_modelo where referencia = ' +  this.referencia + ' and numero_sola = ' + this.numeroSola)
      if (dadosMatriz.next()) {
        definirQuantidade(dadosMatriz.getInt('quantidade'))
          definirDureza(dadosMatriz.getFloat('dureza'))
          definirDensidade(dadosMatriz.getFloat('densidade'))
          definirPeso(dadosMatriz.getFloat('peso'))
          definirVolume(dadosMatriz.getFloat('volume'))
          definirTempoInjecao(dadosMatriz.getFloat('tempo_injecao'))
          definirTempoForma(dadosMatriz.getInt('tempo_forma'))
          definirModificacoes(dadosMatriz.getString('modificacoes'))
          definirReferenciaOriginal(this.referencia)
          definirNumeroSolaOriginal(this.numeroSola)
      }
    dadosMatriz.close()
  }

  void excluirMatriz() {
    Conexao.instance.db.execute 'delete from matriz_modelo where referencia = ' + referencia + ' and numero_sola = ' + numeroSola
  }
}
