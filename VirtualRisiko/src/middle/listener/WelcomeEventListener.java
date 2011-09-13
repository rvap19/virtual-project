/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.listener;

import middle.event.WelcomeEvent;

/**
 *
 * @author root
 */
public interface WelcomeEventListener extends RisikoEventListener{
    public void notify(WelcomeEvent c);
    
}
