/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.event;

import middle.EventTypes;
import middle.messages.ElectionMessage;

/**
 *
 * @author root
 */
public class ElectionEvent extends RisikoEvent{
    public ElectionEvent(ElectionMessage m){
        super(m);
        super.type=EventTypes.ELECTION;
    }
}
