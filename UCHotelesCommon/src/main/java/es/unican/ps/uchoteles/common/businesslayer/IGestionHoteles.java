package es.unican.ps.uchoteles.common.businesslayer;

import es.unican.ps.uchoteles.common.domain.Habitacion;
import es.unican.ps.uchoteles.common.domain.Hotel;
import es.unican.ps.uchoteles.common.domain.TipoHabitacion;
import es.unican.ps.uchoteles.common.exceptions.DataAccessException;
import es.unican.ps.uchoteles.common.exceptions.OperacionNoValidaException;

import java.util.List;

/** Gestión de un hotel y de sus tipos de habitación que hace el gerente (GerenteWeb). */
public interface IGestionHoteles {

    /**
     * Devuelve el hotel con el nombre indicado.
     *
     * @param nombre nombre del hotel
     * @return el hotel
     * @throws DataAccessException        si falla el acceso a la base de datos
     * @throws OperacionNoValidaException si el hotel no existe
     */
    Hotel consultarHotel(String nombre)
            throws DataAccessException, OperacionNoValidaException;

    /**
     * Modifica el precio por noche y el número de habitaciones disponibles de uno o varios tipos de habitación.
     *
     * @param hotel        hotel que se modifica
     * @param habitaciones tipos de habitación con los datos nuevos
     * @return el hotel actualizado
     * @throws DataAccessException        si falla el acceso a la base de datos
     * @throws OperacionNoValidaException si el hotel no tiene alguno de los tipos o los datos no son válidos
     */
    Hotel modificarHabitaciones(Hotel hotel, List<Habitacion> habitaciones)
            throws DataAccessException, OperacionNoValidaException;

    /**
     * Añade un nuevo tipo de habitación al hotel.
     *
     * @param hotel      hotel al que se añade
     * @param habitacion tipo de habitación nuevo, con su precio y su número de habitaciones
     * @return el hotel actualizado
     * @throws DataAccessException        si falla el acceso a la base de datos
     * @throws OperacionNoValidaException si el hotel ya tiene ese tipo o los datos no son válidos
     */
    Hotel anhadirTipoHabitacion(Hotel hotel, Habitacion habitacion)
            throws DataAccessException, OperacionNoValidaException;

    /**
     * Elimina un tipo de habitación del hotel.
     *
     * @param hotel hotel del que se elimina
     * @param tipo  tipo de habitación que se elimina
     * @return el hotel actualizado
     * @throws DataAccessException        si falla el acceso a la base de datos
     * @throws OperacionNoValidaException si el hotel no tiene ese tipo
     */
    Hotel eliminarTipoHabitacion(Hotel hotel, TipoHabitacion tipo)
            throws DataAccessException, OperacionNoValidaException;
}
