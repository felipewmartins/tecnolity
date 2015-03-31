package org.esmerilprogramming.tecnolity.aplicacao

import java.awt.*
import java.sql.*
import java.awt.event.*
import javax.swing.*
import org.esmerilprogramming.tecnolity.util.*
import org.esmerilprogramming.tecnolity.aplicacao.modelos.*

class DlgBancoDados extends JDialog implements ActionListener
{
  final int IDENTIFICADOR = 7

    private Aplicacao aplicacao
    private Container conteudo
    private GridBagLayout gridbag
    private GridBagConstraints gbc

    private JTextField txtCaminhoArquivo
    private JTextArea txaComandos, txaResultados
    private JButton btAbrir, btExecutar, btLimpar, btFechar
    private File arqComandos
    private JTable tblDados
    private ModeloTabelaVisualizacao modeloTabelaVisualizacao

    DlgBancoDados(Aplicacao aplicacao) {
      super(aplicacao,true)

        this.setTitle("Comandos de Banco de Dados")

        this.aplicacao = aplicacao

        this.conteudo = this.getContentPane()

        gridbag = new GridBagLayout()
        gbc = new GridBagConstraints()
        gbc.fill = GridBagConstraints.NONE
        gbc.anchor = GridBagConstraints.NORTHWEST
        gbc.insets.bottom = 2
        gbc.insets.left = 2
        gbc.insets.right = 2
        gbc.insets.top = 2

        JPanel pnlDados = new JPanel(gridbag)
        JLabel label = new JLabel("Comando SQL")
        adicionarComponente(pnlDados,label,1,0,1,1)
        label = new JLabel("Importar Arquivo:")
        adicionarComponente(pnlDados,label,0,0,2,1)
        txtCaminhoArquivo = new JTextField(20)
        txtCaminhoArquivo.setEditable(false)
        adicionarComponente(pnlDados,txtCaminhoArquivo,0,2,1,1)
        btAbrir = new JButton("Abrir...")
        btAbrir.addActionListener(this)
        adicionarComponente(pnlDados,btAbrir,0,3,1,1)
        txaComandos = new JTextArea(4,40)
        txaComandos.setLineWrap(true)
        txaComandos.setWrapStyleWord(true)
        JScrollPane scroll = new JScrollPane(txaComandos)
        adicionarComponente(pnlDados,scroll,2,0,4,1)

        JPanel pnlComandos = new JPanel(new FlowLayout(FlowLayout.RIGHT))
        btExecutar = new JButton("Executar")
        btExecutar.addActionListener(this)
        pnlComandos.add(btExecutar)
        btLimpar = new JButton("Limpar")
        btLimpar.addActionListener(this)
        pnlComandos.add(btLimpar)
        adicionarComponente(pnlDados,pnlComandos,3,0,4,1)

        label = new JLabel("Resultado")
        adicionarComponente(pnlDados,label,4,0,1,1)
        txaResultados = new JTextArea(2,40)
        txaResultados.setLineWrap(true)
        txaResultados.setWrapStyleWord(true)
        scroll = new JScrollPane(txaResultados)
        adicionarComponente(pnlDados,scroll,5,0,4,1)

        this.conteudo.add(pnlDados, BorderLayout.NORTH)

        modeloTabelaVisualizacao = new ModeloTabelaVisualizacao()
        modeloTabelaVisualizacao.definirConexao(aplicacao.obterConexao())
        tblDados = new JTable(modeloTabelaVisualizacao)
        tblDados.setPreferredScrollableViewportSize(new Dimension(700, 300))
        scroll = new JScrollPane(tblDados)
        this.conteudo.add(scroll, BorderLayout.CENTER)

        pnlComandos = new JPanel(new FlowLayout(FlowLayout.RIGHT))
        btFechar = new JButton("Fechar")
        btFechar.addActionListener(this)
        pnlComandos.add(btFechar)
        this.conteudo.add(pnlComandos, BorderLayout.SOUTH)

        this.pack()

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize()
        this.setBounds((screenSize.width/2) - (this.getBounds().width/2),
            (screenSize.height/2) - (this.getBounds().height/2) - 30,
            this.getBounds().width,
            this.getBounds().height)
    }	

  private void adicionarComponente(JPanel painel, Component c, int linha, int coluna, int largura, int altura) {
    gbc.gridx = coluna
      gbc.gridy = linha

      gbc.gridwidth = largura
      gbc.gridheight = altura

      gridbag.setConstraints(c,gbc)
      painel.add(c)
  }

  void importarArquivoComandoSQL() {
    StringBuffer conteudoArquivo = new StringBuffer()
      try
      {
        FileReader entrada = new FileReader(arqComandos)
          int indiceRegistro = 0
          while((indiceRegistro = entrada.read()) != -1) {
            conteudoArquivo.append((char)indiceRegistro)
          }
        txaComandos.setText(conteudoArquivo.toString())
      }
    catch(FileNotFoundException e) {
      JOptionPane.showMessageDialog(aplicacao,"Erro: Arquivo informado não encontrado.","Erro",JOptionPane.ERROR_MESSAGE)
        e.printStackTrace()
    }
    catch(IOException ex) {
      JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível ler o arquivo informado.","Erro",JOptionPane.ERROR_MESSAGE)
        ex.printStackTrace()
    }
  }

  private char reconhecerTipoComando(String comando) {
    // Verifica se o comando é uma consulta. Caso contrário, retorna uma transação.
    if(comando.indexOf("SELECT") >= 0 && comando.indexOf("INSERT") == -1 && comando.indexOf("UPDATE") == -1 && comando.indexOf("DELETE") == -1 && comando.indexOf("CREATE") == -1 && comando.indexOf("ALTER") == -1)
      return 'C'
    else
      return 'T'
  }

  void actionPerformed(java.awt.event.ActionEvent actionEvent) {
    Object objeto = actionEvent.getSource()

      if(objeto == btAbrir) {
        JFileChooser fchComando = new JFileChooser(Configuracao.getRepositorioConsultas())
          int status = fchComando.showOpenDialog(this)

          if(status == JFileChooser.APPROVE_OPTION) {
            arqComandos = fchComando.getSelectedFile()
              txtCaminhoArquivo.setText(arqComandos.getAbsolutePath())
          }

        importarArquivoComandoSQL()
      }

    if(objeto == btExecutar) {
      try
      {
        txaComandos.setText(txaComandos.getText().toUpperCase())
          char tipoComando = reconhecerTipoComando(txaComandos.getText())
          if(tipoComando == 'C') {
            modeloTabelaVisualizacao.definirConsulta(txaComandos.getText())
              tblDados.setModel(modeloTabelaVisualizacao)
              tblDados.updateUI()
              txaResultados.setText("Consulta Realizada com Sucesso!")
          }
          else if(tipoComando == 'T') {
            Conexao conexao = new Conexao(tipoComando)
              if(conexao.abrirConexao()) {
                conexao.executarAtualizacao(txaComandos.getText())
                  txaResultados.setText("Alteração Realizada com Sucesso!")
              }
            conexao.fecharConexao()
          }
      }
      catch(SQLException e) {
        txaResultados.setText("Erro Nº:" + e.getErrorCode() + " - " + e.getMessage())
          e.printStackTrace()
      }
    }

    if(objeto == btLimpar) {
      this.txaComandos.setText("")
    }

    if(objeto == btFechar) {
      this.setVisible(false)
    }
  }
}
