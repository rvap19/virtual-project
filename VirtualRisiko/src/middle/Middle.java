/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle;

import middle.management.advertisement.PipeAdvertisement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import middle.event.RecoveryEvent;
import middle.event.RisikoEvent;
import middle.listener.ApplianceEventListener;
import middle.listener.AttackEventListener;
import middle.listener.ChangeCardEventListener;
import middle.listener.MovementEventListener;
import middle.listener.PassEventListener;
import middle.listener.RisikoEventListener;
import middle.messages.ElectionMessage;
import middle.messages.ElectionRequestMessage;
import middle.messages.RecoveryMessage;
import middle.messages.RisikoMessage;
import middle.messages.WelcomeMessage;

/**
 *
 * @author root
 */
public abstract class Middle implements RisikoMessageListener,ConnectionListener{
    protected boolean permitElection=true;
    protected VirtualCommunicator communicator;
    protected ElectionController electionManager;
    protected MessageSequencer sequencer;
    protected HashMap<String,Collection<RisikoEventListener>> listeners;
    protected List<String> playerNames;
    protected RisikoMessageGenerator messageBuilder; 
    protected RisikoEventGenerator eventGenerator;
    protected String playerName;
    protected boolean gameInProgress;
    protected int playersNumber;
    protected int maxPlayers;
    protected boolean isClose;
    
    protected void init(){
        this.messageBuilder=createMessageBuilder();
        this.eventGenerator=createEventGenerator();
        sequencer=new MessageSequencer(this.playerName,150);
        sequencer.setCurrentMessageID(0);
        sequencer.setNotifier(this);
        sequencer.setEnabled(true);    
        playersNumber=1;
        maxPlayers=Integer.MAX_VALUE;
        isClose=false;
        playerNames=new ArrayList<String>();
        playerNames.add(playerName);
        communicator=this.createVirtualCommunicator();
        communicator.setConnectionListener(this);
        communicator.setElectionManager(electionManager);
        communicator.setMessageNotifier(sequencer);
        communicator.setMessageSequencer(sequencer);
        
        initListeners();
    }

    public ElectionController getElectionManager() {
        return electionManager;
    }

    public void setElectionManager(ElectionController electionManager) {
        this.electionManager = electionManager;
    }

    public boolean isGameInProgress() {
        return gameInProgress;
    }

