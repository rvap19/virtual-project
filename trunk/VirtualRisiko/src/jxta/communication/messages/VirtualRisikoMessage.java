/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jxta.communication.messages;

import middle.messages.RisikoMessage;
import net.jxta.endpoint.Message;
import net.jxta.endpoint.StringMessageElement;

/**
 *
 * @author root
 */
public class VirtualRisikoMessage extends Message implements RisikoMessage{

    public static final String namespace="VRNameSpace";

    public static final String TYPE="type";
    public static final  String GAMER="gamer";
    public static final String ID_MSG="IDMSG";

    
    

    protected String playerName;
    protected int msgID;
    protected String type;

    public VirtualRisikoMessage(){
        super();
        
    }

    public VirtualRisikoMessage(Message message){
        playerName=message.getMessageElement(namespace, GAMER).toString();
        msgID=Integer.parseInt(message.getMessageElement(namespace,ID_MSG).toString());
        type=message.getMessageElement(namespace,TYPE).toString();
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

    public void setPlayerName(String name){
        this.playerName=name;

        StringMessageElement mElement = new StringMessageElement(GAMER,name, null);
        addMessageElement(namespace, mElement);
    }

    public void setMSG_ID(int id){
        this.msgID=id;
        StringMessageElement mElement = new StringMessageElement(ID_MSG,Integer.toString(id), null);
        addMessageElement(namespace, mElement);
    }

    protected void setType(String type){
        this.type=type;
    }

}
