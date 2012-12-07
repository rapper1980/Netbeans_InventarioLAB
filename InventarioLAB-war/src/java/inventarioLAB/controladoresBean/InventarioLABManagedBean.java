/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.controladoresBean;

import inventarioLAB.entidades.Usuario;
import inventarioLAB.logica.UsuarioFacade;
import inventarioLAB.logica.UsuarioFacadeLocal;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Edward J. Holguín Holguín
 */
@ManagedBean(name="inventarioLABManagedBean")
@SessionScoped
public class InventarioLABManagedBean {
    @EJB
    private UsuarioFacadeLocal usuarioFacadeLocal;
    private Usuario usuario;

    /** Creates a new instance of InventarioLABManagedBean */
    public InventarioLABManagedBean() {
        this.usuario = new Usuario();
        this.usuarioFacadeLocal = new UsuarioFacade();
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public String login(){
        return this.usuarioFacadeLocal.autenticar(usuario)? "main" : "error";
    }
}
