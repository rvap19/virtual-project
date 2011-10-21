/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package virtualrisikoii.jxta.communication;

import virtualrisikoii.jxta.communication.messages.AckMessage;
import virtualrisikoii.jxta.communication.messages.ApplianceMessage;
import virtualrisikoii.jxta.communication.messages.AttackMessage;
import virtualrisikoii.jxta.communication.messages.ChangeCardMessage;
import virtualrisikoii.jxta.communication.messages.ChatMessage;
import virtualrisikoii.jxta.communication.messages.ElectionMessage;
import virtualrisikoii.jxta.communication.messages.ElectionRequestMessage;
import virtualrisikoii.jxta.communication.messages.InitMessage;
import virtualrisikoii.jxta.communication.messages.MovementMessage;
import virtualrisikoii.jxta.communication.messages.PassMessage;
import virtualrisikoii.jxta.communication.messages.PingMessage;
import virtualrisikoii.jxta.communication.messages.PongMessage;
import virtualrisikoii.jxta.communication.messages.RecoveryMessage;
import virtualrisikoii.jxta.communication.messages.RetrasmissionRequest;
import virtualrisikoii.jxta.communication.messages.StatusPeerMessage;
import virtualrisikoii.jxta.communication.messages.VirtualRisikoMessage;
import virtualrisikoii.jxta.communication.messages.WelcomeMessage;
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
