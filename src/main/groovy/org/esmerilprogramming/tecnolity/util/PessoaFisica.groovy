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

}
