package es.unican.ps.uchoteles.daolayer;

import es.unican.ps.uchoteles.common.daolayer.IHotelesDAO;
import es.unican.ps.uchoteles.common.domain.Hotel;
import es.unican.ps.uchoteles.common.exceptions.DataAccessException;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;

import java.util.List;

/** Implementación de {@link IHotelesDAO} que guarda los hoteles en la base de datos con JPA. */
@Stateless
@Local(IHotelesDAO.class)
public class HotelesDAO implements IHotelesDAO {

    /** Gestor de entidades con el que se accede a la base de datos. */
    @PersistenceContext(unitName = "UCHotelesPU")
    private EntityManager em;

    @Override
    public Hotel crearHotel(Hotel h) throws DataAccessException {
        try {
            em.persist(h);
            em.flush();
            return h;
        } catch (PersistenceException e) {
            throw new DataAccessException("Error al crear el hotel", e);
        }
    }

    @Override
    public Hotel hotelPorNombre(String nombre) throws DataAccessException {
        try {
            List<Hotel> resultado = em.createQuery(
                            "SELECT h FROM Hotel h WHERE LOWER(h.nombre) = LOWER(:nombre)", Hotel.class)
                    .setParameter("nombre", nombre)
                    .getResultList();
            return resultado.isEmpty() ? null : resultado.get(0);
        } catch (PersistenceException e) {
            throw new DataAccessException("Error al buscar el hotel por nombre", e);
        }
    }

    @Override
    public List<Hotel> hotelesPorLocalidad(String localidad) throws DataAccessException {
        try {
            return em.createQuery(
                            "SELECT h FROM Hotel h WHERE LOWER(h.localidad) = LOWER(:localidad)", Hotel.class)
                    .setParameter("localidad", localidad)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new DataAccessException("Error al buscar los hoteles por localidad", e);
        }
    }

    @Override
    public Hotel modificarHotel(Hotel h) throws DataAccessException {
        try {
            if (h.getId() == null || em.find(Hotel.class, h.getId()) == null) {
                return null;
            }
            Hotel actualizado = em.merge(h);
            em.flush();
            return actualizado;
        } catch (PersistenceException e) {
            throw new DataAccessException("Error al modificar el hotel", e);
        }
    }

    @Override
    public Hotel eliminarHotel(Hotel h) throws DataAccessException {
        try {
            if (h.getId() == null) {
                return null;
            }
            Hotel guardado = em.find(Hotel.class, h.getId());
            if (guardado == null) {
                return null;
            }
            em.remove(guardado);
            em.flush();
            return guardado;
        } catch (PersistenceException e) {
            throw new DataAccessException("Error al eliminar el hotel", e);
        }
    }
}