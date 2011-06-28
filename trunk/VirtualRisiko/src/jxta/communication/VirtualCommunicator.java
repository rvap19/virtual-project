/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jxta.communication;

import services.ElectionController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
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
import jxta.communication.messages.listener.PongListener;
import jxta.communication.messages.listener.ReconnectionRequestListener;
import jxta.communication.messages.listener.StatusPeerListener;

import util.MessageSequencer;
import util.VirtualRisikoMessageNotifier;

/**
 *
 * @author root
 */
public class VirtualCommunicator implements ConnectionListener,PipeMsgListener,VirtualRisikoMessageNotifier{

    public static VirtualCommunicator instance;
    
    private Set<ApplianceListener> applianceListeners;
    private Set<AttackListener> attackListeners;
    private Set<ChangeCardListener> changeCardsListeners;
    private Set<ChatListener> chatListeners;
    private Set<InitListener> initListeners;
    private Set<MovementListener> movementListeners;
    private Set<PassListener> passListeners;
    private RecoveryListener recoveryListeners;
    private ReconnectionRequestListener recoveryRequestListener;
    private Set<StatusPeerListener> statusListener;
    private Set<PongListener> pongListeners;

    private ElectionController electionManager;
    
    

   // private List<JxtaBiDiPipe> toPeersPipes;
    private JxtaBiDiPipe toCentralPeer;
    //HashMap<nomegiocatore,pipe>
    private HashMap<String,JxtaBiDiPipe> toPeersPipes;

    private ConnectionHandler connectionHandler;
    private int maxPlayers;

    private boolean isCentral;
    private String playerName;

   // private List<String> playerNames;
    private boolean isClose;
    private int currentPlayerNumber;
    private boolean gameInProgress;

    private GameParameter gameParameter;
    private PipeAdvertisement centralPeerPipeAdv;
    
    private PeerGroup peerGroup;

    private Lock pipeLock;
    private boolean messageReceived;
    private MessageWaiter waiter=null;

    private MessageSequencer sequencer;
    private List<String> playerNames;
    

    private VirtualCommunicator(String playername){
        this.playerName=playername;
        applianceListeners=new HashSet<ApplianceListener>();
        attackListeners=new HashSet<AttackListener>();
        changeCardsListeners=new HashSet<ChangeCardListener>();
        chatListeners=new HashSet<ChatListener>();
        initListeners=new HashSet<InitListener>();
        movementListeners=new HashSet<MovementListener>();
        passListeners=new HashSet<PassListener>();
        statusListener=new HashSet<StatusPeerListener>();
        pongListeners=new HashSet<PongListener>();
        sequencer=new MessageSequencer(this.playerName,150);
        sequencer.setCurrentMessageID(0);
        sequencer.setNotifier(this);
        sequencer.setEnabled(true);
    }

    /*
     * l'inizializzazione del comunicatore deve prevedere la pubblicazione di una propagate pipe verso tutti i peer
     *
     */


    public static VirtualCommunicator initCentralCommunicator1(String peerName,PeerGroup group,PipeAdvertisement pipe) throws IOException{
        instance=new VirtualCommunicator(peerName);
        instance.pipeLock=new ReentrantLock(true);
        instance.gameInProgress=false;
        instance.isCentral=true;
        instance.currentPlayerNumber=1;
        instance.toPeersPipes=new HashMap<String, JxtaBiDiPipe>();
        instance.connectionHandler= ConnectionHandler.getInstance(group, pipe, 50, 2*60*1000);
        instance.connectionHandler.setConnectionListener(instance);
        
            instance.connectionHandler.start();
        
        
        
        return instance;
    }

    public static VirtualCommunicator initPeerComunicator(String peerName,PeerGroup group,PipeAdvertisement centralPeerPipeAdv,PipeAdvertisement peerPipeAdv) throws IOException{
        instance=new VirtualCommunicator(peerName);
        instance.pipeLock=new ReentrantLock(true);
        instance.connectionHandler=ConnectionHandler.getInstance(group, peerPipeAdv, 50, 2*60*1000);
        instance.connectionHandler.setConnectionListener(instance);
        
            instance.connectionHandler.start();
        
        

        instance.isCentral=false;
        instance.centralPeerPipeAdv=centralPeerPipeAdv;
        instance.peerGroup=group;
        
        if(!instance.connect()){
            instance=null;
        }
        

        return instance;

        
    }

