/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.listener;

import middle.event.PingEvent;

/**
 *
 * @author root
 */
public interface PingEventListener extends RisikoEventListener{
    public void notify(PingEvent c);
    
}
