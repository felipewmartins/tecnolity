package org.esmerilprogramming.tecnolity.suprimentos

import java.sql.*

import org.esmerilprogramming.tecnolity.administracao.Departamento
import org.esmerilprogramming.tecnolity.pedidos.Pedido
import org.esmerilprogramming.tecnolity.util.*

class Item {
  int codigo
  String descricao
  Categoria categoria
  String armazenamento
  Unidade unidade
  float temperatura
  String seguranca
  float quantidade
  float quantidadeMinima
  float quantidadeMaxima
  int percentualIPI
  int percentualPerda
  boolean ativo
  boolean independente
  Vector departamentos
  Vector fornecedoresItem
  Lote lote

    Item(int codigo, String descricao, Categoria categoria,
        String armazenamento, Unidade unidade, float temperatura,
        String seguranca, float quantidade, float quantidadeMinima,
        float quantidadeMaxima, int percentualIPI, int percentualPerda,
        boolean ativo, boolean independente) throws Exception
    {
      this.definirCodigo(codigo)
        this.definirDescricao(descricao)
        this.definirCategoria(categoria)
        this.definirArmazenamento(armazenamento)
        this.definirUnidade(unidade)
        this.definirTemperatura(temperatura)
        this.definirSeguranca(seguranca)
        this.definirQuantidade(quantidade)
        this.definirQuantidadeMinima(quantidadeMinima)
        this.definirQuantidadeMaxima(quantidadeMaxima)
        this.definirPercentualIPI(percentualIPI)
        this.definirPercentualPerda(percentualPerda)
        this.definirAtivo(ativo)
        this.definirIndependente(independente)
    }

  Item(int codigo, String descricao) throws Exception
  {
    this.definirCodigo(codigo)
      this.definirDescricao(descricao)
  }

  Item(int codigo, String descricao, Categoria categoria) throws Exception
  {
    this.definirCodigo(codigo)
      this.definirDescricao(descricao)
      this.definirCategoria(categoria)
  }

  Item(int codigo, String descricao, Categoria categoria, FornecedorItem fornecedorItem) throws Exception
  {
    this.definirCodigo(codigo)
      this.definirDescricao(descricao)
      this.definirCategoria(categoria)
      this.definirFornecedorItem(fornecedorItem)
  }

  Item(int codigo, String descricao, FornecedorItem fornecedorItem) throws Exception
  {
    this.definirCodigo(codigo)
      this.definirDescricao(descricao)
      this.definirFornecedorItem(fornecedorItem)
  }

  Item(int codigo, String descricao, float quantidade) throws Exception
  {
    this.definirCodigo(codigo)
      this.definirDescricao(descricao)
      this.definirQuantidade(quantidade)
  }

  Item(int codigo, String descricao, float quantidade , float quantidadeMinima) throws Exception
  {
    this.definirCodigo(codigo)
      this.definirDescricao(descricao)
      this.definirQuantidade(quantidade)
      this.definirQuantidadeMinima(quantidadeMinima)
  }

  Item(int codigo, String descricao, Unidade unidade) throws Exception
  {
    this.definirCodigo(codigo)
      this.definirDescricao(descricao)
      this.definirUnidade(unidade)
  }

  Item(int codigo, String descricao, Categoria categoria, Unidade unidade, float quantidade) throws Exception
  {
    this.definirCodigo(codigo)
      this.definirDescricao(descricao)
      this.definirCategoria(categoria)
      this.definirUnidade(unidade)
      this.definirQuantidade(quantidade)
  }

  Item(int codigo, String descricao, Categoria categoria, Unidade unidade, float quantidade, float quantidadeMinima) throws Exception
  {
    this.definirCodigo(codigo)
      this.definirDescricao(descricao)
      this.definirCategoria(categoria)
      this.definirUnidade(unidade)
      this.definirQuantidade(quantidade)
      this.definirQuantidadeMinima(quantidadeMinima)
  }

