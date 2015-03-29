package org.esmerilprogramming.tecnolity.pedidos

import java.sql.*
import java.util.*

import org.esmerilprogramming.tecnolity.administracao.Estado
import org.esmerilprogramming.tecnolity.administracao.Pais
import org.esmerilprogramming.tecnolity.util.*

/**
   * Projeto: 001 - Tecnolity
   * Autor do C�digo: Hildeberto Mendon�a Filho
   * Nome do Arquivo: Cliente.java
   * Linguagem: Java
   * 
   * Requerimentos: Requer m�quina virtual java vers�o 1.3 ou superior.
   * 
   * Objetivo: Classe que representa o cliente da organiza��o.
   * 
   * Objetivo definido por: Hildeberto Mendon�a
   * In�cio da Programa��o: 31/01/2002
   * Fim da Programa��o:
   * �ltima Vers�o: 1.0
*/

public class Cliente 
{
    private long codigo
    private String razaoSocial
    private String nomeFantasia
    private String cnpj
    private String inscricaoEstadual
    private String logradouro
    private String bairro
    private String complemento
    private String cidade
    private Estado estado
    private Pais pais
    private String cep
    private String telefone
    private String fax
    private String contatoComercial
    private String contatoTecnico
    private String email
    private Vector locaisEntrega = new Vector()
    
    public Cliente() 
    {
    
    }
    
    public Cliente(long codigo) throws Exception
    {
        definirCodigo(codigo)
    }
    
    public Cliente(String razaoSocial, String nomeFantasia, String cnpj, String inscricaoEstadual,
                   String logradouro, String bairro, String complemento, String cidade,
                   Estado estado, Pais pais, String cep, String telefone, String fax,
                   String contatoComercial, String contatoTecnico, String email) throws Exception
    {
        this.definirRazaoSocial(razaoSocial)
        this.definirNomeFantasia(nomeFantasia)
        this.definirCnpj(cnpj)
        this.definirInscricaoEstadual(inscricaoEstadual)
        this.definirLogradouro(logradouro)
        this.definirBairro(bairro)
        this.definirComplemento(complemento)
        this.definirCidade(cidade)
        this.definirEstado(estado)
        this.definirPais(pais)
        this.definirCep(cep)
        this.definirTelefone(telefone)
        this.definirFax(fax)
        this.definirContatoComercial(contatoComercial)
        this.definirContatoTecnico(contatoTecnico)
        this.definirEmail(email)
    }
    
    public Cliente(long codigo, String razaoSocial) throws Exception
    {
        this.definirCodigo(codigo)
        this.definirRazaoSocial(razaoSocial)
    }
    
    public long obterCodigo()
    {
        return this.codigo
    }
    
    public String obterRazaoSocial()
    {
        return this.razaoSocial
    }
    
    public String obterNomeFantasia()
    {
        return this.nomeFantasia
    }
    
    public String obterCnpj()
    {
        return this.cnpj
    }
    
    public String obterInscricaoEstadual()
    {
        return this.inscricaoEstadual
    }
    
    public String obterLogradouro()
    {
        return this.logradouro
    }
    
    public String obterBairro()
    {
        return this.bairro
    }
    
    public String obterComplemento()
    {
        return this.complemento
    }
    
    public String obterCidade()
    {
        return this.cidade
    }
    
    public Estado obterEstado()
    {
        return this.estado
    }
    
    public Pais obterPais()
    {
        return this.pais
    }
    
    public String obterCep()
    {
        return this.cep
    }
    
    public String obterTelefone()
    {
        return this.telefone
    }
    
    public String obterFax()
    {
        return this.fax
    }
    
    public String obterContatoComercial()
    {
        return this.contatoComercial
    }
    
    public String obterContatoTecnico()
    {
        return this.contatoTecnico
    }
    
    public String obterEmail()
    {
        return this.email
    }
    
    public void definirCodigo(long codigo) throws Exception
    {
        if(codigo <= 0)
        {
            Exception e = new Exception("C�digo do Cliente inv�lido.")
            throw e
        }
        this.codigo = codigo
    }
    
    public void definirRazaoSocial(String razaoSocial) throws Exception
    {
        if(razaoSocial.equals("") || razaoSocial == null)
        {
            Exception e = new Exception("A Raz�o Social n�o foi informada.")
            throw e
        }
        this.razaoSocial = razaoSocial.trim().toUpperCase()
    }
    
    /**
     *  Define o Nome Fantasia do cliente da f�brica.
     *  @param nomeFantasia nome fantasia do cliente.
     */
    public void definirNomeFantasia(String nomeFantasia)
    {
        this.nomeFantasia = nomeFantasia.trim().toUpperCase()
    }
    
