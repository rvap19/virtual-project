/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.event;

import middle.EventTypes;
import middle.messages.PassMessage;

/**
 *
 * @author root
 */
public class PassEvent extends RisikoEvent{
    public PassEvent(PassMessage s){
        super(s);
        super.type=EventTypes.PASS;
    }
}
