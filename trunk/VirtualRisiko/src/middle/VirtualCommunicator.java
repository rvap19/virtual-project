/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package middle;

import java.util.Set;
import middle.management.advertisement.PipeAdvertisement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import javax.sound.midi.Sequencer;
import middle.event.RisikoEvent;
import middle.messages.RisikoMessage;





import util.GameParameter;



/**
 *
 * @author root
 */
public abstract class VirtualCommunicator {

    
    protected RisikoMessageListener messageNotifier;
    protected ConnectionListener connectionListener;
    
    protected Lock pipeLock;

    protected int maxPlayers;

    protected boolean isCentral;
    protected String playerName;

   // private List<String> playerNames;
    protected boolean isClose;
    protected int currentPlayerNumber;
    protected boolean gameInProgress;

    protected GameParameter gameParameter;
    
    protected boolean messageReceived;
    protected MessageWaiter waiter=null;
    protected ElectionController electionManager;
    
    protected HashMap<String,RisikoPipe> pipes;
    
    protected List<String> playerNames;
    protected String managerUsername;
    
    protected PeerGroup group;
    protected PipeAdvertisement centralPeerPipeAdv;
    protected MessageSequencer messageSequencer;

    public MessageSequencer getMessageSequencer() {
        return messageSequencer;
    }

    public void setMessageSequencer(MessageSequencer messageSequencer) {
        this.messageSequencer = messageSequencer;
    }
    
    

    public ElectionController getElectionManager() {
        return electionManager;
    }

    public void setElectionManager(ElectionController electionManager) {
        this.electionManager = electionManager;
    }

    public GameParameter getGameParameter() {
        return gameParameter;
    }

    public void setGameParameter(GameParameter gameParameter) {
        this.gameParameter = gameParameter;
    }

    public boolean isIsCentral() {
        return isCentral;
    }

    public void setIsCentral(boolean isCentral) {
        this.isCentral = isCentral;
    }

    public boolean isIsClose() {
        return isClose;
    }

    public void setIsClose(boolean isClose) {
        this.isClose = isClose;
    }

    public String getManagerUsername() {
        return managerUsername;
    }

