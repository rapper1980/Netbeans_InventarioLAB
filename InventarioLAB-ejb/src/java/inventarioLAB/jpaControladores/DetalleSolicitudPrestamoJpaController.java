/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.jpaControladores;

import inventarioLAB.entidades.DetalleSolicitudPrestamo;
import inventarioLAB.jpaControladores.exceptions.NonexistentEntityException;
import inventarioLAB.jpaControladores.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import inventarioLAB.entidades.SolicitudPrestamo;
import javax.transaction.UserTransaction;

/**
 *
 * @author Edward J. Holguín Holguín
 */
public class DetalleSolicitudPrestamoJpaController implements Serializable {

    public DetalleSolicitudPrestamoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetalleSolicitudPrestamo detalleSolicitudPrestamo) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            SolicitudPrestamo idSolicitudPrestamo = detalleSolicitudPrestamo.getIdSolicitudPrestamo();
            if (idSolicitudPrestamo != null) {
                idSolicitudPrestamo = em.getReference(idSolicitudPrestamo.getClass(), idSolicitudPrestamo.getIdSolicitudPrestamo());
                detalleSolicitudPrestamo.setIdSolicitudPrestamo(idSolicitudPrestamo);
            }
            em.persist(detalleSolicitudPrestamo);
            if (idSolicitudPrestamo != null) {
                idSolicitudPrestamo.getDetalleSolicitudPrestamoCollection().add(detalleSolicitudPrestamo);
                idSolicitudPrestamo = em.merge(idSolicitudPrestamo);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetalleSolicitudPrestamo detalleSolicitudPrestamo) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            DetalleSolicitudPrestamo persistentDetalleSolicitudPrestamo = em.find(DetalleSolicitudPrestamo.class, detalleSolicitudPrestamo.getIdDetalle());
            SolicitudPrestamo idSolicitudPrestamoOld = persistentDetalleSolicitudPrestamo.getIdSolicitudPrestamo();
            SolicitudPrestamo idSolicitudPrestamoNew = detalleSolicitudPrestamo.getIdSolicitudPrestamo();
            if (idSolicitudPrestamoNew != null) {
                idSolicitudPrestamoNew = em.getReference(idSolicitudPrestamoNew.getClass(), idSolicitudPrestamoNew.getIdSolicitudPrestamo());
                detalleSolicitudPrestamo.setIdSolicitudPrestamo(idSolicitudPrestamoNew);
            }
            detalleSolicitudPrestamo = em.merge(detalleSolicitudPrestamo);
            if (idSolicitudPrestamoOld != null && !idSolicitudPrestamoOld.equals(idSolicitudPrestamoNew)) {
                idSolicitudPrestamoOld.getDetalleSolicitudPrestamoCollection().remove(detalleSolicitudPrestamo);
                idSolicitudPrestamoOld = em.merge(idSolicitudPrestamoOld);
            }
            if (idSolicitudPrestamoNew != null && !idSolicitudPrestamoNew.equals(idSolicitudPrestamoOld)) {
                idSolicitudPrestamoNew.getDetalleSolicitudPrestamoCollection().add(detalleSolicitudPrestamo);
                idSolicitudPrestamoNew = em.merge(idSolicitudPrestamoNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detalleSolicitudPrestamo.getIdDetalle();
                if (findDetalleSolicitudPrestamo(id) == null) {
                    throw new NonexistentEntityException("The detalleSolicitudPrestamo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            DetalleSolicitudPrestamo detalleSolicitudPrestamo;
            try {
                detalleSolicitudPrestamo = em.getReference(DetalleSolicitudPrestamo.class, id);
                detalleSolicitudPrestamo.getIdDetalle();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleSolicitudPrestamo with id " + id + " no longer exists.", enfe);
            }
            SolicitudPrestamo idSolicitudPrestamo = detalleSolicitudPrestamo.getIdSolicitudPrestamo();
            if (idSolicitudPrestamo != null) {
                idSolicitudPrestamo.getDetalleSolicitudPrestamoCollection().remove(detalleSolicitudPrestamo);
                idSolicitudPrestamo = em.merge(idSolicitudPrestamo);
            }
            em.remove(detalleSolicitudPrestamo);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetalleSolicitudPrestamo> findDetalleSolicitudPrestamoEntities() {
        return findDetalleSolicitudPrestamoEntities(true, -1, -1);
    }

    public List<DetalleSolicitudPrestamo> findDetalleSolicitudPrestamoEntities(int maxResults, int firstResult) {
        return findDetalleSolicitudPrestamoEntities(false, maxResults, firstResult);
    }

    private List<DetalleSolicitudPrestamo> findDetalleSolicitudPrestamoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetalleSolicitudPrestamo.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public DetalleSolicitudPrestamo findDetalleSolicitudPrestamo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetalleSolicitudPrestamo.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleSolicitudPrestamoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetalleSolicitudPrestamo> rt = cq.from(DetalleSolicitudPrestamo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
