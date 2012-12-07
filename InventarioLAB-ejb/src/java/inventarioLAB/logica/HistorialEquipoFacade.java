/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.logica;

import inventarioLAB.entidades.HistorialEquipo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Edward J. Holguín Holguín
 */
@Stateless
public class HistorialEquipoFacade extends AbstractFacade<HistorialEquipo> implements HistorialEquipoFacadeLocal {
    @PersistenceContext(unitName = "InventarioLAB-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HistorialEquipoFacade() {
        super(HistorialEquipo.class);
    }
    
}
