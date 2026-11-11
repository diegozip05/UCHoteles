package es.unican.ps.uchoteles.businesslayer;

import es.unican.ps.uchoteles.common.businesslayer.IConsultaHoteles;
import es.unican.ps.uchoteles.common.businesslayer.IGestionHoteles;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Componente de negocio para la consulta y la gestión de hoteles.
 * Calcula la disponibilidad de habitaciones y permite al gerente modificar los tipos de habitación.
 */
@Stateless
@Local({IConsultaHoteles.class, IGestionHoteles.class})
public class GestionHoteles implements IConsultaHoteles, IGestionHoteles {

    /** Acceso a los datos de los hoteles. */
    @EJB
    private IHotelesDAO hotelesDAO;

    /** Acceso a los datos de las reservas. */
    @EJB
    private IReservasDAO reservasDAO;

    /** {@inheritDoc} */
    @Override
    public Hotel buscarHotelPorNombre(String nombre) throws DataAccessException {
        return hotelesDAO.hotelPorNombre(nombre);
    }

    /** {@inheritDoc} */
    @Override
    public List<Hotel> buscarHotelesPorLocalidad(String localidad) throws DataAccessException {
        return hotelesDAO.hotelesPorLocalidad(localidad);
    }

    /** Minutos durante los que una reserva bloqueada retiene sus habitaciones. */
    public static final int MINUTOS_BLOQUEO = 10;

    /**
     * {@inheritDoc}
     *
     * <p>Parte de todas las habitaciones del hotel y resta las de las reservas que se solapan con las
     * fechas y siguen vigentes (confirmadas o bloqueadas hace menos de {@link #MINUTOS_BLOQUEO} minutos).</p>
     */
    @Override
    public Map<Habitacion, Integer> consultarDisponibilidad(Hotel hotel, LocalDate entrada, LocalDate salida)
            throws DataAccessException, OperacionNoValidaException {

        if (!salida.isAfter(entrada)) {
            throw new OperacionNoValidaException("La fecha de salida debe ser posterior a la de entrada");
        }

        Map<Habitacion, Integer> libres = new LinkedHashMap<>();
        for (Habitacion h : hotel.getHabitaciones()) {
            libres.put(h, h.getDisponibles());
        }

        LocalDateTime limiteBloqueo = LocalDateTime.now().minusMinutes(MINUTOS_BLOQUEO);
        for (Reserva r : reservasDAO.reservasPorHotel(hotel)) {
            if (seSolapa(r, entrada, salida) && estaVigente(r, limiteBloqueo)) {
                for (ReservaTipoHabitacion rth : r.getReservasPorTipo()) {
                    Habitacion h = hotel.getHabitacion(rth.getHabitacion().getTipo());
                    if (h != null) {
                        libres.put(h, Math.max(0, libres.get(h) - rth.getNumHabitaciones()));
                    }
                }
            }
        }

        int totalLibres = 0;
        for (int n : libres.values()) {
            totalLibres += n;
        }
        if (totalLibres == 0) {
            throw new OperacionNoValidaException("No hay disponibilidad, pruebe otras fechas");
        }
        return libres;
    }

    /**
     * Indica si una reserva se solapa con unas fechas. Si la reserva sale el mismo día que
     * empieza la consulta, o entra el día que termina, no se considera solapada.
     *
     * @param r       reserva
     * @param entrada fecha de entrada consultada
     * @param salida  fecha de salida consultada
     * @return true si las fechas se solapan
     */
    private boolean seSolapa(Reserva r, LocalDate entrada, LocalDate salida) {
        return r.getFechaEntrada().isBefore(salida) && r.getFechaSalida().isAfter(entrada);
    }

    /**
     * Indica si una reserva ocupa habitaciones: está confirmada o se bloqueó después del límite.
     *
     * @param r             reserva
     * @param limiteBloqueo instante a partir del cual un bloqueo sigue vigente
     * @return true si la reserva ocupa habitaciones
     */
    private boolean estaVigente(Reserva r, LocalDateTime limiteBloqueo) {
        if (r.getEstado() == EstadoReserva.CONFIRMADA) {
            return true;
        }
        return r.getFechaBloqueo() != null && r.getFechaBloqueo().isAfter(limiteBloqueo);
    }

    /** {@inheritDoc} */
    @Override
    public Hotel consultarHotel(String nombre) throws DataAccessException, OperacionNoValidaException {
        Hotel hotel = hotelesDAO.hotelPorNombre(nombre);
        if (hotel == null) {
            throw new OperacionNoValidaException("Hotel no encontrado");
        }
        return hotel;
    }

    /** {@inheritDoc} */
    @Override
    public Hotel modificarHabitaciones(Hotel hotel, List<Habitacion> habitaciones)
            throws DataAccessException, OperacionNoValidaException {
        for (Habitacion nueva : habitaciones) {
            Habitacion actual = hotel.getHabitacion(nueva.getTipo());
            if (actual == null) {
                throw new OperacionNoValidaException("El hotel no tiene habitaciones de tipo " + nueva.getTipo());
            }
            if (nueva.getPrecioPorNoche() <= 0) {
                throw new OperacionNoValidaException("El precio por noche debe ser mayor que 0");
            }
            if (nueva.getDisponibles() < 0) {
                throw new OperacionNoValidaException("El número de habitaciones no puede ser negativo");
            }
            actual.setPrecioPorNoche(nueva.getPrecioPorNoche());
            actual.setDisponibles(nueva.getDisponibles());
        }
        return hotelesDAO.modificarHotel(hotel);
    }

    /** {@inheritDoc} */
    @Override
    public Hotel anhadirTipoHabitacion(Hotel hotel, Habitacion habitacion)
            throws DataAccessException, OperacionNoValidaException {
        if (hotel.getHabitacion(habitacion.getTipo()) != null) {
            throw new OperacionNoValidaException("El hotel ya tiene habitaciones de tipo " + habitacion.getTipo());
        }
        validarDatos(habitacion);
        habitacion.setHotel(hotel);
        hotel.getHabitaciones().add(habitacion);
        return hotelesDAO.modificarHotel(hotel);
    }

    /** {@inheritDoc} */
    @Override
    public Hotel eliminarTipoHabitacion(Hotel hotel, TipoHabitacion tipo)
            throws DataAccessException, OperacionNoValidaException {
        Habitacion habitacion = hotel.getHabitacion(tipo);
        if (habitacion == null) {
            throw new OperacionNoValidaException("El hotel no tiene habitaciones de tipo " + tipo);
        }
        hotel.getHabitaciones().remove(habitacion);
        return hotelesDAO.modificarHotel(hotel);
    }

    /**
     * Comprueba que el precio por noche es positivo y que el número de habitaciones no es negativo.
     *
     * @param h tipo de habitación que se valida
     * @throws OperacionNoValidaException si algún dato no es válido
     */
    private void validarDatos(Habitacion h) throws OperacionNoValidaException {
        if (h.getPrecioPorNoche() <= 0) {
            throw new OperacionNoValidaException("El precio por noche debe ser mayor que 0");
        }
        if (h.getDisponibles() < 0) {
            throw new OperacionNoValidaException("El número de habitaciones no puede ser negativo");
        }
    }
}
