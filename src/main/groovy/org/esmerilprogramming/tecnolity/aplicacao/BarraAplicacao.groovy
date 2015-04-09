package org.esmerilprogramming.tecnolity.aplicacao

import java.awt.*
import java.awt.event.*
import javax.swing.*
import javax.swing.border.*

import org.esmerilprogramming.tecnolity.administracao.ui.*
import org.esmerilprogramming.tecnolity.logistica.ui.*
import org.esmerilprogramming.tecnolity.pedidos.ui.*
import org.esmerilprogramming.tecnolity.producao.ui.*
import org.esmerilprogramming.tecnolity.suprimentos.ui.*
import org.esmerilprogramming.tecnolity.ui.img.ImageLoader

class BarraAplicacao extends JPanel implements ActionListener {

  private JButton btGerencia, btSuprimentos, btProducao, btPedidos, btLogistica, btAdministracao
    private Aplicacao aplicacao

    BarraAplicacao(Aplicacao aplicacao) {
      this.aplicacao = aplicacao
      this.setLayout(new BorderLayout())
      this.setBorder(new LineBorder(Color.black))

      def img = ImageLoader.instance

      JPanel pnlComandos = new JPanel(new GridLayout(1, 5))

      btSuprimentos = new JButton("Suprimentos", img.icon('ico_suprimentos.gif'))
      btSuprimentos.setFont(new Font("Arial", Font.PLAIN, 11))
      btSuprimentos.addActionListener(this)
      btSuprimentos.setMnemonic('S')
      btSuprimentos.setVerticalTextPosition(SwingConstants.BOTTOM)
      btSuprimentos.setHorizontalTextPosition(SwingConstants.CENTER)
      pnlComandos.add(btSuprimentos)

      btProducao = new JButton("Produção", img.icon('ico_producao.gif'))
      btProducao.setFont(new Font("Arial", Font.PLAIN, 11))
      btProducao.addActionListener(this)
      btProducao.setMnemonic('P')
      btProducao.setVerticalTextPosition(SwingConstants.BOTTOM)
      btProducao.setHorizontalTextPosition(SwingConstants.CENTER)
      pnlComandos.add(btProducao)

      btPedidos = new JButton("Pedidos", img.icon('ico_pedidos.gif'))
      btPedidos.setFont(new Font("Arial", Font.PLAIN, 11))
      btPedidos.addActionListener(this)
      btPedidos.setMnemonic('d')
      btPedidos.setVerticalTextPosition(SwingConstants.BOTTOM)
      btPedidos.setHorizontalTextPosition(SwingConstants.CENTER)
      pnlComandos.add(btPedidos)

      btLogistica = new JButton("Logística", img.icon('ico_logistica.gif'))
      btLogistica.setFont(new Font("Arial", Font.PLAIN, 11))
      btLogistica.addActionListener(this)
      btLogistica.setMnemonic('L')
      btLogistica.setVerticalTextPosition(SwingConstants.BOTTOM)
      btLogistica.setHorizontalTextPosition(SwingConstants.CENTER)
      pnlComandos.add(btLogistica)

      btAdministracao = new JButton("Administração", img.icon('ico_administracao.gif'))
      btAdministracao.setFont(new Font("Arial", Font.PLAIN, 11))
      btAdministracao.addActionListener(this)
      btAdministracao.setMnemonic('d')
      btAdministracao.setVerticalTextPosition(SwingConstants.BOTTOM)
      btAdministracao.setHorizontalTextPosition(SwingConstants.CENTER)
      pnlComandos.add(btAdministracao)
      JPanel pnlAreaComandos = new JPanel(new FlowLayout(FlowLayout.LEFT))
      pnlAreaComandos.add(pnlComandos)
      this.add(pnlAreaComandos, BorderLayout.CENTER)

      JLabel lblLogomarca = new JLabel(img.icon('logo.gif'))
      this.add(lblLogomarca, BorderLayout.EAST)
    }

  void actionPerformed(java.awt.event.ActionEvent actionEvent) {
    Object objeto = actionEvent.getSource()

    if(objeto == btSuprimentos) {
      AreaTrabalhoSuprimentos areaTrabalho = new AreaTrabalhoSuprimentos(aplicacao)
      this.aplicacao.adicionarAreaTrabalho(areaTrabalho)
    }

    if(objeto == btProducao) {
      AreaTrabalhoProducao areaTrabalho = new AreaTrabalhoProducao(aplicacao)
      this.aplicacao.adicionarAreaTrabalho(areaTrabalho)
    }

    if(objeto == btPedidos) {
      AreaTrabalhoPedido areaTrabalho = new AreaTrabalhoPedido(aplicacao)
      this.aplicacao.adicionarAreaTrabalho(areaTrabalho)
    }

    if(objeto == btLogistica) {
      AreaTrabalhoLogistica areaTrabalho = new AreaTrabalhoLogistica(aplicacao)
      this.aplicacao.adicionarAreaTrabalho(areaTrabalho)
    }

    if(objeto == btAdministracao) {
      AreaTrabalhoAdministracao areaTrabalho = new AreaTrabalhoAdministracao(aplicacao)
      this.aplicacao.adicionarAreaTrabalho(areaTrabalho)
    }
  }

}
