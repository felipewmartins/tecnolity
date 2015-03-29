package org.esmerilprogramming.tecnolity.administracao;

import org.esmerilprogramming.tecnolity.aplicacao.Interface;
import org.esmerilprogramming.tecnolity.util.*;
import org.esmerilprogramming.tecnolity.util.PessoaFisica;
import java.sql.*;
import java.util.Vector;

class Colaborador extends PessoaFisica
{
  private String matricula;
  private String senha;
  private Departamento departamento;
  private String ramal;
  private boolean senhaAlterada;
  private Calendario calendario = new Calendario();
  private Vector permissoes = new Vector();
  private boolean colaboradorExiste = true;
  private String ddd;

  public Colaborador(String matricula, String senha, char sexo, String nomeCompleto,
      String identidade, String orgaoEmissorIdentidade, String cpf,
      Departamento departamento, String logradouro, String complemento,
      String bairro, String cidade, Estado estado, String cep, String ddd,
      String telefone, String ramal, String celular, String email,
      boolean senhaAlterada) throws Exception
  {
    super(nomeCompleto, logradouro,"", complemento, bairro, cidade, estado,
        cep, telefone, celular, email, cpf, identidade,
        orgaoEmissorIdentidade, sexo);
    this.definirMatricula(matricula);
    this.definirSenha(senha);
    this.definirDepartamento(departamento);
    this.definirRamal(ramal);
    this.senhaAlterada(senhaAlterada);
  }

  public Colaborador(String matricula, String senha) throws Exception
  {
    this.definirMatricula(matricula);
    this.definirSenha(senha);
  }

  public Colaborador(String matricula, String nomeCompleto, String senha) throws Exception
  {
    this.definirMatricula(matricula);
    super.setNome(nomeCompleto);
    if(!senha.equals("public"))
      this.definirSenha(senha);
  }

  public Colaborador(String matricula, Conexao conexao) throws Exception
  {
    this.definirMatricula(matricula);

    ResultSet dadosColaborador, dadosDepartamento;

    dadosColaborador = conexao.executarConsulta("select * from usuario where usuario = '"+ this.matricula +"'");

    if(dadosColaborador.next())
    {
      this.definirSenha(dadosColaborador.getString("senha"));
      this.definirSexo(dadosColaborador.getString("sexo").charAt(0));
      super.setNome(dadosColaborador.getString("nome_completo"));
      this.definirIdentidade(dadosColaborador.getString("identidade"));
      this.definirOrgaoEmissorIdentidade(dadosColaborador.getString("orgao_emissor_rg"));
      this.definirCpf(dadosColaborador.getString("cpf"));

      int codigoDepartamento = dadosColaborador.getInt("departamento");
      if(codigoDepartamento > 0)
      {
        dadosDepartamento = conexao.executarConsulta("select departamento from departamento where codigo = " + codigoDepartamento);
        if(dadosDepartamento.next())
        {
          this.definirDepartamento(new Departamento(codigoDepartamento,dadosDepartamento.getString("departamento")));
        }
        dadosDepartamento.close();
      }
      this.definirLogradouro(dadosColaborador.getString("logradouro"));
      this.definirComplemento(dadosColaborador.getString("complemento"));
      this.definirBairro(dadosColaborador.getString("bairro"));
      this.definirCidade(dadosColaborador.getString("cidade"));
      String siglaEstado = dadosColaborador.getString("estado");
      if(siglaEstado != null)
      {
        this.setEstado(new Estado(siglaEstado));
      }
      this.definirCep(dadosColaborador.getString("cep"));
      this.setDDD(dadosColaborador.getString("ddd"));
      this.definirTelefone(dadosColaborador.getString("telefone"));
      this.definirRamal(dadosColaborador.getString("ramal"));
      this.definirCelular(dadosColaborador.getString("celular"));
      super.setEmail(dadosColaborador.getString("email"));
      this.senhaAlterada = dadosColaborador.getBoolean("senha_alterada");
    }
  }

