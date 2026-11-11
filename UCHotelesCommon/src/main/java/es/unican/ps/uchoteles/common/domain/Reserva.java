package es.unican.ps.uchoteles.common.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Reserva de una o varias habitaciones de un hotel para unas fechas.
 * Cada tipo de habitación reservado es una línea ({@link ReservaTipoHabitacion}).
 */
public class Reserva {

    /** El identificador (número) de la reserva. */
    private Long id;

    /** La fecha de entrada. */
    private LocalDate fechaEntrada;

    /** La fecha de salida. */
    private LocalDate fechaSalida;

    /** El estado de la reserva. */
    private EstadoReserva estado;

    /** El instante en el que se bloquearon las habitaciones. */
    private LocalDateTime fechaBloqueo;

    /** El hotel de la reserva. */
    private Hotel hotel;

    /** Los datos del cliente. */
    private DatosCliente cliente;

    /** Los datos de pago. */
    private DatosPago datosPago;

    /** Las líneas de la reserva, una por cada tipo de habitación. */
    private List<ReservaTipoHabitacion> reservasPorTipo = new ArrayList<>();

    /**
     * Crea una reserva vacía. Lo necesita JPA para crear el objeto al leerlo de la base de datos.
     */
    public Reserva() {
    }

    /**
     * Crea una reserva sin líneas. Las líneas se añaden después con {@link #getReservasPorTipo()}.
     *
     * @param fechaEntrada fecha de entrada
     * @param fechaSalida  fecha de salida
     * @param estado       estado inicial de la reserva
     * @param fechaBloqueo instante en el que se bloquean las habitaciones
     * @param hotel        hotel de la reserva
     * @param cliente      datos del cliente (null mientras está bloqueada)
     * @param datosPago    datos de pago (null mientras está bloqueada)
     */
    public Reserva(LocalDate fechaEntrada, LocalDate fechaSalida, EstadoReserva estado,
                   LocalDateTime fechaBloqueo, Hotel hotel,
                   DatosCliente cliente, DatosPago datosPago) {
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.estado = estado;
        this.fechaBloqueo = fechaBloqueo;
        this.hotel = hotel;
        this.cliente = cliente;
        this.datosPago = datosPago;
    }

    /**
     * Calcula el número de noches entre la fecha de entrada y la de salida.
     *
     * @return número de noches de la reserva
     */
    public long getNumNoches() {
        return ChronoUnit.DAYS.between(fechaEntrada, fechaSalida);
    }

    /**
     * Calcula el importe total de la reserva sumando el importe de cada línea.
     *
     * @return importe total en euros
     */
    public double getImporte() {
        double total = 0;
        for (ReservaTipoHabitacion rth : reservasPorTipo) {
            total += rth.getImporte(getNumNoches());
        }
        return total;
    }

    /**
     * Devuelve el identificador (número) de la reserva.
     *
     * @return el identificador (número) de la reserva
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador (número) de la reserva.
     *
     * @param id el identificador (número) de la reserva
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Devuelve la fecha de entrada.
     *
     * @return la fecha de entrada
     */
    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }

    /**
     * Establece la fecha de entrada.
     *
     * @param fechaEntrada la fecha de entrada
     */
    public void setFechaEntrada(LocalDate fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    /**
     * Devuelve la fecha de salida.
     *
     * @return la fecha de salida
     */
    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    /**
     * Establece la fecha de salida.
     *
     * @param fechaSalida la fecha de salida
     */
    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    /**
     * Devuelve el estado de la reserva.
     *
     * @return el estado de la reserva
     */
    public EstadoReserva getEstado() {
        return estado;
    }

    /**
     * Establece el estado de la reserva.
     *
     * @param estado el estado de la reserva
     */
    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    /**
     * Devuelve el instante en el que se bloquearon las habitaciones.
     *
     * @return el instante en el que se bloquearon las habitaciones
     */
    public LocalDateTime getFechaBloqueo() {
        return fechaBloqueo;
    }

    /**
     * Establece el instante en el que se bloquearon las habitaciones.
     *
     * @param fechaBloqueo el instante en el que se bloquearon las habitaciones
     */
    public void setFechaBloqueo(LocalDateTime fechaBloqueo) {
        this.fechaBloqueo = fechaBloqueo;
    }

    /**
     * Devuelve el hotel de la reserva.
     *
     * @return el hotel de la reserva
     */
    public Hotel getHotel() {
        return hotel;
    }

    /**
     * Establece el hotel de la reserva.
     *
     * @param hotel el hotel de la reserva
     */
    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    /**
     * Devuelve los datos del cliente.
     *
     * @return los datos del cliente
     */
    public DatosCliente getCliente() {
        return cliente;
    }

    /**
     * Establece los datos del cliente.
     *
     * @param cliente los datos del cliente
     */
    public void setCliente(DatosCliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Devuelve los datos de pago.
     *
     * @return los datos de pago
     */
    public DatosPago getDatosPago() {
        return datosPago;
    }

    /**
     * Establece los datos de pago.
     *
     * @param datosPago los datos de pago
     */
    public void setDatosPago(DatosPago datosPago) {
        this.datosPago = datosPago;
    }

    /**
     * Devuelve las líneas de la reserva, una por cada tipo de habitación.
     *
     * @return las líneas de la reserva, una por cada tipo de habitación
     */
    public List<ReservaTipoHabitacion> getReservasPorTipo() {
        return reservasPorTipo;
    }

    /**
     * Establece las líneas de la reserva, una por cada tipo de habitación.
     *
     * @param reservasPorTipo las líneas de la reserva, una por cada tipo de habitación
     */
    public void setReservasPorTipo(List<ReservaTipoHabitacion> reservasPorTipo) {
        this.reservasPorTipo = reservasPorTipo;
    }
}
