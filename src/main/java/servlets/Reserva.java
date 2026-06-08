package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet encargado de gestionar las reservas de pistas.
 * 
 * Permite acceder a la página de reservas de un municipio, cargar los usuarios
 * invitables, mantener la selección de pista, fecha y hora, gestionar
 * invitaciones y crear o unirse a una reserva.
*/

public class Reserva extends HttpServlet {

    /**
     * Atiende las peticiones HTTP de tipo GET dirigidas al servlet de reservas.
     * 
     * Delega la carga de la página de reservas en el método cargarReserva,
     * que valida la sesión, obtiene el municipio seleccionado, carga los datos
     * necesarios y redirige a la vista correspondiente.
     * 
     * @param request objeto que contiene la petición HTTP realizada por el cliente.
     * @param response objeto utilizado para generar la respuesta HTTP.
     * @throws IOException si ocurre un error de entrada o salida durante la petición.
     * @throws ServletException si ocurre un error propio del servlet.
    */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        cargarReserva(request, response);
        return;    
    }

    /**
     * Atiende las peticiones HTTP de tipo POST realizadas desde la página de reservas.
     * 
     * Delega la gestión de acciones en el método procesarReserva, que se encarga
     * de añadir o eliminar invitados, crear una reserva o unirse a una reserva
     * existente.
     * 
     * @param request objeto que contiene la petición HTTP enviada por el formulario.
     * @param response objeto utilizado para generar la respuesta HTTP.
     * @throws IOException si ocurre un error de entrada o salida durante la petición.
     * @throws ServletException si ocurre un error propio del servlet.
    */
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

                procesarReserva(request, response);
    }

    /**
     * Carga la información necesaria para mostrar la página de reservas.
     * 
     * Comprueba que el usuario tenga una sesión válida, recupera el municipio
     * seleccionado, carga los usuarios que pueden ser invitados y conserva
     * los valores actuales de pista, fecha y franja horaria.
     * 
     * Finalmente, obtiene la información del municipio y reenvía la petición
     * a la página ReservasMunicipio.jsp.
     * 
     * @param request objeto que contiene la petición HTTP del usuario.
     * @param response objeto utilizado para redirigir o reenviar la petición.
     * @throws IOException si ocurre un error durante una redirección.
     * @throws ServletException si ocurre un error durante el reenvío de la petición.
    */

    private void cargarReserva(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {

        HttpSession session = request.getSession(false);
        Integer codigo = (session != null) ? (Integer) session.getAttribute("usuario") : null;

        if (codigo == null || codigo <= 0) {
            HttpSession nuevaSesion = request.getSession(true);
            nuevaSesion.setAttribute("popupMsg", "Para acceder a la reserva de pistas debes iniciar sesión.");
            response.sendRedirect(request.getContextPath() + "/web/InicioSesion.jsp");
            return;
        }

        cargarMensajeEmergente(request, session);

        AccesoBD con = AccesoBD.getInstance();

        actualizarMunicipioSesion(request, session);

        Integer municipioId = obtenerMunicipioSesion(session);

        if (municipioId == null) {
            response.sendRedirect(request.getContextPath() + "/web/Reservas.jsp");
            return;
        }

        cargarUsuariosInvitables(request, con);
        mantenerSeleccionActual(request);
        inicializarUsuariosReserva(request);

        MunicipioBD municipio = con.obtenerMunicipioBD(municipioId);
        request.setAttribute("municipio", municipio);

        request.getRequestDispatcher("/web/ReservasMunicipio.jsp").forward(request, response);
    }

    /**
     * Procesa las acciones realizadas desde la página de reservas.
     * 
     * Comprueba que exista una sesión válida y, en función de la acción recibida,
     * añade un invitado, elimina un invitado o crea una reserva. También mantiene
     * el contexto actual de pista, fecha y hora para volver a la misma selección.
     * 
     * @param request objeto que contiene los datos enviados desde el formulario.
     * @param response objeto utilizado para redirigir al usuario tras la operación.
     * @throws IOException si ocurre un error durante la redirección.
     * @throws ServletException si ocurre un error propio del servlet.
    */

    private void procesarReserva(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {

        HttpSession session = request.getSession(true);
        Integer codigo = (session != null) ? (Integer) session.getAttribute("usuario") : null;

        if (codigo == null || codigo <= 0) {
            session.setAttribute("popupMsg", "Para acceder a la reserva de pistas debes iniciar sesión.");
            response.sendRedirect(request.getContextPath() + "/web/InicioSesion.jsp");
            return;
        }

        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        String pistaStr = request.getParameter("numero_pista");
        String fechaStr = request.getParameter("fecha");
        String franjaStr = request.getParameter("franja");

        actualizarMunicipioPost(request, session);

        @SuppressWarnings("unchecked")
        ArrayList<Integer> invitados =
            (ArrayList<Integer>) session.getAttribute("invitadosReserva");

        if (invitados == null) {
            invitados = new ArrayList<>();
        }

        try {
            if ("addInv".equals(action)) {
                anyadirInvitado(request, session, codigo, invitados);
                response.sendRedirect(buildReservaUrl(request, pistaStr, fechaStr, franjaStr));
                return;
            }

            if ("delInv".equals(action)) {
                eliminarInvitado(request, session, invitados);
                response.sendRedirect(buildReservaUrl(request, pistaStr, fechaStr, franjaStr));
                return;
            }

            if ("crearReserva".equals(action)) {
                crearReserva(request, response, session, codigo, pistaStr, fechaStr, franjaStr);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("popupMsg", "Error procesando la invitación.");
        }

        response.sendRedirect(buildReservaUrl(request, pistaStr, fechaStr, franjaStr));
    }

    /**
     * Carga en la petición el mensaje emergente almacenado en sesión.
     * 
     * Si existe un mensaje pendiente, lo pasa al request para mostrarlo en la
     * vista y lo elimina de la sesión para evitar que se repita.
     * 
     * @param request objeto donde se guardará el mensaje para la vista.
     * @param session sesión actual del usuario.
    */

    private void cargarMensajeEmergente(HttpServletRequest request, HttpSession session) {
        String msg = (String) session.getAttribute("popupMsg");

        if (msg != null) {
            request.setAttribute("popupMsg", msg);
            session.removeAttribute("popupMsg");
        }
    }

    /**
     * Actualiza en sesión el municipio seleccionado desde la URL.
     * 
     * Si la petición incluye el parámetro id, se guarda como municipio actual
     * para mantener una clave consistente durante el flujo de reservas.
     * 
     * @param request objeto que contiene los parámetros de la petición.
     * @param session sesión actual del usuario.
    */

    private void actualizarMunicipioSesion(HttpServletRequest request, HttpSession session) {
        String municipioParam = request.getParameter("id");

        if (municipioParam != null && !municipioParam.isEmpty()) {
            session.setAttribute("municipio_id", Integer.parseInt(municipioParam));
        }
    }

    /**
     * Actualiza el municipio almacenado en sesión desde una petición POST.
     * 
     * Si el formulario envía el identificador del municipio, se guarda en sesión
     * para conservar el contexto de la reserva actual.
     * 
     * @param request objeto que contiene los datos enviados desde el formulario.
     * @param session sesión actual del usuario.
    */

    private void actualizarMunicipioPost(HttpServletRequest request, HttpSession session) {
        String municipioStr = request.getParameter("municipio_id");

        if (municipioStr != null && !municipioStr.isEmpty()) {
            session.setAttribute("municipio_id", Integer.parseInt(municipioStr));
        }
    }

    /**
     * Obtiene el municipio actualmente almacenado en sesión.
     * 
     * Convierte el valor guardado a entero, independientemente de si fue
     * almacenado como Integer o como cadena de texto.
     * 
     * @param session sesión actual del usuario.
     * @return identificador del municipio seleccionado, o null si no existe.
    */

    private Integer obtenerMunicipioSesion(HttpSession session) {
        Object midObj = session.getAttribute("municipio_id");

        if (midObj == null) {
            return null;
        }

        if (midObj instanceof Integer) {
            return (Integer) midObj;
        }

        return Integer.parseInt(midObj.toString());
    }

    /**
     * Carga la lista de usuarios que pueden ser invitados a una reserva.
     * 
     * Si se produce un error al acceder a la base de datos, se carga una lista
     * vacía para evitar que falle la visualización de la página.
     * 
     * @param request objeto donde se guardará la lista de usuarios invitables.
     * @param con instancia de acceso a base de datos.
    */

    private void cargarUsuariosInvitables(HttpServletRequest request, AccesoBD con) {
        try {
            request.setAttribute("usuariosInvitables", con.obtenerUsuariosInvitables());
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("usuariosInvitables", new ArrayList<>());
        }
    }

    /**
     * Mantiene la selección actual de pista, fecha y franja horaria.
     * 
     * Si la petición contiene estos parámetros, se guardan como atributos
     * para que la página pueda mostrarlos seleccionados tras recargarse.
     * 
     * @param request objeto que contiene los parámetros de selección.
    */

    private void mantenerSeleccionActual(HttpServletRequest request) {
        String pistaStr = request.getParameter("numero_pista");
        String fechaStr = request.getParameter("fecha");
        String franjaStr = request.getParameter("franja");

        if (pistaStr != null && !pistaStr.isEmpty()) {
            request.setAttribute("numero_pista", Integer.parseInt(pistaStr));
        }

        if (fechaStr != null && !fechaStr.isEmpty()) {
            request.setAttribute("fecha", fechaStr);
        }

        if (franjaStr != null && !franjaStr.isEmpty()) {
            request.setAttribute("franja", franjaStr);
        }
    }

    /**
     * Inicializa los atributos de los cuatro usuarios de una reserva.
     * 
     * Actualmente los cuatro espacios se cargan como vacíos, ya que la carga
     * dinámica de participantes se realiza desde la vista o mediante la recarga
     * correspondiente.
     * 
     * @param request objeto donde se guardan los atributos de usuarios.
    */

    private void inicializarUsuariosReserva(HttpServletRequest request) {
        request.setAttribute("u1", null);
        request.setAttribute("u2", null);
        request.setAttribute("u3", null);
        request.setAttribute("u4", null);
    }

    /**
     * Añade un usuario a la lista temporal de invitados de la reserva.
     * 
     * Comprueba que el usuario recibido exista, que no sea el propio usuario
     * autenticado, que no estuviera ya invitado y que no se supere el máximo
     * de tres invitaciones.
     * 
     * @param request objeto que contiene el identificador del invitado.
     * @param session sesión actual del usuario.
     * @param codigo identificador del usuario autenticado.
     * @param invitados lista temporal de invitados almacenada en sesión.
    */

    private void anyadirInvitado(
        HttpServletRequest request,
        HttpSession session,
        Integer codigo,
        ArrayList<Integer> invitados
    ) {
        String invStr = request.getParameter("invitado_id");

        if (invStr != null && !invStr.isEmpty()) {
            int invitadoId = Integer.parseInt(invStr);

            if (invitadoId == codigo) {
                session.setAttribute("popupMsg", "No puedes invitarte a ti mismo.");
            } else if (invitados.contains(invitadoId)) {
                session.setAttribute("popupMsg", "Ese usuario ya está invitado.");
            } else if (invitados.size() >= 3) {
                session.setAttribute("popupMsg", "Máximo 3 invitaciones.");
            } else {
                invitados.add(invitadoId);
            }
        }

        session.setAttribute("invitadosReserva", invitados);
    }

    /**
     * Elimina un usuario de la lista temporal de invitados.
     * 
     * Obtiene el identificador del invitado recibido en la petición y lo elimina
     * de la lista almacenada en sesión.
     * 
     * @param request objeto que contiene el identificador del invitado.
     * @param session sesión actual del usuario.
     * @param invitados lista temporal de invitados almacenada en sesión.
    */

    private void eliminarInvitado(HttpServletRequest request, HttpSession session, ArrayList<Integer> invitados) {
        String invStr = request.getParameter("invitado_id");

        if (invStr != null && !invStr.isEmpty()) {
            int invitadoId = Integer.parseInt(invStr);
            invitados.remove(Integer.valueOf(invitadoId));
        }

        session.setAttribute("invitadosReserva", invitados);
    }

    /**
     * Crea una reserva o une al usuario a una reserva existente.
     * 
     * Valida que se haya seleccionado pista, fecha y hora, comprueba que exista
     * un municipio asociado a la sesión y evita reservar en franjas horarias
     * anteriores al momento actual.
     * 
     * Según el resultado devuelto por la base de datos, informa al usuario de si
     * la reserva ha sido creada, si se ha unido correctamente, si ya formaba parte
     * de la reserva o si la reserva está completa.
     * 
     * @param request objeto que contiene los datos enviados desde el formulario.
     * @param response objeto utilizado para redirigir al usuario.
     * @param session sesión actual del usuario.
     * @param codigo identificador del usuario autenticado.
     * @param pistaStr pista seleccionada.
     * @param fechaStr fecha seleccionada.
     * @param franjaStr franja horaria seleccionada.
     * @throws IOException si ocurre un error durante la redirección.
    */

    private void crearReserva(
        HttpServletRequest request,
        HttpServletResponse response,
        HttpSession session,
        Integer codigo,
        String pistaStr,
        String fechaStr,
        String franjaStr
    ) throws IOException {

        if (pistaStr == null || fechaStr == null || franjaStr == null ||
            pistaStr.isEmpty() || fechaStr.isEmpty() || franjaStr.isEmpty()) {

            session.setAttribute("popupMsg", "Selecciona pista, día y hora antes de reservar.");
            response.sendRedirect(buildReservaUrl(request, pistaStr, fechaStr, franjaStr));
            return;
        }

        Integer municipioId = obtenerMunicipioSesion(session);

        if (municipioId == null) {
            session.setAttribute("popupMsg", "ERROR: municipio no identificado.");
            response.sendRedirect(request.getContextPath() + "/web/Reservas.jsp");
            return;
        }

        int numeroPista = Integer.parseInt(pistaStr);
        LocalDateTime fechaHora = LocalDateTime.of(LocalDate.parse(fechaStr), LocalTime.parse(franjaStr));

        if (esFechaHoraPasada(fechaHora)) {
            session.setAttribute("popupMsg", "No puedes reservar una pista para una hora que ya ha pasado.");
            response.sendRedirect(buildReservaUrl(request, pistaStr, fechaStr, franjaStr));
            return;
        }

        @SuppressWarnings("unchecked")
        ArrayList<Integer> invitaciones =
            (ArrayList<Integer>) session.getAttribute("invitadosReserva");

        if (invitaciones == null) {
            invitaciones = new ArrayList<>();
        }

        try {
            AccesoBD con = AccesoBD.getInstance();

            AccesoBD.ResultadoReserva resultado = con.crearOUnirseReserva(
                municipioId,
                numeroPista,
                fechaHora,
                codigo,
                invitaciones
            );

            procesarResultadoReserva(
                session,
                con,
                resultado,
                municipioId,
                numeroPista,
                fechaHora,
                codigo,
                invitaciones
            );

        } catch (SQLException ex) {
            ex.printStackTrace();
            session.setAttribute("popupMsg", "Error creando/unirte a la reserva.");
        }

        response.sendRedirect(buildReservaUrl(request, pistaStr, fechaStr, franjaStr));
    }

    /**
     * Comprueba si la fecha y hora seleccionadas ya han pasado.
     * 
     * La comparación se realiza utilizando la zona horaria Europe/Madrid para
     * evitar inconsistencias con la hora del servidor.
     * 
     * @param fechaHora fecha y hora seleccionadas para la reserva.
     * @return true si la fecha y hora son anteriores al momento actual.
    */

    private boolean esFechaHoraPasada(LocalDateTime fechaHora) {
        ZoneId zonaMadrid = ZoneId.of("Europe/Madrid");
        LocalDateTime ahora = LocalDateTime.now(zonaMadrid);

        return fechaHora.isBefore(ahora);
    }

    /**
     * Procesa el resultado obtenido al intentar crear o unirse a una reserva.
     * 
     * Dependiendo del resultado, actualiza los mensajes de sesión, limpia las
     * invitaciones temporales o intenta aplicar invitaciones si el usuario ya
     * formaba parte de la reserva y es el creador.
     * 
     * @param session sesión actual del usuario.
     * @param con instancia de acceso a base de datos.
     * @param resultado resultado devuelto por la operación de reserva.
     * @param municipioId identificador del municipio.
     * @param numeroPista número de pista seleccionada.
     * @param fechaHora fecha y hora de la reserva.
     * @param codigo identificador del usuario autenticado.
     * @param invitaciones lista de invitaciones temporales.
    */

    private void procesarResultadoReserva(
        HttpSession session,
        AccesoBD con,
        AccesoBD.ResultadoReserva resultado,
        int municipioId,
        int numeroPista,
        LocalDateTime fechaHora,
        Integer codigo,
        ArrayList<Integer> invitaciones
    ) {
        switch (resultado) {
            case CREADA:
                session.setAttribute("popupMsg", "Reserva creada. Eres el creador.");
                limpiarInvitaciones(session, invitaciones);
                break;

            case UNIDO:
                session.setAttribute("popupMsg", "Te has unido a la reserva.");
                limpiarInvitaciones(session, invitaciones);
                break;

            case YA_ESTAS:
                procesarUsuarioYaEnReserva(
                    session,
                    con,
                    municipioId,
                    numeroPista,
                    fechaHora,
                    codigo,
                    invitaciones
                );
                break;

            case LLENA:
                session.setAttribute("popupMsg", "Esta reserva ya está completa.");
                limpiarInvitaciones(session, invitaciones);
                break;
        }
    }

    /**
     * Gestiona el caso en el que el usuario ya pertenece a la reserva.
     * 
     * Si existen invitaciones temporales, intenta aplicarlas siempre que el
     * usuario sea el creador de la reserva. Si se aplican correctamente, limpia
     * la lista temporal. En caso contrario, informa de que la partida está completa.
     * 
     * @param session sesión actual del usuario.
     * @param con instancia de acceso a base de datos.
     * @param municipioId identificador del municipio.
     * @param numeroPista número de pista seleccionada.
     * @param fechaHora fecha y hora de la reserva.
     * @param codigo identificador del usuario autenticado.
     * @param invitaciones lista de invitaciones temporales.
    */

    private void procesarUsuarioYaEnReserva(
        HttpSession session,
        AccesoBD con,
        int municipioId,
        int numeroPista,
        LocalDateTime fechaHora,
        Integer codigo,
        ArrayList<Integer> invitaciones
    ) {
        boolean aplicadas = false;

        if (invitaciones != null && !invitaciones.isEmpty()) {
            try {
                aplicadas = con.aplicarInvitacionesComoCreador(
                    municipioId,
                    numeroPista,
                    fechaHora,
                    codigo,
                    invitaciones
                );
            } catch (SQLException ex) {
                ex.printStackTrace();
                aplicadas = false;
            }
        }

        if (aplicadas) {
            session.setAttribute("popupMsg", "Invitaciones actualizadas correctamente.");
            limpiarInvitaciones(session, invitaciones);
        } else {
            session.setAttribute("popupMsg", "La partida ya esta completa.");
        }
    }

    /**
     * Limpia la lista temporal de invitaciones almacenada en sesión.
     * 
     * @param session sesión actual del usuario.
     * @param invitaciones lista de invitaciones temporales.
    */

    private void limpiarInvitaciones(HttpSession session, ArrayList<Integer> invitaciones) {
        invitaciones.clear();
        session.setAttribute("invitadosReserva", invitaciones);
    }

    /**
     * Construye la URL de retorno al servlet de reservas.
     * 
     * Mantiene como parámetros la pista, fecha y franja horaria seleccionadas
     * para que el usuario vuelva a la misma selección tras realizar una acción.
     * 
     * @param request objeto utilizado para obtener el contextPath de la aplicación.
     * @param pistaStr pista seleccionada.
     * @param fechaStr fecha seleccionada.
     * @param franjaStr franja horaria seleccionada.
     * @return URL construida para volver al servlet de reservas.
    */

    private String buildReservaUrl( HttpServletRequest request, String pistaStr, String fechaStr,String franjaStr) {
        StringBuilder url = new StringBuilder();
        url.append(request.getContextPath()).append("/Reserva");

        boolean first = true;

        if (pistaStr != null && !pistaStr.isEmpty()) {
            url.append(first ? "?" : "&").append("numero_pista=").append(pistaStr);
            first = false;
        }

        if (fechaStr != null && !fechaStr.isEmpty()) {
            url.append(first ? "?" : "&").append("fecha=").append(fechaStr);
            first = false;
        }

        if (franjaStr != null && !franjaStr.isEmpty()) {
            url.append(first ? "?" : "&").append("franja=").append(franjaStr);
            first = false;
        }

        return url.toString();
    }

}

