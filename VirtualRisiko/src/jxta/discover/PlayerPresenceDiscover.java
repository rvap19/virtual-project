/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jxta.discover;

import jxta.listener.PlayerListener;
import jxta.listener.PipeListener;
import jxta.advertisement.PlayerAdvertisement;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.jar.Attributes.Name;
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
import net.jxta.pipe.PipeID;
import net.jxta.pipe.PipeService;
import net.jxta.protocol.DiscoveryResponseMsg;
import net.jxta.protocol.PeerAdvertisement;
import net.jxta.protocol.PipeAdvertisement;

/**
 *
 * @author root
 */
public class PlayerPresenceDiscover implements DiscoveryListener {

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
    
    private List<PlayerListener> registeredListeners;
    private List<PipeListener> registredPipeListeners;

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
    

    public  void addPlayerListener(PlayerListener listener)
    {
        registeredListeners.add(listener);
    }

    public void removePlayerListener(PlayerListener listener){
        registeredListeners.remove(listener);
    }

    public void addPlayerPipeListener(PipeListener listener){
        registredPipeListeners.add(listener);
    }

    public void removePlayerPipeListener(PipeListener listener){
        registredPipeListeners.remove(listener);

    }


    public void getPlayerAdvertisementUpdated(PeerID peer){
        searchedPeer=peer;
        this.discovery.getRemoteAdvertisements(peer.URIEncodingName, DiscoveryService.ADV, null, null, 10);
        
    }

    public void discoveryEvent(DiscoveryEvent TheDiscoveryEvent) {
        System.out.println("remote discovery event....");

        // Who triggered the event?
        
        DiscoveryResponseMsg TheDiscoveryResponseMsg = TheDiscoveryEvent.getResponse();

        
        if (TheDiscoveryResponseMsg!=null) {

            Enumeration<Advertisement> TheEnumeration = TheDiscoveryResponseMsg.getAdvertisements();

            while (TheEnumeration.hasMoreElements()) {

                Advertisement adv=TheEnumeration.nextElement();
                try {   
                    if(adv.getAdvType().equalsIgnoreCase(PlayerAdvertisement.getAdvertisementType())){
                        PlayerAdvertisement advertisement = (PlayerAdvertisement) adv;
                        
                        Iterator<PlayerListener> listeners = registeredListeners.iterator();
                        while (listeners.hasNext())
                        {
                            PlayerListener listener = listeners.next();

                            // Notify the listener of the presence update.
                            listener.presenceUpdated(advertisement);
                        }
                       
                        
                    }else if(adv.getAdvType().equalsIgnoreCase(PipeAdvertisement.getAdvertisementType())){
                        PipeAdvertisement advertisement = (PipeAdvertisement) adv;

                        Iterator<PipeListener> listeners = registredPipeListeners.iterator();
                        while (listeners.hasNext())
                        {
                            PipeListener listener = listeners.next();

                            // Notify the listener of the presence update.
                            listener.pipeUpdated(advertisement);
                        }
                        
                        
                    }

                    

                } catch (ClassCastException Ex) {
                   // Ex.printStackTrace();

                    // We are not dealing with a Peer Advertisement

                }

            }

        }
        
        

    }

    public void announcePresence(int presenceStatus,String name)
    {
        System.out.println("player presence announcing");
        if (discovery != null)
        {
             presenceInfo = (PlayerAdvertisement)
                AdvertisementFactory.newAdvertisement(
                    PlayerAdvertisement.getAdvertisementType());
            
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

            
        }
        System.out.println("player presence annunced");
    }

    

    public void init(PeerGroup group)throws PeerGroupException    {
        System.out.println("init player presence discover");

        // Save a reference to the group of which that this service is
        // a part.
        peerGroup = group;

        // Get the local Peer ID.
        localPeerID = group.getPeerID().toString();

        this.registeredListeners=new ArrayList<PlayerListener>();
        this.registredPipeListeners=new ArrayList<PipeListener>();
        // Registering our customized advertisement instance
        System.out.println("initiated player presence discover");
        
    }

        /**
     * Start the service.
     *
     * @param   args the arguments to the service. Not used.
     * @return  0 to indicate the service started.
     */
    public int startApp(String[] args) throws IOException

    {
        System.out.println("player presence discover starting");
        // Now that the service is being started, set the DiscoveryService
        // object to use to publish presence information.
        discovery = peerGroup.getDiscoveryService();
System.out.println("player presence discover started");
        

        return 0;
    }

    public Enumeration<PlayerAdvertisement> searchPlayers(boolean includeRemoteSearch) throws IOException{
        System.out.println("searching players...");
        // Add ourselves as a listener.
        discovery.addDiscoveryListener(this);

        Enumeration<Advertisement>e= discovery.getLocalAdvertisements(DiscoveryService.ADV, null, null);
        
        if(includeRemoteSearch)
            discovery.getRemoteAdvertisements(null, DiscoveryService.ADV,
            null, null, 10, this);

        return e;
    }

    public Enumeration<Advertisement> searchPlayers(int type) throws IOException{
        System.out.println("searching players...");
        // Add ourselves as a listener.
        discovery.addDiscoveryListener(this);

        Enumeration<Advertisement>e= discovery.getLocalAdvertisements(type, null, null);


        discovery.getRemoteAdvertisements(null, type,
            null, null, 10, this);

        return e;
    }

    /**
     * Stop the service.
     */
    public void stopApp()
    {
        System.out.println("player presence discover stopping");
        if (discovery != null)
        {
            // Unregister ourselves as a listener.
            discovery.removeDiscoveryListener(this);

            discovery = null;

            // Empty the set of listeners.
            registeredListeners.clear();
            registredPipeListeners.clear();
        }
        System.out.println("player presence discover stopped");
    }

    private void  publishPipeAdvertisement(String name) throws IOException {

        System.out.println("publishing pipe adv");
        // Creating a Pipe Advertisement
        String newName="Player "+name;
        MyPipeAdvertisement = (PipeAdvertisement) AdvertisementFactory.newAdvertisement(PipeAdvertisement.getAdvertisementType());
        PipeID MyPipeID = IDFactory.newPipeID(PeerGroupID.defaultNetPeerGroupID, newName.getBytes());

        MyPipeAdvertisement.setPipeID(MyPipeID);
        MyPipeAdvertisement.setType(PipeService.PropagateType);
        MyPipeAdvertisement.setName(name+" Pipe");
        MyPipeAdvertisement.setDescription("Created by " + name);

        this.discovery.publish(MyPipeAdvertisement,DEFAULT_EXPIRATION, DEFAULT_LIFETIME);
        this.discovery.remotePublish(MyPipeAdvertisement, DEFAULT_LIFETIME);

        System.out.println("pipe adv published");

    }
}
