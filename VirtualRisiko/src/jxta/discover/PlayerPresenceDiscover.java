/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jxta.discover;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxta.advertisement.JXTADiscoveryResponseMsg;
import jxta.advertisement.JXTAPipeAdvertisement;
import jxta.advertisement.JXTARisikoDiscoveryEvent;
import jxta.advertisement.PlayerAdvertisement;
import net.jxta.protocol.PipeAdvertisement;
import jxta.communication.JXTAPeerGroup;
import middle.PeerGroup;


import net.jxta.discovery.DiscoveryEvent;
import net.jxta.discovery.DiscoveryListener;
import net.jxta.discovery.DiscoveryService;
import net.jxta.document.Advertisement;
import net.jxta.document.AdvertisementFactory;
import net.jxta.id.IDFactory;
import net.jxta.peer.PeerID;
import net.jxta.peergroup.PeerGroupID;
import net.jxta.pipe.PipeID;
import net.jxta.pipe.PipeService;
import net.jxta.protocol.DiscoveryResponseMsg;



/**
 *
 * @author root
 */
public class PlayerPresenceDiscover extends middle.management.discover.PlayerPresenceDiscover implements DiscoveryListener {

    /**
     * The default expiration timeout for published presence advertisements.
     * Set to 1 minute.
     */
    private static final int DEFAULT_EXPIRATION = 1000 * 60 * 120;

    /**
     * The default lifetime for published presence advertisements.
     * Set to 1 minutes.
     */
    private static final int DEFAULT_LIFETIME = 1000 * 60 * 120;


    /**
     * The Discovery service used to publish presence information.
     */
    private DiscoveryService discovery = null;
    
   

     /**
     * The local Peer ID.
     */
    private String localPeerID = null;

    /**
     * The peer group to which the service belongs.
     */
    private PeerGroup peerGroup = null;

    

    private PipeAdvertisement MyPipeAdvertisement;
    private PlayerAdvertisement presenceInfo;

    private PeerID searchedPeer;
    

    


    public void getPlayerAdvertisementUpdated(PeerID peer){
        searchedPeer=peer;
        this.discovery.getRemoteAdvertisements(peer.URIEncodingName, DiscoveryService.ADV, null, null, 10);
        
    }

    public void discoveryEvent(DiscoveryEvent TheDiscoveryEvent) {
       

        // Who triggered the event?
        
        DiscoveryResponseMsg TheDiscoveryResponseMsg = TheDiscoveryEvent.getResponse();

        
        if (TheDiscoveryResponseMsg!=null) {

            Enumeration<Advertisement> TheEnumeration = TheDiscoveryResponseMsg.getAdvertisements();
            Vector<middle.management.advertisement.Advertisement> result=new Vector<middle.management.advertisement.Advertisement>();
            while (TheEnumeration.hasMoreElements()) {

                Advertisement adv=TheEnumeration.nextElement();
                try {   
                    if(adv.getAdvType().equalsIgnoreCase(jxta.advertisement.PlayerAdvertisement.getAdvertisementType())){
                        PlayerAdvertisement advertisement = (PlayerAdvertisement) adv;
                        result.add(advertisement);
                        
                       
                        
                    }else if(adv.getAdvType().equalsIgnoreCase(net.jxta.protocol.PipeAdvertisement.getAdvertisementType())){
                        PipeAdvertisement advertisement = (PipeAdvertisement) adv;
                        JXTAPipeAdvertisement jxtaPipe=new JXTAPipeAdvertisement(advertisement);
                        result.add(jxtaPipe);
                        
                        
                        
                    }

                    

                } catch (ClassCastException Ex) {
                   // Ex.printStackTrace();

                    // We are not dealing with a Peer Advertisement

                }

            }
            
            JXTADiscoveryResponseMsg msg=new JXTADiscoveryResponseMsg(result.elements());
            JXTARisikoDiscoveryEvent event=new JXTARisikoDiscoveryEvent(msg);
            super.discoveryEvent(event);
            
            

        }
        
        

    }

    public PlayerAdvertisement announcePresence(int presenceStatus,String name)
    {
       
        if (discovery != null)
        {
             presenceInfo = (PlayerAdvertisement)
                AdvertisementFactory.newAdvertisement(
                    jxta.advertisement.PlayerAdvertisement.getAdvertisementType());
            
             searchedPeer=null;
            

            // Configure the new advertisement.
            presenceInfo.setPresenceStatus(presenceStatus);
            presenceInfo.setName(name);
            presenceInfo.setPeerID(localPeerID);
            

            try
            {
                // Publish the advertisement locally.
                discovery.publish(presenceInfo, DEFAULT_EXPIRATION, DEFAULT_LIFETIME);
                // Publish the advertisement remotely.
                discovery.remotePublish(presenceInfo,DEFAULT_LIFETIME);

                
                    this.publishPipeAdvertisement(name);
            }
            catch (IOException e)
            {
                System.out.println("Error publishing locally: " + e);
            }

            
            return presenceInfo;
            
        }
        
        return null;
        
    }

    

