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
import middle.management.advertisement.Advertisement;
import middle.management.advertisement.GameAdvertisement;
import middle.management.listener.GameListener;



/**
 *
 * @author root
 */
public  abstract class GameDiscover implements RisikoDiscoveryListener {

   
    private List<GameListener> registeredListeners;
    
    public  void addGameListener(GameListener listener)
    {
        registeredListeners.add(listener);
    }

    public void removeGameListener(GameListener listener){
        registeredListeners.remove(listener);
    }

  

    public void discoveryEvent(RisikoDiscoveryEvent TheDiscoveryEvent) {
       

        // Who triggered the event?
        
        DiscoveryResponseMsg TheDiscoveryResponseMsg = TheDiscoveryEvent.getResponse();

        
        if (TheDiscoveryResponseMsg!=null) {

            Enumeration<Advertisement> TheEnumeration = TheDiscoveryResponseMsg.getAdvertisements();

            while (TheEnumeration.hasMoreElements()) {

                Advertisement adv=TheEnumeration.nextElement();
                try {   
                    if(isGameAdvertisement(adv)){
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

    protected abstract boolean isGameAdvertisement(Advertisement sdv);
    public abstract GameAdvertisement announceGame(String creatorName,int maxgame,String name,String mapName);
    public abstract void registerPlayer(GameAdvertisement gameAdv,List<String>playersID) throws IOException;  
    public  void init(PeerGroup group) {
        this.registeredListeners=new ArrayList<GameListener>();
        
    }
    public abstract int startApp(String[] args) throws IOException;
    public abstract List<GameAdvertisement> searchGames(boolean includeRemote) throws IOException;
    public abstract void stopApp();
}
