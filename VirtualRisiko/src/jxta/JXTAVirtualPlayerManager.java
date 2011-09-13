/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jxta;

import java.util.HashMap;
import middle.ElectionController;
import middle.Middle;
import middle.PeerGroup;
import middle.RisikoMessageGenerator;
import middle.VirtualPlayerManager;
import middle.management.advertisement.PipeAdvertisement;

/**
 *
 * @author root
 */
public class JXTAVirtualPlayerManager extends VirtualPlayerManager{
    private int tcpPort;

    public JXTAVirtualPlayerManager(){
        
    }

    public JXTAVirtualPlayerManager(String name,int port) {
        myName=name;
        tcpPort=port;
        manager=new PlayerManager(name,port);
        myName=name;
        
        
             
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
        PlayerManager m=new PlayerManager(myName, tcpPort);
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

    



  

}