  Item(int codigo, Conexao conexao) throws Exception
  {
    this.codigo = codigo
      if (conexao.abrirConexao()) {
        try {
          this.carregarItem(conexao)
            this.carregarDepartamentos(conexao)
            this.carregarFornecedores(conexao)
        }
        catch (e) {
          e.printStackTrace()
        }
      }
      else
      {
        Exception e = new Exception('N�o foi poss�vel realizar uma conex�o com o banco de dados.')
          throw e
      }
  }

  static int obterNumeroItensCadastrados(Conexao conexao) throws Exception
  {
    int i = 0
      ResultSet rsTotal = conexao.executarConsulta('select count(codigo) as numero from item')
      if (rsTotal.next()) {
        i = rsTotal.getInt('numero')
      }
    rsTotal.close()
      return i
  }

  FornecedorItem obterFornecedorItem() {
    return (FornecedorItem)fornecedoresItem.get(0)
  }

  private void carregarItem(Conexao conexao) throws Exception
  {
    String query = 'select descricao, i.categoria as categoria_codigo, c.categoria as categoria, armazenamento, i.unidade as unidade_codigo, u.unidade as unidade, temperatura, seguranca, quantidade, quantidade_minima, quantidade_maxima, percentual_ipi, percentual_perda, ativo, independente from item i, unidade u, categoria_item c where i.unidade = u.codigo and i.categoria = c.codigo and i.codigo = '  +  this.codigo + ' '
      try {
        ResultSet resultado = conexao.executarConsulta(query)
          if (resultado.next()) {
            descricao = resultado.getString('descricao')
              categoria = new Categoria(resultado.getInt('categoria_codigo'), resultado.getString('categoria'))
              armazenamento = resultado.getString('armazenamento')
              unidade = new Unidade(resultado.getInt('unidade_codigo'), resultado.getString('unidade'))
              temperatura = resultado.getFloat('temperatura')
              seguranca = resultado.getString('seguranca')
              quantidade = resultado.getFloat('quantidade')
              quantidadeMinima = resultado.getFloat('quantidade_minima')
              quantidadeMaxima = resultado.getFloat('quantidade_maxima')
              percentualIPI = resultado.getInt('percentual_ipi')
              percentualPerda = resultado.getInt('percentual_perda')
              ativo = resultado.getBoolean('ativo')
              independente = resultado.getBoolean('independente')
          }
        resultado.close()
      }
    catch (SQLException sqle) {
      sqle.printStackTrace()
        Exception e = new Exception(sqle.getMessage())
        throw e
    }
  }

  static Vector carregarItensIndependentes(Departamento departamento, Conexao conexao) throws Exception
  {
    Vector itens = new Vector()
      ResultSet dadosItem
      itens.addElement(null)
      String query = 'select i.codigo, i.descricao, ci.codigo as codigo_categoria, ci.categoria as categoria, u.codigo as codigo_unidade, u.unidade as unidade, i.quantidade, i.quantidade_minima, i.quantidade_maxima, i.percentual_ipi, i.independente '  + 
      'from item i, categoria_item ci, unidade u, departamento_item di '  + 
      'where i.independente = 1 and i.unidade = u.codigo and ci.codigo = i.categoria and i.codigo = di.item and di.departamento = '  +  departamento.obterCodigo() +
      'order by i.descricao, ci.categoria'
      dadosItem = conexao.executarConsulta(query)
      while (dadosItem.next()) {
        itens.addElement(new Item(dadosItem.getInt('codigo'),
              dadosItem.getString('descricao'),
              new Categoria(dadosItem.getInt('codigo_categoria'), dadosItem.getString('categoria')),
              new Unidade(dadosItem.getInt('codigo_unidade'), dadosItem.getString('unidade')),
              dadosItem.getFloat('quantidade'),
              dadosItem.getFloat('quantidade_minima')))
      }
    if (!dadosItem.wasNull())
      dadosItem.close()
        return itens
  }

