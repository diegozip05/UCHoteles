package es.unican.ps.uchoteles.common.exceptions;

public class DataAccessException extends Exception {

    public DataAccessException() {
        super();
    }

    public DataAccessException(String mensaje) {
        super(mensaje);
    }

    public DataAccessException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}