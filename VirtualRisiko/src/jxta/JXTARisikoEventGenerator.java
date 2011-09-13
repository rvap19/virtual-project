/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jxta;

import jxta.communication.messages.VirtualRisikoMessage;
import middle.MessageTypes;
import middle.RisikoEventGenerator;
import middle.event.AckEvent;
import middle.event.ApplianceEvent;
import middle.event.AttackEvent;
import middle.event.ChangeCardEvent;
import middle.event.ChatEvent;
import middle.event.ElectionEvent;
import middle.event.ElectionRequestEvent;
import middle.event.InitEvent;
import middle.event.MovementEvent;
import middle.event.PassEvent;
import middle.event.PingEvent;
import middle.event.PongEvent;
import middle.event.RecoveryEvent;
import middle.event.RetrasmissionRequestEvent;
import middle.event.RisikoEvent;
import middle.event.StatusPeerEvent;
import middle.event.WelcomeEvent;
import middle.messages.AckMessage;
import middle.messages.ApplianceMessage;
import middle.messages.AttackMessage;
import middle.messages.ChangeCardMessage;
import middle.messages.ChatMessage;
import middle.messages.ElectionMessage;
import middle.messages.ElectionRequestMessage;
import middle.messages.InitMessage;
import middle.messages.MovementMessage;
import middle.messages.PassMessage;
import middle.messages.PingMessage;
import middle.messages.PongMessage;
import middle.messages.RecoveryMessage;
import middle.messages.RetrasmissionRequestMessage;
import middle.messages.RisikoMessage;
import middle.messages.StatusPeerMessage;
import middle.messages.WelcomeMessage;

/**
 *
 * @author root
 */
public class JXTARisikoEventGenerator implements RisikoEventGenerator{

    

    public RisikoEvent generateEvent(RisikoMessage message) {
        VirtualRisikoMessage virtualMessage=(VirtualRisikoMessage)message;
        String type=virtualMessage.getType();
        if(type.equals(MessageTypes.ACK)){
            return new AckEvent((AckMessage)message);
        }
        if(type.equals(MessageTypes.APPLIANCE)){
            return new ApplianceEvent((ApplianceMessage)message);
        }
        if(type.equals(MessageTypes.ATTACK)){
            return new AttackEvent((AttackMessage)message);
        }
        if(type.equals(MessageTypes.CHANGE_CARDS)){
            return new ChangeCardEvent((ChangeCardMessage)message);
        }
        if(type.equals(MessageTypes.CHAT)){
            return new ChatEvent((ChatMessage)message);
        }
        if(type.equals(MessageTypes.ELECTION)){
            return  new ElectionEvent((ElectionMessage)message);
        }
        if(type.equals(MessageTypes.INIT)){
            return new InitEvent((InitMessage)message);
        }
        if(type.equals(MessageTypes.MOVEMENT)){
            return new MovementEvent((MovementMessage)message);
        }
        if(type.equals(MessageTypes.PASS)){
            return new PassEvent((PassMessage)message);
        }
        if(type.equals(MessageTypes.PING)){
            return new PingEvent((PingMessage)message);
        }
        if(type.equals(MessageTypes.PONG)){
            return new PongEvent((PongMessage)message);
        }
        if(type.equals(MessageTypes.RECOVERY)){
            return new RecoveryEvent((RecoveryMessage)message);
        }
        if(type.equals(MessageTypes.ELECTION_REQUEST)){
            return new ElectionRequestEvent((ElectionRequestMessage)message);
        }
        if(type.equals(MessageTypes.RETRASMISSION_REQUEST)){
            return new RetrasmissionRequestEvent((RetrasmissionRequestMessage)message);
        }
        if(type.equals(MessageTypes.STATUS_PEER)){
            return new StatusPeerEvent((StatusPeerMessage)message);
        }
        if(type.equals(MessageTypes.WELCOME)){
            return new WelcomeEvent((WelcomeMessage)message);
        }
            
        return null;
    }
    
}
