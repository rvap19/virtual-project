/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jxta.communication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.jxta.endpoint.Message;
import net.jxta.endpoint.MessageElement;
import net.jxta.endpoint.StringMessageElement;
import net.jxta.peergroup.PeerGroup;
import net.jxta.pipe.InputPipe;
import net.jxta.pipe.PipeMsgEvent;
import net.jxta.pipe.PipeMsgListener;
import net.jxta.pipe.PipeService;
import net.jxta.protocol.PipeAdvertisement;
import net.jxta.util.JxtaBiDiPipe;
import net.jxta.util.JxtaServerPipe;
import virtualrisikoii.listener.ApplianceListener;
import virtualrisikoii.listener.AttackListener;
import virtualrisikoii.listener.ChangeCardListener;
import virtualrisikoii.listener.ChatListener;
import virtualrisikoii.listener.InitListener;
import virtualrisikoii.listener.MovementListener;
import virtualrisikoii.listener.PassListener;

/**
 *
 * @author root
 */
public class Communicator implements PipeMsgListener{

    public static Communicator instance;
    
    public final String namespace="VRNameSpace";
    public final String type="type";

    public final String ID_MSG="IDMSG";
    public final String INIT="init";
    public final String APPLIANCE="appliance";
    public final String ATTACK="attack";
    public final String MOVEMENT="movement";
    public final String CHANGE_CARD="change_cards";
    public final String PASSES="passes";
    public final String ACK="ack";
    public final String CHAT="chat";




    private List<ApplianceListener> applianceListeners;
    private List<AttackListener> attackListeners;
    private List<ChangeCardListener> changeCardsListeners;
    private List<ChatListener> chatListeners;
    private List<InitListener> initListeners;
    private List<MovementListener> movementListeners;
    private List<PassListener> passListeners;
    
    private final  String GAMER="gamer";

    private List<JxtaBiDiPipe> toPeersPipes;
    private JxtaBiDiPipe toCentralPeer;

    private boolean isCentral;
    private int current_message_id=0;
    private String playerName;

    

    private Communicator(){
        applianceListeners=new ArrayList<ApplianceListener>();
        attackListeners=new ArrayList<AttackListener>();
        changeCardsListeners=new ArrayList<ChangeCardListener>();
        chatListeners=new ArrayList<ChatListener>();
        initListeners=new ArrayList<InitListener>();
        movementListeners=new ArrayList<MovementListener>();
        passListeners=new ArrayList<PassListener>();
        

    }

    /*
     * l'inizializzazione del comunicatore deve prevedere la pubblicazione di una propagate pipe verso tutti i peer
     *
     */

    public static Communicator initCentralCommunicator1(String peerName,PeerGroup group,PipeAdvertisement pipe,int players) throws IOException{
        instance=new Communicator();
        instance.playerName=peerName;
        instance.isCentral=true;
        instance.toPeersPipes=new ArrayList<JxtaBiDiPipe>();
        JxtaServerPipe serverPipe=new JxtaServerPipe(group, pipe);
        serverPipe.setPipeTimeout(10*1000);
        try{
           while(true){
                JxtaBiDiPipe bdpipe=serverPipe.accept();
                if(bdpipe==null){
                    throw new IOException("impossibile stabilire connessione");
                }
                bdpipe.setMessageListener(instance);
                instance.toPeersPipes.add(bdpipe);

            }
        }catch(Exception ex){
            System.out.println("TIMEOUT");
        }
        return instance;
    }

    public static Communicator initPeerComunicator(String peerName,PeerGroup group,PipeAdvertisement pipe) throws IOException{
        instance=new Communicator();
        instance.playerName=peerName;
        instance.isCentral=false;
        instance.toCentralPeer=	new JxtaBiDiPipe(group,pipe,12*1000, instance, true);
        int counter=0;
        int limit=4;
        while((!instance.toCentralPeer.isBound())&&counter<limit){
            instance.toCentralPeer=new JxtaBiDiPipe(group,pipe,12*1000, instance, true);
            counter++;
        }
        return instance;
    }






   

    public static Communicator getInstance(){
        return instance;
    }


    public void setPlayerName(String player){
        this.playerName=player;
    }
   

    public void addApplianceListener(ApplianceListener listener){
        this.applianceListeners.add(listener);
    }
    public void removeApplianceListener(ApplianceListener listener){
        this.applianceListeners.remove(listener);
    }

    public void addAttackListener(AttackListener listener){
        this.attackListeners.add(listener);
    }
    public void removeAttackListener(AttackListener listener){
        this.attackListeners.remove(listener);
    }

    public void addChangeCardsListener(ChangeCardListener listener){
        this.changeCardsListeners.add(listener);
    }
    public void removeChangeAttackListener(ChangeCardListener listener){
        this.changeCardsListeners.remove(listener);
    }

