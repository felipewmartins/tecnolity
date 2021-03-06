package org.esmerilprogramming.tecnolity.administracao

import org.esmerilprogramming.tecnolity.aplicacao.Interface
import org.esmerilprogramming.tecnolity.util.*
import org.esmerilprogramming.tecnolity.util.PessoaFisica
import java.sql.*

class Colaborador extends PessoaFisica {
  String matricula
  String senha
  Departamento departamento
  String ramal
  boolean senhaAlterada
  Calendario calendario = new Calendario()
  def permissoes = []
  boolean colaboradorExiste = true
  String ddd

  Colaborador(String matricula, Conexao conexao) throws Exception {
    this.matricula = matricula
    ResultSet dadosColaborador, dadosDepartamento
    dadosColaborador = conexao.executarConsulta('select * from usuario where usuario = ' +  this.matricula)

    if (dadosColaborador.next()) {
      this.definirSenha(dadosColaborador.getString('senha'))
      this.definirSexo(dadosColaborador.getString('sexo').charAt(0))
      super.setNome(dadosColaborador.getString('nome_completo'))
      this.definirIdentidade(dadosColaborador.getString('identidade'))
      this.definirOrgaoEmissorIdentidade(dadosColaborador.getString('orgao_emissor_rg'))
      this.definirCpf(dadosColaborador.getString('cpf'))

      int codigoDepartamento = dadosColaborador.getInt('departamento')
      if (codigoDepartamento > 0) {
        dadosDepartamento = conexao.executarConsulta('select departamento from departamento where codigo = '  +  codigoDepartamento)
        if (dadosDepartamento.next()) {
          this.definirDepartamento(new Departamento(codigoDepartamento, dadosDepartamento.getString('departamento')))
        }
        dadosDepartamento.close()
      }
      this.definirLogradouro(dadosColaborador.getString('logradouro'))
      this.definirComplemento(dadosColaborador.getString('complemento'))
      this.definirBairro(dadosColaborador.getString('bairro'))
      this.definirCidade(dadosColaborador.getString('cidade'))
      String siglaEstado = dadosColaborador.getString('estado')
      if (siglaEstado != null) {
        this.setEstado(new Estado(siglaEstado))
      }
      this.definirCep(dadosColaborador.getString('cep'))
      this.setDDD(dadosColaborador.getString('ddd'))
      this.definirTelefone(dadosColaborador.getString('telefone'))
      this.definirRamal(dadosColaborador.getString('ramal'))
      this.definirCelular(dadosColaborador.getString('celular'))
      super.setEmail(dadosColaborador.getString('email'))
      this.senhaAlterada = dadosColaborador.getBoolean('senha_alterada')
    }
  }

  void definirTelefone(String telefone) throws Exception {
    if (telefone == null)
      return
        if (telefone.length() <= 8) {
          super.setTelefone(telefone)
        }
        else
        {
          Exception e = new Exception('N�mero de telefone inv�lido. Informe somente n�meros e sem espa�o.')
            throw e
        }
  }

  void definirCelular(String celular) throws Exception {
    if (celular == null) {
      return
    }
        if (celular.length() <= 8) {
           super.setCelular(celular)
        }
        else
        {
          Exception e = new Exception('N�mero de celular inv�lido. Informe somente n�meros e sem espa�o.')
            throw e
        }
  }

  boolean autenticarColaborador() throws Exception {

    // here we have a groovy SQL no need plain JDBC anymore ;]
    def db = Conexao.instance.db

    db.eachRow('select nome, senha, sexo, nome, identidade, orgao_emissor_rg, cpf, logradouro, complemento, cidade, cep from usuario') {
      matricula = it.nome
      senha = it.senha
      sexo = it.sexo
      nome = it.nome
      identidade = it.identidade
      orgaoIdentidade = it.orgao_emissor_rg
      CPF = it.cpf
      departamento = null
      logradouro = it.logradouro
      complemento = it.complemento
      cidade = it.cidade
      estado = null
      CEP = it.cep
    }
    matricula != null
  }

