package es.unican.ps.uchoteles.common.domain;

public class Habitacion {
    private Long id;
    private TipoHabitacion tipo;
    private  double precioPorNoche;
    private int disponibles;
    private Hotel hotel;

    public Habitacion(){

    }

    public Habitacion(TipoHabitacion tipo, double precioPorNoche, int disponibles, Hotel hotel) {
        this.tipo = tipo;
        this.precioPorNoche = precioPorNoche;
        this.disponibles = disponibles;
        this.hotel = hotel;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoHabitacion getTipo() {
        return tipo;
    }

    public void setTipo(TipoHabitacion tipo) {
        this.tipo = tipo;
    }

    public double getPrecioPorNoche() {
        return precioPorNoche;
    }

    public void setPrecioPorNoche(double precioPorNoche) {
        this.precioPorNoche = precioPorNoche;
    }

    public int getDisponibles() {
        return disponibles;
    }

    public void setDisponibles(int disponibles) {
        this.disponibles = disponibles;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
