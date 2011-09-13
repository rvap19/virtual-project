/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.event;

import middle.EventTypes;
import middle.messages.MovementMessage;

/**
 *
 * @author root
 */
public class MovementEvent extends RisikoEvent{
    public MovementEvent(MovementMessage m){
        super(m);
        super.type=EventTypes.MOVEMENT;
    }
}
