/**
   * Projeto: 001 - Tecnolity
   * Autor do C�digo: Hildeberto Mendon�a Filho
   * Nome do Arquivo: BarraStatus.java
   * Linguagem: Java
   * 
   * Requerimentos: Requer m�quina virtual java vers�o 1.3 ou superior.
   * 
   * Objetivo: Barra de Status da aplica��o. �til para apresentar mensagens ao usu�rio
   * 
   * Objetivo definido por: Hildeberto Mendon�a
   * In�cio da Programa��o: 22/12/2001
   * Fim da Programa��o:
   * �ltima Vers�o: 1.0
*/

package org.esmerilprogramming.tecnolity.aplicacao;

import java.awt.*;
import javax.swing.*;
import org.esmerilprogramming.tecnolity.util.*;

public class BarraStatus extends JPanel
{
    private JLabel lblUsuario, lblMensagem, lblDataHoraAcesso;
    private Aplicacao aplicacao;
    private Calendario calendario = new Calendario();
    
    public BarraStatus(Aplicacao aplicacao)
    {
        setLayout(new BorderLayout());
        this.aplicacao = aplicacao;
        JPanel pnlUsuario = new JPanel();
        lblUsuario =  new JLabel("Usu�rio: " + aplicacao.obterColaborador().obterMatricula());
        pnlUsuario.add(lblUsuario);
        this.add(pnlUsuario,BorderLayout.WEST);
        
        JPanel pnlMensagem = new JPanel();
        lblMensagem = new JLabel("Seja bem vindo ao sistema!!!");
        pnlMensagem.add(lblMensagem);
        this.add(pnlMensagem, BorderLayout.CENTER);
        
        JPanel pnlDataHoraAcesso = new JPanel();
        lblDataHoraAcesso = new JLabel("Acesso em: " + calendario.dataHoje("dd/MM/yyyy 'as' HH:mm:ss"));
        pnlDataHoraAcesso.add(lblDataHoraAcesso);
        this.add(pnlDataHoraAcesso,BorderLayout.EAST);
    }
}