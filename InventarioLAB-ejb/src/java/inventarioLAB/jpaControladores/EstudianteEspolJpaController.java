/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.jpaControladores;

import inventarioLAB.entidades.EstudianteEspol;
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
import java.util.ArrayList;
import javax.transaction.UserTransaction;

/**
 *
 * @author Edward J. Holguín Holguín
 */
public class EstudianteEspolJpaController implements Serializable {

    public EstudianteEspolJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstudianteEspol estudianteEspol) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception {
        List<String> illegalOrphanMessages = null;
        Persona personaOrphanCheck = estudianteEspol.getPersona();
        if (personaOrphanCheck != null) {
            EstudianteEspol oldEstudianteEspolOfPersona = personaOrphanCheck.getEstudianteEspol();
            if (oldEstudianteEspolOfPersona != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Persona " + personaOrphanCheck + " already has an item of type EstudianteEspol whose persona column cannot be null. Please make another selection for the persona field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Persona persona = estudianteEspol.getPersona();
            if (persona != null) {
                persona = em.getReference(persona.getClass(), persona.getIdPersona());
                estudianteEspol.setPersona(persona);
            }
            em.persist(estudianteEspol);
            if (persona != null) {
                persona.setEstudianteEspol(estudianteEspol);
                persona = em.merge(persona);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findEstudianteEspol(estudianteEspol.getIdPersona()) != null) {
                throw new PreexistingEntityException("EstudianteEspol " + estudianteEspol + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EstudianteEspol estudianteEspol) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            EstudianteEspol persistentEstudianteEspol = em.find(EstudianteEspol.class, estudianteEspol.getIdPersona());
            Persona personaOld = persistentEstudianteEspol.getPersona();
            Persona personaNew = estudianteEspol.getPersona();
            List<String> illegalOrphanMessages = null;
            if (personaNew != null && !personaNew.equals(personaOld)) {
                EstudianteEspol oldEstudianteEspolOfPersona = personaNew.getEstudianteEspol();
                if (oldEstudianteEspolOfPersona != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Persona " + personaNew + " already has an item of type EstudianteEspol whose persona column cannot be null. Please make another selection for the persona field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (personaNew != null) {
                personaNew = em.getReference(personaNew.getClass(), personaNew.getIdPersona());
                estudianteEspol.setPersona(personaNew);
            }
            estudianteEspol = em.merge(estudianteEspol);
            if (personaOld != null && !personaOld.equals(personaNew)) {
                personaOld.setEstudianteEspol(null);
                personaOld = em.merge(personaOld);
            }
            if (personaNew != null && !personaNew.equals(personaOld)) {
                personaNew.setEstudianteEspol(estudianteEspol);
                personaNew = em.merge(personaNew);
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
                Integer id = estudianteEspol.getIdPersona();
                if (findEstudianteEspol(id) == null) {
                    throw new NonexistentEntityException("The estudianteEspol with id " + id + " no longer exists.");
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
            EstudianteEspol estudianteEspol;
            try {
                estudianteEspol = em.getReference(EstudianteEspol.class, id);
                estudianteEspol.getIdPersona();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estudianteEspol with id " + id + " no longer exists.", enfe);
            }
            Persona persona = estudianteEspol.getPersona();
            if (persona != null) {
                persona.setEstudianteEspol(null);
                persona = em.merge(persona);
            }
            em.remove(estudianteEspol);
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

    public List<EstudianteEspol> findEstudianteEspolEntities() {
        return findEstudianteEspolEntities(true, -1, -1);
    }

    public List<EstudianteEspol> findEstudianteEspolEntities(int maxResults, int firstResult) {
        return findEstudianteEspolEntities(false, maxResults, firstResult);
    }

    private List<EstudianteEspol> findEstudianteEspolEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EstudianteEspol.class));
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

    public EstudianteEspol findEstudianteEspol(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstudianteEspol.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstudianteEspolCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EstudianteEspol> rt = cq.from(EstudianteEspol.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
