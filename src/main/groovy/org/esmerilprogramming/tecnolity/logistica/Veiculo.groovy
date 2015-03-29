package org.esmerilprogramming.tecnolity.logistica

import org.esmerilprogramming.tecnolity.util.*
import java.util.Vector
import java.sql.*

 class Veiculo 
{
    //dados de Veiculo
    private String placa
    private Transportadora transportadora
    private String chassi
    private String renavam
    private String marca
    private String modelo
    private String ano
    private String cor
    private int numeroEixo
    private float cubagem
    private float tara
    private float pesoBruto
    private String combustivel
    private float volumeCombustivel
    private float mediaConsumo
        
     Veiculo(){}
    
     Veiculo(String placa)
    {
        try
        {
            this.definirPlaca(placa)
        }
        catch(Exception e)
        {
            e.printStackTrace()
        }
    }
    
     Veiculo(String placa, Conexao conexao)throws Exception
    {
        this.definirPlaca(placa)       
        this.carregarDadosVeiculo(conexao)
    } 
    
     Veiculo(String placa, Transportadora transportadora, String chassi, String renavam, 
                   String marca, String modelo, String ano, String cor,
                   int numeroEixo, float cubagem, float tara, float pesoBruto,
                   String combustivel, float volumeCombustivel, float mediaConsumo) throws Exception
    {
        this.definirPlaca(placa)       
        this.definirTransportadora(transportadora)
        this.definirChassi(chassi)
        this.definirRenavam(renavam)
        this.definirMarca(marca)
        this.definirModelo(modelo)
        this.definirAno(ano)
        this.definirCor(cor)
        this.definirCombustivel(combustivel)
        
        this.definirNumeroEixo(numeroEixo)
        this.definirCubagem(cubagem)
        this.definirTara(tara)
        this.definirPesoBruto(pesoBruto)        
        this.definirVolumeCombustivel(volumeCombustivel)
        this.definirMediaConsumo(mediaConsumo)
    }

     void definirPlaca(String placa) throws Exception
    {        
        if(!placa.equals("") && placa.length() <= 8)
            this.placa = placa
        else
        {
            Exception e = new Exception("A Placa não foi informada corretamente.")
            throw e	
        }
    }
    
     void definirTransportadora(Transportadora transportadora) throws Exception
    {
        if(transportadora != null)
            this.transportadora = transportadora
        else
        {
            Exception e = new Exception("A Transportadora não foi informada.")
            throw e	
        }
    }
    
     void definirChassi(String chassi) throws Exception
    {
        if(!chassi.equals("") && chassi.length() <= 10)
            this.chassi = chassi
        else
        {
            Exception e = new Exception("O Chassi não foi informado corretamente.")
            throw e	
        }
    }
    
     void definirRenavam(String renavam) throws Exception
    {
        if(!renavam.equals("") && renavam.length() <= 10)
            this.renavam = renavam
        else
        {
            Exception e = new Exception("O Renavam não foi informado corretamente.")
            throw e	
        }
    }
    
     void definirMarca(String marca) throws Exception
    {
        if(!marca.equals("") && marca.length() <= 50)
            this.marca = marca
        else
        {
            Exception e = new Exception("A Marca não foi informada corretamente.")
            throw e	
        }        
    }
    
     void definirModelo(String modelo) throws Exception
    {
        if(!modelo.equals("") && modelo.length() <= 50)
            this.modelo = modelo
        else
        {
            Exception e = new Exception("O Modelo não foi informado corretamente.")
            throw e	
        }
    }
    
     void definirAno(String ano) throws Exception
    {
        if(ano.length() == 4)
            this.ano = ano 
        else
        {
            Exception e = new Exception("O Ano não foi informado corretamente.")
            throw e	
        }
    }
    
     void definirCor(String cor) throws Exception
    {
        if(ano.length() <= 12)
            this.cor = cor
        else
        {
            Exception e = new Exception("A Cor não foi informada corretamente.")
            throw e	
        }
    }
    
