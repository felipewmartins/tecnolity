package org.esmerilprogramming.tecnolity.suprimentos

import org.esmerilprogramming.tecnolity.administracao.Colaborador
import org.esmerilprogramming.tecnolity.logistica.Transportadora
import org.esmerilprogramming.tecnolity.util.*
import java.sql.*
import java.util.*

/**
   * Projeto: 001 - Tecnolity <br>
   * Autor do Código: Kenia Soares <br>
   * Nome do Arquivo: Movimentacao.java <br>
   * Linguagem: Java <br>
   *  <br>
   * Requerimentos: Requer máquina virtual java versão 1.3 ou superior. <br>
   *  <br>
   * Objetivo: Classe que representa uma movimentação no sistema. <br>
   *  <br>
   * Objetivo definido por: Kenia Soares <br>
   * Início da Programação: 10/03/2002 <br>
   * Fim da Programação: <br>
   * Última Versão: 1.0 <br>
*/

public class Movimentacao 
{
    public static final String ABASTECIMENTO     = "AB"
    public static final String CONSUMO           = "CS"
    public static final String VENDAS            = "VD"
    public static final String DESCARTE          = "DS"
    public static final String DEVOLUCAO         = "DV"
    public static final String DEVOLUCAO_EXTERNA = "DE"
    public static final String DEPOSITO          = "DP"
    public static final String RETIRADA_DEPOSITO = "RD"
    
    private int codigo
    private String tipoMovimentacao
    private Item item
    private ItemRequisicaoInterna itemRequisicaoInterna
    private float quantidadeItem
    private ItemRequisicao itemRequisicao
    private RequisicaoInterna requisicaoInterna
    private Colaborador responsavel
    private Vector movimentacoesItens = new Vector()
    private String dataDespacho, dataRecebimento
    private Transportadora transportadora
    private float valorConhecimento
    private String numeroConhecimento
    private String notaFiscal
    
    public Movimentacao() {}
    
    public Movimentacao(int codigo, Item item)
    {
        definirCodigoMovimentacao(codigo)
        definirItem(item)
    }
    
    public Movimentacao(String tipoMovimentacao,ItemRequisicao itemRequisicao, Colaborador responsavel)
    {
        definirTipoMovimentacao(tipoMovimentacao)
        definirItemRequisicao(itemRequisicao)
        definirResponsavel(responsavel)
    }
    
    public Movimentacao(String tipoMovimentacao,ItemRequisicaoInterna itemRequisicaoInterna,RequisicaoInterna requisicaoInterna,Colaborador responsavel)
    {
        definirTipoMovimentacao(tipoMovimentacao)
        definirItemRequisicaoInterna(itemRequisicaoInterna)
        definirRequisicaoInterna(requisicaoInterna)
        definirResponsavel(responsavel)
    }
     
    public void definirCodigoMovimentacao(int codigo)
    {
        this.codigo = codigo
    }
    
    public void definirItemRequisicaoInterna(ItemRequisicaoInterna itemRequisicaoInterna)
    {
    	this.itemRequisicaoInterna = itemRequisicaoInterna
    }
    
    public void definirTipoMovimentacao(String tipoMovimentacao)
    {
        this.tipoMovimentacao = tipoMovimentacao
    }
    
    public void definirItem(Item item)
    {
        this.item = item
    }
    
    public void definirQuantidadeItem(float quantidadeItem)
    {
        this.quantidadeItem = quantidadeItem
    }
    
    public void definirItemRequisicao(ItemRequisicao itemRequisicao)
    {
        this.itemRequisicao = itemRequisicao
    }
    
    public void definirRequisicaoInterna(RequisicaoInterna requisicaoInterna)
    {
        this.requisicaoInterna = requisicaoInterna
    }
    
    public void definirResponsavel(Colaborador responsavel)
    {
        this.responsavel = responsavel
    }
    
    public void definirDataDespacho(String dataDespacho)
    {
        this.dataDespacho = dataDespacho
    }
    
    public void definirDataRecebimento(String dataRecebimento)
    {
        this.dataRecebimento = dataRecebimento
    }
    
