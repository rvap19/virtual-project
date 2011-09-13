/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package middle.management.discover;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import middle.PeerGroup;
import middle.management.advertisement.PipeAdvertisement;
import middle.management.advertisement.Advertisement;
import middle.management.advertisement.PlayerAdvertisement;
import middle.management.listener.PipeListener;
import middle.management.listener.PlayerListener;


/**
 *
 * @author root
 */
public abstract class PlayerPresenceDiscover implements RisikoDiscoveryListener {

    
    
    protected List<PlayerListener> registeredListeners;
    protected List<PipeListener> registredPipeListeners;


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


    public void discoveryEvent(RisikoDiscoveryEvent TheDiscoveryEvent) {
       

        // Who triggered the event?
        
        DiscoveryResponseMsg TheDiscoveryResponseMsg = TheDiscoveryEvent.getResponse();

        
        if (TheDiscoveryResponseMsg!=null) {

            Enumeration<Advertisement> TheEnumeration = TheDiscoveryResponseMsg.getAdvertisements();

            while (TheEnumeration.hasMoreElements()) {

                Advertisement adv=TheEnumeration.nextElement();
                try {   
                    if(isPresenceAdvertisement(adv)){
                        PlayerAdvertisement advertisement = (PlayerAdvertisement) adv;
                        
                        Iterator<PlayerListener> listeners = registeredListeners.iterator();
                        while (listeners.hasNext())
                        {
                            PlayerListener listener = listeners.next();

                            // Notify the listener of the presence update.
                            listener.presenceUpdated(advertisement);
                        }
                       
                        
                    }else if(isPipeAdvertisement(adv)){
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
    
    protected abstract boolean isPresenceAdvertisement(Advertisement sdv);
    protected abstract boolean isPipeAdvertisement(Advertisement sdv);

    public abstract PlayerAdvertisement announcePresence(int presenceStatus,String name);
    public  void init(PeerGroup group)  {
        this.registeredListeners=new ArrayList<PlayerListener>();
        this.registredPipeListeners=new ArrayList<PipeListener>();
    }
    public abstract int startApp(String[] args) throws IOException;
    public abstract List<PlayerAdvertisement> searchPlayers(boolean includeRemoteSearch) throws IOException;
    public abstract List<PipeAdvertisement> searchPipes(boolean includeRemoteSearch) throws IOException;
    public abstract void stopApp();
    public abstract void  publishPipeAdvertisement(String name) throws IOException ;
    public abstract PipeAdvertisement getPipeAdvertisement();
}
