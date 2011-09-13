/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.event;

import middle.EventTypes;
import middle.messages.RetrasmissionRequestMessage;

/**
 *
 * @author root
 */
public class RetrasmissionRequestEvent extends RisikoEvent {
    public RetrasmissionRequestEvent(RetrasmissionRequestMessage s){
        super(s);
        super.type=EventTypes.RETRASMISSION_REQUEST;
    }
}