  void cadastrarColaborador() {
    Conexao conexao = new Conexao('T')
      boolean existente = false
      try {
        if (conexao.abrirConexao()) {
           ResultSet colaborador = conexao.executarConsulta('select usuario from usuario where usuario = ' +  this.matricula)
            if (colaborador.next()) {
               existente = true
            }
            else
            {
              String query = 'insert into usuario (usuario, senha, sexo, nome_completo, identidade, orgao_emissor_rg, cpf, departamento, logradouro, complemento, bairro, cidade, estado, cep, ddd, telefone, ramal, celular, email, senha_alterada) '
                query += 'values (' + this.matricula + ', ' + this.senha + ', ' + super.getSexo() + ', ' + super.getNome() + ', ' + super.getIdentidade() + ', ' + super.getOrgaoIdentidade() + ', ' + super.getCPF() + ', ' + this.departamento.obterCodigo() + ', ' + super.getLogradouro() + ', ' + super.getComplemento() + ', ' + super.getBairro() + ', ' + super.getCidade() + ', ' + this.obterEstado().getSigla() + ', ' + super.getCEP() + ', ' + this.getDDD() + ', ' + super.getTelefone() + ', ' + this.ramal + ', ' + super.getCelular() + ', ' + super.getEmail() + ', 1)'
                conexao.executarAtualizacao(query)
            }
          conexao.fecharConexao()
        }
      } catch (SQLException e) {
          e.printStackTrace()
    }
  }

  void alterarColaborador() {
    def db = Conexao.instance.db
    //TODO terminar esse
    db.execute 'update usuario set senha = ?, sexo = ?, nome_completo = ?, identidade = ?,  orgao_emissor_rg = ?, cpf = ?, departamento = ?, logradouro = ?, complemento = ?, bairro = ?, cidade = ?, estado = ?, cep = ?, ddd = ?, telefone = ?, ramal = ?, celular = ?, email = ?, senha_alterada = 1 where usuario = ?', senha, super.getSexo() , super.getNome() , super.getIdentidade() , super.getOrgaoIdentidade().trim() , super.getCPF() , ((this.departamento == null)?'NULL':'' + this.departamento.obterCodigo()) , super.getLogradouro() , super.getComplemento(), super.getBairro() , super.getCidade() , (this.obterEstado() != null?'' + this.obterEstado().getSigla() + '':null) ,super.getCEP() , this.getDDD() , super.getTelefone() , this.ramal , super.getCelular(), super.getEmail() , this.matricula
  }

  void excluirColaborador() {
    def db = Conexao.instance.db
    db.execute 'delete from usuario where usuario = ?' , matricula
  }

  boolean colaboradorTemPermissao(Interface inter) {
    true
  }

  List<Colaborador> carregarColaboradores() {
    def colaboradores = []
    colaboradores.add(null)
    def db = Conexao.instance.db
    def query = 'select usuario, nome_completo, senha from usuario order by usuario asc'
    db.eachRow(query) {
      colaboradores.add(new Colaborador(it.usuario, it.nome_completo, it.senha))
    }
    colaboradores
  }

  void carregarPermissoes() {
    Interface tela
    Permissao permissao
    def db = Conexao.instance.db
    def sql = '''select i.identificador as identificador,
 i.interface as interface, p.permissao as tipo_permissao from permissao p,
 interface i where i.identificador = p.interface and p.usuario = '${matricula}' order by i.identificador asc
'''
    db.eachRow(sql) {
      tela = new Interface(it.identificador as int, it.interface)
      permissao = new Permissao(tela, it.tipo_permissao.toCharArray()[0])
      permissoes.addElement(permissao)
    }
  }

  void definirPermissoes(List<Permissao> permissoes) throws SQLException, Exception {
    def db = Conexao.instance.db
    db.execute 'delete from permissao where usuario = ?', matricula
    for (int i = 0; i < permissoes.size(); i++) {
      db.execute 'insert into permissao (interface, usuario, permissao) values (' +  ((Permissao)permissoes.get(i)).tela.identificador + ', ' + matricula + ', ' + ((Permissao)permissoes.get(i)).tipoAcesso + ')'
    }
  }

  char verificarPermissao(Interface tela) {
    char tipoPermissao = '\u0000'
    for (int i=0;i < permissoes.size();i++) {
      if (((Permissao)permissoes.get(i)).tela.identificador == tela.identificador) {
        tipoPermissao = ((Permissao)permissoes.get(i)).tipoAcesso
      }
    }
    tipoPermissao
  }

}
