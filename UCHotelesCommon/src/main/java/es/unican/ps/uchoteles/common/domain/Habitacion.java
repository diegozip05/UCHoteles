package es.unican.ps.uchoteles.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Tipo de habitación de un hotel, con su precio por noche y el número de habitaciones
 * de ese tipo que tiene el hotel. No representa una habitación física concreta.
 */
@Entity
@Table(name = "Habitaciones")
public class Habitacion {
    /** El identificador de la habitación. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** El tipo de habitación. */
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoHabitacion tipo;

    /** El precio por noche de una habitación de este tipo. */
    @Column(name = "precio")
    private double precioPorNoche;

    /** El número de habitaciones de este tipo que tiene el hotel. */
    @Column(name = "numDisp")
    private int disponibles;

    /** El hotel al que pertenece. */
    @ManyToOne
    @JoinColumn(name = "Hotel_FK")
    private Hotel hotel;

    /**
     * Crea una habitación vacía. Lo necesita JPA para crear el objeto al leerlo de la base de datos.
     */
    public Habitacion() {
    }

    /**
     * Crea un tipo de habitación de un hotel.
     *
     * @param tipo           tipo de habitación
     * @param precioPorNoche precio por noche de una habitación
     * @param disponibles    número de habitaciones de este tipo
     * @param hotel          hotel al que pertenece
     */
    public Habitacion(TipoHabitacion tipo, double precioPorNoche, int disponibles, Hotel hotel) {
        this.tipo = tipo;
        this.precioPorNoche = precioPorNoche;
        this.disponibles = disponibles;
        this.hotel = hotel;
    }

    /**
     * Devuelve el identificador de la habitación.
     *
     * @return el identificador de la habitación
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador de la habitación.
     *
     * @param id el identificador de la habitación
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Devuelve el tipo de habitación.
     *
     * @return el tipo de habitación
     */
    public TipoHabitacion getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de habitación.
     *
     * @param tipo el tipo de habitación
     */
    public void setTipo(TipoHabitacion tipo) {
        this.tipo = tipo;
    }

    /**
     * Devuelve el precio por noche de una habitación de este tipo.
     *
     * @return el precio por noche de una habitación de este tipo
     */
    public double getPrecioPorNoche() {
        return precioPorNoche;
    }

    /**
     * Establece el precio por noche de una habitación de este tipo.
     *
     * @param precioPorNoche el precio por noche de una habitación de este tipo
     */
    public void setPrecioPorNoche(double precioPorNoche) {
        this.precioPorNoche = precioPorNoche;
    }

    /**
     * Devuelve el número de habitaciones de este tipo que tiene el hotel.
     *
     * @return el número de habitaciones de este tipo que tiene el hotel
     */
    public int getDisponibles() {
        return disponibles;
    }

    /**
     * Establece el número de habitaciones de este tipo que tiene el hotel.
     *
     * @param disponibles el número de habitaciones de este tipo que tiene el hotel
     */
    public void setDisponibles(int disponibles) {
        this.disponibles = disponibles;
    }

    /**
     * Devuelve el hotel al que pertenece.
     *
     * @return el hotel al que pertenece
     */
    public Hotel getHotel() {
        return hotel;
    }

    /**
     * Establece el hotel al que pertenece.
     *
     * @param hotel el hotel al que pertenece
     */
    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}