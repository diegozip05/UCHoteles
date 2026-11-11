package es.unican.ps.uchoteles.common.daolayer;

import es.unican.ps.uchoteles.common.domain.Hotel;
import es.unican.ps.uchoteles.common.domain.Reserva;
import es.unican.ps.uchoteles.common.exceptions.DataAccessException;

import java.util.List;

/** Acceso a los datos de las reservas. Las líneas de cada reserva se guardan junto con ella. */
public interface IReservasDAO {

    /**
     * Guarda una reserva nueva.
     *
     * @param r reserva que se guarda
     * @return la reserva guardada, con su identificador
     * @throws DataAccessException si falla el acceso a la base de datos
     */
    Reserva crearReserva(Reserva r) throws DataAccessException;

    /**
     * Busca una reserva por su identificador.
     *
     * @param id identificador de la reserva
     * @return la reserva, o null si no existe
     * @throws DataAccessException si falla el acceso a la base de datos
     */
    Reserva reservaPorId(Long id) throws DataAccessException;

    /**
     * Guarda los cambios de una reserva.
     *
     * @param r reserva modificada
     * @return la reserva actualizada
     * @throws DataAccessException si falla el acceso a la base de datos
     */
    Reserva modificarReserva(Reserva r) throws DataAccessException;

    /**
     * Elimina una reserva.
     *
     * @param r reserva que se elimina
     * @return la reserva eliminada, o null si no existía
     * @throws DataAccessException si falla el acceso a la base de datos
     */
    Reserva eliminarReserva(Reserva r) throws DataAccessException;

    /**
     * Devuelve todas las reservas de un hotel, en cualquier estado. No filtra por fechas ni por estado.
     *
     * @param h hotel
     * @return las reservas del hotel (lista vacía si no tiene ninguna)
     * @throws DataAccessException si falla el acceso a la base de datos
     */
    List<Reserva> reservasPorHotel(Hotel h) throws DataAccessException;
}
