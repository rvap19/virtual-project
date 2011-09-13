/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.listener;

import middle.event.ElectionEvent;

/**
 *
 * @author root
 */
public interface ElectionEventListener extends RisikoEventListener{
    public void notify(ElectionEvent c);
    
}
