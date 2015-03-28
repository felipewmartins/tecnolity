package br.com.mentores.util;

import java.sql.*;

public interface ConexaoDB
{
    void abrirConexao() throws Exception;
    
    boolean conexaoAberta() throws Exception;
    
    void fecharConexao() throws Exception;
    
    void commit() throws SQLException;
    
    void rollback() throws SQLException;
    
    ResultSet executarConsulta(String p0) throws SQLException;
    
    int executarAtualizacao(String p0) throws SQLException;
}