    public void setGameInProgress(boolean gameInProgress) {
        this.gameInProgress = gameInProgress;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public RisikoMessageGenerator getMessageBuilder() {
        return messageBuilder;
    }

    public void setMessageBuilder(RisikoMessageGenerator messageBuilder) {
        this.messageBuilder = messageBuilder;
    }

    public boolean isPermitElection() {
        return permitElection;
    }

    public void setPermitElection(boolean permitElection) {
        this.permitElection = permitElection;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    public void setPlayersNumber(int playersNumber) {
        this.playersNumber = playersNumber;
    }

    public MessageSequencer getSequencer() {
        return sequencer;
    }

    public void setSequencer(MessageSequencer sequencer) {
        this.sequencer = sequencer;
    }
    
    
    
    public void setGameController(AbstractGameController controller){
        /*
         * ApplianceEventListener,AttackEventListener,MovementEventListener,ChangeCardEventListener,PassEventListener
         */
        this.addListener(EventTypes.APPLIANCE,(ApplianceEventListener)controller);
        this.addListener(EventTypes.ATTACK,(AttackEventListener)controller);
        this.addListener(EventTypes.MOVEMENT,(MovementEventListener)controller);
        this.addListener(EventTypes.CHANGE_CARDS,(ChangeCardEventListener)controller);
        this.addListener(EventTypes.PASS,(PassEventListener)controller);
    }
    public void addListener(String eventName,RisikoEventListener listener){
        
        Collection<RisikoEventListener> currentCollection=this.listeners.get(eventName);
        if((!listeners.containsKey(eventName)) || currentCollection==null ){
            currentCollection=new ArrayList<RisikoEventListener>();
            
        }
        
        currentCollection.add(listener);
        listeners.put(eventName, currentCollection);
    }
    
    public void notifyEvent(RisikoEvent event){
        
        
        
        String type=event.getType();
        Collection<RisikoEventListener> collection=this.listeners.get(type);
        if(collection!=null){
            Iterator<RisikoEventListener> iter=collection.iterator();
            while(iter.hasNext()){
                iter.next().notify(event);
            }
        }
    }

    public VirtualCommunicator getCommunicator() {
        return communicator;
    }

    public List<String> getPlayerNames() {
        return playerNames;
    }
    
    
    
    
    public void notifyConnection(RisikoPipe pipe, RisikoMessage msg) {
         String type=msg.getType();
            if(type.equals(MessageTypes.ELECTION_REQUEST)){
                this.elaborateRequestElectionMessage(pipe,msg);
                
                return;

            }

            if(type.equals(MessageTypes.ELECTION)){
                this.elaborateElectionMessage(pipe,msg);
                
                return;
            }

            if(!this.gameInProgress){
            
                this.elaborateWelcomeMessage(pipe, msg);
            
            }else{
                this.elaborateReconnectRequest(pipe,msg);
            }

    }
    
    public void notifyMessage(RisikoMessage message) {
        if(this.communicator.isCentral){
            this.communicator.propagateMessage(message);
        }
        RisikoEvent e=eventGenerator.generateEvent(message);
        
        notifyEvent(e);
        
        
    }
    
    protected void initListeners(){
        this.listeners=new HashMap<String, Collection<RisikoEventListener>>();
    }
    
    private void elaborateRequestElectionMessage(RisikoPipe pipe, RisikoMessage msg) {
        this.electionManager.notifyRequestElectionMessage(pipe,(ElectionRequestMessage)msg);

    }

    private void elaborateElectionMessage(RisikoPipe pipe, RisikoMessage msg) {
        this.electionManager.notifyElectionMessage(pipe, (ElectionMessage)msg);
    }

    private void elaborateWelcomeMessage(RisikoPipe pipe, RisikoMessage msg) {
        WelcomeMessage m=(WelcomeMessage) msg;
        String newPlayername=msg.playerName();
        
        if((!isClose)){
            this.playersNumber++;
            pipe.setRisikoMessageNotifier(sequencer);
            pipe.setSequencer(sequencer);
            this.communicator.put(m.getWelcomeName(), pipe);  
            this.playerNames.add(newPlayername);
            this.communicator.getPlayerNames().add(newPlayername);
            if(this.playersNumber==this.maxPlayers){
                this.sendInitMessages();
                this.gameInProgress=true;
            }
        }

    }

    private void elaborateReconnectRequest(RisikoPipe pipe, RisikoMessage msg) {
        String name=msg.playerName();
        
        RisikoPipe oldPipe=communicator.get(name);//toPeersPipes.get(name);
        if(oldPipe!=null){
            oldPipe.close();
        }
        communicator.remove(name);
        communicator.put(name, pipe);
        RecoveryEvent event=new RecoveryEvent((RecoveryMessage)msg);
        this.notifyEvent(event);
        
    }
    
    public abstract VirtualCommunicator createVirtualCommunicator();
    public abstract RisikoMessageGenerator createMessageBuilder();
    public abstract RisikoEventGenerator createEventGenerator();
    
    public abstract void configureVirtualCommunicator(String peername,PeerGroup group,PipeAdvertisement managerPipe,PipeAdvertisement peerPipe)throws IOException;
   
    public abstract void  configureVirtualCommunicatorAsCentral(String peerName,PeerGroup group,PipeAdvertisement pipe);
    
    public abstract void  configureVirtualCommunicatorAsPeer(String peerName,PeerGroup group,PipeAdvertisement centralPeerPipeAdv,PipeAdvertisement peerPipeAdv);

    protected abstract void sendInitMessages();

    
    
    
    
    
    
}
