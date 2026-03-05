<%@ page language="java" contentType="text/html; charset=UTF-8" import=" java.util.List,servlets.*" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/web/css/cabecera-footer.css">
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/web/css/Perfil.css">

        <title>HOME</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" 
        integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
            integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css">
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
            integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">

            <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">

            <link rel="stylesheet" href="https://unpkg.com/@coreui/icons@3.0.0/css/coreui-icons.min.css" />


        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+Knujsl5+7GDvjz4Et6kcu9teW7RSJoV++Ar5QnFexl3O9b" crossorigin="anonymous">
        <link rel="icon" href="Imagenes/raqueta-de-padel.png" type="image/png" class="logo">
    </head>
    <body>
        <mi-cabecera></mi-cabecera>

        
        <%
        HttpSession s = request.getSession(false); // no crea sesión nueva
        UsuarioBD usuario = (UsuarioBD) request.getSession().getAttribute("usuarioPerfil");


        if (usuario == null) {
        response.sendRedirect("InicioSesion.jsp");
        return;
        }
        %>
        <%
        String mensajePerfil = (String) session.getAttribute("mensajePerfil");
        if (mensajePerfil != null) {
            session.removeAttribute("mensajePerfil");

            // Escapado mínimo para meterlo dentro de una cadena JS entre comillas
            String msgPerfil = mensajePerfil
                .replace("\\", "\\\\")
                .replace("'", "\\'")
                .replace("\r", "")
                .replace("\n", "\\n");
        %>
        <script>
        alert('<%= msgPerfil %>');
        </script>
        <%
        }
        %>

        <div class="container-perfil">
            
            <div class="perfil-principal">
                <img src='<%= usuario.getFoto_perfil() %>' alt=" Foto de perfil del Usuario">
                <label class="nombre-usuario"><%= usuario.getUsuario() %></label>
                <label class="email-usuario"><%= usuario.getEmail() %></label>
                <a class="btn" href="${pageContext.request.contextPath}/LogOut">Cerrar Sesion</a>
            </div>

            <hr style=" border-top: 5px solid rgb(50, 83, 146); border-radius: 20px;">

            <div class="opciones-configuracion">
                <ul class="opciones-configuracion-tabs" style="list-style-type: none;">


                    <li>
                        <a class="link-usuario activo" data-target="contenido1" href="#">
                            <div class="bi bi-person-fill fs-2"></div>

                            <div class="texto">
                                <span class="titulo">Edita tu perfil</span>
                                <span> Edita tu foto de perfil, imagen, usuario, etc. </span>
                            </div>
                        </a>
                    </li>

                    <li>
                        <a class="link-usuario" data-target="contenido2" href="#">
                            <div class="bi bi-list-ol fs-2"></div>

                            <div class="texto">
                                <span class="titulo">Revisa tus partidas</span>
                                <span>Gestiona resultados y partidas de padel </span>
                            </div>
                        </a>
                    </li>

                     <li>
                        <a class="link-usuario" data-target="contenido3" href="#">
                            <div class="bi bi-shield-fill-check fs-2"></div>

                            <div class="texto">
                                <span class="titulo">Privacidad</span>
                                <span> Gestiona tus invitaciones de padel</span>
                            </div>
                        </a>
                    </li>

                    <li>
                        <a class="link-usuario" data-target="contenido4" href="#">
                            <div class="bi bi-key-fill fs-2"></div>

                            <div class="texto">
                                <span class="titulo">Email, contraseña</span>
                                <span> Gestiona tu email y contraseñas</span>
                            </div>
                        </a>
                    </li>

                    <li>
                        <a class="link-usuario" data-target="contenido5" href="#">
                            <div class="bi bi-credit-card-fill fs-2"></div>

                            <div class="texto">
                                <span class="titulo">Metodo de pago</span>
                                <span> Gestiona tu metodo de pago</span>
                            </div>
                        </a>
                    </li>

                </ul>

                <div class="opciones-configuracion-editar">
                    <form action="<%=request.getContextPath()%>/Perfil" method="post">

                        <input type="hidden" name="id_usu" value="<%=usuario.getId()%>">

                        <div class="content-block active" id="contenido1">

                            <h2 class="titulo-editar">EDITAR PERFIL</h2>

                            <div class="form-usuario">
                                    <input type="file" id="imagen_usuario" name="Imagen-usuario" accept="image/*" style="display:none"/>
                                    <label for="imagen_usuario" class="label-imagen">Seleccionar imagen</label>
                            </div>

                            <div class="form-usuario">
                                <input type="text" name="nombre_usuario" value="<%=usuario.getUsuario()%>" required>
                            </div>

                            <div class="form-usuario">
                                <input type="text" placeholder="<%=usuario.getEmail()%> " readonly>
                            </div>

                            <div class="form-usuario">
                                <input type="password" placeholder="<%=usuario.getContraseña()%>" readonly>
                            </div>

                            <div class=" form-usuario">
                                <input type="submit" value="Cambiar Datos" class="btn">
                            </div>
                        </div>

                        <div class="content-block" id="contenido2">
                            <div class="partidas-tabla">
                                <div class="partidas-lista">
                                    <h3 class="titulo-partidas"> PENDIENTES</h3>

                                    <div class="partidas-card">
                                        <div class="partida-head">
                                            <div class="partida-fecha">19/06/2026 20:00-21:30</div>
                                            <div class="partida-estado"> Pendiente</div>
                                        </div>

                                        <div class="partida-info">
                                            <div class="partida-lugar"> Mora de Rubielos</div>
                                            <div class="partida-pista"> Pista 1</div>
                                        </div>

                                        <div class="partida-actions">
                                            <div class="btn-partida"> Participantes</div>
                                            <div class="btn-cancelar">Cancelar</div>
                                        </div>
                                    </div>

                                    <div class="partidas-card">
                                        <div class="partida-head">
                                            <div class="partida-fecha">19/06/2026 20:00-21:30</div>
                                            <div class="partida-estado"> Pendiente</div>
                                        </div>

                                        <div class="partida-info">
                                            <div class="partida-lugar"> Mora de Rubielos</div>
                                            <div class="partida-pista"> Pista 1</div>
                                        </div>

                                        <div class="partida-actions">
                                            <div class="btn-partida"> Participantes</div>
                                            <div class="btn-cancelar">Cancelar</div>
                                        </div>


                                    </div>
  
                                    <div class="partidas-card">
                                        <div class="partida-head">
                                            <div class="partida-fecha">19/06/2026 20:00-21:30</div>
                                            <div class="partida-estado"> Pendiente</div>
                                        </div>

                                        <div class="partida-info">
                                            <div class="partida-lugar"> Mora de Rubielos</div>
                                            <div class="partida-pista"> Pista 1</div>
                                        </div>

                                        <div class="partida-actions">
                                            <div class="btn-partida"> Participantes</div>
                                            <div class="btn-cancelar">Cancelar</div>
                                        </div>
                                    </div>
                                    <div class="partidas-card">
                                        <div class="partida-head">
                                            <div class="partida-fecha">19/06/2026 20:00-21:30</div>
                                            <div class="partida-estado"> Pendiente</div>
                                        </div>

                                        <div class="partida-info">
                                            <div class="partida-lugar"> Mora de Rubielos</div>
                                            <div class="partida-pista"> Pista 1</div>
                                        </div>

                                        <div class="partida-actions">
                                            <div class="btn-partida"> Participantes</div>
                                            <div class="btn-cancelar">Cancelar</div>
                                        </div>
                                    </div>
                                </div>

                                <div class="partidas-lista">
                                    <h3 class="titulo-partidas"> HISTORIAL</h3>
                                    <div class="partidas-card">
                                        <div class="partida-head">
                                            <div class="partida-fecha">19/06/2026 20:00-21:30</div>
                                            <div class="partida-estado"> Finalizada</div>
                                        </div>
                                        <div class="partida-info">
                                            <div class="partida-resultado"> Resultado: 6-2/3-6/4-6</div>
                                        </div>
                                        <div class="partida-actions">
                                            <div class="btn-partida">Detalles</div>
                                            <div class="btn-resultado"> Anotar Resultado</div>
                                        </div>
                                    </div>
                                    <div class="partidas-card">
                                        <div class="partida-head">
                                            <div class="partida-fecha">19/06/2026 20:00-21:30</div>
                                            <div class="partida-estado"> Finalizada</div>
                                        </div>
                                        <div class="partida-info">
                                            <div class="partida-resultado"> Resultado: 6-2/3-6/4-6</div>
                                        </div>
                                        <div class="partida-actions">
                                            <div class="btn-partida">Detalles</div>
                                            <div class="btn-resultado"> Anotar Resultado</div>
                                        </div>
                                    </div>
                                    <div class="partidas-card">
                                        <div class="partida-head">
                                            <div class="partida-fecha">19/06/2026 20:00-21:30</div>
                                            <div class="partida-estado"> Finalizada</div>
                                        </div>
                                        <div class="partida-info">
                                            <div class="partida-resultado"> Resultado: 6-2/3-6/4-6</div>
                                        </div>
                                        <div class="partida-actions">
                                            <div class="btn-partida">Detalles</div>
                                            <div class="btn-resultado"> Anotar Resultado</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="content-block" id="contenido3">
                            <h2 class="titulo-editar">GESTIONAR LA PRIVACIDAD DE DATOS</h2>

                            <label class="privacidad-label">
                                <span class="switch">
                                    <input type="checkbox" name="recibir_invitaciones"
                                        <%= usuario.isRecibir_invitacion() ? "checked=\"checked\"" : "" %>/>
                                    <span class="slider"></span>
                                </span>
                                Recibir invitaciones a partidas de otros usuarios
                            </label>

                            <label class="privacidad-label">
                                <span class="switch">
                                    <input type="checkbox" name="mostrar_partidas"
                                        <%= usuario.isMostrar_partidas() ? "checked=\"checked\"" : "" %>/>
                                    <span class="slider"></span>
                                </span>
                                Mostrar mis partidas en la clasificación
                            </label>

                            <div class="form-usuario">
                                <input type="submit" value="Cambiar Configuracion" class="btn">
                            </div>

                        </div>

                        

                        <div class="content-block" id="contenido4">

                            <h2 class="titulo-editar">EDITAR SEGURIDAD</h2>

                            <div class="form-usuario">
                                <input type="text" name="email" value="<%=usuario.getEmail()%>" required>
                            </div>

                            <div class="form-usuario">
                                <input type="password" name="clave1" value="" placeholder="Contraseña nueva" >
                            </div>
                            <div class="form-usuario">
                                <input type="password" name="clave2" value="" placeholder="Confirmar contraseña">
                            </div>

                            <div class=" form-usuario">
                                <input type="submit" value="Cambiar Datos" class="btn">
                            </div>
                        </div>


                        <div class="content-block" id="contenido5">

                            <h2 class="titulo-editar">CAMBIAR METODO DE PAGO</h2>

                            <div class="form-pago form-usuario">
                                <span> NUMERO DE TARJETA</span>
                                <input type="number" name="tarjeta" value="<%=usuario.getTarjeta()%>">
                            </div>

                            <div class="form-pago form-usuario">
                                <span> FECHA DE CADUCIDAD</span>
                                <input type="password" placeholder="10/26" readonly>
                            </div>
                            <div class="form-pago form-usuario">
                                <input type="password" placeholder=" Confirmar contraseña" readonly>
                            </div>

                            <div class=" form-pago form-usuario">
                                <input type="submit" value="Cambiar Datos" class="btn">
                            </div>
                        </div>
                    </div>
                </form>
            </div>

        </div>
        
        
        
        
        <script>
            document.querySelectorAll('.link-usuario').forEach(link => {
            link.addEventListener('click', function(e) {
                e.preventDefault();
                // Quitar active de todos los links y bloques
                document.querySelectorAll('.link-usuario').forEach(l => l.classList.remove('active'));
                document.querySelectorAll('.content-block').forEach(b => b.classList.remove('active'));
                // Activar el link actual y su bloque correspondiente
                link.classList.add('active');
                document.getElementById(link.dataset.target).classList.add('active');
            });
            });
        </script>
        
        
        
        
        
        
        <script>
            window.APP_CTX = "<%= request.getContextPath() %>";
        </script>
        <script src = "<%= request.getContextPath() %>/web/js/cabecera.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN" crossorigin="anonymous"></script>

        <mi-pie></mi-pie>

        <script src = "<%= request.getContextPath() %>/web/js/footer.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>

    </body>
</html>