    public void addChatListener(ChatListener listener){
        this.chatListeners.add(listener);
    }
    public void removeChatListener(ChatListener listener){
        this.chatListeners.remove(listener);
    }

    public void addInitListener(InitListener listener){
        this.initListeners.add(listener);
    }
    public void removeInitListener(InitListener listener){
        this.initListeners.remove(listener);
    }

    public void addMovementListener(MovementListener listener){
        this.movementListeners.add(listener);
    }
    public void removeMovementListener(MovementListener listener){
        this.movementListeners.remove(listener);
    }

    public void addPassListener(PassListener listener){
        this.passListeners.add(listener);
    }
    public void removePassListener(PassListener listener){
        this.passListeners.remove(listener);
    }



    



    private boolean sendMessage(Message message,int msgID) throws IOException{
        StringMessageElement mElement=new StringMessageElement(ID_MSG, Integer.toString(msgID), null);
        message.addMessageElement(namespace, mElement);
       

        if(!this.isCentral){
            return this.toCentralPeer.sendMessage(message);
        }
        boolean result=true;
        Iterator<JxtaBiDiPipe> pipes=this.toPeersPipes.iterator();
        while(pipes.hasNext()){
            result=result&&pipes.next().sendMessage(message);
        }
        return result;
    }

     public boolean sendMessage(Message message) throws IOException{
          StringMessageElement mElement=new StringMessageElement(GAMER,playerName , null);
        message.addMessageElement(namespace, mElement);
         return sendMessage(message,current_message_id);
         
     }



    

    public Message createInitMessage(int players,int seed_dice,String map_name,int seed_card,int seed_region){
        Message message=new Message();
        StringMessageElement mE=new StringMessageElement(type, INIT, null);
        message.addMessageElement(namespace, mE);

        StringMessageElement mElement = new StringMessageElement(InitMessageAttributes.SEED_DICE,Integer.toString(seed_dice), null);
        message.addMessageElement(namespace, mElement);
        mElement = new StringMessageElement(InitMessageAttributes.MAP_NAME,map_name, null);
        message.addMessageElement(namespace, mElement);
        mElement = new StringMessageElement(InitMessageAttributes.SEED_CARDS,Integer.toString(seed_card), null);
        message.addMessageElement(namespace, mElement);
        mElement = new StringMessageElement(InitMessageAttributes.SEED_REGION,Integer.toString(seed_region), null);
        message.addMessageElement(namespace, mElement);
        mElement = new StringMessageElement(InitMessageAttributes.PLAYERS,Integer.toString(players), null);
        message.addMessageElement(namespace, mElement);
        return message;

    }

    public void elaborateInitMessage(Message message){
        String name=message.getMessageElement(namespace, GAMER).toString();
        if(name.equals(playerName)){
            return;
        }
        int seed_dice=Integer.parseInt(message.getMessageElement(namespace, InitMessageAttributes.SEED_DICE).toString());
        String map_name=message.getMessageElement(namespace, InitMessageAttributes.MAP_NAME).toString();
        int seed_card=Integer.parseInt(message.getMessageElement(namespace, InitMessageAttributes.SEED_CARDS).toString());
        int seed_region=Integer.parseInt(message.getMessageElement(namespace, InitMessageAttributes.SEED_REGION).toString());
        int players=Integer.parseInt(message.getMessageElement(namespace, InitMessageAttributes.PLAYERS).toString());
        Iterator<InitListener> listeners=this.initListeners.iterator();
        while(listeners.hasNext()){
            listeners.next().init(players,seed_dice, map_name, seed_card, seed_region);
        }
    }

    public Message createApplicanceMessage(int troops_number,int region){
        Message message=new Message();
        StringMessageElement mE=new StringMessageElement(type, APPLIANCE, null);
        message.addMessageElement(namespace, mE);

        StringMessageElement mElement = new StringMessageElement(ApplianceAttributes.TROOPS_NUMBER,Integer.toString(troops_number), null);
        message.addMessageElement(namespace, mElement);
        mElement = new StringMessageElement(ApplianceAttributes.REGION_IDS,Integer.toString(region), null);
        message.addMessageElement(namespace, mElement);
        return message;
    }

    public void elaborateApplianceMessage(Message message){
         String name=message.getMessageElement(namespace, GAMER).toString();
        if(name.equals(playerName)){
            return;
        }
        int troopNumber=Integer.parseInt(message.getMessageElement(namespace, ApplianceAttributes.TROOPS_NUMBER).toString());
        int region=Integer.parseInt(message.getMessageElement(namespace, ApplianceAttributes.REGION_IDS).toString());
        Iterator<ApplianceListener> listeners=this.applianceListeners.iterator();
        while(listeners.hasNext()){
            listeners.next().updateAppliance(troopNumber, region);
        }
    }