     void definirNumeroEixo(int numeroEixo) throws Exception
    {
        String erro = ""
        if(Float.isNaN(numeroEixo))
                erro = "O Número do Eixo não foi informado corretamente."

        if(!erro.equals(""))
        {
                Exception e = new Exception(erro)
                throw e
        }
        else
            this.numeroEixo = numeroEixo
    }
    
     void definirCubagem(float cubagem) throws Exception
    {
        String erro = ""
        if(Float.isNaN(cubagem))
                erro = "O Valor da Cubagem não foi informado corretamente."

        if(!erro.equals(""))
        {
                Exception e = new Exception(erro)
                throw e
        }
        else
            this.cubagem = cubagem
    }
    
     void definirTara(float tara) throws Exception
    {
        String erro = ""
        if(Float.isNaN(tara))
                erro = "O Valor da Tara não foi informado corretamente."

        if(!erro.equals(""))
        {
                Exception e = new Exception(erro)
                throw e
        }
        else
            this.tara = tara
    }
    
     void definirPesoBruto(float pesoBruto) throws Exception
    {
        String erro = ""
        if(Float.isNaN(pesoBruto))
                erro = "O Peso Bruto não foi informado corretamente."

        if(!erro.equals(""))
        {
                Exception e = new Exception(erro)
                throw e
        }
        else
            this.pesoBruto = pesoBruto
    }
    
     void definirCombustivel(String combustivel) throws Exception
    {
        if(combustivel.length() <= 20)
            this.combustivel = combustivel
        else
        {
            Exception e = new Exception("O Combustível não foi informado corretamente.")
            throw e
        }
    }
    
     void definirVolumeCombustivel(float volumeCombustivel) throws Exception
    {
        String erro = ""
        if(Float.isNaN(volumeCombustivel))
                erro = "O Volume do Combustível não foi informado corretamente."

        if(!erro.equals(""))
        {
                Exception e = new Exception(erro)
                throw e
        }
        else
            this.volumeCombustivel = volumeCombustivel
    }
    
     void definirMediaConsumo(float mediaConsumo) throws Exception
    {
        String erro = ""
        if(Float.isNaN(mediaConsumo))
                erro = "A Média de Consumo não foi informada corretamente."

        if(!erro.equals(""))
        {
                Exception e = new Exception(erro)
                throw e
        }
        else
            this.mediaConsumo = mediaConsumo
    }
    
     String obterPlaca()
    {
        return this.placa
    }
    
     Transportadora obterTransportadora()
    {
        return this.transportadora
    }
    
     String obterChassi()
    {
        return this.chassi
    }
    
     String obterRenavam()
    {
        return this.renavam
    }
    
     String obterMarca()
    {
        return this.marca
    }
    
     String obterModelo()
    {
        return this.modelo
    }
    
     String obterAno()
    {
        return this.ano
    }
    
     String obterCor()
    {
        return this.cor
    }
    
     int obterNumeroEixo()
    {
        return this.numeroEixo
    }
    
     float obterCubagem()
    {
        return this.cubagem
    }
    
     float obterTara()
    {
        return this.tara
    }
    
     float obterPesoBruto()
    {
        return this.pesoBruto
    }
    
     String obterCombustivel()
    {
        return this.combustivel
    }
    
     float obterVolumeCombustivel()
    {
        return this.volumeCombustivel
    }
    
     float obterMediaConsumo()
    {
        return this.mediaConsumo
    }
            
