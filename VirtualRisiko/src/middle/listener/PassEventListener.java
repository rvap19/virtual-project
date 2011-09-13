/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.listener;

import middle.event.PassEvent;

/**
 *
 * @author root
 */
public interface PassEventListener extends RisikoEventListener{
    public void notify(PassEvent c);
    
}
