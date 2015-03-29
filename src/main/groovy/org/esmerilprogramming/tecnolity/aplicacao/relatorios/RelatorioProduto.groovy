package org.esmerilprogramming.tecnolity.aplicacao.relatorios

import org.esmerilprogramming.tecnolity.producao.*
import org.esmerilprogramming.tecnolity.util.*

class RelatorioProduto extends Relatorio
{
  private Produto produto

    RelatorioProduto(Produto produto) {
      this.produto = produto
    }

  String gerarRelatorio() {
    Calendario calendario = new Calendario()
      conteudo = new StringBuffer()
      conteudo.append("FICHA TÉCNICA DO PRODUTO                          TECNOLITY DO NORDESTE LTDA")
      conteudo.append(QUEBRA)
      conteudo.append("============================================================================")
      conteudo.append(QUEBRA)
      conteudo.append("                                                            Data: " + calendario.dataHoje("dd/MM/yyyy"))
      conteudo.append(QUEBRA)
      conteudo.append("        Código: "+ Texto.obterNumeroTamanhoFixo("" + produto.obterCodigo(),7,"0") +"                                 Ref.Cliente: " + Texto.obterStringTamanhoFixo(produto.obterReferenciaCliente(),24))
      conteudo.append(QUEBRA)
      conteudo.append("        Modelo: "+ Texto.obterStringTamanhoFixo(produto.obterNomeModelo(),60))
      conteudo.append(QUEBRA)
      conteudo.append("       Cliente: "+ Texto.obterStringTamanhoFixo(produto.obterCliente().obterRazaoSocial(),60))
      conteudo.append(QUEBRA)
      conteudo.append("Tp. Componente: "+ Texto.obterStringTamanhoFixo(produto.obterComponente().obterNomeComponente(),21) +" Tp. Produção: " + Texto.obterStringTamanhoFixo(produto.obterTipoProducao().obterTipoProducao(),24))
      conteudo.append(QUEBRA)
      try
      {
        conteudo.append("       Mercado: "+ (produto.obterDestino() == 'I'?"Mercado Interno":"Mercado Externo") + '       Valor: R$' + produto.getValor("") + " (Desde: "+ produto.getDataUltimaAlteracaoValor(null) +")")
      }
    catch(Exception e) {
      e.printStackTrace()
    }
    conteudo.append(QUEBRA)

      int alturaLinha = 80
      String[] texto
      if(!produto.obterEspecificacaoInserto().equals("")) {
        conteudo.append("----------------------------------------------------------------------------")
          conteudo.append(QUEBRA)
          conteudo.append("Insertos                                                                    ")
          conteudo.append(QUEBRA)

          texto = Texto.obterTextoAlinhado(produto.obterEspecificacaoInserto(),77)
          for(int linha = 0;linha < texto.length;linha++) {
            conteudo.append(texto[linha])
              conteudo.append(QUEBRA)
          }
      }
    if(!produto.obterAcabamento().equals("")) {
      conteudo.append("----------------------------------------------------------------------------")
        conteudo.append(QUEBRA)
        conteudo.append("Acabamentos                                                                 ")
        conteudo.append(QUEBRA)
        texto = Texto.obterTextoAlinhado(produto.obterAcabamento(),77)
        for(int linha = 0;linha < texto.lengthl;inha++) {
          conteudo.append(texto[linha])
            conteudo.append(QUEBRA)
        }
    }
    if(!produto.obterLavagem().equals("")) {
      conteudo.append("----------------------------------------------------------------------------")
        conteudo.append(QUEBRA)
        conteudo.append("Lavagem                                                                     ")
        conteudo.append(QUEBRA)
        texto = Texto.obterTextoAlinhado(produto.obterLavagem(),77)
        for(int linha = 0 ; linha < texto.length;linha++) {
          conteudo.append(texto[linha])
            conteudo.append(QUEBRA)
        }
    }
    if(!produto.obterPintura().equals("")) {
      conteudo.append("----------------------------------------------------------------------------")
        conteudo.append(QUEBRA)
        conteudo.append("Pintura                                                                     ")
        conteudo.append(QUEBRA)
        texto = Texto.obterTextoAlinhado(produto.obterPintura(),77)
        for(int linha = 0;linha < texto.length;linha++) {
          conteudo.append(texto[linha])
            conteudo.append(QUEBRA)
        }
    }
    conteudo.append("============================================================================")
      conteudo.append(QUEBRA)
      conteudo.append("Composição                                                                  ")
      conteudo.append(QUEBRA)

      int numeroSola, numeroSolaAnterior = 0
      String referencia = ""
      MateriaPrima materiaPrima
      for(int i = 0;i < produto.obterMateriasPrimas().size();i++) {
        materiaPrima = (MateriaPrima)produto.obterMateriasPrimas().get(i)
          referencia = materiaPrima.obterMatriz().obterReferencia()
          numeroSola = materiaPrima.obterMatriz().obterNumeroSola()
          if(numeroSolaAnterior != numeroSola) {
            numeroSolaAnterior = numeroSola
              conteudo.append("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = ")
              conteudo.append(QUEBRA)
              conteudo.append("No. Matriz: "+ Texto.obterStringTamanhoFixo(referencia,13) +"  Num. Sola: "+ Texto.obterStringTamanhoFixo("" + numeroSola,13) +"  Quant.: "+ Texto.obterStringTamanhoFixo("" + materiaPrima.obterMatriz().obterQuantidade(),14))
              conteudo.append(QUEBRA)
              conteudo.append("    Dureza: "+ Texto.obterStringTamanhoFixo("" + materiaPrima.obterMatriz().obterDureza(),13) +"  Densidade: "+ Texto.obterStringTamanhoFixo("" + materiaPrima.obterMatriz().obterDensidade(),15) +"  Peso: "+ Texto.obterStringTamanhoFixo("" + materiaPrima.obterMatriz().obterPeso(),14))
              conteudo.append(QUEBRA)
              conteudo.append("    Volume: "+ Texto.obterStringTamanhoFixo("" + materiaPrima.obterMatriz().obterVolume(),12) + " Tempo Forma: "+ Texto.obterStringTamanhoFixo("" + materiaPrima.obterMatriz().obterTempoForma(),7) + " Tempo Injeção: "+ Texto.obterStringTamanhoFixo("" + materiaPrima.obterMatriz().obterTempoInjecao(),10))
              conteudo.append(QUEBRA)
              conteudo.append(QUEBRA)
              conteudo.append("Item                                             Quantidade                 ")
              conteudo.append(QUEBRA)
          }
        conteudo.append(Texto.obterStringTamanhoFixo(materiaPrima.obterItem().obterDescricao(),47) +"  "+ Texto.obterStringTamanhoFixo("" + materiaPrima.obterQuantidade(""),27))
          conteudo.append(QUEBRA)
      }
    return conteudo.toString()
  }
}
