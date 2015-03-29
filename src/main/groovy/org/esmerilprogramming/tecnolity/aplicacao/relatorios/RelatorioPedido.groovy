package org.esmerilprogramming.tecnolity.aplicacao.relatorios

import java.util.*
import org.esmerilprogramming.tecnolity.pedidos.*
import org.esmerilprogramming.tecnolity.util.*

class RelatorioPedido extends Relatorio
{
  private Pedido pedido

    RelatorioPedido(Pedido pedido) 
    {
      this.pedido = pedido
    }

  String gerarRelatorio()
  {
    conteudo = new StringBuffer()
      Calendario calendario = new Calendario()
      conteudo.append("PEDIDO DO CLIENTE                                 TECNOLITY DO NORDESTE LTDA")
      conteudo.append(QUEBRA)
      conteudo.append("============================================================================")
      conteudo.append(QUEBRA)
      conteudo.append("   Cliente: "+ Texto.obterStringTamanhoFixo(pedido.obterCliente().obterRazaoSocial(),38) +" Data: " + calendario.dataHoje("dd/MM/yyyy 'as' HH:mm"))
      conteudo.append(QUEBRA)
      conteudo.append("    Pedido: "+ Texto.obterStringTamanhoFixo("" + pedido.obterCodigo(),29) + " Ordem Compra:" + pedido.obterOrdemCompra())
      conteudo.append(QUEBRA)
      conteudo.append("   Destino: "+ pedido.obterLocalEntrega().obterDescricaoLocal())
      conteudo.append(QUEBRA)
      conteudo.append("Logradouro: "+ Texto.obterStringTamanhoFixo(pedido.obterLocalEntrega().obterLogradouro(),30) + " Complemento:" + Texto.obterStringTamanhoFixo(pedido.obterLocalEntrega().obterComplemento(),19))
      conteudo.append(QUEBRA)
      conteudo.append("    Bairro: "+ Texto.obterStringTamanhoFixo(pedido.obterLocalEntrega().obterBairro(),34) + " Cidade:" + Texto.obterStringTamanhoFixo(pedido.obterLocalEntrega().obterCidade(),24))
      conteudo.append(QUEBRA)
      conteudo.append("    Estado: "+ Texto.obterStringTamanhoFixo(pedido.obterLocalEntrega().obterEstado().getSigla(),4) + " CEP:" + Texto.obterStringTamanhoFixo(pedido.obterLocalEntrega().obterCep(),10) + " Telefone:" + pedido.obterLocalEntrega().obterTelefone())
      conteudo.append(QUEBRA)
      conteudo.append("Responsável pelo Recebimento: "+ pedido.obterLocalEntrega().obterResponsavelRecebimento())
      conteudo.append(QUEBRA)
      conteudo.append("----------------------------------------------------------------------------")
      conteudo.append(QUEBRA)
      Vector produtosPedido = pedido.getProdutosPedido()
      Vector numerosSola
      String produtoAnterior = " "
      int quantidadePares = 0

      for(int i = 0 ; i < produtosPedido.size() ; i++)
      {
        ProdutoPedido produtoPedido = (ProdutoPedido)produtosPedido.get(i)
          numerosSola = pedido.obterNumeracaoProdutos(produtoPedido.obterMatriz().obterReferencia())
          if(!produtoAnterior.equals(produtoPedido.obterProduto().obterNomeModelo()))
          {
            produtoAnterior = produtoPedido.obterProduto().obterNomeModelo()

              conteudo.append("Produto: "+ Texto.obterStringTamanhoFixo(produtoAnterior,49) +" Matriz: " + produtoPedido.obterMatriz().obterReferencia())
              conteudo.append(QUEBRA)
              conteudo.append("                           --- Quantidades ---                              ")
              conteudo.append(QUEBRA)
              String strNumerosSola = ""
              for(int j = 0 ; j < numerosSola.size() ; j++)
              {
                strNumerosSola += "|  " + numerosSola.get(j) + "  "
              }
            conteudo.append(strNumerosSola)
              conteudo.append(QUEBRA)
          }
        if(produtoAnterior.equals(produtoPedido.obterProduto().obterNomeModelo()))
        {
          for(int k = 0 ; k < numerosSola.size() ; k++)
          {
            if(produtoPedido.obterMatriz().obterNumeroSola() == Integer.parseInt((String)numerosSola.get(k)))
            {
              conteudo.append(Texto.obterStringTamanhoFixo(" " + (int)produtoPedido.obterQuantidade(), 7))
                break
            }
          }
          quantidadePares += produtoPedido.obterQuantidade()
        }
        if(i < (produtosPedido.size() - 1))
        {
          if(!produtoAnterior.equals(((ProdutoPedido)produtosPedido.get(i+1)).obterProduto().obterNomeModelo()))
          {
            conteudo.append(QUEBRA)
              conteudo.append("Total: " + quantidadePares)
              conteudo.append(QUEBRA)
              conteudo.append("----------------------------------------------------------------------------")
              conteudo.append(QUEBRA)
              quantidadePares = 0
          }
        }
      }
    conteudo.append(QUEBRA)
      conteudo.append("Total: " + quantidadePares)
      conteudo.append(QUEBRA)
      conteudo.append("----------------------------------------------------------------------------")
      conteudo.append(QUEBRA)
      conteudo.append("Observação: ")
      conteudo.append(QUEBRA)
      String[] texto = Texto.obterTextoAlinhado(pedido.obterObservacao(),77)
      for(int linha = 0;linha < texto.length;linha++)
      {
        conteudo.append(texto[linha])
          conteudo.append(QUEBRA)
      }
    conteudo.append("----------------------------------------------------------------------------")
      return conteudo.toString()
  }
}
