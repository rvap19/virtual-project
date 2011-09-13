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
public class ChangeCardMessage extends VirtualRisikoMessage implements middle.messages.ChangeCardMessage{


        public static final String CARD1_ID="card1_id";
        public static final String CARD2_ID="card2_id";
        public static final String CARD3_ID="card3_id";


    private int card1;
    private int card2;
    private int card3;
    public ChangeCardMessage(int card1,int card2,int card3){

        StringMessageElement mE=new StringMessageElement(TYPE, MessageTypes.CHANGE_CARDS, null);
        addMessageElement(namespace, mE);

        StringMessageElement mElement = new StringMessageElement(CARD1_ID,Integer.toString(card1), null);
        addMessageElement(namespace, mElement);
        mElement = new StringMessageElement(CARD2_ID,Integer.toString(card2), null);
        addMessageElement(namespace, mElement);
        mElement = new StringMessageElement(CARD3_ID,Integer.toString(card3), null);
        addMessageElement(namespace, mElement);
    }

    public ChangeCardMessage(Message message){
        super(message);
         card1=new Integer(message.getMessageElement(namespace, CARD1_ID).toString()).intValue();
         card2=new Integer(message.getMessageElement(namespace, CARD2_ID).toString()).intValue();
         card3=new Integer(message.getMessageElement(namespace, CARD3_ID).toString()).intValue();
    }

    public int getCard1() {
        return card1;
    }

    public int getCard2() {
        return card2;
    }

    public int getCard3() {
        return card3;
    }

    

}
