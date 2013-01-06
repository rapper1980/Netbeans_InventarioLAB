/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.logica;

import inventarioLAB.entidades.EstudianteEspol;
import inventarioLAB.entidades.Persona;
import inventarioLAB.entidades.Usuario;
import inventarioLAB.logica.excepciones.InsercionIlegal;
import inventarioLAB.logica.excepciones.RestriccionUnica;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Edward J. Holguín Holguín
 */
@Stateless
public class PersonaFacade extends AbstractFacade<Persona> implements PersonaFacadeLocal {

    @PersistenceContext(unitName = "InventarioLAB-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonaFacade() {
        super(Persona.class);
    }

    /**
     * Crea/Registra/Inserta una persona en la Bases de Datos
     *
     * @param persona
     */
    @Override
    public void crear(Persona persona) throws RestriccionUnica, InsercionIlegal {
        EstudianteEspol estudiante;
        Usuario usuario;

        estudiante = persona.getEstudianteEspol();
        usuario = persona.getUsuario();

        if (existeIdentificacion(persona.getIdentificacion())) {
            throw new RestriccionUnica("Error al crear la Persona, la identificacion " + persona.getIdentificacion() + " ya existe");
        }

        if (estudiante != null) {
            if (existeEstudiante(estudiante.getMatricula())) {
                throw new InsercionIlegal("Error al crear la Persona, la matricula " + estudiante.getMatricula() + " ya existe");
            }
        }

        if (usuario != null) {
            if (existeUsuario(usuario.getUsuario())) {
                throw new InsercionIlegal("Error al crear la Persona, el usuario " + usuario.getUsuario() + " ya existe asociado a otra persona");
            }
        }
        
        this.em.persist(persona);

    }

    /**
     *
     * @param persona
     */
    @Override
    public void editar(Persona persona) throws RestriccionUnica, InsercionIlegal {
        this.em.merge(persona);
    }
    
    /**
     *
     * @param clave
     */
    @Override
    public void registrarADMIN(String clave) throws RestriccionUnica, InsercionIlegal {
        Persona persona;
        Usuario usuario;
        
        persona = new Persona();
        usuario = new Usuario();
        
        persona.setTipoIdentificacion("CED");
        persona.setIdentificacion("12345687890");
        persona.setNombres("ADMINISTRADOR");
        persona.setApellidos("ADMIN");
        persona.setFechaNacimiento(new Date());
        
        usuario.setPersona(persona);
//        usuario.setIdPersona(persona.getIdPersona());
        usuario.setUsuario("admin");
        usuario.setClave(clave);
        usuario.setEmail("admin_lab@espol.edu.ec");
        usuario.setEstado("A");
        usuario.setModoAutenticacion("INT");
        usuario.setRol("SPU");
        usuario.setFechaIngreso(new Date());
        usuario.setFechaModificacion(new Date());
        usuario.setUsuarioModificacion("admin");
        
        persona.setUsuario(usuario);
        
        this.crear(persona);
        
        
    }

    /**
     *
     * @param identificacion
     * @return
     */
    private boolean existeIdentificacion(String identificacion) {
        List<Persona> lista;

        Query query = this.em.createNamedQuery("Persona.findByIdentificacion");
        query.setParameter("identificacion", identificacion);
        lista = (List<Persona>) query.getResultList();

        return !lista.isEmpty();
    }

    /**
     *
     * @param usuario
     * @return
     */
    private boolean existeUsuario(String usuario) {
        List<Usuario> listaUsuarios;

        Query query = this.em.createNamedQuery("Usuario.findByUsuario");
        query.setParameter("usuario", usuario);
        listaUsuarios = (List<Usuario>) query.getResultList();

        return !listaUsuarios.isEmpty();
    }

    private boolean existeEstudiante(String matricula) {
        List<EstudianteEspol> listaEstudiantes;

        Query query = this.em.createNamedQuery("EstudianteEspol.findByMatricula");
        listaEstudiantes = (List<EstudianteEspol>) query.setParameter("matricula", matricula);

        return !listaEstudiantes.isEmpty();
    }

}
