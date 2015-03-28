/**
   * Projeto: 001 - Tecnolity
   * Autor do Código: Hildeberto Mendonça Filho
   * Nome do Arquivo: BarraAplicacao.java
   * Linguagem: Java
   * 
   * Requerimentos: Requer máquina virtual java versão 1.3 ou superior.
   * 
   * Objetivo: Barra navegação entre as aplicações.
   * 
   * Objetivo definido por: Hildeberto Mendonça
   * Início da Programação: 22/12/2001
   * Fim da Programação:
   * Última Versão: 1.0
*/

package br.com.tecnolity.aplicacao;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import br.com.tecnolity.administracao.ui.*;
import br.com.tecnolity.logistica.ui.*;
import br.com.tecnolity.pedidos.ui.*;
import br.com.tecnolity.producao.ui.*;
import br.com.tecnolity.suprimentos.ui.*;

public class BarraAplicacao extends JPanel implements ActionListener
{
    private JButton btGerencia, btSuprimentos, btProducao, btPedidos, btLogistica, btAdministracao;
    private Aplicacao aplicacao;
    
    public BarraAplicacao(Aplicacao aplicacao) 
    {
        this.aplicacao = aplicacao;
        this.setLayout(new BorderLayout());
        this.setBorder(new LineBorder(Color.black));
        
        JPanel pnlComandos = new JPanel(new GridLayout(1,5));
        
        btSuprimentos = new JButton("Suprimentos",new ImageIcon("imagens/ico_suprimentos.gif"));
        btSuprimentos.setFont(new Font("Arial",Font.PLAIN,11));
        btSuprimentos.addActionListener(this);
        btSuprimentos.setMnemonic('S');
        btSuprimentos.setVerticalTextPosition(SwingConstants.BOTTOM);
        btSuprimentos.setHorizontalTextPosition(SwingConstants.CENTER);
        pnlComandos.add(btSuprimentos);
        
        btProducao = new JButton("Produção",new ImageIcon("imagens/ico_producao.gif"));
        btProducao.setFont(new Font("Arial",Font.PLAIN,11));
        btProducao.addActionListener(this);
        btProducao.setMnemonic('P');
        btProducao.setVerticalTextPosition(SwingConstants.BOTTOM);
        btProducao.setHorizontalTextPosition(SwingConstants.CENTER);
        pnlComandos.add(btProducao);
        
        btPedidos = new JButton("Pedidos",new ImageIcon("imagens/ico_pedidos.gif"));
        btPedidos.setFont(new Font("Arial",Font.PLAIN,11));
        btPedidos.addActionListener(this);
        btPedidos.setMnemonic('d');
        btPedidos.setVerticalTextPosition(SwingConstants.BOTTOM);
        btPedidos.setHorizontalTextPosition(SwingConstants.CENTER);
        pnlComandos.add(btPedidos);
        
        btLogistica = new JButton("Logística",new ImageIcon("imagens/ico_logistica.gif"));
        btLogistica.setFont(new Font("Arial",Font.PLAIN,11));
        btLogistica.addActionListener(this);
        btLogistica.setMnemonic('L');
        btLogistica.setVerticalTextPosition(SwingConstants.BOTTOM);
        btLogistica.setHorizontalTextPosition(SwingConstants.CENTER);
        pnlComandos.add(btLogistica);
        
        btAdministracao = new JButton("Administração",new ImageIcon("imagens/ico_administracao.gif"));
        btAdministracao.setFont(new Font("Arial",Font.PLAIN,11));
        btAdministracao.addActionListener(this);
        btAdministracao.setMnemonic('d');
        btAdministracao.setVerticalTextPosition(SwingConstants.BOTTOM);
        btAdministracao.setHorizontalTextPosition(SwingConstants.CENTER);
        pnlComandos.add(btAdministracao);
        JPanel pnlAreaComandos = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlAreaComandos.add(pnlComandos);
        this.add(pnlAreaComandos, BorderLayout.CENTER);
        
        JLabel lblLogomarca = new JLabel(new ImageIcon("imagens/logo.gif"));
        this.add(lblLogomarca, BorderLayout.EAST);
    }
    
    public void actionPerformed(java.awt.event.ActionEvent actionEvent) 
    {
        Object objeto = actionEvent.getSource();
        
        /*if(objeto == btGerencia)
        {
            AreaTrabalhoGerencia areaTrabalho = new AreaTrabalhoGerencia(aplicacao);
            this.aplicacao.adicionarAreaTrabalho(areaTrabalho);
        }*/
        
        if(objeto == btSuprimentos)
        {
            AreaTrabalhoSuprimentos areaTrabalho = new AreaTrabalhoSuprimentos(aplicacao);
            this.aplicacao.adicionarAreaTrabalho(areaTrabalho);
        }
        
        if(objeto == btProducao)
        {
            AreaTrabalhoProducao areaTrabalho = new AreaTrabalhoProducao(aplicacao);
            this.aplicacao.adicionarAreaTrabalho(areaTrabalho);
        }
        
        if(objeto == btPedidos)
        {
            AreaTrabalhoPedido areaTrabalho = new AreaTrabalhoPedido(aplicacao);
            this.aplicacao.adicionarAreaTrabalho(areaTrabalho);
        }
        
        if(objeto == btLogistica)
        {
            AreaTrabalhoLogistica areaTrabalho = new AreaTrabalhoLogistica(aplicacao);
            this.aplicacao.adicionarAreaTrabalho(areaTrabalho);
        }
        
        if(objeto == btAdministracao)
        {
            AreaTrabalhoAdministracao areaTrabalho = new AreaTrabalhoAdministracao(aplicacao);
            this.aplicacao.adicionarAreaTrabalho(areaTrabalho);
        }
    }
    
}