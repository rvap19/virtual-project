/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.listener;

import middle.event.ChatEvent;

/**
 *
 * @author root
 */
public interface ChatEventListener extends RisikoEventListener{
    public void notify(ChatEvent c);
    
}
