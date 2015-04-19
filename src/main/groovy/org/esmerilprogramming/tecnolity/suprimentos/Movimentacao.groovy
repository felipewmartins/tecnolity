package org.esmerilprogramming.tecnolity.suprimentos

import org.esmerilprogramming.tecnolity.administracao.Colaborador
import org.esmerilprogramming.tecnolity.logistica.Transportadora
import org.esmerilprogramming.tecnolity.util.*
import java.sql.*


class Movimentacao
{
  static final String ABASTECIMENTO     = 'AB'
    static final String CONSUMO           = 'CS'
    static final String VENDAS            = 'VD'
    static final String DESCARTE          = 'DS'
    static final String DEVOLUCAO         = 'DV'
    static final String DEVOLUCAO_EXTERNA = 'DE'
    static final String DEPOSITO          = 'DP'
    static final String RETIRADA_DEPOSITO = 'RD'

    int codigo
    String tipoMovimentacao
    Item item
    ItemRequisicaoInterna itemRequisicaoInterna
    float quantidadeItem
    ItemRequisicao itemRequisicao
    RequisicaoInterna requisicaoInterna
    Colaborador responsavel
    Vector movimentacoesItens = new Vector()
    String dataDespacho, dataRecebimento
    Transportadora transportadora
    float valorConhecimento
    String numeroConhecimento
    String notaFiscal

    Movimentacao() {}

  Movimentacao(int codigo, Item item) {
    definirCodigoMovimentacao(codigo)
      definirItem(item)
  }

  Movimentacao(String tipoMovimentacao, ItemRequisicao itemRequisicao, Colaborador responsavel) {
    definirTipoMovimentacao(tipoMovimentacao)
      definirItemRequisicao(itemRequisicao)
      definirResponsavel(responsavel)
  }

  Movimentacao(String tipoMovimentacao, ItemRequisicaoInterna itemRequisicaoInterna, RequisicaoInterna requisicaoInterna, Colaborador responsavel) {
    definirTipoMovimentacao(tipoMovimentacao)
      definirItemRequisicaoInterna(itemRequisicaoInterna)
      definirRequisicaoInterna(requisicaoInterna)
      definirResponsavel(responsavel)
  }

  void cadastrarMovimentacao() throws Exception
  {
    String query = 'insert into movimentacao_item (tipo_movimento, item, quantidade, responsavel, requisicao_compra, requisicao_interna, data_despacho, data_recebimento, valor_conhecimento, numero_conhecimento, nota_fiscal) values '
      if (requisicaoInterna != null)
        query = query  +  '(' + this.tipoMovimentacao + ', ' + this.itemRequisicaoInterna.obterItem().obterCodigo() + ', ' + this.itemRequisicaoInterna.obterQuantidadeItem() + ', ' + this.responsavel.obterMatricula() + ', null, ' + this.requisicaoInterna.obterCodigo() + ', null, null, null, null, null)'
      else
        query = query  +  '(' + this.tipoMovimentacao + ', ' + this.itemRequisicao.obterItem().obterCodigo() + ', ' + this.quantidadeItem + ', ' + this.responsavel.obterMatricula() + ', ' + this.itemRequisicao.obterRequisicaoCompra().obterCodigo() + ', null, ' + Calendario.inverterFormato(this.dataDespacho, '/') + ', ' + Calendario.inverterFormato(this.dataRecebimento, '/') + ', ' + this.valorConhecimento + ', ' + this.numeroConhecimento + ', ' + this.notaFiscal + ')'
          Conexao conexao = new Conexao('T')
          if (conexao.abrirConexao()) {
            // registra a movimentação.
            conexao.executarAtualizacao(query)
              // consulta e define o código da movimentação registrada.
              try {
                if (requisicaoInterna)
                  query = 'select codigo from movimentacao_item where tipo_movimento = ' +  this.tipoMovimentacao + ' and item = ' + this.itemRequisicaoInterna.obterItem().obterCodigo() + ' and quantidade = ' + this.itemRequisicaoInterna.obterQuantidadeItem() + ' and responsavel = ' + this.responsavel.obterMatricula() + ' and requisicao_interna = ' + this.requisicaoInterna.obterCodigo()
                else
                  query = 'select codigo from movimentacao_item where tipo_movimento = ' +  this.tipoMovimentacao + ' and item = ' + this.itemRequisicao.obterItem().obterCodigo() + ' and quantidade = ' + this.quantidadeItem + ' and responsavel = ' + this.responsavel.obterMatricula() + ' and requisicao_compra = ' + this.itemRequisicao.obterRequisicaoCompra().obterCodigo()
                    ResultSet mov = conexao.executarConsulta(query)
                    if (mov.next()) {
                      this.definirCodigoMovimentacao(mov.getInt('codigo'))
                    }
                mov.close()
              }
            catch (SQLException e) {
              Exception ex = new Exception('Não foi possível consultar a movimentação.\n'  +  e.getMessage())
                throw ex
            }
            conexao.fecharConexao()
          }
          else
          {
            Exception e = new Exception('Não foi possível realizar uma conexão com o banco de dados.')
              throw e
          }
  }
}
