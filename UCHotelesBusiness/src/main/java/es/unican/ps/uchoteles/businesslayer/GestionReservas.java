package es.unican.ps.uchoteles.businesslayer;

import es.unican.ps.uchoteles.common.businesslayer.IConsultaHoteles;
import es.unican.ps.uchoteles.common.businesslayer.IConsultaReservasHotel;
import es.unican.ps.uchoteles.common.businesslayer.IGestionReservas;
import es.unican.ps.uchoteles.common.daolayer.IHotelesDAO;
import es.unican.ps.uchoteles.common.daolayer.IReservasDAO;
import es.unican.ps.uchoteles.common.domain.*;
import es.unican.ps.uchoteles.common.exceptions.DataAccessException;
import es.unican.ps.uchoteles.common.exceptions.OperacionNoValidaException;
import jakarta.ejb.EJB;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Componente de negocio para la gestión de reservas: reservar, confirmar, consultar y
 * cancelar reservas, y consultar las reservas de un hotel.
 */
@Stateless
@Local({IGestionReservas.class, IConsultaReservasHotel.class})
public class GestionReservas implements IGestionReservas, IConsultaReservasHotel {

    /** Días que tienen que faltar como mínimo para la entrada para poder cancelar una reserva. */
    public static final int DIAS_LIMITE_CANCELACION = 2;

    /** Acceso a los datos de las reservas. */
    @EJB
    private IReservasDAO reservasDAO;

    /** Acceso a los datos de los hoteles. */
    @EJB
    private IHotelesDAO hotelesDAO;

    /** Componente de negocio que calcula la disponibilidad de habitaciones. */
    @EJB
    private IConsultaHoteles consultaHoteles;

    /** {@inheritDoc} */
    @Override
    public List<Reserva> consultarReservasHotel(String nombreHotel)
            throws DataAccessException, OperacionNoValidaException {
        Hotel hotel = hotelesDAO.hotelPorNombre(nombreHotel);
        if (hotel == null) {
            throw new OperacionNoValidaException("Hotel no encontrado");
        }
        List<Reserva> reservas = new ArrayList<>();
        for (Reserva r : reservasDAO.reservasPorHotel(hotel)) {
            if (r.getEstado() == EstadoReserva.CONFIRMADA) {
                reservas.add(r);
            }
        }
        reservas.sort(Comparator.comparing(Reserva::getFechaEntrada));
        return reservas;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Vuelve a calcular la disponibilidad porque puede haber cambiado desde que el usuario la consultó.</p>
     */
    @Override
    public Reserva reservar(Hotel hotel, LocalDate entrada, LocalDate salida, Map<TipoHabitacion, Integer> seleccion)
            throws DataAccessException, OperacionNoValidaException {

        Map<Habitacion, Integer> libres = consultaHoteles.consultarDisponibilidad(hotel, entrada, salida);

        Reserva reserva = new Reserva(entrada, salida, EstadoReserva.BLOQUEADA, LocalDateTime.now(),
                hotel, null, null);

        for (Map.Entry<TipoHabitacion, Integer> eleccion : seleccion.entrySet()) {
            TipoHabitacion tipo = eleccion.getKey();
            int numHabitaciones = eleccion.getValue();
            if (numHabitaciones <= 0) {
                continue;
            }
            Habitacion habitacion = hotel.getHabitacion(tipo);
            if (habitacion == null || numHabitaciones > libres.getOrDefault(habitacion, 0)) {
                throw new OperacionNoValidaException("Habitación de tipo " + tipo + " no disponible");
            }
            reserva.getReservasPorTipo().add(new ReservaTipoHabitacion(numHabitaciones, habitacion, reserva));
        }

        if (reserva.getReservasPorTipo().isEmpty()) {
            throw new OperacionNoValidaException("Debe seleccionar al menos una habitación");
        }

        hotel.getReservas().add(reserva);
        return reservasDAO.crearReserva(reserva);
    }

    /** {@inheritDoc} */
    @Override
    public Long confirmarReserva(Long idReserva, DatosCliente cliente, DatosPago pago)
            throws DataAccessException, OperacionNoValidaException {
        Reserva reserva = reservasDAO.reservaPorId(idReserva);
        if (reserva == null) {
            throw new OperacionNoValidaException("No existe reserva");
        }
        if (reserva.getEstado() == EstadoReserva.CONFIRMADA) {
            throw new OperacionNoValidaException("La reserva ya está confirmada");
        }
        LocalDateTime limiteBloqueo = LocalDateTime.now().minusMinutes(GestionHoteles.MINUTOS_BLOQUEO);
        if (reserva.getFechaBloqueo() == null || reserva.getFechaBloqueo().isBefore(limiteBloqueo)) {
            throw new OperacionNoValidaException("El tiempo para completar la reserva ha expirado");
        }
        reserva.setCliente(cliente);
        reserva.setDatosPago(pago);
        reserva.setEstado(EstadoReserva.CONFIRMADA);
        return reservasDAO.modificarReserva(reserva).getId();
    }

    /** {@inheritDoc} */
    @Override
    public Reserva consultarReserva(Long idReserva, String dni)
            throws DataAccessException, OperacionNoValidaException {
        Reserva reserva = reservasDAO.reservaPorId(idReserva);
        if (reserva == null) {
            throw new OperacionNoValidaException("No existe reserva");
        }
        if (reserva.getCliente() == null || !reserva.getCliente().getDni().equals(dni)) {
            throw new OperacionNoValidaException("Reserva no accesible");
        }
        return reserva;
    }

    /** {@inheritDoc} */
    @Override
    public void cancelarReserva(Long idReserva, String dni)
            throws DataAccessException, OperacionNoValidaException {
        Reserva reserva = consultarReserva(idReserva, dni);

        long diasHastaEntrada = ChronoUnit.DAYS.between(LocalDate.now(), reserva.getFechaEntrada());
        if (diasHastaEntrada < DIAS_LIMITE_CANCELACION) {
            throw new OperacionNoValidaException("Límite de cancelación de reserva sobrepasado");
        }

        if (reserva.getHotel() != null) {
            reserva.getHotel().getReservas().remove(reserva);
        }
        reservasDAO.eliminarReserva(reserva);
    }
}
