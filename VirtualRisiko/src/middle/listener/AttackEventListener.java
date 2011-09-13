/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.listener;

import middle.event.AttackEvent;

/**
 *
 * @author root
 */
public interface AttackEventListener extends RisikoEventListener{
    public void notify(AttackEvent c);
}
