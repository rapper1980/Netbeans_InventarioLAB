/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.logica;

import inventarioLAB.entidades.DetalleSolicitudPrestamo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Edward J. Holguín Holguín
 */
@Stateless
public class DetalleSolicitudPrestamoFacade extends AbstractFacade<DetalleSolicitudPrestamo> implements DetalleSolicitudPrestamoFacadeLocal {
    @PersistenceContext(unitName = "InventarioLAB-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DetalleSolicitudPrestamoFacade() {
        super(DetalleSolicitudPrestamo.class);
    }
    
}
