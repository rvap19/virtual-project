/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.management.discover;

import java.util.Enumeration;
import middle.management.advertisement.Advertisement;

/**
 *
 * @author root
 */
public interface DiscoveryResponseMsg {

    public Enumeration<Advertisement> getAdvertisements();
    
}
