package org.esmerilprogramming.tecnolity.util

import java.util.Properties
import java.io.*

class Configuracao extends Properties
{
  private static String organizacaoRazaoSocial = ""
  private static String organizacaoNomeFantasia = ""
  private static String organizacaoCnpj = ""
  private static String organizacaoLogradouro = ""
  private static String organizacaoNumero = ""
  private static String organizacaoComplemento = ""
  private static String organizacaoBairro = ""
  private static String organicacaoCidade = ""
  private static String organizacaoEstado = ""
  private static String organizacaoPais = ""
  private static String organizacaoCep = ""
  private static String organizacaoTelefone = ""
  private static String organizacaoFax = ""
  private static String repositorioConsultas = ""
  private static String repositorioRelatorios = ""
  private static String repositorioLogs = ""
  private static String bancoDadosUsuario = ""
  private static String bancoDadosSenha = ""
  private static String bancoDadosDriver = ""
  private static String bancoDadosURL = ""
  private static String bancoDadosBaseDados = ""
  // Propriedades de impressão
  private static int larguraPapel = 0
  private static int alturaPapel = 0
  private static int orientacao = 0
  private static int margemEsquerda = 0
  private static int margemDireita = 0
  private static int margemSuperior = 0
  private static int margemInferior = 0
  private boolean carregada

   Configuracao(){}

   void carregarConfiguracao() throws IOException
  {
    FileInputStream entrada = new FileInputStream("config.properties")
    this.load(entrada)
    entrada.close()

    this.setOrganizacaoRazaoSocial(this.getProperty("organizacaoRazaoSocial"))
    this.setOrganizacaoNomeFantasia(this.getProperty("organizacaoNomeFantasia"))
    this.setOrganizacaoCnpj(this.getProperty("organizacaoCnpj"))
    this.setOrganizacaoLogradouro(this.getProperty("organizacaoLogradouro"))
    this.setOrganizacaoNumero(this.getProperty("organizacaoNumero"))
    this.setOrganizacaoComplemento(this.getProperty("organizacaoComplemento"))
    this.setOrganizacaoBairro(this.getProperty("organizacaoBairro"))
    this.setOrganizacaoCidade(this.getProperty("organicacaoCidade"))
    this.setOrganizacaoEstado(this.getProperty("organizacaoEstado"))
    this.setOrganizacaoPais(this.getProperty("organizacaoPais"))
    this.setOrganizacaoCep(this.getProperty("organizacaoCep"))
    this.setOrganizacaoTelefone(this.getProperty("organizacaoTelefone"))
    this.setOrganizacaoFax(this.getProperty("organizacaoFax"))
    this.setRepositorioConsultas(this.getProperty("repositorioConsultas"))
    this.setRepositorioRelatorios(this.getProperty("repositorioRelatorios"))
    this.setRepositorioLogs(this.getProperty("repositorioLogs"))
    this.setBancoDadosUsuario(this.getProperty("bancoDadosUsuario"))
    this.setBancoDadosSenha(this.getProperty("bancoDadosSenha"))
    this.setBancoDadosDriver(this.getProperty("bancoDadosDriver"))
    this.setBancoDadosURL(this.getProperty("bancoDadosURL"))
    this.setBancoDadosBaseDados(this.getProperty("bancoDadosBaseDados"))
    try
    {
      this.setLarguraPapel(Integer.parseInt(this.getProperty("larguraPapel")))
      this.setAlturaPapel(Integer.parseInt(this.getProperty("alturaPapel")))
      this.setOrientacao(Integer.parseInt(this.getProperty("orientacao")))
      this.setMargemEsquerda(Integer.parseInt(this.getProperty("margemEsquerda")))
      this.setMargemDireita(Integer.parseInt(this.getProperty("margemDireita")))
      this.setMargemSuperior(Integer.parseInt(this.getProperty("margemSuperior")))
      this.setMargemInferior(Integer.parseInt(this.getProperty("margemInferior")))
    }
    catch(NumberFormatException e)
    {

    }
    carregada = true
  }

   void setOrganizacaoRazaoSocial(String organizacaoRazaoSocial)
  {
    Configuracao.organizacaoRazaoSocial = organizacaoRazaoSocial
  }

   void setOrganizacaoNomeFantasia(String organizacaoNomeFantasia)
  {
    Configuracao.organizacaoNomeFantasia = organizacaoNomeFantasia
  }

   void setOrganizacaoCnpj(String organizacaoCnpj)
  {
    Configuracao.organizacaoCnpj = organizacaoCnpj
  }

