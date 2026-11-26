package es.unican.ps.uchoteles.daolayer;

import es.unican.ps.uchoteles.common.daolayer.IReservasDAO;
import es.unican.ps.uchoteles.common.domain.Hotel;
import es.unican.ps.uchoteles.common.domain.Reserva;
import es.unican.ps.uchoteles.common.exceptions.DataAccessException;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;

import java.util.ArrayList;
import java.util.List;

/** Implementación de {@link IReservasDAO} que guarda las reservas en la base de datos con JPA. */
@Stateless
@Local(IReservasDAO.class)
public class ReservasDAO implements IReservasDAO {

    /** Gestor de entidades con el que se accede a la base de datos. Visible en el paquete para las pruebas. */
    @PersistenceContext(unitName = "UCHotelesPU")
    EntityManager em;

    @Override
    public Reserva crearReserva(Reserva r) throws DataAccessException {
        try {
            em.persist(r);
            em.flush();
            return r;
        } catch (PersistenceException e) {
            throw new DataAccessException("Error al crear la reserva", e);
        }
    }

    @Override
    public Reserva reservaPorId(Long id) throws DataAccessException {
        try {
            if (id == null) {
                return null;
            }
            return em.find(Reserva.class, id);
        } catch (PersistenceException e) {
            throw new DataAccessException("Error al buscar la reserva", e);
        }
    }

    @Override
    public Reserva modificarReserva(Reserva r) throws DataAccessException {
        try {
            if (r.getId() == null || em.find(Reserva.class, r.getId()) == null) {
                return null;
            }
            Reserva actualizada = em.merge(r);
            em.flush();
            return actualizada;
        } catch (PersistenceException e) {
            throw new DataAccessException("Error al modificar la reserva", e);
        }
    }

    @Override
    public Reserva eliminarReserva(Reserva r) throws DataAccessException {
        try {
            if (r.getId() == null) {
                return null;
            }
            Reserva guardada = em.find(Reserva.class, r.getId());
            if (guardada == null) {
                return null;
            }
            em.remove(guardada);
            em.flush();
            return guardada;
        } catch (PersistenceException e) {
            throw new DataAccessException("Error al eliminar la reserva", e);
        }
    }

    @Override
    public List<Reserva> reservasPorHotel(Hotel h) throws DataAccessException {
        try {
            if (h == null || h.getId() == null) {
                return new ArrayList<>();
            }
            return em.createQuery("SELECT r FROM Reserva r WHERE r.hotel.id = :idHotel", Reserva.class)
                    .setParameter("idHotel", h.getId())
                    .getResultList();
        } catch (PersistenceException e) {
            throw new DataAccessException("Error al buscar las reservas del hotel", e);
        }
    }
}