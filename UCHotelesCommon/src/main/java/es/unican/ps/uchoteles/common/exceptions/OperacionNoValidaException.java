package es.unican.ps.uchoteles.common.exceptions;

public class OperacionNoValidaException extends Exception {

    public OperacionNoValidaException() {
        super();
    }

    public OperacionNoValidaException(String mensaje) {
        super(mensaje);
    }

    public OperacionNoValidaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}