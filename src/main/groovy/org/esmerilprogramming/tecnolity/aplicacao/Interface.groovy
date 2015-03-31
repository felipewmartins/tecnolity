package org.esmerilprogramming.tecnolity.aplicacao

import org.esmerilprogramming.tecnolity.util.*
import java.sql.*

class Interface
{
  private int identificador
    private String nomeInterface
    private String descricaoInterface

    Interface(int identificador, String nomeInterface, String descricaoInterface) {
      this.definirIdentificador(identificador)
        this.definirNomeInterface(nomeInterface)
        this.definirDescricaoInterface(descricaoInterface)
    }

  Interface(int identificador) {
    this.definirIdentificador(identificador)
  }

  Interface() {

  }

  int obterIdentificador() {
    return this.identificador
  }

  String obterNomeInterface() {
    return this.nomeInterface
  }

  String obterDescricaoInterface() {
    return this.descricaoInterface
  }

  void definirIdentificador(int identificador) {
    this.identificador = identificador
  }

  void definirNomeInterface(String nomeInterface) {
    this.nomeInterface = nomeInterface
  }

  void definirDescricaoInterface(String descricaoInterface) {
    this.descricaoInterface = descricaoInterface
  }

  Vector carregarInterfaces(Conexao conexao) throws Exception
  {
    Vector interfaces = new Vector()
      ResultSet dadosInterface = conexao.executarConsulta("select * from interface order by interface asc")
      while(dadosInterface.next()) {
        interfaces.addElement(new Interface(dadosInterface.getInt("identificador"),dadosInterface.getString("interface"),dadosInterface.getString("descricao")))
      }
    dadosInterface.close()
      return interfaces
  }

  void cadastrarInterfacesPadroes() throws Exception
  {
    Conexao conexao = new Conexao('T')

      if(conexao.abrirConexao()) {
        try
        {
          ResultSet interfaces = conexao.executarConsulta("select * from interface")
            if(!interfaces.next()) {
              conexao.executarAtualizacao("insert into interface values (1,'Seção Administração','')")
                conexao.executarAtualizacao("insert into interface values (2,'Seção Gerência','')")
                conexao.executarAtualizacao("insert into interface values (3,'Seção Logística','')")
                conexao.executarAtualizacao("insert into interface values (4,'Seção Pedido','')")
                conexao.executarAtualizacao("insert into interface values (5,'Seção Produção','')")
                conexao.executarAtualizacao("insert into interface values (6,'Seção Suprimentos','')")
                conexao.executarAtualizacao("insert into interface values (7,'Acesso Direto a Base de Dados','')")
                conexao.executarAtualizacao("insert into interface values (8,'Categoria','')")
                conexao.executarAtualizacao("insert into interface values (9,'Cliente','')")
                conexao.executarAtualizacao("insert into interface values (10,'Colaborador','')")
                conexao.executarAtualizacao("insert into interface values (11,'Componente','')")
                conexao.executarAtualizacao("insert into interface values (12,'Departamento','')")
                conexao.executarAtualizacao("insert into interface values (13,'Despesa','')")
                conexao.executarAtualizacao("insert into interface values (14,'Estado','')")
                conexao.executarAtualizacao("insert into interface values (15,'Forma de Pagamento','')")
                conexao.executarAtualizacao("insert into interface values (16,'Fornecedor','')")
                conexao.executarAtualizacao("insert into interface values (17,'Item','')")
                conexao.executarAtualizacao("insert into interface values (18,'Lote','')")
                conexao.executarAtualizacao("insert into interface values (19,'Matriz','')")
                conexao.executarAtualizacao("insert into interface values (20,'Motorista','')")
                conexao.executarAtualizacao("insert into interface values (21,'Movimentação de Itens','')")
                conexao.executarAtualizacao("insert into interface values (22,'Multa','')")
                conexao.executarAtualizacao("insert into interface values (23,'País','')")
                conexao.executarAtualizacao("insert into interface values (24,'Pedido','')")
                conexao.executarAtualizacao("insert into interface values (25,'Atribuição de Permissões','')")
                conexao.executarAtualizacao("insert into interface values (26,'Produto','')")
                conexao.executarAtualizacao("insert into interface values (27,'Requisição de Compra','')")
                conexao.executarAtualizacao("insert into interface values (28,'Requisição Interna','')")
                conexao.executarAtualizacao("insert into interface values (29,'Tipo de Produção','')")
                conexao.executarAtualizacao("insert into interface values (30,'Transportadora','')")
                conexao.executarAtualizacao("insert into interface values (31,'Unidade','')")
                conexao.executarAtualizacao("insert into interface values (32,'Veículo','')")
                conexao.executarAtualizacao("insert into interface values (33,'Recursos do Pedido','')")
                conexao.executarAtualizacao("insert into interface values (34,'Alteração de Preço','')")
            }
          conexao.fecharConexao()
        }
        catch(SQLException e) {

        }
      }
  }

  void excluirInterfacesPadroes() throws Exception
  {
    Conexao conexao = new Conexao('T')

      if(conexao.abrirConexao()) {
        conexao.executarAtualizacao("delete from interface")
          conexao.fecharConexao()
      }
  }
}
