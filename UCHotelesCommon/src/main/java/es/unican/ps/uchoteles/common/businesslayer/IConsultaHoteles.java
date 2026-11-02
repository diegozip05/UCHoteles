package es.unican.ps.uchoteles.common.businesslayer;

import es.unican.ps.uchoteles.common.domain.Habitacion;
import es.unican.ps.uchoteles.common.domain.Hotel;
import es.unican.ps.uchoteles.common.exceptions.DataAccessException;
import es.unican.ps.uchoteles.common.exceptions.OperacionNoValidaException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IConsultaHoteles {

    Hotel buscarHotelPorNombre(String nombre) throws DataAccessException;

    List<Hotel> buscarHotelesPorLocalidad(String localidad) throws DataAccessException;

    Map<Habitacion, Integer> consultarDisponibilidad(Hotel hotel, LocalDate entrada, LocalDate salida)
            throws DataAccessException, OperacionNoValidaException;
}