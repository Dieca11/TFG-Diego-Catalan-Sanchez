package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class Clasificacion extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        ArrayList<MunicipioBD> municipios =
        (ArrayList<MunicipioBD>) AccesoBD.getInstance().obtenerMunicipiosBD();

        session.setAttribute("municipiosClasificacion", municipios);

        try {
            ArrayList<UsuarioClasificacion> clasificacion =
                AccesoBD.getInstance().obtenerClasificacionGeneral();

            session.setAttribute("clasificacionGeneral", clasificacion);
            session.setAttribute("filtroClasificacion", "General");

            System.out.println("TAMANO CLASIFICACION EN SERVLET: " + clasificacion.size());
            session.setAttribute("clasificacionGeneral", clasificacion);
            System.out.println("ATRIBUTO GUARDADO EN SESION: " + session.getAttribute("clasificacionGeneral"));

            response.sendRedirect(request.getContextPath() + "/web/Clasificaciones.jsp");

        } catch (SQLException e) {
            System.err.println("Error al cargar la clasificación");
            e.printStackTrace();
            throw new ServletException(e);
        }
        return;
    }
}