    /**
     *  Define o CNPJ do cliente da f�brica.
     *  @param cnpj n�meros do CNPJ sem pontos e separadores.
     *  @throws Exception caso o CNPJ n�o tenha sido informado.
     */
    public void definirCnpj(String cnpj) throws Exception
    {
        if(!cnpj.equals("") && cnpj.length() <= 15)
        {
            this.cnpj = cnpj.trim()
        }
        else
        {
            Exception e = new Exception("CNPJ inv�lido. Informe somente n�meros.")
            throw e
        }
    }
    
    public void definirInscricaoEstadual(String inscricaoEstadual)
    {
        this.inscricaoEstadual = inscricaoEstadual.trim()
    }
    
    public void definirLogradouro(String logradouro) throws Exception
    {
        if(logradouro.equals("") || logradouro == null)
        {
            Exception e = new Exception("O Logradouro nao foi informado.")
            throw e
        }
        this.logradouro = logradouro.trim().toUpperCase()
    }
    
    public void definirBairro(String bairro)
    {
        this.bairro = bairro.trim().toUpperCase()
    }
    
    public void definirComplemento(String complemento)
    {
        this.complemento = complemento.trim().toUpperCase()
    }
    
    public void definirCidade(String cidade) throws Exception
    {
        if(cidade.equals("") || cidade == null)
        {
            Exception e = new Exception("A Cidade n�o foi informada.")
            throw e
        }
        this.cidade = cidade.trim().toUpperCase()
    }
    
    public void definirEstado(Estado estado)
    {
        this.estado = estado
    }
    
    public void definirPais(Pais pais) throws Exception
    {
        if(pais == null)
        {
            Exception e = new Exception("O Pa�s nao foi informado.")
            throw e
        }
        this.pais = pais
    }
    
    /**
     *  Define o CEP do endere�o do cliente da f�brica.
     *  @param cep n�meros do CEP sem pontos e separadores.
     *  @throws Exception caso o CEP tenha tamanho diferente de 8.
     */
    public void definirCep(String cep) throws Exception
    {
        if(!cep.equals(""))
        {
            if(cep.length() == 8)
                this.cep = cep
            else
            {
                Exception e = new Exception("CEP inv�lido. Informe somente n�meros.")
                throw e
            }
        }
    }
    
    public void definirTelefone(String telefone)
    {
        this.telefone = telefone
    }
    
    public void definirFax(String fax)
    {
        this.fax = fax
    }
    
    public void definirContatoComercial(String contatoComercial)
    {
        this.contatoComercial = contatoComercial
    }
    
    public void definirContatoTecnico(String contatoTecnico)
    {
        this.contatoTecnico = contatoTecnico.trim().toUpperCase()
    }
    
    public void definirEmail(String email)
    {
        this.email = email.trim().toLowerCase()
    }
    
    public void carregarCliente(Conexao conexao) throws Exception
    {
        if(codigo > 0)
        {
            ResultSet dadosCliente = conexao.executarConsulta("select * from cliente where codigo = " + this.codigo)
            if(dadosCliente.next())
            {
                this.definirRazaoSocial(dadosCliente.getString("razao_social"))
                this.definirNomeFantasia(dadosCliente.getString("nome_fantasia"))
                this.definirCnpj(dadosCliente.getString("cnpj"))
                this.definirInscricaoEstadual(dadosCliente.getString("inscricao_estadual"))
                this.definirLogradouro(dadosCliente.getString("logradouro"))
                this.definirBairro(dadosCliente.getString("bairro"))
                this.definirComplemento(dadosCliente.getString("complemento"))
                this.definirCidade(dadosCliente.getString("cidade"))
                this.definirEstado(new Estado(dadosCliente.getString("estado")))
                this.definirPais(new Pais(dadosCliente.getString("pais")))
                this.definirCep(dadosCliente.getString("cep"))
                this.definirTelefone(dadosCliente.getString("telefone"))
                this.definirFax(dadosCliente.getString("fax"))
                this.definirContatoComercial(dadosCliente.getString("contato_comercial"))
                this.definirContatoTecnico(dadosCliente.getString("contato_tecnico"))
                this.definirEmail(dadosCliente.getString("email"))
                this.setLocaisEntrega(Cliente.carregarLocaisEntrega(conexao, this))
            }
            else
            {
                Exception e = new Exception("N�o existe cliente cadastrado com o c�digo informado.")
                throw e
            }
            dadosCliente.close()
        }
        else
        {
            Exception e = new Exception("O C�digo do Cliente n�o foi informado.")
            throw e
        }
    }
    
    public Vector carregarClientes(Conexao conexao) throws Exception
    {
        ResultSet dadosCliente
        Vector clientes = new Vector()
        try
        {
            dadosCliente = conexao.executarConsulta("select codigo, razao_social from cliente order by razao_social asc")
            clientes.addElement(null)
                        
            while(dadosCliente.next())
            {
                clientes.addElement(new Cliente(dadosCliente.getLong("codigo"),dadosCliente.getString("razao_social")))
            }
            dadosCliente.close()
        }
        catch (SQLException e)
        {
            e.printStackTrace()
        }
        return clientes
    }
    
