package es.unican.ps.uchoteles.daolayer;

import es.unican.ps.uchoteles.common.daolayer.IReservasDAO;
import es.unican.ps.uchoteles.common.domain.Hotel;
import es.unican.ps.uchoteles.common.domain.Reserva;
import es.unican.ps.uchoteles.common.domain.ReservaTipoHabitacion;
import es.unican.ps.uchoteles.common.exceptions.DataAccessException;
import jakarta.ejb.Local;
import jakarta.ejb.Singleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación provisional de {@link IReservasDAO} que guarda las reservas en memoria.
 * Se sustituirá por una implementación con JPA en la práctica 3.3.
 */
@Singleton
@Local(IReservasDAO.class)
public class ReservasDAO implements IReservasDAO {

    /** Reservas guardadas. */
    private final List<Reserva> reservas = new ArrayList<>();

    /** Siguiente identificador que se asignará a una reserva o a una de sus líneas. */
    private long siguienteId = 1;

    @Override
    public Reserva crearReserva(Reserva r) throws DataAccessException {
        asignarIds(r);
        reservas.add(r);
        return r;
    }

    @Override
    public Reserva reservaPorId(Long id) throws DataAccessException {
        for (Reserva r : reservas) {
            if (r.getId().equals(id)) {
                return r;
            }
        }
        return null;
    }

    @Override
    public Reserva modificarReserva(Reserva r) throws DataAccessException {
        Reserva guardada = reservaPorId(r.getId());
        if (guardada == null) {
            return null;
        }
        asignarIds(r);
        reservas.set(reservas.indexOf(guardada), r);
        return r;
    }

    @Override
    public Reserva eliminarReserva(Reserva r) throws DataAccessException {
        Reserva guardada = reservaPorId(r.getId());
        if (guardada != null) {
            reservas.remove(guardada);
        }
        return guardada;
    }

    @Override
    public List<Reserva> reservasPorHotel(Hotel h) throws DataAccessException {
        List<Reserva> resultado = new ArrayList<>();
        for (Reserva r : reservas) {
            if (r.getHotel() != null && r.getHotel().getId().equals(h.getId())) {
                resultado.add(r);
            }
        }
        return resultado;
    }

    /**
     * Asigna identificador a la reserva y a sus líneas si todavía no lo tienen,
     * igual que haría la base de datos al guardarlas.
     *
     * @param r reserva
     */
    private void asignarIds(Reserva r) {
        if (r.getId() == null) {
            r.setId(siguienteId++);
        }
        for (ReservaTipoHabitacion rth : r.getReservasPorTipo()) {
            if (rth.getId() == null) {
                rth.setId(siguienteId++);
            }
        }
    }
}
