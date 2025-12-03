package servlets;

public class MunicipioBD{

    private int id;
    private String municipio;
    private String imagen;
    private String map_iframe;
    private int num_pistas;
    private boolean activo;

    public int getId() {
        return id;
    }
    public String getMunicipio() {
        return municipio;
    }
    public String getImagen() {
        return imagen;
    }
    public String getMap_iframe() {
        return map_iframe;
    }
    public int getNum_pistas() {
        return num_pistas;
    }
    public boolean isActivo() {
        return activo;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    public void setMap_iframe(String map_iframe) {
        this.map_iframe = map_iframe;
    }
    public void setNum_pistas(int num_pistas) {
        this.num_pistas = num_pistas;
    }
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
}