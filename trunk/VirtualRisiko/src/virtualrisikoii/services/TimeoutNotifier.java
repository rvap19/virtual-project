/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualrisikoii.services;

/**
 *
 * @author root
 */
public interface TimeoutNotifier {
    public void timeoutNotify();
    public void remaingTimeNotify(int remaing);
}
