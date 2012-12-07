/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.logica;

import inventarioLAB.entidades.Usuario;
import inventarioLAB.webServicesClient.ESPOL.DirectorioEspol;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author Edward J. Holguín Holguín
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> implements UsuarioFacadeLocal {
    @WebServiceRef(wsdlLocation = "META-INF/wsdl/www.academico.espol.edu.ec/services/directorioEspol.asmx.wsdl")
    private DirectorioEspol service;
    @PersistenceContext(unitName = "InventarioLAB-ejbPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    @Override
    public boolean autenticar(Usuario usuario) {
        return autenticacion(usuario.getUsuario(), usuario.getClave());
    }

    private boolean autenticacion(java.lang.String varUser, java.lang.String varContrasenia) {
        inventarioLAB.webServicesClient.ESPOL.DirectorioEspolSoap port = service.getDirectorioEspolSoap12();
        return port.autenticacion(varUser, varContrasenia);
    }

    @Override
    public boolean existeSuperUsuario() {
        boolean resultado = false;
        Collection<Usuario> lista = null;
        Query query = this.em.createNamedQuery("Usuario.findByRolEstado");
        query.setParameter("rol", "SUPER");
        query.setParameter("estado", "A");
        try {
            lista = query.getResultList();
            
            if (lista != null)
                if (!lista.isEmpty())                
                    resultado = true;
        } catch (Exception e) {
            resultado = false;
        }
        
        return resultado;
    }
    
}
