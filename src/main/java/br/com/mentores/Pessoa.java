package br.com.mentores;

import java.util.regex.*;

public abstract class Pessoa
{
    public static final char PESSOAFISICA = 'F';
    public static final char PESSOAJURIDICA = 'J';
    public static final char OUTRO = 'O';
    private char tipoCliente;
    private String nome;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private Estado estado;
    private Pais pais;
    private String CEP;
    private String telefone;
    private String fax;
    private String celular;
    private String website;
    private String email;
    private String caixaPostal;
    
    public Pessoa() {
        this.nome = "";
        this.logradouro = "";
        this.numero = "";
        this.complemento = "";
        this.bairro = "";
        this.cidade = "";
        this.CEP = "";
        this.telefone = "";
        this.fax = "";
        this.celular = "";
        this.website = "";
        this.email = "";
        this.caixaPostal = "";
    }
    
    public Pessoa(final String nome, final String logradouro, final String numero, final String complemento, final String bairro, final String cidade, final Estado estado, final Pais pais, final String CEP, final String telefone, final String email, final String caixaPostal) throws Exception {
        this.setNome(nome);
        this.setLogradouro(logradouro);
        this.setNumero(numero);
        this.setComplemento(complemento);
        this.setBairro(bairro);
        this.setCidade(cidade);
        this.setEstado(estado);
        this.setPais(pais);
        this.setCEP(CEP);
        this.setTelefone(telefone);
        this.setEmail(email);
        this.setCaixaPostal(caixaPostal);
        this.setTipoCliente(this.tipoCliente);
    }
    
    public Pessoa(final String nome, final String logradouro, final String numero, final String complemento, final String bairro, final String cidade, final Estado estado, final String CEP, final String telefone, final String email) throws Exception {
        this.setNome(nome);
        this.setLogradouro(logradouro);
        this.setNumero(numero);
        this.setComplemento(complemento);
        this.setBairro(bairro);
        this.setCidade(cidade);
        this.setEstado(estado);
        this.setCEP(CEP);
        this.setTelefone(telefone);
        this.setEmail(email);
    }
    
    public Pessoa(final String nome, final String logradouro, final String numero, final String complemento, final String bairro, final String cidade, final String CEP, final String telefone, final String email) throws Exception {
        this.setNome(nome);
        this.setLogradouro(logradouro);
        this.setNumero(numero);
        this.setComplemento(complemento);
        this.setBairro(bairro);
        this.setCidade(cidade);
        this.setCEP(CEP);
        this.setTelefone(telefone);
        this.setEmail(email);
    }
    
    public Pessoa(final String nome, final String logradouro, final String numero, final String complemento, final String bairro, final String cidade, final Estado estado, final String CEP, final String telefone, final String celular, final String email) throws Exception {
        this.setNome(nome);
        this.setLogradouro(logradouro);
        this.setNumero(numero);
        this.setComplemento(complemento);
        this.setBairro(bairro);
        this.setCidade(cidade);
        this.setEstado(estado);
        this.setCEP(CEP);
        this.setTelefone(telefone);
        this.setCelular(celular);
        this.setEmail(email);
    }
    
    public Pessoa(final String nome) {
        this.setNome(nome);
    }
    
    public String getNome() {
        return this.nome;
    }
    
    public String getLogradouro() {
        return this.logradouro;
    }
    
    public String getNumero() {
        return this.numero;
    }
    
    public String getComplemento() {
        return this.complemento;
    }
    
    public String getBairro() {
        return this.bairro;
    }
    
    public String getCidade() {
        return this.cidade;
    }
    
    public String getCEP() {
        return this.CEP;
    }
    
    public String getTelefone() {
        return this.telefone;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public void setNome(final String nome) {
        if (nome != null) {
            this.nome = nome.trim();
        }
    }
    
    public void setLogradouro(final String logradouro) {
        if (logradouro != null) {
            this.logradouro = logradouro.trim();
        }
    }
    
    public void setNumero(final String numero) {
        if (numero != null) {
            this.numero = numero.trim();
        }
    }
    
    public void setComplemento(final String complemento) {
        if (complemento != null) {
            this.complemento = complemento.trim();
        }
    }
    
    public void setBairro(final String bairro) {
        this.bairro = bairro;
    }
    
    public void setCidade(final String cidade) {
        if (cidade != null) {
            this.cidade = cidade.trim();
        }
    }
    
    public void setCEP(final String CEP) {
        if (CEP != null) {
            this.CEP = CEP.trim();
        }
    }
    
    public void setTelefone(final String telefone) {
        if (telefone != null) {
            this.telefone = telefone.trim();
        }
    }
    
    public void setEmail(final String email) {
        if (email != null) {
            this.email = email.trim();
        }
    }
    
    public static boolean validarEmail(final String email) {
        final Pattern padrao = Pattern.compile("[a-z][a-z\\p{Punct}0-9]+@([a-z0-9]+\\p{Punct})+[a-z]{2,3}(\\p{Punct}[a-z]{2})?");
        final Matcher busca = padrao.matcher(email);
        return busca.matches();
    }
    
    public String getCelular() {
        return this.celular;
    }
    
    public void setCelular(final String celular) {
        if (celular != null) {
            this.celular = celular.trim();
        }
    }
    
    public Estado getEstado() {
        return this.estado;
    }
    
    public void setEstado(final Estado estado) {
        this.estado = estado;
    }
    
    public Pais getPais() {
        return this.pais;
    }
    
    public void setPais(final Pais pais) {
        this.pais = pais;
    }
    
    public String getFax() {
        return this.fax;
    }
    
    public void setFax(final String fax) {
        if (fax != null) {
            this.fax = fax.trim();
        }
    }
    
    public String getWebsite() {
        return this.website;
    }
    
    public void setWebsite(final String website) {
        if (website != null) {
            this.website = website.trim();
        }
    }
    
    public String getCaixaPostal() {
        return this.caixaPostal;
    }
    
    public char getTipoCliente() {
        return this.tipoCliente;
    }
    
    public void setCaixaPostal(final String caixaPostal) {
        if (caixaPostal != null) {
            this.caixaPostal = caixaPostal.trim();
        }
    }
    
    public void setTipoCliente(final char tipoCliente) {
        this.tipoCliente = tipoCliente;
    }
    
    public static String formatarCEP(final String cep) {
        if (cep == null) {
            return "";
        }
        if (cep.equals("")) {
            return "";
        }
        String cepFormatado = "";
        cepFormatado = "-" + cep.substring(cep.length() - 3);
        cepFormatado = "." + cep.substring(cep.length() - 6, cep.length() - 3) + cepFormatado;
        cepFormatado = String.valueOf(cep.substring(0, cep.length() - 6)) + cepFormatado;
        return cepFormatado;
    }
}
