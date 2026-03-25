<%@ page language="java" contentType="text/html; charset=UTF-8" import=" java.util.ArrayList, servlets.*" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/web/css/cabecera-footer.css">
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/web/css/Clasificacion.css">


        <title>CLASIFICACION</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" 
        integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
            integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css">
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
            integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">

            <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+Knujsl5+7GDvjz4Et6kcu9teW7RSJoV++Ar5QnFexl3O9b" crossorigin="anonymous">
        <link rel="icon" href="Imagenes/raqueta-de-padel.png" type="image/png" class="logo">
    </head>
    <body>
        <mi-cabecera></mi-cabecera>

        <%
            ArrayList<MunicipioBD> municipios =
                (ArrayList<MunicipioBD>) session.getAttribute("municipiosClasificacion");

            if (municipios == null) municipios = new ArrayList<MunicipioBD>();

            ArrayList<UsuarioClasificacion> clasificacion =
                (ArrayList<UsuarioClasificacion>) session.getAttribute("clasificacionGeneral");

            if (clasificacion == null) clasificacion = new ArrayList<>();

            String filtroClasificacion = (String) session.getAttribute("filtroClasificacion");
            if (filtroClasificacion == null) filtroClasificacion = "GENERAL";
        %>

        <div class="cuerpo">

            <h1> CLASIFICACION - <%= filtroClasificacion %></h1>

            <div class="btn-group dropend">
                <button type="button" class=" dropdown-btn btn-secondary dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                    <%= filtroClasificacion %>
                </button>
                    <ul class="dropdown-menu">
                        <li data-value="GENERAL">
                            <a class="dropdown-item" href="<%= request.getContextPath() %>/Clasificacion">GENERAL</a></li>
                        
                        <% for (MunicipioBD m : municipios) { %>
                            <% if (m.isActivo()) { %>
                            <li><hr class="dropdown-divider"></li>
                            <li>
                                <a class="dropdown-item" href="<%= request.getContextPath() %>/Clasificacion?idMunicipio=<%= m.getId() %>">
                                    <%= m.getMunicipio() %>
                                </a>
                            </li>
                        <% } %>
                        <% } %>
                    </ul>
            </div>

            <div class="tabla-scroll">
                <table >
                    <thead>
                        <tr>
                            <th>POSICION</th>
                            <th>USUARIO</th>
                            <th>PARTIDOS J. </th>
                            <th>PARTIDOS G.</th>
                            <th>%VICTORIAS</th>
                            <th>RACHA ACTUAL</th>
                        </tr>
                    </thead>
                    
                    <tbody>
                        <% if (clasificacion.isEmpty()) { %>
                            <tr>
                                <td colspan="6"> No hay usuarios suficientes para mostrar la clasificacion</td>
                            </tr>
                        <% } else { %>
                            <% for (UsuarioClasificacion u : clasificacion) { %>
                                <tr class="<%= u.getPosicion() == 1 ? "top1" : u.getPosicion() == 2 ? "top2" : u.getPosicion() == 3 ? "top3" : "" %>">
                                    <td><%= u.getPosicion() %></td>
                                    <td><%= u.getNombreUsuario() %></td>
                                    <td><%= u.getPartidasJugadas() %></td>
                                    <td><%= u.getPartidasGanadas() %></td>
                                    <td><%= u.getPorcentajeV() %>%</td>
                                    <td>
                                        <span class="<%= u.getRacha().contains("V") ? "racha-victoria" : "racha-derrota" %>">
                                            <%= u.getRacha() %>
                                        </span>
                                    </td>
                                </tr>
                            <% } %>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
        


        <script>
            const dropdownBtn = document.querySelector('.dropdown-btn');
            const dropdownMenu = document.querySelector('.dropdown-menu');

            // Alternar visibilidad al pulsar el botón
            dropdownBtn.addEventListener('click', (event) => {
                event.stopPropagation(); // evita que se cierre al hacer clic en el botón
                dropdownMenu.classList.toggle('show');
            });

            // Cerrar al hacer clic fuera
            document.addEventListener('click', (event) => {
                if (!dropdownBtn.contains(event.target) && !dropdownMenu.contains(event.target)) {
                    dropdownMenu.classList.remove('show');
                }
            });

            // Cambiar texto y cerrar al elegir opción
            dropdownMenu.querySelectorAll('li[data-value]').forEach(option => {
                option.addEventListener('click', () => {
                    dropdownBtn.textContent = option.textContent;
                    dropdownMenu.classList.remove('show');
                });
            });
        </script>
        
        <script>
            window.APP_CTX = "<%= request.getContextPath() %>";
        </script>
        <script src = js/cabecera.js></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN" crossorigin="anonymous"></script>

        <mi-pie></mi-pie>

        <script src = js/footer.js></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>

    </body>
</html>