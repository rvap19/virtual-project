/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle;

import java.util.List;
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
import util.GameParameter;
import virtualrisikoii.RecoveryParameter;

/**
 *
 * @author root
 */
public abstract class  RisikoMessageGenerator {
    protected String playerName;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    
    public abstract AckMessage generateAckMSG(int ack_message_id);
    public abstract ApplianceMessage generateApplianceMSG(int region,int troopsNumber);
    public abstract AttackMessage generateAttackMSG(int from,int to,int troopsNumber);
    public abstract ChangeCardMessage generateChangeCardMSG(int c1,int c2,int c3);
    public abstract ChatMessage generateChatMSG(String from,String to,String message);
    public abstract ElectionMessage generateElectionMSG(String peedID,int currentTurn);
    public abstract ElectionRequestMessage generateElectionRequestMSG();
    public abstract InitMessage generateInitMSG(List<String> userNames,GameParameter parameter) ;
    public abstract MovementMessage generateMovementMSG(int from,int to,int troopsNumber);
    public abstract PassMessage generatePassMSG(int turno_successivo);
    public abstract PingMessage generatePingMSG();
    public abstract PongMessage generatePongMSG(String peerID);
    public abstract RecoveryMessage generateRecoveryMSG(RecoveryParameter parameter);
    public abstract RetrasmissionRequestMessage generateRetrasmissionRequestMSG(int msgID);
    public abstract StatusPeerMessage generateStatusPeerMSG(int peerID,boolean isOnline);
    public abstract WelcomeMessage generateWelcomeMSG(String playerUsername);
    
}
