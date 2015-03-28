package br.com.tecnolity.aplicacao.modelos;

import javax.swing.*;
import br.com.tecnolity.administracao.*;
import br.com.tecnolity.aplicacao.*;
import br.com.tecnolity.util.*;

public class ModeloAreaTrabalho extends JPanel
{
    public ModeloAreaTrabalho() {}
    
    protected char verificarPermissaoAcesso(Interface tela, Colaborador colaborador, Conexao conexao)
    {
        Permissao permissao = new Permissao(tela,colaborador);
        return permissao.verificarPermissaoAcesso(conexao);
    }
}