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

public class Reserva extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession(false);
        Integer codigo = (session != null) ? (Integer) session.getAttribute("usuario") : null;

        if (codigo == null || codigo <= 0) {
            HttpSession s2 = request.getSession(true);
            s2.setAttribute("popupMsg", "Para acceder a la reserva de pistas debes iniciar sesión.");
            response.sendRedirect(request.getContextPath() + "/web/InicioSesion.jsp");
            return;
        }

        String msg = (String) session.getAttribute("popupMsg");
        if (msg != null) {
            request.setAttribute("popupMsg", msg);
            session.removeAttribute("popupMsg");
        }

        AccesoBD con = AccesoBD.getInstance();

        // 2) municipio: usa UNA clave consistente en sesión (ej: "municipio_id")
        String municipioParam = request.getParameter("id"); // si tu enlace usa ?id=...
        if (municipioParam != null && !municipioParam.isEmpty()) {
            session.setAttribute("municipio_id", Integer.parseInt(municipioParam));
        }

        Object midObj = session.getAttribute("municipio_id");
        if (midObj == null) {
            // vuelve a lista de municipios (con contextPath)
            response.sendRedirect(request.getContextPath() + "/web/Reservas.jsp");
            return;
        }
        int municipioId = (midObj instanceof Integer) ? (Integer) midObj : Integer.parseInt(midObj.toString());

        // 3) Usuarios invitables
        try {
            request.setAttribute("usuariosInvitables", con.obtenerUsuariosInvitables());
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("usuariosInvitables", new java.util.ArrayList<>());
        }

        // 4) Selección actual
        String pistaStr = request.getParameter("numero_pista");
        String fechaStr = request.getParameter("fecha");
        String franjaStr = request.getParameter("franja");

        if (pistaStr != null && !pistaStr.isEmpty()) request.setAttribute("numero_pista", Integer.parseInt(pistaStr));
        if (fechaStr != null && !fechaStr.isEmpty()) request.setAttribute("fecha", fechaStr);
        if (franjaStr != null && !franjaStr.isEmpty()) request.setAttribute("franja", franjaStr);

        // 5) Cargar usuarios de la reserva si hay slot completo (cuando lo actives)
        if (pistaStr != null && fechaStr != null && franjaStr != null &&
            !pistaStr.isEmpty() && !fechaStr.isEmpty() && !franjaStr.isEmpty()) {

            request.setAttribute("u1", null);
            request.setAttribute("u2", null);
            request.setAttribute("u3", null);
            request.setAttribute("u4", null);

        } else {
            request.setAttribute("u1", null);
            request.setAttribute("u2", null);
            request.setAttribute("u3", null);
            request.setAttribute("u4", null);
        }
        // 6) Forward
        MunicipioBD municipio = con.obtenerMunicipioBD(municipioId);
        request.setAttribute("municipio", municipio);

        request.getRequestDispatcher("/web/ReservasMunicipio.jsp").forward(request, response);
    }


        @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

                HttpSession s = request.getSession(true);

        Integer codigo = (s != null) ? (Integer) s.getAttribute("usuario") : null;


        if (codigo == null || codigo <= 0) {
            s.setAttribute("popupMsg", "Para acceder a la reserva de pistas debes iniciar sesión.");
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
        String municipioStr = request.getParameter("municipio_id");
        if (municipioStr != null && !municipioStr.isEmpty()) {
            s.setAttribute("municipio_id", Integer.parseInt(municipioStr));
        }


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
            else if ("crearReserva".equals(action)) {

                if (pistaStr == null || fechaStr == null || franjaStr == null ||
                    pistaStr.isEmpty() || fechaStr.isEmpty() || franjaStr.isEmpty()) {
                    s.setAttribute("popupMsg", "Selecciona pista, día y hora antes de reservar.");
                    response.sendRedirect(buildReservaUrl(request, pistaStr, fechaStr, franjaStr));
                    return;
                }

                Object midObj = s.getAttribute("municipio_id");
                if (midObj == null) {
                    s.setAttribute("popupMsg", "ERROR: municipio no identificado.");
                    response.sendRedirect(request.getContextPath() + "/web/Reservas.jsp");
                    return;
                }
                int municipioId = (midObj instanceof Integer) ? (Integer) midObj : Integer.parseInt(midObj.toString());


                int numeroPista = Integer.parseInt(pistaStr);

                LocalDateTime fechaHora = LocalDateTime.of(LocalDate.parse(fechaStr), LocalTime.parse(franjaStr));

                ZoneId zid = ZoneId.of("Europe/Madrid");
                LocalDateTime ahora = LocalDateTime.now(zid);
                if (fechaHora.isBefore(ahora)) {
                    s.setAttribute("popupMsg", "No puedes reservar una pista para una hora que ya ha pasado.");
                    response.sendRedirect(buildReservaUrl(request, pistaStr, fechaStr, franjaStr));
                    return;
                }

                // invitaciones actuales (solo se usan si NO existe reserva)
                @SuppressWarnings("unchecked")
                ArrayList<Integer> invitaciones = (ArrayList<Integer>) s.getAttribute("invitadosReserva");
                if (invitaciones == null) invitaciones = new ArrayList<>();

                try {
                    AccesoBD con = AccesoBD.getInstance();
                    AccesoBD.ResultadoReserva r = AccesoBD.getInstance()
                            .crearOUnirseReserva(municipioId, numeroPista, fechaHora, codigo, invitaciones);

                switch (r) {
                    case CREADA:
                        s.setAttribute("popupMsg", "Reserva creada. Eres el creador.");
                        // ya se han aplicado invitaciones al crear
                        invitaciones.clear();
                        s.setAttribute("invitadosReserva", invitaciones);
                        break;

                    case UNIDO:
                        s.setAttribute("popupMsg", "Te has unido a la reserva.");
                        // si te unes, no tiene sentido mantener pre-invitaciones
                        invitaciones.clear();
                        s.setAttribute("invitadosReserva", invitaciones);
                        break;

                    case YA_ESTAS:
                        boolean aplicadas = false;
                        if (invitaciones != null && !invitaciones.isEmpty()) {
                            try {
                                aplicadas = con.aplicarInvitacionesComoCreador(municipioId, numeroPista, fechaHora, codigo, invitaciones);
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                                aplicadas = false;
                            }
                        }

                        if (aplicadas) {
                            s.setAttribute("popupMsg", "Invitaciones actualizadas correctamente.");
                            invitaciones.clear();
                            s.setAttribute("invitadosReserva", invitaciones);
                        } else {
                            s.setAttribute("popupMsg", "Ya estás dentro de esta reserva.");
                        }
                        break;

                    case LLENA:
                        s.setAttribute("popupMsg", "Esta reserva ya está completa.");
                        invitaciones.clear();
                        s.setAttribute("invitadosReserva", invitaciones);
                        break;
                }

                response.sendRedirect(buildReservaUrl(request, pistaStr, fechaStr, franjaStr));
                return;


                } catch (SQLException ex) {
                    ex.printStackTrace();
                    s.setAttribute("popupMsg", "Error creando/unirte a la reserva.");
                }
                
                response.sendRedirect(buildReservaUrl(request, pistaStr, fechaStr, franjaStr));
                return;
            }


        } catch (Exception e) {
            e.printStackTrace();
            s.setAttribute("popupMsg", "Error procesando la invitación.");
        }


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

