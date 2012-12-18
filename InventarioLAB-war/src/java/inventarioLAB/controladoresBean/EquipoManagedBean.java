/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.controladoresBean;

import inventarioLAB.entidades.Equipo;
import inventarioLAB.entidades.TipoEquipo;
import inventarioLAB.logica.EquipoFacade;
import inventarioLAB.logica.EquipoFacadeLocal;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Gianni
 */
@ManagedBean(name="equipoManagedBean")
@SessionScoped
public class EquipoManagedBean implements Serializable {
    @EJB
    private EquipoFacadeLocal equipoFacadeLocal;
    private Equipo equipo;
    private Equipo current;
    private int selectedItemIndex;
   // private static Map<String, TipoEquipo> fooMap = new TipoEquipo().map();
    //private List<SelectItem> selectItems;
    //private String selectedItem; // You can also use any Number type, e.g. Long.

//    {
  //      fillSelectItems();
    //}
    
    public EquipoManagedBean(){
        this.equipo = new Equipo();
        this.equipoFacadeLocal = new EquipoFacade();    
        this.current = new Equipo();
    }
    public Equipo getSelected() {
        if (current == null) {
            current = new Equipo();
            selectedItemIndex = -1;
        }
        return current;
    }
    public void registrarEquipo(){
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
    public void setEquipo(Equipo equipo){
        this.equipo = equipo;
    }
    
    public Equipo getEquipo(){
        return this.equipo;
    }
    private EquipoFacadeLocal getFacade() {
        return this.equipoFacadeLocal;
    }
    
}
