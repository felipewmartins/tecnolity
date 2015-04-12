package org.esmerilprogramming.tecnolity.administracao.ui

import java.sql.*
import java.awt.*
import java.awt.event.*
import javax.swing.*
import org.esmerilprogramming.tecnolity.administracao.*
import org.esmerilprogramming.tecnolity.aplicacao.Aplicacao
import org.esmerilprogramming.tecnolity.aplicacao.Interface

class DlgDadosPermissao extends JDialog implements ActionListener {
  final int IDENTIFICADOR = 25

    private Aplicacao aplicacao
    private char modo // I = inserir, A = alterar, V = visualizar
    private Container conteudo
    private GridBagLayout gridbag
    private GridBagConstraints gbc
    private JComboBox cbxColaborador
    private JList lstInterface, lstInterfaceAcessoLeitura, lstInterfaceAcessoEscrita
    private JButton btAdicionarLeitura, btRemoverLeitura, btAdicionarEscrita, btRemoverEscrita, btAplicar, btConfirmar, btCancelar
    private Vector colaboradores, interfaces, acessosLeitura, acessosEscrita

    DlgDadosPermissao(Aplicacao aplicacao, char modo) {
      super(aplicacao, true)

        this.setTitle('Atribuição de Permissões')

        this.aplicacao = aplicacao
        this.modo = modo

        this.conteudo = this.getContentPane()

        gridbag = new GridBagLayout()
        gbc = new GridBagConstraints()
        gbc.fill = GridBagConstraints.NONE
        gbc.anchor = GridBagConstraints.NORTHWEST
        gbc.insets.bottom = 2
        gbc.insets.left = 2
        gbc.insets.right = 2
        gbc.insets.top = 2

        JPanel pnlColaborador = new JPanel(new FlowLayout(FlowLayout.LEFT))
        JLabel label = new JLabel('Colaborador:')
        pnlColaborador.add(label)
        cbxColaborador = new JComboBox()
        Colaborador colaborador = new Colaborador()
        try {
          colaboradores = colaborador.carregarColaboradores(aplicacao.obterConexao())
        }
      catch (e) {
        JOptionPane.showMessageDialog(aplicacao, 'Erro: '  +  e.getMessage(), 'Erro', JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
      carregarColaboradores()
        cbxColaborador.addActionListener(this)
        pnlColaborador.add(cbxColaborador)
        conteudo.add(pnlColaborador, BorderLayout.NORTH)

        JPanel pnlInterfaces = new JPanel(new BorderLayout(5, 5))
        label = new JLabel('Interfaces')
        pnlInterfaces.add(label, BorderLayout.NORTH)
        this.lstInterface = new JList()

        JScrollPane scroll = new JScrollPane(lstInterface)
        pnlInterfaces.add(scroll, BorderLayout.CENTER)
        conteudo.add(pnlInterfaces, BorderLayout.WEST)

        JPanel pnlPermissoes = new JPanel(new BorderLayout())

        JPanel pnlPermissaoLeitura = new JPanel(new BorderLayout())
        JPanel pnlComandosLeitura = new JPanel(gridbag)
        btAdicionarLeitura = new JButton('Adicionar >>')
        btAdicionarLeitura.addActionListener(this)
        adicionarComponente(pnlComandosLeitura, btAdicionarLeitura, 0, 0, 1, 1)
        btRemoverLeitura = new JButton(' << Remover')
        btRemoverLeitura.addActionListener(this)
        adicionarComponente(pnlComandosLeitura, btRemoverLeitura, 1, 0, 1, 1)
        pnlPermissaoLeitura.add(pnlComandosLeitura, BorderLayout.WEST)

        JPanel pnlListaLeitura = new JPanel(gridbag)
        label = new JLabel('Acesso de Leitura')
        adicionarComponente(pnlListaLeitura, label, 0, 0, 1, 1)
        pnlPermissoes.add(pnlPermissaoLeitura, BorderLayout.NORTH)
        DefaultListModel modeloLista = new DefaultListModel()
        lstInterfaceAcessoLeitura = new JList(modeloLista)
        lstInterfaceAcessoLeitura.setFixedCellWidth(180)
        scroll = new JScrollPane(lstInterfaceAcessoLeitura)
        adicionarComponente(pnlListaLeitura, scroll, 1, 0, 1, 1)
        pnlPermissaoLeitura.add(pnlListaLeitura, BorderLayout.CENTER)
        pnlPermissoes.add(pnlPermissaoLeitura, BorderLayout.NORTH)

        JPanel pnlPermissaoEscrita = new JPanel(new BorderLayout())
        JPanel pnlComandosEscrita = new JPanel(gridbag)
        btAdicionarEscrita = new JButton('Adicionar >>')
        btAdicionarEscrita.addActionListener(this)
        adicionarComponente(pnlComandosEscrita, btAdicionarEscrita, 0, 0, 1, 1)
        btRemoverEscrita   = new JButton(' << Remover')
        btRemoverEscrita.addActionListener(this)
        adicionarComponente(pnlComandosEscrita, btRemoverEscrita, 1, 0, 1, 1)
        pnlPermissaoEscrita.add(pnlComandosEscrita, BorderLayout.WEST)

        JPanel pnlListaEscrita = new JPanel(gridbag)
        label = new JLabel('Acesso de Escrita')
        adicionarComponente(pnlListaEscrita, label, 0, 0, 1, 1)
        modeloLista = new DefaultListModel()
        lstInterfaceAcessoEscrita = new JList(modeloLista)
        lstInterfaceAcessoEscrita.setFixedCellWidth(180)
        scroll = new JScrollPane(lstInterfaceAcessoEscrita)
        adicionarComponente(pnlListaEscrita, scroll, 1, 0, 1, 1)
        pnlPermissaoEscrita.add(pnlListaEscrita, BorderLayout.CENTER)
        pnlPermissoes.add(pnlPermissaoEscrita, BorderLayout.SOUTH)
        conteudo.add(pnlPermissoes, BorderLayout.CENTER)

        JPanel pnlComandos = new JPanel(new FlowLayout(FlowLayout.RIGHT))
        btAplicar = new JButton('Aplicar')
        btAplicar.addActionListener(this)
        pnlComandos.add(btAplicar)
        btConfirmar = new JButton('Confirmar')
        btConfirmar.addActionListener(this)
        pnlComandos.add(btConfirmar)
        btCancelar = new JButton('Cancelar')
        btCancelar.addActionListener(this)
        pnlComandos.add(btCancelar)
        this.conteudo.add(pnlComandos, BorderLayout.SOUTH)

        this.carregarPermissoes()

        this.pack()

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize()
        this.setBounds((screenSize.width/2) - (this.getBounds().width/2),
            (screenSize.height/2) - (this.getBounds().height/2) - 30,
            this.getBounds().width,
            this.getBounds().height)
    }

  private void carregarColaboradores() {
    cbxColaborador.removeAllItems()
      cbxColaborador.addItem('Selecione...')
      for(int i = 1;i < colaboradores.size();i++) {
        cbxColaborador.addItem(((Colaborador)colaboradores.get(i)).getNome())
      }
  }

  private void carregarInterfaces() {
    DefaultListModel modeloLista = new DefaultListModel()
      this.lstInterface.setModel(modeloLista)
      for(int i = 0;i < interfaces.size();i++) {
        modeloLista.addElement(((Interface)interfaces.get(i)).obterNomeInterface()  +  ' ('+ ((Interface)interfaces.get(i)).obterIdentificador() +')')
      }
  }

  private void carregarPermissoesLeitura() {
    DefaultListModel modeloLista = new DefaultListModel()
      lstInterfaceAcessoLeitura.setModel(modeloLista)
      for(int i = 0;i < acessosLeitura.size();i++) {
        modeloLista.addElement(((Interface)acessosLeitura.get(i)).obterNomeInterface())
      }
  }

  private void carregarPermissoesEscrita() {
    DefaultListModel modeloLista = new DefaultListModel()
      lstInterfaceAcessoEscrita.setModel(modeloLista)
      for(int i = 0;i < acessosEscrita.size();i++) {
        modeloLista.addElement(((Interface)acessosEscrita.get(i)).obterNomeInterface())
      }
  }

  /**
   *  Alimenta as listas com as interfaces do sistema. Caso seja escolhido um
   *  usuário que já possua permissões definidas no sistema, suas permissões
   *  de leitura e escrita serão apresentadas, ao mesmo tempo que as mesmas
   *  são retiradas da lista de interfaces.
   */
  private void carregarPermissoes() {
    Colaborador colaborador = (Colaborador)colaboradores.get(cbxColaborador.getSelectedIndex())
      Vector permissoes

      interfaces = new Vector()
      acessosLeitura = new Vector()
      acessosEscrita = new Vector()

      Interface tela = new Interface()

      try {
        interfaces = tela.carregarInterfaces(aplicacao.obterConexao())
      }
    catch (e) {
      JOptionPane.showMessageDialog(aplicacao, 'Erro: '  +  e.getMessage(), 'Erro', JOptionPane.ERROR_MESSAGE)
    }

    if(colaborador != null) {
      try {
        colaborador.carregarPermissoes(aplicacao.obterConexao())
          permissoes = colaborador.obterPermissoes()
          for(int i = 0 ;i < permissoes.size();i++) {
            Permissao permissao = (Permissao)permissoes.get(i)
              if(permissao.obterTipoAcesso() == 'L') {
                acessosLeitura.addElement(permissao.obterTela())
              }
            if(permissao.obterTipoAcesso() == 'E') {
              acessosEscrita.addElement(permissao.obterTela())
            }

            for(int j = 0;j < interfaces.size();j++) {
              if(((Interface)interfaces.get(j)).obterIdentificador() == ((Permissao)permissoes.get(i)).obterTela().obterIdentificador())
                interfaces.removeElementAt(j)
            }
          }
      }
      catch (SQLException e) {
        JOptionPane.showMessageDialog(aplicacao, 'Erro: ' +  e.getMessage(), 'Erro', JOptionPane.ERROR_MESSAGE)
      }
    }
    this.carregarInterfaces()
      this.carregarPermissoesLeitura()
      this.carregarPermissoesEscrita()
  }

  private void adicionarComponente(JPanel painel, Component c, int linha, int coluna, int largura, int altura) {
    gbc.gridx = coluna
      gbc.gridy = linha

      gbc.gridwidth = largura
      gbc.gridheight = altura

      gridbag.setConstraints(c, gbc)
      painel.add(c)
  }

  void actionPerformed(java.awt.event.ActionEvent actionEvent) {
    Object objeto = actionEvent.getSource()

      if(objeto == cbxColaborador) {
        this.carregarPermissoes()
      }

    if(objeto == btAdicionarLeitura) {
      if(!lstInterface.isSelectionEmpty()) {
        acessosLeitura.addElement(interfaces.get(lstInterface.getSelectedIndex()))
          interfaces.removeElementAt(lstInterface.getSelectedIndex())
          DefaultListModel modelo = (DefaultListModel)lstInterface.getModel()
          DefaultListModel modeloSelecionado = (DefaultListModel)lstInterfaceAcessoLeitura.getModel()
          modeloSelecionado.addElement(((Interface)acessosLeitura.get(acessosLeitura.size() - 1)).obterNomeInterface())
          modelo.removeElementAt(lstInterface.getSelectedIndex())
      }
      else
      {
        JOptionPane.showMessageDialog(aplicacao, 'Selecione uma interface.', 'Erro', JOptionPane.WARNING_MESSAGE)
      }
    }

    if(objeto == btRemoverLeitura) {
      if(!lstInterfaceAcessoLeitura.isSelectionEmpty()) {
        interfaces.addElement(acessosLeitura.get(lstInterfaceAcessoLeitura.getSelectedIndex()))
          acessosLeitura.removeElementAt(lstInterfaceAcessoLeitura.getSelectedIndex())
          DefaultListModel modelo = (DefaultListModel)lstInterface.getModel()
          DefaultListModel modeloSelecionado = (DefaultListModel)lstInterfaceAcessoLeitura.getModel()
          modelo.addElement(((Interface)interfaces.get(interfaces.size() - 1)).obterNomeInterface())
          modeloSelecionado.removeElementAt(lstInterfaceAcessoLeitura.getSelectedIndex())
      }
      else
      {
        JOptionPane.showMessageDialog(aplicacao, 'Selecione uma interface com acesso de Leitura.', 'Erro', JOptionPane.WARNING_MESSAGE)
      }
    }

    if(objeto == btAdicionarEscrita) {
      if(!lstInterface.isSelectionEmpty()) {
        acessosEscrita.addElement(interfaces.get(lstInterface.getSelectedIndex()))
          interfaces.removeElementAt(lstInterface.getSelectedIndex())
          DefaultListModel modelo = (DefaultListModel)lstInterface.getModel()
          DefaultListModel modeloSelecionado = (DefaultListModel)lstInterfaceAcessoEscrita.getModel()
          modeloSelecionado.addElement(lstInterface.getSelectedValue())
          modelo.removeElementAt(lstInterface.getSelectedIndex())
      }
      else
      {
        JOptionPane.showMessageDialog(aplicacao, 'Selecione uma interface.', 'Erro', JOptionPane.WARNING_MESSAGE)
      }
    }

    if(objeto == btRemoverEscrita) {
      if(!lstInterfaceAcessoEscrita.isSelectionEmpty()) {
        interfaces.addElement(acessosEscrita.get(lstInterfaceAcessoEscrita.getSelectedIndex()))
          acessosEscrita.removeElementAt(lstInterfaceAcessoEscrita.getSelectedIndex())
          DefaultListModel modelo = (DefaultListModel)lstInterface.getModel()
          DefaultListModel modeloSelecionado = (DefaultListModel)lstInterfaceAcessoEscrita.getModel()
          modelo.addElement(lstInterfaceAcessoEscrita.getSelectedValue())
          modeloSelecionado.removeElementAt(lstInterfaceAcessoEscrita.getSelectedIndex())
      }
      else
      {
        JOptionPane.showMessageDialog(aplicacao, 'Selecione uma interface com acesso de Escrita.', 'Erro', JOptionPane.WARNING_MESSAGE)
      }
    }

    if(objeto == btAplicar) {
      if(cbxColaborador.getSelectedIndex() > 0) {
        Vector permissoes = new Vector()
          for(int i = 0;i < acessosLeitura.size();i++) {
            permissoes.addElement(new Permissao(((Interface)acessosLeitura.get(i)), 'L'))
          }
        for(int i = 0;i < acessosEscrita.size();i++) {
          permissoes.addElement(new Permissao(((Interface)acessosEscrita.get(i)), 'E'))
        }
        try {
          ((Colaborador)colaboradores.get(cbxColaborador.getSelectedIndex())).definirPermissoes(permissoes)
        }
        catch (e) {
          JOptionPane.showMessageDialog(aplicacao, 'Erro: '  +  e.getMessage(), 'Erro', JOptionPane.ERROR_MESSAGE)
        }
      }
      else
      {
        JOptionPane.showMessageDialog(aplicacao, 'Erro: Selecione um Colaborador antes de aplicar as alterações.', 'Erro', JOptionPane.WARNING_MESSAGE)
      }
    }

    if(objeto == btConfirmar) {
      if(cbxColaborador.getSelectedIndex() > 0) {
        Vector permissoes = new Vector()
          for(int i = 0;i < acessosLeitura.size();i++) {
            permissoes.addElement(new Permissao(((Interface)acessosLeitura.get(i)), 'L'))
          }
        for(int i = 0;i < acessosEscrita.size();i++) {
          permissoes.addElement(new Permissao(((Interface)acessosEscrita.get(i)), 'E'))
        }
        try {
          ((Colaborador)colaboradores.get(cbxColaborador.getSelectedIndex())).definirPermissoes(permissoes)
            this.setVisible(false)
        }
        catch (e) {
          JOptionPane.showMessageDialog(aplicacao, 'Erro: '  +  e.getMessage(), 'Erro', JOptionPane.ERROR_MESSAGE)
        }
      }
      else
      {
        JOptionPane.showMessageDialog(aplicacao, 'Erro: Selecione um Colaborador antes de confirmar as alterações.', 'Erro', JOptionPane.WARNING_MESSAGE)
      }
    }

    if(objeto == btCancelar) {
      this.setVisible(false)
    }
  }
}
