package org.esmerilprogramming.tecnolity.aplicacao.relatorios

import java.util.*
import org.esmerilprogramming.tecnolity.pedidos.*
import org.esmerilprogramming.tecnolity.suprimentos.*
import org.esmerilprogramming.tecnolity.util.*

 class RelRequisicaoCompra extends Relatorio
{
    private RequisicaoCompra requisicaoCompra
    private Vector pedidos
    private String via
    
     RelRequisicaoCompra(RequisicaoCompra requisicaoCompra, Vector pedidos, String via)
    {
        this.requisicaoCompra = requisicaoCompra
        this.pedidos = pedidos
        this.via = via
    }
    
     String gerarRelatorio()
    {
        Calendario calendario = new Calendario()
        conteudo = new StringBuffer()
        conteudo.append("REQUISIÇÃO DE COMPRA AO FORNECEDOR                               TECNOLITY DO NORDESTE LTDA")
        conteudo.append(QUEBRA)
        conteudo.append("VIA " + via)
        conteudo.append(QUEBRA)
        conteudo.append("                                           Rua Rafael Malzone, 600 - Bairro: Tiro de Guerra")
        conteudo.append(QUEBRA)
        conteudo.append("                                                      Fone/Fax: (00xx88)571-1686 / 571-1085")
        conteudo.append(QUEBRA)
        conteudo.append("                                                    CEP: 63041-140 - Juazeiro do Norte - CE")
        conteudo.append(QUEBRA)
        conteudo.append("                                               CNPJ: 02.538.983/0001-40 - CGF: 06.267.648-2")
        conteudo.append(QUEBRA)
        conteudo.append("                                                          E-mail: tecnolity@baydejbc.com.br")
        conteudo.append(QUEBRA)
        conteudo.append("===========================================================================================")
        conteudo.append(QUEBRA)
        conteudo.append("    Fornecedor: " + Texto.obterStringTamanhoFixo(this.requisicaoCompra.obterFornecedor().obterRazaoSocial(),42) + "   No.: " + Texto.obterNumeroTamanhoFixo("" + this.requisicaoCompra.obterCodigo(),5,"0"))
        conteudo.append(QUEBRA)
        conteudo.append("Transportadora: " + Texto.obterStringTamanhoFixo(this.requisicaoCompra.obterTransportadora().obterNome(),34) + "   Dt. Emissão: " + this.requisicaoCompra.obterDataEmissao())
        conteudo.append(QUEBRA)
        conteudo.append("         Frete: " + Texto.obterStringTamanhoFixo((this.requisicaoCompra.obterTipoFrete().equals("C")?"CIF":this.requisicaoCompra.obterTipoFrete().equals("F")?"FOB":"Próprio"),7) + "                         Dt. Lim. Entrega: " + this.requisicaoCompra.obterDataLimiteEntrega())
        conteudo.append(QUEBRA)
        conteudo.append("Cnd. Pagamento: " + Texto.obterStringTamanhoFixo(this.requisicaoCompra.obterCondicaoPagamento(),29) + "    Forma Pagamento: " + Texto.obterStringTamanhoFixo(this.requisicaoCompra.obterFormaPagamento().obterFormaPagamento(),10))
        conteudo.append(QUEBRA)
        conteudo.append("       Pedidos: ")
        for(int i = 0i < pedidos.size()i++)
        {
            conteudo.append(((Pedido)pedidos.get(i)).obterCodigo() + "(OC:" +((Pedido)pedidos.get(i)).obterOrdemCompra() + ") ")
        }
        conteudo.append(QUEBRA)
        conteudo.append("===========================================================================================")
        conteudo.append(QUEBRA)
        conteudo.append("Item                            | Ref. Fornec. | Quant.   | Vl. Unit. | Total     | IPI")
        conteudo.append(QUEBRA)
        conteudo.append("-------------------------------------------------------------------------------------------")
        conteudo.append(QUEBRA)
        Vector itens = this.requisicaoCompra.obterItensRequisicao()
        int posicaoBarraLista = 0
        float totalRequisicao = 0.0f, totalIPI = 0.0f
        for(int i = 0i < itens.size()i++)
        {
            ItemRequisicao irAtual = (ItemRequisicao)itens.get(i)
            conteudo.append(Texto.obterStringTamanhoFixo(irAtual.obterItem().obterDescricao(),32) + "|" + 
                            Texto.obterStringTamanhoFixo("" + irAtual.obterItem().obterFornecedorItem().obterReferenciaFornecedor(),14) + "|" + 
                            Texto.obterNumeroTamanhoFixo(Numero.formatarValorNumerico(irAtual.getQuantidadeItem(),3,","),10," ") + "|" +
                            Texto.obterNumeroTamanhoFixo("" + Numero.formatarValorMoeda(irAtual.obterValorUnitario(),""),11," ") + "|" +
                            Texto.obterNumeroTamanhoFixo("" + Numero.formatarValorMoeda(irAtual.obterValorTotal(),""),11," ") + "|" +
                            Texto.obterNumeroTamanhoFixo("" + Numero.formatarValorMoeda(irAtual.obterValorIPI(),""),8," "))
            conteudo.append(QUEBRA)
            totalRequisicao += irAtual.obterValorTotal()
            totalIPI += irAtual.obterValorIPI()
            posicaoBarraLista = 170 + (i * 10)
        }
        conteudo.append("-------------------------------------------------------------------------------------------")
        conteudo.append(QUEBRA)
        conteudo.append("                                                               Total: |"+ Texto.obterNumeroTamanhoFixo("" + Numero.formatarValorMoeda(totalRequisicao,""),11," ") +"|"+ Texto.obterNumeroTamanhoFixo("" + Numero.formatarValorMoeda(totalIPI,""),8," "))
        conteudo.append(QUEBRA)
        conteudo.append("-------------------------------------------------------------------------------------------")
        conteudo.append(QUEBRA)
        conteudo.append("Observação: ")
        conteudo.append(QUEBRA)
        String[] texto = Texto.obterTextoAlinhado(requisicaoCompra.obterObservacao(),92)
        for(int linha = 0linha < texto.lengthlinha++)
        {
            conteudo.append(texto[linha])
            conteudo.append(QUEBRA)
        }
        conteudo.append("-------------------------------------------------------------------------------------------")
        conteudo.append(QUEBRA)
        conteudo.append(QUEBRA)
        conteudo.append("Dt.  Recebimento: ___/___/_____                                             ")
        conteudo.append(QUEBRA)
        conteudo.append(QUEBRA)
        conteudo.append("Vl. Conhecimento: ____________________                      No. Conhecimento: _____________")
        conteudo.append(QUEBRA)
        conteudo.append(QUEBRA)
        conteudo.append("  Transportadora: __________________________                 No. Nota Fiscal: _____________")
        conteudo.append(QUEBRA)
        conteudo.append(QUEBRA)
        conteudo.append("    Recebido por: __________________________                 Vl. Nota Fiscal: _____________")
        conteudo.append(QUEBRA)
        conteudo.append(QUEBRA)
        conteudo.append("                                            A vista: ____________ Vencimento: ___/___/_____")
        conteudo.append(QUEBRA)
        conteudo.append(QUEBRA)
        conteudo.append("                                            30 dias: ____________ Vencimento: ___/___/_____")
        conteudo.append(QUEBRA)
        conteudo.append(QUEBRA)
        conteudo.append("                                            45 dias: ____________ Vencimento: ___/___/_____")
        conteudo.append(QUEBRA)
        conteudo.append(QUEBRA)
        conteudo.append("                                            60 dias: ____________ Vencimento: ___/___/_____")
        conteudo.append(QUEBRA)
        conteudo.append(QUEBRA)
        conteudo.append("                                            75 dias: ____________ Vencimento: ___/___/_____")
        conteudo.append(QUEBRA)
        conteudo.append(QUEBRA)
        conteudo.append(QUEBRA)
        conteudo.append("______________________________________                                      ")
        conteudo.append(QUEBRA)
        conteudo.append("             Assinatura                                                     ")
        conteudo.append(QUEBRA)
        
        return conteudo.toString()
    }
}