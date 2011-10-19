/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jxta;

import java.io.IOException;
import jxta.communication.JXTAVirtualCommunicator;
import middle.ElectionController;
import middle.EventTypes;
import middle.Middle;
import middle.PeerGroup;
import middle.RisikoEventGenerator;
import middle.RisikoMessageGenerator;
import middle.VirtualCommunicator;
import middle.event.PingEvent;
import middle.event.RisikoEvent;
import middle.listener.PingEventListener;
import middle.management.advertisement.PipeAdvertisement;
import middle.messages.PongMessage;
import middle.messages.RisikoMessage;

/**
 *
 * @author root
 */
public class JXTAMiddle extends Middle implements PingEventListener{
    
    
    
    public JXTAMiddle(String name){
        super.playerName=name;
        
        init();
        super.addListener(EventTypes.PING, this);
        Middle.instance=this;
    }

    

    
    
    @Override
    public void configureVirtualCommunicator(String peername, PeerGroup group, PipeAdvertisement managerPipe, PipeAdvertisement peerPipe) throws IOException {
        if((managerPipe==null || managerPipe==peerPipe)&&(peerPipe!=null)){
            configureVirtualCommunicatorAsPeer(peername, group, managerPipe, peerPipe);
        }else{
            configureVirtualCommunicatorAsCentral(peername, group, peerPipe);
        }
        
    }

    @Override
    public void configureVirtualCommunicatorAsCentral(String peerName, PeerGroup group, PipeAdvertisement pipe) {
      //  super.communicator=new JXTAVirtualCommunicator(peerName);
        communicator.initCentralCommunicator(peerName, group, pipe);
        
    }

    @Override
    public void configureVirtualCommunicatorAsPeer(String peername, PeerGroup group, PipeAdvertisement centralPeerPipeAdv, PipeAdvertisement peerPipeAdv) {
     //   super.communicator=new JXTAVirtualCommunicator(peername);
        communicator.initPeerComunicator(peername, group, centralPeerPipeAdv, peerPipeAdv);
        
    }

    

    

    @Override
    protected void sendInitMessages() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public VirtualCommunicator createVirtualCommunicator() {
        return new JXTAVirtualCommunicator(playerName);
        
    }

    @Override
    public RisikoMessageGenerator createMessageBuilder() {
        return new JXTARisikoMessageGenerator(playerName);
    }

    @Override
    public RisikoEventGenerator createEventGenerator() {
        return new JXTARisikoEventGenerator();
    }

    public void notify(PingEvent c) {
        PongMessage message=super.messageBuilder.generatePongMSG(playerName);
        super.communicator.sendMessage(message);
    }

    public void notify(RisikoEvent event) {
        notify((PingEvent)event);
    }


    

    
    
}
