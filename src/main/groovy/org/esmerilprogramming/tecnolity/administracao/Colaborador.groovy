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
  Vector permissoes = new Vector()
  boolean colaboradorExiste = true
  String ddd

  Colaborador(String matricula, String senha, char sexo, String nomeCompleto,
        String identidade, String orgaoEmissorIdentidade, String cpf,
        Departamento departamento, String logradouro, String complemento,
        String bairro, String cidade, Estado estado, String cep, String ddd,
        String telefone, String ramal, String celular, String email,
        boolean senhaAlterada) throws Exception {

      super(nomeCompleto, logradouro, "", complemento, bairro, cidade, estado,
          cep, telefone, celular, email, cpf, identidade,
          orgaoEmissorIdentidade, sexo)
        this.definirMatricula(matricula)
        this.definirSenha(senha)
        this.definirDepartamento(departamento)
        this.definirRamal(ramal)
        this.senhaAlterada(senhaAlterada)
    }

  Colaborador(String matricula, String senha) {
    this.matricula = matricula
    this.senha = senha
  }

  Colaborador(String matricula, String nomeCompleto, String senha) {
    this.matricula = matricula
    super.setNome(nomeCompleto)
    this.senha = senha
  }

  Colaborador(String matricula, Conexao conexao) throws Exception {
    this.definirMatricula(matricula)

      ResultSet dadosColaborador, dadosDepartamento

      dadosColaborador = conexao.executarConsulta("select * from usuario where usuario = '" +  this.matricula + "'")

      if(dadosColaborador.next()) {
        this.definirSenha(dadosColaborador.getString("senha"))
          this.definirSexo(dadosColaborador.getString("sexo").charAt(0))
          super.setNome(dadosColaborador.getString("nome_completo"))
          this.definirIdentidade(dadosColaborador.getString("identidade"))
          this.definirOrgaoEmissorIdentidade(dadosColaborador.getString("orgao_emissor_rg"))
          this.definirCpf(dadosColaborador.getString("cpf"))

          int codigoDepartamento = dadosColaborador.getInt("departamento")
          if(codigoDepartamento > 0) {
            dadosDepartamento = conexao.executarConsulta("select departamento from departamento where codigo = "  +  codigoDepartamento)
              if(dadosDepartamento.next()) {
                this.definirDepartamento(new Departamento(codigoDepartamento, dadosDepartamento.getString("departamento")))
              }
            dadosDepartamento.close()
          }
        this.definirLogradouro(dadosColaborador.getString("logradouro"))
        this.definirComplemento(dadosColaborador.getString("complemento"))
        this.definirBairro(dadosColaborador.getString("bairro"))
        this.definirCidade(dadosColaborador.getString("cidade"))
        String siglaEstado = dadosColaborador.getString("estado")
        if(siglaEstado != null) {
          this.setEstado(new Estado(siglaEstado))
        }
        this.definirCep(dadosColaborador.getString("cep"))
          this.setDDD(dadosColaborador.getString("ddd"))
          this.definirTelefone(dadosColaborador.getString("telefone"))
          this.definirRamal(dadosColaborador.getString("ramal"))
          this.definirCelular(dadosColaborador.getString("celular"))
          super.setEmail(dadosColaborador.getString("email"))
          this.senhaAlterada = dadosColaborador.getBoolean("senha_alterada")
      }
  }

  Colaborador(String matricula) {
    this.matricula = matricula
  }

  void definirTelefone(String telefone) throws Exception {
    if(telefone == null)
      return
        if(telefone.length() <= 8) {
          super.setTelefone(telefone)
        }
        else
        {
          Exception e = new Exception("Número de telefone inválido. Informe somente números e sem espaço.")
            throw e
        }
  }

  void definirCelular(String celular) throws Exception {
    if(celular == null)
      return
        if(celular.length() <= 8) {
          super.setCelular(celular)
        }
        else
        {
          Exception e = new Exception("Número de celular inválido. Informe somente números e sem espaço.")
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
        if(conexao.abrirConexao()) {
          ResultSet colaborador = conexao.executarConsulta("select usuario from usuario where usuario = '" +  this.matricula + "'")
            if(colaborador.next()) {
              existente = true
            }
            else
            {
              String query = "insert into usuario (usuario, senha, sexo, nome_completo, identidade, orgao_emissor_rg, cpf, departamento, logradouro, complemento, bairro, cidade, estado, cep, ddd, telefone, ramal, celular, email, senha_alterada) "
                query += "values ('" + this.matricula + "', '" + this.senha + "', '" + super.getSexo() + "', '" + super.getNome() + "', '" + super.getIdentidade() + "', '" + super.getOrgaoIdentidade() + "', '" + super.getCPF() + "', '" + this.departamento.obterCodigo() + "', '" + super.getLogradouro() + "', '" + super.getComplemento() + "', '" + super.getBairro() + "', '" + super.getCidade() + "', '" + this.obterEstado().getSigla() + "', '" + super.getCEP() + "', '" + this.getDDD() + "', '" + super.getTelefone() + "', '" + this.ramal + "', '" + super.getCelular() + "', '" + super.getEmail() + "', '1')"
                conexao.executarAtualizacao(query)
            }
          conexao.fecharConexao()
        }
      } catch (SQLException e) {
          e.printStackTrace()
    }
  }

  void alterarColaborador() {
    Conexao conexao = new Conexao('T')
      conexao.abrirConexao()
      String query = "update usuario set senha = '" +  this.senha + "', sexo = '" + super.getSexo() + "', nome_completo = '" + super.getNome() + "', identidade = '" + super.getIdentidade() + "', orgao_emissor_rg = '" + super.getOrgaoIdentidade().trim() + "', cpf = '" + super.getCPF() + "', departamento = " + ((this.departamento == null)?"NULL":"" + this.departamento.obterCodigo()) + ", logradouro = '" + super.getLogradouro() + "', complemento = '" + super.getComplemento() + "', bairro = '" + super.getBairro() + "', cidade = '" + super.getCidade() + "', estado = " + (this.obterEstado() != null?"'" + this.obterEstado().getSigla() + "'":null) + ", cep = '" + super.getCEP() + "', ddd = '" + this.getDDD() + "', telefone = '" + super.getTelefone() + "', ramal = '" + this.ramal + "', celular = '" + super.getCelular() + "', email = '" + super.getEmail() + "', senha_alterada = 1 where usuario = '" + this.matricula + "'"
      conexao.executarAtualizacao(query)
      conexao.fecharConexao()
  }

  void excluirColaborador() {
    Conexao conexao = new Conexao('T')
      conexao.abrirConexao()
      String query = "delete from usuario where usuario = '" +  this.matricula + "'"
      conexao.executarAtualizacao(query)
      conexao.fecharConexao()
  }

  boolean colaboradorTemPermissao(Interface inter) {
    return true
  }

  Vector carregarColaboradores(Conexao conexao) throws Exception {
    ResultSet dadosColaborador
      Vector colaboradores = new Vector()
      try {
          dadosColaborador = conexao.executarConsulta("select usuario, nome_completo, senha from usuario order by usuario asc")
          colaboradores.addElement(null)
          while(dadosColaborador.next()) {
            colaboradores.addElement(new Colaborador(dadosColaborador.getString("usuario"), dadosColaborador.getString("nome_completo"), dadosColaborador.getString("senha")))
          }
        dadosColaborador.close()
      }
    catch (SQLException e) {
      e.printStackTrace()
    }
    return colaboradores
  }

  void carregarPermissoes(Conexao conexao) throws SQLException {
    Interface tela
    Permissao permissao
    permissoes.removeAllElements()
    def db = Conexao.instance.db
    def consulta = '''select i.identificador as identificador,
 i.interface as interface, i.descricao as descricao_interface, p.permissao as tipo_permissao from permissao p,
 interface i where i.identificador = p.interface and p.usuario = '" +  this.matricula + "' order by i.identificador asc
'''
    db.eachRow(consulta) {
      tela = new Interface(it.identificador as int, it.interface, it.descricao_interface)
      permissao = new Permissao(tela, it.tipo_permissao.toCharArray()[0])
      permissoes.addElement(permissao)
    }
  }

  void definirPermissoes(Vector permissoes) throws SQLException, Exception {
    Conexao conexao = new Conexao('T')
      conexao.abrirConexao()
      ResultSet dadosColaborador

      conexao.executarAtualizacao("delete from permissao where usuario = '" +  this.matricula + "'")
      for(int i = 0;i < permissoes.size();i++) {
        conexao.executarAtualizacao("insert into permissao (interface, usuario, permissao) values (" +  ((Permissao)permissoes.get(i)).obterTela().obterIdentificador() + ", '" + this.matricula + "', '" + ((Permissao)permissoes.get(i)).obterTipoAcesso() + "')")
      }
    conexao.fecharConexao()
  }

  char verificarPermissao(Interface tela) {
    char tipoPermissao = '\u0000'
      for(int i=0;i < permissoes.size();i++) {
        if(((Permissao)permissoes.get(i)).obterTela().obterIdentificador() == tela.obterIdentificador()) {
          tipoPermissao = ((Permissao)permissoes.get(i)).obterTipoAcesso()
        }
      }
    return tipoPermissao
  }


}
