/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.listener;

import middle.event.InitEvent;

/**
 *
 * @author root
 */
public interface InitEventListener extends RisikoEventListener{
    public void notify(InitEvent c);
    
}
