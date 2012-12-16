/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.controladoresBean;

import inventarioLAB.entidades.HistorialUsuario;
import inventarioLAB.entidades.Persona;
import inventarioLAB.entidades.Usuario;
import inventarioLAB.logica.UsuarioFacade;
import inventarioLAB.logica.UsuarioFacadeLocal;
import inventarioLAB.logica.beansAdicionales.InformacionEstudianteESPOL;
import inventarioLAB.logica.excepciones.InsercionIlegal;
import java.util.Date;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Edward J. Holguín Holguín
 */
@ManagedBean(name="usuarioManageBean")
@SessionScoped
public class UsuarioManagedBean {
    @EJB
    private UsuarioFacadeLocal usuarioFacadeLocal;
    private Usuario usuarioSesion;
    private Usuario usuarioEditar;
    private Usuario usuarioTMP;
    private InformacionEstudianteESPOL infoEstudiante;
    /**
     * Creates a new instance of UsuarioManagedBean
     */
    public UsuarioManagedBean() {
        this.usuarioSesion = new Usuario();
        this.usuarioEditar = new Usuario();
        this.usuarioTMP = new Usuario();
        this.usuarioFacadeLocal = new UsuarioFacade();
        this.infoEstudiante = new InformacionEstudianteESPOL();
        
    }
    
    public void iniciarSesion() {
        this.usuarioSesion = this.usuarioFacadeLocal.autenticar(usuarioSesion);
    }
    
    public void salir() {
        //Destruir el Bean
    }
    
    public void registrarUsuarioADMIN(){
        Persona persona = new Persona();
        persona.setTipoIdentificacion("CED");
        persona.setIdentificacion("12345687890");
        persona.setApellidos("ADMIN");
        persona.setNombres("ADMINISTRADOR");
        persona.setSexo("M");
        persona.setFechaNacimiento(new Date());
        this.usuarioSesion.setIdPersona(persona);
        this.usuarioSesion.setEstado("A");
        this.usuarioSesion.setRol("SPU");
        this.usuarioSesion.setModoAutenticacion("INT");
        this.usuarioSesion.setEmail("admin_lab@espol.edu.ec");
        this.usuarioSesion.setFechaIngreso(new Date());
        this.usuarioSesion.setFechaModificacion(new Date());
        this.usuarioSesion.setUsuarioModificacion(this.usuarioSesion.getUsuario());
        System.out.println(this.usuarioSesion);
            System.out.println(this.usuarioSesion.getIdPersona());
//        try {
//            this.usuarioFacadeLocal.crear(usuarioSesion);
//        } catch (InsercionIlegal ex) {
//            String msj = "ERROR: No se pudo crear el usuario";
//        }
    }
    
    public void registrarUsuario(){
        this.usuarioTMP.setFechaIngreso(new Date());
        this.usuarioTMP.setFechaModificacion(new Date());
        this.usuarioTMP.setUsuarioModificacion(this.usuarioSesion.getUsuario());
        try {
            this.usuarioFacadeLocal.crear(usuarioTMP);
        } catch (InsercionIlegal ex) {
            String msj = "ERROR: No se pudo crear el usuario";
        }
    }
    
    public void modificarUsuario(){
        HistorialUsuario historialUsuario = new HistorialUsuario();
        usuarioTMP.setUsuario(usuarioEditar.getUsuario());
        usuarioTMP.setIdPersona(usuarioEditar.getIdPersona());
        usuarioTMP.setEstado(usuarioEditar.getEstado());
        usuarioTMP.setModoAutenticacion(usuarioEditar.getModoAutenticacion());
        usuarioTMP.setRol(usuarioEditar.getRol());
        usuarioTMP.setFechaIngreso(usuarioEditar.getFechaIngreso());
        usuarioTMP.setFechaModificacion(usuarioEditar.getFechaModificacion());
        usuarioTMP.setUsuarioModificacion(usuarioEditar.getUsuarioModificacion());
        //
        historialUsuario.setUsuario(usuarioTMP.getUsuario());
        historialUsuario.setIdPersona(usuarioTMP.getIdPersona().getIdPersona());
        historialUsuario.setEstado(usuarioTMP.getEstado());
        historialUsuario.setModoAutenticacion(usuarioTMP.getModoAutenticacion());
        historialUsuario.setRol(usuarioTMP.getRol());
        historialUsuario.setFechaIngreso(usuarioTMP.getFechaIngreso());
        historialUsuario.setFechaModificacion(usuarioTMP.getFechaModificacion());
        historialUsuario.setUsuarioModificacion(usuarioTMP.getUsuarioModificacion());
        historialUsuario.setObservacion("Modificado por el Usuario " + this.usuarioSesion.getUsuario());
        
        this.usuarioTMP.setFechaIngreso(new Date());
        this.usuarioTMP.setFechaModificacion(new Date());
        this.usuarioTMP.setUsuarioModificacion(this.usuarioSesion.getUsuario());
        try {
            this.usuarioFacadeLocal.crear(usuarioTMP);
        } catch (InsercionIlegal ex) {
            String msj = "ERROR: No se pudo crear el usuario";
        }
    }
    
    public void inactivarUsuario(){
        this.usuarioTMP.setFechaIngreso(new Date());
        this.usuarioTMP.setFechaModificacion(new Date());
        this.usuarioTMP.setUsuarioModificacion(this.usuarioSesion.getUsuario());
        try {
            this.usuarioFacadeLocal.crear(usuarioTMP);
        } catch (InsercionIlegal ex) {
            String msj = "ERROR: No se pudo crear el usuario";
        }
    }
    
    public void verificarInfoEstiduante(){
        this.infoEstudiante = this.usuarioFacadeLocal.obtenerInformacionAcademicaEstudianteESPOL(this.infoEstudiante.getIdentificacion(), this.infoEstudiante.getMatricula());
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
    
    
}
