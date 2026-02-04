package servlets;

import java.time.LocalDateTime;

public class ReservaBD {
    private int id;
    private int municipio_id;
    private int numero_pista;
    private int creador_id;
    private int invitado1_id;
    private int invitado2_id;
    private int invitado3_id;
    private LocalDateTime fecha_hora;
    private String estado;

    
    public int getId() {
        return id;
    }
    public int getMunicipio_id() {
        return municipio_id;
    }
    public int getNumero_pista() {
        return numero_pista;
    }
    public int getCreador_id() {
        return creador_id;
    }
    public int getInvitado1_id() {
        return invitado1_id;
    }
    public int getInvitado2_id() {
        return invitado2_id;
    }
    public int getInvitado3_id() {
        return invitado3_id;
    }
    public LocalDateTime getFecha_hora() {
        return fecha_hora;
    }
    public String getEstado() {
        return estado;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setMunicipio_id(int municipio_id) {
        this.municipio_id = municipio_id;
    }
    public void setNumero_pista(int numero_pista) {
        this.numero_pista = numero_pista;
    }
    public void setCreador_id(int creador_id) {
        this.creador_id = creador_id;
    }
    public void setInvitado1_id(int invitado1_id) {
        this.invitado1_id = invitado1_id;
    }
    public void setInvitado2_id(int invitado2_id) {
        this.invitado2_id = invitado2_id;
    }
    public void setInvitado3_id(int invitado3_id) {
        this.invitado3_id = invitado3_id;
    }
    public void setFecha_hora(LocalDateTime fecha_hora) {
        this.fecha_hora = fecha_hora;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

}
