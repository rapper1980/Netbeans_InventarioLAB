/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.controladoresBean;

import inventarioLAB.entidades.Equipo;
import inventarioLAB.entidades.TipoEquipo;
import inventarioLAB.logica.EquipoFacade;
import inventarioLAB.logica.EquipoFacadeLocal;
import inventarioLAB.util.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Gianni
 */
@ManagedBean(name = "equipoManagedBean")
@SessionScoped
public class EquipoManagedBean implements Serializable {

    @EJB
    private EquipoFacadeLocal equipoFacadeLocal;
    private Equipo equipo;
    private Equipo current;
    private Equipo equipoEditado;   
    private List<Equipo> todosEquipos = null;
    private int selectedItemIndex;
    private int currentIdEquipo;

    public Equipo getEquipoEditado() {
        return equipoEditado;
    }

    public void setEquipoEditado(Equipo equipoEditado) {
        this.equipoEditado = equipoEditado;
    }
    public int getCurrentIdEquipo() {
        return currentIdEquipo;
    }

    public void setCurrentIdEquipo(int currentIdEquipo) {
        this.currentIdEquipo = currentIdEquipo;
    }
    
    public int getSelectedItemIndex() {
        return selectedItemIndex;
    }

    public void setSelectedItemIndex(int selectedItemIndex) {
        this.selectedItemIndex = selectedItemIndex;
    }
    
    // private static Map<String, TipoEquipo> fooMap = new TipoEquipo().map();
    //private List<SelectItem> selectItems;
    //private String selectedItem; // You can also use any Number type, e.g. Long.

//    {
    //      fillSelectItems();
    //}
    public EquipoManagedBean() {
        this.equipo = new Equipo();
        this.equipoFacadeLocal = new EquipoFacade();
        this.current = new Equipo();
        this.todosEquipos = new ArrayList<Equipo>();
    }

    public Equipo getSelected() {
        if (current == null) {
            current = new Equipo();
            selectedItemIndex = -1;
        }
        return current;
    }

    public void registrarEquipo() {
        this.equipoFacadeLocal.create(equipo);
    }

    public String createEquipo() {
        getFacade().create(current);
        return prepareCreate();
    }

    public String prepareCreate() {
        current = new Equipo();
        selectedItemIndex = -1;
        return "Create";
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public Equipo getEquipo() {
        return this.equipo;
    }

    private EquipoFacadeLocal getFacade() {
        return this.equipoFacadeLocal;
    }

    public void setTodosTipos(List<Equipo> todosEquipos) {
        this.todosEquipos = todosEquipos;
    }

    public List<Equipo> getTodosTipos() {
        this.clearTodos();
        return JsfUtil.getAllEquipos(equipoFacadeLocal.findAll(), this.todosEquipos);
    }

    public void clearTodos() {
        this.todosEquipos = new ArrayList<Equipo>();
    }
    
    public void eliminarEquipo(){
        current = this.equipoFacadeLocal.find(currentIdEquipo);
        this.equipoFacadeLocal.remove(current);
        current = new Equipo();
    }
    public void editarEquipo(){
        this.equipoFacadeLocal.edit(equipoEditado);
    }

    
}
