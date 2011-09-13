/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.event;

import middle.EventTypes;
import middle.messages.InitMessage;

/**
 *
 * @author root
 */
public class InitEvent extends RisikoEvent{
    public InitEvent(InitMessage m){
        super(m);
        super.type=EventTypes.INIT;
    }
}
