/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle;

import middle.listener.ApplianceEventListener;
import middle.listener.AttackEventListener;
import middle.listener.ChangeCardEventListener;
import middle.listener.MovementEventListener;
import middle.listener.PassEventListener;

/**
 *
 * @author root
 */
public abstract class AbstractGameController implements ApplianceEventListener,AttackEventListener,MovementEventListener,ChangeCardEventListener,PassEventListener{
    
}
