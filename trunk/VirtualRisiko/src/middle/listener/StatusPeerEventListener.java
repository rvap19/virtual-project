/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.listener;

import middle.event.StatusPeerEvent;

/**
 *
 * @author root
 */
public interface StatusPeerEventListener extends RisikoEventListener{
    public void notify(StatusPeerEvent c);
    
}
