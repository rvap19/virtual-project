/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jxta;

import java.io.IOException;
import jxta.communication.JXTAVirtualCommunicator;
import middle.Middle;
import middle.PeerGroup;
import middle.RisikoEventGenerator;
import middle.RisikoMessageGenerator;
import middle.VirtualCommunicator;
import middle.event.RisikoEvent;
import middle.management.advertisement.PipeAdvertisement;
import middle.messages.RisikoMessage;

/**
 *
 * @author root
 */
public class JXTAMiddle extends Middle{
    
    
    
    public JXTAMiddle(String name){
        super.playerName=name;
        
        init();
        
    }

    @Override
    public void init() {
        super.init();
        
        
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

    

    public void notifyMessage(RisikoMessage message) {
        RisikoEvent e=super.eventGenerator.generateEvent(message);
        super.notifyEvent(e);
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

    

    
    
}
