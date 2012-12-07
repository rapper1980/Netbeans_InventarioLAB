/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.logica;

import inventarioLAB.entidades.HistorialEquipo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Edward J. Holguín Holguín
 */
@Local
public interface HistorialEquipoFacadeLocal {

    void create(HistorialEquipo historialEquipo);

    void edit(HistorialEquipo historialEquipo);

    void remove(HistorialEquipo historialEquipo);

    HistorialEquipo find(Object id);

    List<HistorialEquipo> findAll();

    List<HistorialEquipo> findRange(int[] range);

    int count();
    
}
