package org.esmerilprogramming.tecnolity.util

class PessoaFisica extends Pessoa {

   static final char FEMININO = 'F'
   static final char MASCULINO = 'M'
   static final char SOLTEIRO = 'S'
   static final char CASADO = 'C'
   static final char DESQUITADO = 'D'
   static final char SEPARADO = 'P'
   static final char VIUVO = 'V'
  char sexo
  Calendario dataNascimento
  char estadoCivil
  String CPF
  String identidade
  String orgaoIdentidade
  Estado estadoIdentidade

  static boolean validarCpf(String cpf) {
    final char[] chrCpf = cpf.toCharArray()
      final int[] digitos = new int[11]
      int soma = 0
      String numerosCpf = ""
      for (int i = 0;  i < chrCpf.length; ++i) {
        if (Character.isDigit(chrCpf[i])) {
          numerosCpf = String.valueOf(numerosCpf) + chrCpf[i]
        }
      }
    cpf = numerosCpf
      if (cpf.length() != 11) {
        return false
      }
    if (cpf.equals("00000000000")) {
      return false
    }
    digitos[0] = cpf.substring(0, 1) as int
    digitos[1] = cpf.substring(1, 2) as int
    digitos[2] = cpf.substring(2, 3) as int
    digitos[3] = cpf.substring(3, 4) as int
    digitos[4] = cpf.substring(4, 5) as int
    digitos[5] = cpf.substring(5, 6) as int
    digitos[6] = cpf.substring(6, 7) as int
    digitos[7] = cpf.substring(7, 8) as int
    digitos[8] = cpf.substring(8, 9) as int
    digitos[9] = cpf.substring(9, 10) as int
    digitos[10] = cpf.substring(10) as int
    soma = 10 * digitos[0] + 9 * digitos[1] + 8 * digitos[2] + 7 * digitos[3] + 6 * digitos[4] + 5 * digitos[5] + 4 * digitos[6] + 3 * digitos[7] + 2 * digitos[8]
    soma -= 11 * soma / 11
    int resultado
    if (soma == 0 || soma == 1) {
      resultado = 0
    }
    else {
      resultado = 11 - soma
    }
    if (resultado == digitos[9]) {
      soma = digitos[0] * 11 + digitos[1] * 10 + digitos[2] * 9 + digitos[3] * 8 + digitos[4] * 7 + digitos[5] * 6 + digitos[6] * 5 + digitos[7] * 4 + digitos[8] * 3 + digitos[9] * 2
      soma -= 11 * (soma / 11)
      int resultadoDv
      if (soma == 0 || soma == 1) {
        resultadoDv = 0
      }
      else {
        resultadoDv = 11 - soma
      }
      return resultadoDv == digitos[10]
    }
    return false
  }

  static String formatarCPF(final String cpf) {
    if (cpf) {
      String cpfFormatado = ''
      cpfFormatado = "-" + cpf.substring(cpf.length() - 2)
      cpfFormatado = "." + cpf.substring(cpf.length() - 5, cpf.length() - 2) + cpfFormatado
      cpfFormatado = "." + cpf.substring(cpf.length() - 8, cpf.length() - 5) + cpfFormatado
      cpfFormatado = String.valueOf(cpf.substring(0, cpf.length() - 8)) + cpfFormatado
      return cpfFormatado
    }
    return ''
  }
}
