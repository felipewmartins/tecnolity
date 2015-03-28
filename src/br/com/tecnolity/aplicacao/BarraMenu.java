/**
   * Projeto: 001 - Tecnolity
   * Autor do C�digo: Hildeberto Mendon�a Filho
   * Nome do Arquivo: MenuAplicacao.java
   * Linguagem: Java
   * 
   * Requerimentos: Requer m�quina virtual java vers�o 1.3 ou superior.
   * 
   * Objetivo: Navega��o entre as fun��es da aplica��o.
   * 
   * Objetivo definido por: Hildeberto Mendon�a
   * In�cio da Programa��o: 22/12/2001
   * Fim da Programa��o:
   * �ltima Vers�o: 1.0
*/

package br.com.tecnolity.aplicacao;

import java.awt.event.*;
import java.io.File;
import java.net.URL;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.help.CSH.DisplayHelpFromSource;
import javax.swing.*;

public class BarraMenu extends JMenuBar implements ActionListener
{
    // Declara��o dos Itens e SubItens de Arquivo
    private JMenu mnArquivo;
    private JMenuItem mnArqDesconectar;
    private JMenuItem mnArqSair;

    private Aplicacao aplicacao;
    
    private JMenu mnPreferencias;
    private JMenu mnPrefAparencia;
    private JMenuItem mnPrefApPadrao;
    private JMenuItem mnPrefApWindows;
    private JMenuItem mnPrefApUnix;
    private JMenuItem mnPrefConfigurarImpressora;
    private JMenuItem mnPrefConfiguracoes;
    private JMenu mnAjuda;
    private JMenuItem mnAjdConteudo;
    
    // Vari�veis da ajuda.
    private HelpSet helpSet;
    private HelpBroker helpBroker;
        
    public BarraMenu(Aplicacao aplicacao) 
    {
        this.aplicacao = aplicacao;
        
        // Itens e SubItens de Arquivo
        mnArquivo = new JMenu("Arquivo");
        mnArquivo.setMnemonic('A');
        mnArqDesconectar = new JMenuItem("Desconectar-se");
        mnArqDesconectar.setMnemonic('D');
        mnArqDesconectar.addActionListener(this);
        mnArquivo.add(mnArqDesconectar);
        mnArquivo.addSeparator();
        mnArqSair = new JMenuItem("Sair da Aplica��o");
        mnArqSair.setMnemonic('S');
        mnArqSair.addActionListener(this);
        mnArquivo.add(mnArqSair);
        this.add(mnArquivo);
        
        mnPreferencias = new JMenu("Prefer�ncias");
        mnPreferencias.setMnemonic('P');
        mnPrefAparencia = new JMenu("Apar�ncia");
        mnPrefApPadrao = new JMenuItem("Padr�o");
        mnPrefApPadrao.setMnemonic('P');
        mnPrefApPadrao.addActionListener(this);
        mnPrefApWindows = new JMenuItem("Windows");
        mnPrefApWindows.setMnemonic('W');
        mnPrefApWindows.addActionListener(this);
        mnPrefApUnix = new JMenuItem("Unix");
        mnPrefApUnix.setMnemonic('U');
        mnPrefApUnix.addActionListener(this);
        mnPrefAparencia.add(mnPrefApPadrao);
        mnPrefAparencia.add(mnPrefApWindows);
        mnPrefAparencia.add(mnPrefApUnix);
        mnPreferencias.add(mnPrefAparencia);
        mnPreferencias.addSeparator();
        mnPrefConfigurarImpressora = new JMenuItem("Impress�o", new ImageIcon("imagens/ico_impressao.gif"));
        mnPrefConfigurarImpressora.setMnemonic('C');
        mnPrefConfigurarImpressora.addActionListener(this);
        mnPreferencias.add(mnPrefConfigurarImpressora);
        mnPrefConfiguracoes = new JMenuItem("Configura��es", new ImageIcon("imagens/ico_configuracoes.gif"));
        mnPrefConfiguracoes.setMnemonic('C');
        mnPrefConfiguracoes.addActionListener(this);
        mnPreferencias.add(mnPrefConfiguracoes);
        this.add(mnPreferencias);
        
        mnAjuda = new JMenu("Ajuda");
        mnAjuda.setMnemonic('A');
        mnAjdConteudo = new JMenuItem("Conte�do");
        try
        {
            ClassLoader classLoader = aplicacao.getClass().getClassLoader();
            URL hsURL = new URL((new File(".")).toURL( ), "ajuda/helpset.hs");
            helpSet = new HelpSet(classLoader, hsURL);
            helpBroker = helpSet.createHelpBroker();
            helpBroker.enableHelpKey(aplicacao,"Vis�o Geral", helpSet);
            ActionListener helper = new DisplayHelpFromSource(helpBroker);
            mnAjdConteudo.addActionListener(helper);
        }
        catch(Exception e)
        {
            mnAjdConteudo.setEnabled(false);
        }
        mnAjdConteudo.setMnemonic('C');
        mnAjdConteudo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, ActionEvent.ALT_MASK));
        mnAjuda.add(mnAjdConteudo);
        this.add(mnAjuda);
    }
    
    public void actionPerformed(java.awt.event.ActionEvent actionEvent) 
    {
        Object objeto = actionEvent.getSource();
   
        if(objeto == mnArqDesconectar)
        {
            aplicacao.setVisible(false);
            aplicacao = new Aplicacao();
            aplicacao.setVisible(true);
        }
        
        if(objeto == mnArqSair)
        {
            aplicacao.finalizarAplicacao();
        }
                
        if(objeto == mnPrefApPadrao)
        {
            try
            {
                UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                SwingUtilities.updateComponentTreeUI(aplicacao);
             }catch(Exception e){}
        }
        
        if(objeto == mnPrefApWindows)
        {
            try
            {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                SwingUtilities.updateComponentTreeUI(aplicacao);
            }catch(Exception e){}
        }
        
        if(objeto == mnPrefApUnix)
        {
            try
            {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                SwingUtilities.updateComponentTreeUI(aplicacao);
            }catch(Exception e){}
        }
        
        if(objeto == mnPrefConfigurarImpressora)
        {
            DlgConfiguracaoImpressora dlgConfiguracaoImpressora = new DlgConfiguracaoImpressora(this.aplicacao);
            dlgConfiguracaoImpressora.setVisible(true);
            this.aplicacao.definirFormatoPagina();
        }
        
        if(objeto == mnPrefConfiguracoes)
        {
            DlgConfiguracoes dlgConfiguracoes = new DlgConfiguracoes(this.aplicacao, this.aplicacao.obterConfiguracao());
            dlgConfiguracoes.setVisible(true);
        }
    }
}