  static Vector carregarItensIndependentes(Conexao conexao) throws Exception
  {
    Vector itens = new Vector()
      ResultSet dadosItem
      itens.addElement(null)
      String query = 'select i.codigo, i.descricao, ci.codigo as codigo_categoria, ci.categoria as categoria, u.codigo as codigo_unidade, u.unidade as unidade, i.quantidade, i.quantidade_minima, i.quantidade_maxima, i.percentual_ipi, i.independente '  + 
      'from item i, categoria_item ci, unidade u '  + 
      'where i.independente = 1 and i.unidade = u.codigo and ci.codigo = i.categoria ' + 
      'order by i.descricao, ci.categoria'
      dadosItem = conexao.executarConsulta(query)
      while (dadosItem.next()) {
        itens.addElement(new Item(dadosItem.getInt('codigo'),
              dadosItem.getString('descricao'),
              new Categoria(dadosItem.getInt('codigo_categoria'), dadosItem.getString('categoria')),
              new Unidade(dadosItem.getInt('codigo_unidade'), dadosItem.getString('unidade')),
              dadosItem.getFloat('quantidade'),
              dadosItem.getFloat('quantidade_minima')))
      }
    if (!dadosItem.wasNull())
      dadosItem.close()
        return itens
  }

  static Vector carregarItensIndependentesEmFalta(Conexao conexao) throws Exception
  {
    Vector itens = new Vector()
      ResultSet dadosItem
      itens.addElement(null)
      String query = 'select i.codigo, i.descricao, ci.codigo as codigo_categoria, ci.categoria as categoria, u.codigo as codigo_unidade, u.unidade as unidade, i.quantidade, i.quantidade_minima, i.quantidade_maxima, i.percentual_ipi, i.independente '  + 
      'from item i, categoria_item ci, unidade u, fornecedor_item fi, fornecedor f '  + 
      'where i.independente = 1 and i.quantidade_minima > i.quantidade and i.unidade = u.codigo and ci.codigo = i.categoria and fi.item = i.codigo and fi.fornecedor = f.codigo ' + 
      'order by i.descricao, ci.categoria'
      dadosItem = conexao.executarConsulta(query)
      while (dadosItem.next()) {
        itens.addElement(new Item(dadosItem.getInt('codigo'),
              dadosItem.getString('descricao'),
              new Categoria(dadosItem.getInt('codigo_categoria'), dadosItem.getString('categoria')),
              new Unidade(dadosItem.getInt('codigo_unidade'), dadosItem.getString('unidade')),
              dadosItem.getFloat('quantidade'),
              dadosItem.getFloat('quantidade_minima')))
      }
    if (!dadosItem.wasNull())
      dadosItem.close()
        return itens
  }

  static Vector carregarItensIndependentesEmFalta(Fornecedor fornecedor, Conexao conexao) throws Exception
  {
    Vector itens = new Vector()
      ResultSet dadosItem
      itens.addElement(null)
      String query = 'select i.codigo as codigo_item, i.descricao, i.quantidade, i.quantidade_minima, f.codigo as codigo_fornecedor, f.razao_social, fi.valor_item '  + 
      'from item i, fornecedor_item fi, fornecedor f '  + 
      'where independente = 1 and quantidade_minima > quantidade and fi.item = i.codigo and fi.fornecedor = f.codigo and f.codigo = '  +  fornecedor.obterCodigo()
      dadosItem = conexao.executarConsulta(query)
      Item item
      while (dadosItem.next()) {
        item = new Item(dadosItem.getInt('codigo_item'), dadosItem.getString('descricao'), dadosItem.getFloat('quantidade'), dadosItem.getFloat('quantidade_minima'))
          FornecedorItem fornecedorItem = new FornecedorItem(fornecedor, item, dadosItem.getFloat('valor_item'))
          item.definirFornecedorItem(fornecedorItem)
          itens.addElement(item)

      }
    if (!dadosItem.wasNull())
      dadosItem.close()
        return itens
  }

  static Vector carregarItens() {
    Vector itens = new Vector()
  }

  static Vector carregarItensExistentes() throws Exception {
    Vector itens = new Vector()
  }

  static Vector carregarItensInativos() throws Exception {
    Vector itens = new Vector()
  }

