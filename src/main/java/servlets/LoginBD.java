package servlets;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class LoginBD extends HttpServlet{

protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// Nombre del usuario
	String usuario = request.getParameter("usuario");

	// Clave
	String clave = request.getParameter("clave");


	// Accedemos al entorno de sesión y si no está creado lo creamos

	HttpSession session = request.getSession(true);

	AccesoBD con = AccesoBD.getInstance();

	if ((usuario != null) && (clave != null)) {
		int codigo = con.comprobarUsuarioBD(usuario,clave);
		if (codigo>0) {
			session.setAttribute("usuario",codigo);
			try {
				con.ActualizaPartidas();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			response.sendRedirect(request.getContextPath() + "/web/Perfil.jsp");
		}
		else {
			session.setAttribute("mensaje","Usuario y/o clave incorrectos");
			response.sendRedirect("./web/InicioSesion.jsp");
		}
	}	
	return;
}
}