  public Colaborador(String matricula) throws Exception
  {
    this.definirMatricula(matricula);
  }

  public Colaborador(){}

  public String obterMatricula()
  {
    return this.matricula;
  }

  public String obterSenha()
  {
    return this.senha;
  }

  public char obterSexo()
  {
    return super.getSexo();
  }

  public Departamento obterDepartamento()
  {
    return this.departamento;
  }

  public Estado obterEstado()
  {
    return (Estado)this.getEstado();
  }

  public String obterRamal()
  {
    return this.ramal;
  }

  public boolean senhaAlterada()
  {
    return this.senhaAlterada;
  }

  public void definirMatricula(String matricula) throws Exception
  {
    if(matricula != null)
    {
      if(matricula.equals(""))
      {
        Exception e = new Exception("A Matrícula não foi informada.");
        throw e;
      }
      else
        this.matricula = matricula;
    }
  }

  public void definirSenha(String senha) throws Exception
  {
    if(senha.equals(""))
    {
      Exception e = new Exception("A Senha não foi informada.");
      throw e;
    }
    else
      this.senha = senha;
  }

  public void definirSexo(char sexo) throws Exception
  {
    if(sexo == '\u0000')
    {
      Exception e = new Exception("O Sexo não foi informado.");
      throw e;
    }
    else
      super.setSexo(sexo);
  }

  public void definirIdentidade(String identidade)
  {
    super.setIdentidade(identidade);
  }

  public void definirOrgaoEmissorIdentidade(String orgaoEmissorIdentidade)
  {
    super.setOrgaoIdentidade(orgaoEmissorIdentidade);
  }

  public void definirCpf(String cpf) throws Exception
  {
    super.setCPF(cpf);
  }

  public void definirDepartamento(Departamento departamento)
  {
    this.departamento = departamento;
  }

  public void definirLogradouro(String logradouro) throws Exception
  {
    super.setLogradouro(logradouro);
  }

  public void definirComplemento(String complemento)
  {
    super.setComplemento(complemento);
  }

  public void definirBairro(String bairro)
  {
    super.setBairro(bairro);
  }

  public void definirCidade(String cidade) throws Exception
  {
    super.setCidade(cidade);
  }

  public void definirCep(String cep)
  {
    super.setCEP(cep);
  }

  public void setDDD(String ddd)
  {
    this.ddd = ddd;
  }

  public void definirTelefone(String telefone) throws Exception
  {
    if(telefone == null)
      return;
    if(telefone.length() <= 8)
    {
      super.setTelefone(telefone);
    }
    else
    {
      Exception e = new Exception("Número de telefone inválido. Informe somente números e sem espaço.");
      throw e;
    }
  }

  public void definirRamal(String ramal)
  {
    if(ramal == null)
      return;
    this.ramal = ramal.trim();
  }

  public void definirCelular(String celular) throws Exception
  {
    if(celular == null)
      return;
    if(celular.length() <= 8)
    {
      super.setCelular(celular);
    }
    else
    {
      Exception e = new Exception("Número de celular inválido. Informe somente números e sem espaço.");
      throw e;
    }
  }

  public void definirEmail(String email) throws Exception
  {
    super.setEmail(email);
  }

  public void senhaAlterada(boolean senhaAlterada)
  {
    this.senhaAlterada = senhaAlterada;
  }

