package org.esmerilprogramming.tecnolity.util

import java.math.*

import java.text.*

class Numero extends DecimalFormat {
  private List<String> nro
  private BigInteger num
  private String[][] qualificadores
  private String[][] numeros

  Numero(final BigDecimal dec) {
    nro = new ArrayList()
    qualificadores = [['centavo', 'centavos'], ['', ''], ['mil', 'mil'], ['milh\u00e3o', 'milh\u00f5es'], ['bilh\u00e3o', 'bilh\u00f5es' ], [ 'trilh\u00e3o', 'trilh\u00f5es' ], ['quatrilh\u00e3o', 'quatrilh\u00f5es' ], ['quintilh\u00e3o', 'quintilh\u00f5es' ], ['sextilh\u00e3o', 'sextilh\u00f5es'], ['septilh\u00e3o', 'septilh\u00f5es']]
    numeros = [['zero', 'um', 'dois', 'tr\u00eas', 'quatro', 'cinco', 'seis', 'sete', 'oito', 'nove', 'dez', 'onze', 'doze', 'treze', 'quatorze', 'quinze', 'desesseis', 'desessete', 'dezoito', 'desenove'], ['vinte', 'trinta', 'quarenta', 'cinquenta', 'sessenta', 'setenta', 'oitenta', 'noventa'], ['cem', 'cento', 'duzentos', 'trezentos', 'quatrocentos', 'quinhentos', 'seiscentos', 'setecentos', 'oitocentos', 'novecentos']]
    setNumber(dec)
  }

  Numero(final double dec) {
    nro = new ArrayList()
    qualificadores = [['centavo', 'centavos' ], [ '', '' ], [ 'mil', 'mil' ], [ 'milh\u00e3o', 'milh\u00f5es' ], [ 'bilh\u00e3o', 'bilh\u00f5es' ], [ 'trilh\u00e3o', 'trilh\u00f5es' ], [ 'quatrilh\u00e3o', 'quatrilh\u00f5es' ], [ 'quintilh\u00e3o', 'quintilh\u00f5es' ], [ 'sextilh\u00e3o', 'sextilh\u00f5es' ], [ 'septilh\u00e3o', 'septilh\u00f5es' ]]
    numeros = [[ 'zero', 'um', 'dois', 'tr\u00eas', 'quatro', 'cinco', 'seis', 'sete', 'oito', 'nove', 'dez', 'onze', 'doze', 'treze', 'quatorze', 'quinze', 'desesseis', 'desessete', 'dezoito', 'desenove' ], [ 'vinte', 'trinta', 'quarenta', 'cinquenta', 'sessenta', 'setenta', 'oitenta', 'noventa' ], [ 'cem', 'cento', 'duzentos', 'trezentos', 'quatrocentos', 'quinhentos', 'seiscentos', 'setecentos', 'oitocentos', 'novecentos']]
    setNumber(dec)
  }

  void setNumber(final BigDecimal dec) {
    num = dec.setScale(2, 4).multiply(BigDecimal.valueOf(100L)).toBigInteger()
      this.nro.clear()
      if (this.num.equals(BigInteger.ZERO)) {
        this.nro.add(new Integer(0))
          this.nro.add(new Integer(0))
      }
      else {
        this.addRemainder(100)
          while (!this.num.equals(BigInteger.ZERO)) {
            this.addRemainder(1000)
          }
      }
  }

  void setNumber(final double dec) {
    this.setNumber(new BigDecimal(dec))
  }

  void show() {
    Iterator valores = this.nro.iterator()
    while (valores.hasNext()) {
      println (int)valores.next()
    }
    println this.toString()
  }

  String toString() {
    StringBuffer buf = new StringBuffer()
      int numero = this.nro.get(0)
      for (int ct = nro.size() - 1 ; ct > 0 ; --ct) {
        if (buf.length() > 0 && !this.ehGrupoZero(ct)) {
          buf.append(' e ')
        }
        buf.append(this.numToString(this.nro.get(ct), ct))
      }
    if (buf.length() > 0) {
      if (this.ehUnicoGrupo()) {
        buf.append(' de ')
      }
      while (buf.toString().endsWith(' ')) {
        buf.setLength(buf.length() - 1)
      }
      if (this.ehPrimeiroGrupoUm()) {
        buf.insert(0, 'h')
      }
      if (this.nro.size() == 2 && this.nro.get(1) == 1) {
        buf.append(' real')
      }
      else {
        buf.append(' reais')
      }
      if (this.nro.get(0) != 0) {
        buf.append(' e ')
      }
    }
    if (this.nro.get(0) != 0) {
      buf.append(this.numToString(this.nro.get(0), 0))
    }
    return buf.toString()
  }

  private boolean ehPrimeiroGrupoUm() {
    this.nro.get(this.nro.size() - 1) == 1
  }

  private void addRemainder(final int divisor) {
    BigInteger[] newNum = this.num.divideAndRemainder(BigInteger.valueOf(divisor))
    this.nro.add(new Integer(newNum[1].intValue()))
    this.num = newNum[0]
  }

  private boolean temMaisGrupos(int ps) {
    while (ps > 0) {
      if (this.nro.get(ps) != 0) {
        return true
      }
      --ps
    }
    false
  }

  private boolean ehUltimoGrupo(final int ps) {
    ps > 0 && this.nro.get(ps) != 0 && !this.temMaisGrupos(ps - 1)
  }

  private boolean ehUnicoGrupo() {
    if (this.nro.size() <= 3) {
      return false
    }
    if (!this.ehGrupoZero(1) && !this.ehGrupoZero(2)) {
      return false
    }
    boolean hasOne = false
      for (int i = 3 ; i < nro.size() ; ++i) {
        if (this.nro.get(i) != 0) {
          if (hasOne) {
            return false
          }
          hasOne = true
        }
      }
    return true
  }

  private boolean ehGrupoZero(final int ps) {
    return ps <= 0 || ps >= this.nro.size() || this.nro.get(ps) == 0
  }

  private String numToString(final int numero, final int escala) {
    final int unidade = numero % 10
    int dezena = numero % 100
    final int centena = numero / 100
    StringBuffer buf = new StringBuffer()
    if (numero != 0) {
      if (centena != 0) {
        if (dezena == 0 && centena == 1) {
          buf.append(this.numeros[2][0])
        }
        else {
          buf.append(this.numeros[2][centena])
        }
      }
      if (buf.length() > 0 && dezena != 0) {
        buf.append(' e ')
      }
      if (dezena > 19) {
        dezena /= 10
        buf.append(this.numeros[1][dezena - 2])
        if (unidade != 0) {
          buf.append(' e ')
            buf.append(this.numeros[0][unidade])
          }
      }
      else if (centena == 0 || dezena != 0) {
        buf.append(this.numeros[0][dezena])
      }
      buf.append(' ')
      if (numero == 1) {
        buf.append(this.qualificadores[escala][0])
      }
      else {
        buf.append(this.qualificadores[escala][1])
      }
    }
    buf.toString()
  }

  static String inverterSeparador(String numero) {
    if (numero) {
      if (numero.indexOf('.') >= 0) {
        return numero.replace('.', ', ')
      }
      if (numero.indexOf(', ') >= 0) {
        return numero.replace(', ', '.')
      }
    }
    else {
      numero = '0'
    }
    numero
  }

}
