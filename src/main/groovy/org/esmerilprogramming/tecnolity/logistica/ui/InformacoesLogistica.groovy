package org.esmerilprogramming.tecnolity.logistica.ui

import java.awt.*
import java.awt.event.*
import javax.swing.*
import javax.swing.border.*
import org.esmerilprogramming.tecnolity.aplicacao.Aplicacao
import org.esmerilprogramming.tecnolity.aplicacao.modelos.*
import org.esmerilprogramming.tecnolity.logistica.*

class InformacoesLogistica extends JTabbedPane implements ActionListener
{
  private Aplicacao aplicacao
    private JPanel pnlVeiculo, pnlExpedicao, pnlMotorista, pnlDespesa, pnlMulta
    private Vector transportadoras, veiculos
    private Veiculo veiculo = new Veiculo()

    /* Objetos da aba de Veículos */
    private ModeloTabela modeloTabelaVeiculos
    private JTable tblVeiculos
    private JButton btAdicionarVeiculo,btAlterarVeiculo,btExcluirVeiculo,btAtualizarVeiculo
    private JComboBox cbxTransportadora
    private Transportadora transportadora = new Transportadora()

    /* Objetos da aba Expedições */
    private ModeloTabela modeloTabelaExpedicoes
    private JTable tblExpedicoes
    private JButton btAdicionarExpedicao,btAlterarExpedicao,btExcluirExpedicao,btAtualizarExpedicao

    /* Objetos da aba Motoristas */
    private ModeloTabela modeloTabelaMotoristas
    private JTable tblMotoristas
    private JButton btAdicionarMotorista,btAlterarMotorista,btExcluirMotorista,btAtualizarMotorista

    /* Objetos da aba Despesas */
    private ModeloTabela modeloTabelaDespesas
    private JTable tblDespesas
    private JButton btAdicionarDespesa,btAlterarDespesa,btExcluirDespesa,btAtualizarDespesa
    private JComboBox cbxVeiculoDespesas

    /* Objetos da aba Multas */
    private ModeloTabela modeloTabelaMultas
    private JTable tblMultas
    private JButton btAdicionarMulta,btAlterarMulta,btExcluirMulta,btAtualizarMulta
    private JComboBox cbxVeiculoMultas

