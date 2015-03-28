package br.com.mentores.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Aplicacao extends JFrame implements WindowListener
{
    public Aplicacao(final String tituloJanela) {
        super(tituloJanela);
        this.addWindowListener(this);
    }
    
    public void finalizarAplicacao() {
        System.exit(0);
    }
    
    public void maximizar() {
        final Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(0, 0, tela.width, tela.height - 28);
    }
    
    public void windowOpened(final WindowEvent evento) {
    }
    
    public void windowClosing(final WindowEvent evento) {
        this.finalizarAplicacao();
    }
    
    public void windowClosed(final WindowEvent evento) {
    }
    
    public void windowIconified(final WindowEvent evento) {
    }
    
    public void windowDeiconified(final WindowEvent evento) {
    }
    
    public void windowActivated(final WindowEvent evento) {
    }
    
    public void windowDeactivated(final WindowEvent evento) {
    }
}
