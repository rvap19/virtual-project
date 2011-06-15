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
public class StatusPeerMessage extends VirtualRisikoMessage{
    public static final String PEER_ID="peer_id";
    public static final String PEER_STATUS="status";

    private int id;
    private boolean online;

    public StatusPeerMessage(int id,boolean online){
        super();
        this.id=id;
        this.online=online;
        StringMessageElement mE=new StringMessageElement(type, STATUS, null);
        addMessageElement(namespace, mE);

        StringMessageElement mElement = new StringMessageElement(PEER_ID,Integer.toString(id), null);
        addMessageElement(namespace, mElement);
        mElement = new StringMessageElement(PEER_STATUS,Boolean.toString(online), null);
        addMessageElement(namespace, mElement);


    }

    public StatusPeerMessage(Message message){
        super(message);
         id=Integer.parseInt(message.getMessageElement(namespace, PEER_ID).toString());
         online=Boolean.parseBoolean(message.getMessageElement(namespace, PEER_STATUS).toString());
    }

    public int getId() {
        return id;
    }

    public boolean isOnline() {
        return online;
    }

    

}
