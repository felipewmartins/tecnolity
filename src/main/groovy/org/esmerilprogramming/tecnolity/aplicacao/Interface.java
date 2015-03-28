package org.esmerilprogramming.tecnolity.aplicacao;

import org.esmerilprogramming.tecnolity.util.*;
import java.sql.*;
import java.util.*;

/**
   * Projeto: 001 - Tecnolity <br>
   * Autor do C�digo: Hildeberto Mendon�a Filho <br>
   * Nome do Arquivo: Interface.java <br>
   * Linguagem: Java <br>
   *  <br>
   * Requerimentos: Requer m�quina virtual java vers�o 1.3 ou superior. <br>
   *  <br>
   * Objetivo: Classe que representa os elementos da Interface de Software. <br>
   * utilizada para obter informa��es sobre a Interface e controle de acesso <br>
   * pelo usu�rio <br>
   *  <br>
   * Objetivo definido por: Hildeberto Mendon�a <br>
   * In�cio da Programa��o: 25/12/2001 <br>
   * Fim da Programa��o: <br>
   * �ltima Vers�o: 1.0 <br>
*/

public class Interface
{
    private int identificador;
    private String nomeInterface;
    private String descricaoInterface;

    public Interface(int identificador, String nomeInterface, String descricaoInterface)
    {
        this.definirIdentificador(identificador);
        this.definirNomeInterface(nomeInterface);
        this.definirDescricaoInterface(descricaoInterface);
    }
    
    public Interface(int identificador)
    {
        this.definirIdentificador(identificador);
    }
    
    public Interface()
    {
        
    }
    
    public int obterIdentificador()
    {
        return this.identificador;
    }
    
    public String obterNomeInterface()
    {
        return this.nomeInterface;
    }
    
    public String obterDescricaoInterface()
    {
        return this.descricaoInterface;
    }
    
    public void definirIdentificador(int identificador)
    {
        this.identificador = identificador;
    }
    
    public void definirNomeInterface(String nomeInterface)
    {
        this.nomeInterface = nomeInterface;
    }
    
    public void definirDescricaoInterface(String descricaoInterface)
    {
        this.descricaoInterface = descricaoInterface;
    }
    
    public Vector carregarInterfaces(Conexao conexao) throws Exception
    {
        Vector interfaces = new Vector();
        ResultSet dadosInterface = conexao.executarConsulta("select * from interface order by interface asc");
        while(dadosInterface.next())
        {
            interfaces.addElement(new Interface(dadosInterface.getInt("identificador"),dadosInterface.getString("interface"),dadosInterface.getString("descricao")));
        }
        dadosInterface.close();
        return interfaces;
    }
    
    public void cadastrarInterfacesPadroes() throws Exception
    {
        Conexao conexao = new Conexao('T');
        
        if(conexao.abrirConexao())
        {
            try
            {
                ResultSet interfaces = conexao.executarConsulta("select * from interface");
                if(!interfaces.next())
                {
                    conexao.executarAtualizacao("insert into interface values (1,'Se��o Administra��o','')");
                    conexao.executarAtualizacao("insert into interface values (2,'Se��o Ger�ncia','')");
                    conexao.executarAtualizacao("insert into interface values (3,'Se��o Log�stica','')");
                    conexao.executarAtualizacao("insert into interface values (4,'Se��o Pedido','')");
                    conexao.executarAtualizacao("insert into interface values (5,'Se��o Produ��o','')");
                    conexao.executarAtualizacao("insert into interface values (6,'Se��o Suprimentos','')");
                    conexao.executarAtualizacao("insert into interface values (7,'Acesso Direto a Base de Dados','')");
                    conexao.executarAtualizacao("insert into interface values (8,'Categoria','')");
                    conexao.executarAtualizacao("insert into interface values (9,'Cliente','')");
                    conexao.executarAtualizacao("insert into interface values (10,'Colaborador','')");
                    conexao.executarAtualizacao("insert into interface values (11,'Componente','')");
                    conexao.executarAtualizacao("insert into interface values (12,'Departamento','')");
                    conexao.executarAtualizacao("insert into interface values (13,'Despesa','')");
                    conexao.executarAtualizacao("insert into interface values (14,'Estado','')");
                    conexao.executarAtualizacao("insert into interface values (15,'Forma de Pagamento','')");
                    conexao.executarAtualizacao("insert into interface values (16,'Fornecedor','')");
                    conexao.executarAtualizacao("insert into interface values (17,'Item','')");
                    conexao.executarAtualizacao("insert into interface values (18,'Lote','')");
                    conexao.executarAtualizacao("insert into interface values (19,'Matriz','')");
                    conexao.executarAtualizacao("insert into interface values (20,'Motorista','')");
                    conexao.executarAtualizacao("insert into interface values (21,'Movimenta��o de Itens','')");
                    conexao.executarAtualizacao("insert into interface values (22,'Multa','')");
                    conexao.executarAtualizacao("insert into interface values (23,'Pa�s','')");
                    conexao.executarAtualizacao("insert into interface values (24,'Pedido','')");
                    conexao.executarAtualizacao("insert into interface values (25,'Atribui��o de Permiss�es','')");
                    conexao.executarAtualizacao("insert into interface values (26,'Produto','')");
                    conexao.executarAtualizacao("insert into interface values (27,'Requisi��o de Compra','')");
                    conexao.executarAtualizacao("insert into interface values (28,'Requisi��o Interna','')");
                    conexao.executarAtualizacao("insert into interface values (29,'Tipo de Produ��o','')");
                    conexao.executarAtualizacao("insert into interface values (30,'Transportadora','')");
                    conexao.executarAtualizacao("insert into interface values (31,'Unidade','')");
                    conexao.executarAtualizacao("insert into interface values (32,'Ve�culo','')");
                    conexao.executarAtualizacao("insert into interface values (33,'Recursos do Pedido','')");
                    conexao.executarAtualizacao("insert into interface values (34,'Altera��o de Pre�o','')");
                }
                conexao.fecharConexao();
            }
            catch(SQLException e)
            {
                
            }
        }
    }
    
    public void excluirInterfacesPadroes() throws Exception
    {
        Conexao conexao = new Conexao('T');
        
        if(conexao.abrirConexao())
        {
            conexao.executarAtualizacao("delete from interface");
            conexao.fecharConexao();
        }
    }
}