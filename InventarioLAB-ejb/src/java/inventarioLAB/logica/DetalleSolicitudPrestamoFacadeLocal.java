/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.logica;

import inventarioLAB.entidades.DetalleSolicitudPrestamo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Edward J. Holguín Holguín
 */
@Local
public interface DetalleSolicitudPrestamoFacadeLocal {

    void create(DetalleSolicitudPrestamo detalleSolicitudPrestamo);

    void edit(DetalleSolicitudPrestamo detalleSolicitudPrestamo);

    void remove(DetalleSolicitudPrestamo detalleSolicitudPrestamo);

    DetalleSolicitudPrestamo find(Object id);

    List<DetalleSolicitudPrestamo> findAll();

    List<DetalleSolicitudPrestamo> findRange(int[] range);

    int count();
    
}
