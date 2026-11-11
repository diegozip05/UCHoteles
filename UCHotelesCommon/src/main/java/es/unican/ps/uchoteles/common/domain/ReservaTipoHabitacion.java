package es.unican.ps.uchoteles.common.domain;

/**
 * Línea de una reserva: cuántas habitaciones de un tipo se reservan y a qué precio.
 * El precio se guarda al crear la línea para que el importe no cambie si el hotel modifica sus precios.
 */
public class ReservaTipoHabitacion {

    /** El identificador de la línea. */
    private Long id;

    /** El número de habitaciones reservadas de este tipo. */
    private int numHabitaciones;

    /** El precio por noche guardado al hacer la reserva. */
    private double precioPorNoche;

    /** El tipo de habitación reservado. */
    private Habitacion habitacion;

    /** La reserva a la que pertenece la línea. */
    private Reserva reserva;

    /** Crea una línea vacía. Lo necesita JPA para crear el objeto al leerlo de la base de datos. */
    public ReservaTipoHabitacion() {
    }

    /**
     * Crea una línea de reserva y guarda el precio por noche que tiene la habitación en ese momento.
     *
     * @param numHabitaciones número de habitaciones reservadas de este tipo
     * @param habitacion      tipo de habitación reservado
     * @param reserva         reserva a la que pertenece la línea
     */
    public ReservaTipoHabitacion(int numHabitaciones, Habitacion habitacion, Reserva reserva) {
        this.numHabitaciones = numHabitaciones;
        this.habitacion = habitacion;
        this.reserva = reserva;
        this.precioPorNoche = habitacion.getPrecioPorNoche();
    }

    /**
     * Calcula el importe de la línea con el precio guardado al reservar.
     *
     * @param noches número de noches de la reserva
     * @return número de habitaciones x precio por noche x noches
     */
    public double getImporte(long noches) {
        return numHabitaciones * precioPorNoche * noches;
    }

    /**
     * Devuelve el identificador de la línea.
     *
     * @return el identificador de la línea
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador de la línea.
     *
     * @param id el identificador de la línea
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Devuelve el número de habitaciones reservadas de este tipo.
     *
     * @return el número de habitaciones reservadas de este tipo
     */
    public int getNumHabitaciones() {
        return numHabitaciones;
    }

    /**
     * Establece el número de habitaciones reservadas de este tipo.
     *
     * @param numHabitaciones el número de habitaciones reservadas de este tipo
     */
    public void setNumHabitaciones(int numHabitaciones) {
        this.numHabitaciones = numHabitaciones;
    }

    /**
     * Devuelve el precio por noche guardado al hacer la reserva.
     *
     * @return el precio por noche guardado al hacer la reserva
     */
    public double getPrecioPorNoche() {
        return precioPorNoche;
    }

    /**
     * Establece el precio por noche guardado al hacer la reserva.
     *
     * @param precioPorNoche el precio por noche guardado al hacer la reserva
     */
    public void setPrecioPorNoche(double precioPorNoche) {
        this.precioPorNoche = precioPorNoche;
    }

    /**
     * Devuelve el tipo de habitación reservado.
     *
     * @return el tipo de habitación reservado
     */
    public Habitacion getHabitacion() {
        return habitacion;
    }

    /**
     * Establece el tipo de habitación reservado.
     *
     * @param habitacion el tipo de habitación reservado
     */
    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    /**
     * Devuelve la reserva a la que pertenece la línea.
     *
     * @return la reserva a la que pertenece la línea
     */
    public Reserva getReserva() {
        return reserva;
    }

    /**
     * Establece la reserva a la que pertenece la línea.
     *
     * @param reserva la reserva a la que pertenece la línea
     */
    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }
}
