<%@ page language="java" contentType="text/html; charset=UTF-8" import=" java.util.*,servlets.*" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/web/css/cabecera-footer.css">
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/web/css/Perfil.css">

        <title>PERFIL</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" 
        integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
            integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css">
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
            integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">

            <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">

            <link rel="stylesheet" href="https://unpkg.com/@coreui/icons@3.0.0/css/coreui-icons.min.css" />


        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
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

        <%
        ArrayList<PartidaPerfilBD> pendientes =
            (ArrayList<PartidaPerfilBD>) session.getAttribute("pendientesPerfil");

        ArrayList<PartidaPerfilBD> historial =
            (ArrayList<PartidaPerfilBD>) session.getAttribute("historialPerfil");

        if (pendientes == null) pendientes = new ArrayList<>();
        if (historial == null) historial = new ArrayList<>();
        %>
        <div class="container-perfil">
            
            <div class="perfil-principal">
                <img src="<%= request.getContextPath() %>/<%= usuario.getFoto_perfil() %>" alt=" Foto de perfil del Usuario">
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
                </ul>

                <div class="opciones-configuracion-editar">
                    <form action="<%=request.getContextPath()%>/Perfil" method="post">

                        <input type="hidden" name="accion" value="actualizarPerfil">

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

                            <div class=" form-usuario">
                                <input type="submit" value="Cambiar Datos" class="btn">
                            </div>
                        </div>

                        <div class="content-block" id="contenido2">
                            <div class="partidas-tabla">
                                <div class="partidas-lista">
                                    <h3 class="titulo-partidas"> PENDIENTES</h3>

                                    <% if (pendientes.isEmpty()) { %>
                                        <p> No hay partidas Pendientes</p>
                                    <% } else { %>
                                            <% for (PartidaPerfilBD p : pendientes) { 

                                            String participantesP = "";
                                            for (int i = 0; i < p.getParticipantes().size(); i++) {
                                                ParticipantePerfilBD participante = p.getParticipantes().get(i);
                                                if (i > 0) participantesP += "|";
                                                participantesP += participante.getId() + "##" + participante.getNombre();
                                            }
                                            %>

                                        <div class="partidas-card" id="colpendientes" data-participantes="<%= participantesP %>">
                                            <div class="partida-head">
                                                <%
                                                    java.text.SimpleDateFormat formato =
                                                        new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm");

                                                    String fecha = formato.format(p.getFechaHora());
                                                    %>
                                                <div class="partida-fecha"><%= fecha %></div>
                                                <div class="partida-estado"><%= p.getEstado() %></div>
                                            </div>

                                            <div class="partida-info">
                                                <div class="partida-lugar"> <%= p.getMunicipio() %></div>
                                                <div class="partida-pista">Pista: <%= p.getNumeroPista() %></div>
                                            </div>

                                            <div class="partida-actions">
                                                <button type="button" class="btn-partida" onclick="abrirParticipantes(this)"> Participantes</button>
                                                <% if (p.isCreador()) { %>
                                                        <button type="button" class="btn-cancelar" data-id="<%= p.getId() %>" onclick="cancelarPartida(this.dataset.id)">
                                                            Cancelar
                                                        </button>
                                                <% } %>
                                            </div>
                                        </div>
                                        <% } %>
                                    <% } %>
                                </div>

                                <div class="partidas-lista">
                                    <h3 class="titulo-partidas"> HISTORIAL</h3>

                                    <% if (historial.isEmpty()) { %>
                                        <p> No has jugado partidas todavia</p>
                                    <% } else { %>
                                        <% for ( PartidaPerfilBD h : historial) { %>
                                            <%
                                                java.text.SimpleDateFormat formato =
                                                new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm");
                                                String fecha = formato.format(h.getFechaHora());
                                            %>
                                            <%
                                                String participantesH = "";
                                                for (int i = 0; i < h.getParticipantes().size(); i++) {
                                                    ParticipantePerfilBD participante = h.getParticipantes().get(i);
                                                    if (i > 0) participantesH += "|";
                                                    participantesH += participante.getId() + "##" + participante.getNombre();
                                                }
                                                %>
                                            <div class="partidas-card" id="colHistorial" data-fecha="<%=fecha%>"
                                                                                        data-lugar="<%=h.getMunicipio()%>"
                                                                                        data-pista="<%=h.getNumeroPista()%>"
                                                                                        data-participantes="<%= participantesH%>"
                                                                                        data-resultado="<%= h.getResultado()%>"
                                                                                        data-ganador1="<%= h.getGanador1_id()%>"
                                                                                        data-ganador2="<%= h.getGanador2_id()%>"
                                                                                        >
                                                <div class="partida-head">

                                                    <div class="partida-fecha"><%= fecha %></div>
                                                    <div class="partida-estado"><%= h.getEstado() %></div>
                                                </div>
                                                <div class="partida-info">
                                                    <%
                                                        String resultado = h.getResultado();
                                                        if (resultado == null || resultado.isBlank()) {
                                                            resultado = "Resultado no disponible todavía";
                                                        }
                                                    %>
                                                    <div class="partida-resultado"> Resultado: <%= resultado %> </div>
                                                </div>
                                                <div class="partida-actions">
                                                    <button type="button" class="btn-partida" onclick="abrirDetalles(this)">Detalles</button>
                                                    <%
                                                        boolean puedeAnotar = h.isCreador() && h.getParticipantes() != null && h.getParticipantes().size() == 4;
                                                    %>
                                                    <% if (puedeAnotar==true && h.getResultado()==null) { %>
                                                        <button type="button" class="btn-resultado" data-id="<%= h.getId() %>" data-participantes="<%= participantesH %>"
                                                                onclick="abrirResultado(this)"> 
                                                            Anotar Resultado
                                                        </button> 
                                                    <% } %>
                                                </div>
                                            </div>
                                        <% } %>
                                    <%} %>
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
                    </div>
                </form>
            </div>
        </div>

        <form id="formCancelarPartida" method="post" action="<%= request.getContextPath() %>/Perfil" style="display:none;">
            <input type="hidden" name="accion" value="cancelarPartida">
            <input type="hidden" name="idReserva" id="idReservaCancelar">
        </form>

        <script>
            function cancelarPartida(idReserva) {
                if (!confirm("¿Seguro que quieres cancelar esta partida?")) {
                    return;
                }

                document.getElementById("idReservaCancelar").value = idReserva;
                document.getElementById("formCancelarPartida").submit();
            }
        </script>

        <div id="overlayPartida" class="overlay-partida" style="display:none;">
            <div class="overlay-contenido">
                <div class="overlay-cabecera">
                    <h3 id="overlayTitulo">Detalles</h3>
                    <button type="button" class="overlay-cerrar" onclick="cerrarOverlay()">×</button>
                </div>

                <div id="overlayBody" class="overlay-body">
                </div>
            </div>
        </div>

        <div id="overlayResultado" class="overlay-partida" style="display:none;">
            <div class="overlay-contenido">
                <div class="overlay-cabecera">
                    <h3 id="overlayResultadoTitulo">Anotar resultado</h3>
                    <button type="button" class="overlay-cerrar" onclick="cerrarOverlayResultado()">×</button>
                </div>

                <div class="overlay-body">
                    <form id="formResultadoPartida" method="post" action="<%= request.getContextPath() %>/Perfil" onsubmit="return validarFormularioResultado()">
                        <input type="hidden" name="accion" value="anotarResultado">
                        <input type="hidden" name="idReserva" id="resultadoIdReserva">

                        <div class="bloque-overlay-resultado">
                            <label for="inputResultado"><b>Resultado: </b></label>
                            <input type="text"
                                id="inputResultado"
                                name="resultado"
                                class="input-resultado"
                                placeholder="Ej: 7-6/6-2"
                                required>
                        </div>

                        <div class="bloque-overlay-resultado">
                            <p><b>Selecciona los 2 ganadores</b></p>
                            <div id="contenedorGanadores"></div>
                        </div>

                        <div class="partida-actions">
                            <button type="submit" class="btn-partida btn-resultado">Guardar resultado</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script>
            function cerrarOverlay() {
                document.getElementById("overlayPartida").style.display = "none";
                document.getElementById("overlayTitulo").textContent = "";
                document.getElementById("overlayBody").innerHTML = "";
            }

            function abrirOverlay(titulo, contenidoHtml) {
                document.getElementById("overlayTitulo").textContent = titulo;
                document.getElementById("overlayBody").innerHTML = contenidoHtml;
                document.getElementById("overlayPartida").style.display = "flex";
            }

            function abrirParticipantes(boton) {
                const card = boton.closest(".partidas-card");
                const participantesTexto = card.dataset.participantes || "";
                const participantes = participantesTexto.split("|").filter(p => p.trim() !== "");

                let html = "";

                if (participantes.length === 0) {
                    html = "<p>No hay participantes disponibles.</p>";
                } else {
                    html = "<ul class='overlay-lista'>";
                    participantes.forEach(function(item, index) {
                        const partes = item.split("##");
                        const idUsuario = partes[0];
                        const nombre = partes[1];

                        html += "<li>Participante "+ (index + 1) + ": " + nombre + "</li>";
                    });
                    html += "</ul>";
                }

                abrirOverlay("Participantes", html);
            }

            function abrirDetalles(boton) {
                const card = boton.closest(".partidas-card");

                const fecha = card.dataset.fecha || "-";
                const lugar = card.dataset.lugar || "-";
                const pista = card.dataset.pista || "-";
                const ganador1 = card.dataset.ganador1 || "";
                const ganador2 = card.dataset.ganador2 || "";
                const resultado = card.dataset.resultado || "Resultado no disponible";
                const participantesTexto = card.dataset.participantes || "";
                const participantes = participantesTexto.split("|").filter(p => p.trim() !== "");

                let html = "";
                html += "<p><b>Fecha:</b> " + fecha + "</p>";
                html += "<p><b>Lugar:</b> " + lugar + "</p>";
                html += "<p><b>Pista:</b> " + pista + "</p>";
                html += "<p><b>Resultado:</b> " + resultado + "</p>";
                html += "<p><b>Participantes:</b></p>";

                if (participantes.length === 0) {
                    html += "<p>No hay participantes disponibles.</p>";
                } else {
                    html += "<ul class='overlay-lista'>";
                    participantes.forEach(function(item, index) {

                        const partes = item.split("##");
                        const idUsuario = partes[0];
                        const nombre = partes[1];
                        
                        const esGanador = (idUsuario=== ganador1 || idUsuario===ganador2)
                        html += "<li>Participante "+ (index + 1) + ": " + nombre ;

                        if(esGanador){
                            html += " <i class='bi bi-trophy-fill icono-ganador'> </i>";
                        }
                        html += " <li>";
                    });
                    html += "</ul>";
                }

                abrirOverlay("Detalles de la partida", html);
            }

            window.addEventListener("click", function(event) {
                const overlay = document.getElementById("overlayPartida");
                if (event.target === overlay) {
                    cerrarOverlay();
                }
            });
        </script>

        <script>
            function abrirResultado(boton) {
                const idReserva = boton.dataset.id;
                const participantesTexto = boton.dataset.participantes || "";
                const participantes = participantesTexto
                    .split("|")
                    .map(function(p) { return p.trim(); })
                    .filter(function(p) { return p !== ""; });

                document.getElementById("resultadoIdReserva").value = idReserva;

                const contenedor = document.getElementById("contenedorGanadores");
                contenedor.innerHTML = "";

                participantes.forEach(function(item, index) {

                    const partes = item.split("##");
                    const idUsuario = partes[0];
                    const nombre = partes[1];

                    contenedor.innerHTML +=
                        "<div class='fila-ganador'>" +
                            "<input type='checkbox' name='ganadores' value='" + idUsuario + "'>" +
                            "<span>Participante " + (index + 1) + ": " + nombre + "</span>" +
                        "</div>";
                });

                document.getElementById("overlayResultado").style.display = "flex";
            }

            function cerrarOverlayResultado() {
                document.getElementById("overlayResultado").style.display = "none";
                document.getElementById("resultadoIdReserva").value = "";
                document.getElementById("inputResultado").value = "";
                document.getElementById("contenedorGanadores").innerHTML = "";
            }
        </script>

        <script>
            function validarFormularioResultado() {
                const resultado = document.getElementById("inputResultado").value.trim();
                const ganadores = document.querySelectorAll("input[name='ganadores']:checked");

                if (ganadores.length !== 2) {
                    alert("Debes seleccionar exactamente 2 ganadores.");
                    return false;
                }

                if (!esResultadoPadelValido(resultado)) {
                    alert("Introduce un resultado válido de pádel. Ejemplo: 7-6/2-6/6-4");
                    return false;
                }

                return true;
            }

            function esResultadoPadelValido(resultado) {
                if (!resultado) return false;

                const sets = resultado.split("/");

                if (sets.length < 1 || sets.length > 3) return false;

                let setsGanadosA = 0;
                let setsGanadosB = 0;

                for (let i = 0; i < sets.length; i++) {

                    const set = sets[i].trim();

                    if (!esSetValido(set)) {
                        return false;
                    }

                    const partes = set.split("-");
                    const a = parseInt(partes[0].trim(), 10);
                    const b = parseInt(partes[1].trim(), 10);

                    if (a > b) {
                        setsGanadosA++;
                    } else {
                        setsGanadosB++;
                    }
                }

                return (setsGanadosA === 2 || setsGanadosB === 2);
            }

            function esSetValido(set) {
                const partes = set.split("-");

                if (partes.length !== 2) return false;

                const a = parseInt(partes[0].trim(), 10);
                const b = parseInt(partes[1].trim(), 10);

                if (isNaN(a) || isNaN(b)) return false;
                if (a === b) return false;
                if (a < 0 || b < 0) return false;
                if (a > 7 || b > 7) return false;

                const max = Math.max(a, b);
                const min = Math.min(a, b);

                if (max === 6) {
                    return min >= 0 && min <= 4;
                }

                if (max === 7) {
                    return min === 5 || min === 6;
                }

                return false;
            }
            </script>


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