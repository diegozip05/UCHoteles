package es.unican.ps.uchoteles.businesslayer;

import es.unican.ps.uchoteles.common.daolayer.IHotelesDAO;
import es.unican.ps.uchoteles.common.daolayer.IReservasDAO;
import es.unican.ps.uchoteles.common.domain.Categoria;
import es.unican.ps.uchoteles.common.domain.EstadoReserva;
import es.unican.ps.uchoteles.common.domain.Habitacion;
import es.unican.ps.uchoteles.common.domain.Hotel;
import es.unican.ps.uchoteles.common.domain.Reserva;
import es.unican.ps.uchoteles.common.domain.ReservaTipoHabitacion;
import es.unican.ps.uchoteles.common.domain.TipoHabitacion;
import es.unican.ps.uchoteles.common.exceptions.OperacionNoValidaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * Pruebas unitarias de {@link GestionHoteles#consultarDisponibilidad} (Tabla 2 del plan de pruebas).
 * Los DAO se sustituyen por mocks de Mockito. Todos los casos usan el hotel Bahía, con 5 dobles y
 * 3 individuales, y salvo que se indique otra cosa la consulta es del 10/12/2026 al 12/12/2026.
 */
@ExtendWith(MockitoExtension.class)
class GestionHotelesTest {

    /** DAO de reservas simulado. */
    @Mock
    private IReservasDAO reservasDAO;

    /** DAO de hoteles simulado. */
    @Mock
    private IHotelesDAO hotelesDAO;

    /** Componente que se prueba, con los mocks inyectados. */
    @InjectMocks
    private GestionHoteles gestionHoteles;

    /** Hotel Bahía. */
    private Hotel hotel;

    /** Habitación doble del hotel (5 disponibles). */
    private Habitacion doble;

    /** Habitación individual del hotel (3 disponibles). */
    private Habitacion individual;

    /** Fecha de entrada de la consulta. */
    private final LocalDate entrada = LocalDate.of(2026, 12, 10);

    /** Fecha de salida de la consulta. */
    private final LocalDate salida = LocalDate.of(2026, 12, 12);

    /** Crea un hotel nuevo antes de cada prueba para que sean independientes. */
    @BeforeEach
    void setUp() {
        hotel = new Hotel("Bahía", "Calle Ejemplo 1", "Santander", Categoria.FOUR_STARS);
        doble = new Habitacion(TipoHabitacion.DOBLE, 100, 5, hotel);
        individual = new Habitacion(TipoHabitacion.INDIVIDUAL, 50, 3, hotel);
        hotel.getHabitaciones().add(doble);
        hotel.getHabitaciones().add(individual);
    }

    /**
     * Crea una reserva del hotel Bahía en diciembre de 2026.
     *
     * @param diaEntrada      día de entrada
     * @param diaSalida       día de salida
     * @param estado          estado de la reserva
     * @param minutosBloqueo  hace cuántos minutos se bloqueó
     * @param numDobles       habitaciones dobles reservadas
     * @param numIndividuales habitaciones individuales reservadas
     * @return la reserva
     */
    private Reserva crearReserva(int diaEntrada, int diaSalida, EstadoReserva estado,
                                 int minutosBloqueo, int numDobles, int numIndividuales) {
        Reserva r = new Reserva(LocalDate.of(2026, 12, diaEntrada), LocalDate.of(2026, 12, diaSalida),
                estado, LocalDateTime.now().minusMinutes(minutosBloqueo), hotel, null, null);
        if (numDobles > 0) {
            r.getReservasPorTipo().add(new ReservaTipoHabitacion(numDobles, doble, r));
        }
        if (numIndividuales > 0) {
            r.getReservasPorTipo().add(new ReservaTipoHabitacion(numIndividuales, individual, r));
        }
        return r;
    }

    /**
     * UGH.1a: sin reservas, todas las habitaciones están libres.
     *
     * @throws Exception si falla la consulta
     */
    @Test
    @DisplayName("UGH.1a Sin reservas")
    void sinReservas() throws Exception {
        when(reservasDAO.reservasPorHotel(hotel)).thenReturn(List.of());

        Map<Habitacion, Integer> libres = gestionHoteles.consultarDisponibilidad(hotel, entrada, salida);

        assertEquals(5, libres.get(doble));
        assertEquals(3, libres.get(individual));
    }

    /**
     * UGH.1b: una reserva confirmada que se solapa resta sus habitaciones.
     *
     * @throws Exception si falla la consulta
     */
    @Test
    @DisplayName("UGH.1b Reserva confirmada que se solapa")
    void reservaQueSeSolapa() throws Exception {
        Reserva r = crearReserva(11, 13, EstadoReserva.CONFIRMADA, 0, 2, 0);
        when(reservasDAO.reservasPorHotel(hotel)).thenReturn(List.of(r));

        Map<Habitacion, Integer> libres = gestionHoteles.consultarDisponibilidad(hotel, entrada, salida);

        assertEquals(3, libres.get(doble));
        assertEquals(3, libres.get(individual));
    }

    /**
     * UGH.1c: una reserva confirmada de otras fechas no cuenta.
     *
     * @throws Exception si falla la consulta
     */
    @Test
    @DisplayName("UGH.1c Reserva confirmada que no se solapa")
    void reservaQueNoSeSolapa() throws Exception {
        Reserva r = crearReserva(1, 5, EstadoReserva.CONFIRMADA, 0, 2, 0);
        when(reservasDAO.reservasPorHotel(hotel)).thenReturn(List.of(r));

        Map<Habitacion, Integer> libres = gestionHoteles.consultarDisponibilidad(hotel, entrada, salida);

        assertEquals(5, libres.get(doble));
        assertEquals(3, libres.get(individual));
    }

    /**
     * UGH.1d: una reserva que sale el día que empieza la consulta no cuenta (valor límite).
     *
     * @throws Exception si falla la consulta
     */
    @Test
    @DisplayName("UGH.1d Reserva que se va el día que empieza la consulta (valor límite)")
    void reservaQueSaleElDiaDeEntrada() throws Exception {
        Reserva r = crearReserva(8, 10, EstadoReserva.CONFIRMADA, 0, 2, 0);
        when(reservasDAO.reservasPorHotel(hotel)).thenReturn(List.of(r));

        Map<Habitacion, Integer> libres = gestionHoteles.consultarDisponibilidad(hotel, entrada, salida);

        assertEquals(5, libres.get(doble));
        assertEquals(3, libres.get(individual));
    }

    /**
     * UGH.1e: una reserva bloqueada hace 5 minutos sí ocupa habitaciones.
     *
     * @throws Exception si falla la consulta
     */
    @Test
    @DisplayName("UGH.1e Reserva bloqueada hace 5 minutos")
    void reservaBloqueadaVigente() throws Exception {
        Reserva r = crearReserva(10, 12, EstadoReserva.BLOQUEADA, 5, 2, 0);
        when(reservasDAO.reservasPorHotel(hotel)).thenReturn(List.of(r));

        Map<Habitacion, Integer> libres = gestionHoteles.consultarDisponibilidad(hotel, entrada, salida);

        assertEquals(3, libres.get(doble));
        assertEquals(3, libres.get(individual));
    }

    /**
     * UGH.1f: una reserva bloqueada hace 15 minutos ya no ocupa habitaciones.
     *
     * @throws Exception si falla la consulta
     */
    @Test
    @DisplayName("UGH.1f Reserva bloqueada hace 15 minutos (bloqueo caducado)")
    void reservaBloqueadaCaducada() throws Exception {
        Reserva r = crearReserva(10, 12, EstadoReserva.BLOQUEADA, 15, 2, 0);
        when(reservasDAO.reservasPorHotel(hotel)).thenReturn(List.of(r));

        Map<Habitacion, Integer> libres = gestionHoteles.consultarDisponibilidad(hotel, entrada, salida);

        assertEquals(5, libres.get(doble));
        assertEquals(3, libres.get(individual));
    }

    /**
     * UGH.1g: si no queda ninguna habitación libre se lanza la excepción del flujo 4a.
     *
     * @throws Exception si falla la consulta
     */
    @Test
    @DisplayName("UGH.1g Hotel completo")
    void hotelCompleto() throws Exception {
        Reserva r = crearReserva(10, 12, EstadoReserva.CONFIRMADA, 0, 5, 3);
        when(reservasDAO.reservasPorHotel(hotel)).thenReturn(List.of(r));

        OperacionNoValidaException e = assertThrows(OperacionNoValidaException.class,
                () -> gestionHoteles.consultarDisponibilidad(hotel, entrada, salida));

        assertEquals("No hay disponibilidad, pruebe otras fechas", e.getMessage());
    }

    /** UGH.1h: si la salida es anterior a la entrada se lanza OperacionNoValidaException. */
    @Test
    @DisplayName("UGH.1h Fechas al revés")
    void fechasAlReves() {
        assertThrows(OperacionNoValidaException.class,
                () -> gestionHoteles.consultarDisponibilidad(hotel, salida, entrada));
    }

    /**
     * UGH.1i: si la entrada y la salida son el mismo día se lanza OperacionNoValidaException (valor límite).
     */
    @Test
    @DisplayName("UGH.1i Misma fecha de entrada y de salida (valor límite)")
    void mismaFecha() {
        assertThrows(OperacionNoValidaException.class,
                () -> gestionHoteles.consultarDisponibilidad(hotel, entrada, entrada));
    }
}