  public boolean autenticarColaborador() throws Exception
  {
    ResultSet colaborador;
    String query;
    boolean usuarioAutenticado = false;

    Conexao conexao = new Conexao('T');
    if(conexao.abrirConexao())
    {
      query = "select usuario,senha,sexo,nome_completo,identidade,orgao_emissor_rg,cpf,d.codigo as codigo_departamento,d.departamento,logradouro,complemento,bairro,cidade,estado,cep,ddd,telefone,ramal,celular,email,senha_alterada from usuario u, departamento d "+
        "where usuario = '"+ matricula +"' and senha = '"+ senha +"' and d.codigo =* u.departamento";
      colaborador = conexao.executarConsulta(query);
      if(colaborador.next())
      {
        usuarioAutenticado = true;
        this.matricula = colaborador.getString("usuario");
        this.senha = colaborador.getString("senha");
        super.setSexo(colaborador.getString("sexo").toCharArray()[0]);
        super.setNome(colaborador.getString("nome_completo"));
        super.setIdentidade(colaborador.getString("identidade"));
        super.setOrgaoIdentidade(colaborador.getString("orgao_emissor_rg"));
        super.setCPF(colaborador.getString("cpf"));
        this.departamento = new Departamento(colaborador.getInt("codigo_departamento"),colaborador.getString("departamento"));
        super.setLogradouro(colaborador.getString("logradouro"));
        super.setComplemento(colaborador.getString("complemento"));
        super.setBairro(colaborador.getString("bairro"));
        super.setCidade(colaborador.getString("cidade"));
        this.setEstado(new Estado(colaborador.getString("estado")));
        super.setCEP(colaborador.getString("cep"));
        this.setDDD(colaborador.getString("ddd"));
        super.setTelefone(colaborador.getString("telefone"));
        this.ramal = colaborador.getString("ramal");
        super.setCelular(colaborador.getString("celular"));
        super.setEmail(colaborador.getString("email"));
        this.senhaAlterada = colaborador.getBoolean("senha_alterada");
        query = "insert into log_sistema (usuario,descricao) values ('"+ this.matricula +"','Acesso ao Sistema')";
        conexao.executarAtualizacao(query);
      }
      else
      {
        query = "select * from usuario";
        colaborador = conexao.executarConsulta(query);
        if(!colaborador.next())
        {
          colaboradorExiste = false;
        }
      }
      colaborador.close();
    }
    conexao.fecharConexao();
    return usuarioAutenticado;
  }

  /** Retorna true se houver algum colaborador cadastrado no Banco de dados.
    Retorna false se não houver nenhum colaborador cadastrado.*/
  public boolean colaboradorExiste()
  {
    return this.colaboradorExiste;
  }

  public void cadastrarColaborador()
  {
    Conexao conexao = new Conexao('T');
    boolean existente = false;
    try
    {
      if(conexao.abrirConexao())
      {
        ResultSet colaborador = conexao.executarConsulta("select usuario from usuario where usuario = '"+ this.matricula +"'");
        if(colaborador.next())
        {
          existente = true;
        }
        else
        {
          String query = "insert into usuario (usuario,senha,sexo,nome_completo,identidade,orgao_emissor_rg,cpf,departamento,logradouro,complemento,bairro,cidade,estado,cep,ddd,telefone,ramal,celular,email,senha_alterada) ";
          query += "values ('"+ this.matricula +"','"+ this.senha +"','"+ super.getSexo() +"','"+ super.getNome() +"','"+ super.getIdentidade() +"','"+ super.getOrgaoIdentidade() +"','"+ super.getCPF() +"','"+ this.departamento.obterCodigo() +"','"+ super.getLogradouro() +"','"+ super.getComplemento() +"','"+ super.getBairro() +"','"+ super.getCidade() +"','"+ this.obterEstado().getSigla() +"','"+ super.getCEP() +"','"+ this.getDDD() +"','"+ super.getTelefone() +"','"+ this.ramal +"','"+ super.getCelular() +"','"+ super.getEmail() +"','1')";
          conexao.executarAtualizacao(query);
        }
        conexao.fecharConexao();
      }
    }
    catch (SQLException e)
    {

    }
  }

