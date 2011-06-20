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


        public static final String PEER_ID="pong_peer_id";


        private int peer_id;

    public PongMessage(int peerId){
        super();
        this.peer_id=peerId;
        StringMessageElement mE=new StringMessageElement(TYPE, PONG, null);
        addMessageElement(namespace, mE);

        StringMessageElement mElement = new StringMessageElement(PEER_ID,Integer.toString(peerId), null);
        addMessageElement(namespace, mElement);
    }

    public PongMessage(Message message){
        super(message);
        this.peer_id=Integer.parseInt(getMessageElement(PEER_ID).toString());

    }

    public int getPeerID() {
        return peer_id;
    }



}
