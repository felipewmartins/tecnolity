package br.com.mentores.organizacao;

import br.com.mentores.*;
import br.com.mentores.util.*;

public abstract class ClientePessoaJuridica extends PessoaJuridica
{
    private int codigo;
    private Calendario dataFundacao;
    public static final char FILIAL = 'F';
    public static final char MATRIZ = 'M';
    private char composicao;
    public static final char MICRO = 'C';
    public static final char PEQUENO = 'P';
    public static final char M\u00c9DIO = 'M';
    public static final char GRANDE = 'G';
    private char porte;
    private boolean exportacao;
    private String setorAtividade;
    private String missao;
    private String publicoAlvo;
    private String atividadeFim;
    
    public ClientePessoaJuridica() {
    }
    
    public ClientePessoaJuridica(final int codigo) {
        this.setCodigo(codigo);
    }
    
    public int getCodigo() {
        return this.codigo;
    }
    
    public void setCodigo(final int codigo) {
        this.codigo = codigo;
    }
    
    public char getComposicao() {
        return this.composicao;
    }
    
    public Calendario getDataFundacao() {
        return this.dataFundacao;
    }
    
    public char getPorte() {
        return this.porte;
    }
    
    public String getSetorAtividade() {
        return this.setorAtividade;
    }
    
    public void setComposicao(final char composicao) {
        this.composicao = composicao;
    }
    
    public void setDataFundacao(final Calendario dataFundacao) {
        this.dataFundacao = dataFundacao;
    }
    
    public void setPorte(final char porte) {
        this.porte = porte;
    }
    
    public void setSetorAtividade(final String setorAtividade) {
        this.setorAtividade = setorAtividade;
    }
    
    public String getAtividadeFim() {
        return this.atividadeFim;
    }
    
    public String getMissao() {
        return this.missao;
    }
    
    public String getPublicoAlvo() {
        return this.publicoAlvo;
    }
    
    public void setAtividadeFim(final String atividadeFim) {
        this.atividadeFim = atividadeFim;
    }
    
    public void setMissao(final String missao) {
        this.missao = missao;
    }
    
    public void setPublicoAlvo(final String publicoAlvo) {
        this.publicoAlvo = publicoAlvo;
    }
    
    public boolean isExportacao() {
        return this.exportacao;
    }
    
    public void setExportacao(final boolean exportacao) {
        this.exportacao = exportacao;
    }
}
