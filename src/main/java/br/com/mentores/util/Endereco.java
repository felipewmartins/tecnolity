package br.com.mentores.util;

import br.com.mentores.*;
import java.net.*;
import java.io.*;

public class Endereco
{
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private Estado estado;
    private Pais pais;
    private String cep;
    
    public static void localizarEnderecoCEP(final String cep) throws IOException {
        final URL url = new URL("http://www.mentores.com.br/servicos/app/buscar_endereco.asp?cep=" + cep);
        final BufferedReader entrada = new BufferedReader(new InputStreamReader(url.openStream()));
        String linha;
        while ((linha = entrada.readLine()) != null) {
            System.out.println(linha);
        }
    }
}
