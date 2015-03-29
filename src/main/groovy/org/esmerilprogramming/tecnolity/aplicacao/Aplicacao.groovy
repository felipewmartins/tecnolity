package org.esmerilprogramming.tecnolity.aplicacao

import javax.swing.*

import java.awt.*
import java.io.*
import java.awt.print.*
import org.esmerilprogramming.tecnolity.administracao.*
import org.esmerilprogramming.tecnolity.util.*
import org.esmerilprogramming.tecnolity.aplicacao.*
import org.esmerilprogramming.tecnolity.ui.*

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
        try
        {
          configuracao.carregarConfiguracao()
        }
      catch(IOException e) {
        JOptionPane.showMessageDialog(this,"Falha no carregamento das propriedades do sistema.","Falha",JOptionPane.ERROR_MESSAGE)
          DlgConfiguracoes dlgConfiguracoes = new DlgConfiguracoes(this, configuracao)
          dlgConfiguracoes.setVisible(true)
      }

      super.setIconImage((new ImageIcon("imagens/ico_tecnolity.jpg")).getImage())

        abrirConexao()
        if(!conexao.abrirConexao()) {
          JOptionPane.showMessageDialog(this,"Não foi possível realizar uma conexão com o banco de dados. \nA aplicação terá que ser finalizada.","Erro",JOptionPane.WARNING_MESSAGE)
            System.exit(0)
        }

      FormAutenticacao formAutenticacao = new FormAutenticacao(this)
        formAutenticacao.setVisible(true)

        try
        {
          colaborador.carregarPermissoes(conexao)
        }
      catch(Exception e) {
        JOptionPane.showMessageDialog(this,"Não foi possível carregar as permissões do usuário.\n\n" + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }

      conteudo = this.getContentPane()

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize()
        this.setBounds(0,0, screenSize.width, screenSize.height - 27)
        this.addWindowListener(this)

        /* adiciona a barra de menu */
        BarraMenu barraMenu = new BarraMenu(this)
        this.setJMenuBar(barraMenu)

        /* Adiciona a barra de aplicações*/
        BarraAplicacao barraAplicacao = new BarraAplicacao(this)
        conteudo.add(barraAplicacao, BorderLayout.NORTH)

        /* Adiciona a barra de status */
        BarraStatus barraStatus = new BarraStatus(this)
        conteudo.add(barraStatus,BorderLayout.SOUTH)
    }

  void adicionarAreaTrabalho(JPanel areaTrabalho) {
    if(this.areaTrabalho != null)
      conteudo.remove(this.areaTrabalho)
        this.areaTrabalho = areaTrabalho
        conteudo.add(this.areaTrabalho,BorderLayout.CENTER)
        this.areaTrabalho.updateUI()
  }

  void inicializarTarefaImpressao() {
    if(this.tarefaImpressao == null)
      tarefaImpressao = PrinterJob.getPrinterJob()
  }

  PrinterJob obterTarefaImpressao() {
    inicializarTarefaImpressao()
      return tarefaImpressao
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
    this.conexao = new Conexao('C')
  }

  Colaborador obterColaborador() {
    return this.colaborador
  }

  void definirColaborador(Colaborador colaborador) {
    this.colaborador = colaborador

      // Inicia o registro de saídas no log.
      try
      {
        Calendario calendario = new Calendario()
          LogAplicacao.start("log/log-"+ colaborador.obterMatricula() +"-"+ calendario.dataHoje("yyyyMMddHHmmss") +".txt")
          System.out.println("LOG DE EVENTOS DO SISTEMA TECNOLITY")
          System.out.println("---------------------------------------------------------------")
          System.out.println("Colaborador: " + colaborador.obterMatricula())
          System.out.println("  Acesso em: " + calendario.dataHoje("dd/MM/yyyy 'as' HH:mm:ss"))
          System.out.println("---------------------------------------------------------------")
          System.out.println("")
      }
    catch(Exception e) {
      e.printStackTrace()
    }
  }

  static main (args) {
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

class LogAplicacao extends PrintStream
{
  static OutputStream arquivoLog
    static PrintStream saida
    static PrintStream erro

    LogAplicacao(PrintStream ps) {
      super(ps)
    }

  static void start(String arquivo) throws IOException
  {
    saida = System.out
      erro = System.err

      arquivoLog = new PrintStream(new BufferedOutputStream(new FileOutputStream(arquivo)))
      System.setOut(new LogAplicacao(System.out))
      System.setErr(new LogAplicacao(System.err))
  }

  static void stop() {
    if(saida != null || erro != null) {
      System.setOut(saida)
        System.setErr(erro)
        try
        {
          arquivoLog.close()
        }
      catch(Exception e) {
        e.printStackTrace()
      }
    }
  }

  void write(int b) {
    try
    {
      arquivoLog.write(b)
    }
    catch(Exception e) {
      e.printStackTrace()
        setError()
    }
    super.write(b)
  }

  void write(byte[] buf,int off, int len) {
    try {
      arquivoLog.write(buf,off,len)
    }
    catch(Exception e) {
      e.printStackTrace()
        setError()
    }
    super.write(buf,off,len)
  }
}
