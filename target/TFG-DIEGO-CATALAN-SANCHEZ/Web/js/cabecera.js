class Cabecera extends HTMLElement {
    constructor() {
        super();
        const BASE = window.APP_CTX + "/web/";
        this.innerHTML = `
            <header>
                <nav class="navbar navbar-expand-lg navbar-light">
                    <div class="container-fluid">
                        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"  aria-label="Toggle navigation" style= "color :white">
                            <span class="navbar-toggler-icon" ></span>
                        </button>
                        <div class="collapse navbar-collapse mb-auto mb-lg-0" id="navbarSupportedContent">
                            <ul class="navbar-nav d-flex align-items-start">
                            <li class="nav-item">
                                    <a class="nav-link" aria-current="page" href="${BASE}home.jsp">INICIO</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" aria-current="page" href="${BASE}SobreNosotros.jsp">SOBRE NOSOTROS</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="${BASE}Reservas.jsp">RESERVAS</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="${BASE}Clasificaciones.jsp">CLASIFICACIONES</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="${BASE}Reseñas.jsp">RESEÑAS</a>
                                </li>
                            </ul>
                            <ul class="navbar-nav ms-auto mb-2 mb-lg-2">
                                <li class="nav-item">

                                    <a class="nav-link" href="${BASE}InicioSesion.jsp"><i class="bi bi-person-circle px-4 fs-1" style="color: white"></i></a>
                                </li> 
                            </ul>
                        </div>
                    </div>
                </nav>
            </header>
                    `;
    } 
}
window.customElements.define('mi-cabecera', Cabecera);