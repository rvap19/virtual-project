/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.event;

import middle.EventTypes;
import middle.messages.RecoveryMessage;

/**
 *
 * @author root
 */
public class RecoveryEvent extends RisikoEvent{
    public RecoveryEvent(RecoveryMessage source){
       super(source);
       super.type=EventTypes.RECOVERY;
    }
}
