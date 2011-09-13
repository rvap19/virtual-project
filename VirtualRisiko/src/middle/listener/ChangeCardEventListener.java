/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.listener;

import middle.event.ChangeCardEvent;

/**
 *
 * @author root
 */
public interface ChangeCardEventListener extends RisikoEventListener{
    public void notify(ChangeCardEvent e);
    
}
