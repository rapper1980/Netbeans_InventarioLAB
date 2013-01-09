/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.controladoresBean;

import inventarioLAB.logica.SolicitudPrestamoFacadeLocal;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Gianni
 */
@ManagedBean(name = "solicitudManagedBean")
@SessionScoped
public class SolicitudManagedBean implements Serializable {
    @EJB
    private SolicitudPrestamoFacadeLocal solicitudFacadeLocal;
    
    public void crearSolicitud(){
    
    }
}
