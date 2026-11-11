package es.unican.ps.uchoteles.common.exceptions;

/**
 * Se incumple una regla de negocio. La lanza la capa de negocio con el mensaje
 * de error que se debe mostrar al usuario.
 */
public class OperacionNoValidaException extends Exception {

    /** Crea la excepción sin mensaje. */
    public OperacionNoValidaException() {
        super();
    }

    /**
     * Crea la excepción con un mensaje.
     *
     * @param mensaje mensaje de error para el usuario
     */
    public OperacionNoValidaException(String mensaje) {
        super(mensaje);
    }

    /**
     * Crea la excepción con un mensaje y la excepción que la ha causado.
     *
     * @param mensaje mensaje de error para el usuario
     * @param causa   excepción original
     */
    public OperacionNoValidaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
