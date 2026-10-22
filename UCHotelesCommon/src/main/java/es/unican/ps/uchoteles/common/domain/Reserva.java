package es.unican.ps.uchoteles.common.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Reserva {

    private Long id;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private EstadoReserva estado;
    private LocalDateTime fechaBloqueo;
    private Hotel hotel;
    private DatosCliente cliente;
    private DatosPago datosPago;
    private List<ReservaTipoHabitacion> reservasPorTipo = new ArrayList<>();

    public Reserva() {
    }

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

    public long getNumNoches() {
        return ChronoUnit.DAYS.between(fechaEntrada, fechaSalida);
    }

    public double getImporte() {
        double total = 0;
        for (ReservaTipoHabitacion rth : reservasPorTipo) {
            total += rth.getImporte(getNumNoches());
        }
        return total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(LocalDate fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaBloqueo() {
        return fechaBloqueo;
    }

    public void setFechaBloqueo(LocalDateTime fechaBloqueo) {
        this.fechaBloqueo = fechaBloqueo;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public DatosCliente getCliente() {
        return cliente;
    }

    public void setCliente(DatosCliente cliente) {
        this.cliente = cliente;
    }

    public DatosPago getDatosPago() {
        return datosPago;
    }

    public void setDatosPago(DatosPago datosPago) {
        this.datosPago = datosPago;
    }

    public List<ReservaTipoHabitacion> getReservasPorTipo() {
        return reservasPorTipo;
    }

    public void setReservasPorTipo(List<ReservaTipoHabitacion> reservasPorTipo) {
        this.reservasPorTipo = reservasPorTipo;
    }
}