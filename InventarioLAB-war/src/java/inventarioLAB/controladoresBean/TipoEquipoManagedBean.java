/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.controladoresBean;

import inventarioLAB.entidades.TipoEquipo;
import inventarioLAB.logica.TipoEquipoFacade;
import inventarioLAB.logica.TipoEquipoFacadeLocal;
import inventarioLAB.util.JsfUtil;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Gianni
 */
@ManagedBean(name = "tipoEquipoManagedBean")
@SessionScoped
public class TipoEquipoManagedBean {
    @EJB
    private TipoEquipoFacadeLocal tipoEquipoFacadeLocal;
    private TipoEquipo tipoEquipo;
    private int currentIndex;
    private List<TipoEquipo> todosTipos = null;
    
    public TipoEquipoManagedBean(){
        this.tipoEquipoFacadeLocal = new TipoEquipoFacade();
        this.tipoEquipo = new TipoEquipo();
        this.todosTipos = new ArrayList<TipoEquipo>();
        //TipoEquipo tmp = new TipoEquipo(4, "prueba3", "S", "S");
        //this.todosTipos.add(tmp);
    }
    public void setTipoEquipo(TipoEquipo tipoEquipo){
        this.tipoEquipo = tipoEquipo;
    }
    public TipoEquipo getTipoEquipo(){
        return this.tipoEquipo;
    }
    public void registrarTipoEquipo(){
        this.tipoEquipoFacadeLocal.create(tipoEquipo);
    }
    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(tipoEquipoFacadeLocal.findAll(), true);
    }
    public List<TipoEquipo> getTodosTipos() {
        this.clearTodos();
        return JsfUtil.getAllItems(tipoEquipoFacadeLocal.findAll(),this.todosTipos);
    }
    public void setTodosTipos(List<TipoEquipo> todosTipos) {
        this.todosTipos=todosTipos;
    }
    public void clearTodos(){
        this.todosTipos=new ArrayList<TipoEquipo>();
    }
}
