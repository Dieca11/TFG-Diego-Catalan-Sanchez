package servlets;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class Perfil extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    HttpSession s = request.getSession(false);

    Integer codigo = (s != null) ? (Integer) s.getAttribute("usuario") : null;

        AccesoBD con = AccesoBD.getInstance();

    if (codigo == null || codigo <= 0) {
      response.sendRedirect(request.getContextPath() + "/web/InicioSesion.jsp");
    }

    UsuarioBD u = con.obtenerUsuarioPorCodigo(codigo);
    s.setAttribute("usuarioPerfil", u);
    try {
        s.setAttribute("pendientesPerfil",con.obtenerPendientesPerfil(codigo));
        s.setAttribute("historialPerfil", con.obtenerHistorialPerfil(codigo));
    } catch (SQLException e) {
        e.printStackTrace();
        s.setAttribute("mensajePerfil", "No se pudieron cargar las partidas del perfil.");
        response.sendRedirect(request.getContextPath() + "/web/Perfil.jsp");
    }

    response.sendRedirect(request.getContextPath() + "/web/Perfil.jsp");

    return;
  }
  @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
            HttpSession session = request.getSession(false);
            Integer id = (session != null) ? (Integer) session.getAttribute("usuario") : null;

            if (id == null || id <= 0) {
                response.sendRedirect(request.getContextPath() + "/web/InicioSesion.jsp");
                return;
            }


            String accion = request.getParameter("accion");

            if ("cancelarPartida".equals(accion)) {
                String idReservaStr = request.getParameter("idReserva");

                try {
                    int idReserva = Integer.parseInt(idReservaStr);

                    AccesoBD con = AccesoBD.getInstance();
                    con.cancelarReserva(idReserva);

                    response.sendRedirect(request.getContextPath() + "/Perfil");
                    return;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if ("anotarResultado".equals(accion)) {

                String idReservaStr = request.getParameter("idReserva");
                String resultado = request.getParameter("resultado");
                String[] ganadores = request.getParameterValues("ganadores");

                if (idReservaStr == null || idReservaStr.isBlank()) {
                    throw new ServletException("No se recibió idReserva.");
                }

                int idReserva = Integer.parseInt(idReservaStr);

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
                    int ganador1 = Integer.parseInt(ganadores[0]);
                    int ganador2 = Integer.parseInt(ganadores[1]);

                    con.guardarResultadoPartida(idReserva, resultado, ganador1, ganador2);

                    session.setAttribute("mensajePerfil", "Resultado guardado correctamente.");
                    response.sendRedirect(request.getContextPath() + "/Perfil");
                    return;

                } catch (Exception e) {
                    e.printStackTrace();
                    session.setAttribute("mensajePerfil", "No se pudo guardar el resultado.");
                    response.sendRedirect(request.getContextPath() + "/Perfil");
                    return;
                }
            }

 
            //Recogemos los datos a actualizar
            Integer id_usu = Integer.parseInt(request.getParameter("id_usu"));
            String imagen = request.getParameter("imagen_usuario");
            String nombre_usu = request.getParameter("nombre_usuario");
            String email = request.getParameter("email");
            boolean invitacion = request.getParameter("recibir_invitaciones") != null;
            boolean partidas = request.getParameter("mostrar_partidas") != null;
            String clave1 = request.getParameter("clave1");
            String clave2 = request.getParameter("clave2");
            String tarjeta = request.getParameter("tarjeta");

            if (tarjeta != null) {
                tarjeta = tarjeta.trim();
            }

            AccesoBD con = AccesoBD.getInstance();


            //Comprobar si se quiere cambiar la clave y que sean ambas iguales
            if ((clave1 != null && !clave1.isEmpty()) ||
                (clave2 != null && !clave2.isEmpty())) {

                if (clave1 == null || clave2 == null ||
                    clave1.isEmpty() || clave2.isEmpty() ||
                    !clave1.equals(clave2)) {

                    session.setAttribute("mensajePerfil",
                            "Las contraseñas no coinciden o están vacías");
                        response.sendRedirect(request.getContextPath() + "/web/Perfil.jsp");
                        return;
                }
            }

            //Validar si la tarjeta tiene los numeros necesarios
            if (tarjeta != null && !tarjeta.isEmpty()) {
                String limpia = tarjeta.replaceAll("\\s+", "");
                // Aquí supongo 16 dígitos
                if (!limpia.matches("\\d{16}")) {
                    session.setAttribute("mensajePerfil",
                            "La tarjeta debe tener 16 dígitos numéricos");
                        response.sendRedirect(request.getContextPath() + "/web/Perfil.jsp");
                        return;
                }
                tarjeta = limpia;
            } else {
                // Si permites tarjeta vacía, puedes ponerla a null
                tarjeta = null;
            }


            //Comprobar si ese usuario/email ya existen en la bd
            if (con.existeUsuOEmail(id_usu, nombre_usu, email)) {
                session.setAttribute("mensajePerfil",
                        "Nombre de usuario o email ya estan en uso");
                    response.sendRedirect(request.getContextPath() + "/web/Perfil.jsp");
                    return;
            }
            //Actualizar nuevos datos del usuario
             con.ActualizarUsuario(id_usu,nombre_usu,email,imagen,invitacion,partidas,clave1,tarjeta);
             UsuarioBD actualizado = con.obtenerUsuarioPorCodigo(id_usu);
             session.setAttribute("usuarioPerfil", actualizado);
            response.sendRedirect(request.getContextPath() + "/web/Perfil.jsp");
        }


}
