/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.listener;

import middle.event.RecoveryRequestEvent;

/**
 *
 * @author root
 */
public interface RecoveryRequestEventListener extends RisikoEventListener{
    public void notify(RecoveryRequestEvent c);
    
}
