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
public class RetrasmissionRequest extends VirtualRisikoMessage{

            public static final String RETRASMIT_MESG_ID="rts_msg_ID";


        private int messageID;

    public RetrasmissionRequest(int messageID){
        super();
        this.messageID=messageID;
        StringMessageElement mE=new StringMessageElement(TYPE, RETRASMIT_REQUEST, null);
        addMessageElement(namespace, mE);

        StringMessageElement mElement = new StringMessageElement(RETRASMIT_MESG_ID,Integer.toString(messageID), null);
        addMessageElement(namespace, mElement);
    }

    public RetrasmissionRequest(Message message){
        super(message);
        this.messageID=Integer.parseInt(getMessageElement(namespace,RETRASMIT_MESG_ID).toString());

    }

    public int getMessageID() {
        return messageID;
    }

    

}
