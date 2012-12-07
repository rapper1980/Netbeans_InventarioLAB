/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.jpaControladores;

import inventarioLAB.entidades.SolicitudPrestamo;
import inventarioLAB.jpaControladores.exceptions.IllegalOrphanException;
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
import inventarioLAB.entidades.Usuario;
import inventarioLAB.entidades.DetalleSolicitudPrestamo;
import java.util.ArrayList;
import java.util.Collection;
import javax.transaction.UserTransaction;

/**
 *
 * @author Edward J. Holguín Holguín
 */
public class SolicitudPrestamoJpaController implements Serializable {

    public SolicitudPrestamoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SolicitudPrestamo solicitudPrestamo) throws RollbackFailureException, Exception {
        if (solicitudPrestamo.getDetalleSolicitudPrestamoCollection() == null) {
            solicitudPrestamo.setDetalleSolicitudPrestamoCollection(new ArrayList<DetalleSolicitudPrestamo>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario usuario = solicitudPrestamo.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getUsuario());
                solicitudPrestamo.setUsuario(usuario);
            }
            Collection<DetalleSolicitudPrestamo> attachedDetalleSolicitudPrestamoCollection = new ArrayList<DetalleSolicitudPrestamo>();
            for (DetalleSolicitudPrestamo detalleSolicitudPrestamoCollectionDetalleSolicitudPrestamoToAttach : solicitudPrestamo.getDetalleSolicitudPrestamoCollection()) {
                detalleSolicitudPrestamoCollectionDetalleSolicitudPrestamoToAttach = em.getReference(detalleSolicitudPrestamoCollectionDetalleSolicitudPrestamoToAttach.getClass(), detalleSolicitudPrestamoCollectionDetalleSolicitudPrestamoToAttach.getIdDetalle());
                attachedDetalleSolicitudPrestamoCollection.add(detalleSolicitudPrestamoCollectionDetalleSolicitudPrestamoToAttach);
            }
            solicitudPrestamo.setDetalleSolicitudPrestamoCollection(attachedDetalleSolicitudPrestamoCollection);
            em.persist(solicitudPrestamo);
            if (usuario != null) {
                usuario.getSolicitudPrestamoCollection().add(solicitudPrestamo);
                usuario = em.merge(usuario);
            }
            for (DetalleSolicitudPrestamo detalleSolicitudPrestamoCollectionDetalleSolicitudPrestamo : solicitudPrestamo.getDetalleSolicitudPrestamoCollection()) {
                SolicitudPrestamo oldIdSolicitudPrestamoOfDetalleSolicitudPrestamoCollectionDetalleSolicitudPrestamo = detalleSolicitudPrestamoCollectionDetalleSolicitudPrestamo.getIdSolicitudPrestamo();
                detalleSolicitudPrestamoCollectionDetalleSolicitudPrestamo.setIdSolicitudPrestamo(solicitudPrestamo);
                detalleSolicitudPrestamoCollectionDetalleSolicitudPrestamo = em.merge(detalleSolicitudPrestamoCollectionDetalleSolicitudPrestamo);
                if (oldIdSolicitudPrestamoOfDetalleSolicitudPrestamoCollectionDetalleSolicitudPrestamo != null) {
                    oldIdSolicitudPrestamoOfDetalleSolicitudPrestamoCollectionDetalleSolicitudPrestamo.getDetalleSolicitudPrestamoCollection().remove(detalleSolicitudPrestamoCollectionDetalleSolicitudPrestamo);
                    oldIdSolicitudPrestamoOfDetalleSolicitudPrestamoCollectionDetalleSolicitudPrestamo = em.merge(oldIdSolicitudPrestamoOfDetalleSolicitudPrestamoCollectionDetalleSolicitudPrestamo);
                }
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

    public void edit(SolicitudPrestamo solicitudPrestamo) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            SolicitudPrestamo persistentSolicitudPrestamo = em.find(SolicitudPrestamo.class, solicitudPrestamo.getIdSolicitudPrestamo());
            Usuario usuarioOld = persistentSolicitudPrestamo.getUsuario();
            Usuario usuarioNew = solicitudPrestamo.getUsuario();
            Collection<DetalleSolicitudPrestamo> detalleSolicitudPrestamoCollectionOld = persistentSolicitudPrestamo.getDetalleSolicitudPrestamoCollection();
            Collection<DetalleSolicitudPrestamo> detalleSolicitudPrestamoCollectionNew = solicitudPrestamo.getDetalleSolicitudPrestamoCollection();
            List<String> illegalOrphanMessages = null;
            for (DetalleSolicitudPrestamo detalleSolicitudPrestamoCollectionOldDetalleSolicitudPrestamo : detalleSolicitudPrestamoCollectionOld) {
                if (!detalleSolicitudPrestamoCollectionNew.contains(detalleSolicitudPrestamoCollectionOldDetalleSolicitudPrestamo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleSolicitudPrestamo " + detalleSolicitudPrestamoCollectionOldDetalleSolicitudPrestamo + " since its idSolicitudPrestamo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getUsuario());
                solicitudPrestamo.setUsuario(usuarioNew);
            }
            Collection<DetalleSolicitudPrestamo> attachedDetalleSolicitudPrestamoCollectionNew = new ArrayList<DetalleSolicitudPrestamo>();
            for (DetalleSolicitudPrestamo detalleSolicitudPrestamoCollectionNewDetalleSolicitudPrestamoToAttach : detalleSolicitudPrestamoCollectionNew) {
                detalleSolicitudPrestamoCollectionNewDetalleSolicitudPrestamoToAttach = em.getReference(detalleSolicitudPrestamoCollectionNewDetalleSolicitudPrestamoToAttach.getClass(), detalleSolicitudPrestamoCollectionNewDetalleSolicitudPrestamoToAttach.getIdDetalle());
                attachedDetalleSolicitudPrestamoCollectionNew.add(detalleSolicitudPrestamoCollectionNewDetalleSolicitudPrestamoToAttach);
            }
            detalleSolicitudPrestamoCollectionNew = attachedDetalleSolicitudPrestamoCollectionNew;
            solicitudPrestamo.setDetalleSolicitudPrestamoCollection(detalleSolicitudPrestamoCollectionNew);
            solicitudPrestamo = em.merge(solicitudPrestamo);
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getSolicitudPrestamoCollection().remove(solicitudPrestamo);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getSolicitudPrestamoCollection().add(solicitudPrestamo);
                usuarioNew = em.merge(usuarioNew);
            }
            for (DetalleSolicitudPrestamo detalleSolicitudPrestamoCollectionNewDetalleSolicitudPrestamo : detalleSolicitudPrestamoCollectionNew) {
                if (!detalleSolicitudPrestamoCollectionOld.contains(detalleSolicitudPrestamoCollectionNewDetalleSolicitudPrestamo)) {
                    SolicitudPrestamo oldIdSolicitudPrestamoOfDetalleSolicitudPrestamoCollectionNewDetalleSolicitudPrestamo = detalleSolicitudPrestamoCollectionNewDetalleSolicitudPrestamo.getIdSolicitudPrestamo();
                    detalleSolicitudPrestamoCollectionNewDetalleSolicitudPrestamo.setIdSolicitudPrestamo(solicitudPrestamo);
                    detalleSolicitudPrestamoCollectionNewDetalleSolicitudPrestamo = em.merge(detalleSolicitudPrestamoCollectionNewDetalleSolicitudPrestamo);
                    if (oldIdSolicitudPrestamoOfDetalleSolicitudPrestamoCollectionNewDetalleSolicitudPrestamo != null && !oldIdSolicitudPrestamoOfDetalleSolicitudPrestamoCollectionNewDetalleSolicitudPrestamo.equals(solicitudPrestamo)) {
                        oldIdSolicitudPrestamoOfDetalleSolicitudPrestamoCollectionNewDetalleSolicitudPrestamo.getDetalleSolicitudPrestamoCollection().remove(detalleSolicitudPrestamoCollectionNewDetalleSolicitudPrestamo);
                        oldIdSolicitudPrestamoOfDetalleSolicitudPrestamoCollectionNewDetalleSolicitudPrestamo = em.merge(oldIdSolicitudPrestamoOfDetalleSolicitudPrestamoCollectionNewDetalleSolicitudPrestamo);
                    }
                }
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
                Integer id = solicitudPrestamo.getIdSolicitudPrestamo();
                if (findSolicitudPrestamo(id) == null) {
                    throw new NonexistentEntityException("The solicitudPrestamo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            SolicitudPrestamo solicitudPrestamo;
            try {
                solicitudPrestamo = em.getReference(SolicitudPrestamo.class, id);
                solicitudPrestamo.getIdSolicitudPrestamo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The solicitudPrestamo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<DetalleSolicitudPrestamo> detalleSolicitudPrestamoCollectionOrphanCheck = solicitudPrestamo.getDetalleSolicitudPrestamoCollection();
            for (DetalleSolicitudPrestamo detalleSolicitudPrestamoCollectionOrphanCheckDetalleSolicitudPrestamo : detalleSolicitudPrestamoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SolicitudPrestamo (" + solicitudPrestamo + ") cannot be destroyed since the DetalleSolicitudPrestamo " + detalleSolicitudPrestamoCollectionOrphanCheckDetalleSolicitudPrestamo + " in its detalleSolicitudPrestamoCollection field has a non-nullable idSolicitudPrestamo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario usuario = solicitudPrestamo.getUsuario();
            if (usuario != null) {
                usuario.getSolicitudPrestamoCollection().remove(solicitudPrestamo);
                usuario = em.merge(usuario);
            }
            em.remove(solicitudPrestamo);
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

    public List<SolicitudPrestamo> findSolicitudPrestamoEntities() {
        return findSolicitudPrestamoEntities(true, -1, -1);
    }

    public List<SolicitudPrestamo> findSolicitudPrestamoEntities(int maxResults, int firstResult) {
        return findSolicitudPrestamoEntities(false, maxResults, firstResult);
    }

    private List<SolicitudPrestamo> findSolicitudPrestamoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SolicitudPrestamo.class));
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

    public SolicitudPrestamo findSolicitudPrestamo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SolicitudPrestamo.class, id);
        } finally {
            em.close();
        }
    }

    public int getSolicitudPrestamoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SolicitudPrestamo> rt = cq.from(SolicitudPrestamo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
