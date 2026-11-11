package es.unican.ps.uchoteles.common.domain;

/** Datos personales del cliente que hace una reserva. */
public class DatosCliente {

    /** El DNI del cliente. */
    private String dni;

    /** El nombre del cliente. */
    private String nombre;

    /** El primer apellido del cliente. */
    private String primerApellido;

    /** El segundo apellido del cliente. */
    private String segundoApellido;

    /** El correo electrónico del cliente. */
    private String email;

    /**
     * Crea unos datos de cliente vacíos. Lo necesita JPA para crear el objeto al leerlo de la base de datos.
     */
    public DatosCliente() {
    }

    /**
     * Crea los datos de un cliente.
     *
     * @param dni             DNI del cliente
     * @param nombre          nombre del cliente
     * @param primerApellido  primer apellido
     * @param segundoApellido segundo apellido
     * @param email           correo electrónico
     */
    public DatosCliente(String dni, String nombre, String primerApellido,
                        String segundoApellido, String email) {
        this.dni = dni;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.email = email;
    }

    /**
     * Devuelve el DNI del cliente.
     *
     * @return el DNI del cliente
     */
    public String getDni() {
        return dni;
    }

    /**
     * Establece el DNI del cliente.
     *
     * @param dni el DNI del cliente
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Devuelve el nombre del cliente.
     *
     * @return el nombre del cliente
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del cliente.
     *
     * @param nombre el nombre del cliente
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el primer apellido del cliente.
     *
     * @return el primer apellido del cliente
     */
    public String getPrimerApellido() {
        return primerApellido;
    }

    /**
     * Establece el primer apellido del cliente.
     *
     * @param primerApellido el primer apellido del cliente
     */
    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    /**
     * Devuelve el segundo apellido del cliente.
     *
     * @return el segundo apellido del cliente
     */
    public String getSegundoApellido() {
        return segundoApellido;
    }

    /**
     * Establece el segundo apellido del cliente.
     *
     * @param segundoApellido el segundo apellido del cliente
     */
    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    /**
     * Devuelve el correo electrónico del cliente.
     *
     * @return el correo electrónico del cliente
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del cliente.
     *
     * @param email el correo electrónico del cliente
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
