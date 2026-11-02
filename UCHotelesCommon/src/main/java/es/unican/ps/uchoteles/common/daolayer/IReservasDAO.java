package es.unican.ps.uchoteles.common.daolayer;

import es.unican.ps.uchoteles.common.domain.Hotel;
import es.unican.ps.uchoteles.common.domain.Reserva;
import es.unican.ps.uchoteles.common.exceptions.DataAccessException;

import java.util.List;

public interface IReservasDAO {

    Reserva crearReserva(Reserva r) throws DataAccessException;

    Reserva reservaPorId(Long id) throws DataAccessException;

    Reserva modificarReserva(Reserva r) throws DataAccessException;

    Reserva eliminarReserva(Reserva r) throws DataAccessException;

    List<Reserva> reservasPorHotel(Hotel h) throws DataAccessException;
}