package es.unican.ps.uchoteles.daolayer;

import es.unican.ps.uchoteles.common.domain.Categoria;
import es.unican.ps.uchoteles.common.domain.EstadoReserva;
import es.unican.ps.uchoteles.common.domain.Habitacion;
import es.unican.ps.uchoteles.common.domain.Hotel;
import es.unican.ps.uchoteles.common.domain.Reserva;
import es.unican.ps.uchoteles.common.domain.ReservaTipoHabitacion;
import es.unican.ps.uchoteles.common.domain.TipoHabitacion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pruebas unitarias de {@link ReservasDAO#reservasPorHotel} (Tabla 3 del plan de pruebas).
 * Se usa una base de datos H2 en memoria con los datos del Anexo: los hoteles Bahía y Mirador,
 * y las reservas R1 y R2 del Bahía. Cada prueba deshace sus cambios al terminar.
 */
class ReservasDAOTest {

    /** Factoría de JPA con la unidad de persistencia de pruebas. */
    private static EntityManagerFactory emf;

    /** Gestor de entidades de cada prueba. */
    private EntityManager em;

    /** DAO que se prueba (System Under Test). */
    private ReservasDAO sut;

    /** Hotel Bahía, con reservas. */
    private Hotel bahia;

    /** Hotel Mirador, sin reservas. */
    private Hotel mirador;

    /** Reserva R1: Bahía, 11/12 al 13/12, 2 dobles. */
    private Reserva r1;

    /** Reserva R2: Bahía, 20/12 al 24/12, 5 dobles y 3 individuales. */
    private Reserva r2;

    /** Arranca JPA con H2 una vez antes de todas las pruebas. */
    @BeforeAll
    static void arrancarJPA() {
        emf = Persistence.createEntityManagerFactory("UCHotelesTestPU");
    }

    /** Cierra JPA al terminar todas las pruebas. */
    @AfterAll
    static void cerrarJPA() {
        emf.close();
    }

    /** Mete los datos del Anexo antes de cada prueba. */
    @BeforeEach
    void setUp() {
        em = emf.createEntityManager();
        sut = new ReservasDAO();
        sut.em = em;
        em.getTransaction().begin();

        bahia = new Hotel("Bahía", "Calle Castelar 1", "Santander", Categoria.FOUR_STARS);
        bahia.getHabitaciones().add(new Habitacion(TipoHabitacion.DOBLE, 100, 5, bahia));
        bahia.getHabitaciones().add(new Habitacion(TipoHabitacion.INDIVIDUAL, 50, 3, bahia));
        em.persist(bahia);

        mirador = new Hotel("Mirador", "Avenida de la Playa 3", "Laredo", Categoria.THREE_STARS);
        mirador.getHabitaciones().add(new Habitacion(TipoHabitacion.DOBLE, 80, 4, mirador));
        em.persist(mirador);

        Habitacion doble = bahia.getHabitacion(TipoHabitacion.DOBLE);
        Habitacion individual = bahia.getHabitacion(TipoHabitacion.INDIVIDUAL);

        r1 = new Reserva(LocalDate.of(2026, 12, 11), LocalDate.of(2026, 12, 13),
                EstadoReserva.CONFIRMADA, LocalDateTime.now(), bahia, null, null);
        r1.getReservasPorTipo().add(new ReservaTipoHabitacion(2, doble, r1));
        em.persist(r1);

        r2 = new Reserva(LocalDate.of(2026, 12, 20), LocalDate.of(2026, 12, 24),
                EstadoReserva.CONFIRMADA, LocalDateTime.now(), bahia, null, null);
        r2.getReservasPorTipo().add(new ReservaTipoHabitacion(5, doble, r2));
        r2.getReservasPorTipo().add(new ReservaTipoHabitacion(3, individual, r2));
        em.persist(r2);

        em.flush();
        em.clear();
    }

    /** Deshace los cambios para que la siguiente prueba empiece con la base de datos vacía. */
    @AfterEach
    void tearDown() {
        em.getTransaction().rollback();
        em.close();
    }

    /**
     * UDR.1a: el Bahía tiene dos reservas, R1 y R2.
     *
     * @throws Exception si falla la consulta
     */
    @Test
    void hotelConReservas() throws Exception {
        List<Reserva> resultado = sut.reservasPorHotel(bahia);

        assertEquals(2, resultado.size());
        List<Long> ids = resultado.stream().map(Reserva::getId).toList();
        assertTrue(ids.contains(r1.getId()));
        assertTrue(ids.contains(r2.getId()));
    }

    /**
     * UDR.1b: el Mirador no tiene reservas, se devuelve una lista vacía.
     *
     * @throws Exception si falla la consulta
     */
    @Test
    void hotelSinReservas() throws Exception {
        List<Reserva> resultado = sut.reservasPorHotel(mirador);

        assertTrue(resultado.isEmpty());
    }

    /**
     * UDR.1c: un hotel que no está en la base de datos, se devuelve una lista vacía.
     *
     * @throws Exception si falla la consulta
     */
    @Test
    void hotelNoGuardado() throws Exception {
        Hotel noGuardado = new Hotel("Inexistente", "Calle Falsa 1", "Soria", Categoria.ONE_STAR);

        List<Reserva> resultado = sut.reservasPorHotel(noGuardado);

        assertTrue(resultado.isEmpty());
    }

    /**
     * UDR.1d: R1 se recupera con su línea, 2 dobles a 100 €, y un importe de 400 €.
     *
     * @throws Exception si falla la consulta
     */
    @Test
    void reservaConSusLineas() throws Exception {
        List<Reserva> resultado = sut.reservasPorHotel(bahia);

        Reserva leida = resultado.stream()
                .filter(r -> r.getId().equals(r1.getId()))
                .findFirst()
                .orElseThrow();
        assertEquals(1, leida.getReservasPorTipo().size());
        ReservaTipoHabitacion linea = leida.getReservasPorTipo().get(0);
        assertEquals(2, linea.getNumHabitaciones());
        assertEquals(100.0, linea.getPrecioPorNoche(), 0.001);
        assertEquals(400.0, leida.getImporte(), 0.001);
    }
}