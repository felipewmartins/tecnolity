package org.esmerilprogramming.tecnolity.pedidos

import java.io.*
import java.util.*
import java.sql.*

import org.esmerilprogramming.tecnolity.administracao.Estado
import org.esmerilprogramming.tecnolity.producao.Matriz
import org.esmerilprogramming.tecnolity.producao.Produto
import org.esmerilprogramming.tecnolity.suprimentos.Movimentacao
import org.esmerilprogramming.tecnolity.util.*

/**
 * Projeto: 001 - Tecnolity <br>
 * Autor do Código: Hildeberto Mendonça Filho <br>
 * Nome do Arquivo: Pedido.java <br>
 * Linguagem: Java <br>
 *  <br>
 * Requerimentos: Requer máquina virtual java versão 1.3 ou superior. <br>
 *  <br>
 * Objetivo: Classe que representa o pedido do cliente. <br>
 *  <br>
 * Objetivo definido por: Hildeberto Mendonça <br>
 * Início da Programação: 31/01/2002 <br>
 * Fim da Programação: <br>
 * Última Versão: 1.0 <br>
 */

public class Pedido
{
  private long codigo
  private Cliente cliente
  private String ordemCompra
  private String dataEmissao
  private String dataEntrega
  private char tipoOperacao
  private String status
  private float valorTotal
  private int numeroRegistros = 1
  private String[] registros
  private LocalEntrega localEntrega
  private String esteira
  private String observacao
  private Vector produtosPedido

  public static final String PENDENTE   = "1P"
  public static final String PRODUZINDO = "2P"
  public static final String FINALIZADO = "3F"
  public static final String ATRASADO   = "4A"
  public static final String PARALIZADO = "5P"
  public static final String CANCELADO  = "6C"

  Pedido(){}

  Pedido(long codigo) throws Exception
  {
    definirCodigo(codigo)
  }

  Pedido(String status, long codigo) throws Exception
  {
    definirCodigo(codigo)
    definirStatus(status)
  }

  Pedido(long codigo, String ordemCompra) throws Exception
  {
    definirCodigo(codigo)
    definirOrdemCompra(ordemCompra)
  }

  Pedido(long codigo, Cliente cliente, String ordemCompra, String dataEmissao, String dataEntrega, String status, float valorTotal) throws Exception
  {
    definirCodigo(codigo)
    definirCliente(cliente)
    definirOrdemCompra(ordemCompra)
    definirDataEmissao(dataEmissao)
    definirDataEntrega(dataEntrega)
    definirStatus(status)
    definirValorTotal(valorTotal)
  }

  public long obterCodigo()
  {
    return this.codigo
  }

  public String obterOrdemCompra()
  {
    return this.ordemCompra
  }

  public void definirCodigo(long codigo) throws Exception
  {
    if(codigo < 0)
    {
      Exception e = new Exception("Código de Pedido Inválido.")
      throw e
    }
    this.codigo = codigo
  }

  public void definirObservacao(String observacao)
  {
    this.observacao = observacao
  }

  public void definirEsteira(String esteira)
  {
    this.esteira = esteira
  }

  public void definirLocalEntrega(LocalEntrega localEntrega)
  {
    this.localEntrega = localEntrega
  }

  public void definirCliente(Cliente cliente) throws Exception
  {
    if(cliente == null)
    {
      Exception e = new Exception("O Cliente não foi informado.")
      throw e
    }
    this.cliente = cliente
  }

  public void definirTipoOperacao(char tipoOperacao) throws Exception
  {
    if(tipoOperacao == '\u0000')
    {
      Exception e = new Exception("O Tipo de Operação não foi informado.")
      throw e
    }
    this.tipoOperacao = tipoOperacao
  }

  public void definirOrdemCompra(String ordemCompra) throws Exception
  {
    if(ordemCompra != null)
    {
      if(!ordemCompra.equals(""))
      {
        this.ordemCompra = ordemCompra
      }
      else
      {
        Exception e = new Exception("A Ordem de Compra do cliente não foi informada.")
        throw e
      }
    }
  }

