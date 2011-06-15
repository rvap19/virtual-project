/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jxta.communication;

import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxta.communication.messages.ApplianceMessage;
import jxta.communication.messages.AttackMessage;
import jxta.communication.messages.ChangeCardMessage;
import jxta.communication.messages.ChatMessage;
import jxta.communication.messages.InitMessage;
import jxta.communication.messages.MovementMessage;
import jxta.communication.messages.PassMessage;
import jxta.communication.messages.RecoveryMessage;
import jxta.communication.messages.StatusPeerMessage;
import jxta.communication.messages.VirtualRisikoMessage;
import jxta.communication.messages.WelcomeMessage;


import jxta.listener.ConnectionListener;
import net.jxta.endpoint.Message;
import net.jxta.endpoint.StringMessageElement;
import net.jxta.peergroup.PeerGroup;
import net.jxta.pipe.PipeMsgEvent;
import net.jxta.pipe.PipeMsgListener;
import net.jxta.protocol.PipeAdvertisement;
import net.jxta.util.JxtaBiDiPipe;
import services.RecoveryListener;
import util.RecoveryUtil;
import virtualrisikoii.GameParameter;
import virtualrisikoii.RecoveryParameter;
import jxta.communication.messages.listener.ApplianceListener;
import jxta.communication.messages.listener.AttackListener;
import jxta.communication.messages.listener.ChangeCardListener;
import jxta.communication.messages.listener.ChatListener;
import jxta.communication.messages.listener.InitListener;
import jxta.communication.messages.listener.MovementListener;
import jxta.communication.messages.listener.PassListener;
import jxta.communication.messages.listener.ReconnectionRequestListener;
import jxta.communication.messages.listener.StatusPeerListener;

/**
 *
 * @author root
 */
public class VirtualCommunicator implements ConnectionListener,PipeMsgListener{

    public static VirtualCommunicator instance;
    
    public final String namespace=VirtualRisikoMessage.namespace;
   




    private List<ApplianceListener> applianceListeners;
    private List<AttackListener> attackListeners;
    private List<ChangeCardListener> changeCardsListeners;
    private List<ChatListener> chatListeners;
    private List<InitListener> initListeners;
    private List<MovementListener> movementListeners;
    private List<PassListener> passListeners;
    private RecoveryListener recoveryListeners;
    private ReconnectionRequestListener recoveryRequestListener;
    private List<StatusPeerListener> statusListener;
    
    

   // private List<JxtaBiDiPipe> toPeersPipes;
    private JxtaBiDiPipe toCentralPeer;
    //HashMap<nomegiocatore,pipe>
    private HashMap<String,JxtaBiDiPipe> toPeersPipes;

    private ConnectionHandler connectionHandler;
    private int maxPlayers;

    private boolean isCentral;
    private int current_message_id=0;
    private String playerName;

   // private List<String> playerNames;
    private boolean isClose;
    private int currentPlayerNumber;
    private boolean gameInProgress;

    private GameParameter gameParameter;
    private PipeAdvertisement centralPeerPipeAdv;
    
    private PeerGroup peerGroup;

    private boolean pingReceived;

    private VirtualCommunicator(){
        applianceListeners=new ArrayList<ApplianceListener>();
        attackListeners=new ArrayList<AttackListener>();
        changeCardsListeners=new ArrayList<ChangeCardListener>();
        chatListeners=new ArrayList<ChatListener>();
        initListeners=new ArrayList<InitListener>();
        movementListeners=new ArrayList<MovementListener>();
        passListeners=new ArrayList<PassListener>();
        statusListener=new ArrayList<StatusPeerListener>();

    }

    /*
     * l'inizializzazione del comunicatore deve prevedere la pubblicazione di una propagate pipe verso tutti i peer
     *
     */


