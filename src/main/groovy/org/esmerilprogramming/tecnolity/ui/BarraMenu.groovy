package br.com.tecnolity.ui

import java.awt.event.*
import java.io.File
import java.net.URL
import org.esmerilprogramming.tecnolity.App
import javax.swing.*

class BarraMenu extends JMenuBar implements ActionListener {
  private JMenu mnArquivo
    private JMenuItem mnArqDesconectar
    private JMenuItem mnArqSair

    private App aplicacao

    private JMenu mnPreferencias
    private JMenu mnPrefAparencia
    private JMenuItem mnPrefApPadrao
    private JMenuItem mnPrefApWindows
    private JMenuItem mnPrefApUnix
    private JMenuItem mnPrefConfigurarImpressora
    private JMenuItem mnPrefConfiguracoes

    BarraMenu(App aplicacao) {
      this.aplicacao = aplicacao
        // Itens e SubItens de Arquivo
        mnArquivo = new JMenu("Arquivo")
        mnArquivo.setMnemonic('A')
        mnArqDesconectar = new JMenuItem("Desconectar-se")
        mnArqDesconectar.setMnemonic('D')
        mnArqDesconectar.addActionListener(this)
        mnArquivo.add(mnArqDesconectar)
        mnArquivo.addSeparator()
        mnArqSair = new JMenuItem("Sair da Aplicação")
        mnArqSair.setMnemonic('S')
        mnArqSair.addActionListener(this)
        mnArquivo.add(mnArqSair)
        this.add(mnArquivo)
        mnPreferencias = new JMenu("Preferências")
        mnPreferencias.setMnemonic('P')
        mnPrefAparencia = new JMenu("Aparência")
        mnPrefApPadrao = new JMenuItem("Padrão")
        mnPrefApPadrao.setMnemonic('P')
        mnPrefApPadrao.addActionListener(this)
        mnPrefApWindows = new JMenuItem("Windows")
        mnPrefApWindows.setMnemonic('W')
        mnPrefApWindows.addActionListener(this)
        mnPrefApUnix = new JMenuItem("Unix")
        mnPrefApUnix.setMnemonic('U')
        mnPrefApUnix.addActionListener(this)
        mnPrefAparencia.add(mnPrefApPadrao)
        mnPrefAparencia.add(mnPrefApWindows)
        mnPrefAparencia.add(mnPrefApUnix)
        mnPreferencias.add(mnPrefAparencia)
        mnPreferencias.addSeparator()
        mnPrefConfigurarImpressora = new JMenuItem("Impressão", new ImageIcon("imagens/ico_impressao.gif"))
        mnPrefConfigurarImpressora.setMnemonic('C')
        mnPrefConfigurarImpressora.addActionListener(this)
        mnPreferencias.add(mnPrefConfigurarImpressora)
        mnPrefConfiguracoes = new JMenuItem("Configurações", new ImageIcon("imagens/ico_configuracoes.gif"))
        mnPrefConfiguracoes.setMnemonic('C')
        mnPrefConfiguracoes.addActionListener(this)
        mnPreferencias.add(mnPrefConfiguracoes)
        this.add(mnPreferencias)
    }

  public void actionPerformed(java.awt.event.ActionEvent actionEvent) 
  {
    Object objeto = actionEvent.getSource()
      if(objeto == mnArqDesconectar)
      {
        aplicacao.setVisible(false)
          aplicacao = new Aplicacao()
          aplicacao.setVisible(true)
      }
    if(objeto == mnArqSair)
    {
      aplicacao.finalizarAplicacao()
    }
    if(objeto == mnPrefApPadrao)
    {
      try
      {
        UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel")
          SwingUtilities.updateComponentTreeUI(aplicacao)
      }catch(Exception e){}
    }
    if(objeto == mnPrefApWindows)
    {
      try
      {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel")
          SwingUtilities.updateComponentTreeUI(aplicacao)
      }catch(Exception e){}
    }

    if(objeto == mnPrefApUnix)
    {
      try
      {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel")
          SwingUtilities.updateComponentTreeUI(aplicacao)
      }catch(Exception e){}
    }

    if(objeto == mnPrefConfigurarImpressora)
    {
      DlgConfiguracaoImpressora dlgConfiguracaoImpressora = new DlgConfiguracaoImpressora(this.aplicacao)
        dlgConfiguracaoImpressora.setVisible(true)
        this.aplicacao.definirFormatoPagina()
    }

    if(objeto == mnPrefConfiguracoes)
    {
      DlgConfiguracoes dlgConfiguracoes = new DlgConfiguracoes(this.aplicacao, this.aplicacao.obterConfiguracao())
        dlgConfiguracoes.setVisible(true)
    }
  }
}