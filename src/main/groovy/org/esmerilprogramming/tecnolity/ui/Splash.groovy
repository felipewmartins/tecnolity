package br.com.mentores.ui;

import javax.swing.*;
import java.awt.*;

public class Splash extends JWindow
{
    private JLabel figura;
    
    public Splash(final String caminhoFigura) {
        final Container conteudo = this.getContentPane();
        conteudo.add(this.figura = new JLabel(new ImageIcon(caminhoFigura)), "Center");
        this.dimencionar(400, 260);
    }
    
    public void dimencionar(final int largura, final int altura) {
        final Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds((tela.width - largura) / 2, (tela.height - altura) / 2, largura, altura);
    }
}