  static Vector carregarItensSelecionados(int[] codigos, Conexao conexao) throws Exception {
    Vector itens = new Vector()
      ResultSet dadosItem
      itens.addElement(null)
      String query = 'select i.codigo, i.descricao, ci.codigo as codigo_categoria, ci.categoria as categoria, u.codigo as codigo_unidade, u.unidade as unidade, i.quantidade, i.quantidade_minima, i.quantidade_maxima, i.percentual_ipi, i.independente from item i, categoria_item ci, unidade u '  + 
      'where i.categoria = ci.codigo and i.unidade = u.codigo and '
      for (int i = 0;i < codigos.length;i++) {
        if (i == 0)
          query += '(i.codigo = ' + codigos[i] + ' '
        else
          query += 'or i.codigo = ' + codigos[i] + ' '
      }
    query += ') order by i.descricao, ci.categoria'
      dadosItem = conexao.executarConsulta(query)
      while (dadosItem.next()) {
        itens.addElement(new Item(dadosItem.getInt('codigo'),
              dadosItem.getString('descricao'),
              new Categoria(dadosItem.getInt('codigo_categoria'), dadosItem.getString('categoria')),
              new Unidade(dadosItem.getInt('codigo_unidade'), dadosItem.getString('unidade')),
              dadosItem.getFloat('quantidade'), dadosItem.getFloat('quantidade_minima')))
      }
    if (!dadosItem.wasNull())
      dadosItem.close()
        return itens
  }

  static Vector carregarItens(Fornecedor fornecedor, Vector pedidos, Conexao conexao) throws Exception
  {
    ResultSet dadosItem
      Vector itens = new Vector()
      String query = ''
      if (pedidos != null) {
        query = 'select i.codigo, i.descricao '  + 
          'from item i, modelo_pedido mp, quantidade_materia_prima qmp, fornecedor_item fi '  + 
          'where mp.referencia = qmp.referencia and qmp.produto = mp.modelo and mp.numero_sola = qmp.numero_sola and '  + 
          'qmp.item = i.codigo and i.codigo = fi.item '
          if (fornecedor != null)
            query += 'and fi.fornecedor = ' + fornecedor.obterCodigo()
              for (int i = 0;i < pedidos.size();i++) {
                if (i == 0) {
                  query += ' and ('
                }
                query += 'mp.pedido = ' + ((Pedido)pedidos.get(i)).obterCodigo()
                  if (i != (pedidos.size() - 1)) {
                    query += ' or '
                  }
                  else
                  {
                    query += ') '
                  }
              }
        query += 'group by i.codigo, i.descricao, i.quantidade, i.percentual_perda'
      }
      else
      {
        query = 'select i.codigo, i.descricao from item i, fornecedor_item fi '  + 
          'where i.codigo = fi.item '
          if (fornecedor != null)
            query += 'and fi.fornecedor = ' + fornecedor.obterCodigo() + ' '
              query += 'group by i.codigo, i.descricao, i.quantidade, i.percentual_perda'
      }
    dadosItem = conexao.executarConsulta(query)
      itens.addElement(null)
      while (dadosItem.next()) {
        itens.addElement(new Item(dadosItem.getInt('codigo'), dadosItem.getString('descricao')))
      }
    dadosItem.close()
      return itens
  }

  static Vector carregarItens(Departamento departamento, Conexao conexao) throws Exception
  {
    ResultSet dadosItem
      Vector itens = new Vector()
      String query = 'select i.codigo, i.descricao from item i, departamento_item di, departamento d '  + 
      'where di.item = i.codigo and di.departamento = d.codigo and d.codigo = ' +  departamento.obterCodigo() +
      'order by i.descricao asc'
      dadosItem = conexao.executarConsulta(query)
      itens.addElement(null)
      while (dadosItem.next()) {
        itens.addElement(new Item(dadosItem.getInt('codigo'), dadosItem.getString('descricao')))
      }
    dadosItem.close()
      return itens
  }

