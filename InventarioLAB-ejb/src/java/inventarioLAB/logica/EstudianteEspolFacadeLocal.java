/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.logica;

import inventarioLAB.entidades.EstudianteEspol;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Edward J. Holguín Holguín
 */
@Local
public interface EstudianteEspolFacadeLocal {

    void create(EstudianteEspol estudianteEspol);

    void edit(EstudianteEspol estudianteEspol);

    void remove(EstudianteEspol estudianteEspol);

    EstudianteEspol find(Object id);

    List<EstudianteEspol> findAll();

    List<EstudianteEspol> findRange(int[] range);

    int count();
    
}
