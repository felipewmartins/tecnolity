package br.com.mentores.ui;

import javax.swing.*;
import java.awt.*;

public class Dialogo extends JDialog
{
    public static final char ACESSO_INCLUSAO = 'I';
    public static final char ACESSO_ALTERACAO = 'A';
    public static final char ACESSO_LEITURA = 'L';
    private int identificador;
    private char acesso;
    private Aplicacao aplicacao;
    private Container conteudo;
    private JPanel pnlAreaGuia;
    private JPanel pnlAreaDados;
    private JPanel pnlAreaComandos;
    private JPanel pnlAreaCabecalho;
    private GridBagLayout gridbag;
    private GridBagConstraints gbc;
    
    public Dialogo() {
        super(new Aplicacao("Di\u00e1logo"), true);
        this.conteudo = this.getContentPane();
        this.gridbag = new GridBagLayout();
        this.gbc = new GridBagConstraints();
        this.gbc.fill = 0;
        this.gbc.anchor = 17;
        this.gbc.insets.bottom = 2;
        this.gbc.insets.left = 2;
        this.gbc.insets.right = 2;
        this.gbc.insets.top = 2;
    }
    
    public Dialogo(final Aplicacao aplicacao, final String titulo) {
        super(aplicacao, true);
        this.setTitle(titulo);
        this.aplicacao = aplicacao;
        this.conteudo = this.getContentPane();
        this.gridbag = new GridBagLayout();
        this.gbc = new GridBagConstraints();
        this.gbc.fill = 0;
        this.gbc.anchor = 17;
        this.gbc.insets.bottom = 2;
        this.gbc.insets.left = 2;
        this.gbc.insets.right = 2;
        this.gbc.insets.top = 2;
    }
    
    protected void dimencionar() {
        this.pack();
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(screenSize.width / 2 - this.getBounds().width / 2, screenSize.height / 2 - this.getBounds().height / 2 - 30, this.getBounds().width, this.getBounds().height);
    }
    
    public void dimencionar(final int largura, final int altura) {
        final Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds((tela.width - largura) / 2, (tela.height - altura) / 2, largura, altura);
    }
    
    protected void adicionarComponente(final JPanel painel, final Component c, final int linha, final int coluna, final int largura, final int altura) {
        this.gbc.gridx = coluna;
        this.gbc.gridy = linha;
        this.gbc.gridwidth = largura;
        this.gbc.gridheight = altura;
        this.gridbag.setConstraints(c, this.gbc);
        painel.add(c);
    }
    
    protected int getIdentificador() {
        return this.identificador;
    }
    
    protected Aplicacao getAplicacao() {
        return this.aplicacao;
    }
    
    protected Container getConteudo() {
        return this.conteudo;
    }
    
    protected JPanel getPnlAreaCabecalho() {
        return this.pnlAreaCabecalho;
    }
    
    protected JPanel getPnlAreaGuia() {
        return this.pnlAreaGuia;
    }
    
    protected JPanel getPnlAreaDados() {
        return this.pnlAreaDados;
    }
    
    protected JPanel getPnlAreaComandos() {
        return this.pnlAreaComandos;
    }
    
    protected GridBagLayout getGridbag() {
        return this.gridbag;
    }
    
    protected GridBagConstraints getGbc() {
        return this.gbc;
    }
    
    protected void setIdentificador(final int identificador) {
        this.identificador = identificador;
    }
    
    protected void setAplicacao(final Aplicacao aplicacao) {
        this.aplicacao = aplicacao;
    }
    
    protected void setConteudo(final Container conteudo) {
        this.conteudo = conteudo;
    }
    
    protected void setPnlAreaCabecalho(final JPanel pnlAreaCabecalho) {
        this.pnlAreaCabecalho = pnlAreaCabecalho;
        this.conteudo.add(this.pnlAreaCabecalho, "North");
    }
    
    protected void setPnlAreaGuia(final JPanel pnlAreaGuia) {
        this.pnlAreaGuia = pnlAreaGuia;
        this.conteudo.add(this.pnlAreaGuia, "West");
    }
    
    protected void setPnlAreaDados(final JPanel pnlAreaDados) {
        this.pnlAreaDados = pnlAreaDados;
        this.conteudo.add(this.pnlAreaDados, "Center");
    }
    
    protected void setPnlAreaComandos(final JPanel pnlAreaComandos) {
        this.pnlAreaComandos = pnlAreaComandos;
        this.conteudo.add(this.pnlAreaComandos, "South");
    }
    
    protected void setGridbag(final GridBagLayout gridbag) {
        this.gridbag = gridbag;
    }
    
    protected void setGbc(final GridBagConstraints gbc) {
        this.gbc = gbc;
    }
    
    public char getAcesso() {
        return this.acesso;
    }
    
    public void setAcesso(final char acesso) {
        if (acesso != '\0' && (acesso == 'I' || acesso == 'A' || acesso == 'L')) {
            this.acesso = acesso;
        }
    }
}
