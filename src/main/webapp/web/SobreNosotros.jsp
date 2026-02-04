<%@ page language="java" contentType="text/html; charset=UTF-8" import=" java.util.List,servlets.*" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link rel="stylesheet" type="text/css" href="css/cabecera-footer.css">
        <link rel="stylesheet" type="text/css" href="css/SobreNosotros.css">

        <title>SOBRE NOSOTROS</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" 
        integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
            integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
            integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">

        <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">


        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+Knujsl5+7GDvjz4Et6kcu9teW7RSJoV++Ar5QnFexl3O9b" crossorigin="anonymous">
        <link rel="icon" href="Imagenes/raqueta-de-padel.png" type="image/png">
    </head>
    <body>
        <mi-cabecera></mi-cabecera>

        <h1 class="titulo-sobrenosotros">SOBRE NOSOTROS</h1>
        
        <div class="row mx-sm-3 mx-md-3 mx-lg-3 mx-xl-5">
            <div class="col">
                <P>Soy Diego Catalán Sánchez estudiante de Ingenieria Informática de la Universidad de Valencia y creador de esta aplicación web, tengo 22 años y las aplicaciones web es aquello
                    a lo que me querría dedicar en el mundo laboral.
                </P>
                <p> Esta web ha sido diseñada para la realizazión del Trabajo Final de Grado de Ingenieria Informática de la Universidad de Valencia, por el momento el fin es unicamente 
                    aprobar con buena nota el mismo y si es posible mediante algun estudio de mercado, sacarla a internet y que aquellos municipios que no tengan un sistema con el que controlar y
                    regular el uso de las pistas de padel cuente con mis servicios para poder ser usada.
                </p>
                <p>La finalidad de esta aplicación web es ofrecer un control a los municipios para saber como y cuando se usan las pistas, pudiendo ofrecer a los usuarios expresarse sobre el estado 
                    de las mismas y facilidades que podria adoptar el ayuntamiento o de quien este a cargo de su mantenimiento y restauración. Además se ofrece una experiencia competitiva entre usuarios 
                    del mismo municipio y de otros para saber quien lidera el ranking como mejores jugadores. </p>

                <p>Prestamos servicio de reservas, clasificaciones, invitaciones y reseñas para que mediante la aplicacion web se registren partidas, resultados e incidencias.</p>
                
                <p>Como nueva aplicacion web estamos a disposición de los ayuntamientos y demás clubes de pádel que quieran requerir de nuestros servicios. Tanto de usurios como de futuros clientes
                    queremos saber si es posible mejorar la accesibilidad a las distintas caracteristicas de la misma.
                </p>

                <p> Si quieres que tu municipio cuente con nuestros servicios, contacta a este correo reservapistasdepadel@gmail.com</p>
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