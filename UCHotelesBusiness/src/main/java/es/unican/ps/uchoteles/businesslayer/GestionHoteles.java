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



@Stateless
@Local({IConsultaHoteles.class, IGestionHoteles.class})
public class GestionHoteles implements IConsultaHoteles, IGestionHoteles {



    @EJB
    private IHotelesDAO hotelesDAO;

    @EJB
    private IReservasDAO reservasDAO;


    @Override
    public Hotel buscarHotelPorNombre(String nombre) throws DataAccessException {
        return hotelesDAO.hotelPorNombre(nombre);

    }

    @Override
    public List<Hotel> buscarHotelesPorLocalidad(String localidad) throws DataAccessException {
        return hotelesDAO.hotelesPorLocalidad(localidad);
    }

    public static final int MINUTOS_BLOQUEO = 10;

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


    private boolean seSolapa(Reserva r, LocalDate entrada, LocalDate salida) {
        return r.getFechaEntrada().isBefore(salida) && r.getFechaSalida().isAfter(entrada);
    }

    private boolean estaVigente(Reserva r, LocalDateTime limiteBloqueo) {
        if (r.getEstado() == EstadoReserva.CONFIRMADA) {
            return true;
        }
        return r.getFechaBloqueo() != null && r.getFechaBloqueo().isAfter(limiteBloqueo);
    }

    @Override
    public Hotel consultarHotel(String nombre) throws DataAccessException, OperacionNoValidaException {
        Hotel hotel = hotelesDAO.hotelPorNombre(nombre);
        if (hotel == null) {
            throw new OperacionNoValidaException("Hotel no encontrado");
        }
        return hotel;
    }

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

    private void validarDatos(Habitacion h) throws OperacionNoValidaException {
        if (h.getPrecioPorNoche() <= 0) {
            throw new OperacionNoValidaException("El precio por noche debe ser mayor que 0");
        }
        if (h.getDisponibles() < 0) {
            throw new OperacionNoValidaException("El número de habitaciones no puede ser negativo");
        }
    }
}
