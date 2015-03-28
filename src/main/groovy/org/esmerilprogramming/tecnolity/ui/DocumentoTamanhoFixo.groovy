package org.esmerilprogramming.ui

import java.awt.*
import javax.swing.text.*

class DocumentoTamanhoFixo extends PlainDocument
{
  private int tamanhoMaximo

  DocumentoTamanhoFixo(final int tamanhoMaximo) {
    this.tamanhoMaximo = tamanhoMaximo
  }

  void insertString(final int offset, final String str, final AttributeSet a) throws BadLocationException {
    if (this.getLength() + str.length() > this.tamanhoMaximo) {
      Toolkit.getDefaultToolkit().beep()
    }
    else {
      super.insertString(offset, str, a)
    }
  }
}