    public Message createAttackMessage(int troops_number,int from,int to){
        Message message=new Message();
        StringMessageElement mE=new StringMessageElement(type, ATTACK, null);
        message.addMessageElement(namespace, mE);

        StringMessageElement mElement = new StringMessageElement(AttackAttributes.TROOPS_NUMBER,Integer.toString(troops_number), null);
        message.addMessageElement(namespace, mElement);
        mElement = new StringMessageElement(AttackAttributes.FROM_REGION_ID,Integer.toString(from), null);
        message.addMessageElement(namespace, mElement);
        mElement = new StringMessageElement(AttackAttributes.TO_REGION_ID,Integer.toString(to), null);
        message.addMessageElement(namespace, mElement);
        return message;
    }

    public void elaborateAttackMessage(Message message){
         String name=message.getMessageElement(namespace, GAMER).toString();
        if(name.equals(playerName)){
            return;
        }
        int troops_number=Integer.parseInt(message.getMessageElement(namespace, AttackAttributes.TROOPS_NUMBER).toString());
        int from=Integer.parseInt(message.getMessageElement(namespace, AttackAttributes.FROM_REGION_ID).toString());
        int to=Integer.parseInt(message.getMessageElement(namespace, AttackAttributes.TO_REGION_ID).toString());
        Iterator<AttackListener> listeners=this.attackListeners.iterator();
        while(listeners.hasNext()){
            listeners.next().updateAttack(troops_number, from, to);
        }
    }

    public Message createMovementMessage(int troops_number,int from,int to){
        Message message=new Message();
        StringMessageElement mE=new StringMessageElement(type, MOVEMENT, null);
        message.addMessageElement(namespace, mE);

        StringMessageElement mElement = new StringMessageElement(MovementAttributes.TROOPS_NUMBER,Integer.toString(troops_number), null);
        message.addMessageElement(namespace, mElement);
        mElement = new StringMessageElement(MovementAttributes.FROM_REGION_ID,Integer.toString(from), null);
        message.addMessageElement(namespace, mElement);
        mElement = new StringMessageElement(MovementAttributes.TO_REGION_ID,Integer.toString(to), null);
        message.addMessageElement(namespace, mElement);
        return message;

    }

    public void elaborateMovementMessage(Message message){
         String name=message.getMessageElement(namespace, GAMER).toString();
        if(name.equals(playerName)){
            return;
        }
        int troops=Integer.parseInt(message.getMessageElement(namespace, MovementAttributes.TROOPS_NUMBER).toString());
        int from = Integer.parseInt(message.getMessageElement(namespace, MovementAttributes.FROM_REGION_ID).toString());
        int to= Integer.parseInt(message.getMessageElement(namespace, MovementAttributes.TO_REGION_ID).toString());
        Iterator<MovementListener> listeners=this.movementListeners.iterator();
        while(listeners.hasNext()){
            listeners.next().updateMovement(troops, from, to);
        }
    }

    public Message createChangeCardsMessage(int card1,int card2,int card3){
        Message message=new Message();
        StringMessageElement mE=new StringMessageElement(type, CHANGE_CARD, null);
        message.addMessageElement(namespace, mE);

        StringMessageElement mElement = new StringMessageElement(ChangeCardsAttributes.CARD_ID,Integer.toString(card1), null);
        message.addMessageElement(namespace, mElement);
        mElement = new StringMessageElement(ChangeCardsAttributes.CARD_ID,Integer.toString(card2), null);
        message.addMessageElement(namespace, mElement);
        mElement = new StringMessageElement(ChangeCardsAttributes.CARD_ID,Integer.toString(card3), null);
        message.addMessageElement(namespace, mElement);
        return message;

    }

    public void elaborateChangeCardsMessage(Message message){
         String name=message.getMessageElement(namespace, GAMER).toString();
        if(name.equals(playerName)){
            return;
        }
        Message.ElementIterator iterator= message.getMessageElements(namespace, ChangeCardsAttributes.CARD_ID);
        
        MessageElement element=iterator.next();
        int card1=new Integer(message.getMessageElement(namespace, ChangeCardsAttributes.CARD_ID).toString()).intValue();
        int card2=new Integer(message.getMessageElement(namespace, ChangeCardsAttributes.CARD_ID).toString()).intValue();
        int card3=new Integer(message.getMessageElement(namespace, ChangeCardsAttributes.CARD_ID).toString()).intValue();

        Iterator<ChangeCardListener> listeners=this.changeCardsListeners.iterator();
        while(listeners.hasNext()){
            listeners.next().updateChangeCards(card1, card2, card3);
        }
        
    }

    public Message createPassesMessage(int turno_successivo){
        Message message=new Message();
        StringMessageElement mE=new StringMessageElement(type, PASSES, null);
        message.addMessageElement(namespace, mE);

        StringMessageElement mElement = new StringMessageElement(PassAttributes.SUCC_TURN,Integer.toString(turno_successivo), null);
        message.addMessageElement(namespace, mElement);
        return message;
    }

