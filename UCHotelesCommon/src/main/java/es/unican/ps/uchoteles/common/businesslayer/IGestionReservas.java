package es.unican.ps.uchoteles.common.businesslayer;

import es.unican.ps.uchoteles.common.domain.*;
import es.unican.ps.uchoteles.common.exceptions.DataAccessException;
import es.unican.ps.uchoteles.common.exceptions.OperacionNoValidaException;

import java.time.LocalDate;
import java.util.Map;

/** Gestión de reservas que hace el usuario (ReservasWeb). */
public interface IGestionReservas {
    /**
     * Comprueba que hay habitaciones suficientes y crea la reserva bloqueada durante 10 minutos.
     *
     * @param hotel     hotel en el que se reserva
     * @param entrada   fecha de entrada
     * @param salida    fecha de salida
     * @param seleccion número de habitaciones que se quieren de cada tipo
     * @return la reserva creada, en estado BLOQUEADA
     * @throws DataAccessException        si falla el acceso a la base de datos
     * @throws OperacionNoValidaException si no hay habitaciones suficientes de algún tipo
     */
    Reserva reservar(Hotel hotel, LocalDate entrada, LocalDate salida, Map<TipoHabitacion, Integer> seleccion)
            throws DataAccessException, OperacionNoValidaException;

    /**
     * Guarda los datos del cliente y del pago y confirma la reserva.
     *
     * @param idReserva identificador de la reserva bloqueada
     * @param cliente   datos del cliente
     * @param pago      datos de pago
     * @return el identificador de la reserva, que se muestra al usuario
     * @throws DataAccessException        si falla el acceso a la base de datos
     * @throws OperacionNoValidaException si la reserva no existe, ya está confirmada o han pasado los 10 minutos
     */
    Long confirmarReserva(Long idReserva, DatosCliente cliente, DatosPago pago)
            throws DataAccessException, OperacionNoValidaException;

    /**
     * Devuelve una reserva si el DNI coincide con el del cliente que la hizo.
     *
     * @param idReserva identificador de la reserva
     * @param dni       DNI del cliente
     * @return la reserva
     * @throws DataAccessException        si falla el acceso a la base de datos
     * @throws OperacionNoValidaException si la reserva no existe o no corresponde a ese DNI
     */
    Reserva consultarReserva(Long idReserva, String dni)
            throws DataAccessException, OperacionNoValidaException;

    /**
     * Cancela una reserva si faltan 2 días o más para la fecha de entrada.
     *
     * @param idReserva identificador de la reserva
     * @param dni       DNI del cliente
     * @throws DataAccessException        si falla el acceso a la base de datos
     * @throws OperacionNoValidaException si la reserva no es accesible o se ha pasado el límite de cancelación
     */
    void cancelarReserva(Long idReserva, String dni) throws DataAccessException, OperacionNoValidaException;
}
