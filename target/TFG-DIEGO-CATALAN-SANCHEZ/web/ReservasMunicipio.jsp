<%@ page language="java" contentType="text/html; charset=UTF-8" import=" java.util.List,servlets.*" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="css/cabecera-footer.css">
        <link rel="stylesheet" type="text/css" href="css/ReservasMunicipio.css ">

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
        <link rel="icon" href="Imagenes/raqueta-de-padel.png" type="image/png" class="logo">
    </head>
    <body>
        <mi-cabecera></mi-cabecera>

        <%
            int idMunicipio = Integer.parseInt(request.getParameter("id"));

            AccesoBD con=AccesoBD.getInstance();
            MunicipioBD municipio  = con.obtenerMunicipioBD(idMunicipio);

            String nombre = municipio.getMunicipio();
            int pistas = municipio.getNum_pistas();
            String mapIframe = municipio.getMap_iframe();

        %>
        <div class="container">

            <div class="row mx-sm-3 mx-md-3 mx-lg-3 mx-xl-5">

                <div class="col contenedor-pista">
                    <img src="./Imagenes/padel.png" alt="Pista de Padel" width="600px">
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

                        <div class="btn-group2">
                            <button class="btn btn-secondary btn-sm dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                                PISTA 1
                            </button>
                            <ul class="dropdown-menu">
                                <% 
                                for (int i= 1; i<=pistas; i++){
                                %>
                                    <li>
                                        Pista <%= i %>
                                    </li>
                                <% 
                                } 
                                %>

                            </ul>
                            </div>

                        <label for="horarios">SELECCIONA UNA FRANJA HORARIA:</label>

                        <label for="fecha">Día</label>
                        <input type="date" id="fecha" name="fecha" required>
                        
                        <div class="btn-group2 btn2">
                            <button class="btn btn-secondary btn-sm dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                                08:00-09:30
                            </button>
                            <ul class="dropdown-menu dropmenu2">
                                <li>08:00-09:30</li>
                                <li>09:30-11:00</li>
                                <li>11:00-12:30</li>
                                <li>12:30-14:00</li>
                                <li>14:00-15:30</li>
                            </ul>
                        </div>
                        <div class="botones">
                            <button class="btn-secondary">Invitaciones</button>
                            <button class="btn-secondary">Reservar</button>
                        </div>
                        <iframe src="<%= mapIframe %>" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
                    </div>
                </div>
            </div>
        </div>
        
        
        
        
        
        <script>
            const dropdownBtn = document.querySelector('.btn-group2');
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
            const dropdownBtn2 = document.querySelector('.btn2');
            const dropdownMenu2 = document.querySelector('.dropmenu2');

            // Alternar visibilidad al pulsar el botón
            dropdownBtn2.addEventListener('click', (event) => {
                event.stopPropagation(); // evita que se cierre al hacer clic en el botón
                dropdownMenu2.classList.toggle('show');
            });

            // Cerrar al hacer clic fuera
            document.addEventListener('click', (event) => {
                if (!dropdownBtn2.contains(event.target) && !dropdownMenu2.contains(event.target)) {
                    dropdownMenu2.classList.remove('show');
                }
            });

            // Cambiar texto y cerrar al elegir opción
            dropdownMenu2.querySelectorAll('li[data-value]').forEach(option => {
                option.addEventListener('click', () => {
                    dropdownBtn2.textContent = option.textContent;
                    dropdownMenu2.classList.remove('show');
                });
            });
        </script>
        
        
        
        <script src = js/cabecera.js></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN" crossorigin="anonymous"></script>

        <mi-pie></mi-pie>

        <script src = js/footer.js></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>

        <script src="https://maps.googleapis.com/maps/api/js?key=TU_CLAVE_API"></script>


    </body>
</html>