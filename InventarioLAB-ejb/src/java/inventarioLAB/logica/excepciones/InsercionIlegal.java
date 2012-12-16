/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.logica.excepciones;

/**
 *
 * @author Edward J. Holguín Holguín
 */
public class InsercionIlegal extends Exception {

    /**
     * Creates a new instance of
     * <code>InsercionIlegal</code> without detail message.
     */
    public InsercionIlegal() {
    }

    /**
     * Constructs an instance of
     * <code>InsercionIlegal</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public InsercionIlegal(String msg) {
        super(msg);
    }
}
