/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.event;

import middle.EventTypes;
import middle.messages.WelcomeMessage;


/**
 *
 * @author root
 */
public class RecoveryRequestEvent extends RisikoEvent{
    public RecoveryRequestEvent(WelcomeMessage source){
       super(source);
       super.type=EventTypes.RECOVERY_REQUEST;
    }
    
}
