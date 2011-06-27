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
public class ElectionRequestMessage extends VirtualRisikoMessage{





    public ElectionRequestMessage(){
        super();


        StringMessageElement mE=new StringMessageElement(TYPE, REQUEST_ELECTION, null);
        addMessageElement(namespace, mE);



    }

    public ElectionRequestMessage(Message message){
        super(message);



    }

  







}
