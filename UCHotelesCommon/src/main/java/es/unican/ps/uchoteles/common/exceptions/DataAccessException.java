package es.unican.ps.uchoteles.common.exceptions;

/** Error técnico al acceder a la base de datos. La lanza la capa DAO. */
public class DataAccessException extends Exception {

    /** Crea la excepción sin mensaje. */
    public DataAccessException() {
        super();
    }

    /**
     * Crea la excepción con un mensaje.
     *
     * @param mensaje descripción del error
     */
    public DataAccessException(String mensaje) {
        super(mensaje);
    }

    /**
     * Crea la excepción con un mensaje y la excepción que la ha causado.
     *
     * @param mensaje descripción del error
     * @param causa   excepción original
     */
    public DataAccessException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
