/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jxta.communication;

import jxta.communication.messages.AckMessage;
import jxta.communication.messages.ApplianceMessage;
import jxta.communication.messages.AttackMessage;
import jxta.communication.messages.ChangeCardMessage;
import jxta.communication.messages.ChatMessage;
import jxta.communication.messages.ElectionMessage;
import jxta.communication.messages.ElectionRequestMessage;
import jxta.communication.messages.InitMessage;
import jxta.communication.messages.MovementMessage;
import jxta.communication.messages.PassMessage;
import jxta.communication.messages.PingMessage;
import jxta.communication.messages.PongMessage;
import jxta.communication.messages.RecoveryMessage;
import jxta.communication.messages.RetrasmissionRequest;
import jxta.communication.messages.StatusPeerMessage;
import jxta.communication.messages.VirtualRisikoMessage;
import jxta.communication.messages.WelcomeMessage;
import middle.MessageTypes;
import middle.messages.RisikoMessage;
import net.jxta.endpoint.Message;

/**
 *
 * @author root
 */
public class MessageUtil {
    public static  RisikoMessage messageCreator(Message m){
        
        RisikoMessage msg=null;
        
        String type=m.getMessageElement(VirtualRisikoMessage.namespace,VirtualRisikoMessage.TYPE).toString();
        
        if(type.equals(MessageTypes.ACK)){
            msg=new AckMessage(m);
        }else if(type.equals(MessageTypes.APPLIANCE)){
            msg=new ApplianceMessage(m);
        }else if(type.equals(MessageTypes.ATTACK)){
            msg=new AttackMessage(m);
        }else if(type.equals(MessageTypes.CHANGE_CARDS)){
            msg=new ChangeCardMessage(m);
        }else if(type.equals(MessageTypes.CHAT)){
            msg=new ChatMessage(m);
        }else if(type.equals(MessageTypes.ELECTION)){
            msg=new ElectionMessage(m);
        }else if(type.equals(MessageTypes.ELECTION_REQUEST)){
            msg=new ElectionRequestMessage(m);
        }else if(type.equals(MessageTypes.INIT)){
            msg=new InitMessage(m);
        }else if(type.equals(MessageTypes.MOVEMENT)){
            msg=new MovementMessage(m);
        }else if(type.equals(MessageTypes.PASS)){
            msg=new PassMessage(m);
        }else if(type.equals(MessageTypes.PING)){
            msg=new PingMessage(m);
        }else if(type.equals(MessageTypes.PONG)){
            msg=new PongMessage(m);
        }else if(type.equals(MessageTypes.RECOVERY)){
            msg=new RecoveryMessage(m);
        }else if(type.equals(MessageTypes.RETRASMISSION_REQUEST)){
            msg=new RetrasmissionRequest(m);
        }else if(type.equals(MessageTypes.STATUS_PEER)){
            msg=new StatusPeerMessage(m);
        }else if(type.equals(MessageTypes.WELCOME)){
            msg=new WelcomeMessage(m);
        }
        
        return msg;
    }
    
}
