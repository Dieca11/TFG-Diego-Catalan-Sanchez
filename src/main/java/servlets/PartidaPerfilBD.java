package servlets;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PartidaPerfilBD {
    private int id; 
    private Timestamp fechaHora;
    private String municipio;
    private int numeroPista;
    private boolean creador;
    private String estado;
    private String resultado;
    private List<String> participantes= new ArrayList<>();

    public int getId() {
        return id;
    }
    public Timestamp getFechaHora() {
        return fechaHora;
    }
    public String getMunicipio() {
        return municipio;
    }
    public int getNumeroPista() {
        return numeroPista;
    }
    public boolean isCreador() {
        return creador;
    }
    public String getEstado() {
        return estado;
    }
    public String getResultado() {
        return resultado;
    }
    public List<String> getParticipantes() {
        return participantes;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setFechaHora(Timestamp fechaHora) {
        this.fechaHora = fechaHora;
    }
    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }
    public void setNumeroPista(int numeroPista) {
        this.numeroPista = numeroPista;
    }
    public void setCreador(boolean creador) {
        this.creador = creador;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
    public void setParticipantes(List<String> participantes) {
        participantes = participantes;
    }
    
}
