package servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet encargado de gestionar el cierre de sesión del usuario.
 * 
 * Invalida la sesión actual si existe y redirige al usuario a la página
 * de inicio de sesión.
*/

public class LogOut extends HttpServlet {

    /**
     * Atiende las peticiones HTTP de tipo GET dirigidas al servlet de cierre de sesión.
     * 
     * Delega la lógica de cierre de sesión en el método cerrarSesion, que invalida
     * la sesión actual y redirige al usuario a la página de inicio de sesión.
     * 
     * @param request objeto que contiene la petición HTTP realizada por el cliente.
     * @param response objeto utilizado para generar la respuesta HTTP.
     * @throws ServletException si ocurre un error propio del servlet.
     * @throws IOException si ocurre un error de entrada o salida durante la petición.
    */

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        cerrarSesion(request, response);
        return;
    }

    /**
     * Cierra la sesión del usuario actual.
     * 
     * Obtiene la sesión existente sin crear una nueva. Si la sesión existe,
     * la invalida para eliminar los datos asociados al usuario autenticado.
     * Finalmente, redirige al usuario a la página de inicio de sesión.
     * 
     * @param request objeto utilizado para acceder a la sesión actual.
     * @param response objeto utilizado para redirigir al usuario tras cerrar sesión.
     * @throws IOException si ocurre un error durante la redirección.
    */

    private void cerrarSesion(HttpServletRequest request, HttpServletResponse response)
        throws IOException {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        response.sendRedirect("./web/InicioSesion.jsp");
    }
}