  public void definirDataEmissao(String dataEmissao) throws Exception
  {
    if(dataEmissao.equals("") || !Calendario.validarData(dataEmissao,"/"))
    {
      Exception e = new Exception("A Data de Emissão não foi informada.")
      throw e
    }
    this.dataEmissao = dataEmissao
  }

  public void definirDataEntrega(String dataEntrega) throws Exception
  {
    if(!dataEntrega.equals("") && dataEntrega != null)
    {
      if(!Calendario.compararData(dataEntrega,this.dataEmissao,"/"))
      {
        Exception e = new Exception("A Data de Emissão deve ser menor que a Data de Entrega")
        throw e
      }
    }
    else
    {
      Exception e = new Exception("A Data de Entrega não foi informada.")
      throw e
    }
    this.dataEntrega = dataEntrega
  }

  void definirStatus(String status)
  {
    this.status = status
  }

  void definirValorTotal(float valorTotal)
  {
    this.valorTotal = valorTotal
  }

  int obterNumeroRegistros()
  {
    return this.numeroRegistros
  }

  Cliente obterCliente()
  {
    return this.cliente
  }

  char obterTipoOperacao()
  {
    return this.tipoOperacao
  }

  String obterDataEmissao()
  {
    return this.dataEmissao
  }

  String obterDataEntrega()
  {
    return this.dataEntrega
  }

  String obterEsteira()
  {
    return this.esteira
  }

  String obterObservacao()
  {
    return this.observacao
  }

  Vector getProdutosPedido()
  {
    return this.produtosPedido
  }

  Vector obterNumeracaoProdutos(String referencia)
  {
    Vector numeros = new Vector()
    Matriz matriz
    int j = 0
    for(int i = 0 0i < produtosPedido.size()i++)
    {
      matriz = ((ProdutoPedido)produtosPedido.get(i)).obterMatriz()
      if(matriz.obterReferencia().equals(referencia))
      {
        numeros.addElement("" + matriz.obterNumeroSola())
      }
    }

    return numeros
  }

  LocalEntrega obterLocalEntrega()
  {
    return this.localEntrega
  }

  /**
   *  O pedido pode ser feito através de arquivo sequencial. Esse arquivo segue
   *  um padrão nacional cujo formado é compreensível pela maioria das indústrias
   *  de calçados. Esta função lê e alimenta o banco de dados com os dados do
   *  pedido presente no arquivo.
   */
  String importarArquivoEDI(File arquivo) throws Exception
  {
    StringBuffer conteudoArquivo = new StringBuffer()
    StringBuffer conteudoRegistro = new StringBuffer()
    FileReader entrada = new FileReader(arquivo)
    int c
    while((c = entrada.read()) != -1)
    {
      if(c == 13)
      {
        this.numeroRegistros++
      }
    }
    entrada.close()
    registros = new String[numeroRegistros]
    entrada = new FileReader(arquivo)
    int indiceRegistro = 0
    while((c = entrada.read()) != -1)
    {
      if(c == 13)
      {
        registros[indiceRegistro++] = conteudoRegistro.toString()
        conteudoRegistro = new StringBuffer()
      }
      else
      {
        if(c != 10)
        {
          conteudoRegistro.append((char)c)
        }
      }
      conteudoArquivo.append((char)c)
    }
    registros[indiceRegistro] = conteudoRegistro.toString()
    entrada.close()
    importarRegistro()
    return conteudoArquivo.toString()
  }


