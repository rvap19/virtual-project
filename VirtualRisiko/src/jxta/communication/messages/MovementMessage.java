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
public class MovementMessage extends VirtualRisikoMessage implements middle.messages.MovementMessage{

    public static final String TROOPS_NUMBER="troops";
    public static final String FROM_REGION_ID="from_region";
    public static final String TO_REGION_ID="to_region";


    private int troopNumber;
    private int from;
    private int to;
    public MovementMessage(int troops_number,int from,int to){
        super();
        this.troopNumber=troops_number;
        this.from=from;
        this.to=to;
        
        StringMessageElement mE=new StringMessageElement(TYPE, MessageTypes.MOVEMENT, null);
        addMessageElement(namespace, mE);

        StringMessageElement mElement = new StringMessageElement(TROOPS_NUMBER,Integer.toString(troops_number), null);
        addMessageElement(namespace, mElement);
        mElement = new StringMessageElement(FROM_REGION_ID,Integer.toString(from), null);
        addMessageElement(namespace, mElement);
        mElement = new StringMessageElement(TO_REGION_ID,Integer.toString(to), null);
        addMessageElement(namespace, mElement);

    }

    public MovementMessage(Message message){
        super(message);
        this.troopNumber=Integer.parseInt(message.getMessageElement(namespace, TROOPS_NUMBER).toString());
         from = Integer.parseInt(message.getMessageElement(namespace, FROM_REGION_ID).toString());
         to= Integer.parseInt(message.getMessageElement(namespace, TO_REGION_ID).toString());
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public int getTroopNumber() {
        return troopNumber;
    }

    


}
