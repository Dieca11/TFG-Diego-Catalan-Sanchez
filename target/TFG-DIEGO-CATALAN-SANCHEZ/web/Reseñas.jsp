<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="css/cabecera-footer.css">
        <link rel="stylesheet" type="text/css" href="css/reseñas.css">

        <title>RESEÑAS</title>


        
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css">

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
            integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">

        <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">

        <link rel="icon" href="Imagenes/raqueta-de-padel.png" type="image/png" class="logo">
    </head>
    <body>
        <mi-cabecera></mi-cabecera>

            <div class="container">
                <div class="btn-group dropend">
                    <button type="button" class=" dropdown-btn btn-secondary dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                        GENERAL
                    </button>
                    <ul class="dropdown-menu">
                        <li data-value="General">GENERAL</li>
                        <li><hr class="dropdown-divider"></li>
                        <li data-value="municipio1">MORA DE RUBIELOS</li>
                        <li><hr class="dropdown-divider"></li>
                        <li data-value="municipio2">VALBONA</li>
                    </ul>
                </div>

                <div class="container-cards">
                    <div class="review-card">

                        <div class="user-info">

                            <img src="Imagenes/raqueta-de-padel.png" alt="Foto de perfil" class="profile-pic" />

                            <div class="detalles-total">

                                <div class="user-details">

                                    <h3 class="usuario">Dieca11</h3>
                                    <p class="lugar">Mora de Rubielos</p>
                                </div>

                                <div class="estrellas">
                                    <img src="Imagenes/5 estrellas.png" alt="Cinco Estrellas">
                                </div>
                            </div>
                        </div>
                        <p class="comment">El estado de las pistas es correcto y faciles de encontrar, muy satisfecho con las mismas, ademas se dispone de una fuente para beber agua</p>
                    </div>
            
                    <div class="review-card">

                        <div class="user-info">

                            <img src="Imagenes/raqueta-de-padel.png" alt="Foto de perfil" class="profile-pic" />

                            <div class="detalles-total">

                                <div class="user-details">

                                    <h3 class="usuario">Dieca11</h3>
                                    <p class="lugar">Mora de Rubielos</p>
                                </div>

                                <div class="estrellas">
                                    <img src="Imagenes/5 estrellas.png" alt="Cinco Estrellas">
                                </div>
                            </div>
                        </div>
                        <p class="comment">El estado de las pistas es correcto y faciles de encontrar, muy satisfecho con las mismas, ademas se dispone de una fuente para beber agua</p>
                    </div>
                    <div class="review-card">

                        <div class="user-info">

                            <img src="Imagenes/raqueta-de-padel.png" alt="Foto de perfil" class="profile-pic" />

                            <div class="detalles-total">

                                <div class="user-details">

                                    <h3 class="usuario">Dieca11</h3>
                                    <p class="lugar">Mora de Rubielos</p>
                                </div>

                                <div class="estrellas">
                                    <img src="Imagenes/5 estrellas.png" alt="Cinco Estrellas">
                                </div>
                            </div>
                        </div>
                        <p class="comment">El estado de las pistas es correcto y faciles de encontrar, muy satisfecho con las mismas, ademas se dispone de una fuente para beber agua
                            El estado de las pistas es correcto y faciles de encontrar, muy satisfecho con las mismas, ademas se dispone de una fuente para beber agua
                        </p>
                    </div>
                    <div class="review-card">

                        <div class="user-info">

                            <img src="Imagenes/raqueta-de-padel.png" alt="Foto de perfil" class="profile-pic" />

                            <div class="detalles-total">

                                <div class="user-details">

                                    <h3 class="usuario">Dieca11</h3>
                                    <p class="lugar">Mora de Rubielos</p>
                                </div>

                                <div class="estrellas">
                                    <img src="Imagenes/5 estrellas.png" alt="Cinco Estrellas">
                                </div>
                            </div>
                        </div>
                        <p class="comment">El estado de las pistas es correcto y faciles de encontrar, muy satisfecho con las mismas, ademas se dispone de una fuente para beber agua
                            El estado de las pistas es correcto y faciles de encontrar, muy satisfecho con las mismas, ademas se dispone de una fuente para beber agua
                        </p>
                    </div>
                    <div class="review-card">

                        <div class="user-info">

                            <img src="Imagenes/raqueta-de-padel.png" alt="Foto de perfil" class="profile-pic" />

                            <div class="detalles-total">

                                <div class="user-details">

                                    <h3 class="usuario">Dieca11</h3>
                                    <p class="lugar">Mora de Rubielos</p>
                                </div>

                                <div class="estrellas">
                                    <img src="Imagenes/1 estrella.png" alt="Cinco Estrellas">
                                </div>
                            </div>
                        </div>
                        <p class="comment">El estado de las pistas es correcto y faciles de encontrar, muy satisfecho con las mismas, ademas se dispone de una fuente para beber agua
                            El estado de las pistas es correcto y faciles de encontrar, muy satisfecho con las mismas, ademas se dispone de una fuente para beber agua
                        </p>
                    </div>
                </div>
            </div>
        
        
        <script> 

        const dropdownBtn = document.querySelector('.dropdown-btn');
        const dropdownMenu = document.querySelector('.dropdown-menu');

        // Alternar visibilidad al pulsar el botón
        dropdownBtn.addEventListener('click', (event) => {
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