  /**
   *  
   */
  private void importarRegistro() throws Exception
  {
    String cnpjCliente,anteriorOrdemCompra, proximaOrdemCompra, remessa, dataEmissao, tipoOperacao, 
           dataEntrega, localEntrega, fabrica, codigoProduto, unidadeMedida,
           moeda, condicaoPagamento, drawback, observacao, cnpjFornecedor,
           sequencia, transferenciaICMS, quantidade, precoUnitario, resumo
    boolean clienteVerificado = false
    int codigoCliente = 0, codigoPedido = 0, codigoModelo = 0
    Conexao conexao = new Conexao('T')
    if(conexao.abrirConexao())
    {
      anteriorOrdemCompra = ""
      for(int i = 0i < registros.lengthi++)
      {
        cnpjCliente = registros[i].substring(0,14).trim()
        proximaOrdemCompra = registros[i].substring(14,24).trim()
        sequencia = registros[i].substring(24,26).trim()
        remessa = registros[i].substring(26,32).trim()
        dataEmissao = registros[i].substring(32,40).trim()
        tipoOperacao = registros[i].substring(40,41).trim()
        dataEntrega = registros[i].substring(41,49).trim()
        localEntrega = registros[i].substring(49,51).trim()
        fabrica = registros[i].substring(51,55).trim()
        codigoProduto = registros[i].substring(55,70).trim()
        quantidade = registros[i].substring(70,79).trim()
        unidadeMedida = registros[i].substring(79,82).trim()
        transferenciaICMS = registros[i].substring(82,85).trim()
        precoUnitario = registros[i].substring(85,99).trim()
        moeda = registros[i].substring(99,102).trim()
        condicaoPagamento = registros[i].substring(102,117).trim()
        drawback = registros[i].substring(117,118).trim()
        observacao = registros[i].substring(118,179).trim()
        cnpjFornecedor = registros[i].substring(179,192).trim()
        resumo = registros[i].substring(192).trim()

        if(true)
        {
          /*Verifica a existência do cliente no sistema.*/
          if(!clienteVerificado)
          {
            ResultSet cliente = conexao.executarConsulta("select codigo,cnpj from cliente where cnpj = '"+ cnpjCliente +"'") 
            if(cliente.next())
            {
              codigoCliente = cliente.getInt("codigo")
            }
            else
            {
              cliente.close()
              Exception excecao = new Exception("O cliente do EDI informado não se encontra cadastrado.")
              throw excecao
            }
            clienteVerificado = true
            cliente.close()
          }

          /* Verifica a existencia de pedidos do cliente com a mesma ordem de compra */
          if(!anteriorOrdemCompra.equals(proximaOrdemCompra))
          {
            ResultSet pedidoCliente = conexao.executarConsulta("select codigo, ordem_compra from pedido_cliente where ordem_compra = '"+ proximaOrdemCompra +"' and cliente = "+ codigoCliente +" and tipo_operacao = 'V'")
            /* Se um pedido for encontrado com a mesma ordem de compra um erro ocorrerá */
            if(pedidoCliente.next())
            {
              Exception excecao = new Exception("Este arquivo EDI já foi importado.")
              throw excecao
            }
            pedidoCliente.close()
            /* Cria o novo pedido no banco de dados */
            conexao.executarAtualizacao("insert into pedido_cliente (tipo_operacao,ordem_compra,cliente,local_entrega,sequencia,data_emissao,data_entrega,status,moeda,esteira_cliente,remessa,drawback) " +
                "values ('"+ tipoOperacao +"','"+ proximaOrdemCompra +"',"+ codigoCliente +","+ localEntrega +","+ Integer.parseInt(sequencia) +",'"+ Calendario.inverterFormato(Calendario.formatarAAAAMMDD(dataEmissao,"/"),"/") +"','"+ Calendario.inverterFormato(Calendario.formatarAAAAMMDD(dataEntrega,"/"),"/") +"','PD','"+ moeda +"','"+ fabrica +"','"+ remessa +"','"+ drawback +"')")

            /* Obtém o código do pedido para realizar integridade */
            pedidoCliente = conexao.executarConsulta("select codigo from pedido_cliente where tipo_operacao = '"+ tipoOperacao +"' and ordem_compra = '"+ proximaOrdemCompra +"' and cliente = "+ codigoCliente)
            if(pedidoCliente.next())
            {
              codigoPedido = pedidoCliente.getInt("codigo")
            }
            pedidoCliente.close()
            anteriorOrdemCompra = proximaOrdemCompra
          }

          /* Se o código do Pedido for obtido o sistema seguirá cadastrando os produtos solicitados. */
          if(codigoPedido > 0)
          {
            /* Obtém o código do produto para realizar relacionamento com pedido */
            ResultSet modelos = conexao.executarConsulta("select codigo from modelo where referencia_cliente = '"+ codigoProduto +"'")
            if(modelos.next())
            {
              codigoModelo = modelos.getInt("codigo")

              /* Cadastra o pedido do produto no banco de dados */
              conexao.executarAtualizacao("insert into modelo_pedido (pedido,modelo,quantidade,observacao,transferencia_icms,valor_negociado,moeda,resumo) values " + 
                  "("+ codigoPedido +","+ codigoModelo +","+ Numero.separarInteiroDecimal(quantidade,3) +",'"+ observacao +"',"+ transferenciaICMS +","+ Numero.separarInteiroDecimal(precoUnitario,2) +",'"+ moeda +"','"+ resumo +"')")
            }
            else
            {
              modelos.close()
              Exception excecao = new Exception("Os produtos encontrados no EDI não possuem correspondentes no sistema.")
              throw excecao
            }
            modelos.close()
          }
        }
      }
      conexao.fecharConexao()
    }
    else
    {
      Exception excecao = new Exception("Não foi possível realizar uma conexão com o banco de dados.")
      throw excecao
    }
  }

