package es.unican.ps.uchoteles.common.domain;

import java.util.ArrayList;
import java.util.List;

/** Hotel de la cadena, con sus tipos de habitación y sus reservas. */
public class Hotel {

    /** El identificador del hotel. */
    private Long id;

    /** El nombre del hotel. */
    private String nombre;

    /** La dirección del hotel. */
    private String direccion;

    /** La localidad en la que está el hotel. */
    private String localidad;

    /** La categoría (número de estrellas) del hotel. */
    private Categoria categoria;

    /** Los tipos de habitación del hotel. */
    private List<Habitacion> habitaciones = new ArrayList<>();

    /** Las reservas del hotel. */
    private List<Reserva> reservas = new ArrayList<>();

    /** Crea un hotel vacío. Lo necesita JPA para crear el objeto al leerlo de la base de datos. */
    public Hotel() {
    }

    /**
     * Crea un hotel sin habitaciones ni reservas.
     *
     * @param nombre    nombre del hotel
     * @param direccion dirección
     * @param localidad localidad en la que está
     * @param categoria categoría del hotel
     */
    public Hotel(String nombre, String direccion, String localidad, Categoria categoria) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.localidad = localidad;
        this.categoria = categoria;
    }

    /**
     * Busca el tipo de habitación indicado entre las habitaciones del hotel.
     *
     * @param tipo tipo de habitación buscado
     * @return la habitación de ese tipo, o null si el hotel no tiene ese tipo
     */
    public Habitacion getHabitacion(TipoHabitacion tipo) {
        for (Habitacion h : habitaciones) {
            if (h.getTipo() == tipo) {
                return h;
            }
        }
        return null;
    }

    /**
     * Devuelve el identificador del hotel.
     *
     * @return el identificador del hotel
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador del hotel.
     *
     * @param id el identificador del hotel
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Devuelve el nombre del hotel.
     *
     * @return el nombre del hotel
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del hotel.
     *
     * @param nombre el nombre del hotel
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve la dirección del hotel.
     *
     * @return la dirección del hotel
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección del hotel.
     *
     * @param direccion la dirección del hotel
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Devuelve la localidad en la que está el hotel.
     *
     * @return la localidad en la que está el hotel
     */
    public String getLocalidad() {
        return localidad;
    }

    /**
     * Establece la localidad en la que está el hotel.
     *
     * @param localidad la localidad en la que está el hotel
     */
    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    /**
     * Devuelve la categoría (número de estrellas) del hotel.
     *
     * @return la categoría (número de estrellas) del hotel
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * Establece la categoría (número de estrellas) del hotel.
     *
     * @param categoria la categoría (número de estrellas) del hotel
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    /**
     * Devuelve los tipos de habitación del hotel.
     *
     * @return los tipos de habitación del hotel
     */
    public List<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    /**
     * Establece los tipos de habitación del hotel.
     *
     * @param habitaciones los tipos de habitación del hotel
     */
    public void setHabitaciones(List<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    /**
     * Devuelve las reservas del hotel.
     *
     * @return las reservas del hotel
     */
    public List<Reserva> getReservas() {
        return reservas;
    }

    /**
     * Establece las reservas del hotel.
     *
     * @param reservas las reservas del hotel
     */
    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }
}
