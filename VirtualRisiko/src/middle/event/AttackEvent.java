/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.event;

import middle.EventTypes;
import middle.messages.AttackMessage;

/**
 *
 * @author root
 */
public class AttackEvent extends RisikoEvent{
    public AttackEvent(AttackMessage m){
        super(m);
        super.type=EventTypes.ATTACK;
    }
}
