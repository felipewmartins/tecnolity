package org.esmerilprogramming.tecnolity.producao;

import org.esmerilprogramming.tecnolity.util.*;
import java.util.*;
import java.sql.*;

/**
   * Projeto: 001 - Tecnolity <br>
   * Autor do Código: Hildeberto Mendonça <br>
   * Nome do Arquivo: Matriz.java <br>
   * Linguagem: Java <br>
   *  <br>
   * Requerimentos: Requer máquina virtual java versão 1.3 ou superior. <br>
   *  <br>
   * Objetivo: Classe que representa a matriz do produto. <br>
   *  <br>
   * Objetivo definido por: Hildeberto Mendonça <br>
   * Início da Programação: 19/02/2002 <br>
   * Fim da Programação: <br>
   * Última Versão: 1.0 <br>
*/

public class Matriz 
{
    private String referencia, referenciaOriginal;
    private int numeroSola, numeroSolaOriginal;
    private int quantidade;
    private float dureza;
    private float densidade;
    private float peso;
    private float volume;
    private float tempoInjecao;
    private int tempoForma;
    private String modificacoes;
    private boolean mudouChave = false;
    
    public Matriz() {}
    
    public Matriz(String referencia, int numeroSola) throws Exception
    {
        this.definirReferencia(referencia);
        this.definirNumeroSola(numeroSola);
    }
    
    public Matriz(String referencia, int numeroSola, int quantidade, float dureza, float densidade, float peso, float volume, float tempoInjecao, int tempoForma, String modificacoes) throws Exception
    {
        this.definirReferencia(referencia);
        this.definirNumeroSola(numeroSola);
        this.definirQuantidade(quantidade);
        this.definirDureza(dureza);
        this.definirDensidade(densidade);
        this.definirPeso(peso);
        this.definirVolume(volume);
        this.definirTempoInjecao(tempoInjecao);
        this.definirTempoForma(tempoForma);
        this.definirModificacoes(modificacoes);
    }
    
    public Matriz(String referencia, int numeroSola, int quantidade, int tempoForma, float tempoInjecao, float dureza, float densidade, float peso, float volume) throws Exception
    {
        this.definirReferencia(referencia);
        this.definirNumeroSola(numeroSola);
        this.definirQuantidade(quantidade);
        this.definirTempoForma(tempoForma);
        this.definirTempoInjecao(tempoInjecao);
        this.definirDureza(dureza);
        this.definirDensidade(densidade);
        this.definirPeso(peso);
        this.definirVolume(volume);
    }
    
    public Matriz(String referencia, int numeroSola, float dureza, float densidade, float peso, float volume) throws Exception
    {
        this.definirReferencia(referencia);
        this.definirNumeroSola(numeroSola);
        this.definirDureza(dureza);
        this.definirDensidade(densidade);
        this.definirPeso(peso);
        this.definirVolume(volume);
    }
    
    public void definirReferencia(String referencia) throws Exception
    {
        if(referencia.equals("") || referencia == null)
        {
            Exception e = new Exception("A referência não foi informada.");
            throw e;
        }            
        this.referencia = referencia.trim();
    }
    
    private void definirReferenciaOriginal(String referencia) throws Exception
    {
        if(referencia.equals("") || referencia == null)
        {
            Exception e = new Exception("A referência não foi informada.");
            throw e;
        }            
        this.referenciaOriginal = referencia.trim();
    }
        
    public void definirNumeroSola(int numeroSola) throws Exception
    {
        if(numeroSola <= 0)
        {
            Exception e = new Exception("Número da sola inválida.");
            throw e;
        }
        this.numeroSola = numeroSola;
    }
           
    public void definirNumeroSolaOriginal(int numeroSola) throws Exception
    {
        if(numeroSola <= 0)
        {
            Exception e = new Exception("Número da sola inválida.");
            throw e;
        }
        this.numeroSolaOriginal = numeroSola;
    }
    
    public void definirQuantidade(int quantidade) throws Exception
    {
        if(quantidade <= 0)
        {
            Exception e = new Exception("Quantidade informada inválida.");
            throw e;
        }
        this.quantidade = quantidade;
    }
    
    public void definirDureza(float dureza)
    {
        this.dureza = dureza;
    }
    
    public void definirDensidade(float densidade)
    {
        this.densidade = densidade;
    }
    
