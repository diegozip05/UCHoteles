package es.unican.ps.uchoteles.common.domain;

public class DatosCliente {

    private String dni;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String email;

    // Constructor vacío: lo necesitará JPA en la 3.3
    public DatosCliente() {
    }

    // Constructor completo: útil para crear clientes en los tests
    public DatosCliente(String dni, String nombre, String primerApellido,
                        String segundoApellido, String email) {
        this.dni = dni;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.email = email;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}