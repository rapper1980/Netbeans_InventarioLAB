/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.logica;

import inventarioLAB.entidades.Usuario;
import inventarioLAB.logica.beansAdicionales.InformacionEstudianteESPOL;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Edward J. Holguín Holguín
 */
@Local
public interface UsuarioFacadeLocal {

    void create(Usuario usuario);

    void edit(Usuario usuario);

    void remove(Usuario usuario);

    Usuario find(Object id);

    List<Usuario> findAll();

    List<Usuario> findRange(int[] range);

    int count();
    
    void inactiva(Usuario usuario);
    
    Usuario obtenerUsuario(String usuario, String estado);
    
    List<Usuario> obtenerUsuarios();
    
    List<Usuario> obtenerUsuarios(String estado);
    
    List<Usuario> obtenerUsuarios(String rol, String estado);
    
    Usuario autenticar(String usuario, String Clave);
    
    boolean existeSuperUsuario();

    InformacionEstudianteESPOL obtenerInformacionAcademicaEstudianteESPOL(String identificacion, String matricula);

    
}
