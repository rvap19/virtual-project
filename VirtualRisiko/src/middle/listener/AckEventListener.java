/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.listener;

import middle.event.AckEvent;

/**
 *
 * @author root
 */
public interface AckEventListener extends RisikoEventListener{
    public void notify(AckEvent s);
}