  static Vector carregarItens(Departamento departamento, Pedido pedido, Conexao conexao) throws Exception
  {
    ResultSet dadosItem
      Vector itens = new Vector()
      String query = 'select i.codigo, i.descricao '  + 
      'from item i, modelo_pedido mp, quantidade_materia_prima qmp, departamento_item di '  + 
      'where mp.referencia = qmp.referencia and qmp.produto = mp.modelo and mp.numero_sola = qmp.numero_sola and qmp.item = i.codigo and i.codigo = di.item and di.departamento = ' +  departamento.obterCodigo() + ' and mp.pedido = ' + pedido.obterCodigo() + ' ' +
      'group by i.codigo, i.descricao'
      dadosItem = conexao.executarConsulta(query)
      itens.addElement('Selecione...')
      while (dadosItem.next()) {
        itens.addElement(new Item(dadosItem.getInt('codigo'), dadosItem.getString('descricao')))
      }
    dadosItem.close()
      return itens
  }

  /** Retorna os itens que foram comprados para um determinado pedido do cliente. */
  Vector carregarItensCompradosPedido(Pedido pedidoCliente, Conexao conexao) throws Exception
  {
    ResultSet dadosItem
      Vector itens = new Vector()
      dadosItem = conexao.executarConsulta('select distinct item.codigo, item.descricao from item, lote, movimentacao_item, requisicao_compra where requisicao_compra.pedido_cliente = ' +  pedidoCliente.obterCodigo() + ' and requisicao_compra.codigo = movimentacao_item.requisicao_compra and movimentacao_item.item = item.codigo or item.independente = 0 order by item.descricao asc')
      itens.addElement(null)
      while (dadosItem.next()) {
        itens.addElement(new Item(dadosItem.getInt('codigo'), dadosItem.getString('descricao')))
      }
    dadosItem.close()
      return itens
  }

  private void carregarDepartamentos() {
    departamentos = new Vector()
    def query = 'select departamento from departamento_item where item = ' + codigo
    def db = Conexao.instance.db
    db.eachRow(query) {
      int depto = resultado.getInt('departamento')
      departamentos.addElement(new Departamento(it.departamento))
    }
  }

  private void carregarFornecedores(Conexao conexao) {
    String query = 'select fi.fornecedor, i.codigo, i.descricao, fi.data_atualizacao_valor, fi.unidade, fi.valor_item, fi.moeda, fi.referencia_fornecedor from fornecedor_item fi, item i where fi.item = i.codigo and fi.item = '  +  this.codigo + ' '
      try {
        fornecedoresItem = new Vector()
          ResultSet resultado = conexao.executarConsulta(query)
          while (resultado.next()) {
            try {
              fornecedoresItem.addElement(new FornecedorItem(new Fornecedor(resultado.getInt('fornecedor')),
                    new Item(this.codigo, resultado.getString('descricao')),
                    resultado.getString('data_atualizacao_valor'),
                    new Unidade(resultado.getInt('unidade')),
                    resultado.getFloat('valor_item'),
                    resultado.getString('moeda'),
                    resultado.getString('referencia_fornecedor')))
            }
            catch (e) {
              e.printStackTrace()
            }
          }
        resultado.close()
      }
    catch (SQLException e) {
      e.printStackTrace()
    }
  }

  void definirCodigo(int codigo) throws Exception
  {
    if (codigo > 0) {
      this.codigo = codigo
    }
    else
    {
      Exception e = new Exception('C�digo incorreto.')
        throw e
    }
  }

  void definirDescricao(String descricao) throws Exception
  {
    if (!descricao.equals('') && descricao.length() <= 60)
      this.descricao = descricao
    else
    {
      Exception e = new Exception('A Descri��o n�o foi informada corretamente.')
        throw e
    }
  }

  void definirFornecedorItem(FornecedorItem fornecedorItem) {
    if (fornecedoresItem == null) {
      fornecedoresItem = new Vector()
        fornecedoresItem.addElement(fornecedorItem)
    }
    else if (fornecedoresItem.size() == 0) {
      fornecedoresItem.addElement(fornecedorItem)
    }
    else
    {
      fornecedoresItem.setElementAt(fornecedorItem, 0)
    }
  }

