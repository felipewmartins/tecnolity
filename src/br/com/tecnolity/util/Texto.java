package br.com.tecnolity.util;

import java.util.StringTokenizer;

public class Texto 
{
    public Texto() {}

    public static String obterStringTamanhoFixo(String texto,int tamanho)
    {
        if(texto.length() > tamanho)
        {
            return texto.substring(0,tamanho);
        }
        else if(texto.length() < tamanho)
        {
            String espacosBranco = "";
            for(int i = 0;i < (tamanho - texto.length());i++)
            {
                espacosBranco += " ";
            }
            return texto + espacosBranco;
        }
        else
            return texto;
    }
    
    public static String obterNumeroTamanhoFixo(String numero, int tamanho, String preenchimento)
    {
        if(numero.length() > tamanho)
        {
            return numero.substring(numero.length() - tamanho - 1,numero.length());
        }
        else if(numero.length() < tamanho)
        {
            String zeros = "";
            for(int i = 0;i < (tamanho - numero.length());i++)
            {
                zeros += preenchimento;
            }
            return zeros + numero;
        }
        else
            return numero;
    }
    
    public static String[] obterTextoAlinhado(String texto, int largura)
    {
    	String[] linhas;
    	if(texto == null)
    	{
    		linhas = new String[1];
    		linhas[0] = "";
    	}
    	else if(texto.length() > largura)
    	{
    		linhas = new String[((texto.length()%largura) == 0?texto.length()/largura:texto.length()/largura + 1)];
	    	String token = "";
	    	int numeroLinha = 0;
	    	StringTokenizer st = new StringTokenizer(texto);
	    	String linha = "";
	    	String espaco;
	    	
	    	while(st.hasMoreTokens())
	    	{
	    		token = st.nextToken();
	    		
	    		if(linha.length() == 0)
	    			espaco = "";
	    		else
	    			espaco = " ";
	    		if((linha.length() + espaco.length() + token.length()) == largura)
	    		{
	    			linhas[numeroLinha++] = linha;
	    			linha = token;
	    		}
	    		else if((linha.length() + espaco.length() + token.length()) < largura)
	    		{
	    			if(numeroLinha == linhas.length - 1)
	    			{
	    				linha += espaco + token;
	    				linhas[numeroLinha] = linha;
	    			}
	    			else
	    				linha += espaco + token;
	    		}
	    		else if((linha.length() + espaco.length() + token.length()) > largura)
	    		{
	    			linhas[numeroLinha++] = linha;
	    			linha = token;
	    		}
	    	}
    	}
    	else
    	{
    		linhas = new String[1];
    		linhas[0] = texto;
    	}
    	return linhas;
    }
}