  /** 
    Cadastra o pedido do cliente no banco de dados. É preciso que o objeto seja
   *  criado e suas propriedades alimentadas com os valores a serem inseridos.
   */
  void cadastrarPedido() throws Exception
  {
    Conexao conexao = new Conexao('T')
    if(conexao.abrirConexao())
    {
      conexao.executarAtualizacao("insert into pedido_cliente (tipo_operacao,ordem_compra,cliente,local_entrega,data_emissao,data_entrega,status,esteira_cliente,observacao) " + 
          "values ('"+ this.tipoOperacao +"','"+ this.ordemCompra +"',"+ this.cliente.obterCodigo() +","+ this.localEntrega.obterCodigo() +",'"+ Calendario.inverterFormato(this.dataEmissao,"/") +"','"+ Calendario.inverterFormato(this.dataEntrega,"/") +"','"+ Pedido.PENDENTE +"','"+ this.esteira +"','"+ this.observacao +"')")
      ResultSet pedidoInserido = conexao.executarConsulta("select codigo from pedido_cliente where tipo_operacao = '"+ this.tipoOperacao +"' and ordem_compra = '"+ this.ordemCompra +"' and cliente = " + this.cliente.obterCodigo())
      if(pedidoInserido.next())
      {
        this.codigo = pedidoInserido.getLong("codigo")
      }
      conexao.executarAtualizacao("insert into historico_status_pedido values ("+ this.codigo +",'"+ Pedido.PENDENTE +"',getdate())")
      pedidoInserido.close()
      conexao.fecharConexao()
    }
  }

  void alterarPedido() throws Exception
  {
    Conexao conexao = new Conexao('T')
    if(conexao.abrirConexao())
    {
      conexao.executarAtualizacao("update pedido_cliente set tipo_operacao = '"+ this.tipoOperacao +"', ordem_compra = '"+ this.ordemCompra +"',cliente = "+ this.cliente.obterCodigo() +
          ",local_entrega = "+ this.localEntrega.obterCodigo() +",data_emissao = '"+ Calendario.inverterFormato(this.dataEmissao,"/") +
          "',data_entrega = '"+ Calendario.inverterFormato(this.dataEntrega,"/") +"',esteira_cliente = '"+ this.esteira +"',observacao = '"+ this.observacao +
          "' where codigo = " + this.codigo)
      conexao.fecharConexao()
    }
  }

  void alterarStatus() throws Exception
  {
    Conexao conexao = new Conexao('T')
    if(conexao.abrirConexao())
    {
      conexao.executarAtualizacao("update pedido_cliente set status = '"+ this.status +"' where codigo = " + this.codigo)
      ResultSet rsStatus = conexao.executarConsulta("select status from historico_status_pedido where status = '"+ this.status +"' and pedido = "+ this.codigo)
      if(rsStatus.next())
        conexao.executarAtualizacao("update historico_status_pedido set data = getdate() where pedido = "+ this.codigo + " and status = '"+ this.status +"'")
      else
        conexao.executarAtualizacao("insert into historico_status_pedido values ("+ this.codigo +",'"+ this.status +"',getdate())")
      conexao.fecharConexao()
    }
  }

