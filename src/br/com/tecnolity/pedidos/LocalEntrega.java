package br.com.tecnolity.pedidos;

import java.sql.*;

import br.com.tecnolity.administracao.Estado;
import br.com.tecnolity.util.Conexao;

public class LocalEntrega 
{
    private long codigo;
    private Cliente cliente;
    private String descricaoLocal;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String cidade;
    private Estado estado;
    private String cep;
    private String telefone;
    private String responsavelRecebimento;
    private boolean novoLocalEntrega; // Quando um novo local de entrega for informado para inclusão.
    private boolean invalido; // Quando o local de entrega for selecionado para exclusão.

    public LocalEntrega() 
    {
        setNovoLocalEntrega(true);
    }
    
    public LocalEntrega(long codigo,Cliente cliente, String descricaoLocal) throws Exception
    {
        this.definirCodigo(codigo);
        this.definirCliente(cliente);
        this.definirDescricaoLocal(descricaoLocal);
        this.setNovoLocalEntrega(false);
    }
    
    public LocalEntrega(Cliente cliente, String descricaoLocal, String logradouro, String complemento,
                        String bairro, String cidade, Estado estado, String cep, String telefone,
                        String responsavelRecebimento) throws Exception
    {
        definirCliente(cliente);
        definirDescricaoLocal(descricaoLocal);
        definirLogradouro(logradouro);
        definirComplemento(complemento);
        definirBairro(bairro);
        definirCidade(cidade);
        definirEstado(estado);
        definirCep(cep);
        definirTelefone(telefone);
        definirResponsavelRecebimento(responsavelRecebimento);
        setNovoLocalEntrega(true);
    }
    
    public LocalEntrega(Cliente cliente, long codigo, String descricaoLocal, String logradouro, String complemento,
                        String bairro, String cidade, Estado estado, String cep, String telefone,
                        String responsavelRecebimento) throws Exception
    {
        definirCliente(cliente);
        definirCodigo(codigo);
        definirDescricaoLocal(descricaoLocal);
        definirLogradouro(logradouro);
        definirComplemento(complemento);
        definirBairro(bairro);
        definirCidade(cidade);
        definirEstado(estado);
        definirCep(cep);
        definirTelefone(telefone);
        definirResponsavelRecebimento(responsavelRecebimento);
        setNovoLocalEntrega(false);
    }
    
    public long obterCodigo()
    {
        return this.codigo;
    }
    
    public String obterDescricaoLocal()
    {
        return this.descricaoLocal;
    }
    
    public Cliente obterCliente()
    {
        return this.cliente;
    }
    
    public String obterLogradouro()
    {
        return this.logradouro;
    }
    
    public String obterComplemento()
    {
        return this.complemento;
    }
    
    public String obterBairro()
    {
        return this.bairro;
    }
    
    public String obterCidade()
    {
        return this.cidade;
    }
    
    public Estado obterEstado()
    {
        return this.estado;
    }
    
    public String obterCep()
    {
        return this.cep;
    }
    
    public String obterTelefone()
    {
        return this.telefone;
    }
    
    public String obterResponsavelRecebimento()
    {
        return this.responsavelRecebimento;
    }
    
    public void definirCodigo(long codigo) throws Exception
    {
        if(codigo <= 0)
        {
            Exception e = new Exception("Código do Local de Entrega inválido.");
            throw e;
        }
        this.codigo = codigo;
        setNovoLocalEntrega(false);
    }
    
    public void definirCliente(Cliente cliente) throws Exception
    {
        if(cliente == null)
        {
            Exception e = new Exception("O Cliente não foi informado.");
            throw e;
        }
        this.cliente = cliente;
    }
    
    public void definirDescricaoLocal(String descricaoLocal) throws Exception
    {
        if(descricaoLocal.equals("") || descricaoLocal == null)
        {
            Exception e = new Exception("A Descrição do Local não foi informada.");
            throw e;
        }
        this.descricaoLocal = descricaoLocal;
    }
    
    public void definirLogradouro(String logradouro) throws Exception
    {
        if(!logradouro.equals(""))
        {
            this.logradouro = logradouro;
        }
        else
        {
            Exception e = new Exception("O Logradouro não foi informado.");
            throw e;
        }
    }
    
    public void definirComplemento(String complemento)
    {
        this.complemento = complemento;
    }
    
    public void definirBairro(String bairro)
    {
        this.bairro = bairro;
    }
    
    public void definirCidade(String cidade) throws Exception
    {
        if(!cidade.equals(""))
        {
            this.cidade = cidade;
        }
        else
        {
            Exception e = new Exception("A Cidade não foi informada.");
            throw e;
        }
    }
    
    public void definirEstado(Estado estado) throws Exception
    {
        if(estado != null)
        {
            this.estado = estado;
            this.estado.carregarEstado();
        }
        else
        {
            Exception e = new Exception("O Estado não foi informado.");
            throw e;
        }
    }
    
    public void definirCep(String cep)
    {
        this.cep = cep;
    }
    
    public void definirTelefone(String telefone)
    {
        this.telefone = telefone;
    }
    
    public void definirResponsavelRecebimento(String responsavelRecebimento)
    {
        this.responsavelRecebimento = responsavelRecebimento;
    }
    
    public void addLocalEntrega(Conexao conexao) throws Exception
    {
        String query = "";
        if(isNovoLocalEntrega() && !isInvalido())
        {
                query = "insert into local_entrega (cliente,descricao_local,logradouro,complemento,bairro,cidade,estado,cep,telefone,responsavel_recebimento) " +
                               "values ("+ this.obterCliente().obterCodigo() +",'"+ this.obterDescricaoLocal() +"','"+ this.obterLogradouro() +"','"+ this.obterComplemento() +"','"+ this.obterBairro() +"','"+ this.obterCidade() +"','"+ this.obterEstado().getSigla() +"','"+ this.obterCep() +"','"+ this.obterTelefone() +"','"+ this.obterResponsavelRecebimento() +"')";
                conexao.executarAtualizacao(query);
                return;
        }
        else if(isInvalido())
        {
            deleteLocalEntrega(conexao);
        }
    }
    
    public void deleteLocalEntrega(Conexao conexao) throws Exception
    {
    	ResultSet rsLocalEntrega = conexao.executarConsulta("select * from pedido_cliente where local_entrega = " + this.codigo);
        if(rsLocalEntrega.next())
        {
          	Exception e = new Exception("Não foi possível excluir o local de entrega por estar associado a um pedido cadastrado.");
           	throw e;
        }
        else
          	conexao.executarAtualizacao("delete from local_entrega where codigo_local = " + this.codigo);
        rsLocalEntrega.close();
    }
    
    public String toString()
    {
        return this.descricaoLocal;
    }

    public boolean isNovoLocalEntrega()
    {
        return novoLocalEntrega;
    }

    public void setNovoLocalEntrega(boolean b)
    {
        novoLocalEntrega = b;
    }

    public boolean isInvalido()
    {
        return invalido;
    }

    public void setInvalido(boolean b)
    {
        invalido = b;
    }
}