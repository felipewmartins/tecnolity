package org.esmerilprogramming.tecnolity.aplicacao

import javax.swing.*
import javax.swing.JPanel
import java.awt.*
import java.awt.print.*
import org.esmerilprogramming.tecnolity.administracao.*
import org.esmerilprogramming.tecnolity.util.*
import org.esmerilprogramming.tecnolity.aplicacao.*
import org.esmerilprogramming.tecnolity.ui.*
import org.esmerilprogramming.tecnolity.ui.img.ImageLoader

class Aplicacao extends org.esmerilprogramming.tecnolity.ui.Aplicacao {

  private JPanel areaTrabalho
  private Container conteudo
  private Colaborador colaborador
  private Conexao conexao
  private PrinterJob tarefaImpressao
  private PageFormat formatoPagina
  private Configuracao configuracao

  Aplicacao() {
    super('Sistema de Planejamento e Controle da Produção')
    configuracao = new Configuracao()
    try {
      configuracao.carregarConfiguracao()
    } catch(e) {
      JOptionPane.showMessageDialog(this, 'Falha no carregamento das propriedades do sistema.', 'Falha', JOptionPane.ERROR_MESSAGE)
      DlgConfiguracoes dlgConfiguracoes = new DlgConfiguracoes(this, configuracao)
      dlgConfiguracoes.setVisible(true)
    }

    super.setIconImage(ImageLoader.instance.icon('ico_tecnolity.jpg').getImage())

    FormAutenticacao formAutenticacao = new FormAutenticacao(this)
    formAutenticacao.setVisible(true)

    try {
      colaborador.carregarPermissoes(conexao)
    } catch(e) {
      JOptionPane.showMessageDialog(this, 'Não foi possível carregar as permissões do usuário.\n\n'  +  e.message, 'Erro', JOptionPane.ERROR_MESSAGE)
      e.printStackTrace()
    }

    conteudo = this.getContentPane()

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize()
    setBounds(0, 0, screenSize.width as int, screenSize.height - 27 as int)
    addWindowListener(this)

    BarraMenu barraMenu = new BarraMenu(this)
    setJMenuBar(barraMenu)

    BarraAplicacao barraAplicacao = new BarraAplicacao(this)
    conteudo.add(barraAplicacao, BorderLayout.NORTH)

    BarraStatus barraStatus = new BarraStatus(this)
    conteudo.add(barraStatus, BorderLayout.SOUTH)
  }

  void adicionarAreaTrabalho(JPanel areaTrabalho) {
    if(this.areaTrabalho) {
      conteudo.remove(this.areaTrabalho)
    }
    this.areaTrabalho = areaTrabalho
    conteudo.add(this.areaTrabalho, BorderLayout.CENTER)
    this.areaTrabalho.updateUI()
  }

  void inicializarTarefaImpressao() {
    if (this.tarefaImpressao == null) {
      tarefaImpressao = PrinterJob.getPrinterJob()
    }
  }

  PrinterJob obterTarefaImpressao() {
    inicializarTarefaImpressao()
    tarefaImpressao
  }

  void definirFormatoPagina() {
    formatoPagina = new PageFormat()
    Paper papel = new Paper()
    papel.setSize(Configuracao.getLarguraPapelPixel(), Configuracao.getAlturaPapelPixel())
    papel.setImageableArea(Configuracao.getMargemEsquerdaPixel(),
          Configuracao.getMargemSuperiorPixel(),
          Configuracao.getLarguraPapelPixel() - Configuracao.getMargemEsquerdaPixel() - Configuracao.getMargemDireitaPixel(),
          Configuracao.getAlturaPapelPixel() - Configuracao.getMargemSuperiorPixel() - Configuracao.getMargemInferiorPixel())
    formatoPagina.setPaper(papel)
    formatoPagina.setOrientation(Configuracao.getOrientacao())
  }

  Configuracao obterConfiguracao() {
    configuracao
  }

  PageFormat obterFormatoPagina() {
    if(formatoPagina == null) {
      definirFormatoPagina()
    }
    formatoPagina
  }

  Conexao obterConexao() {
    if(conexao.conexaoAberta() || conexao != null) {
      return conexao
    }
    else {
      abrirConexao()
      return conexao
    }
  }

  void abrirConexao() {
    this.conexao = new Conexao()
  }

  void definirColaborador(Colaborador colaborador) {
    this.colaborador = colaborador

    try {
      println 'LOG DE EVENTOS DO SISTEMA TECNOLITY'
      println '---------------------------------------------------------------'
      println 'Colaborador: '  +  colaborador.matricula
      println '  Acesso em: '  +  new Date().format('dd/MM/yyyy \'as\' HH:mm:ss')
      println '---------------------------------------------------------------'
      println ''
    } catch(e) {
      e.printStackTrace()
    }
  }

  static main (args) {
    Conexao.instance.setupDB()
    Aplicacao aplicacao = new Aplicacao()
    aplicacao.setVisible(true)
  }

  boolean conectado() {
    try {
      if(conexao == null)
        return false
      else if(conexao.conexaoAberta())
        return true
      else
        return false
    }
    catch(e) {
      JOptionPane.showMessageDialog(this, 'Erro:'  +  e.getMessage(), 'Erro', JOptionPane.ERROR_MESSAGE)
      return false
    }
  }

  void finalizarAplicacao() {
    if(conectado()) {
      try {
        conexao.fecharConexao()
        LogAplicacao.stop()
      }
      catch(e) {
        JOptionPane.showMessageDialog(this, 'Erro:'  +  e.getMessage(), 'Erro', JOptionPane.ERROR_MESSAGE)
      }
    }
    super.finalizarAplicacao()
  }

}
