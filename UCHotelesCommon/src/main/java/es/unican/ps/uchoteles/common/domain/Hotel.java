package es.unican.ps.uchoteles.common.domain;

import java.util.ArrayList;
import java.util.List;

public class Hotel {

    private Long id;
    private String nombre;
    private String direccion;
    private String localidad;
    private Categoria categoria;
    private List<Habitacion> habitaciones = new ArrayList<>();
    // Navegabilidad Hotel -> Reserva (feedback de la profe)
    private List<Reserva> reservas = new ArrayList<>();

    public Hotel() {
    }

    public Hotel(String nombre, String direccion, String localidad, Categoria categoria) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.localidad = localidad;
        this.categoria = categoria;
    }

    // Devuelve la habitación del tipo indicado, o null si el hotel no tiene ese tipo
    public Habitacion getHabitacion(TipoHabitacion tipo) {
        for (Habitacion h : habitaciones) {
            if (h.getTipo() == tipo) {
                return h;
            }
        }
        return null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(List<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }
}