   void setOrganizacaoLogradouro(String organizacaoLogradouro)
  {
    Configuracao.organizacaoLogradouro = organizacaoLogradouro
  }

   void setOrganizacaoNumero(String organizacaoNumero)
  {
    Configuracao.organizacaoNumero = organizacaoNumero
  }

   void setOrganizacaoComplemento(String organizacaoComplemento)
  {
    Configuracao.organizacaoComplemento = organizacaoComplemento
  }

   void setOrganizacaoBairro(String organizacaoBairro)
  {
    Configuracao.organizacaoBairro = organizacaoBairro
  }

   void setOrganizacaoCidade(String organicacaoCidade)
  {
    Configuracao.organicacaoCidade = organicacaoCidade
  }

   void setOrganizacaoEstado(String organizacaoEstado)
  {
    Configuracao.organizacaoEstado = organizacaoEstado
  }

   void setOrganizacaoPais(String organizacaoPais)
  {
    Configuracao.organizacaoPais = organizacaoPais
  }

   void setOrganizacaoCep(String organizacaoCep)
  {
    Configuracao.organizacaoCep = organizacaoCep
  }

   void setOrganizacaoTelefone(String organizacaoTelefone)
  {
    Configuracao.organizacaoTelefone = organizacaoTelefone
  }

   void setOrganizacaoFax(String organizacaoFax)
  {
    Configuracao.organizacaoFax = organizacaoFax
  }

   void setRepositorioConsultas(String repositorioConsultas)
  {
    Configuracao.repositorioConsultas = repositorioConsultas
  }

   void setRepositorioRelatorios(String repositorioRelatorios)
  {
    Configuracao.repositorioRelatorios = repositorioRelatorios
  }

   void setRepositorioLogs(String repositorioLogs)
  {
    Configuracao.repositorioLogs = repositorioLogs
  }

   void setBancoDadosUsuario(String bancoDadosUsuario)
  {
    Configuracao.bancoDadosUsuario = bancoDadosUsuario
  }

   void setBancoDadosSenha(String bancoDadosSenha)
  {
    Configuracao.bancoDadosSenha = bancoDadosSenha
  }

   void setBancoDadosDriver(String bancoDadosDriver)
  {
    Configuracao.bancoDadosDriver = bancoDadosDriver
  }

   void setBancoDadosURL(String bancoDadosURL)
  {
    Configuracao.bancoDadosURL = bancoDadosURL
  }

   void setBancoDadosBaseDados(String bancoDadosBaseDados)
  {
    Configuracao.bancoDadosBaseDados = bancoDadosBaseDados
  }

   void setLarguraPapel(int larguraPapel)
  {
    Configuracao.larguraPapel = larguraPapel
  }

   void setAlturaPapel(int alturaPapel)
  {
    Configuracao.alturaPapel = alturaPapel
  }

   void setOrientacao(int orientacao)
  {
    Configuracao.orientacao = orientacao
  }

   void setMargemEsquerda(int margemEsquerda)
  {
    Configuracao.margemEsquerda = margemEsquerda
  }

   void setMargemDireita(int margemDireita)
  {
    Configuracao.margemDireita = margemDireita
  }

   void setMargemSuperior(int margemSuperior)
  {
    Configuracao.margemSuperior = margemSuperior
  }

   void setMargemInferior(int margemInferior)
  {
    Configuracao.margemInferior = margemInferior
  }

   static String getOrganizacaoRazaoSocial()
  {
    return organizacaoRazaoSocial
  }

   static String getOrganizacaoNomeFantasia()
  {
    return organizacaoNomeFantasia
  }

   static String getOrganizacaoCnpj()
  {
    return organizacaoCnpj
  }

   static String getOrganizacaoLogradouro()
  {
    return organizacaoLogradouro
  }

   static String getOrganizacaoNumero()
  {
    return organizacaoNumero
  }

   static String getOrganizacaoComplemento()
  {
    return organizacaoComplemento
  }

   static String getOrganizacaoBairro()
  {
    return organizacaoBairro
  }

   static String getOrganizacaoCidade()
  {
    return organicacaoCidade
  }

   static String getOrganizacaoEstado()
  {
    return organizacaoEstado
  }

   static String getOrganizacaoPais()
  {
    return organizacaoPais
  }

   static String getOrganizacaoCep()
  {
    return organizacaoCep
  }

   static String getOrganizacaoTelefone()
  {
    return organizacaoTelefone
  }

   static String getOrganizacaoFax()
  {
    return organizacaoFax
  }