    public void setManagerUsername(String managerUsername) {
        this.managerUsername = managerUsername;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public boolean isMessageReceived() {
        return messageReceived;
    }

    public void setMessageReceived(boolean messageReceived) {
        this.messageReceived = messageReceived;
    }

    public Lock getPipeLock() {
        return pipeLock;
    }

    public void setPipeLock(Lock pipeLock) {
        this.pipeLock = pipeLock;
    }

    public HashMap<String, RisikoPipe> getPipes() {
        return pipes;
    }

    public void setPipes(HashMap<String, RisikoPipe> pipes) {
        this.pipes = pipes;
    }

    public RisikoMessageListener getMessageNotifier() {
        return messageNotifier;
    }

    public void setMessageNotifier(RisikoMessageListener messageNotifier) {
        this.messageNotifier = messageNotifier;
    }

    public int size() {
        return pipes.size();
    }

    public RisikoPipe remove(Object key) {
        return pipes.remove(key);
    }

    public RisikoPipe put(String key, RisikoPipe value) {
        return pipes.put(key, value);
    }

    public Set<String> keySet() {
        return pipes.keySet();
    }

    public RisikoPipe get(Object key) {
        return pipes.get(key);
    }

    

    

    public MessageWaiter getWaiter() {
        return waiter;
    }

    public void setWaiter(MessageWaiter waiter) {
        this.waiter = waiter;
    }

    public void setCurrentPlayerNumber(int currentPlayerNumber) {
        this.currentPlayerNumber = currentPlayerNumber;
    }

    public void setPlayerNames(List<String> playerNames) {
        this.playerNames = playerNames;
    }

    public PipeAdvertisement getCentralPeerPipeAdv() {
        return centralPeerPipeAdv;
    }

    public void setCentralPeerPipeAdv(PipeAdvertisement centralPeerPipeAdv) {
        this.centralPeerPipeAdv = centralPeerPipeAdv;
    }

    public PeerGroup getGroup() {
        return group;
    }

    public void setGroup(PeerGroup group) {
        this.group = group;
    }
    
    

    
    
    public abstract void  initCentralCommunicator(String peerName,PeerGroup group,PipeAdvertisement pipe);
    
    public abstract void  initPeerComunicator(String peerName,PeerGroup group,PipeAdvertisement centralPeerPipeAdv,PipeAdvertisement peerPipeAdv) ;
    
    public abstract boolean restartPeerCommunicator(PipeAdvertisement centralPipe,PipeAdvertisement peerPipe) throws IOException;
    

    public abstract void commuteToCentralCommunicator(PipeAdvertisement pipe,List<String> playerNames,boolean isClose) throws IOException;

    public boolean isManagerOnLine() {
        return messageReceived;
    }


    
    public abstract   boolean connect() throws IOException ;

    public ConnectionListener getConnectionListener() {
        return connectionListener;
    }

    public void setConnectionListener(ConnectionListener connectionListener) {
        this.connectionListener = connectionListener;
    }


    


    public void setPlayerName(String player){
        this.playerName=player;
    }
   
    public int getCurrentPlayerNumber() {
        return currentPlayerNumber;
    }

    public   boolean sendMessageTo(RisikoMessage message,String to) throws IOException{
        RisikoPipe x=this.pipes.get(to);
        return x.sendMessage(message);
    }
    
    public boolean sendMessage(RisikoMessage message) {
        String type=message.getType();
        boolean result=false;
        
        
        if(!this.isCentral){
            try {
                
                result= sendMessageTo(message, managerUsername);
                
            } catch (Exception ex) {
                System.err.println("Impossibile inviare messaggio al coordinatore");
                result=false;
                
            }
            
        }else{
             result=true;

            try{

                Iterator<String> users=this.playerNames.iterator();
                while(users.hasNext()){

                            result = result && sendMessageTo(message,users.next());
                }
            } catch (IOException ex) {
                System.err.println("impossibile inviare msg");
                result=false;
            }
        }
        
        if((!type.equals(MessageTypes.CHAT)) && (!type.equals(MessageTypes.PING)) && (!type.equals(MessageTypes.PONG)) && (!type.equals(MessageTypes.STATUS_PEER))&& (!type.equals(MessageTypes.ACK)) && (!type.equals(MessageTypes.RECOVERY)) && (!type.equals(MessageTypes.RETRASMISSION_REQUEST))){
            this.messageSequencer.getAndIncrementID();
        }
        
        
        return result;
    }

    
    public void setGameParameter(GameParameter par,boolean isClosed,List<String> playerNames,int maxPlayers){
        this.gameParameter=par;
        this.isClose=isClosed;
        if(isClosed){
           this.playerNames=playerNames;
        } 
        this.maxPlayers=maxPlayers;
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

   public void notifyMessage(RisikoMessage msg,int msgID){
        
        
         messageReceived=true;
      
         if(this.isCentral){
                this.sendMessage(msg);

         }

    }

    

    
    public boolean isManager() {
        return this.isCentral;
    }


    
    public void closePipeFor(int turn,String name) throws IOException{
        try{
             pipeLock.lock();
             RisikoPipe pipe=this.pipes.get(name);
             if(pipe!=null){
                 pipe.close();
                 
                 
             }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        finally{
            pipes.put(name, null);
            pipeLock.unlock();
        }
    }

    public List<String> getPlayerNames() {
        return playerNames;
    }

   

    public boolean isGameInProgress() {
        return gameInProgress;
    }

    

    public void setNames(List<String> names){
        for(int i=1;i<names.size();i++){
           if(!names.get(i).equals(playerName)){
                this.pipes.put(names.get(i), null);
            }

        }
    }

    

    public void setGameInProgress(boolean b) {
        this.gameInProgress=b;
    }

    public void propagateMessage(RisikoMessage message) {
        try{

            Iterator<String> users=this.playerNames.iterator();
            while(users.hasNext()){
                sendMessageTo(message,users.next());
            }
        } catch (IOException ex) {
            System.err.println("impossibile propagare msg");
            
        }
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
                        System.out.println("Nessun messaggio ricevuto dal manager .... riconnessione");
                        boolean connectSuccess=connect();
                        continueTimer.set(connectSuccess);
                        if(connectSuccess){
                            System.out.println("Riconnessione riuscita");
                        }else{
                            System.out.println("rinnessione fallita");
                        }
                    }else{
                        System.out.println("ricevuto dal coordinatore");
                    }
            
                } catch (Exception ex) {
                   ex.printStackTrace();
                }

            }

            closeVirtualCommunicator();
            try {
                if(!electionManager.isStarted()){
                        electionManager.startElection();
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


    
    
    
   



