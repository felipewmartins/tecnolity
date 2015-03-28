/**
   * Projeto: 001 - Tecnolity <br>
   * Autor do Código: Kenia Soares <br>
   * Nome do Arquivo: Veiculo.java <br>
   * Linguagem: Java <br>
   *  <br>
   * Requerimentos: Requer máquina virtual java versão 1.3 ou superior. <br>
   *  <br>
   * Objetivo: Classe que representa um veículo no sistema. <br>
   *  <br>
   * Objetivo definido por: Kenia Soares <br>
   * Início da Programação: 11/02/2001 <br>
   * Fim da Programação: <br>
   * Última Versão: 1.0 <br>
*/

package br.com.tecnolity.logistica;

import br.com.tecnolity.util.*;
import java.util.Vector;
import java.sql.*;

public class Veiculo 
{
    //dados de Veiculo
    private String placa;
    private Transportadora transportadora;
    private String chassi;
    private String renavam;
    private String marca;
    private String modelo;
    private String ano;
    private String cor;
    private int numeroEixo;
    private float cubagem;
    private float tara;
    private float pesoBruto;
    private String combustivel;
    private float volumeCombustivel;
    private float mediaConsumo;
        
    public Veiculo(){}
    
    public Veiculo(String placa)
    {
        try
        {
            this.definirPlaca(placa);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public Veiculo(String placa, Conexao conexao)throws Exception
    {
        this.definirPlaca(placa);       
        this.carregarDadosVeiculo(conexao);
    } 
    
    public Veiculo(String placa, Transportadora transportadora, String chassi, String renavam, 
                   String marca, String modelo, String ano, String cor,
                   int numeroEixo, float cubagem, float tara, float pesoBruto,
                   String combustivel, float volumeCombustivel, float mediaConsumo) throws Exception
    {
        this.definirPlaca(placa);       
        this.definirTransportadora(transportadora);
        this.definirChassi(chassi);
        this.definirRenavam(renavam);
        this.definirMarca(marca);
        this.definirModelo(modelo);
        this.definirAno(ano);
        this.definirCor(cor);
        this.definirCombustivel(combustivel);
        
        this.definirNumeroEixo(numeroEixo);
        this.definirCubagem(cubagem);
        this.definirTara(tara);
        this.definirPesoBruto(pesoBruto);        
        this.definirVolumeCombustivel(volumeCombustivel);
        this.definirMediaConsumo(mediaConsumo);
    }

    public void definirPlaca(String placa) throws Exception
    {        
        if(!placa.equals("") && placa.length() <= 8)
            this.placa = placa;
        else
        {
            Exception e = new Exception("A Placa não foi informada corretamente.");
            throw e;	
        }
    }
    
    public void definirTransportadora(Transportadora transportadora) throws Exception
    {
        if(transportadora != null)
            this.transportadora = transportadora;
        else
        {
            Exception e = new Exception("A Transportadora não foi informada.");
            throw e;	
        }
    }
    
    public void definirChassi(String chassi) throws Exception
    {
        if(!chassi.equals("") && chassi.length() <= 10)
            this.chassi = chassi;
        else
        {
            Exception e = new Exception("O Chassi não foi informado corretamente.");
            throw e;	
        }
    }
    
    public void definirRenavam(String renavam) throws Exception
    {
        if(!renavam.equals("") && renavam.length() <= 10)
            this.renavam = renavam;
        else
        {
            Exception e = new Exception("O Renavam não foi informado corretamente.");
            throw e;	
        }
    }
    
    public void definirMarca(String marca) throws Exception
    {
        if(!marca.equals("") && marca.length() <= 50)
            this.marca = marca;
        else
        {
            Exception e = new Exception("A Marca não foi informada corretamente.");
            throw e;	
        }        
    }
    
    public void definirModelo(String modelo) throws Exception
    {
        if(!modelo.equals("") && modelo.length() <= 50)
            this.modelo = modelo;
        else
        {
            Exception e = new Exception("O Modelo não foi informado corretamente.");
            throw e;	
        }
    }
    
    public void definirAno(String ano) throws Exception
    {
        if(ano.length() == 4)
            this.ano = ano; 
        else
        {
            Exception e = new Exception("O Ano não foi informado corretamente.");
            throw e;	
        }
    }
    
    public void definirCor(String cor) throws Exception
    {
        if(ano.length() <= 12)
            this.cor = cor;
        else
        {
            Exception e = new Exception("A Cor não foi informada corretamente.");
            throw e;	
        }
    }
    
    public void definirNumeroEixo(int numeroEixo) throws Exception
    {
        String erro = "";
        if(Float.isNaN(numeroEixo))
                erro = "O Número do Eixo não foi informado corretamente.";

        if(!erro.equals(""))
        {
                Exception e = new Exception(erro);
                throw e;
        }
        else
            this.numeroEixo = numeroEixo;
    }
    
    public void definirCubagem(float cubagem) throws Exception
    {
        String erro = "";
        if(Float.isNaN(cubagem))
                erro = "O Valor da Cubagem não foi informado corretamente.";

        if(!erro.equals(""))
        {
                Exception e = new Exception(erro);
                throw e;
        }
        else
            this.cubagem = cubagem;
    }
    
    public void definirTara(float tara) throws Exception
    {
        String erro = "";
        if(Float.isNaN(tara))
                erro = "O Valor da Tara não foi informado corretamente.";

        if(!erro.equals(""))
        {
                Exception e = new Exception(erro);
                throw e;
        }
        else
            this.tara = tara;
    }
    
    public void definirPesoBruto(float pesoBruto) throws Exception
    {
        String erro = "";
        if(Float.isNaN(pesoBruto))
                erro = "O Peso Bruto não foi informado corretamente.";

        if(!erro.equals(""))
        {
                Exception e = new Exception(erro);
                throw e;
        }
        else
            this.pesoBruto = pesoBruto;
    }
    
    public void definirCombustivel(String combustivel) throws Exception
    {
        if(combustivel.length() <= 20)
            this.combustivel = combustivel;
        else
        {
            Exception e = new Exception("O Combustível não foi informado corretamente.");
            throw e;
        }
    }
    
    public void definirVolumeCombustivel(float volumeCombustivel) throws Exception
    {
        String erro = "";
        if(Float.isNaN(volumeCombustivel))
                erro = "O Volume do Combustível não foi informado corretamente.";

        if(!erro.equals(""))
        {
                Exception e = new Exception(erro);
                throw e;
        }
        else
            this.volumeCombustivel = volumeCombustivel;
    }
    
    public void definirMediaConsumo(float mediaConsumo) throws Exception
    {
        String erro = "";
        if(Float.isNaN(mediaConsumo))
                erro = "A Média de Consumo não foi informada corretamente.";

        if(!erro.equals(""))
        {
                Exception e = new Exception(erro);
                throw e;
        }
        else
            this.mediaConsumo = mediaConsumo;
    }
    
    public String obterPlaca()
    {
        return this.placa;
    }
    
    public Transportadora obterTransportadora()
    {
        return this.transportadora;
    }
    
    public String obterChassi()
    {
        return this.chassi;
    }
    
    public String obterRenavam()
    {
        return this.renavam;
    }
    
    public String obterMarca()
    {
        return this.marca;
    }
    
    public String obterModelo()
    {
        return this.modelo;
    }
    
    public String obterAno()
    {
        return this.ano;
    }
    
    public String obterCor()
    {
        return this.cor;
    }
    
    public int obterNumeroEixo()
    {
        return this.numeroEixo;
    }
    
    public float obterCubagem()
    {
        return this.cubagem;
    }
    
    public float obterTara()
    {
        return this.tara;
    }
    
    public float obterPesoBruto()
    {
        return this.pesoBruto;
    }
    
    public String obterCombustivel()
    {
        return this.combustivel;
    }
    
    public float obterVolumeCombustivel()
    {
        return this.volumeCombustivel;
    }
    
    public float obterMediaConsumo()
    {
        return this.mediaConsumo;
    }
            
    public void carregarDadosVeiculo(Conexao conexao) throws Exception
    {
        ResultSet dadosVeiculo;        
        
        dadosVeiculo = conexao.executarConsulta("select t.codigo as codigo_transportadora, t.transportadora, v.chassi, v.renavam, v.marca, v.modelo, v.ano, v.cor, v.numero_eixo, v.cubagem, v.tara, v.peso_bruto, v.combustivel, v.volume_combustivel, v.media_consumo from transportadora t, veiculo v where v.transportadora = t.codigo and placa = '"+ this.placa +"'");
        
        if(dadosVeiculo.next())
        {
            this.definirTransportadora(new Transportadora(dadosVeiculo.getInt("codigo_transportadora"),dadosVeiculo.getString("transportadora")));
            this.definirChassi(dadosVeiculo.getString("chassi"));
            this.definirRenavam(dadosVeiculo.getString("renavam"));
            this.definirMarca(dadosVeiculo.getString("marca"));
            this.definirModelo(dadosVeiculo.getString("modelo"));
            this.definirAno(dadosVeiculo.getString("ano"));
            this.definirCor(dadosVeiculo.getString("cor"));
            this.definirNumeroEixo(dadosVeiculo.getInt("numero_eixo"));
            this.definirCubagem(dadosVeiculo.getFloat("cubagem"));
            this.definirTara(dadosVeiculo.getFloat("tara"));
            this.definirPesoBruto(dadosVeiculo.getFloat("peso_bruto"));
            this.definirCombustivel(dadosVeiculo.getString("combustivel"));
            this.definirVolumeCombustivel(dadosVeiculo.getFloat("volume_combustivel"));
            this.definirMediaConsumo(dadosVeiculo.getFloat("media_consumo"));
        }
    }
        
    public static Vector carregarVeiculos(Conexao conexao) throws Exception
    {
    	ResultSet dadosVeiculo;
        Vector veiculos = new Vector();
        dadosVeiculo = conexao.executarConsulta("select placa from veiculo order by placa asc");
        veiculos.addElement(null);
        while(dadosVeiculo.next())
        {
            veiculos.addElement(new Veiculo(dadosVeiculo.getString("placa")));
        }
        dadosVeiculo.close();        
        return veiculos;
    }
    
    public void cadastrarDadosVeiculo() throws Exception
    {
        String query = "insert into veiculo (placa,transportadora,chassi,renavam,marca,modelo,ano,cor,numero_eixo,cubagem,tara,peso_bruto,combustivel,volume_combustivel,media_consumo) values ";
        query += "('"+ this.placa +"', "+ this.transportadora.obterCodigo() +", '"+ this.chassi +"', '"+ this.renavam +"', '"+ this.marca +"', '"+ this.modelo +"', '"+ this.ano +"', '"+ this.cor +"', "+ this.numeroEixo +", "+ this.cubagem +", "+ this.tara +", "+ this.pesoBruto +", '"+ this.combustivel +"', "+ this.volumeCombustivel +", "+ this.mediaConsumo +") ";
        Conexao conexao = new Conexao('T');
        boolean existente = false;

        if (conexao.abrirConexao())
        {
            ResultSet veiculo = conexao.executarConsulta("select * from veiculo where placa = '"+ this.placa +"' ");
            if(veiculo.next())
            {
                existente = true;
                Exception e = new Exception("Já existe um veículo com a placa informada. Não foi possível realizar o cadastro.");
                throw e;
            }
            else
            {
                conexao.executarAtualizacao(query);
            }
            conexao.fecharConexao();
        }
        else
        {
            Exception e = new Exception("Não foi possível realizar uma conexão com o banco de dados.");
            throw e;
        }
    }
        
    public void alterarDadosVeiculo() throws Exception
    {
        String query = "update veiculo set transportadora = "+ this.transportadora.obterCodigo() +",chassi = '"+ this.chassi +"',renavam = '"+ this.renavam +"',marca = '"+ this.marca +"',modelo = '"+ this.modelo +"',ano = '"+ this.ano +"',cor = '"+ this.cor +"',numero_eixo = "+ this.numeroEixo +",cubagem = "+ this.cubagem +",tara = "+ this.tara +",peso_bruto = "+ this.pesoBruto +",combustivel = '"+ this.combustivel +"',volume_combustivel = "+ this.volumeCombustivel +",media_consumo = "+ this.mediaConsumo +" where placa =  '"+ this.placa +"' ";        
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
        
    public void excluirVeiculo() throws Exception
    {
        String queryVeiculo = "delete from veiculo where placa = '"+ this.placa +"' ";
        Conexao conexao = new Conexao('T');
        if(conexao.abrirConexao())
        {                        
            conexao.executarAtualizacao(queryVeiculo);
            conexao.fecharConexao();
        }
        else
        {
            Exception e = new Exception("Não foi possível realizar uma conexão com o banco de dados.");
            throw e;
        }
    }
    
    public String toString()
    {
        return this.placa + " " + modelo;
    }
}