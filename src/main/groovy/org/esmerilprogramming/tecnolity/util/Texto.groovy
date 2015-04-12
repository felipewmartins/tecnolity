package org.esmerilprogramming.tecnolity.util

class Texto {
  static String formatarTextoHTML(final String texto) {
    texto.replaceAll('\n', '<br>')
  }

  static String getStringTamanhoFixo(final String texto, final int tamanho) {
    def retorno
    if (texto.length() > tamanho) {
      retorno = texto[0..tamanho - 1]
    }
    if (texto.length() < tamanho) {
      String espacosBranco = ''
        for (int i = 0;  i < tamanho - texto.length();  ++i) {
          espacosBranco = String.valueOf(espacosBranco)  +  ' '
        }
      retorno = String.valueOf(texto)  +  espacosBranco
    }
    retorno
  }

  static String getNumeroTamanhoFixo(final String numero, final int tamanho, final String preenchimento) {
    def retorno
    if (numero.length() > tamanho) {
      int inicio = numero.length() - tamanho - 1
      retorno = numero[inicio..-1]
    }
    if (numero.length() < tamanho) {
      String zeros = ''
        for (int i = 0 ; i < tamanho - numero.length();  ++i) {
          zeros = String.valueOf(zeros)  +  preenchimento
        }
      retorno = String.valueOf(zeros)  +  numero
    }
    retorno
  }

  static String[] getTextoAlinhado(final String texto, final int largura) {
    String[] linhas
      if (texto == null) {
        linhas = ['']
      }
      else if (texto.length() > largura) {
        linhas = new String[(texto.length() % largura == 0) ? (texto.length() / largura) : (texto.length() / largura  +  1)]
          String token = ''
          int numeroLinha = 0
          StringTokenizer tokenizer = new StringTokenizer(texto)
          String linha = ''
          while (tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken()
              String espaco
              if (linha.length() == 0) {
                espaco = ''
              }
              else {
                espaco = ' '
              }
            if (linha.length()  +  espaco.length() + token.length() == largura) {
              linhas[numeroLinha++] = linha
                linha = token
            }
            else if (linha.length()  +  espaco.length() + token.length() < largura) {
              if (numeroLinha == linhas.length - 1) {
                linha = String.valueOf(linha)  +  espaco + token
                  linhas[numeroLinha] = linha
              }
              else {
                linha = String.valueOf(linha)  +  espaco + token
              }
            }
            else {
              if (linha.length()  +  espaco.length() + token.length() <= largura) {
                continue
              }
              linhas[numeroLinha++] = linha
                linha = token
            }
          }
      }
      else {
        linhas = [texto]
      }
    linhas
  }
}
