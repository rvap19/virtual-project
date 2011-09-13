/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jxta;

import java.util.HashMap;
import jxta.communication.JXTAPeerGroup;
import jxta.advertisement.JXTAPipeAdvertisement;
import jxta.communication.JXTARisikoPipe;
import middle.PeerGroup;
import middle.RisikoMessageGenerator;
import middle.RisikoMessageListener;
import middle.RisikoPipe;
import middle.event.ElectionEvent;
import middle.event.RisikoEvent;
import middle.management.advertisement.PipeAdvertisement;
import net.jxta.util.JxtaBiDiPipe;

/**
 *
 * @author root
 */
public class ElectionController extends middle.ElectionController implements RisikoMessageListener{
    
    public ElectionController(String playerName,PeerGroup group,HashMap<String,PipeAdvertisement> advertisements){
        super(playerName, group, advertisements);
    }
    


     protected  RisikoPipe connect(String player,PipeAdvertisement adv) {
        int counter=0;
        int limit=1;
        JXTARisikoPipe risikoPipe=new JXTARisikoPipe();
        JxtaBiDiPipe pipe=new JxtaBiDiPipe();
        System.out.println("trying to connect to "+player);
        while((!pipe.isBound())&&counter<limit){
            try {
                JXTAPeerGroup pG=(JXTAPeerGroup) peerGroup;
                JXTAPipeAdvertisement pA=(JXTAPipeAdvertisement)adv;
                pipe = new JxtaBiDiPipe(pG.getPeerGroup(), pA.getPipe(), 25 * 1000, risikoPipe, true);
                
            } catch (Exception ex) {
                System.err.println("impossibie contattare il player "+player);
            }

            counter++;
        }

        if(pipe.isBound()){
            System.err.println("player "+player+" contattato");

            risikoPipe.setPipe(pipe);
            risikoPipe.setRisikoMessageNotifier(this);
            
            return risikoPipe;
        }
        System.err.println("impossibile contattare player "+player);
        return null;
    }

    @Override
    protected RisikoMessageGenerator initMessageBuilder() {
        return new JXTARisikoMessageGenerator(playerName);
    }

    public void notify(ElectionEvent c) {
        ackReceived=true;
        electionListener.notify(c);
    }

    public void notify(RisikoEvent event) {
        ElectionEvent e=(ElectionEvent) event;
        notify(e);
    }



   
}
