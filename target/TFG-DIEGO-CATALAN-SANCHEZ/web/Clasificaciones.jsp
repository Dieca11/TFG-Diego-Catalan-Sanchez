<%@ page language="java" contentType="text/html; charset=UTF-8" import=" java.util.List, servlets.*" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="css/cabecera-footer.css">
        <link rel="stylesheet" type="text/css" href="css/Clasificacion.css">


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
        <div class="container gx-0 cuerpo">

            <div class="btn-group dropend">
                <button type="button" class=" dropdown-btn btn-secondary dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                    GENERAL
                </button>
                    <ul class="dropdown-menu">
                        <li data-value="General">GENERAL</li>
                        <li><hr class="dropdown-divider"></li>
                        <li data-value="municipio1">MORA DE RUBIELOS</li>
                        <li><hr class="dropdown-divider"></li>
                        <li data-value="municipio2">Municipio 3</li>
                    </ul>
            </div>
            <div class="tabla-scroll">
                <table >
                    <thead>
                        <tr>
                            <th>POSICION</th>
                            <th>NOMBRE DE USUARIO</th>
                            <th>LUGAR</th>
                            <th>PARTIDOS JUGADOS </th>
                            <th>PARTIDOS GANADOS</th>
                            <th>ULTIMO RESULTADO </th>
                            <th>%VICTORIAS</th>
                        </tr>
                    </thead>
                    
                    <tbody>
                        <tr>
                            <td>1</td>
                            <td>Dieca11</td>
                            <td>MORA DE RUBIELOS</td>
                            <td>10</td>
                            <td>9</td>
                            <td>6-0/3-6/6-2</td>
                            <td>90%</td>
                        </tr>

                        <tr>
                            <td>2</td>
                            <td>Dieca11</td>
                            <td>VALBONA</td>
                            <td>10</td>
                            <td>9</td>
                            <td>6-0/3-6/6-2</td>
                            <td>90%</td>
                        </tr>
                        <tr>
                            <td>2</td>
                            <td>Dieca11</td>
                            <td>VALBONA</td>
                            <td>10</td>
                            <td>9</td>
                            <td>6-0/3-6/6-2</td>
                            <td>90%</td>
                        </tr>
                        <tr>
                            <td>2</td>
                            <td>Dieca11</td>
                            <td>VALBONA</td>
                            <td>10</td>
                            <td>9</td>
                            <td>6-0/3-6/6-2</td>
                            <td>90%</td>
                        </tr>
                        <tr>
                            <td>2</td>
                            <td>Dieca11</td>
                            <td>VALBONA</td>
                            <td>10</td>
                            <td>9</td>
                            <td>6-0/3-6/6-2</td>
                            <td>90%</td>
                        </tr>
                        <tr>
                            <td>2</td>
                            <td>Dieca11</td>
                            <td>VALBONA</td>
                            <td>10</td>
                            <td>9</td>
                            <td>6-0/3-6/6-2</td>
                            <td>90%</td>
                        </tr>
                        <tr>
                            <td>2</td>
                            <td>Dieca11</td>
                            <td>VALBONA</td>
                            <td>10</td>
                            <td>9</td>
                            <td>6-0/3-6/6-2</td>
                            <td>90%</td>
                        </tr>
                        
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
        
        
        <script src = js/cabecera.js></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN" crossorigin="anonymous"></script>

        <mi-pie></mi-pie>

        <script src = js/footer.js></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>

    </body>
</html>