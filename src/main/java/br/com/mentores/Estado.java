package br.com.mentores;

public abstract class Estado
{
    private String sigla;
    private String nome;
    private Pais pais;
    
    public Estado(final String sigla, final String nome, final Pais pais) {
        this.setSigla(sigla);
        this.setNome(nome);
        this.setPais(pais);
    }
    
    public Estado(final String sigla, final Pais pais) {
        this.setSigla(sigla);
        this.setPais(pais);
    }
    
    public Estado(final String sigla, final String nome) {
        this.setSigla(sigla);
        this.setNome(nome);
    }
    
    public Estado(final String sigla) {
        this.setSigla(sigla);
    }
    
    public Estado() {
    }
    
    public String getNome() {
        return this.nome;
    }
    
    public Pais getPais() {
        return this.pais;
    }
    
    public String getSigla() {
        return this.sigla.toUpperCase();
    }
    
    public void setNome(final String nome) {
        if (nome == null) {
            this.nome = "";
        }
        else {
            this.nome = nome;
        }
    }
    
    public void setPais(final Pais pais) {
        this.pais = pais;
    }
    
    public void setSigla(final String sigla) {
        if (sigla != null) {
            this.sigla = sigla.toUpperCase();
        }
    }
    
    public String toString() {
        return this.nome;
    }
}
