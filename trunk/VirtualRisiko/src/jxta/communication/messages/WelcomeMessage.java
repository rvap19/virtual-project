/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jxta.communication.messages;

import net.jxta.endpoint.Message;
import net.jxta.endpoint.StringMessageElement;

/**
 *
 * @author root
 */
public class WelcomeMessage extends VirtualRisikoMessage{
    
        public static final String PEER_NAME="peer_name";

        private String welcomeName;

    public WelcomeMessage(String welcomePlayerName){
        super();
        StringMessageElement mE=new StringMessageElement(TYPE, WELCOME, null);
        addMessageElement(namespace, mE);

        StringMessageElement mElement = new StringMessageElement(PEER_NAME,welcomePlayerName, null);
        addMessageElement(namespace, mElement);
    }

    public WelcomeMessage(Message message){
        super(message);
        welcomeName=message.getMessageElement(namespace, PEER_NAME).toString();
    }

    public String getWelcomeName() {
        return welcomeName;
    }
    


}
