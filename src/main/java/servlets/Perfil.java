package servlets;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;

/** 
 * Servlet encargado de gestionar las operaciones relacionadas con el perfil * 
 * del usuario autenticado.
 * 
 * Permite cargar la información del perfil, mostrar las reservas pendientes,
 * consultar el historial de partidas, cancelar reservas, anotar resultados
 * y actualizar los datos personales del usuario.
*/
@MultipartConfig
public class Perfil extends HttpServlet {

    /** 
    * Atiende las peticiones HTTP de tipo GET dirigidas al servlet de perfil.
    * 
    * Delega la carga de los datos del perfil en el método cargarPerfil,
    * que obtiene la información del usuario, sus reservas pendientes y 
    * su historial de partidas.
    * 
    * @param request objeto que contiene la petición HTTP realizada por el cliente.
    * @param response objeto utilizado para generar la respuesta HTTP.
    * @throws IOException si ocurre un error de entrada o salida durante la petición.
    * @throws ServletException si ocurre un error propio del servlet. 
    */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {

            cargarPerfil(request, response);
    }

    /**
     * Atiende las peticiones HTTP de tipo POST realizadas desde la página de perfil.
    * Comprueba que exista una sesión válida y, según el valor del parámetro * accion, 
    * ejecuta la operación correspondiente: cancelar una partida,
    * anotar un resultado o actualizar los datos del perfil.
    * 
    * @param request objeto que contiene la petición HTTP enviada por el formulario. 
    * @param response objeto utilizado para generar la respuesta HTTP. 
    * @throws IOException si ocurre un error de entrada o salida durante la petición.
    * @throws ServletException si ocurre un error propio del servlet. 
    */


  @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
            HttpSession session = obtenerSesionValida(request, response);
            
            if (session == null) {
                response.sendRedirect(request.getContextPath() + "/web/InicioSesion.jsp");
                return;
            }
            String accion = request.getParameter("accion");
            if ("cancelarPartida".equals(accion)) {
                cancelarPartida(request, response, session);
                return;
            }

            if ("anotarResultado".equals(accion)) {
                anotarResultado(request, response, session);
                return;
            }

            if("actualizarPerfil".equals(accion)){
                actualizarPerfil(request, response, session);
                return;
            }
 
