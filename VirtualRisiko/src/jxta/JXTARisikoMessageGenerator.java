/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jxta;

import java.util.List;
import middle.RisikoMessageGenerator;
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
import middle.messages.StatusPeerMessage;
import middle.messages.WelcomeMessage;
import util.GameParameter;
import virtualrisikoii.RecoveryParameter;

/**
 *
 * @author root
 */
public class JXTARisikoMessageGenerator extends RisikoMessageGenerator{
    
    public JXTARisikoMessageGenerator(String playerName){
        setPlayerName(playerName);
    }

    @Override
    public AckMessage generateAckMSG(int ack_message_id) {
         AckMessage ack=new jxta.communication.messages.AckMessage(ack_message_id);
         ack.setPlayerName(playerName);
         return ack;
    }

    @Override
    public ApplianceMessage generateApplianceMSG(int region, int troopsNumber) {
        ApplianceMessage app= new jxta.communication.messages.ApplianceMessage(troopsNumber, region);
        app.setPlayerName(playerName);
        return app;
    }

    @Override
    public AttackMessage generateAttackMSG(int from, int to, int troopsNumber) {
        AttackMessage attack= new jxta.communication.messages.AttackMessage(troopsNumber, to, from);
        attack.setPlayerName(playerName);
        return attack;
    }

    @Override
    public ChangeCardMessage generateChangeCardMSG(int c1, int c2, int c3) {
        ChangeCardMessage ccm= new jxta.communication.messages.ChangeCardMessage(c1, c2, c3);
        ccm.setPlayerName(playerName);
        return ccm;
    }

    @Override
    public ChatMessage generateChatMSG(String from, String to, String message) {
        ChatMessage cm= new jxta.communication.messages.ChatMessage(to, from, message);
        cm.setPlayerName(playerName);
        return cm;
    }

    @Override
    public ElectionMessage generateElectionMSG(String peedID, int currentTurn) {
        ElectionMessage em= new jxta.communication.messages.ElectionMessage(peedID, currentTurn);
        em.setPlayerName(playerName);
        return em;
    }

    @Override
    public ElectionRequestMessage generateElectionRequestMSG() {
        ElectionRequestMessage erm= new jxta.communication.messages.ElectionRequestMessage();
        erm.setPlayerName(playerName);
        return erm;
    }

    

    @Override
    public MovementMessage generateMovementMSG(int from, int to, int troopsNumber) {
        MovementMessage mm= new jxta.communication.messages.MovementMessage(troopsNumber, from, to);
        mm.setPlayerName(playerName);
        return mm;
    }

    @Override
    public PassMessage generatePassMSG(int turno_successivo) {
        PassMessage pm= new jxta.communication.messages.PassMessage(turno_successivo);
        pm.setPlayerName(playerName);
        return pm;
        
    }

    @Override
    public PingMessage generatePingMSG() {
        PingMessage pm= new jxta.communication.messages.PingMessage();
        pm.setPlayerName(playerName);
        return pm;
    }

    @Override
    public PongMessage generatePongMSG(String peerID) {
        PongMessage pm= new jxta.communication.messages.PongMessage(peerID);
        pm.setPlayerName(playerName);
        return pm;
    }

    @Override
    public RecoveryMessage generateRecoveryMSG(RecoveryParameter parameter) {
        RecoveryMessage rm= new jxta.communication.messages.RecoveryMessage(parameter);
        rm.setPlayerName(playerName);
        return rm;
    }

    @Override
    public RetrasmissionRequestMessage generateRetrasmissionRequestMSG(int msgID) {
        RetrasmissionRequestMessage rrm= new jxta.communication.messages.RetrasmissionRequest(msgID);
        rrm.setPlayerName(playerName);
        return rrm;
    }

    @Override
    public StatusPeerMessage generateStatusPeerMSG(int peerID, boolean isOnline) {
        StatusPeerMessage spm= new jxta.communication.messages.StatusPeerMessage(peerID, isOnline);
        spm.setPlayerName(playerName);
        return spm;
    }

    @Override
    public WelcomeMessage generateWelcomeMSG(String playerUsername) {
        WelcomeMessage wm= new jxta.communication.messages.WelcomeMessage(playerName);
        wm.setPlayerName(playerName);
        return wm;
    }

    @Override
    public InitMessage generateInitMSG(List<String> userNames, GameParameter parameter) {
        InitMessage im= new jxta.communication.messages.InitMessage(parameter, userNames);
        im.setPlayerName(playerName);
        return im;
    }
    
}
