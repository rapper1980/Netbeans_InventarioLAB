/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.presentacionBeans;

import inventarioLAB.entidades.Usuario;
import inventarioLAB.logica.UsuarioFacade;
import inventarioLAB.logica.UsuarioFacadeLocal;
import inventarioLAB.logica.beansAdicionales.InformacionEstudianteESPOL;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Edward J. Holguín Holguín
 */
@ManagedBean(name="personalBean")
@ViewScoped
public class PersonalBean {

//    private static final long serialVersionUID = -3832235132261771583L;
    private static final int DECIMALS = 1;
    private static final int CLIENT_ROWS_IN_AJAX_MODE = 10;
    private static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;
    private List<Usuario> todosUsuarios = null;
    private int indiceActual;
    private Usuario usuarioEditar;
    private int pagina = 1;
    
    @EJB
    private UsuarioFacadeLocal usuarioFacadeLocal;
    private InformacionEstudianteESPOL infoEstudinate;
 
    private int filasCliente;
    
    public PersonalBean() {
        this.infoEstudinate = new InformacionEstudianteESPOL();
        this.usuarioFacadeLocal = new UsuarioFacade();
    }

    public List<Usuario> getTodosUsuarios() {
        return todosUsuarios;
    }

    public void setTodosUsuarios(List<Usuario> todosUsuarios) {
        this.todosUsuarios = todosUsuarios;
    }

    public int getIndiceActual() {
        return indiceActual;
    }

    public void setIndiceActual(int indiceActual) {
        this.indiceActual = indiceActual;
    }

    public Usuario getUsuarioEditar() {
        return usuarioEditar;
    }

    public void setUsuarioEditar(Usuario usuarioEditar) {
        this.usuarioEditar = usuarioEditar;
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public int getFilasCliente() {
        return filasCliente;
    }

    public void setFilasCliente(int filasCliente) {
        this.filasCliente = filasCliente;
    }
    
    public void eliminar(){
        this.todosUsuarios.remove(this.todosUsuarios.get(this.indiceActual));
    }

    public InformacionEstudianteESPOL getInfoEstudinate() {
        return infoEstudinate;
    }

    public void setInfoEstudinate(InformacionEstudianteESPOL infoEstudinate) {
        this.infoEstudinate = infoEstudinate;
    }
    
    public void validaEstudianteESPOL(){
        this.infoEstudinate = this.usuarioFacadeLocal.obtenerInformacionAcademicaEstudianteESPOL(null, null);
    }
}
