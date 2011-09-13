/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.listener;

import middle.event.ApplianceEvent;

/**
 *
 * @author root
 */
public interface ApplianceEventListener extends RisikoEventListener{
    public void notify(ApplianceEvent s);
}
