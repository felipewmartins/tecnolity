package org.esmerilprogramming.tecnolity.util

class Configuracao extends Properties {
  String organizacaoRazaoSocial = ''
  String organizacaoNomeFantasia = ''
  String organizacaoCnpj = ''
  String organizacaoLogradouro = ''
  String organizacaoNumero = ''
  String organizacaoComplemento = ''
  String organizacaoBairro = ''
  String organicacaoCidade = ''
  String organizacaoEstado = ''
  String organizacaoPais = ''
  String organizacaoCep = ''
  String organizacaoTelefone = ''
  String organizacaoFax = ''
  String repositorioConsultas = ''
  String repositorioRelatorios = ''
  String repositorioLogs = ''
  String bancoDadosUsuario = ''
  String bancoDadosSenha = ''
  String bancoDadosDriver = ''
  String bancoDadosURL = ''
  String bancoDadosBaseDados = ''
  // Propriedades de impressão
  int larguraPapel = 0
  int alturaPapel = 0
  int orientacao = 0
  int margemEsquerda = 0
  int margemDireita = 0
  int margemSuperior = 0
  int margemInferior = 0
  private boolean carregada

  void carregarConfiguracao() throws IOException {
    FileInputStream entrada = new FileInputStream('config.properties')
    load(entrada)
    entrada.close()

    organizacaoRazaoSocial = getProperty('organizacaoRazaoSocial')
    organizacaoNomeFantasia = getProperty('organizacaoNomeFantasia')
    organizacaoCnpj = getProperty('organizacaoCnpj')
    organizacaoLogradouro = getProperty('organizacaoLogradouro')
    organizacaoNumero = getProperty('organizacaoNumero')
    organizacaoComplemento = getProperty('organizacaoComplemento')
    organizacaoBairro = getProperty('organizacaoBairro')
    organizacaoCidade = getProperty('organicacaoCidade')
    organizacaoEstado = getProperty('organizacaoEstado')
    organizacaoPais = getProperty('organizacaoPais')
    organizacaoCep = getProperty('organizacaoCep')
    organizacaoTelefone = getProperty('organizacaoTelefone')
    organizacaoFax = getProperty('organizacaoFax')
    repositorioConsultas = getProperty('repositorioConsultas')
    repositorioRelatorios = getProperty('repositorioRelatorios')
    repositorioLogs = getProperty('repositorioLogs')
    bancoDadosUsuario = getProperty('bancoDadosUsuario')
    bancoDadosSenha = getProperty('bancoDadosSenha')
    bancoDadosDriver = getProperty('bancoDadosDriver')
    bancoDadosURL = getProperty('bancoDadosURL')
    bancoDadosBaseDados = getProperty('bancoDadosBaseDados')
    try {
      larguraPapel = getProperty('larguraPapel') as int
      alturaPapel = getProperty('alturaPapel') as int
      orientacao = getProperty('orientacao') as int
      margemEsquerda = getProperty('margemEsquerda') as int
      margemDireita = getProperty('margemDireita') as int
      margemSuperior = getProperty('margemSuperior') as int
      margemInferior = getProperty('margemInferior') as int
    } catch(e) {
      e.printStackTrace()
    }
    carregada = true
  }

  int getLarguraPapelPixel() {
    return (larguraPapel/25.4f) * 72
  }

  int getAlturaPapelPixel() {
    return (alturaPapel/25.4f) * 72
  }

  int getMargemEsquerdaPixel() {
    return margemEsquerda/25.4f * 72
  }

  int getMargemDireitaPixel() {
    return (margemDireita/25.4f) * 72
  }

  int getMargemSuperiorPixel() {
    return (margemSuperior/25.4f) * 72
  }

  int getMargemInferiorPixel() {
    return (margemInferior/25.4f) * 72
  }

  void salvarConfiguracao() {
    try {
      put('organizacaoRazaoSocial', organizacaoRazaoSocial)
      put('organizacaoNomeFantasia', organizacaoNomeFantasia)
      put('organizacaoCnpj', organizacaoCnpj)
      put('organizacaoLogradouro', organizacaoLogradouro)
      put('organizacaoNumero', organizacaoNumero)
      put('organizacaoComplemento', organizacaoComplemento)
      put('organizacaoBairro', organizacaoBairro)
      put('organicacaoCidade', organizacaoCidade)
      put('organizacaoEstado', organizacaoEstado)
      put('organizacaoPais', organizacaoPais)
      put('organizacaoCep', organizacaoCep)
      put('organizacaoTelefone', organizacaoTelefone)
      put('organizacaoFax', organizacaoFax)
      put('repositorioConsultas', repositorioConsultas)
      put('repositorioRelatorios', repositorioRelatorios)
      put('repositorioLogs', repositorioLogs)
      put('bancoDadosUsuario', bancoDadosUsuario)
      put('bancoDadosSenha', bancoDadosSenha)
      put('bancoDadosDriver', bancoDadosDriver)
      put('bancoDadosURL', bancoDadosURL)
      put('bancoDadosBaseDados', bancoDadosBaseDados)
      put('larguraPapel', larguraPapel)
      put('alturaPapel', alturaPapel)
      put('orientacao', orientacao)
      put('margemEsquerda', margemEsquerda)
      put('margemDireita',  margemDireita)
      put('margemSuperior', margemSuperior)
      put('margemInferior', margemInferior)

      FileOutputStream saida = new FileOutputStream('config.properties')
      store(saida, 'Configurações do Sistema')
      saida.close()
    } catch(e) {
      println('Erro: '  +  e.message)
    }
  }
}
