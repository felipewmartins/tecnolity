package org.esmerilprogramming.tecnolity.logistica

import org.esmerilprogramming.tecnolity.util.*
import java.sql.*
import java.util.*

 class Despesa 
{

	private String placa, descricao, data, placaAntesAlteracao, dataAntesAlteracao
	private float valor
	
	 Despesa(){}		
        
         Despesa(String data)
        {
            this.data = data
        }
        
         Despesa(String placa, String data)
        {            
            try
            {
                this.definirPlaca(placa)
                this.definirData(data) 
            }
            catch(Exception e)
            {
                e.printStackTrace()
            }
        }
	
	 Despesa(String placa, String data, Conexao conexao)throws Exception
	{            
            try
            {
                this.definirPlaca(placa)
                this.definirData(data) 
                this.definirPlacaAntesAlteracao(placa)
                this.definirDataAntesAlteracao(data)
            }
            catch(Exception e)
            {
                e.printStackTrace()
            }

            ResultSet dadosDespesa        
            dadosDespesa = conexao.executarConsulta("select * from despesa_veiculo where veiculo = '"+ this.placa +"' and datahora = '"+ Calendario.inverterFormato(this.data,"/") +"' ")
        
            if(dadosDespesa.next())
            {                    
                try
                {
                    this.definirDescricao(dadosDespesa.getString("descricao"))
                    this.definirValor(dadosDespesa.getFloat("valor"))            
                }
                catch(Exception e)
                {
                    e.printStackTrace()
                }
            }			
	}
	
	 Despesa(String placa, String descricao, float valor, String data)
	{		
		try
                {
                    this.definirPlaca(placa)
                    this.definirDescricao(descricao)
                    this.definirValor(valor)
                    this.definirData(data)
                }
                catch(Exception e)
                {
                    e.printStackTrace()
                }
	}
        
         Despesa(String placa, String descricao, float valor, String data, String placaAntesAlteracao, String dataAntesAlteracao)
	{		
		try
                {
                    this.definirPlaca(placa)
                    this.definirPlacaAntesAlteracao(placaAntesAlteracao)
                    this.definirDescricao(descricao)
                    this.definirValor(valor)
                    this.definirData(data)
                    this.definirDataAntesAlteracao(dataAntesAlteracao)
                }
                catch(Exception e)
                {
                    e.printStackTrace()
                }
	}
        
         void definirPlacaAntesAlteracao(String placaAntesAlteracao) throws Exception
        {
            if(!placaAntesAlteracao.equals(""))           
                this.placaAntesAlteracao = placaAntesAlteracao
            else
            {
                Exception e = new Exception("A Placa não foi informada.")
                throw e	
            }            
        }
        
	 void definirDataAntesAlteracao(String dataAntesAlteracao) throws Exception
        {
            
            String erro = ""
            if(dataAntesAlteracao.equals(""))
                erro = "A Data não foi informada."
            else if(dataAntesAlteracao.length() == 10)
            {
                if(!Calendario.validarData(dataAntesAlteracao,"/"))
                    erro = "Data inválida."
            }
            else if(dataAntesAlteracao.length() < 10)
                erro = "Data inválida."
            
            if(!erro.equals(""))
            {
                Exception e = new Exception(erro)
                throw e
            }
            else
                this.dataAntesAlteracao = dataAntesAlteracao
        }
        
	 void definirPlaca(String placa) throws Exception
	{
            if(!placa.equals(""))           
                this.placa = placa
            else
            {
                Exception e = new Exception("A Placa não foi informada.")
                throw e	
            }  	            
	}
	
	 void definirDescricao(String descricao) throws Exception
	{
	    if(!descricao.equals("") && descricao.length() <= 500)           
                this.descricao = descricao
            else
            {
                Exception e = new Exception("A Descrição não foi informada.")
                throw e	
            }  	            
	}
	
	 void definirValor(float valor) throws Exception
	{	   
            String erro = ""
            if(Float.isNaN(valor) || valor <= 0.0f)
                    erro = "O Valor não foi informado corretamente."

            if(!erro.equals(""))
            {
                    Exception e = new Exception(erro)
                    throw e
            }
            else
                this.valor = valor
	}
	
	 void definirData(String data) throws Exception
	{
		
            String erro = ""
            if(data.equals(""))
                erro = "A Data não foi informada."
            else if(data.length() == 10)
            {
                if(!Calendario.validarData(data,"/"))
                    erro = "Data inválida."
            }
            else
                erro = "Data inválida."
            
            if(!erro.equals(""))
            {
                Exception e = new Exception(erro)
                throw e
            }
            else
                this.data = data
	}
        
         String obterPlacaAntesAlteracao()
        {
            return this.placaAntesAlteracao
        }
        
         String obterDataAntesAlteracao()
        {
            return this.dataAntesAlteracao
        }
	
	 String obterPlaca()
	{
		return this.placa	
	}
	
	 String obterDescricao()
	{
		return this.descricao
	}
	
	 float obterValor()
	{
		return this.valor
	}
	
	 String obterData()
	{
		return this.data
	}
        
         Vector carregarDatasDespesas(Conexao conexao) throws Exception
        {
            ResultSet dadosDespesas
            Vector datasDespesas = null
            dadosDespesas = conexao.executarConsulta("select datahora from despesa_veiculo where veiculo = '"+ this.placa +"' order by datahora desc")
            datasDespesas = new Vector()
            while(dadosDespesas.next())
            {
                datasDespesas.addElement(new Despesa(dadosDespesas.getString("datahora")))
            }
            dadosDespesas.close()        
            return datasDespesas
        }
	
	 void cadastrarDespesa() throws Exception
	{
		String query = "insert into despesa_veiculo (veiculo,datahora,descricao,valor) values "
		query = query + "('"+ this.placa +"', '"+ Calendario.inverterFormato(this.data,"/") +"', '"+ this.descricao +"', "+ this.valor +")"
		Conexao conexao = new Conexao('T')
                boolean existente = false
                
		if (conexao.abrirConexao())
		{
                    ResultSet despesa = conexao.executarConsulta("select * from despesa_veiculo where veiculo = '"+ this.placa +"' and datahora = '"+ Calendario.inverterFormato(this.data,"/") +"' ")
                    if(despesa.next())
                    {
                        existente = true
                        Exception e = new Exception("Já existe uma despesa associada a este veículo na data informada. Não foi possível realizar o cadastro.")
                        throw e
                    }
                    else
                    {
			conexao.executarAtualizacao(query)
                    }
		    conexao.fecharConexao()
		}
                else
                {
                    Exception e = new Exception("Não foi possível realizar uma conexão com o banco de dados.")
                    throw e
                }
	}
        
         void alterarDespesa() throws Exception
        {
            String query = "update despesa_veiculo set veiculo = '"+ this.placa +"',datahora = '"+ Calendario.inverterFormato(this.data,"/") +"',descricao = '"+ this.descricao +"',valor = "+ this.valor +" where veiculo = '"+ this.placaAntesAlteracao +"' and datahora = '"+ Calendario.inverterFormato(this.dataAntesAlteracao,"/") +"' "
            Conexao conexao = new Conexao('T')
            if(conexao.abrirConexao())
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
        
         void excluirDespesa() throws Exception
        {
            String query = "delete from despesa_veiculo where veiculo = '"+ this.placa +"' and datahora = '"+ Calendario.inverterFormato(this.data,"/") +"' "
            Conexao conexao = new Conexao('T')
            if(conexao.abrirConexao())
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
