package servlets;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class Perfil extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    HttpSession s = request.getSession(false);

    Integer codigo = (s != null) ? (Integer) s.getAttribute("usuario") : null;

        AccesoBD con = AccesoBD.getInstance();

    if (codigo == null || codigo <= 0) {
      response.sendRedirect(request.getContextPath() + "./web/InicioSesion.jsp");
    }


    UsuarioBD u = con.obtenerUsuarioPorCodigo(codigo);
    s.setAttribute("usuarioPerfil", u);
    response.sendRedirect("./web/Perfil.jsp");

    return;
  }
    
}
