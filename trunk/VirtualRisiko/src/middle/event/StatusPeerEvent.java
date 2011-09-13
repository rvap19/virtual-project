/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.event;

import middle.EventTypes;
import middle.messages.StatusPeerMessage;

/**
 *
 * @author root
 */
public class StatusPeerEvent extends RisikoEvent{
    public StatusPeerEvent(StatusPeerMessage source){
        super(source);
        super.type=EventTypes.STATUS_PEER;
    }
}