    public void init(PeerGroup group)   {
        super.init(group);
        System.out.println("init player presence discover");

        // Save a reference to the group of which that this service is
        // a part.
        peerGroup = group;
        JXTAPeerGroup pG=(JXTAPeerGroup) peerGroup;
        // Get the local Peer ID.
        localPeerID = pG.getPeerGroup().getPeerID().toString();

        
       
        
    }

        /**
     * Start the service.
     *
     * @param   args the arguments to the service. Not used.
     * @return  0 to indicate the service started.
     */
    public int startApp(String[] args) throws IOException

    {
        JXTAPeerGroup pG=(JXTAPeerGroup) peerGroup;
        // Now that the service is being started, set the DiscoveryService
        // object to use to publish presence information.
        discovery = pG.getPeerGroup().getDiscoveryService();

        
        discovery.addDiscoveryListener(this);
        return 0;
    }

    public List<middle.management.advertisement.PlayerAdvertisement> searchPlayers(boolean includeRemoteSearch) {
       
        // Add ourselves as a listener.
        

        Enumeration<Advertisement>e;
        try {
            e = discovery.getLocalAdvertisements(DiscoveryService.ADV, null, null);
        } catch (IOException ex) {
            System.out.println("IO Exception in finding player advs");
            return null;
        }
        ArrayList<middle.management.advertisement.PlayerAdvertisement> advs=new ArrayList<middle.management.advertisement.PlayerAdvertisement>();
        while(e.hasMoreElements()){
            Advertisement a=e.nextElement();
            if(a.getAdvType().equals(PlayerAdvertisement.AdvertisementType)){
                advs.add((middle.management.advertisement.PlayerAdvertisement) a);
            }
        }
        
        if(includeRemoteSearch)
            discovery.getRemoteAdvertisements(null, DiscoveryService.ADV,
            null, null, 10, this);

        return advs;
    }

    public List<middle.management.advertisement.PipeAdvertisement> searchPipes(boolean includeRemoteSearch) {
       
        // Add ourselves as a listener.


        Enumeration<Advertisement>e;
        try {
            e = discovery.getLocalAdvertisements(DiscoveryService.ADV, null, null);
        } catch (IOException ex) {
            System.out.println("IO Exception in finding pipe advs");
            return null;
        }
        ArrayList<middle.management.advertisement.PipeAdvertisement> advs=new ArrayList<middle.management.advertisement.PipeAdvertisement>();
        while(e.hasMoreElements()){
            Advertisement a=e.nextElement();
            if(a.getAdvType().equals(PipeAdvertisement.getAdvertisementType())){
                JXTAPipeAdvertisement jxtaA=new JXTAPipeAdvertisement((PipeAdvertisement) a);
                advs.add(jxtaA);
            }
        }

        if(includeRemoteSearch)
            discovery.getRemoteAdvertisements(null, DiscoveryService.ADV,
            null, null, 10, this);

        return advs;
    }

    

    /**
     * Stop the service.
     */
    public void stopApp()
    {
       
        if (discovery != null)
        {
            // Unregister ourselves as a listener.
            discovery.removeDiscoveryListener(this);

            discovery = null;

            // Empty the set of listeners.
            registeredListeners.clear();
            registredPipeListeners.clear();
        }
        
    }

    public void  publishPipeAdvertisement(String name) throws IOException {

        
        // Creating a Pipe Advertisement
        String newName="Player "+name;
        if(MyPipeAdvertisement==null){
            MyPipeAdvertisement = (PipeAdvertisement) AdvertisementFactory.newAdvertisement(PipeAdvertisement.getAdvertisementType());
            PipeID MyPipeID = IDFactory.newPipeID(PeerGroupID.defaultNetPeerGroupID, newName.getBytes());

            MyPipeAdvertisement.setPipeID(MyPipeID);
            MyPipeAdvertisement.setType(PipeService.UnicastType);
            MyPipeAdvertisement.setName(name+" Pipe");
            MyPipeAdvertisement.setDescription("Created by " + name);
        }

        this.discovery.publish(MyPipeAdvertisement,DEFAULT_EXPIRATION, DEFAULT_LIFETIME);
        this.discovery.remotePublish(MyPipeAdvertisement, DEFAULT_LIFETIME);

       

    }

    public middle.management.advertisement.PipeAdvertisement getPipeAdvertisement(){
        return new JXTAPipeAdvertisement(MyPipeAdvertisement);
    }

    @Override
    protected boolean isPresenceAdvertisement(middle.management.advertisement.Advertisement sdv) {
        return sdv.getAdvType().equals(PlayerAdvertisement.getAdvertisementType());
    }

    @Override
    protected boolean isPipeAdvertisement(middle.management.advertisement.Advertisement sdv) {
        return sdv.getAdvType().equals(PipeAdvertisement.getAdvertisementType());
    }
}
