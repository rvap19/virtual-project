/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jxta.communication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import jxta.listener.ConnectionListener;
import net.jxta.endpoint.Message;
import net.jxta.endpoint.Message.ElementIterator;
import net.jxta.endpoint.StringMessageElement;
import net.jxta.peergroup.PeerGroup;
import net.jxta.pipe.PipeMsgEvent;
import net.jxta.pipe.PipeMsgListener;
import net.jxta.protocol.PipeAdvertisement;
import net.jxta.util.JxtaBiDiPipe;
import net.jxta.util.JxtaServerPipe;
import virtualrisikoii.listener.ApplianceListener;
import virtualrisikoii.listener.AttackListener;
import virtualrisikoii.listener.ChangeCardListener;
import virtualrisikoii.listener.ChatListener;
import virtualrisikoii.listener.FullInitListener;
import virtualrisikoii.listener.MovementListener;
import virtualrisikoii.listener.PassListener;

/**
 *
 * @author root
 */
public class FullCommunicator implements PipeMsgListener,ConnectionListener{

    public static FullCommunicator instance;
    
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
    public final String PRESENCE="presence";



    private List<ApplianceListener> applianceListeners;
    private List<AttackListener> attackListeners;
    private List<ChangeCardListener> changeCardsListeners;
    private List<ChatListener> chatListeners;
    private List<FullInitListener> initListeners;
    private List<MovementListener> movementListeners;
    private List<PassListener> passListeners;
    
    private final  String GAMER="gamer";

    private HashMap<String,JxtaBiDiPipe> pipes;
    private HashMap<String,PipeAdvertisement> advertisement;

    
    private int current_message_id=0;
    private String playerName;
    

    private ConnectionHandler connectionHandler;
    private PeerGroup peerGroup;

    

    private FullCommunicator(){
        applianceListeners=new ArrayList<ApplianceListener>();
        attackListeners=new ArrayList<AttackListener>();
        changeCardsListeners=new ArrayList<ChangeCardListener>();
        chatListeners=new ArrayList<ChatListener>();
        initListeners=new ArrayList<FullInitListener>();
        movementListeners=new ArrayList<MovementListener>();
        passListeners=new ArrayList<PassListener>();
        pipes=new HashMap<String,JxtaBiDiPipe>();

    }

    /*
     * inizializza il comunicatore...nessuna comunicazione è avviata
     */
    private boolean run;

    public static FullCommunicator initPeerFullCommunicator(String peerName,PipeAdvertisement myPipe,PeerGroup peerGroup) throws IOException{
        instance=new FullCommunicator();

        instance.playerName=peerName;
        instance.peerGroup=peerGroup;
        instance.connectionHandler=new ConnectionHandler(peerGroup, myPipe, 10, 120000);
        instance.connectionHandler.setConnectionListener(instance);
        instance.connectionHandler.start();
     //   thread.start();
        
        return instance;
    }



   

    public void connectoToPeer(PipeAdvertisement pipe) throws IOException{
        JxtaBiDiPipe x=new JxtaBiDiPipe(instance.peerGroup,pipe,12*1000, instance, true);

        int counter=0;
        System.out.println("tentativo connessione :: "+counter+" :: connessione stabilita --> "+x.isBound());
        int limit=5;
        while((!x.isBound())&&counter<limit){
            x=new JxtaBiDiPipe(instance.peerGroup,pipe,12*1000, instance, true);

            counter++;
            System.out.println("tentativo connessione :: "+counter+" :: connessione stabilita --> "+x.isBound());
        }
        x.setMessageListener(instance);

        pipes.put(pipe.getName(), x);
        Message msg=createPresenceMessage(this.playerName+" Pipe");
        x.sendMessage(msg);

        
    }

