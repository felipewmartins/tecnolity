package br.com.mentores.site;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;

public class UsuarioAction
{
    private int pagina;
    private int dado;
    private int acao;
    
    public UsuarioAction(final int pagina, final int dado, final int acao) {
        this.pagina = pagina;
        this.dado = dado;
        this.acao = acao;
    }
    
    public void doAction(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException {
        switch (this.acao) {
            case 4: {
                final RequestDispatcher rd = ((ServletRequest)req).getRequestDispatcher("../usuario/usuario.jsp");
                rd.forward((ServletRequest)req, (ServletResponse)res);
                break;
            }
            case 5: {
                break;
            }
            case 6: {
                break;
            }
            case 7: {
                break;
            }
            case 8: {
                break;
            }
            default: {
                final RequestDispatcher rd = ((ServletRequest)req).getRequestDispatcher("controller?pg=" + this.pagina);
                rd.forward((ServletRequest)req, (ServletResponse)res);
                break;
            }
        }
    }
}
