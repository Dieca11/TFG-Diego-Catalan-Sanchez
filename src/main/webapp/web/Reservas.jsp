<%@ page language="java" contentType="text/html; charset=UTF-8" import=" java.util.List,servlets.*" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="css/cabecera-footer.css">
        <link rel="stylesheet" type="text/css" href="css/Reservas.css">

        <title>RESERVAS</title>
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
            String popupMsg = (session != null) ? (String) session.getAttribute("popupMsg") : null;
            if (popupMsg != null) {
                session.removeAttribute("popupMsg"); // para que salga una sola vez
            }
            %>

            <% if (popupMsg != null) { %>
            <div style="background:#fff3cd; border:1px solid #ffeeba; color:#856404; padding:12px; border-radius:8px; margin:12px 0;">
                <%= popupMsg %>
            </div>
            <% } %>


            <%
                AccesoBD con=AccesoBD.getInstance();
                List<MunicipioBD> municipios  = con.obtenerMunicipiosBD();
            %>

        <div class="container">
            <div class="contenedor-cabecera">
                <h1> Reserva tu pista de pádel </h1>
                <p> Selecciona tu municipio, consulta disponibilidad y reserva tu pista en pocos pasos.</p>
            </div>

            <div class="row">
                <% for (MunicipioBD Municipio : municipios){
                    int id=Municipio.getId();
                    String municipio=Municipio.getMunicipio();
                    String imagen=Municipio.getImagen();
                    String map_iframe=Municipio.getMap_iframe();
                    int num_pistas=Municipio.getNum_pistas();
                    boolean activo=Municipio.isActivo();
			    %>
                <% if (activo == true) { %>
                <div class="col-lg-4 col-md-6 col-sm-12 mb-4 ">
                    <div class="contenedor-municipio">
                        <div class="contenedor-municipio-img">
                            <img class="municipio-img" src=" <%=imagen%> " alt="<%=municipio%>">
                            <span class="municipio-etiqueta">
                                <%= num_pistas %> Pistas disponibles
                            </span>
                        </div>

                        <div class="municipio-detalle">
                            <label class="municipio-nombre"><%=municipio%></label>
                            <a class="boton-reservar" href="<%= request.getContextPath() %>/Reserva?id=<%= Municipio.getId() %>">
                                RESERVAR
                            </a>
                        </div>
                    </div>
                </div>
           <% } %>
           <% } %>
            </div>
        </div>

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