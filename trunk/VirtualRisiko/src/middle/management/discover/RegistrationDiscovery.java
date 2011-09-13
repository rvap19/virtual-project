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
import middle.management.advertisement.RegistrationAdvertisement;
import middle.management.listener.RegistrationListener;



/**
 *
 * @author root
 */
public abstract class RegistrationDiscovery implements RisikoDiscoveryListener {

    
    protected List<RegistrationListener> registeredListeners;
    

    public  void addRegistrationListener(RegistrationListener listener)
    {
        registeredListeners.add(listener);
    }

    public void removeRegistartionListener(RegistrationListener listener){
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
                    if(isRegistrationAdvertisement(adv)){
                        RegistrationAdvertisement advertisement = (RegistrationAdvertisement) adv;
                        
                        Iterator<RegistrationListener> listeners = registeredListeners.iterator();
                        while (listeners.hasNext())
                        {
                            RegistrationListener listener = listeners.next();

                            // Notify the listener of the presence update.
                            listener.registrationUpdated(advertisement);
                        }
                       
                        
                    }

                    

                } catch (ClassCastException Ex) {
                   // Ex.printStackTrace();

                    // We are not dealing with a Peer Advertisement

                }

            }

        }
        
        

    }
    
    protected abstract boolean isRegistrationAdvertisement(Advertisement sdv);

    public abstract RegistrationAdvertisement announceRegistartion(String peerName,String gameID);

    public  void init(PeerGroup group){
        this.registeredListeners=new ArrayList<RegistrationListener>();
    }

    public abstract int startApp(String[] args) throws IOException;

    public abstract List<RegistrationAdvertisement> searchRegistrations(String gameID,boolean includeRemote) ;

    public abstract void stopApp();

  
}
