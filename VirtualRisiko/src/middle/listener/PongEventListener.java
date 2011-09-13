/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.listener;

import middle.event.PongEvent;

/**
 *
 * @author root
 */
public interface PongEventListener extends RisikoEventListener{
    public void notify(PongEvent c);
    
}
