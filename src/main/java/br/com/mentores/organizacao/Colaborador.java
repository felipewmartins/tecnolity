package br.com.mentores.organizacao;

import br.com.mentores.*;
import br.com.mentores.util.*;

public abstract class Colaborador extends PessoaFisica
{
    public Colaborador() {
    }
    
    public Colaborador(final String nome) {
        this.setNome(nome);
    }
    
    public Colaborador(final String nome, final String CPF, final char sexo, final Calendario dataNascimento, final String identidade, final String orgaoEmissorIdentidade, final String logradouro, final String numero, final String complemento, final String bairro, final String CEP, final String cidade, final Estado estado, final Pais pais, final String telefone, final String celular, final String email) throws Exception {
        this.setNome(nome);
        this.setCPF(CPF);
        this.setSexo(sexo);
        this.setDataNascimento(dataNascimento);
        this.setIdentidade(identidade);
        this.setOrgaoIdentidade(orgaoEmissorIdentidade);
        this.setLogradouro(logradouro);
        this.setNumero(numero);
        this.setComplemento(complemento);
        this.setBairro(bairro);
        this.setCEP(CEP);
        this.setCidade(cidade);
        this.setEstado(estado);
        this.setPais(pais);
        this.setTelefone(telefone);
        this.setCelular(celular);
        this.setEmail(email);
    }
    
    public abstract void addColaborador(final ConexaoDB p0) throws Exception;
    
    public abstract void removeColaborador(final ConexaoDB p0) throws Exception;
    
    public abstract void setColaborador(final ConexaoDB p0) throws Exception;
    
    public abstract Colaborador getColaborador(final ConexaoDB p0) throws Exception;
    
    public void setCPF(final String cpf) {
        super.setCPF(cpf);
    }
    
    public void setNome(final String nome) {
        super.setNome(nome);
    }
    
    public void setSexo(final char sexo) {
        super.setSexo(sexo);
    }
    
    public void setDataNascimento(final Calendario dataNascimento) {
        super.setDataNascimento(dataNascimento);
    }
    
    public void setLogradouro(final String logradouro) {
        super.setLogradouro(logradouro);
    }
    
    public void setCidade(final String cidade) {
        super.setCidade(cidade);
    }
    
    public void setPais(final Pais pais) {
        super.setPais(pais);
    }
}
