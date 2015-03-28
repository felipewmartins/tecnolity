package org.esmerilprogramming.tecnolity.aplicacao.relatorios;

import java.util.*;
import org.esmerilprogramming.tecnolity.suprimentos.*;
import org.esmerilprogramming.tecnolity.util.*;

public class RelatorioRequisicaoInterna extends Relatorio
{
    private RequisicaoInterna requisicaoInterna;
    
    public RelatorioRequisicaoInterna(RequisicaoInterna requisicaoInterna) 
    {
        this.requisicaoInterna = requisicaoInterna;
    }
    
    public String gerarRelatorio()
    {
        conteudo = new StringBuffer();
        Calendario calendario = new Calendario();
        conteudo.append("REQUISIÇÃO INTERNA                                TECNOLITY DO NORDESTE LTDA");
        conteudo.append(QUEBRA);
        conteudo.append("                                                         " +  calendario.dataHoje("dd/MM/yyyy 'as' HH:mm"));
        conteudo.append(QUEBRA);
        conteudo.append("============================================================================");
        conteudo.append(QUEBRA);
        conteudo.append("      Código: "+ Texto.obterNumeroTamanhoFixo("" + requisicaoInterna.obterCodigo(),8,"0") +"  Solicitação: " + Texto.obterStringTamanhoFixo(requisicaoInterna.obterTipoSolicitacaoLiteral(),17) + "  Entrega: " + Texto.obterStringTamanhoFixo(requisicaoInterna.obterDataLimiteEntrega(),10));
        conteudo.append(QUEBRA);
        conteudo.append("Departamento: "+ Texto.obterStringTamanhoFixo("" + requisicaoInterna.obterDepartamento(),15) + "  Solicitante: " + Texto.obterStringTamanhoFixo(requisicaoInterna.obterSolicitante().toString(),32));
        conteudo.append(QUEBRA);
        conteudo.append("Dta. Emissão: "+ Texto.obterStringTamanhoFixo(requisicaoInterna.obterData(),10) + "  Status: " + requisicaoInterna.obterStatusLiteral());
        conteudo.append(QUEBRA);
        conteudo.append("----------------------------------------------------------------------------");
        conteudo.append(QUEBRA);
        conteudo.append("Observação: ");
        conteudo.append(QUEBRA);
        String[] texto = Texto.obterTextoAlinhado(requisicaoInterna.obterJustificativa(),77);
        for(int linha = 0;linha < texto.length;linha++)
        {
            conteudo.append(texto[linha]);
            conteudo.append(QUEBRA);
        }
        conteudo.append("----------------------------------------------------------------------------");
        conteudo.append(QUEBRA);
        conteudo.append("Item                                           Quantidade    Status");
        conteudo.append(QUEBRA);
        conteudo.append("---------------------------------------------  ------------  ---------------");
        conteudo.append(QUEBRA);
        Vector itensRequisicaoInterna = requisicaoInterna.obterItensRequisicaoInterna();
        ItemRequisicaoInterna itemRequisicaoInterna;
        for(int i = 0;i < itensRequisicaoInterna.size();i++)
        {
            itemRequisicaoInterna = (ItemRequisicaoInterna)itensRequisicaoInterna.get(i);
            conteudo.append("" + Texto.obterStringTamanhoFixo(itemRequisicaoInterna.obterItem().toString(),45) + "  " + Texto.obterNumeroTamanhoFixo("" + itemRequisicaoInterna.obterQuantidadeItem(),12," ") + "  " + Texto.obterStringTamanhoFixo(itemRequisicaoInterna.obterStatusLiteral(),15));
            conteudo.append(QUEBRA);
        }
        conteudo.append("----------------------------------------------------------------------------");
        return conteudo.toString();
    }
}