  /** Cancela o pedido atual, evitando que seja produzido. */
  void cancelarPedido() throws Exception
  {
    Conexao conexao = new Conexao('T')
    if(conexao.abrirConexao())
    {
      conexao.executarAtualizacao("update pedido_cliente set status = '"+ CANCELADO +"' where codigo = " + this.codigo)
      conexao.fecharConexao()
    }
  }

  /**
   *  Relaciona os produtos da empresa com o pedido, de forma que os produtos
   *  a serem relacionados sejam os que serão produzidos para aquele pedido.
   */
  void associarProdutos(Vector produtosPedido) throws Exception
  {
    Conexao conexao = new Conexao('T')
    ProdutoPedido produtoPedido
    ResultSet rsProdutoPedido
    if(conexao.abrirConexao())
    {
      //Exclui todos os produtos do pedido e os insere novamente.
      String query = "delete from modelo_pedido where pedido = " + this.codigo
      conexao.executarAtualizacao(query)
      for(int i = 0i < produtosPedido.size()i++)
      {
        produtoPedido = (ProdutoPedido)produtosPedido.get(i)
        query = "select * from modelo_pedido where pedido = "+ this.codigo +" and modelo = "+ produtoPedido.obterProduto().obterCodigo() +" and referencia = '"+ produtoPedido.obterMatriz().obterReferencia() +"' and numero_sola = " + produtoPedido.obterMatriz().obterNumeroSola()
        rsProdutoPedido = conexao.executarConsulta(query)
        if(rsProdutoPedido.next())
        {
          Exception e = new Exception("O produto " + produtoPedido.obterProduto().obterNomeModelo() + "\nde referência " + produtoPedido.obterMatriz().obterReferencia() + " e número " + produtoPedido.obterMatriz().obterNumeroSola() + "\njá foi registrado anteriormente no pedido "+ this.codigo +".")
          throw e
        }
        else
        {
          query = "insert into modelo_pedido (pedido,modelo,referencia,numero_sola,quantidade,observacao,transferencia_icms,valor_negociado,moeda) " + 
            "values ("+ this.codigo +","+ produtoPedido.obterProduto().obterCodigo() +",'"+ produtoPedido.obterMatriz().obterReferencia() +"',"+ produtoPedido.obterMatriz().obterNumeroSola() +","+ produtoPedido.obterQuantidade() +",'"+ produtoPedido.obterObservacao() +"',"+ produtoPedido.obterTransferenciaICMS() +","+ produtoPedido.obterValorNegociado() +",'"+ produtoPedido.obterMoeda() +"')"
          conexao.executarAtualizacao(query)
        }
        rsProdutoPedido.close()
      }
    }
    conexao.fecharConexao()
  }

