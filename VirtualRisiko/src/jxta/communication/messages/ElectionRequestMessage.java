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
public class ElectionRequestMessage extends VirtualRisikoMessage implements middle.messages.ElectionRequestMessage{





    public ElectionRequestMessage(){
        super();

        setType(MessageTypes.ELECTION_REQUEST);
        StringMessageElement mE=new StringMessageElement(TYPE, MessageTypes.ELECTION_REQUEST, null);
        addMessageElement(namespace, mE);



    }

    public ElectionRequestMessage(Message message){
        super(message);



    }

  







}
