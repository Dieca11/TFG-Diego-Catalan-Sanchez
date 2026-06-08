package servlets;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

/**
 * Servlet encargado de gestionar el inicio de sesión de los usuarios.
 * 
 * Comprueba las credenciales introducidas en el formulario de inicio de sesión,
 * crea la sesión del usuario autenticado y actualiza las partidas pendientes
 * antes de redirigir al perfil.
 */

public class LoginBD extends HttpServlet{

	/**
     * Atiende las peticiones HTTP de tipo POST realizadas desde el formulario
     * de inicio de sesión.
     * 
     * Delega la lógica de autenticación en el método iniciarSesion, que obtiene
     * las credenciales introducidas, comprueba si son válidas y realiza la
     * redirección correspondiente.
     * 
     * @param request objeto que contiene la petición HTTP enviada por el formulario.
     * @param response objeto utilizado para generar la respuesta HTTP.
     * @throws ServletException si ocurre un error propio del servlet.
     * @throws IOException si ocurre un error de entrada o salida durante la petición.
     */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		iniciarSesion(request, response);
		return;
	}

	/**
     * Gestiona el proceso de inicio de sesión del usuario.
     * 
     * Obtiene el nombre de usuario y la contraseña introducidos en el formulario,
     * comprueba las credenciales en la base de datos y, si son correctas, guarda
     * el identificador del usuario en sesión.
     * 
     * Tras iniciar sesión correctamente, actualiza las partidas pendientes que
     * hayan finalizado y redirige al usuario a su perfil. Si las credenciales no
     * son válidas, guarda un mensaje de error y redirige de nuevo a la página de
     * inicio de sesión.
     * 
     * @param request objeto que contiene los datos enviados desde el formulario.
     * @param response objeto utilizado para redirigir al usuario tras la comprobación.
     * @throws IOException si ocurre un error durante la redirección.
    */

    private void iniciarSesion(HttpServletRequest request, HttpServletResponse response)
        throws IOException {

        String usuario = request.getParameter("usuario");
        String clave = request.getParameter("clave");

        HttpSession session = request.getSession(true);
        AccesoBD con = AccesoBD.getInstance();

        if ((usuario != null) && (clave != null)) {
            int codigo = con.comprobarUsuarioBD(usuario, clave);

            if (codigo > 0) {
                session.setAttribute("usuario", codigo);
                actualizarPartidasFinalizadas(con);
                response.sendRedirect(request.getContextPath() + "/web/Perfil.jsp");
            } else {
                session.setAttribute("mensaje", "Usuario y/o clave incorrectos");
                response.sendRedirect("./web/InicioSesion.jsp");
            }
        }
    }

    /**
     * Actualiza las partidas pendientes que ya han finalizado.
     * 
     * Llama a la base de datos para transformar en partidas jugadas aquellas
     * reservas cuya fecha ya haya pasado. Si se produce algún error durante
     * la actualización, se muestra la traza para facilitar la depuración.
     * 
     * @param con instancia de acceso a la base de datos.
    */

    private void actualizarPartidasFinalizadas(AccesoBD con) {
        try {
            con.ActualizaPartidas();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}