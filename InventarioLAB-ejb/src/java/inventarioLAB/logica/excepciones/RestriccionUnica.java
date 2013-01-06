/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.logica.excepciones;

/**
 *
 * @author Edward J. Holguín Holguín
 */
public class RestriccionUnica extends Exception {

    /**
     * Creates a new instance of
     * <code>RestriccionUnica</code> without detail message.
     */
    public RestriccionUnica() {
    }

    /**
     * Constructs an instance of
     * <code>RestriccionUnica</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public RestriccionUnica(String msg) {
        super(msg);
    }
}
