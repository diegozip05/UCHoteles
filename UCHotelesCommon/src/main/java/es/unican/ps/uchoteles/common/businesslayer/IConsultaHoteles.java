package es.unican.ps.uchoteles.common.businesslayer;

import es.unican.ps.uchoteles.common.domain.Habitacion;
import es.unican.ps.uchoteles.common.domain.Hotel;
import es.unican.ps.uchoteles.common.exceptions.DataAccessException;
import es.unican.ps.uchoteles.common.exceptions.OperacionNoValidaException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/** Consultas de hoteles que hace el usuario (ReservasWeb). */
public interface IConsultaHoteles {

    /**
     * Busca un hotel por su nombre.
     *
     * @param nombre nombre del hotel
     * @return el hotel, o null si no existe
     * @throws DataAccessException si falla el acceso a la base de datos
     */
    Hotel buscarHotelPorNombre(String nombre) throws DataAccessException;

    /**
     * Busca los hoteles de una localidad.
     *
     * @param localidad localidad buscada
     * @return los hoteles de esa localidad (lista vacía si no hay ninguno)
     * @throws DataAccessException si falla el acceso a la base de datos
     */
    List<Hotel> buscarHotelesPorLocalidad(String localidad) throws DataAccessException;

    /**
     * Calcula cuántas habitaciones de cada tipo quedan libres en un hotel entre dos fechas.
     *
     * @param hotel   hotel consultado
     * @param entrada fecha de entrada
     * @param salida  fecha de salida
     * @return para cada tipo de habitación del hotel, el número de habitaciones libres
     * @throws DataAccessException        si falla el acceso a la base de datos
     * @throws OperacionNoValidaException si las fechas no son válidas o no queda ninguna habitación libre
     */
    Map<Habitacion, Integer> consultarDisponibilidad(Hotel hotel, LocalDate entrada, LocalDate salida)
            throws DataAccessException, OperacionNoValidaException;
}
