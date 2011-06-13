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
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;


import jxta.listener.ConnectionListener;
import net.jxta.endpoint.Message;
import net.jxta.endpoint.MessageElement;
import net.jxta.endpoint.StringMessageElement;
import net.jxta.peergroup.PeerGroup;
import net.jxta.pipe.PipeMsgEvent;
import net.jxta.pipe.PipeMsgListener;
import net.jxta.protocol.PipeAdvertisement;
import net.jxta.util.JxtaBiDiPipe;
import net.jxta.util.JxtaServerPipe;
import services.RecoveryListener;
import util.RecoveryUtil;
import virtualrisikoii.GameParameter;
import virtualrisikoii.RecoveryParameter;
import virtualrisikoii.listener.ApplianceListener;
import virtualrisikoii.listener.AttackListener;
import virtualrisikoii.listener.ChangeCardListener;
import virtualrisikoii.listener.ChatListener;
import virtualrisikoii.listener.InitListener;
import virtualrisikoii.listener.MovementListener;
import virtualrisikoii.listener.PassListener;
import virtualrisikoii.listener.ReconnectionRequestListener;
import virtualrisikoii.risiko.Tavolo;

/**
 *
 * @author root
 */
public class VirtualCommunicator implements PipeMsgListener,ConnectionListener{

    public static VirtualCommunicator instance;
    
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
    public final String WELCOME="welcome";
    public final String RECOVERY="recovery";




    private List<ApplianceListener> applianceListeners;
    private List<AttackListener> attackListeners;
    private List<ChangeCardListener> changeCardsListeners;
    private List<ChatListener> chatListeners;
    private List<InitListener> initListeners;
    private List<MovementListener> movementListeners;
    private List<PassListener> passListeners;
    private RecoveryListener recoveryListeners;
    private ReconnectionRequestListener recoveryRequestListener;
    
    private final  String GAMER="gamer";

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

    private final long GAME_TIMEOUT= 2 * 60 *1000;
    private PeerGroup peerGroup;

