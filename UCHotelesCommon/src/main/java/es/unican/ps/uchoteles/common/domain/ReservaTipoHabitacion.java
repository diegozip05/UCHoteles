package es.unican.ps.uchoteles.common.domain;

public class ReservaTipoHabitacion {

    private Long id;
    private int numHabitaciones;
    private double precioPorNoche;    // Precio por noche en el momento de reservar (no cambia aunque el hotel lo modifique)

    private Habitacion habitacion;
    private Reserva reserva;

    public ReservaTipoHabitacion() {
    }

    public ReservaTipoHabitacion(int numHabitaciones, Habitacion habitacion, Reserva reserva) {
        this.numHabitaciones = numHabitaciones;
        this.habitacion = habitacion;
        this.reserva = reserva;
        // "Foto" del precio actual de la habitación
        this.precioPorNoche = habitacion.getPrecioPorNoche();
    }

    public double getImporte(long noches) {
        return numHabitaciones * precioPorNoche * noches;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumHabitaciones() {
        return numHabitaciones;
    }

    public void setNumHabitaciones(int numHabitaciones) {
        this.numHabitaciones = numHabitaciones;
    }

    public double getPrecioPorNoche() {
        return precioPorNoche;
    }

    public void setPrecioPorNoche(double precioPorNoche) {
        this.precioPorNoche = precioPorNoche;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }
}