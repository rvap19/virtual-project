/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.event;

import middle.EventTypes;
import middle.messages.ChatMessage;

/**
 *
 * @author root
 */
public class ChatEvent extends RisikoEvent{
    public static String TO_ALL="TO_ALL_PLAYERS";
    public ChatEvent(ChatMessage m){
        super(m);
        super.type=EventTypes.CHAT;
    }
}