    public void definirTransportadora(Transportadora transportadora)
    {
        this.transportadora = transportadora
    }
    
    public void definirValorConhecimento(float valorConhecimento)
    {
        this.valorConhecimento = valorConhecimento
    }
    
    public void definirNumeroConhecimento(String numeroConhecimento)
    {
        this.numeroConhecimento = numeroConhecimento
    }
    
    public void definirNotaFiscal(String notaFiscal)
    {
        this.notaFiscal = notaFiscal
    }
        
    public ItemRequisicao obterItemRequisicao()
    {
        return this.itemRequisicao   
    }
    
    public int obterCodigo()
    {
        return this.codigo
    }
    
    public Item obterItem()
    {
        return this.item
    }
    
    public String obterDataDespacho()
    {
        return this.dataDespacho
    }
    
    public String obterDataRecebimento()
    {
        return this.dataRecebimento
    }
    
    public Transportadora obterTransportadora()
    {
        return this.transportadora
    }
    
    public float obterValorConhecimento()
    {
        return this.valorConhecimento
    }
    
    public String obterNumeroConhecimento()
    {
        return this.numeroConhecimento
    }
    
    public String obterNotaFiscal()
    {
        return this.notaFiscal
    }
    
    public void cadastrarMovimentacao() throws Exception
    {
        String query = "insert into movimentacao_item (tipo_movimento,item,quantidade,responsavel,requisicao_compra,requisicao_interna,data_despacho,data_recebimento,valor_conhecimento,numero_conhecimento,nota_fiscal) values "
        if(requisicaoInterna != null)
            query = query + "('"+ this.tipoMovimentacao +"', "+ this.itemRequisicaoInterna.obterItem().obterCodigo() +", "+ this.itemRequisicaoInterna.obterQuantidadeItem() +", '"+ this.responsavel.obterMatricula() +"',null, "+ this.requisicaoInterna.obterCodigo() +",null,null,null,null,null)"
        else
            query = query + "('"+ this.tipoMovimentacao +"', "+ this.itemRequisicao.obterItem().obterCodigo() +", "+ this.quantidadeItem +",'"+ this.responsavel.obterMatricula() +"',"+ this.itemRequisicao.obterRequisicaoCompra().obterCodigo() +",null,'"+ Calendario.inverterFormato(this.dataDespacho,"/") +"','"+ Calendario.inverterFormato(this.dataRecebimento,"/") +"',"+ this.valorConhecimento +",'"+ this.numeroConhecimento +"','"+ this.notaFiscal +"')"
        Conexao conexao = new Conexao('T')
        if (conexao.abrirConexao())
        {            
            // registra a movimentação.
            conexao.executarAtualizacao(query)
            // consulta e define o código da movimentação registrada.
            try
            {
                if(requisicaoInterna != null)
                    query = "select codigo from movimentacao_item where tipo_movimento = '"+ this.tipoMovimentacao +"' and item = "+ this.itemRequisicaoInterna.obterItem().obterCodigo() +" and quantidade = "+ this.itemRequisicaoInterna.obterQuantidadeItem() +" and responsavel = '"+ this.responsavel.obterMatricula() +"' and requisicao_interna = " + this.requisicaoInterna.obterCodigo()
                else
                    query = "select codigo from movimentacao_item where tipo_movimento = '"+ this.tipoMovimentacao +"' and item = "+ this.itemRequisicao.obterItem().obterCodigo() +" and quantidade = "+ this.quantidadeItem +" and responsavel = '"+ this.responsavel.obterMatricula() +"' and requisicao_compra = " + this.itemRequisicao.obterRequisicaoCompra().obterCodigo()
                ResultSet mov = conexao.executarConsulta(query)
                if(mov.next())
                {
                    this.definirCodigoMovimentacao(mov.getInt("codigo"))
                }
                mov.close()
            }
            catch(SQLException e)
            {
                Exception ex = new Exception("Não foi possível consultar a movimentação.\n" + e.getMessage())
                throw ex
            }
            conexao.fecharConexao()
        }
        else
        {
            Exception e = new Exception("Não foi possível realizar uma conexão com o banco de dados.")
            throw e
        }
    }
}