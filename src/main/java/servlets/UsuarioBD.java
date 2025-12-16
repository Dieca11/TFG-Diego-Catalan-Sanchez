package servlets;

import java.sql.Date;

public class UsuarioBD {
    private int id;
    private String usuario;
    private String email;
    private String contraseña;
    private String foto_perfil;
    private boolean recibir_invitacion;
    private boolean mostrar_partidas;
    private String tarjeta;
    private Date fecha_creacion;

    public void setId(int id) {
        this.id = id;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    public void setFoto_perfil(String foto_perfil) {
        this.foto_perfil = foto_perfil;
    }
    public void setRecibir_invitacion(boolean recibir_invitacion) {
        this.recibir_invitacion = recibir_invitacion;
    }
    public void setMostrar_partidas(boolean mostrar_partidas) {
        this.mostrar_partidas = mostrar_partidas;
    }
    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }
    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }
    
    public int getId() {
        return id;
    }
    public String getUsuario() {
        return usuario;
    }
    public String getEmail() {
        return email;
    }
    public String getContraseña() {
        return contraseña;
    }
    public String getFoto_perfil() {
        return foto_perfil;
    }
    public boolean isRecibir_invitacion() {
        return recibir_invitacion;
    }
    public boolean isMostrar_partidas() {
        return mostrar_partidas;
    }
    public String getTarjeta() {
        return tarjeta;
    }
    public Date getFecha_creacion() {
        return fecha_creacion;
    }
}
