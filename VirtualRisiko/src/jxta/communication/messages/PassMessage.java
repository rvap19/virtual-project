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
public class PassMessage extends VirtualRisikoMessage{

        public static final String SUCC_TURN="successive_turn";
        private int turno_successivo;

    public PassMessage(int turno_successivo){
        super();
        StringMessageElement mE=new StringMessageElement(type, PASSES, null);
        addMessageElement(namespace, mE);

        StringMessageElement mElement = new StringMessageElement(SUCC_TURN,Integer.toString(turno_successivo), null);
        addMessageElement(namespace, mElement);
    }

    public PassMessage(Message message){
        super(message);
        this.turno_successivo=Integer.parseInt(message.getMessageElement(namespace, SUCC_TURN).toString());
    }

    public int getTurno_successivo() {
        return turno_successivo;
    }


}
