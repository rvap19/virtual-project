/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle;

import middle.event.RisikoEvent;
import middle.messages.RisikoMessage;

/**
 *
 * @author root
 */
public interface RisikoEventGenerator {
    public RisikoEvent generateEvent(RisikoMessage message);
}
