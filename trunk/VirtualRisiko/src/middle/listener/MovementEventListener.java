/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.listener;

import middle.event.MovementEvent;

/**
 *
 * @author root
 */
public interface MovementEventListener extends RisikoEventListener{
    public void notify(MovementEvent c);
    
}
