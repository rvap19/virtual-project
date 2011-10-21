/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package remote.impl;

import java.util.HashMap;
import jxta.JXTAMiddle;
import jxta.JXTARisikoMessageGenerator;
import middle.ElectionController;
import middle.Middle;
import middle.PeerGroup;
import middle.RisikoMessageGenerator;
import middle.RisikoRecoveryRequestEventListener;
import middle.management.PlayerManager;
import middle.management.advertisement.PipeAdvertisement;

/**
 *
 * @author root
 */
public class JXTARemoteVirtualPlayerManager extends RemoteVirtualPlayerManager{
    private int tcpPort;
    
    public JXTARemoteVirtualPlayerManager(String name,int port){
        myName=name;
        tcpPort=port;
    }
    @Override
    protected ElectionController initElectionController() {
        ElectionController c=new jxta.ElectionController(myName, super.manager.getPeerGroup(), pipes);
        return c;
    }

    @Override
    protected Middle initMiddleLayer() {
        Middle x=new JXTAMiddle(myName);
        return x;
    }

    @Override 
    protected middle.management.PlayerManager initPlayerManager() {
        PlayerManager m=new jxta.PlayerManager(myName, tcpPort);
        return m;
    }
    
    

    @Override
    protected RisikoMessageGenerator initMessageGenerator() {
        return new JXTARisikoMessageGenerator(myName);
    }

    @Override
    protected ElectionController initElectionController(String myName, PeerGroup peerGroup, HashMap<String, PipeAdvertisement> pipes) {
        return new jxta.ElectionController(myName, peerGroup, pipes);
    }

    @Override
    protected RisikoRecoveryRequestEventListener initRecoveryRequestListener() {
        RisikoRecoveryRequestEventListener listener=new RisikoRecoveryRequestEventListener(middle);
        return listener;
    }
    
}