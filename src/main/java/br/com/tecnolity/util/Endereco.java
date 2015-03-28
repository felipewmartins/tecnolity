package br.com.tecnolity.util;

/**
   * Projeto: 001 - Tecnolity <br>
   * Autor do Código: Hildeberto Mendonça Filho <br>
   * Nome do Arquivo: Endereco.java <br>
   * Linguagem: Java <br>
   *  <br>
   * Requerimentos: Requer máquina virtual java versão 1.3 ou superior. <br>
   *  <br>
   * Objetivo: Manipula informações de endereçamento. <br>
   *  <br>
   * Objetivo definido por: Hildeberto Mendonça <br>
   * Início da Programação: 20/02/2002 <br>
   * Fim da Programação: <br>
   * Última Versão: 1.0 <br>
*/

public class Endereco 
{
    public final String VIRGULA = ",";
    public final String PONTO = ".";
    
    public Endereco() {}

    public static String formatarEndereco(String logradouro, String complemento, String bairro, String cidade, String estado, String pais, String cep)
    {
        String enderecoFormatado = "";
        
        if(!logradouro.equals(""))
        {
            enderecoFormatado = logradouro;
        }
        if(!complemento.equals(""))
        {
            enderecoFormatado += ", ";
            enderecoFormatado += complemento;
        }
        if(!bairro.equals(""))
        {
            enderecoFormatado += ", ";
            enderecoFormatado += bairro;
        }
        if(!cidade.equals(""))
        {
            enderecoFormatado += ", ";
            enderecoFormatado += cidade;
        }
        if(estado != null)
        {
            if(!estado.equals(""))
            {
                enderecoFormatado += ", ";
                enderecoFormatado += estado;
            }
        }
        if(pais != null)
        {
            if(!pais.equals(""))
            {
                enderecoFormatado += " - ";
                enderecoFormatado += pais;
            }
        }
        if(!cep.equals(""))
        {
            enderecoFormatado += ". CEP:";
            enderecoFormatado += formatarCep(cep);
        }
        return enderecoFormatado;
    }
    
    public static String formatarCep(String cep)
    {
        String cepFormatado = "";
        if(!cep.equals(""))
        {
            cepFormatado = cep.substring(0,2);
            cepFormatado += ".";
            cepFormatado += cep.substring(2,5);
            cepFormatado += "-";
            cepFormatado += cep.substring(5);
        }
        return cepFormatado;
    }
}