    public static Vector carregarLocaisEntrega(Conexao conexao, Cliente cliente) throws Exception
    {
        ResultSet dadosLocalEntrega
        Vector locaisEntrega = new Vector()
        String query = "select * from local_entrega where cliente = "+ cliente.obterCodigo() +" order by descricao_local asc"
        dadosLocalEntrega = conexao.executarConsulta(query)
        while(dadosLocalEntrega.next())
        {
            locaisEntrega.addElement(new LocalEntrega(cliente,
                                                      dadosLocalEntrega.getLong("codigo_local"),
                                                      dadosLocalEntrega.getString("descricao_local"),
                                                      dadosLocalEntrega.getString("logradouro"),
                                                      dadosLocalEntrega.getString("complemento"),
                                                      dadosLocalEntrega.getString("bairro"),
                                                      dadosLocalEntrega.getString("cidade"),
                                                      (new Estado(dadosLocalEntrega.getString("estado"))),
                                                      dadosLocalEntrega.getString("cep"),
                                                      dadosLocalEntrega.getString("telefone"),
                                                      dadosLocalEntrega.getString("responsavel_recebimento")))
        }
        dadosLocalEntrega.close()
        
        return locaisEntrega
    }
    
    public Vector getLocaisEntrega()
    {
        return this.locaisEntrega
    }
    
    public void cadastrarCliente(Conexao conexao) throws Exception
    {
        String query
        ResultSet dadosCliente = conexao.executarConsulta("select cnpj from cliente where cnpj = '"+ this.cnpj +"'")
        if(dadosCliente.next())
        {
            Exception e = new Exception("J� existe um cliente com este CNPJ.")
            throw e
        }
        dadosCliente.close()
        query = "insert into cliente (razao_social,nome_fantasia,cnpj,inscricao_estadual,logradouro,bairro,complemento,cidade,estado,pais,cep,telefone,fax,contato_comercial,contato_tecnico,email) values " +
                "('"+ this.razaoSocial +"','"+ this.nomeFantasia +"','"+ this.cnpj +"','"+ this.inscricaoEstadual +"','"+ this.logradouro +"','"+ this.bairro +"','"+ this.complemento +"','"+ this.cidade +"','"+ ((this.estado == null)?"":this.estado.getSigla()) +"','"+ this.pais.getSigla() +"','"+ this.cep +"','"+ this.telefone +"','"+ this.fax +"','"+ this.contatoComercial +"','"+ this.contatoTecnico +"','"+ this.email +"')"
        conexao.executarAtualizacao(query)
        LocalEntrega localEntrega
        for(int i = 0i < locaisEntrega.size()i++)
        {
            localEntrega = (LocalEntrega)locaisEntrega.get(i)
            localEntrega.addLocalEntrega(conexao)
        }
        dadosCliente = conexao.executarConsulta("select codigo from cliente where razao_social = '"+ this.razaoSocial +"' and cnpj = '"+ this.cnpj +"'")
        if(dadosCliente.next())
        {
            this.codigo = dadosCliente.getInt("codigo")
        }
    }
    
    public void alterarCliente(Conexao conexao) throws Exception
    {
        String query
        query = "update cliente set razao_social = '"+ this.razaoSocial +"',nome_fantasia = '"+ this.nomeFantasia +"',cnpj = '"+ this.cnpj +"',inscricao_estadual = '"+ this.inscricaoEstadual +"',logradouro = '"+ this.logradouro +"',bairro = '"+ this.bairro +"',complemento = '"+ this.complemento +"',cidade = '"+ this.cidade +"',estado = '"+ this.estado.getSigla() +"',pais = '"+ this.pais.getSigla() +"',cep = '"+ this.obterCep() +"',telefone = '"+ this.telefone +"',fax = '"+ this.fax +"',contato_comercial = '"+ this.contatoComercial +"',contato_tecnico = '"+ this.contatoTecnico +"',email = '"+ this.email +"' where codigo = " + this.codigo
        conexao.executarAtualizacao(query)
        LocalEntrega localEntrega
        for(int i = 0i < locaisEntrega.size()i++)
        {
            localEntrega = (LocalEntrega)locaisEntrega.get(i)
            localEntrega.addLocalEntrega(conexao)
        }
    }
    
    public void excluirCliente(Conexao conexao) throws Exception
    {
        ResultSet dadosModelo = conexao.executarConsulta("select codigo from modelo where cliente = "+ this.obterCodigo())
        while(dadosModelo.next())
        {
            conexao.executarAtualizacao("delete from materia_prima where modelo =" + dadosModelo.getInt("codigo"))
        }
        dadosModelo.close()
        conexao.executarAtualizacao("delete from modelo where cliente = " + this.obterCodigo())
        conexao.executarAtualizacao("delete from local_entrega where cliente =" + this.obterCodigo())
        conexao.executarAtualizacao("delete from cliente where codigo = " + this.obterCodigo())
     }

    public void setLocaisEntrega(Vector vector)
    {
        locaisEntrega = vector
    }
}