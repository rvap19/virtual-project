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
public class PingMessage extends VirtualRisikoMessage{



    

    public PingMessage(){
        super();
        StringMessageElement mE=new StringMessageElement(TYPE, PING, null);
        addMessageElement(namespace, mE);

        
    }

    public PingMessage(Message message){
        super(message);

    }

   



}
