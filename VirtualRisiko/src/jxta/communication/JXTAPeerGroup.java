/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jxta.communication;

import middle.PeerGroup;

/**
 *
 * @author root
 */
public class JXTAPeerGroup implements PeerGroup{
    
    private  net.jxta.peergroup.PeerGroup peerGroup;
    public JXTAPeerGroup(net.jxta.peergroup.PeerGroup pG){
        this.peerGroup=pG;
    }

    public net.jxta.peergroup.PeerGroup getPeerGroup() {
        return peerGroup;
    }

    public void setPeerGroup(net.jxta.peergroup.PeerGroup peerGroup) {
        this.peerGroup = peerGroup;
    }
    
    
    
}