    private VirtualCommunicator(){
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
        instance.toCentralPeer=	new JxtaBiDiPipe(group,pipe,30*1000, instance, true);
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
        while((!toCentralPeer.isBound())&&counter<limit){
            toCentralPeer=new JxtaBiDiPipe(peerGroup,centralPeerPipeAdv,12*1000, this, true);
            counter++;
        }

        if(toCentralPeer.isBound()){
            System.err.println("server contattato");
            toCentralPeer.setMessageListener(instance);
            Message msg=createWelcomeMessage();
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
        StringMessageElement mElement=new StringMessageElement(ID_MSG, Integer.toString(msgID), null);
        message.addMessageElement(namespace, mElement);
       

        if(!this.isCentral){
            try {
                return this.toCentralPeer.sendMessage(message);
            } catch (Exception ex) {
                ex.printStackTrace();
                this.toCentralPeer=null;
                try {
                    this.connect();
                } catch (IOException ex1) {
                    System.err.println("impossibile accedere al nodo centrale");
                    System.exit(-1);
                }
                return false;
            }
        }
        boolean result=true;

        Iterator<JxtaBiDiPipe> iterPipe=this.toPeersPipes.values().iterator();
        while(iterPipe.hasNext()){
            JxtaBiDiPipe pipe=iterPipe.next();
            try {
                result = result && pipe.sendMessage(message);
            } catch (Exception ex) {
                System.err.println("pipe malfunzionante");
            }
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
         mElement=new StringMessageElement(ID_MSG, Integer.toString(0), null);
            message.addMessageElement(namespace, mElement);
             mElement=new StringMessageElement(GAMER, playerName, null);
            message.addMessageElement(namespace, mElement);
        return message;

    }



    public boolean sendInitMessages() throws IOException{
        Message msg;
        boolean gine=true;
        if(this.gameInProgress){
            return false;
        }
        int turn=1;

        Iterator<String> iter=toPeersPipes.keySet().iterator();
        while(iter.hasNext()){
            msg=createInitMessage(this.currentPlayerNumber, gameParameter.getSeed_dice(), gameParameter.getMapName(), gameParameter.getSeed_cards(), gameParameter.getSeed_region());
            StringMessageElement mE=new StringMessageElement(InitMessageAttributes.TURN, Integer.toString(turn), null);
             
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
        Iterator<InitListener> listeners=this.initListeners.iterator();
        while(listeners.hasNext()){
            listeners.next().init(myTurno,players,seed_dice, map_name, seed_card, seed_region);
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

    private Message createWelcomeMessage(){
       Message message=new Message();
        StringMessageElement mE=new StringMessageElement(type, WELCOME, null);
        message.addMessageElement(namespace, mE);

        StringMessageElement mElement = new StringMessageElement(WelcolmeAttributes.PEER_NAME,this.playerName, null);
        message.addMessageElement(namespace, mElement);
        return message;
    }

    private void elaborateWelcomeMessage(JxtaBiDiPipe pipe,Message msg) throws IOException{
        String name=msg.getMessageElement(namespace, WelcolmeAttributes.PEER_NAME).toString();
        if((!isClose)||(this.toPeersPipes.containsKey(name))){
            this.currentPlayerNumber++;
            this.toPeersPipes.put(name, pipe);
            pipe.setMessageListener(instance);
            if(this.currentPlayerNumber==this.maxPlayers){
                this.sendInitMessages();
                this.gameInProgress=true;
            }
        }
        
    }

    private void elaborateReconnectRequest(JxtaBiDiPipe pipe,Message msg){
        String name=msg.getMessageElement(namespace, WelcolmeAttributes.PEER_NAME).toString();
        int turn=this.findTurno(name);
        this.toPeersPipes.put(name, pipe);
        pipe.setMessageListener(this);
        this.recoveryRequestListener.notifyReconnectionRequest(turn);

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
            System.err.println("id msg non riconisciuto");
       }




     if(this.isCentral){
            this.sendMessage(msg,msgID);
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
       }else if(messageType.equals(RECOVERY)){
           this.elaborateRecoveryMessage(msg);
       }else if(messageType.equals(CHAT)){
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
        Message msg=createRecoveryMessage(parameter);
        Iterator<String> iter=toPeersPipes.keySet().iterator();
        int index=0;
        String name=null;
        while(index<player && iter.hasNext()){
            name=iter.next();
            index++;
        }
        StringMessageElement mElement=new StringMessageElement(GAMER,playerName , null);
        msg.addMessageElement(namespace, mElement);

         mElement=new StringMessageElement(ID_MSG,"0" , null);
        msg.addMessageElement(namespace, mElement);

        toPeersPipes.get(name).sendMessage(msg);

        
       


    }

    private Message createRecoveryMessage(RecoveryParameter parameter){
        Message message=new Message();
        StringMessageElement mE=new StringMessageElement(type, RECOVERY, null);
        message.addMessageElement(namespace, mE);
        message=addMapNameElement(message, parameter.getMapName());

        //appendo i dati sull inizializzaione
        message=addInitElement(message, parameter.isInizializzazione());
        //appendo i dati sul territorio
        message=addTerritoriOccupanteElement(message, parameter.getIdOccupante());
        message=addTerritoriNumeroTruppeElement(message, parameter.getNumeroTruppe());
        message=addRegionsNumberElement(message, parameter.getIdOccupante().length);
        //appendo i dati sugli obiettivi
        message=addObjectiveElement(message, parameter.getObjectives());
        //appendo i dati sul turno
        message=addTurnElement(message, parameter.getTurno());
        //appendo i dati sul num giocatori
        message=addPlayersNumberElement(message, parameter.getNumeroGiocatori());
        //appendo i dati sulseed dadi
        message=addSeedDiceElement(message, parameter.getSeed_dice());
        //appendo i dati sul seed cards
        message=addSeedCardsElement(message, parameter.getSeed_card());

        //appendo i dati sul numero di volte che i dadi sono stati lanciati

        message=addDiceLanchElement(message, parameter.getDice_lanch());

        //appendo dati lancio carte
        message=addCardLanchElement(message, parameter.getCards_lanch());

        //appendoi dati sulle armate
        message=addArmateElement(message, parameter.getArmateDisponibili());

        message=addTurnoMyGiocatoreElement(message, parameter.getTurnoMyGiocatore());

        return message;
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
        boolean inizializzazione=elaborateInitElement(message);

        //ricevo i dati sul territorio
        int regions=elaborateRegionsNumberElement(message);
        int[] idOccupante=elaborateTerritoriOccupanteElements(message, regions);
        int[] troopsNumber=elaborateTerritoriNumeroTruppeElements(message, regions);

         //ricevo i dati sul num giocatori
        int numeroGiocatori=elaboratePlayerNumberElement(message);

        //ricevo i dati sugli obiettivi
        int[] objectives=elaborateObjectiveElements(message, numeroGiocatori);
        
        
        //ricevo i dati sul turno
        int turno=elaborateTurnElement(message);
       
        //ricevo i dati sulseed dadi
        int seed_dice=elaborateSeedDiceElement(message);
        
        //ricevo i dati sul seed cards
        int seed_card=elaborateSeedCardElement(message);

        //ricevo i dati sul numero di volte che i dadi sono stati lanciati
        int dice_lanch=elaborateDiceLanchElement(message);

        //appendo dati lancio carte
        int card_lanch=elaborateCardLanchElement(message);

        //appendoi dati sulle armate
        int[] num_armate=elaborateArmateElements(message, numeroGiocatori);

        int turnoMyGiocatore=elaborateTurnoMyGiocatoreElement(message);

        String mapName=elaborateMapNameElement(message);

        RecoveryParameter parameter=new RecoveryParameter(mapName, inizializzazione, idOccupante, troopsNumber, objectives, num_armate, turno, numeroGiocatori, seed_card, seed_dice, dice_lanch, card_lanch);
        parameter.setTurnoMyGiocatore(turnoMyGiocatore);
        
            recoveryListeners.notifyReconnect(parameter);
        
    }

    private Message addInitElement(Message message,boolean inizializzazione){

        String info;
        StringMessageElement mE;

            info=Boolean.toString(inizializzazione);
            mE=new StringMessageElement(RecoveryMessageAttributes.INIT_INFO,info , null);
            message.addMessageElement(namespace, mE);

        return message;

    }

    private boolean elaborateInitElement(Message message){

        boolean  inizializzazione=Boolean.parseBoolean(message.getMessageElement(namespace, RecoveryMessageAttributes.INIT_INFO).toString());
        return inizializzazione;
    }

    private Message addMapNameElement(Message message,String mapName){

        String info=mapName;
        StringMessageElement mE;

            
            mE=new StringMessageElement(RecoveryMessageAttributes.MAP_NAME,info , null);
            message.addMessageElement(namespace, mE);

        return message;

    }

    private String elaborateMapNameElement(Message message){

        String  mapName=message.getMessageElement(namespace, RecoveryMessageAttributes.MAP_NAME).toString();
        return mapName;
    }

    private Message addRegionsNumberElement(Message message,int regions){

        String info;
        StringMessageElement mE;

            info=Integer.toString(regions);
            mE=new StringMessageElement(RecoveryMessageAttributes.REGION_NUMBER,info , null);
            message.addMessageElement(namespace, mE);

        return message;

    }

    private int elaborateRegionsNumberElement(Message message){

        int  regionsNumber=Integer.parseInt(message.getMessageElement(namespace, RecoveryMessageAttributes.REGION_NUMBER).toString());
        return regionsNumber;
    }


    private Message addTerritoriOccupanteElement(Message message,int[] idOccupante){
        int size=idOccupante.length;
        String info;
        StringMessageElement mE;
        for(int i=0;i<size;i++){
            info=Integer.toString(idOccupante[i]);
            mE=new StringMessageElement(RecoveryMessageAttributes.REGION_OCCUPANTE_INFO,info , null);
            message.addMessageElement(namespace, mE);     
        }
        return message;
    }

    private int[] elaborateTerritoriOccupanteElements(Message message,int regions){
        int[] occupante=new int[regions];
        Message.ElementIterator iter=message.getMessageElements(RecoveryMessageAttributes.REGION_OCCUPANTE_INFO);
        int counter=0;
        while(iter.hasNext()){
            occupante[counter]=Integer.parseInt(iter.next().toString());
            counter++;
        }
        return occupante;
    }

    private Message addTerritoriNumeroTruppeElement(Message message,int[] numeroTruppe){
        int size=numeroTruppe.length;
        String info;
        StringMessageElement mE;
        for(int i=0;i<size;i++){
            info=Integer.toString(numeroTruppe[i]);
            mE=new StringMessageElement(RecoveryMessageAttributes.REGION_TROOPS_NUMBER_INFO,info , null);
            message.addMessageElement(namespace, mE);
        }
        return message;
    }

    private int[] elaborateTerritoriNumeroTruppeElements(Message message,int regions){
        int[] troopsNumber=new int[regions];
        Message.ElementIterator iter=message.getMessageElements(RecoveryMessageAttributes.REGION_TROOPS_NUMBER_INFO);
        int counter=0;
        while(iter.hasNext()){
            troopsNumber[counter]=Integer.parseInt(iter.next().toString());
            counter++;
        }
        return troopsNumber;
    }

    private Message addObjectiveElement(Message message,int[] objective){
        int size=objective.length;
        String info;
        StringMessageElement mE;
        for(int i=0;i<size;i++){
            info=Integer.toString(objective[i]);
            mE=new StringMessageElement(RecoveryMessageAttributes.OBJECTIVE_INFO,info , null);
            message.addMessageElement(namespace, mE);
        }
        return message;

    }

    private int[] elaborateObjectiveElements(Message message,int players){
        int[] objectives=new int[players];
        Message.ElementIterator iter=message.getMessageElements(RecoveryMessageAttributes.OBJECTIVE_INFO);
        int counter=0;
        while(iter.hasNext()){
            objectives[counter]=Integer.parseInt(iter.next().toString());
            counter++;
        }
        return objectives;
    }

    private Message addTurnElement(Message message,int turno){

        String info;
        StringMessageElement mE;

            info=Integer.toString(turno);
            mE=new StringMessageElement(RecoveryMessageAttributes.TURN_INFO,info , null);
            message.addMessageElement(namespace, mE);

        return message;

    }

    public int elaborateTurnElement(Message message){
        int turn=Integer.parseInt(message.getMessageElement(namespace, RecoveryMessageAttributes.TURN_INFO).toString());
        return turn;
    }

    private Message addPlayersNumberElement(Message message,int numeroGiocatori){

        String info;
        StringMessageElement mE;

            info=Integer.toString(numeroGiocatori);
            mE=new StringMessageElement(RecoveryMessageAttributes.PLAYERS_INFO,info , null);
            message.addMessageElement(namespace, mE);

        return message;

    }

    public int elaboratePlayerNumberElement(Message message){
        int players=Integer.parseInt(message.getMessageElement(namespace, RecoveryMessageAttributes.PLAYERS_INFO).toString());
        return players;
    }

    private Message addSeedDiceElement(Message message,int seed_dice){

        String info;
        StringMessageElement mE;

            info=Integer.toString(seed_dice);
            mE=new StringMessageElement(RecoveryMessageAttributes.SEED_DICE,info , null);
            message.addMessageElement(namespace, mE);

        return message;

    }

    public int elaborateSeedDiceElement(Message message){
        int seed_dice=Integer.parseInt(message.getMessageElement(namespace, RecoveryMessageAttributes.SEED_DICE).toString());
        return seed_dice;
    }

    private Message addSeedCardsElement(Message message,int seed_cards){

        String info;
        StringMessageElement mE;

            info=Integer.toString(seed_cards);
            mE=new StringMessageElement(RecoveryMessageAttributes.SEED_CARDS,info , null);
            message.addMessageElement(namespace, mE);

        return message;

    }

    public int elaborateSeedCardElement(Message message){
        int seed_cards=Integer.parseInt(message.getMessageElement(namespace, RecoveryMessageAttributes.SEED_CARDS).toString());
        return seed_cards;
    }

    private Message addDiceLanchElement(Message message,int dice_lanches){

        String info;
        StringMessageElement mE;

            info=Integer.toString(dice_lanches);
            mE=new StringMessageElement(RecoveryMessageAttributes.DICE_LANCH,info , null);
            message.addMessageElement(namespace, mE);

        return message;

    }

    public int elaborateDiceLanchElement(Message message){
        int dice_lanch=Integer.parseInt(message.getMessageElement(namespace, RecoveryMessageAttributes.DICE_LANCH).toString());
        return dice_lanch;
    }

    private Message addCardLanchElement(Message message,int card_lanches){

        String info;
        StringMessageElement mE;

            info=Integer.toString(card_lanches);
            mE=new StringMessageElement(RecoveryMessageAttributes.CARDS_LANCH,info , null);
            message.addMessageElement(namespace, mE);

        return message;

    }

    public int elaborateCardLanchElement(Message message){
        int card_lanch=Integer.parseInt(message.getMessageElement(namespace, RecoveryMessageAttributes.CARDS_LANCH).toString());
        return card_lanch;
    }

    public Message addTurnoMyGiocatoreElement(Message message,int myTurno){
         String info;
        StringMessageElement mE;

            info=Integer.toString(myTurno);
            mE=new StringMessageElement(RecoveryMessageAttributes.MY_TURNO,info , null);
            message.addMessageElement(namespace, mE);

        return message;
    }

    public int elaborateTurnoMyGiocatoreElement(Message message){
        int turno=Integer.parseInt(message.getMessageElement(namespace, RecoveryMessageAttributes.MY_TURNO).toString());
        return turno;
    }

    private Message addArmateElement(Message message,int[] num_armate){
        int size=num_armate.length;
        String info;
        StringMessageElement mE;
        for(int i=0;i<size;i++){
            info=Integer.toString(num_armate[i]);
            mE=new StringMessageElement(RecoveryMessageAttributes.ARMATE,info , null);
            message.addMessageElement(namespace, mE);
        }
        return message;

    }
    
    private int[] elaborateArmateElements(Message message,int players){
        int[] armate=new int[players];
        Message.ElementIterator iter=message.getMessageElements(RecoveryMessageAttributes.ARMATE);
        int counter=0;
        while(iter.hasNext()){
            armate[counter]=Integer.parseInt(iter.next().toString());
            counter++;
        }
        return armate;
    }



    
    

    public class RecoveryMessageAttributes{
        public static final String OBJECTIVE_INFO="objective_info";
        public static final String TURN_INFO="turn_info";
        public static final String PLAYERS_INFO="players_info";
        public static final String SEED_DICE="seed_dice";
        public static final String SEED_CARDS="seed_card";
        public static final String DICE_LANCH="dice_lanch";
        public static final String CARDS_LANCH="cards_lanch";
        public static final String ARMATE="armate_lanch";
        public static final String INIT_INFO="inizializzazione";
        public static final String REGION_OCCUPANTE_INFO="occupante";
        public static final String REGION_TROOPS_NUMBER_INFO="troops_number";
        public static final String REGION_NUMBER="regions";
        public static final String MAP_NAME="map_name";
        public static final String MY_TURNO="my_turno";
    }
   

    public class InitMessageAttributes{
        public static final String SEED_DICE="seed_dice";
        public static final String MAP_NAME="map_name";
        public static final String SEED_CARDS="seed_cards";
        public static final String SEED_REGION="seed_region";
        public static final String PLAYERS="players_number";
        public static final String TURN="myTurn";
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

    public class WelcolmeAttributes{
        public static final String PEER_NAME="peer_name";
    }



    private class TimeOutToCentralPeerTask extends TimerTask{

        public TimeOutToCentralPeerTask(){
            super();
        }
        
        @Override
        public void run() {
            try {
                instance.connect();
            } catch (IOException ex) {
                System.err.println("io exception in connection to central peer ");
            }
        }

    }
    
    
   


}