  void carregarPedido(Conexao conexao)
  {
    String query = "select c.codigo as codigo_cliente, c.razao_social , pc.tipo_operacao, pc.ordem_compra, pc.data_emissao, pc.data_entrega, le.codigo_local, le.descricao_local, le.logradouro, le.complemento, le.bairro, le.cidade, le.estado, le.cep, le.telefone, le.responsavel_recebimento, pc.esteira_cliente, pc.observacao " +
      "from pedido_cliente pc, cliente c, local_entrega le " +
      "where pc.cliente = c.codigo and c.codigo = le.cliente and pc.local_entrega = le.codigo_local and pc.codigo =" + this.obterCodigo()
    try
    {
      ResultSet rsPedido = conexao.executarConsulta(query)
      if(rsPedido.next())
      {
        this.definirCliente(new Cliente(rsPedido.getLong("codigo_cliente"),rsPedido.getString("razao_social")))
        this.definirTipoOperacao(rsPedido.getString("tipo_operacao").charAt(0))
        this.definirOrdemCompra(rsPedido.getString("ordem_compra"))
        this.definirDataEmissao(Calendario.ajustarFormatoDataBanco(rsPedido.getString("data_emissao")))
        this.definirDataEntrega(Calendario.ajustarFormatoDataBanco(rsPedido.getString("data_entrega")))
        this.definirLocalEntrega(new LocalEntrega(this.cliente,rsPedido.getString("descricao_local"),rsPedido.getString("logradouro"),rsPedido.getString("complemento"),rsPedido.getString("bairro"),rsPedido.getString("cidade"),new Estado(rsPedido.getString("estado")),rsPedido.getString("cep"),rsPedido.getString("telefone"),rsPedido.getString("responsavel_recebimento")))
        this.definirEsteira(rsPedido.getString("esteira_cliente"))
        this.definirObservacao(rsPedido.getString("observacao"))
        rsPedido.close()
        rsPedido = null
      }

      query = "select mp.modelo as codigo_modelo,m.referencia_cliente, m.modelo, mp.referencia, mp.numero_sola,mp.quantidade, mp.transferencia_icms, mp.moeda, m.valor, mp.observacao " +
        "from modelo_pedido mp, modelo m, matriz_modelo mm " +
        "where mp.modelo = m.codigo and mp.referencia = mm.referencia and mp.numero_sola = mm.numero_sola and mp.pedido = " + this.codigo + " " +
        "order by mm.referencia, mp.modelo asc"
      ResultSet rsModeloPedido = conexao.executarConsulta(query)
      produtosPedido = new Vector()
      while(rsModeloPedido.next())
      {
        ProdutoPedido produtoPedido = new ProdutoPedido(this,
            new Produto(rsModeloPedido.getInt("codigo_modelo"),rsModeloPedido.getString("referencia_cliente"),rsModeloPedido.getString("modelo")),
            new Matriz(rsModeloPedido.getString("referencia"),rsModeloPedido.getInt("numero_sola")),
            rsModeloPedido.getFloat("quantidade"),
            rsModeloPedido.getInt("transferencia_icms"),
            rsModeloPedido.getString("moeda"), 
            rsModeloPedido.getFloat("valor"),
            rsModeloPedido.getString("observacao"))
        produtosPedido.addElement(produtoPedido)
      }
      rsModeloPedido.close()
      rsModeloPedido = null
    }
    catch(SQLException e){ e.printStackTrace() }
    catch(Exception e){ e.printStackTrace() }
  }

  /**
   * Method carregarPedidosClientes. obtém uma lista com todos os pedidos de clientes
   * que ainda não foram finalizados.
   * @param conexao conexão com o banco de dados para realização de consulta.
   * @return Vector pedidos do cliente que ainda não estão finalizados.
   * @throws Exception 
   */
  static Vector carregarPedidosClientes(Conexao conexao) throws Exception
  {
    ResultSet dadosPedido
    Vector pedidos = new Vector()
    dadosPedido = conexao.executarConsulta("select codigo,ordem_compra from pedido_cliente where status <> '"+ Pedido.CANCELADO +"' and status <> '"+ Pedido.FINALIZADO +"' order by codigo asc")
    pedidos.addElement("Selecione...")
    while(dadosPedido.next())
    {
      pedidos.addElement(new Pedido(dadosPedido.getLong("codigo"),dadosPedido.getString("ordem_compra")))
    }
    dadosPedido.close()
    return pedidos
  }

  static Vector carregarPedidosPendentes(Conexao conexao) throws Exception
  {
    ResultSet dadosPedido
    Vector pedidos = new Vector()
    dadosPedido = conexao.executarConsulta("select codigo,ordem_compra from pedido_cliente where status = '"+ Pedido.PENDENTE +"' order by codigo asc")
    while(dadosPedido.next())
    {
      pedidos.addElement(new Pedido(dadosPedido.getLong("codigo"),dadosPedido.getString("ordem_compra")))
    }
    dadosPedido.close()
    return pedidos
  }

  static Vector carregarPedidosProduzindo(Conexao conexao) throws Exception
  {
    ResultSet dadosPedido
    Vector pedidos = new Vector()
    dadosPedido = conexao.executarConsulta("select codigo,ordem_compra from pedido_cliente where status = '"+ Pedido.PRODUZINDO +"' order by codigo asc")
    while(dadosPedido.next())
    {
      pedidos.addElement(new Pedido(dadosPedido.getLong("codigo"),dadosPedido.getString("ordem_compra")))
    }
    dadosPedido.close()
    return pedidos
  }