  void definirCategoria(Categoria categoria) throws Exception
  {
    if (categoria != null)
      this.categoria = categoria
    else
    {
      Exception e = new Exception('A Categoria n�o foi informada.')
        throw e
    }
  }

  void definirUnidade(Unidade unidade) throws Exception
  {
    if (unidade != null)
      this.unidade = unidade
    else
    {
      Exception e = new Exception('A Unidade n�o foi informada.')
        throw e
    }
  }

  void definirTemperatura(float temperatura) throws Exception
  {
    String erro = ''
      if (Float.isNaN(temperatura))
        erro = 'O Valor da Temperatura n�o foi informado corretamente.'

          if (!erro.equals('')) {
            Exception e = new Exception(erro)
              throw e
          }
          else
            this.temperatura = temperatura
  }

  void definirQuantidade(float quantidade) throws Exception
  {
    if (!Float.isNaN(quantidade) && quantidade >= 0.0f)
      this.quantidade = quantidade
    else
    {
      Exception e = new Exception('A Quantidade n�o foi definida.')
        throw e
    }
  }

  void definirQuantidadeMinima(float quantidadeMinima) throws Exception
  {
    String erro = ''
      if (Float.isNaN(quantidadeMinima) || quantidadeMinima < 0.0f)
        erro = 'A Quantidade M�nima n�o foi informada corretamente.'

          if (!erro.equals('')) {
            Exception e = new Exception(erro)
              throw e
          }
          else
            this.quantidadeMinima = quantidadeMinima
  }

  void definirQuantidadeMaxima(float quantidadeMaxima) throws Exception
  {
    String erro = ''
      if (Float.isNaN(quantidadeMaxima) || quantidadeMaxima < 0.0f)
        erro = 'A Quantidade M�xima n�o foi informada corretamente.'

          if (!erro.equals('')) {
            Exception e = new Exception(erro)
              throw e
          }
          else
            this.quantidadeMaxima = quantidadeMaxima
  }

  void definirPercentualIPI(int percentualIPI) throws Exception
  {
    String erro = ''
      if (Float.isNaN(percentualIPI))
        erro = 'O Valor do Percentual de IPI n�o foi informado corretamente.'

          if (!erro.equals('')) {
            Exception e = new Exception(erro)
              throw e
          }
          else
            this.percentualIPI = percentualIPI
  }

  void definirPercentualPerda(int percentualPerda) throws Exception
  {
    String erro = ''
      if (Float.isNaN(percentualPerda))
        erro = 'O Valor do Percentual de Perda n�o foi informado corretamente.'

          if (!erro.equals('')) {
            Exception e = new Exception(erro)
              throw e
          }
          else
            this.percentualPerda = percentualPerda
  }

  void cadastrarItem() throws Exception
  {
    String query = 'insert into item (descricao, categoria, armazenamento, unidade, temperatura, seguranca, quantidade, quantidade_minima, quantidade_maxima, percentual_ipi, percentual_perda, ativo, independente) values '
      query = query  +  '(' + descricao + ', ' + categoria.obterCodigo() + ', ' + armazenamento + ', ' + unidade.obterCodigo() + ', ' + temperatura + ', ' + seguranca + ', 0, ' + quantidadeMinima + ', ' + quantidadeMaxima + ', ' + percentualIPI + ', ' + percentualPerda + ', ' + ((this.ativo)?1:0) + ', ' + ((this.independente)?1:0) + ')'
      Conexao conexao = new Conexao('T')
      if (conexao.abrirConexao()) {
        ResultSet dadosItem = conexao.executarConsulta('select descricao from item where descricao = ' +  this.descricao)
          if (dadosItem.next()) {
            Exception e = new Exception('J� existem um item com esta descri��o.')
              throw e
          }
        dadosItem.close()
          conexao.executarAtualizacao(query)
          // definir o c�digo do �tem e cria um lote basico para suportar sua quantidade atual.
          dadosItem = conexao.executarConsulta('select codigo from item where descricao = ' +  descricao)
          if (dadosItem.next()) {
            this.definirCodigo(dadosItem.getInt('codigo'))
              conexao.executarAtualizacao('insert into lote (item, quantidade, lote_basico) values (' +  this.obterCodigo() + ', ' + this.obterQuantidade() + ', ' + Lote.LOTE_BASICO + ')')
              dadosItem.close()
          }
        conexao.fecharConexao()
      }
      else
      {
        Exception e = new Exception('N�o foi poss�vel realizar uma conex�o com o banco de dados.')
          throw e
      }
  }

