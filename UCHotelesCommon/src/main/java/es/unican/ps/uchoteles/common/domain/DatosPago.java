package es.unican.ps.uchoteles.common.domain;

/** Datos de la tarjeta con la que se paga una reserva. */
public class DatosPago {
    /** El número de la tarjeta. */
    private String numTarjeta;

    /** El código de seguridad (CVC) de la tarjeta. */
    private String cvc;

    /** El mes de caducidad de la tarjeta (de 1 a 12). */
    private int mesCaducidad;

    /** El año de caducidad de la tarjeta. */
    private int anhoCaducidad;

    /** El tipo de tarjeta. */
    private TipoTarjeta tipo;

    /**
     * Crea unos datos de pago vacíos. Lo necesita JPA para crear el objeto al leerlo de la base de datos.
     */
    public DatosPago() {
    }

    /**
     * Crea los datos de pago de una reserva.
     *
     * @param numTarjeta    número de la tarjeta
     * @param cvc           código de seguridad de la tarjeta
     * @param mesCaducidad  mes de caducidad (de 1 a 12)
     * @param anhoCaducidad año de caducidad
     * @param tipo          tipo de tarjeta
     */
    public DatosPago(String numTarjeta, String cvc, int mesCaducidad, int anhoCaducidad, TipoTarjeta tipo) {
        this.numTarjeta = numTarjeta;
        this.cvc = cvc;
        this.mesCaducidad = mesCaducidad;
        this.anhoCaducidad = anhoCaducidad;
        this.tipo = tipo;
    }

    /**
     * Devuelve el número de la tarjeta.
     *
     * @return el número de la tarjeta
     */
    public String getNumTarjeta() {
        return numTarjeta;
    }

    /**
     * Establece el número de la tarjeta.
     *
     * @param numTarjeta el número de la tarjeta
     */
    public void setNumTarjeta(String numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    /**
     * Devuelve el tipo de tarjeta.
     *
     * @return el tipo de tarjeta
     */
    public TipoTarjeta getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de tarjeta.
     *
     * @param tipo el tipo de tarjeta
     */
    public void setTipo(TipoTarjeta tipo) {
        this.tipo = tipo;
    }

    /**
     * Devuelve el año de caducidad de la tarjeta.
     *
     * @return el año de caducidad de la tarjeta
     */
    public int getAnhoCaducidad() {
        return anhoCaducidad;
    }

    /**
     * Establece el año de caducidad de la tarjeta.
     *
     * @param anhoCaducidad el año de caducidad de la tarjeta
     */
    public void setAnhoCaducidad(int anhoCaducidad) {
        this.anhoCaducidad = anhoCaducidad;
    }

    /**
     * Devuelve el mes de caducidad de la tarjeta (de 1 a 12).
     *
     * @return el mes de caducidad de la tarjeta (de 1 a 12)
     */
    public int getMesCaducidad() {
        return mesCaducidad;
    }

    /**
     * Establece el mes de caducidad de la tarjeta (de 1 a 12).
     *
     * @param mesCaducidad el mes de caducidad de la tarjeta (de 1 a 12)
     */
    public void setMesCaducidad(int mesCaducidad) {
        this.mesCaducidad = mesCaducidad;
    }

    /**
     * Devuelve el código de seguridad (CVC) de la tarjeta.
     *
     * @return el código de seguridad (CVC) de la tarjeta
     */
    public String getCvc() {
        return cvc;
    }

    /**
     * Establece el código de seguridad (CVC) de la tarjeta.
     *
     * @param cvc el código de seguridad (CVC) de la tarjeta
     */
    public void setCvc(String cvc) {
        this.cvc = cvc;
    }
}
