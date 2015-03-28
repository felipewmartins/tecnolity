package br.com.mentores.ui;

import java.awt.*;
import javax.swing.text.*;

public class DocumentoTamanhoFixo extends PlainDocument
{
    private int tamanhoMaximo;
    
    public DocumentoTamanhoFixo(final int tamanhoMaximo) {
        this.tamanhoMaximo = tamanhoMaximo;
    }
    
    public void insertString(final int offset, final String str, final AttributeSet a) throws BadLocationException {
        if (this.getLength() + str.length() > this.tamanhoMaximo) {
            Toolkit.getDefaultToolkit().beep();
        }
        else {
            super.insertString(offset, str, a);
        }
    }
}
