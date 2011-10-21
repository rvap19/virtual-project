/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualrisikoii.jxta.communication.messages; 

import middle.MessageTypes;
import net.jxta.endpoint.Message;
import net.jxta.endpoint.StringMessageElement;

/**
 *
 * @author root
 */
public class WelcomeMessage extends VirtualRisikoMessage implements middle.messages.WelcomeMessage{
    
        public static final String PEER_NAME="peer_name";

        private String welcomeName;

    public WelcomeMessage(String welcomePlayerName){
        super();
        setType(MessageTypes.WELCOME);
        StringMessageElement mE=new StringMessageElement(TYPE, MessageTypes.WELCOME, null);
        addMessageElement(namespace, mE);

        StringMessageElement mElement = new StringMessageElement(PEER_NAME,welcomePlayerName, null);
        addMessageElement(namespace, mElement);
        this.welcomeName=welcomeName;
        
    }

    public WelcomeMessage(Message message){
        super(message);
        welcomeName=message.getMessageElement(namespace, PEER_NAME).toString();
    }

    public String getWelcomeName() {
        return welcomeName;
    }
    


}
