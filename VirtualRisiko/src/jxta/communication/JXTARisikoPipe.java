/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jxta.communication;

import java.io.IOException;
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
import middle.RisikoMessageListener;
import middle.RisikoPipe;
import middle.messages.RisikoMessage;
import net.jxta.endpoint.Message;
import net.jxta.pipe.PipeMsgEvent;
import net.jxta.pipe.PipeMsgListener;
import net.jxta.util.JxtaBiDiPipe;

/**
 *
 * @author root
 */
public class JXTARisikoPipe extends  RisikoPipe implements PipeMsgListener{
    
    private JxtaBiDiPipe pipe;
    
    
    public JXTARisikoPipe(){
        this(null);
    }
    public JXTARisikoPipe(JxtaBiDiPipe jxtaPipe){
        this.pipe=jxtaPipe;
    }

    public JxtaBiDiPipe getPipe() {
        return pipe;
    }

    public void setPipe(JxtaBiDiPipe pipe) {
        this.pipe = pipe;
    }

    
    
    protected boolean send(RisikoMessage message) {
        VirtualRisikoMessage msg=(VirtualRisikoMessage)message;
        try {
            System.out.println("invio msg "+message.getType()+" con ID "+message.getMSG_ID());
            this.pipe.sendMessage(msg);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public void close() {
        try {
            this.pipe.close();
        } catch (IOException ex) {
            
        }
    }

    

    public void pipeMsgEvent(PipeMsgEvent pme) {
        Message m=pme.getMessage();
        RisikoMessage msg=MessageUtil.messageCreator(m);
        
        if(messageNotifier!=null){
            this.messageNotifier.notifyMessage(msg);
        }
        
    }
    
    
    
}
