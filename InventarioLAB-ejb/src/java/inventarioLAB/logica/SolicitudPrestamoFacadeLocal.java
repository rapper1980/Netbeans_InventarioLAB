/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.logica;

import inventarioLAB.entidades.SolicitudPrestamo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Edward J. Holguín Holguín
 */
@Local
public interface SolicitudPrestamoFacadeLocal {

    void create(SolicitudPrestamo solicitudPrestamo);

    void edit(SolicitudPrestamo solicitudPrestamo);

    void remove(SolicitudPrestamo solicitudPrestamo);

    SolicitudPrestamo find(Object id);

    List<SolicitudPrestamo> findAll();

    List<SolicitudPrestamo> findRange(int[] range);

    int count();
    
}
