package br.com.tecnolity.util;

import java.util.Properties;
import java.io.*;

public class Configuracao extends Properties
{
    private static String organizacaoRazaoSocial = "";
    private static String organizacaoNomeFantasia = "";
    private static String organizacaoCnpj = "";
    private static String organizacaoLogradouro = "";
    private static String organizacaoNumero = "";
    private static String organizacaoComplemento = "";
    private static String organizacaoBairro = "";
    private static String organicacaoCidade = "";
    private static String organizacaoEstado = "";
    private static String organizacaoPais = "";
    private static String organizacaoCep = "";
    private static String organizacaoTelefone = "";
    private static String organizacaoFax = "";
    private static String repositorioConsultas = "";
    private static String repositorioRelatorios = "";
    private static String repositorioLogs = "";
    private static String bancoDadosUsuario = "";
    private static String bancoDadosSenha = "";
    private static String bancoDadosDriver = "";
    private static String bancoDadosURL = "";
    private static String bancoDadosBaseDados = "";
    // Propriedades de impressão
    private static int larguraPapel = 0;
    private static int alturaPapel = 0;
    private static int orientacao = 0;
    private static int margemEsquerda = 0;
    private static int margemDireita = 0;
    private static int margemSuperior = 0;
    private static int margemInferior = 0;
    private boolean carregada;
    
    public Configuracao(){}
    
    public void carregarConfiguracao() throws IOException
    {
        FileInputStream entrada = new FileInputStream("config.properties");
        this.load(entrada);
        entrada.close();
        
        this.setOrganizacaoRazaoSocial(this.getProperty("organizacaoRazaoSocial"));
        this.setOrganizacaoNomeFantasia(this.getProperty("organizacaoNomeFantasia"));
        this.setOrganizacaoCnpj(this.getProperty("organizacaoCnpj"));
        this.setOrganizacaoLogradouro(this.getProperty("organizacaoLogradouro"));
        this.setOrganizacaoNumero(this.getProperty("organizacaoNumero"));
        this.setOrganizacaoComplemento(this.getProperty("organizacaoComplemento"));
        this.setOrganizacaoBairro(this.getProperty("organizacaoBairro"));
        this.setOrganizacaoCidade(this.getProperty("organicacaoCidade"));
        this.setOrganizacaoEstado(this.getProperty("organizacaoEstado"));
        this.setOrganizacaoPais(this.getProperty("organizacaoPais"));
        this.setOrganizacaoCep(this.getProperty("organizacaoCep"));
        this.setOrganizacaoTelefone(this.getProperty("organizacaoTelefone"));
        this.setOrganizacaoFax(this.getProperty("organizacaoFax"));
        this.setRepositorioConsultas(this.getProperty("repositorioConsultas"));
        this.setRepositorioRelatorios(this.getProperty("repositorioRelatorios"));
        this.setRepositorioLogs(this.getProperty("repositorioLogs"));
        this.setBancoDadosUsuario(this.getProperty("bancoDadosUsuario"));
        this.setBancoDadosSenha(this.getProperty("bancoDadosSenha"));
        this.setBancoDadosDriver(this.getProperty("bancoDadosDriver"));
        this.setBancoDadosURL(this.getProperty("bancoDadosURL"));
        this.setBancoDadosBaseDados(this.getProperty("bancoDadosBaseDados"));
        try
        {
            this.setLarguraPapel(Integer.parseInt(this.getProperty("larguraPapel")));
            this.setAlturaPapel(Integer.parseInt(this.getProperty("alturaPapel")));
            this.setOrientacao(Integer.parseInt(this.getProperty("orientacao")));
            this.setMargemEsquerda(Integer.parseInt(this.getProperty("margemEsquerda")));
            this.setMargemDireita(Integer.parseInt(this.getProperty("margemDireita")));
            this.setMargemSuperior(Integer.parseInt(this.getProperty("margemSuperior")));
            this.setMargemInferior(Integer.parseInt(this.getProperty("margemInferior")));
        }
        catch(NumberFormatException e)
        {
            
        }
        carregada = true;
    }
    
    public void setOrganizacaoRazaoSocial(String organizacaoRazaoSocial)
    {
        Configuracao.organizacaoRazaoSocial = organizacaoRazaoSocial;
    }
    
    public void setOrganizacaoNomeFantasia(String organizacaoNomeFantasia)
    {
        Configuracao.organizacaoNomeFantasia = organizacaoNomeFantasia;
    }
    
    public void setOrganizacaoCnpj(String organizacaoCnpj)
    {
        Configuracao.organizacaoCnpj = organizacaoCnpj;
    }
    
    public void setOrganizacaoLogradouro(String organizacaoLogradouro)
    {
        Configuracao.organizacaoLogradouro = organizacaoLogradouro;
    }
    
