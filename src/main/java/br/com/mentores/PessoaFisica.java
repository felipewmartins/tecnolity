package br.com.mentores;

import br.com.mentores.util.*;

public abstract class PessoaFisica extends Pessoa
{
    public static final char FEMININO = 'F';
    public static final char MASCULINO = 'M';
    public static final char SOLTEIRO = 'S';
    public static final char CASADO = 'C';
    public static final char DESQUITADO = 'D';
    public static final char SEPARADO = 'P';
    public static final char VIUVO = 'V';
    private char sexo;
    private Calendario dataNascimento;
    private char estadoCivil;
    private String CPF;
    private String identidade;
    private String orgaoIdentidade;
    private Estado estadoIdentidade;
    
    public PessoaFisica() {
    }
    
    public PessoaFisica(final String nome) throws Exception {
        super(nome);
    }
    
    public PessoaFisica(final String nome, final char sexo) throws Exception {
        super(nome);
        this.setSexo(sexo);
    }
    
    public PessoaFisica(final String nome, final String logradouro, final String numero, final String complemento, final String bairro, final String cidade, final Estado estado, final String CEP, final String telefone, final String email, final Calendario dataNascimento, final char sexo) throws Exception {
        super(nome, logradouro, numero, complemento, bairro, cidade, estado, CEP, telefone, email);
        this.setDataNascimento(dataNascimento);
        this.setSexo(sexo);
    }
    
    public PessoaFisica(final String nome, final String logradouro, final String numero, final String complemento, final String bairro, final String cidade, final Estado estado, final String CEP, final String telefone, final String email, final String CPF, final Calendario dataNascimento, final char sexo) throws Exception {
        super(nome, logradouro, numero, complemento, bairro, cidade, estado, CEP, telefone, email);
        this.setCPF(CPF);
        this.setDataNascimento(dataNascimento);
        this.setSexo(sexo);
    }
    
    public PessoaFisica(final String nome, final String logradouro, final String numero, final String complemento, final String bairro, final String cidade, final String CEP, final String telefone, final String email, final String CPF, final String identidade, final String orgaoIdentidade, final Calendario dataNascimento, final char sexo) throws Exception {
        super(nome, logradouro, numero, complemento, bairro, cidade, CEP, telefone, email);
        this.setCPF(CPF);
        this.setIdentidade(identidade);
        this.setOrgaoIdentidade(orgaoIdentidade);
        this.setEstadoIdentidade(this.estadoIdentidade);
        this.setDataNascimento(dataNascimento);
        this.setSexo(sexo);
    }
    
    public PessoaFisica(final String nome, final String logradouro, final String numero, final String complemento, final String bairro, final String cidade, final Estado estado, final String CEP, final String telefone, final String email, final String CPF, final String identidade, final String orgaoIdentidade, final Estado estadoIdentidade, final Calendario dataNascimento, final char sexo) throws Exception {
        super(nome, logradouro, numero, complemento, bairro, cidade, estado, CEP, telefone, email);
        this.setCPF(CPF);
        this.setIdentidade(identidade);
        this.setOrgaoIdentidade(orgaoIdentidade);
        this.setEstadoIdentidade(estadoIdentidade);
        this.setDataNascimento(dataNascimento);
        this.setSexo(sexo);
    }
    
    public PessoaFisica(final String nome, final String logradouro, final String numero, final String complemento, final String bairro, final String cidade, final Estado estado, final String CEP, final String telefone, final String celular, final String email, final String CPF, final String identidade, final String orgaoIdentidade, final char sexo) throws Exception {
        super(nome, logradouro, numero, complemento, bairro, cidade, estado, CEP, telefone, celular, email);
        this.setCPF(CPF);
        this.setIdentidade(identidade);
        this.setOrgaoIdentidade(orgaoIdentidade);
        this.setDataNascimento(this.dataNascimento);
        this.setSexo(sexo);
    }
    
    public PessoaFisica(final String nome, final String logradouro, final String numero, final String complemento, final String bairro, final String cidade, final Estado estado, final String CEP, final String telefone, final String celular, final String email, final String CPF, final String identidade, final String orgaoIdentidade, final Estado estadoIdentidade, final Calendario dataNascimento, final char sexo) throws Exception {
        super(nome, logradouro, numero, complemento, bairro, cidade, estado, CEP, telefone, celular, email);
        this.setCPF(CPF);
        this.setIdentidade(identidade);
        this.setOrgaoIdentidade(orgaoIdentidade);
        this.setEstadoIdentidade(estadoIdentidade);
        this.setDataNascimento(dataNascimento);
        this.setSexo(sexo);
    }
    
    public void setCPF(final String CPF) {
        if (CPF != null) {
            this.CPF = CPF.trim();
        }
    }
    
