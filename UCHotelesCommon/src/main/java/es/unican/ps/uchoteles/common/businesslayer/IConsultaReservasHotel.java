package es.unican.ps.uchoteles.common.businesslayer;

import es.unican.ps.uchoteles.common.domain.Reserva;
import es.unican.ps.uchoteles.common.exceptions.DataAccessException;
import es.unican.ps.uchoteles.common.exceptions.OperacionNoValidaException;

import java.util.List;

/** Consulta de las reservas de un hotel que hace el gerente (GerenteWeb). */
public interface IConsultaReservasHotel {
    /**
     * Devuelve las reservas confirmadas de un hotel ordenadas por fecha de entrada.
     *
     * @param nombreHotel nombre del hotel
     * @return las reservas del hotel (lista vacía si no tiene ninguna)
     * @throws DataAccessException        si falla el acceso a la base de datos
     * @throws OperacionNoValidaException si el hotel no existe
     */
    List<Reserva> consultarReservasHotel(String nombreHotel)
            throws DataAccessException, OperacionNoValidaException;
}
