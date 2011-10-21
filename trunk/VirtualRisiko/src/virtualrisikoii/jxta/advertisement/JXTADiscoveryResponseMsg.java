/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package virtualrisikoii.jxta.advertisement;

import java.util.Enumeration;
import middle.management.advertisement.Advertisement;
import middle.management.discover.DiscoveryResponseMsg;


/**
 *
 * @author root
 */
public class JXTADiscoveryResponseMsg implements DiscoveryResponseMsg{
    private Enumeration<Advertisement> advs;
    
    public JXTADiscoveryResponseMsg(Enumeration<Advertisement> ads){
        this.advs=ads;
    }

    public Enumeration<Advertisement> getAdvertisements() {
        return advs;
    }
    
}
