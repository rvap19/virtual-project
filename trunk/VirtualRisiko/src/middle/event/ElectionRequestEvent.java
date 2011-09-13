/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.event;

import middle.EventTypes;
import middle.messages.ElectionRequestMessage;

/**
 *
 * @author root
 */
public class ElectionRequestEvent extends RisikoEvent{
    public ElectionRequestEvent(ElectionRequestMessage m){
        super(m);
        super.type=EventTypes.ELECTION_REQUEST;
    }
}
