package org.esmerilprogramming.tecnolity.aplicacao.relatorios

import org.esmerilprogramming.tecnolity.suprimentos.*
import org.esmerilprogramming.tecnolity.util.*

class RelatorioFornecedoresPendentes extends Relatorio
{
  private Vector fornecedores

    RelatorioFornecedoresPendentes(Vector fornecedores) {
      this.fornecedores = fornecedores
    }

  String gerarRelatorio() {
    Calendario calendario = new Calendario()
      conteudo = new StringBuffer()
      conteudo.append("FORNECEDORES PENDENTES                                        TECNOLITY DO NORDESTE LTDA")
      conteudo.append(QUEBRA)
      conteudo.append("========================================================================================")
      conteudo.append(QUEBRA)
      conteudo.append("                                                                        Data: " + calendario.dataHoje("dd/MM/yyyy"))
      conteudo.append(QUEBRA)
      conteudo.append("Cod.  | Fornecedor")
      conteudo.append(QUEBRA)
      conteudo.append("----------------------------------------------------------------------------------------")
      conteudo.append(QUEBRA)
      String quantMinima = ""
      for(int i = 0 ; i < fornecedores.size() ; i++) {
        Fornecedor fornecedor = (Fornecedor)fornecedores.get(i)
          conteudo.append("" + Texto.obterNumeroTamanhoFixo("" + fornecedor.obterCodigo(),6,"0") +"|"+ fornecedor.obterRazaoSocial())
          conteudo.append(QUEBRA)
      }
    conteudo.append("----------------------------------------------------------------------------------------")
      return conteudo.toString()
  }
}