  public void alterarColaborador()
  {
    Conexao conexao = new Conexao('T');
    conexao.abrirConexao();
    String query = "update usuario set senha = '"+ this.senha +"',sexo = '"+ super.getSexo() +"',nome_completo = '"+ super.getNome() +"',identidade = '"+ super.getIdentidade() +"',orgao_emissor_rg = '"+ super.getOrgaoIdentidade().trim() +"',cpf = '"+ super.getCPF() +"',departamento = "+ ((this.departamento == null)?"NULL":"" + this.departamento.obterCodigo()) +",logradouro = '"+ super.getLogradouro() +"',complemento = '"+ super.getComplemento() +"',bairro = '"+ super.getBairro() +"',cidade = '"+ super.getCidade() +"',estado = "+ (this.obterEstado() != null?"'"+ this.obterEstado().getSigla() +"'":null) +",cep = '"+ super.getCEP() +"',ddd = '"+ this.getDDD() +"',telefone = '"+ super.getTelefone() +"',ramal = '"+ this.ramal +"',celular = '"+ super.getCelular() +"',email = '"+ super.getEmail() +"',senha_alterada = 1 where usuario = '"+ this.matricula +"'";
    conexao.executarAtualizacao(query);
    conexao.fecharConexao();
  }

  public void excluirColaborador()
  {
    Conexao conexao = new Conexao('T');
    conexao.abrirConexao();
    String query = "delete from usuario where usuario = '"+ this.matricula +"'";
    conexao.executarAtualizacao(query);
    conexao.fecharConexao();
  }

  public boolean colaboradorTemPermissao(Interface inter)
  {
    return true;
  }

  public Vector carregarColaboradores(Conexao conexao) throws Exception
  {
    ResultSet dadosColaborador;
    Vector colaboradores = new Vector();
    try
    {
      dadosColaborador = conexao.executarConsulta("select usuario, nome_completo, senha from usuario order by usuario asc");
      colaboradores.addElement(null);
      while(dadosColaborador.next())
      {
        colaboradores.addElement(new Colaborador(dadosColaborador.getString("usuario"),dadosColaborador.getString("nome_completo"), dadosColaborador.getString("senha")));
      }
      dadosColaborador.close();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return colaboradores;
  }

  public void carregarPermissoes(Conexao conexao) throws SQLException
  {
    Interface tela;
    Permissao permissao;
    permissoes.removeAllElements();
    ResultSet dadosPermissoes = conexao.executarConsulta("select i.identificador as identificador, i.interface as interface, i.descricao as descricao_interface, p.permissao as tipo_permissao from permissao p, interface i where i.identificador = p.interface and p.usuario = '"+ this.matricula +"' order by i.identificador asc");
    while(dadosPermissoes.next())
    {
      tela = new Interface(dadosPermissoes.getInt("identificador"),dadosPermissoes.getString("interface"),dadosPermissoes.getString("descricao_interface"));
      permissao = new Permissao(tela,dadosPermissoes.getString("tipo_permissao").toCharArray()[0]);
      permissoes.addElement(permissao);
    }
    dadosPermissoes.close();
  }

  public Vector obterPermissoes()
  {
    return this.permissoes;
  }

  public void definirPermissoes(Vector permissoes) throws SQLException, Exception
  {
    Conexao conexao = new Conexao('T');
    conexao.abrirConexao();
    ResultSet dadosColaborador;

    conexao.executarAtualizacao("delete from permissao where usuario = '"+ this.matricula +"'");
    for(int i = 0;i < permissoes.size();i++)
    {
      conexao.executarAtualizacao("insert into permissao (interface,usuario,permissao) values ("+ ((Permissao)permissoes.get(i)).obterTela().obterIdentificador() +",'"+ this.matricula +"','"+ ((Permissao)permissoes.get(i)).obterTipoAcesso() +"')");
    }
    conexao.fecharConexao();
  }

  public char verificarPermissao(Interface tela)
  {
    char tipoPermissao = '\u0000';
    for(int i=0;i < permissoes.size();i++)
    {
      if(((Permissao)permissoes.get(i)).obterTela().obterIdentificador() == tela.obterIdentificador())
      {
        tipoPermissao = ((Permissao)permissoes.get(i)).obterTipoAcesso();
      }
    }
    return tipoPermissao;
  }

  public String getDDD()
  {
    return ddd;
  }

  public String toString()
  {
    return super.getNome();
  }
}
