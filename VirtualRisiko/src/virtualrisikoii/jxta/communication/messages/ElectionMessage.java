/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualrisikoii.jxta.communication.messages;

import middle.MessageTypes;
import net.jxta.endpoint.Message;
import net.jxta.endpoint.StringMessageElement;

/**
 *
 * @author root
 */
public class ElectionMessage extends VirtualRisikoMessage implements middle.messages.ElectionMessage{


    public static final String PEER_ID="peerID";
    public static final String CURRENT_TURN="current_turn";


    private String peerID;
    private int currentTurn;
    

    public ElectionMessage(String peerID,int currentTurn){
        super();
        this.peerID=peerID;
        this.currentTurn=currentTurn;
        setType(MessageTypes.ELECTION);
        StringMessageElement mE=new StringMessageElement(TYPE, MessageTypes.ELECTION, null);
        addMessageElement(namespace, mE);

        StringMessageElement mElement = new StringMessageElement(PEER_ID,peerID, null);
        addMessageElement(namespace, mElement);

        mElement = new StringMessageElement(CURRENT_TURN,Integer.toString(currentTurn), null);
        addMessageElement(namespace, mElement);
       
    }

    public ElectionMessage(Message message){
        super(message);
         peerID=message.getMessageElement(namespace, PEER_ID).toString();
         this.currentTurn=Integer.parseInt(message.getMessageElement(namespace, CURRENT_TURN).toString());
         

    }

    public String getPeerID() {
        return peerID;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }
    

    

    



}
