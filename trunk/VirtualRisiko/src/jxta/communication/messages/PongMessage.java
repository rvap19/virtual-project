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
public class PongMessage extends VirtualRisikoMessage implements middle.messages.PongMessage{


        public static final String PING_PEER_ID="pong_peer_id";


        private String peer_id;

    public PongMessage(String peerId){
        super();
        setType(MessageTypes.PONG);
        this.peer_id=peerId;
        StringMessageElement mE=new StringMessageElement(TYPE, MessageTypes.PONG, null);
        addMessageElement(namespace, mE);

        StringMessageElement mElement = new StringMessageElement(PING_PEER_ID,peerId, null);
        addMessageElement(namespace, mElement);
    }

    public PongMessage(Message message){
        super(message);
        this.peer_id=(message.getMessageElement(namespace,PING_PEER_ID).toString());

    }

    public String getPeerID() {
        return peer_id;
    }



}
