/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jxta.communication.messages;

import middle.MessageTypes;
import middle.messages.RisikoMessage;
import net.jxta.endpoint.Message;
import net.jxta.endpoint.StringMessageElement;

/**
 *
 * @author root
 */
public class PassMessage extends VirtualRisikoMessage implements middle.messages.PassMessage{

        public static final String SUCC_TURN="successive_turn";
        private int turno_successivo;

    public PassMessage(int turno_successivo){
        super();
        setType(MessageTypes.PASS);
        StringMessageElement mE=new StringMessageElement(TYPE, MessageTypes.PASS, null);
        addMessageElement(namespace, mE);

        StringMessageElement mElement = new StringMessageElement(SUCC_TURN,Integer.toString(turno_successivo), null);
        addMessageElement(namespace, mElement);
        this.turno_successivo=turno_successivo;
    }

    public PassMessage(Message message){
        super(message);
        this.turno_successivo=Integer.parseInt(message.getMessageElement(namespace, SUCC_TURN).toString());
    }

    public int getTurno_successivo() {
        return turno_successivo;
    }


}
