/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jxta.communication;

import jxta.advertisement.JXTAPipeAdvertisement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import middle.PeerGroup;
import middle.management.advertisement.PipeAdvertisement;
import middle.RisikoPipe;
import net.jxta.util.JxtaBiDiPipe;

/**
 *
 * @author root
 */
public class JXTAVirtualCommunicator extends middle.VirtualCommunicator{
    
    public JXTAVirtualCommunicator(String playername){
        super.playerName=playername;
        super.playerNames=new ArrayList<String>();
        
    }
        

    @Override
    public void initCentralCommunicator(String peerName, PeerGroup group, PipeAdvertisement pipe) {
        
        setPipeLock(new ReentrantLock(true));
        setGameInProgress(false);
        setCentral(true);
        setCurrentPlayerNumber(1);
        setPipes(new HashMap<String, RisikoPipe>());
        try {
            JXTAConnectionHandler handler=(JXTAConnectionHandler) JXTAConnectionHandler.getInstance(group, pipe, 50, 2*60*1000);
            handler.setConnectionListener(super.connectionListener);
            handler.start();
        } catch (IOException ex) {
            System.out.println("Impossibile avviare server");
            System.exit(-1);
        }
        
        
    }
    

    @Override
    public void initPeerComunicator(String peerName, PeerGroup group, PipeAdvertisement centralPeerPipeAdv, PipeAdvertisement peerPipeAdv) {
        
        setPipeLock(new ReentrantLock(true));
        setCentral(false);
        setCentralPeerPipeAdv(centralPeerPipeAdv);
        setGroup(group);
        setPipes(new HashMap<String, RisikoPipe>());
        setManagerUsername(((JXTAPipeAdvertisement)centralPeerPipeAdv).getName());
        try{
            JXTAConnectionHandler handler=(JXTAConnectionHandler) JXTAConnectionHandler.getInstance(group, peerPipeAdv, 50, 2*60*1000);
            handler.setConnectionListener(super.connectionListener);
            handler.start();
        }catch(IOException ex){
            System.out.println("Impossibile avviare server");
            System.exit(-1);
        }
        try {
            connect();
        } catch (IOException ex) {
            Logger.getLogger(JXTAVirtualCommunicator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public boolean restartPeerCommunicator(PipeAdvertisement centralPipe, PipeAdvertisement peerPipe) throws IOException {
        isCentral=false;
        
        try{
            JXTARisikoPipe x=(JXTARisikoPipe) this.pipes.get(this.getManagerUsername());
            if(x!=null){
                x.setRisikoMessageNotifier(null);
                x.close();
            }
        }catch(Exception ex){
            System.out.println("problemi in chiusura vecchia pipe ...");
        }
        
        this.centralPeerPipeAdv=centralPipe;
        
        gameInProgress=true;
        return connect();
    }

    @Override
    public void commuteToCentralCommunicator(PipeAdvertisement pipe, List<String> playerNames, boolean isClose) throws IOException {
        isCentral=true;
        this.centralPeerPipeAdv=pipe;
        
        this.isClose=isClose;
        gameInProgress=true;
        pipes.get(managerUsername).setRisikoMessageNotifier(null);
        pipes.get(managerUsername).close();
        pipes.remove(managerUsername);
        Iterator<String> players=playerNames.iterator();
        while(players.hasNext()){
            pipes.put(players.next(), null);
        }
     
    }

    @Override
    public boolean connect() throws IOException {
        int counter=0;
        int limit=4;
        JxtaBiDiPipe toCentralPeer=new JxtaBiDiPipe();
        
        while((!toCentralPeer.isBound())&&counter<limit){
            try {
                JXTAPeerGroup pG=(JXTAPeerGroup) super.group;
                JXTAPipeAdvertisement pA=(JXTAPipeAdvertisement) super.centralPeerPipeAdv;
                JXTARisikoPipe pipe=new JXTARisikoPipe();
                toCentralPeer = new JxtaBiDiPipe(pG.getPeerGroup(), pA.getPipe(), 25 * 1000, pipe, true);
                pipe.setPipe(toCentralPeer);
                pipe.setRisikoMessageNotifier(this.messageNotifier);
                pipe.setSequencer(messageSequencer);
                super.pipes.put(super.managerUsername, pipe);
            } catch (IOException ex) {
                System.err.println("connection timeout :: impossibile connettersi al amanger");
            }
            
            counter++;
        }

        if(toCentralPeer.isBound()){
            System.err.println("server contattato");
            gameInProgress=true;
            

          //  toCentralPeer.setMessageListener(instance);
//            this.sequencer.setPermitRetrasmissionRequest(false);
           /* Message msg=new WelcomeMessage(playerName);
            sendMessage(msg,false);*/

            return true;
        }
        System.err.println("impossibile contattare server");
        return false;

    }

    
    
}
