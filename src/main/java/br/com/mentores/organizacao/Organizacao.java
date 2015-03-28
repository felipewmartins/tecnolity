package br.com.mentores.organizacao;

import br.com.mentores.*;

public abstract class Organizacao extends PessoaJuridica
{
    private String missao;
    private String sigla;
    
    public Organizacao() throws Exception {
        this.missao = "";
        this.sigla = "";
    }
    
    public Organizacao(final String razaoSocial, final String nomeFantasia, final String sigla, final String cnpj, final String inscricaoEstadual, final String inscricaoMunicipal, final String missao, final String logradouro, final String numero, final String complemento, final String bairro, final String cidade, final String cep, final Pais pais, final Estado estado, final String telefone, final String fax, final String celular, final String website, final String email) throws Exception {
        this.setRazaoSocial(razaoSocial);
        this.setNomeFantasia(nomeFantasia);
        this.setSigla(sigla);
        this.setCNPJ(cnpj);
        this.setInscricaoEstadual(inscricaoEstadual);
        this.setInscricaoMunicipal(inscricaoMunicipal);
        this.setMissao(missao);
        this.setLogradouro(logradouro);
        this.setNumero(numero);
        this.setComplemento(complemento);
        this.setBairro(bairro);
        this.setCidade(cidade);
        this.setCEP(cep);
        this.setPais(pais);
        this.setEstado(estado);
        this.setTelefone(telefone);
        this.setFax(fax);
        this.setCelular(celular);
        this.setWebsite(website);
        this.setEmail(email);
    }
    
    public String getMissao() {
        return this.missao;
    }
    
    public void setMissao(final String missao) {
        if (missao != null) {
            this.missao = missao.trim();
        }
    }
    
    public String getSigla() {
        return this.sigla;
    }
    
    public void setSigla(final String sigla) {
        if (sigla != null) {
            this.sigla = sigla.trim();
        }
    }
    
    public void setRazaoSocial(final String razaoSocial) {
        super.setNome(razaoSocial);
    }
    
    public void setCNPJ(final String cnpj) {
        super.setCNPJ(cnpj);
    }
}
