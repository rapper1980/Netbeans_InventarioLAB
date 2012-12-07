/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.logica;

import inventarioLAB.entidades.HistorialUsuario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Edward J. Holguín Holguín
 */
@Local
public interface HistorialUsuarioFacadeLocal {

    void create(HistorialUsuario historialUsuario);

    void edit(HistorialUsuario historialUsuario);

    void remove(HistorialUsuario historialUsuario);

    HistorialUsuario find(Object id);

    List<HistorialUsuario> findAll();

    List<HistorialUsuario> findRange(int[] range);

    int count();
    
}