  void associarDepartamentos(Vector departamentos) {
    if (departamentos != null) {
      this.departamentos = departamentos
        int numDepartamentos = this.departamentos.size()
        Conexao conexao = new Conexao('T')
        String query = ''
        if (numDepartamentos > 0 && conexao.abrirConexao()) {
          for (int i = 0;i < numDepartamentos;i++) {
            query = 'insert into departamento_item (departamento, item) values (' +  ((Departamento)departamentos.get(i)).obterCodigo() + ', ' + this.obterCodigo() + ')'
              conexao.executarAtualizacao(query)
          }
          conexao.fecharConexao()
        }
    }
  }

  void associarFornecedores(Vector fornecedoresItem) {
    if (fornecedoresItem != null) {
      this.fornecedoresItem = fornecedoresItem
        int numFornecedores = this.fornecedoresItem.size()
        Conexao conexao = new Conexao('T')
        String query = ''
        FornecedorItem fiAtual = null
        if (numFornecedores > 0 && conexao.abrirConexao()) {
          for (int i = 0;i < numFornecedores;i++) {
            fiAtual = (FornecedorItem)this.fornecedoresItem.get(i)
              query = 'insert into fornecedor_item (fornecedor, item, unidade, valor_item, data_atualizacao_valor, moeda, referencia_fornecedor) values (' +  fiAtual.obterFornecedor().obterCodigo() + ', ' + fiAtual.obterItem().obterCodigo() + ', ' + fiAtual.obterUnidade().obterCodigo() + ', ' + fiAtual.obterValorItem() + ', ' + Calendario.inverterFormato(fiAtual.obterDataAtualizacaoValor(), '/') + ', ' + fiAtual.obterMoeda() + ', ' + fiAtual.obterReferenciaFornecedor() + ')'
              conexao.executarAtualizacao(query)
          }
          conexao.fecharConexao()
        }
    }
  }

  void alterarItem() throws Exception
  {
    float quantidadeAtual = 0.0f
      Conexao conexao = new Conexao('T')
      String query = 'select quantidade from item where codigo not in (select item from lote where lote_basico is null) and codigo = '  +  codigo
      if (conexao.abrirConexao()) {
        ResultSet itemSelecionado = conexao.executarConsulta(query)
          if (itemSelecionado.next()) {
            query = 'update item set descricao = ' +  descricao + ', categoria = ' + categoria.obterCodigo() + ', armazenamento = ' + armazenamento + ', unidade = ' + unidade.obterCodigo() + ', temperatura = ' + temperatura + ', seguranca = ' + seguranca + ', quantidade = ' + quantidade + ', quantidade_minima = ' + quantidadeMinima + ', quantidade_maxima = ' + quantidadeMaxima + ', percentual_ipi = ' + percentualIPI + ', percentual_perda = ' + percentualPerda + ', ativo = ' + ((this.ativo)?1:0) + ', independente = ' + ((this.independente)?1:0) + ' where codigo = ' + codigo
              conexao.executarAtualizacao(query)
              query = 'update lote set quantidade = ' +  quantidade + ' where item = ' + codigo + ' and lote_basico = ' + Lote.LOTE_BASICO
              conexao.executarAtualizacao(query)
              itemSelecionado.close()
          }
          else
          {
            query = 'update item set descricao = ' +  descricao + ', categoria = ' + categoria.obterCodigo() + ', armazenamento = ' + armazenamento + ', unidade = ' + unidade.obterCodigo() + ', temperatura = ' + temperatura + ', seguranca = ' + seguranca + ', quantidade_minima = ' + quantidadeMinima + ', quantidade_maxima = ' + quantidadeMaxima + ', percentual_ipi = ' + percentualIPI + ', percentual_perda = ' + percentualPerda + ', ativo = ' + ((this.ativo)?1:0) + ', independente = ' + ((this.independente)?1:0) + ' where codigo = ' + codigo
            conexao.executarAtualizacao(query)
          }
        conexao.fecharConexao()
      }
      else
      {
        Exception e = new Exception('N�o foi poss�vel realizar uma conex�o com o banco de dados.')
          throw e
      }
  }

