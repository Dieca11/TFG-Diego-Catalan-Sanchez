package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class Reserva extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession(false);
        Integer codigo = (session != null) ? (Integer) session.getAttribute("usuario") : null;

        if (codigo == null || codigo <= 0) {
            session.setAttribute("mensajeError", "Para acceder a la reserva de pistas debes iniciar sesión.");
            response.sendRedirect(request.getContextPath() + "/web/InicioSesion.jsp");
            return;
        }

        AccesoBD con = AccesoBD.getInstance();

        // 1) municipio_id: si viene por parámetro, lo guardas en sesión
        String municipioParam = request.getParameter("id");
        if (municipioParam != null && !municipioParam.isEmpty()) {
            session.setAttribute("id", Integer.parseInt(municipioParam));
        }

        // 2) si no hay municipio en sesión, no puedes continuar
        Object midObj = session.getAttribute("id");
        if (midObj == null) {
            response.sendRedirect("web/Reservas.jsp"); // o donde toque
            return;
        }
        int municipioId = (midObj instanceof Integer) ? (Integer) midObj : Integer.parseInt(midObj.toString());

        // 3) SIEMPRE cargar invitables para el modal
        try{
        request.setAttribute("usuariosInvitables", con.obtenerUsuariosInvitables());
        } catch (SQLException e) {
			System.err.println("Error al obtener los datos de la reserva");
			System.err.println(e.getMessage());
        }

        // 4) valores de selección (pueden venir vacíos la primera vez)
        String pistaStr = request.getParameter("numero_pista");
        String fechaStr = request.getParameter("fecha");
        String franjaStr = request.getParameter("franja");

        if (pistaStr != null) request.setAttribute("numero_pista", Integer.parseInt(pistaStr));
        if (fechaStr != null) request.setAttribute("fecha", fechaStr);
        if (franjaStr != null) request.setAttribute("franja", franjaStr);

        // 5) Si tienes los 3, cargas u1..u4. Si no, los dejas a null.
        if (pistaStr != null && fechaStr != null && franjaStr != null &&
            !pistaStr.isEmpty() && !fechaStr.isEmpty() && !franjaStr.isEmpty()) {

            int numeroPista = Integer.parseInt(pistaStr);
            LocalDateTime fechaHora = LocalDateTime.of(LocalDate.parse(fechaStr), LocalTime.parse(franjaStr));
   /*
            int[] ids = con.obtenerReservaIdsUsuarios(municipioId, numeroPista, fechaHora);
            AccesoBD.UsuarioVista[] usuarios = con.obtenerUsuariosVistaPorIds(ids);
        
            request.setAttribute("u1", usuarios[0]);
            request.setAttribute("u2", usuarios[1]);
            request.setAttribute("u3", usuarios[2]);
            request.setAttribute("u4", usuarios[3]);      */   
        } else {
            request.setAttribute("u1", null);
            request.setAttribute("u2", null);
            request.setAttribute("u3", null);
            request.setAttribute("u4", null);
        }

        // 6) forward SIEMPRE a la JSP
        MunicipioBD municipio = con.obtenerMunicipioBD(municipioId);
        request.setAttribute("municipio", municipio);

        request.getRequestDispatcher("/web/ReservasMunicipio.jsp")
                   .forward(request, response);
        return;
        }

        @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        // 1) Bloqueo por sesión (mismo patrón que usas en Perfil)
        HttpSession s = request.getSession(false);
        Integer codigo = (s != null) ? (Integer) s.getAttribute("usuario") : null;

        if (codigo == null || codigo <= 0) {
            HttpSession s2 = request.getSession(true);
            s2.setAttribute("popupMsg", "Para acceder a la reserva de pistas debes iniciar sesión.");
            response.sendRedirect(request.getContextPath() + "/web/InicioSesion.jsp");
            return;
        }

        // 2) Acción
        String action = request.getParameter("action");
        if (action == null) action = "";

        // 3) Recuperar contexto (para volver a la misma selección)
        String pistaStr = request.getParameter("numero_pista");
        String fechaStr = request.getParameter("fecha");
        String franjaStr = request.getParameter("franja");

        // 4) Recuperar / crear lista en sesión
        @SuppressWarnings("unchecked")
        java.util.ArrayList<Integer> invitados =
                (java.util.ArrayList<Integer>) s.getAttribute("invitadosReserva");

        if (invitados == null) invitados = new java.util.ArrayList<>();

        try {
            if ("addInv".equals(action)) {
                String invStr = request.getParameter("invitado_id");
                if (invStr != null && !invStr.isEmpty()) {
                    int invitadoId = Integer.parseInt(invStr);

                    // No permitir invitarse a uno mismo
                    if (invitadoId == codigo) {
                        s.setAttribute("popupMsg", "No puedes invitarte a ti mismo.");
                    } else if (invitados.contains(invitadoId)) {
                        s.setAttribute("popupMsg", "Ese usuario ya está invitado.");
                    } else if (invitados.size() >= 3) {
                        s.setAttribute("popupMsg", "Máximo 3 invitaciones.");
                    } else {
                        invitados.add(invitadoId);
                    }
                }

                s.setAttribute("invitadosReserva", invitados);

            } else if ("delInv".equals(action)) {
                String invStr = request.getParameter("invitado_id");
                if (invStr != null && !invStr.isEmpty()) {
                    int invitadoId = Integer.parseInt(invStr);
                    invitados.remove(Integer.valueOf(invitadoId));
                }
                s.setAttribute("invitadosReserva", invitados);
            }

        } catch (Exception e) {
            e.printStackTrace();
            s.setAttribute("popupMsg", "Error procesando la invitación.");
        }

        // 5) Volver a /Reserva manteniendo pista/fecha/franja
        response.sendRedirect(buildReservaUrl(request, pistaStr, fechaStr, franjaStr));
    }

    private String buildReservaUrl(HttpServletRequest request, String pistaStr, String fechaStr, String franjaStr) {
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

