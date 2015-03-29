package org.esmerilprogramming.tecnolity.suprimentos

import org.esmerilprogramming.tecnolity.util.*

 class FornecedorItem
{
  private Fornecedor fornecedor
  private Item item
  private String dataAtualizacaoValor
  private Unidade unidade
  private float valorItem
  private String moeda
  private String referenciaFornecedor

   FornecedorItem(Fornecedor fornecedor, Item item, float valorItem) throws Exception
  {
    this.definirValorItem(valorItem)
    this.definirFornecedor(fornecedor)
    this.definirItem(item)
  }

   FornecedorItem(Fornecedor fornecedor, String referenciaFornecedor) throws Exception
  {
    this.definirFornecedor(fornecedor)
    this.definirReferenciaFornecedor(referenciaFornecedor)
  }

   FornecedorItem(float valorItem, String referenciaFornecedor) throws Exception
  {
    definirValorItem(valorItem)
    definirReferenciaFornecedor(referenciaFornecedor)
  }

   FornecedorItem(Fornecedor fornecedor, Item item, String dataAtualizacaoValor,
      Unidade unidade, float valorItem, String moeda, String referenciaFornecedor) throws Exception
  {
    this.definirFornecedor(fornecedor)
    definirItem(item)
    definirDataAtualizacaoValor(dataAtualizacaoValor)
    definirUnidade(unidade)
    definirValorItem(valorItem)
    definirMoeda(moeda)
    definirReferenciaFornecedor(referenciaFornecedor)
  }

   Fornecedor obterFornecedor()
  {
    return this.fornecedor
  }

   Item obterItem()
  {
    return this.item
  }

   String obterDataAtualizacaoValor()
  {
    return this.dataAtualizacaoValor
  }

   Unidade obterUnidade()
  {
    return this.unidade
  }

   float obterValorItem()
  {
    return this.valorItem
  }

   String obterMoeda()
  {
    return this.moeda
  }

   String obterReferenciaFornecedor()
  {
    return this.referenciaFornecedor
  }

   void definirItem(Item item) throws Exception
  {
    if(item != null)
      this.item = item
    else
    {
      Exception e = new Exception("Item não informado.")
      throw e
    }
  }

   void definirFornecedor(Fornecedor fornecedor) throws Exception
  {
    if(fornecedor != null)
      this.fornecedor = fornecedor
    else
    {
      Exception e = new Exception("Fornecedor não informado.")
      throw e
    }
  }

   void definirDataAtualizacaoValor(String data) throws Exception
  {
    String erro = ""
    if(data.equals(""))
      erro = "Data de Atualização não foi informada."
    else if(!Calendario.validarData(data,"/"))
      erro = "Data inválida."
    if(!erro.equals(""))
    {
      Exception e = new Exception(erro)
      throw e
    }
    else
    {
      this.dataAtualizacaoValor = data
    }
  }

   void definirUnidade(Unidade unidade) throws Exception
  {
    if(unidade != null)
      this.unidade = unidade
    else
    {
      Exception e = new Exception("A Unidade não foi informada.")
      throw e
    }
  }

   void definirValorItem(float valorItem) throws Exception
  {
    if(!Float.isNaN(valorItem) && valorItem >= 0.0f)
      this.valorItem = valorItem
    else
    {
      Exception e = new Exception("Valor inválido.")
      throw e
    }
    this.valorItem = valorItem
  }

   void definirMoeda(String moeda)
  {
    if(moeda.equals(""))
      this.moeda = 'R$'
    else
      this.moeda = moeda
  }

   void definirReferenciaFornecedor(String referenciaFornecedor)
  {
    this.referenciaFornecedor = referenciaFornecedor
  }

   void alterarValorItem() throws Exception
  {
    String query = "update fornecedor_item set valor_item = "+ this.valorItem +" where fornecedor = "+ this.fornecedor.obterCodigo() +" and item = " + this.item.obterCodigo()
    Conexao conexao = new Conexao('T')
    if (conexao.abrirConexao())
    {
      conexao.executarAtualizacao(query)
      conexao.fecharConexao()
    }
    else
    {
      Exception e = new Exception("Não foi possível realizar uma conexão com o banco de dados.")
      throw e
    }
  }
}
