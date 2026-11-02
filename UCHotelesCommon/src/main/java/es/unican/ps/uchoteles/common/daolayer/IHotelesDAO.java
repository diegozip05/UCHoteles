package es.unican.ps.uchoteles.common.daolayer;

import es.unican.ps.uchoteles.common.domain.Hotel;
import es.unican.ps.uchoteles.common.exceptions.DataAccessException;

import java.util.List;

public interface IHotelesDAO {

    Hotel crearHotel(Hotel h) throws DataAccessException;

    Hotel hotelPorNombre(String nombre) throws DataAccessException;

    List<Hotel> hotelesPorLocalidad(String localidad) throws DataAccessException;

    Hotel modificarHotel(Hotel h) throws DataAccessException;

    Hotel eliminarHotel(Hotel h) throws DataAccessException;
}