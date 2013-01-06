/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.logica;

import inventarioLAB.entidades.Persona;
import inventarioLAB.logica.excepciones.InsercionIlegal;
import inventarioLAB.logica.excepciones.RestriccionUnica;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Edward J. Holguín Holguín
 */
@Local
public interface PersonaFacadeLocal {

    void create(Persona persona);

    void edit(Persona persona);

    void remove(Persona persona);

    Persona find(Object id);

    List<Persona> findAll();

    List<Persona> findRange(int[] range);

    int count();

    void crear(Persona persona) throws RestriccionUnica, InsercionIlegal ;

    void editar(inventarioLAB.entidades.Persona persona) throws RestriccionUnica, InsercionIlegal;

    void registrarADMIN(String clave) throws RestriccionUnica, InsercionIlegal;
    
}
