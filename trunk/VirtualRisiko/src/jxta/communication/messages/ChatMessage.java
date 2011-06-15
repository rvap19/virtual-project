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
public class ChatMessage extends VirtualRisikoMessage{
    
        public static final String MESSAGE="message";
        public static final String DESTINATION="destination";
        public static final String TO_ALL="tutti";
        public static final String SENDER="sender";
    

        private String to;
        private String from;
        private String messageString;

    public ChatMessage(String to,String from,String messageString){
        super();

        this.to=to;
        this.from=from;
        this.messageString=messageString;

        StringMessageElement mE=new StringMessageElement(TYPE, CHAT, null);
        addMessageElement(namespace, mE);

         mE=new StringMessageElement(DESTINATION , to, null);
        addMessageElement(namespace, mE);
        mE=new StringMessageElement(SENDER , from, null);
        addMessageElement(namespace, mE);

         mE=new StringMessageElement(MESSAGE , messageString, null);
        addMessageElement(namespace, mE);
    }

    public ChatMessage(Message message){
        super(message);
         to=message.getMessageElement(namespace, DESTINATION).toString();
         messageString=message.getMessageElement(namespace, MESSAGE).toString();
         from=message.getMessageElement(namespace, SENDER).toString();
    }

    public String getFrom() {
        return from;
    }

    public String getMessageString() {
        return messageString;
    }

    public String getTo() {
        return to;
    }

    


}
