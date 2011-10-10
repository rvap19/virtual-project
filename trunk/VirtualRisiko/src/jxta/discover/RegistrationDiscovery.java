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
import jxta.advertisement.JXTARisikoDiscoveryEvent;
import jxta.advertisement.RegistrationAdvertisement;
import jxta.communication.JXTAPeerGroup;
import jxta.listener.RegistrationListener;
import middle.PeerGroup;


import net.jxta.discovery.DiscoveryEvent;
import net.jxta.discovery.DiscoveryListener;
import net.jxta.discovery.DiscoveryService;
import net.jxta.document.Advertisement;
import net.jxta.document.AdvertisementFactory;

import net.jxta.exception.PeerGroupException;



import net.jxta.protocol.DiscoveryResponseMsg;


/**
 *
 * @author root
 */
public class RegistrationDiscovery extends middle.management.discover.RegistrationDiscovery implements DiscoveryListener {

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
            Advertisement adv;
            Vector<middle.management.advertisement.Advertisement> result=new Vector<middle.management.advertisement.Advertisement>();
            while(TheEnumeration.hasMoreElements()){
                adv=TheEnumeration.nextElement();
                if(adv.getAdvType().equals(RegistrationAdvertisement.getAdvertisementType())){
                    RegistrationAdvertisement current=(RegistrationAdvertisement) adv;
                    result.add(current);
                }
                
            }
            
            JXTADiscoveryResponseMsg msg=new JXTADiscoveryResponseMsg(result.elements());
            JXTARisikoDiscoveryEvent event=new JXTARisikoDiscoveryEvent(msg);
            super.discoveryEvent(event);
        }
        
        

    }

    public RegistrationAdvertisement announceRegistartion(String peerName,String gameID)
    {
        
        if (discovery != null)
        {
             RegistrationAdvertisement registrationInfo = (RegistrationAdvertisement)
                AdvertisementFactory.newAdvertisement(
                    RegistrationAdvertisement.getAdvertisementType());
              if(gameID==null){
                  return null;
              }

            // Configure the new advertisement.
            registrationInfo.setGameID(gameID);
            registrationInfo.setPeerID(peerName);
            registrationInfo.setTime(System.currentTimeMillis());
            

            try
            {
                // Publish the advertisement locally.
                discovery.publish(registrationInfo, DEFAULT_EXPIRATION, DEFAULT_LIFETIME);
                // Publish the advertisement remotely.
                discovery.remotePublish(registrationInfo,DEFAULT_LIFETIME);
                discovery.remotePublish(null, registrationInfo, DEFAULT_LIFETIME);
                
            }
            catch (IOException e)
            {
                System.out.println("Error publishing locally: " + e);
            }
           
                return registrationInfo;
            
        }
       
        return null;
        
    }

    

    public void init(PeerGroup group)   {
        super.init(group);

        // Save a reference to the group of which that this service is
        // a part.
        peerGroup = group;

        // Get the local Peer ID.
        JXTAPeerGroup pG=(JXTAPeerGroup)group;
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
       
        // Now that the service is being started, set the DiscoveryService
        // object to use to publish presence information.
        JXTAPeerGroup pG=(JXTAPeerGroup)this.peerGroup;
        discovery = pG.getPeerGroup().getDiscoveryService();

        discovery.addDiscoveryListener(this);

        return 0;
    }

    public List<middle.management.advertisement.RegistrationAdvertisement> searchRegistrations(String gameID,boolean includeRemote) {
       
        // Add ourselves as a listener.
        

        Enumeration<Advertisement>e;
        try {
            e = discovery.getLocalAdvertisements(DiscoveryService.ADV, RegistrationAdvertisement.tagGameID, gameID);
        } catch (IOException ex) {
            System.out.println("IO Exception in finding registration advertisement");
            return null;
        }
        ArrayList<middle.management.advertisement.RegistrationAdvertisement> advs=new ArrayList<middle.management.advertisement.RegistrationAdvertisement>();
        while(e.hasMoreElements()){
            Advertisement a=e.nextElement();
            if(a.getAdvType().equals(RegistrationAdvertisement.AdvertisementType)){
                advs.add((RegistrationAdvertisement) a);
            }
        }
        
        if(includeRemote)
            discovery.getRemoteAdvertisements(null, DiscoveryService.ADV,RegistrationAdvertisement.tagGameID, gameID, 10, this);

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
            
        }
        
    }

    @Override
    protected boolean isRegistrationAdvertisement(middle.management.advertisement.Advertisement sdv) {
        return sdv.getAdvType().equals(RegistrationAdvertisement.getAdvertisementType());
    }

    

  
}
