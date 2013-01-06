/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.presentacionBeans;

import inventarioLAB.entidades.EstudianteEspol;
import inventarioLAB.entidades.Persona;
import inventarioLAB.entidades.Usuario;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author Edward J. Holguín Holguín
 */
@ManagedBean(name="personalBean")
@ViewScoped
public class PersonalBean implements Serializable{

    private static final long serialVersionUID = -3832235132261771583L;
    private static final int DECIMALS = 1;
    private static final int CLIENT_ROWS_IN_AJAX_MODE = 10;
    private static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;
    private List<Usuario> todosUsuarios = null;
    private int indiceActual;
    private Usuario usuarioEditar;
    private int pagina = 1;
    
    private int clientRows;

    public PersonalBean() {
//        this.todosUsuarios = new ArrayList<Usuario>();
//        for(int i = 0; i < 5; i++){
//            
//            this.todosUsuarios.add(tmp);
//        }
    }

    
    public void switchAjaxLoading(ValueChangeEvent event) {
        this.clientRows = (Boolean) event.getNewValue() ? CLIENT_ROWS_IN_AJAX_MODE : 0;
    }
    
    public void eliminar() {
        todosUsuarios.remove(todosUsuarios.get(indiceActual));
    }
 
    public void grabar() {
        todosUsuarios.set(indiceActual, usuarioEditar);
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

    public int getClientRows() {
        return clientRows;
    }

    public void setClientRows(int clientRows) {
        this.clientRows = clientRows;
    }
    
    
}