    public boolean waitForInitAck(long timeout) {
        Iterator<JxtaBiDiPipe> x=this.pipes.values().iterator();
        while(x.hasNext()){
            try {
                JxtaBiDiPipe bdp = x.next();
                Message msg = bdp.getMessage(timeout);
                
            } catch (InterruptedException ex) {
                Logger.getLogger(FullCommunicator.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        return true;
    }







   

    public static FullCommunicator getInstance(){
        return instance;
    }


    public void setPlayerName(String player){
        this.playerName=player;
    }

    public HashMap<String, PipeAdvertisement> getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(HashMap<String, PipeAdvertisement> advertisement) {
        this.advertisement = advertisement;
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

    public void addFullInitListener(FullInitListener listener){
        this.initListeners.add(listener);
    }
    public void removeFullInitListener(FullInitListener listener){
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
       

        
        boolean result=true;

        Iterator<JxtaBiDiPipe> x=pipes.values().iterator();
        while(x.hasNext()){
            result=result&&x.next().sendMessage(message);
        }
        return result;
    }

     public boolean sendMessage(Message message) throws IOException{
          StringMessageElement mElement=new StringMessageElement(GAMER,playerName , null);
        message.addMessageElement(namespace, mElement);
         return sendMessage(message,current_message_id);
         
     }



    

    private Message createInitMessage(int players,List<String> names,int seed_dice,String map_name,int seed_card,int seed_region){
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
        mElement=new StringMessageElement(ID_MSG, Integer.toString(0), null);
        message.addMessageElement(namespace, mElement);
        mElement=new StringMessageElement(GAMER, playerName, null);
        message.addMessageElement(namespace, mElement);
         mElement=new StringMessageElement(InitMessageAttributes.CREATOR_PIPENAME, playerName+" Pipe", null);
        message.addMessageElement(namespace, mElement);

        Iterator<String> iter=names.iterator();
        while(iter.hasNext()){
            mElement=new StringMessageElement(InitMessageAttributes.PIPESNAMES, iter.next(), null);
            message.addMessageElement(namespace, mElement);
        }
        return message;

    }

    /*
     * invocato dal master
     */

    public boolean sendInitMessages(int players,int seed_dice,String map_name,int seed_card,int seed_region) throws IOException{
        Message msg;
        boolean gine=true;
        int turn=1;
        Iterator<String> keys=pipes.keySet().iterator();
        List<String> names=elaboratePipesName();
        while(keys.hasNext()){
            msg=createInitMessage(players, names,seed_dice, map_name, seed_card, seed_region);
            StringMessageElement mE=new StringMessageElement(InitMessageAttributes.TURN, Integer.toString(turn), null);
             
            msg.addMessageElement(namespace, mE);
            gine=gine&&pipes.get(keys.next()).sendMessage(msg);
            turn++;
        }
        return gine;

    }

    public List<String> elaboratePipesName(){
        ArrayList<String> result=new ArrayList<String>();
        Iterator<String> iter=this.pipes.keySet().iterator();
        while(iter.hasNext()){
            result.add(iter.next());
        }
        return result;

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
        int myTurno=Integer.parseInt(message.getMessageElement(namespace, InitMessageAttributes.TURN).toString());
        String creator=message.getMessageElement(namespace, InitMessageAttributes.CREATOR_PIPENAME).toString();
        ElementIterator elements=message.getMessageElements(namespace, InitMessageAttributes.PIPESNAMES);
        ArrayList<String> names=new ArrayList<String>();
        while(elements.hasNext()){
            names.add(elements.next().toString());
        }
        Iterator<FullInitListener> listeners=this.initListeners.iterator();
        while(listeners.hasNext()){
            listeners.next().init(myTurno,players,names,creator,seed_dice, map_name, seed_card, seed_region);
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

        StringMessageElement mElement = new StringMessageElement(ChangeCardsAttributes.CARD1_ID,Integer.toString(card1), null);
        message.addMessageElement(namespace, mElement);
        mElement = new StringMessageElement(ChangeCardsAttributes.CARD2_ID,Integer.toString(card2), null);
        message.addMessageElement(namespace, mElement);
        mElement = new StringMessageElement(ChangeCardsAttributes.CARD3_ID,Integer.toString(card3), null);
        message.addMessageElement(namespace, mElement);
        return message;

    }

    public void elaborateChangeCardsMessage(Message message){
         String name=message.getMessageElement(namespace, GAMER).toString();
        if(name.equals(playerName)){
            return;
        }

        System.out.println("inizio notifica change listener");
        int card1=new Integer(message.getMessageElement(namespace, ChangeCardsAttributes.CARD1_ID).toString()).intValue();
        int card2=new Integer(message.getMessageElement(namespace, ChangeCardsAttributes.CARD2_ID).toString()).intValue();
        int card3=new Integer(message.getMessageElement(namespace, ChangeCardsAttributes.CARD3_ID).toString()).intValue();

        Iterator<ChangeCardListener> listeners=this.changeCardsListeners.iterator();
        while(listeners.hasNext()){
            System.out.println("notifica change listener");
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

    public Message createPresenceMessage(String x){
        Message message=new Message();
        StringMessageElement mE=new StringMessageElement(type, PRESENCE, null);
        message.addMessageElement(namespace, mE);

        StringMessageElement mElement = new StringMessageElement(PresenceAttributes.PIPE_NAME,x, null);
        message.addMessageElement(namespace, mElement);
        return message;
    }

    public String elaboratePresenceMessage(Message msg){
        return msg.getMessageElement(namespace, PresenceAttributes.PIPE_NAME).toString();
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


   

    public Message createChatMessage(String from,String to,String messageString){
        Message message=new Message();
        StringMessageElement mE=new StringMessageElement(type, CHAT, null);
        message.addMessageElement(namespace, mE);

         mE=new StringMessageElement(ChatAttributes.DESTINATION , to, null);
        message.addMessageElement(namespace, mE);
        mE=new StringMessageElement(ChatAttributes.SENDER , from, null);
        message.addMessageElement(namespace, mE);

         mE=new StringMessageElement(ChatAttributes.MESSAGE , messageString, null);
        message.addMessageElement(namespace, mE);

        return message;

    }

    public void elaborateChatMessage(Message message){
        
        String to=message.getMessageElement(namespace, ChatAttributes.DESTINATION).toString();
        String messageString=message.getMessageElement(namespace, ChatAttributes.MESSAGE).toString();
        String from=message.getMessageElement(namespace, ChatAttributes.SENDER).toString();
        Iterator<ChatListener> listeners=this.chatListeners.iterator();
        while(listeners.hasNext()){
            listeners.next().updateChat(from,to, messageString);
        }
        
    }

    private boolean free=true;

    public synchronized void pipeMsgEvent(PipeMsgEvent pme) {
        
       Message msg=pme.getMessage();
       System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
       
       System.out.println(msg.getMessageElement(type).toString()+" FROM "+msg.getMessageElement(GAMER)+" ID "+msg.getMessageElement(ID_MSG));

       String messageType=msg.getMessageElement(namespace, type).toString();
       int msgID=0;

       try{
           msgID=new Integer(msg.getMessageElement(namespace, ID_MSG).toString()).intValue();
       }catch(NumberFormatException ex){

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

   

    public void notifyConnection(JxtaBiDiPipe pipe,Message msg) {
        String pipeName=this.elaboratePresenceMessage(msg);
            pipes.put(pipeName, pipe);

            pipe.setMessageListener(instance);
        System.out.println("accettata connessione da "+pipeName);
        
    }

    public void sendMessageTo(Message ack, String creatorPipe) throws IOException {
        this.pipes.get(creatorPipe).sendMessage(ack);
    }

    

    

   

    public class InitMessageAttributes{
        public static final String SEED_DICE="seed_dice";
        public static final String MAP_NAME="map_name";
        public static final String SEED_CARDS="seed_cards";
        public static final String SEED_REGION="seed_region";
        public static final String PLAYERS="players_number";
        public static final String TURN="myTurn";
        public static final String PIPESNAMES="pipe_name";
        public static final String CREATOR_PIPENAME="creator_pipe_name";
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
        public static final String CARD1_ID="card1_id";
        public static final String CARD2_ID="card2_id";
        public static final String CARD3_ID="card3_id";
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
        public static final String TO_ALL="tutti";
        public static final String SENDER="sender";
    }

    public class PresenceAttributes{
        public static final String PIPE_NAME="pipe_name";
    }

    
    
   


}
