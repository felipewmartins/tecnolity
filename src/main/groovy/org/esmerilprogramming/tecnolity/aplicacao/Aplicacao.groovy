package org.esmerilprogramming.tecnolity.aplicacao

import javax.swing.*

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
  private int teste
  private static String caminho
  private PrinterJob tarefaImpressao
  private PageFormat formatoPagina
  private Configuracao configuracao

  Aplicacao() {
    super("Sistema de Planejamento e Controle da Produção")
    configuracao = new Configuracao()
    try {
      configuracao.carregarConfiguracao()
    } catch(e) {
      JOptionPane.showMessageDialog(this,"Falha no carregamento das propriedades do sistema.","Falha",JOptionPane.ERROR_MESSAGE)
      DlgConfiguracoes dlgConfiguracoes = new DlgConfiguracoes(this, configuracao)
      dlgConfiguracoes.setVisible(true)
    }

    super.setIconImage(ImageLoader.instance.icon('ico_tecnolity.jpg').getImage())

    FormAutenticacao formAutenticacao = new FormAutenticacao(this)
    formAutenticacao.setVisible(true)

    try {
      colaborador.carregarPermissoes(conexao)
    } catch(e) {
      JOptionPane.showMessageDialog(this,"Não foi possível carregar as permissões do usuário.\n\n" + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE)
      e.printStackTrace()
    }

    conteudo = this.getContentPane()

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize()
    this.setBounds(0,0, screenSize.width as int, screenSize.height - 27 as int)
    this.addWindowListener(this)

    BarraMenu barraMenu = new BarraMenu(this)
    this.setJMenuBar(barraMenu)

    BarraAplicacao barraAplicacao = new BarraAplicacao(this)
    conteudo.add(barraAplicacao, BorderLayout.NORTH)

    BarraStatus barraStatus = new BarraStatus(this)
    conteudo.add(barraStatus,BorderLayout.SOUTH)
  }

  void adicionarAreaTrabalho(JPanel areaTrabalho) {
    if(this.areaTrabalho != null) {
      conteudo.remove(this.areaTrabalho)
    }
    this.areaTrabalho = areaTrabalho
    conteudo.add(this.areaTrabalho,BorderLayout.CENTER)
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
    this.formatoPagina = new PageFormat()
    Paper papel = new Paper()
    papel.setSize(Configuracao.getLarguraPapelPixel(), Configuracao.getAlturaPapelPixel())
    papel.setImageableArea(Configuracao.getMargemEsquerdaPixel(),
          Configuracao.getMargemSuperiorPixel(),
          Configuracao.getLarguraPapelPixel() - Configuracao.getMargemEsquerdaPixel() - Configuracao.getMargemDireitaPixel(),
          Configuracao.getAlturaPapelPixel() - Configuracao.getMargemSuperiorPixel() - Configuracao.getMargemInferiorPixel())
      this.formatoPagina.setPaper(papel)
      this.formatoPagina.setOrientation(Configuracao.getOrientacao())
  }

  Configuracao obterConfiguracao() {
    return this.configuracao
  }

  PageFormat obterFormatoPagina() {
    if(this.formatoPagina == null) {
      definirFormatoPagina()
    }
    return this.formatoPagina
  }

  Conexao obterConexao() {
    if(this.conexao.conexaoAberta() || this.conexao != null) {
      return this.conexao
    }
    else
    {
      abrirConexao()
        return this.conexao
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
      println 'Colaborador: ' + colaborador.matricula
      println '  Acesso em: ' + new Date().format("dd/MM/yyyy 'as' HH:mm:ss")
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
    try
    {
      if(conexao == null)
        return false
      else if(conexao.conexaoAberta())
        return true
      else
        return false
    }
    catch(Exception e) {
      JOptionPane.showMessageDialog(this,"Erro:" + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE)
        return false
    }
  }

  void finalizarAplicacao() {
    if(conectado()) {
      try
      {
        conexao.fecharConexao()
          LogAplicacao.stop()
      }
      catch(Exception e) {
        JOptionPane.showMessageDialog(this,"Erro:" + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE)
      }
    }
    super.finalizarAplicacao()
  }

  static String obterCaminho() {
    return caminho
  }
}