            response.sendRedirect(request.getContextPath() + "/web/Perfil.jsp");
        }


    /** 
     * Carga la información necesaria para mostrar la página de perfil del usuario.
     * 
     * Comprueba que exista una sesión válida, obtiene el identificador del usuario 
     * autenticado y recupera desde la base de datos sus datos personales, sus 
     * reservas pendientes y su historial de partidas.
     * 
     * La información obtenida se guarda en la sesión para ser utilizada
     * posteriormente en la página Perfil.jsp.
     * 
     * @param request objeto que contiene la petición HTTP del usuario.
     * @param response objeto utilizado para redirigir al usuario tras cargar los datos.
     * @throws IOException si ocurre un error de entrada o salida durante la redirección.
     * @throws ServletException si ocurre un error propio del servlet. 
    */

    private void cargarPerfil(HttpServletRequest request, HttpServletResponse response) 
        throws IOException, ServletException {

        HttpSession session = obtenerSesionValida(request, response);
        if (session == null) {
            return;
        }

        Integer idUsuario = (Integer) session.getAttribute("usuario");
        AccesoBD con = AccesoBD.getInstance();

        try {
            UsuarioBD usuario = con.obtenerUsuarioPorCodigo(idUsuario);

            session.setAttribute("usuarioPerfil", usuario);
            session.setAttribute("pendientesPerfil", con.obtenerPendientesPerfil(idUsuario));
            session.setAttribute("historialPerfil", con.obtenerHistorialPerfil(idUsuario));

            response.sendRedirect(request.getContextPath() + "/web/Perfil.jsp");

        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("mensajePerfil", "No se pudieron cargar las partidas del perfil.");
            response.sendRedirect(request.getContextPath() + "/web/Perfil.jsp");
        }
    }

    /** 
     * Comprueba si existe una sesión válida para el usuario actual.
     * 
     * Obtiene la sesión sin crear una nueva y verifica que exista el atributo
     * usuario, que representa el identificador del usuario autenticado.
     * 
     * Si la sesión no existe o el identificador del usuario no es válido,
     * redirige al usuario a la página de inicio de sesión. 
     * 
     * @param request objeto utilizado para acceder a la sesión actual.
     * @param response objeto utilizado para redirigir al usuario si no tiene sesión válida.
     * @return la sesión actual si es válida, o null si el usuario no está autenticado.
     * @throws IOException si ocurre un error durante la redirección. 
     */

    private HttpSession obtenerSesionValida(HttpServletRequest request, HttpServletResponse response)
        throws IOException {

        HttpSession session = request.getSession(false);
        Integer idUsuario = (session != null) ? (Integer) session.getAttribute("usuario") : null;

        if (idUsuario == null || idUsuario <= 0) {
            response.sendRedirect(request.getContextPath() + "/web/InicioSesion.jsp");
            return null;
        }

        return session;
    }

    /** 
     * Cancela una reserva pendiente desde la página de perfil. 
     * 
     * Obtiene el identificador de la reserva recibido en la petición, lo convierte
     * a entero y llama a la capa de acceso a datos para marcar la reserva como * cancelada. 
     * 
     * Tras realizar la operación, guarda un mensaje informativo en la sesión  
     * y redirige al servlet de perfil para recargar los datos actualizados. 
     * 
     * @param request objeto que contiene los datos enviados desde el formulario.
     * @param response objeto utilizado para redirigir al usuario tras la operación.
     * @param session sesión actual del usuario autenticado.
     * @throws IOException si ocurre un error durante la redirección. 
    */

    private void cancelarPartida(HttpServletRequest request, HttpServletResponse response, HttpSession session)
        throws IOException {

        String idReservaStr = request.getParameter("idReserva");

        try {
            int idReserva = Integer.parseInt(idReservaStr);

            AccesoBD con = AccesoBD.getInstance();
            con.cancelarReserva(idReserva);

            session.setAttribute("mensajePerfil", "Reserva cancelada correctamente.");
            response.sendRedirect(request.getContextPath() + "/Perfil");

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("mensajePerfil", "No se pudo cancelar la reserva.");
            response.sendRedirect(request.getContextPath() + "/Perfil");
        }
    }

    /** 
     * Registra el resultado de una partida asociada a una reserva.
     * 
     * Obtiene el identificador de la reserva, el resultado introducido y los 
     * jugadores seleccionados como ganadores. Antes de guardar el resultado, 
     * comprueba que se haya recibido la reserva, que se hayan seleccionado 
     * exactamente dos ganadores y que el resultado tenga un formato válido 
     * para una partida de pádel. 
     * 
     * Si las validaciones son correctas, guarda el resultado y los ganadores
     * en la base de datos. 
     * 
     * @param request objeto que contiene los datos enviados desde el formulario de resultado.
     * @param response objeto utilizado para redirigir al usuario tras la operación. 
     * @param session sesión actual del usuario autenticado.
     * @throws IOException si ocurre un error durante la redirección.
     * @throws ServletException si no se recibe el identificador de la reserva. 
    */

    private void anotarResultado(HttpServletRequest request, HttpServletResponse response, HttpSession session)
        throws IOException, ServletException {

        String idReservaStr = request.getParameter("idReserva");
        String resultado = request.getParameter("resultado");
        String[] ganadores = request.getParameterValues("ganadores");

        if (idReservaStr == null || idReservaStr.isBlank()) {
            throw new ServletException("No se recibió idReserva.");
        }

        if (ganadores == null || ganadores.length != 2) {
            session.setAttribute("mensajePerfil", "Debes seleccionar exactamente 2 ganadores.");
            response.sendRedirect(request.getContextPath() + "/Perfil");
            return;
        }

        AccesoBD con = AccesoBD.getInstance();

        if (!con.esResultadoPadelValido(resultado)) {
            session.setAttribute("mensajePerfil", "El resultado introducido no es válido.");
            response.sendRedirect(request.getContextPath() + "/Perfil");
            return;
        }

        try {
            int idReserva = Integer.parseInt(idReservaStr);
            int ganador1 = Integer.parseInt(ganadores[0]);
            int ganador2 = Integer.parseInt(ganadores[1]);

            con.guardarResultadoPartida(idReserva, resultado, ganador1, ganador2);

            session.setAttribute("mensajePerfil", "Resultado guardado correctamente.");
            response.sendRedirect(request.getContextPath() + "/Perfil");

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("mensajePerfil", "No se pudo guardar el resultado.");
            response.sendRedirect(request.getContextPath() + "/Perfil");
        }
    }

    /** 
     * Actualiza los datos personales del usuario autenticado. *
     * 
     * Obtiene los datos enviados desde el formulario de edición del perfil,
     * incluyendo la imagen, el nombre de usuario, el correo electrónico, las 
     * preferencias de privacidad, la tarjeta y la nueva contraseña en caso 
     * de que el usuario quiera modificarla. * 
     * 
     * Si el usuario introduce una nueva contraseña, comprueba que ambos campos 
     * coincidan y genera su hash antes de guardarla. También verifica que el 
     * nuevo nombre de usuario o correo electrónico no estén siendo utilizados 
     * por otro usuario.
     * 
     * Si todas las validaciones son correctas, actualiza la información en la 
     * base de datos y guarda los datos actualizados en la sesión. 
     *  
     * @param request objeto que contiene los datos enviados desde el formulario de edición.
     * @param response objeto utilizado para redirigir al usuario tras la actualización.
     * @param session sesión actual del usuario autenticado.
     * @throws IOException si ocurre un error durante la redirección. 
    */

    private void actualizarPerfil(HttpServletRequest request, HttpServletResponse response, HttpSession session)
        throws IOException {
        try {
            Integer idUsuario = (Integer) session.getAttribute("usuario");

            Part ficheroImagen = request.getPart("imagen_usuario");
            String imagen = null;
            if (ficheroImagen != null && ficheroImagen.getSize() > 0) {
                String nombreArchivo = Paths.get(ficheroImagen.getSubmittedFileName()).getFileName().toString();

                String rutaCarpeta = getServletContext().getRealPath("/web/Imagenes/Perfiles/");
                File carpeta = new File(rutaCarpeta);
                if (!carpeta.exists()) {
                    carpeta.mkdirs();
                }

                ficheroImagen.write(rutaCarpeta + File.separator + nombreArchivo);
                imagen = "/web/Imagenes/Perfiles/" + nombreArchivo;

                if (imagen == null || imagen.isBlank()) {
                    UsuarioBD usuarioActual = (UsuarioBD) session.getAttribute("usuario");
                    if (usuarioActual != null) {
                        imagen = usuarioActual.getFoto_perfil();
                    }
                }
            }

            String nombreUsuario = request.getParameter("nombre_usuario");
            String email = request.getParameter("email");
            boolean recibirInvitaciones = request.getParameter("recibir_invitaciones") != null;
            boolean mostrarPartidas = request.getParameter("mostrar_partidas") != null;
            String clave1 = request.getParameter("clave1");
            String clave2 = request.getParameter("clave2");
            String tarjeta = request.getParameter("tarjeta");

            boolean quiereCambiarClave =
            (clave1 != null && !clave1.isBlank()) ||
            (clave2 != null && !clave2.isBlank());

            String claveHash = null;

            if (quiereCambiarClave) {
                if (clave1 == null || clave2 == null ||
                    clave1.isBlank() || clave2.isBlank() ||
                    !clave1.equals(clave2)) {

                    session.setAttribute("mensajePerfil", "Las contraseñas no coinciden o están vacías.");
                    response.sendRedirect(request.getContextPath() + "/web/Perfil.jsp");
                    return;
                }

                claveHash = Seguridad.hashearPassword(clave1);
            }

            AccesoBD con = AccesoBD.getInstance();

            if (con.existeUsuOEmail(idUsuario, nombreUsuario, email)) {
                session.setAttribute("mensajePerfil", "Nombre de usuario o email ya están en uso.");
                response.sendRedirect(request.getContextPath() + "/web/Perfil.jsp");
                return;
            }

            con.ActualizarUsuario(
                    idUsuario,
                    nombreUsuario,
                    email,
                    imagen,
                    recibirInvitaciones,
                    mostrarPartidas,
                    claveHash,
                    tarjeta
            );

            UsuarioBD actualizado = con.obtenerUsuarioPorCodigo(idUsuario);
            session.setAttribute("usuarioPerfil", actualizado);
            session.setAttribute("mensajePerfil", "Perfil actualizado correctamente.");

            response.sendRedirect(request.getContextPath() + "/web/Perfil.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("mensajePerfil", "No se pudo actualizar el perfil: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/web/Perfil.jsp");
            return;
        }
    }
}
