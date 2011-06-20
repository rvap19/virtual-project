/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jxta.communication.messages;

import net.jxta.endpoint.Message;

/**
 *
 * @author root
 */
public class VirtualRisikoMessage extends Message{

    public static final String namespace="VRNameSpace";

    public static final String TYPE="type";
    public static final  String GAMER="gamer";
    public static final String ID_MSG="IDMSG";

    public static final String INIT="init";
    public static final String APPLIANCE="appliance";
    public static final String ATTACK="attack";
    public static final String MOVEMENT="movement";
    public static final String CHANGE_CARD="change_cards";
    public static final String PASSES="passes";
    public static final String ACK="ack";
    public static final String CHAT="chat";
    public static final String WELCOME="welcome";
    public static final String RECOVERY="recovery";
    public static final String STATUS="status";
    public static final String PING="ping";
    public static final String PONG="pong";
    

    protected String playerName;
    protected int msgID;
    protected String type;

    public VirtualRisikoMessage(){
        super();
    }

    public VirtualRisikoMessage(Message message){
        playerName=message.getMessageElement(namespace, GAMER).toString();
        msgID=Integer.parseInt(message.getMessageElement(namespace,ID_MSG).toString());

    }

    public String getType(){
        return type;
    }

    public String playerName(){
        return playerName;
    }

    public int getMSG_ID(){
       return msgID;
    }


}
