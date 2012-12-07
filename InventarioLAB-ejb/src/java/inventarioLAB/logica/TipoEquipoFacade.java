/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.logica;

import inventarioLAB.entidades.TipoEquipo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Edward J. Holguín Holguín
 */
@Stateless
public class TipoEquipoFacade extends AbstractFacade<TipoEquipo> implements TipoEquipoFacadeLocal {
    @PersistenceContext(unitName = "InventarioLAB-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoEquipoFacade() {
        super(TipoEquipo.class);
    }
    
}
