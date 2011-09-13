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
public class WelcomeEvent extends RisikoEvent{
    public WelcomeEvent(WelcomeMessage message){
        super(message);
        super.type=EventTypes.WELCOME;
    }
}
