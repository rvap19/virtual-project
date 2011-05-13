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
import jxta.advertisement.GameAdvertisement;
import jxta.listener.GameListener;
import net.jxta.discovery.DiscoveryEvent;
import net.jxta.discovery.DiscoveryListener;
import net.jxta.discovery.DiscoveryService;
import net.jxta.document.Advertisement;
import net.jxta.document.AdvertisementFactory;
import net.jxta.document.MimeMediaType;
import net.jxta.exception.PeerGroupException;
import net.jxta.id.IDFactory;
import net.jxta.peer.PeerID;
import net.jxta.peergroup.PeerGroup;
import net.jxta.peergroup.PeerGroupID;

import net.jxta.protocol.DiscoveryResponseMsg;


/**
 *
 * @author root
 */
public class GameDiscover implements DiscoveryListener {

    /**
     * The default expiration timeout for published presence advertisements.
     * Set to 1 minute.
     */
    private static final int DEFAULT_EXPIRATION = 1000 * 60 * 1;

    /**
     * The default lifetime for published presence advertisements.
     * Set to 1 minutes.
     */
    private static final int DEFAULT_LIFETIME = 1000 * 60 * 5;


    /**
     * The Discovery service used to publish presence information.
     */
    private DiscoveryService discovery = null;
    
    private List<GameListener> registeredListeners;
    

     /**
     * The local Peer ID.
     */
    private String localPeerID = null;

    /**
     * The peer group to which the service belongs.
     */
    private PeerGroup peerGroup = null;

    

    
    private GameAdvertisement gameInfo;

    private PeerID searchedPeer;
    

    public  void addGameListener(GameListener listener)
    {
        registeredListeners.add(listener);
    }

    public void removeGameListener(GameListener listener){
        registeredListeners.remove(listener);
    }

  

    public void discoveryEvent(DiscoveryEvent TheDiscoveryEvent) {
        System.out.println("game remote discovery event....");

        // Who triggered the event?
        
        DiscoveryResponseMsg TheDiscoveryResponseMsg = TheDiscoveryEvent.getResponse();

        
        if (TheDiscoveryResponseMsg!=null) {

            Enumeration<Advertisement> TheEnumeration = TheDiscoveryResponseMsg.getAdvertisements();

            while (TheEnumeration.hasMoreElements()) {

                Advertisement adv=TheEnumeration.nextElement();
                try {   
                    if(adv.getAdvType().equalsIgnoreCase(GameAdvertisement.getAdvertisementType())){
                        GameAdvertisement advertisement = (GameAdvertisement) adv;
                        
                        Iterator<GameListener> listeners = registeredListeners.iterator();
                        while (listeners.hasNext())
                        {
                            GameListener listener = listeners.next();

                            // Notify the listener of the presence update.
                            listener.gameUpdated(advertisement);
                        }
                       
                        
                    }

                    

                } catch (ClassCastException Ex) {
                   // Ex.printStackTrace();

                    // We are not dealing with a Peer Advertisement

                }

            }

        }
        
        

    }

    public void announceGame(int maxgame,String name)
    {
        System.out.println("game presence announcing");
        if (discovery != null)
        {
             gameInfo = (GameAdvertisement)
                AdvertisementFactory.newAdvertisement(
                    GameAdvertisement.getAdvertisementType());
            
             searchedPeer=null;
            

            // Configure the new advertisement.
            gameInfo.setMaxPlayer(maxgame);
            gameInfo.setGameName(name);
            gameInfo.setCreatorID(localPeerID);
            

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

            
        }
        System.out.println("game presence annunced");
    }

    

    public void init(PeerGroup group)throws PeerGroupException    {
        System.out.println("init game presence discover");

        // Save a reference to the group of which that this service is
        // a part.
        peerGroup = group;

        // Get the local Peer ID.
        localPeerID = group.getPeerID().toString();

        this.registeredListeners=new ArrayList<GameListener>();
        
        // Registering our customized advertisement instance
        System.out.println("initiated game presence discover");
        
    }

        /**
     * Start the service.
     *
     * @param   args the arguments to the service. Not used.
     * @return  0 to indicate the service started.
     */
    public int startApp(String[] args) throws IOException

    {
        System.out.println("game presence discover starting");
        // Now that the service is being started, set the DiscoveryService
        // object to use to publish presence information.
        discovery = peerGroup.getDiscoveryService();
System.out.println("game presence discover started");
        discovery.addDiscoveryListener(this);

        return 0;
    }

    public List<GameAdvertisement> searchGames(boolean includeRemote) throws IOException{
        System.out.println("searching games...");
        // Add ourselves as a listener.
        

        Enumeration<Advertisement>e= discovery.getLocalAdvertisements(DiscoveryService.ADV, null, null);
        ArrayList<GameAdvertisement> advs=new ArrayList<GameAdvertisement>();
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
        System.out.println("game presence discover stopping");
        if (discovery != null)
        {
            // Unregister ourselves as a listener.
            discovery.removeDiscoveryListener(this);

            discovery = null;

            // Empty the set of listeners.
            registeredListeners.clear();
            
        }
        System.out.println("game presence discover stopped");
    }

  
}