    public void definirPeso(float peso)
    {
        this.peso = peso;
    }
    
    public void definirVolume(float volume)
    {
        this.volume = volume;
    }
    
    public void definirTempoInjecao(float tempoInjecao)
    {
        this.tempoInjecao = tempoInjecao;
    }
    
    public void definirTempoForma(int tempoForma)
    {
        this.tempoForma = tempoForma;
    }
    
    public void definirModificacoes(String modificacoes)
    {
        this.modificacoes = modificacoes.trim();
    }
    
    public String obterReferencia()
    {
    	return this.referencia;	
    }
    
    public String obterReferenciaOriginal()
    {
    	return this.referenciaOriginal;	
    }
        
    public int obterNumeroSola()
    {
        return this.numeroSola;
    }
    
    public int obterNumeroSolaOriginal()
    {
        return this.numeroSolaOriginal;
    }
    
    public int obterQuantidade()
    {
        return this.quantidade;
    }
    public float obterDureza()
    {
        return this.dureza;
    }
    
    public float obterDensidade()
    {
        return this.densidade;
    }
    
    public float obterPeso()
    {
        return this.peso;
    }
    
    public float obterVolume()
    {
        return this.volume;
    }
    
    public float obterTempoInjecao()
    {
        return this.tempoInjecao;
    }
    
    public int obterTempoForma()
    {
        return this.tempoForma;
    }
    
    public String obterModificacoes()
    {
        return this.modificacoes;
    }
    
    public String obterDescricao()
    {
        String descricao = "";
        descricao = this.referencia + " - " + this.numeroSola;
        if(this.dureza > 0.0f || this.densidade > 0.0f || this.peso > 0.0f || this.volume > 0.0f)
        {
            descricao += "(";
        }
        if(this.dureza > 0.0f)
        {
            descricao += "Du:" + this.dureza;
        }
        if(this.densidade > 0.0f)
        {
            descricao += "/De:" + this.densidade;
        }
        if(this.peso > 0.0f)
        {
            descricao += "/Pe:" + this.peso;
        }
        if(this.volume > 0.0f)
        {
            descricao += "/Vo:" + this.volume;
        }
        if(this.dureza > 0.0f || this.densidade > 0.0f || this.peso > 0.0f || this.volume > 0.0f)
        {
            descricao += ")";
        }
        return descricao;
    }
    
    public Vector carregarMatrizes(Conexao conexao) throws Exception
    {
        Vector matrizes = new Vector();
        matrizes.addElement(null);
        ResultSet dadosMatriz;
        
        dadosMatriz = conexao.executarConsulta("select referencia,numero_sola,dureza,densidade,peso,volume from matriz_modelo order by mm.referencia asc");
        while (dadosMatriz.next())
        {
            matrizes.addElement(new Matriz(dadosMatriz.getString("referencia"),dadosMatriz.getInt("numero_sola"),dadosMatriz.getFloat("dureza"),dadosMatriz.getFloat("densidade"),dadosMatriz.getFloat("peso"),dadosMatriz.getFloat("volume")));
        }
        
        dadosMatriz.close();
        return matrizes;
    }
    
    public Vector carregarMatrizes(Produto produto,Conexao conexao) throws Exception
    {
        Vector matrizes = new Vector();
        matrizes.addElement(null);
        ResultSet dadosMatriz;
        
        dadosMatriz = conexao.executarConsulta("select distinct mm.referencia, mm.numero_sola, dureza, densidade, peso, volume from matriz_modelo mm,quantidade_materia_prima qmp, modelo m where mm.referencia = qmp.referencia and mm.numero_sola = qmp.numero_sola and qmp.produto in (select codigo from modelo where codigo = "+ produto.obterCodigo() +")");
        while (dadosMatriz.next())
        {
            matrizes.addElement(new Matriz(dadosMatriz.getString("referencia"),dadosMatriz.getInt("numero_sola"),dadosMatriz.getFloat("dureza"),dadosMatriz.getFloat("densidade"),dadosMatriz.getFloat("peso"),dadosMatriz.getFloat("volume")));
        }
        
        dadosMatriz.close();
        return matrizes;
    }
    
