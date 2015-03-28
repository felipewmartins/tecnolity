package br.com.tecnolity.aplicacao.relatorios;

import java.util.*;
import br.com.tecnolity.pedidos.*;
import br.com.tecnolity.util.*;

public class RelatorioPedidoReferencia extends Relatorio
{
    private Pedido pedido;
    
    public RelatorioPedidoReferencia(Pedido pedido) 
    {
        this.pedido = pedido;
    }
        
    public String gerarRelatorio()
    {
        Calendario calendario = new Calendario();
        conteudo = new StringBuffer();
        conteudo.append("PEDIDOS POR REFER�NCIA                            TECNOLITY DO NORDESTE LTDA");
        conteudo.append(QUEBRA);
        conteudo.append("============================================================================");
        conteudo.append(QUEBRA);
        conteudo.append("   Cliente: "+ Texto.obterStringTamanhoFixo(pedido.obterCliente().obterRazaoSocial(),38) +" Data: " + calendario.dataHoje("dd/MM/yyyy 'as' HH:mm"));
        conteudo.append(QUEBRA);
        conteudo.append("    Pedido: "+ Texto.obterStringTamanhoFixo("" + pedido.obterCodigo(),29) + " Ordem Compra:" + pedido.obterOrdemCompra());
        conteudo.append(QUEBRA);
        conteudo.append("   Destino: "+ pedido.obterLocalEntrega().obterDescricaoLocal());
        conteudo.append(QUEBRA);
        conteudo.append("Logradouro: "+ Texto.obterStringTamanhoFixo(pedido.obterLocalEntrega().obterLogradouro(),30) + " Complemento:" + Texto.obterStringTamanhoFixo(pedido.obterLocalEntrega().obterComplemento(),19));
        conteudo.append(QUEBRA);
        conteudo.append("    Bairro: "+ Texto.obterStringTamanhoFixo(pedido.obterLocalEntrega().obterBairro(),34) + " Cidade:" + Texto.obterStringTamanhoFixo(pedido.obterLocalEntrega().obterCidade(),24));
        conteudo.append(QUEBRA);
        conteudo.append("    Estado: "+ Texto.obterStringTamanhoFixo(pedido.obterLocalEntrega().obterEstado().getSigla(),4) + " CEP:" + Texto.obterStringTamanhoFixo(pedido.obterLocalEntrega().obterCep(),10) + " Telefone:" + pedido.obterLocalEntrega().obterTelefone());
        conteudo.append(QUEBRA);
        conteudo.append("Respons�vel pelo Recebimento: "+ pedido.obterLocalEntrega().obterResponsavelRecebimento());
        conteudo.append(QUEBRA);
        String referenciaAnterior = "";
        String referencia = "";
        String margemNumeroSola = "";
        int quantidadeProduto = 0;
        int quantidadeTotal = 0;
        boolean espacamentoCabecalho = false;
        ProdutoPedido produtoPedido;
        for(int i = 0;i < pedido.getProdutosPedido().size(); i ++)
        {
            produtoPedido = (ProdutoPedido)pedido.getProdutosPedido().get(i);
            referencia = produtoPedido.obterProduto().obterReferenciaCliente();
            if(!referencia.equals(referenciaAnterior))
            {
                referenciaAnterior = referencia;
                margemNumeroSola = "";
                if(espacamentoCabecalho)
                {
                    conteudo.append(QUEBRA);
                    conteudo.append("Total: " + quantidadeTotal);
                    conteudo.append(QUEBRA);
                    conteudo.append("----------------------------------------------------------------------------");
                    conteudo.append(QUEBRA);
                    quantidadeTotal = 0;
                }
                else
                {
                    conteudo.append("----------------------------------------------------------------------------");
                    conteudo.append(QUEBRA);
                }
                conteudo.append("Refer�ncia da Matriz: " + referencia);
                conteudo.append(QUEBRA);
                conteudo.append("                           --- Quantidades ---                              ");
                conteudo.append(QUEBRA);
                String strNumerosSola = "";
                Vector numerosSola = pedido.obterNumeracaoProdutos(referencia);
                for(int j = 0;j < numerosSola.size();j++)
                {
                    strNumerosSola += "|  " + numerosSola.get(j) + "  ";
                }
                conteudo.append(strNumerosSola);
                conteudo.append(QUEBRA);
                quantidadeProduto = (int)produtoPedido.obterQuantidade();
                conteudo.append(Texto.obterStringTamanhoFixo(" " +quantidadeProduto,7));
                espacamentoCabecalho = true;
                quantidadeTotal += quantidadeProduto;
            }
            else
            {
                quantidadeProduto = (int)produtoPedido.obterQuantidade(); 
                conteudo.append(Texto.obterStringTamanhoFixo(" " +quantidadeProduto,7));
                quantidadeTotal += quantidadeProduto;
            }
        }
        conteudo.append(QUEBRA);
        conteudo.append("Total: " + quantidadeTotal);
        conteudo.append(QUEBRA);
        conteudo.append("----------------------------------------------------------------------------");
        conteudo.append(QUEBRA);
        conteudo.append("Observa��o: ");
        conteudo.append(QUEBRA);
        String[] texto = Texto.obterTextoAlinhado(pedido.obterObservacao(),77);
        for(int linha = 0;linha < texto.length;linha++)
        {
            conteudo.append(texto[linha]);
            conteudo.append(QUEBRA);
        }
        return conteudo.toString();
    }
}