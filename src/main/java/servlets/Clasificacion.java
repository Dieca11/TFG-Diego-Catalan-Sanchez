package servlets;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet encargado de gestionar la clasificación de usuarios.
 * 
 * Permite consultar la clasificación general o filtrada por municipio,
 * cargando también la lista de municipios disponibles para el selector
 * de la página de clasificaciones.
 */

public class Clasificacion extends HttpServlet {

    /**
     * Atiende las peticiones HTTP de tipo GET dirigidas al servlet de clasificación.
     * 
     * Delega la carga de los datos de clasificación en el método cargarClasificacion,
     * que obtiene la clasificación correspondiente, los municipios disponibles
     * y el filtro seleccionado.
     * 
     * @param request objeto que contiene la petición HTTP realizada por el cliente.
     * @param response objeto utilizado para generar la respuesta HTTP.
     * @throws ServletException si ocurre un error propio del servlet.
     * @throws IOException si ocurre un error de entrada o salida durante la petición.
     */

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        cargarClasificacion(request, response);
        return;

    }

    /**
     * Carga la información necesaria para mostrar la página de clasificaciones.
     * 
     * Obtiene el municipio seleccionado, recupera la clasificación correspondiente
     * desde la base de datos, carga la lista de municipios disponibles y determina
     * el nombre del filtro que se mostrará en la vista.
     * 
     * La información obtenida se guarda en la sesión para ser utilizada
     * posteriormente en la página Clasificaciones.jsp.
     * 
     * @param request objeto que contiene la petición HTTP del usuario.
     * @param response objeto utilizado para redirigir al usuario tras cargar los datos.
     * @throws ServletException si ocurre un error propio del servlet.
     * @throws IOException si ocurre un error de entrada o salida durante la redirección.
     */

    private void cargarClasificacion(HttpServletRequest request, HttpServletResponse response)
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
    }
}