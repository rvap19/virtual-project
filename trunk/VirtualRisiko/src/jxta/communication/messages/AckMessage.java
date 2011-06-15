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
public class AckMessage extends VirtualRisikoMessage{


        public static final String ACK_FOR_MESSAGE_ID="ack_message";


        private int ack_message_id;

    public AckMessage(int ack_message_id){
        super();
        this.ack_message_id=ack_message_id;
        StringMessageElement mE=new StringMessageElement(TYPE, ACK, null);
        addMessageElement(namespace, mE);

        StringMessageElement mElement = new StringMessageElement(ACK_FOR_MESSAGE_ID,Integer.toString(ack_message_id), null);
        addMessageElement(namespace, mElement);
    }

    public AckMessage(Message message){
        super(message);
        this.ack_message_id=Integer.parseInt(getMessageElement(ACK_FOR_MESSAGE_ID).toString());

    }

    public int getAck_message_id() {
        return ack_message_id;
    }

    

}