    public void cadastrarMatriz() throws Exception
    {
        Conexao conexao = new Conexao('T');
        if(conexao.abrirConexao())
        {
            ResultSet dadosMatriz = conexao.executarConsulta("select * from matriz_modelo where referencia = '"+ this.referencia +"' and numero_sola = " + this.numeroSola);
            if(dadosMatriz.next())
            {
                Exception e = new Exception("Já existe uma matriz cadastrada com a referência e o número de sola informados.");
                throw e;
            }
            dadosMatriz.close();
            conexao.executarAtualizacao("insert into matriz_modelo (referencia,numero_sola,quantidade,dureza,densidade,peso,volume,tempo_injecao,modificacoes,tempo_forma) " +
                                        "values ('"+ this.referencia +"',"+ this.numeroSola +","+ this.quantidade +","+ this.dureza +","+ this.densidade +","+ this.peso +","+ this.volume +","+ this.tempoInjecao +",'"+ this.modificacoes +"',"+ this.tempoForma +")");
            conexao.fecharConexao();
        }
        else
        {
            Exception e = new Exception("Não possível cadastrar a Matriz.");
            throw e;
        }
    }
    
    public void alterarMatriz() throws Exception
    {
        Conexao conexao = new Conexao('T');
        String query = "";
        boolean mudouChave = true;
        if(conexao.abrirConexao())
        {
            query = "select referencia,numero_sola from matriz_modelo where referencia = '"+ this.obterReferencia() +"' and numero_sola = "+ this.obterNumeroSola();
            ResultSet dadosMatriz = conexao.executarConsulta(query);
            if(this.referencia.equals(this.referenciaOriginal) && (this.numeroSola == this.numeroSolaOriginal))
            {
                mudouChave = false;
            }
                        
            if(dadosMatriz.next() && mudouChave)
            {
                Exception e = new Exception("Já existe uma matriz com a referência e número de sola informados.");
                throw e;
            }
            else
            {
                query = "update matriz_modelo set referencia = '"+ this.obterReferencia() +"',numero_sola = "+ this.obterNumeroSola() +",quantidade = "+ this.obterQuantidade() +",dureza = "+ this.obterDureza() +",densidade = "+ this.obterDensidade() +",peso = "+ this.obterPeso() +",volume = "+ this.obterVolume() +",tempo_injecao = "+ this.obterTempoInjecao() +",modificacoes = '"+ this.obterModificacoes() +"',tempo_forma = "+ this.obterTempoForma() +" where referencia = '"+ this.obterReferenciaOriginal() +"' and numero_sola = "+ this.obterNumeroSolaOriginal();
                conexao.executarAtualizacao(query);
            }
            dadosMatriz.close();
            conexao.fecharConexao();
        }
        else
        {
            Exception e = new Exception("Não foi possível realizar uma conexão com o banco de dados.");
            throw e;
        }
    }
    
    public void carregarMatriz(Conexao conexao) throws Exception
    {
        ResultSet dadosMatriz = conexao.executarConsulta("select * from matriz_modelo where referencia = '"+ this.referencia +"' and numero_sola = " + this.numeroSola);
        if(dadosMatriz.next())
        {
            definirQuantidade(dadosMatriz.getInt("quantidade"));
            definirDureza(dadosMatriz.getFloat("dureza"));
            definirDensidade(dadosMatriz.getFloat("densidade"));
            definirPeso(dadosMatriz.getFloat("peso"));
            definirVolume(dadosMatriz.getFloat("volume"));
            definirTempoInjecao(dadosMatriz.getFloat("tempo_injecao"));
            definirTempoForma(dadosMatriz.getInt("tempo_forma"));
            definirModificacoes(dadosMatriz.getString("modificacoes"));
            definirReferenciaOriginal(this.referencia);
            definirNumeroSolaOriginal(this.numeroSola);
        }
        dadosMatriz.close();
    }
    
    public void excluirMatriz() throws Exception
    {
        Conexao conexao = new Conexao('T');
        if(conexao.abrirConexao())
        {
            conexao.executarAtualizacao("delete from matriz_modelo where referencia = '"+ this.referencia +"' and numero_sola = "+ this.numeroSola);
            conexao.fecharConexao();
        }
        else
        {
            Exception e = new Exception("Não possível excluir a Matriz. Ela precisa ser desassociada dos produtos antes de ser excluída.");
            throw e;
        }
    }
}