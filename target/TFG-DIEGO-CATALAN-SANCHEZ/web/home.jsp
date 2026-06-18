<%@ page language="java" contentType="text/html; charset=UTF-8" import="servlets.*" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="css/cabecera-footer.css">
        <link rel="stylesheet" type="text/css" href="css/Home.css">

        <title>HOME</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" 
            integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
            integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
            integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">

        <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+Knujsl5+7GDvjz4Et6kcu9teW7RSJoV++Ar5QnFexl3O9b" crossorigin="anonymous">
        <link rel="icon" href="./Imagenes/raqueta-de-padel.png" type="image/png" class="logo">
        <mi-cabecera></mi-cabecera>
    </head>

    <body>
        
        <section class="home-hero">

            <div class="home-hero-overlay"></div>

            <div class="home-hero-contenido">

                <div class="home-hero-texto">
                    <span class="home-hero-etiqueta">
                        <i class="bi bi-geo-alt-fill"></i>
                        Pistas municipales cerca de ti
                    </span>

                    <h1>Reserva tu pista de pádel en pocos segundos</h1>

                    <p>
                        Consulta la disponibilidad de pistas, crea una reserva o únete a una partida
                        ya abierta en tu municipio de forma rápida y sencilla.
                    </p>

                    <div class="home-hero-botones">
                        <a href="Reservas.jsp" class="btn-hero-principal" aria-label="Reservas">
                            Reservar ahora <i class="bi bi-arrow-right"></i>
                        </a>
                    </div>

                    <div class="home-hero-datos">
                        <div>
                            <strong>+ Fácil</strong>
                            <span>Reserva online</span>
                        </div>

                        <div>
                            <strong>+ Social</strong>
                            <span>Únete a partidas</span>
                        </div>

                        <div>
                            <strong>+ Municipal</strong>
                            <span>Gestión para pueblos</span>
                        </div>
                    </div>
                </div>

                <div class="home-hero-card">
                    <h2>Organiza tu próxima partida</h2>

                    <div class="hero-card-item">
                        <i class="bi bi-calendar-check"></i>
                        <div>
                            <h4>Elige pista y horario</h4>
                            <p>Consulta los turnos disponibles en cada municipio.</p>
                        </div>
                    </div>

                    <div class="hero-card-item">
                        <i class="bi bi-people"></i>
                        <div>
                            <h4>Reserva o únete</h4>
                            <p>Crea una partida nueva o completa una ya existente.</p>
                        </div>
                    </div>

                    <div class="hero-card-item">
                        <i class="bi bi-trophy"></i>
                        <div>
                            <h4>Consulta tu progreso</h4>
                            <p>Revisa resultados, historial y clasificación.</p>
                        </div>
                    </div>
                </div>

            </div>

        </section>

            <div class="home-contenido">

                <section class="home-panel-principal">

                    <div class="home-accesos">
                        <article class="home-card">
                            <div class="home-card-icono">
                                <i class="bi bi-calendar-check"></i>
                            </div>
                            <div>
                                <h3>Reservas</h3>
                                <p>Consulta pistas disponibles y reserva tu horario favorito en pocos clics.</p>
                                <a href="Reservas.jsp">Reservar ahora <i class="bi bi-arrow-right"></i></a>
                            </div>
                        </article>

                        <article class="home-card">
                            <div class="home-card-icono">
                                <i class="bi bi-trophy"></i>
                            </div>
                            <div>
                                <h3>Clasificación</h3>
                                <p>Consulta el ranking de jugadores y compite con la comunidad.</p>
                                <a href="Clasificaciones.jsp">Ver clasificación <i class="bi bi-arrow-right"></i></a>
                            </div>
                        </article>

                        <article class="home-card">
                            <div class="home-card-icono">
                                <i class="bi bi-geo-alt"></i>
                            </div>
                            <div>
                                <h3>Municipios</h3>
                                <p>Encuentra pistas municipales disponibles cerca de ti.</p>
                                <a href="Reservas.jsp">Ver municipios <i class="bi bi-arrow-right"></i></a>
                            </div>
                        </article>

                        <article class="home-card">
                            <div class="home-card-icono">
                                <i class="bi bi-chat-square-text"></i>
                            </div>
                            <div>
                                <h3>Reseñas</h3>
                                <p>Lee y comparte opiniones sobre pistas y jugadores.</p>
                                <a href="Reseñas.jsp">Ver reseñas <i class="bi bi-arrow-right"></i></a>
                            </div>
                        </article>
                    </div>

                    <div class="home-como-funciona">
                        <h2>¿Cómo funciona?</h2>

                        <div class="paso">
                            <span class="paso-numero">1</span>
                            <div class="paso-icono">
                                <i class="bi bi-geo-alt"></i>
                            </div>
                            <div>
                                <h4>Elegir municipio</h4>
                                <p>Selecciona el municipio donde quieres jugar.</p>
                            </div>
                        </div>

                        <div class="paso">
                            <span class="paso-numero">2</span>
                            <div class="paso-icono">
                                <i class="bi bi-calendar-event"></i>
                            </div>
                            <div>
                                <h4>Seleccionar pista y hora</h4>
                                <p>Consulta la disponibilidad y elige el horario que prefieras.</p>
                            </div>
                        </div>

                        <div class="paso">
                            <span class="paso-numero">3</span>
                            <div class="paso-icono">
                                <i class="bi bi-people"></i>
                            </div>
                            <div>
                                <h4>Reservar o unirse</h4>
                                <p>Crea una nueva reserva o únete a una partida abierta.</p>
                            </div>
                        </div>

                        <div class="paso">
                            <span class="paso-numero">4</span>
                            <div class="paso-icono">
                                <i class="bi bi-graph-up-arrow"></i>
                            </div>
                            <div>
                                <h4>Jugar y consultar resultados</h4>
                                <p>Disfruta del partido y revisa tus resultados y clasificación.</p>
                            </div>
                        </div>
                    </div>

                </section>

                <section class="home-ventajas">
                    <h2>Ventajas de jugar con nosotros</h2>

                    <div class="ventajas-contenedor">
                        <article class="ventaja">
                            <i class="bi bi-lightning-charge"></i>
                            <div>
                                <h4>Reservas rápidas</h4>
                                <p>Reserva tu pista en menos de un minuto.</p>
                            </div>
                        </article>

                        <article class="ventaja">
                            <i class="bi bi-people-fill"></i>
                            <div>
                                <h4>Partidas abiertas</h4>
                                <p>Únete a partidas y conoce nuevos jugadores.</p>
                            </div>
                        </article>

                        <article class="ventaja">
                            <i class="bi bi-gear"></i>
                            <div>
                                <h4>Gestión sencilla</h4>
                                <p>Gestiona reservas, perfil e historial desde tu cuenta.</p>
                            </div>
                        </article>

                        <article class="ventaja">
                            <i class="bi bi-phone"></i>
                            <div>
                                <h4>Multidispositivo</h4>
                                <p>Accede desde móvil, tablet u ordenador.</p>
                            </div>
                        </article>
                    </div>
                </section>

                <section class="home-cta-municipios">
                    <div class="cta-texto">
                        <h2>¿Tu municipio aún no tiene gestión online de pistas?</h2>
                        <p>
                            Si en tu municipio todavía no existe un sistema para gestionar reservas y partidas,
                            ponte en contacto con nosotros y te ayudaremos a digitalizarlo.
                        </p>
                    </div>

                    <div class="cta-botones">
                        <a href="SobreNosotros.jsp" class="btn-cta-principal">
                            Contacta con nosotros <i class="bi bi-arrow-right"></i>
                        </a>

                        <a href="Reservas.jsp" class="btn-cta-secundario">
                            Propón tu municipio
                        </a>
                    </div>
                </section>

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