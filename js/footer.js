class Pie extends HTMLElement {
    constructor() {
        super()
        this.innerHTML = `
        <footer>
            <div class="iconos">
                <a href="Contacto.html"><i class="bi bi-telephone-fill px-4 fs-1" style="color: white;"></i></a>
                <a href=""><i class="bi bi-envelope-fill px-4 fs-1" style="color: white;"></i></a>
                <a href="Mapa.html"><i class="bi bi-geo-alt-fill px-4 fs-1" style="color: white;"></i></a>
            </div>
      </footer>    
        `
    }
}
window.customElements.define('mi-pie', Pie);