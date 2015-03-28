package br.com.tecnolity.util;

/**
   * Projeto: 001 - Tecnolity <br>
   * Autor do C�digo: Hildeberto Mendon�a Filho <br>
   * Nome do Arquivo: Endereco.java <br>
   * Linguagem: Java <br>
   *  <br>
   * Requerimentos: Requer m�quina virtual java vers�o 1.3 ou superior. <br>
   *  <br>
   * Objetivo: Manipula informa��es de endere�amento. <br>
   *  <br>
   * Objetivo definido por: Hildeberto Mendon�a <br>
   * In�cio da Programa��o: 20/02/2002 <br>
   * Fim da Programa��o: <br>
   * �ltima Vers�o: 1.0 <br>
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