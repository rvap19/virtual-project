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
public class ApplianceMessage extends VirtualRisikoMessage implements middle.messages.ApplianceMessage{
    

    public static final String TROOPS_NUMBER="troops";
    public static final String REGION_IDS="region_id";


    private int troops_number;
    private int region;
    
    public ApplianceMessage(int troops_number,int region){
        super();
        this.troops_number=troops_number;
        this.region=region;
        StringMessageElement mE=new StringMessageElement(TYPE, MessageTypes.APPLIANCE, null);
        addMessageElement(namespace, mE);

        StringMessageElement mElement = new StringMessageElement(TROOPS_NUMBER,Integer.toString(troops_number), null);
        addMessageElement(namespace, mElement);
        mElement = new StringMessageElement(REGION_IDS,Integer.toString(region), null);
        addMessageElement(namespace, mElement);
    }

    public ApplianceMessage(Message message){
        super(message);
         troops_number=Integer.parseInt(message.getMessageElement(namespace, TROOPS_NUMBER).toString());
         region=Integer.parseInt(message.getMessageElement(namespace, REGION_IDS).toString());

    }

    public int getRegion() {
        return region;
    }

    public int getTroops_number() {
        return troops_number;
    }

    

}
