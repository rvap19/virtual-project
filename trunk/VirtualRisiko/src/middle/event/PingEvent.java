/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.event;

import middle.EventTypes;
import middle.messages.PingMessage;

/**
 *
 * @author root
 */
public class PingEvent extends RisikoEvent {
    public PingEvent(PingMessage s){
        super(s);
        super.type=EventTypes.PING;
    }
}
