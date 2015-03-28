/**
   * Projeto: 001 - Tecnolity <br>
   * Autor do Código: Kenia Soares <br>
   * Nome do Arquivo: Motorista.java <br>
   * Linguagem: Java <br>
   *  <br>
   * Requerimentos: Requer máquina virtual java versão 1.3 ou superior. <br>
   *  <br>
   * Objetivo: Classe que representa um motorista no sistema. <br>
   *  <br>
   * Objetivo definido por: Kenia Soares <br>
   * Início da Programação: 13/02/2001 <br>
   * Fim da Programação: <br>
   * Última Versão: 1.0 <br>
*/

package br.com.tecnolity.logistica;

import br.com.tecnolity.administracao.Estado;
import br.com.tecnolity.util.*;
import java.util.*;
import java.sql.*;

public class Motorista 
{
    private int codigo;
    private String placa;
    private String motorista;
    private String identidade;
    private String orgaoEmissorIdentidade;
    private String cpf;
    private String habilitacao;
    private String categoria;
    private String validade;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String cidade;
    private Estado estado;
    private String cep;    
    private String telefone;
    private String celular;

    public Motorista() {}
    
    public Motorista(int codigo,String motorista)
    {
    	this.definirCodigo(codigo);
        try
        {
            this.definirMotorista(motorista);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public Motorista(int codigo)
    {
        this.definirCodigo(codigo);
    }
    
    public Motorista(int codigo, Conexao conexao)throws Exception
    {
        this.definirCodigo(codigo);

        ResultSet dadosMotorista;        
        dadosMotorista = conexao.executarConsulta("select * from motorista where codigo = "+ this.codigo +" ");

        if(dadosMotorista.next())
        {            
            try
            {
                this.definirPlaca(dadosMotorista.getString("placa"));
                this.definirMotorista(dadosMotorista.getString("motorista"));
                this.definirIdentidade(dadosMotorista.getString("identidade"));
                this.definirOrgaoEmissorIdentidade(dadosMotorista.getString("orgao_emissor"));
                this.definirCpf(dadosMotorista.getString("cpf"));
                this.definirHabilitacao(dadosMotorista.getString("habilitacao"));
                this.definirCategoria(dadosMotorista.getString("categoria"));
                this.definirValidade(dadosMotorista.getString("validade"));
                this.definirLogradouro(dadosMotorista.getString("logradouro"));
                this.definirComplemento(dadosMotorista.getString("complemento"));
                this.definirBairro(dadosMotorista.getString("bairro"));
                this.definirCidade(dadosMotorista.getString("cidade"));
                String siglaEstado = dadosMotorista.getString("estado");
                if(siglaEstado != null)
                {
                    this.definirEstado(new Estado(siglaEstado));
                }
                this.definirCep(dadosMotorista.getString("cep"));
                this.definirTelefone(dadosMotorista.getString("telefone"));
                this.definirCelular(dadosMotorista.getString("celular"));            
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }			
    }
    
    public Motorista(String placa,String motorista,String identidade,
                     String orgaoEmissorIdentidade,String cpf,String habilitacao,
                     String categoria,String validade,String logradouro,
                     String complemento,String bairro,String cidade,Estado estado,
                     String cep,String telefone,String celular) throws Exception
    {        
        try
        {
            this.definirPlaca(placa);
            this.definirMotorista(motorista);
            this.definirIdentidade(identidade);
            this.definirOrgaoEmissorIdentidade(orgaoEmissorIdentidade);
            this.definirCpf(cpf);
            this.definirHabilitacao(habilitacao);
            this.definirCategoria(categoria);
            this.definirValidade(validade);
            this.definirLogradouro(logradouro);
            this.definirComplemento(complemento);
            this.definirBairro(bairro);     
            this.definirCidade(cidade);
            this.definirEstado(estado);
            this.definirCep(cep);
            this.definirTelefone(telefone);
            this.definirCelular(celular); 
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public Motorista(int codigo,String placa,String motorista,String identidade,
                     String orgaoEmissorIdentidade,String cpf,String habilitacao,
                     String categoria,String validade,String logradouro,
                     String complemento,String bairro,String cidade,Estado estado,
                     String cep,String telefone,String celular) throws Exception
    {
        this.definirCodigo(codigo);        
        try
        {
            this.definirPlaca(placa);
            this.definirMotorista(motorista);
            this.definirIdentidade(identidade);
            this.definirOrgaoEmissorIdentidade(orgaoEmissorIdentidade);
            this.definirCpf(cpf);
            this.definirHabilitacao(habilitacao);
            this.definirCategoria(categoria);
            this.definirValidade(validade);
            this.definirLogradouro(logradouro);
            this.definirComplemento(complemento);
            this.definirBairro(bairro);   
            this.definirCidade(cidade);
            this.definirEstado(estado);                    
            this.definirCep(cep);
            this.definirTelefone(telefone);
            this.definirCelular(celular); 
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }   
    }
    
    public void definirCodigo(int codigo)
    {
        this.codigo = codigo;
    }
    
    public void definirPlaca(String placa) throws Exception
    {
         if(!placa.equals(""))
            this.placa = placa;
        else
        {
            Exception e = new Exception("A Placa não foi informada.");
            throw e;	
        }
    }
    
    public void definirMotorista(String motorista) throws Exception
    {
         if(!motorista.equals("") && motorista.length() <= 50)
            this.motorista = motorista;
        else
        {
            Exception e = new Exception("O Motorista não foi informado corretamente.");
            throw e;	
        }        
    }
    
    public void definirIdentidade(String identidade) throws Exception
    {
        if(!identidade.equals("") && identidade.length() <= 15)
            this.identidade = identidade;
        else
        {
            Exception e = new Exception("A Identidade não foi informada corretamente.");
            throw e;	
        }        
    }
    
    public void definirOrgaoEmissorIdentidade(String orgaoEmissorIdentidade) throws Exception
    {
        if(!orgaoEmissorIdentidade.equals("") && orgaoEmissorIdentidade.length() <= 15)
            this.orgaoEmissorIdentidade = orgaoEmissorIdentidade;
        else
        {
            Exception e = new Exception("O Órgão Emissor da Identidade não foi informado corretamente.");
            throw e;	
        }
    }
    
    public void definirCpf(String cpf) throws Exception
    {
        if(!cpf.equals("") && cpf.length() <= 15)
            this.cpf = cpf;
        else
        {
            Exception e = new Exception("O CPF não foi informado corretamente.");
            throw e;	
        }
    }
    
    public void definirHabilitacao(String habilitacao) throws Exception
    {
        if(!habilitacao.equals("") && habilitacao.length() <= 10)
            this.habilitacao = habilitacao;
        else
        {
            Exception e = new Exception("A Habilitação não foi informada corretamente.");
            throw e;	
        }        
    }
    
    public void definirCategoria(String categoria) throws Exception
    {
        if(!categoria.equals(""))
            this.categoria = categoria;
        else
        {
            Exception e = new Exception("A Categoria não foi informada.");
            throw e;	
        }        
    }
    
    public void definirValidade(String validade) throws Exception
    {
        String erro = "";
        if(validade.equals(""))
            erro = "A Data de Validade não foi informada.";
        else if(validade.length() == 10)
        {
            if(!Calendario.validarData(validade,"/"))
                erro = "Data de Validade inválida.";
        }
        else
            erro = "Data de Validade inválida.";
        
        if(!erro.equals(""))
        {
            Exception e = new Exception(erro);
            throw e;
        }
        else
            this.validade = validade;
    }
    
    public void definirLogradouro(String logradouro) throws Exception
    {
        if(!logradouro.equals("") && logradouro.length() <= 50)
            this.logradouro = logradouro;
        else
        {
            Exception e = new Exception("O Logradouro não foi informado corretamente.");
            throw e;	
        }        
    }

    public void definirComplemento(String complemento) throws Exception
    {
        if(complemento.length() <= 30)
            this.complemento = complemento;
        else
        {
            Exception e = new Exception("O Complemento não foi informado corretamente.");
            throw e;	
        } 
    }

    public void definirBairro(String bairro) throws Exception
    {
        if(bairro.length() <= 50)
            this.bairro = bairro;
        else
        {
            Exception e = new Exception("O Bairro não foi informado corretamente.");
            throw e;	
        } 
    }

    public void definirCidade(String cidade) throws Exception
    {
        if(!cidade.equals("") && cidade.length() <= 50)
            this.cidade = cidade;
        else
        {
            Exception e = new Exception("A Cidade não foi informada corretamente.");
            throw e;	
        }        
    }

    public void definirEstado(Estado estado) throws Exception
    {
        if(estado != null)
            this.estado = estado;
        else
        {
            Exception e = new Exception("O Estado não foi informado.");
            throw e;
        }        
    }

    public void definirCep(String cep) throws Exception
    {
        if(cep.length() <= 8)
            this.cep = cep;
        else
        {
            Exception e = new Exception("O Cep não foi informado corretamente.");
            throw e;	
        } 
    }

    public void definirTelefone(String telefone) throws Exception
    {
        if(telefone.length() <= 15)
            this.telefone = telefone;
        else
        {
            Exception e = new Exception("O Telefone não foi informado corretamente.");
            throw e;	
        } 
    }

    public void definirCelular(String celular) throws Exception
    {
        if(celular.length() <= 15)
            this.celular = celular;
        else
        {
            Exception e = new Exception("O Celular não foi informado corretamente.");
            throw e;	
        } 
    }

    public int obterCodigo()
    {
    	return this.codigo;	
    }
    
    public String obterPlaca()
    {
        return this.placa;
    }
    
    public String obterMotorista()
    {
        return this.motorista;
    }
    
    public String obterIdentidade()
    {
        return this.identidade;
    }
    
    public String obterOrgaoEmissorIdentidade()
    {
        return this.orgaoEmissorIdentidade;
    }
    
    public String obterCpf()
    {
        return this.cpf;
    }
    
    public String obterHabilitacao()
    {
        return this.habilitacao;
    }
    
    public String obterCategoria()
    {
        return this.categoria;
    }
    
    public String obterValidade()
    {
        return this.validade;
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

    public String obterCelular()
    {
        return this.celular;
    }
   
    public Vector carregarMotoristas(Conexao conexao) throws Exception
    {
    	ResultSet dadosMotorista;
        Vector motoristas = null;
        try
        {
            dadosMotorista = conexao.executarConsulta("select codigo,motorista from motorista order by motorista asc");
            motoristas = new Vector();
            motoristas.addElement(null);
            while(dadosMotorista.next())
            {
                motoristas.addElement(new Motorista(dadosMotorista.getInt("codigo"),dadosMotorista.getString("motorista")));
            }
            dadosMotorista.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return motoristas;
    } 
    
    public void cadastrarMotorista() throws Exception
	{
		String query = "insert into motorista (placa,motorista,identidade,orgao_emissor,cpf,habilitacao,categoria,validade,logradouro,complemento,bairro,cidade,estado,cep,telefone,celular) values ";
		query = query + "('"+ this.placa +"', '"+ this.motorista +"', '"+ this.identidade +"', '"+ this.orgaoEmissorIdentidade +"','"+ this.cpf +"','"+ this.habilitacao +"','"+ this.categoria +"','"+ Calendario.inverterFormato(this.validade,"/") +"','"+ this.logradouro +"','"+ this.complemento +"','"+ this.bairro +"','"+ this.cidade +"','"+ this.estado.getSigla() +"','"+ this.cep +"','"+ this.telefone +"','"+ this.celular +"')";
		Conexao conexao = new Conexao('T');
		if (conexao.abrirConexao())
		{
			conexao.executarAtualizacao(query);
			conexao.fecharConexao();
		}
                else
                {
                    Exception e = new Exception("Não foi possível realizar uma conexão com o banco de dados.");
                    throw e;
                }
	}
        
        public void alterarMotorista() throws Exception
        {
            String query = "update motorista set placa = '"+ this.placa +"',motorista = '"+ this.motorista +"',identidade = '"+ this.identidade +"',orgao_emissor = '"+ this.orgaoEmissorIdentidade +"',cpf = '"+ this.cpf +"',habilitacao = '"+ this.habilitacao +"',categoria = '"+ this.categoria +"',validade = '"+ Calendario.inverterFormato(this.validade,"/") +"',logradouro = '"+ this.logradouro +"',complemento = '"+ this.complemento +"',bairro = '"+ this.bairro +"',cidade = '"+ this.cidade +"',estado = '"+ this.estado.getSigla() +"',cep = '"+ this.cep +"',telefone = '"+ this.telefone +"',celular = '"+ this.celular +"' where codigo = "+ this.codigo +" ";
            Conexao conexao = new Conexao('T');
            if(conexao.abrirConexao())
            {            
                conexao.executarAtualizacao(query);
                conexao.fecharConexao();
            }
            else
            {
                Exception e = new Exception("Não foi possível realizar uma conexão com o banco de dados.");
                throw e;
            }
        }
        
        public void excluirMotorista() throws Exception
        {
            String query = "delete from motorista where codigo = "+ this.codigo +" ";
            Conexao conexao = new Conexao('T');
            if(conexao.abrirConexao())
            {            
                conexao.executarAtualizacao(query);
                conexao.fecharConexao();
            }
            else
            {
                Exception e = new Exception("Não foi possível realizar uma conexão com o banco de dados.");
                throw e;
            }
        }
}