    public void setOrganizacaoNumero(String organizacaoNumero)
    {
        Configuracao.organizacaoNumero = organizacaoNumero;
    }
    
    public void setOrganizacaoComplemento(String organizacaoComplemento)
    {
        Configuracao.organizacaoComplemento = organizacaoComplemento;
    }
    
    public void setOrganizacaoBairro(String organizacaoBairro)
    {
        Configuracao.organizacaoBairro = organizacaoBairro;
    }
    
    public void setOrganizacaoCidade(String organicacaoCidade)
    {
        Configuracao.organicacaoCidade = organicacaoCidade;
    }
    
    public void setOrganizacaoEstado(String organizacaoEstado)
    {
        Configuracao.organizacaoEstado = organizacaoEstado;
    }
    
    public void setOrganizacaoPais(String organizacaoPais)
    {
        Configuracao.organizacaoPais = organizacaoPais;
    }
    
    public void setOrganizacaoCep(String organizacaoCep)
    {
        Configuracao.organizacaoCep = organizacaoCep;
    }
    
    public void setOrganizacaoTelefone(String organizacaoTelefone)
    {
        Configuracao.organizacaoTelefone = organizacaoTelefone;
    }
    
    public void setOrganizacaoFax(String organizacaoFax)
    {
        Configuracao.organizacaoFax = organizacaoFax;
    }
    
    public void setRepositorioConsultas(String repositorioConsultas)
    {
        Configuracao.repositorioConsultas = repositorioConsultas;
    }
    
    public void setRepositorioRelatorios(String repositorioRelatorios)
    {
        Configuracao.repositorioRelatorios = repositorioRelatorios;
    }
    
    public void setRepositorioLogs(String repositorioLogs)
    {
        Configuracao.repositorioLogs = repositorioLogs;
    }
    
    public void setBancoDadosUsuario(String bancoDadosUsuario)
    {
        Configuracao.bancoDadosUsuario = bancoDadosUsuario;
    }
    
    public void setBancoDadosSenha(String bancoDadosSenha)
    {
        Configuracao.bancoDadosSenha = bancoDadosSenha;
    }
    
    public void setBancoDadosDriver(String bancoDadosDriver)
    {
        Configuracao.bancoDadosDriver = bancoDadosDriver;
    }
    
    public void setBancoDadosURL(String bancoDadosURL)
    {
        Configuracao.bancoDadosURL = bancoDadosURL;
    }
    
    public void setBancoDadosBaseDados(String bancoDadosBaseDados)
    {
        Configuracao.bancoDadosBaseDados = bancoDadosBaseDados;
    }
    
    public void setLarguraPapel(int larguraPapel)
    {
        Configuracao.larguraPapel = larguraPapel;
    }
    
    public void setAlturaPapel(int alturaPapel)
    {
        Configuracao.alturaPapel = alturaPapel;
    }
    
    public void setOrientacao(int orientacao)
    {
        Configuracao.orientacao = orientacao;
    }
    
    public void setMargemEsquerda(int margemEsquerda)
    {
        Configuracao.margemEsquerda = margemEsquerda;
    }
    
    public void setMargemDireita(int margemDireita)
    {
        Configuracao.margemDireita = margemDireita;
    }
    
    public void setMargemSuperior(int margemSuperior)
    {
        Configuracao.margemSuperior = margemSuperior;
    }
    
    public void setMargemInferior(int margemInferior)
    {
        Configuracao.margemInferior = margemInferior;
    }
         
    public static String getOrganizacaoRazaoSocial()
    {
        return organizacaoRazaoSocial;
    }
    
    public static String getOrganizacaoNomeFantasia()
    {
        return organizacaoNomeFantasia;
    }
    
    public static String getOrganizacaoCnpj()
    {
        return organizacaoCnpj;
    }
    
    public static String getOrganizacaoLogradouro()
    {
        return organizacaoLogradouro;
    }
    
    public static String getOrganizacaoNumero()
    {
        return organizacaoNumero;
    }
    
    public static String getOrganizacaoComplemento()
    {
        return organizacaoComplemento;
    }
    
    public static String getOrganizacaoBairro()
    {
        return organizacaoBairro;
    }
    
    public static String getOrganizacaoCidade()
    {
        return organicacaoCidade;
    }
    
    public static String getOrganizacaoEstado()
    {
        return organizacaoEstado;
    }
    
    public static String getOrganizacaoPais()
    {
        return organizacaoPais;
    }
    
    public static String getOrganizacaoCep()
    {
        return organizacaoCep;
    }
    
    public static String getOrganizacaoTelefone()
    {
        return organizacaoTelefone;
    }
    
