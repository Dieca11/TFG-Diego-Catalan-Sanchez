package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ReservaMunicipio extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
        HttpSession session = request.getSession();

        // Obtener datos del formulario
        String municipiostr = request.getParameter("municipio_id");
        String numero_pistastr = request.getParameter("numero_pista");
        String fechastr = request.getParameter("fecha");
        String franjastr = request.getParameter("franja");

        int municipio = Integer.parseInt(municipiostr);
        int numero_pista = Integer.parseInt(numero_pistastr);
        LocalDate fecha = LocalDate.parse(fechastr);
        LocalTime franja = LocalTime.parse(franjastr);

        LocalDateTime fechahora = LocalDateTime.of(fecha, franja);

        AccesoBD con = AccesoBD.getInstance();

        try {
            Integer[] usuariosids = con.obtenerReserva(municipio, numero_pista, fechahora);

                    // 2) Convertir ids -> perfiles (while/for)
            AccesoBD.UsuarioVista[] usuarios = new AccesoBD.UsuarioVista[4];

        int i = 0;
        while (i < 4) {
            Integer id = usuariosids[i];

            // Si estás usando getInt y te llega 0 como "vacío"
            if (id == null || id == 0) {
                usuarios[i] = null;
            } else {
                usuarios[i] = con.obtenerUsuarioVistaPorId(id);
            }
            i++;
        }
        // 3) Enviar a JSP
        request.setAttribute("u1", usuarios[0]);
        request.setAttribute("u2", usuarios[1]);
        request.setAttribute("u3", usuarios[2]);
        request.setAttribute("u4", usuarios[3]);


        ArrayList<AccesoBD.UsuarioVista> invitables = con.obtenerUsuariosInvitables();
        request.setAttribute("usuariosInvitables", invitables);


        request.getRequestDispatcher("/ReservasMunicipio.jsp").forward(request, response);

        } catch (SQLException e) {
			System.err.println("Error al obtener los datos de la reserva");
			System.err.println(e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/web/ReservaMunicipio.jsp");

    }
}

