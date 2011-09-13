/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.listener;

import middle.event.ElectionRequestEvent;

/**
 *
 * @author root
 */
public interface ElectionRequestEventListener extends RisikoEventListener{
    public void notify(ElectionRequestEvent c);
    
}
