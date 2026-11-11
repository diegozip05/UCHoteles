package es.unican.ps.uchoteles.common.domain;

/** Estado en el que se encuentra una reserva. */
public enum EstadoReserva {
    /**
     * Las habitaciones están retenidas durante 10 minutos mientras el cliente introduce sus datos.
     */
    BLOQUEADA,

    /** El cliente ha introducido sus datos y la reserva es definitiva. */
    CONFIRMADA
}
