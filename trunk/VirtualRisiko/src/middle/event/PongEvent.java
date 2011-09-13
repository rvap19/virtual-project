/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.event;

import middle.EventTypes;
import middle.messages.PongMessage;

/**
 *
 * @author root
 */
public class PongEvent extends RisikoEvent{
    public PongEvent(PongMessage s){
        super(s);
        super.type=EventTypes.PONG;
    }
}