    public static VirtualCommunicator initCentralCommunicator1(String peerName,PeerGroup group,PipeAdvertisement pipe) throws IOException{
        instance=new VirtualCommunicator();
        instance.gameInProgress=false;
        instance.playerName=peerName;
        instance.isCentral=true;
        instance.currentPlayerNumber=1;
        instance.toPeersPipes=new HashMap<String, JxtaBiDiPipe>();
        instance.connectionHandler=new ConnectionHandler(group, pipe, 10, 120000);
        instance.connectionHandler.setConnectionListener(instance);
        instance.connectionHandler.start();
        return instance;
    }

    public static VirtualCommunicator initPeerComunicator(String peerName,PeerGroup group,PipeAdvertisement pipe) throws IOException{
        instance=new VirtualCommunicator();
        instance.playerName=peerName;
        instance.isCentral=false;
        instance.centralPeerPipeAdv=pipe;
        instance.peerGroup=group;
        
        if(!instance.connect()){
            instance=null;
        }
        

        return instance;

        
    }

    public RecoveryListener getRecoveryListeners() {
        return recoveryListeners;
    }

    public void setRecoveryListeners(RecoveryListener recoveryListeners) {
        this.recoveryListeners = recoveryListeners;
    }

    public ReconnectionRequestListener getRecoveryRequestListener() {
        return recoveryRequestListener;
    }

    public void setRecoveryRequestListener(ReconnectionRequestListener recoveryRequestListener) {
        this.recoveryRequestListener = recoveryRequestListener;
    }




    public synchronized  boolean connect() throws IOException{
        int counter=0;
        int limit=4;
        toCentralPeer=new JxtaBiDiPipe();
        
        while((!toCentralPeer.isBound())&&counter<limit){
            toCentralPeer=new JxtaBiDiPipe(peerGroup,centralPeerPipeAdv,25*1000, this, true);
            
            counter++;
        }

        if(toCentralPeer.isBound()){
            System.err.println("server contattato");
            gameInProgress=true;
            

          //  toCentralPeer.setMessageListener(instance);
            
            Message msg=new WelcomeMessage(playerName);
            sendMessage(msg);
            return true;
        }
        System.err.println("impossibile contattare server");
        return false;
    }





   

