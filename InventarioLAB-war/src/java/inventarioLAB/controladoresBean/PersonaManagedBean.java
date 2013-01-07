/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.controladoresBean;

import inventarioLAB.entidades.EstudianteEspol;
import inventarioLAB.entidades.Persona;
import inventarioLAB.entidades.Usuario;
import inventarioLAB.logica.EstudianteEspolFacade;
import inventarioLAB.logica.EstudianteEspolFacadeLocal;
import inventarioLAB.logica.PersonaFacade;
import inventarioLAB.logica.PersonaFacadeLocal;
import inventarioLAB.logica.UsuarioFacade;
import inventarioLAB.logica.UsuarioFacadeLocal;
import inventarioLAB.logica.beansAdicionales.InformacionEstudianteESPOL;
import inventarioLAB.logica.excepciones.InsercionIlegal;
import inventarioLAB.logica.excepciones.RestriccionUnica;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Edward J. Holguín Holguín
 */
@ManagedBean(name="personaManagedBean")
@SessionScoped
public class PersonaManagedBean {
    @EJB
    private PersonaFacadeLocal personaFacadeLocal;
    private Persona personaEditar;
    @EJB
    private EstudianteEspolFacadeLocal estudianteEspolFacadeLocal;
    private EstudianteEspol estudianteEspol;
    @EJB
    private UsuarioFacadeLocal usuarioFacadeLocal;
    private Usuario usuarioEditar;
    
    private InformacionEstudianteESPOL infoEstudiante;

    /**
     * Creates a new instance of PersonaManagedBean
     */
    public PersonaManagedBean() {
        this.personaFacadeLocal = new PersonaFacade();
        this.personaEditar = new Persona();
        this.usuarioFacadeLocal = new UsuarioFacade();
        this.usuarioEditar = new Usuario();
        
        this.estudianteEspolFacadeLocal = new EstudianteEspolFacade();
        this.estudianteEspol = new EstudianteEspol();
        
        this.infoEstudiante = new InformacionEstudianteESPOL();
    }

    public void registrarADMIN(){
        Persona persona;
        Usuario usuario;
        
        persona = new Persona();
        usuario = new Usuario();
        
        persona.setTipoIdentificacion("CED");
        persona.setIdentificacion("12345687890");
        persona.setNombres("ADMINISTRADOR");
        persona.setApellidos("ADMIN");
        persona.setFechaNacimiento(new Date());
        
//        usuario.setPersona(persona);
        usuario.setIdPersona(persona.getIdPersona());
        usuario.setUsuario("admin");
        usuario.setClave(this.usuarioEditar.getClave());
        usuario.setEmail("admin_lab@espol.edu.ec");
        usuario.setEstado("A");
        usuario.setModoAutenticacion("INT");
        usuario.setRol("SPU");
        usuario.setFechaIngreso(new Date());
        usuario.setFechaModificacion(new Date());
        usuario.setUsuarioModificacion("admin");
        
//        persona.setUsuario(usuario);
        
        System.out.println("ANTES");
        try {
            this.personaFacadeLocal.crear(persona);
            usuario.setIdPersona(persona.getIdPersona());
            this.usuarioFacadeLocal.create(usuario);
        } catch (RestriccionUnica rue) {
            //Logger.getLogger(PersonaManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error 1: " + rue);
        } catch (InsercionIlegal iie) {
            //Logger.getLogger(PersonaManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error 2: " + iie);
        }
    }

    public Persona getPersonaEditar() {
        return personaEditar;
    }

    public void setPersonaEditar(Persona personaEditar) {
        this.personaEditar = personaEditar;
    }

    public Usuario getUsuarioEditar() {
        return usuarioEditar;
    }

    public void setUsuarioEditar(Usuario usuarioEditar) {
        this.usuarioEditar = usuarioEditar;
    }

    public InformacionEstudianteESPOL getInfoEstudiante() {
        return infoEstudiante;
    }

    public void setInfoEstudiante(InformacionEstudianteESPOL infoEstudiante) {
        this.infoEstudiante = infoEstudiante;
    }
    
    public void verificarInfoEstiduante() {
        this.infoEstudiante = this.usuarioFacadeLocal.obtenerInformacionAcademicaEstudianteESPOL(this.infoEstudiante.getIdentificacion(), this.infoEstudiante.getMatricula());
    }
    
    public void llenarInfoPersonaEstudianteUsuario(){
        this.personaEditar.setTipoIdentificacion(this.infoEstudiante.getTipo_identif());
        this.personaEditar.setIdentificacion(this.infoEstudiante.getIdentificacion());
        this.personaEditar.setNombres(this.infoEstudiante.getNombres());
        this.personaEditar.setApellidos(this.infoEstudiante.getApellidos());
        DateFormat df = DateFormat.getDateInstance();
        try {
            this.personaEditar.setFechaNacimiento(df.parse(this.infoEstudiante.getFecha_nacimiento()));
        } catch (ParseException ex) {
//            Logger.getLogger(PersonaManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            this.personaEditar.setFechaNacimiento(new Date());
        }
        
        this.estudianteEspol.setMatricula(this.infoEstudiante.getMatricula());
        this.estudianteEspol.setCodUnidad(this.infoEstudiante.getUnidadAcademica());
        this.estudianteEspol.setCodDivision(this.infoEstudiante.getDivision());
        this.estudianteEspol.setCodCarrera(this.infoEstudiante.getCarrera());
        this.estudianteEspol.setCodEspecializacion(this.infoEstudiante.getEspecializ());
        this.estudianteEspol.setNombreCarrera(this.infoEstudiante.getNombreCarrera());
        this.estudianteEspol.setEmail(this.infoEstudiante.getEmail());
        
        this.usuarioEditar.setUsuario(this.infoEstudiante.getEmail().substring(0, this.infoEstudiante.getEmail().indexOf("@")));
        this.usuarioEditar.setEmail(this.infoEstudiante.getEmail());
        this.usuarioEditar.setEstado("A");
        //this.usuarioEditar.setRol(null);
//        this.usuarioEditar.setModoAutenticacion(null);
        this.usuarioEditar.setFechaIngreso(new Date());
        this.usuarioEditar.setFechaModificacion(new Date());
        this.usuarioEditar.setUsuarioModificacion("admin");
        
        
    }
    
    public void registrarUsuario(){
        llenarInfoPersonaEstudianteUsuario();
        try {
            this.personaFacadeLocal.crear(this.personaEditar);
            this.estudianteEspol.setIdPersona(this.personaEditar.getIdPersona());
            this.estudianteEspolFacadeLocal.create(this.estudianteEspol);
            this.usuarioEditar.setIdPersona(this.personaEditar.getIdPersona());
            this.usuarioFacadeLocal.create(this.usuarioEditar);
        } catch (Exception e) {
            System.out.println("ERROR Q: "+ e);
        }
    }
    

}
