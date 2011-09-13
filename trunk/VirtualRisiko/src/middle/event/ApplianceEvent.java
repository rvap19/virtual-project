/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.event;

import middle.EventTypes;
import middle.messages.ApplianceMessage;

/**
 *
 * @author root
 */
public class ApplianceEvent extends RisikoEvent {
    public ApplianceEvent(ApplianceMessage m){
        super(m);
        super.type=EventTypes.APPLIANCE;
    }
}
