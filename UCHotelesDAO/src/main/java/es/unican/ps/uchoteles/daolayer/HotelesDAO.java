package es.unican.ps.uchoteles.daolayer;

import es.unican.ps.uchoteles.common.daolayer.IHotelesDAO;
import es.unican.ps.uchoteles.common.domain.Categoria;
import es.unican.ps.uchoteles.common.domain.Habitacion;
import es.unican.ps.uchoteles.common.domain.Hotel;
import es.unican.ps.uchoteles.common.domain.TipoHabitacion;
import es.unican.ps.uchoteles.common.exceptions.DataAccessException;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Local;
import jakarta.ejb.Singleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación provisional de {@link IHotelesDAO} que guarda los hoteles en memoria.
 * Se sustituirá por una implementación con JPA en la práctica 3.3.
 */
@Singleton
@Local(IHotelesDAO.class)
public class HotelesDAO implements IHotelesDAO {

    /** Hoteles guardados. */
    private final List<Hotel> hoteles = new ArrayList<>();

    /** Siguiente identificador que se asignará a un hotel o a una habitación. */
    private long siguienteId = 1;

    /** Carga unos hoteles de ejemplo (los del Anexo 1 del plan de pruebas) al arrancar. */
    @PostConstruct
    public void cargarDatosIniciales() {
        Hotel bahia = new Hotel("Bahía", "Calle Castelar 1", "Santander", Categoria.FOUR_STARS);
        bahia.getHabitaciones().add(new Habitacion(TipoHabitacion.DOBLE, 100, 5, bahia));
        bahia.getHabitaciones().add(new Habitacion(TipoHabitacion.INDIVIDUAL, 50, 3, bahia));
        crearHotel(bahia);

        Hotel mirador = new Hotel("Mirador", "Avenida de la Playa 3", "Laredo", Categoria.THREE_STARS);
        mirador.getHabitaciones().add(new Habitacion(TipoHabitacion.DOBLE, 80, 4, mirador));
        crearHotel(mirador);
    }

    @Override
    public Hotel crearHotel(Hotel h) {
        asignarIds(h);
        hoteles.add(h);
        return h;
    }

    @Override
    public Hotel hotelPorNombre(String nombre) throws DataAccessException {
        for (Hotel h : hoteles) {
            if (h.getNombre().equalsIgnoreCase(nombre)) {
                return h;
            }
        }
        return null;
    }

    @Override
    public List<Hotel> hotelesPorLocalidad(String localidad) throws DataAccessException {
        List<Hotel> resultado = new ArrayList<>();
        for (Hotel h : hoteles) {
            if (h.getLocalidad().equalsIgnoreCase(localidad)) {
                resultado.add(h);
            }
        }
        return resultado;
    }

    @Override
    public Hotel modificarHotel(Hotel h) throws DataAccessException {
        Hotel guardado = buscarPorId(h.getId());
        if (guardado == null) {
            return null;
        }
        asignarIds(h);
        hoteles.set(hoteles.indexOf(guardado), h);
        return h;
    }

    @Override
    public Hotel eliminarHotel(Hotel h) throws DataAccessException {
        Hotel guardado = buscarPorId(h.getId());
        if (guardado != null) {
            hoteles.remove(guardado);
        }
        return guardado;
    }

    /**
     * Busca un hotel guardado por su identificador.
     *
     * @param id identificador del hotel
     * @return el hotel, o null si no existe
     */
    private Hotel buscarPorId(Long id) {
        for (Hotel h : hoteles) {
            if (h.getId().equals(id)) {
                return h;
            }
        }
        return null;
    }

    /**
     * Asigna identificador al hotel y a sus habitaciones si todavía no lo tienen,
     * igual que haría la base de datos al guardarlos.
     *
     * @param h hotel
     */
    private void asignarIds(Hotel h) {
        if (h.getId() == null) {
            h.setId(siguienteId++);
        }
        for (Habitacion hab : h.getHabitaciones()) {
            if (hab.getId() == null) {
                hab.setId(siguienteId++);
            }
        }
    }
}
