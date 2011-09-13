/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jxta.discover;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxta.advertisement.GameAdvertisement;
import jxta.advertisement.JXTADiscoveryResponseMsg;
import jxta.advertisement.JXTARisikoDiscoveryEvent;
import jxta.communication.JXTAPeerGroup;
import middle.PeerGroup;
import net.jxta.discovery.DiscoveryEvent;
import net.jxta.discovery.DiscoveryListener;
import net.jxta.discovery.DiscoveryService;
import net.jxta.document.Advertisement;
import net.jxta.document.AdvertisementFactory;
import net.jxta.protocol.DiscoveryResponseMsg;






/**
 *
 * @author root
 */
public class GameDiscover extends middle.management.discover.GameDiscover implements DiscoveryListener {

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

    
  

    public void discoveryEvent(DiscoveryEvent TheDiscoveryEvent) {
       

        // Who triggered the event?
        
        DiscoveryResponseMsg TheDiscoveryResponseMsg = TheDiscoveryEvent.getResponse();

        
        if (TheDiscoveryResponseMsg!=null) {

            Enumeration<Advertisement> TheEnumeration = TheDiscoveryResponseMsg.getAdvertisements();
            Vector<middle.management.advertisement.Advertisement> result=new Vector<middle.management.advertisement.Advertisement>();
            while (TheEnumeration.hasMoreElements()) {

                Advertisement adv=TheEnumeration.nextElement();
                try {   
                    if(adv.getAdvType().equalsIgnoreCase(GameAdvertisement.getAdvertisementType())){
                        GameAdvertisement advertisement = (GameAdvertisement) adv;
                        
                        result.add(advertisement);
                        
                       
                        
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

    public GameAdvertisement announceGame(String creatorName,int maxgame,String name,String mapName)
    {
       
        if (discovery != null)
        {
             GameAdvertisement gameInfo = (GameAdvertisement)
                AdvertisementFactory.newAdvertisement(
                    GameAdvertisement.getAdvertisementType());
            
            
            

            // Configure the new advertisement.
            gameInfo.setMaxPlayer(maxgame);
            gameInfo.setGameName(name);
            gameInfo.setCreatorID(creatorName);
            gameInfo.setGameID(name+Long.toString(System.currentTimeMillis()));
            gameInfo.setMapName(mapName);

            try
            {
                // Publish the advertisement locally.
                discovery.publish(gameInfo, DEFAULT_EXPIRATION, DEFAULT_LIFETIME);
                // Publish the advertisement remotely.
                discovery.remotePublish(gameInfo,DEFAULT_LIFETIME);
                
            }
            catch (IOException e)
            {
                System.out.println("Error publishing locally: " + e);
            }
            
            return gameInfo;
            
        }else{
            
            return null;
        }


        
    }

    public void registerPlayer(middle.management.advertisement.GameAdvertisement g,List<String>playersID) throws IOException{
        GameAdvertisement gameAdv=(GameAdvertisement) g;
        this.discovery.flushAdvertisement(gameAdv);
        gameAdv.setPlayerIds(playersID);
        try
            {
                // Publish the advertisement locally.
                discovery.publish(gameAdv, DEFAULT_EXPIRATION, DEFAULT_LIFETIME);
                // Publish the advertisement remotely.
                discovery.remotePublish(gameAdv,DEFAULT_LIFETIME);

            }
            catch (IOException e)
            {
                System.out.println("Error publishing locally: " + e);
            }
       

    }
    

    public void init(PeerGroup group)   {
        
        super.init(group);
        // Save a reference to the group of which that this service is
        // a part.
        peerGroup = group;
        JXTAPeerGroup pG=(JXTAPeerGroup) peerGroup;

        // Get the local Peer ID.
        localPeerID = pG.getPeerGroup().getPeerID().toString();

        
        
        // Registering our customized advertisement instance
        
        
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

    public List<middle.management.advertisement.GameAdvertisement> searchGames(boolean includeRemote) {
        
        // Add ourselves as a listener.
        

        Enumeration<Advertisement>e;
        try {
            e = discovery.getLocalAdvertisements(DiscoveryService.ADV, null, null);
        } catch (IOException ex) {
            System.out.println("IO Exception in finding game advertisement");
            return null;
        }
        ArrayList<middle.management.advertisement.GameAdvertisement> advs=new ArrayList<middle.management.advertisement.GameAdvertisement>();
        while(e.hasMoreElements()){
            Advertisement a=e.nextElement();
            if(a.getAdvType().equals(GameAdvertisement.AdvertisementType)){
                advs.add((GameAdvertisement) a);
            }
        }
        
        if(includeRemote)
            discovery.getRemoteAdvertisements(null, DiscoveryService.ADV,null, null, 10, this);

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
            
            
        }
        
    }

    @Override
    protected boolean isGameAdvertisement(middle.management.advertisement.Advertisement sdv) {
        return sdv.getAdvType().equals(GameAdvertisement.getAdvertisementType());
    }

    

  

    

  
}
