/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.event;

import middle.EventTypes;
import middle.messages.AckMessage;

/**
 *
 * @author root
 */
public class AckEvent extends RisikoEvent{
    public AckEvent(AckMessage m){
        super(m);
        super.type=EventTypes.ACK;
    }
}