    public boolean restartPeerCommunicator(PipeAdvertisement centralPipe,PipeAdvertisement peerPipe) throws IOException{
        
        isCentral=false;

        if(toCentralPeer!=null){
            this.toCentralPeer.setPipeEventListener(null);
            this.toCentralPeer.close();
        }
        
        this.centralPeerPipeAdv=centralPipe;
        
        gameInProgress=true;

        connectionHandler= ConnectionHandler.getInstance(this.peerGroup, peerPipe, 50, 2*60*1000);
        instance.connectionHandler.setConnectionListener(instance);
       /* if(!instance.connectionHandler.isAlive()){
            instance.connectionHandler.start();
        }*/

        return instance.connect();

    }
    

    public void commuteToCentralCommunicator(PipeAdvertisement pipe,List<String> playerNames,boolean isClose) throws IOException{
        
        isCentral=true;
        this.centralPeerPipeAdv=pipe;
        
        this.isClose=isClose;
        gameInProgress=true;
        toPeersPipes=new HashMap<String, JxtaBiDiPipe>();

        Iterator<String> players=playerNames.iterator();
        while(players.hasNext()){
            toPeersPipes.put(players.next(), null);
        }
     /*   connectionHandler= ConnectionHandler.getInstance(this.peerGroup, pipe, 50, 2*60*1000);
        instance.connectionHandler.setConnectionListener(instance);
        if(!instance.connectionHandler.isAlive()){
            instance.connectionHandler.start();
        }*/

    }

    public boolean isManagerOnLine() {
        return messageReceived;
    }


    

    public ElectionController getElectionNotifier() {
        return electionManager;
    }

