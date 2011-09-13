/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.event;

import middle.EventTypes;
import middle.messages.ChangeCardMessage;

/**
 *
 * @author root
 */
public class ChangeCardEvent extends RisikoEvent{
    public ChangeCardEvent(ChangeCardMessage m){
        super(m);
        super.type=EventTypes.CHANGE_CARDS;
    }
}