    public void elaboratePassesMessage(Message message){
         String name=message.getMessageElement(namespace, GAMER).toString();
        if(name.equals(playerName)){
            return;
        }
        int turn=Integer.parseInt(message.getMessageElement(namespace, PassAttributes.SUCC_TURN).toString());
        Iterator<PassListener> listeners=this.passListeners.iterator();
        while(listeners.hasNext()){
            listeners.next().updatePass(turn);
        }
    }


    public Message createACKMessage(int ack_message_id){
        Message message=new Message();
        StringMessageElement mE=new StringMessageElement(type, ACK, null);
        message.addMessageElement(namespace, mE);

        StringMessageElement mElement = new StringMessageElement(AckAttributes.ACK_FOR_MESSAGE_ID,Integer.toString(ack_message_id), null);
        message.addMessageElement(namespace, mElement);
        return message;
    }

   

    public Message createChatMessage(String to,String messageString){
        Message message=new Message();
        StringMessageElement mE=new StringMessageElement(type, CHAT, null);
        message.addMessageElement(namespace, mE);

         mE=new StringMessageElement(ChatAttributes.DESTINATION , to, null);
        message.addMessageElement(namespace, mE);

         mE=new StringMessageElement(ChatAttributes.MESSAGE , messageString, null);
        message.addMessageElement(namespace, mE);

        return message;

    }

    public void elaborateChatMessage(Message message){
         String name=message.getMessageElement(namespace, GAMER).toString();
        if(name.equals(playerName)){
            return;
        }
        String to=message.getMessageElement(namespace, ChatAttributes.DESTINATION).toString();
        String messageString=message.getMessageElement(namespace, ChatAttributes.MESSAGE).toString();
        Iterator<ChatListener> listeners=this.chatListeners.iterator();
        while(listeners.hasNext()){
            listeners.next().updateChat(to, messageString);
        }
        
    }

    public void pipeMsgEvent(PipeMsgEvent pme) {
        
       Message msg=pme.getMessage();
       System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
       
       System.out.println(msg.getMessageElement(type).toString()+" FROM "+msg.getMessageElement(GAMER)+" ID "+msg.getMessageElement(ID_MSG));

       String messageType=msg.getMessageElement(namespace, type).toString();
       int msgID=0;

       try{
           msgID=new Integer(msg.getMessageElement(namespace, ID_MSG).toString()).intValue();
       }catch(NumberFormatException ex){

       }




     if(this.isCentral){
            try {
                this.sendMessage(msg,msgID);
            } catch (IOException ex) {
                Logger.getLogger(Communicator.class.getName()).log(Level.SEVERE, null, ex);
            }
     }
      

       if(messageType.equals(INIT)){
           this.elaborateInitMessage(msg);

       }else if(messageType.equals(APPLIANCE)){
           this.elaborateApplianceMessage(msg);
       }else if(messageType.equals(ATTACK)){
           this.elaborateAttackMessage(msg);
       }else if(messageType.equals(MOVEMENT)){
           this.elaborateMovementMessage(msg);
       }else if(messageType.equals(CHANGE_CARD)){
           this.elaborateChangeCardsMessage(msg);
       }else if(messageType.equals(PASSES)){
           this.elaboratePassesMessage(msg);
       }else if(messageType.equals(CHAT)){
           this.elaborateChatMessage(msg);
           return;
       }else{
           return;
       }


   

    }

    

   

    public class InitMessageAttributes{
        public static final String SEED_DICE="seed_dice";
        public static final String MAP_NAME="map_name";
        public static final String SEED_CARDS="seed_cards";
        public static final String SEED_REGION="seed_region";
        public static final String PLAYERS="players_number";
    }

    public class ApplianceAttributes{
        public static final String TROOPS_NUMBER="troops";
        public static final String REGION_IDS="region_id";
    }

    public class AttackAttributes{
        public static final String TROOPS_NUMBER="troops";
        public static final String FROM_REGION_ID="from_region";
        public static final String TO_REGION_ID="to_region";
        
    }

    public class MovementAttributes{
        public static final String TROOPS_NUMBER="troops";
        public static final String FROM_REGION_ID="from_region";
        public static final String TO_REGION_ID="to_region";
    }

    public class ChangeCardsAttributes{
        public static final String CARD_ID="card_id";
    }

    public class PassAttributes{
        public static final String SUCC_TURN="successive_turn";
    }

    public class AckAttributes{
        public static final String ACK_FOR_MESSAGE_ID="ack_message";
    }

    public class ChatAttributes{
        public static final String MESSAGE="message";
        public static final String DESTINATION="destination";
        public static final String all="all";
    }

    
    
   


}