    public static String getOrganizacaoFax()
    {
        return organizacaoFax;
    }
    
    public static String getRepositorioConsultas()
    {
        return repositorioConsultas;
    }
    
    public static String getRepositorioRelatorios()
    {
        return repositorioRelatorios;
    }
    
    public static String getRepositorioLogs()
    {
        return repositorioLogs;
    }
    
    public static String getBancoDadosUsuario()
    {
        return bancoDadosUsuario;
    }
    
    public static String getBancoDadosSenha()
    {
        return bancoDadosSenha;
    }
    
    public static String getBancoDadosDriver()
    {
        return bancoDadosDriver;
    }
    
    public static String getBancoDadosURL()
    {
        return bancoDadosURL;
    }
    
    public static String getBancoDadosBaseDados()
    {
        return bancoDadosBaseDados;
    }
    
    public static int getLarguraPapel()
    {
        return larguraPapel;
    }
    
    public static int getAlturaPapel()
    {
        return alturaPapel;
    }
    
    public static int getLarguraPapelPixel()
    {
        return (int)((larguraPapel/25.4f) * 72);
    }
    
    public static int getAlturaPapelPixel()
    {
        return (int)((alturaPapel/25.4f) * 72);
    }
    
    public static int getOrientacao()
    {
        return orientacao;
    }
    
    public static int getMargemEsquerda()
    {
        return margemEsquerda;
    }
    
    public static int getMargemDireita()
    {
        return margemDireita;
    }
    
    public static int getMargemSuperior()
    {
        return margemSuperior;
    }
    
    public static int getMargemInferior()
    {
        return margemInferior;
    }
    
    public static int getMargemEsquerdaPixel()
    {
        return (int)((margemEsquerda/25.4f) * 72);
    }
    
    public static int getMargemDireitaPixel()
    {
        return (int)((margemDireita/25.4f) * 72);
    }
    
    public static int getMargemSuperiorPixel()
    {
        return (int)((margemSuperior/25.4f) * 72);
    }
    
    public static int getMargemInferiorPixel()
    {
        return (int)((margemInferior/25.4f) * 72);
    }
        
    public boolean isCarregada()
    {
        return this.carregada;
    }
   
    public void salvarConfiguracao()
    {
        try
        {
            this.put("organizacaoRazaoSocial",Configuracao.getOrganizacaoRazaoSocial());
            this.put("organizacaoNomeFantasia",Configuracao.getOrganizacaoNomeFantasia());
            this.put("organizacaoCnpj",Configuracao.getOrganizacaoCnpj());
            this.put("organizacaoLogradouro",Configuracao.getOrganizacaoLogradouro());
            this.put("organizacaoNumero",Configuracao.getOrganizacaoNumero());
            this.put("organizacaoComplemento",Configuracao.getOrganizacaoComplemento());
            this.put("organizacaoBairro",Configuracao.getOrganizacaoBairro());
            this.put("organicacaoCidade",Configuracao.getOrganizacaoCidade());
            this.put("organizacaoEstado",Configuracao.getOrganizacaoEstado());
            this.put("organizacaoPais",Configuracao.getOrganizacaoPais());
            this.put("organizacaoCep",Configuracao.getOrganizacaoCep());
            this.put("organizacaoTelefone",Configuracao.getOrganizacaoTelefone());
            this.put("organizacaoFax",Configuracao.getOrganizacaoFax());
            this.put("repositorioConsultas",Configuracao.getRepositorioConsultas());
            this.put("repositorioRelatorios",Configuracao.getRepositorioRelatorios());
            this.put("repositorioLogs",Configuracao.getRepositorioLogs());
            this.put("bancoDadosUsuario",Configuracao.getBancoDadosUsuario());
            this.put("bancoDadosSenha",Configuracao.getBancoDadosSenha());
            this.put("bancoDadosDriver",Configuracao.getBancoDadosDriver());
            this.put("bancoDadosURL",Configuracao.getBancoDadosURL());
            this.put("bancoDadosBaseDados",Configuracao.getBancoDadosBaseDados());
            this.put("larguraPapel", "" + Configuracao.getLarguraPapel());
            this.put("alturaPapel", "" + Configuracao.getAlturaPapel());
            this.put("orientacao", "" + Configuracao.getOrientacao());
            this.put("margemEsquerda", "" + Configuracao.getMargemEsquerda());
            this.put("margemDireita", "" + Configuracao.getMargemDireita());
            this.put("margemSuperior", "" + Configuracao.getMargemSuperior());
            this.put("margemInferior", "" + Configuracao.getMargemInferior());
            
            FileOutputStream saida = new FileOutputStream("config.properties");
            this.store(saida,"Configurações do Sistema");
            saida.close();
        }
        catch(IOException e)
        {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}