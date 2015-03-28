package br.com.mentores.ui;

import javax.swing.*;
import javax.swing.text.*;

public class CampoTexto extends JTextField
{
    public CampoTexto(final int tamanho, final int tamanhoMaximo) {
        this(null, tamanho, tamanhoMaximo);
    }
    
    public CampoTexto(final String texto, final int tamanho, final int tamanhoMaximo) {
        super(new DocumentoTamanhoFixo(tamanhoMaximo), texto, tamanho);
    }
}
