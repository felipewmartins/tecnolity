package br.com.tecnolity.util;

import java.util.*;
import java.text.SimpleDateFormat;

public class Calendario
{
    private Calendar calendario = new GregorianCalendar();
    private String strHoje;
    private static int[] numeroDiasMes = {31,28,31,30,31,30,31,31,30,31,30,31};

    public Calendario()	{}

    public String dataHoje(String formato)
    {
        strHoje = (String)new SimpleDateFormat(formato).format(calendario.getTime());
        return strHoje;
    }
	
    public static boolean validarData(String data, String separador)
    {
	int dd, mm, aaaa;
        if(data.indexOf(separador) < 0)
        {
            data = ajustarFormatoDataBanco(data);
        }
        try
        {
            dd   = Integer.parseInt(data.substring(0,data.indexOf(separador)));
            data = data.substring(data.indexOf(separador) + 1);
            mm   = Integer.parseInt(data.substring(0,data.indexOf(separador)));
            data = data.substring(data.indexOf(separador) + 1);
            aaaa = Integer.parseInt(data);
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        catch (StringIndexOutOfBoundsException s)
        {
            return false;
        }
        if(anoBisexto(aaaa))
            numeroDiasMes[1] = 29;
        else
            numeroDiasMes[1] = 28;

        if(aaaa < 0)
        {
            return false;
        }
        else if(mm < 1 || mm > 12)
        {
            return false;
        }
        else if(dd < 1 || dd > numeroDiasMes[mm - 1])
        {
            return false;
        }
        return true;
    }
    
    public static String ajustarFormatoDataBanco(String data)
    {
        data = data.substring(0,10);
        String separador = "-";
        int dd,mm,aaaa;
        try
        {
            aaaa = Integer.parseInt(data.substring(0,data.indexOf(separador)));
            data = data.substring(data.indexOf(separador) + 1);
            mm   = Integer.parseInt(data.substring(0,data.indexOf(separador)));
            data = data.substring(data.indexOf(separador) + 1);
            dd = Integer.parseInt(data);
        }
        catch (NumberFormatException e)
        {
            return "";
        }
        catch (StringIndexOutOfBoundsException s)
        {
            return "";
        }
        data = dd + "/" + mm + "/" + aaaa;
        return data;
    }
    
    /**
     *  Recebe uma data no formato AAAAMMDD e retorna como DD/MM/AAAA.
     */
    public static String formatarAAAAMMDD(String data, String separador)
    {
        String dd, mm, aaaa;
        aaaa = data.substring(0,4);
        mm   = data.substring(4,6);
        dd   = data.substring(6);
        return dd + separador + mm + separador + aaaa;
    }
	
    /** 
            Se o parametro da data maior for maior do que o parametro
            da data menor a função retorna true, senão retornará false.
    */
    public static boolean compararData(String dataMaior, String dataMenor, String separador)
    {
        if(!validarData(dataMaior, separador) || !validarData(dataMenor, separador))
            return false;

        int ddMaior, ddMenor, mmMaior, mmMenor, aaaaMaior, aaaaMenor;

        try
        {
            ddMaior   = Integer.parseInt(dataMaior.substring(0,dataMaior.indexOf(separador)));
            dataMaior = dataMaior.substring(dataMaior.indexOf(separador) + 1);
            mmMaior   = Integer.parseInt(dataMaior.substring(0,dataMaior.indexOf(separador)));
            dataMaior = dataMaior.substring(dataMaior.indexOf(separador) + 1);
            aaaaMaior = Integer.parseInt(dataMaior);

            ddMenor   = Integer.parseInt(dataMenor.substring(0,dataMenor.indexOf(separador)));
            dataMenor = dataMenor.substring(dataMenor.indexOf(separador) + 1);
            mmMenor   = Integer.parseInt(dataMenor.substring(0,dataMenor.indexOf(separador)));
            dataMenor = dataMenor.substring(dataMenor.indexOf(separador) + 1);
            aaaaMenor = Integer.parseInt(dataMenor);
        }
        catch (NumberFormatException e)
        {
            return false;
        }

        if(aaaaMaior < aaaaMenor)
        {
            return false;
        }
        else if (aaaaMaior == aaaaMenor)
        {
            if(mmMaior < mmMenor)
            {
               return false;
            }
            else if(mmMaior == mmMenor)
            {
                if(ddMaior < ddMenor)
                {
                    return false;	
                }
            }
        }
        return true;
    }
	
    public static String inverterFormato(String data, String separador)
    {
        if(!data.equals("") && !separador.equals(""))
        {
            String dd, mm, aaaa;
            dd   = data.substring(0,data.indexOf(separador));
            data = data.substring(data.indexOf(separador) + 1);
            mm   = data.substring(0,data.indexOf(separador));
            data = data.substring(data.indexOf(separador) + 1);
            aaaa = data;
            return mm + separador + dd + separador + aaaa;
        }
        else
            return "";
    }
    
    public static String trocarSeparador(String data,char separadorAtual,char separadorNovo)
    {
        String dataModificada = data;
        
        if(!dataModificada.equals(""))
        {
            dataModificada = dataModificada.replace(separadorAtual,separadorNovo);
        }
        return dataModificada;
    }
    
    public static String removerSeparador(String data,char separador)
    {
        String dataModificada = data;
        
        if(!dataModificada.equals(""))
        {
            dataModificada = dataModificada.replace(separador,'\u0000');
        }
        return dataModificada;
    }
	
    public static boolean anoBisexto(int ano)
    {
        if((ano % 4) != 0)
            return false;
        return true;
    }
}