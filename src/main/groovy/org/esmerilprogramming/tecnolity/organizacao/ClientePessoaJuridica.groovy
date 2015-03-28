package br.com.mentores.organizacao

class ClientePessoaJuridica extends PessoaJuridica {
  int codigo
  Calendario dataFundacao
  static final char FILIAL = 'F'
  static final char MATRIZ = 'M'
  char composicao
  static final char MICRO = 'C'
  static final char PEQUENO = 'P'
  static final char M\u00c9DIO = 'M'
  static final char GRANDE = 'G'
  char porte
  boolean exportacao
  String setorAtividade
  String missao
  String publicoAlvo
  String atividadeFim

}
