package servlets;

public class UsuarioClasificacion {
    private int posicion;
    private int idUsuario;
    private String nombreUsuario;
    private int partidasJugadas;
    private int partidasGanadas;
    private double porcentajeV;
    private String racha;

    public int getPosicion() {
        return posicion;
    }
    public int getIdUsuario() {
        return idUsuario;
    }
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    public int getPartidasJugadas() {
        return partidasJugadas;
    }
    public int getPartidasGanadas() {
        return partidasGanadas;
    }
    public double getPorcentajeV() {
        return porcentajeV;
    }
    public String getRacha() {
        return racha;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    public void setPartidasJugadas(int partidasJugadas) {
        this.partidasJugadas = partidasJugadas;
    }
    public void setPartidasGanadas(int partidasGanadas) {
        this.partidasGanadas = partidasGanadas;
    }
    public void setPorcentajeV(double porcentajeV) {
        this.porcentajeV = porcentajeV;
    }
    public void setRacha(String racha) {
        this.racha = racha;
    }
}
