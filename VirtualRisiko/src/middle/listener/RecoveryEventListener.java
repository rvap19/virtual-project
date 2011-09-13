/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.listener;

import middle.event.RecoveryEvent;

/**
 *
 * @author root
 */
public interface RecoveryEventListener extends RisikoEventListener{
    public void notify(RecoveryEvent c);
    
}
