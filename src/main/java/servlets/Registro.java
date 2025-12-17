package servlets;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class Registro extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
        HttpSession session = request.getSession();

        // Obtener datos del formulario
        String nombreUsuario = request.getParameter("nombre_usuario");
        String email = request.getParameter("email");
        String clave1 = request.getParameter("clave1");
        String clave2 = request.getParameter("clave2");

        if (nombreUsuario == null || nombreUsuario.isEmpty() ||
                email == null || email.isEmpty() ||
                clave1 == null || clave1.isEmpty()) {

                session.setAttribute("mensajeRegistro", "Todos los campos son obligatorios");
                response.sendRedirect(request.getContextPath() + "/web/Registro.jsp");
                return;
        }

        AccesoBD con = AccesoBD.getInstance();
        if (con.existeUsuOEmail(0, nombreUsuario, email)) { // 0 porque es nuevo registro
            session.setAttribute("mensajeRegistro", "Usuario o email ya existen");
            response.sendRedirect(request.getContextPath() + "/web/Registro.jsp");
            return;
        }

        if ((clave1 != null && !clave1.isEmpty()) ||
        (clave2 != null && !clave2.isEmpty())) {

            if (clave1 == null || clave2 == null ||
                clave1.isEmpty() || clave2.isEmpty() ||
                !clave1.equals(clave2)) {

                session.setAttribute("mensajeRegistro",
                        "Las contraseñas no coinciden o están vacías");
                    response.sendRedirect(request.getContextPath() + "/web/Registro.jsp");
                    return;
            }
        }

        con.RegistarUsuario(nombreUsuario, email, clave1);
        response.sendRedirect(request.getContextPath() + "/web/InicioSesion.jsp");

    }
}
