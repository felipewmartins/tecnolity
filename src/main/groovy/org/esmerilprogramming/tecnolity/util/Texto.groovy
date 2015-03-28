package org.esmerilprogramming.tecnolity.util

import java.util.*

class Texto {
  static String formatarTextoHTML(final String texto) {
    return texto.replaceAll("\n", "<br>")
  }

  static String getStringTamanhoFixo(final String texto, final int tamanho) {
    if (texto.length() > tamanho) {
      return texto.substring(0, tamanho)
    }
    if (texto.length() < tamanho) {
      String espacosBranco = ""
        for (int i = 0 ; i < tamanho - texto.length() ; ++i) {
          espacosBranco = String.valueOf(espacosBranco) + " "
        }
      return String.valueOf(texto) + espacosBranco
    }
    return texto
  }

  static String getNumeroTamanhoFixo(final String numero, final int tamanho, final String preenchimento) {
    if (numero.length() > tamanho) {
      return numero.substring(numero.length() - tamanho - 1, numero.length())
    }
    if (numero.length() < tamanho) {
      String zeros = ""
        for (int i = 0 ; i < tamanho - numero.length() ; ++i) {
          zeros = String.valueOf(zeros) + preenchimento
        }
      return String.valueOf(zeros) + numero
    }
    return numero
  }

  static String ajustarTexto(final String texto) {
    return texto.replaceAll("'", "''")
  }

  static String[] getTextoAlinhado(final String texto, final int largura) {
    String[] linhas
      if (texto == null) {
        linhas = ['']
      }
      else if (texto.length() > largura) {
        linhas = new String[(texto.length() % largura == 0) ? (texto.length() / largura) : (texto.length() / largura + 1)]
          String token = ""
          int numeroLinha = 0
          final StringTokenizer st = new StringTokenizer(texto)
          String linha = ""
          while (st.hasMoreTokens()) {
            token = st.nextToken()
              String espaco
              if (linha.length() == 0) {
                espaco = ""
              }
              else {
                espaco = " "
              }
            if (linha.length() + espaco.length() + token.length() == largura) {
              linhas[numeroLinha++] = linha
                linha = token
            }
            else if (linha.length() + espaco.length() + token.length() < largura) {
              if (numeroLinha == linhas.length - 1) {
                linha = String.valueOf(linha) + espaco + token
                  linhas[numeroLinha] = linha
              }
              else {
                linha = String.valueOf(linha) + espaco + token
              }
            }
            else {
              if (linha.length() + espaco.length() + token.length() <= largura) {
                continue
              }
              linhas[numeroLinha++] = linha
                linha = token
            }
          }
      }
      else {
        linhas = null //new String[] { texto }
      }
    return linhas
  }
}
