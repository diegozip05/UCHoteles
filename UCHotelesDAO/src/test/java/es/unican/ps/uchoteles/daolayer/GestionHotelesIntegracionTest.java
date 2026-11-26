package es.unican.ps.uchoteles.daolayer;

import es.unican.ps.uchoteles.businesslayer.GestionHoteles;
import es.unican.ps.uchoteles.common.domain.Categoria;
import es.unican.ps.uchoteles.common.domain.EstadoReserva;
import es.unican.ps.uchoteles.common.domain.Habitacion;
import es.unican.ps.uchoteles.common.domain.Hotel;
import es.unican.ps.uchoteles.common.domain.Reserva;
import es.unican.ps.uchoteles.common.domain.ReservaTipoHabitacion;
import es.unican.ps.uchoteles.common.domain.TipoHabitacion;
import es.unican.ps.uchoteles.common.exceptions.OperacionNoValidaException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Pruebas de integración de GestionHoteles.consultarDisponibilidad con el ReservasDAO real.
 * Se usa H2 en memoria con los datos del Anexo del plan de pruebas.
 */
class GestionHotelesIntegracionTest {

    private static EntityManagerFactory emf;
    private EntityManager em;
    private GestionHoteles sut;

    private Hotel bahia;
    private Hotel mirador;
    private Habitacion doble;
    private Habitacion individual;

    @BeforeAll
    static void arrancarJPA() {
        emf = Persistence.createEntityManagerFactory("UCHotelesTestPU");
    }

    @AfterAll
    static void cerrarJPA() {
        emf.close();
    }

    @BeforeEach
    void setUp() throws Exception {
        em = emf.createEntityManager();

        // DAO real conectado a H2
        ReservasDAO reservasDAO = new ReservasDAO();
        reservasDAO.em = em;

        // Negocio con el DAO real (lo que haria GlassFish con @EJB)
        sut = new GestionHoteles();
        inyectar(sut, "reservasDAO", reservasDAO);

        em.getTransaction().begin();

        // Datos del Anexo
        bahia = new Hotel("Bahía", "Calle Castelar 1", "Santander", Categoria.FOUR_STARS);
        doble = new Habitacion(TipoHabitacion.DOBLE, 100, 5, bahia);
        individual = new Habitacion(TipoHabitacion.INDIVIDUAL, 50, 3, bahia);
        bahia.getHabitaciones().add(doble);
        bahia.getHabitaciones().add(individual);
        em.persist(bahia);

        mirador = new Hotel("Mirador", "Avenida de la Playa 3", "Laredo", Categoria.THREE_STARS);
        mirador.getHabitaciones().add(new Habitacion(TipoHabitacion.DOBLE, 80, 4, mirador));
        em.persist(mirador);

        // R1: del 11 al 13 de diciembre, 2 dobles
        Reserva r1 = new Reserva(LocalDate.of(2026, 12, 11), LocalDate.of(2026, 12, 13),
                EstadoReserva.CONFIRMADA, LocalDateTime.now(), bahia, null, null);
        r1.getReservasPorTipo().add(new ReservaTipoHabitacion(2, doble, r1));
        em.persist(r1);

        // R2: del 20 al 24 de diciembre, 5 dobles y 3 individuales (hotel lleno)
        Reserva r2 = new Reserva(LocalDate.of(2026, 12, 20), LocalDate.of(2026, 12, 24),
                EstadoReserva.CONFIRMADA, LocalDateTime.now(), bahia, null, null);
        r2.getReservasPorTipo().add(new ReservaTipoHabitacion(5, doble, r2));
        r2.getReservasPorTipo().add(new ReservaTipoHabitacion(3, individual, r2));
        em.persist(r2);

        // Se guarda en H2 y se vacia la memoria de JPA para que lea de la base de datos
        em.flush();
        em.clear();
    }

    @AfterEach
    void tearDown() {
        em.getTransaction().rollback();
        em.close();
    }

    // Da valor a un atributo privado (lo mismo que hace el servidor con @EJB)
    private void inyectar(Object objeto, String atributo, Object valor) throws Exception {
        Field f = objeto.getClass().getDeclaredField(atributo);
        f.setAccessible(true);
        f.set(objeto, valor);
    }

    // IGH.1a: se solapa con R1, quedan 3 dobles y 3 individuales
    @Test
    void seSolapaConUnaReserva() throws Exception {
        Map<Habitacion, Integer> libres = sut.consultarDisponibilidad(bahia,
                LocalDate.of(2026, 12, 10), LocalDate.of(2026, 12, 12));

        assertEquals(3, libres.get(doble));
        assertEquals(3, libres.get(individual));
    }

    // IGH.1b: no se solapa con ninguna reserva, esta todo libre
    @Test
    void noSeSolapaConNinguna() throws Exception {
        Map<Habitacion, Integer> libres = sut.consultarDisponibilidad(bahia,
                LocalDate.of(2026, 12, 1), LocalDate.of(2026, 12, 5));

        assertEquals(5, libres.get(doble));
        assertEquals(3, libres.get(individual));
    }

    // IGH.1c: R2 ocupa todo el hotel, salta la excepcion
    @Test
    void hotelCompleto() {
        OperacionNoValidaException e = assertThrows(OperacionNoValidaException.class,
                () -> sut.consultarDisponibilidad(bahia,
                        LocalDate.of(2026, 12, 21), LocalDate.of(2026, 12, 23)));

        assertEquals("No hay disponibilidad, pruebe otras fechas", e.getMessage());
    }

    // IGH.1d: las reservas del Bahia no afectan al Mirador
    @Test
    void otroHotelSinReservas() throws Exception {
        Map<Habitacion, Integer> libres = sut.consultarDisponibilidad(mirador,
                LocalDate.of(2026, 12, 10), LocalDate.of(2026, 12, 12));

        assertEquals(4, libres.get(mirador.getHabitacion(TipoHabitacion.DOBLE)));
    }
}