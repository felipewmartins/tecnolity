package org.esmerilprogramming.tecnolity.suprimentos

import org.esmerilprogramming.tecnolity.administracao.Departamento
import org.esmerilprogramming.tecnolity.util.Conexao

class ItemRequisicaoInterna
{
  static final String STATUS_EMITIDO    = 'EM'
    static final String STATUS_CANCELADO  = 'CL'
    static final String STATUS_CONFIRMADO = 'CO'
    static final String STATUS_PENDENTE   = 'PD'

    Item item
    RequisicaoInterna requisicaoInterna
    String status
    float quantidadeItem
    Departamento destino

    ItemRequisicaoInterna(Item item, RequisicaoInterna requisicaoInterna, float quantidadeItem, Departamento destino, String status) throws Exception
    {
      this.definirItem(item)
        this.definirRequisicaoInterna(requisicaoInterna)
        this.definirQuantidadeItem(quantidadeItem)
        this.definirDestino(destino)
        this.definirStatus(status)
    }

  /** Construtor para carregar itens de uma requisicao interna. */
  ItemRequisicaoInterna(RequisicaoInterna requisicaoInterna) {
    this.definirRequisicaoInterna(requisicaoInterna)
  }

  ItemRequisicaoInterna(Item item, float quantidade) throws Exception
  {
    this.definirItem(item)
    this.definirQuantidadeItem(quantidade)
  }

  void definirQuantidadeItem(float quantidadeItem) throws Exception
  {
    if (quantidadeItem >= 0.0f) {
      this.quantidadeItem = quantidadeItem
    }
    else
    {
      throw new Exception('A quantidade deve ser maior ou igual a zero zero.')
    }
  }

  String obterStatusLiteral() {
    if (status.equals(STATUS_CANCELADO))
      return 'Cancelado'
        if (status.equals(STATUS_CONFIRMADO))
          return 'Confirmado'
            if (status.equals(STATUS_EMITIDO))
              return 'Emitido'
            else
              return 'Pendente'
  }

  void registrarItemRequisicaoInterna() {
    Conexao.instance.db.execute 'insert into item_requisicao_interna (item, requisicao_interna, status, quantidade, destino) values (' +  this.item.obterCodigo() + ', ' + this.requisicaoInterna.obterCodigo() + ', \'EM\', ' + this.quantidadeItem + ', ' + this.destino.obterCodigo() + ')'
  }

  void atualizarItemRequisicaoInterna() {
    Conexao.instance.db.execute 'update item_requisicao_interna set status = ' +  status + ', quantidade = ' + this.quantidadeItem + ' where item = ' + this.obterItem().obterCodigo() + ' and requisicao_interna = ' + this.obterRequisicaoInterna().obterCodigo()
  }
}