    public void setIdentidade(final String identidade) {
        if (identidade != null) {
            this.identidade = identidade.trim();
        }
    }
    
    public void setOrgaoIdentidade(final String orgaoIdentidade) {
        if (orgaoIdentidade != null) {
            this.orgaoIdentidade = orgaoIdentidade.trim();
        }
    }
    
    public void setEstadoIdentidade(final Estado estadoIdentidade) {
        this.estadoIdentidade = estadoIdentidade;
    }
    
    public void setDataNascimento(final Calendario dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
    public void setEstadoCivil(final char estadoCivil) throws Exception {
        if (estadoCivil == 'S' || estadoCivil == 'C' || estadoCivil == 'D' || estadoCivil == 'P' || estadoCivil == 'V' || estadoCivil == '\0') {
            this.estadoCivil = estadoCivil;
            return;
        }
        throw new Exception("Estado Civil inv\u00e1lido.");
    }
    
    public void setSexo(final char sexo) {
        this.sexo = sexo;
    }
    
    public char getSexo() {
        return this.sexo;
    }
    
    public Calendario getDataNascimento() {
        return this.dataNascimento;
    }
    
    public char getEstadoCivil() {
        return this.estadoCivil;
    }
    
    public String getCPF() {
        return this.CPF;
    }
    
    public String getIdentidade() {
        return this.identidade;
    }
    
    public String getOrgaoIdentidade() {
        return this.orgaoIdentidade;
    }
    
    public Estado getEstadoIdentidade() {
        return this.estadoIdentidade;
    }
    
    public static boolean validarCpf(String cpf) {
        final char[] chrCpf = cpf.toCharArray();
        final int[] digitos = new int[11];
        int soma = 0;
        String numerosCpf = "";
        for (int i = 0; i < chrCpf.length; ++i) {
            if (Character.isDigit(chrCpf[i])) {
                numerosCpf = String.valueOf(numerosCpf) + chrCpf[i];
            }
        }
        cpf = numerosCpf;
        if (cpf.length() != 11) {
            return false;
        }
        if (cpf.equals("00000000000")) {
            return false;
        }
        digitos[0] = Integer.parseInt(cpf.substring(0, 1));
        digitos[1] = Integer.parseInt(cpf.substring(1, 2));
        digitos[2] = Integer.parseInt(cpf.substring(2, 3));
        digitos[3] = Integer.parseInt(cpf.substring(3, 4));
        digitos[4] = Integer.parseInt(cpf.substring(4, 5));
        digitos[5] = Integer.parseInt(cpf.substring(5, 6));
        digitos[6] = Integer.parseInt(cpf.substring(6, 7));
        digitos[7] = Integer.parseInt(cpf.substring(7, 8));
        digitos[8] = Integer.parseInt(cpf.substring(8, 9));
        digitos[9] = Integer.parseInt(cpf.substring(9, 10));
        digitos[10] = Integer.parseInt(cpf.substring(10));
        soma = 10 * digitos[0] + 9 * digitos[1] + 8 * digitos[2] + 7 * digitos[3] + 6 * digitos[4] + 5 * digitos[5] + 4 * digitos[6] + 3 * digitos[7] + 2 * digitos[8];
        soma -= 11 * (soma / 11);
        int resultado;
        if (soma == 0 || soma == 1) {
            resultado = 0;
        }
        else {
            resultado = 11 - soma;
        }
        if (resultado == digitos[9]) {
            soma = digitos[0] * 11 + digitos[1] * 10 + digitos[2] * 9 + digitos[3] * 8 + digitos[4] * 7 + digitos[5] * 6 + digitos[6] * 5 + digitos[7] * 4 + digitos[8] * 3 + digitos[9] * 2;
            soma -= 11 * (soma / 11);
            int resultadoDv;
            if (soma == 0 || soma == 1) {
                resultadoDv = 0;
            }
            else {
                resultadoDv = 11 - soma;
            }
            return resultadoDv == digitos[10];
        }
        return false;
    }
    
    public static String formatarCPF(final String cpf) {
        if (cpf == null) {
            return "";
        }
        if (cpf.equals("")) {
            return "";
        }
        String cpfFormatado = "";
        cpfFormatado = "-" + cpf.substring(cpf.length() - 2);
        cpfFormatado = "." + cpf.substring(cpf.length() - 5, cpf.length() - 2) + cpfFormatado;
        cpfFormatado = "." + cpf.substring(cpf.length() - 8, cpf.length() - 5) + cpfFormatado;
        cpfFormatado = String.valueOf(cpf.substring(0, cpf.length() - 8)) + cpfFormatado;
        return cpfFormatado;
    }
}
