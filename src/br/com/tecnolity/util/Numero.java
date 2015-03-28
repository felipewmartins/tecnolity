package br.com.tecnolity.util;


public abstract class Numero
{
    public Numero(){}	

    public static String inverterSeparador(String numero)
    {
        if(!numero.equals(""))
        {
            if(numero.indexOf(".") >= 0)
            {
                return numero.replace('.',',');
            }
            else if(numero.indexOf(",") >= 0)
            {
                return numero.replace(',','.');
            }
        }
        else
            numero = "0";
        return numero;
    }
    
    public static String inverterSeparador(float numeroFloat)
    {
        String numero = Float.toString(numeroFloat);
        if(!numero.equals(""))
        {
            if(numero.indexOf(".") >= 0)
            {
                return numero.replace('.',',');
            }
            else if(numero.indexOf(",") >= 0)
            {
                return numero.replace(',','.');
            }
        }
        else
            numero = "0";
        return numero;
    }

    public static String separarInteiroDecimal(String numero, int numCasasDecimais)
    {
        String parteInteira = numero.substring(0,numero.length() - (numCasasDecimais - 1));
        String parteDecimal = numero.substring(numero.length() - (numCasasDecimais - 1));
        return parteInteira + "." + parteDecimal;
    }
    
    public static String formatarValorMoeda(float valor, String moeda)
    {
        String strValor = "" + valor + "00";
        int posSeparador = strValor.indexOf(".");
        String parteInteira = strValor.substring(0,posSeparador);
        String parteDecimal = strValor.substring(posSeparador + 1,posSeparador + 3);
        return moeda + " " + parteInteira + "," + parteDecimal;
    }
    
    public static String formatarValorNumerico(float valor, int numCasasDecimais, String separador)
    {
        String zeros = "";
        for(int i = 0;i < numCasasDecimais;i++)
        {
            zeros += "0";
        }
        String strValor = "" + valor + zeros;
        int posSeparador = strValor.indexOf(".");
        String parteInteira = strValor.substring(0,posSeparador);
        strValor = strValor.substring(posSeparador + 1);
        String parteDecimal = strValor.substring(0,numCasasDecimais);
        return parteInteira + separador + parteDecimal;
    }
}