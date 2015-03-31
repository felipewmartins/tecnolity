package org.esmerilprogramming.tecnolity.util



class Pessoa {
  static final char PESSOAFISICA = 'F'
  static final char PESSOAJURIDICA = 'J'
  static final char OUTRO = 'O'
  char tipoCliente
  String nome
  String logradouro
  String numero
  String complemento
  String bairro
  String cidade
  Estado estado
  Pais pais
  String CEP
  String telefone
  String fax
  String celular
  String website
  String email
  String caixaPostal

  static boolean validarEmail(final String email) {
    final Pattern padrao = Pattern.compile("[a-z][a-z\\p{Punct}0-9]+@([a-z0-9]+\\p{Punct})+[a-z]{2,3}(\\p{Punct}[a-z]{2})?")
    final Matcher busca = padrao.matcher(email)
    busca.matches()
  }

  static String formatarCEP(final String cep) {
    if (cep == null) {
      return ""
    }
    if (cep.equals("")) {
      return ""
    }
    String cepFormatado = ""
      cepFormatado = "-" + cep.substring(cep.length() - 3)
      cepFormatado = "." + cep.substring(cep.length() - 6, cep.length() - 3) + cepFormatado
      cepFormatado = String.valueOf(cep.substring(0, cep.length() - 6)) + cepFormatado
      cepFormatado
  }
}
