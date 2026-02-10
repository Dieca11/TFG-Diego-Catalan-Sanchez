<%@ page language="java" contentType="text/html; charset=UTF-8" import=" java.util.List,java.util.ArrayList, servlets.*" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/web/css/cabecera-footer.css">
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/web/css/ReservasMunicipio.css ">

        <title>RESERVAS MUNICIPIO</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" 
        integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
            integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">

            <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css">
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
            integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">


        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+Knujsl5+7GDvjz4Et6kcu9teW7RSJoV++Ar5QnFexl3O9b" crossorigin="anonymous">
        <link rel="icon" href="<%= request.getContextPath() %>/web/Imagenes/raqueta-de-padel.png" type="image/png" class="logo">
    </head>
    <body>
        <mi-cabecera></mi-cabecera>

        <%
        String msg = (String) request.getAttribute("popupMsg");
        %>

        <% if (msg != null) { %>
        <div id="toastMsg"
            style="position:fixed; top:16px; left:50%; transform:translateX(-50%);
                    z-index:999999; background:#fff3cd; border:1px solid #ffeeba;
                    color:#856404; padding:12px 16px; border-radius:10px;
                    box-shadow:0 6px 18px rgba(0,0,0,.15); max-width:90%;
                    font-weight:600;">
            <%= msg %>
        </div>

        <script>
            // se autocierra a los 3s (opcional)
            setTimeout(function(){
            var t = document.getElementById("toastMsg");
            if (t) t.remove();
            }, 3000);
        </script>
        <% } %>



        <%
            AccesoBD con=AccesoBD.getInstance();
            MunicipioBD municipio = (MunicipioBD) request.getAttribute("municipio");

            if (municipio == null) {
                // Si alguien entra directo a la JSP sin pasar por el servlet
                response.sendRedirect(request.getContextPath() + "/web/Reservas.jsp");
                return;
            }
            String nombre = municipio.getMunicipio();
            int pistas = municipio.getNum_pistas();
            String mapIframe = municipio.getMap_iframe();

            ArrayList<AccesoBD.UsuarioVista> invitables =
            (ArrayList<AccesoBD.UsuarioVista>) request.getAttribute("usuariosInvitables");

            if (invitables == null) invitables = new ArrayList<>();

        %>
        <div class="container">

            <div class="row mx-sm-3 mx-md-3 mx-lg-3 mx-xl-5">

                <div class="col contenedor-pista">
                    <img class="pista-img" src="<%= request.getContextPath() %>/web/Imagenes/padel.png" alt="Pista de Padel" width="600px">
                      <div class="usuario-cuadrante" data-posicion="1">
                        <img class="usuario-foto" src="usuarios/juan.jpg" alt="Juan">
                        <div class="usuario-nombre">Juan Pérez</div>
                    </div>

                    <!-- Cuadrante 2 - Jugador 2 -->
                    <div class="usuario-cuadrante" data-posicion="2">
                        <img class="usuario-foto" src="usuarios/maria.jpg" alt="María">
                        <div class="usuario-nombre">María López</div>
                    </div>

                    <!-- Cuadrante 3 - Jugador 3 -->
                    <div class="usuario-cuadrante" data-posicion="3">
                        
                        <img class="usuario-foto" src="usuarios/pedro.jpg" alt="Pedro">
                        <div class="usuario-nombre">Pedro García</div>
                    </div>

                    <!-- Cuadrante 4 - Jugador 4 -->
                    <div class="usuario-cuadrante" data-posicion="4">
                        <!-- Vacío si no hay usuario -->
                    </div>
                </div>



                <div class="col">

                    <div class="contenedor-derecha">
                        <h1><%=nombre%></h1>
                        <label for="pista">SELECCIONA UNA PISTA:</label>

                        <input type="hidden" id="numeroPista" name="numero_pista" value="1">

                        <div class="btn-group2" id="pistaGroup">
                            <button class="btn btn-secondary" id="pistaBtn" type="button">PISTA 1</button>

                            <ul class="dropdown-menu" id="pistaMenu">
                            <% for (int i = 1; i <= pistas; i++) { %>
                                <li>
                                <a class="dropdown-item pista-item" href="#" data-pista="<%= i %>">PISTA <%= i %></a>
                                </li>
                            <% } %>
                            </ul>
                        </div>


                        <label for="horarios">SELECCIONA UNA FRANJA HORARIA:</label>
                        
                        <!-- Valores reales para enviar / usar en AJAX -->
                        <input type="hidden" id="fecha" name="fecha" value="">
                        <input type="hidden" id="franjaInicio" name="franja" value="">
                        <input type="hidden" id="municipioId" name="municipio_id" value="<%= municipio.getId() %>">

                        <div class="selector-reserva">
                            <!-- Dropdown de día -->
                            <div class="btn-group2 btn2" id="ddDia">
                                <button class="btn btn-secondary" id="ddDiaBtn" type="button">
                                Selecciona día
                                </button>
                                <ul class="dropdown-menu dropmenu2" id="ddDiaMenu"></ul>
                            </div>

                            <!-- Dropdown de franja -->
                            <div class="btn-group2 btn2" id="ddHora">
                                <button class="btn btn-secondary" id="ddHoraBtn" type="button">
                                Selecciona franja
                                </button>
                                <ul class="dropdown-menu dropmenu2" id="ddHoraMenu"></ul>
                            </div>
                        </div>

                        <%
                            ArrayList<Integer> invitadosSel = (ArrayList<Integer>) session.getAttribute("invitadosReserva");
                            if (invitadosSel == null) invitadosSel = new ArrayList<>();
                            %>

                            <div class="mt-2">
                                <strong>Invitados:</strong>

                                <% if (invitadosSel.isEmpty()) { %>
                                    <span>Ninguno</span>
                                <% } else { %>
                                    <% for (Integer invId : invitadosSel) { 
                                        String nombreInv = con.obtenerNombreUsuarioPorId(invId);
                                    %>
                                    <form method="post" action="<%= request.getContextPath() %>/Reserva" style="display:inline;"
                                            onsubmit="
                                            this.numero_pista.value = document.getElementById('numeroPista').value;
                                            this.fecha.value = document.getElementById('fecha').value;
                                            this.franja.value = document.getElementById('franjaInicio').value;
                                            ">
                                            
                                        <input type="hidden" name="action" value="delInv">
                                        <input type="hidden" name="invitado_id" value="<%= invId %>">
                                        <input type="hidden" name="numero_pista" value="">
                                        <input type="hidden" name="fecha" value="">
                                        <input type="hidden" name="franja" value="">
                                        <button type="submit" class="btn btn-sm btn-outline-danger">
                                        Quitar <%= nombreInv %>
                                        </button>
                                    </form>
                                    <% } %>
                                <% } %>
                            </div>


                        <div class="botones">
                            <button id="btnInvitaciones" class="btn-secondary">Invitaciones</button>
                            <form method="post" action="<%= request.getContextPath() %>/Reserva" style="margin:0;"
                                onsubmit="
                                    var p = document.getElementById('numeroPista').value;
                                    var f = document.getElementById('fecha').value;
                                    var h = document.getElementById('franjaInicio').value;
                                    var m = document.getElementById('municipioId').value;

                                    if (!p || !f || !h || !m) { alert('Selecciona pista, día y hora.'); return false; }

                                    this.municipio_id.value = m;
                                    this.numero_pista.value = p;
                                    this.fecha.value = f;
                                    this.franja.value = h;
                                ">
                                <input type="hidden" name="action" value="crearReserva">
                                <input type="hidden" name="municipio_id" value="">
                                <input type="hidden" name="numero_pista" value="">
                                <input type="hidden" name="fecha" value="">
                                <input type="hidden" name="franja" value="">

                                <button type="submit" class="btn-secondary">Reservar</button>
                            </form>


                        </div>
                        <iframe src="<%= mapIframe %>" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
                    </div>

                    <div id="modalInvitaciones" class="modal-overlay" aria-hidden="true">
                        <div class="modal-box">
                            <div class="modal-header">
                                <h3 id="modalInvTitle">Invitaciones</h3>
                                <button type="button" id="btnCerrarInv" class="modal-close" aria-label="Cerrar">✕</button>
                            </div>

                            <div class="modal-body">
                                <input id="buscadorInv" class="modal-search" type="text" placeholder="Buscar usuario...">

                                <div id="listaInv" class="modal-list">

                                    <% if (invitables.isEmpty()) { %>
                                        <div class="modal-item" data-nombre="">
                                            <div class="modal-user">
                                            <span class="modal-username">No hay usuarios disponibles para invitar</span>
                                            </div>
                                        </div>
                                    <% } else { %>

                                        <%
                                            Integer idSesion = (session != null) ? (Integer) session.getAttribute("usuario") : null;
                                            if (idSesion == null) idSesion = -1;
                                        %>
                                        <% for (AccesoBD.UsuarioVista u : invitables) {
                                            String nombreInv= u.getNombreUsuario();
                                            String img = u.getImagenPerfil();
                                            img = request.getContextPath() + "/" + img;
                                            if (img == null || img.trim().isEmpty()) img = "Imagenes/usuario.png"; %>

                                        <div class="modal-item" data-nombre="<%= nombreInv.toLowerCase() %>">
                                            <div class="modal-user">
                                            <img class="modal-avatar" src="<%= img %>" alt="<%= nombreInv %>">
                                            <span class="modal-username"><%= nombreInv %></span>
                                            </div>

                                            <%
                                                boolean esCreador = (u.getId() == idSesion);
                                                boolean yaInvitado = invitadosSel.contains(u.getId());
                                            %>

                                            <% if (esCreador) { %>
                                                <span class="badge-creador">Creador</span>
                                            <% } else if (yaInvitado) { %>
                                                    <span class="badge-invitado">Invitado</span>
                                            <% } else { %>
                                                <form method="post" action="<%= request.getContextPath() %>/Reserva" style="margin:0;"
                                                    onsubmit="
                                                    this.numero_pista.value = document.getElementById('numeroPista').value;
                                                    this.fecha.value = document.getElementById('fecha').value;
                                                    this.franja.value = document.getElementById('franjaInicio').value;
                                                    ">
                                                    <input type="hidden" name="action" value="addInv">
                                                    <input type="hidden" name="invitado_id" value="<%= u.getId() %>">

                                                    <!-- estos se rellenan en onsubmit -->
                                                    <input type="hidden" name="numero_pista" value="">
                                                    <input type="hidden" name="fecha" value="">
                                                    <input type="hidden" name="franja" value="">

                                                    <button type="submit" class="btn btn-success">Invitar</button>
                                                </form>

                                            <% } %>

                                        </div>
                                        <% } %>
                                    <% } %>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>


            <script>
                document.addEventListener("DOMContentLoaded", function(){
                    var pistaBtn = document.getElementById("pistaBtn");
                    var pistaMenu = document.getElementById("pistaMenu");
                    var pistaGroup = document.getElementById("pistaGroup");
                    var numeroPista = document.getElementById("numeroPista");

                    if (!pistaBtn || !pistaMenu || !pistaGroup || !numeroPista) return;

                    // Toggle del menú (si no usas Bootstrap JS)
                    pistaBtn.addEventListener("click", function(e){
                    e.preventDefault();
                    e.stopPropagation();

                    // Cierra otros menús si quieres (ajusta IDs si existen)
                    var otros = document.querySelectorAll("#ddDiaMenu, #ddHoraMenu");
                    otros.forEach(function(m){ m.classList.remove("show"); });

                    pistaMenu.classList.toggle("show");
                    });

                    // Click en una opción
                    pistaMenu.addEventListener("click", function(e){
                    var a = e.target.closest("a.pista-item");
                    if (!a) return;

                    e.preventDefault();
                    e.stopPropagation();

                    var pista = a.getAttribute("data-pista");
                    if (!pista) return;

                    numeroPista.value = pista;
                    pistaBtn.textContent = "PISTA " + pista;

                    // Cierra menú
                    pistaMenu.classList.remove("show");

                    // Recarga si ya hay fecha y franja
                    var fecha = document.getElementById("fecha");
                    var franja = document.getElementById("franjaInicio");
                    if (fecha && franja && fecha.value && franja.value) {
                        if (typeof recargarReserva === "function") recargarReserva();
                    }
                    });

                    // Click fuera cierra
                    document.addEventListener("click", function(){
                    pistaMenu.classList.remove("show");
                    });

                    // Evita que click dentro del menú cierre por el listener global
                    pistaMenu.addEventListener("click", function(e){ e.stopPropagation(); });
                });
        </script>

        <script>

            /* Formato en la que van a salir dias y horas */
            function pad2(n){ 
                return String(n).padStart(2, "0"); }

            function formatISODateLocal(d){
                return d.getFullYear() + "-" + pad2(d.getMonth() + 1) + "-" + pad2(d.getDate());
            }

            function formatEtiquetaDia(d){
                var dias = ["dom","lun","mar","mie","jue","vie","sab"];
                return dias[d.getDay()] + " " + pad2(d.getDate()) + "/" + pad2(d.getMonth() + 1);
            }

            function formatHHMM(h, m){
                return pad2(h) + ":" + pad2(m);
            }

            function addMinutes(h, m, minutes){
                var total = h * 60 + m + minutes;
                return { h: Math.floor(total / 60), m: total % 60 };
            }

            function closeAll(){
                var menus = document.querySelectorAll("#ddDiaMenu, #ddHoraMenu");
                menus.forEach(function(m){ m.classList.remove("show"); });
            }

            function setupToggle(btnId, menuId){
                var btn = document.getElementById(btnId);
                var menu = document.getElementById(menuId);

                btn.addEventListener("click", function(e){
                e.preventDefault();
                e.stopPropagation();

                // Cierra el otro menú
                document.querySelectorAll("#ddDiaMenu, #ddHoraMenu").forEach(function(m){
                    if (m !== menu) m.classList.remove("show");
                });

                // Toggle del actual
                menu.classList.toggle("show");
                });

                menu.addEventListener("click", function(e){
                e.stopPropagation();
                });
            }

            /* Funcion que restringe las fechas que se pueden elegir para reservas*/
            function buildDias(){
                var menu = document.getElementById("ddDiaMenu");
                var btn = document.getElementById("ddDiaBtn");
                menu.innerHTML = "";

                var hoy = new Date();
                hoy.setHours(0,0,0,0);

                var max = new Date(hoy);
                max.setDate(max.getDate() + 7); // 

                for (var d = new Date(hoy); d <= max; d.setDate(d.getDate() + 1)) {
                var iso = formatISODateLocal(d);
                var etiqueta = formatEtiquetaDia(d);

                var li = document.createElement("li");
                var a = document.createElement("a");
                a.className = "dropdown-item";
                a.href = "#";
                a.setAttribute("data-value", iso);
                a.textContent = etiqueta;

                a.addEventListener("click", function(e){
                    e.preventDefault();
                    var val = this.getAttribute("data-value");
                    document.getElementById("fecha").value = val;
                    btn.textContent = this.textContent.trim();
                    closeAll();
                    if (typeof recargarReserva === "function") recargarReserva();
                });

                li.appendChild(a);
                menu.appendChild(li);
                }

                document.getElementById("fecha").value = formatISODateLocal(hoy);
                btn.textContent = formatEtiquetaDia(hoy);
            }

            /* Funcion que produce las horas de las reservas */
            function buildFranjas(){
                var menu = document.getElementById("ddHoraMenu");
                var btn = document.getElementById("ddHoraBtn");
                menu.innerHTML = "";

                var apertura = { h: 8, m: 0 };
                var cierre = { h: 23, m: 0 };
                var dur = 90;

                var cur = { h: apertura.h, m: apertura.m };

                while (true){
                var fin = addMinutes(cur.h, cur.m, dur);

                var finTotal = fin.h * 60 + fin.m;
                var cierreTotal = cierre.h * 60 + cierre.m;
                if (finTotal > cierreTotal) break;

                var inicioStr = formatHHMM(cur.h, cur.m);
                var finStr = formatHHMM(fin.h, fin.m);
                var etiqueta = inicioStr + "-" + finStr;

                var li = document.createElement("li");
                var a = document.createElement("a");
                a.className = "dropdown-item";
                a.href = "#";
                a.setAttribute("data-value", inicioStr);
                a.textContent = etiqueta;

                a.addEventListener("click", function(e){
                    e.preventDefault();
                    var val = this.getAttribute("data-value");
                    document.getElementById("franjaInicio").value = val;
                    btn.textContent = this.textContent.trim();
                    closeAll();
                    if (typeof recargarReserva === "function") recargarReserva();
                });

                li.appendChild(a);
                menu.appendChild(li);

                cur = addMinutes(cur.h, cur.m, dur);
                }

                var first = menu.querySelector("a.dropdown-item");
                if (first) {
                document.getElementById("franjaInicio").value = first.getAttribute("data-value");
                btn.textContent = first.textContent.trim();
                }
            }

            document.addEventListener("click", closeAll);

            document.addEventListener("DOMContentLoaded", function(){
                setupToggle("ddDiaBtn", "ddDiaMenu");
                setupToggle("ddHoraBtn", "ddHoraMenu");

                buildDias();
                buildFranjas();

                if (typeof recargarReserva === "function") recargarReserva();
            });
        </script>



        <script>
            function limpiarCuadrante(c) {
                c.classList.add("empty");
                c.innerHTML = ""; // tu CSS ya pinta placeholder + "Vacío" si está empty
            }

            function pintarCuadrante(c, u) {
                c.classList.remove("empty");
                c.innerHTML =
                '<img class="usuario-foto" src="' + u.fotoUrl + '" alt="' + u.nombre + '">' +
                '<div class="usuario-nombre">' + u.nombre + '</div>';
            }

            function pintarCuadrantesDesdeUsuarios(usuarios) {
                // usuarios en orden: creador, invitado1, invitado2, invitado3
                for (var i = 1; i <= 4; i++) {
                var c = document.querySelector('.usuario-cuadrante[data-posicion="' + i + '"]');
                if (!c) continue;

                var u = (usuarios && usuarios.length >= i) ? usuarios[i - 1] : null;
                if (u) pintarCuadrante(c, u);
                else limpiarCuadrante(c);
                }
            }

            async function recargarReserva() {
                var municipioId = document.getElementById("municipioId").value;
                var numeroPista = document.getElementById("numeroPista").value;
                var fecha = document.getElementById("fecha").value;           // YYYY-MM-DD
                var franja = document.getElementById("franjaInicio").value;   // HH:mm

                if (!municipioId || !numeroPista || !fecha || !franja) return;

                var params = new URLSearchParams({
                municipioId: municipioId,
                numeroPista: numeroPista,
                fecha: fecha,
                franja: franja
                });

                var resp = await fetch("<%= request.getContextPath() %>/reservas/estado?" + params.toString(), {
                headers: { "Accept": "application/json" }
                });

                if (!resp.ok) {
                // si quieres, aquí pintas todo vacío en caso de error
                pintarCuadrantesDesdeUsuarios([]);
                return;
                }

                var data = await resp.json();

                if (data.libre) {
                pintarCuadrantesDesdeUsuarios([]);
                } else {
                // data.usuarios ya viene en orden (creador, invitado1..3)
                pintarCuadrantesDesdeUsuarios(data.usuarios || []);
                }
            }
        </script>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
            var btnOpen = document.getElementById("btnInvitaciones");
            var modal = document.getElementById("modalInvitaciones");
            var btnClose = document.getElementById("btnCerrarInv");

            var buscador = document.getElementById("buscadorInv");
            var lista = document.getElementById("listaInv");

            if (!modal || !btnClose) return;

            function mostrarTodos() {
                if (!lista) return;
                var items = Array.from(lista.querySelectorAll(".modal-item"));
                items.forEach(function (item) {
                item.style.display = "";
                });
            }

            function filtrarLista() {
                if (!buscador || !lista) return;

                var q = buscador.value.trim().toLowerCase();
                var items = Array.from(lista.querySelectorAll(".modal-item"));

                items.forEach(function (item) {
                var nombre = (item.getAttribute("data-nombre") || "").toLowerCase();
                item.style.display = nombre.indexOf(q) !== -1 ? "" : "none";
                });
            }

            function openModal() {
                modal.classList.add("open");
                modal.setAttribute("aria-hidden", "false");

                // Reset buscador al abrir
                if (buscador) {
                buscador.value = "";
                mostrarTodos();
                buscador.focus();
                }
            }

            function closeModal() {
                modal.classList.remove("open");
                modal.setAttribute("aria-hidden", "true");
            }

            // Abrir (si existe botón)
            if (btnOpen) {
                btnOpen.addEventListener("click", function (e) {
                e.preventDefault();
                openModal();
                });
            }

            // Cerrar con X
            btnClose.addEventListener("click", function () {
                closeModal();
            });

            // Cerrar al hacer click fuera del modal-box
            modal.addEventListener("click", function (e) {
                if (e.target === modal) closeModal();
            });

            // Cerrar con ESC
            document.addEventListener("keydown", function (e) {
                if (e.key === "Escape" && modal.classList.contains("open")) closeModal();
            });

            // Filtrado en tiempo real
            if (buscador) {
                buscador.addEventListener("input", filtrarLista);
            }
            });

        </script>

        <script>
            var buscador = document.getElementById("buscadorInv");
            var lista = document.getElementById("listaInv");

            if (buscador && lista) {

            function filtrar() {
                var q = buscador.value.trim().toLowerCase();
                var items = Array.from(lista.querySelectorAll(".modal-item"));

                items.forEach(function(item){
                var nombre = item.getAttribute("data-nombre") || "";
                item.style.display = (nombre.indexOf(q) !== -1) ? "" : "none";
                });
            }

            buscador.addEventListener("input", filtrar);


            buscador.value = "";
            filtrar();
            }

        </script>


        <script>
        window.APP_CTX = "<%= request.getContextPath() %>";
        </script>
        
        <script src="<%= request.getContextPath() %>/web/js/cabecera.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN" crossorigin="anonymous"></script>

        <mi-pie></mi-pie>

        <script src ="<%= request.getContextPath() %>/web/js/footer.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>

        <script src="https://maps.googleapis.com/maps/api/js?key=TU_CLAVE_API"></script>


    </body>
</html>