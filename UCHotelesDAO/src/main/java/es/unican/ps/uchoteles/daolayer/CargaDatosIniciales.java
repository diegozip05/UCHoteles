package es.unican.ps.uchoteles.daolayer;

import es.unican.ps.uchoteles.common.daolayer.IHotelesDAO;
import es.unican.ps.uchoteles.common.daolayer.IReservasDAO;
import es.unican.ps.uchoteles.common.domain.Categoria;
import es.unican.ps.uchoteles.common.domain.DatosCliente;
import es.unican.ps.uchoteles.common.domain.DatosPago;
import es.unican.ps.uchoteles.common.domain.EstadoReserva;
import es.unican.ps.uchoteles.common.domain.Habitacion;
import es.unican.ps.uchoteles.common.domain.Hotel;
import es.unican.ps.uchoteles.common.domain.Reserva;
import es.unican.ps.uchoteles.common.domain.ReservaTipoHabitacion;
import es.unican.ps.uchoteles.common.domain.TipoHabitacion;
import es.unican.ps.uchoteles.common.domain.TipoTarjeta;
import es.unican.ps.uchoteles.common.exceptions.DataAccessException;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Carga en la base de datos los hoteles y reservas de ejemplo (Anexo 1 del plan de pruebas)
 * al desplegar la aplicación, si todavía no están.
 */
@Singleton
@Startup
public class CargaDatosIniciales {

    /** DAO de hoteles. */
    @EJB
    private IHotelesDAO hotelesDAO;

    /** DAO de reservas. */
    @EJB
    private IReservasDAO reservasDAO;

    /** Crea los datos de ejemplo si la base de datos todavía no los tiene. */
    @PostConstruct
    public void cargar() {
        try {
            if (hotelesDAO.hotelPorNombre("Bahía") != null) {
                return;
            }

            Hotel bahia = new Hotel("Bahía", "Calle Castelar 1", "Santander", Categoria.FOUR_STARS);
            bahia.getHabitaciones().add(new Habitacion(TipoHabitacion.DOBLE, 100, 5, bahia));
            bahia.getHabitaciones().add(new Habitacion(TipoHabitacion.INDIVIDUAL, 50, 3, bahia));
            hotelesDAO.crearHotel(bahia);

            Hotel mirador = new Hotel("Mirador", "Avenida de la Playa 3", "Laredo", Categoria.THREE_STARS);
            mirador.getHabitaciones().add(new Habitacion(TipoHabitacion.DOBLE, 80, 4, mirador));
            hotelesDAO.crearHotel(mirador);

            Habitacion doble = bahia.getHabitacion(TipoHabitacion.DOBLE);
            Habitacion individual = bahia.getHabitacion(TipoHabitacion.INDIVIDUAL);

            Reserva r1 = new Reserva(LocalDate.of(2026, 12, 11), LocalDate.of(2026, 12, 13),
                    EstadoReserva.CONFIRMADA, LocalDateTime.now(), bahia,
                    new DatosCliente("11111111A", "Ana", "García", "López", "ana@ejemplo.com"),
                    new DatosPago("4111111111111111", "123", 12, 2028, TipoTarjeta.VISA));
            r1.getReservasPorTipo().add(new ReservaTipoHabitacion(2, doble, r1));
            bahia.getReservas().add(r1);
            reservasDAO.crearReserva(r1);

            Reserva r2 = new Reserva(LocalDate.of(2026, 12, 20), LocalDate.of(2026, 12, 24),
                    EstadoReserva.CONFIRMADA, LocalDateTime.now(), bahia,
                    new DatosCliente("22222222B", "Luis", "Pérez", "Ruiz", "luis@ejemplo.com"),
                    new DatosPago("5500000000000004", "456", 6, 2029, TipoTarjeta.MASTERCARD));
            r2.getReservasPorTipo().add(new ReservaTipoHabitacion(5, doble, r2));
            r2.getReservasPorTipo().add(new ReservaTipoHabitacion(3, individual, r2));
            bahia.getReservas().add(r2);
            reservasDAO.crearReserva(r2);

        } catch (DataAccessException e) {
            throw new IllegalStateException("No se pudieron cargar los datos iniciales", e);
        }
    }
}