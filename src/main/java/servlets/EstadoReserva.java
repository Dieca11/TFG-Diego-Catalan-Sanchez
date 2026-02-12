package servlets;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.time.*;

@WebServlet("/reservas/estado")
public class EstadoReserva extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json; charset=UTF-8");

        HttpSession s = request.getSession(false);
        Integer codigo = (s != null) ? (Integer) s.getAttribute("usuario") : null;
        if (codigo == null || codigo <= 0) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"libre\":true,\"usuarios\":[null,null,null,null]}");
            return;
        }

        String municipioIdStr = request.getParameter("municipioId");
        String numeroPistaStr = request.getParameter("numeroPista");
        String fechaStr = request.getParameter("fecha");   // YYYY-MM-DD
        String franjaStr = request.getParameter("franja"); // HH:mm

        if (isBlank(municipioIdStr) || isBlank(numeroPistaStr) || isBlank(fechaStr) || isBlank(franjaStr)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"params\"}");
            return;
        }

        int municipioId, numeroPista;
        try {
            municipioId = Integer.parseInt(municipioIdStr);
            numeroPista = Integer.parseInt(numeroPistaStr);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"number\"}");
            return;
        }

        LocalDateTime fechaHora;
        try {
            fechaHora = LocalDateTime.of(LocalDate.parse(fechaStr), LocalTime.parse(franjaStr));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"datetime\"}");
            return;
        }

        try {
            AccesoBD con = AccesoBD.getInstance();
            AccesoBD.UsuarioVista[] pos = con.obtenerUsuariosReservaSlot(municipioId, numeroPista, fechaHora);

            boolean libre = true;
            for (AccesoBD.UsuarioVista u : pos) {
                if (u != null) { libre = false; break; }
            }

            String ctx = request.getContextPath();
            response.getWriter().write(buildJson(libre, pos, ctx));

        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"server\"}");
        }
    }

    private static String buildJson(boolean libre, AccesoBD.UsuarioVista[] pos, String ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"libre\":").append(libre).append(",\"usuarios\":[");
        for (int i = 0; i < 4; i++) {
            if (i > 0) sb.append(",");
            AccesoBD.UsuarioVista u = pos[i];
            if (u == null) {
                sb.append("null");
            } else {
                String img = u.getImagenPerfil();
                String fotoUrl = (img == null || img.isBlank())
                        ? (ctx + "/web/Imagenes/usuario.png")
                        : (img.startsWith("/") ? (ctx + img) : (ctx + "/" + img));

                sb.append("{\"id\":").append(u.getId())
                  .append(",\"nombre\":\"").append(escapeJson(u.getNombreUsuario()))
                  .append("\",\"fotoUrl\":\"").append(escapeJson(fotoUrl))
                  .append("\"}");
            }
        }
        sb.append("]}");
        return sb.toString();
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private static String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}