    public void setElectionNotifier(ElectionController electionNotifier) {
        this.electionManager = electionNotifier;
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




    public synchronized  boolean connect() throws IOException {
        int counter=0;
        int limit=2;
        toCentralPeer=new JxtaBiDiPipe();
        
        while((!toCentralPeer.isBound())&&counter<limit){
            try {
                toCentralPeer = new JxtaBiDiPipe(peerGroup, centralPeerPipeAdv, 25 * 1000, this, true);
            } catch (IOException ex) {
                System.err.println("connection timeout :: impossibile connettersi al amanger");
            }
            
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

    public void addPongListener(PongListener lister){
        pongListeners.add(lister);
    }

    public void removePongListener(PongListener listener){
        pongListeners.remove(listener);
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
        message.addMessageElement(VirtualRisikoMessage.namespace, mElement);
       

        if(!this.isCentral){
            try {
                return this.toCentralPeer.sendMessage(message);
            } catch (Exception ex) {
                
                
               
                System.err.println("Impossibile inviare messaggio al coordinatore");
                try {
                    toCentralPeer.close();
                    System.out.println("chiudo pipe verso manager");
                } catch (IOException ex1) {
                    System.err.println("impossibile chidere pipe verso manager");
                }

            }
            return false;
        }
        boolean result=true;

        try{
            pipeLock.lock();
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
        }finally{
            pipeLock.unlock();
        }
        
        
        return result;
    }

     public boolean sendMessage(Message message) throws IOException{
          StringMessageElement mElement=new StringMessageElement(VirtualRisikoMessage.GAMER,playerName , null);
        message.addMessageElement(VirtualRisikoMessage.namespace, mElement);
       // System.out.println("inviato messaggio di "+message.getMessageElement(VirtualRisikoMessage.TYPE)+" dal "+message.getMessageElement(VirtualRisikoMessage.GAMER));
         boolean result= sendMessage(message,sequencer.getCurrentMessageID());
         String type=message.getMessageElement(VirtualRisikoMessage.namespace, VirtualRisikoMessage.TYPE).toString();
         if(!(type.equals(VirtualRisikoMessage.STATUS)||type.equals(VirtualRisikoMessage.ACK)||type.equals(VirtualRisikoMessage.PING)||type.equals(VirtualRisikoMessage.PONG)||type.equals(VirtualRisikoMessage.CHAT))){
            System.err.println("Inviato messaggio di "+type+" con MSG_ID "+sequencer.getCurrentMessageID());
            // sequencer.setCurrentMessageID(sequencer.getCurrentMessageID()+1);
            sequencer.incrementID();

         }
         return result;
         
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
            msg.addMessageElement(VirtualRisikoMessage.namespace, mE);

            mE=new StringMessageElement(VirtualRisikoMessage.GAMER, playerName, null);
            msg.addMessageElement(VirtualRisikoMessage.namespace, mE);

            mE=new StringMessageElement(VirtualRisikoMessage.ID_MSG, Integer.toString(sequencer.getCurrentMessageID()), null);

            msg.addMessageElement(VirtualRisikoMessage.namespace, mE);

            gine=gine&&toPeersPipes.get(iter.next()).sendMessage(msg);
            turn++;
        }
        this.gameInProgress=true;
     //   sequencer.setCurrentMessageID(sequencer.getCurrentMessageID()+1);
        sequencer.incrementID();

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

        this.playerNames=init.getNames();
        Iterator<InitListener> listeners=this.initListeners.iterator();
        while(listeners.hasNext()){
            listeners.next().init(init);
        }
        startMessageWaiter();
        
        gameInProgress=true;
      

    }

    private void startMessageWaiter(){
        if(waiter==null){
            waiter=new MessageWaiter();
             waiter.start();

        }else{
            waiter.stopTimer();
            waiter=new MessageWaiter();
            waiter.start();
        }

        
    }

    public void elaborateApplianceMessage(Message message){
         String name=message.getMessageElement(VirtualRisikoMessage.namespace, VirtualRisikoMessage.GAMER).toString();
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
         String name=message.getMessageElement(VirtualRisikoMessage.namespace, VirtualRisikoMessage.GAMER).toString();
        if(name.equals(playerName)){
            return;
        }
        AttackMessage m=new AttackMessage(message);
        Iterator<AttackListener> listeners=this.attackListeners.iterator();
        while(listeners.hasNext()){
            listeners.next().updateAttack(m);
        }
    }

    public void elaboratePingMessage(Message message){
         String name=message.getMessageElement(VirtualRisikoMessage.namespace, VirtualRisikoMessage.GAMER).toString();
        if(name.equals(playerName)){
            return;
        }
        PingMessage m=new PingMessage(message);

        PongMessage pong=new PongMessage(playerName);
        try {
            sendMessage(pong);
        } catch (IOException ex) {
            System.err.println("impossibile inviare pong al manager");
        }
    }


    public void elaborateStatusMessage(Message message){
         String name=message.getMessageElement(VirtualRisikoMessage.namespace, VirtualRisikoMessage.GAMER).toString();
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
         String name=message.getMessageElement(VirtualRisikoMessage.namespace, VirtualRisikoMessage.GAMER).toString();
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
         String name=message.getMessageElement(VirtualRisikoMessage.namespace, VirtualRisikoMessage.GAMER).toString();
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
         String name=message.getMessageElement(VirtualRisikoMessage.namespace, VirtualRisikoMessage.GAMER).toString();
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
        String name=msg.getMessageElement(VirtualRisikoMessage.namespace, WelcomeMessage.PEER_NAME).toString();
        
        try{
            pipeLock.lock();
            this.toPeersPipes.put(name, pipe);
        }finally{
            pipeLock.unlock();
        }
        pipe.setMessageListener(this);
        
        this.recoveryRequestListener.notifyReconnectionRequest(name);

    }



   

    public void elaborateChatMessage(Message message){
        ChatMessage m=new ChatMessage(message);
        
        Iterator<ChatListener> listeners=this.chatListeners.iterator();
        while(listeners.hasNext()){
            listeners.next().updateChat(m);
        }
        
    }

    
    

    public synchronized void pipeMsgEvent(PipeMsgEvent pme) {
        
       Message msg=pme.getMessage();
       
       
       this.sequencer.insertMessage(msg);
       
           
           
        
            
        
    }





    public void notifyMessage(Message msg,int msgID){
        
        
        messageReceived=true;
       
       String messageType=msg.getMessageElement(VirtualRisikoMessage.namespace, VirtualRisikoMessage.TYPE).toString();
      

       if(messageType.equals(VirtualRisikoMessage.PONG)){
           this.elaboratePongMessage(msg);
           return;
       }


     if(this.isCentral){
            this.sendMessage(msg,msgID);

     }


       if(messageType.equals(VirtualRisikoMessage.PING)){
           this.elaboratePingMessage(msg);

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

    private void elaborateRequestElectionMessage(JxtaBiDiPipe pipe,Message msg){
        ElectionRequestMessage erMsg=new ElectionRequestMessage(msg);
        this.electionManager.notifyRequestElectionMessage(pipe,erMsg);
        
    }

    private void elaborateElectionMessage(JxtaBiDiPipe pipe,Message msg){
        ElectionMessage eMsg=new ElectionMessage(msg);
        this.electionManager.notifyElectionMessage(pipe,eMsg);
    }

    public void notifyConnection(JxtaBiDiPipe pipe,Message msg) {
       

            String type=msg.getMessageElement(VirtualRisikoMessage.namespace, VirtualRisikoMessage.TYPE).toString();
            if(type.equals(VirtualRisikoMessage.REQUEST_ELECTION)){
                this.elaborateRequestElectionMessage(pipe,msg);
                
                return;

            }

            if(type.equals(VirtualRisikoMessage.ELECTION)){
                this.elaborateElectionMessage(pipe,msg);
                
                return;
            }

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
        msg.addMessageElement(VirtualRisikoMessage.namespace, mElement);

         mElement=new StringMessageElement(VirtualRisikoMessage.ID_MSG,Integer.toString(sequencer.getCurrentMessageID()), null);
        msg.addMessageElement(VirtualRisikoMessage.namespace, mElement);

        JxtaBiDiPipe pipe=toPeersPipes.get(name);
        if(pipe!=null){
            pipe.sendMessage(msg);
        }

        
       


    }

   

    private int findTurno(String name){
        Iterator<String> iter=this.playerNames.iterator();
        int index=0;
        boolean found=false;
        while(iter.hasNext()&&!found){
            found=iter.next().equals(name);
            if(!found){
                index++;
            }
        }
        return index;
    }
    public void elaborateRecoveryMessage(Message message){


        //ricevo i dati sull inizializzaione
        
        RecoveryMessage m=new RecoveryMessage(message);
        recoveryListeners.notifyReconnect(m.getParameter());
        this.startMessageWaiter();
        
    }

    

    public boolean isManager() {
        return this.isCentral;
    }


    public boolean isOnline(String player){
        return toPeersPipes.get(player)!=null;
    }

    

    
    

    
   

    

    

   

    

    

    

    

    

    

  

    




    

    public void closePipeFor(int turn,String name) throws IOException{
        try{
             pipeLock.lock();
             JxtaBiDiPipe pipe=this.toPeersPipes.get(name);
             if(pipe!=null){
                 pipe.close();
                 
                 
             }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        finally{
            toPeersPipes.put(name, null);
            pipeLock.unlock();
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

    private void elaboratePongMessage(Message msg) {
        PongMessage m=new PongMessage(msg);

        Iterator<PongListener> listeners=this.pongListeners.iterator();
        while(listeners.hasNext()){
            listeners.next().notifyPong(m);
        }
    }

    public void setGameInProgress(boolean b) {
        this.gameInProgress=b;
    }



   

    private  class  MessageWaiter extends Thread{

        private int sleepTime=45 * 1000 ;
        private int interval=1;

        private AtomicBoolean continueTimer;

        public MessageWaiter(){
            this.continueTimer=new AtomicBoolean(true);
        }

        public void stopTimer(){
            this.continueTimer.set(false);
        }
        
        @Override
        public void run() {
            while(continueTimer.get()){
                try {
                    messageReceived=false;
                    
                    for(int i=0;i<interval;i++){
                        this.sleep(sleepTime);
                    }
                    if(!messageReceived){
                        System.out.println("Nessun messaggio ricevuto dam manager .... riconnessione");
                        boolean connectSuccess=connect();
                        continueTimer.set(connectSuccess);
                        if(connectSuccess){
                            System.out.println("Riconnessione riuscita");
                        }else{
                            System.out.println("rinnessione fallita");
                        }
                    }else{
                        System.out.println("contattato dal coordinatore");
                    }
            
                } catch (Exception ex) {
                   ex.printStackTrace();
                }

            }

            closeVirtualCommunicator();
            try {
                if(!electionManager.isStarted()){
                        electionManager.startElection(false);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }


        }

        private void closeVirtualCommunicator() {
            System.err.println("close Virtual communicator");
        }

        
    }

    
    

}


    
    
    
   



