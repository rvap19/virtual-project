/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import middle.event.PassEvent;
import middle.event.RecoveryRequestEvent;
import middle.event.RisikoEvent;
import middle.listener.PassEventListener;
import middle.listener.RecoveryRequestEventListener;
import middle.messages.PassMessage;
import middle.messages.RecoveryMessage;
import virtualrisikoii.util.RecoveryUtil;
import virtualrisikoii.RecoveryParameter;
import virtualrisikoii.risiko.Giocatore;
import virtualrisikoii.risiko.Tavolo;

/**
 *
 * @author root
 */
public class RisikoRecoveryRequestEventListener implements RecoveryRequestEventListener,PassEventListener{

    private Middle middle;
    private VirtualCommunicator communicator;
    private boolean[] reconnectionNeeds;
    private HashMap<String,Integer> turns;
    private RisikoMessageGenerator messageBuilder;
    
    public RisikoRecoveryRequestEventListener(Middle middle){
        this();
        this.setMiddle(middle);
    }
    
    public RisikoRecoveryRequestEventListener(){
        int giocatori=Tavolo.getInstance().getGiocatori().size();
        this.reconnectionNeeds=new boolean[giocatori];
        for(int i=0;i<giocatori;i++){
            this.reconnectionNeeds[i]=false;
        }
        
        Iterator<Giocatore> iter=Tavolo.getInstance().getGiocatori().iterator();
        while(iter.hasNext()){
            Giocatore g=iter.next();
            turns.put(g.getUsername(), g.getID());
        }
    }

    public Middle getMiddle() {
        return middle;
    }

    public void setMiddle(Middle middle) {
        this.middle = middle;
        this.communicator=middle.getCommunicator();
        this.messageBuilder=this.middle.getMessageBuilder();
        middle.addListener(EventTypes.PASS, this);
        middle.addListener(EventTypes.RECOVERY_REQUEST, this);
    }
    
    
    public void notify(RecoveryRequestEvent c) {
        if(communicator.isCentral()){
            this.reconnectionNeeds[turns.get(c.getSource().playerName())]=true;
            System.out.println("Richiesta riconnessione "+c.getSource().playerName()+" registrata ");
        }
        
        
        
    }

    public void notify(RisikoEvent event) {
        String type=event.getType();
        if(type.equals(EventTypes.PASS)){
            PassEvent x=(PassEvent)event;
            notify(x);
        }else if(type.equals(EventTypes.RECOVERY_REQUEST)){
            RecoveryRequestEvent x=(RecoveryRequestEvent)event;
            notify(x);
        }
    }

    public void notify(PassEvent c) {
        PassMessage msg=(PassMessage) c.getSource();
        int turno=msg.getTurno_successivo();
        if(communicator.isCentral()&&reconnectionNeeds[turno]){
            try {
                
                RecoveryUtil util=new RecoveryUtil();
                RecoveryParameter parameter=util.createBackup();
                RecoveryMessage message=this.messageBuilder.generateRecoveryMSG(parameter);
                this.communicator.sendMessageTo(message, Tavolo.getInstance().getGiocatori().get(turno).getUsername());
                this.reconnectionNeeds[turno]=false;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
    }
    
}
