/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package virtualrisikoii.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import middle.Middle;
import middle.RisikoMessageGenerator;
import middle.VirtualCommunicator;
import middle.event.ChatEvent;
import middle.messages.ChatMessage;
import virtualrisikoii.risiko.Giocatore;
import virtualrisikoii.risiko.Tavolo;

/**
 *
 * @author root
 */
public class ChatMessageSender {
    private Middle middle;
    private VirtualCommunicator communicator;
    private RisikoMessageGenerator messageBuilder;
    public ChatMessageSender(Middle middle){
        setMiddle(middle);
    }
    
    public ChatMessageSender(){
        
    }

    public Middle getMiddle() {
        return middle;
    }

    public void setMiddle(Middle middle) {
        this.middle=middle;
        this.communicator=middle.getCommunicator();
        this.messageBuilder=middle.getMessageBuilder();
    }

    public RisikoMessageGenerator getMessageBuilder() {
        return messageBuilder;
    }

    public void setMessageBuilder(RisikoMessageGenerator messageBuilder) {
        this.messageBuilder = messageBuilder;
    }
    
    
    
    public List<String> getPlayersNames(){
        ArrayList<String> players=new ArrayList<String>();
        players.add(ChatEvent.TO_ALL);

        Iterator<Giocatore> iter=Tavolo.getInstance().getGiocatori().iterator();

        while(iter.hasNext()){
            players.add(iter.next().getNome());
        }
        return players;
        
    }
    
     public void sendMessage(String from, String to, String message) {

        ChatMessage msg=this.messageBuilder.generateChatMSG(middle.getPlayerName(), to, message); 
                //new ChatMessage(to, Tavolo.getInstance().getMyGiocatore().getNome(), message);
        
       
            this.communicator.sendMessage(msg);
            middle.notifyMessage(msg);
       
    }
    
}
