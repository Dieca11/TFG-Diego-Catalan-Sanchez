class Pie extends HTMLElement {
    constructor() {
        super()
        this.innerHTML = `
        <footer>
            <div class="iconos">
                <a href="Contacto.html"><i class="bi bi-telephone-fill px-4 fs-1" style="color: white;"></i></a>
                <a href=""><i class="bi bi-envelope-fill px-4 fs-1" style="color: white;"></i></a>
                <a href="PoliticaPrivacidad.jsp"><i class="bi bi-file-earmark-lock px-4 fs-1" style="color: white;"></i></a>
            </div>
      </footer>    
        `
    }
}
window.customElements.define('mi-pie', Pie);