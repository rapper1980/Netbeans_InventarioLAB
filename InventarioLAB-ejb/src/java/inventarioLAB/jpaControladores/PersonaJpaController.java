/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.jpaControladores;

import inventarioLAB.entidades.Persona;
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
import inventarioLAB.entidades.EstudianteEspol;
import inventarioLAB.entidades.Usuario;
import java.util.ArrayList;
import java.util.Collection;
import javax.transaction.UserTransaction;

/**
 *
 * @author Edward J. Holguín Holguín
 */
public class PersonaJpaController implements Serializable {

    public PersonaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Persona persona) throws RollbackFailureException, Exception {
        if (persona.getUsuarioCollection() == null) {
            persona.setUsuarioCollection(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            EstudianteEspol estudianteEspol = persona.getEstudianteEspol();
            if (estudianteEspol != null) {
                estudianteEspol = em.getReference(estudianteEspol.getClass(), estudianteEspol.getIdPersona());
                persona.setEstudianteEspol(estudianteEspol);
            }
            Collection<Usuario> attachedUsuarioCollection = new ArrayList<Usuario>();
            for (Usuario usuarioCollectionUsuarioToAttach : persona.getUsuarioCollection()) {
                usuarioCollectionUsuarioToAttach = em.getReference(usuarioCollectionUsuarioToAttach.getClass(), usuarioCollectionUsuarioToAttach.getUsuario());
                attachedUsuarioCollection.add(usuarioCollectionUsuarioToAttach);
            }
            persona.setUsuarioCollection(attachedUsuarioCollection);
            em.persist(persona);
            if (estudianteEspol != null) {
                Persona oldPersonaOfEstudianteEspol = estudianteEspol.getPersona();
                if (oldPersonaOfEstudianteEspol != null) {
                    oldPersonaOfEstudianteEspol.setEstudianteEspol(null);
                    oldPersonaOfEstudianteEspol = em.merge(oldPersonaOfEstudianteEspol);
                }
                estudianteEspol.setPersona(persona);
                estudianteEspol = em.merge(estudianteEspol);
            }
            for (Usuario usuarioCollectionUsuario : persona.getUsuarioCollection()) {
                Persona oldIdPersonaOfUsuarioCollectionUsuario = usuarioCollectionUsuario.getIdPersona();
                usuarioCollectionUsuario.setIdPersona(persona);
                usuarioCollectionUsuario = em.merge(usuarioCollectionUsuario);
                if (oldIdPersonaOfUsuarioCollectionUsuario != null) {
                    oldIdPersonaOfUsuarioCollectionUsuario.getUsuarioCollection().remove(usuarioCollectionUsuario);
                    oldIdPersonaOfUsuarioCollectionUsuario = em.merge(oldIdPersonaOfUsuarioCollectionUsuario);
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

    public void edit(Persona persona) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Persona persistentPersona = em.find(Persona.class, persona.getIdPersona());
            EstudianteEspol estudianteEspolOld = persistentPersona.getEstudianteEspol();
            EstudianteEspol estudianteEspolNew = persona.getEstudianteEspol();
            Collection<Usuario> usuarioCollectionOld = persistentPersona.getUsuarioCollection();
            Collection<Usuario> usuarioCollectionNew = persona.getUsuarioCollection();
            List<String> illegalOrphanMessages = null;
            if (estudianteEspolOld != null && !estudianteEspolOld.equals(estudianteEspolNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain EstudianteEspol " + estudianteEspolOld + " since its persona field is not nullable.");
            }
            for (Usuario usuarioCollectionOldUsuario : usuarioCollectionOld) {
                if (!usuarioCollectionNew.contains(usuarioCollectionOldUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuario " + usuarioCollectionOldUsuario + " since its idPersona field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (estudianteEspolNew != null) {
                estudianteEspolNew = em.getReference(estudianteEspolNew.getClass(), estudianteEspolNew.getIdPersona());
                persona.setEstudianteEspol(estudianteEspolNew);
            }
            Collection<Usuario> attachedUsuarioCollectionNew = new ArrayList<Usuario>();
            for (Usuario usuarioCollectionNewUsuarioToAttach : usuarioCollectionNew) {
                usuarioCollectionNewUsuarioToAttach = em.getReference(usuarioCollectionNewUsuarioToAttach.getClass(), usuarioCollectionNewUsuarioToAttach.getUsuario());
                attachedUsuarioCollectionNew.add(usuarioCollectionNewUsuarioToAttach);
            }
            usuarioCollectionNew = attachedUsuarioCollectionNew;
            persona.setUsuarioCollection(usuarioCollectionNew);
            persona = em.merge(persona);
            if (estudianteEspolNew != null && !estudianteEspolNew.equals(estudianteEspolOld)) {
                Persona oldPersonaOfEstudianteEspol = estudianteEspolNew.getPersona();
                if (oldPersonaOfEstudianteEspol != null) {
                    oldPersonaOfEstudianteEspol.setEstudianteEspol(null);
                    oldPersonaOfEstudianteEspol = em.merge(oldPersonaOfEstudianteEspol);
                }
                estudianteEspolNew.setPersona(persona);
                estudianteEspolNew = em.merge(estudianteEspolNew);
            }
            for (Usuario usuarioCollectionNewUsuario : usuarioCollectionNew) {
                if (!usuarioCollectionOld.contains(usuarioCollectionNewUsuario)) {
                    Persona oldIdPersonaOfUsuarioCollectionNewUsuario = usuarioCollectionNewUsuario.getIdPersona();
                    usuarioCollectionNewUsuario.setIdPersona(persona);
                    usuarioCollectionNewUsuario = em.merge(usuarioCollectionNewUsuario);
                    if (oldIdPersonaOfUsuarioCollectionNewUsuario != null && !oldIdPersonaOfUsuarioCollectionNewUsuario.equals(persona)) {
                        oldIdPersonaOfUsuarioCollectionNewUsuario.getUsuarioCollection().remove(usuarioCollectionNewUsuario);
                        oldIdPersonaOfUsuarioCollectionNewUsuario = em.merge(oldIdPersonaOfUsuarioCollectionNewUsuario);
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
                Integer id = persona.getIdPersona();
                if (findPersona(id) == null) {
                    throw new NonexistentEntityException("The persona with id " + id + " no longer exists.");
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
            Persona persona;
            try {
                persona = em.getReference(Persona.class, id);
                persona.getIdPersona();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The persona with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            EstudianteEspol estudianteEspolOrphanCheck = persona.getEstudianteEspol();
            if (estudianteEspolOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the EstudianteEspol " + estudianteEspolOrphanCheck + " in its estudianteEspol field has a non-nullable persona field.");
            }
            Collection<Usuario> usuarioCollectionOrphanCheck = persona.getUsuarioCollection();
            for (Usuario usuarioCollectionOrphanCheckUsuario : usuarioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Usuario " + usuarioCollectionOrphanCheckUsuario + " in its usuarioCollection field has a non-nullable idPersona field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(persona);
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

    public List<Persona> findPersonaEntities() {
        return findPersonaEntities(true, -1, -1);
    }

    public List<Persona> findPersonaEntities(int maxResults, int firstResult) {
        return findPersonaEntities(false, maxResults, firstResult);
    }

    private List<Persona> findPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Persona.class));
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

    public Persona findPersona(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Persona.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Persona> rt = cq.from(Persona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
