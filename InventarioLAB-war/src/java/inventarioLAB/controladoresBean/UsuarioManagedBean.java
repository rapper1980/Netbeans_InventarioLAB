/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.controladoresBean;

import inventarioLAB.entidades.Persona;
import inventarioLAB.entidades.Usuario;
import inventarioLAB.logica.PersonaFacade;
import inventarioLAB.logica.PersonaFacadeLocal;
import inventarioLAB.logica.UsuarioFacade;
import inventarioLAB.logica.UsuarioFacadeLocal;
import inventarioLAB.logica.beansAdicionales.InformacionEstudianteESPOL;
import inventarioLAB.logica.excepciones.InsercionIlegal;
import inventarioLAB.logica.excepciones.RestriccionUnica;
import java.util.ArrayList;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Edward J. Holguín Holguín
 */
@ManagedBean(name = "usuarioManageBean")
@SessionScoped
public class UsuarioManagedBean {

    @EJB
    private UsuarioFacadeLocal usuarioFacadeLocal;
    private Usuario usuarioSesion;
    private Usuario usuarioEditar;
    private Usuario usuarioTMP;
    private InformacionEstudianteESPOL infoEstudiante;
    @EJB
    private PersonaFacadeLocal personaFacadeLocal;
    private Persona personaEditar;

    /**
     * Creates a new instance of UsuarioManagedBean
     */
    public UsuarioManagedBean() {
        this.usuarioSesion = new Usuario();
        this.usuarioEditar = new Usuario();
        this.usuarioTMP = new Usuario();
        this.usuarioFacadeLocal = new UsuarioFacade();
        this.infoEstudiante = new InformacionEstudianteESPOL();

        this.personaFacadeLocal = new PersonaFacade();
        this.personaEditar = new Persona();
    }

    public void iniciarSesion() {
        this.usuarioSesion = this.usuarioFacadeLocal.autenticar(usuarioSesion);
    }

    public void salir() {
        //Destruir el Bean
    }

    public void registrarUsuario() {
    }

    public void modificarUsuario() {
    }

    public void inactivarUsuario() {
    }

    public void verificarInfoEstiduante() {
        this.infoEstudiante = this.usuarioFacadeLocal.obtenerInformacionAcademicaEstudianteESPOL(this.infoEstudiante.getIdentificacion(), this.infoEstudiante.getMatricula());
        
        this.personaEditar.setTipoIdentificacion(this.infoEstudiante.getTipo_identif());
        this.personaEditar.setIdentificacion(this.infoEstudiante.getIdentificacion());
//        this.personaEditar.setSexo(this.infoEstudiante.getS);
        this.personaEditar.setNombres(this.infoEstudiante.getNombres());
        this.personaEditar.setApellidos(this.infoEstudiante.getApellidos());
        
    }
    

    public UsuarioFacadeLocal getUsuarioFacadeLocal() {
        return usuarioFacadeLocal;
    }

    public void setUsuarioFacadeLocal(UsuarioFacadeLocal usuarioFacadeLocal) {
        this.usuarioFacadeLocal = usuarioFacadeLocal;
    }

    public Usuario getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setUsuarioSesion(Usuario usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }

    public Usuario getUsuarioEditar() {
        return usuarioEditar;
    }

    public void setUsuarioEditar(Usuario usuarioEditar) {
        this.usuarioEditar = usuarioEditar;
    }

    public Usuario getUsuarioTMP() {
        return usuarioTMP;
    }

    public void setUsuarioTMP(Usuario usuarioTMP) {
        this.usuarioTMP = usuarioTMP;
    }

    public InformacionEstudianteESPOL getInfoEstudiante() {
        return infoEstudiante;
    }

    public void setInfoEstudiante(InformacionEstudianteESPOL infoEstudiante) {
        this.infoEstudiante = infoEstudiante;
    }

    public Persona getPersonaEditar() {
        return personaEditar;
    }

    public void setPersonaEditar(Persona personaEditar) {
        this.personaEditar = personaEditar;
    }

    public PersonaFacadeLocal getPersonaFacadeLocal() {
        return personaFacadeLocal;
    }

    public void setPersonaFacadeLocal(PersonaFacadeLocal personaFacadeLocal) {
        this.personaFacadeLocal = personaFacadeLocal;
    }
    
}
