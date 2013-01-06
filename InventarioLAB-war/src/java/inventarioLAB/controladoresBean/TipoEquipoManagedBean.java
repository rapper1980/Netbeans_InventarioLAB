/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.controladoresBean;

import inventarioLAB.entidades.Equipo;
import inventarioLAB.entidades.TipoEquipo;
import inventarioLAB.logica.TipoEquipoFacade;
import inventarioLAB.logica.TipoEquipoFacadeLocal;
import inventarioLAB.util.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;

/**
 *
 * @author Gianni
 */
@ManagedBean(name = "tipoEquipoManagedBean")
@SessionScoped
public class TipoEquipoManagedBean implements Serializable {
    @EJB
    private TipoEquipoFacadeLocal tipoEquipoFacadeLocal;
    private TipoEquipo tipoEquipo;
    private int currentIdTipo;
    private TipoEquipo current;

    private List<TipoEquipo> todosTipos = null;
    private TipoEquipo tipoEquipoEditado;

    public TipoEquipo getTipoEquipoEditado() {
        return tipoEquipoEditado;
    }

    public void setTipoEquipoEditado(TipoEquipo tipoEquipoEditado) {
        this.tipoEquipoEditado = tipoEquipoEditado;
    }
    
    public TipoEquipoManagedBean(){
        this.tipoEquipoFacadeLocal = new TipoEquipoFacade();
        this.tipoEquipo = new TipoEquipo();
        this.todosTipos = new ArrayList<TipoEquipo>();
        //TipoEquipo tmp = new TipoEquipo(4, "prueba3", "S", "S");
        //this.todosTipos.add(tmp);
    }
    public int getCurrentIdTipo() {
        return currentIdTipo;
    }

    public void setCurrentIdTipo(int currentTipo) {
        this.currentIdTipo = currentTipo;
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
    public void editarTipoEquipo(){
        this.tipoEquipoFacadeLocal.edit(tipoEquipoEditado);
    }    
    public void eliminarTipoEquipo(){
        current = this.tipoEquipoFacadeLocal.find(currentIdTipo);
        this.tipoEquipoFacadeLocal.remove(current);
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
    
    @FacesConverter(forClass = TipoEquipo.class)
    public static class TipoEquipoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            
            TipoEquipoManagedBean controller = (TipoEquipoManagedBean) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tipoEquipoManagedBean");
            return controller.tipoEquipoFacadeLocal.find(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof TipoEquipo) {
                TipoEquipo o = (TipoEquipo) object;
                return getStringKey(o.getIdTipoEquipo());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + TipoEquipo.class.getName());
            }
        }
    }
}
