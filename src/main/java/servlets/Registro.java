package servlets;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet encargado de gestionar el registro de nuevos usuarios.
 * 
 * Valida los datos introducidos en el formulario de registro, comprueba que
 * el usuario o correo electrónico no existan previamente, cifra la contraseña
 * y registra el nuevo usuario en la base de datos.
*/

public class Registro extends HttpServlet {

    /**
     * Atiende las peticiones HTTP de tipo GET realizadas desde el formulario
     * de registro.
     * 
     * Delega la lógica de registro en el método registrarUsuario, que obtiene
     * los datos enviados, realiza las validaciones necesarias y registra al
     * nuevo usuario si la información es correcta.
     * 
     * @param request objeto que contiene la petición HTTP enviada por el formulario.
     * @param response objeto utilizado para generar la respuesta HTTP.
     * @throws IOException si ocurre un error de entrada o salida durante la petición.
     * @throws ServletException si ocurre un error propio del servlet.
    */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
        
        registrarUsuario(request, response);
        return;
    }

     /**
     * Gestiona el proceso completo de registro de un nuevo usuario.
     * 
     * Obtiene los datos del formulario, comprueba que los campos obligatorios
     * estén informados, valida la aceptación de la política de privacidad,
     * verifica que el nombre de usuario o correo no estén ya registrados y
     * comprueba que las contraseñas coincidan.
     * 
     * Si todas las validaciones son correctas, cifra la contraseña y registra
     * el usuario en la base de datos.
     * 
     * @param request objeto que contiene los datos enviados desde el formulario.
     * @param response objeto utilizado para redirigir al usuario tras el registro.
     * @throws IOException si ocurre un error durante la redirección.
     * @throws ServletException si ocurre un error durante el reenvío de la petición.
    */

    private void registrarUsuario(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {

        HttpSession session = request.getSession();

        String nombreUsuario = request.getParameter("nombre_usuario");
        String email = request.getParameter("email");
        String clave1 = request.getParameter("clave1");
        String clave2 = request.getParameter("clave2");
        String aceptaPolitica = request.getParameter("aceptaPolitica");

        if (nombreUsuario == null || nombreUsuario.isEmpty() ||
            email == null || email.isEmpty() ||
            clave1 == null || clave1.isEmpty()) {

            session.setAttribute("mensajeRegistro", "Todos los campos son obligatorios");
            response.sendRedirect(request.getContextPath() + "/web/Registro.jsp");
            return;
        }

        if (aceptaPolitica == null) {

            request.setAttribute(
                "mensajeRegistro",
                "Debes aceptar la política de privacidad y protección de datos."
            );

            request.getRequestDispatcher("/web/Registro.jsp")
                .forward(request, response);

            return;
        }

        AccesoBD con = AccesoBD.getInstance();

        if (con.existeUsuOEmail(0, nombreUsuario, email)) {
            session.setAttribute("mensajeRegistro", "Usuario o email ya existen");
            response.sendRedirect(request.getContextPath() + "/web/Registro.jsp");
            return;
        }

        if ((clave1 != null && !clave1.isEmpty()) ||
            (clave2 != null && !clave2.isEmpty())) {

            if (clave1 == null || clave2 == null ||
                clave1.isEmpty() || clave2.isEmpty() ||
                !clave1.equals(clave2)) {

                session.setAttribute("mensajeRegistro", "Las contraseñas no coinciden o están vacías");

                response.sendRedirect(request.getContextPath() + "/web/Registro.jsp");
                return;
            }
        }

        String claveHash = Seguridad.hashearPassword(clave1);

        con.RegistarUsuario(nombreUsuario, email, claveHash);
        session.setAttribute("mensaje", "Usuario registrado correctamente. Ya puedes iniciar sesión.");
        response.sendRedirect(request.getContextPath() + "/web/InicioSesion.jsp");
    }
}
