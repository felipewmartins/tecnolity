package br.com.mentores.organizacao;

import br.com.mentores.util.*;

public interface Cliente
{
    void addCliente(ConexaoDB p0) throws Exception;
    
    void removeCliente(ConexaoDB p0) throws Exception;
    
    Cliente getCliente(ConexaoDB p0) throws Exception;
    
    void setCliente(ConexaoDB p0) throws Exception;
}
