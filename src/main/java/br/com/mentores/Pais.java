package br.com.mentores;

public abstract class Pais
{
    private String sigla;
    private String nome;
    private String continente;
    
    public Pais(final String sigla, final String nome) {
        this.setSigla(sigla);
        this.setNome(nome);
    }
    
    public Pais(final String sigla) {
        this.setSigla(sigla);
    }
    
    public Pais() {
    }
    
    public String getSigla() {
        return this.sigla.toUpperCase();
    }
    
    public String getNome() {
        return this.nome;
    }
    
    public void setSigla(final String sigla) {
        if (sigla != null) {
            this.sigla = sigla.toUpperCase();
        }
    }
    
    public void setNome(final String nome) {
        this.nome = nome;
    }
    
    public String toString() {
        return this.nome;
    }
}
