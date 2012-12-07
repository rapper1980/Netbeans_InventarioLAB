/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.logica;

import inventarioLAB.entidades.HistorialUsuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Edward J. Holguín Holguín
 */
@Stateless
public class HistorialUsuarioFacade extends AbstractFacade<HistorialUsuario> implements HistorialUsuarioFacadeLocal {
    @PersistenceContext(unitName = "InventarioLAB-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HistorialUsuarioFacade() {
        super(HistorialUsuario.class);
    }
    
}
