class Cabecera extends HTMLElement {
    constructor() {
        super();
        this.innerHTML = `
            <header>
                <nav class="navbar navbar-expand-lg navbar-light">
                    <div class="container-fluid">
                        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" style="color: white" aria-label="Toggle navigation">
                            <span class="navbar-toggler-icon" ></span>
                        </button>
                        <div class="collapse navbar-collapse" id="navbarSupportedContent">
                            <ul class="navbar-nav me-auto mb-sm-2 mb-lg-2">
                            <li class="nav-item">
                                    <a class="nav-link" aria-current="page" href="HOME.html">INICIO</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" aria-current="page" href="SobreNosotros.html">SOBRE NOSOTROS</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="Reservas.html">RESERVAS</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="Clasificaciones.html">CLASIFICACIONES</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="Reseñas.html">RESEÑAS</a>
                                </li>
                            </ul>
                            <ul class="navbar-nav ms-auto mb-2 mb-lg-2">
                                <li class="nav-item">

                                    <a class="nav-link" href="InicioSesion.html"><i class="bi bi-person-circle px-4 fs-1" style="color: white"></i></a>
                                </li> 
                            </ul>
                        </div>
                    </div>
                </nav>
                <h1 class="titulo">RESERVA AQUI TU PISTA DE PADEL!</h1>
            </header>
                    `;
    } 
}
window.customElements.define('mi-cabecera', Cabecera);