package es.unican.ps.uchoteles.common.businesslayer;

import es.unican.ps.uchoteles.common.domain.*;
import es.unican.ps.uchoteles.common.exceptions.DataAccessException;
import es.unican.ps.uchoteles.common.exceptions.OperacionNoValidaException;

import java.time.LocalDate;
import java.util.Map;

public interface IGestionReservas {
    Reserva reservar(Hotel hotel, LocalDate entrada, LocalDate salida, Map<TipoHabitacion,Integer> seleccion)
            throws DataAccessException, OperacionNoValidaException;
    Long confirmarReserva(Long idReserva, DatosCliente cliente, DatosPago pago)
            throws DataAccessException,OperacionNoValidaException;
    Reserva consultarReserva(Long idReserva,String dni)
            throws DataAccessException, OperacionNoValidaException;
    void cancelarReserva(Long idReserva,String dni) throws DataAccessException,OperacionNoValidaException;
}
