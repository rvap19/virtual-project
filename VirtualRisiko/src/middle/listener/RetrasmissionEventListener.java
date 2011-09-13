/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.listener;

import middle.event.RetrasmissionRequestEvent;

/**
 *
 * @author root
 */
public interface RetrasmissionEventListener extends RisikoEventListener{
    public void notify(RetrasmissionRequestEvent c);
    
}
