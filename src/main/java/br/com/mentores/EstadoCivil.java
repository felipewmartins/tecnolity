package br.com.mentores;

public class EstadoCivil
{
    String descricao;
    
    public EstadoCivil() {
    }
    
    public EstadoCivil(final String descricao) {
        this.setDescricao(descricao);
    }
    
    public String getDescricao() {
        return this.descricao;
    }
    
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }
    
    public String toString() {
        return this.descricao;
    }
}