    InformacoesLogistica(Aplicacao aplicacao) {
      this.setBorder(new LineBorder(Color.black))
        this.aplicacao = aplicacao

        // Conteúdo da Aba Veículos
        pnlVeiculo = new JPanel(new BorderLayout())

        JPanel pnlParametroVeiculo = new JPanel(new FlowLayout(FlowLayout.LEFT))
        JLabel labelParametroVeiculo = new JLabel("Transportadoras: ")
        pnlParametroVeiculo.add(labelParametroVeiculo)
        cbxTransportadora = new JComboBox()
        try
        {
          transportadoras = Transportadora.carregarTransportadoras(aplicacao.obterConexao())
            carregarTransportadoras()
        }
      catch(Exception e) {
        JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar as Transportadoras.","Erro",JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
      cbxTransportadora.addActionListener(this)

        pnlParametroVeiculo.add(cbxTransportadora)
        pnlVeiculo.add(pnlParametroVeiculo, BorderLayout.NORTH)

        modeloTabelaVeiculos = new ModeloTabelaVeiculos()
        modeloTabelaVeiculos.definirConexao(aplicacao.obterConexao())
        tblVeiculos = new JTable(modeloTabelaVeiculos)
        JScrollPane scrollVeiculos = new JScrollPane(tblVeiculos)
        pnlVeiculo.add(scrollVeiculos, BorderLayout.CENTER)

        JPanel pnlAreaComandos = new JPanel()
        JPanel pnlComandos = new JPanel(new GridLayout(4,1,5,5))
        btAdicionarVeiculo = new JButton("Adicionar Veículo")
        btAdicionarVeiculo.addActionListener(this)
        pnlComandos.add(btAdicionarVeiculo)
        btAlterarVeiculo = new JButton("Alterar Selecionado")
        btAlterarVeiculo.addActionListener(this)
        pnlComandos.add(btAlterarVeiculo)
        btExcluirVeiculo = new JButton("Excluir Selecionado")
        btExcluirVeiculo.addActionListener(this)
        pnlComandos.add(btExcluirVeiculo)
        btAtualizarVeiculo = new JButton("Atualizar Tabela")
        btAtualizarVeiculo.addActionListener(this)
        pnlComandos.add(btAtualizarVeiculo)
        pnlAreaComandos.add(pnlComandos)
        pnlVeiculo.add(pnlAreaComandos, BorderLayout.EAST)

        this.addTab("Veículos",pnlVeiculo)

        // Conteúdo da Aba Expedições
        pnlExpedicao = new JPanel(new BorderLayout())

        modeloTabelaExpedicoes = new ModeloTabela()
        modeloTabelaExpedicoes.definirConexao(aplicacao.obterConexao())
        tblExpedicoes = new JTable(modeloTabelaExpedicoes)
        JScrollPane scrollExpedicoes = new JScrollPane(tblExpedicoes)
        pnlExpedicao.add(scrollExpedicoes, BorderLayout.CENTER)

        pnlAreaComandos = new JPanel()
        pnlComandos = new JPanel(new GridLayout(4,1,5,5))
        btAdicionarExpedicao = new JButton("Adicionar Expedição")
        btAdicionarExpedicao.addActionListener(this)
        pnlComandos.add(btAdicionarExpedicao)
        btAlterarExpedicao = new JButton("Alterar Selecionado")
        btAlterarExpedicao.addActionListener(this)
        pnlComandos.add(btAlterarExpedicao)
        btExcluirExpedicao = new JButton("Excluir Selecionado")
        btExcluirExpedicao.addActionListener(this)
        pnlComandos.add(btExcluirExpedicao)
        btAtualizarExpedicao = new JButton("Atualizar Tabela")
        pnlComandos.add(btAtualizarExpedicao)
        pnlAreaComandos.add(pnlComandos)
        pnlExpedicao.add(pnlAreaComandos, BorderLayout.EAST)

        this.addTab("Expedições",pnlExpedicao)

        // Conteúdo da Aba Motoristas
        pnlMotorista = new JPanel(new BorderLayout())

        modeloTabelaMotoristas = new ModeloTabela()
        modeloTabelaMotoristas.definirConexao(aplicacao.obterConexao())
        tblMotoristas = new JTable(modeloTabelaMotoristas)
        JScrollPane scrollMotoristas = new JScrollPane(tblMotoristas)
        pnlMotorista.add(scrollMotoristas, BorderLayout.CENTER)

        pnlAreaComandos = new JPanel()
        pnlComandos = new JPanel(new GridLayout(4,1,5,5))
        btAdicionarMotorista = new JButton("Adicionar Motorista")
        btAdicionarMotorista.addActionListener(this)
        pnlComandos.add(btAdicionarMotorista)
        btAlterarMotorista = new JButton("Alterar Selecionado")
        btAlterarMotorista.addActionListener(this)
        pnlComandos.add(btAlterarMotorista)
        btExcluirMotorista = new JButton("Excluir Selecionado")
        btExcluirMotorista.addActionListener(this)
        pnlComandos.add(btExcluirMotorista)
        btAtualizarMotorista = new JButton("Atualizar Tabela")
        btAtualizarMotorista.addActionListener(this)
        pnlComandos.add(btAtualizarMotorista)
        pnlAreaComandos.add(pnlComandos)
        pnlMotorista.add(pnlAreaComandos, BorderLayout.EAST)

        this.addTab("Motoristas",pnlMotorista)

        // Conteúdo da Aba Despesas
        pnlDespesa = new JPanel(new BorderLayout())

        modeloTabelaDespesas = new ModeloTabela()
        modeloTabelaDespesas.definirConexao(aplicacao.obterConexao())
        tblMultas = new JTable(modeloTabelaDespesas)
        JScrollPane scrollDespesas = new JScrollPane(tblMultas)
        pnlDespesa.add(scrollDespesas, BorderLayout.CENTER)

        JPanel pnlParametroDespesa = new JPanel(new FlowLayout(FlowLayout.LEFT))
        JLabel labelParametroDespesa = new JLabel("Veículo: ")
        pnlParametroDespesa.add(labelParametroDespesa)
        cbxVeiculoDespesas = new JComboBox()
        try
        {
          veiculos = Veiculo.carregarVeiculos(aplicacao.obterConexao())
            carregarVeiculosDespesas()
        }
      catch(Exception e) {
        JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar os Veículos.","Erro",JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
      cbxVeiculoDespesas.addActionListener(this)

        pnlParametroDespesa.add(cbxVeiculoDespesas)
        pnlDespesa.add(pnlParametroDespesa, BorderLayout.NORTH)

        pnlAreaComandos = new JPanel()
        pnlComandos = new JPanel(new GridLayout(4,1,5,5))
        btAdicionarDespesa = new JButton("Adicionar Despesa")
        btAdicionarDespesa.addActionListener(this)
        pnlComandos.add(btAdicionarDespesa)
        btAlterarDespesa = new JButton("Alterar Selecionado")
        btAlterarDespesa.addActionListener(this)
        pnlComandos.add(btAlterarDespesa)
        btExcluirDespesa = new JButton("Excluir Selecionado")
        btExcluirDespesa.addActionListener(this)
        pnlComandos.add(btExcluirDespesa)
        btAtualizarDespesa = new JButton("Atualizar Tabela")
        btAtualizarDespesa.addActionListener(this)
        pnlComandos.add(btAtualizarDespesa)
        pnlAreaComandos.add(pnlComandos)
        pnlDespesa.add(pnlAreaComandos, BorderLayout.EAST)

        this.addTab("Despesas",pnlDespesa)

        // Conteúdo da Aba Multas
        pnlMulta = new JPanel(new BorderLayout())

        modeloTabelaMultas = new ModeloTabela()
        modeloTabelaMultas.definirConexao(aplicacao.obterConexao())
        tblMultas = new JTable(modeloTabelaMultas)
        JScrollPane scrollMultas = new JScrollPane(tblMultas)
        pnlMulta.add(scrollMultas, BorderLayout.CENTER)

        JPanel pnlParametroMulta = new JPanel(new FlowLayout(FlowLayout.LEFT))
        JLabel labelParametroMulta = new JLabel("Veículo: ")
        pnlParametroMulta.add(labelParametroMulta)
        cbxVeiculoMultas = new JComboBox()
        try
        {
          veiculos = Veiculo.carregarVeiculos(aplicacao.obterConexao())
            carregarVeiculosMultas()
        }
      catch(Exception e) {
        JOptionPane.showMessageDialog(aplicacao,"Erro: Não foi possível carregar os Veículos.","Erro",JOptionPane.ERROR_MESSAGE)
          e.printStackTrace()
      }
      cbxVeiculoMultas.addActionListener(this)

        pnlParametroMulta.add(cbxVeiculoMultas)
        pnlMulta.add(pnlParametroMulta, BorderLayout.NORTH)

        pnlAreaComandos = new JPanel()
        pnlComandos = new JPanel(new GridLayout(4,1,5,5))
        btAdicionarMulta = new JButton("Adicionar Multa")
        btAdicionarMulta.addActionListener(this)
        pnlComandos.add(btAdicionarMulta)
        btAlterarMulta = new JButton("Alterar Selecionado")
        btAlterarMulta.addActionListener(this)
        pnlComandos.add(btAlterarMulta)
        btExcluirMulta = new JButton("Excluir Selecionado")
        btExcluirMulta.addActionListener(this)
        pnlComandos.add(btExcluirMulta)
        btAtualizarMulta = new JButton("Atualizar Tabela")
        btAtualizarMulta.addActionListener(this)
        pnlComandos.add(btAtualizarMulta)
        pnlAreaComandos.add(pnlComandos)
        pnlMulta.add(pnlAreaComandos, BorderLayout.EAST)

        this.addTab("Multas",pnlMulta)
    }

  private void carregarTransportadoras() {
    cbxTransportadora.removeAllItems()
      cbxTransportadora.addItem("Selecione...")

      for(int i = 1;i < transportadoras.size();i++) {
        cbxTransportadora.addItem(((Transportadora)transportadoras.get(i)).obterNome())
      }
  }

  private void carregarVeiculosDespesas() {
    cbxVeiculoDespesas.removeAllItems()
      cbxVeiculoDespesas.addItem("Selecione...")

      for(int i = 1;i < veiculos.size();i++) {
        cbxVeiculoDespesas.addItem(((Veiculo)veiculos.get(i)).obterPlaca())
      }
  }

  private void carregarVeiculosMultas() {
    cbxVeiculoMultas.removeAllItems()
      cbxVeiculoMultas.addItem("Selecione...")

      for(int i = 1;i < veiculos.size();i++) {
        cbxVeiculoMultas.addItem(((Veiculo)veiculos.get(i)).obterPlaca())
      }
  }

  private void atualizarTabelaVeiculos() {
    String sql = "select placa, transportadora.transportadora, marca, modelo, ano, cubagem, tara from veiculo, transportadora where transportadora.codigo = veiculo.transportadora order by marca asc"
      modeloTabelaMotoristas.definirConsulta(sql)
      tblVeiculos.setModel(modeloTabelaMotoristas)
      tblVeiculos.updateUI()
  }

  private void atualizarTabelaExpedicoes() {
    modeloTabelaExpedicoes.definirConsulta("select codigo, veiculo, data, responsavel from expedicao order by data desc")
      tblExpedicoes.setModel(modeloTabelaExpedicoes)
      tblExpedicoes.updateUI()
  }

  private void atualizarTabelaMotoristas() {
    modeloTabelaMotoristas.definirConsulta("select codigo,motorista,habilitacao,telefone from motorista order by motorista desc")
      tblMotoristas.setModel(modeloTabelaMotoristas)
      tblMotoristas.updateUI()
  }

  private void atualizarTabelaDespesas() {
    modeloTabelaDespesas.definirConsulta("select veiculo, datahora, descricao, valor from despesa_veiculo order by datahora desc")
      tblDespesas.setModel(modeloTabelaDespesas)
      tblDespesas.updateUI()
  }

  private void atualizarTabelaMultas() {
    modeloTabelaMultas.definirConsulta("select codigo, placa, motivo, valor, datahora from multa order by datahora desc")
      tblMultas.setModel(modeloTabelaMultas)
      tblMultas.updateUI()
  }

  void actionPerformed(java.awt.event.ActionEvent actionEvent) {
    Object objeto = actionEvent.getSource()

      if (objeto == cbxTransportadora) {
        atualizarTabelaVeiculos()
      }

    if(objeto == cbxVeiculoDespesas) {
      Veiculo veiculoSelecionado = (Veiculo)veiculos.get(cbxVeiculoDespesas.getSelectedIndex())
        String sql = "select veiculo as 'veículo', datahora as 'data', descricao as 'descrição', valor from despesa_veiculo where veiculo = '" + ((veiculoSelecionado == null)?"":veiculoSelecionado.obterPlaca()) + "' order by datahora desc"
        modeloTabelaDespesas.definirConsulta(sql)

        this.tblDespesas = new JTable(modeloTabelaDespesas)
        JScrollPane scrollDespesas = new JScrollPane(this.tblDespesas)
        pnlDespesa.add(scrollDespesas, BorderLayout.CENTER)
        this.pnlDespesa.updateUI()
    }

    if(objeto == cbxVeiculoMultas) {
      Veiculo veiculoSelecionado = (Veiculo)veiculos.get(cbxVeiculoMultas.getSelectedIndex())
        String sql = "select multa.codigo,multa.placa, motivo, valor, motorista from multa, motorista where multa.placa = '" + ((veiculoSelecionado == null)?"":veiculoSelecionado.obterPlaca()) + "' and multa.responsabilidade = motorista.codigo order by motorista asc"
        modeloTabelaMultas.definirConsulta(sql)

        this.tblMultas = new JTable(modeloTabelaMultas)
        JScrollPane scrollMultas = new JScrollPane(this.tblMultas)
        pnlMulta.add(scrollMultas, BorderLayout.CENTER)
        this.pnlMulta.updateUI()
    }

    if(objeto == btAdicionarVeiculo) {
      DlgDadosVeiculo dlgDadosVeiculo = new DlgDadosVeiculo(aplicacao)
        dlgDadosVeiculo.setVisible(true)
        atualizarTabelaVeiculos()
    }

    if(objeto == btAlterarVeiculo) {
      if(this.tblVeiculos.getSelectedRow() >= 0) {
        try
        {
          int linha = this.tblVeiculos.getSelectedRow()
            String placaVeiculo = (String)tblVeiculos.getValueAt(linha,0)
            DlgDadosVeiculo dlgDadosVeiculo = new DlgDadosVeiculo(aplicacao,new Veiculo(placaVeiculo,aplicacao.obterConexao()))
            dlgDadosVeiculo.setVisible(true)
        }
        catch(Exception e) {
          JOptionPane.showMessageDialog(aplicacao,"Erro: " + e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE)
        }
      }
      else
      {
        JOptionPane.showMessageDialog(aplicacao,"Atenção: Selecione uma linha da visão para alterar os dados do veículo.","Atenção",JOptionPane.WARNING_MESSAGE)
      }
    }

    if(objeto == btExcluirVeiculo) {
      if(tblVeiculos.getSelectedRow() >=0) {
        if(JOptionPane.showConfirmDialog(aplicacao,"Atenção: Tem certeza que deseja excluir o veículo selecionado?","Atenção",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE) == 0) {
          int linha = tblVeiculos.getSelectedRow()
            String placaVeiculo = (String)tblVeiculos.getValueAt(linha,0)
            Veiculo veiculo = new Veiculo(placaVeiculo)
            try
            {
              veiculo.excluirVeiculo()
            }
          catch(Exception e) {
            e.printStackTrace()
          }
        }
      }
      else
      {
        JOptionPane.showMessageDialog(aplicacao,"Atenção: Selecione uma linha da visão para excluir um veículo.","Atenção",JOptionPane.WARNING_MESSAGE)
      }
    }

    if(objeto == btAtualizarVeiculo) {
      atualizarTabelaVeiculos()
    }

    if(objeto == btAdicionarMotorista) {
      DlgDadosMotorista dlgDadosMotorista = new DlgDadosMotorista(aplicacao,'I')
        dlgDadosMotorista.setVisible(true)
    }

    if(objeto == btAlterarMotorista) {
      if(this.tblMotoristas.getSelectedRow() >= 0) {
        int linha = this.tblMotoristas.getSelectedRow()
          int codigoMotorista = Integer.parseInt((String)tblMotoristas.getValueAt(linha,0))
          DlgDadosMotorista dlgDadosMotorista = new DlgDadosMotorista(aplicacao,'A',codigoMotorista)
          dlgDadosMotorista.setVisible(true)
      }
      else
      {
        JOptionPane.showMessageDialog(aplicacao,"Atenção: Selecione uma linha da visão para alterar os dados do motorista.","Atenção",JOptionPane.WARNING_MESSAGE)
      }
    }

    if(objeto == btExcluirMotorista) {
      if(tblMotoristas.getSelectedRow() >=0) {
        if(JOptionPane.showConfirmDialog(aplicacao,"Atenção: Tem certeza que deseja excluir o motorista selecionado?","Atenção",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE) == 0) {
          int linha = tblMotoristas.getSelectedRow()
            int codigoMotorista = Integer.parseInt((String)tblMotoristas.getValueAt(linha,0))
            Motorista motorista = new Motorista(codigoMotorista)
            try
            {
              motorista.excluirMotorista()
            }
          catch(Exception e) {
            e.printStackTrace()
          }
        }
      }
      else
      {
        JOptionPane.showMessageDialog(aplicacao,"Atenção: Selecione uma linha da visão para excluir um motorista.","Atenção",JOptionPane.WARNING_MESSAGE)
      }
    }

    if(objeto == btAtualizarMotorista) {
      String sql = "select codigo as 'código', motorista, placa, habilitacao as 'habilitação', categoria, validade, telefone from motorista order by codigo asc"
        modeloTabelaMotoristas.definirConsulta(sql)
        tblMotoristas.setModel(modeloTabelaMotoristas)
        tblMotoristas.updateUI()
    }

    if(objeto == btAdicionarDespesa) {
      DlgDadosDespesa dlgDadosDespesa = new DlgDadosDespesa(aplicacao,'I')
        dlgDadosDespesa.setVisible(true)
    }

    if(objeto == btAlterarDespesa) {
      if(((Veiculo)veiculos.get(this.cbxVeiculoDespesas.getSelectedIndex()))!=null) {
        if(this.tblDespesas.getSelectedRow() >= 0) {
          int linha = this.tblDespesas.getSelectedRow()
            String placa = (String)this.tblDespesas.getValueAt(linha,0)
            String data = (String)this.tblDespesas.getValueAt(linha,1)
            DlgDadosDespesa dlgDadosDespesa = new DlgDadosDespesa(aplicacao,'A',placa,data)
            dlgDadosDespesa.setVisible(true)
        }
        else
        {
          JOptionPane.showMessageDialog(aplicacao,"Atenção: Selecione uma linha da visão para alterar os dados da despesa.","Atenção",JOptionPane.WARNING_MESSAGE)
        }
      }
      else
      {
        JOptionPane.showMessageDialog(aplicacao,"Atenção: Selecione uma linha da visão para alterar os dados da despesa.","Atenção",JOptionPane.WARNING_MESSAGE)
      }
    }

    if(objeto == btExcluirDespesa) {
      if(((Veiculo)veiculos.get(this.cbxVeiculoDespesas.getSelectedIndex()))!=null) {
        if(tblDespesas.getSelectedRow() >=0) {
          if(JOptionPane.showConfirmDialog(aplicacao,"Atenção: Tem certeza que deseja excluir a despesa selecionada?","Atenção",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE) == 0) {
            int linha = tblDespesas.getSelectedRow()
              String placa = (String)this.tblDespesas.getValueAt(linha,0)
              String data = (String)this.tblDespesas.getValueAt(linha,1)
              Despesa despesa = new Despesa(placa,data)
              try
              {
                despesa.excluirDespesa()
              }
            catch(Exception e) {
              e.printStackTrace()
            }
          }
        }
        else
        {
          JOptionPane.showMessageDialog(aplicacao,"Atenção: Selecione uma linha da visão para excluir uma despesa.","Atenção",JOptionPane.WARNING_MESSAGE)
        }
      }
      else
      {
        JOptionPane.showMessageDialog(aplicacao,"Atenção: Selecione uma linha da visão para excluir uma despesa.","Atenção",JOptionPane.WARNING_MESSAGE)
      }
    }

    if(objeto == btAtualizarDespesa) {
      if(((Veiculo)veiculos.get(this.cbxVeiculoDespesas.getSelectedIndex()))!=null) {
        Veiculo veiculoSelecionado = (Veiculo)veiculos.get(cbxVeiculoDespesas.getSelectedIndex())
          String sql = "select veiculo as 'placa', datahora as 'data', descricao as 'descrição', valor from despesa_veiculo where veiculo = '" + ((veiculoSelecionado == null)?"":veiculoSelecionado.obterPlaca()) + "' order by datahora desc"
          modeloTabelaDespesas.definirConsulta(sql)
          tblDespesas.setModel(modeloTabelaDespesas)
          tblDespesas.updateUI()
      }
    }

    if(objeto == btAdicionarMulta) {
      DlgDadosMulta dlgDadosMulta = new DlgDadosMulta(aplicacao)
        dlgDadosMulta.setVisible(true)
    }

    if(objeto == btAlterarMulta) {
      if(((Veiculo)veiculos.get(this.cbxVeiculoMultas.getSelectedIndex()))!=null) {
        if(this.tblMultas.getSelectedRow() >= 0) {
          int linha = this.tblMultas.getSelectedRow()
            int codigoMulta = Integer.parseInt((String)this.tblMultas.getValueAt(linha,0))
            DlgDadosMulta dlgDadosMulta = new DlgDadosMulta(aplicacao,new Multa(codigoMulta))
            dlgDadosMulta.setVisible(true)
        }
        else
        {
          JOptionPane.showMessageDialog(aplicacao,"Atenção: Selecione uma linha da visão para alterar os dados da multa.","Atenção",JOptionPane.WARNING_MESSAGE)
        }
      }
      else
      {
        JOptionPane.showMessageDialog(aplicacao,"Atenção: Selecione uma linha da visão para alterar os dados da multa.","Atenção",JOptionPane.WARNING_MESSAGE)
      }
    }

    if(objeto == btExcluirMulta) {
      if(((Veiculo)veiculos.get(this.cbxVeiculoMultas.getSelectedIndex()))!=null) {
        if(tblMultas.getSelectedRow() >=0) {
          if(JOptionPane.showConfirmDialog(aplicacao,"Atenção: Tem certeza que deseja excluir a multa selecionada?","Atenção",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE) == 0) {
            int linha = tblMultas.getSelectedRow()
              int codigoMulta = Integer.parseInt((String)tblMultas.getValueAt(linha,0))
              Multa multa = new Multa(codigoMulta)
              try
              {
                multa.excluirMulta()
              }
            catch(Exception e) {
              e.printStackTrace()
            }
          }
        }
        else
        {
          JOptionPane.showMessageDialog(aplicacao,"Atenção: Selecione uma linha da visão para excluir uma multa.","Atenção",JOptionPane.WARNING_MESSAGE)
        }
      }
      else
      {
        JOptionPane.showMessageDialog(aplicacao,"Atenção: Selecione uma linha da visão para excluir uma multa.","Atenção",JOptionPane.WARNING_MESSAGE)
      }
    }

    if(objeto == btAtualizarMulta) {
      if(((Veiculo)veiculos.get(this.cbxVeiculoMultas.getSelectedIndex()))!=null) {
        Veiculo veiculoSelecionado = (Veiculo)veiculos.get(cbxVeiculoMultas.getSelectedIndex())
          String sql = "select multa.codigo,multa.placa, motivo, valor, motorista.motorista from multa, motorista where multa.placa = '" + ((veiculoSelecionado == null)?"":veiculoSelecionado.obterPlaca()) + "' and multa.responsabilidade = motorista.codigo order by motorista.motorista asc"
          modeloTabelaMultas.definirConsulta(sql)
          tblMultas.setModel(modeloTabelaMultas)
          tblMultas.updateUI()
      }
    }
  }
}
