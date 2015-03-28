package br.com.mentores.ui;

public interface DialogoCaixaSelecao
{
    void setVisible(boolean p0);
    
    void setAplicacao(Aplicacao p0);
    
    void setObjeto(Object p0);
    
    void montarInterface();
    
    Object getObjeto();
}
