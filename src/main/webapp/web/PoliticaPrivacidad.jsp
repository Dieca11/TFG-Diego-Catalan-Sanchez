<%@ page language="java" contentType="text/html; charset=UTF-8" import=" java.util.List,servlets.*" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="css/cabecera-footer.css">
        <link rel="stylesheet" type="text/css" href="css/PoliticaPrivacidad.css">

        <title>INICIO SESION</title>
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


        <main class="politica-container">

            <h1>Política de Privacidad y Protección de Datos</h1>

            <p>
                En cumplimiento del Reglamento (UE) 2016/679 del Parlamento Europeo y del Consejo,
                relativo a la protección de las personas físicas en lo que respecta al tratamiento de
                datos personales, y de la Ley Orgánica 3/2018 de Protección de Datos Personales y
                garantía de los derechos digitales, se informa a los usuarios de esta plataforma sobre
                el tratamiento de sus datos personales.
            </p>

            <section>
                <h2>1. Responsable del tratamiento</h2>
                <p>
                    El responsable del tratamiento de los datos personales recogidos a través de esta
                    plataforma es el administrador de la aplicación desarrollada como parte del Trabajo
                    Fin de Grado.
                </p>
            </section>

            <section>
                <h2>2. Datos recopilados</h2>
                <p>
                    La aplicación podrá recopilar los siguientes datos proporcionados por el usuario:
                </p>
                <ul>
                    <li>Nombre de usuario.</li>
                    <li>Correo electrónico.</li>
                    <li>Contraseña almacenada de forma cifrada.</li>
                    <li>Información relacionada con reservas y participación en partidas.</li>
                </ul>
            </section>

            <section>
                <h2>3. Finalidad del tratamiento</h2>
                <p>
                    Los datos personales serán utilizados exclusivamente para las siguientes finalidades:
                </p>
                <ul>
                    <li>Gestionar el registro e inicio de sesión de usuarios.</li>
                    <li>Permitir la creación y gestión de reservas de pistas.</li>
                    <li>Gestionar la participación en partidas.</li>
                    <li>Mostrar información personalizada dentro de la plataforma.</li>
                </ul>
            </section>

            <section>
                <h2>4. Conservación de los datos</h2>
                <p>
                    Los datos serán conservados mientras el usuario mantenga una cuenta activa en la
                    plataforma o mientras sean necesarios para el correcto funcionamiento de la aplicación.
                </p>
            </section>

            <section>
                <h2>5. Legitimación</h2>
                <p>
                    La base legal para el tratamiento de los datos personales es el consentimiento otorgado
                    por el usuario al aceptar la presente política de privacidad durante el proceso de registro.
                </p>
            </section>

            <section>
                <h2>6. Cesión de datos</h2>
                <p>
                    Los datos personales no serán cedidos a terceros ni utilizados con fines comerciales.
                </p>
            </section>

            <section>
                <h2>7. Seguridad de la información</h2>
                <p>
                    La aplicación adopta medidas técnicas para proteger la información de los usuarios y
                    evitar accesos no autorizados. Las contraseñas se almacenan mediante mecanismos de
                    cifrado seguro.
                </p>
            </section>

            <section>
                <h2>8. Derechos del usuario</h2>
                <p>
                    El usuario podrá ejercer sus derechos de acceso, rectificación, supresión, limitación
                    del tratamiento y oposición mediante solicitud al responsable de la aplicación.
                </p>
            </section>

            <section>
                <h2>9. Aceptación de la política de privacidad</h2>
                <p>
                    El registro y uso de esta plataforma implica la aceptación de la presente política de
                    privacidad y protección de datos.
                </p>
            </section>

            <a href="Registro.jsp" class="btn-volver">Volver al registro</a>

        </main>        
        
        
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