    public static VirtualCommunicator getInstance(){
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

    public void removeStatusListener(StatusPeerListener o) {
         statusListener.remove(o);
    }

    public void addStatusListener(StatusPeerListener e) {
         statusListener.add(e);
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

    public int getCurrentPlayerNumber() {
        return currentPlayerNumber;
    }




    



    private boolean sendMessage(Message message,int msgID) {
        StringMessageElement mElement=new StringMessageElement(VirtualRisikoMessage.ID_MSG, Integer.toString(msgID), null);
        message.addMessageElement(namespace, mElement);
       

        if(!this.isCentral){
            try {
                return this.toCentralPeer.sendMessage(message);
            } catch (Exception ex) {
                ex.printStackTrace();
                
               
                return false;
            }
        }
        boolean result=true;

        Iterator<String> users=this.toPeersPipes.keySet().iterator();
        while(users.hasNext()){
            JxtaBiDiPipe pipe=toPeersPipes.get(users.next());
            try {
                if(pipe!=null&&pipe.isBound()){
                    result = result && pipe.sendMessage(message);
                }
                
            } catch (Exception ex) {
                System.err.println("pipe malfunzionante");
            }
        }
        
        
        return result;
    }

     public boolean sendMessage(Message message) throws IOException{
          StringMessageElement mElement=new StringMessageElement(VirtualRisikoMessage.GAMER,playerName , null);
        message.addMessageElement(namespace, mElement);
         return sendMessage(message,current_message_id);
         
     }

    public boolean sendInitMessages() throws IOException{
        Message msg;
        boolean gine=true;
        if(this.gameInProgress){
            return false;
        }
        int turn=1;

        List<String> names=this.getPlayerrNames();
        Iterator<String> iter=toPeersPipes.keySet().iterator();
        while(iter.hasNext()){

            msg=new InitMessage(this.currentPlayerNumber, gameParameter.getSeed_dice(), gameParameter.getMapName(), gameParameter.getSeed_cards(), gameParameter.getSeed_region(),names);

            StringMessageElement mE=new StringMessageElement(InitMessage.TURN, Integer.toString(turn), null);
            msg.addMessageElement(namespace, mE);

            mE=new StringMessageElement(VirtualRisikoMessage.GAMER, playerName, null);
            msg.addMessageElement(namespace, mE);

            mE=new StringMessageElement(VirtualRisikoMessage.ID_MSG, Integer.toString(0), null);

            msg.addMessageElement(namespace, mE);

            gine=gine&&toPeersPipes.get(iter.next()).sendMessage(msg);
            turn++;
        }
        this.gameInProgress=true;
       

        return gine;

    }

    public void setGameParameter(GameParameter par,boolean isClosed,List<String> playerNames,int maxPlayers){
        this.gameParameter=par;
        this.isClose=isClosed;
        if(isClosed){
           Iterator<String> iter=playerNames.iterator();
            while(iter.hasNext()){
                toPeersPipes.put(iter.next(), null);
            }
        }
        
        this.maxPlayers=maxPlayers;
    }

    public void elaborateInitMessage(Message message){
        InitMessage init=new InitMessage(message);
        String name=init.playerName();
        if(name.equals(playerName)){
            return;
        }

        Iterator<InitListener> listeners=this.initListeners.iterator();
        while(listeners.hasNext()){
            listeners.next().init(init);
        }

        
        gameInProgress=true;
      

    }

    public void elaborateApplianceMessage(Message message){
         String name=message.getMessageElement(namespace, VirtualRisikoMessage.GAMER).toString();
        if(name.equals(playerName)){
            return;
        }
        ApplianceMessage m=new ApplianceMessage(message);
        
        Iterator<ApplianceListener> listeners=this.applianceListeners.iterator();
        while(listeners.hasNext()){
            listeners.next().updateAppliance(m);
        }
    }

    

    public void elaborateAttackMessage(Message message){
         String name=message.getMessageElement(namespace, VirtualRisikoMessage.GAMER).toString();
        if(name.equals(playerName)){
            return;
        }
        AttackMessage m=new AttackMessage(message);
        Iterator<AttackListener> listeners=this.attackListeners.iterator();
        while(listeners.hasNext()){
            listeners.next().updateAttack(m);
        }
    }


    public void elaborateStatusMessage(Message message){
         String name=message.getMessageElement(namespace, VirtualRisikoMessage.GAMER).toString();
        if(name.equals(playerName)){
            return;
        }
        StatusPeerMessage m=new StatusPeerMessage(message);
        Iterator<StatusPeerListener> listeners=this.statusListener.iterator();
        while(listeners.hasNext()){
            listeners.next().updateStatus(m);
        }
    }

    

    public void elaborateMovementMessage(Message message){
         String name=message.getMessageElement(namespace, VirtualRisikoMessage.GAMER).toString();
        if(name.equals(playerName)){
            return;
        }

         MovementMessage m=new MovementMessage(message);
        
        Iterator<MovementListener> listeners=this.movementListeners.iterator();
        while(listeners.hasNext()){
            listeners.next().updateMovement(m);
        }
    }

   

    public void elaborateChangeCardsMessage(Message message){
         String name=message.getMessageElement(namespace, VirtualRisikoMessage.GAMER).toString();
        if(name.equals(playerName)){
            return;
        }

       
        ChangeCardMessage m=new ChangeCardMessage(message);

        Iterator<ChangeCardListener> listeners=this.changeCardsListeners.iterator();
        while(listeners.hasNext()){
            System.out.println("notifica change listener");
            listeners.next().updateChangeCards(m);
        }
        
    }

    

    public void elaboratePassesMessage(Message message){
         String name=message.getMessageElement(namespace, VirtualRisikoMessage.GAMER).toString();
        if(name.equals(playerName)){
            return;
        }

         PassMessage m=new PassMessage(message);
        Iterator<PassListener> listeners=this.passListeners.iterator();
        while(listeners.hasNext()){
            listeners.next().updatePass(m);
        }
    }

    

    private void elaborateWelcomeMessage(JxtaBiDiPipe pipe,Message msg) throws IOException{
       WelcomeMessage m=new WelcomeMessage(msg);
        if((!isClose)||(this.toPeersPipes.containsKey(m.getWelcomeName()))){
            this.currentPlayerNumber++;
            this.toPeersPipes.put(m.getWelcomeName(), pipe);
            
            pipe.setMessageListener(this);
            
            if(this.currentPlayerNumber==this.maxPlayers){
                this.sendInitMessages();
                this.gameInProgress=true;
            }
        }
        
    }

    private void elaborateReconnectRequest(JxtaBiDiPipe pipe,Message msg){
        String name=msg.getMessageElement(namespace, WelcomeMessage.PEER_NAME).toString();
        int turn=this.findTurno(name);
        this.toPeersPipes.put(name, pipe);
        
        pipe.setMessageListener(this);
        
        this.recoveryRequestListener.notifyReconnectionRequest(turn);

    }



   

    public void elaborateChatMessage(Message message){
        ChatMessage m=new ChatMessage(message);
        
        Iterator<ChatListener> listeners=this.chatListeners.iterator();
        while(listeners.hasNext()){
            listeners.next().updateChat(m);
        }
        
    }

    private boolean free=true;
    Lock lock=new ReentrantLock();

    public synchronized void pipeMsgEvent(PipeMsgEvent pme) {
        
       Message msg=pme.getMessage();
       try{
           lock.lock();
           this.elaborateMessage(msg);
        }finally{
            lock.unlock();
        }
    }

    private synchronized void elaborateMessage(Message msg){
        
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

       System.out.println(msg.getMessageElement(VirtualRisikoMessage.TYPE).toString()+" FROM "+msg.getMessageElement(VirtualRisikoMessage.GAMER)+" ID "+msg.getMessageElement(VirtualRisikoMessage.ID_MSG));

       String messageType=msg.getMessageElement(namespace, VirtualRisikoMessage.TYPE).toString();
       int msgID=0;

       try{
           msgID=new Integer(msg.getMessageElement(namespace, VirtualRisikoMessage.ID_MSG).toString()).intValue();
       }catch(NumberFormatException ex){
            System.err.println("id msg non riconisciuto");
       }




     if(this.isCentral){
            this.sendMessage(msg,msgID);

     }


       if(messageType.equals(VirtualRisikoMessage.INIT)){
           this.elaborateInitMessage(msg);

       }else if(messageType.equals(VirtualRisikoMessage.APPLIANCE)){
           
           this.elaborateApplianceMessage(msg);

       }else if(messageType.equals(VirtualRisikoMessage.ATTACK)){
          
           this.elaborateAttackMessage(msg);

       }else if(messageType.equals(VirtualRisikoMessage.MOVEMENT)){
           
           this.elaborateMovementMessage(msg);

       }else if(messageType.equals(VirtualRisikoMessage.CHANGE_CARD)){
          
           this.elaborateChangeCardsMessage(msg);
       }else if(messageType.equals(VirtualRisikoMessage.PASSES)){
          
           this.elaboratePassesMessage(msg);
       }else if(messageType.equals(VirtualRisikoMessage.RECOVERY)){
           
           this.elaborateRecoveryMessage(msg);
       }else if(messageType.equals(VirtualRisikoMessage.STATUS)){

           this.elaborateStatusMessage(msg);
       }else if(messageType.equals(VirtualRisikoMessage.CHAT)){

           this.elaborateChatMessage(msg);
           return;
       }else{
           return;
       }
    }

    public void notifyConnection(JxtaBiDiPipe pipe,Message msg) {
       
            
            if(!this.gameInProgress){
            try {
                this.elaborateWelcomeMessage(pipe, msg);
            } catch (IOException ex) {
                System.err.println("impossibile usre msg di registrazione");
            }
            }else{
                this.elaborateReconnectRequest(pipe,msg);
            }


       
    }

    

    public void sendRecoveryMessage(int player) throws IOException{
        RecoveryUtil util=new RecoveryUtil();
        RecoveryParameter parameter=util.createBackup();
        parameter.setTurnoMyGiocatore(player);
        Message msg=new RecoveryMessage(parameter);
        Iterator<String> iter=toPeersPipes.keySet().iterator();
        int index=0;
        String name=null;
        while(index<player && iter.hasNext()){
            name=iter.next();
            index++;
        }
        StringMessageElement mElement=new StringMessageElement(VirtualRisikoMessage.GAMER,playerName , null);
        msg.addMessageElement(namespace, mElement);

         mElement=new StringMessageElement(VirtualRisikoMessage.ID_MSG,"0" , null);
        msg.addMessageElement(namespace, mElement);

        toPeersPipes.get(name).sendMessage(msg);

        
       


    }

   

    private int findTurno(String name){
        Iterator<String> iter=this.toPeersPipes.keySet().iterator();
        int index=0;
        boolean found=false;
        while(iter.hasNext()&&!found){
            found=iter.next().equals(name);
            if(!found){
                index++;
            }
        }
        return index+1;
    }
    public void elaborateRecoveryMessage(Message message){


        //ricevo i dati sull inizializzaione
        
        RecoveryMessage m=new RecoveryMessage(message);
        recoveryListeners.notifyReconnect(m.getParameter());
        
    }

    

    public boolean isManager() {
        return this.isCentral;
    }



    
    

    
   

    

    

   

    

    

    

    

    

    

  

    




    public void closePipeFor(int playerTurn) throws IOException{
        String name=null;
        Iterator<String> iter=this.toPeersPipes.keySet().iterator();
        for(int i=0;i<playerTurn;i++){
            iter.hasNext();
            name=iter.next();
        }

        JxtaBiDiPipe pipe=this.toPeersPipes.get(name);
        if(pipe!=null){
             pipe.close();
             toPeersPipes.put(name, null);
         }
    }

    public void closePipeFor(int turn,String name) throws IOException{
         JxtaBiDiPipe pipe=this.toPeersPipes.get(name);
         if(pipe!=null){
             pipe.close();
             toPeersPipes.put(name, null);
             StatusPeerMessage msg=new StatusPeerMessage(turn, false);
             sendMessage(msg);
             this.elaborateStatusMessage(msg);
         }
    }

    public List<String> getPlayerrNames(){

        ArrayList<String> list=new ArrayList<String>();
        list.add(playerName);
        if(!isCentral){
            return list;
        }

        Iterator<String> iter=this.toPeersPipes.keySet().iterator();
        while(iter.hasNext()){
            list.add(iter.next());
        }
        return list;

    }

    private  class  Pinger extends Thread{

        private int sleepTime=90 * 1000 ;
        private int interval=1;

        private AtomicBoolean continueTimer;

        public Pinger(){
            this.continueTimer=new AtomicBoolean(true);
        }

        public void stopTimer(){
            this.continueTimer.set(false);
        }
        
        @Override
        public void run() {
            while(continueTimer.get()){
                try {
                    pingReceived=false;
                    sendPing();
                    for(int i=0;i<interval;i++){
                        this.sleep(sleepTime);
                    }
                    if(!pingReceived){
                        try {
                            connect();


                        } catch (IOException ex) {
                            System.err.println("IMPOSSIBILE RICONNETTERSI");
                        }
                    }


                    
                    
                } catch (InterruptedException ex) {
                   
                }

            }


        }

        private void sendPing() {
            throw new UnsupportedOperationException("Not yet implemented");
        }
    }

    
    

}


    
    
    
   



