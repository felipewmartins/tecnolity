package br.com.mentores.ui

import javax.swing.*
import javax.swing.text.*

class CampoTexto extends JTextField
{
  CampoTexto(final int tamanho, final int tamanhoMaximo) {
    this(null, tamanho, tamanhoMaximo)
  }

  CampoTexto(final String texto, final int tamanho, final int tamanhoMaximo) {
    super(new DocumentoTamanhoFixo(tamanhoMaximo), texto, tamanho)
  }
}