   static String getRepositorioConsultas()
  {
    return repositorioConsultas
  }

   static String getRepositorioRelatorios()
  {
    return repositorioRelatorios
  }

   static String getRepositorioLogs()
  {
    return repositorioLogs
  }

   static String getBancoDadosUsuario()
  {
    return bancoDadosUsuario
  }

   static String getBancoDadosSenha()
  {
    return bancoDadosSenha
  }

   static String getBancoDadosDriver()
  {
    return bancoDadosDriver
  }

   static String getBancoDadosURL()
  {
    return bancoDadosURL
  }

   static String getBancoDadosBaseDados()
  {
    return bancoDadosBaseDados
  }

   static int getLarguraPapel()
  {
    return larguraPapel
  }

   static int getAlturaPapel()
  {
    return alturaPapel
  }

   static int getLarguraPapelPixel()
  {
    return (int)((larguraPapel/25.4f) * 72)
  }

   static int getAlturaPapelPixel()
  {
    return (int)((alturaPapel/25.4f) * 72)
  }

   static int getOrientacao()
  {
    return orientacao
  }

   static int getMargemEsquerda()
  {
    return margemEsquerda
  }

   static int getMargemDireita()
  {
    return margemDireita
  }

   static int getMargemSuperior()
  {
    return margemSuperior
  }

   static int getMargemInferior()
  {
    return margemInferior
  }

   static int getMargemEsquerdaPixel()
  {
    return (int)((margemEsquerda/25.4f) * 72)
  }

   static int getMargemDireitaPixel()
  {
    return (int)((margemDireita/25.4f) * 72)
  }

   static int getMargemSuperiorPixel()
  {
    return (int)((margemSuperior/25.4f) * 72)
  }

   static int getMargemInferiorPixel()
  {
    return (int)((margemInferior/25.4f) * 72)
  }

   boolean isCarregada()
  {
    return this.carregada
  }

   void salvarConfiguracao()
  {
    try
    {
      this.put("organizacaoRazaoSocial",Configuracao.getOrganizacaoRazaoSocial())
      this.put("organizacaoNomeFantasia",Configuracao.getOrganizacaoNomeFantasia())
      this.put("organizacaoCnpj",Configuracao.getOrganizacaoCnpj())
      this.put("organizacaoLogradouro",Configuracao.getOrganizacaoLogradouro())
      this.put("organizacaoNumero",Configuracao.getOrganizacaoNumero())
      this.put("organizacaoComplemento",Configuracao.getOrganizacaoComplemento())
      this.put("organizacaoBairro",Configuracao.getOrganizacaoBairro())
      this.put("organicacaoCidade",Configuracao.getOrganizacaoCidade())
      this.put("organizacaoEstado",Configuracao.getOrganizacaoEstado())
      this.put("organizacaoPais",Configuracao.getOrganizacaoPais())
      this.put("organizacaoCep",Configuracao.getOrganizacaoCep())
      this.put("organizacaoTelefone",Configuracao.getOrganizacaoTelefone())
      this.put("organizacaoFax",Configuracao.getOrganizacaoFax())
      this.put("repositorioConsultas",Configuracao.getRepositorioConsultas())
      this.put("repositorioRelatorios",Configuracao.getRepositorioRelatorios())
      this.put("repositorioLogs",Configuracao.getRepositorioLogs())
      this.put("bancoDadosUsuario",Configuracao.getBancoDadosUsuario())
      this.put("bancoDadosSenha",Configuracao.getBancoDadosSenha())
      this.put("bancoDadosDriver",Configuracao.getBancoDadosDriver())
      this.put("bancoDadosURL",Configuracao.getBancoDadosURL())
      this.put("bancoDadosBaseDados",Configuracao.getBancoDadosBaseDados())
      this.put("larguraPapel", "" + Configuracao.getLarguraPapel())
      this.put("alturaPapel", "" + Configuracao.getAlturaPapel())
      this.put("orientacao", "" + Configuracao.getOrientacao())
      this.put("margemEsquerda", "" + Configuracao.getMargemEsquerda())
      this.put("margemDireita", "" + Configuracao.getMargemDireita())
      this.put("margemSuperior", "" + Configuracao.getMargemSuperior())
      this.put("margemInferior", "" + Configuracao.getMargemInferior())

      FileOutputStream saida = new FileOutputStream("config.properties")
      this.store(saida,"Configurações do Sistema")
      saida.close()
    }
    catch(IOException e)
    {
      System.err.println("Erro: " + e.getMessage())
    }
  }
}
