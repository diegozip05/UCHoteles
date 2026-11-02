package es.unican.ps.uchoteles.common.businesslayer;

import es.unican.ps.uchoteles.common.domain.Reserva;
import es.unican.ps.uchoteles.common.exceptions.DataAccessException;
import es.unican.ps.uchoteles.common.exceptions.OperacionNoValidaException;

import java.util.List;

public interface IConsultaReservasHotel {
    List<Reserva> consultarReservasHotel(String nombreHotel)
            throws DataAccessException, OperacionNoValidaException;}
