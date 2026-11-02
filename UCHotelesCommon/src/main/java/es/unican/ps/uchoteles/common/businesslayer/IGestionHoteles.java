package es.unican.ps.uchoteles.common.businesslayer;

import es.unican.ps.uchoteles.common.domain.Habitacion;
import es.unican.ps.uchoteles.common.domain.Hotel;
import es.unican.ps.uchoteles.common.domain.TipoHabitacion;
import es.unican.ps.uchoteles.common.exceptions.DataAccessException;
import es.unican.ps.uchoteles.common.exceptions.OperacionNoValidaException;

import java.util.List;

public interface IGestionHoteles {


    Hotel consultarHotel(String nombre)
            throws DataAccessException, OperacionNoValidaException;


    Hotel modificarHabitaciones(Hotel hotel, List<Habitacion> habitaciones)
            throws DataAccessException, OperacionNoValidaException;
    Hotel anhadirTipoHabitacion(Hotel hotel, Habitacion habitacion)
            throws DataAccessException, OperacionNoValidaException;

    Hotel eliminarTipoHabitacion(Hotel hotel, TipoHabitacion tipo)
            throws DataAccessException, OperacionNoValidaException;
}