     void carregarDadosVeiculo(Conexao conexao) throws Exception
    {
        ResultSet dadosVeiculo        
        
        dadosVeiculo = conexao.executarConsulta("select t.codigo as codigo_transportadora, t.transportadora, v.chassi, v.renavam, v.marca, v.modelo, v.ano, v.cor, v.numero_eixo, v.cubagem, v.tara, v.peso_bruto, v.combustivel, v.volume_combustivel, v.media_consumo from transportadora t, veiculo v where v.transportadora = t.codigo and placa = '"+ this.placa +"'")
        
        if(dadosVeiculo.next())
        {
            this.definirTransportadora(new Transportadora(dadosVeiculo.getInt("codigo_transportadora"),dadosVeiculo.getString("transportadora")))
            this.definirChassi(dadosVeiculo.getString("chassi"))
            this.definirRenavam(dadosVeiculo.getString("renavam"))
            this.definirMarca(dadosVeiculo.getString("marca"))
            this.definirModelo(dadosVeiculo.getString("modelo"))
            this.definirAno(dadosVeiculo.getString("ano"))
            this.definirCor(dadosVeiculo.getString("cor"))
            this.definirNumeroEixo(dadosVeiculo.getInt("numero_eixo"))
            this.definirCubagem(dadosVeiculo.getFloat("cubagem"))
            this.definirTara(dadosVeiculo.getFloat("tara"))
            this.definirPesoBruto(dadosVeiculo.getFloat("peso_bruto"))
            this.definirCombustivel(dadosVeiculo.getString("combustivel"))
            this.definirVolumeCombustivel(dadosVeiculo.getFloat("volume_combustivel"))
            this.definirMediaConsumo(dadosVeiculo.getFloat("media_consumo"))
        }
    }
        
     static Vector carregarVeiculos(Conexao conexao) throws Exception
    {
    	ResultSet dadosVeiculo
        Vector veiculos = new Vector()
        dadosVeiculo = conexao.executarConsulta("select placa from veiculo order by placa asc")
        veiculos.addElement(null)
        while(dadosVeiculo.next())
        {
            veiculos.addElement(new Veiculo(dadosVeiculo.getString("placa")))
        }
        dadosVeiculo.close()        
        return veiculos
    }
    
     void cadastrarDadosVeiculo() throws Exception
    {
        String query = "insert into veiculo (placa,transportadora,chassi,renavam,marca,modelo,ano,cor,numero_eixo,cubagem,tara,peso_bruto,combustivel,volume_combustivel,media_consumo) values "
        query += "('"+ this.placa +"', "+ this.transportadora.obterCodigo() +", '"+ this.chassi +"', '"+ this.renavam +"', '"+ this.marca +"', '"+ this.modelo +"', '"+ this.ano +"', '"+ this.cor +"', "+ this.numeroEixo +", "+ this.cubagem +", "+ this.tara +", "+ this.pesoBruto +", '"+ this.combustivel +"', "+ this.volumeCombustivel +", "+ this.mediaConsumo +") "
        Conexao conexao = new Conexao('T')
        boolean existente = false

        if (conexao.abrirConexao())
        {
            ResultSet veiculo = conexao.executarConsulta("select * from veiculo where placa = '"+ this.placa +"' ")
            if(veiculo.next())
            {
                existente = true
                Exception e = new Exception("Já existe um veículo com a placa informada. Não foi possível realizar o cadastro.")
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
        
     void alterarDadosVeiculo() throws Exception
    {
        String query = "update veiculo set transportadora = "+ this.transportadora.obterCodigo() +",chassi = '"+ this.chassi +"',renavam = '"+ this.renavam +"',marca = '"+ this.marca +"',modelo = '"+ this.modelo +"',ano = '"+ this.ano +"',cor = '"+ this.cor +"',numero_eixo = "+ this.numeroEixo +",cubagem = "+ this.cubagem +",tara = "+ this.tara +",peso_bruto = "+ this.pesoBruto +",combustivel = '"+ this.combustivel +"',volume_combustivel = "+ this.volumeCombustivel +",media_consumo = "+ this.mediaConsumo +" where placa =  '"+ this.placa +"' "        
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
        
     void excluirVeiculo() throws Exception
    {
        String queryVeiculo = "delete from veiculo where placa = '"+ this.placa +"' "
        Conexao conexao = new Conexao('T')
        if(conexao.abrirConexao())
        {                        
            conexao.executarAtualizacao(queryVeiculo)
            conexao.fecharConexao()
        }
        else
        {
            Exception e = new Exception("Não foi possível realizar uma conexão com o banco de dados.")
            throw e
        }
    }
    
     String toString()
    {
        return this.placa + " " + modelo
    }
}
