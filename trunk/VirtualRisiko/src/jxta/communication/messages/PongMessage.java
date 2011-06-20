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
public class PongMessage extends VirtualRisikoMessage{


        public static final String PING_PEER_ID="pong_peer_id";


        private String peer_id;

    public PongMessage(String peerId){
        super();
        this.peer_id=peerId;
        StringMessageElement mE=new StringMessageElement(TYPE, PONG, null);
        addMessageElement(namespace, mE);

        StringMessageElement mElement = new StringMessageElement(PING_PEER_ID,peerId, null);
        addMessageElement(namespace, mElement);
    }

    public PongMessage(Message message){
        super(message);
        this.peer_id=(getMessageElement(namespace,PING_PEER_ID).toString());

    }

    public String getPeerID() {
        return peer_id;
    }



}
