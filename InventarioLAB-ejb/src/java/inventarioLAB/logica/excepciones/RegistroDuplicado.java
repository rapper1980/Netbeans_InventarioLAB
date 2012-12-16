/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.logica.excepciones;

/**
 *
 * @author Edward J. Holguín Holguín
 */
public class RegistroDuplicado extends Exception {

    /**
     * Creates a new instance of
     * <code>RegistroDuplicado</code> without detail message.
     */
    public RegistroDuplicado() {
    }

    /**
     * Constructs an instance of
     * <code>RegistroDuplicado</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public RegistroDuplicado(String msg) {
        super(msg);
    }
}
