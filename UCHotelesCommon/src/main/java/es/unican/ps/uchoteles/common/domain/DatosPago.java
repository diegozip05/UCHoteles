package es.unican.ps.uchoteles.common.domain;

public class DatosPago {
    private String numTarjeta;
    private String cvc;
    private int mesCaducidad;
    private int anhoCaducidad;
    private TipoTarjeta tipo;

    public DatosPago(){

    }

    public DatosPago(String numTarjeta, String cvc, int mesCaducidad, int anhoCaducidad, TipoTarjeta tipo){
        this.numTarjeta = numTarjeta;
        this.cvc = cvc;
        this.mesCaducidad = mesCaducidad;
        this.anhoCaducidad = anhoCaducidad;
        this.tipo = tipo;
    }

    public String getNumTarjeta() {
        return numTarjeta;
    }

    public void setNumTarjeta(String numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    public TipoTarjeta getTipo() {
        return tipo;
    }

    public void setTipo(TipoTarjeta tipo) {
        this.tipo = tipo;
    }

    public int getAnhoCaducidad() {
        return anhoCaducidad;
    }

    public void setAnhoCaducidad(int anhoCaducidad) {
        this.anhoCaducidad = anhoCaducidad;
    }

    public int getMesCaducidad() {
        return mesCaducidad;
    }

    public void setMesCaducidad(int mesCaducidad) {
        this.mesCaducidad = mesCaducidad;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }
}
