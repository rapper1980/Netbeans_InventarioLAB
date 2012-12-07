/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.jpaControladores;

import inventarioLAB.entidades.Usuario;
import inventarioLAB.jpaControladores.exceptions.IllegalOrphanException;
import inventarioLAB.jpaControladores.exceptions.NonexistentEntityException;
import inventarioLAB.jpaControladores.exceptions.PreexistingEntityException;
import inventarioLAB.jpaControladores.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import inventarioLAB.entidades.Persona;
import inventarioLAB.entidades.SolicitudPrestamo;
import java.util.ArrayList;
import java.util.Collection;
import javax.transaction.UserTransaction;

/**
 *
 * @author Edward J. Holguín Holguín
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (usuario.getSolicitudPrestamoCollection() == null) {
            usuario.setSolicitudPrestamoCollection(new ArrayList<SolicitudPrestamo>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Persona idPersona = usuario.getIdPersona();
            if (idPersona != null) {
                idPersona = em.getReference(idPersona.getClass(), idPersona.getIdPersona());
                usuario.setIdPersona(idPersona);
            }
            Collection<SolicitudPrestamo> attachedSolicitudPrestamoCollection = new ArrayList<SolicitudPrestamo>();
            for (SolicitudPrestamo solicitudPrestamoCollectionSolicitudPrestamoToAttach : usuario.getSolicitudPrestamoCollection()) {
                solicitudPrestamoCollectionSolicitudPrestamoToAttach = em.getReference(solicitudPrestamoCollectionSolicitudPrestamoToAttach.getClass(), solicitudPrestamoCollectionSolicitudPrestamoToAttach.getIdSolicitudPrestamo());
                attachedSolicitudPrestamoCollection.add(solicitudPrestamoCollectionSolicitudPrestamoToAttach);
            }
            usuario.setSolicitudPrestamoCollection(attachedSolicitudPrestamoCollection);
            em.persist(usuario);
            if (idPersona != null) {
                idPersona.getUsuarioCollection().add(usuario);
                idPersona = em.merge(idPersona);
            }
            for (SolicitudPrestamo solicitudPrestamoCollectionSolicitudPrestamo : usuario.getSolicitudPrestamoCollection()) {
                Usuario oldUsuarioOfSolicitudPrestamoCollectionSolicitudPrestamo = solicitudPrestamoCollectionSolicitudPrestamo.getUsuario();
                solicitudPrestamoCollectionSolicitudPrestamo.setUsuario(usuario);
                solicitudPrestamoCollectionSolicitudPrestamo = em.merge(solicitudPrestamoCollectionSolicitudPrestamo);
                if (oldUsuarioOfSolicitudPrestamoCollectionSolicitudPrestamo != null) {
                    oldUsuarioOfSolicitudPrestamoCollectionSolicitudPrestamo.getSolicitudPrestamoCollection().remove(solicitudPrestamoCollectionSolicitudPrestamo);
                    oldUsuarioOfSolicitudPrestamoCollectionSolicitudPrestamo = em.merge(oldUsuarioOfSolicitudPrestamoCollectionSolicitudPrestamo);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findUsuario(usuario.getUsuario()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getUsuario());
            Persona idPersonaOld = persistentUsuario.getIdPersona();
            Persona idPersonaNew = usuario.getIdPersona();
            Collection<SolicitudPrestamo> solicitudPrestamoCollectionOld = persistentUsuario.getSolicitudPrestamoCollection();
            Collection<SolicitudPrestamo> solicitudPrestamoCollectionNew = usuario.getSolicitudPrestamoCollection();
            List<String> illegalOrphanMessages = null;
            for (SolicitudPrestamo solicitudPrestamoCollectionOldSolicitudPrestamo : solicitudPrestamoCollectionOld) {
                if (!solicitudPrestamoCollectionNew.contains(solicitudPrestamoCollectionOldSolicitudPrestamo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SolicitudPrestamo " + solicitudPrestamoCollectionOldSolicitudPrestamo + " since its usuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idPersonaNew != null) {
                idPersonaNew = em.getReference(idPersonaNew.getClass(), idPersonaNew.getIdPersona());
                usuario.setIdPersona(idPersonaNew);
            }
            Collection<SolicitudPrestamo> attachedSolicitudPrestamoCollectionNew = new ArrayList<SolicitudPrestamo>();
            for (SolicitudPrestamo solicitudPrestamoCollectionNewSolicitudPrestamoToAttach : solicitudPrestamoCollectionNew) {
                solicitudPrestamoCollectionNewSolicitudPrestamoToAttach = em.getReference(solicitudPrestamoCollectionNewSolicitudPrestamoToAttach.getClass(), solicitudPrestamoCollectionNewSolicitudPrestamoToAttach.getIdSolicitudPrestamo());
                attachedSolicitudPrestamoCollectionNew.add(solicitudPrestamoCollectionNewSolicitudPrestamoToAttach);
            }
            solicitudPrestamoCollectionNew = attachedSolicitudPrestamoCollectionNew;
            usuario.setSolicitudPrestamoCollection(solicitudPrestamoCollectionNew);
            usuario = em.merge(usuario);
            if (idPersonaOld != null && !idPersonaOld.equals(idPersonaNew)) {
                idPersonaOld.getUsuarioCollection().remove(usuario);
                idPersonaOld = em.merge(idPersonaOld);
            }
            if (idPersonaNew != null && !idPersonaNew.equals(idPersonaOld)) {
                idPersonaNew.getUsuarioCollection().add(usuario);
                idPersonaNew = em.merge(idPersonaNew);
            }
            for (SolicitudPrestamo solicitudPrestamoCollectionNewSolicitudPrestamo : solicitudPrestamoCollectionNew) {
                if (!solicitudPrestamoCollectionOld.contains(solicitudPrestamoCollectionNewSolicitudPrestamo)) {
                    Usuario oldUsuarioOfSolicitudPrestamoCollectionNewSolicitudPrestamo = solicitudPrestamoCollectionNewSolicitudPrestamo.getUsuario();
                    solicitudPrestamoCollectionNewSolicitudPrestamo.setUsuario(usuario);
                    solicitudPrestamoCollectionNewSolicitudPrestamo = em.merge(solicitudPrestamoCollectionNewSolicitudPrestamo);
                    if (oldUsuarioOfSolicitudPrestamoCollectionNewSolicitudPrestamo != null && !oldUsuarioOfSolicitudPrestamoCollectionNewSolicitudPrestamo.equals(usuario)) {
                        oldUsuarioOfSolicitudPrestamoCollectionNewSolicitudPrestamo.getSolicitudPrestamoCollection().remove(solicitudPrestamoCollectionNewSolicitudPrestamo);
                        oldUsuarioOfSolicitudPrestamoCollectionNewSolicitudPrestamo = em.merge(oldUsuarioOfSolicitudPrestamoCollectionNewSolicitudPrestamo);
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
                String id = usuario.getUsuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<SolicitudPrestamo> solicitudPrestamoCollectionOrphanCheck = usuario.getSolicitudPrestamoCollection();
            for (SolicitudPrestamo solicitudPrestamoCollectionOrphanCheckSolicitudPrestamo : solicitudPrestamoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the SolicitudPrestamo " + solicitudPrestamoCollectionOrphanCheckSolicitudPrestamo + " in its solicitudPrestamoCollection field has a non-nullable usuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Persona idPersona = usuario.getIdPersona();
            if (idPersona != null) {
                idPersona.getUsuarioCollection().remove(usuario);
                idPersona = em.merge(idPersona);
            }
            em.remove(usuario);
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

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
