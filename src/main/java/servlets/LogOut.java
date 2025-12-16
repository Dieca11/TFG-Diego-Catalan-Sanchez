package servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LogOut extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener la sesión actual o crear una nueva si no existe
        HttpSession session = request.getSession(false);

        // Si existe una sesión, la invalidamos
        if (session != null) {
            session.invalidate(); // Invalida la sesión
        }

        // Redirigir a una página de confirmación de cierre de sesión
        response.sendRedirect("./web/InicioSesion.jsp");

        return;
    }
}
