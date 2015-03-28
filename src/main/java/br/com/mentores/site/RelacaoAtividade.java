package br.com.mentores.site;

import br.com.mentores.util.*;
import java.sql.*;

public class RelacaoAtividade
{
    public static final int PROXIMO = 1;
    public static final int ANTERIOR = 2;
    public static final int CANCELAMENTO = 3;
    public static final int FINALIZACAO = 4;
    public static final int EXCESSAO = 5;
    private Atividade atividadeOrigem;
    private Atividade atividadeDestino;
    private int tipoRelacao;
    
    public RelacaoAtividade(final Atividade atividadeOrigem, final Atividade atividadeDestino, final int tipoRelacao) {
        this.setAtividadeOrigem(atividadeOrigem);
        this.setAtividadeDestino(atividadeDestino);
        this.setTipoRelacao(tipoRelacao);
    }
    
    public RelacaoAtividade getRelacaoAtividade(final ConexaoDB conexao) throws Exception {
        final String sql = "SELECT ATIVIDADE_ORIGEM,ATIVIDADE_DESTINO,TIPO_RELACAO FROM RELACAO_ATIVIDADE WHERE ATIVIDADE_ORIGEM = " + this.getAtividadeOrigem().getCodigo() + " AND ATIVIDADE_DESTINO = " + this.getAtividadeDestino().getCodigo();
        final ResultSet rsRelacaoAtividade = conexao.executarConsulta(sql);
        if (rsRelacaoAtividade.next()) {
            this.setAtividadeOrigem(new Atividade(rsRelacaoAtividade.getInt("ATIVIDADE_ORIGEM")));
            this.setAtividadeDestino(new Atividade(rsRelacaoAtividade.getInt("ATIVIDADE_DESTINO")));
            this.setTipoRelacao(rsRelacaoAtividade.getInt("TIPO_RELACAO"));
        }
        rsRelacaoAtividade.close();
        return this;
    }
    
    public Atividade getAtividadeDestino() {
        return this.atividadeDestino;
    }
    
    public Atividade getAtividadeOrigem() {
        return this.atividadeOrigem;
    }
    
    public int getTipoRelacao() {
        return this.tipoRelacao;
    }
    
    public void setAtividadeDestino(final Atividade atividadeDestino) {
        this.atividadeDestino = atividadeDestino;
    }
    
    public void setAtividadeOrigem(final Atividade atividadeOrigem) {
        this.atividadeOrigem = atividadeOrigem;
    }
    
    public void setTipoRelacao(final int tipoRelacao) {
        this.tipoRelacao = tipoRelacao;
    }
}