  /**
   * Method carregarRecursosPedido. Retorna os recursos necessários para a produção
   * de um pedido, informando a quantidade necessária, a quantidade requisitada e
   * a quantidade disponível.
   * @param conexao Conexão ativa com o banco de dados.
   * @param pedido Pedido selecionado pelo usuário.
   * @return String[][] dados para preenchimento de tabela.
   * @throws Exception
   */
  static String[][] carregarRecursosPedido(Conexao conexao, Pedido pedido) throws Exception
  {
    String[][] dadosRecursos = new String[40][6]
    String query = "select i.codigo,i.descricao,(sum(qmp.quantidade * mp.quantidade) + ((i.percentual_perda * sum(qmp.quantidade * mp.quantidade))/100)) as necessaria "+
      "from item i, modelo_pedido mp, quantidade_materia_prima qmp "+
      "where mp.referencia = qmp.referencia and qmp.produto = mp.modelo and mp.numero_sola = qmp.numero_sola and qmp.item = i.codigo and mp.pedido = " + pedido.obterCodigo() + " " +
      "group by i.codigo,i.descricao,i.quantidade,i.percentual_perda"
    ResultSet rsItensPedido = conexao.executarConsulta(query)
    ResultSet rsItensRequisitados
    ResultSet rsItensDisponiveis
    int linha = 0
    while(rsItensPedido.next())
    {
      int codigo = rsItensPedido.getInt("codigo")
      dadosRecursos[linha][0] = rsItensPedido.getString("descricao")
      float necessario = rsItensPedido.getFloat("necessaria")
      dadosRecursos[linha][1] = "" + Numero.inverterSeparador(necessario)
      query = "select sum(ir.quantidade) as quantidade from requisicao_compra rc, item_requisicao ir " +
        "where rc.codigo = ir.requisicao_compra and rc.pedido_cliente = "+ pedido.obterCodigo() +" and ir.item = " + codigo
      rsItensRequisitados = conexao.executarConsulta(query)
      if(rsItensRequisitados.next())
      {
        float quantidade = rsItensRequisitados.getFloat("quantidade")
        dadosRecursos[linha][2] = "" + Numero.inverterSeparador(quantidade)
        dadosRecursos[linha][3] = "" + Numero.formatarValorNumerico(((quantidade * 100)/necessario),2,",") + "%"
        rsItensRequisitados.close()
      }
      query = "select i.codigo, i.descricao, sum(mi.quantidade) as quantidade " +
        "from movimentacao_item mi, item i, requisicao_compra rc " +
        "where tipo_movimento = '"+ Movimentacao.ABASTECIMENTO +"' and i.codigo = mi.item and mi.requisicao_compra = rc.codigo and rc.pedido_cliente = "+ pedido.obterCodigo() +" and mi.item = "+ codigo +" " +
        "group by i.codigo, i.descricao"
      rsItensDisponiveis = conexao.executarConsulta(query)
      if(rsItensDisponiveis.next())
      {
        float quantidade = rsItensDisponiveis.getFloat("quantidade")
        dadosRecursos[linha][4] = "" + Numero.inverterSeparador(quantidade)
        dadosRecursos[linha][5] = "" + Numero.formatarValorNumerico(((quantidade * 100)/necessario),2,",") + "%"
        rsItensDisponiveis.close()
      }
      linha++
    }
    rsItensPedido.close()
    return dadosRecursos
  }

  Vector carregarHistoricoPedido(Conexao conexao) throws Exception
  {
    ResultSet rsHistorico
    Vector historico = new Vector()
    rsHistorico = conexao.executarConsulta("select * from historico_status_pedido where pedido = "+ this.obterCodigo() +" order by status")
    while(rsHistorico.next())
    {
      historico.addElement(new RegistroHistoricoStatusPedido(rsHistorico.getString("status"),rsHistorico.getString("data")))
    }
    rsHistorico.close()
    return historico
  }

  String toString()
  {
    return "" + this.codigo + " - " + this.ordemCompra
  }
}
