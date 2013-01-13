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
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Edward J. Holguín Holguín
 */
@ManagedBean(name="usuarioManagedBean")
@SessionScoped
public class UsuarioManagedBean {
    @EJB
    private PersonaFacadeLocal personaFacadeLocal;
    private Persona personaSesion;
    @EJB
    private UsuarioFacadeLocal usuarioFacadeLocal;
    private Usuario usuarioSesion;

    /**
     * Creates a new instance of UsuarioManagedBean
     */
    public UsuarioManagedBean() {
        this.usuarioFacadeLocal = new UsuarioFacade();
        this.usuarioSesion = new Usuario();
        this.personaFacadeLocal = new PersonaFacade();
        this.personaSesion = new Persona();
    }

    public String iniciarSesion() {
        String res = "main";
        this.usuarioSesion = this.usuarioFacadeLocal.autenticar(usuarioSesion.getUsuario(), usuarioSesion.getClave(), usuarioSesion.getModoAutenticacion());
        if(usuarioSesion == null){
            res = "error";
            usuarioSesion = new Usuario();
        } else{
            this.personaSesion = this.personaFacadeLocal.find(this.usuarioSesion.getIdPersona());
        }
        return res;
    }

    public String salir() {
        //Destruir el Bean
        return "login";
    }

    public Usuario getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setUsuarioSesion(Usuario usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }

    public Persona getPersonaSesion() {
        return personaSesion;
    }

    public void setPersonaSesion(Persona personaSesion) {
        this.personaSesion = personaSesion;
    }
    
}