  void alterarDepartamentosItem(Vector departamentos) {
    if (departamentos != null) {
      this.departamentos = departamentos
        int numDepartamentos = this.departamentos.size()
        Conexao conexao = new Conexao('T')
        String query = ''

        if (numDepartamentos > 0 && conexao.abrirConexao()) {
          query = 'delete from departamento_item where item = ' +  this.obterCodigo() + ' '
            conexao.executarAtualizacao(query)

            for (int i = 0;i < numDepartamentos;i++) {
              query = 'insert into departamento_item (departamento, item) values (' +  ((Departamento)departamentos.get(i)).obterCodigo() + ', ' + this.obterCodigo() + ')'
                conexao.executarAtualizacao(query)
            }
          conexao.fecharConexao()
        }
    }
  }

  void alterarFornecedoresItem(Vector fornecedoresItem) {
    if (fornecedoresItem != null) {
      this.fornecedoresItem = fornecedoresItem
        int numFornecedores = this.fornecedoresItem.size()
        Conexao conexao = new Conexao('T')
        String query = ''
        FornecedorItem fiAtual = null
        if (numFornecedores >= 0 && conexao.abrirConexao()) {
          if (this.obterCodigo() > 0) {
            query = 'delete from fornecedor_item where item = ' +  this.obterCodigo()
              conexao.executarAtualizacao(query)
          }
          for (int i = 0;i < numFornecedores;i++) {
            fiAtual = (FornecedorItem)this.fornecedoresItem.get(i)
              query = 'insert into fornecedor_item (fornecedor, item, unidade, valor_item, data_atualizacao_valor, moeda, referencia_fornecedor) values (' +  fiAtual.obterFornecedor().obterCodigo() + ', ' + fiAtual.obterItem().obterCodigo() + ', ' + fiAtual.obterUnidade().obterCodigo() + ', ' + fiAtual.obterValorItem() + ', ' + Calendario.inverterFormato(fiAtual.obterDataAtualizacaoValor(), '/') + ', ' + fiAtual.obterMoeda() + ', ' + fiAtual.obterReferenciaFornecedor() + ')'
              conexao.executarAtualizacao(query)
          }
          conexao.fecharConexao()
        }
    }
  }

  void excluirFornecedorItem(int codigo) throws Exception
  {
    String query = 'delete from fornecedor_item where item = ' +  codigo
      Conexao conexao = new Conexao('T')
      if (conexao.abrirConexao()) {
        conexao.executarAtualizacao(query)
          conexao.fecharConexao()
      }
      else
      {
        Exception e = new Exception('N�o foi poss�vel realizar uma conex�o com o banco de dados.')
          throw e
      }
  }

  void excluirDepartamentoItem(int codigo) throws Exception
  {
    String query = 'delete from departamento_item where item = ' +  codigo
      Conexao conexao = new Conexao('T')
      if (conexao.abrirConexao()) {
        conexao.executarAtualizacao(query)
          conexao.fecharConexao()
      }
      else
      {
        Exception e = new Exception('N�o foi poss�vel realizar uma conex�o com o banco de dados.')
          throw e
      }
  }

  void excluirItem(int codigo) throws Exception
  {
    String query = 'delete from item where codigo = ' +  codigo
      Conexao conexao = new Conexao('T')
      if (conexao.abrirConexao()) {
        conexao.executarAtualizacao(query)
          conexao.fecharConexao()
      }
      else
      {
        Exception e = new Exception('N�o foi poss�vel realizar uma conex�o com o banco de dados.')
          throw e
      }
  }

  void  carregarListaCompras(Conexao conexao) {

  }

  String toString() {
    return this.obterDescricao()
  }
}
