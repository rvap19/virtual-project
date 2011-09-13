/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jxta.communication.messages;

import middle.MessageTypes;
import net.jxta.endpoint.Message;
import net.jxta.endpoint.StringMessageElement;

/**
 *
 * @author root
 */
public class PingMessage extends VirtualRisikoMessage implements middle.messages.PingMessage{



    

    public PingMessage(){
        super();
        setType(MessageTypes.PING);
        StringMessageElement mE=new StringMessageElement(TYPE, MessageTypes.PING, null);
        addMessageElement(namespace, mE);

        
    }

    public PingMessage(Message message){
        super(message);

    }

   



}
