/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.jpaControladores;

import inventarioLAB.entidades.TipoEquipo;
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
import inventarioLAB.entidades.Equipo;
import java.util.ArrayList;
import java.util.Collection;
import javax.transaction.UserTransaction;

/**
 *
 * @author Edward J. Holguín Holguín
 */
public class TipoEquipoJpaController implements Serializable {

    public TipoEquipoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoEquipo tipoEquipo) throws RollbackFailureException, Exception {
        if (tipoEquipo.getEquipoCollection() == null) {
            tipoEquipo.setEquipoCollection(new ArrayList<Equipo>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Equipo> attachedEquipoCollection = new ArrayList<Equipo>();
            for (Equipo equipoCollectionEquipoToAttach : tipoEquipo.getEquipoCollection()) {
                equipoCollectionEquipoToAttach = em.getReference(equipoCollectionEquipoToAttach.getClass(), equipoCollectionEquipoToAttach.getIdEquipo());
                attachedEquipoCollection.add(equipoCollectionEquipoToAttach);
            }
            tipoEquipo.setEquipoCollection(attachedEquipoCollection);
            em.persist(tipoEquipo);
            for (Equipo equipoCollectionEquipo : tipoEquipo.getEquipoCollection()) {
                TipoEquipo oldIdTipoEquipoOfEquipoCollectionEquipo = equipoCollectionEquipo.getIdTipoEquipo();
                equipoCollectionEquipo.setIdTipoEquipo(tipoEquipo);
                equipoCollectionEquipo = em.merge(equipoCollectionEquipo);
                if (oldIdTipoEquipoOfEquipoCollectionEquipo != null) {
                    oldIdTipoEquipoOfEquipoCollectionEquipo.getEquipoCollection().remove(equipoCollectionEquipo);
                    oldIdTipoEquipoOfEquipoCollectionEquipo = em.merge(oldIdTipoEquipoOfEquipoCollectionEquipo);
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

    public void edit(TipoEquipo tipoEquipo) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoEquipo persistentTipoEquipo = em.find(TipoEquipo.class, tipoEquipo.getIdTipoEquipo());
            Collection<Equipo> equipoCollectionOld = persistentTipoEquipo.getEquipoCollection();
            Collection<Equipo> equipoCollectionNew = tipoEquipo.getEquipoCollection();
            List<String> illegalOrphanMessages = null;
            for (Equipo equipoCollectionOldEquipo : equipoCollectionOld) {
                if (!equipoCollectionNew.contains(equipoCollectionOldEquipo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Equipo " + equipoCollectionOldEquipo + " since its idTipoEquipo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Equipo> attachedEquipoCollectionNew = new ArrayList<Equipo>();
            for (Equipo equipoCollectionNewEquipoToAttach : equipoCollectionNew) {
                equipoCollectionNewEquipoToAttach = em.getReference(equipoCollectionNewEquipoToAttach.getClass(), equipoCollectionNewEquipoToAttach.getIdEquipo());
                attachedEquipoCollectionNew.add(equipoCollectionNewEquipoToAttach);
            }
            equipoCollectionNew = attachedEquipoCollectionNew;
            tipoEquipo.setEquipoCollection(equipoCollectionNew);
            tipoEquipo = em.merge(tipoEquipo);
            for (Equipo equipoCollectionNewEquipo : equipoCollectionNew) {
                if (!equipoCollectionOld.contains(equipoCollectionNewEquipo)) {
                    TipoEquipo oldIdTipoEquipoOfEquipoCollectionNewEquipo = equipoCollectionNewEquipo.getIdTipoEquipo();
                    equipoCollectionNewEquipo.setIdTipoEquipo(tipoEquipo);
                    equipoCollectionNewEquipo = em.merge(equipoCollectionNewEquipo);
                    if (oldIdTipoEquipoOfEquipoCollectionNewEquipo != null && !oldIdTipoEquipoOfEquipoCollectionNewEquipo.equals(tipoEquipo)) {
                        oldIdTipoEquipoOfEquipoCollectionNewEquipo.getEquipoCollection().remove(equipoCollectionNewEquipo);
                        oldIdTipoEquipoOfEquipoCollectionNewEquipo = em.merge(oldIdTipoEquipoOfEquipoCollectionNewEquipo);
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
                Integer id = tipoEquipo.getIdTipoEquipo();
                if (findTipoEquipo(id) == null) {
                    throw new NonexistentEntityException("The tipoEquipo with id " + id + " no longer exists.");
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
            TipoEquipo tipoEquipo;
            try {
                tipoEquipo = em.getReference(TipoEquipo.class, id);
                tipoEquipo.getIdTipoEquipo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoEquipo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Equipo> equipoCollectionOrphanCheck = tipoEquipo.getEquipoCollection();
            for (Equipo equipoCollectionOrphanCheckEquipo : equipoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoEquipo (" + tipoEquipo + ") cannot be destroyed since the Equipo " + equipoCollectionOrphanCheckEquipo + " in its equipoCollection field has a non-nullable idTipoEquipo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoEquipo);
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

    public List<TipoEquipo> findTipoEquipoEntities() {
        return findTipoEquipoEntities(true, -1, -1);
    }

    public List<TipoEquipo> findTipoEquipoEntities(int maxResults, int firstResult) {
        return findTipoEquipoEntities(false, maxResults, firstResult);
    }

    private List<TipoEquipo> findTipoEquipoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoEquipo.class));
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

    public TipoEquipo findTipoEquipo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoEquipo.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoEquipoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoEquipo> rt = cq.from(TipoEquipo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
