package br.com.mentores.site;

import javax.servlet.http.*;
import java.io.*;
import br.com.mentores.util.*;
import java.util.*;
import br.com.mentores.gestao.bibliografia.*;
import javax.servlet.*;

public class Controller extends HttpServlet
{
    public static String SMTP_SERVER;
    public static String DRIVER;
    public static String FONTE_DADOS;
    public static String USUARIO;
    public static String SENHA;
    public static final int FEEDBACK = 1;
    public static final int PRINT = 2;
    public static final int SEND = 3;
    public static final int CADASTRO = 4;
    public static final int VISAO = 5;
    public static final int LEITURA = 6;
    public static final int ALTERACAO = 7;
    public static final int EXCLUSAO = 8;
    public static final int USUARIO_SITE = 1;
    
    public void doPost(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException {
        this.doGet(req, res);
    }
    
    public void doGet(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException {
        Controller.DRIVER = ((GenericServlet)this).getInitParameter("driver");
        Controller.FONTE_DADOS = ((GenericServlet)this).getInitParameter("fonte_dados");
        Controller.USUARIO = ((GenericServlet)this).getInitParameter("usuario");
        Controller.SENHA = ((GenericServlet)this).getInitParameter("senha");
        Controller.SMTP_SERVER = ((GenericServlet)this).getInitParameter("smtp");
        final int page = Integer.parseInt((((ServletRequest)req).getParameter("pg") == null) ? "0" : ((ServletRequest)req).getParameter("pg"));
        final int document = Integer.parseInt((((ServletRequest)req).getParameter("doc") == null) ? "0" : ((ServletRequest)req).getParameter("doc"));
        final int data = Integer.parseInt((((ServletRequest)req).getParameter("dt") == null) ? "0" : ((ServletRequest)req).getParameter("dt"));
        final int action = Integer.parseInt((((ServletRequest)req).getParameter("act") == null) ? "0" : ((ServletRequest)req).getParameter("act"));
        if (page == 0) {
            final RequestDispatcher rd = ((ServletRequest)req).getRequestDispatcher("../main.jsp");
            rd.forward((ServletRequest)req, (ServletResponse)res);
        }
        else if (page > 0) {
            if (document == 0) {
                if (data > 0) {
                    switch (data) {
                        case 1: {
                            final UsuarioAction usuario = new UsuarioAction(page, data, action);
                            usuario.doAction(req, res);
                            break;
                        }
                    }
                }
                else {
                    final RequestDispatcher rd = ((ServletRequest)req).getRequestDispatcher("../section.jsp");
                    rd.forward((ServletRequest)req, (ServletResponse)res);
                }
            }
            else if (document > 0) {
                try {
                    if (action == 1) {
                        this.addFeedback(req, res, document);
                    }
                    if (action == 3) {
                        this.sendDocument(req, res, document, page);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace(((ServletResponse)res).getWriter());
                    return;
                }
                RequestDispatcher rd;
                if (action == 2) {
                    rd = ((ServletRequest)req).getRequestDispatcher("../paper_print.jsp");
                }
                else if (action == 3) {
                    rd = ((ServletRequest)req).getRequestDispatcher("../paper_send.jsp");
                }
                else {
                    rd = ((ServletRequest)req).getRequestDispatcher("../paper.jsp");
                }
                rd.forward((ServletRequest)req, (ServletResponse)res);
            }
        }
    }
    
    private void addFeedback(final HttpServletRequest req, final HttpServletResponse res, final int documento) throws Exception {
        final ConexaoDB conexao = new Conexao(Controller.DRIVER, Controller.FONTE_DADOS, Controller.USUARIO, Controller.SENHA);
        final Comentario comentario = new Comentario();
        comentario.setNome(((ServletRequest)req).getParameter("nome"));
        comentario.setEmail(((ServletRequest)req).getParameter("email"));
        if (((ServletRequest)req).getParameter("nota") != null && !((ServletRequest)req).getParameter("nota").equals("")) {
            comentario.setNota(Integer.parseInt(((ServletRequest)req).getParameter("nota")));
        }
        comentario.setComentario(((ServletRequest)req).getParameter("comentarios"));
        comentario.setDataEnvio(new Calendario());
        comentario.setArtigo(new Artigo(documento));
        conexao.abrirConexao();
        comentario.addComentario(conexao);
        final Vector autores = comentario.getArtigo().getAutores(conexao);
        final String[][] to = new String[autores.size()][2];
        for (int i = 0; i < autores.size(); ++i) {
            to[i][0] = autores.get(i).getEmail();
            to[i][1] = autores.get(i).getNome();
        }
        final SendMail email = new SendMail(Controller.SMTP_SERVER, to, "Mentores Website", comentario.getEmail(), "[Mentores] Novo coment\u00e1rio feito ao artigo No. " + documento, String.valueOf(comentario.getNome()) + " atribuiu a nota " + comentario.getNota() + " e escreveu em " + comentario.getDataEnvio().get("dd/MM/yyyy as hh:mm") + " a seguinte mensagem:\n\n" + comentario.getComentario());
        email.send();
        ((ServletRequest)req).setAttribute("feedback", (Object)"Obrigado pela sua participa\u00e7\u00e3o.<br>Seu coment\u00e1rio \u00e9 muito importante para melhorarmos a qualidade do nosso conte\u00fado.");
        conexao.fecharConexao();
    }
    
    private void sendDocument(final HttpServletRequest req, final HttpServletResponse res, final int document, final int page) throws Exception {
        final ConexaoDB conexao = new Conexao(Controller.DRIVER, Controller.FONTE_DADOS, Controller.USUARIO, Controller.SENHA);
        final EnvioArtigo envioArtigo = new EnvioArtigo();
        String erros = "";
        boolean virgula = false;
        if (((ServletRequest)req).getParameter("remetente").trim().equals("")) {
            erros = String.valueOf(erros) + "rementente";
            virgula = true;
        }
        if (((ServletRequest)req).getParameter("email_remetente").trim().equals("")) {
            if (virgula) {
                erros = String.valueOf(erros) + ", ";
            }
            erros = String.valueOf(erros) + "e-mail do remetente";
            virgula = true;
        }
        if (((ServletRequest)req).getParameter("destinatario").trim().equals("")) {
            if (virgula) {
                erros = String.valueOf(erros) + ", ";
            }
            erros = String.valueOf(erros) + "destinat\u00e1rio";
            virgula = true;
        }
        if (((ServletRequest)req).getParameter("email_destinatario").trim().equals("")) {
            if (virgula) {
                erros = String.valueOf(erros) + ", ";
            }
            erros = String.valueOf(erros) + "e-mail do destinat\u00e1rio";
            virgula = true;
        }
        if (erros.equals("")) {
            envioArtigo.setArtigo(new Artigo(document));
            envioArtigo.setDestinatario(((ServletRequest)req).getParameter("destinatario"));
            envioArtigo.setEmailDestinatario(((ServletRequest)req).getParameter("email_destinatario"));
            envioArtigo.setRemetente(((ServletRequest)req).getParameter("remetente"));
            envioArtigo.setEmailRemetente(((ServletRequest)req).getParameter("email_remetente"));
            envioArtigo.setComentario(((ServletRequest)req).getParameter("comentario"));
            conexao.abrirConexao();
            envioArtigo.getArtigo().getArtigo(conexao);
            envioArtigo.addEnvioArtigo(conexao);
            conexao.fecharConexao();
            final SendMail email = new SendMail(Controller.SMTP_SERVER, envioArtigo.getDestinatario(), envioArtigo.getEmailDestinatario(), envioArtigo.getRemetente(), envioArtigo.getEmailRemetente(), "Recomendo um artigo da Mentores para voc\u00ea", "Recomendo que voc\u00ea leia o artigo \"" + envioArtigo.getArtigo().getTitulo() + " - " + envioArtigo.getArtigo().getComentario() + "\".\n\n" + envioArtigo.getComentario() + "\n\nPara acessar o artigo use o link \n http://www.mentores.com.br/servlet/controller?pg=" + page + "&doc=" + envioArtigo.getArtigo().getCodigoArtigo() + "\n\n Atenciosamente, \n\n" + envioArtigo.getRemetente());
            email.send();
            ((ServletRequest)req).setAttribute("confirm", (Object)"A mensagem foi enviada com sucesso ao destinat\u00e1rio. <br> <b>Obrigado por utilizar este servi\u00e7o.</b>");
        }
        else {
            if (erros.indexOf(",") >= 0) {
                erros = "Os campos " + erros + " s\u00e3o obrigat\u00f3rios.";
            }
            else {
                erros = "O campo " + erros + " \u00e9 obrigat\u00f3rio.";
            }
            ((ServletRequest)req).setAttribute("error", (Object)erros);
        }
    }
}
