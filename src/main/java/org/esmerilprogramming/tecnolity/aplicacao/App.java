/**
   * Projeto: 001 - Tecnolity
   * Autor do Código: Hildeberto Mendonça Filho
   * Nome do Arquivo: Aplicacao.java
   * Linguagem: Java
   * 
   * Requerimentos: Requer máquina virtual java versão 1.3 ou superior.
   * 
   * Objetivo: Inicializa a aplicação.
   * 
   * Objetivo definido por: Hildeberto Mendonça
   * Início da Programação: 22/12/2001
   * Fim da Programação:
   * Última Versão: 1.0
*/

package br.com.tecnolity.aplicacao;

import javax.swing.*;

import java.awt.*;
import java.io.*;
import java.awt.print.*;
import br.com.tecnolity.administracao.*;
import br.com.tecnolity.util.*;

public class Aplicacao extends br.com.mentores.ui.Aplicacao
{
    private JPanel areaTrabalho;
    private Container conteudo;
    private Colaborador colaborador;
    private Conexao conexao;
    private int teste;
    private static String caminho;
    private PrinterJob tarefaImpressao;
    private PageFormat formatoPagina;
    private Configuracao configuracao;
    
    public Aplicacao()
    {
        super("PCP MENTOR - Sistema de Planejamento e Controle da Produção");
        configuracao = new Configuracao();
        try
        {
            configuracao.carregarConfiguracao();
        }
        catch(IOException e)
        {
            JOptionPane.showMessageDialog(this,"Falha no carregamento das propriedades do sistema.","Falha",JOptionPane.ERROR_MESSAGE);
            DlgConfiguracoes dlgConfiguracoes = new DlgConfiguracoes(this, configuracao);
            dlgConfiguracoes.setVisible(true);
        }
        
        super.setIconImage((new ImageIcon("imagens/ico_tecnolity.jpg")).getImage());
        
        abrirConexao();
        if(!conexao.abrirConexao())
        {
            JOptionPane.showMessageDialog(this,"Não foi possível realizar uma conexão com o banco de dados. \nA aplicação terá que ser finalizada.","Erro",JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }
        
        FormAutenticacao formAutenticacao = new FormAutenticacao(this);
        formAutenticacao.setVisible(true);
        
        try
        {
            colaborador.carregarPermissoes(conexao);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this,"Não foi possível carregar as permissões do usuário.\n\n" + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
 
        conteudo = this.getContentPane();
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(0,0, screenSize.width, screenSize.height - 27);
        this.addWindowListener(this);
        
        /* adiciona a barra de menu */
        BarraMenu barraMenu = new BarraMenu(this);
        this.setJMenuBar(barraMenu);
        
        /* Adiciona a barra de aplicações*/
        BarraAplicacao barraAplicacao = new BarraAplicacao(this);
        conteudo.add(barraAplicacao, BorderLayout.NORTH);
        
        /* Adiciona a barra de status */
        BarraStatus barraStatus = new BarraStatus(this);
        conteudo.add(barraStatus,BorderLayout.SOUTH);
    }
    
    public void adicionarAreaTrabalho(JPanel areaTrabalho)
    {
        if(this.areaTrabalho != null)
            conteudo.remove(this.areaTrabalho);
        this.areaTrabalho = areaTrabalho;
        conteudo.add(this.areaTrabalho,BorderLayout.CENTER);
        this.areaTrabalho.updateUI();
    }
    
    public void inicializarTarefaImpressao()
    {
        if(this.tarefaImpressao == null)
            tarefaImpressao = PrinterJob.getPrinterJob();
    }
    
    public PrinterJob obterTarefaImpressao()
    {
        inicializarTarefaImpressao();
        return tarefaImpressao;
    }
    
    public void definirFormatoPagina()
    {
        this.formatoPagina = new PageFormat();
        Paper papel = new Paper();
        papel.setSize(Configuracao.getLarguraPapelPixel(), Configuracao.getAlturaPapelPixel());
        papel.setImageableArea(Configuracao.getMargemEsquerdaPixel(),
                               Configuracao.getMargemSuperiorPixel(),
                               Configuracao.getLarguraPapelPixel() - Configuracao.getMargemEsquerdaPixel() - Configuracao.getMargemDireitaPixel(),
                               Configuracao.getAlturaPapelPixel() - Configuracao.getMargemSuperiorPixel() - Configuracao.getMargemInferiorPixel());
        this.formatoPagina.setPaper(papel);
        this.formatoPagina.setOrientation(Configuracao.getOrientacao());
    }
        
    public Configuracao obterConfiguracao()
    {
        return this.configuracao;
    }
    
    public PageFormat obterFormatoPagina()
    {
        if(this.formatoPagina == null)
        {
            definirFormatoPagina();
        }
        return this.formatoPagina;
    }
    
    public Conexao obterConexao()
    {
        if(this.conexao.conexaoAberta() || this.conexao != null)
        {
            return this.conexao;
        }
        else
        {
            abrirConexao();
            return this.conexao;
        }
    }
    
    public void abrirConexao()
    {
        this.conexao = new Conexao('C');
    }
    
    public Colaborador obterColaborador()
    {
    	return this.colaborador;
    }
    
    public void definirColaborador(Colaborador colaborador)
    {
    	this.colaborador = colaborador;
        
        // Inicia o registro de saídas no log.
        try
        {
            Calendario calendario = new Calendario();
            LogAplicacao.start("log/log-"+ colaborador.obterMatricula() +"-"+ calendario.dataHoje("yyyyMMddHHmmss") +".txt");
            System.out.println("LOG DE EVENTOS DO SISTEMA TECNOLITY");
            System.out.println("---------------------------------------------------------------");
            System.out.println("Colaborador: " + colaborador.obterMatricula());
            System.out.println("  Acesso em: " + calendario.dataHoje("dd/MM/yyyy 'as' HH:mm:ss"));
            System.out.println("---------------------------------------------------------------");
            System.out.println("");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main (String args[]) 
    {
        Aplicacao aplicacao = new Aplicacao();
        aplicacao.setVisible(true);
    }
    
    public boolean conectado()
    {
        try
        {
            if(conexao == null)
                return false;
            else if(conexao.conexaoAberta())
                return true;
            else
                return false;
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this,"Erro:" + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
        
    public void finalizarAplicacao()
    {
        if(conectado())
        {
            try
            {
                conexao.fecharConexao();
                LogAplicacao.stop();
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(this,"Erro:" + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
            }
        }
        super.finalizarAplicacao();
    }
    
    public static String obterCaminho()
    {
        return caminho;
    }
}

class LogAplicacao extends PrintStream
{
    static OutputStream arquivoLog;
    static PrintStream saida;
    static PrintStream erro;
    
    LogAplicacao(PrintStream ps)
    {
        super(ps);
    }
    
    public static void start(String arquivo) throws IOException
    {
        saida = System.out;
        erro = System.err;
        
        arquivoLog = new PrintStream(new BufferedOutputStream(new FileOutputStream(arquivo)));
        System.setOut(new LogAplicacao(System.out));
        System.setErr(new LogAplicacao(System.err));
    }
    
    public static void stop()
    {
        if(saida != null || erro != null)
        {
            System.setOut(saida);
            System.setErr(erro);
            try
            {
                arquivoLog.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public void write(int b)
    {
        try
        {
            arquivoLog.write(b);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            setError();
        }
        super.write(b);
    }
    
    public void write(byte buf[],int off, int len)
    {
        try
        {
            arquivoLog.write(buf,off,len);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            setError();
        }
        super.write(buf,off,len);
    }
}