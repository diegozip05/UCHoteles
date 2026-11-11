package es.unican.ps.uchoteles.common.daolayer;

import es.unican.ps.uchoteles.common.domain.Hotel;
import es.unican.ps.uchoteles.common.exceptions.DataAccessException;

import java.util.List;

/** Acceso a los datos de los hoteles. Las habitaciones se guardan junto con su hotel. */
public interface IHotelesDAO {

    /**
     * Guarda un hotel nuevo.
     *
     * @param h hotel que se guarda
     * @return el hotel guardado, con su identificador
     * @throws DataAccessException si falla el acceso a la base de datos
     */
    Hotel crearHotel(Hotel h) throws DataAccessException;

    /**
     * Busca un hotel por su nombre.
     *
     * @param nombre nombre del hotel
     * @return el hotel, o null si no existe
     * @throws DataAccessException si falla el acceso a la base de datos
     */
    Hotel hotelPorNombre(String nombre) throws DataAccessException;

    /**
     * Busca los hoteles de una localidad.
     *
     * @param localidad localidad buscada
     * @return los hoteles de esa localidad (lista vacía si no hay ninguno)
     * @throws DataAccessException si falla el acceso a la base de datos
     */
    List<Hotel> hotelesPorLocalidad(String localidad) throws DataAccessException;

    /**
     * Guarda los cambios de un hotel y de sus habitaciones.
     *
     * @param h hotel modificado
     * @return el hotel actualizado
     * @throws DataAccessException si falla el acceso a la base de datos
     */
    Hotel modificarHotel(Hotel h) throws DataAccessException;

    /**
     * Elimina un hotel.
     *
     * @param h hotel que se elimina
     * @return el hotel eliminado, o null si no existía
     * @throws DataAccessException si falla el acceso a la base de datos
     */
    Hotel eliminarHotel(Hotel h) throws DataAccessException;
}
