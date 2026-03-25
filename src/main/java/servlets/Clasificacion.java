package servlets;

import java.io.IOException;
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

        try {
            String idMunicipioStr = request.getParameter("idMunicipio");
            Integer idMunicipio = null;

            if (idMunicipioStr != null && !idMunicipioStr.trim().isEmpty()) {
                idMunicipio = Integer.parseInt(idMunicipioStr);
            }

            ArrayList<UsuarioClasificacion> clasificacion =
                AccesoBD.getInstance().obtenerClasificacionPorMunicipio(idMunicipio);

            ArrayList<MunicipioBD> municipios =
                (ArrayList<MunicipioBD>) AccesoBD.getInstance().obtenerMunicipiosBD();

            // Nombre del filtro
            String filtro = "GENERAL";

            if (idMunicipio != null) {
                MunicipioBD m = AccesoBD.getInstance().obtenerMunicipioBD(idMunicipio);
                if (m != null) {
                    filtro = m.getMunicipio();
                }
            }

            session.setAttribute("clasificacionGeneral", clasificacion);
            session.setAttribute("municipiosClasificacion", municipios);
            session.setAttribute("filtroClasificacion", filtro);
            session.setAttribute("idMunicipioSeleccionado", idMunicipio);

            response.sendRedirect(request.getContextPath() + "/web/Clasificaciones.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
        return;
    }
}