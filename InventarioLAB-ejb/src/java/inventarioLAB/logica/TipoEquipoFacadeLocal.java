/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.logica;

import inventarioLAB.entidades.TipoEquipo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Edward J. Holguín Holguín
 */
@Local
public interface TipoEquipoFacadeLocal {

    void create(TipoEquipo tipoEquipo);

    void edit(TipoEquipo tipoEquipo);

    void remove(TipoEquipo tipoEquipo);

    TipoEquipo find(Object id);

    List<TipoEquipo> findAll();

    List<TipoEquipo> findRange(int[] range);

    int count();
    
}
