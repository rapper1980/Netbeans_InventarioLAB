/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.jpaControladores;

import inventarioLAB.entidades.Equipo;
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
import inventarioLAB.entidades.TipoEquipo;
import javax.transaction.UserTransaction;

/**
 *
 * @author Edward J. Holguín Holguín
 */
public class EquipoJpaController implements Serializable {

    public EquipoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Equipo equipo) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoEquipo idTipoEquipo = equipo.getIdTipoEquipo();
            if (idTipoEquipo != null) {
                idTipoEquipo = em.getReference(idTipoEquipo.getClass(), idTipoEquipo.getIdTipoEquipo());
                equipo.setIdTipoEquipo(idTipoEquipo);
            }
            em.persist(equipo);
            if (idTipoEquipo != null) {
                idTipoEquipo.getEquipoCollection().add(equipo);
                idTipoEquipo = em.merge(idTipoEquipo);
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

    public void edit(Equipo equipo) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Equipo persistentEquipo = em.find(Equipo.class, equipo.getIdEquipo());
            TipoEquipo idTipoEquipoOld = persistentEquipo.getIdTipoEquipo();
            TipoEquipo idTipoEquipoNew = equipo.getIdTipoEquipo();
            if (idTipoEquipoNew != null) {
                idTipoEquipoNew = em.getReference(idTipoEquipoNew.getClass(), idTipoEquipoNew.getIdTipoEquipo());
                equipo.setIdTipoEquipo(idTipoEquipoNew);
            }
            equipo = em.merge(equipo);
            if (idTipoEquipoOld != null && !idTipoEquipoOld.equals(idTipoEquipoNew)) {
                idTipoEquipoOld.getEquipoCollection().remove(equipo);
                idTipoEquipoOld = em.merge(idTipoEquipoOld);
            }
            if (idTipoEquipoNew != null && !idTipoEquipoNew.equals(idTipoEquipoOld)) {
                idTipoEquipoNew.getEquipoCollection().add(equipo);
                idTipoEquipoNew = em.merge(idTipoEquipoNew);
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
                Integer id = equipo.getIdEquipo();
                if (findEquipo(id) == null) {
                    throw new NonexistentEntityException("The equipo with id " + id + " no longer exists.");
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
            Equipo equipo;
            try {
                equipo = em.getReference(Equipo.class, id);
                equipo.getIdEquipo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The equipo with id " + id + " no longer exists.", enfe);
            }
            TipoEquipo idTipoEquipo = equipo.getIdTipoEquipo();
            if (idTipoEquipo != null) {
                idTipoEquipo.getEquipoCollection().remove(equipo);
                idTipoEquipo = em.merge(idTipoEquipo);
            }
            em.remove(equipo);
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

    public List<Equipo> findEquipoEntities() {
        return findEquipoEntities(true, -1, -1);
    }

    public List<Equipo> findEquipoEntities(int maxResults, int firstResult) {
        return findEquipoEntities(false, maxResults, firstResult);
    }

    private List<Equipo> findEquipoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Equipo.class));
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

    public Equipo findEquipo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Equipo.class, id);
        } finally {
            em.close();
        }
    }

    public int getEquipoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Equipo> rt = cq.from(Equipo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
